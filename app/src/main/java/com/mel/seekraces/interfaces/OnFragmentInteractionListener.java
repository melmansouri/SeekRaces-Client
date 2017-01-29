package com.mel.seekraces.interfaces;

import com.mel.seekraces.entities.Event;

/**
 * Created by void on 18/01/2017.
 */

public interface OnFragmentInteractionListener {
        void onListFragmentInteraction(Event item);
        void changeTitleActionBar(int idTitle);
        void startActivityFilters();
}
