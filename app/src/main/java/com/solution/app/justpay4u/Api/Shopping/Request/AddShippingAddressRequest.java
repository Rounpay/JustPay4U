package com.solution.app.justpay4u.Api.Shopping.Request;

public class AddShippingAddressRequest {

    public int id;
    public String title;
    public String customerName;
    public String mobileNo;
    public String address;
    public int cityID;
    public int stateID;
    public String area;
    public String pin;
    public String landmark;
    public String userID;
    public String sessionID;
    public String session;
    public String appid;
    public String imei;
    public String regKey;
    public String version;
    public String serialNo;
    public String loginTypeID;
    boolean isDeleted;
    boolean isDefault;

    public AddShippingAddressRequest(boolean isDeleted, boolean isDefault, int id, String title, String customerName, String mobileNo,
                                     String address, int cityID, int stateID, String area, String pin, String landmark, String userID,
                                     String loginTypeID, String appid, String imei, String regKey, String version, String serialNo,
                                     String sessionID, String session) {
        this.isDeleted = isDeleted;
        this.isDefault = isDefault;
        this.id = id;
        this.title = title;
        this.customerName = customerName;
        this.mobileNo = mobileNo;
        this.address = address;
        this.cityID = cityID;
        this.stateID = stateID;
        this.pin = pin;
        this.area = area;
        this.landmark = landmark;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
    }
}
