package com.mel.seekraces.activities.login;

import android.util.Log;

import com.google.gson.Gson;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.login.ILoginInteractor;
import com.mel.seekraces.interfaces.login.ILoginPresenter;
import com.mel.seekraces.interfaces.login.ILoginView;

/**
 * Created by moha on 10/01/17.
 */

public class LoginPresenterImpl implements ILoginPresenter ,IListennerCallBack{

    private ILoginView view;
    private ILoginInteractor loginInteractor;
    private SharedPreferencesSingleton sharedPreferencesSingleton;

    public LoginPresenterImpl(ILoginView loginView, SharedPreferencesSingleton sharedPreferencesSingleton){
        this.view=loginView;
        this.loginInteractor=new LoginInteractorImpl(this);
        this.sharedPreferencesSingleton=sharedPreferencesSingleton;
    }

    @Override
    public void login(User user) {
        view.showProgress();
        if (!validateDataLogin(user)){
            view.hideProgress();
            return;
        }
        loginInteractor.login(user);
    }

    private boolean validateDataLogin(User user){
        boolean result = true;
        if (user.getEmail().isEmpty()) {
            view.showEmailError("El email no puede estar vacio");
            result = false;
        }else if(!Utils.isValidEmail(user.getEmail())){
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
    public void activityResult(int requestCode, int resultCode, int resultOk) {
        if (requestCode == Constantes.REQUEST_START_SIGNIN_FOR_RESULT) {
            if (resultCode == resultOk) {
                view.showMessage("Se le ha enviado un correo de confirmación.");
            }
        }
    }

    @Override
    public void onDestroy() {
        view=null;
    }


    @Override
    public void onSuccess(Response response) {
        view.hideProgress();
        Log.e("tag",response.toString());
        User user=new Gson().fromJson(response.getContent(),User.class);
        if ((user.getPhotoBase64()!=null && !user.getPhotoBase64().isEmpty()) && (user.getPhoto_url() !=null&&!user.getPhoto_url().isEmpty())){
            if (Utils.mkdir(Constantes.RUTA_IMAGENES)){
                Utils.saveImage(user.getPhotoBase64(),Constantes.RUTA_IMAGENES+user.getPhoto_url());
                user.setPhotoBase64("");
            }
        }
        sharedPreferencesSingleton.saveStringSP(Constantes.KEY_USER,new Gson().toJson(user));

        view.goToMainScreen();
    }

    @Override
    public void onError(Response response) {
        view.hideProgress();
        view.showMessage(response.getMessage());
        Log.e("tag",response.toString());
    }
}
