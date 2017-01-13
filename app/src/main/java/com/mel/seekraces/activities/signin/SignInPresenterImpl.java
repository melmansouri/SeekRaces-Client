package com.mel.seekraces.activities.signin;

import com.mel.seekraces.interfaces.signin.ISignInPresenter;
import com.mel.seekraces.interfaces.signin.ISignInView;
import com.mel.seekraces.pojos.User;

/**
 * Created by moha on 13/01/17.
 */

public class SignInPresenterImpl implements ISignInPresenter {
    private ISignInView view;

    public SignInPresenterImpl(ISignInView view) {
        this.view = view;
    }

    @Override
    public void signIn(User user) {
        if (!verifyDataUser(user)){
            view.hideProgress();
        }
    }

    private boolean verifyDataUser(User user){
        boolean result=true;
        if (user.getUsername().isEmpty()){
            view.showErrorUserName("El nombre de usuario no puede estar vacío");
            result=false;
        }
        if (user.getEmail().isEmpty()){
            view.showErrorEmail("El email no puede estar vacio");
            result=false;
        }
        if (user.getPwd().isEmpty()){
            view.showErrorPwd("La contraseña no puede estar vacía");
            result=false;
        }else if(!user.getPwd().equals(user.getPwd_repeat())) {
            view.showErrorPwdRpeat("Las contraseñas no coincíden");
            result=false;
        }
        return result;
    }

    @Override
    public void activityResult(int resultCode, int type_result) {
        if (resultCode==type_result){
            view.fillImageView();
        }
    }


    @Override
    public void selectOptionDialogPicture(String[] options, int selected) {
        if(options[selected].equals("Tomar foto")){
            view.openCamera();
        }else if(options[selected].equals("Elegir de galeria")){
            view.openGalery();
        }
    }


}
