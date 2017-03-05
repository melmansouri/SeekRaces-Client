package com.mel.seekraces.interfaces.fragmentOwnRacesPublished;

import com.mel.seekraces.adapters.RVRacesPublishedAdapter;
import com.mel.seekraces.entities.Race;

import java.util.List;

/**
 * Created by void on 22/01/2017.
 */

public interface IListFragmentOwnRacesPublishedPresenter {

    void getOwnRacesPublished(boolean isOnline);

    void onDestroy();

    void selectOptionDialogLongClickList(String[] options, int selected,Race race);

    void deleteOwnRacePublished(boolean online, String user, int id);

    void filter(RVRacesPublishedAdapter adapter, List<Race> racesWithoutFilter, String newText);
}
