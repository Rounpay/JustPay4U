package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeyVals {

    @SerializedName("amount")
    @Expose
    public int amount;
    @SerializedName("api_key")
    @Expose
    public String apiKey;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("currency")
    @Expose
    public String currency;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mode")
    @Expose
    public String mode;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("order_id")
    @Expose
    public int orderId;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("return_url")
    @Expose
    public String returnUrl;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("zip_code")
    @Expose
    public String zipCode;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("client_vpa")
    @Expose
    private String clientVpa;
    @SerializedName("client_txn_id")
    @Expose
    private String clientTxnId;
    @SerializedName("p_info")
    @Expose
    private String pInfo;
    @SerializedName("client_name")
    @Expose
    private String clientName;
    @SerializedName("client_email")
    @Expose
    private String clientEmail;
    @SerializedName("client_mobile")
    @Expose
    private String clientMobile;
    @SerializedName("udf1")
    @Expose
    private String udf1;
    @SerializedName("udf2")
    @Expose
    private String udf2;
    @SerializedName("udf3")
    @Expose
    private String udf3;
    @SerializedName("redirect_url")
    @Expose
    private String redirectUrl;

    public String getUdf1() {
        return udf1;
    }

    public String getUdf2() {
        return udf2;
    }

    public String getUdf3() {
        return udf3;
    }

    public int getAmount() {
        return amount;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getKey() {
        return key;
    }

    public String getClientVpa() {
        return clientVpa;
    }

    public String getClientTxnId() {
        return clientTxnId;
    }

    public String getpInfo() {
        return pInfo;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public String getClientMobile() {
        return clientMobile;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getPhone() {
        return phone;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }
}
