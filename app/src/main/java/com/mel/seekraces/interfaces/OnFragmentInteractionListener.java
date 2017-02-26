package com.mel.seekraces.interfaces;

import com.mel.seekraces.entities.Event;
import com.mel.seekraces.entities.Favorite;

/**
 * Created by void on 18/01/2017.
 */

public interface OnFragmentInteractionListener {
        void onListFragmentInteraction(Event item);
        void addEventToFavorite(Favorite item);
        void changeTitleActionBar(int idTitle);
        void startActivityFilters();
        void showMessageFromFragments(String message);

        void deleteEventFromFavorite(String user, int id);
}
