package com.mel.seekraces.interfaces.signin;

/**
 * Created by moha on 10/01/17.
 */

public interface ISignInView {
    void selectPictureProfile();
    void showProgress();
    void hideProgress();
    void showMessageInfoSnackBar(String message);
    void showMessageError(String message);
    void showErrorEmail(String message);
    void showErrorPwd(String message);
    void showErrorUserName(String message);
    void returnToLoginScreen();
    void signIn();
}
