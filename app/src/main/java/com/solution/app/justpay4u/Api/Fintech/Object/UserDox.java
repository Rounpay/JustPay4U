package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDox {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("docTypeID")
    @Expose
    public int docTypeID;
    @SerializedName("docName")
    @Expose
    public String docName;
    @SerializedName("isOptional")
    @Expose
    public boolean isOptional;
    @SerializedName("remark")
    @Expose
    public String remark;
    @SerializedName("modifyDate")
    @Expose
    public String modifyDate;
    @SerializedName("userId")
    @Expose
    public int userId;
    @SerializedName("statusCode")
    @Expose
    public int statusCode;
    @SerializedName("entryDate")
    @Expose
    public String entryDate;
    @SerializedName("docUrl")
    @Expose
    public String docUrl;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("verifyStatus")
    @Expose
    public int verifyStatus;
    @SerializedName("loginId")
    @Expose
    public int loginId;
    @SerializedName("dRemark")
    @Expose
    public String dRemark;
    @SerializedName("isOutlet")
    @Expose
    public int isOutlet;
    @SerializedName("loginTypeID")
    @Expose
    public int loginTypeID;
    @SerializedName("outletID")
    @Expose
    public int outletID;
    @SerializedName("kycStatus")
    @Expose
    public int kycStatus;
    @SerializedName("msg")
    @Expose
    public String msg;

    public int getId() {
        return id;
    }

    public int getDocTypeID() {
        return docTypeID;
    }

    public String getDocName() {
        return docName;
    }

    public boolean getOptional() {
        return isOptional;
    }

    public String getRemark() {
        return remark;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public int getUserId() {
        return userId;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public String getDescription() {
        return description;
    }

    public int getVerifyStatus() {
        return verifyStatus;
    }

    public int getLoginId() {
        return loginId;
    }

    public String getdRemark() {
        if (dRemark != null && !dRemark.isEmpty()) {
            return dRemark;
        } else {
            return "";
        }
    }

    public int getIsOutlet() {
        return isOutlet;
    }

    public int getLoginTypeID() {
        return loginTypeID;
    }

    public int getOutletID() {
        return outletID;
    }

    public int getKycStatus() {
        return kycStatus;
    }

    public String getMsg() {
        return msg;
    }
}
