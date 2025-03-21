package com.solution.app.justpay4u.Api.Fintech.Object;

public class ReceiptObject {
    String name, value;

    public ReceiptObject(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
