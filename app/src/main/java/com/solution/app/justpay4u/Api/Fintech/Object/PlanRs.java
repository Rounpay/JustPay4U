package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlanRs implements Parcelable {

    @SerializedName(value = "1 MONTHS", alternate = {"1 months", "monthly"})
    @Expose
    private String _1MONTHS;
    @SerializedName(value = "3 MONTHS", alternate = {"3 months", "quarterly"})
    @Expose
    private String _3MONTHS;
    @SerializedName(value = "6 MONTHS", alternate = {"6 months", "halfYearly"})
    @Expose
    private String _6MONTHS;
    @SerializedName(value = "9 MONTHS", alternate = "9 months")
    @Expose
    private String _9MONTHS;
    @SerializedName(value = "1 YEAR", alternate = {"1 year", "yearly"})
    @Expose
    private String _1YEAR;
    @SerializedName(value = "5 YEAR", alternate = "5 year")
    @Expose
    private String _5YEAR;

    protected PlanRs(Parcel in) {
        _1MONTHS = in.readString();
        _3MONTHS = in.readString();
        _6MONTHS = in.readString();
        _9MONTHS = in.readString();
        _1YEAR = in.readString();
        _5YEAR = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_1MONTHS);
        dest.writeString(_3MONTHS);
        dest.writeString(_6MONTHS);
        dest.writeString(_9MONTHS);
        dest.writeString(_1YEAR);
        dest.writeString(_5YEAR);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<PlanRs> CREATOR = new Creator<PlanRs>() {
        @Override
        public PlanRs createFromParcel(Parcel in) {
            return new PlanRs(in);
        }

        @Override
        public PlanRs[] newArray(int size) {
            return new PlanRs[size];
        }
    };

    public String get1MONTHS() {
        return _1MONTHS;
    }

    public void set1MONTHS(String _1MONTHS) {
        this._1MONTHS = _1MONTHS;
    }

    public String get3MONTHS() {
        return _3MONTHS;
    }

    public void set3MONTHS(String _3MONTHS) {
        this._3MONTHS = _3MONTHS;
    }

    public String get6MONTHS() {
        return _6MONTHS;
    }

    public void set6MONTHS(String _6MONTHS) {
        this._6MONTHS = _6MONTHS;
    }

    public String get9MONTHS() {
        return _9MONTHS;
    }

    public void set9MONTHS(String _9MONTHS) {
        this._9MONTHS = _9MONTHS;
    }

    public String get1YEAR() {
        return _1YEAR;
    }

    public void set1YEAR(String _1YEAR) {
        this._1YEAR = _1YEAR;
    }

    public String get5YEAR() {
        return _5YEAR;
    }

    public void set5YEAR(String _5YEAR) {
        this._5YEAR = _5YEAR;
    }
}
