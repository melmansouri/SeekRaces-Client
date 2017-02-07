package com.mel.seekraces.interfaces.login;

/**
 * Created by moha on 10/01/17.
 */

public interface ILoginView {

    void goToSignIn();

    void startActivitySignIn();

    void login();

    void showEmailError(String message);
    void hideEmailError();

    void showPwdError(String message);
    void hidePwdError();

    void showMessage(String message);

    void showProgress();

    void hideProgress();

    void showComponentScreen();

    void hideComponentScreen();

    void goToMainScreen();

    void loginFacebook();

    void fogotPassword();

    void finishActivity();

    void saveCountriesCitiesSqlite(String countries,String cities);
}
