package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChildRoles {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("ind")
    @Expose
    public int ind;
    @SerializedName("prefix")
    @Expose
    public String prefix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

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
