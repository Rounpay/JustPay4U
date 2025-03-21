package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DTHInfoRecords implements Parcelable {

    @SerializedName("statusCode")
    @Expose
    private int statusCode;
    @SerializedName(value = "monthlyRecharge", alternate = "Monthly")
    @Expose
    private String monthlyRecharge;

    @SerializedName(value = "accountNo", alternate = "accountno")
    @Expose
    private String accountNo;

    @SerializedName(value = "balance", alternate = "Balance")
    @Expose
    private String balance;

    @SerializedName(value = "customerName", alternate = "Name")
    @Expose
    private String customerName;

    @SerializedName(value = "status", alternate = "Status")
    @Expose
    private String status;
    @SerializedName(value = "nextRechargeDate", alternate = "Next Recharge Date")
    @Expose
    private String nextRechargeDate;

    @SerializedName(value = "lastrechargedate", alternate = "Last Recharge Date")
    @Expose
    private String lastrechargedate;
    @SerializedName(value = "lastrechargeamount", alternate = "Last Recharge Amount")
    @Expose
    private String lastrechargeamount;
    @SerializedName(value = "planname", alternate = {"Plan", "planName"})
    @Expose
    private String planname;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("data")
    @Expose
    private ArrayList<DTHInfoRecords> data;

    protected DTHInfoRecords(Parcel in) {
        statusCode = in.readInt();
        monthlyRecharge = in.readString();
        accountNo = in.readString();
        balance = in.readString();
        customerName = in.readString();
        status = in.readString();
        nextRechargeDate = in.readString();
        lastrechargedate = in.readString();
        lastrechargeamount = in.readString();
        planname = in.readString();
        key = in.readString();
        value = in.readString();
        data = in.createTypedArrayList(DTHInfoRecords.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(statusCode);
        dest.writeString(monthlyRecharge);
        dest.writeString(accountNo);
        dest.writeString(balance);
        dest.writeString(customerName);
        dest.writeString(status);
        dest.writeString(nextRechargeDate);
        dest.writeString(lastrechargedate);
        dest.writeString(lastrechargeamount);
        dest.writeString(planname);
        dest.writeString(key);
        dest.writeString(value);
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<DTHInfoRecords> CREATOR = new Creator<DTHInfoRecords>() {
        @Override
        public DTHInfoRecords createFromParcel(Parcel in) {
            return new DTHInfoRecords(in);
        }

        @Override
        public DTHInfoRecords[] newArray(int size) {
            return new DTHInfoRecords[size];
        }
    };

    public String getMonthlyRecharge() {
        return monthlyRecharge;
    }

    public String getBalance() {
        return balance;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    public String getNextRechargeDate() {
        return nextRechargeDate;
    }

    public String getLastrechargedate() {
        return lastrechargedate;
    }

    public String getLastrechargeamount() {
        return lastrechargeamount;
    }

    public String getPlanname() {
        return planname;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public ArrayList<DTHInfoRecords> getData() {
        return data;
    }
}
