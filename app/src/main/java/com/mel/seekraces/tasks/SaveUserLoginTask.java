package com.mel.seekraces.tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.deserializers.UserDeserializer;
import com.mel.seekraces.entities.User;

/**
 * Created by void on 15/01/2017.
 */

public class SaveUserLoginTask extends AsyncTask<Void,Void,Void> {
    private String userJson;
    private SharedPreferencesSingleton sharedPreferencesSingleton;
    public SaveUserLoginTask(String userJson,SharedPreferencesSingleton sharedPreferencesSingleton){
        this.userJson=userJson;
        this.sharedPreferencesSingleton=sharedPreferencesSingleton;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.registerTypeAdapter(User.class,new UserDeserializer());
        Gson gson =gsonBuilder.create();
        User user=gson.fromJson(userJson,User.class);
        /*if ((user.getPhotoBase64()!=null && !user.getPhotoBase64().isEmpty()) && (user.getPhoto_url() !=null&&!user.getPhoto_url().isEmpty())){
            if (Utils.mkdir(Constantes.RUTA_IMAGENES)){
                Utils.saveImage(user.getPhotoBase64(),Constantes.RUTA_IMAGENES+user.getPhoto_url());
                user.setPhotoBase64("");
            }
        }*/
        sharedPreferencesSingleton.saveStringSP(Constantes.KEY_USER,user.getEmail());
        sharedPreferencesSingleton.saveStringSP(Constantes.KEY_USER_NAME_PICTURE,user.getPhoto_url());
        sharedPreferencesSingleton.saveStringSP(Constantes.KEY_USERNAME,user.getUsername());

        return null;
    }
}
