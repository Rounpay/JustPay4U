package com.solution.app.justpay4u.Fintech.Employee.Api.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitMeetingRequest {
    public int Id;
    public String Name;
    public String OutletName;
    public String Area;
    public String Pincode;
    public int PurposeId;
    public String Purpose;
    public int Consumption;
    public boolean Isusingotherbrands;
    public int otherbrandconsumption;
    public int ReasonId;
    public String Reason;
    public String Remark;
    public int AttandanceId;
    public String MobileNo;
    public String Latitute;
    public String Longitute;
    int rechargeConsumption;
    int billPaymentConsumption;
    int moneyTransferConsumption;
    int aepsConsumption;
    int miniATMConsumption;
    int insuranceConsumption;
    int hotelConsumption;
    int panConsumption;
    int vehicleConsumption;
    @SerializedName("userID")
    @Expose
    public String userID;
    @SerializedName("sessionID")
    @Expose
    public String sessionID;
    @SerializedName("session")
    @Expose
    public String session;
    @SerializedName("appid")
    @Expose
    public String appid;
    @SerializedName("imei")
    @Expose
    public String imei;
    @SerializedName("regKey")
    @Expose
    public String regKey;
    @SerializedName("version")
    @Expose
    public String version;
    @SerializedName("serialNo")
    @Expose
    public String serialNo;
    @SerializedName("loginTypeID")
    @Expose
    public int loginTypeID;
    boolean IsImage;

    public SubmitMeetingRequest(boolean IsImage, int otherbrandconsumption, int id, String name, String outletName, String area, String pincode, int purposeId,
                                String purpose, int consumption, boolean isusingotherbrands, int rechargeConsumption,
                                int billPaymentConsumption,
                                int moneyTransferConsumption,
                                int aepsConsumption,
                                int miniATMConsumption,
                                int insuranceConsumption,
                                int hotelConsumption,
                                int panConsumption,
                                int vehicleConsumption,
                                int reasonId, String reason, String remark, int attandanceId, String mobileNo, String latitute,
                                String longitute, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.IsImage = IsImage;this.otherbrandconsumption = otherbrandconsumption;
        this.Id = id;
        this.Name = name;
        this.OutletName = outletName;
        this.Area = area;
        this.Pincode = pincode;
        this.PurposeId = purposeId;
        this.Purpose = purpose;
        this.Consumption = consumption;
        this.Isusingotherbrands = isusingotherbrands;
        this.rechargeConsumption = rechargeConsumption;
        this.billPaymentConsumption = billPaymentConsumption;
        this.moneyTransferConsumption = moneyTransferConsumption;
        this.aepsConsumption = aepsConsumption;
        this.miniATMConsumption = miniATMConsumption;
        this.insuranceConsumption = insuranceConsumption;
        this.hotelConsumption = hotelConsumption;
        this.panConsumption = panConsumption;
        this.vehicleConsumption = vehicleConsumption;
        this.ReasonId = reasonId;
        this.Reason = reason;
        this.Remark = remark;
        this.AttandanceId = attandanceId;
        this.MobileNo = mobileNo;
        this.Latitute = latitute;
        this.Longitute = longitute;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
    }


}
