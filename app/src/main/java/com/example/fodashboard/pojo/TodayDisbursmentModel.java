package com.example.fodashboard.pojo;

import java.util.ArrayList;

public class TodayDisbursmentModel  extends BaseModel{
    ArrayList<TodayDisbursmentData> Data = new ArrayList<TodayDisbursmentData>();


    public ArrayList<TodayDisbursmentData> getData() {
        return Data;
    }

    public void setData(ArrayList<TodayDisbursmentData> data) {
        Data = data;
    }
}
