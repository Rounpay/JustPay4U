package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoleSlab {
    @SerializedName("roles")
    @Expose
    public List<Role> roles = null;
    @SerializedName("slabs")
    @Expose
    public List<Slabs> slabs = null;

    public List<Role> getRoles() {
        return roles;
    }

    public List<Slabs> getSlabs() {
        return slabs;
    }
}
