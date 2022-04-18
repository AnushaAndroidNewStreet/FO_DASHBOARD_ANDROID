package com.example.fodashboard.pojo;

import java.util.ArrayList;

public class VerificationAuditData {
    String title;
    ArrayList<VerificationAuditProspects> prospects = new ArrayList<VerificationAuditProspects>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<VerificationAuditProspects> getProspects() {
        return prospects;
    }

    public void setProspects(ArrayList<VerificationAuditProspects> prospects) {
        this.prospects = prospects;
    }
}
