package com.mel.seekraces.fragments.detailUserPublishRace;

import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.entities.Follow;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.detailUserPublishRace.IDetailUserPublishRaceInteractor;
import com.mel.seekraces.interfaces.detailUserPublishRace.IDetailUserPublishRacePresenter;
import com.mel.seekraces.interfaces.detailUserPublishRace.IDetailUserPublishRaceView;

/**
 * Created by void on 08/03/2017.
 */

public class DetailUserPublishRacePresenterImpl implements IDetailUserPublishRacePresenter,IListennerCallBack{

    private SharedPreferencesSingleton sharedPreferencesSingleton;
    private IDetailUserPublishRaceView view;
    private IDetailUserPublishRaceInteractor interactor;

    public DetailUserPublishRacePresenterImpl(SharedPreferencesSingleton sharedPreferencesSingleton, IDetailUserPublishRaceView view) {
        this.sharedPreferencesSingleton = sharedPreferencesSingleton;
        this.view = view;
        this.interactor=new DetailUserPublishRaceInteractorImpl(this);
    }

    @Override
    public void follow(boolean isOnline, Follow follow) {

    }

    @Override
    public void unFollow(boolean isOnline, String userFollowed) {

    }

    @Override
    public void getRacesPublishedByUser(boolean isOnline, String userFollowed) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onError(Response response) {

    }
}
