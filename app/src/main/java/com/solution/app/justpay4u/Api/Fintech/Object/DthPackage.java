package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DthPackage implements Parcelable {

    public static final Creator<DthPackage> CREATOR = new Creator<DthPackage>() {
        @Override
        public DthPackage createFromParcel(Parcel in) {
            return new DthPackage(in);
        }

        @Override
        public DthPackage[] newArray(int size) {
            return new DthPackage[size];
        }
    };
    @SerializedName("channelCount")
    @Expose
    private int channelCount;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("oid")
    @Expose
    private int oid;
    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("opType")
    @Expose
    private String opType;
    @SerializedName("opTypeID")
    @Expose
    private int opTypeID;
    @SerializedName("packageName")
    @Expose
    private String packageName;
    @SerializedName("packageMRP")
    @Expose
    private double packageMRP;
    @SerializedName("bookingAmount")
    @Expose
    private double bookingAmount;
    @SerializedName("validity")
    @Expose
    private int validity;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("isActive")
    @Expose
    private boolean isActive;
    @SerializedName("spKey")
    @Expose
    private Object spKey;
    @SerializedName("businessModel")
    @Expose
    private Object businessModel;

    protected DthPackage(Parcel in) {
        channelCount = in.readInt();
        id = in.readInt();
        oid = in.readInt();
        operator = in.readString();
        opType = in.readString();
        opTypeID = in.readInt();
        packageName = in.readString();
        packageMRP = in.readDouble();
        bookingAmount = in.readDouble();
        validity = in.readInt();
        description = in.readString();
        remark = in.readString();
        isActive = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(channelCount);
        dest.writeInt(id);
        dest.writeInt(oid);
        dest.writeString(operator);
        dest.writeString(opType);
        dest.writeInt(opTypeID);
        dest.writeString(packageName);
        dest.writeDouble(packageMRP);
        dest.writeDouble(bookingAmount);
        dest.writeInt(validity);
        dest.writeString(description);
        dest.writeString(remark);
        dest.writeByte((byte) (isActive ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public int getOpTypeID() {
        return opTypeID;
    }

    public void setOpTypeID(int opTypeID) {
        this.opTypeID = opTypeID;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public double getPackageMRP() {
        return packageMRP;
    }

    public void setPackageMRP(double packageMRP) {
        this.packageMRP = packageMRP;
    }

    public double getBookingAmount() {
        return bookingAmount;
    }

    public void setBookingAmount(double bookingAmount) {
        this.bookingAmount = bookingAmount;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Object getSpKey() {
        return spKey;
    }

    public void setSpKey(Object spKey) {
        this.spKey = spKey;
    }

    public Object getBusinessModel() {
        return businessModel;
    }

    public void setBusinessModel(Object businessModel) {
        this.businessModel = businessModel;
    }

    public int getChannelCount() {
        return channelCount;
    }

}
