package com.solution.app.justpay4u.Fintech.Employee.Api.Object;

public class GetTodayTransactorsData {

    int id;
    int userId;
    String userName;
    String outletName;
    String mobilleNo;
    int transactionCount;
    double amount;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getOutletName() {
        return outletName;
    }

    public String getMobilleNo() {
        return mobilleNo;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public double getAmount() {
        return amount;
    }
}
