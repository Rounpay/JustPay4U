package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionDetail implements Parcelable {

    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("wid")
    @Expose
    private int wid;
    @SerializedName("supportEmail")
    @Expose
    private String supportEmail;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("channel")
    @Expose
    private String channel;
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("senderNo")
    @Expose
    private String senderNo;
    @SerializedName("beneName")
    @Expose
    private String beneName;
    @SerializedName("userID")
    @Expose
    private int userID;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pinCode")
    @Expose
    private String pinCode;
    @SerializedName("entryDate")
    @Expose
    private String entryDate;
    @SerializedName("lists")
    @Expose
    private List<ListOblect> lists = null;

    protected TransactionDetail(Parcel in) {
        statuscode = in.readInt();
        status = in.readString();
        wid = in.readInt();
        supportEmail = in.readString();
        email = in.readString();
        ifsc = in.readString();
        channel = in.readString();
        account = in.readString();
        bankName = in.readString();
        senderNo = in.readString();
        beneName = in.readString();
        userID = in.readInt();
        mobileNo = in.readString();
        name = in.readString();
        address = in.readString();
        pinCode = in.readString();
        entryDate = in.readString();
        lists = in.createTypedArrayList(ListOblect.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(statuscode);
        dest.writeString(status);
        dest.writeInt(wid);
        dest.writeString(supportEmail);
        dest.writeString(email);
        dest.writeString(ifsc);
        dest.writeString(channel);
        dest.writeString(account);
        dest.writeString(bankName);
        dest.writeString(senderNo);
        dest.writeString(beneName);
        dest.writeInt(userID);
        dest.writeString(mobileNo);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(pinCode);
        dest.writeString(entryDate);
        dest.writeTypedList(lists);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<TransactionDetail> CREATOR = new Creator<TransactionDetail>() {
        @Override
        public TransactionDetail createFromParcel(Parcel in) {
            return new TransactionDetail(in);
        }

        @Override
        public TransactionDetail[] newArray(int size) {
            return new TransactionDetail[size];
        }
    };

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSenderNo() {
        return senderNo;
    }

    public void setSenderNo(String senderNo) {
        this.senderNo = senderNo;
    }

    public String getBeneName() {
        return beneName;
    }

    public void setBeneName(String beneName) {
        this.beneName = beneName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public java.util.List<ListOblect> getLists() {
        return lists;
    }

    public void setLists(java.util.List<ListOblect> lists) {
        this.lists = lists;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getStatus() {
        return status;
    }
}