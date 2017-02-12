package com.mel.seekraces.activities.filters;

import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.entities.PlacePredictions;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.filters.IFiltersInteractor;
import com.mel.seekraces.interfaces.filters.IFiltersPresenter;
import com.mel.seekraces.interfaces.filters.IFiltersView;

/**
 * Created by void on 31/01/2017.
 */

public class FilterPresenterImpl implements IFiltersPresenter,IListennerCallBack {

    private IFiltersView view;
    private IFiltersInteractor interactor;

    public FilterPresenterImpl(IFiltersView view){
        this.view=view;
        this.interactor=new FiltersInteractorImpl(this);
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

    @Override
    public void onTextChangedPlaces(String text) {
        if(text.length()>3){
            interactor.getAutoCompletePlaces(null);
            interactor.getAutoCompletePlaces(Utils.getPlaceAutoCompleteUrl(text));
        }
    }

    @Override
    public void onSuccess(Object object) {
        if (((PlacePredictions)object).getStatus().equals("OK")){
            if (view.getAdapterAutoComplete()==null){
                view.initAdapterAutoComplete(((PlacePredictions)object));
            }else{
                view.resetAdapterAutoComplete(((PlacePredictions)object));
            }
        }
    }

    @Override
    public void onError(Response response) {

    }
}
