package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.Banners;
import com.solution.app.justpay4u.Api.Fintech.Object.ChildRoles;
import com.solution.app.justpay4u.Api.Fintech.Object.CommissionDisplay;
import com.solution.app.justpay4u.Api.Fintech.Object.CompanyProfile;
import com.solution.app.justpay4u.Api.Fintech.Object.FosList;
import com.solution.app.justpay4u.Api.Fintech.Object.FundRequestForApproval;
import com.solution.app.justpay4u.Api.Fintech.Object.IncentiveDetails;
import com.solution.app.justpay4u.Api.Fintech.Object.NewsContent;
import com.solution.app.justpay4u.Api.Fintech.Object.NotificationData;
import com.solution.app.justpay4u.Api.Fintech.Object.PGModelForApp;
import com.solution.app.justpay4u.Api.Fintech.Object.PaymentGatewayType;
import com.solution.app.justpay4u.Api.Fintech.Object.RealLapuCommissionSlab;
import com.solution.app.justpay4u.Api.Fintech.Object.RefundLog;
import com.solution.app.justpay4u.Api.Fintech.Object.TargetAchieved;
import com.solution.app.justpay4u.Api.Fintech.Object.UserDaybookDMRReport;
import com.solution.app.justpay4u.Api.Fintech.Object.UserDaybookReport;
import com.solution.app.justpay4u.Api.Fintech.Object.UserList;

import java.util.ArrayList;
import java.util.List;

public class AppUserListResponse {
    @SerializedName("refferalContent")
    @Expose
    public String refferalContent;
    @SerializedName("refferalImage")
    @Expose
    public ArrayList<Banners> refferalImage;
    @SerializedName("appLogoUrl")
    @Expose
    public String appLogoUrl;
    @SerializedName("pGModelForApp")
    @Expose
    public PGModelForApp pGModelForApp = null;
    @SerializedName("pGs")
    @Expose
    public ArrayList<PaymentGatewayType> pGs = null;
    @SerializedName("incentiveDetails")
    @Expose
    public ArrayList<IncentiveDetails> incentiveDetails = null;
    @SerializedName("slabDetail")
    @Expose
    public RealLapuCommissionSlab realLapuCommissionSlab;

    @SerializedName("commissionDisplay")
    @Expose
    public CommissionDisplay commissionDisplay;


    @SerializedName("refundLog")
    @Expose
    public List<RefundLog> refundLog = null;
    @SerializedName("companyProfile")
    @Expose
    public CompanyProfile companyProfile = null;

    @SerializedName("notifications")
    @Expose
    public List<NotificationData> notifications = null;

    @SerializedName("targetAchieveds")
    @Expose
    public List<TargetAchieved> targetAchieveds = null;
    @SerializedName("userDaybookReport")
    @Expose
    public List<UserDaybookReport> userDaybookReport = null;
    @SerializedName("userDaybookDMRReport")
    @Expose
    public List<UserDaybookDMRReport> userDaybookDMTReport = null;
    @SerializedName("rechargeReport")
    @Expose
    public Object rechargeReport;
    @SerializedName("dmtReport")
    @Expose
    public Object dmtReport;
    @SerializedName("ledgerReport")
    @Expose
    public Object ledgerReport;
    @SerializedName("fundDCReport")
    @Expose
    public Object fundDCReport;
    @SerializedName("fundOrderReport")
    @Expose
    public Object fundOrderReport;
    @SerializedName("slabCommissions")
    @Expose
    public Object slabCommissions;
    @SerializedName("userList")
    @Expose
    public ArrayList<UserList> userList = null;
    @SerializedName("childRoles")
    @Expose
    public List<ChildRoles> childRoles = null;
    @SerializedName("fundRequestForApproval")
    @Expose
    public List<FundRequestForApproval> fundRequestForApproval = null;
    @SerializedName("banners")
    @Expose
    public List<Banners> banners = null;

    @SerializedName("newsContent")
    @Expose
    public NewsContent newsContent = null;

    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid;
    @SerializedName("fosList")
    @Expose
    private FosList fosList;

    public FosList getFosList() {
        return fosList;
    }

    public PGModelForApp getpGModelForApp() {
        return pGModelForApp;
    }

    public List<RefundLog> getRefundLog() {
        return refundLog;
    }

    public CommissionDisplay getCommissionDisplay() {
        return commissionDisplay;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public RealLapuCommissionSlab getRealLapuCommissionSlab() {
        return realLapuCommissionSlab;
    }

    public Object getRechargeReport() {
        return rechargeReport;
    }

    public Object getDmtReport() {
        return dmtReport;
    }

    public Object getLedgerReport() {
        return ledgerReport;
    }

    public Object getFundDCReport() {
        return fundDCReport;
    }

    public Object getFundOrderReport() {
        return fundOrderReport;
    }

    public Object getSlabCommissions() {
        return slabCommissions;
    }

    public ArrayList<UserList> getUserList() {
        return userList;
    }

    public List<ChildRoles> getChildRoles() {
        return childRoles;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public List<TargetAchieved> getTargetAchieveds() {
        return targetAchieveds;
    }

    public boolean getVersionValid() {
        return isVersionValid;
    }

    public List<FundRequestForApproval> getFundRequestForApproval() {
        return fundRequestForApproval;
    }

    public List<UserDaybookReport> getUserDaybookReport() {
        return userDaybookReport;
    }

    public List<UserDaybookDMRReport> getUserDaybookDMTReport() {
        return userDaybookDMTReport;
    }

    public List<Banners> getBanners() {
        return banners;
    }

    public String getAppLogoUrl() {
        return appLogoUrl;
    }

    public NewsContent getNewsContent() {
        return newsContent;
    }

    public boolean getAppValid() {
        return isAppValid;
    }

    public List<NotificationData> getNotifications() {
        return notifications;
    }

    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public String getRefferalContent() {
        return refferalContent;
    }

    public ArrayList<Banners> getRefferalImage() {
        return refferalImage;
    }

    public ArrayList<PaymentGatewayType> getpGs() {
        return pGs;
    }

    public ArrayList<IncentiveDetails> getIncentiveDetails() {
        return incentiveDetails;
    }
}
