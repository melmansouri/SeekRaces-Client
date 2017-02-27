package com.mel.seekraces.fragments.racesPublishedFavorites;

import com.mel.seekraces.connection.RetrofitSingleton;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.fragmentRacesPublishedFavorites.IListFragmentRacesPublishedFavoritesInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by void on 22/01/2017.
 */

public class ListFragmentRacesPublishedFavoritesInteractorImpl implements IListFragmentRacesPublishedFavoritesInteractor {
    private IListennerCallBack listennerCallBack;
    private INetworkConnectionApi networkConnectionApi;
    private Call<Response> racesPublishedFavoritesCall;
    Call<Response> deleteEventFromFavoritesCall;

    public ListFragmentRacesPublishedFavoritesInteractorImpl(IListennerCallBack listennerCallBack) {
        this.listennerCallBack = listennerCallBack;
    }

    @Override
    public void getRacesPublishedFavorites(String url) {
        if (url!=null){
            Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
            networkConnectionApi=retrofit.create(INetworkConnectionApi.class);

            racesPublishedFavoritesCall=networkConnectionApi.getOwnRacesPublished(url);
            racesPublishedFavoritesCall.enqueue(new Callback<Response>() {
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
                    if (racesPublishedFavoritesCall!=null && !racesPublishedFavoritesCall.isCanceled()){
                        Response response=new Response();
                        response.setMessage(t.getMessage());
                        listennerCallBack.onError(response);
                    }
                }
            });
        }else{
            if (racesPublishedFavoritesCall!=null){
                racesPublishedFavoritesCall.cancel();
            }
        }
    }
    @Override
    public void deleteEventFromFavorite(final String user,int id) {
        if (user!=null){
            Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
            networkConnectionApi=retrofit.create(INetworkConnectionApi.class);
            String url = INetworkConnectionApi.BASE_URL + "user/" + user + "/event/" + id + "/favorites";
            deleteEventFromFavoritesCall=networkConnectionApi.deleteEventFromFavorites(url);
            deleteEventFromFavoritesCall.enqueue(new Callback<Response>() {
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
                        //listennerCallBack.onSuccess(responsetmp.isOk());

                        String url= INetworkConnectionApi.BASE_URL+"user/"+user+"/event/favorites";
                        getRacesPublishedFavorites(url);
                    }else{
                        listennerCallBack.onError(responsetmp);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    if (deleteEventFromFavoritesCall!=null && !deleteEventFromFavoritesCall.isCanceled()){
                        Response response=new Response();
                        response.setMessage(t.getMessage());
                        listennerCallBack.onError(response);
                    }
                }
            });
        }else{
            if (deleteEventFromFavoritesCall!=null){
                deleteEventFromFavoritesCall.cancel();
            }
        }
    }
}
