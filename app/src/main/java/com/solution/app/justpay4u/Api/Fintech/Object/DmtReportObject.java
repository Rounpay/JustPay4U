package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DmtReportObject {

    @SerializedName("_Type")
    @Expose
    public int _Type;
    @SerializedName("type_")
    @Expose
    public String type_;
    @SerializedName("refundStatus")
    @Expose
    public int refundStatus;
    @SerializedName("refundStatus_")
    @Expose
    public String refundStatus_;
    @SerializedName("opening")
    @Expose
    public double opening;
    @SerializedName("outletUserMobile")
    @Expose
    public String outletUserMobile;
    @SerializedName("outletUserCompany")
    @Expose
    public String outletUserCompany;
    @SerializedName("senderMobile")
    @Expose
    public String senderMobile;
    @SerializedName("groupID")
    @Expose
    public String groupID;
    @SerializedName("ccf")
    @Expose
    public double ccf;
    @SerializedName("surcharge")
    @Expose
    public double surcharge;
    @SerializedName("refundGST")
    @Expose
    public double refundGST;
    @SerializedName("amtWithTDS")
    @Expose
    public double amtWithTDS;
    @SerializedName("credited_Amount")
    @Expose
    public double creditedAmount;
    @SerializedName("ccName")
    @Expose
    public String ccName;
    @SerializedName("ccMobileNo")
    @Expose
    public Object ccMobileNo;
    @SerializedName("_ServiceID")
    @Expose
    public int serviceID;
    @SerializedName("tid")
    @Expose
    public int tid;
    @SerializedName("transactionID")
    @Expose
    public String transactionID;
    @SerializedName("prefix")
    @Expose
    public Object prefix;
    @SerializedName("userID")
    @Expose
    public int userID;
    @SerializedName("role")
    @Expose
    public Object role;
    @SerializedName("outletNo")
    @Expose
    public String outletNo;
    @SerializedName("outlet")
    @Expose
    public String outlet;
    @SerializedName("account")
    @Expose
    public String account;
    @SerializedName("oid")
    @Expose
    public int oid;
    @SerializedName("operator")
    @Expose
    public String operator;
    @SerializedName("lastBalance")
    @Expose
    public double lastBalance;
    @SerializedName("requestedAmount")
    @Expose
    public double requestedAmount;
    @SerializedName("amount")
    @Expose
    public double amount;
    @SerializedName("balance")
    @Expose
    public double balance;
    @SerializedName("slabCommType")
    @Expose
    public Object slabCommType;
    @SerializedName("commission")
    @Expose
    public double commission;
    @SerializedName("entryDate")
    @Expose
    public String entryDate;
    @SerializedName("api")
    @Expose
    public String api;
    @SerializedName("liveID")
    @Expose
    public String liveID;
    @SerializedName("vendorID")
    @Expose
    public String vendorID;
    @SerializedName("apiRequestID")
    @Expose
    public String apiRequestID;
    @SerializedName("modifyDate")
    @Expose
    public String modifyDate;
    @SerializedName("optional1")
    @Expose
    public String optional1;
    @SerializedName("optional2")
    @Expose
    public String optional2;
    @SerializedName("optional3")
    @Expose
    public String optional3;
    @SerializedName("optional4")
    @Expose
    public String optional4;
    @SerializedName("display1")
    @Expose
    public Object display1;
    @SerializedName("display2")
    @Expose
    public Object display2;
    @SerializedName("display3")
    @Expose
    public Object display3;
    @SerializedName("display4")
    @Expose
    public Object display4;
    @SerializedName("switchingName")
    @Expose
    public Object switchingName;
    @SerializedName("circleName")
    @Expose
    public Object circleName;
    @SerializedName("isWTR")
    @Expose
    public boolean isWTR;
    @SerializedName("commType")
    @Expose
    public boolean commType;
    @SerializedName("gstAmount")
    @Expose
    public double gstAmount;
    @SerializedName("tdsAmount")
    @Expose
    public double tdsAmount;
    @SerializedName("customerNo")
    @Expose
    public Object customerNo;
    @SerializedName("ccMobile")
    @Expose
    public String ccMobile;
    @SerializedName("apiCode")
    @Expose
    public Object apiCode;
    @SerializedName("requestMode")
    @Expose
    public String requestMode;

    public int get_Type() {
        return _Type;
    }

    public String getType_() {
        return type_;
    }

    public int getRefundStatus() {
        return refundStatus;
    }

    public String getRefundStatus_() {
        return refundStatus_;
    }

    public double getOpening() {
        return opening;
    }

    public String getOutletUserMobile() {
        return outletUserMobile;
    }

    public String getOutletUserCompany() {
        return outletUserCompany;
    }

    public String getSenderMobile() {
        return senderMobile;
    }

    public String getGroupID() {
        return groupID;
    }

    public double getCcf() {
        return ccf;
    }

    public double getSurcharge() {
        return surcharge;
    }

    public double getRefundGST() {
        return refundGST;
    }

    public double getAmtWithTDS() {
        return amtWithTDS;
    }

    public double getCreditedAmount() {
        return creditedAmount;
    }

    public String getCcName() {
        return ccName;
    }

    public Object getCcMobileNo() {
        return ccMobileNo;
    }

    public int getServiceID() {
        return serviceID;
    }

    public int getTid() {
        return tid;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public Object getPrefix() {
        return prefix;
    }

    public int getUserID() {
        return userID;
    }

    public Object getRole() {
        return role;
    }

    public String getOutletNo() {
        return outletNo;
    }

    public String getOutlet() {
        return outlet;
    }

    public String getAccount() {
        return account;
    }

    public int getOid() {
        return oid;
    }

    public String getOperator() {
        return operator;
    }

    public double getLastBalance() {
        return lastBalance;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }

    public Object getSlabCommType() {
        return slabCommType;
    }

    public double getCommission() {
        return commission;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getApi() {
        return api;
    }

    public String getLiveID() {
        return liveID;
    }

    public String getVendorID() {
        return vendorID;
    }

    public String getApiRequestID() {
        return apiRequestID;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public String getOptional1() {
        return optional1;
    }

    public String getOptional2() {
        return optional2;
    }

    public String getOptional3() {
        return optional3;
    }

    public String getOptional4() {
        return optional4;
    }

    public Object getDisplay1() {
        return display1;
    }

    public Object getDisplay2() {
        return display2;
    }

    public Object getDisplay3() {
        return display3;
    }

    public Object getDisplay4() {
        return display4;
    }

    public Object getSwitchingName() {
        return switchingName;
    }

    public Object getCircleName() {
        return circleName;
    }

    public boolean isWTR() {
        return isWTR;
    }

    public boolean isCommType() {
        return commType;
    }

    public double getGstAmount() {
        return gstAmount;
    }

    public double getTdsAmount() {
        return tdsAmount;
    }

    public Object getCustomerNo() {
        return customerNo;
    }

    public String getCcMobile() {
        return ccMobile;
    }

    public Object getApiCode() {
        return apiCode;
    }

    public String getRequestMode() {
        return requestMode;
    }
}
