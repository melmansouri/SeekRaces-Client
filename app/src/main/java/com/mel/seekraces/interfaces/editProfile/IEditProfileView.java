package com.mel.seekraces.interfaces.editProfile;

import com.mel.seekraces.entities.Response;

/**
 * Created by moha on 10/01/17.
 */

public interface IEditProfileView {
    void selectPictureProfile();
    void showProgress();
    void hideProgress();
    void showMessage(String message);
    void showErrorUserName(String message);
    void hideErrorUserName();
    void showComponents();
    void hideComponents();
    void returnToMainScreen(String response);
    void fillImageViewFromCamera();
    void fillImageViewFromGallery();
    void openCamera();
    void openGalery();
    void editProfile();

    void finishActivity();

    boolean retunSuperOnOptionsItemSelected();
}
