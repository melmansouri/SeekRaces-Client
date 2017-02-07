package com.mel.seekraces.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mel.seekraces.entities.City;
import com.mel.seekraces.entities.Country;
import com.mel.seekraces.sqlite.DBManager;
import com.mel.seekraces.sqlite.DBOperations;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by void on 07/02/2017.
 */

public class SaveCountriesCitiesSqlite extends AsyncTask<String, Void, Void> {
    private Context context;

    public SaveCountriesCitiesSqlite(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        String jsonCountries = params[0];
        String jsonCities = params[1];
        try {
            if (!TextUtils.isEmpty(jsonCountries) && !TextUtils.isEmpty(jsonCities)){

                DBOperations dbOperations = DBOperations.getInstance(context);
                List<Country> tmp= dbOperations.getCountries();
                List<Country> countries = getListCountriesFromJson(jsonCountries);
                List<City> cities = getListCitiesFromJson(jsonCities);
                if (tmp==null || tmp.size()==0) {
                    for (Country country : countries) {
                        if(!dbOperations.insertCountry(country)){
                            Log.d("Countries","Pais no insertado ");
                        }
                    }

                    for (City city : cities) {
                        if(!dbOperations.insertCity(city)){
                            Log.d("Cities","Ciudad no insertada");
                        }
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Country> getListCountriesFromJson(String json) {
        List<Country> countries;

        Type type = new TypeToken<ArrayList<Country>>() {
        }.getType();
        countries = new Gson().fromJson(json, type);

        return countries;
    }

    private List<City> getListCitiesFromJson(String json) {
        List<City> cities;

        Type type = new TypeToken<ArrayList<City>>() {
        }.getType();
        cities = new Gson().fromJson(json, type);

        return cities;
    }
}
