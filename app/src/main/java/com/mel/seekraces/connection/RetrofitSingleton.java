package com.mel.seekraces.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mel.seekraces.interfaces.INetworkConnectionApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by void on 15/01/2017.
 */

public class RetrofitSingleton {
    private static RetrofitSingleton instance;
    private Retrofit retrofit;

    private RetrofitSingleton(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit=new Retrofit.Builder()
                .baseUrl(INetworkConnectionApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static RetrofitSingleton getInstance(){
        if (instance==null){
            instance=new RetrofitSingleton();
        }
        return instance;
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }


}
