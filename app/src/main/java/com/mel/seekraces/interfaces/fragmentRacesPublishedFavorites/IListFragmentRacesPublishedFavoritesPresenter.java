package com.mel.seekraces.interfaces.fragmentRacesPublishedFavorites;

import com.mel.seekraces.adapters.RVRacesPublishedAdapter;
import com.mel.seekraces.entities.Race;

import java.util.List;

/**
 * Created by void on 22/01/2017.
 */

public interface IListFragmentRacesPublishedFavoritesPresenter {
    void getRacesPublishedFavorites(boolean isOnline);

    void onDestroy();

    void deleteEventFromFavorite(boolean online, String user, int id);

    void filter(RVRacesPublishedAdapter adapter, List<Race> racesWithoutFilter, String newText);
}
