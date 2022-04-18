package com.example.fodashboard.pojo;

import java.util.ArrayList;

public class PendingStatusData {
    int customerCount;
    ArrayList<PendingStatusSummary> summaryStatus = new ArrayList<PendingStatusSummary>();

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public ArrayList<PendingStatusSummary> getSummaryStatus() {
        return summaryStatus;
    }

    public void setSummaryStatus(ArrayList<PendingStatusSummary> summaryStatus) {
        this.summaryStatus = summaryStatus;
    }
}
