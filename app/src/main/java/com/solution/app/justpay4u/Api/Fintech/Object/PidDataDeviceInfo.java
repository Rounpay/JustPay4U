package com.solution.app.justpay4u.Api.Fintech.Object;

public class PidDataDeviceInfo {

    public PidDataAdditionalInfo additionalInfo;

    public String dpId;

    public String rdsId;

    public String rdsVer;

    public String mi;

    public String mc;

    public String dc;

    public PidDataAdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(PidDataAdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getRdsId() {
        return rdsId;
    }

    public void setRdsId(String rdsId) {
        this.rdsId = rdsId;
    }

    public String getRdsVer() {
        return rdsVer;
    }

    public void setRdsVer(String rdsVer) {
        this.rdsVer = rdsVer;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }
}
