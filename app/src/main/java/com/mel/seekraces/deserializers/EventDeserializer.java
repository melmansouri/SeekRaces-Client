package com.mel.seekraces.deserializers;

import android.text.TextUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.entities.Race;

import java.lang.reflect.Type;

/**
 * Created by moha on 23/01/17.
 */

public class EventDeserializer implements JsonDeserializer<Race>{
    @Override
    public Race deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject=json.getAsJsonObject();
        Race race =new Race();
        race.setId(jsonObject.get("id").getAsInt());
        race.setUser(jsonObject.get("user").getAsString());
        race.setUserName(jsonObject.get("userName").getAsString());
        race.setName(jsonObject.get("name").getAsString());
        race.setDescription(jsonObject.get("description").getAsString());
        //race.setImageName(jsonObject.get("imageName").getAsString());
        String base64=jsonObject.get("imageBase64").getAsString();
        if (!TextUtils.isEmpty(base64)){
            race.setBitmap(Utils.base64ToBitmap(base64));
        }
        race.setDistance(jsonObject.get("distance").getAsInt());
        race.setPlace(jsonObject.get("place").getAsString());
        race.setDate_time_init(jsonObject.get("date_time_init").getAsString());
        race.setWeb(jsonObject.get("web").getAsString());
        /*race.setNum_reviews(jsonObject.get("num_reviews").getAsInt());
        race.setTotal_scores(jsonObject.get("total_scores").getAsInt());
        race.setRating(jsonObject.get("rating").getAsDouble());*/
        race.setFavorite(!jsonObject.get("isFavorite").isJsonNull());
        return race;
    }
}
