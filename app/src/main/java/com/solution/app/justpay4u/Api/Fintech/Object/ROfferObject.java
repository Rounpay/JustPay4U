package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ROfferObject implements Parcelable {
    @SerializedName(value = "rs", alternate = {"price", "amount"})
    @Expose
    private String rs;
    @SerializedName(value = "desc", alternate = {"ofrtext", "description"})
    @Expose
    private String desc;

    @SerializedName("commissionUnit")
    @Expose
    private String commissionUnit;
    @SerializedName("logdesc")
    @Expose
    private String logdesc;
    @SerializedName("commissionAmount")
    @Expose
    private String commissionAmount;

    protected ROfferObject(Parcel in) {
        rs = in.readString();
        desc = in.readString();
        commissionUnit = in.readString();
        logdesc = in.readString();
        commissionAmount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rs);
        dest.writeString(desc);
        dest.writeString(commissionUnit);
        dest.writeString(logdesc);
        dest.writeString(commissionAmount);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<ROfferObject> CREATOR = new Creator<ROfferObject>() {
        @Override
        public ROfferObject createFromParcel(Parcel in) {
            return new ROfferObject(in);
        }

        @Override
        public ROfferObject[] newArray(int size) {
            return new ROfferObject[size];
        }
    };

    public String getRs() {
        return rs;
    }

    public String getDesc() {
        return desc;
    }

    public String getCommissionUnit() {
        return commissionUnit;
    }

    public String getLogdesc() {
        return logdesc;
    }

    public String getCommissionAmount() {
        return commissionAmount;
    }
}
