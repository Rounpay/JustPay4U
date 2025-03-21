package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlanInfoData implements Parcelable {

    @SerializedName(value = "records", alternate = "rdata")
    @Expose
    private PlanInfoRecords records;
    @SerializedName(value = "status", alternate = "error")
    @Expose
    private int status;

    protected PlanInfoData(Parcel in) {
        records = in.readParcelable(PlanInfoRecords.class.getClassLoader());
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(records, flags);
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<PlanInfoData> CREATOR = new Creator<PlanInfoData>() {
        @Override
        public PlanInfoData createFromParcel(Parcel in) {
            return new PlanInfoData(in);
        }

        @Override
        public PlanInfoData[] newArray(int size) {
            return new PlanInfoData[size];
        }
    };

    public PlanInfoRecords getRecords() {
        return records;
    }

    public void setRecords(PlanInfoRecords records) {
        this.records = records;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
