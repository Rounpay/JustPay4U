package com.solution.app.justpay4u.Fintech.Employee.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEmployees {

    @SerializedName("empID")
    @Expose
    public int empID;
    @SerializedName("empCode")
    @Expose
    public String empCode;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("emailID")
    @Expose
    public String emailID;
    @SerializedName("reportingTo")
    @Expose
    public int reportingTo;
    @SerializedName("reportingToName")
    @Expose
    public String reportingToName;
    @SerializedName("password")
    @Expose
    public String  password;
    @SerializedName("empRoleID")
    @Expose
    public int empRoleID;
    @SerializedName("empRole")
    @Expose
    public String empRole;
    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("pinCode")
    @Expose
    public int pinCode;
    @SerializedName("pan")
    @Expose
    public String pan;
    @SerializedName("aadhar")
    @Expose
    public String aadhar;
    @SerializedName("isActive")
    @Expose
    public boolean isActive;
    @SerializedName("isOtp")
    @Expose
    public boolean isOtp;
    @SerializedName("entryBy")
    @Expose
    public int entryBy;
    @SerializedName("referralID")
    @Expose
    public int referralID;
    @SerializedName("referralBy")
    @Expose
    public String referralBy;

    public int getEmpID() {
        return empID;
    }

    public String getEmpCode() {
        return empCode;
    }

    public String getName() {
        return name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public int getReportingTo() {
        return reportingTo;
    }

    public String getReportingToName() {
        return reportingToName;
    }

    public String getPassword() {
        return password;
    }

    public int getEmpRoleID() {
        return empRoleID;
    }

    public String getEmpRole() {
        return empRole;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getAddress() {
        return address != null && !address.isEmpty() ? address : "";
    }

    public String getState() {
        return state != null && !state.isEmpty() ? state : "";
    }

    public String getCity() {
        return city != null && !city.isEmpty() ? city : "";
    }

    public int getPinCode() {
        return pinCode;
    }

    public String getPan() {
        return pan != null && !pan.isEmpty() ? pan : "";
    }

    public String getAadhar() {
        return aadhar != null && !aadhar.isEmpty() ? aadhar : "";
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isOtp() {
        return isOtp;
    }

    public int getEntryBy() {
        return entryBy;
    }

    public int getReferralID() {
        return referralID;
    }

    public String getReferralBy() {
        return referralBy;
    }
}
