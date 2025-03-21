package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vishnu Agarwal on 28,December,2019
 */
public class PlanInfoRPRecords implements Parcelable {
    int status;
    String desc;

    protected PlanInfoRPRecords(Parcel in) {
        status = in.readInt();
        desc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeString(desc);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<PlanInfoRPRecords> CREATOR = new Creator<PlanInfoRPRecords>() {
        @Override
        public PlanInfoRPRecords createFromParcel(Parcel in) {
            return new PlanInfoRPRecords(in);
        }

        @Override
        public PlanInfoRPRecords[] newArray(int size) {
            return new PlanInfoRPRecords[size];
        }
    };

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
