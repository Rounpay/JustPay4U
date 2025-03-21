package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vishnu Agarwal on 18,December,2019
 */
public class AddressData implements Serializable {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("orderNo")
    @Expose
    public String orderNo;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName(value = "pinCode", alternate = "pincode")
    @Expose
    public String pinCode;
    @SerializedName(value = "landmark", alternate = "landMark")
    @Expose
    public String landmark;
    @SerializedName("area")
    @Expose
    public String area;
    @SerializedName(value = "type", alternate = "addressType")
    @Expose
    public String type;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("alternateMobileNo")
    @Expose
    public String alternateMobileNo;
    @SerializedName("emailId")
    @Expose
    public String emailId;
    @SerializedName("addressId")
    @Expose
    public int addressId;
    @SerializedName("loginId")
    @Expose
    public int loginId;
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName(value = "isDefault", alternate = "IsDefault")
    @Expose
    boolean isDefault;

    public String get$id() {
        return $id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address != null && !address.isEmpty() ? address : "";
    }

    public String getState() {
        return state != null && !state.isEmpty() ? state : "";
    }

    public String getCity() {
        return city != null && !city.isEmpty() ? city : "";
    }

    public String getPinCode() {
        return pinCode != null && !pinCode.isEmpty() ? pinCode : "";
    }

    public String getLandmark() {
        return landmark != null && !landmark.isEmpty() ? landmark : "";
    }

    public String getArea() {
        return area != null && !area.isEmpty() ? area : "";
    }

    public String getType() {
        return type;
    }

    public String getMobileNo() {
        return mobileNo != null && !mobileNo.isEmpty() ? mobileNo : "";
    }

    public String getAlternateMobileNo() {
        return alternateMobileNo != null && !alternateMobileNo.isEmpty() ? alternateMobileNo : "";
    }

    public String getEmailId() {
        return emailId != null && !emailId.isEmpty() ? emailId : "";
    }

    public int getAddressId() {
        return addressId;
    }

    public int getLoginId() {
        return loginId;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
