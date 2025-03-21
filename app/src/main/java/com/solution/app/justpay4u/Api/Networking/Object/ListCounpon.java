package com.solution.app.justpay4u.Api.Networking.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 04/02/2022.
 */
public class ListCounpon {
    @SerializedName("userID")
    @Expose
    public int userID;
    @SerializedName("loginID")
    @Expose
    public int loginID;
    @SerializedName("prefixUserId")
    @Expose
    public String prefixUserId;
    @SerializedName("lt")
    @Expose
    public int lt;
    @SerializedName("roleID")
    @Expose
    public int roleID;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("couponId")
    @Expose
    public int couponId;
    @SerializedName("couponUserId")
    @Expose
    public int couponUserId;
    @SerializedName("couponTypeId")
    @Expose
    public int couponTypeId;
    @SerializedName("couponCode")
    @Expose
    public String couponCode;
    @SerializedName("counponType")
    @Expose
    public String counponType;
    @SerializedName("couponStatus")
    @Expose
    public String couponStatus;
    @SerializedName("validityDays")
    @Expose
    public int validityDays;
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("couponPrice")
    @Expose
    public float couponPrice;
    @SerializedName("redeemStatus")
    @Expose
    public int redeemStatus;
    @SerializedName("redeemDate")
    @Expose
    public String redeemDate;
    @SerializedName("couponGeneDate")
    @Expose
    public String couponGeneDate;
    @SerializedName("couponExpDate")
    @Expose
    public String couponExpDate;
    @SerializedName("closingUserId")
    @Expose
    public Object closingUserId;
    @SerializedName("operatorId")
    @Expose
    public int operatorId;
    @SerializedName("isConvertEnable")
    @Expose
    public boolean isConvertEnable;
    @SerializedName("amount")
    @Expose
    public float amount;
    @SerializedName("couponShowType")
    @Expose
    public Object couponShowType;
    @SerializedName("browser")
    @Expose
    public Object browser;
    @SerializedName("ip")
    @Expose
    public Object ip;
    @SerializedName("couponSearchOrder")
    @Expose
    public Object couponSearchOrder;
    @SerializedName("totalRecord")
    @Expose
    public int totalRecord;
    @SerializedName("topRow")
    @Expose
    public int topRow;
    @SerializedName("isMobileEditable")
    @Expose
    public boolean isMobileEditable;


    public int getUserID() {
        return userID;
    }

    public int getLoginID() {
        return loginID;
    }

    public String getPrefixUserId() {
        return prefixUserId;
    }

    public int getLt() {
        return lt;
    }

    public int getRoleID() {
        return roleID;
    }

    public String getName() {
        return name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public int getCouponId() {
        return couponId;
    }

    public int getCouponUserId() {
        return couponUserId;
    }

    public int getCouponTypeId() {
        return couponTypeId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public String getCounponType() {
        return counponType;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public int getValidityDays() {
        return validityDays;
    }

    public int getStatus() {
        return status;
    }

    public float getCouponPrice() {
        return couponPrice;
    }

    public int getRedeemStatus() {
        return redeemStatus;
    }

    public String getRedeemDate() {
        return redeemDate;
    }

    public String getCouponGeneDate() {
        return couponGeneDate;
    }

    public String getCouponExpDate() {
        return couponExpDate;
    }

    public Object getClosingUserId() {
        return closingUserId;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public boolean isConvertEnable() {
        return isConvertEnable;
    }

    public float getAmount() {
        return amount;
    }

    public Object getCouponShowType() {
        return couponShowType;
    }

    public Object getBrowser() {
        return browser;
    }

    public Object getIp() {
        return ip;
    }

    public Object getCouponSearchOrder() {
        return couponSearchOrder;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public int getTopRow() {
        return topRow;
    }

    public boolean isMobileEditable() {
        return isMobileEditable;
    }
}
