package com.mel.seekraces.interfaces.login;

import com.mel.seekraces.entities.User;

/**
 * Created by moha on 10/01/17.
 */

public interface ILoginPresenter {

    void login(boolean isOnline, boolean verifyDataUser, User user);

    void checkSession();
    void startActivitySignIn();
    void activityResult(int requestCode, int resultCode);
    void forgotPwd(boolean isOnline,String email);
    void onDestroy();

    void checkSignInGoogle(boolean success);
}
