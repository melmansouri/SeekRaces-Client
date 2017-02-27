package com.mel.seekraces.interfaces.fragmentOwnRacesPublished;

import com.mel.seekraces.entities.Event;

/**
 * Created by void on 22/01/2017.
 */

public interface IListFragmentOwnRacesPublishedPresenter {
    void getOwnRacesPublished();
    void onDestroy();

    void selectOptionDialogLongClickList(String[] options, int selected,Event event);

    void deleteOwnRacePublished(String user, int id);
}
