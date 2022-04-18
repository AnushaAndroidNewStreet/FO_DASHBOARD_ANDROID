package com.example.fodashboard.common;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

public class MyApplication extends Application {
    MyApplication application;


    public void ConnectionParam(JSONObject saveObj, JSONObject payload, String receiver, String structure_value, String sender, String password) throws JSONException, JSONException {
        //newly added
        application = (MyApplication) getApplicationContext();



        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ID", receiver);

        //end


        /*try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            saveObj.addProperty("versionnumber",version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }*/
        saveObj.put("Payload", payload);



    }
}








