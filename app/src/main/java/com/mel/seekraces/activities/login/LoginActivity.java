package com.mel.seekraces.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;

import com.mel.seekraces.R;
import com.mel.seekraces.activities.signin.SignInActivity;
import com.mel.seekraces.interfaces.login.ILoginPresenter;
import com.mel.seekraces.interfaces.login.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ILoginView{

    @BindView(R.id.edtEmail)
    TextInputEditText edtEmail;
    @BindView(R.id.edtPassword)
    TextInputEditText edtPassword;

    private ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //loginPresenter=new LoginPresenterImpl(this);

    }

    @Override
    @OnClick(R.id.btnSignIn)
    public void goToSignIn() {
        Intent i=new Intent(this, SignInActivity.class);
        startActivity(i);
    }

    @Override
    public void login() {

    }

    @Override
    public void showEmailError(String message) {

    }

    @Override
    public void showPwdError(String message) {

    }

    @Override
    public void showErrorLogin() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void closeProgress() {

    }

    @Override
    public void goToMainScreen() {

    }

    @Override
    public void loginFacebook() {

    }

    @Override
    public void fogotPassword() {

    }
}
