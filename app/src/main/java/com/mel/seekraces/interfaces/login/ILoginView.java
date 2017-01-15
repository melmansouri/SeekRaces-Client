package com.mel.seekraces.interfaces.login;

/**
 * Created by moha on 10/01/17.
 */

public interface ILoginView {

    void goToSignIn();

    void login();

    void showEmailError(String message);
    void hideEmailError();

    void showPwdError(String message);
    void hidePwdError();

    void showMessage(String message);

    void showProgress();

    void hideProgress();

    void goToMainScreen();

    void loginFacebook();

    void fogotPassword();
}
