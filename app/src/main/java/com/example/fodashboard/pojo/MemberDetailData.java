package com.example.fodashboard.pojo;

import java.util.ArrayList;

public class MemberDetailData {
    String groupId;
    String  groupImage;
    String groupName;
    Double loanAmount;
    ArrayList<MembersArray> members = new ArrayList<MembersArray>();

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public ArrayList<MembersArray> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<MembersArray> members) {
        this.members = members;
    }
}
