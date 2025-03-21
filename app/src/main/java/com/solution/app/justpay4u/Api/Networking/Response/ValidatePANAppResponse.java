package com.solution.app.justpay4u.Api.Networking.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidatePANAppResponse {
    @SerializedName("isAppValid")
    @Expose
    private Boolean isAppValid;
    @SerializedName("isVersionValid")
    @Expose
    private Boolean isVersionValid;
    @SerializedName("isPasswordExpired")
    @Expose
    private Boolean isPasswordExpired;
    @SerializedName("statuscode")
    @Expose
    private Integer statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("directorName")
    @Expose
    private Object directorName;
    @SerializedName("isAadharSeeded")
    @Expose
    private Boolean isAadharSeeded;
    @SerializedName("isPANValid")
    @Expose
    private Boolean isPANValid;
    @SerializedName("firstName")
    @Expose
    private Object firstName;
    @SerializedName("panNumber")
    @Expose
    private Object panNumber;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("lastName")
    @Expose
    private Object lastName;
    @SerializedName("title")
    @Expose
    private Object title;

    public Boolean getIsAppValid() {
        return isAppValid;
    }

    public void setIsAppValid(Boolean isAppValid) {
        this.isAppValid = isAppValid;
    }

    public Boolean getIsVersionValid() {
        return isVersionValid;
    }

    public void setIsVersionValid(Boolean isVersionValid) {
        this.isVersionValid = isVersionValid;
    }

    public Boolean getIsPasswordExpired() {
        return isPasswordExpired;
    }

    public void setIsPasswordExpired(Boolean isPasswordExpired) {
        this.isPasswordExpired = isPasswordExpired;
    }

    public Integer getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(Integer statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getDirectorName() {
        return directorName;
    }

    public void setDirectorName(Object directorName) {
        this.directorName = directorName;
    }

    public Boolean getIsAadharSeeded() {
        return isAadharSeeded;
    }

    public void setIsAadharSeeded(Boolean isAadharSeeded) {
        this.isAadharSeeded = isAadharSeeded;
    }

    public Boolean getIsPANValid() {
        return isPANValid;
    }

    public void setIsPANValid(Boolean isPANValid) {
        this.isPANValid = isPANValid;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(Object panNumber) {
        this.panNumber = panNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }
}
