package com.mel.seekraces.interfaces.fragmentRacesPublishedFinished;

import com.mel.seekraces.adapters.RVRacesPublishedAdapter;
import com.mel.seekraces.entities.Race;

import java.util.List;

/**
 * Created by void on 22/01/2017.
 */

public interface IListFragmentRacesFinishedPresenter {

    void getOwnRacesPublished(boolean isOnline);

    void onDestroy();

    void filter(RVRacesPublishedAdapter adapter, List<Race> racesWithoutFilter, String newText);

    void deleteOwnRacePublished(boolean online, Object object);
}
