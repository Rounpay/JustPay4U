package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Response.PlanRPResponse;

import java.util.ArrayList;
import java.util.Collections;

public class PlanInfoRPWithPackage implements Parcelable {
    String tel;
    String operator;
    PlanInfoRPRecords records;
    int status;
    ArrayList<PlanRPResponse> response = new ArrayList<>();
    @SerializedName("package")
    @Expose
    ArrayList<PlanRPResponse> packageList = new ArrayList<>();
    ArrayList<DthPlanLanguages> languages = new ArrayList<>();

    protected PlanInfoRPWithPackage(Parcel in) {
        tel = in.readString();
        operator = in.readString();
        records = in.readParcelable(PlanInfoRPRecords.class.getClassLoader());
        status = in.readInt();
        response = in.createTypedArrayList(PlanRPResponse.CREATOR);
        packageList = in.createTypedArrayList(PlanRPResponse.CREATOR);
        languages = in.createTypedArrayList(DthPlanLanguages.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tel);
        dest.writeString(operator);
        dest.writeParcelable(records, flags);
        dest.writeInt(status);
        dest.writeTypedList(response);
        dest.writeTypedList(packageList);
        dest.writeTypedList(languages);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<PlanInfoRPWithPackage> CREATOR = new Creator<PlanInfoRPWithPackage>() {
        @Override
        public PlanInfoRPWithPackage createFromParcel(Parcel in) {
            return new PlanInfoRPWithPackage(in);
        }

        @Override
        public PlanInfoRPWithPackage[] newArray(int size) {
            return new PlanInfoRPWithPackage[size];
        }
    };

    public String getTel() {
        return tel;
    }

    public String getOperator() {
        return operator;
    }

    public PlanInfoRPRecords getRecords() {
        return records;
    }

    public int getStatus() {
        return status;
    }

    public ArrayList<PlanRPResponse> getResponse() {
        if (response != null && response.size() > 0) {
            Collections.sort(response, (o1, o2) ->
                    Integer.compare(o2.getChannelcount(), o1.getChannelcount()));
        }
        return response;
    }

    public ArrayList<PlanRPResponse> getPackageList() {
        if (packageList != null && packageList.size() > 0) {
            Collections.sort(packageList, (o1, o2) ->
                    Integer.compare(o2.getChannelcount(), o1.getChannelcount()));
        }
        return packageList;
    }

    public ArrayList<DthPlanLanguages> getLanguages() {
        return languages;
    }
}

