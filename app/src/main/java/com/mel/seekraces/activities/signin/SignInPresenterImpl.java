package com.mel.seekraces.activities.signin;

import android.util.Log;

import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.signin.ISignInInteractor;
import com.mel.seekraces.interfaces.signin.ISignInPresenter;
import com.mel.seekraces.interfaces.signin.ISignInView;
import com.mel.seekraces.entities.User;

/**
 * Created by moha on 13/01/17.
 */

public class SignInPresenterImpl implements ISignInPresenter, IListennerCallBack{
    private ISignInView view;
    private ISignInInteractor interactor;

    public SignInPresenterImpl(ISignInView view) {
        this.view = view;
        this.interactor=new SignInInteractorImpl(this);
    }

    @Override
    public void signIn(User user) {
        if (!verifyDataUser(user)) {
            view.hideProgress();
            return;
        }
        interactor.login(user);
    }

    private boolean verifyDataUser(User user) {
        boolean result = true;
        if (user.getUsername().isEmpty()) {
            view.showErrorUserName("El nombre de usuario no puede estar vacío");
            result = false;
        }
        if (user.getEmail().isEmpty()) {
            view.showErrorEmail("El email no puede estar vacio");
            result = false;
        }
        if (user.getPwd().isEmpty()) {
            view.showErrorPwd("La contraseña no puede estar vacía");
            result = false;
        } else if (user.getPwd().length() < Constantes.MIN_LENGTH_PASSWORD) {
            view.showErrorPwd(String.format("La contraseña debe estar compuesta de al menos %1$2s caracteres alfanuméricos", Constantes.MIN_LENGTH_PASSWORD));
            result=false;
        }else if (!user.getPwd_repeat().equals(user.getPwd())) {
            view.showErrorPwdRepeat("Las contraseñas no coincíden");
            return false;
        } else if (user.getPwd_repeat().equals(user.getPwd())) {
            view.hideErrorPwdRepeat();
        }
        return result;
    }



    @Override
    public void activityResult(int resultCode, int type_result) {
        if (resultCode == type_result) {
            view.fillImageView();
        }
    }


    @Override
    public void selectOptionDialogPicture(String[] options, int selected) {
        if (options[selected].equals("Tomar foto")) {
            view.openCamera();
        } else if (options[selected].equals("Elegir de galeria")) {
            view.openGalery();
        }
    }

    @Override
    public void validatePasswordRepeat(String pwd, String pwdRepeat) {
        if (pwdRepeat.equals(pwd)) {
            view.hideErrorPwdRepeat();
        }
    }

    @Override
    public void onDestroy() {
        view=null;
    }


    @Override
    public void onSuccess(Response response) {
        view.hideProgress();
        view.showMessage(response.getMessage());
        Log.e("tag",response.toString());
        view.returnToLoginScreen();
    }

    @Override
    public void onError(Response response) {
        view.hideProgress();
        view.showMessage(response.getMessage());
        Log.e("tag",response.toString());
    }
}
