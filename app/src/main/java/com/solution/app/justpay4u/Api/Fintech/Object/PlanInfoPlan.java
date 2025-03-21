package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlanInfoPlan implements Parcelable {
    public static final Creator<PlanInfoPlan> CREATOR = new Creator<PlanInfoPlan>() {
        @Override
        public PlanInfoPlan createFromParcel(Parcel in) {
            return new PlanInfoPlan(in);
        }

        @Override
        public PlanInfoPlan[] newArray(int size) {
            return new PlanInfoPlan[size];
        }
    };
    @SerializedName(value = "desc", alternate = "pDescription")
    @Expose
    private String desc;
    @SerializedName("last_update")
    @Expose
    private String lastUpdate;
    @SerializedName("pLangauge")
    @Expose
    private String pLangauge;
    @SerializedName("packageId")
    @Expose
    private int packageId;
    @SerializedName("pCount")
    @Expose
    private int pCount;
    @SerializedName("pChannelCount")
    @Expose
    private int pChannelCount;
    @SerializedName(value = "plan_name", alternate = "packageName")
    @Expose
    private String planName;
    @SerializedName(value = "rs", alternate = "price")
    @Expose
    private PlanRs rs;

    protected PlanInfoPlan(Parcel in) {
        desc = in.readString();
        lastUpdate = in.readString();
        pLangauge = in.readString();
        packageId = in.readInt();
        pCount = in.readInt();
        pChannelCount = in.readInt();
        planName = in.readString();
        rs = in.readParcelable(PlanRs.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(desc);
        dest.writeString(lastUpdate);
        dest.writeString(pLangauge);
        dest.writeInt(packageId);
        dest.writeInt(pCount);
        dest.writeInt(pChannelCount);
        dest.writeString(planName);
        dest.writeParcelable(rs, flags);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public PlanRs getRs() {
        return rs;
    }

    public void setRs(PlanRs rs) {
        this.rs = rs;
    }

    public String getpLangauge() {
        return pLangauge;
    }

    public int getPackageId() {
        return packageId;
    }

    public int getpCount() {
        return pCount;
    }

    public int getpChannelCount() {
        return pChannelCount;
    }
}
