package com.solution.app.justpay4u.Api.Fintech.Response;

public class CreateSenderResponse {
    String remainingLimit, availbleLimit;
    private boolean isSenderNotExists;
    private boolean isEKYCAvailable;
    private boolean isOTPGenerated;
    private boolean isOTPRequired;
    private String sid;
    private String senderName;
    private String senderBalance;
    private int statuscode;
    private String msg;
    private boolean isVersionValid;
    private boolean isAppValid;

    public boolean isSenderNotExists() {
        return isSenderNotExists;
    }

    public String getSenderName() {
        return senderName != null ? senderName : "";
    }

    public String getSenderBalance() {
        return senderBalance;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public String getRemainingLimit() {
        return remainingLimit;
    }

    public String getAvailbleLimit() {
        return availbleLimit;
    }

    public boolean isEKYCAvailable() {
        return isEKYCAvailable;
    }

    public boolean isOTPGenerated() {
        return isOTPGenerated;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public String getSid() {
        return sid;
    }
}
