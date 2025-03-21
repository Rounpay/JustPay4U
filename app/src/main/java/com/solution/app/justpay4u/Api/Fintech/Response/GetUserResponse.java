package com.solution.app.justpay4u.Api.Fintech.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.UserDetailInfo;

public class GetUserResponse implements Parcelable {
    @SerializedName("userInfo")
    @Expose
    public UserDetailInfo userInfo;
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
    @SerializedName("isPasswordExpired")
    @Expose
    public boolean isPasswordExpired;
    @SerializedName("checkID")
    @Expose
    public int checkID;
    @SerializedName(value = "verifyAadharFromProfile")
    @Expose
    private boolean verifyAadharFromProfile;

    @SerializedName("verifyPANFromProfile")
    @Expose
    private boolean verifyPANFromProfile;
    protected GetUserResponse(Parcel in) {
        userInfo = in.readParcelable(UserDetailInfo.class.getClassLoader());
        statuscode = in.readInt();
        msg = in.readString();
        isVersionValid = in.readByte() != 0;
        isAppValid = in.readByte() != 0;
        isPasswordExpired = in.readByte() != 0;
        checkID = in.readInt();
        verifyAadharFromProfile = in.readByte() != 0;
        verifyPANFromProfile = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(userInfo, flags);
        dest.writeInt(statuscode);
        dest.writeString(msg);
        dest.writeByte((byte) (isVersionValid ? 1 : 0));
        dest.writeByte((byte) (isAppValid ? 1 : 0));
        dest.writeByte((byte) (isPasswordExpired ? 1 : 0));
        dest.writeInt(checkID);
        dest.writeByte((byte) (verifyAadharFromProfile ? 1 : 0));
        dest.writeByte((byte) (verifyPANFromProfile ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<GetUserResponse> CREATOR = new Creator<GetUserResponse>() {
        @Override
        public GetUserResponse createFromParcel(Parcel in) {
            return new GetUserResponse(in);
        }

        @Override
        public GetUserResponse[] newArray(int size) {
            return new GetUserResponse[size];
        }
    };

    public UserDetailInfo getUserInfo() {
        return userInfo;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean getVersionValid() {
        return isVersionValid;
    }

    public boolean getAppValid() {
        return isAppValid;
    }

    public int getCheckID() {
        return checkID;
    }

    public boolean isVerifyAadharFromProfile() {
        return verifyAadharFromProfile;
    }

    public void setVerifyAadharFromProfile(boolean verifyAadharFromProfile) {
        this.verifyAadharFromProfile = verifyAadharFromProfile;
    }

    public boolean isVerifyPANFromProfile() {
        return verifyPANFromProfile;
    }

    public void setVerifyPANFromProfile(boolean verifyPANFromProfile) {
        this.verifyPANFromProfile = verifyPANFromProfile;
    }

    public boolean getPasswordExpired() {
        return isPasswordExpired;
    }
}
