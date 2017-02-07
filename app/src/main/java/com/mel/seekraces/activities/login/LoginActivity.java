package com.mel.seekraces.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.mel.seekraces.R;
import com.mel.seekraces.activities.main.MainActivity;
import com.mel.seekraces.activities.signin.SignInActivity;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.login.ILoginPresenter;
import com.mel.seekraces.interfaces.login.ILoginView;
import com.mel.seekraces.tasks.SaveCountriesCitiesSqlite;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

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
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    @BindView(R.id.activity_login)
    RelativeLayout activityLogin;

    private ILoginPresenter loginPresenter;

    private SharedPreferencesSingleton sharedPreferencesSingleton;

    private LoginManager loginManagerFacebook;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sharedPreferencesSingleton = SharedPreferencesSingleton.getInstance(this);
        loginPresenter = new LoginPresenterImpl(this, sharedPreferencesSingleton);
        loginManagerFacebook=LoginManager.getInstance();
        callbackManager=CallbackManager.Factory.create();
        loginManagerFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("LoginFacebook",loginResult.getAccessToken().toString());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
        loginPresenter.checkSession();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        loginPresenter.activityResult(requestCode, resultCode);
    }

    @Override
    @OnClick(R.id.btnSignIn)
    public void goToSignIn() {
        loginPresenter.startActivitySignIn(UtilsViews.PermisosValidos(this));
    }

    @Override
    public void startActivitySignIn() {
        Intent i = new Intent(this, SignInActivity.class);
        startActivityForResult(i, Constantes.REQUEST_START_SIGNIN_FOR_RESULT);
    }

    @Override
    @OnClick(R.id.btnLogin)
    public void login() {
        UtilsViews.closeKeyBoard(this);
        User user = new User();
        user.setEmail(edtEmail.getText().toString());
        user.setPwd(edtPassword.getText().toString());
        user.setToken_push(sharedPreferencesSingleton.getStringSP(Constantes.KEY_TOKEN_PUSH));
        sharedPreferencesSingleton.removeValueSP(Constantes.KEY_TOKEN_PUSH);
        loginPresenter.login(Utils.isOnline(this), UtilsViews.PermisosValidos(this), user);
    }

    @Override
    public void showEmailError(String message) {
        textInputLayoutEmail.setError(message);
    }

    @Override
    @OnTextChanged(value = R.id.edtEmail, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void hideEmailError() {
        textInputLayoutEmail.setErrorEnabled(false);
    }

    @Override
    public void showPwdError(String message) {
        textInputLayoutPass.setError(message);
    }

    @Override
    @OnTextChanged(value = R.id.edtPassword, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void hidePwdError() {
        textInputLayoutPass.setErrorEnabled(false);
    }

    @Override
    public void showMessage(String message) {
        UtilsViews.showSnackBar(coordinatorLayout, message);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        hideComponentScreen();
        //UtilsViews.disableScreen(this);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        showComponentScreen();
        //UtilsViews.enableSreen(this);
    }

    @Override
    public void showComponentScreen() {
        btnLogin.setVisibility(View.VISIBLE);
        btnSignIn.setVisibility(View.VISIBLE);
        textInputLayoutEmail.setVisibility(View.VISIBLE);
        textInputLayoutPass.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideComponentScreen() {
        btnLogin.setVisibility(View.GONE);
        btnSignIn.setVisibility(View.GONE);
        textInputLayoutEmail.setVisibility(View.GONE);
        textInputLayoutPass.setVisibility(View.GONE);
    }

    @Override
    public void goToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, Constantes.REQUEST_START_MAIN_FOR_RESULT);
    }

    @Override
    @OnClick(R.id.btnLoginFacebook)
    public void loginFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));
    }

    @Override
    public void fogotPassword() {

    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void saveCountriesCitiesSqlite(String countries, String cities) {
        new SaveCountriesCitiesSqlite(this){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                hideProgress();
                goToMainScreen();
            }
        }.execute(countries,cities);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
    }


}
