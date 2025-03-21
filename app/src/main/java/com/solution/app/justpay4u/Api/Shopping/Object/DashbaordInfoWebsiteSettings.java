package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 13,December,2019
 */
public class DashbaordInfoWebsiteSettings {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("websiteId")
    @Expose
    public int websiteId;
    @SerializedName("logo")
    @Expose
    public String logo;
    @SerializedName("whiteLabelId")
    @Expose
    public int whiteLabelId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("favicon")
    @Expose
    public String favicon;
    @SerializedName("copyRightText")
    @Expose
    public String copyRightText;

    @SerializedName("isRecharge")
    @Expose
    public boolean isRecharge;
    @SerializedName(value = "mobileNo", alternate = "shoppingMobileNo")
    @Expose
    public String shoppingMobileNo;
    @SerializedName(value = "emailId", alternate = "shoppingEmailId")
    @Expose
    public String shoppingEmailId;
    @SerializedName(value = "landline", alternate = "shoppingLandLineNo")
    @Expose
    public String shoppingLandline;

    @SerializedName(value = "whatsappNo", alternate = "shoppingWhatsappNo")
    @Expose
    public String shoppingWhatsappNo;

    @SerializedName("rechargeMobileNo")
    @Expose
    public String rechargeMobileNo;
    @SerializedName("rechargeEmailId")
    @Expose
    public String rechargeEmailId;
    @SerializedName("rechargeLandline")
    @Expose
    public String rechargeLandline;

    @SerializedName("rechargeWhatsappNo")
    @Expose
    public String rechargeWhatsappNo;


    @SerializedName("salesMobileNo")
    @Expose
    public String salesMobileNo;
    @SerializedName("salesEmailId")
    @Expose
    public String salesEmailId;
    @SerializedName("salesLandline")
    @Expose
    public String salesLandline;

    @SerializedName("salesWhatsappNo")
    @Expose
    public String salesWhatsappNo;

    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("youtubeLinks")
    @Expose
    public ArrayList<YoutubeLinkData> youtubeLinks;
    @SerializedName("websiteLink")
    @Expose
    public String websiteLink;

    @SerializedName("fbLink")
    @Expose
    public String fbLink;
    @SerializedName("instagramLink")
    @Expose
    public String instagramLink;
    @SerializedName("twitterLink")
    @Expose
    public String twitterLink;
    @SerializedName("telegramLink")
    @Expose
    public String telegramLink;

    public String get$id() {
        return $id;
    }

    public int getWebsiteId() {
        return websiteId;
    }

    public String getLogo() {
        return logo;
    }

    public int getWhiteLabelId() {
        return whiteLabelId;
    }

    public String getTitle() {
        return title;
    }

    public String getFavicon() {
        return favicon;
    }

    public String getCopyRightText() {
        return copyRightText;
    }

    public boolean getIsRecharge() {
        return isRecharge;
    }

    public String getShoppingMobileNo() {
        return shoppingMobileNo;
    }

    public String getShoppingEmailId() {
        return shoppingEmailId;
    }

    public String getShoppingLandline() {
        return shoppingLandline;
    }

    public String getShoppingWhatsappNo() {
        return shoppingWhatsappNo;
    }

    public String getRechargeMobileNo() {
        return rechargeMobileNo;
    }

    public String getRechargeEmailId() {
        return rechargeEmailId;
    }

    public String getRechargeLandline() {
        return rechargeLandline;
    }

    public String getRechargeWhatsappNo() {
        return rechargeWhatsappNo;
    }

    public String getSalesMobileNo() {
        return salesMobileNo;
    }

    public String getSalesEmailId() {
        return salesEmailId;
    }

    public String getSalesLandline() {
        return salesLandline;
    }

    public String getSalesWhatsappNo() {
        return salesWhatsappNo;
    }

    public String getAddress() {
        return address;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public ArrayList<YoutubeLinkData> getYoutubeLinks() {
        return youtubeLinks;
    }

    public String getFbLink() {
        return fbLink;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public String getTelegramLink() {
        return telegramLink;
    }
}
