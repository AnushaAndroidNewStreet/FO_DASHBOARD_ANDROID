package com.example.fodashboard.model;

public class BankItem {

    private String id;
    private String backName;
    private String productName;
    private String sortName;
    private int bankImage;
    private boolean isSelected;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getBackName() {
        return backName;
    }

    public void setBackName(String backName) {
        this.backName = backName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getBankImage() {
        return bankImage;
    }

    public void setBankImage(int bankImage) {
        this.bankImage = bankImage;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
}
