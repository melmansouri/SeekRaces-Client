package com.mel.seekraces.interfaces.fragmentRacesPublished;

import com.mel.seekraces.entities.Favorite;
import com.mel.seekraces.entities.Filter;

/**
 * Created by void on 22/01/2017.
 */

public interface IListFragmentRacesPublishedInteractor {
    void getRacesPublished(Filter filter);

    void addEventToFavorites(Favorite item);

    void deleteEventFromFavorite(String user, int id);

    void deleteEvent(String user, int id);
}
