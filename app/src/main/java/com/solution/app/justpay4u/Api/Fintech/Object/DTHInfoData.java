package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DTHInfoData implements Parcelable {
    @SerializedName("tel")
    @Expose
    private String tel;
    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName(value = "status", alternate = "error")
    @Expose
    private int status;
    @SerializedName("records")
    @Expose
    private ArrayList<DTHInfoRecords> records;
    @SerializedName("data")
    @Expose
    private DTHInfoRecords data;

    protected DTHInfoData(Parcel in) {
        tel = in.readString();
        operator = in.readString();
        status = in.readInt();
        records = in.createTypedArrayList(DTHInfoRecords.CREATOR);
        data = in.readParcelable(DTHInfoRecords.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tel);
        dest.writeString(operator);
        dest.writeInt(status);
        dest.writeTypedList(records);
        dest.writeParcelable(data, flags);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<DTHInfoData> CREATOR = new Creator<DTHInfoData>() {
        @Override
        public DTHInfoData createFromParcel(Parcel in) {
            return new DTHInfoData(in);
        }

        @Override
        public DTHInfoData[] newArray(int size) {
            return new DTHInfoData[size];
        }
    };

    public String getTel() {
        return tel;
    }

    public String getOperator() {
        return operator;
    }

    public int getStatus() {
        return status;
    }

    public ArrayList<DTHInfoRecords> getRecords() {
        return records;
    }

    public DTHInfoRecords getData() {
        return data;
    }
}
