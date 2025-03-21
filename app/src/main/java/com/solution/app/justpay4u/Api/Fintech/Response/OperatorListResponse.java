package com.solution.app.justpay4u.Api.Fintech.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;

import java.util.ArrayList;

public class OperatorListResponse implements Parcelable {
    boolean isTakeCustomerNo;
    int statuscode;
    String msg;
    boolean isVersionValid;
    boolean isAppValid;


    ArrayList<OperatorList> operators;
    ArrayList<OperatorList> dmtOperatorLists = new ArrayList<>();

    protected OperatorListResponse(Parcel in) {
        isTakeCustomerNo = in.readByte() != 0;
        statuscode = in.readInt();
        msg = in.readString();
        isVersionValid = in.readByte() != 0;
        isAppValid = in.readByte() != 0;
        operators = in.createTypedArrayList(OperatorList.CREATOR);
        dmtOperatorLists = in.createTypedArrayList(OperatorList.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isTakeCustomerNo ? 1 : 0));
        dest.writeInt(statuscode);
        dest.writeString(msg);
        dest.writeByte((byte) (isVersionValid ? 1 : 0));
        dest.writeByte((byte) (isAppValid ? 1 : 0));
        dest.writeTypedList(operators);
        dest.writeTypedList(dmtOperatorLists);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<OperatorListResponse> CREATOR = new Creator<OperatorListResponse>() {
        @Override
        public OperatorListResponse createFromParcel(Parcel in) {
            return new OperatorListResponse(in);
        }

        @Override
        public OperatorListResponse[] newArray(int size) {
            return new OperatorListResponse[size];
        }
    };

    public boolean isTakeCustomerNo() {
        return isTakeCustomerNo;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public ArrayList<OperatorList> getDmtOperatorLists() {
        return dmtOperatorLists;
    }

    public void setDmtOperatorLists(ArrayList<OperatorList> dmtOperatorLists) {
        this.dmtOperatorLists = dmtOperatorLists;
    }

    public ArrayList<OperatorList> getOperators() {
        return operators;
    }
}
