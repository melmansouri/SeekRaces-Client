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

    public LoginInteractorImpl(IListennerCallBack listennerCallBack){
        this.listennerCallBack=listennerCallBack;
    }

    @Override
    public void login(User user) {
        Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
        networkConnectionApi=retrofit.create(INetworkConnectionApi.class);

        Call<Response> loginCall=networkConnectionApi.login(user);
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
                Response response=new Response();
                response.setMessage(t.getMessage());
                listennerCallBack.onError(response);
            }
        });
    }

    @Override
    public void forgotPwd(String url) {
        Retrofit retrofit= RetrofitSingleton.getInstance().getRetrofit();
        networkConnectionApi=retrofit.create(INetworkConnectionApi.class);

        Call<Response> forgotPwdCall=networkConnectionApi.forgotPwd(url);
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
                Response response=new Response();
                response.setMessage(t.getMessage());
                listennerCallBack.onError(response);
            }
        });
    }
}
