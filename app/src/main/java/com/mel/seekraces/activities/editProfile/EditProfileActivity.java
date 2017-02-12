package com.mel.seekraces.activities.editProfile;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.mel.seekraces.R;
import com.mel.seekraces.interfaces.editProfile.IEditProfileView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity implements IEditProfileView {

    @BindView(R.id.edtUserName)
    TextInputEditText edtUserName;
    @BindView(R.id.text_input_layout_username)
    TextInputLayout textInputLayoutUsername;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.imgProfileUser)
    CircleImageView imgProfileUser;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
    }

    @Override
    public void selectPictureProfile() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showErrorUserName(String message) {

    }

    @Override
    public void hideErrorUserName() {

    }

    @Override
    public void showComponents() {

    }

    @Override
    public void hideComponents() {

    }

    @Override
    public void returnToMainScreen() {

    }

    @Override
    public void fillImageViewFromCamera() {

    }

    @Override
    public void fillImageViewFromGallery() {

    }

    @Override
    public void openCamera() {

    }

    @Override
    public void openGalery() {

    }

    @Override
    public void navigateUpFromSameTask() {

    }

    @Override
    public void editProfile() {

    }
}
