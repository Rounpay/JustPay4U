package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RofferType implements Parcelable {
    @SerializedName("tel")
    @Expose
    private String tel;
    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("error")
    @Expose
    private int error;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("mobileno")
    @Expose
    private String mobileno;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName(value = "records", alternate = "rdata")
    @Expose
    private ArrayList<ROfferObject> records;

    protected RofferType(Parcel in) {
        tel = in.readString();
        operator = in.readString();
        error = in.readInt();
        status = in.readInt();
        mobileno = in.readString();
        message = in.readString();
        records = in.createTypedArrayList(ROfferObject.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tel);
        dest.writeString(operator);
        dest.writeInt(error);
        dest.writeInt(status);
        dest.writeString(mobileno);
        dest.writeString(message);
        dest.writeTypedList(records);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<RofferType> CREATOR = new Creator<RofferType>() {
        @Override
        public RofferType createFromParcel(Parcel in) {
            return new RofferType(in);
        }

        @Override
        public RofferType[] newArray(int size) {
            return new RofferType[size];
        }
    };

    public String getTel() {
        return tel;
    }

    public String getOperator() {
        return operator;
    }

    public int getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public String getMobileno() {
        return mobileno;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<ROfferObject> getRecords() {
        return records;
    }
}
