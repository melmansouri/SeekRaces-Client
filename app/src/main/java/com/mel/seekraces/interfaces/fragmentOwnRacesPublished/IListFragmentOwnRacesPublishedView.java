package com.mel.seekraces.interfaces.fragmentOwnRacesPublished;

import com.mel.seekraces.entities.Event;

import java.util.List;

/**
 * Created by void on 22/01/2017.
 */

public interface IListFragmentOwnRacesPublishedView {
    void fillAdapterList(List<Event> races);
    void showProgressBar();
    void hideProgressBar();
    void showList();
    void hideList();
    void showMessage(String message);
}