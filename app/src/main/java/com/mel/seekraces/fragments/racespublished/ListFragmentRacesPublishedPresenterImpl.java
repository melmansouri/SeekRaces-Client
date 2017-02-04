package com.mel.seekraces.fragments.racespublished;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.deserializers.EventDeserializer;
import com.mel.seekraces.entities.Event;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.fragmentRacesPublished.IListFragmentRacesPublishedInteractor;
import com.mel.seekraces.interfaces.fragmentRacesPublished.IListFragmentRacesPublishedPresenter;
import com.mel.seekraces.interfaces.fragmentRacesPublished.IListFragmentRacesPublishedView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by void on 22/01/2017.
 */

public class ListFragmentRacesPublishedPresenterImpl implements IListFragmentRacesPublishedPresenter, IListennerCallBack{
    private IListFragmentRacesPublishedView view;
    private IListFragmentRacesPublishedInteractor interactor;

    public ListFragmentRacesPublishedPresenterImpl(IListFragmentRacesPublishedView view){
        this.view=view;
        this.interactor=new ListFragmentRacesPublishedInteractorImpl(this);
    }


    @Override
    public void getRacesPublished(Filter filter) {
        view.showProgressBar();
        //view.showList();
        interactor.getRacesPublished(filter);
    }

    @Override
    public void onOptionsItemSelected(int itemSelected) {
        if (itemSelected== RMapped.ITEM_FILTER.getValue()){
            view.startScreenFilter();
        }
    }

    @Override
    public void onSuccess(Response response) {
        view.hideProgressBar();
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setLenient();
        gsonBuilder.registerTypeAdapter(Event.class,new EventDeserializer());
        Gson gson=gsonBuilder.create();
        Type founderListType = new TypeToken<ArrayList<Event>>(){}.getType();
        List<Event> carreras=gson.fromJson(response.getContent(),founderListType);
        view.fillAdapterList(carreras);
        view.showList();
    }

    @Override
    public void onError(Response response) {
        view.hideProgressBar();
        //view.hideList();
        view.showMessage(response.getMessage());
        Log.e("Fragment",response.toString());
    }
}
