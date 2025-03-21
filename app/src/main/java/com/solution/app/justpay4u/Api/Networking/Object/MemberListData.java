package com.solution.app.justpay4u.Api.Networking.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberListData {
    @SerializedName("strUserId")
    @Expose
    String strUserId;
    @SerializedName(value = "userID", alternate = "userId")
    @Expose
    int userID;
    @SerializedName(value = "sponsoredId",alternate = {"sponserId"})
    @Expose
    String sponserId;
    @SerializedName(value = "parentId", alternate = "parentUserId")
    @Expose
    int parentId;
    @SerializedName("referalID")
    @Expose
    int referalID;
    @SerializedName("introducedBy")
    @Expose
    int introducedBy;
    @SerializedName("levelNo")
    @Expose
    int levelNo;
    @SerializedName("sponserName")
    @Expose
    String sponserName;
    @SerializedName("parentName")
    @Expose
    String parentName;
    @SerializedName(value = "emailID", alternate = {"emailId","EmailID"})
    @Expose
    String emailID;
    @SerializedName(value = "position", alternate = {"leg", "legs"})
    @Expose
    String position;
    @SerializedName(value = "name", alternate = {"username", "userName"})
    @Expose
    String name;
    @SerializedName("mobileNo")
    @Expose
    String mobileNo;
    @SerializedName("rank")
    @Expose
    String rank;
    @SerializedName("businessDate")
    @Expose
    String businessDate;
    @SerializedName("businessType")
    @Expose
    String businessType;
    @SerializedName("businessTypeId")
    @Expose
    int businessTypeId;
    @SerializedName("businessCurrency")
    @Expose
    String businessCurrency;
    @SerializedName(value = "regDate", alternate = "registerDate")
    @Expose
    String regDate;
    @SerializedName(value = "activationDate", alternate = "ActivationDate")
    @Expose
    String activationDate;
    @SerializedName(value = "entrydate", alternate = "entryDate")
    @Expose
    String entrydate;
    @SerializedName("status")
    @Expose
    String status;
    @SerializedName(value = "PackageCost", alternate = {"packageCost", "packageAmount", "amount"})
    @Expose
    String packageCost;

    @SerializedName("teamBusiness")
    @Expose
    double teamBusiness;
    @SerializedName("selfBusiness")
    @Expose
    double selfBusiness;
    @SerializedName("directBusiness")
    @Expose
    double directBusiness;


    @SerializedName("lastTopupAmount")
    @Expose
    double lastTopupAmount;

    @SerializedName(value = "lastDate", alternate = "lastTopupDate")
    @Expose
    String lastTopupDate;

    @SerializedName("packagePV")
    @Expose
    double packagePV;
    @SerializedName("packageBV")
    @Expose
    double packageBV;


    @SerializedName("uPrefix")
    @Expose
    String uPrefix;
    @SerializedName("sPrefix")
    @Expose
    String sPrefix;
    @SerializedName("pPrefix")
    @Expose
    String pPrefix;

    @SerializedName("totalDirectCount")
    @Expose
    int totalDirectCount;
    @SerializedName("activeDirect")
    @Expose
    int activeDirect;
    @SerializedName("deActiveDirect")
    @Expose
    int deActiveDirect;


    @SerializedName("targetStatus")
    @Expose
    int targetStatus;
    @SerializedName("displayField")
    @Expose
    String displayField;


    public double getTeamBusiness() {
        return teamBusiness;
    }

    public double getSelfBusiness() {
        return selfBusiness;
    }

    public double getDirectBusiness() {
        return directBusiness;
    }

    public int getTotalDirectCount() {
        return totalDirectCount;
    }

    public int getActiveDirect() {
        return activeDirect;
    }

    public int getDeActiveDirect() {
        return deActiveDirect;
    }


    public int getTargetStatus() {
        return targetStatus;
    }

    public String getDisplayField() {
        return displayField;
    }

    public String getStrUserId() {
        return strUserId;
    }

    public int getUserID() {
        return userID;
    }

    public int getLevelNo() {
        return levelNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public String getName() {
        return name;
    }

    public String getMobileNo() {
        return mobileNo;
    }
    public String getRankNo() {
        return rank;
    }

    public String getRegDate() {
        return regDate;
    }

    public String getActivationDate() {
        return activationDate;
    }
    public String getentryDate() {
        return entrydate;
    }

    public String getPackageCost() {
        return packageCost;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public String getBusinessType() {
        return businessType;
    }

    public String getBusinessCurrency() {
        if (businessCurrency != null && !businessCurrency.isEmpty()) {
            if (businessCurrency.toLowerCase().equalsIgnoreCase("usdt")
                    || businessCurrency.toLowerCase().equalsIgnoreCase("usd")) {
                return "$";
            } else if (businessCurrency.toLowerCase().equalsIgnoreCase("inr")
                    || businessCurrency.toLowerCase().equalsIgnoreCase("rupee")) {
                return "\u20B9";
            } else {
                return businessCurrency;
            }
        }
        return businessCurrency;
    }

    public int getBusinessTypeId() {
        return businessTypeId;
    }

    public String getSponserId() {
        return sponserId;
    }

    public String getSponserName() {
        return sponserName;
    }

    public int getParentId() {
        return parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public String getPosition() {
        return position;
    }

    public int getReferalID() {
        return referalID;
    }

    public int getIntroducedBy() {
        return introducedBy;
    }

    public String getStatus() {
        return status;
    }

    public double getLastTopupAmount() {
        return lastTopupAmount;
    }

    public String getLastTopupDate() {
        return lastTopupDate;
    }

    public double getPackagePV() {
        return packagePV;
    }

    public double getPackageBV() {
        return packageBV;
    }

    public String getuPrefix() {
        return uPrefix;
    }

    public String getsPrefix() {
        return sPrefix;
    }

    public String getpPrefix() {
        return pPrefix;
    }
}
