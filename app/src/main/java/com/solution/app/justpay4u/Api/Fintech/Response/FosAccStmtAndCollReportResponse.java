package com.solution.app.justpay4u.Api.Fintech.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.AscReport;
import com.solution.app.justpay4u.Api.Fintech.Object.LedgerObject;

import java.util.ArrayList;

public class FosAccStmtAndCollReportResponse implements Parcelable {


    @SerializedName("ascReport")
    @Expose
    private ArrayList<AscReport> ascReport = null;

    @SerializedName("accountStatementSummary")
    @Expose
    private ArrayList<LedgerObject> accountStatementSummary = null;
    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;
    @SerializedName("checkID")
    @Expose
    private int checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    private boolean isPasswordExpired;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("isLookUpFromAPI")
    @Expose
    private boolean isLookUpFromAPI;
    @SerializedName("isDTHInfoCall")
    @Expose
    private boolean isDTHInfoCall;
    @SerializedName("isShowPDFPlan")
    @Expose
    private boolean isShowPDFPlan;
    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("isOTPRequired")
    @Expose
    private boolean isOTPRequired;
    @SerializedName("isResendAvailable")
    @Expose
    private boolean isResendAvailable;
    @SerializedName("getID")
    @Expose
    private int getID;
    @SerializedName("isDTHInfo")
    @Expose
    private boolean isDTHInfo;
    @SerializedName("isRoffer")
    @Expose
    private boolean isRoffer;
    @SerializedName("isGreen")
    @Expose
    private boolean isGreen;


    protected FosAccStmtAndCollReportResponse(Parcel in) {
        ascReport = in.createTypedArrayList(AscReport.CREATOR);
        accountStatementSummary = in.createTypedArrayList(LedgerObject.CREATOR);
        statuscode = in.readInt();
        msg = in.readString();
        isVersionValid = in.readByte() != 0;
        isAppValid = in.readByte() != 0;
        checkID = in.readInt();
        isPasswordExpired = in.readByte() != 0;
        mobileNo = in.readString();
        emailID = in.readString();
        isLookUpFromAPI = in.readByte() != 0;
        isDTHInfoCall = in.readByte() != 0;
        isShowPDFPlan = in.readByte() != 0;
        sid = in.readString();
        isOTPRequired = in.readByte() != 0;
        isResendAvailable = in.readByte() != 0;
        getID = in.readInt();
        isDTHInfo = in.readByte() != 0;
        isRoffer = in.readByte() != 0;
        isGreen = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(ascReport);
        dest.writeTypedList(accountStatementSummary);
        dest.writeInt(statuscode);
        dest.writeString(msg);
        dest.writeByte((byte) (isVersionValid ? 1 : 0));
        dest.writeByte((byte) (isAppValid ? 1 : 0));
        dest.writeInt(checkID);
        dest.writeByte((byte) (isPasswordExpired ? 1 : 0));
        dest.writeString(mobileNo);
        dest.writeString(emailID);
        dest.writeByte((byte) (isLookUpFromAPI ? 1 : 0));
        dest.writeByte((byte) (isDTHInfoCall ? 1 : 0));
        dest.writeByte((byte) (isShowPDFPlan ? 1 : 0));
        dest.writeString(sid);
        dest.writeByte((byte) (isOTPRequired ? 1 : 0));
        dest.writeByte((byte) (isResendAvailable ? 1 : 0));
        dest.writeInt(getID);
        dest.writeByte((byte) (isDTHInfo ? 1 : 0));
        dest.writeByte((byte) (isRoffer ? 1 : 0));
        dest.writeByte((byte) (isGreen ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<FosAccStmtAndCollReportResponse> CREATOR = new Creator<FosAccStmtAndCollReportResponse>() {
        @Override
        public FosAccStmtAndCollReportResponse createFromParcel(Parcel in) {
            return new FosAccStmtAndCollReportResponse(in);
        }

        @Override
        public FosAccStmtAndCollReportResponse[] newArray(int size) {
            return new FosAccStmtAndCollReportResponse[size];
        }
    };

    public ArrayList<AscReport> getAscReport() {
        return (ArrayList<AscReport>) ascReport;
    }

    public void setAscReport(ArrayList<AscReport> ascReport) {
        this.ascReport = ascReport;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public void setIsVersionValid(boolean isVersionValid) {
        this.isVersionValid = isVersionValid;
    }

    public boolean getIsAppValid() {
        return isAppValid;
    }

    public void setIsAppValid(boolean isAppValid) {
        this.isAppValid = isAppValid;
    }

    public int getCheckID() {
        return checkID;
    }

    public void setCheckID(int checkID) {
        this.checkID = checkID;
    }

    public boolean getIsPasswordExpired() {
        return isPasswordExpired;
    }

    public void setIsPasswordExpired(boolean isPasswordExpired) {
        this.isPasswordExpired = isPasswordExpired;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public boolean getIsLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public void setIsLookUpFromAPI(boolean isLookUpFromAPI) {
        this.isLookUpFromAPI = isLookUpFromAPI;
    }

    public boolean getIsDTHInfoCall() {
        return isDTHInfoCall;
    }

    public void setIsDTHInfoCall(boolean isDTHInfoCall) {
        this.isDTHInfoCall = isDTHInfoCall;
    }

    public boolean getIsShowPDFPlan() {
        return isShowPDFPlan;
    }

    public void setIsShowPDFPlan(boolean isShowPDFPlan) {
        this.isShowPDFPlan = isShowPDFPlan;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public boolean getIsOTPRequired() {
        return isOTPRequired;
    }

    public void setIsOTPRequired(boolean isOTPRequired) {
        this.isOTPRequired = isOTPRequired;
    }

    public boolean getIsResendAvailable() {
        return isResendAvailable;
    }

    public void setIsResendAvailable(boolean isResendAvailable) {
        this.isResendAvailable = isResendAvailable;
    }

    public int getGetID() {
        return getID;
    }

    public void setGetID(int getID) {
        this.getID = getID;
    }

    public boolean getIsDTHInfo() {
        return isDTHInfo;
    }

    public void setIsDTHInfo(boolean isDTHInfo) {
        this.isDTHInfo = isDTHInfo;
    }

    public ArrayList<LedgerObject> getAccountStatementSummary() {
        return accountStatementSummary;
    }

    public boolean getIsRoffer() {
        return isRoffer;
    }

    public void setIsRoffer(boolean isRoffer) {
        this.isRoffer = isRoffer;
    }

    public boolean getIsGreen() {
        return isGreen;
    }

    public void setIsGreen(boolean isGreen) {
        this.isGreen = isGreen;
    }
}