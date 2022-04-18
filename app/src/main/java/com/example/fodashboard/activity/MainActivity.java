package com.example.fodashboard.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.fodashboard.R;

import com.example.fodashboard.common.ApiClient;
import com.example.fodashboard.common.ApiInterface;
import com.example.fodashboard.common.AppConstants;
import com.example.fodashboard.common.CustomSharedPreferences;
import com.example.fodashboard.common.MyApplication;
import com.example.fodashboard.fragment.DashboardFragment;
import com.example.fodashboard.pojo.CurrentMonthDisbursmentData;
import com.example.fodashboard.pojo.CurrentMonthDisbursmentModel;
import com.example.fodashboard.pojo.PerforamnceSummary;
import com.example.fodashboard.pojo.PerformanceData;
import com.example.fodashboard.pojo.TodayDisbursmentData;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static Context contextOfApplication;
    public static final String TAG_TOKEN_TYPE_VALUE = "tokenTypeValue";
    ApiInterface apiInterface;
    ArrayList<PerforamnceSummary> summary = new ArrayList<PerforamnceSummary>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment homeFragment = new DashboardFragment();
        openFragment(homeFragment);


/*
        try {
            getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/




    }

    public void openFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void getData() throws JSONException {

        apiInterface = ApiClient.getInstance().getClient().create(ApiInterface.class);
        final JsonObject mainObj = new JsonObject();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        String dateToString = dateFormat.format(cal.getTime());


        JsonObject payload = new JsonObject();
        payload.addProperty("productType", "JLG");
        payload.addProperty("userId", "S0334");


        payload.addProperty("from", dateToString);
        payload.addProperty("to", dateToString);
        payload.addProperty("type", AppConstants.DISBURSMENT_TODAY);
        mainObj.add("Payload", payload);
        Call<JsonObject> call1 = apiInterface.getCurrentDisbursmentData(mainObj);
        call1.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                       JsonObject jsonObject =  response.body();

                       JsonObject data = (JsonObject) jsonObject.get("Data");
                        TodayDisbursmentData current_data = new TodayDisbursmentData();
                        current_data.setDisbursementAmt(data.get("disbursementAmt").getAsDouble());
                        current_data.setDisbursementCount(data.get("disbursementCount").getAsInt());

                        getMonthData();






                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }



           /* @Override
            public void onFailure(Call<ResetPasswordOutput> call, Throwable t) {
                Log.e(TAG, "Login API Failed ");
                Intent i = new Intent(getApplicationContext(), ServerErrorConnectionActivity.class);
                startActivity(i);
                //Toast.makeText(getApplicationContext(), "Failed+++++++++", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }*/



        });


    }

    private void getMonthData() {

        apiInterface = ApiClient.getInstance().getClient().create(ApiInterface.class);
        final JsonObject mainObj = new JsonObject();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();

        String dateToString = dateFormat.format(cal.getTime());

        cal.set(Calendar.DAY_OF_MONTH, 1);
        String first_date_of_month = dateFormat.format(cal.getTime());

        JsonObject payload = new JsonObject();
        payload.addProperty("productType", "JLG");
        payload.addProperty("userId", "S0334");
        payload.addProperty("from", first_date_of_month);
        payload.addProperty("to", dateToString);
        payload.addProperty("type", AppConstants.DISBURSMENT_MONTH);
        mainObj.add("Payload", payload);
        Call<JsonObject> call1 = apiInterface.getCurrentDisbursmentData(mainObj);
        call1.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                        JsonObject jsonObject =  response.body();

                        JsonObject data = (JsonObject) jsonObject.get("Data");
                        CurrentMonthDisbursmentData current_data = new CurrentMonthDisbursmentData();
                        current_data.setDisbursementAmt(data.get("disbursementAmt").getAsDouble());
                        current_data.setDisbursementCount(data.get("disbursementCount").getAsInt());
                        current_data.setPrediction(data.get("prediction").toString());
                        current_data.setTarget(data.get("target").getAsInt());
                        try {
                            getTopPerfomerData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }



           /* @Override
            public void onFailure(Call<ResetPasswordOutput> call, Throwable t) {
                Log.e(TAG, "Login API Failed ");
                Intent i = new Intent(getApplicationContext(), ServerErrorConnectionActivity.class);
                startActivity(i);
                //Toast.makeText(getApplicationContext(), "Failed+++++++++", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }*/



        });


    }

    private void getTopPerfomerData() throws JSONException {

        apiInterface = ApiClient.getInstance().getClient().create(ApiInterface.class);
        final JsonObject mainObj = new JsonObject();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();

        String dateToString = dateFormat.format(cal.getTime());

        cal.set(Calendar.DAY_OF_MONTH, 1);
        String first_date_of_month = dateFormat.format(cal.getTime());

        JsonObject payload = new JsonObject();
        payload.addProperty("productType", "JLG");
        payload.addProperty("userId", "S0334");
        payload.addProperty("from", first_date_of_month);
        payload.addProperty("to", dateToString);
        payload.addProperty("type", AppConstants.PERFOMENCE_REPORT);
        mainObj.add("Payload", payload);


        Call<JsonObject> call1 = apiInterface.getCurrentDisbursmentData(mainObj);
        call1.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        JsonObject gson = new JsonParser().parse(response.body().toString()).getAsJsonObject();

                        try {
                            JSONObject jo2 = new JSONObject(gson.toString());
                            JSONObject object = (JSONObject) jo2.get("Data");
                            JSONArray array = (JSONArray)  object.getJSONArray("performanceSummary");
                            for(int i = 0; i < array.length(); i ++) {
                                PerforamnceSummary sum = new PerforamnceSummary();
                                sum.setAmount(Double.valueOf(array.getJSONObject(i).get("amount").toString()));
                                sum.setCount(Integer.parseInt(array.getJSONObject(i).get("count").toString()));
                                sum.setFieldOfficerName(array.getJSONObject(i).get("fieldOfficerName").toString());
                                sum.setLocaleName(array.getJSONObject(i).get("localeName").toString());
                                summary.add(sum);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }






                      /* JsonObject object=   response.body().getAsJsonObject("Data");
                       JsonArray array = object.getAsJsonArray("performanceSummary");

                       for(int i = 0; i<=array.size(); i++){
                           PerforamnceSummary summary = new PerforamnceSummary();

                       }*/







                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }



           /* @Override
            public void onFailure(Call<ResetPasswordOutput> call, Throwable t) {
                Log.e(TAG, "Login API Failed ");
                Intent i = new Intent(getApplicationContext(), ServerErrorConnectionActivity.class);
                startActivity(i);
                //Toast.makeText(getApplicationContext(), "Failed+++++++++", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }*/



        });

    }
}