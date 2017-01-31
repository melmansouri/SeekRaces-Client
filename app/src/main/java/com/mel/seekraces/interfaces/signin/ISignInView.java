package com.mel.seekraces.interfaces.signin;

import com.mel.seekraces.entities.Response;

/**
 * Created by moha on 10/01/17.
 */

public interface ISignInView {
    void selectPictureProfile();
    void showProgress();
    void hideProgress();
    void showMessage(String message);
    void showErrorEmail(String message);
    void hideErrorEmail();
    void showErrorPwd(String message);
    void hideErrorPwd();
    void showErrorPwdRepeat(String message);
    void hideErrorPwdRepeat();
    void showErrorUserName(String message);
    void hideErrorUserName();
    void showComponents();
    void hideComponents();
    void returnToLoginScreen();
    void fillImageViewFromCamera();
    void fillImageViewFromGallery();
    void openCamera();
    void openGalery();
    void navigateUpFromSameTask();
    void signIn();
}
