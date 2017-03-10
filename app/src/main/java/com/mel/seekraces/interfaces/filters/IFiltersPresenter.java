package com.mel.seekraces.interfaces.filters;

import com.mel.seekraces.entities.Filter;

/**
 * Created by void on 31/01/2017.
 */

public interface IFiltersPresenter {
    boolean onOptionsItemSelected(int idSelected, Filter filter);

    void onTextChangedPlaces(String input);

    void onDestroy();

    void showGroupsItemMenu(Filter filter);
}
