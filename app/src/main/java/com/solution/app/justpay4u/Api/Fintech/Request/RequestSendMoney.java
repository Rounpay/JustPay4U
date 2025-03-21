package com.solution.app.justpay4u.Api.Fintech.Request;

public class RequestSendMoney {

    private String o;
    private String oid;
    private String beneID;
    private String mobileNo;
    private String ifsc;
    private String accountNo;
    private String amount;
    private String channel;
    private String bank;
    private String beneName;

    public RequestSendMoney(String beneID, String mobileNo, String ifsc, String accountNo, String amount, String channel, String bank, String beneName) {
        this.beneID = beneID;
        this.mobileNo = mobileNo;
        this.ifsc = ifsc;
        this.accountNo = accountNo;
        this.amount = amount;
        this.channel = channel;
        this.bank = bank;
        this.beneName = beneName;
    }

    public RequestSendMoney(String o, String oid, String beneID, String mobileNo, String ifsc, String accountNo, String amount, String channel, String bank, String beneName) {
        this.o = o;
        this.oid = oid;
        this.beneID = beneID;
        this.mobileNo = mobileNo;
        this.ifsc = ifsc;
        this.accountNo = accountNo;
        this.amount = amount;
        this.channel = channel;
        this.bank = bank;
        this.beneName = beneName;
    }


}
