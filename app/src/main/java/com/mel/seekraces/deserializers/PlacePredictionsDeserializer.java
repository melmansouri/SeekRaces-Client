package com.mel.seekraces.deserializers;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mel.seekraces.entities.PlacePredictions;

import java.lang.reflect.Type;
import java.util.ArrayList;

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
            String tmp=jObject.get("description").getAsString();
            String description = "";
            if (!TextUtils.isEmpty(tmp)) {
                int indexFinal = tmp.indexOf(',');

                description = tmp.substring(0, indexFinal);
            }
            lugares.add(description);
        }
        placePredictions.setPlaces(lugares);
        placePredictions.setStatus(jsonObject.get("status").getAsString());
        return placePredictions;
    }
}
