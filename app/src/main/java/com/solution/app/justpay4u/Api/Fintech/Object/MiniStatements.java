package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MiniStatements implements Parcelable {
    @SerializedName(value = "TransactionDate", alternate = "transactionDate")
    @Expose
    public String transactionDate;
    @SerializedName(value = "TransactionType", alternate = "transactionType")
    @Expose
    public String transactionType;
    @SerializedName(value = "Amount", alternate = "amount")
    @Expose
    public String amount;
    @SerializedName(value = "Narration", alternate = "narration")
    @Expose
    public String narration;

    public MiniStatements(String transactionDate, String transactionType, String amount, String narration) {
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.amount = amount;
        this.narration = narration;
    }

    protected MiniStatements(Parcel in) {
        transactionDate = in.readString();
        transactionType = in.readString();
        amount = in.readString();
        narration = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(transactionDate);
        dest.writeString(transactionType);
        dest.writeString(amount);
        dest.writeString(narration);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<MiniStatements> CREATOR = new Creator<MiniStatements>() {
        @Override
        public MiniStatements createFromParcel(Parcel in) {
            return new MiniStatements(in);
        }

        @Override
        public MiniStatements[] newArray(int size) {
            return new MiniStatements[size];
        }
    };

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getAmount() {
        return amount;
    }

    public String getNarration() {
        return narration;
    }
}
