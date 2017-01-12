package com.mel.seekraces.activities.login;

import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.login.ILoginInteractor;
import com.mel.seekraces.interfaces.login.ILoginPresenter;
import com.mel.seekraces.interfaces.login.ILoginView;

/**
 * Created by moha on 10/01/17.
 */

public class LoginPresenterImpl implements ILoginPresenter ,IListennerCallBack{

    private ILoginView loginView;
    private ILoginInteractor loginInteractor;

    public LoginPresenterImpl(ILoginView loginView){
        this.loginView=loginView;
        this.loginInteractor=new LoginInteractorImpl(this);
    }

    @Override
    public void login() {

    }

    private void validateEmail(String email) {

    }

    private void validatePassword(String pwd) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }
}
