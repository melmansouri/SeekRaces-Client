package com.mel.seekraces.interfaces.main;

import com.mel.seekraces.entities.Race;

/**
 * Created by moha on 18/01/17.
 */

public interface IMainPresenter {
    void fillDataHeaderView();

    void onNavigationItemSelected(int itemSelectd);

    void activityResult(int requestCode,int resultCode);

    void onBackPressed();

    void onDestroy();

    void startDetailRaceFromNotification(Race race);
}
