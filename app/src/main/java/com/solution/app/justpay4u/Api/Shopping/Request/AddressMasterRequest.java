package com.solution.app.justpay4u.Api.Shopping.Request;

/**
 * Created by Vishnu Agarwal on 20,December,2019
 */
public class AddressMasterRequest {
    String addressId;
    String websiteId;
    String loginId;
    String name;
    String mobileNo;
    String alternateMobileNo;
    String pincode;
    String area;
    String address;
    String state;
    String city;
    String landMark;
    boolean isDefault;
    String addressType;
    String mode;

    public AddressMasterRequest(String addressId, String websiteId, String loginId, String name, String mobileNo, String alternateMobileNo, String pincode, String area, String address, String state, String city, String landMark, boolean isDefault, String addressType, String mode) {
        this.addressId = addressId;
        this.websiteId = websiteId;
        this.loginId = loginId;
        this.name = name;
        this.mobileNo = mobileNo;
        this.alternateMobileNo = alternateMobileNo;
        this.pincode = pincode;
        this.area = area;
        this.address = address;
        this.state = state;
        this.city = city;
        this.landMark = landMark;
        this.isDefault = isDefault;
        this.addressType = addressType;
        this.mode = mode;
    }
}
