package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vishnu on 12-04-2017.
 */

public class BankListObject implements Parcelable {

    public static final Creator<BankListObject> CREATOR = new Creator<BankListObject>() {
        @Override
        public BankListObject createFromParcel(Parcel in) {
            return new BankListObject(in);
        }

        @Override
        public BankListObject[] newArray(int size) {
            return new BankListObject[size];
        }
    };
    int iin;
    private int id;
    private String bankName;
    private String accountLimit;
    private String code;
    private String isIMPS;
    private String isNEFT;
    private String isACVerification;
    private String ifsc;
    private String logo;
    private String ekO_BankID;
    private String bankType;
    private boolean isaepsStatus;
    private int bankID;

    protected BankListObject(Parcel in) {
        id = in.readInt();
        bankName = in.readString();
        accountLimit = in.readString();
        code = in.readString();
        isIMPS = in.readString();
        isNEFT = in.readString();
        isACVerification = in.readString();
        ifsc = in.readString();
        logo = in.readString();
        ekO_BankID = in.readString();
        bankType = in.readString();
        isaepsStatus = in.readByte() != 0;
        iin = in.readInt();
        bankID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(bankName);
        dest.writeString(accountLimit);
        dest.writeString(code);
        dest.writeString(isIMPS);
        dest.writeString(isNEFT);
        dest.writeString(isACVerification);
        dest.writeString(ifsc);
        dest.writeString(logo);
        dest.writeString(ekO_BankID);
        dest.writeString(bankType);
        dest.writeByte((byte) (isaepsStatus ? 1 : 0));
        dest.writeInt(iin);
        dest.writeInt(bankID);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getBankID() {
        return bankID;
    }

    public String getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(String accountLimit) {
        this.accountLimit = accountLimit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIsIMPS() {
        return isIMPS;
    }

    public void setIsIMPS(String isIMPS) {
        this.isIMPS = isIMPS;
    }

    public String getIsNEFT() {
        return isNEFT;
    }

    public void setIsNEFT(String isNEFT) {
        this.isNEFT = isNEFT;
    }

    public String getIsACVerification() {
        return isACVerification;
    }

    public void setIsACVerification(String isACVerification) {
        this.isACVerification = isACVerification;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEkO_BankID() {
        return ekO_BankID;
    }

    public void setEkO_BankID(String ekO_BankID) {
        this.ekO_BankID = ekO_BankID;
    }

    public String getBankType() {
        return bankType;
    }

    public boolean isIsaepsStatus() {
        return isaepsStatus;
    }

    public int getIin() {
        return iin;
    }
}
