package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.solution.app.justpay4u.Api.Fintech.Response.PlanRPResponse;

import java.util.ArrayList;

public class PlanInfoRPData implements Parcelable {
    String tel;
    String operator;
    PlanInfoRPRecords records;
    int status;
    ArrayList<PlanRPResponse> response = new ArrayList<>();

    protected PlanInfoRPData(Parcel in) {
        tel = in.readString();
        operator = in.readString();
        records = in.readParcelable(PlanInfoRPRecords.class.getClassLoader());
        status = in.readInt();
        response = in.createTypedArrayList(PlanRPResponse.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tel);
        dest.writeString(operator);
        dest.writeParcelable(records, flags);
        dest.writeInt(status);
        dest.writeTypedList(response);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<PlanInfoRPData> CREATOR = new Creator<PlanInfoRPData>() {
        @Override
        public PlanInfoRPData createFromParcel(Parcel in) {
            return new PlanInfoRPData(in);
        }

        @Override
        public PlanInfoRPData[] newArray(int size) {
            return new PlanInfoRPData[size];
        }
    };

    public String getTel() {
        return tel;
    }

    public String getOperator() {
        return operator;
    }

    public PlanInfoRPRecords getRecords() {
        return records;
    }

    public int getStatus() {
        return status;
    }

    public ArrayList<PlanRPResponse> getResponse() {
        return response;
    }
}

