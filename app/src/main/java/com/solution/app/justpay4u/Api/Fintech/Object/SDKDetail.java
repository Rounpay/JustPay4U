package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Response.BcResponse;

import java.util.List;

public class SDKDetail implements Parcelable {
    String apiOutletID;
    String apiOutletPassword;
    String apiPartnerID;
    String apiOutletMob;
    @SerializedName("bcResponse")
    @Expose
    private List<BcResponse> bcResponse = null;

    protected SDKDetail(Parcel in) {
        apiOutletID = in.readString();
        apiOutletPassword = in.readString();
        apiPartnerID = in.readString();
        apiOutletMob = in.readString();
        bcResponse = in.createTypedArrayList(BcResponse.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(apiOutletID);
        dest.writeString(apiOutletPassword);
        dest.writeString(apiPartnerID);
        dest.writeString(apiOutletMob);
        dest.writeTypedList(bcResponse);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<SDKDetail> CREATOR = new Creator<SDKDetail>() {
        @Override
        public SDKDetail createFromParcel(Parcel in) {
            return new SDKDetail(in);
        }

        @Override
        public SDKDetail[] newArray(int size) {
            return new SDKDetail[size];
        }
    };

    public String getApiOutletID() {
        return apiOutletID;
    }

    public String getApiOutletPassword() {
        return apiOutletPassword;
    }

    public String getApiPartnerID() {
        return apiPartnerID;
    }

    public String getApiOutletMob() {
        return apiOutletMob;
    }

    public List<BcResponse> getBcResponse() {
        return bcResponse;
    }
}
