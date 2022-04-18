package com.example.fodashboard.pojo;

import java.util.ArrayList;

public class ProductFulfilmentData {
    ArrayList<ProductFuldilmentGroups> groups;
    String title;

    public ArrayList<ProductFuldilmentGroups> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<ProductFuldilmentGroups> groups) {
        this.groups = groups;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
