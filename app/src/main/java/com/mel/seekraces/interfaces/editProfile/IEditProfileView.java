package com.mel.seekraces.interfaces.editProfile;

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
    void returnToMainScreen();
    void fillImageViewFromCamera();
    void fillImageViewFromGallery();
    void openCamera();
    void openGalery();
    void navigateUpFromSameTask();
    void editProfile();
}
