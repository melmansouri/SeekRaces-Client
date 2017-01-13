package com.mel.seekraces.activities.signin;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mel.seekraces.R;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.interfaces.signin.ISignInPresenter;
import com.mel.seekraces.interfaces.signin.ISignInView;
import com.mel.seekraces.pojos.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignInActivity extends AppCompatActivity implements ISignInView {

    @BindView(R.id.imgProfileUser)
    ImageView imgProfileUser;
    @BindView(R.id.edtUserName)
    TextInputEditText edtUserName;
    @BindView(R.id.edtEmail)
    TextInputEditText edtEmail;
    @BindView(R.id.edtPassword)
    TextInputEditText edtPassword;
    @BindView(R.id.edtRepeatPassword)
    TextInputEditText edtRepeatPassword;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.text_input_layout_username)
    TextInputLayout textInputLayoutUsername;
    @BindView(R.id.text_input_layout_email)
    TextInputLayout textInputLayoutEmail;
    @BindView(R.id.text_input_layout_pass)
    TextInputLayout textInputLayoutPass;
    @BindView(R.id.text_input_layout_repeat_pass)
    TextInputLayout textInputLayoutRepeatPass;
    private Intent intentOnActivityResult;
    private ISignInPresenter presenter;
    private String base64ImgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        presenter = new SignInPresenterImpl(this);
        initialize();

    }

    private void initialize(){
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputLayoutEmail.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputLayoutPass.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputLayoutUsername.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        intentOnActivityResult = data;
        presenter.activityResult(resultCode, RESULT_OK);
    }

    @Override
    @OnClick(R.id.imgProfileUser)
    public void selectPictureProfile() {
        AlertDialog.Builder builder = UtilsViews.createAlertDialog(this, getString(R.string.elige_opcion));
        builder.setItems(R.array.option_dialog_picture, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.selectOptionDialogPicture(getResources().getStringArray(R.array.option_dialog_picture), i);
            }
        });

        builder.show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        UtilsViews.showSnackBar(coordinatorLayout, message);
    }

    @Override
    public void showErrorEmail(String message) {
        textInputLayoutEmail.setError(message);
    }

    @Override
    public void showErrorPwd(String message) {
        textInputLayoutPass.setError(message);
    }

    @Override
    public void showErrorPwdRpeat(String message) {
        textInputLayoutRepeatPass.setError(message);
    }

    @Override
    public void showErrorUserName(String message) {
        textInputLayoutUsername.setError(message);
    }

    @Override
    public void returnToLoginScreen() {
        finish();
    }

    @Override
    public void fillImageView() {
        Uri uriImage = intentOnActivityResult.getData();
        base64ImgProfile = Utils.convertUriImageToBase64(this, uriImage);
        imgProfileUser.setImageURI(uriImage);
    }

    @Override
    public void openCamera() {
        UtilsViews.openCamera(this);
    }

    @Override
    public void openGalery() {
        UtilsViews.openGallery(this);
    }

    @Override
    @OnClick(R.id.btnSignIn)
    public void signIn() {
        showProgress();
        User user = new User();
        user.setEmail(edtEmail.getText().toString());
        user.setPwd(edtPassword.getText().toString());
        user.setPwd_repeat(edtRepeatPassword.getText().toString());
        user.setUsername(edtUserName.getText().toString());
        user.setPhotoBase64(base64ImgProfile);

        presenter.signIn(user);
    }
}
