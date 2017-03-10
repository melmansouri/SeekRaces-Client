package com.mel.seekraces.interfaces;

import android.view.View;

import com.mel.seekraces.entities.Favorite;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.entities.Race;
import com.mel.seekraces.entities.User;

/**
 * Created by void on 26/02/2017.
 */

public interface IGenericInterface {



    /**
     * Created by void on 26/02/2017.
     */

    interface OnFragmentInteractionListener {
        void showHamburgerIconDrawer(boolean show);

        void setNavigationIcon();


        void setOnClickNavigationToolbar(View.OnClickListener onToolbarNavigationClickListener);

        void onListFragmentInteraction(Race item);

        void setCheckedItemNavView(int index);

        void changeTitleActionBar(int idTitle);

        void startActivityFilters(Filter filter);

        void showMessageFromFragments(String message);

        void hideFloatingButton();

        void setDrawerEnabled(boolean enabled);

        void editEvent(Race race);

        void showFloatingButton();

        void startScreenReviews(int idEvent);

        void showDetailUserHowPublishRace(User user);
    }

    /**
     * Created by void on 26/02/2017.
     */

    interface OnListInteractionListener {
        void addEventToFavorite(Favorite item);
        void deleteEventFromFavorite(String user, int id);
        void onItemLongClickListener(Object object);
        void deleteOwnEvent(Object object);
        void editEvent(Object object);
    }
}
