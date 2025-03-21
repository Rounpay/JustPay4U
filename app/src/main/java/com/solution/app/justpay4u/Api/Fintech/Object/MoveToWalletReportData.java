package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoveToWalletReportData {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("userId")
    @Expose
    public int userId;
    @SerializedName("userRoleId")
    @Expose
    public String userRoleId;
    @SerializedName("showGroupID")
    @Expose
    public int showGroupID;
    @SerializedName("amount")
    @Expose
    public double amount;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("remark")
    @Expose
    public String remark;
    @SerializedName("entryDate")
    @Expose
    public String entryDate;
    @SerializedName("approvalDate")
    @Expose
    public String approvalDate;
    @SerializedName("transMode")
    @Expose
    public String transMode;
    @SerializedName("charge")
    @Expose
    public double charge;
    @SerializedName("userName")
    @Expose
    public String userName;
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("bankName")
    @Expose
    public String bankName;
    @SerializedName("ifsc")
    @Expose
    public String ifsc;
    @SerializedName("accountHolder")
    @Expose
    public String accountHolder;
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;
    @SerializedName("fDate")
    @Expose
    public Object fDate;
    @SerializedName("tDate")
    @Expose
    public Object tDate;
    @SerializedName("groupID")
    @Expose
    public int groupID;
    @SerializedName("transactionId")
    @Expose
    public String transactionId;
    @SerializedName("tid")
    @Expose
    public String tid;
    @SerializedName("payIds")
    @Expose
    public Object payIds;
    @SerializedName("dt")
    @Expose
    public Object dt;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("pincode")
    @Expose
    public String pincode;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("bankRRN")
    @Expose
    public String bankRRN;
    @SerializedName("miniBankBalance")
    @Expose
    public double miniBankBalance;
    @SerializedName("miniBankCapping")
    @Expose
    public int miniBankCapping;
    @SerializedName("inRealTime")
    @Expose
    public boolean inRealTime;
    @SerializedName("loginID")
    @Expose
    public int loginID;
    @SerializedName("loginTypeID")
    @Expose
    public int loginTypeID;
    @SerializedName("userID")
    @Expose
    public int userID;
    @SerializedName("commonInt")
    @Expose
    public int commonInt;
    @SerializedName("commonInt2")
    @Expose
    public int commonInt2;
    @SerializedName("commonStr")
    @Expose
    public Object commonStr;
    @SerializedName("commonStr2")
    @Expose
    public Object commonStr2;
    @SerializedName("isListType")
    @Expose
    public boolean isListType;
    @SerializedName("str")
    @Expose
    public Object str;
    @SerializedName("commonInt3")
    @Expose
    public int commonInt3;
    @SerializedName("commonDecimal")
    @Expose
    public int commonDecimal;
    @SerializedName("commonInt4")
    @Expose
    public int commonInt4;
    @SerializedName("commonStr3")
    @Expose
    public Object commonStr3;
    @SerializedName("commonStr4")
    @Expose
    public Object commonStr4;
    @SerializedName("commonBool")
    @Expose
    public boolean commonBool;
    @SerializedName("commonBool1")
    @Expose
    public boolean commonBool1;
    @SerializedName("commonBool2")
    @Expose
    public boolean commonBool2;
    @SerializedName("commonChar")
    @Expose
    public String commonChar;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public int getShowGroupID() {
        return showGroupID;
    }

    public double getAmount() {
        return amount;
    }

    public String getMobile() {
        return mobile;
    }

    public String getRemark() {
        return remark;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public String getTransMode() {
        return transMode;
    }

    public double getCharge() {
        return charge;
    }

    public String getUserName() {
        return userName;
    }

    public int getStatus() {
        return status;
    }

    public String getBankName() {
        return bankName;
    }

    public String getIfsc() {
        return ifsc;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Object getfDate() {
        return fDate;
    }

    public Object gettDate() {
        return tDate;
    }

    public int getGroupID() {
        return groupID;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTid() {
        return tid;
    }

    public Object getPayIds() {
        return payIds;
    }

    public Object getDt() {
        return dt;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPincode() {
        return pincode;
    }

    public String getEmail() {
        return email;
    }

    public String getBankRRN() {
        return bankRRN;
    }

    public double getMiniBankBalance() {
        return miniBankBalance;
    }

    public int getMiniBankCapping() {
        return miniBankCapping;
    }

    public boolean isInRealTime() {
        return inRealTime;
    }

    public int getLoginID() {
        return loginID;
    }

    public int getLoginTypeID() {
        return loginTypeID;
    }

    public int getUserID() {
        return userID;
    }

    public int getCommonInt() {
        return commonInt;
    }

    public int getCommonInt2() {
        return commonInt2;
    }

    public Object getCommonStr() {
        return commonStr;
    }

    public Object getCommonStr2() {
        return commonStr2;
    }

    public boolean isListType() {
        return isListType;
    }

    public Object getStr() {
        return str;
    }

    public int getCommonInt3() {
        return commonInt3;
    }

    public int getCommonDecimal() {
        return commonDecimal;
    }

    public int getCommonInt4() {
        return commonInt4;
    }

    public Object getCommonStr3() {
        return commonStr3;
    }

    public Object getCommonStr4() {
        return commonStr4;
    }

    public boolean isCommonBool() {
        return commonBool;
    }

    public boolean isCommonBool1() {
        return commonBool1;
    }

    public boolean isCommonBool2() {
        return commonBool2;
    }

    public String getCommonChar() {
        return commonChar;
    }
}
