package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserList {
    @SerializedName("rental")
    @Expose
    public String rental;
    @SerializedName("rentalAmt")
    @Expose
    public int rentalAmt;
    @SerializedName("capping")
    @Expose
    public int capping;
    @SerializedName("referalID")
    @Expose
    public int referalID;
    @SerializedName("roleID")
    @Expose
    public int roleID;
    @SerializedName("commRate")
    @Expose
    public double commRate;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("kycStatus")
    @Expose
    public String kycStatusStr;
    @SerializedName("kycStatus_")
    @Expose
    public int kycStatus;
    @SerializedName("outletName")
    @Expose
    public String outletName;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("status")
    @Expose
    public boolean status;
    @SerializedName("isOTP")
    @Expose
    public boolean isOTP;
    @SerializedName("joinDate")
    @Expose
    public String joinDate;
    @SerializedName("joinBy")
    @Expose
    public String joinBy;
    @SerializedName("slab")
    @Expose
    public String slab;
    @SerializedName("websiteName")
    @Expose
    public String websiteName;
    @SerializedName("osBalance")
    @Expose
    public double osBalance;
    @SerializedName("balance")
    @Expose
    public double balance;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("prefix")
    @Expose
    public String prefix;

    @SerializedName("introID")
    @Expose
    public int introID;
    @SerializedName("fosId")
    @Expose
    public int fosId;
    @SerializedName("fosName")
    @Expose
    public String fosName;
    @SerializedName("fosMobile")
    @Expose
    public String fosMobile;
    @SerializedName("joinByMobile")
    @Expose
    public String joinByMobile;
    @SerializedName("isVirtual")
    @Expose
    public boolean isVirtual;

    @SerializedName("bCapping")
    @Expose
    public double bCapping;
    @SerializedName("isBalance")
    @Expose
    public boolean isBalance;
    @SerializedName("isBalanceFund")
    @Expose
    public boolean isBalanceFund;
    @SerializedName("uBalance")
    @Expose
    public double uBalance;
    @SerializedName("uCapping")
    @Expose
    public double uCapping;
    @SerializedName("isUBalance")
    @Expose
    public boolean isUBalance;
    @SerializedName("isUBalanceFund")
    @Expose
    public boolean isUBalanceFund;
    @SerializedName("bBalance")
    @Expose
    public double bBalance;
    @SerializedName("bbCapping")
    @Expose
    public double bbCapping;
    @SerializedName("isBBalance")
    @Expose
    public boolean isBBalance;
    @SerializedName("isBBalanceFund")
    @Expose
    public boolean isBBalanceFund;
    @SerializedName("cBalance")
    @Expose
    public double cBalance;
    @SerializedName("cCapping")
    @Expose
    public double cCapping;
    @SerializedName("isCBalance")
    @Expose
    public boolean isCBalance;
    @SerializedName("isCBalanceFund")
    @Expose
    public boolean isCBalanceFund;
    @SerializedName(value = "idBalnace",alternate ="idBalance" )
    @Expose
    public double idBalnace;
    @SerializedName("idCapping")
    @Expose
    public double idCapping;
    @SerializedName("isIDBalance")
    @Expose
    public boolean isIDBalance;
    @SerializedName("isIDBalanceFund")
    @Expose
    public boolean isIDBalanceFund;
    @SerializedName("pacakgeBalance")
    @Expose
    public double pacakgeBalance;
    @SerializedName("packageCapping")
    @Expose
    public double packageCapping;
    @SerializedName("isPacakgeBalance")
    @Expose
    public boolean isPacakgeBalance;
    @SerializedName("isPacakgeBalanceFund")
    @Expose
    public boolean isPacakgeBalanceFund;
    @SerializedName("isP")
    @Expose
    public boolean isP;
    @SerializedName("isPN")
    @Expose
    public boolean isPN;
    @SerializedName("isLowBalance")
    @Expose
    public boolean isLowBalance;
    @SerializedName("isAdminDefined")
    @Expose
    public boolean isAdminDefined;
    ArrayList<BalanceType> balanceTypes;

    public ArrayList<BalanceType> getBalanceTypes() {
        return balanceTypes;
    }

    public void setBalanceTypes(ArrayList<BalanceType> balanceTypes) {
        this.balanceTypes = balanceTypes;
    }

    public String getRental() {
        return rental;
    }

    public int getRentalAmt() {
        return rentalAmt;
    }

    public int getCapping() {
        return capping;
    }

    public int getReferalID() {
        return referalID;
    }

    public int getRoleID() {
        return roleID;
    }

    public double getCommRate() {
        return commRate;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getKycStatusStr() {
        return kycStatusStr;
    }

    public int getKycStatus() {
        return kycStatus;
    }

    public String getOutletName() {
        return outletName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isOTP() {
        return isOTP;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public String getJoinBy() {
        return joinBy;
    }

    public String getSlab() {
        return slab;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public double getBalance() {
        return balance;
    }

    public double getOsBalance() {
        return osBalance;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getIntroID() {
        return introID;
    }

    public int getFosId() {
        return fosId;
    }

    public String getFosName() {
        return fosName;
    }

    public String getFosMobile() {
        return fosMobile;
    }

    public String getJoinByMobile() {
        return joinByMobile;
    }

    public boolean isVirtual() {
        return isVirtual;
    }

    public double getbCapping() {
        return bCapping;
    }

    public boolean isBalance() {
        return isBalance;
    }

    public boolean isBalanceFund() {
        return isBalanceFund;
    }

    public double getuBalance() {
        return uBalance;
    }

    public double getuCapping() {
        return uCapping;
    }

    public boolean isUBalance() {
        return isUBalance;
    }

    public boolean isUBalanceFund() {
        return isUBalanceFund;
    }

    public double getbBalance() {
        return bBalance;
    }

    public double getBbCapping() {
        return bbCapping;
    }

    public boolean isBBalance() {
        return isBBalance;
    }

    public boolean isBBalanceFund() {
        return isBBalanceFund;
    }

    public double getcBalance() {
        return cBalance;
    }

    public double getcCapping() {
        return cCapping;
    }

    public boolean isCBalance() {
        return isCBalance;
    }

    public boolean isCBalanceFund() {
        return isCBalanceFund;
    }

    public double getIdBalnace() {
        return idBalnace;
    }

    public double getIdCapping() {
        return idCapping;
    }

    public boolean isIDBalance() {
        return isIDBalance;
    }

    public boolean isIDBalanceFund() {
        return isIDBalanceFund;
    }

    public double getPacakgeBalance() {
        return pacakgeBalance;
    }

    public double getPackageCapping() {
        return packageCapping;
    }

    public boolean isPacakgeBalance() {
        return isPacakgeBalance;
    }

    public boolean isPacakgeBalanceFund() {
        return isPacakgeBalanceFund;
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

    public boolean isAdminDefined() {
        return isAdminDefined;
    }
}
