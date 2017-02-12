package com.mel.seekraces.interfaces.filters;

import com.mel.seekraces.R;
import com.mel.seekraces.adapters.AutoCompleteAdapter;
import com.mel.seekraces.entities.PlacePredictions;

import butterknife.OnTextChanged;

/**
 * Created by void on 29/01/2017.
 */

public interface IFiltersView {
    void backToListRacesPublished();
    void finishActivity();
    void showDialogDateFrom();
    void showDialogDateUntil();
    boolean retunSuperOnOptionsItemSelected();

    void onTextChangedPlaces();

    AutoCompleteAdapter getAdapterAutoComplete();

    void initAdapterAutoComplete(PlacePredictions placePredictions);

    void resetAdapterAutoComplete(PlacePredictions placePredictions);
}
