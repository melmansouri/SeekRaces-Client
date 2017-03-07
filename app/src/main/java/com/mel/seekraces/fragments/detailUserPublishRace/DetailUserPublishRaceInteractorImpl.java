package com.mel.seekraces.fragments.detailUserPublishRace;

import com.mel.seekraces.entities.Follow;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.detailUserPublishRace.IDetailUserPublishRaceInteractor;

/**
 * Created by void on 08/03/2017.
 */

public class DetailUserPublishRaceInteractorImpl implements IDetailUserPublishRaceInteractor{

    private IListennerCallBack callBack;

    public DetailUserPublishRaceInteractorImpl(IListennerCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void follow(Follow follow) {

    }

    @Override
    public void unFollow(String url) {

    }

    @Override
    public void getRacesPublishedByUser(String url) {

    }
}
