package com.solution.app.justpay4u.Api.Shopping.Request;

/**
 * Created by Vishnu Agarwal on 17,January,2020
 */
public class FinalOrderPlacementRequest {
    String loginId;
    String paytmresponse;
    String orderNo;
    int WalletType, GetwayID;
    String transactionID;
    String websiteId, sessionId, session, paymentid, signature, status, statuscode;
    boolean release;

    public FinalOrderPlacementRequest(String loginId, String paytmresponse, String orderNo, String transactionID, String websiteId,
                                      boolean release, String sessionId, String session, int WalletType, int GetwayID, String paymentid, String signature,
                                      String statuscode, String status) {
        this.loginId = loginId;
        this.paytmresponse = paytmresponse;
        this.orderNo = orderNo;
        this.transactionID = transactionID;
        this.websiteId = websiteId;
        this.release = release;
        this.sessionId = sessionId;
        this.session = session;
        this.WalletType = WalletType;
        this.GetwayID = GetwayID;
        this.paymentid = paymentid;
        this.signature = signature;
        this.statuscode = statuscode;
        this.status = status;
    }
}
