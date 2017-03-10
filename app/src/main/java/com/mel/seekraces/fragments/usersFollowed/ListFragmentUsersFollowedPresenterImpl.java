package com.mel.seekraces.fragments.usersFollowed;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.deserializers.UserDeserializer;
import com.mel.seekraces.entities.Follow;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.fragmentUserFollowed.IFragmentListUsersFollowedInteractor;
import com.mel.seekraces.interfaces.fragmentUserFollowed.IFragmentListUsersFollowedPresenter;
import com.mel.seekraces.interfaces.fragmentUserFollowed.IFragmentListUsersFollowedView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by void on 08/03/2017.
 */

public class ListFragmentUsersFollowedPresenterImpl implements IFragmentListUsersFollowedPresenter,IListennerCallBack{

    private SharedPreferencesSingleton sharedPreferencesSingleton;
    private IFragmentListUsersFollowedView view;
    private IFragmentListUsersFollowedInteractor interactor;

    public ListFragmentUsersFollowedPresenterImpl(SharedPreferencesSingleton sharedPreferencesSingleton, IFragmentListUsersFollowedView view) {
        this.sharedPreferencesSingleton = sharedPreferencesSingleton;
        this.view = view;
        this.interactor=new ListFragmentUsersFollowedInteractorImpl(this);
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
    public void getUsersFollowed(boolean isOnline) {
        if (view!=null){
            view.showProgress();
            view.hideComponents();
            if (!isOnline){
                view.hideProgress();
                view.showComponents();
                view.showMessage("Comprueba tu conexión");
                return;
            }
            String url= INetworkConnectionApi.BASE_URL+"user/"+sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER)+"/followed";
            interactor.getUsersFollowed(url);
        }
    }

    @Override
    public void onDestroy() {
        interactor.unFollow(null);
        interactor.setSentNotificacion(null);
        view=null;
        interactor=null;
        sharedPreferencesSingleton=null;
    }

    @Override
    public void onSuccess(Object object) {
        if (view!=null){
            try{
                String userJson=((Response)object ).getContent();
                GsonBuilder gsonBuilder=new GsonBuilder();
                gsonBuilder.registerTypeAdapter(User.class,new UserDeserializer());
                Gson gson =gsonBuilder.create();
                Type listType = new TypeToken<ArrayList<User>>(){}.getType();

                List<User> followed = gson.fromJson(userJson, listType);
                if (followed!=null){
                    if (followed.size()>0){
                        view.fillAdapter(followed);
                    }else{
                        view.showMessage("No sigues a ningún usuario");
                    }
                }else if(followed==null){
                    view.showMessage("No sigues a ningún usuario");
                }
            }catch(Exception e){
                e.printStackTrace();
                if (view.getUserFollowedSelectedToDelete()!=null && !view.getUserFollowedSelectedToDelete().isEmpty()){
                    view.unFollowFromAdapter();
                }else if (view.getUserFollowedSelectedToSetSendNotificacion()!=null && !view.getUserFollowedSelectedToSetSendNotificacion().isEmpty()){
                    view.setSendNotificacionFromAdapter();
                }
            }
            view.hideProgress();
            view.showComponents();
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
