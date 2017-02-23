package com.mel.seekraces.activities.signin;

import android.util.Log;

import com.mel.seekraces.connection.RetrofitSingleton;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.signin.ISignInInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by moha on 13/01/17.
 */

public class SignInInteractorImpl implements ISignInInteractor{
    private IListennerCallBack listennerCallBack;
    private INetworkConnectionApi networkConnectionApi;
    private Call<Response> signInCall;

    public SignInInteractorImpl(IListennerCallBack listennerCallBack){
        this.listennerCallBack=listennerCallBack;
        Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
        networkConnectionApi=retrofit.create(INetworkConnectionApi.class);
    }

    @Override
    public void signIn(User user) {
        if (user!=null){
            signInCall=networkConnectionApi.signIn(user);
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
                    if (signInCall!=null && !signInCall.isCanceled()){
                        Response response=new Response();
                        response.setMessage(t.getMessage());
                        listennerCallBack.onError(response);
                    }
                }
            });
        }else{
            if (signInCall!=null){
                signInCall.cancel();
            }
        }
    }
}
