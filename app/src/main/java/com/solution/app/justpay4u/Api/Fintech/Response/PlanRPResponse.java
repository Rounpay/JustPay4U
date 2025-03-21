package com.solution.app.justpay4u.Api.Fintech.Response;

import android.os.Parcel;
import android.os.Parcelable;

public class PlanRPResponse implements Parcelable {
    public static final Creator<PlanRPResponse> CREATOR = new Creator<PlanRPResponse>() {
        @Override
        public PlanRPResponse createFromParcel(Parcel in) {
            return new PlanRPResponse(in);
        }

        @Override
        public PlanRPResponse[] newArray(int size) {
            return new PlanRPResponse[size];
        }
    };
    String opName;
    double rechargeAmount;
    String rechargeValidity;
    String rechargeType;
    String details, packagelanguage;
    int packageId, channelcount;

    protected PlanRPResponse(Parcel in) {
        opName = in.readString();
        rechargeAmount = in.readDouble();
        rechargeValidity = in.readString();
        rechargeType = in.readString();
        details = in.readString();
        packagelanguage = in.readString();
        packageId = in.readInt();
        channelcount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(opName);
        dest.writeDouble(rechargeAmount);
        dest.writeString(rechargeValidity);
        dest.writeString(rechargeType);
        dest.writeString(details);
        dest.writeString(packagelanguage);
        dest.writeInt(packageId);
        dest.writeInt(channelcount);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public String getOpName() {
        return opName;
    }

    public double getRechargeAmount() {
        return rechargeAmount;
    }

    public String getRechargeValidity() {
        return rechargeValidity;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public String getDetails() {
        return details;
    }

    public int getPackageId() {
        return packageId;
    }

    public String getPackagelanguage() {
        return packagelanguage;
    }

    public int getChannelcount() {
        return channelcount;
    }
}
