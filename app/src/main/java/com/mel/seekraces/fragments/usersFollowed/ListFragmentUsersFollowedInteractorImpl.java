package com.mel.seekraces.fragments.usersFollowed;

import com.mel.seekraces.connection.RetrofitSingleton;
import com.mel.seekraces.entities.Follow;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.fragmentUserFollowed.IFragmentListUsersFollowedInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by void on 08/03/2017.
 */

public class ListFragmentUsersFollowedInteractorImpl implements IFragmentListUsersFollowedInteractor {

    private IListennerCallBack callBack;
    private INetworkConnectionApi networkConnectionApi;
    private Call<Response> unFollowCall;
    private Call<Response> getUsersFollowedCall;
    private Call<Response> updateFollowCall;

    public ListFragmentUsersFollowedInteractorImpl(IListennerCallBack callBack) {
        this.callBack = callBack;
        Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
        networkConnectionApi=retrofit.create(INetworkConnectionApi.class);
    }


    @Override
    public void getUsersFollowed(String url) {
        if (url !=null){
            getUsersFollowedCall=networkConnectionApi.getListFollowed(url);
            getUsersFollowedCall.enqueue(new Callback<Response>() {
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
                    if (getUsersFollowedCall!=null && !getUsersFollowedCall.isCanceled()){
                        Response response=new Response();
                        response.setMessage("Problemas para conectar con el servidor. Intentalo más tarde");
                        callBack.onError(response);
                    }
                }
            });
        }else{
            if (getUsersFollowedCall != null) {
                getUsersFollowedCall.cancel();
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
}
