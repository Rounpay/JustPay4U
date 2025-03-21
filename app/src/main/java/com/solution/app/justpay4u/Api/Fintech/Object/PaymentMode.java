package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMode implements Parcelable {


    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("bankID")
    @Expose
    public int bankID;
    @SerializedName("modeID")
    @Expose
    public int modeID;
    @SerializedName("isActive")
    @Expose
    public boolean isActive;
    @SerializedName("mode")
    @Expose
    public String mode;
    @SerializedName("isTransactionIdAuto")
    @Expose
    public boolean isTransactionIdAuto;
    @SerializedName("isAccountHolderRequired")
    @Expose
    public boolean isAccountHolderRequired;
    @SerializedName("isChequeNoRequired")
    @Expose
    public boolean isChequeNoRequired;
    @SerializedName("isCardNumberRequired")
    @Expose
    public boolean isCardNumberRequired;
    @SerializedName("isMobileNoRequired")
    @Expose
    public boolean isMobileNoRequired;
    @SerializedName("isBranchRequired")
    @Expose
    public boolean isBranchRequired;
    @SerializedName("isUPIID")
    @Expose
    public boolean isUPIID;
    @SerializedName("cid")
    @Expose
    public String cid;

    protected PaymentMode(Parcel in) {
        id = in.readInt();
        bankID = in.readInt();
        modeID = in.readInt();
        isActive = in.readByte() != 0;
        mode = in.readString();
        isTransactionIdAuto = in.readByte() != 0;
        isAccountHolderRequired = in.readByte() != 0;
        isChequeNoRequired = in.readByte() != 0;
        isCardNumberRequired = in.readByte() != 0;
        isMobileNoRequired = in.readByte() != 0;
        isBranchRequired = in.readByte() != 0;
        isUPIID = in.readByte() != 0;
        cid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(bankID);
        dest.writeInt(modeID);
        dest.writeByte((byte) (isActive ? 1 : 0));
        dest.writeString(mode);
        dest.writeByte((byte) (isTransactionIdAuto ? 1 : 0));
        dest.writeByte((byte) (isAccountHolderRequired ? 1 : 0));
        dest.writeByte((byte) (isChequeNoRequired ? 1 : 0));
        dest.writeByte((byte) (isCardNumberRequired ? 1 : 0));
        dest.writeByte((byte) (isMobileNoRequired ? 1 : 0));
        dest.writeByte((byte) (isBranchRequired ? 1 : 0));
        dest.writeByte((byte) (isUPIID ? 1 : 0));
        dest.writeString(cid);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<PaymentMode> CREATOR = new Creator<PaymentMode>() {
        @Override
        public PaymentMode createFromParcel(Parcel in) {
            return new PaymentMode(in);
        }

        @Override
        public PaymentMode[] newArray(int size) {
            return new PaymentMode[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModeID() {
        return modeID;
    }

    public int getBankID() {
        return bankID;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean getIsBranchRequired() {
        return isBranchRequired;
    }

    public void setBranchRequired(boolean branchRequired) {
        isBranchRequired = branchRequired;
    }

    public boolean getIsUPIID() {
        return isUPIID;
    }

    public void setUPIID(boolean UPIID) {
        isUPIID = UPIID;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean getIsTransactionIdAuto() {
        return isTransactionIdAuto;
    }

    public void setIsTransactionIdAuto(boolean isTransactionIdAuto) {
        this.isTransactionIdAuto = isTransactionIdAuto;
    }

    public boolean getIsAccountHolderRequired() {
        return isAccountHolderRequired;
    }

    public void setIsAccountHolderRequired(boolean isAccountHolderRequired) {
        this.isAccountHolderRequired = isAccountHolderRequired;
    }

    public boolean getIsChequeNoRequired() {
        return isChequeNoRequired;
    }

    public void setIsChequeNoRequired(boolean isChequeNoRequired) {
        this.isChequeNoRequired = isChequeNoRequired;
    }

    public boolean getIsCardNumberRequired() {
        return isCardNumberRequired;
    }

    public void setIsCardNumberRequired(boolean isCardNumberRequired) {
        this.isCardNumberRequired = isCardNumberRequired;
    }

    public boolean getIsMobileNoRequired() {
        return isMobileNoRequired;
    }

    public void setIsMobileNoRequired(boolean isMobileNoRequired) {
        this.isMobileNoRequired = isMobileNoRequired;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

}