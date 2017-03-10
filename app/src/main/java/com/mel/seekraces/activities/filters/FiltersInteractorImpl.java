package com.mel.seekraces.activities.filters;

import com.mel.seekraces.connection.RetrofitSingleton;
import com.mel.seekraces.entities.PlacePredictions;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.filters.IFiltersInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by void on 05/02/2017.
 */

public class FiltersInteractorImpl implements IFiltersInteractor {
    private IListennerCallBack listennerCallBack;
    private INetworkConnectionApi networkConnectionApi;

    public FiltersInteractorImpl(IListennerCallBack listennerCallBack) {
        this.listennerCallBack = listennerCallBack;
    }

    @Override
    public void getAutoCompletePlaces(String url) {
        Call<PlacePredictions> getPlacesCall=null;
        if (url != null){
            Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
            networkConnectionApi=retrofit.create(INetworkConnectionApi.class);

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
