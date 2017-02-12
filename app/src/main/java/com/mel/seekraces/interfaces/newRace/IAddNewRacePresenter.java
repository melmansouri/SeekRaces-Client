package com.mel.seekraces.interfaces.newRace;

import com.mel.seekraces.entities.Event;

/**
 * Created by void on 05/02/2017.
 */

public interface IAddNewRacePresenter {
    void addRace(boolean isOnline,Event event);
    void onTextChangedPlaces(String text);
    void activityResult(int requestCode,int resultCode);
    boolean onOptionsItemSelected(int idSelected);
    void selectOptionDialogPicture(String[] options,int selected);

}
