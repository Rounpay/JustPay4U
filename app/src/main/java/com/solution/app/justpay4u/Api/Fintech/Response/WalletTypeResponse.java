package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.MoveToWalletMappings;
import com.solution.app.justpay4u.Api.Fintech.Object.WalletType;

import java.util.List;

public class WalletTypeResponse {
    @SerializedName("walletTypes")
    @Expose
    public List<WalletType> walletTypes = null;
    @SerializedName("moveToWalletMappings")
    @Expose
    public List<MoveToWalletMappings> moveToWalletMappings = null;
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid;
    @SerializedName("checkID")
    @Expose
    public int checkID;

    public List<WalletType> getWalletTypes() {
        return walletTypes;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public List<MoveToWalletMappings> getMoveToWalletMappings() {
        return moveToWalletMappings;
    }

    public void setMoveToWalletMappings(List<MoveToWalletMappings> moveToWalletMappings) {
        this.moveToWalletMappings = moveToWalletMappings;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public boolean getVersionValid() {
        return isVersionValid;
    }

    public boolean getAppValid() {
        return isAppValid;
    }

    public int getCheckID() {
        return checkID;
    }
}
