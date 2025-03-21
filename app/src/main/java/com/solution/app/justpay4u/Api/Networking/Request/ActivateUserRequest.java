package com.solution.app.justpay4u.Api.Networking.Request;

import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;

/**
 * Created by Vishnu Agarwal on 17/08/2022.
 */
public class ActivateUserRequest {

    BasicRequest appSession;
    ActivateUserRequest request;
    String TopupUserId;
    int WalletId;
    int PackageID;
    int BusinessType;

    double PackageAmount;
    String EPin;

    public ActivateUserRequest(BasicRequest appSession, ActivateUserRequest request) {
        this.appSession = appSession;
        this.request = request;
    }

    public ActivateUserRequest(String topupUserId, int walletId, int packageID, int businessType,double PackageAmount, String EPin) {
        this.TopupUserId = topupUserId;
        this.WalletId = walletId;
        this.PackageID = packageID;
        this.BusinessType = businessType;
        this.PackageAmount=PackageAmount;
        this.EPin = EPin;
    }
    public ActivateUserRequest(String topupUserId, int walletId, int packageID, int businessType, String EPin) {
        this.TopupUserId = topupUserId;
        this.WalletId = walletId;
        this.PackageID = packageID;
        this.BusinessType = businessType;
        this.EPin = EPin;
    }
}
