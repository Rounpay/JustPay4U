package com.solution.app.justpay4u.Fintech.Employee.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPstReport {

    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("shid")
    @Expose
    public int shid;
    @SerializedName("shDetail")
    @Expose
    public String shDetail;
    @SerializedName("chid")
    @Expose
    public int chid;
    @SerializedName("cDetail")
    @Expose
    public String cDetail;
    @SerializedName("zid")
    @Expose
    public int zid;
    @SerializedName("zDetail")
    @Expose
    public String zDetail;
    @SerializedName("aid")
    @Expose
    public int aid;
    @SerializedName("aDetail")
    @Expose
    public String aDetail;

    @SerializedName("userID")
    @Expose
    public int userID;
    @SerializedName("user")
    @Expose
    public String user;
    @SerializedName("userMobile")
    @Expose
    public String userMobile;
    @SerializedName("uRoleID")
    @Expose
    public int uRoleID;
    @SerializedName("priLM")
    @Expose
    public double priLM;
    @SerializedName("priLMTD")
    @Expose
    public double priLMTD;
    @SerializedName("pri")
    @Expose
    public double pri;
    @SerializedName("pGrowth")
    @Expose
    public double pGrowth;
    @SerializedName("secLM")
    @Expose
    public double secLM;
    @SerializedName("secLMTD")
    @Expose
    public double secLMTD;
    @SerializedName("sec")
    @Expose
    public double sec;
    @SerializedName("sGrowth")
    @Expose
    public double sGrowth;
    @SerializedName("terLM")
    @Expose
    public double terLM;
    @SerializedName("terLMTD")
    @Expose
    public double terLMTD;
    @SerializedName("ter")
    @Expose
    public double ter;
    @SerializedName("tGrowth")
    @Expose
    public double tGrowth;
    @SerializedName("tOutletLM")
    @Expose
    public int tOutletLM;
    @SerializedName("tOutletLMTD")
    @Expose
    public int tOutletLMTD;
    @SerializedName("tOutlet")
    @Expose
    public int tOutlet;
    @SerializedName("tNewOutletLM")
    @Expose
    public int tNewOutletLM;
    @SerializedName("tNewOutletLMTD")
    @Expose
    public int tNewOutletLMTD;
    @SerializedName("tNewOutlet")
    @Expose
    public int tNewOutlet;
    @SerializedName("oGrowth")
    @Expose
    public double oGrowth;
    @SerializedName("packageSellLM")
    @Expose
    public double packageSellLM;
    @SerializedName("packageSellLMTD")
    @Expose
    public double packageSellLMTD;
    @SerializedName("packageSell")
    @Expose
    public double packageSell;
    @SerializedName("packageGrowth")
    @Expose
    public double packageGrowth;
    @SerializedName("amid")
    @Expose
    public int amid;
    @SerializedName("amMobileNo")
    @Expose
    public Object amMobileNo;
    @SerializedName("am")
    @Expose
    public String am;
    @SerializedName("amRoleID")
    @Expose
    public int amRoleID;
    @SerializedName("entryDate")
    @Expose
    public String entryDate;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public int getId() {
        return id;
    }

    public int getShid() {
        return shid;
    }

    public String getShDetail() {
        return shDetail;
    }

    public int getChid() {
        return chid;
    }

    public String getcDetail() {
        return cDetail;
    }

    public int getZid() {
        return zid;
    }

    public String getzDetail() {
        return zDetail;
    }

    public int getAid() {
        return aid;
    }

    public String getaDetail() {
        return aDetail;
    }

    public int getUserID() {
        return userID;
    }

    public String getUser() {
        return user;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public int getuRoleID() {
        return uRoleID;
    }

    public double getPriLM() {
        return priLM;
    }

    public double getPriLMTD() {
        return priLMTD;
    }

    public double getPri() {
        return pri;
    }

    public double getpGrowth() {
        return pGrowth;
    }

    public double getSecLM() {
        return secLM;
    }

    public double getSecLMTD() {
        return secLMTD;
    }

    public double getSec() {
        return sec;
    }

    public double getsGrowth() {
        return sGrowth;
    }

    public double getTerLM() {
        return terLM;
    }

    public double getTerLMTD() {
        return terLMTD;
    }

    public double getTer() {
        return ter;
    }

    public double gettGrowth() {
        return tGrowth;
    }

    public int gettOutletLM() {
        return tOutletLM;
    }

    public int gettOutletLMTD() {
        return tOutletLMTD;
    }

    public int gettOutlet() {
        return tOutlet;
    }

    public int gettNewOutletLM() {
        return tNewOutletLM;
    }

    public int gettNewOutletLMTD() {
        return tNewOutletLMTD;
    }

    public int gettNewOutlet() {
        return tNewOutlet;
    }

    public double getoGrowth() {
        return oGrowth;
    }

    public double getPackageSellLM() {
        return packageSellLM;
    }

    public double getPackageSellLMTD() {
        return packageSellLMTD;
    }

    public double getPackageSell() {
        return packageSell;
    }

    public double getPackageGrowth() {
        return packageGrowth;
    }

    public int getAmid() {
        return amid;
    }

    public Object getAmMobileNo() {
        return amMobileNo;
    }

    public String getAm() {
        return am;
    }



    public int getAmRoleID() {
        return amRoleID;
    }

    public String getEntryDate() {
        return entryDate;
    }
}
