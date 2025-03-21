package com.solution.app.justpay4u.Api.Fintech.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;

import java.util.ArrayList;

public class BalanceResponse implements Parcelable {
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("defaultCurrencySymbol")
    @Expose
    public String defaultCurrencySymbol;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid;
    @SerializedName("isPasswordExpired")
    @Expose
    private boolean isPasswordExpired;
    @SerializedName("isLookUpFromAPI")
    @Expose
    private boolean isLookUpFromAPI;
    @SerializedName(value = "IsDTHInfoCall", alternate = "isDTHInfoCall")
    @Expose
    private boolean isDTHInfoCall;
    @SerializedName(value = "IsShowPDFPlan", alternate = "isShowPDFPlan")
    @Expose
    private boolean isShowPDFPlan;
    @SerializedName("isP")
    @Expose
    public boolean isP;
    @SerializedName("isPN")
    @Expose
    public boolean isPN;
    @SerializedName("isLowBalance")
    @Expose
    public boolean isLowBalance;
    @SerializedName("isPackageDeducionForRetailor")
    @Expose
    public boolean isPackageDeducionForRetailor;
    @SerializedName("isQRMappedToUser")
    @Expose
    public boolean isQRMappedToUser;
    @SerializedName("isCandebit")
    @Expose
    public boolean isCandebit;
    @SerializedName(value = "IsDTHInfo", alternate = "isDTHInfo")
    @Expose
    private boolean isDTHInfo;
    @SerializedName(value = "IsRoffer", alternate = "isRoffer")
    @Expose
    private boolean isRoffer;
    @SerializedName("isUPI")
    @Expose
    public boolean isUPI;
    @SerializedName(value = "isPaymentGatway", alternate = "isPaymentsgateway")
    @Expose
    public boolean isPaymentGatway;
    @SerializedName("isBidWithPG")
    @Expose
    public boolean isBidWithPG;
    @SerializedName("isBinaryon")
    @Expose
    public int isBinaryon;
    @SerializedName("isLevelIncome")
    @Expose
    public boolean isLevelIncome;
    @SerializedName("isFintechServiceOn")
    @Expose
    public boolean isFintechServiceOn;
    @SerializedName("isMLM")
    @Expose
    public boolean isMLM;
    @SerializedName("isECommerceAllowed")
    @Expose
    public boolean isECommerceAllowed;
    @SerializedName("isBulkQRGeneration")
    @Expose
    public boolean isBulkQRGeneration;
    @SerializedName("isReferral")
    @Expose
    public boolean isReferral;
    @SerializedName("isEKYCForced")
    @Expose
    private boolean isEKYCForced;
    @SerializedName("isDrawOpImage")
    @Expose
    private boolean isDrawOpImage;
    @SerializedName("userTopupType")
    @Expose
    public int userTopupType;
    @SerializedName("isTopup")
    @Expose
    public int isTopup;

    @SerializedName("isUPIQR")
    @Expose
    public boolean isUPIQR;
    @SerializedName("isECollectEnable")
    @Expose
    public boolean isECollectEnable;
    @SerializedName("isAddMoneyEnable")
    @Expose
    public boolean isAddMoneyEnable;
    @SerializedName("isReTopupAllow")
    @Expose
    public boolean isReTopupAllow;
    @SerializedName("isFlatCommission")
    @Expose
    public boolean isFlatCommission;
    @SerializedName("globalPoolTree")
    @Expose
    public boolean GlobalPoolTree;
    @SerializedName("isTopupByPackage")
    @Expose
    public boolean isTopupByPackage;
    @SerializedName("isTopupByEpin")
    @Expose
    public boolean isTopupByEpin;
    @SerializedName("isPV")
    @Expose
    public boolean isPV;
    @SerializedName("isBV")
    @Expose
    public boolean isBV;
    @SerializedName("rechargeWithPG")
    @Expose
    public boolean rechargeWithPG;
    @SerializedName("rechargeWalletPercentage")
    @Expose
    public double rechargeWalletPercentage;
    @SerializedName("defaultUserDashboardDate")
    @Expose
    public int defaultUserDashboardDate;
    @SerializedName(value = "IsAutoVerifyVPA", alternate = "isAutoVerifyVPA")
    @Expose
    private boolean isAutoVerifyVPA;
    @SerializedName(value = "data", alternate = "balanceData")
    @Expose
    public ArrayList<BalanceData> balanceData;


