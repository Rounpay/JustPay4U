package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCreate {
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("websiteName")
    @Expose
    public String websiteName;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("isRealAPI")
    @Expose
    public boolean isRealAPI;
    @SerializedName("commRate")
    @Expose
    public double commRate;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("outletName")
    @Expose
    public String outletName;
    @SerializedName("emailID")
    @Expose
    public String emailID;
    @SerializedName("roleID")
    @Expose
    public int roleID;
    /* @SerializedName("role")
     @Expose
     public String role;*/
    @SerializedName("slabID")
    @Expose
    public int slabID;
    @SerializedName("isGSTApplicable")
    @Expose
    public boolean isGSTApplicable;
    @SerializedName("isTDSApplicable")
    @Expose
    public boolean isTDSApplicable;
    @SerializedName("isVirtual")
    @Expose
    public boolean isVirtual;
    @SerializedName("isWebsite")
    @Expose
    public boolean isWebsite;
    @SerializedName("isAdminDefined")
    @Expose
    public boolean isAdminDefined;
    @SerializedName("pincode")
    @Expose
    public String pincode;
    String referralID; String whatsAppNumber;
    public UserCreate(String address, String password, String websiteName, String token, boolean isRealAPI, double commRate, String mobileNo, String name, String outletName, String emailID, int roleID, int slabID, boolean isGSTApplicable, boolean isTDSApplicable, boolean isVirtual, boolean isWebsite, boolean isAdminDefined, String pincode) {
        this.address = address;
        this.password = password;
        this.websiteName = websiteName;
        this.token = token;
        this.isRealAPI = isRealAPI;
        this.commRate = commRate;
        this.mobileNo = mobileNo;
        this.name = name;
        this.outletName = outletName;
        this.emailID = emailID;
        this.roleID = roleID;
        /* this.role = role;*/
        this.slabID = slabID;
        this.isGSTApplicable = isGSTApplicable;
        this.isTDSApplicable = isTDSApplicable;
        this.isVirtual = isVirtual;
        this.isWebsite = isWebsite;
        this.isAdminDefined = isAdminDefined;
        this.pincode = pincode;
    }

    public UserCreate(String address, String password, String referralID, String mobileNo, String whatsAppNumber, String name,
                      String emailID) {
        this.address = address;
        this.password = password;
        this.referralID = referralID;
        this.mobileNo = mobileNo;
        this.whatsAppNumber = whatsAppNumber;
        this.name = name;
        this.emailID = emailID;

    }
}
