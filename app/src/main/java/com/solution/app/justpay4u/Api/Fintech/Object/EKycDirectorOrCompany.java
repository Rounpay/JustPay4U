package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 17/01/2022.
 */
public class EKycDirectorOrCompany {
    @SerializedName("disabled")
    @Expose
    private boolean disabled;
    @SerializedName("group")
    @Expose
    private Object group;
    @SerializedName("selected")
    @Expose
    private boolean selected;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("value")
    @Expose
    private String value;

    public boolean isDisabled() {
        return disabled;
    }


    public Object getGroup() {
        return group;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }

}
