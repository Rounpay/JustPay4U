package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

public class CircleList implements Parcelable {
    public static final Creator<CircleList> CREATOR = new Creator<CircleList>() {
        @Override
        public CircleList createFromParcel(Parcel in) {
            return new CircleList(in);
        }

        @Override
        public CircleList[] newArray(int size) {
            return new CircleList[size];
        }
    };
    private String id;
    private String circle;

    protected CircleList(Parcel in) {
        id = in.readString();
        circle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(circle);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }
}
