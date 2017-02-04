package com.mel.seekraces.fragments.ownRacesPublished;

import com.mel.seekraces.connection.RetrofitSingleton;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.fragmentOwnRacesPublished.IListFragmentOwnRacesPublishedInteractor;
import com.mel.seekraces.interfaces.fragmentRacesPublished.IListFragmentRacesPublishedInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by void on 22/01/2017.
 */

public class ListFragmentOwnRacesPublishedInteractorImpl implements IListFragmentOwnRacesPublishedInteractor{
    private IListennerCallBack listennerCallBack;
    private INetworkConnectionApi networkConnectionApi;

    public ListFragmentOwnRacesPublishedInteractorImpl(IListennerCallBack listennerCallBack) {
        this.listennerCallBack = listennerCallBack;
    }

    @Override
    public void getOwnRacesPublished(String url) {
        Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
        networkConnectionApi=retrofit.create(INetworkConnectionApi.class);

        Call<Response> signInCall=networkConnectionApi.getOwnRacesPublished(url);
        signInCall.enqueue(new Callback<Response>() {
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
                Response response=new Response();
                response.setMessage(t.getMessage());
                listennerCallBack.onError(response);
            }
        });
    }
}
