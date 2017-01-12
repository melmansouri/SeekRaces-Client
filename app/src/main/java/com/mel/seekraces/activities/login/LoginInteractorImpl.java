package com.mel.seekraces.activities.login;

import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.login.ILoginInteractor;

/**
 * Created by moha on 10/01/17.
 */

public class LoginInteractorImpl implements ILoginInteractor{
    private IListennerCallBack listennerCallBack;

    public LoginInteractorImpl(IListennerCallBack listennerCallBack){
        this.listennerCallBack=listennerCallBack;
    }

    @Override
    public void login() {
    }
}
