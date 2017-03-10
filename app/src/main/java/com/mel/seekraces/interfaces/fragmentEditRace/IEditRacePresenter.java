package com.mel.seekraces.interfaces.fragmentEditRace;

import com.mel.seekraces.entities.Race;

/**
 * Created by void on 05/02/2017.
 */

public interface IEditRacePresenter {
    void editRace(boolean isOnline, Race race);
    void onTextChangedPlaces(String text);
    void activityResult(int requestCode, int resultCode);
    void onOptionsItemSelected(int idSelected);
    void selectOptionDialogPicture(String[] options, int selected);
    void onDestroy();

    void onRequestPermissionsResult(int requestCode, int[] grantResults);
}
