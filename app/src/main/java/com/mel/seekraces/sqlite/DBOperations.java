package com.mel.seekraces.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.design.widget.TabLayout;

import com.mel.seekraces.entities.City;
import com.mel.seekraces.entities.Country;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by void on 07/02/2017.
 */

public class DBOperations {

    private static DBManager dbManager;

    private static DBOperations instance = new DBOperations();

    private DBOperations() {
    }

    public static DBOperations getInstance(Context contexto) {
        if (dbManager == null) {
            dbManager = new DBManager(contexto);
        }
        return instance;
    }

    public boolean insertCountry(Country country) {
        long id;
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TablesDB.TableCountries.CODE, country.getCode());
        contentValues.put(TablesDB.TableCountries.NAME, country.getName());
        id = db.insertOrThrow(TablesDB.TableCountries.TABLE_NAME, null, contentValues);
        db.close();
        return id > -1;
    }

    public boolean insertCity(City city) {
        long id;
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TablesDB.TableCities.NAME, city.getName());
        contentValues.put(TablesDB.TableCities.COUNTRY_CODE, city.getCountryCode());
        id = db.insertOrThrow(TablesDB.TableCities.TABLE_NAME, null, contentValues);
        db.close();
        return id > -1;
    }

    public List<Country> getCountries(){
        List<Country> countries=new ArrayList<>();
        SQLiteDatabase db=dbManager.getReadableDatabase();
        String query="select * from "+ TablesDB.TableCountries.TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                do {
                    Country country=new Country();
                    country.setCode(cursor.getString(cursor.getColumnIndex(TablesDB.TableCountries.CODE)));
                    country.setName(cursor.getString(cursor.getColumnIndex(TablesDB.TableCountries.NAME)));
                    List<City> cities=getCities(db,country.getCode());
                    country.setCities(cities);
                    countries.add(country);
                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();

        return countries;
    }

    public List<City> getCities(SQLiteDatabase db,String codeCountry){
        List<City> cities=new ArrayList<>();
        String query="select * from "+ TablesDB.TableCities.TABLE_NAME+" WHERE "+ TablesDB.TableCities.COUNTRY_CODE+" = '"+codeCountry+"'";
        Cursor cursor=db.rawQuery(query,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                do {
                    City city=new City();
                    city.setName(cursor.getString(cursor.getColumnIndex(TablesDB.TableCities.NAME)));
                    cities.add(city);
                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        return cities;
    }


}
