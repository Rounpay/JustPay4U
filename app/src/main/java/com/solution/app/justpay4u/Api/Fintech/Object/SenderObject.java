package com.solution.app.justpay4u.Api.Fintech.Object;

public class SenderObject {
    private String mobileNo;
    private String name, sid;
    private String lastName;
    private String pincode;
    private String address;
    private String otp;
    private String dob;

    public SenderObject(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public SenderObject(String mobileNo, String name, String lastName, String pincode, String address, String otp, String dob) {
        this.mobileNo = mobileNo;
        this.name = name;
        this.lastName = lastName;
        this.pincode = pincode;
        this.address = address;
        this.otp = otp;
        this.dob = dob;
    }

    public SenderObject(String mobileNo, String otp, String sid) {
        this.mobileNo = mobileNo;
        this.otp = otp;
        this.sid = sid;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }


}