    protected BalanceResponse(Parcel in) {
        statuscode = in.readInt();
        rechargeWithPG = in.readByte() != 0;
        rechargeWalletPercentage = in.readDouble();
        defaultUserDashboardDate = in.readInt();
        msg = in.readString();
        isVersionValid = in.readByte() != 0;
        isAppValid = in.readByte() != 0;
        isPasswordExpired = in.readByte() != 0;
        isLookUpFromAPI = in.readByte() != 0;
        isDTHInfoCall = in.readByte() != 0;
        isShowPDFPlan = in.readByte() != 0;
        isP = in.readByte() != 0;
        isPN = in.readByte() != 0;
        isLowBalance = in.readByte() != 0;
        isPackageDeducionForRetailor = in.readByte() != 0;
        isQRMappedToUser = in.readByte() != 0;
        isCandebit = in.readByte() != 0;
        isDTHInfo = in.readByte() != 0;
        isRoffer = in.readByte() != 0;
        isUPI = in.readByte() != 0;
        isPaymentGatway = in.readByte() != 0;
        isBidWithPG = in.readByte() != 0;
        isBinaryon = in.readInt();
        isLevelIncome = in.readByte() != 0;
        isFintechServiceOn = in.readByte() != 0;
        isMLM = in.readByte() != 0;
        GlobalPoolTree = in.readByte() != 0;
        isECommerceAllowed = in.readByte() != 0;
        isBulkQRGeneration = in.readByte() != 0;
        isReferral = in.readByte() != 0;
        isEKYCForced = in.readByte() != 0;
        isDrawOpImage = in.readByte() != 0;
        userTopupType = in.readInt();
        isTopup = in.readInt();
        isUPIQR = in.readByte() != 0;
        isECollectEnable = in.readByte() != 0;
        isAddMoneyEnable = in.readByte() != 0;
        isReTopupAllow = in.readByte() != 0;
        isFlatCommission = in.readByte() != 0;
        isTopupByPackage = in.readByte() != 0;
        isTopupByEpin = in.readByte() != 0;
        isAutoVerifyVPA = in.readByte() != 0;
        balanceData = in.createTypedArrayList(BalanceData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(statuscode);
        dest.writeByte((byte) (rechargeWithPG ? 1 : 0));
        dest.writeDouble(rechargeWalletPercentage);
        dest.writeString(msg);
        dest.writeInt(defaultUserDashboardDate);
        dest.writeByte((byte) (isVersionValid ? 1 : 0));
        dest.writeByte((byte) (isAppValid ? 1 : 0));
        dest.writeByte((byte) (isPasswordExpired ? 1 : 0));
        dest.writeByte((byte) (isLookUpFromAPI ? 1 : 0));
        dest.writeByte((byte) (isDTHInfoCall ? 1 : 0));
        dest.writeByte((byte) (isShowPDFPlan ? 1 : 0));
        dest.writeByte((byte) (isP ? 1 : 0));
        dest.writeByte((byte) (isPN ? 1 : 0));
        dest.writeByte((byte) (isLowBalance ? 1 : 0));
        dest.writeByte((byte) (isPackageDeducionForRetailor ? 1 : 0));
        dest.writeByte((byte) (isQRMappedToUser ? 1 : 0));
        dest.writeByte((byte) (isCandebit ? 1 : 0));
        dest.writeByte((byte) (isDTHInfo ? 1 : 0));
        dest.writeByte((byte) (isRoffer ? 1 : 0));
        dest.writeByte((byte) (isUPI ? 1 : 0));
        dest.writeByte((byte) (isPaymentGatway ? 1 : 0));
        dest.writeByte((byte) (isBidWithPG ? 1 : 0));
        dest.writeInt(isBinaryon);
        dest.writeByte((byte) (isLevelIncome ? 1 : 0));
        dest.writeByte((byte) (isFintechServiceOn ? 1 : 0));
        dest.writeByte((byte) (isMLM ? 1 : 0));
        dest.writeByte((byte) (GlobalPoolTree ? 1 : 0));
        dest.writeByte((byte) (isECommerceAllowed ? 1 : 0));
        dest.writeByte((byte) (isBulkQRGeneration ? 1 : 0));
        dest.writeByte((byte) (isReferral ? 1 : 0));
        dest.writeByte((byte) (isEKYCForced ? 1 : 0));
        dest.writeByte((byte) (isDrawOpImage ? 1 : 0));
        dest.writeInt(userTopupType);
        dest.writeInt(isTopup);
        dest.writeByte((byte) (isUPIQR ? 1 : 0));
        dest.writeByte((byte) (isECollectEnable ? 1 : 0));
        dest.writeByte((byte) (isAddMoneyEnable ? 1 : 0));
        dest.writeByte((byte) (isReTopupAllow ? 1 : 0));
        dest.writeByte((byte) (isFlatCommission ? 1 : 0));
        dest.writeByte((byte) (isTopupByPackage ? 1 : 0));
        dest.writeByte((byte) (isTopupByEpin ? 1 : 0));
        dest.writeByte((byte) (isAutoVerifyVPA ? 1 : 0));
        dest.writeTypedList(balanceData);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<BalanceResponse> CREATOR = new Creator<BalanceResponse>() {
        @Override
        public BalanceResponse createFromParcel(Parcel in) {
            return new BalanceResponse(in);
        }

        @Override
        public BalanceResponse[] newArray(int size) {
            return new BalanceResponse[size];
        }
    };

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

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }

    public boolean isLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public boolean isDTHInfoCall() {
        return isDTHInfoCall;
    }

    public boolean isShowPDFPlan() {
        return isShowPDFPlan;
    }

    public boolean isP() {
        return isP;
    }

    public boolean isPN() {
        return isPN;
    }

    public boolean isLowBalance() {
        return isLowBalance;
    }

    public boolean isPackageDeducionForRetailor() {
        return isPackageDeducionForRetailor;
    }

    public boolean isQRMappedToUser() {
        return isQRMappedToUser;
    }

    public boolean isCandebit() {
        return isCandebit;
    }

    public boolean isDTHInfo() {
        return isDTHInfo;
    }

    public boolean isRoffer() {
        return isRoffer;
    }

    public boolean isUPI() {
        return isUPI;
    }

    public boolean isPaymentGatway() {
        return isPaymentGatway;
    }

    public boolean isBidWithPG() {
        return isBidWithPG;
    }

    public int getIsBinaryon() {
        return isBinaryon;
    }

    public boolean isLevelIncome() {
        return isLevelIncome;
    }

    public boolean isFintechServiceOn() {
        return isFintechServiceOn;
    }

    public boolean isMLM() {
        return isMLM;
    }

    public boolean isECommerceAllowed() {
        return isECommerceAllowed;
    }

    public boolean isBulkQRGeneration() {
        return isBulkQRGeneration;
    }

    public boolean isReferral() {
        return isReferral;
    }

    public boolean isEKYCForced() {
        return isEKYCForced;
    }

    public boolean isDrawOpImage() {
        return isDrawOpImage;
    }

    public int getUserTopupType() {
       /* Self = 1;
        Team = 2;
        Univaersal = 3;*/
        return userTopupType;
    }
    public int getIsTopup() {
        return isTopup;
    }

    public boolean isUPIQR() {
        return isUPIQR;
    }

    public boolean isECollectEnable() {
        return isECollectEnable;
    }

    public boolean isAddMoneyEnable() {
        return isAddMoneyEnable;
    }

    public boolean isReTopupAllow() {
        return isReTopupAllow;
    }

    public boolean isFlatCommission() {
        return isFlatCommission;
    }

    public boolean isTopupByPackage() {
        return isTopupByPackage;
    }

    public boolean isTopupByEpin() {
        return isTopupByEpin;
    }

    public boolean isAutoVerifyVPA() {
        return isAutoVerifyVPA;
    }

    public ArrayList<BalanceData> getBalanceData() {
        return balanceData;
    }

    public String getDefaultCurrencySymbol() {
        if (defaultCurrencySymbol != null) {
            if (defaultCurrencySymbol.toLowerCase().equalsIgnoreCase("usdt")
                    || defaultCurrencySymbol.toLowerCase().equalsIgnoreCase("usd")) {
                return "$";
            } else if (defaultCurrencySymbol.toLowerCase().equalsIgnoreCase("inr")
                    || defaultCurrencySymbol.toLowerCase().equalsIgnoreCase("rupee")
            ||defaultCurrencySymbol.toLowerCase().equalsIgnoreCase("INR")) {
                return "\u20B9";
            } else {
                return defaultCurrencySymbol;
            }
        }
        return defaultCurrencySymbol;

    }

    public boolean isGlobalPoolTree() {
        return GlobalPoolTree;
    }

    public boolean isPV() {
        return isPV;
    }

    public boolean isBV() {
        return isBV;
    }

    public int getDefaultUserDashboardDate() {
        return defaultUserDashboardDate;
    }

    public double getRechargeWalletPercentage() {
        return rechargeWalletPercentage;
    }


    public boolean isRechargeWithPG() {
        return rechargeWithPG;
    }
}
