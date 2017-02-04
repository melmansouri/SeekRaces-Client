package com.mel.seekraces.activities.filters;

import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.interfaces.filters.IFiltersPresenter;
import com.mel.seekraces.interfaces.filters.IFiltersView;

/**
 * Created by void on 31/01/2017.
 */

public class FilterPresenterImpl implements IFiltersPresenter {

    private IFiltersView view;

    public FilterPresenterImpl(IFiltersView view){
        this.view=view;
    }

    @Override
    public boolean onOptionsItemSelected(int idSelected) {
        if(idSelected== RMapped.ITEM_HOME_BACK.getValue()) {
            view.finishActivity();
            return true;
        }else if(idSelected==RMapped.ITEM_ACTIVITY_FILTERS_FILTRAR.getValue()){
            view.backToListRacesPublished();
            return true;
        }
        return view.retunSuperOnOptionsItemSelected();
    }
}
