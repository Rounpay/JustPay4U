package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

public class MNPClaimsList implements Parcelable {

    String opName;
    String status;
    String mnpMobile;
    String referenceID;
    double amount;
    String approvedDate;

    protected MNPClaimsList(Parcel in) {
        opName = in.readString();
        status = in.readString();
        mnpMobile = in.readString();
        referenceID = in.readString();
        amount = in.readDouble();
        approvedDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(opName);
        dest.writeString(status);
        dest.writeString(mnpMobile);
        dest.writeString(referenceID);
        dest.writeDouble(amount);
        dest.writeString(approvedDate);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<MNPClaimsList> CREATOR = new Creator<MNPClaimsList>() {
        @Override
        public MNPClaimsList createFromParcel(Parcel in) {
            return new MNPClaimsList(in);
        }

        @Override
        public MNPClaimsList[] newArray(int size) {
            return new MNPClaimsList[size];
        }
    };

    public String getOpName() {
        return opName;
    }

    public String getStatus() {
        return status;
    }

    public String getMnpMobile() {
        return mnpMobile;
    }

    public String getReferenceID() {
        return referenceID;
    }

    public double getAmount() {
        return amount;
    }

    public String getApprovedDate() {
        return approvedDate;
    }
}
