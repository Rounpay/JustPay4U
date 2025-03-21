package com.solution.app.justpay4u.Api.Fintech.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.BankListObject;

import java.util.ArrayList;

/**
 * Created by Vishnu on 12-04-2017.
 */

public class BankListResponse implements Parcelable {

    public static final Creator<BankListResponse> CREATOR = new Creator<BankListResponse>() {
        @Override
        public BankListResponse createFromParcel(Parcel in) {
            return new BankListResponse(in);
        }

        @Override
        public BankListResponse[] newArray(int size) {
            return new BankListResponse[size];
        }
    };
    private int statuscode;
    private String msg;
    private boolean isVersionValid;
    private boolean isAppValid;
    @SerializedName(value = "bankMasters", alternate = {"aepsBanks", "banks"})
    @Expose
    private ArrayList<BankListObject> bankMasters;

    protected BankListResponse(Parcel in) {
        statuscode = in.readInt();
        msg = in.readString();
        isVersionValid = in.readByte() != 0;
        isAppValid = in.readByte() != 0;
        bankMasters = in.createTypedArrayList(BankListObject.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(statuscode);
        dest.writeString(msg);
        dest.writeByte((byte) (isVersionValid ? 1 : 0));
        dest.writeByte((byte) (isAppValid ? 1 : 0));
        dest.writeTypedList(bankMasters);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public boolean getIsAppValid() {
        return isAppValid;
    }

    public int getStatuscode() {
        return statuscode;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<BankListObject> getBankMasters() {
        return bankMasters;
    }

    public void setBankMasters(ArrayList<BankListObject> bankMasters) {
        this.bankMasters = bankMasters;
    }


}
