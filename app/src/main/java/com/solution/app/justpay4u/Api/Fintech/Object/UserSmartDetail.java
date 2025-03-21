package com.solution.app.justpay4u.Api.Fintech.Object;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 04/05/2022.
 */
public class UserSmartDetail<T extends Object> {

    boolean isQRShow;
    boolean isVPAShow;
    boolean isVirtualShow;
    @SerializedName("data")
    @Expose
    T data;

    @SerializedName(value = "smartCollectTypeID", alternate = "collectTypeID")
    @Expose
    int smartCollectTypeID;
    @SerializedName(value = "smartCollectType", alternate = "collectType")
    @Expose
    String smartCollectType;
    @SerializedName("customerID")
    @Expose
    String customerID;
    @SerializedName(value = "smartAccountNo", alternate = "account")
    @Expose
    String smartAccountNo;
    @SerializedName("virtualAccount")
    @Expose
    String virtualAccount;
    @SerializedName(value = "smartVPA", alternate = "vpa")
    @Expose
    String smartVPA;
    @SerializedName(value = "smartQRShortURL", alternate = "qr")
    @Expose
    String smartQRShortURL;
    @SerializedName("bankName")
    @Expose
    String bankName;
    @SerializedName("accountHolder")
    @Expose
    String accountHolder;
    @SerializedName("ifsc")
    @Expose
    String ifsc;
    @SerializedName("beneName")
    @Expose
    String beneName;
    @SerializedName("branch")
    @Expose
    String branch;
    String modifystr;
    Bitmap qrImage;

    public Bitmap getQrImage() {
        return qrImage;
    }

    public void setQrImage(Bitmap qrImage) {
        this.qrImage = qrImage;
    }

    public String getModifystr() {
        return modifystr;
    }

    public void setModifystr(String modifystr) {
        this.modifystr = modifystr;
    }

    public int getSmartCollectTypeID() {
        return smartCollectTypeID;
    }

    public String getSmartCollectType() {
        return smartCollectType;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getSmartAccountNo() {
        return smartAccountNo;
    }

    public String getSmartVPA() {
        return smartVPA;
    }

    public String getSmartQRShortURL() {
        return smartQRShortURL;
    }

    public String getBankName() {
        return bankName;
    }

    public boolean isQRShow() {
        return isQRShow;
    }

    public boolean isVPAShow() {
        return isVPAShow;
    }

    public boolean isVirtualShow() {
        return isVirtualShow;
    }

    public String getVirtualAccount() {
        return virtualAccount;
    }

    public String getBeneName() {
        return beneName;
    }

    public String getBranch() {
        return branch;
    }

    public T getData() {
        return data;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getIfsc() {
        return ifsc;
    }
}
