package com.solution.app.justpay4u.Fintech.Employee.Api.Object;

public class GetMeetingDetails {
    int loginID;
    int statusCode;
    int loginTypeID;
    int id;
    String name;
    String outletName;
    String area;
    String pincode;
    int purposeId;
    String purpose;
    double consumption;
    double rechargeConsumption;
    double billPaymentConsumption;
    double moneyTransferConsumption;
    double aepsConsumption;
    double miniATMConsumption;
    double insuranceConsumption;
    double hotelConsumption;
    double panConsumption;
    double vehicleConsumption;
    boolean isusingotherbrands;
    double otherbrandconsumption;
    int reasonId;
    String reason;
    String remark;
    int attandanceId;
    String mobileNo;
    String latitute;
    String longitute;
    public String shopImagePath;

    public String getShopImagePath() {
        return shopImagePath;
    }

    public int getLoginID() {
        return loginID;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getLoginTypeID() {
        return loginTypeID;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOutletName() {
        return outletName;
    }

    public String getArea() {
        return area;
    }

    public String getPincode() {
        return pincode;
    }

    public int getPurposeId() {
        return purposeId;
    }

    public String getPurpose() {
        return purpose;
    }

    public double getConsumption() {
        return consumption;
    }

    public boolean isIsusingotherbrands() {
        return isusingotherbrands;
    }

    public double getOtherbrandconsumption() {
        return otherbrandconsumption;
    }

    public int getReasonId() {
        return reasonId;
    }

    public String getReason() {
        return reason;
    }

    public String getRemark() {
        return remark;
    }

    public int getAttandanceId() {
        return attandanceId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getLatitute() {
        return latitute;
    }

    public String getLongitute() {
        return longitute;
    }

    public double getRechargeConsumption() {
        return rechargeConsumption;
    }

    public double getBillPaymentConsumption() {
        return billPaymentConsumption;
    }

    public double getMoneyTransferConsumption() {
        return moneyTransferConsumption;
    }

    public double getAepsConsumption() {
        return aepsConsumption;
    }

    public double getMiniATMConsumption() {
        return miniATMConsumption;
    }

    public double getInsuranceConsumption() {
        return insuranceConsumption;
    }

    public double getHotelConsumption() {
        return hotelConsumption;
    }

    public double getPanConsumption() {
        return panConsumption;
    }

    public double getVehicleConsumption() {
        return vehicleConsumption;
    }
}
