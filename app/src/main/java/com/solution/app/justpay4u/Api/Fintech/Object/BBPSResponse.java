package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BBPSResponse {

    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("session")
    @Expose
    public String session;
    @SerializedName("error")
    @Expose
    public String error;
    @SerializedName("result")
    @Expose
    public String result;
    @SerializedName("transid")
    @Expose
    public String transid;

    @SerializedName("billNumber")
    @Expose
    public String billNumber;
    @SerializedName("billDate")
    @Expose
    public String billDate;
    @SerializedName("dueDate")
    @Expose
    public String dueDate;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("customerName")
    @Expose
    public String customerName;
    @SerializedName("authcode")
    @Expose
    public String authcode;
    @SerializedName("trnxstatus")
    @Expose
    public String trnxstatus;

    @SerializedName(value = "refID", alternate = {"refferenceID", "RefferenceID"})
    @Expose
    public String refID;

    @SerializedName("errorCode")
    @Expose
    public String errorCode;
    @SerializedName("errorMsg")
    @Expose
    public String errorMsg;
    @SerializedName(value = "isEditable", alternate = "iseditable")
    @Expose
    public boolean isEditable;
    @SerializedName("isEnablePayment")
    @Expose
    public boolean isEnablePayment;
    @SerializedName("isShowMsgOnly")
    @Expose
    public boolean isShowMsgOnly;
    @SerializedName("isHardCoded")
    @Expose
    public boolean isHardCoded;

    @SerializedName("billPeriod")
    @Expose
    public String billPeriod;
    @SerializedName("fetchBillID")
    @Expose
    public int fetchBillID;
    @SerializedName("exactness")
    @Expose
    public int exactness;
    @SerializedName("billAmountOptions")
    @Expose
    public ArrayList<BillAdditionalInfo> billAmountOptions;
    @SerializedName("billAdditionalInfo")
    @Expose
    public ArrayList<BillAdditionalInfo> billAdditionalInfo;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public String getDate() {
        return date;
    }

    public String getSession() {
        return session;
    }

    public String getError() {
        return error;
    }

    public String getResult() {
        return result;
    }

    public String getTransid() {
        return transid;
    }


    public String getBillNumber() {
        return billNumber;
    }

    public String getBillDate() {
        return billDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getAmount() {
        return amount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Object getAuthcode() {
        return authcode;
    }

    public Object getTrnxstatus() {
        return trnxstatus;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public boolean getIsEditable() {
        return isEditable;
    }

    public boolean getIsEnablePayment() {
        return isEnablePayment;
    }

    public boolean getIsShowMsgOnly() {
        return isShowMsgOnly;
    }

    public boolean isHardCoded() {
        return isHardCoded;
    }

    public String getBillPeriod() {
        return billPeriod;
    }

    public int getFetchBillID() {
        return fetchBillID;
    }

    public ArrayList<BillAdditionalInfo> getBillAmountOptions() {
        return billAmountOptions;
    }

    public ArrayList<BillAdditionalInfo> getBillAdditionalInfo() {
        return billAdditionalInfo;
    }

    public int getExactness() {
        return exactness;
    }

    public String getRefID() {
        return refID;
    }
}
