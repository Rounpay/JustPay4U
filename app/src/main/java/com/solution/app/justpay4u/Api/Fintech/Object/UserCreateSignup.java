package com.solution.app.justpay4u.Api.Fintech.Object;

/**
 * Created by Vishnu Agarwal on 20,December,2019
 */
public class UserCreateSignup {

    int referalID;
    String address;
    String mobileNo;
    String name;
    String outletName;
    String emailID;
    String referalIDStr;
    int roleID;
    String pincode, legs;

    String cityID,stateID,AreaID;

    String AddharNo;

    public UserCreateSignup(int referalID, String address, String mobileNo, String name, String outletName,
                            String emailID, int roleID, String pincode, String legs) {
        this.referalID = referalID;
        this.address = address;
        this.mobileNo = mobileNo;
        this.name = name;
        this.outletName = outletName;
        this.emailID = emailID;
        this.roleID = roleID;
        this.pincode = pincode;
        this.legs = legs;
    }

    public UserCreateSignup(int referalID, String address, String mobileNo,String AddharNo, String name, String outletName, String emailID, String referalIDStr, int roleID, String pincode, String legs) {
        this.referalID = referalID;
        this.address = address;
        this.mobileNo = mobileNo;
        this.AddharNo=AddharNo;
        this.name = name;
        this.outletName = outletName;
        this.emailID = emailID;
        this.referalIDStr = referalIDStr;
        this.roleID = roleID;
        this.pincode = pincode;
        this.legs = legs;
    }

    public UserCreateSignup(int referalID, String address, String mobileNo,String AddharNo, String name, String outletName, String emailID, String referalIDStr, int roleID, String pincode, String legs, String cityID, String stateID, String AreaID) {
        this.referalID = referalID;
        this.address = address;
        this.mobileNo = mobileNo;
        this.AddharNo = AddharNo;
        this.name = name;
        this.outletName = outletName;
        this.emailID = emailID;
        this.referalIDStr = referalIDStr;
        this.roleID = roleID;
        this.pincode = pincode;
        this.legs = legs;
        this.cityID = cityID;
        this.stateID = stateID;
        this.AreaID = AreaID;
    }
}
