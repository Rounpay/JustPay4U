package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class RechargeStatus implements Parcelable {


    private String tid;
    private String transactionID;
    private String userID;
    private String outletNo;
    private String outlet;
    private String account;
    private int oid;
    private String operator;
    private String lastBalance;
    private String requestedAmount;
    private String amount;
    private String balance;
    private String entryDate;
    private String liveID, extraParam;
    private int _Type;
    private String type_;
    private String apiRequestID;
    private String modifyDate;
    private String optional1;
    private String optional2;
    private String optional3;
    private String optional4;
    private String refundStatus;
    private String refundStatus_;
    private boolean isWTR;
    private boolean commType;
    private String commission;
    private String requestMode;

    protected RechargeStatus(Parcel in) {
        tid = in.readString();
        transactionID = in.readString();
        userID = in.readString();
        outletNo = in.readString();
        outlet = in.readString();
        account = in.readString();
        oid = in.readInt();
        operator = in.readString();
        lastBalance = in.readString();
        requestedAmount = in.readString();
        amount = in.readString();
        balance = in.readString();
        entryDate = in.readString();
        liveID = in.readString();
        extraParam = in.readString();
        _Type = in.readInt();
        type_ = in.readString();
        apiRequestID = in.readString();
        modifyDate = in.readString();
        optional1 = in.readString();
        optional2 = in.readString();
        optional3 = in.readString();
        optional4 = in.readString();
        refundStatus = in.readString();
        refundStatus_ = in.readString();
        isWTR = in.readByte() != 0;
        commType = in.readByte() != 0;
        commission = in.readString();
        requestMode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tid);
        dest.writeString(transactionID);
        dest.writeString(userID);
        dest.writeString(outletNo);
        dest.writeString(outlet);
        dest.writeString(account);
        dest.writeInt(oid);
        dest.writeString(operator);
        dest.writeString(lastBalance);
        dest.writeString(requestedAmount);
        dest.writeString(amount);
        dest.writeString(balance);
        dest.writeString(entryDate);
        dest.writeString(liveID);
        dest.writeString(extraParam);
        dest.writeInt(_Type);
        dest.writeString(type_);
        dest.writeString(apiRequestID);
        dest.writeString(modifyDate);
        dest.writeString(optional1);
        dest.writeString(optional2);
        dest.writeString(optional3);
        dest.writeString(optional4);
        dest.writeString(refundStatus);
        dest.writeString(refundStatus_);
        dest.writeByte((byte) (isWTR ? 1 : 0));
        dest.writeByte((byte) (commType ? 1 : 0));
        dest.writeString(commission);
        dest.writeString(requestMode);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<RechargeStatus> CREATOR = new Creator<RechargeStatus>() {
        @Override
        public RechargeStatus createFromParcel(Parcel in) {
            return new RechargeStatus(in);
        }

        @Override
        public RechargeStatus[] newArray(int size) {
            return new RechargeStatus[size];
        }
    };

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOutletNo() {
        return outletNo;
    }

    public void setOutletNo(String outletNo) {
        this.outletNo = outletNo;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getLastBalance() {
        return lastBalance;
    }

    public void setLastBalance(String lastBalance) {
        this.lastBalance = lastBalance;
    }

    public String getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(String requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getLiveID() {
        return liveID;
    }

    public void setLiveID(String liveID) {
        this.liveID = liveID;
    }

    public int get_Type() {
        return _Type;
    }


    public String getType_() {
        return type_ != null ? type_ : "";
    }

    public void setType_(String type_) {
        this.type_ = type_;
    }

    public String getApiRequestID() {
        return apiRequestID;
    }

    public void setApiRequestID(String apiRequestID) {
        this.apiRequestID = apiRequestID;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getOptional1() {
        return optional1;
    }

    public void setOptional1(String optional1) {
        this.optional1 = optional1;
    }

    public String getOptional2() {
        return optional2;
    }

    public void setOptional2(String optional2) {
        this.optional2 = optional2;
    }

    public String getRequestMode() {
        return requestMode;
    }

    public String getOptional3() {
        return optional3;
    }

    public void setOptional3(String optional3) {
        this.optional3 = optional3;
    }

    public String getOptional4() {
        return optional4;
    }

    public void setOptional4(String optional4) {
        this.optional4 = optional4;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundStatus_() {
        return refundStatus_;
    }

    public void setRefundStatus_(String refundStatus_) {
        this.refundStatus_ = refundStatus_;
    }

    public boolean getIsWTR() {
        return isWTR;
    }

    public void setWTR(boolean WTR) {
        isWTR = WTR;
    }

    public boolean getCommType() {
        return commType;
    }

    public void setCommType(boolean commType) {
        this.commType = commType;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getExtraParam() {
        return extraParam;
    }
}
