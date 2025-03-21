package com.solution.app.justpay4u.Api.Shopping.Request;

/**
 * Created by Vishnu Agarwal on 17,December,2019
 */
public class AddToCartRequest {
    public String userID;
    public String sessionID;
    public String session;
    public String appid;
    public String imei;
    public String regKey;
    public String version;
    public String serialNo;
    public String loginTypeID;
    public String productID, productDetailID;
    String posId;
    String userId;
    String quantity;
    String websiteId;

    public AddToCartRequest(String quantity, String productID, String productDetailID, String userID, String loginTypeID,
                            String appid, String imei, String regKey, String version, String serialNo,
                            String sessionID, String session, String websiteId) {
        this.quantity = quantity;
        this.productID = productID;
        this.productDetailID = productDetailID;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
        this.websiteId = websiteId;
    }

   /* public AddToCartRequest(String posId, String userId, String quantity, String websiteId) {
        this.posId = posId;
        this.userId = userId;
        this.quantity = quantity;
        this.websiteId = websiteId;
    }*/
}
