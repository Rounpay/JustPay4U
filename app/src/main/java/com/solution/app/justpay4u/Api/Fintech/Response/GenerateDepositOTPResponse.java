package com.solution.app.justpay4u.Api.Fintech.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 01/03/2021.
 */
public class GenerateDepositOTPResponse implements Parcelable {

    @SerializedName("reff1")
    @Expose
    public String reff1;
    @SerializedName("reff2")
    @Expose
    public String reff2;
    @SerializedName("reff3")
    @Expose
    public String reff3;
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("liveID")
    @Expose
    public String liveID;
    @SerializedName("transactionID")
    @Expose
    public String transactionID;
    @SerializedName("serverDate")
    @Expose
    public String serverDate;
    @SerializedName("beneficiaryName")
    @Expose
    public String beneficiaryName;
    @SerializedName("balance")
    @Expose
    public double balance;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid;
    @SerializedName("checkID")
    @Expose
    public int checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    public boolean isPasswordExpired;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("emailID")
    @Expose
    public String emailID;
    @SerializedName("isLookUpFromAPI")
    @Expose
    public boolean isLookUpFromAPI;
    @SerializedName("isDTHInfoCall")
    @Expose
    public boolean isDTHInfoCall;
    @SerializedName("isShowPDFPlan")
    @Expose
    public boolean isShowPDFPlan;
    @SerializedName("sid")
    @Expose
    public String sid;
    @SerializedName("isOTPRequired")
    @Expose
    public boolean isOTPRequired;
    @SerializedName("isResendAvailable")
    @Expose
    public boolean isResendAvailable;
    @SerializedName("getID")
    @Expose
    public int getID;
    @SerializedName("isDTHInfo")
    @Expose
    public boolean isDTHInfo;
    @SerializedName("isRoffer")
    @Expose
    public boolean isRoffer;

    protected GenerateDepositOTPResponse(Parcel in) {
        reff1 = in.readString();
        reff2 = in.readString();
        reff3 = in.readString();
        statuscode = in.readInt();
        status = in.readInt();
        msg = in.readString();
        liveID = in.readString();
        transactionID = in.readString();
        serverDate = in.readString();
        beneficiaryName = in.readString();
        balance = in.readDouble();
        isVersionValid = in.readByte() != 0;
        isAppValid = in.readByte() != 0;
        checkID = in.readInt();
        isPasswordExpired = in.readByte() != 0;
        mobileNo = in.readString();
        emailID = in.readString();
        isLookUpFromAPI = in.readByte() != 0;
        isDTHInfoCall = in.readByte() != 0;
        isShowPDFPlan = in.readByte() != 0;
        sid = in.readString();
        isOTPRequired = in.readByte() != 0;
        isResendAvailable = in.readByte() != 0;
        getID = in.readInt();
        isDTHInfo = in.readByte() != 0;
        isRoffer = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reff1);
        dest.writeString(reff2);
        dest.writeString(reff3);
        dest.writeInt(statuscode);
        dest.writeInt(status);
        dest.writeString(msg);
        dest.writeString(liveID);
        dest.writeString(transactionID);
        dest.writeString(serverDate);
        dest.writeString(beneficiaryName);
        dest.writeDouble(balance);
        dest.writeByte((byte) (isVersionValid ? 1 : 0));
        dest.writeByte((byte) (isAppValid ? 1 : 0));
        dest.writeInt(checkID);
        dest.writeByte((byte) (isPasswordExpired ? 1 : 0));
        dest.writeString(mobileNo);
        dest.writeString(emailID);
        dest.writeByte((byte) (isLookUpFromAPI ? 1 : 0));
        dest.writeByte((byte) (isDTHInfoCall ? 1 : 0));
        dest.writeByte((byte) (isShowPDFPlan ? 1 : 0));
        dest.writeString(sid);
        dest.writeByte((byte) (isOTPRequired ? 1 : 0));
        dest.writeByte((byte) (isResendAvailable ? 1 : 0));
        dest.writeInt(getID);
        dest.writeByte((byte) (isDTHInfo ? 1 : 0));
        dest.writeByte((byte) (isRoffer ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<GenerateDepositOTPResponse> CREATOR = new Creator<GenerateDepositOTPResponse>() {
        @Override
        public GenerateDepositOTPResponse createFromParcel(Parcel in) {
            return new GenerateDepositOTPResponse(in);
        }

        @Override
        public GenerateDepositOTPResponse[] newArray(int size) {
            return new GenerateDepositOTPResponse[size];
        }
    };

    public String getReff1() {
        return reff1;
    }

    public String getReff2() {
        return reff2;
    }

    public String getReff3() {
        return reff3;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public int getCheckID() {
        return checkID;
    }

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public boolean isLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public boolean isDTHInfoCall() {
        return isDTHInfoCall;
    }

    public boolean isShowPDFPlan() {
        return isShowPDFPlan;
    }

    public String getSid() {
        return sid;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public boolean isResendAvailable() {
        return isResendAvailable;
    }

    public int getGetID() {
        return getID;
    }

    public boolean isDTHInfo() {
        return isDTHInfo;
    }

    public boolean isRoffer() {
        return isRoffer;
    }

    public int getStatus() {
        return status;
    }

    public String getLiveID() {
        return liveID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getServerDate() {
        return serverDate;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public double getBalance() {
        return balance;
    }
}
