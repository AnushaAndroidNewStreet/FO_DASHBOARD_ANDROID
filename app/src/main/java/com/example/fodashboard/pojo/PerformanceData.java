package com.example.fodashboard.pojo;

import java.util.ArrayList;

public class PerformanceData {
    String dashboardProspectDetails;
    String loanAmount;
    String prospectID;
    ArrayList<PerforamnceSummary> performanceSummary = new ArrayList<PerforamnceSummary>();

    public String getDashboardProspectDetails() {
        return dashboardProspectDetails;
    }

    public void setDashboardProspectDetails(String dashboardProspectDetails) {
        this.dashboardProspectDetails = dashboardProspectDetails;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getProspectID() {
        return prospectID;
    }

    public void setProspectID(String prospectID) {
        this.prospectID = prospectID;
    }

    public ArrayList<PerforamnceSummary> getPerformanceSummary() {
        return performanceSummary;
    }

    public void setPerformanceSummary(ArrayList<PerforamnceSummary> performanceSummary) {
        this.performanceSummary = performanceSummary;
    }
}
