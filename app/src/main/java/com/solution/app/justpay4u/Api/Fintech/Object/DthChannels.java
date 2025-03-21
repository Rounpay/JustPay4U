package com.solution.app.justpay4u.Api.Fintech.Object;

public class DthChannels {
    int id;
    String channelName, header;
    int categoryID;
    String categoryName;
    String categories;
    boolean del;
    boolean isActive;


    public DthChannels(String header, int id, String channelName, int categoryID, String categoryName, String categories, boolean del, boolean isActive) {
        this.id = id;
        this.channelName = channelName;
        this.header = header;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categories = categories;
        this.del = del;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getChannelName() {
        return channelName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategories() {
        return categories;
    }

    public boolean isDel() {
        return del;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getHeader() {
        return header;
    }
}
