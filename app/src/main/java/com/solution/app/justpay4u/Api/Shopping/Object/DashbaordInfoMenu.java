package com.solution.app.justpay4u.Api.Shopping.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 13,December,2019
 */
public class DashbaordInfoMenu implements Parcelable {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName(value = "mainCategoryID", alternate = "mainCategoryId")
    @Expose
    public int mainCategoryID;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("categoryString")
    @Expose
    public String categoryString;

    @SerializedName(value = "mainCategoryImage", alternate = "image")
    @Expose
    public String mainCategoryImage;
    @SerializedName("categoryList")
    @Expose
    public ArrayList<DashbaordInfoCategoryList> categoryList = null;

    protected DashbaordInfoMenu(Parcel in) {
        $id = in.readString();
        mainCategoryID = in.readInt();
        name = in.readString();
        categoryString = in.readString();
        mainCategoryImage = in.readString();
        categoryList = in.createTypedArrayList(DashbaordInfoCategoryList.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString($id);
        dest.writeInt(mainCategoryID);
        dest.writeString(name);
        dest.writeString(categoryString);
        dest.writeString(mainCategoryImage);
        dest.writeTypedList(categoryList);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<DashbaordInfoMenu> CREATOR = new Creator<DashbaordInfoMenu>() {
        @Override
        public DashbaordInfoMenu createFromParcel(Parcel in) {
            return new DashbaordInfoMenu(in);
        }

        @Override
        public DashbaordInfoMenu[] newArray(int size) {
            return new DashbaordInfoMenu[size];
        }
    };

    public String get$id() {
        return $id;
    }

    public int getMainCategoryID() {
        return mainCategoryID;
    }

    public String getMainCategoryImage() {
        return mainCategoryImage;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return mainCategoryImage;
    }

    public String getCategoryString() {
        return categoryString != null && !categoryString.isEmpty() ? categoryString : "";
    }

    public ArrayList<DashbaordInfoCategoryList> getCategoryList() {
        return categoryList;
    }
}
