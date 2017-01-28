package com.mel.seekraces.interfaces.login;

import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.User;

/**
 * Created by moha on 10/01/17.
 */

public interface ILoginPresenter {
    void login(boolean havePermission,User user);
    void checkSession();
    void startActivitySignIn(boolean havePermission);
    void activityResult(int requestCode, int resultCode, int resultOk);
    void onDestroy();

}
