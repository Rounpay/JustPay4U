package com.solution.app.justpay4u.Api.Networking.Object;



public class TeamData {

    public Integer id;
    public String name;
    public String displayName;
    public int downlinelUser;
    public int downlinelUserActive;
    public int downlinelUserDeActive;
    public String url;
    public boolean isActive;
    double business;


    public TeamData(Integer id, String name, String displayName, int downlinelUser, int downlinelUserActive, int downlinelUserDeActive, String url,double business, boolean isActive) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.downlinelUser = downlinelUser;
        this.downlinelUserActive = downlinelUserActive;
        this.downlinelUserDeActive = downlinelUserDeActive;
        this.url = url;
        this.business = business;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getDownlinelUser() {
        return downlinelUser;
    }

    public int getDownlinelUserActive() {
        return downlinelUserActive;
    }

    public int getDownlinelUserDeActive() {
        return downlinelUserDeActive;
    }

    public String getUrl() {
        return url;
    }


    public boolean isActive() {
        return isActive;
    }

    public double getBusiness() {
        return business;
    }
}

