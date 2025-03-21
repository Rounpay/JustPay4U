package com.solution.app.justpay4u.Api.Shopping.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vishnu Agarwal on 21,December,2019
 */
public class SearchKeywordResponse implements Serializable {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("keywordId")
    @Expose
    public int keywordId;
    @SerializedName("keyword")
    @Expose
    public String keyword;
    @SerializedName("subcategoryName")
    @Expose
    public String subcategoryName;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("subcategoryId")
    @Expose
    public int subcategoryId;

    public String get$id() {
        return $id;
    }

    public int getKeywordId() {
        return keywordId;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public String getImage() {
        return image;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }
}
