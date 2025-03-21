package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllowedWithdrawalType implements Parcelable {

    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("serviceId")
    @Expose
    int serviceId;
    @SerializedName("walletId")
    @Expose
    int walletId;
    @SerializedName("isActive")
    @Expose
    boolean isActive;
    @SerializedName("serviceName")
    @Expose
    String serviceName;


    protected AllowedWithdrawalType(Parcel in) {
        id = in.readInt();
        serviceId = in.readInt();
        walletId = in.readInt();
        isActive = in.readByte() != 0;
        serviceName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(serviceId);
        dest.writeInt(walletId);
        dest.writeByte((byte) (isActive ? 1 : 0));
        dest.writeString(serviceName);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<AllowedWithdrawalType> CREATOR = new Creator<AllowedWithdrawalType>() {
        @Override
        public AllowedWithdrawalType createFromParcel(Parcel in) {
            return new AllowedWithdrawalType(in);
        }

        @Override
        public AllowedWithdrawalType[] newArray(int size) {
            return new AllowedWithdrawalType[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public int getWalletId() {
        return walletId;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getServiceName() {
        return serviceName;
    }
}
