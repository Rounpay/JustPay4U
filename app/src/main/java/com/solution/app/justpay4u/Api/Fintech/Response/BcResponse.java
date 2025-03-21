package com.solution.app.justpay4u.Api.Fintech.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BcResponse implements Parcelable {

    @SerializedName("errorCode")
    @Expose
    private int errorCode;
    @SerializedName("mobileno")
    @Expose
    private String mobileno;
    @SerializedName("secretKey")
    @Expose
    private String secretKey;
    @SerializedName("saltKey")
    @Expose
    private String saltKey;
    @SerializedName("cpid")
    @Expose
    private String cpid;
    @SerializedName("aepsOutletId")
    @Expose
    private String aepsOutletId;
    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("bcid")
    @Expose
    private String bcid;
    @SerializedName("merchantId")
    @Expose
    private String merchantId;
    @SerializedName("password")
    @Expose
    private String password;

    protected BcResponse(Parcel in) {
        errorCode = in.readInt();
        mobileno = in.readString();
        secretKey = in.readString();
        saltKey = in.readString();
        cpid = in.readString();
        aepsOutletId = in.readString();
        emailId = in.readString();
        userId = in.readString();
        bcid = in.readString();
        merchantId = in.readString();
        password = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(errorCode);
        dest.writeString(mobileno);
        dest.writeString(secretKey);
        dest.writeString(saltKey);
        dest.writeString(cpid);
        dest.writeString(aepsOutletId);
        dest.writeString(emailId);
        dest.writeString(userId);
        dest.writeString(bcid);
        dest.writeString(merchantId);
        dest.writeString(password);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<BcResponse> CREATOR = new Creator<BcResponse>() {
        @Override
        public BcResponse createFromParcel(Parcel in) {
            return new BcResponse(in);
        }

        @Override
        public BcResponse[] newArray(int size) {
            return new BcResponse[size];
        }
    };

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSaltKey() {
        return saltKey;
    }

    public void setSaltKey(String saltKey) {
        this.saltKey = saltKey;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public String getAepsOutletId() {
        return aepsOutletId;
    }

    public void setAepsOutletId(String aepsOutletId) {
        this.aepsOutletId = aepsOutletId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBcid() {
        return bcid;
    }

    public void setBcid(String bcid) {
        this.bcid = bcid;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
