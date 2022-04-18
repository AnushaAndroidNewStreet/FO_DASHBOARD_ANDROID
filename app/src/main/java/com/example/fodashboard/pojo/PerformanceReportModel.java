package com.example.fodashboard.pojo;

import java.util.ArrayList;

public class PerformanceReportModel extends BaseModel{
    ArrayList<PerformanceData> Data = new ArrayList<PerformanceData>();

    public ArrayList<PerformanceData> getData() {
        return Data;
    }

    public void setData(ArrayList<PerformanceData> data) {
        Data = data;
    }
}
