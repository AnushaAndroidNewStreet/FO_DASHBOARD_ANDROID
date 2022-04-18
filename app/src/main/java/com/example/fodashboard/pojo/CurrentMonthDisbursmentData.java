package com.example.fodashboard.pojo;

public class CurrentMonthDisbursmentData {
    Double disbursementAmt;
    int disbursementCount;
    String prediction;
    int target;

    public Double getDisbursementAmt() {
        return disbursementAmt;
    }

    public void setDisbursementAmt(Double disbursementAmt) {
        this.disbursementAmt = disbursementAmt;
    }

    public int getDisbursementCount() {
        return disbursementCount;
    }

    public void setDisbursementCount(int disbursementCount) {
        this.disbursementCount = disbursementCount;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}
