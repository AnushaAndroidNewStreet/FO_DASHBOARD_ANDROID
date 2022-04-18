package com.example.fodashboard.pojo;

import java.util.ArrayList;

public class MemberDetailsModel  extends BaseModel{
    ArrayList<MemberDetailData> Data = new ArrayList<MemberDetailData>();

    public ArrayList<MemberDetailData> getData() {
        return Data;
    }

    public void setData(ArrayList<MemberDetailData> data) {
        Data = data;
    }
}
