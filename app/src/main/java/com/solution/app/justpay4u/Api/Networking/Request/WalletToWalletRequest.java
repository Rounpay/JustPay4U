package com.solution.app.justpay4u.Api.Networking.Request;

import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;

/**
 * Created by Vishnu Agarwal on 17/08/2022.
 */


public class WalletToWalletRequest {

    BasicRequest appSession;
    WalletToWalletRequest request;
    String FromUserId;
    String ToUserId;
    int FromWalletId;
    int ToWalletId;
    String FromCurrAmount;
    String Remark;

    public WalletToWalletRequest(BasicRequest appSession, WalletToWalletRequest request) {
        this.appSession = appSession;
        this.request = request;
    }

    public WalletToWalletRequest(String fromUserId, String toUserId, int fromWalletId, int toWalletId, String fromCurrAmount, String remark) {
        FromUserId = fromUserId;
        ToUserId = toUserId;
        FromWalletId = fromWalletId;
        ToWalletId = toWalletId;
        FromCurrAmount = fromCurrAmount;
        Remark = remark;
    }
}
