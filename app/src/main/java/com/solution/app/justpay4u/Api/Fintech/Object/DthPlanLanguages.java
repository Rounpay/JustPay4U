package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

public class DthPlanLanguages implements Parcelable {
    String language;
    String opname;
    int packageCount;

    protected DthPlanLanguages(Parcel in) {
        language = in.readString();
        opname = in.readString();
        packageCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(language);
        dest.writeString(opname);
        dest.writeInt(packageCount);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<DthPlanLanguages> CREATOR = new Creator<DthPlanLanguages>() {
        @Override
        public DthPlanLanguages createFromParcel(Parcel in) {
            return new DthPlanLanguages(in);
        }

        @Override
        public DthPlanLanguages[] newArray(int size) {
            return new DthPlanLanguages[size];
        }
    };

    public String getLanguage() {
        return language;
    }

    public String getOpname() {
        return opname;
    }

    public int getPackageCount() {
        return packageCount;
    }
}
