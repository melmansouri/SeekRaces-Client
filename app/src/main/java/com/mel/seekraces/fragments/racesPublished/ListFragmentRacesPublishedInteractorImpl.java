package com.mel.seekraces.fragments.racesPublished;

import com.mel.seekraces.connection.RetrofitSingleton;
import com.mel.seekraces.entities.Favorite;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.fragmentRacesPublished.IListFragmentRacesPublishedInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by void on 22/01/2017.
 */

public class ListFragmentRacesPublishedInteractorImpl implements IListFragmentRacesPublishedInteractor{
    private IListennerCallBack listennerCallBack;
    private INetworkConnectionApi networkConnectionApi;
    Call<Response> racesPublishedCall;
    Call<Response> addEventToFavoritesCall;
    Call<Response> deleteEventFromFavoritesCall;

    public ListFragmentRacesPublishedInteractorImpl(IListennerCallBack listennerCallBack) {
        this.listennerCallBack = listennerCallBack;
    }

    @Override
    public void getRacesPublished(Filter filter) {
        if (filter!=null){
            Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
            networkConnectionApi=retrofit.create(INetworkConnectionApi.class);

            racesPublishedCall=networkConnectionApi.getRacesPublished(filter.getUser(),filter.getName(),filter.getPlace(),filter.getDistanceMin(),filter.getDistanceMax(),filter.getDate_interval_init(),filter.getDate_interval_end());
            racesPublishedCall.enqueue(new Callback<Response>() {
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
                    if (racesPublishedCall!=null && !racesPublishedCall.isCanceled()){
                        Response response=new Response();
                        response.setMessage("Problemas para conectar con el servidor. Intentalo más tarde");
                        listennerCallBack.onError(response);
                    }
                }
            });
        }else{
            if (racesPublishedCall!=null){
                racesPublishedCall.cancel();
            }
        }
    }

    @Override
    public void addEventToFavorites(final Favorite item) {
        if (item!=null){
            Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
            networkConnectionApi=retrofit.create(INetworkConnectionApi.class);

            addEventToFavoritesCall=networkConnectionApi.addEventToFavorites(item);
            addEventToFavoritesCall.enqueue(new Callback<Response>() {
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
                        Filter filter=new Filter();
                        filter.setUser(item.getUser());
                        getRacesPublished(filter);
                    }else{
                        listennerCallBack.onError(responsetmp);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    if (addEventToFavoritesCall!=null && !addEventToFavoritesCall.isCanceled()){
                        Response response=new Response();
                        response.setMessage("Problemas para conectar con el servidor. Intentalo más tarde");
                        listennerCallBack.onError(response);
                    }
                }
            });
        }else{
            if (addEventToFavoritesCall!=null){
                addEventToFavoritesCall.cancel();
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

                        Filter filter=new Filter();
                        filter.setUser(user);
                        getRacesPublished(filter);
                    }else{
                        listennerCallBack.onError(responsetmp);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    if (deleteEventFromFavoritesCall!=null && !deleteEventFromFavoritesCall.isCanceled()){
                        Response response=new Response();
                        response.setMessage("Problemas para conectar con el servidor. Intentalo más tarde");
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
