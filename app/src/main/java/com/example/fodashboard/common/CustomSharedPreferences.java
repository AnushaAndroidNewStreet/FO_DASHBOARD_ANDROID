package com.example.fodashboard.common;

import android.content.Context;
import android.content.SharedPreferences;




public class CustomSharedPreferences {
    public static final String share_db_preferenced_db = "share_db_preferenced_db";
    public final static String SIMPLE_NULL = "";
    public final static boolean SIMPLE_FALSE = false;


    public static void saveStringData(Context context, String data, SP_KEY spKey) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(share_db_preferenced_db, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(spKey.name(), data);
        editor.commit();
    }

    public static String getStringData(Context context, SP_KEY spKey) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(share_db_preferenced_db, Context.MODE_PRIVATE);
        String sourceId = sharedPreferences.getString(spKey.name(), SIMPLE_NULL);
        return sourceId;
    }

    //newly added
    public static void saveboolenData(Context context, boolean status, SP_KEY spKey) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(share_db_preferenced_db, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(spKey.name(), status);
        editor.commit();
    }

    public static boolean getbooleanData(Context context, SP_KEY spKey) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(share_db_preferenced_db, Context.MODE_PRIVATE);
        boolean status = sharedPreferences.getBoolean(spKey.name(), SIMPLE_FALSE);
        return status;
    }

    public static void clear(Context context, SP_KEY spKey) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(share_db_preferenced_db, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(spKey.name());
        editor.commit();

    }

    public enum SP_KEY {
        PRODUCT_NAME,


    }

}