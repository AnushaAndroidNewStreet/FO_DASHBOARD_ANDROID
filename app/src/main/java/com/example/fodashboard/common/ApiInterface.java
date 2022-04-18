package com.example.fodashboard.common;


import com.example.fodashboard.pojo.CurrentMonthDisbursmentModel;
import com.example.fodashboard.pojo.PerforamnceSummary;
import com.example.fodashboard.pojo.PerformanceData;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.POST;

public interface ApiInterface {


    @POST("api/getDashboardDetails")
    Call<JsonObject> getCurrentDisbursmentData(@Body JsonObject resetPasswordInput);

    @POST("api/getDashboardDetails")
    Call<PerformanceData> getPerformanceSummary(@Body JsonObject resetPasswordInput);


}

