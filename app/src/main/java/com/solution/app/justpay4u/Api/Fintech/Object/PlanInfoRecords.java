package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlanInfoRecords implements Parcelable {
    @SerializedName("plan")
    @Expose
    private ArrayList<PlanInfoPlan> plan = null;
    @SerializedName(value = "Add-On Pack", alternate = "add-onpack")
    @Expose
    private ArrayList<PlanInfoPlan> addOnPack = null;

    protected PlanInfoRecords(Parcel in) {
        plan = in.createTypedArrayList(PlanInfoPlan.CREATOR);
        addOnPack = in.createTypedArrayList(PlanInfoPlan.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(plan);
        dest.writeTypedList(addOnPack);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<PlanInfoRecords> CREATOR = new Creator<PlanInfoRecords>() {
        @Override
        public PlanInfoRecords createFromParcel(Parcel in) {
            return new PlanInfoRecords(in);
        }

        @Override
        public PlanInfoRecords[] newArray(int size) {
            return new PlanInfoRecords[size];
        }
    };

    public ArrayList<PlanInfoPlan> getPlan() {
        return plan;
    }

    public void setPlan(ArrayList<PlanInfoPlan> plan) {
        this.plan = plan;
    }

    public ArrayList<PlanInfoPlan> getAddOnPack() {
        return addOnPack;
    }

    public void setAddOnPack(ArrayList<PlanInfoPlan> addOnPack) {
        this.addOnPack = addOnPack;
    }

}
