package com.solution.app.justpay4u.Api.Fintech.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.TransactionDetail;

public class DMTReceiptResponse implements Parcelable {
    @SerializedName("transactionDetail")
    @Expose
    private TransactionDetail transactionDetail;
    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;

    protected DMTReceiptResponse(Parcel in) {
        transactionDetail = in.readParcelable(TransactionDetail.class.getClassLoader());
        statuscode = in.readInt();
        msg = in.readString();
        isVersionValid = in.readByte() != 0;
        isAppValid = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(transactionDetail, flags);
        dest.writeInt(statuscode);
        dest.writeString(msg);
        dest.writeByte((byte) (isVersionValid ? 1 : 0));
        dest.writeByte((byte) (isAppValid ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<DMTReceiptResponse> CREATOR = new Creator<DMTReceiptResponse>() {
        @Override
        public DMTReceiptResponse createFromParcel(Parcel in) {
            return new DMTReceiptResponse(in);
        }

        @Override
        public DMTReceiptResponse[] newArray(int size) {
            return new DMTReceiptResponse[size];
        }
    };

    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public boolean getIsAppValid() {
        return isAppValid;
    }

    public TransactionDetail getTransactionDetail() {
        return transactionDetail;
    }

    public void setTransactionDetail(TransactionDetail transactionDetail) {
        this.transactionDetail = transactionDetail;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
