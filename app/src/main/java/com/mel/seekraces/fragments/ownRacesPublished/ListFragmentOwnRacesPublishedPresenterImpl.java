package com.mel.seekraces.fragments.ownRacesPublished;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.deserializers.EventDeserializer;
import com.mel.seekraces.entities.Event;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.fragmentOwnRacesPublished.IListFragmentOwnRacesPublishedInteractor;
import com.mel.seekraces.interfaces.fragmentOwnRacesPublished.IListFragmentOwnRacesPublishedPresenter;
import com.mel.seekraces.interfaces.fragmentOwnRacesPublished.IListFragmentOwnRacesPublishedView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by void on 22/01/2017.
 */

public class ListFragmentOwnRacesPublishedPresenterImpl implements IListFragmentOwnRacesPublishedPresenter, IListennerCallBack{
    private IListFragmentOwnRacesPublishedView view;
    private IListFragmentOwnRacesPublishedInteractor interactor;
    private SharedPreferencesSingleton sharedPreferencesSingleton;

    public ListFragmentOwnRacesPublishedPresenterImpl(IListFragmentOwnRacesPublishedView view, SharedPreferencesSingleton sharedPreferencesSingleton){
        this.view=view;
        this.interactor=new ListFragmentOwnRacesPublishedInteractorImpl(this);
        this.sharedPreferencesSingleton=sharedPreferencesSingleton;
    }


    @Override
    public void getOwnRacesPublished() {
        if (view!=null){
            view.showProgressBar();
            //view.showList();
            String user=sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER);
            String url= INetworkConnectionApi.BASE_URL+"user/"+user+"/event";
            interactor.getOwnRacesPublished(url);
        }
    }

    @Override
    public void onDestroy() {
        view=null;
        interactor.getOwnRacesPublished(null);
    }

    @Override
    public void selectOptionDialogLongClickList(String[] options, int selected,Event event) {
        if (view!=null){
            if (options[selected].equals("Editar")) {
                view.editEvent(event);
            } else if (options[selected].equals("Eliminar")) {
                view.deleteOwnRacePublished(event.getUser(),event.getId());
            }
        }
    }

    @Override
    public void deleteOwnRacePublished(String user, int id) {
        interactor.deleteEvent(user,id);
    }

    @Override
    public void onSuccess(Object object) {
        if (view!=null){
            view.hideProgressBar();
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .setLenient();
            gsonBuilder.registerTypeAdapter(Event.class,new EventDeserializer());
            Gson gson=gsonBuilder.create();
            Type founderListType = new TypeToken<ArrayList<Event>>(){}.getType();
            List<Event> carreras=gson.fromJson(((Response)object).getContent(),founderListType);
            view.fillAdapterList(carreras);
            view.showList();
        }
    }

    @Override
    public void onError(Response response) {
        if (view!=null){
            view.hideProgressBar();
            view.hideList();
            view.showMessage(response.getMessage());
        }
    }
}
