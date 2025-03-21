package com.solution.app.justpay4u.Fintech.Employee.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostDailyClosingResponse {
    @SerializedName("data")
    @Expose
    public PostDailyClosingData data;
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid;
    @SerializedName("checkID")
    @Expose
    public int checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    public boolean isPasswordExpired;
    @SerializedName("mobileNo")
    @Expose
    public Object mobileNo;
    @SerializedName("emailID")
    @Expose
    public Object emailID;
    @SerializedName("isLookUpFromAPI")
    @Expose
    public boolean isLookUpFromAPI;
    @SerializedName("isDTHInfoCall")
    @Expose
    public boolean isDTHInfoCall;
    @SerializedName("isShowPDFPlan")
    @Expose
    public boolean isShowPDFPlan;
    @SerializedName("sid")
    @Expose
    public Object sid;
    @SerializedName("isOTPRequired")
    @Expose
    public boolean isOTPRequired;
    @SerializedName("isResendAvailable")
    @Expose
    public boolean isResendAvailable;
    @SerializedName("getID")
    @Expose
    public int getID;
    @SerializedName("isDTHInfo")
    @Expose
    public boolean isDTHInfo;
    @SerializedName("isRoffer")
    @Expose
    public boolean isRoffer;

    public PostDailyClosingData getData() {
        return data;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public int getCheckID() {
        return checkID;
    }

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }

    public Object getMobileNo() {
        return mobileNo;
    }

    public Object getEmailID() {
        return emailID;
    }

    public boolean isLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public boolean isDTHInfoCall() {
        return isDTHInfoCall;
    }

    public boolean isShowPDFPlan() {
        return isShowPDFPlan;
    }

    public Object getSid() {
        return sid;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public boolean isResendAvailable() {
        return isResendAvailable;
    }

    public int getGetID() {
        return getID;
    }

    public boolean isDTHInfo() {
        return isDTHInfo;
    }

    public boolean isRoffer() {
        return isRoffer;
    }

    public class PostDailyClosingData {
        @SerializedName("statuscode")
        @Expose
        public int statuscode;
        @SerializedName("msg")
        @Expose
        public String msg;
        @SerializedName("commonInt")
        @Expose
        public int commonInt;
        @SerializedName("commonInt2")
        @Expose
        public int commonInt2;
        @SerializedName("commonStr")
        @Expose
        public Object commonStr;
        @SerializedName("commonStr2")
        @Expose
        public Object commonStr2;
        @SerializedName("commonStr3")
        @Expose
        public Object commonStr3;
        @SerializedName("commonStr4")
        @Expose
        public Object commonStr4;
        @SerializedName("commonInt3")
        @Expose
        public int commonInt3;
        @SerializedName("commonBool")
        @Expose
        public boolean commonBool;
        @SerializedName("flag")
        @Expose
        public String flag;
        @SerializedName("errorCode")
        @Expose
        public int errorCode;
        @SerializedName("errorMsg")
        @Expose
        public Object errorMsg;
        @SerializedName("reffID")
        @Expose
        public Object reffID;
        @SerializedName("status")
        @Expose
        public int status;

        public int getStatuscode() {
            return statuscode;
        }

        public String getMsg() {
            return msg;
        }

        public int getCommonInt() {
            return commonInt;
        }

        public int getCommonInt2() {
            return commonInt2;
        }

        public Object getCommonStr() {
            return commonStr;
        }

        public Object getCommonStr2() {
            return commonStr2;
        }

        public Object getCommonStr3() {
            return commonStr3;
        }

        public Object getCommonStr4() {
            return commonStr4;
        }

        public int getCommonInt3() {
            return commonInt3;
        }

        public boolean isCommonBool() {
            return commonBool;
        }

        public String getFlag() {
            return flag;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public Object getErrorMsg() {
            return errorMsg;
        }

        public Object getReffID() {
            return reffID;
        }

        public int getStatus() {
            return status;
        }
    }
}
