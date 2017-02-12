package com.mel.seekraces.deserializers;

import android.text.TextUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.entities.Event;

import java.lang.reflect.Type;

/**
 * Created by moha on 23/01/17.
 */

public class EventDeserializer implements JsonDeserializer<Event>{
    @Override
    public Event deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject=json.getAsJsonObject();
        Event event=new Event();
        event.setId(jsonObject.get("id").getAsInt());
        event.setUser(jsonObject.get("user").getAsString());
        event.setUserName(jsonObject.get("userName").getAsString());
        event.setName(jsonObject.get("name").getAsString());
        event.setDescription(jsonObject.get("description").getAsString());
        String base64=jsonObject.get("imageBase64").getAsString();
        if (!TextUtils.isEmpty(base64)){
            event.setBitmap(Utils.base64ToBitmap(base64));
        }
        event.setDistance(jsonObject.get("distance").getAsInt());
        event.setPlace(jsonObject.get("place").getAsString());
        event.setDate_time_init(jsonObject.get("date_time_init").getAsString());
        event.setWeb(jsonObject.get("web").getAsString());
        event.setNum_reviews(jsonObject.get("num_reviews").getAsInt());
        event.setTotal_scores(jsonObject.get("total_scores").getAsInt());
        event.setRating(jsonObject.get("rating").getAsDouble());
        event.setFavorite(!jsonObject.get("isFavorite").isJsonNull());
        return event;
    }
}
