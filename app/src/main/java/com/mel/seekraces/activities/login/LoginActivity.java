package com.mel.seekraces.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.mel.seekraces.R;
import com.mel.seekraces.activities.signin.SignInActivity;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.login.ILoginPresenter;
import com.mel.seekraces.interfaces.login.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    @BindView(R.id.edtEmail)
    TextInputEditText edtEmail;
    @BindView(R.id.edtPassword)
    TextInputEditText edtPassword;
    @BindView(R.id.text_input_layout_email)
    TextInputLayout textInputLayoutEmail;
    @BindView(R.id.text_input_layout_pass)
    TextInputLayout textInputLayoutPass;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenterImpl(this);
        UtilsViews.PermisosValidos(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginPresenter.activityResult(requestCode, resultCode, RESULT_OK);
    }

    @Override
    @OnClick(R.id.btnSignIn)
    public void goToSignIn() {
        Intent i = new Intent(this, SignInActivity.class);
        startActivityForResult(i, Constantes.REQUEST_START_SIGNIN_FOR_RESULT);
    }

    @Override
    @OnClick(R.id.btnLogin)
    public void login() {
        User user=new User();
        user.setEmail(edtEmail.getText().toString());
        user.setPwd(edtPassword.getText().toString());
        loginPresenter.login(user);
    }

    @Override
    public void showEmailError(String message) {
        textInputLayoutEmail.setError(message);
    }

    @Override
    public void hideEmailError() {
        textInputLayoutEmail.setErrorEnabled(false);
    }

    @Override
    public void showPwdError(String message) {
        textInputLayoutPass.setError(message);
    }

    @Override
    public void hidePwdError() {
        textInputLayoutPass.setErrorEnabled(false);
    }

    @Override
    public void showMessage(String message) {
        UtilsViews.showSnackBar(coordinatorLayout,message);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        UtilsViews.disableScreen(this);
        UtilsViews.closeKeyBoard(this);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        UtilsViews.enableSreen(this);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
    }
}
