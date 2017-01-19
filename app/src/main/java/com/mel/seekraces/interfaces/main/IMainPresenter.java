package com.mel.seekraces.interfaces.main;

/**
 * Created by moha on 18/01/17.
 */

public interface IMainPresenter {
    void fillDataHeaderView();

    void onNavigationItemSelected(int itemSelectd);

    void getDataRacesPublished();

    void fillDataMyRacesPublished();

    void fillDataRacesFavorites();

    void fillDataRacesPrevious();

    void onBackPressed();
}
