package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.BenisObject;
import com.solution.app.justpay4u.Api.Fintech.Object.DmtReportObject;
import com.solution.app.justpay4u.Api.Fintech.Object.FundDCObject;
import com.solution.app.justpay4u.Api.Fintech.Object.FundOrderReportObject;
import com.solution.app.justpay4u.Api.Fintech.Object.LedgerObject;
import com.solution.app.justpay4u.Api.Fintech.Object.MoveToWalletReportData;
import com.solution.app.justpay4u.Api.Fintech.Object.RechargeStatus;
import com.solution.app.justpay4u.Api.Fintech.Object.SlabCommtObject;

import java.util.ArrayList;

public class RechargeReportResponse {

    private int statuscode;
    private String msg;
    private int orderID;
    private boolean isVersionValid;
    private boolean isAppValid;
    private String groupID, sid;
    private boolean isOTPRequired;
    private boolean isResendAvailable;
    @SerializedName(value = "transactionID", alternate = "transactionId")
    @Expose
    private String transactionID;
    private String TXN, tid, liveID;
    private String beneName;
    private ArrayList<MoveToWalletReportData> moveToWalletReport;
    private ArrayList<SlabCommtObject> slabCommissions;
    private ArrayList<DmtReportObject> dmtReport;
    private ArrayList<RechargeStatus> rechargeReport;
    private ArrayList<RechargeStatus> recentRecharge;
    private ArrayList<RechargeStatus> aePsDetail;
    @SerializedName(value = "benis", alternate = "beneficiaries")
    @Expose
    private ArrayList<BenisObject> benis;
    private ArrayList<LedgerObject> ledgerReport;
    private ArrayList<FundDCObject> fundDCReport;
    private ArrayList<FundOrderReportObject> fundOrderReport;

    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public boolean getIsAppValid() {
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


    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSid() {
        return sid;
    }

    public ArrayList<RechargeStatus> getRechargeReport() {
        return rechargeReport;
    }

    public void setRechargeReport(ArrayList<RechargeStatus> rechargeReport) {
        this.rechargeReport = rechargeReport;
    }

    public ArrayList<BenisObject> getBenis() {
        return benis;
    }

    public void setBenis(ArrayList<BenisObject> benis) {
        this.benis = benis;
    }

    public ArrayList<LedgerObject> getLedgerReport() {
        return ledgerReport;
    }

    public void setLedgerReport(ArrayList<LedgerObject> ledgerReport) {
        this.ledgerReport = ledgerReport;
    }


    public ArrayList<FundDCObject> getFundDCReport() {
        return fundDCReport;
    }

    public void setFundDCReport(ArrayList<FundDCObject> fundDCReport) {
        this.fundDCReport = fundDCReport;
    }

    public ArrayList<RechargeStatus> getAePsDetail() {
        return aePsDetail;
    }

    public ArrayList<RechargeStatus> getRecentRecharge() {
        return recentRecharge;
    }

    public ArrayList<FundOrderReportObject> getFundOrderReport() {
        return fundOrderReport;
    }

    public void setFundOrderReport(ArrayList<FundOrderReportObject> fundOrderReport) {
        this.fundOrderReport = fundOrderReport;
    }


    public ArrayList<SlabCommtObject> getSlabCommissions() {
        return slabCommissions;
    }

    public void setSlabCommissions(ArrayList<SlabCommtObject> slabCommissions) {
        this.slabCommissions = slabCommissions;
    }

    public ArrayList<MoveToWalletReportData> getMoveToWalletReport() {
        return moveToWalletReport;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public boolean isResendAvailable() {
        return isResendAvailable;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getTid() {
        return tid;
    }

    public String getLiveID() {
        return liveID;
    }

    public ArrayList<DmtReportObject> getDmtReport() {
        return dmtReport;
    }

    public void setDmtReport(ArrayList<DmtReportObject> dmtReport) {
        this.dmtReport = dmtReport;
    }

    public int getOrderID() {
        return orderID;
    }
}
