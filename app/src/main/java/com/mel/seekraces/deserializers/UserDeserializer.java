package com.mel.seekraces.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mel.seekraces.entities.User;

import java.lang.reflect.Type;

/**
 * Created by moha on 23/01/17.
 */

public class UserDeserializer implements JsonDeserializer<User>{
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject=json.getAsJsonObject();
        User user=new User();
        user.setEmail(jsonObject.get("email").getAsString());
        user.setUsername(jsonObject.get("username").getAsString());
        user.setPhoto_url(jsonObject.get("photo_url").getAsString());
        //String base64=jsonObject.get("photoBase64").getAsString();
        /*if (!TextUtils.isEmpty(base64)){
            user.setPhoto(Utils.base64ToBitmap(base64));
        }*/
        //user.setPhotoBase64(base64);
        if (!jsonObject.get("country").isJsonNull()){
            user.setCountry(jsonObject.get("country").getAsString());
        }

        return user;
    }
}