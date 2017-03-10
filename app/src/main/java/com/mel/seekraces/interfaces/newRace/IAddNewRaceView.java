package com.mel.seekraces.interfaces.newRace;

import com.mel.seekraces.adapters.AutoCompleteAdapter;
import com.mel.seekraces.entities.PlacePredictions;
import com.mel.seekraces.entities.Response;

/**
 * Created by void on 05/02/2017.
 */

public interface IAddNewRaceView {
    void selectPictureRace();
    void showProgress();
    void hideProgress();
    void showMessage(String message);
    void showErrorName(String message);
    void hideErrorName();
    void showErrorPlaces(String message);
    void hideErrorPlaces();
    void onTextChangedPlaces();
    void showComponents();
    void hideComponents();
    void showDialogDate();
    void showDialogTime();
    void returnToMainScreen(String message);
    boolean retunSuperOnOptionsItemSelected();
    void finishActivity();
    void fillImageViewFromCamera();
    void fillImageViewFromGallery();
    void openCamera();
    void openGalery();
    AutoCompleteAdapter getAdapterAutoComplete();
    void initAdapterAutoComplete(PlacePredictions placePredictions);
    void resetAdapterAutoComplete(PlacePredictions placePredictions);

    void addRace();
}
