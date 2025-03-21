package com.solution.app.justpay4u.Api.Fintech.Response;

import com.solution.app.justpay4u.Api.Fintech.Object.DthSubscriptionReport;

import java.util.ArrayList;

public class DthSubscriptionReportResponse {

    private String statuscode;
    private String msg;
    private boolean isVersionValid;
    private String isAppValid;
    private String groupID;
    private String TXN;
    private String beneName;
    private String chargedAmount;
    private ArrayList<DthSubscriptionReport> dthSubscriptions;


    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        groupID = groupID;
    }

    public String getTXN() {
        return TXN;
    }

    public void setTXN(String TXN) {
        this.TXN = TXN;
    }

    public String getBeneName() {
        return beneName;
    }

    public void setBeneName(String beneName) {
        this.beneName = beneName;
    }

    public String getChargedAmount() {
        return chargedAmount;
    }

    public void setChargedAmount(String chargedAmount) {
        this.chargedAmount = chargedAmount;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<DthSubscriptionReport> getDTHSubscriptionReport() {
        return dthSubscriptions;
    }
}
