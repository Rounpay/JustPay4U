package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vishnu Agarwal on 16/05/2022.
 */
public class VPAList implements Parcelable {
    int id;
    String vpa;
    String accountHolder;
    String senderNo;
    boolean isVerified;

    protected VPAList(Parcel in) {
        id = in.readInt();
        vpa = in.readString();
        accountHolder = in.readString();
        senderNo = in.readString();
        isVerified = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(vpa);
        dest.writeString(accountHolder);
        dest.writeString(senderNo);
        dest.writeByte((byte) (isVerified ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<VPAList> CREATOR = new Creator<VPAList>() {
        @Override
        public VPAList createFromParcel(Parcel in) {
            return new VPAList(in);
        }

        @Override
        public VPAList[] newArray(int size) {
            return new VPAList[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getVpa() {
        return vpa;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getSenderNo() {
        return senderNo;
    }

    public boolean isVerified() {
        return isVerified;
    }
}
