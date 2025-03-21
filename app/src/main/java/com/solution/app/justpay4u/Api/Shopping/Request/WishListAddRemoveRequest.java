package com.solution.app.justpay4u.Api.Shopping.Request;

/**
 * Created by Vishnu Agarwal on 17,December,2019
 */
public class WishListAddRemoveRequest {
    String posId;
    String loginId;
    String websiteId;

    public WishListAddRemoveRequest(String posId, String loginId, String websiteId) {
        this.posId = posId;
        this.loginId = loginId;
        this.websiteId = websiteId;
    }
}
