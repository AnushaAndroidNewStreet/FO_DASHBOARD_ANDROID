package com.example.fodashboard.model;

public class UserItem {

    private int userImage;
    private String userName;
    private String contactNo;
    private String amount;
    private String status;

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public int getUserImage() {
        return userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getStatus() {
        return status;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
