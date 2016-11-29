package com.example.katesudal.participantgroupmanagement;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by katesuda.l on 29/11/2559.
 */

public class PreferencesService {
    public static SharedPreferences settingPreferences;
    public static SharedPreferences.Editor editor;

    public static void savePreferences(String key, Object object, Context context){
        settingPreferences = context.getSharedPreferences("project",0);
        editor=settingPreferences.edit();
        String data = new Gson().toJson(object);
        editor.putString(key,data);
        editor.apply();
    }

    public static Object getPreferences(String key, Class c, Context context){
        settingPreferences = context.getSharedPreferences("project",0);
        String data = settingPreferences.getString(key,null);
        return new Gson().fromJson(data,c);
    }
}
