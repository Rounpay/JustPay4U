package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderList implements Serializable {

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("orderNo")
    @Expose
    public String orderNo;
    @SerializedName("courierTrackingNo")
    @Expose
    public String courierTrackingNo;


    @SerializedName(value = "StatusCode", alternate = "statusCode")
    @Expose
    public int statusCode;

    @SerializedName("orderAmount")
    @Expose
    public double orderAmount;
    @SerializedName(value = "unitAmount", alternate = "unitPrice")
    @Expose
    public double unitAmount;
    @SerializedName("shippingAmount")
    @Expose
    public double shippingAmount;


    @SerializedName("orderedDate")
    @Expose
    public String orderedDate;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("setName")
    @Expose
    public String setName;
    @SerializedName("frontImage")
    @Expose
    public String frontImage;

    @SerializedName("posId")
    @Expose
    public int posId;
    @SerializedName("qty")
    @Expose
    public int qty;
    @SerializedName("entryDate")
    @Expose
    public String entryDate;
    @SerializedName("totalCost")
    @Expose
    public double totalCost;
    @SerializedName("userId")
    @Expose
    public int userId;
    @SerializedName("orderDetailId")
    @Expose
    public int orderDetailId;
    @SerializedName("orderId")
    @Expose
    public int orderId;
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("productDetailId")
    @Expose
    public int productDetailId;
    @SerializedName("productId")
    @Expose
    public int productId;
    @SerializedName("quantity")
    @Expose
    public int quantity;
    @SerializedName("mrp")
    @Expose
    public double mrp;
    @SerializedName("discount")
    @Expose
    public double discount;
    @SerializedName("discountType")
    @Expose
    public Object discountType;
    @SerializedName("productName")
    @Expose
    public String productName;
    @SerializedName(value = "imgUrl", alternate = "productImage")
    @Expose
    public String imgUrl;
    @SerializedName("vendorName")
    @Expose
    public String vendorName;
    @SerializedName("isPaid")
    @Expose
    public boolean isPaid;
    @SerializedName("isOrderClosed")
    @Expose
    public boolean isOrderClosed;
    @SerializedName("orderStatus")
    @Expose
    public int orderStatus;
    @SerializedName("shippingMode")
    @Expose
    public int shippingMode;
    @SerializedName("sellingPrice")
    @Expose
    public double sellingPrice;
    @SerializedName("shippingCharge")
    @Expose
    public double shippingCharge;


    public String getEntryDate() {
        return entryDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getUserId() {
        return userId;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductDetailId() {
        return productDetailId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }


    public String getProductName() {
        return productName;
    }

    public String getImgUrl() {
        return imgUrl;
    }


    public int getShippingMode() {
        return shippingMode;
    }

    public double getShippingCharge() {
        return shippingCharge;
    }

    public String getVendorName() {
        return vendorName;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public boolean isOrderClosed() {
        return isOrderClosed;
    }

    public int getOrderStatus() {
        return orderStatus;
    }


    public boolean isDiscountType() {
        if (discountType instanceof Integer) {
            return (int) discountType == 1;
        } else if (discountType instanceof Boolean) {
            return (boolean) discountType;
        }
        return false;
    }

    public String get$id() {
        return $id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public double getUnitAmount() {
        return unitAmount;
    }

    public double getShippingAmount() {
        return shippingAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public double getMrp() {
        return mrp;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public int getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getSetName() {
        return setName;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public String getCourierTrackingNo() {
        return courierTrackingNo;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getPosId() {
        return posId;
    }

    public int getQty() {
        return qty;
    }
}
