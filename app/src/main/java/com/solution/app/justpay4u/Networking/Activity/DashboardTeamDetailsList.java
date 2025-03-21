package com.solution.app.justpay4u.Networking.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardTeamDetailsList {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("displayName")
    @Expose
    public String displayName;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("displayFields")
    @Expose
    public String displayFields;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("headerName1")
    @Expose
    public String headerName1;
    @SerializedName("headerValue1")
    @Expose
    public String headerValue1;
    @SerializedName("headerName2")
    @Expose
    public String headerName2;
    @SerializedName("headerValue2")
    @Expose
    public String headerValue2;
    @SerializedName("headerName3")
    @Expose
    public String headerName3;
    @SerializedName("headerValue3")
    @Expose
    public String headerValue3;
    @SerializedName("headerName4")
    @Expose
    public String headerName4;
    @SerializedName("headerValue4")
    @Expose
    public int headerValue4;

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUrl() {
        return url;
    }

    public String getDisplayFields() {
        return displayFields;
    }

    public String getType() {
        return type;
    }

    public String getHeaderName1() {
        return headerName1;
    }

    public String getHeaderValue1() {
        return headerValue1;
    }

    public String getHeaderName2() {
        return headerName2;
    }

    public String getHeaderValue2() {
        return headerValue2;
    }

    public String getHeaderName3() {
        return headerName3;
    }

    public String getHeaderValue3() {
        return headerValue3;
    }

    public String getHeaderName4() {
        return headerName4;
    }

    public int getHeaderValue4() {
        return headerValue4;
    }
}

