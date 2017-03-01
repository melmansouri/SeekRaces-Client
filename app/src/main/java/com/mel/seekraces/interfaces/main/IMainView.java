package com.mel.seekraces.interfaces.main;

import android.content.Intent;

import com.mel.seekraces.entities.Filter;

/**
 * Created by moha on 18/01/17.
 */

public interface IMainView {

    void backToPreviousFragment(int id);

    String getBackStackEntryNameAt(int id);

    int getBackStackEntryCount();

    void closeDrawerLayout();

    void fillNavHeaderTxtUserName(String username);

    void fillNavHeaderTxtEmail(String email);

    void fillNavHeaderImgProfile(String namePicture);

    void returnBack();

    void exitSession();

    void setResult();

    void showMessage(String message);

    boolean isDrawerOpen();

    void chargeFragmentRacesPublished(Filter filter);

    void chargeFragmentMyRacesPublished();

    void chargeFragmentRacesFavorites();

    void chargeFragmentRacesPrevious();

    void goToAddRaceScreen();

    void goToEditProfileScreen();

    Intent getIntentActivityResult();


    void setIntentActivityResultToNull();
}
