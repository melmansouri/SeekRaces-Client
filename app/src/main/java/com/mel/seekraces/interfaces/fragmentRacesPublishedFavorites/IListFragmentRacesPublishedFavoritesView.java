package com.mel.seekraces.interfaces.fragmentRacesPublishedFavorites;

import com.mel.seekraces.entities.Race;

import java.util.List;

/**
 * Created by void on 22/01/2017.
 */

public interface IListFragmentRacesPublishedFavoritesView {
    void fillAdapterList(List<Race> races);
    void showProgressBar();
    void hideProgressBar();
    void showList();
    void hideList();
    void showMessage(String message);
}
