package com.solution.app.justpay4u.Api.Shopping.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 13,December,2019
 */
public class DashbaordInfoSubCategoryList implements Parcelable {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("subCategoryId")
    @Expose
    public int subCategoryId;
    @SerializedName(value = "categoryID", alternate = "categoryId")
    @Expose
    public int categoryID;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName(value = "subCategoryImage", alternate = "image")
    @Expose
    public String subCategoryImage;

    protected DashbaordInfoSubCategoryList(Parcel in) {
        $id = in.readString();
        subCategoryId = in.readInt();
        categoryID = in.readInt();
        name = in.readString();
        subCategoryImage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString($id);
        dest.writeInt(subCategoryId);
        dest.writeInt(categoryID);
        dest.writeString(name);
        dest.writeString(subCategoryImage);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<DashbaordInfoSubCategoryList> CREATOR = new Creator<DashbaordInfoSubCategoryList>() {
        @Override
        public DashbaordInfoSubCategoryList createFromParcel(Parcel in) {
            return new DashbaordInfoSubCategoryList(in);
        }

        @Override
        public DashbaordInfoSubCategoryList[] newArray(int size) {
            return new DashbaordInfoSubCategoryList[size];
        }
    };

    public String get$id() {
        return $id;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return subCategoryImage;
    }
}
