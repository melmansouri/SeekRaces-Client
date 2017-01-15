package com.mel.seekraces.activities.signin;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mel.seekraces.R;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.interfaces.signin.ISignInPresenter;
import com.mel.seekraces.interfaces.signin.ISignInView;
import com.mel.seekraces.entities.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;


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
        UtilsViews.PermisosValidos(this);
        initialize();

    }

    private void initialize(){
        base64ImgProfile="";
        presenter = new SignInPresenterImpl(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_signin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.item_menu_signin_registrarse:
                signIn();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        /*UtilsViews.disableScreen(this);
        UtilsViews.closeKeyBoard(this);*/
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        UtilsViews.enableSreen(this);
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
    @OnTextChanged(value = R.id.edtEmail,callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void hideErrorEmail() {
        textInputLayoutEmail.setErrorEnabled(false);
    }

    @Override
    public void showErrorPwd(String message) {
        textInputLayoutPass.setError(message);
    }

    @Override
    @OnTextChanged(value = R.id.edtPassword,callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void hideErrorPwd() {
        textInputLayoutPass.setErrorEnabled(false);
    }

    @Override
    public void showErrorPwdRepeat(String message) {
        textInputLayoutRepeatPass.setError(message);
    }

    @Override
    public void hideErrorPwdRepeat() {
        textInputLayoutRepeatPass.setErrorEnabled(false);
    }

    @OnTextChanged(value = R.id.edtRepeatPassword,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChangedPwdRepeat(Editable editable){
        presenter.validatePasswordRepeat(edtPassword.getText().toString(),editable.toString());
    }

    @Override
    public void showErrorUserName(String message) {
        textInputLayoutUsername.setError(message);
    }

    @Override
    @OnTextChanged(value = R.id.edtUserName,callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void hideErrorUserName() {
        textInputLayoutUsername.setErrorEnabled(false);
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



    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
