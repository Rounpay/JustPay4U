package com.solution.app.justpay4u.Api.Fintech.Response;

import android.os.Parcel;
import android.os.Parcelable;

public class DFStatusResponse implements Parcelable {

    String refID;
    boolean isOTPRequired;
    int statuscode;
    String msg;
    boolean isVersionValid;
    boolean isAppValid;
    int checkID;
    boolean isPasswordExpired;
    String mobileNo;
    String emailID;

    protected DFStatusResponse(Parcel in) {
        refID = in.readString();
        isOTPRequired = in.readByte() != 0;
        statuscode = in.readInt();
        msg = in.readString();
        isVersionValid = in.readByte() != 0;
        isAppValid = in.readByte() != 0;
        checkID = in.readInt();
        isPasswordExpired = in.readByte() != 0;
        mobileNo = in.readString();
        emailID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(refID);
        dest.writeByte((byte) (isOTPRequired ? 1 : 0));
        dest.writeInt(statuscode);
        dest.writeString(msg);
        dest.writeByte((byte) (isVersionValid ? 1 : 0));
        dest.writeByte((byte) (isAppValid ? 1 : 0));
        dest.writeInt(checkID);
        dest.writeByte((byte) (isPasswordExpired ? 1 : 0));
        dest.writeString(mobileNo);
        dest.writeString(emailID);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<DFStatusResponse> CREATOR = new Creator<DFStatusResponse>() {
        @Override
        public DFStatusResponse createFromParcel(Parcel in) {
            return new DFStatusResponse(in);
        }

        @Override
        public DFStatusResponse[] newArray(int size) {
            return new DFStatusResponse[size];
        }
    };

    public String getRefID() {
        return refID;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
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

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmailID() {
        return emailID;
    }
}

