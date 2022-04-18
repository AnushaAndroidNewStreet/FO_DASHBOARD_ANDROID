package com.example.fodashboard.pojo;

import java.util.ArrayList;

public class VerificationAuditModel  extends BaseModel{
    ArrayList<VerificationAuditData> Data = new ArrayList<VerificationAuditData>();

    public ArrayList<VerificationAuditData> getData() {
        return Data;
    }

    public void setData(ArrayList<VerificationAuditData> data) {
        Data = data;
    }
}
