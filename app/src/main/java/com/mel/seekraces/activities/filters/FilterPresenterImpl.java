package com.mel.seekraces.activities.filters;

import android.text.TextUtils;

import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.entities.PlacePredictions;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.filters.IFiltersInteractor;
import com.mel.seekraces.interfaces.filters.IFiltersPresenter;
import com.mel.seekraces.interfaces.filters.IFiltersView;

/**
 * Created by void on 31/01/2017.
 */

public class FilterPresenterImpl implements IFiltersPresenter, IListennerCallBack {

    private IFiltersView view;
    private IFiltersInteractor interactor;

    public FilterPresenterImpl(IFiltersView view) {
        this.view = view;
        this.interactor = new FiltersInteractorImpl(this);
    }

    @Override
    public boolean onOptionsItemSelected(int idSelected, Filter filter) {
        if (view != null) {
            if (idSelected == RMapped.ITEM_HOME_BACK.getValue()) {
                view.finishActivity();
                return true;
            } else if (idSelected == RMapped.ITEM_ACTIVITY_FILTERS_FILTRAR.getValue()) {
                view.backToListRacesPublished(filter);
                return true;
            } else if (idSelected == RMapped.ITEM_RESTART.getValue()) {
                Filter newFilter = new Filter();
                newFilter.setUser(filter.getUser());
                //view.backToListRacesPublished(newFilter);
                view.initComponentsWithFilterData(newFilter);
                return true;
            }
            return view.retunSuperOnOptionsItemSelected();
        }
        return false;
    }

    @Override
    public void onTextChangedPlaces(String text) {
        if (text.length() > 1) {
            interactor.getAutoCompletePlaces(null);
            interactor.getAutoCompletePlaces(Utils.getPlaceAutoCompleteUrl(text));
        }
    }

    @Override
    public void onDestroy() {
        view = null;
        interactor.getAutoCompletePlaces(null);
        interactor = null;
    }

    @Override
    public void showGroupsItemMenu(Filter filter) {
        if (view != null) {
            String name = filter.getName();
            String place = filter.getPlace();
            String dateInit = filter.getDate_interval_init();
            String dateEnd = filter.getDate_interval_end();
            int distMin = filter.getDistanceMin();
            int distMax = filter.getDistanceMax();
            if (TextUtils.isEmpty(name) && TextUtils.isEmpty(place) && TextUtils.isEmpty(dateInit) && TextUtils.isEmpty(dateEnd) && distMin == 0 && distMax == 0) {
                view.showGroupItemMenu(RMapped.ITEM_GROUP_FILTER.getValue(),true);
                view.showGroupItemMenu(RMapped.ITEM_GROUP_RESTART.getValue(),false);
            }else{
                view.showGroupItemMenu(RMapped.ITEM_GROUP_FILTER.getValue(),false);
                view.showGroupItemMenu(RMapped.ITEM_GROUP_RESTART.getValue(),true);
            }
        }
    }

    @Override
    public void onSuccess(Object object) {
        if (view != null) {
            if (((PlacePredictions) object).getStatus().equals("OK")) {
                if (view.getAdapterAutoComplete() == null) {
                    view.initAdapterAutoComplete(((PlacePredictions) object));
                } else {
                    view.resetAdapterAutoComplete(((PlacePredictions) object));
                }
            }
        }
    }

    @Override
    public void onError(Response response) {

    }
}
