package com.solution.app.justpay4u.Networking.Activity;

import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;


public class WalletCryptoReportRequest {

    BasicRequest appSession;
    WalletCryptoReportRequest request;
    String FromDate;
    String ToDate;
    String WalletId, CurrencyId;


    public WalletCryptoReportRequest(BasicRequest appSession, WalletCryptoReportRequest request) {
        this.appSession = appSession;
        this.request = request;
    }

    public WalletCryptoReportRequest(String fromDate, String toDate, String walletId, String currencyId) {

        this.FromDate = fromDate;
        this.ToDate = toDate;
        this.WalletId = walletId;
        this.CurrencyId = currencyId;
    }
}
