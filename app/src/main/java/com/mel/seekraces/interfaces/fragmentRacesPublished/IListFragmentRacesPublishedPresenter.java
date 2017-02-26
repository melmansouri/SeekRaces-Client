package com.mel.seekraces.interfaces.fragmentRacesPublished;

import com.mel.seekraces.entities.Event;
import com.mel.seekraces.entities.Favorite;
import com.mel.seekraces.entities.Filter;

/**
 * Created by void on 22/01/2017.
 */

public interface IListFragmentRacesPublishedPresenter {
    void getRacesPublished(Filter filter);
    void onOptionsItemSelected(int itemSelected);
    void onDestroy();

    void addEventToFavorite(Favorite item);

    void deleteEventFromFavorite(String user, int id);
}
