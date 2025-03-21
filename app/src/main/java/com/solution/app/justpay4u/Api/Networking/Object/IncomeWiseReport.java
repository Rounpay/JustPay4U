package com.solution.app.justpay4u.Api.Networking.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class IncomeWiseReport {

    @SerializedName("resultCode")
    @Expose
    private int resultCode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("tid")
    @Expose
    private int tid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("binaryIncome")
    @Expose
    private double binaryIncome;
    @SerializedName("lapsIncome")
    @Expose
    private int lapsIncome;
    @SerializedName("entryDate")
    @Expose
    private String entryDate;
    @SerializedName("payoutDate")
    @Expose
    private String payoutDate;
    @SerializedName("creditedAmount")
    @Expose
    private double creditedAmount;
    @SerializedName("holdCommAmt")
    @Expose
    private double holdCommAmt;
    @SerializedName("adminCharge")
    @Expose
    private double adminCharge;
    @SerializedName("incomeOPID")
    @Expose
    private String incomeOPID;
    @SerializedName("fromName")
    @Expose
    private String fromName;
    @SerializedName("fromUserId")
    @Expose
    private String fromUserId;
    @SerializedName("toName")
    @Expose
    private String toName;
    @SerializedName("toUserId")
    @Expose
    private String toUserId;
    @SerializedName("incomeName")
    @Expose
    private String incomeName;
    @SerializedName("fromTeamOf")
    @Expose
    private String fromTeamOf;
    @SerializedName("todayMatchBusiness")
    @Expose
    private int todayMatchBusiness;
    @SerializedName("totalRightBusiness")
    @Expose
    private int totalRightBusiness;
    @SerializedName("totalLeftBusiness")
    @Expose
    private int totalLeftBusiness;
    @SerializedName("levelNo")
    @Expose
    private int levelNo;
    @SerializedName("totalCreditedAmount")
    @Expose
    private double totalCreditedAmount;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBinaryIncome() {
        return binaryIncome;
    }

    public void setBinaryIncome(double binaryIncome) {
        this.binaryIncome = binaryIncome;
    }

    public int getLapsIncome() {
        return lapsIncome;
    }

    public void setLapsIncome(int lapsIncome) {
        this.lapsIncome = lapsIncome;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getPayoutDate() {
        return payoutDate;
    }

    public void setPayoutDate(String payoutDate) {
        this.payoutDate = payoutDate;
    }

    public double getCreditedAmount() {
        return creditedAmount;
    }


    public void setCreditedAmount(double creditedAmount) {
        this.creditedAmount = creditedAmount;
    }

    public double getholdCommAmt() {
        return holdCommAmt;
    }


    public void setholdCommAmt(double holdCommAmt) {
        this.holdCommAmt = holdCommAmt;
    }

    public double getAdminCharge() {
        return adminCharge;
    }

    public void setAdminCharge(double adminCharge) {
        this.adminCharge = adminCharge;
    }

    public String getIncomeOPID() {
        return incomeOPID;
    }

    public void setIncomeOPID(String incomeOPID) {
        this.incomeOPID = incomeOPID;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getIncomeName() {
        return incomeName;
    }

    public void setIncomeName(String incomeName) {
        this.incomeName = incomeName;
    }

    public String getFromTeamOf() {
        return fromTeamOf;
    }

    public void setFromTeamOf(String fromTeamOf) {
        this.fromTeamOf = fromTeamOf;
    }

    public int getTodayMatchBusiness() {
        return todayMatchBusiness;
    }

    public void setTodayMatchBusiness(int todayMatchBusiness) {
        this.todayMatchBusiness = todayMatchBusiness;
    }

    public int getTotalRightBusiness() {
        return totalRightBusiness;
    }

    public void setTotalRightBusiness(int totalRightBusiness) {
        this.totalRightBusiness = totalRightBusiness;
    }

    public int getTotalLeftBusiness() {
        return totalLeftBusiness;
    }

    public void setTotalLeftBusiness(int totalLeftBusiness) {
        this.totalLeftBusiness = totalLeftBusiness;
    }

    public int getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(int levelNo) {
        this.levelNo = levelNo;
    }

    public double getTotalCreditedAmount() {
        return totalCreditedAmount;
    }

    public void setTotalCreditedAmount(double totalCreditedAmount) {
        this.totalCreditedAmount = totalCreditedAmount;
    }

}