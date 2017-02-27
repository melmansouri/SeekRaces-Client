package com.mel.seekraces.commons;

import android.app.Activity;
import android.content.pm.PackageManager;

import com.mel.seekraces.R;

/**
 * Created by moha on 18/01/17.
 */

public enum RMapped {
    //NAvigation Drawer
    ITEM_RACES_PUBLISHED(R.id.itemListEventsPublished),
    ITEM_RACES_MY_PUBLISHED(R.id.itemListMyEventsPublished),
    ITEM_RACES_FAVORITES(R.id.itemListEventsFavorites),
    ITEM_RACES_PREVIOUS(R.id.itemListPreviousEvents),
    ITEM_EXIT(R.id.exit),

    //Menus
    ITEM_FILTER(R.id.filter),
    ITEM_ACTIVITY_FILTERS_FILTRAR(R.id.item_menu_filters_filtrar),
    ITEM_HOME_BACK(android.R.id.home),
    ITEM_SIGNIN(R.id.item_menu_signin_registrarse),
    ITEM_ADDRACE(R.id.item_menu_add_new_race),
    ITEM_EDIT_PROFILE(R.id.item_menu_edit_profile),
    ITEM_EDIT_RACE(R.id.item_menu_edit_race),

    //Strings
    TITLE_CARRERAS_PUBLICADAS(R.string.title_carreras_publicadas),
    TITLE_MIS_CARRERAS_PUBLICADAS(R.string.title_mis_carreras_publicadas),
    TITLE_CARRERAS_FAVORITAS(R.string.title_carreras_favoritas),
    TITLE_CARRERAS_ANTERIORES(R.string.title_carreras_anteriores),

    //Result Code
    RESULT_OK(Activity.RESULT_OK),

    //Permissions_package_manager
    PERMISSION_GRANTED(PackageManager.PERMISSION_GRANTED);

    private int resource_id;

    RMapped(int id) {
        resource_id = id;
    }

    public int getValue() {
        return resource_id;
    }
}
