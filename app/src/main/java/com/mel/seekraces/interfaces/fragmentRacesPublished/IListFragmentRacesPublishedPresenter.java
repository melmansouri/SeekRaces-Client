package com.mel.seekraces.interfaces.fragmentRacesPublished;

import com.mel.seekraces.adapters.RVRacesPublishedAdapter;
import com.mel.seekraces.entities.Favorite;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.entities.Race;

import java.util.List;

/**
 * Created by void on 22/01/2017.
 */

public interface IListFragmentRacesPublishedPresenter {

    void getRacesPublished(boolean isOnline, Filter filter);

    void onOptionsItemSelected(int itemSelected, Filter filter);
    void onDestroy();

    void addEventToFavorite(boolean online, Favorite item);

    void deleteEventFromFavorite(boolean online, String user, int id);

    void filter(RVRacesPublishedAdapter adapter, List<Race> racesWithoutFilter, String newText);

    void deleteOwnRacePublished(boolean online, String user, int id);

    void deleteOwnRacePublished(boolean online, Object object);

    void selectOptionDialogLongClickList(String[] options, int i, Race object);
}
