package com.mel.seekraces.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mel.seekraces.entities.City;
import com.mel.seekraces.entities.Country;
import com.mel.seekraces.sqlite.DBOperations;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by void on 07/02/2017.
 */

public class RetrieveCountriesCitiesSqlite extends AsyncTask<Void, Void, List<Country>> {
    private Context context;

    public RetrieveCountriesCitiesSqlite(Context context) {
        this.context = context;
    }

    @Override
    protected List<Country> doInBackground(Void... params) {
        List<Country> countries = new ArrayList<>();
        try {

            DBOperations dbOperations = DBOperations.getInstance(context);
            countries=dbOperations.getCountries();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return countries;
    }
}
