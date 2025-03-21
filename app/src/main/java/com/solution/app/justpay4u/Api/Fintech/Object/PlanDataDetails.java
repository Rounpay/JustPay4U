package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlanDataDetails implements Parcelable {
    public static final Creator<PlanDataDetails> CREATOR = new Creator<PlanDataDetails>() {
        @Override
        public PlanDataDetails createFromParcel(Parcel in) {
            return new PlanDataDetails(in);
        }

        @Override
        public PlanDataDetails[] newArray(int size) {
            return new PlanDataDetails[size];
        }
    };
    @SerializedName("validity")
    @Expose
    private String validity;
    @SerializedName("last_update")
    @Expose
    private String lastUpdate;
    @SerializedName("rs")
    @Expose
    private String rs;
    @SerializedName("desc")
    @Expose
    private String desc;

    protected PlanDataDetails(Parcel in) {
        validity = in.readString();
        lastUpdate = in.readString();
        rs = in.readString();
        desc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(validity);
        dest.writeString(lastUpdate);
        dest.writeString(rs);
        dest.writeString(desc);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
