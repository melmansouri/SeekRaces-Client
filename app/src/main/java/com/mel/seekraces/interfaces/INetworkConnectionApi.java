package com.mel.seekraces.interfaces;

import com.mel.seekraces.entities.Favorite;
import com.mel.seekraces.entities.Follow;
import com.mel.seekraces.entities.PlacePredictions;
import com.mel.seekraces.entities.Race;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.Review;
import com.mel.seekraces.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by void on 15/01/2017.
 */

public interface INetworkConnectionApi {
    // TODO: Cambiar host por "10.0.0.2" para Genymotion.
    // TODO: Cambiar host por "10.0.0.3" para AVD.
    // TODO: Cambiar host por IP de tu PC para dispositivo real.
    String BASE_URL = "https://seekraces.tk/SeekRaces/";
    String BASE_URL_PICTURES = "https://seekraces.tk/SeekRaces/pictures/";

    //String BASE_URL = "http://192.168.0.101:8080/SeekRaces/api/";
    //String BASE_URL_PICTURES = "http://192.168.0.101:8080/SeekRaces/pictures/";

    //String BASE_URL = "http://192.168.105.109/SeekRaces/api/";
    //String BASE_URL_PICTURES = "http://192.168.105.109/SeekRaces/pictures/";

    @GET
    Call<PlacePredictions> getAutoCompletePlaces(@Url String url);

    @POST("user/signIn")
    Call<Response> signIn(@Body User user);

    @POST("user/login")
    Call<Response> login(@Body User user);

    @POST("user/follow")
    Call<Response> follow(@Body Follow follow);

    @DELETE
    Call<Response> unfollow(@Url String url);

    @PUT("user/follow")
    Call<Response> updateFollowToSentNotificacion(@Body Follow follow);

    @GET
    Call<Response> forgotPwd(@Url String url);

    @PUT("user")
    Call<Response> editProfile(@Body User user);

    @GET("event")
    Call<Response> getRacesPublished(@Query("user") String user,
                                     @Query("name") String name,
                                     @Query("place") String place,
                                     @Query("distanceMin") int distanceMin,
                                     @Query("distanceMax") int distanceMax,
                                     @Query("date_interval_init") String date_interval_init,
                                     @Query("date_interval_end") String date_interval_end);

    @GET
    Call<Response> getOwnRacesPublished(@Url String url);

    @DELETE
    Call<Response> deleteOwnRacePublished(@Url String url);

    @GET("event/finished")
    Call<Response> getRacesFinished();

    @POST("event")
    Call<Response> addRaces(@Body Race race);

    @POST("user/event/favorites")
    Call<Response> addEventToFavorites(@Body Favorite favorite);

    @DELETE
    Call<Response> deleteEventFromFavorites(@Url String url);

    @PUT("event")
    Call<Response> editRace(@Body Race race);

    @GET
    Call<Response> getReviews(@Url String url);

    @POST("user/event/reviews")
    Call<Response> addNewOpinionEvent(@Body Review review);

    @PUT("user/event/reviews")
    Call<Response> editEventOpinion(@Body Review review);

    @DELETE
    Call<Response> deleteEventOpinion(@Url String url);
}
