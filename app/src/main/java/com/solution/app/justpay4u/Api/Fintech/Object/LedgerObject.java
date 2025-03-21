package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class LedgerObject implements Parcelable {

    int serviceID;
    boolean lType;
    private String tid;
    private String description;
    private String user;
    private String id, userID;
    private String entryDate;
    private double lastAmount;
    private double credit;
    private double debit;
    private double curentBalance;
    private String mobileNo;
    private String remark;

    protected LedgerObject(Parcel in) {
        serviceID = in.readInt();
        lType = in.readByte() != 0;
        tid = in.readString();
        description = in.readString();
        user = in.readString();
        id = in.readString();
        userID = in.readString();
        entryDate = in.readString();
        lastAmount = in.readDouble();
        credit = in.readDouble();
        debit = in.readDouble();
        curentBalance = in.readDouble();
        mobileNo = in.readString();
        remark = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(serviceID);
        dest.writeByte((byte) (lType ? 1 : 0));
        dest.writeString(tid);
        dest.writeString(description);
        dest.writeString(user);
        dest.writeString(id);
        dest.writeString(userID);
        dest.writeString(entryDate);
        dest.writeDouble(lastAmount);
        dest.writeDouble(credit);
        dest.writeDouble(debit);
        dest.writeDouble(curentBalance);
        dest.writeString(mobileNo);
        dest.writeString(remark);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<LedgerObject> CREATOR = new Creator<LedgerObject>() {
        @Override
        public LedgerObject createFromParcel(Parcel in) {
            return new LedgerObject(in);
        }

        @Override
        public LedgerObject[] newArray(int size) {
            return new LedgerObject[size];
        }
    };

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public double getLastAmount() {
        return lastAmount;
    }

    public void setLastAmount(double lastAmount) {
        this.lastAmount = lastAmount;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCurentBalance() {
        return curentBalance;
    }

    public void setCurentBalance(double curentBalance) {
        this.curentBalance = curentBalance;
    }

    public String getUserID() {
        return userID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public boolean islType() {
        return lType;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
