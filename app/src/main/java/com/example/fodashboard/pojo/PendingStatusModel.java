package com.example.fodashboard.pojo;

import java.util.ArrayList;

public class PendingStatusModel  extends BaseModel{
    ArrayList<PendingStatusData> Data = new ArrayList<PendingStatusData>();

    public ArrayList<PendingStatusData> getData() {
        return Data;
    }

    public void setData(ArrayList<PendingStatusData> data) {
        Data = data;
    }
}
