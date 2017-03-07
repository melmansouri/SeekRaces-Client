package com.mel.seekraces.fragments.racesPublished;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mel.seekraces.adapters.RVRacesPublishedAdapter;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.deserializers.EventDeserializer;
import com.mel.seekraces.entities.Race;
import com.mel.seekraces.entities.Favorite;
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
    public void getRacesPublished(boolean isOnline, Filter filter) {
        if (view!=null){
            view.showProgressBar();
            if (!isOnline){
                view.hideProgressBar();
                view.showMessage("Comprueba tu conexión");
                return;
            }
            //view.showList();
            interactor.getRacesPublished(filter);
        }
    }

    @Override
    public void onOptionsItemSelected(int itemSelected, Filter filter) {
        if (view!=null){
            if (itemSelected== RMapped.ITEM_FILTER.getValue()){
                view.startScreenFilter(filter);
            }
        }
    }

    @Override
    public void onDestroy() {
        interactor.getRacesPublished(null);
        interactor.addEventToFavorites(null);
        interactor.deleteEventFromFavorite(null,0);
        interactor.deleteEvent(null,0);
        interactor=null;
        view=null;
        Log.e("ListRacesPublished","onDestroy");
    }

    @Override
    public void addEventToFavorite(boolean online, Favorite item) {
        if (view!=null){
            view.showProgressBar();
            if (!online){
                view.hideProgressBar();
                view.showMessage("Comprueba tu conexión");
                return;
            }
            interactor.addEventToFavorites(item);
        }

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
    public void deleteOwnRacePublished(boolean online, String user, int id) {
        if (view!=null){
            view.showProgressBar();
            if (!online){
                view.hideProgressBar();
                view.showMessage("Comprueba tu conexión");
                return;
            }
            interactor.deleteEvent(user,id);
        }
    }

    @Override
    public void deleteOwnRacePublished(boolean online, Object object) {
        if (view!=null && object!=null){
            view.showProgressBar();
            if (!online){
                view.hideProgressBar();
                view.showMessage("Comprueba tu conexión");
                return;
            }
            interactor.deleteEvent(((Race)object).getUser(),((Race)object).getId());
        }
    }

    @Override
    public void selectOptionDialogLongClickList(String[] options, int selected, Race race) {
        /*if (view!=null){
            if (options[selected].equals("Editar")) {
                view.editEvent(race);
            } else if (options[selected].equals("Eliminar")) {
                view.deleteOwnRacePublished(race.getUser(), race.getId());
            }
        }*/
    }

    @Override
    public void onSuccess(Object object) {
        if (view!=null){
                view.hideProgressBar();
                GsonBuilder gsonBuilder = new GsonBuilder()
                        .setLenient();
                gsonBuilder.registerTypeAdapter(Race.class,new EventDeserializer());
                Gson gson=gsonBuilder.create();
                Type founderListType = new TypeToken<ArrayList<Race>>(){}.getType();
            String content=((Response)object).getContent();
            List<Race> carreras=new ArrayList<>();
            if (content!=null && !content.isEmpty()){
                carreras=gson.fromJson(content,founderListType);
            }else{
                view.showMessage(((Response)object).getMessage());
            }
                view.fillAdapterList(carreras);
                view.showList();
        }
    }

    @Override
    public void onError(Response response) {
        if (view!=null){
            view.hideProgressBar();
            //view.hideList();
            view.showMessage(response.getMessage());
        }
    }
}
