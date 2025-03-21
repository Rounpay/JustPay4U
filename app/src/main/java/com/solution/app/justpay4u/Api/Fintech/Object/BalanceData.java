package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BalanceData implements Parcelable {
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("walletType")
    @Expose
    String walletType;
    @SerializedName("inFundProcess")
    @Expose
    boolean inFundProcess;
    @SerializedName("preferredForShoping")
    @Expose
    int preferredForShoping;
    @SerializedName("deductionPercent")
    @Expose
    double deductionPercent;
    @SerializedName("isPackageDedectionForRetailor")
    @Expose
    boolean isPackageDedectionForRetailor;
    @SerializedName("minTransferAmount")
    @Expose
    double minTransferAmount;
    @SerializedName("maxTransferAmount")
    @Expose
    double maxTransferAmount;
    @SerializedName("maxTransaferCount")
    @Expose
    double maxTransaferCount;
    @SerializedName("minWithdrawalAmount")
    @Expose
    double minWithdrawalAmount;
    @SerializedName("maxWithdrawalAmount")
    @Expose
    double maxWithdrawalAmount;
    @SerializedName("maxWithdrawalCount")
    @Expose
    double maxWithdrawalCount;
    @SerializedName("walletTransferType")
    @Expose
    int walletTransferType;
    @SerializedName("withdrawalType")
    @Expose
    int withdrawalType;
    @SerializedName("isWithdrawal")
    @Expose
    boolean isWithdrawal;

    @SerializedName("balance")
    @Expose
    double balance;
    @SerializedName("capping")
    @Expose
    double capping;
    @SerializedName("walletCurrencySymbol")
    @Expose
    String walletCurrencySymbol;
    @SerializedName("allowedWallet")
    @Expose
    ArrayList<AllowedWallet> allowedWallet;
    @SerializedName("allowedWithdrawalType")
    @Expose
    ArrayList<AllowedWithdrawalType> allowedWithdrawalType;


    protected BalanceData(Parcel in) {
        id = in.readInt();
        walletType = in.readString();
        inFundProcess = in.readByte() != 0;
        preferredForShoping = in.readInt();
        deductionPercent = in.readDouble();
        isPackageDedectionForRetailor = in.readByte() != 0;
        minTransferAmount = in.readDouble();
        maxTransferAmount = in.readDouble();
        maxTransaferCount = in.readDouble();
        minWithdrawalAmount = in.readDouble();
        maxWithdrawalAmount = in.readDouble();
        maxWithdrawalCount = in.readDouble();
        walletTransferType = in.readInt();
        withdrawalType = in.readInt();
        isWithdrawal = in.readByte() != 0;
        balance = in.readDouble();
        capping = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(walletType);
        dest.writeByte((byte) (inFundProcess ? 1 : 0));
        dest.writeInt(preferredForShoping);
        dest.writeDouble(deductionPercent);
        dest.writeByte((byte) (isPackageDedectionForRetailor ? 1 : 0));
        dest.writeDouble(minTransferAmount);
        dest.writeDouble(maxTransferAmount);
        dest.writeDouble(maxTransaferCount);
        dest.writeDouble(minWithdrawalAmount);
        dest.writeDouble(maxWithdrawalAmount);
        dest.writeDouble(maxWithdrawalCount);
        dest.writeInt(walletTransferType);
        dest.writeInt(withdrawalType);
        dest.writeByte((byte) (isWithdrawal ? 1 : 0));
        dest.writeDouble(balance);
        dest.writeDouble(capping);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<BalanceData> CREATOR = new Creator<BalanceData>() {
        @Override
        public BalanceData createFromParcel(Parcel in) {
            return new BalanceData(in);
        }

        @Override
        public BalanceData[] newArray(int size) {
            return new BalanceData[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getWalletType() {
        return walletType;
    }

    public boolean isInFundProcess() {
        return inFundProcess;
    }

    public int getPreferredForShoping() {
        return preferredForShoping;
    }

    public double getDeductionPercent() {
        return deductionPercent;
    }

    public boolean isPackageDedectionForRetailor() {
        return isPackageDedectionForRetailor;
    }

    public double getMinTransferAmount() {
        return minTransferAmount;
    }

    public double getMaxTransferAmount() {
        return maxTransferAmount;
    }

    public double getMaxTransaferCount() {
        return maxTransaferCount;
    }

    public double getMinWithdrawalAmount() {
        return minWithdrawalAmount;
    }

    public double getMaxWithdrawalAmount() {
        return maxWithdrawalAmount;
    }

    public double getMaxWithdrawalCount() {
        return maxWithdrawalCount;
    }

    public int getWalletTransferType() {

        /*1	Self Only	    Transfer himself
        2	Downline Only	Transfer towords downline team
        3	Both		    Self and Downline
        4	Both All		Self and all
        5	Others Only	    All others members*/
        return walletTransferType;
    }

    public int getWithdrawalType() {

       /* 1	DMT		                    Money Transfer
        2	Real Time Sattelment		Payout
        3	Crypto		                Crypto Api*/
        return withdrawalType;
    }

    public boolean isWithdrawal() {
        return isWithdrawal;
    }


    public double getBalance() {
        return balance;
    }

    public double getCapping() {
        return capping;
    }

    public String getWalletCurrencySymbol() {

        if (walletCurrencySymbol != null && !walletCurrencySymbol.isEmpty()) {
            if (walletCurrencySymbol.toLowerCase().equalsIgnoreCase("usdt")
                    || walletCurrencySymbol.toLowerCase().equalsIgnoreCase("usd")) {
                return "$";
            } else if (walletCurrencySymbol.toLowerCase().equalsIgnoreCase("inr")
                    || walletCurrencySymbol.toLowerCase().equalsIgnoreCase("rupee")) {
                return "\u20B9";
            } else {
                return walletCurrencySymbol;
            }
        }
        return walletCurrencySymbol;
    }

    public ArrayList<AllowedWallet> getAllowedWallet() {
        return allowedWallet;
    }

    public ArrayList<AllowedWithdrawalType> getAllowedWithdrawalType() {
        return allowedWithdrawalType;
    }
}
