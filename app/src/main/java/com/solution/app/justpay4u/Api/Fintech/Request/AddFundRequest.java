package com.solution.app.justpay4u.Api.Fintech.Request;

/**
 * Created by Vishnu Agarwal on 04,January,2020
 */
public class AddFundRequest extends BasicRequest{
    String orderID, upiid;
    String checksum;
    int isImage;
    String bankID;
    String amount;
    String transactionID;
    String mobileNo;
    String chequeNo;
    String cardNo;
    String accountHolderName;
    String accountNumber;
    int paymentID;
    private String walletTypeID;

    public AddFundRequest(String upiid, String orderID, String checksum, int isImage, String bankID, String amount, String transactionID, String mobileNo, String chequeNo, String cardNo, String accountHolderName, String AccountNo, int PaymentModeID, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session, String walletTypeID) {
        this.upiid = upiid;
        this.orderID = orderID;
        this.checksum = checksum;
        this.isImage = isImage;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.bankID = bankID;
        this.amount = amount;
        this.transactionID = transactionID;
        this.mobileNo = mobileNo;
        this.chequeNo = chequeNo;
        this.cardNo = cardNo;
        this.accountHolderName = accountHolderName;
        this.accountNumber = AccountNo;
        this.paymentID = PaymentModeID;
        this.sessionID = sessionID;
        this.session = session;
        this.walletTypeID = walletTypeID;
    }
}
