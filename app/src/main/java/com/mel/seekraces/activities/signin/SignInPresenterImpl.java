package com.mel.seekraces.activities.signin;

import android.util.Log;

import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.signin.ISignInInteractor;
import com.mel.seekraces.interfaces.signin.ISignInPresenter;
import com.mel.seekraces.interfaces.signin.ISignInView;

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
    public void signIn(boolean isOnline,User user) {
        if (!isOnline){
            view.showMessage("Comprueba tu conexión");
            return;
        }
        if (!verifyDataUser(user)) {
            view.hideProgress();
            return;
        }
        interactor.signIn(user);
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
        }else if(!Utils.isValidEmail(user.getEmail())){
            view.showErrorEmail("Introduzca un email válido");
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
    public void activityResult(int requestCode,int resultCode) {
        if (resultCode == RMapped.RESULT_OK.getValue()) {
            if (requestCode==Constantes.REQUEST_IMAGE_CAPTURE_CAMERA){
                view.fillImageViewFromCamera();
            }else if(requestCode==Constantes.REQUEST_IMAGE_CAPTURE_GALLERY){
                view.fillImageViewFromGallery();
            }

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
