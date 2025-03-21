package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditUser {


    @SerializedName("profilePic")
    @Expose
    public String profilePic;
    @SerializedName("branchName")
    @Expose
    public String branchName;
    @SerializedName("commRate")
    @Expose
    private int commRate;
    @SerializedName("aadhar")
    @Expose
    private Object aadhar;
    @SerializedName("pan")
    @Expose
    private Object pan;
    @SerializedName("gstin")
    @Expose
    private Object gstin;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("userID")
    @Expose
    private int userID;
    @SerializedName("mobileNo")
    @Expose
    private Object mobileNo;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("outletName")
    @Expose
    private Object outletName;
    @SerializedName("emailID")
    @Expose
    private Object emailID;
    @SerializedName("isGSTApplicable")
    @Expose
    private boolean isGSTApplicable;
    @SerializedName("isTDSApplicable")
    @Expose
    private boolean isTDSApplicable;
    @SerializedName("isCCFGstApplicable")
    @Expose
    private boolean isCCFGstApplicable;
    @SerializedName("pincode")
    @Expose
    private Object pincode;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("shoptype")
    @Expose
    private String shoptype;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("poupulation")
    @Expose
    private String poupulation;
    @SerializedName("locationType")
    @Expose
    private String locationType;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("alternateMobile")
    @Expose
    private String alternateMobile;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("accountName")
    @Expose
    private String accountName;

   /* @SerializedName("commRate")
    @Expose
    public int commRate;
    @SerializedName("profilePic")
    @Expose
    public String profilePic;
    @SerializedName("aadhar")
    @Expose
    public String aadhar;
    @SerializedName("pan")
    @Expose
    public String pan;
    @SerializedName("gstin")
    @Expose
    public String gstin;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("userID")
    @Expose
    public int userID;
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
    @SerializedName("isGSTApplicable")
    @Expose
    public boolean isGSTApplicable;
    @SerializedName("isTDSApplicable")
    @Expose
    public boolean isTDSApplicable;
    @SerializedName("isCCFGstApplicable")
    @Expose
    public boolean isCCFGstApplicable;
    @SerializedName("pincode")
    @Expose
    public String pincode;*/

    public EditUser(int commRate, String profilePic, String aadhar, String pan, String gstin, String address, int userID,
                    String mobileNo, String name, String outletName, String emailID, boolean isGSTApplicable,
                    boolean isTDSApplicable, boolean isCCFGstApplicable, String pincode, String dob, String shoptype,
                    String qualification, String poupulation, String locationType, String landmark, String alternateMobile,
                    String bankName, String ifsc, String accountNumber, String accountName, String branchName) {
        this.commRate = commRate;
        this.profilePic = profilePic;
        this.aadhar = aadhar;
        this.pan = pan;
        this.gstin = gstin;
        this.address = address;
        this.userID = userID;
        this.mobileNo = mobileNo;
        this.name = name;
        this.outletName = outletName;
        this.emailID = emailID;
        this.isGSTApplicable = isGSTApplicable;
        this.isTDSApplicable = isTDSApplicable;
        this.isCCFGstApplicable = isCCFGstApplicable;
        this.pincode = pincode;
        this.dob = dob;
        this.shoptype = shoptype;
        this.qualification = qualification;
        this.poupulation = poupulation;
        this.locationType = locationType;
        this.poupulation = poupulation;
        this.landmark = landmark;
        this.alternateMobile = alternateMobile;
        this.bankName = bankName;
        this.ifsc = ifsc;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.branchName = branchName;
    }


    public EditUser(String bankName, String ifsc, String accountNumber, String accountName, String branchName) {
        this.bankName = bankName;
        this.ifsc = ifsc;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.branchName = branchName;
    }
}
