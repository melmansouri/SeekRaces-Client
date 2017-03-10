package com.mel.seekraces.activities.login;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.crash.FirebaseCrash;
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
import com.mel.seekraces.tasks.EncodeImageTask;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends AppCompatActivity implements ILoginView, GoogleApiClient.OnConnectionFailedListener {

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
    @BindView(R.id.activity_login)
    RelativeLayout activityLogin;
    @BindView(R.id.btnSignInGoogle)
    Button btnSignInGoogle;
    @BindView(R.id.txtAppName)
    TextView txtAppName;
    @BindView(R.id.btnForgotPwd)
    Button btnForgotPwd;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;

    private ILoginPresenter loginPresenter;

    private SharedPreferencesSingleton sharedPreferencesSingleton;

    private LoginManager loginManagerFacebook;
    private CallbackManager callbackManager;

    private GoogleSignInOptions gso;

    private GoogleApiClient mGoogleApiClient;
    private Intent intentActivityResult;
    private GoogleSignInResult resultSignInGoogle;

    @Override
    protected void onResume() {
        super.onResume();
        showComponentScreen();
        edtEmail.getText().clear();
        edtPassword.getText().clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sharedPreferencesSingleton = SharedPreferencesSingleton.getInstance(this);
        loginPresenter = new LoginPresenterImpl(this, sharedPreferencesSingleton);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        try{
            Typeface custom_font = Typeface.createFromAsset(getAssets(), "android_insomnia_regular.ttf");
            txtAppName.setTypeface(custom_font);
        }catch(Exception e){
            e.printStackTrace();
            FirebaseCrash.report(e);
        }

        loginPresenter.checkSession();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        intentActivityResult = data;
        loginPresenter.activityResult(requestCode, resultCode);
    }

    @Override
    @OnClick(R.id.btnSignIn)
    public void goToSignIn() {
        loginPresenter.startActivitySignIn();
    }

    @Override
    public void startActivitySignIn() {
        Intent i = new Intent(this, SignInActivity.class);
        startActivityForResult(i, Constantes.REQUEST_START_SIGNIN_FOR_RESULT);
    }

    @Override
    @OnClick(R.id.btnLogin)
    public void login() {
        try {
            UtilsViews.closeKeyBoard(this);
            User user = new User();
            user.setEmail(edtEmail.getText().toString());
            user.setPwd(edtPassword.getText().toString());
            user.setToken_push(sharedPreferencesSingleton.getStringSP(Constantes.KEY_TOKEN_PUSH));
            //sharedPreferencesSingleton.removeValueSP(Constantes.KEY_TOKEN_PUSH);
            loginPresenter.login(Utils.isOnline(this), true, user);
        } catch (Exception e) {
            FirebaseCrash.report(e);
            e.printStackTrace();
        }
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
        //showComponentScreen();
        //UtilsViews.enableSreen(this);
    }

    @Override
    public void showComponentScreen() {
        btnLogin.setVisibility(View.VISIBLE);
        btnSignIn.setVisibility(View.VISIBLE);
        textInputLayoutEmail.setVisibility(View.VISIBLE);
        textInputLayoutPass.setVisibility(View.VISIBLE);
        btnForgotPwd.setVisibility(View.VISIBLE);
        btnSignInGoogle.setVisibility(View.VISIBLE);
        txtAppName.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideComponentScreen() {
        btnLogin.setVisibility(View.GONE);
        btnSignIn.setVisibility(View.GONE);
        textInputLayoutEmail.setVisibility(View.GONE);
        textInputLayoutPass.setVisibility(View.GONE);
        btnForgotPwd.setVisibility(View.GONE);
        btnSignInGoogle.setVisibility(View.GONE);
        txtAppName.setVisibility(View.GONE);
    }

    @Override
    public void goToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, Constantes.REQUEST_START_MAIN_FOR_RESULT);
    }

    @Override
    //@OnClick(R.id.btnLoginFacebook)
    public void loginFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    @Override
    @OnClick(R.id.btnSignInGoogle)
    public void signInGoogle() {
        if (Utils.isOnline(this)){
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, Constantes.REQUEST_START_SIGNIN_GOOGLE_FOR_RESULT);
        }else{
            showMessage("Comprueba tu conexión");
        }
    }

    @Override
    public void checkSignInGoogle() {
        resultSignInGoogle = Auth.GoogleSignInApi.getSignInResultFromIntent(intentActivityResult);
        loginPresenter.checkSignInGoogle(resultSignInGoogle.isSuccess());
    }

    @Override
    public void loginGoogle() {
        showProgress();
        try {
            final GoogleSignInAccount acct = resultSignInGoogle.getSignInAccount();
            final User user = new User();
            new EncodeImageTask(this, acct.getPhotoUrl()) {
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    user.setEmail(acct.getEmail());
                    user.setUsername(acct.getDisplayName());
                    user.setPhotoBase64(s);
                    user.setToken_push(sharedPreferencesSingleton.getStringSP(Constantes.KEY_TOKEN_PUSH));
                    user.setIdTokenGoogle(acct.getId());
                    loginPresenter.login(Utils.isOnline(LoginActivity.this), false, user);
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
        hideProgress();
        showComponentScreen();
    }

    @Override
    @OnClick(R.id.btnForgotPwd)
    public void fogotPassword() {
        UtilsViews.closeKeyBoard(this);
        loginPresenter.forgotPwd(Utils.isOnline(this), edtEmail.getText().toString());
    }

    @Override
    public void finishActivity() {
        finish();
    }


    @Override
    public void revokeAccessSignInGoogle() {
        try {
            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Log.e("LoginActivity", "desconectado de google");
                        }
                    });
        } catch (Exception e) {
            FirebaseCrash.report(e);
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showMessage(connectionResult.getErrorMessage());
    }
}
