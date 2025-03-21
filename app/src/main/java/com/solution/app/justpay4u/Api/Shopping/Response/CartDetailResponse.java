package com.solution.app.justpay4u.Api.Shopping.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Shopping.Object.Address;
import com.solution.app.justpay4u.Api.Shopping.Object.AddressData;
import com.solution.app.justpay4u.Api.Shopping.Object.CartDetail;
import com.solution.app.justpay4u.Api.Shopping.Object.CartDetailData;
import com.solution.app.justpay4u.Api.Shopping.Object.CartDetailProductList;

import java.util.ArrayList;
import java.util.List;

public class CartDetailResponse {

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("status")
    @Expose
    public boolean status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public ArrayList<CartDetailProductList> cartDetailProductList = null;
    @SerializedName("data1")
    @Expose
    public CartDetailData cartDetailData;
    @SerializedName("data2")
    @Expose
    public AddressData addressData;
    @SerializedName("quantity")
    @Expose
    public int quantity;
    @SerializedName("primaryDeductionPer")
    @Expose
    public int primaryDeductionPer;
    @SerializedName("totalCost")
    @Expose
    public double totalCost;
    @SerializedName("totalMRP")
    @Expose
    public double totalMRP;

    @SerializedName("totalDiscount")
    @Expose
    public double totalDiscount;
    @SerializedName("pDeduction")
    @Expose
    public double pDeduction;
    @SerializedName("sDeduction")
    @Expose
    public double sDeduction;
    @SerializedName("shippingCharge")
    @Expose
    public double shippingCharge;
    @SerializedName("pWallet")
    @Expose
    public String pWallet;
    @SerializedName("sWallet")
    @Expose
    public String sWallet;
    @SerializedName("address")
    @Expose
    public Address address;

    @SerializedName(value = "cartDetail", alternate = "cartDetails")
    @Expose
    public List<CartDetail> cartDetail = null;
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid;
    @SerializedName(value = "checkID", alternate = "checkId")
    @Expose
    public int checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    public boolean isPasswordExpired;
    @SerializedName("mobileNo")
    @Expose
    public Object mobileNo;
    @SerializedName(value = "emailID", alternate = "emailId")
    @Expose
    public Object emailID;
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
    public Object sid;
    @SerializedName("isOTPRequired")
    @Expose
    public boolean isOTPRequired;
    @SerializedName(value = "getID", alternate = "getId")
    @Expose
    public int getID;
    @SerializedName("isDTHInfo")
    @Expose
    public boolean isDTHInfo;
    @SerializedName("isRoffer")
    @Expose
    public boolean isRoffer;

    public List<CartDetail> getCartDetail() {
        return cartDetail;
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

    public Object getMobileNo() {
        return mobileNo;
    }

    public Object getEmailID() {
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

    public Object getSid() {
        return sid;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
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

    public int getQuantity() {
        return quantity;
    }

    public int getPrimaryDeductionPer() {
        return primaryDeductionPer;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getTotalMRP() {
        return totalMRP;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public double getpDeduction() {
        return pDeduction;
    }

    public double getsDeduction() {
        return sDeduction;
    }

    public double getShippingCharge() {
        return shippingCharge;
    }

    public String getpWallet() {
        return pWallet;
    }

    public String getsWallet() {
        return sWallet;
    }

    public Address getAddress() {
        return address;
    }

    public String get$id() {
        return $id;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<CartDetailProductList> getCartDetailProductList() {
        return cartDetailProductList;
    }

    public CartDetailData getCartDetailData() {
        return cartDetailData;
    }

    public AddressData getAddressData() {
        return addressData;
    }
}
