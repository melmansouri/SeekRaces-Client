package com.mel.seekraces.deserializers;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mel.seekraces.entities.Race;
import com.mel.seekraces.entities.User;

import java.lang.reflect.Type;

/**
 * Created by moha on 23/01/17.
 */

public class EventDeserializer implements JsonDeserializer<Race>{
    @Override
    public Race deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Race race = new Race();
        try{
            race.setId(jsonObject.get("id").getAsInt());
            //race.setUserEmail(jsonObject.get("user").getAsString());
            race.setUserName(jsonObject.get("userName").getAsString());
            race.setName(jsonObject.get("name").getAsString());
            if (!jsonObject.get("description").isJsonNull()){
                race.setDescription(jsonObject.get("description").getAsString());
            }
            if (!jsonObject.get("imageName").isJsonNull()){
                race.setImageName(jsonObject.get("imageName").getAsString());
            }
        /*String base64 = jsonObject.get("imageBase64").getAsString();
        if (!TextUtils.isEmpty(base64)) {
            race.setBitmap(Utils.base64ToBitmap(base64));
        }*/
            race.setDistance(jsonObject.get("distance").getAsInt());
            race.setPlace(jsonObject.get("place").getAsString());
            race.setDate_time_init(jsonObject.get("date_time_init").getAsString());
            if (!jsonObject.get("web").isJsonNull()){
                race.setWeb(jsonObject.get("web").getAsString());
            }
        /*race.setNum_reviews(jsonObject.get("num_reviews").getAsInt());
        race.setTotal_scores(jsonObject.get("total_scores").getAsInt());
        race.setRating(jsonObject.get("rating").getAsDouble());*/
            race.setFavorite(!jsonObject.get("isFavorite").isJsonNull());
            race.setFinished(jsonObject.get("isFinished").getAsInt() == 1 ? true : false);
            if (!jsonObject.get("user").isJsonNull()) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer());
                Gson gson = gsonBuilder.create();
                User user = gson.fromJson(jsonObject.get("user").getAsString(), User.class);
                race.setUser(user);
            }
        }catch (Exception e){
            FirebaseCrash.report(e);
            e.printStackTrace();
        }


        return race;
    }
}
