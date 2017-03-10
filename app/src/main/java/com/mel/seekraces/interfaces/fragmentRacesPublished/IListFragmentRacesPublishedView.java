package com.mel.seekraces.interfaces.fragmentRacesPublished;

import com.mel.seekraces.adapters.RVRacesPublishedAdapter;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.entities.Race;

import java.util.List;

/**
 * Created by void on 22/01/2017.
 */

public interface IListFragmentRacesPublishedView {
    void fillAdapterList(List<Race> races);

    RVRacesPublishedAdapter getAdapter();

    void showProgressBar();
    void hideProgressBar();
    void showList();
    void hideList();

    void showMessage(String message);

    void startScreenFilter(Filter filter);
}
