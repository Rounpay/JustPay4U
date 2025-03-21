package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 19,December,2019
 */
public class CartDetailData {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("totalCartSum")
    @Expose
    public int totalCartSum;
    @SerializedName("totalShippingCharges")
    @Expose
    public double totalShippingCharges;
    @SerializedName("orderTotal")
    @Expose
    public double orderTotal;
    @SerializedName("cartCount")
    @Expose
    public int cartCount;
    @SerializedName("totalDiscount")
    @Expose
    public double totalDiscount;
    @SerializedName("gatewayDeduction")
    @Expose
    public double gatewayDeduction;
    @SerializedName("walletDeduction")
    @Expose
    public double walletDeduction;

    public double getGatewayDeduction() {
        return gatewayDeduction;
    }

    public double getWalletDeduction() {
        return walletDeduction;
    }

    public String get$id() {
        return $id;
    }

    public int getTotalCartSum() {
        return totalCartSum;
    }

    public double getTotalShippingCharges() {
        return totalShippingCharges;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public int getCartCount() {
        return cartCount;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }
}
