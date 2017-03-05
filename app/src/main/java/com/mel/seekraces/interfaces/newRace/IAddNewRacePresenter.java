package com.mel.seekraces.interfaces.newRace;

import com.mel.seekraces.entities.Race;

/**
 * Created by void on 05/02/2017.
 */

public interface IAddNewRacePresenter {
    void addRace(boolean isOnline,Race race);
    void onTextChangedPlaces(String text);
    void activityResult(int requestCode,int resultCode);
    boolean onOptionsItemSelected(int idSelected);
    void selectOptionDialogPicture(String[] options,int selected);
    void onDestroy();

    void onRequestPermissionsResult(int requestCode, int[] grantResults);
}
