package com.mel.seekraces.interfaces.main;

import android.net.Uri;

import com.mel.seekraces.entities.Filter;

/**
 * Created by moha on 18/01/17.
 */

public interface IMainView {
    void closeDrawerLayout();

    void fillNavHeaderTxtUserName(String username);

    void fillNavHeaderImgProfile(Uri uri);

    void returnBack();

    boolean isDrawerOpen();

    void showProgressDialog();

    void hideProgressDialog();

    void chargeFragmentRacesPublished(Filter filter);

    void chargeFragmentMyRacesPublished();

    void chargeFragmentRacesFavorites();

    void chargeFragmentRacesPrevious();

}
