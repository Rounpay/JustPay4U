package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

public class BalanceType implements Parcelable {
    String name;
    double amount;

    public BalanceType(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    protected BalanceType(Parcel in) {
        name = in.readString();
        amount = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(amount);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<BalanceType> CREATOR = new Creator<BalanceType>() {
        @Override
        public BalanceType createFromParcel(Parcel in) {
            return new BalanceType(in);
        }

        @Override
        public BalanceType[] newArray(int size) {
            return new BalanceType[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
