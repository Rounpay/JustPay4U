package com.solution.app.justpay4u.Api.Fintech.Object;

public class RecentDmrLogin {

    String name, number, limit;

    public RecentDmrLogin(String name, String number, String limit) {
        this.name = name;
        this.number = number;
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getLimit() {
        return limit;
    }
}
