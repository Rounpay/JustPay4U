package com.solution.app.justpay4u.Api.Fintech.Object;

public class PidData {

    public PidDataResp resp;

    public PidDataDeviceInfo deviceInfo;

    public PidDataSkey skey;

    public String hmac;

    public PidDataData data;

    public PidDataResp getResp() {
        return resp;
    }

    public void setResp(PidDataResp resp) {
        this.resp = resp;
    }

    public PidDataDeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(PidDataDeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public PidDataSkey getSkey() {
        return skey;
    }

    public void setSkey(PidDataSkey skey) {
        this.skey = skey;
    }

    public String getHmac() {
        return hmac;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
    }

    public PidDataData getData() {
        return data;
    }

    public void setData(PidDataData data) {
        this.data = data;
    }
}
