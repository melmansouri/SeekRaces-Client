package com.mel.seekraces.fragments.racespublished;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mel.seekraces.deserializers.EventDeserializer;
import com.mel.seekraces.entities.Event;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.fragment_racespublished.IListFragmentRacesPublishedInteractor;
import com.mel.seekraces.interfaces.fragment_racespublished.IListFragmentRacesPublishedPresenter;
import com.mel.seekraces.interfaces.fragment_racespublished.IListFragmentRacesPublishedView;

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
        interactor.getRacesPublished(filter);
    }

    @Override
    public void onSuccess(Response response) {
        Log.e("Fragment",response.toString());
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
        Log.e("Fragment",response.toString());
    }
}
