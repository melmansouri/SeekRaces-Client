package com.mel.seekraces.fragments.racesPublishedFavorites;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mel.seekraces.adapters.RVRacesPublishedAdapter;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.deserializers.EventDeserializer;
import com.mel.seekraces.entities.Race;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.fragmentRacesPublishedFavorites.IListFragmentRacesPublishedFavoritesInteractor;
import com.mel.seekraces.interfaces.fragmentRacesPublishedFavorites.IListFragmentRacesPublishedFavoritesPresenter;
import com.mel.seekraces.interfaces.fragmentRacesPublishedFavorites.IListFragmentRacesPublishedFavoritesView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by void on 22/01/2017.
 */

public class ListFragmentRacesPublishedFavoritesPresenterImpl implements IListFragmentRacesPublishedFavoritesPresenter, IListennerCallBack {
    private IListFragmentRacesPublishedFavoritesView view;
    private IListFragmentRacesPublishedFavoritesInteractor interactor;
    private SharedPreferencesSingleton sharedPreferencesSingleton;

    public ListFragmentRacesPublishedFavoritesPresenterImpl(IListFragmentRacesPublishedFavoritesView view, SharedPreferencesSingleton sharedPreferencesSingleton) {
        this.view = view;
        this.interactor = new ListFragmentRacesPublishedFavoritesInteractorImpl(this);
        this.sharedPreferencesSingleton = sharedPreferencesSingleton;
    }


    @Override
    public void getRacesPublishedFavorites(boolean isOnline) {
        if (view != null) {
            view.showProgressBar();
            if (!isOnline){
                view.hideProgressBar();
                view.showMessage("Comprueba tu conexión");
                return;
            }
            //view.showList();
            String user = sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER);
            String url = INetworkConnectionApi.BASE_URL + "user/" + user + "/event/favorites";
            interactor.getRacesPublishedFavorites(url);
        }
    }

    @Override
    public void onDestroy() {
        interactor.getRacesPublishedFavorites(null);
        interactor.deleteEventFromFavorite(null, 0);
        view = null;
        interactor=null;
        sharedPreferencesSingleton=null;
    }

    @Override
    public void deleteEventFromFavorite(boolean online, String user, int id) {
        if (view!=null){
            view.showProgressBar();
            if (!online){
                view.hideProgressBar();
                view.showMessage("Comprueba tu conexión");
                return;
            }
            interactor.deleteEventFromFavorite(user,id);
        }
    }

    @Override
    public void filter(RVRacesPublishedAdapter adapter, List<Race> racesWithoutFilter, String newText) {
        if (adapter!=null){
            adapter.filter(racesWithoutFilter,newText);
        }
    }

    @Override
    public void onSuccess(Object object) {
        if (view != null) {
            view.hideProgressBar();
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .setLenient();
            gsonBuilder.registerTypeAdapter(Race.class, new EventDeserializer());
            Gson gson = gsonBuilder.create();
            Type founderListType = new TypeToken<ArrayList<Race>>() {
            }.getType();
            List<Race> carreras = gson.fromJson(((Response) object).getContent(), founderListType);
            view.fillAdapterList(carreras);
            view.showList();
        }
    }

    @Override
    public void onError(Response response) {
        if (view != null) {
            view.hideProgressBar();
            //view.hideList();
            view.showMessage(response.getMessage());
        }
    }
}
