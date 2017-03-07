package com.mel.seekraces.interfaces.filters;

import com.mel.seekraces.adapters.AutoCompleteAdapter;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.entities.PlacePredictions;

/**
 * Created by void on 29/01/2017.
 */

public interface IFiltersView {

    void initComponentsWithFilterData(Filter filter);

    void showGroupItemMenu(int idGroup, boolean show);

    void backToListRacesPublished(Filter filter);

    void finishActivity();
    void showDialogDateFrom();
    void showDialogDateUntil();
    boolean retunSuperOnOptionsItemSelected();

    void onTextChangedPlaces();

    AutoCompleteAdapter getAdapterAutoComplete();

    void initAdapterAutoComplete(PlacePredictions placePredictions);

    void resetAdapterAutoComplete(PlacePredictions placePredictions);
}
