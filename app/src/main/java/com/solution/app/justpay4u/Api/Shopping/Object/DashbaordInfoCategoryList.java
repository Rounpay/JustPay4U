package com.solution.app.justpay4u.Api.Shopping.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 13,December,2019
 */
public class DashbaordInfoCategoryList implements Parcelable {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName(value = "subcategory", alternate = "subCategory")
    @Expose
    public ArrayList<DashbaordInfoSubCategoryList> subcategory = null;
    @SerializedName(value = "categoryID", alternate = "categoryId")
    @Expose
    public int categoryID;
    @SerializedName(value = "mainCategoryID", alternate = "mainCategoryId")
    @Expose
    public int mainCategoryID;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName(value = "image", alternate = "categoryImage")
    @Expose
    public String image;

    public DashbaordInfoCategoryList(String $id, int categoryID, int mainCategoryID, String name, String image, ArrayList<DashbaordInfoSubCategoryList> subcategory) {
        this.$id = $id;
        this.subcategory = subcategory;
        this.categoryID = categoryID;
        this.mainCategoryID = mainCategoryID;
        this.name = name;
        this.image = image;
    }

    protected DashbaordInfoCategoryList(Parcel in) {
        $id = in.readString();
        subcategory = in.createTypedArrayList(DashbaordInfoSubCategoryList.CREATOR);
        categoryID = in.readInt();
        mainCategoryID = in.readInt();
        name = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString($id);
        dest.writeTypedList(subcategory);
        dest.writeInt(categoryID);
        dest.writeInt(mainCategoryID);
        dest.writeString(name);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<DashbaordInfoCategoryList> CREATOR = new Creator<DashbaordInfoCategoryList>() {
        @Override
        public DashbaordInfoCategoryList createFromParcel(Parcel in) {
            return new DashbaordInfoCategoryList(in);
        }

        @Override
        public DashbaordInfoCategoryList[] newArray(int size) {
            return new DashbaordInfoCategoryList[size];
        }
    };

    public String get$id() {
        return $id;
    }

    public ArrayList<DashbaordInfoSubCategoryList> getSubcategory() {
        return subcategory;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public int getMainCategoryID() {
        return mainCategoryID;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
