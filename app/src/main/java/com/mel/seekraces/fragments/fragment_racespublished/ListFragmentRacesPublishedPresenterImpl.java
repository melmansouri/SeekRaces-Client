package com.mel.seekraces.fragments.fragment_racespublished;

import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.fragment_racespublished.IListFragmentRacesPublishedInteractor;
import com.mel.seekraces.interfaces.fragment_racespublished.IListFragmentRacesPublishedPresenter;
import com.mel.seekraces.interfaces.fragment_racespublished.IListFragmentRacesPublishedView;

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

    }

    @Override
    public void onSuccess(Response response) {

    }

    @Override
    public void onError(Response response) {

    }
}
