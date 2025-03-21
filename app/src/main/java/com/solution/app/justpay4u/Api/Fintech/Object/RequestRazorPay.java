package com.solution.app.justpay4u.Api.Fintech.Object;

public class RequestRazorPay {
    String key_id;
    String order_id;
    String name;
    String description;
    String image;
    String prefill_name;
    String prefill_contact;
    String prefill_email;
    String callback_url;
    String cancel_url;
    double amount;

    public String getKey_id() {
        return key_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getPrefill_name() {
        return prefill_name;
    }

    public String getPrefill_contact() {
        return prefill_contact;
    }

    public String getPrefill_email() {
        return prefill_email;
    }

    public String getCallback_url() {
        return callback_url;
    }

    public String getCancel_url() {
        return cancel_url;
    }

    public double getAmount() {
        return amount;
    }
}
