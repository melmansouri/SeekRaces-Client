package com.mel.seekraces.activities.login;

import android.util.Log;

import com.google.gson.Gson;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.login.ILoginInteractor;
import com.mel.seekraces.interfaces.login.ILoginPresenter;
import com.mel.seekraces.interfaces.login.ILoginView;
import com.mel.seekraces.tasks.SaveUserLoginTask;

/**
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
    public void login(boolean isOnline,boolean havePermission,User user) {
        if (!isOnline){
            view.showMessage("Comprueba tu conexión");
            return;
        }
        if (havePermission) {
            view.showProgress();
            if (!validateDataLogin(user)) {
                view.hideProgress();
                return;
            }
            loginInteractor.login(user);
        }
    }

    @Override
    public void checkSession() {
        if (sharedPreferencesSingleton.containValue(Constantes.KEY_USER)){
            view.goToMainScreen();
        }
    }

    @Override
    public void startActivitySignIn(boolean havePermission) {
        if (havePermission){
            view.startActivitySignIn();
        }
    }

    private boolean validateDataLogin(User user) {
        boolean result = true;
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
        if (resultCode == RMapped.RESULT_OK.getValue()) {
            if (requestCode == Constantes.REQUEST_START_SIGNIN_FOR_RESULT) {
                view.showMessage("Se le ha enviado un correo de confirmación.");
            }else if (requestCode == Constantes.REQUEST_START_MAIN_FOR_RESULT) {
                view.finishActivity();
            }
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }


    @Override
    public void onSuccess(Object object) {
        view.hideProgress();
        User user=new Gson().fromJson(((Response)object).getContent(),User.class);
        sharedPreferencesSingleton.saveStringSP(Constantes.KEY_USER,user.getEmail());
        sharedPreferencesSingleton.saveStringSP(Constantes.KEY_USER_NAME_PICTURE,user.getPhoto_url());
        sharedPreferencesSingleton.saveStringSP(Constantes.KEY_USERNAME,user.getUsername());

        view.goToMainScreen();
    }

    @Override
    public void onError(Response response) {
        view.hideProgress();
        view.showMessage(response.getMessage());
        Log.e("tag", response.toString());
    }
}
