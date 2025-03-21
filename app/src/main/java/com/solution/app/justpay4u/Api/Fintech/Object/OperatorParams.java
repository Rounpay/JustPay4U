package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperatorParams {
    @SerializedName("paramName")
    @Expose
    public String paramName;
    @SerializedName("dataType")
    @Expose
    public String dataType;
    @SerializedName("minLength")
    @Expose
    public int minLength;
    @SerializedName("maxLength")
    @Expose
    public int maxLength;
    @SerializedName("ind")
    @Expose
    public int ind;
    @SerializedName("regEx")
    @Expose
    public String regEx;
    @SerializedName("remark")
    @Expose
    public String remark;
    @SerializedName("isOptional")
    @Expose
    public boolean isOptional;
    @SerializedName("isCustomerNo")
    @Expose
    public boolean isCustomerNo;
    @SerializedName("isDropDown")
    @Expose
    public boolean isDropDown;
    @SerializedName("id")
    @Expose
    public int id;

    public String getParamName() {
        return paramName;
    }

    public String getDataType() {
        return dataType;
    }

    public int getMinLength() {
        if (minLength > 0) {
            return minLength;
        } else {
            return 1;
        }
    }

    public int getMaxLength() {
        if (maxLength > 0) {
            return maxLength;
        } else {
            return 50;
        }

    }

    public int getInd() {
        return ind;
    }

    public String getRegEx() {
        return regEx;
    }

    public String getRemark() {
        return remark;
    }

    public String getCustomRemark() {
        if (remark != null && !remark.isEmpty()) {
            return remark;
        } else {
            if (getDataType() != null && dataType.equalsIgnoreCase("NUMERIC")) {
                if ((getMaxLength() != 0 && getMinLength() != 0) && getMinLength() == getMaxLength()) {
                    remark = paramName + " (" + getMaxLength() + " digits)";
                } else if ((getMaxLength() != 0 && getMinLength() != 0) && getMinLength() != getMaxLength()) {
                    remark = paramName + " (" + getMinLength() + "-" + getMaxLength() + " digits)";
                } else if (getMaxLength() == 0 && getMinLength() != 0) {
                    remark = paramName + " (" + getMinLength() + " digits)";
                } else if (getMaxLength() != 0 && getMinLength() == 0) {
                    remark = paramName + " (" + getMaxLength() + " digits)";
                } else {
                    remark = "Enter " + paramName + " number";
                }

            } else if (getDataType() != null && dataType.equalsIgnoreCase("ALPHANUMERIC")) {
                if ((getMaxLength() != 0 && getMinLength() != 0) && getMinLength() == getMaxLength()) {
                    remark = paramName + " (" + getMaxLength() + " alphanumeric)";
                } else if ((getMaxLength() != 0 && getMinLength() != 0) && getMinLength() != getMaxLength()) {
                    remark = paramName + " (" + getMinLength() + "-" + getMaxLength() + " alphanumeric)";
                } else if (getMaxLength() == 0 && getMinLength() != 0) {
                    remark = paramName + " (" + getMinLength() + " alphanumeric)";
                } else if (getMaxLength() != 0 && getMinLength() == 0) {
                    remark = paramName + " (" + getMaxLength() + " alphanumeric)";
                } else {
                    remark = "Enter " + paramName;
                }

            } else {
                if ((getMaxLength() != 0 && getMinLength() != 0) && getMinLength() == getMaxLength()) {
                    remark = paramName + " (" + getMaxLength() + " characters)";
                } else if ((getMaxLength() != 0 && getMinLength() != 0) && getMinLength() != getMaxLength()) {
                    remark = paramName + " (" + getMinLength() + "-" + getMaxLength() + " characters)";
                } else if (getMaxLength() == 0 && getMinLength() != 0) {
                    remark = paramName + " (" + getMinLength() + " characters)";
                } else if (getMaxLength() != 0 && getMinLength() == 0) {
                    remark = paramName + " (" + getMaxLength() + " characters)";
                } else {
                    remark = "Enter " + paramName;
                }
            }
            return remark;
        }
    }

    public boolean isOptional() {
        return isOptional;
    }

    public boolean isCustomerNo() {
        return isCustomerNo;
    }

    public boolean isDropDown() {
        return isDropDown;
    }

    public int getId() {
        return id;
    }
}
