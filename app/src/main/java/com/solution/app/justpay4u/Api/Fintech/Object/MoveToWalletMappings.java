package com.solution.app.justpay4u.Api.Fintech.Object;

public class MoveToWalletMappings {
    int id;
    int fromWalletID;
    int toWalletID;
    String fromWalletType;
    String toWalletType;
    boolean isFrenchies;

    public int getId() {
        return id;
    }

    public int getFromWalletID() {
        return fromWalletID;
    }

    public int getToWalletID() {
        return toWalletID;
    }

    public String getFromWalletType() {
        return fromWalletType;
    }

    public String getToWalletType() {
        return toWalletType;
    }
    public boolean getisFrenchies() {
        return isFrenchies;
    }
}
