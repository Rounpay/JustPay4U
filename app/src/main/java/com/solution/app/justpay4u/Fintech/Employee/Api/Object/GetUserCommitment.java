package com.solution.app.justpay4u.Fintech.Employee.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserCommitment {
    @SerializedName("commitmentID")
    @Expose
    public int commitmentID;
    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("userID")
    @Expose
    public int userID;
    @SerializedName("userMobile")
    @Expose
    public String userMobile;
    @SerializedName("userName")
    @Expose
    public String userName;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("commitment")
    @Expose
    public int commitment;
    @SerializedName("achieved")
    @Expose
    public double achieved;
    @SerializedName("balance")
    @Expose
    public double balance;
    @SerializedName("empID")
    @Expose
    public int empID;
    @SerializedName("loginTypeID")
    @Expose
    public int loginTypeID;
    @SerializedName("entryDate")
    @Expose
    public String entryDate;
    @SerializedName("longitute")
    @Expose
    public String longitute;
    @SerializedName("latitude")
    @Expose
    public String latitude;

    public int getCommitmentID() {
        return commitmentID;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getRole() {
        return role;
    }

    public int getCommitment() {
        return commitment;
    }

    public double getAchieved() {
        return achieved;
    }

    public int getEmpID() {
        return empID;
    }

    public double getBalance() {
        return balance;
    }

    public int getLoginTypeID() {
        return loginTypeID;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getLongitute() {
        return longitute;
    }

    public String getLatitude() {
        return latitude;
    }
}
