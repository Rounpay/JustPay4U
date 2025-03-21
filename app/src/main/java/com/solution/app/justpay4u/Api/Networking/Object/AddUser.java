package com.solution.app.justpay4u.Api.Networking.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 19/07/2022.
 */
public class AddUser {
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("emailID")
    @Expose
    public String emailID;
    @SerializedName("referralID")
    @Expose
    String referralID;
    @SerializedName("whatsAppNumber")
    @Expose
    String whatsAppNumber;
    @SerializedName("legs")
    @Expose
    String legs;


    public AddUser(String address, String password, String referralID, String mobileNo, String whatsAppNumber, String name,
                   String emailID, String legs) {
        this.address = address;
        this.password = password;
        this.referralID = referralID;
        this.mobileNo = mobileNo;
        this.whatsAppNumber = whatsAppNumber;
        this.name = name;
        this.emailID = emailID;
        this.legs = legs;

    }
}
