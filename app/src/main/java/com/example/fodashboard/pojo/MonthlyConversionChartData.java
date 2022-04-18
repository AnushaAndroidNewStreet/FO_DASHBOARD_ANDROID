package com.example.fodashboard.pojo;

import java.util.ArrayList;

public class MonthlyConversionChartData {
    int conversionGrowthPercentage;
    Double conversionVsPercentage;
    Double disburseAmount;
    int disburseCount;
    String filter;
    int leadCount;
    String prediction;
    int target;
    ArrayList<MonthlyCOnversionRatio> conversionRatio = new ArrayList<MonthlyCOnversionRatio>();

    public int getConversionGrowthPercentage() {
        return conversionGrowthPercentage;
    }

    public void setConversionGrowthPercentage(int conversionGrowthPercentage) {
        this.conversionGrowthPercentage = conversionGrowthPercentage;
    }

    public Double getConversionVsPercentage() {
        return conversionVsPercentage;
    }

    public void setConversionVsPercentage(Double conversionVsPercentage) {
        this.conversionVsPercentage = conversionVsPercentage;
    }

    public Double getDisburseAmount() {
        return disburseAmount;
    }

    public void setDisburseAmount(Double disburseAmount) {
        this.disburseAmount = disburseAmount;
    }

    public int getDisburseCount() {
        return disburseCount;
    }

    public void setDisburseCount(int disburseCount) {
        this.disburseCount = disburseCount;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public int getLeadCount() {
        return leadCount;
    }

    public void setLeadCount(int leadCount) {
        this.leadCount = leadCount;
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

    public ArrayList<MonthlyCOnversionRatio> getConversionRatio() {
        return conversionRatio;
    }

    public void setConversionRatio(ArrayList<MonthlyCOnversionRatio> conversionRatio) {
        this.conversionRatio = conversionRatio;
    }
}
