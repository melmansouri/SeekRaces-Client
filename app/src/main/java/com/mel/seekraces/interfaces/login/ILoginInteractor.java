package com.mel.seekraces.interfaces.login;

import com.mel.seekraces.entities.User;

/**
 * Created by moha on 10/01/17.
 */

public interface ILoginInteractor {
    void login(User user);
    void forgotPwd(String url);
}
