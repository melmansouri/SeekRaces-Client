package com.mel.seekraces.deserializers;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.entities.Event;
import com.mel.seekraces.entities.PlacePredictions;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by moha on 23/01/17.
 */

public class PlacePredictionsDeserializer implements JsonDeserializer<PlacePredictions>{
    @Override
    public PlacePredictions deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject=json.getAsJsonObject();
        PlacePredictions placePredictions=new PlacePredictions();
        JsonArray jsonArray=jsonObject.get("predictions").getAsJsonArray();
        ArrayList<String> lugares=new ArrayList<>();
        for (int i=0;i<jsonArray.size();i++){
            JsonObject jObject=jsonArray.get(i).getAsJsonObject();
            lugares.add(jObject.get("description").getAsString());
        }
        placePredictions.setPlaces(lugares);
        placePredictions.setStatus(jsonObject.get("status").getAsString());
        return placePredictions;
    }
}
