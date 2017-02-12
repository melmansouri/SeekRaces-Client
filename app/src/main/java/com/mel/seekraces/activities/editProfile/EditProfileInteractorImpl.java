package com.mel.seekraces.activities.editProfile;

import com.google.gson.Gson;
import com.mel.seekraces.connection.RetrofitSingleton;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.INetworkConnectionApi;
import com.mel.seekraces.interfaces.editProfile.IEditProfileInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by void on 12/02/2017.
 */

public class EditProfileInteractorImpl implements IEditProfileInteractor {

    private IListennerCallBack callBack;
    private INetworkConnectionApi networkConnectionApi;

    public EditProfileInteractorImpl(IListennerCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void editProfile(User user) {
        final User userTmp=user;
        Retrofit retrofit = RetrofitSingleton.getInstance().getRetrofit();
        networkConnectionApi = retrofit.create(INetworkConnectionApi.class);

        Call<Response> edtiProfileCall = networkConnectionApi.editProfile(user);
        edtiProfileCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response responsetmp;
                if (!response.isSuccessful()) {
                    responsetmp = new Response();
                    responsetmp.setMessage(response.message());
                    responsetmp.setOk(false);

                } else {
                    responsetmp = response.body();
                    userTmp.setPhotoBase64("");
                    responsetmp.setContent(new Gson().toJson(userTmp));
                }

                if (responsetmp.isOk()) {
                    callBack.onSuccess(responsetmp);
                } else {
                    callBack.onError(responsetmp);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Response response = new Response();
                response.setMessage(t.getMessage());
                callBack.onError(response);
            }
        });

    }
}
