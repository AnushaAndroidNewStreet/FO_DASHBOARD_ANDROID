package com.example.fodashboard.pojo;

import java.util.ArrayList;

public class MonthlyConversionChartModel extends BaseModel {
    ArrayList<MonthlyConversionChartData> Data = new ArrayList<MonthlyConversionChartData>();

    public ArrayList<MonthlyConversionChartData> getData() {
        return Data;
    }

    public void setData(ArrayList<MonthlyConversionChartData> data) {
        Data = data;
    }
}
