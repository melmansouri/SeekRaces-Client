package com.mel.seekraces.fragments.editRace;

import com.mel.seekraces.connection.RetrofitSingleton;
import com.mel.seekraces.entities.Race;
import com.mel.seekraces.entities.PlacePredictions;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.fragmentEditRace.IEditRaceInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by void on 05/02/2017.
 */

public class EditRaceFragmentInteractorImpl implements IEditRaceInteractor {
    private IListennerCallBack listennerCallBack;
    private INetworkConnectionApi networkConnectionApi;
    private Call<Response> editRaceCall;

    public EditRaceFragmentInteractorImpl(IListennerCallBack listennerCallBack) {
        this.listennerCallBack = listennerCallBack;
        Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
        networkConnectionApi=retrofit.create(INetworkConnectionApi.class);
    }

    @Override
    public void editRace(Race race) {
        if (race !=null){
            editRaceCall=networkConnectionApi.editRace(race);
            editRaceCall.enqueue(new Callback<Response>() {
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
                    if (editRaceCall!=null && !editRaceCall.isCanceled()){
                        Response response=new Response();
                        response.setMessage("Problemas para conectar con el servidor. Intentalo más tarde");
                        listennerCallBack.onError(response);
                    }
                }
            });
        }else{
            if (editRaceCall != null) {
                editRaceCall.cancel();
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
