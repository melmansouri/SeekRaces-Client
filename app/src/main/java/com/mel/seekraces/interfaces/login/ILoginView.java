package com.mel.seekraces.interfaces.login;

/**
 * Created by moha on 10/01/17.
 */

public interface ILoginView {

    void goToSignIn();

    void login();

    void showEmailError(String message);

    void showPwdError(String message);

    void showErrorLogin();

    void showProgress();

    void closeProgress();

    void goToMainScreen();

    void loginFacebook();

    void fogotPassword();
}
