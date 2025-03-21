package com.solution.app.justpay4u.Api.Fintech.Object;

public class BenisObject {
    boolean isVerified, impsStatus, neftStatus;
    int transMode;
    private String mobileNo;
    private String ifsc;
    private String accountNo;
    private String bankName;
    private String bankID;
    private String beneName;
    private String beneID;

    public String getBeneName() {
        return beneName;
    }

    public void setBeneName(String beneName) {
        this.beneName = beneName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getBeneID() {
        return beneID;
    }

    public void setBeneID(String beneID) {
        this.beneID = beneID;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public boolean isImpsStatus() {
        return impsStatus;
    }

    public boolean isNeftStatus() {
        return neftStatus;
    }

    public int getTransMode() {
        return transMode;
    }
}
