package com.solution.app.justpay4u.Api.Fintech.Object;

public class MiniBankData {
    int statuscode;
    String msg;
    String bankName;
    String balance;
    String transactionTime;
    String liveID;
    String cardNumber;
    String amount;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBalance() {
        return balance;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public String getLiveID() {
        return liveID;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getAmount() {
        return amount;
    }
}
