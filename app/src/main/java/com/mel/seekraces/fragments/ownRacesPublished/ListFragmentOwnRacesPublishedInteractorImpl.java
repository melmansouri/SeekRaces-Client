package com.mel.seekraces.fragments.ownRacesPublished;

import com.mel.seekraces.connection.RetrofitSingleton;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.fragmentOwnRacesPublished.IListFragmentOwnRacesPublishedInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by void on 22/01/2017.
 */

public class ListFragmentOwnRacesPublishedInteractorImpl implements IListFragmentOwnRacesPublishedInteractor{
    private IListennerCallBack listennerCallBack;
    private Call<Response> ownRacesPublishedCall;
    private Call<Response> deleteOwnRacePublished;

    public ListFragmentOwnRacesPublishedInteractorImpl(IListennerCallBack listennerCallBack) {
        this.listennerCallBack = listennerCallBack;
    }

    @Override
    public void getOwnRacesPublished(String url) {
        if (url!=null){
            Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
            INetworkConnectionApi networkConnectionApi=retrofit.create(INetworkConnectionApi.class);

            ownRacesPublishedCall=networkConnectionApi.getOwnRacesPublished(url);
            ownRacesPublishedCall.enqueue(new Callback<Response>() {
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
                    if (ownRacesPublishedCall!=null && !ownRacesPublishedCall.isCanceled()) {
                        Response response=new Response();
                        response.setMessage(t.getMessage());
                        listennerCallBack.onError(response);
                    }
                }
            });
        }else{
            if (ownRacesPublishedCall!=null){
                ownRacesPublishedCall.cancel();
            }
        }
    }

    @Override
    public void deleteEvent(final String user,int id) {
        if (user!=null){
            Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
            INetworkConnectionApi networkConnectionApi=retrofit.create(INetworkConnectionApi.class);

            String url = INetworkConnectionApi.BASE_URL + "user/" + user + "/event/" + id;
            deleteOwnRacePublished=networkConnectionApi.deleteOwnRacePublished(url);
            deleteOwnRacePublished.enqueue(new Callback<Response>() {
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
                        //listennerCallBack.onSuccess(responsetmp);
                        String url= INetworkConnectionApi.BASE_URL+"user/"+user+"/event";
                        getOwnRacesPublished(url);
                    }else{
                        listennerCallBack.onError(responsetmp);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    if (deleteOwnRacePublished!=null && !deleteOwnRacePublished.isCanceled()) {
                        Response response=new Response();
                        response.setMessage(t.getMessage());
                        listennerCallBack.onError(response);
                    }
                }
            });
        }else{
            if (deleteOwnRacePublished!=null){
                deleteOwnRacePublished.cancel();
            }
        }
    }
}
