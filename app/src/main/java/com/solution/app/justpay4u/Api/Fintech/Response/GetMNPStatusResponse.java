package com.solution.app.justpay4u.Api.Fintech.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.solution.app.justpay4u.Api.Fintech.Object.MNPClaimsList;

import java.util.ArrayList;

public class GetMNPStatusResponse implements Parcelable {

    int oid;
    String opName, userName, password, appLink, mnpMobile;
    int mnpStatus;
    String mnpRemark;
    int statuscode;
    String msg;
    boolean isVersionValid;
    boolean isAppValid;
    ArrayList<MNPClaimsList> mnpClaimsList;

    protected GetMNPStatusResponse(Parcel in) {
        oid = in.readInt();
        opName = in.readString();
        userName = in.readString();
        password = in.readString();
        appLink = in.readString();
        mnpMobile = in.readString();
        mnpStatus = in.readInt();
        mnpRemark = in.readString();
        statuscode = in.readInt();
        msg = in.readString();
        isVersionValid = in.readByte() != 0;
        isAppValid = in.readByte() != 0;
        mnpClaimsList = in.createTypedArrayList(MNPClaimsList.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(oid);
        dest.writeString(opName);
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(appLink);
        dest.writeString(mnpMobile);
        dest.writeInt(mnpStatus);
        dest.writeString(mnpRemark);
        dest.writeInt(statuscode);
        dest.writeString(msg);
        dest.writeByte((byte) (isVersionValid ? 1 : 0));
        dest.writeByte((byte) (isAppValid ? 1 : 0));
        dest.writeTypedList(mnpClaimsList);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<GetMNPStatusResponse> CREATOR = new Creator<GetMNPStatusResponse>() {
        @Override
        public GetMNPStatusResponse createFromParcel(Parcel in) {
            return new GetMNPStatusResponse(in);
        }

        @Override
        public GetMNPStatusResponse[] newArray(int size) {
            return new GetMNPStatusResponse[size];
        }
    };

    public int getOid() {
        return oid;
    }

    public String getOpName() {
        return opName;
    }

    public int getMnpStatus() {
        return mnpStatus;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getAppLink() {
        return appLink;
    }

    public String getMnpMobile() {
        return mnpMobile;
    }

    public ArrayList<MNPClaimsList> getMnpClaimsList() {
        return mnpClaimsList;
    }

    public String getMnpRemark() {
        return mnpRemark;
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
}
