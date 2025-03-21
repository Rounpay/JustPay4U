package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageSetting {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("topRows")
    @Expose
    private Integer topRows;
    @SerializedName("pageNumber")
    @Expose
    private Integer pageNumber;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTopRows() {
        return topRows;
    }

    public void setTopRows(Integer topRows) {
        this.topRows = topRows;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
