package com.mel.seekraces.activities.editProfile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.mel.seekraces.R;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.commons.UtilsViews;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.editProfile.IEditProfilePresenter;
import com.mel.seekraces.interfaces.editProfile.IEditProfileView;
import com.mel.seekraces.tasks.EncodeImageTask;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class EditProfileActivity extends AppCompatActivity implements IEditProfileView {

    @BindView(R.id.edtUserName)
    TextInputEditText edtUserName;
    @BindView(R.id.text_input_layout_username)
    TextInputLayout textInputLayoutUsername;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.imgProfileUser)
    ImageView imgProfileUser;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    private SharedPreferencesSingleton sharedPreferencesSingleton;
    private MenuItem item;
    private IEditProfilePresenter presenter;
    private Intent intentOnActivityResult;
    private Bitmap imageBitmap;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imgProfileUser.setVisibility(View.INVISIBLE);
        edtUserName.setVisibility(View.INVISIBLE);
        sharedPreferencesSingleton=SharedPreferencesSingleton.getInstance(this);
        presenter=new EditProfilePresenterImpl(this,sharedPreferencesSingleton);

        String username=sharedPreferencesSingleton.getStringSP(Constantes.KEY_USERNAME);
        String usernamePicture=sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER_NAME_PICTURE);
        email=sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER);

        Glide.with(getApplicationContext()).load(INetworkConnectionApi.BASE_URL_PICTURES + usernamePicture).error(R.drawable.ic_default_user).into(imgProfileUser);
        edtUserName.setText(username);

        imgProfileUser.setVisibility(View.VISIBLE);
        edtUserName.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.item = item;
        return presenter.onOptionsItemSelected(item.getItemId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        intentOnActivityResult = data;
        presenter.activityResult(requestCode,resultCode);
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
    public void showErrorUserName(String message) {
        textInputLayoutUsername.setError(message);
    }

    @Override
    @OnTextChanged(value = R.id.edtUserName,callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void hideErrorUserName() {
        textInputLayoutUsername.setErrorEnabled(false);
    }

    @Override
    public void showComponents() {
        imgProfileUser.setVisibility(View.VISIBLE);
        textInputLayoutUsername.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideComponents() {
        imgProfileUser.setVisibility(View.GONE);
        textInputLayoutUsername.setVisibility(View.GONE);
    }

    @Override
    public void returnToMainScreen(String response) {
        Intent intent=new Intent();
        intent.putExtra("editProfile",response);
        setResult(RESULT_OK,intent);
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
        imageBitmap= Utils.getBitmapFromUriImage(this,uriImage);
        imgProfileUser.setImageBitmap(imageBitmap);
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
    public void editProfile() {
        showProgress();
        new EncodeImageTask(this,imageBitmap){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                User user=new User();
                user.setEmail(email);
                user.setUsername(edtUserName.getText().toString());
                user.setPhoto_url(String.valueOf(new Date().getTime()));
                user.setPhotoBase64(s);
                presenter.editProfile(Utils.isOnline(EditProfileActivity.this),user);
            }
        }.execute();

    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public boolean retunSuperOnOptionsItemSelected() {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
