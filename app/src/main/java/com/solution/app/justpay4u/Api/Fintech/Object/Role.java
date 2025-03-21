package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Role implements Parcelable {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("ind")
    @Expose
    public int ind;

    protected Role(Parcel in) {
        id = in.readInt();
        role = in.readString();
        ind = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(role);
        dest.writeInt(ind);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<Role> CREATOR = new Creator<Role>() {
        @Override
        public Role createFromParcel(Parcel in) {
            return new Role(in);
        }

        @Override
        public Role[] newArray(int size) {
            return new Role[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public int getInd() {
        return ind;
    }
}
