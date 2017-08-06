package com.musiconlinelisten.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.musiconlinelisten.MainApplication;

import java.util.UUID;

/**
 * Created by alexd on 05/08/2017.
 */

public class Preferences {
    public static void saveString(String key, String value){
        SharedPreferences.Editor editor = MainApplication.getInstance().getSharedPreferences("mama", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveInt(String key, int value){
        SharedPreferences.Editor editor = MainApplication.getInstance().getSharedPreferences("mama", Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static String getStringValue(String key){
        SharedPreferences prefs = MainApplication.getInstance().getSharedPreferences("mama", Context.MODE_PRIVATE);
        String restoredText = prefs.getString(key, null);
        if (restoredText == null) {
            restoredText = "";
        }

        return restoredText;
    }

    public static Integer getIntValue(String key){
        SharedPreferences prefs = MainApplication.getInstance().getSharedPreferences("mama", Context.MODE_PRIVATE);
        int value = prefs.getInt(key, 0);

        return value;
    }

    public static String id(Context context) {
        String ID = null;

        if (Preferences.getStringValue("id") == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    "mama", Context.MODE_PRIVATE);
            ID = sharedPrefs.getString("mama", null);


            if (ID == null) {
                ID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("mama", ID);
                editor.commit();
            }
        }
        return "&id="+ ID;
    }
}
