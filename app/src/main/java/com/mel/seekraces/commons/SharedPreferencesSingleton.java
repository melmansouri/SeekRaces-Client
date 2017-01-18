package com.mel.seekraces.commons;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by moha on 17/01/17.
 */

public class SharedPreferencesSingleton {
    private static SharedPreferencesSingleton instance;
    private SharedPreferences sharedPreferences;

    private SharedPreferencesSingleton(Context context){
        sharedPreferences=context.getSharedPreferences(Constantes.FILE_SP,Context.MODE_PRIVATE);
    }

    public static SharedPreferencesSingleton getInstance(Context context){
        if (instance==null){
            instance=new SharedPreferencesSingleton(context);
        }
        return instance;
    }


    public  void saveStringSP(String key,String value) {
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(key, value);
        ed.commit();
    }

    public String getStringSP(String key) {
        String value="";
        if (sharedPreferences.contains(key)){
            value=sharedPreferences.getString(key,null);
        }
        return value;
    }

    public void removeValueSP(String key) {
        if(sharedPreferences.contains(key)){
            return;
        }
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.remove(key);
        ed.commit();
    }

}
