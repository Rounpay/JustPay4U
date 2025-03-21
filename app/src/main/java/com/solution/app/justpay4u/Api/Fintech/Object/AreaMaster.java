package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AreaMaster implements Parcelable {
    @SerializedName("areaID")
    @Expose
    private int areaID;
    @SerializedName("userID")
    @Expose
    private int userID;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("entryDate")
    @Expose
    private String entryDate;
    @SerializedName("modifyDate")
    @Expose
    private String modifyDate;

    public AreaMaster() {
    }

    protected AreaMaster(Parcel in) {
        areaID = in.readInt();
        userID = in.readInt();
        area = in.readString();
        entryDate = in.readString();
        modifyDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(areaID);
        dest.writeInt(userID);
        dest.writeString(area);
        dest.writeString(entryDate);
        dest.writeString(modifyDate);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<AreaMaster> CREATOR = new Creator<AreaMaster>() {
        @Override
        public AreaMaster createFromParcel(Parcel in) {
            return new AreaMaster(in);
        }

        @Override
        public AreaMaster[] newArray(int size) {
            return new AreaMaster[size];
        }
    };

    public int getAreaID() {
        return areaID;
    }

    public void setAreaID(int areaID) {
        this.areaID = areaID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

}

