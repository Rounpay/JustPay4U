package com.solution.app.justpay4u.Api.Networking.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 29/08/2022.
 */
public class EPinList implements Parcelable {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("consumedUserId")
    @Expose
    public int consumedUserId;
    @SerializedName("projectDescription")
    @Expose
    public String projectDescription;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("associateName")
    @Expose
    public String associateName;
    @SerializedName("associateNm")
    @Expose
    public String associateNm;
    @SerializedName("consumerName")
    @Expose
    public String consumerName;
    @SerializedName("consumerMobile")
    @Expose
    public String consumerMobile;
    @SerializedName("isActive")
    @Expose
    public boolean isActive;
    @SerializedName("isFreezed")
    @Expose
    public boolean isFreezed;
    @SerializedName("isUsed")
    @Expose
    public boolean isUsed;
    @SerializedName("amount")
    @Expose
    public double amount;
    @SerializedName("ePinGeneratedDate")
    @Expose
    public String ePinGeneratedDate;
    @SerializedName("ePin")
    @Expose
    public String ePin;

    protected EPinList(Parcel in) {
        id = in.readInt();
        consumedUserId = in.readInt();
        projectDescription = in.readString();
        mobileNo = in.readString();
        associateName = in.readString();
        associateNm = in.readString();
        consumerName = in.readString();
        consumerMobile = in.readString();
        isActive = in.readByte() != 0;
        isFreezed = in.readByte() != 0;
        isUsed = in.readByte() != 0;
        amount = in.readDouble();
        ePinGeneratedDate = in.readString();
        ePin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(consumedUserId);
        dest.writeString(projectDescription);
        dest.writeString(mobileNo);
        dest.writeString(associateName);
        dest.writeString(associateNm);
        dest.writeString(consumerName);
        dest.writeString(consumerMobile);
        dest.writeByte((byte) (isActive ? 1 : 0));
        dest.writeByte((byte) (isFreezed ? 1 : 0));
        dest.writeByte((byte) (isUsed ? 1 : 0));
        dest.writeDouble(amount);
        dest.writeString(ePinGeneratedDate);
        dest.writeString(ePin);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<EPinList> CREATOR = new Creator<EPinList>() {
        @Override
        public EPinList createFromParcel(Parcel in) {
            return new EPinList(in);
        }

        @Override
        public EPinList[] newArray(int size) {
            return new EPinList[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getConsumedUserId() {
        return consumedUserId;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getAssociateName() {
        return associateName;
    }

    public String getAssociateNm() {
        return associateNm;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public String getConsumerMobile() {
        return consumerMobile;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isFreezed() {
        return isFreezed;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public double getAmount() {
        return amount;
    }

    public String getePinGeneratedDate() {
        return ePinGeneratedDate;
    }

    public String getePin() {
        return ePin;
    }
}
