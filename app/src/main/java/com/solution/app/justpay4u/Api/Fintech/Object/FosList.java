package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FosList {
    @SerializedName("userReports")
    @Expose
    private ArrayList<UserList> userReports = null;
    @SerializedName("canEdit")
    @Expose
    private Boolean canEdit;
    @SerializedName("canAssignPackage")
    @Expose
    private Boolean canAssignPackage;
    @SerializedName("canVerifyDocs")
    @Expose
    private Boolean canVerifyDocs;
    @SerializedName("canFundTransfer")
    @Expose
    private Boolean canFundTransfer;
    @SerializedName("canChangeUserStatus")
    @Expose
    private Boolean canChangeUserStatus;
    @SerializedName("canChangeOTPStatus")
    @Expose
    private Boolean canChangeOTPStatus;
    @SerializedName("canChangeSlab")
    @Expose
    private Boolean canChangeSlab;
    @SerializedName("canChangeRole")
    @Expose
    private Boolean canChangeRole;
    @SerializedName("canAssignAvailablePackage")
    @Expose
    private Boolean canAssignAvailablePackage;
    @SerializedName("loginID")
    @Expose
    private String loginID;
    @SerializedName("rowCount")
    @Expose
    private String rowCount;
    @SerializedName("pegeSetting")
    @Expose
    private PageSetting pegeSetting;
    @SerializedName("canRegeneratePassword")
    @Expose
    private Boolean canRegeneratePassword;

    public ArrayList<UserList> getUserReports() {
        return userReports;
    }

    public void setUserReports(ArrayList<UserList> userReports) {
        this.userReports = userReports;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Boolean getCanAssignPackage() {
        return canAssignPackage;
    }

    public void setCanAssignPackage(Boolean canAssignPackage) {
        this.canAssignPackage = canAssignPackage;
    }

    public Boolean getCanVerifyDocs() {
        return canVerifyDocs;
    }

    public void setCanVerifyDocs(Boolean canVerifyDocs) {
        this.canVerifyDocs = canVerifyDocs;
    }

    public Boolean getCanFundTransfer() {
        return canFundTransfer;
    }

    public void setCanFundTransfer(Boolean canFundTransfer) {
        this.canFundTransfer = canFundTransfer;
    }

    public Boolean getCanChangeUserStatus() {
        return canChangeUserStatus;
    }

    public void setCanChangeUserStatus(Boolean canChangeUserStatus) {
        this.canChangeUserStatus = canChangeUserStatus;
    }

    public Boolean getCanChangeOTPStatus() {
        return canChangeOTPStatus;
    }

    public void setCanChangeOTPStatus(Boolean canChangeOTPStatus) {
        this.canChangeOTPStatus = canChangeOTPStatus;
    }

    public Boolean getCanChangeSlab() {
        return canChangeSlab;
    }

    public void setCanChangeSlab(Boolean canChangeSlab) {
        this.canChangeSlab = canChangeSlab;
    }

    public Boolean getCanChangeRole() {
        return canChangeRole;
    }

    public void setCanChangeRole(Boolean canChangeRole) {
        this.canChangeRole = canChangeRole;
    }

    public Boolean getCanAssignAvailablePackage() {
        return canAssignAvailablePackage;
    }

    public void setCanAssignAvailablePackage(Boolean canAssignAvailablePackage) {
        this.canAssignAvailablePackage = canAssignAvailablePackage;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getRowCount() {
        return rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    public PageSetting getPegeSetting() {
        return pegeSetting;
    }

    public void setPegeSetting(PageSetting pegeSetting) {
        this.pegeSetting = pegeSetting;
    }

    public Boolean getCanRegeneratePassword() {
        return canRegeneratePassword;
    }

    public void setCanRegeneratePassword(Boolean canRegeneratePassword) {
        this.canRegeneratePassword = canRegeneratePassword;
    }
}
