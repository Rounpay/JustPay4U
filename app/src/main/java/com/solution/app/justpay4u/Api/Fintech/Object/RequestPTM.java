package com.solution.app.justpay4u.Api.Fintech.Object;

/**
 * Created by Vishnu Agarwal on 23,January,2020
 */
public class RequestPTM {
    String mid;
    String ordeR_ID;
    String cusT_ID;
    String txN_AMOUNT;
    String channeL_ID;
    String website;
    String mobilE_NO;
    String email;
    String industrY_TYPE_ID;
    String callbacK_URL;
    String merC_UNQ_REF;
    String paymenT_MODE_ONLY;
    String autH_MODE;
    String paymenT_TYPE_ID;
    String banK_CODE;
    String checksumhash;

    public String getMid() {
        return mid;
    }

    public String getOrdeR_ID() {
        return ordeR_ID;
    }

    public String getCusT_ID() {
        return cusT_ID;
    }

    public String getTxN_AMOUNT() {
        return txN_AMOUNT;
    }

    public String getChanneL_ID() {
        return channeL_ID;
    }

    public String getWebsite() {
        return website;
    }

    public String getMobilE_NO() {
        return mobilE_NO;
    }

    public String getEmail() {
        return email;
    }

    public String getIndustrY_TYPE_ID() {
        return industrY_TYPE_ID;
    }

    public String getCallbacK_URL() {
        return callbacK_URL;
    }

    public String getMerC_UNQ_REF() {
        return merC_UNQ_REF;
    }

    public String getPaymenT_MODE_ONLY() {
        return paymenT_MODE_ONLY;
    }

    public String getAutH_MODE() {
        return autH_MODE;
    }

    public String getPaymenT_TYPE_ID() {
        return paymenT_TYPE_ID;
    }

    public String getBanK_CODE() {
        return banK_CODE;
    }

    public String getChecksumhash() {
        return checksumhash;
    }
}
