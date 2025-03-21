package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FundRequestForApproval {
    @SerializedName("isSelf")
    @Expose
    public boolean isSelf;
    @SerializedName("paymentId")
    @Expose
    public int paymentId;
    @SerializedName("userId")
    @Expose
    public int userId;
    @SerializedName("lt")
    @Expose
    public int lt;
    @SerializedName("bank")
    @Expose
    public String bank;
    @SerializedName("accountNo")
    @Expose
    public String accountNo;
    @SerializedName("mode")
    @Expose
    public String mode;
    @SerializedName("transactionId")
    @Expose
    public String transactionId;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("chequeNo")
    @Expose
    public String chequeNo;
    @SerializedName("accountHolder")
    @Expose
    public String accountHolder;
    @SerializedName("cardNumber")
    @Expose
    public String cardNumber;
    @SerializedName("entryDate")
    @Expose
    public String entryDate;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("statusCode")
    @Expose
    public int statusCode;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("userName")
    @Expose
    public String userName;
    @SerializedName("userMobile")
    @Expose
    public String userMobile;
    @SerializedName("toDate")
    @Expose
    public String toDate;
    @SerializedName("_TMode")
    @Expose
    public int tMode;
    @SerializedName("commRate")
    @Expose
    public int commRate;
    @SerializedName("remark")
    @Expose
    public String remark;
    String branch;
    String upiid;
    String approveDate;

    public boolean getSelf() {
        return isSelf;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getUserId() {
        return userId;
    }

    public int getLt() {
        return lt;
    }

    public String getBank() {
        return bank;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getMode() {
        return mode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getDescription() {
        return description;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getToDate() {
        return toDate;
    }

    public int gettMode() {
        return tMode;
    }

    public int getCommRate() {
        return commRate;
    }

    public String getBranch() {
        return branch;
    }

    public String getUpiid() {
        return upiid;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public String getRemark() {
        return remark;
    }
}
