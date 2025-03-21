package com.solution.app.justpay4u.Api.Networking.Request;

import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;

public class BankTransferRequest {

    BasicRequest appSession;
    BankTransferRequest request;
    String Amount;
    int BankId,WalletID,OID;

    public BankTransferRequest(BasicRequest appSession, BankTransferRequest request) {
        this.appSession = appSession;
        this.request = request;
    }

    public BankTransferRequest(String amount, int bankId, int walletID, int OID) {
        this. Amount = amount;
        this.BankId = bankId;
        this. WalletID = walletID;
        this.OID = OID;
    }
}

