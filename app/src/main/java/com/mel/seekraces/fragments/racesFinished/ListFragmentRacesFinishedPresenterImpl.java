package com.mel.seekraces.fragments.racesFinished;

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
import com.mel.seekraces.interfaces.fragmentRacesPublishedFinished.IListFragmentRacesFinishedInteractor;
import com.mel.seekraces.interfaces.fragmentRacesPublishedFinished.IListFragmentRacesFinishedPresenter;
import com.mel.seekraces.interfaces.fragmentRacesPublishedFinished.IListFragmentRacesFinishedView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by void on 22/01/2017.
 */

public class ListFragmentRacesFinishedPresenterImpl implements IListFragmentRacesFinishedPresenter, IListennerCallBack{
    private IListFragmentRacesFinishedView view;
    private IListFragmentRacesFinishedInteractor interactor;
    private SharedPreferencesSingleton sharedPreferencesSingleton;

    public ListFragmentRacesFinishedPresenterImpl(IListFragmentRacesFinishedView view, SharedPreferencesSingleton sharedPreferencesSingleton){
        this.view=view;
        this.interactor=new ListFragmentRacesFinishedInteractorImpl(this);
        this.sharedPreferencesSingleton=sharedPreferencesSingleton;
    }


    @Override
    public void getOwnRacesPublished(boolean isOnline) {
        if (view!=null){
            view.showProgressBar();
            if (!isOnline){
                view.hideProgressBar();
                view.showMessage("Comprueba tu conexión");
                return;
            }
            //view.showList();
            String user=sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER);
            String url= INetworkConnectionApi.BASE_URL+"user/"+user+"/event";
            interactor.getRacesFinished(url);
        }
    }

    @Override
    public void onDestroy() {
        view=null;
        interactor.getRacesFinished(null);
        interactor=null;
        sharedPreferencesSingleton=null;
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

            interactor.deleteEvent(((Race)object).getUser().getEmail(),((Race)object).getId());
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
