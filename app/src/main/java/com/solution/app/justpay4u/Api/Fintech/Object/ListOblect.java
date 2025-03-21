package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOblect implements Parcelable {

    @SerializedName("requestedAmount")
    @Expose
    private int requestedAmount;
    @SerializedName("liveID")
    @Expose
    private String liveID;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("transactionID")
    @Expose
    private String transactionID;
    @SerializedName("tid")
    @Expose
    private int tid;

    protected ListOblect(Parcel in) {
        requestedAmount = in.readInt();
        liveID = in.readString();
        status = in.readString();
        statuscode = in.readInt();
        transactionID = in.readString();
        tid = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(requestedAmount);
        dest.writeString(liveID);
        dest.writeString(status);
        dest.writeInt(statuscode);
        dest.writeString(transactionID);
        dest.writeInt(tid);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<ListOblect> CREATOR = new Creator<ListOblect>() {
        @Override
        public ListOblect createFromParcel(Parcel in) {
            return new ListOblect(in);
        }

        @Override
        public ListOblect[] newArray(int size) {
            return new ListOblect[size];
        }
    };

    public int getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(int requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public String getLiveID() {
        return liveID;
    }

    public void setLiveID(String liveID) {
        this.liveID = liveID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

}