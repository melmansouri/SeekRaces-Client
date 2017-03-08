package com.mel.seekraces.fragments.detailUserPublishRace;

import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.entities.Follow;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
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
        if (view!=null){
            view.showProgress();
            view.hideComponents();
            if (!isOnline){
                view.hideProgress();
                view.showComponents();
                view.showMessage("Comprueba tu conexión");
                return;
            }
            interactor.follow(follow);
        }
    }

    @Override
    public void unFollow(boolean isOnline, String userFollowed) {
        if (view!=null){
            view.showProgress();
            view.hideComponents();
            if (!isOnline){
                view.hideProgress();
                view.showComponents();
                view.showMessage("Comprueba tu conexión");
                return;
            }
            String url= INetworkConnectionApi.BASE_URL+"follow/"+sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER)+"/"+userFollowed;
            interactor.unFollow(url);
        }
    }

    @Override
    public void setSentNotificacion(boolean isOnline, Follow follow) {
        if (view!=null){
            view.showProgress();
            view.hideComponents();
            if (!isOnline){
                view.hideProgress();
                view.showComponents();
                view.showMessage("Comprueba tu conexión");
                return;
            }
            interactor.setSentNotificacion(follow);
        }
    }

    @Override
    public void getRacesPublishedByUser(boolean isOnline, String userFollowed) {

    }

    @Override
    public void onDestroy() {
        interactor.follow(null);
        interactor.unFollow(null);
        interactor.setSentNotificacion(null);
        view=null;
        interactor=null;
        sharedPreferencesSingleton=null;
    }

    @Override
    public void onSuccess(Object object) {
        if (view!=null){
            view.hideProgress();
            view.showComponents();
            User user=view.getUser();
            if (user!=null){
                if(view.isClickedbtnFollow()){
                    if (user.isFollowed()){
                        user.setFollowed(false);
                        view.changeIconButtonollow(RMapped.DRAWABLE_FOLLOW.getValue());
                        view.showButtonSendNotificacion(false);
                    }else{
                        user.setFollowed(true);
                        view.showButtonSendNotificacion(true);
                        view.changeIconButtonollow(RMapped.DRAWABLE_UNFOLLOW.getValue());
                    }
                    view.setClickedbtnFollow(false);
                }

                if(view.isClickedbtnSetNotificacion() && user.isFollowed()) {
                    if (user.isSentNotificacion()){
                        user.setSentNotificacion(false);
                        view.changeIconButtonSendNotificacion(RMapped.DRAWABLE_NOTIFICATION.getValue());
                    }else{
                        user.setSentNotificacion(true);
                        view.changeIconButtonSendNotificacion(RMapped.DRAWABLE_OFF_NOTIFICACION.getValue());
                    }
                    view.setClickedbtnSetNotificacion(false);
                }
            }
        }
    }

    @Override
    public void onError(Response response) {
        if (view!=null){
            view.hideProgress();
            view.showComponents();
            view.showMessage(response.getMessage());
        }
    }
}
