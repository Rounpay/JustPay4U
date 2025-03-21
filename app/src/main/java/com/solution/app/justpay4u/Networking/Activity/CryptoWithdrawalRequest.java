package com.solution.app.justpay4u.Networking.Activity;

import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;


public class CryptoWithdrawalRequest {

    BasicRequest appSession;
    CryptoWithdrawalRequest request;
    String ToUserId, Amount, ToWithdrwalType;
    int ToWalletId, FromWalletId;


    public CryptoWithdrawalRequest(BasicRequest appSession, CryptoWithdrawalRequest request) {
        this.appSession = appSession;
        this.request = request;
    }

    public CryptoWithdrawalRequest(String ToUserId, String Amount, String ToWithdrwalType, int ToWalletId, int FromWalletId) {
        this.ToUserId = ToUserId;
        this.Amount = Amount;
        this.ToWithdrwalType = ToWithdrwalType;
        this.ToWalletId = ToWalletId;
        this.FromWalletId = FromWalletId;

    }
}
