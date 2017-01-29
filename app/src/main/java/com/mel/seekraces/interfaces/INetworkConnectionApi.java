package com.mel.seekraces.interfaces;

import com.mel.seekraces.entities.Event;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by void on 15/01/2017.
 */

public interface INetworkConnectionApi {
    // TODO: Cambiar host por "10.0.0.2" para Genymotion.
    // TODO: Cambiar host por "10.0.0.3" para AVD.
    // TODO: Cambiar host por IP de tu PC para dispositivo real.
    String BASE_URL = "http://192.168.0.102:8080/SeekRaces/api/";
    String BASE_URL_PICTURES = "http://192.168.0.102:8080/SeekRaces/pictures/";
    //String BASE_URL = "http://192.168.105.18/SeekRaces/api/";
    //String BASE_URL_PICTURES = "http://192.168.105.18/SeekRaces/pictures/";

    @POST("user/signIn")
    Call<Response> signIn(@Body User user);

    @POST("user/login")
    Call<Response> login(@Body User user);

    @GET("event")
    Call<Response> getRacesPublished(@Query("user") String user,
                                     @Query("country") String country,
                                     @Query("city") String city,
                                     @Query("distance") String distance,
                                     @Query("date_interval_init") String date_interval_init,
                                     @Query("date_interval_end") String date_interval_end);

    @POST("event")
    Call<Response> addRaces(@Body Event event);

}
