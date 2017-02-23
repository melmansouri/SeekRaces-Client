package com.mel.seekraces.activities.newRace;

import android.text.style.ReplacementSpan;

import com.mel.seekraces.connection.RetrofitSingleton;
import com.mel.seekraces.entities.Event;
import com.mel.seekraces.entities.PlacePredictions;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.newRace.IAddNewRaceInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by void on 05/02/2017.
 */

public class AddNewRaceInteractorImpl implements IAddNewRaceInteractor {
    private IListennerCallBack listennerCallBack;
    private INetworkConnectionApi networkConnectionApi;
    private Call<Response> addRacesCall;

    public AddNewRaceInteractorImpl(IListennerCallBack listennerCallBack) {
        this.listennerCallBack = listennerCallBack;
        Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
        networkConnectionApi=retrofit.create(INetworkConnectionApi.class);
    }

    @Override
    public void addRace(Event event) {
        if (event!=null){
            addRacesCall=networkConnectionApi.addRaces(event);
            addRacesCall.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    Response responsetmp;
                    if (!response.isSuccessful()){
                        responsetmp=new Response();
                        responsetmp.setMessage(response.message());
                        responsetmp.setOk(false);

                    }else{
                        responsetmp=response.body();
                    }

                    if (responsetmp.isOk()){
                        listennerCallBack.onSuccess(responsetmp);
                    }else{
                        listennerCallBack.onError(responsetmp);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    if (addRacesCall!=null && !addRacesCall.isCanceled()){
                        Response response=new Response();
                        response.setMessage(t.getMessage());
                        listennerCallBack.onError(response);
                    }
                }
            });
        }else{
            if (addRacesCall != null) {
                addRacesCall.cancel();
            }
        }

    }

    @Override
    public void getAutoCompletePlaces(String url) {
        Call<PlacePredictions> getPlacesCall=null;
        if (url != null){
            getPlacesCall=networkConnectionApi.getAutoCompletePlaces(url);
            getPlacesCall.enqueue(new Callback<PlacePredictions>() {
                @Override
                public void onResponse(Call<PlacePredictions> call, retrofit2.Response<PlacePredictions> response) {
                    PlacePredictions responsetmp;
                    if (!response.isSuccessful()){
                        responsetmp=new PlacePredictions();

                    }else{
                        responsetmp=response.body();
                    }

                        listennerCallBack.onSuccess(responsetmp);
                }

                @Override
                public void onFailure(Call<PlacePredictions> call, Throwable t) {
                }
            });
        }else{
            if (getPlacesCall!=null){
                getPlacesCall.cancel();
            }
        }
    }
}
