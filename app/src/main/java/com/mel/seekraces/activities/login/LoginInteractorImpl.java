package com.mel.seekraces.activities.login;

import android.util.Log;

import com.google.gson.Gson;
import com.mel.seekraces.connection.RetrofitSingleton;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.login.ILoginInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by moha on 10/01/17.
 */

public class LoginInteractorImpl implements ILoginInteractor{
    private IListennerCallBack listennerCallBack;
    private INetworkConnectionApi networkConnectionApi;
    private Call<Response> forgotPwdCall;
    private Call<Response> loginCall;

    public LoginInteractorImpl(IListennerCallBack listennerCallBack){
        this.listennerCallBack=listennerCallBack;
        Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
        networkConnectionApi=retrofit.create(INetworkConnectionApi.class);
    }

    @Override
    public void login(User user) {
        if (user!=null){
            loginCall=networkConnectionApi.login(user);
            loginCall.enqueue(new Callback<Response>() {
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
                    Log.e("LoginInteractor",responsetmp.toString());
                    if (responsetmp.isOk()){
                        listennerCallBack.onSuccess(responsetmp);
                    }else{
                        listennerCallBack.onError(responsetmp);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    if (!loginCall.isCanceled()){
                        Response response=new Response();
                        response.setMessage(t.getMessage());
                        listennerCallBack.onError(response);
                    }
                }
            });
        }else{
            if (loginCall!=null) {
                loginCall.cancel();
            }
        }
    }

    @Override
    public void forgotPwd(String url) {
        if (url!=null){
            forgotPwdCall=networkConnectionApi.forgotPwd(url);
            forgotPwdCall.enqueue(new Callback<Response>() {
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
                        listennerCallBack.onSuccess(responsetmp.getMessage());
                    }else{
                        listennerCallBack.onError(responsetmp);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    if (forgotPwdCall!=null && !forgotPwdCall.isCanceled()){
                        Response response=new Response();
                        response.setMessage(t.getMessage());
                        listennerCallBack.onError(response);
                    }
                }
            });
        }else{
            if (forgotPwdCall!=null){
                forgotPwdCall.cancel();
            }
        }
    }
}
