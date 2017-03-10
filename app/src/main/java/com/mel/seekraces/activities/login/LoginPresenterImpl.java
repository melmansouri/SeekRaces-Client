package com.mel.seekraces.activities.login;

import android.text.TextUtils;
import android.util.Log;

import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.login.ILoginInteractor;
import com.mel.seekraces.interfaces.login.ILoginPresenter;
import com.mel.seekraces.interfaces.login.ILoginView;
import com.mel.seekraces.tasks.SaveUserLoginTask;

/**
 * Esta clase actua de intermediario entre la vista y la clase que proprciona los datos que en este caso es el interactor
 * Created by moha on 10/01/17.
 */

public class LoginPresenterImpl implements ILoginPresenter, IListennerCallBack {

    private ILoginView view;
    private ILoginInteractor loginInteractor;
    private SharedPreferencesSingleton sharedPreferencesSingleton;

    public LoginPresenterImpl(ILoginView loginView, SharedPreferencesSingleton sharedPreferencesSingleton) {
        this.view = loginView;
        this.loginInteractor = new LoginInteractorImpl(this);
        this.sharedPreferencesSingleton = sharedPreferencesSingleton;
    }

    @Override
    public void login(boolean isOnline, boolean verifyDataUser, User user) {
        if (view!=null){
                if (!isOnline) {
                    view.showMessage("Comprueba tu conexión");
                    return;
                }
                view.showProgress();
                if (!validateDataLogin(verifyDataUser,user)) {
                    view.hideProgress();
                    view.showComponentScreen();
                    return;
                }
                loginInteractor.login(user);
        }
    }

    @Override
    public void checkSession() {
        if (view!=null){
            if (sharedPreferencesSingleton.containValue(Constantes.KEY_USER)) {
                view.goToMainScreen();
            }
        }
    }

    @Override
    public void startActivitySignIn() {
        if (view!=null){
            view.startActivitySignIn();
        }
    }

    private boolean validateDataLogin(boolean verifyDataUser, User user) {
        boolean result = true;
        if (!verifyDataUser){
            return result;
        }
        if (user.getEmail().isEmpty()) {
            view.showEmailError("El email no puede estar vacio");
            result = false;
        } else if (!Utils.isValidEmail(user.getEmail())) {
            view.showEmailError("Introduzca un email válido");
            result = false;
        }
        if (user.getPwd().isEmpty()) {
            view.showPwdError("La contraseña no puede estar vacía");
            result = false;
        }
        return result;
    }

    @Override
    public void activityResult(int requestCode, int resultCode) {
        if (view!=null){
            if (resultCode == RMapped.RESULT_OK.getValue()) {
                if (requestCode == Constantes.REQUEST_START_SIGNIN_FOR_RESULT) {
                    view.showMessage("Se le ha enviado un correo de confirmación.");
                } else if (requestCode == Constantes.REQUEST_START_MAIN_FOR_RESULT) {
                    view.finishActivity();
                }else if(requestCode==Constantes.REQUEST_START_SIGNIN_GOOGLE_FOR_RESULT){
                    view.checkSignInGoogle();

                }
            }else{
                //view.revokeAccessSignInGoogle();
            }
        }
    }

    @Override
    public void forgotPwd(boolean isOnline, String email) {
        if (view!=null){
            if (!isOnline) {
                view.showMessage("Comprueba tu conexión");
                return;
            }
            view.showProgress();
            if (!validateDataForgotPwd(email)) {
                view.hideProgress();
                view.showComponentScreen();
                return;
            }
            String url = INetworkConnectionApi.BASE_URL + "user/" + email + "/forgotPassword";
            loginInteractor.forgotPwd(url);

        }
    }

    private boolean validateDataForgotPwd(String email) {
        boolean result = true;
        if (TextUtils.isEmpty(email)) {
            view.showMessage("Introduzca el email para la recuperación de la contraseña");
            result = false;
        } else if (!Utils.isValidEmail(email)) {
            view.showMessage("Introduzca un email válido");
            result = false;
        }
        return result;
    }

    @Override
    public void onDestroy() {
        view = null;
        loginInteractor.login(null);
        loginInteractor.forgotPwd(null);
        loginInteractor=null;
        sharedPreferencesSingleton=null;
    }

    @Override
    public void checkSignInGoogle(boolean success) {
        if (success){
            view.revokeAccessSignInGoogle();
            view.loginGoogle();
        }else{
            Response response=new Response();
            response.setMessage("Error al iniciar sesión con google");
            onError(response);
        }
    }


    @Override
    public void onSuccess(Object object) {
        if (view!=null){
            if (object instanceof String){
                view.hideProgress();
                view.showComponentScreen();
                view.showMessage((String)object);
                return;
            }
            new SaveUserLoginTask(((Response) object).getContent(),sharedPreferencesSingleton){
                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    view.hideProgress();
                    if (sharedPreferencesSingleton.containValue(Constantes.KEY_USER)){
                        view.goToMainScreen();
                    }else{
                        view.showComponentScreen();
                        view.showMessage("Error al iniciar sesión");
                    }
                }
            }.execute();
            /*try {
                User user = new Gson().fromJson(((Response) object).getContent(), User.class);
                sharedPreferencesSingleton.saveStringSP(Constantes.KEY_USER, user.getEmail());
                sharedPreferencesSingleton.saveStringSP(Constantes.KEY_USER_NAME_PICTURE, user.getPhoto_url());
                sharedPreferencesSingleton.saveStringSP(Constantes.KEY_USERNAME, user.getUsername());
            }catch (Exception e){
                e.printStackTrace();
            }*/

        }
    }

    @Override
    public void onError(Response response) {
        if (view!=null){
            view.hideProgress();
            view.showComponentScreen();
            view.showMessage(response.getMessage());
            Log.e("tag", response.toString());
        }
    }
}
