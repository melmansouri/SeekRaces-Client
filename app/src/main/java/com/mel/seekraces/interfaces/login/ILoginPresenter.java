package com.mel.seekraces.interfaces.login;

import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.User;

/**
 * Created by moha on 10/01/17.
 */

public interface ILoginPresenter {
    void login(User user);
    void activityResult(int requestCode, int resultCode, int resultOk);
    void onDestroy();

}
