package com.mel.seekraces.activities.signin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.mel.seekraces.R;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.signin.ISignInPresenter;
import com.mel.seekraces.interfaces.signin.ISignInView;
import com.mel.seekraces.tasks.EncodeImageTask;

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
    @BindView(R.id.lnDataUser)
    ScrollView lnDataUser;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private Intent intentOnActivityResult;
    private ISignInPresenter presenter;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        initialize();

    }

    private void initialize() {
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
        presenter.onOptionsItemSelected(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        intentOnActivityResult = data;
        presenter.activityResult(requestCode, resultCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.onRequestPermissionsResult(requestCode,grantResults);
    }

    @Override
    @OnClick(R.id.fab)
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
        UtilsViews.closeKeyBoard(this);
        UtilsViews.disableScreen(this);
        progressBar.setVisibility(View.VISIBLE);
        hideComponents();
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        showComponents();
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
    @OnTextChanged(value = R.id.edtEmail, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void hideErrorEmail() {
        textInputLayoutEmail.setErrorEnabled(false);
    }

    @Override
    public void showErrorPwd(String message) {
        textInputLayoutPass.setError(message);
    }

    @Override
    @OnTextChanged(value = R.id.edtPassword, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
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
    public void afterTextChangedPwdRepeat(Editable editable) {
        presenter.validatePasswordRepeat(edtPassword.getText().toString(), editable.toString());
    }

    @Override
    public void showErrorUserName(String message) {
        textInputLayoutUsername.setError(message);
    }

    @Override
    @OnTextChanged(value = R.id.edtUserName, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void hideErrorUserName() {
        textInputLayoutUsername.setErrorEnabled(false);
    }

    @Override
    public void showComponents() {
        /*textInputLayoutRepeatPass.setVisibility(View.VISIBLE);
        textInputLayoutEmail.setVisibility(View.VISIBLE);
        textInputLayoutUsername.setVisibility(View.VISIBLE);
        textInputLayoutPass.setVisibility(View.VISIBLE);
        imgProfileUser.setVisibility(View.VISIBLE);*/
        lnDataUser.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideComponents() {
        /*textInputLayoutRepeatPass.setVisibility(View.GONE);
        textInputLayoutEmail.setVisibility(View.GONE);
        textInputLayoutUsername.setVisibility(View.GONE);
        textInputLayoutPass.setVisibility(View.GONE);
        imgProfileUser.setVisibility(View.GONE);*/
        lnDataUser.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);
    }

    @Override
    public void returnToLoginScreen() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void fillImageViewFromCamera() {
        Bundle extras = intentOnActivityResult.getExtras();
        //Bitmap bitmaptmp=(Bitmap) extras.get("data");
        //imageBitmap = Bitmap.createScaledBitmap(bitmaptmp,(int)(bitmaptmp.getWidth()*0.8), (int)(bitmaptmp.getHeight()*0.8), true);
        imageBitmap = (Bitmap) extras.get("data");
        imgProfileUser.setImageBitmap(imageBitmap);
    }

    @Override
    public void fillImageViewFromGallery() {
        Uri uriImage = intentOnActivityResult.getData();
        //Bitmap bitmaptmp=Utils.getBitmapFromUriImage(this,uriImage);
        //imageBitmap = Bitmap.createScaledBitmap(bitmaptmp,(int)(bitmaptmp.getWidth()*0.8), (int)(bitmaptmp.getHeight()*0.8), true);
        //Glide.with(this).load(uriImage).into(imgProfileUser);
        //imageBitmap = Utils.getBitmapFromUriImage(this, uriImage);
        //imgProfileUser.setImageBitmap(imageBitmap);
        Bitmap bitmaptmp = Utils.getBitmapFromUriImage(this, uriImage);
        //imageBitmap = Bitmap.createScaledBitmap(bitmaptmp, (int) (bitmaptmp.getWidth() * 0.5), (int) (bitmaptmp.getHeight() * 0.5), true);
        if (bitmaptmp!=null){
            imageBitmap= bitmaptmp;
            imgProfileUser.setImageBitmap(imageBitmap);
        }
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
    public void navigateUpFromSameTask() {
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public void signIn() {

        showProgress();

        final User user = new User();
        new EncodeImageTask(this, imageBitmap) {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                user.setEmail(edtEmail.getText().toString());
                user.setPwd(edtPassword.getText().toString());
                user.setPwd_repeat(edtRepeatPassword.getText().toString());
                user.setUsername(edtUserName.getText().toString());
                user.setPhotoBase64(s);
                presenter.signIn(Utils.isOnline(SignInActivity.this), user);
            }
        }.execute();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
