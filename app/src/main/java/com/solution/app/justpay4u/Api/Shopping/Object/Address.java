package com.solution.app.justpay4u.Api.Shopping.Object;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable {

    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public Object msg;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName(value = "stateID", alternate = "stateId")
    @Expose
    public int stateID;
    @SerializedName(value = "cityID", alternate = "cityId")
    @Expose
    public int cityID;
    @SerializedName(value = "areaID", alternate = "areaId")
    @Expose
    public int areaID;
    @SerializedName("customerName")
    @Expose
    public String customerName;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("addressOnly")
    @Expose
    public String addressOnly;
    @SerializedName(value = "pinCode", alternate = "pin")
    @Expose
    public String pinCode;
    @SerializedName("landmark")
    @Expose
    public String landmark;
    @SerializedName("isDefault")
    @Expose
    public boolean isDefault;
    @SerializedName(value = "EmailId", alternate = "emailId")
    @Expose
    private String emailId;
    @SerializedName(value = "FirstName", alternate = "firstName")
    @Expose
    private String firstName;
    @SerializedName(value = "LastName", alternate = "lastName")
    @Expose
    private String lastName;
    @SerializedName(value = "Area", alternate = "area")
    @Expose
    private String area;
    @SerializedName(value = "Name", alternate = "name")
    @Expose
    private String name;
    @SerializedName(value = "MobileNo", alternate = "mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName(value = "Address", alternate = "address")
    @Expose
    private String address;
    @SerializedName(value = "City", alternate = "city")
    @Expose
    private String city;
    @SerializedName(value = "State", alternate = "state")
    @Expose
    private String state;
    @SerializedName(value = "Pincode", alternate = "pincode")
    @Expose
    private String pincode;
    @SerializedName(value = "Country", alternate = "country")
    @Expose
    private String country;
    @SerializedName(value = "IsPrime", alternate = "isPrime")
    @Expose
    private String isPrime;
    @SerializedName(value = "Type", alternate = "type")
    @Expose
    private String type;

    public int getStatuscode() {
        return statuscode;
    }

    public Object getMsg() {
        return msg;
    }

    public int getId() {
        return id;
    }

    public int getAreaID() {
        return areaID;
    }


    public String getCustomerName() {
        return customerName;
    }

    public String getTitle() {
        return title;
    }


    public String getAddressOnly() {
        return addressOnly;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getLandmark() {
        return landmark;
    }


    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public int getStateID() {
        return stateID;
    }

    public int getCityID() {
        return cityID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCombineAddress() {
        return address + ", " + city + ", " + state + ", " + pincode + ", " + country;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIsPrime() {
        return isPrime;
    }

    public void setIsPrime(String isPrime) {
        this.isPrime = isPrime;
    }

    public String getType() {

        if (type != null && !type.isEmpty()) {
            return type;
        } else {
            return "0";
        }
    }


    public boolean isAddressValid() {
        return area != null && !TextUtils.isEmpty(area) && !area.equalsIgnoreCase("0") &&
                address != null && !TextUtils.isEmpty(address) && !address.equalsIgnoreCase("0") &&
                city != null && !TextUtils.isEmpty(city) && !city.equalsIgnoreCase("0") &&
                state != null && !TextUtils.isEmpty(state) && !state.equalsIgnoreCase("0") &&
                pincode != null && !TextUtils.isEmpty(pincode) && !pincode.equalsIgnoreCase("0");
    }
}
