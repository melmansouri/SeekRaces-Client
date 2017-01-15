package com.mel.seekraces.activities.login;

import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.User;
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
    public void login(User user) {

    }

    @Override
    public void activityResult(int requestCode, int resultCode, int resultOk) {
        if (requestCode == Constantes.REQUEST_START_SIGNIN_FOR_RESULT) {
            if (resultCode == resultOk) {
                loginView.showMessage("Se le ha enviado un correo de confirmación.");
            }
        }
    }

    @Override
    public void onDestroy() {
        loginView=null;
    }


    @Override
    public void onSuccess(Response response) {

    }

    @Override
    public void onError(Response response) {

    }
}
