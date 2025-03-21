package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllowedWallet implements Parcelable {
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("fromWalletId")
    @Expose
    int fromWalletId;
    @SerializedName("toWalletId")
    @Expose
    int toWalletId;
    @SerializedName("isActive")
    @Expose
    boolean isActive;
    @SerializedName("isFrenchie")
    @Expose
    boolean isFrenchie;
    @SerializedName(value = "toWalletName",alternate = "walletName")
    @Expose
    String toWalletName;

    protected AllowedWallet(Parcel in) {
        id = in.readInt();
        fromWalletId = in.readInt();
        toWalletId = in.readInt();
        isActive = in.readByte() != 0;
        isFrenchie = in.readByte() != 0;
        toWalletName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(fromWalletId);
        dest.writeInt(toWalletId);
        dest.writeByte((byte) (isActive ? 1 : 0));
        dest.writeByte((byte) (isFrenchie ? 1 : 0));
        dest.writeString(toWalletName);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<AllowedWallet> CREATOR = new Creator<AllowedWallet>() {
        @Override
        public AllowedWallet createFromParcel(Parcel in) {
            return new AllowedWallet(in);
        }

        @Override
        public AllowedWallet[] newArray(int size) {
            return new AllowedWallet[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getFromWalletId() {
        return fromWalletId;
    }

    public int getToWalletId() {
        return toWalletId;
    }

    public boolean isActive() {
        return isActive;
    }
    public boolean isFrenchie() {
        return isFrenchie;
    }

    public String getToWalletName() {
        return toWalletName;
    }
}
