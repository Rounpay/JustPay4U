package com.solution.app.justpay4u.Api.Shopping.Response;

/**
 * Created by Vishnu Agarwal on 17,December,2019
 */
public class WalletBalanceResponse {
    String $id;
    boolean status;
    String message;
    double data;
    int data1;
    double totalIncome;
    double totalWithdrawal;
    double rechargewallet;

    public String get$id() {
        return $id;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return String.format("%.2f", data);
    }

    public String getRechargewallet() {
        return String.format("%.2f", rechargewallet);
    }

    public int getData1() {
        return data1;
    }

    public String getTotalIncome() {
        return String.format("%.2f", totalIncome);
    }

    public String getTotalWithdrawal() {
        return String.format("%.2f", totalWithdrawal);
    }
}
