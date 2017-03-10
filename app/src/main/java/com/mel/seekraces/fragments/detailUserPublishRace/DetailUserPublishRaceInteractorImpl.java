package com.mel.seekraces.fragments.detailUserPublishRace;

import com.mel.seekraces.connection.RetrofitSingleton;
import com.mel.seekraces.entities.Follow;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.detailUserPublishRace.IDetailUserPublishRaceInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by void on 08/03/2017.
 */

public class DetailUserPublishRaceInteractorImpl implements IDetailUserPublishRaceInteractor {

    private IListennerCallBack callBack;
    private INetworkConnectionApi networkConnectionApi;
    private Call<Response> followCall;
    private Call<Response> unFollowCall;
    private Call<Response> updateFollowCall;

    public DetailUserPublishRaceInteractorImpl(IListennerCallBack callBack) {
        this.callBack = callBack;
        Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
        networkConnectionApi=retrofit.create(INetworkConnectionApi.class);
    }

    @Override
    public void follow(Follow follow) {
        if (follow !=null){
            followCall=networkConnectionApi.follow(follow);
            followCall.enqueue(new Callback<Response>() {
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
                        callBack.onSuccess(responsetmp);
                    }else{
                        callBack.onError(responsetmp);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    if (followCall!=null && !followCall.isCanceled()){
                        Response response=new Response();
                        response.setMessage("Problemas para conectar con el servidor. Intentalo más tarde");
                        callBack.onError(response);
                    }
                }
            });
        }else{
            if (followCall != null) {
                followCall.cancel();
            }
        }
    }

    @Override
    public void unFollow(String url) {
        if (url !=null){
            unFollowCall=networkConnectionApi.unfollow(url);
            unFollowCall.enqueue(new Callback<Response>() {
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
                        callBack.onSuccess(responsetmp);
                    }else{
                        callBack.onError(responsetmp);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    if (unFollowCall!=null && !unFollowCall.isCanceled()){
                        Response response=new Response();
                        response.setMessage("Problemas para conectar con el servidor. Intentalo más tarde");
                        callBack.onError(response);
                    }
                }
            });
        }else{
            if (unFollowCall != null) {
                unFollowCall.cancel();
            }
        }
    }

    @Override
    public void setSentNotificacion(Follow follow) {
        if (follow !=null){
            updateFollowCall=networkConnectionApi.updateFollowToSentNotificacion(follow);
            updateFollowCall.enqueue(new Callback<Response>() {
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
                        callBack.onSuccess(responsetmp);
                    }else{
                        callBack.onError(responsetmp);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    if (updateFollowCall!=null && !updateFollowCall.isCanceled()){
                        Response response=new Response();
                        response.setMessage("Problemas para conectar con el servidor. Intentalo más tarde");
                        callBack.onError(response);
                    }
                }
            });
        }else{
            if (updateFollowCall != null) {
                updateFollowCall.cancel();
            }
        }
    }

    @Override
    public void getRacesPublishedByUser(String url) {

    }
}
