package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RankInfo implements Parcelable {
    @SerializedName("rankName")
    @Expose
    private String rankName;
    @SerializedName("rankImage")
    @Expose
    private String rankImage;
    @SerializedName("toupAmount")
    @Expose
    private Double toupAmount;
    @SerializedName("bussinessCurrId")
    @Expose
    private Integer bussinessCurrId;
    @SerializedName("bussinessCurrSymbol")
    @Expose
    private String bussinessCurrSymbol;
    @SerializedName("bussinessCurrImage")
    @Expose
    private String bussinessCurrImage;
    @SerializedName("bussinessCurrName")
    @Expose
    private String bussinessCurrName;

    protected RankInfo(Parcel in) {
        rankName = in.readString();
        rankImage = in.readString();
        if (in.readByte() == 0) {
            toupAmount = null;
        } else {
            toupAmount = in.readDouble();
        }
        if (in.readByte() == 0) {
            bussinessCurrId = null;
        } else {
            bussinessCurrId = in.readInt();
        }
        bussinessCurrSymbol = in.readString();
        bussinessCurrImage = in.readString();
        bussinessCurrName = in.readString();
    }

    public static final Creator<RankInfo> CREATOR = new Creator<RankInfo>() {
        @Override
        public RankInfo createFromParcel(Parcel in) {
            return new RankInfo(in);
        }

        @Override
        public RankInfo[] newArray(int size) {
            return new RankInfo[size];
        }
    };

    public String getRankName() {
        return rankName;
    }

    public String getRankImage() {
        return rankImage;
    }

    public Double getToupAmount() {
        return toupAmount;
    }

    public Integer getBussinessCurrId() {
        return bussinessCurrId;
    }

    public String getBussinessCurrSymbol() {
        return bussinessCurrSymbol;
    }

    public String getBussinessCurrImage() {
        return bussinessCurrImage;
    }

    public String getBussinessCurrName() {
        return bussinessCurrName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(rankName);
        dest.writeString(rankImage);
        if (toupAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(toupAmount);
        }
        if (bussinessCurrId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(bussinessCurrId);
        }
        dest.writeString(bussinessCurrSymbol);
        dest.writeString(bussinessCurrImage);
        dest.writeString(bussinessCurrName);
    }
}
