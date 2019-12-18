package com.alitersolutions.evolveyork.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;



public class SharedPreferenceUtil {

    private static String MY_PREFS_NAME = "evolveyork";

    public static void storeStringValue(Context context, String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void storeBooleanValue(Context context, String key, boolean value){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void storeIntValue(Context context, String key, int value){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static String getStringValue(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static int getIntValue(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }

    public static String getStringValue(Context context, String key, String defaultVal){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(key, defaultVal);
    }
    public static boolean getBoolen(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }
}
