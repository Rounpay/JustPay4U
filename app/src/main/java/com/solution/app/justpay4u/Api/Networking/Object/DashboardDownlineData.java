package com.solution.app.justpay4u.Api.Networking.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 04/08/2022.
 */
public class DashboardDownlineData {

    @SerializedName("directDownlinelUser")
    @Expose
    public int directDownlinelUser;
    @SerializedName("directDownlinelUserActive")
    @Expose
    public int directDownlinelUserActive;
    @SerializedName("directDownlinelUserDeactive")
    @Expose
    public int directDownlinelUserDeactive;
    @SerializedName("allDownlinelUser")
    @Expose
    public int allDownlinelUser;
    @SerializedName("allDownlinelUserActive")
    @Expose
    public int allDownlinelUserActive;
    @SerializedName(value = "allDownlinelUserDeActive",alternate = "allDownlinelUserDeactive")
    @Expose
    public int allDownlinelUserDeactive;
    @SerializedName("packageName")
    @Expose
    public Object packageName;
    @SerializedName("packageAmount")
    @Expose
    public double packageAmount;
    @SerializedName("totalSponser")
    @Expose
    public int totalSponser;
    @SerializedName("totalTeam")
    @Expose
    public int totalTeam;
//    @SerializedName("totalActive")
    @Expose
    public int totalActive;
    @SerializedName("totalDeactive")
    @Expose
    public int totalDeactive;
    @SerializedName("totalLeftTeam")
    @Expose
    public int totalLeftTeam;
    @SerializedName("totalLeftActive")
    @Expose
    public int totalLeftActive;
    @SerializedName("totalLeftDeactive")
    @Expose
    public int totalLeftDeactive;
    @SerializedName("totalRightTeam")
    @Expose
    public int totalRightTeam;
    @SerializedName("totalRightActive")
    @Expose
    public int totalRightActive;
    @SerializedName("totalRightDeactive")
    @Expose
    public int totalRightDeactive;
    @SerializedName("totalSponserActive")
    @Expose
    public int totalSponserActive;
    @SerializedName("totalSponserDeactive")
    @Expose
    public int totalSponserDeactive;
    @SerializedName("leftBusiness")
    @Expose
    public double leftBusiness;
    @SerializedName("rightBusiness")
    @Expose
    public double rightBusiness;
    @SerializedName("totalBusiness")
    @Expose
    public double totalBusiness;
    @SerializedName("selfBusiness")
    @Expose
    public double selfBusiness;
    @SerializedName("directBusiness")
    @Expose
    public double directBusiness;
    @SerializedName("sponserBusiness")
    @Expose
    public double sponserBusiness;
    @SerializedName("leftReferralLink")
    @Expose
    public String leftReferralLink;
    @SerializedName("rightReferralLink")
    @Expose
    public String rightReferralLink;
    @SerializedName("singleLink")
    @Expose
    public String singleLink;
    @SerializedName("isTeamByPool")
    @Expose
    public boolean isTeamByPool;
    @SerializedName("rankName")
    @Expose
    public String rankName;
    @SerializedName("lastPackageName")
    @Expose
    public String lastPackageName;
    @SerializedName("rankImageUrl")
    @Expose
    public String rankImageUrl;
    @SerializedName("lastPackageAmount")
    @Expose
    public double lastPackageAmount;

    @SerializedName("totalIncome")
    @Expose
    public double totalIncome;
    @SerializedName("todayIncome")
    @Expose
    public double todayIncome;
    @SerializedName("todayActive")
    @Expose
    public int todayActive;
    @SerializedName("todayDeactive")
    @Expose
    public int todayDeactive;
    @SerializedName("totalHoldCommAmt")
    @Expose
    public int totalHoldCommAmt;


    public String getRankImageUrl() {
        return rankImageUrl;
    }

    public boolean isTeamByPool() {
        return isTeamByPool;
    }

    public int getDirectDownlinelUser() {
        return directDownlinelUser;
    }

    public int getDirectDownlinelUserActive() {
        return directDownlinelUserActive;
    }

    public int getDirectDownlinelUserDeactive() {
        return directDownlinelUserDeactive;
    }

    public int getAllDownlinelUser() {
        return allDownlinelUser;
    }

    public int getAllDownlinelUserActive() {
        return allDownlinelUserActive;
    }

    public int getAllDownlinelUserDeactive() {
        return allDownlinelUserDeactive;
    }

    public Object getPackageName() {
        return packageName;
    }

    public double getPackageAmount() {
        return packageAmount;
    }

    public int getTotalSponser() {
        return totalSponser;
    }

    public int getTotalTeam() {
        return totalTeam;
    }

    public int getTotalActive() {
        return totalActive;
    }

    public int getTotalDeactive() {
        return totalDeactive;
    }

    public int getTotalLeftTeam() {
        return totalLeftTeam;
    }

    public int getTotalLeftActive() {
        return totalLeftActive;
    }

    public int getTotalLeftDeactive() {
        return totalLeftDeactive;
    }

    public int getTotalRightTeam() {
        return totalRightTeam;
    }

    public int getTotalRightActive() {
        return totalRightActive;
    }

    public int getTotalRightDeactive() {
        return totalRightDeactive;
    }

    public int getTotalSponserActive() {
        return totalSponserActive;
    }

    public int getTotalSponserDeactive() {
        return totalSponserDeactive;
    }

    public double getLeftBusiness() {
        return leftBusiness;
    }

    public double getRightBusiness() {
        return rightBusiness;
    }

    public double getTotalBusiness() {
        return totalBusiness;
    }

    public double getDirectBusiness() {
        return directBusiness;
    }

    public double getSponserBusiness() {
        return sponserBusiness;
    }

    public String getLeftReferralLink() {
        return leftReferralLink;
    }

    public String getRightReferralLink() {
        return rightReferralLink;
    }

    public String getSingleLink() {
        return singleLink;
    }

    public double getSelfBusiness() {
        return selfBusiness;
    }

    public String getRankName() {
        return rankName;
    }
    public String getLastPackageName() {
        return lastPackageName;
    }

    public double getLastPackageAmount() {
        return lastPackageAmount;
    }


    public int getTodayActive() {
        return todayActive;
    }

    public int getTodayDeactive() {
        return todayDeactive;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTodayIncome() {
        return todayIncome;
    }
    public double getTotalHoldCommAmt() {
        return totalHoldCommAmt;
    }

}
