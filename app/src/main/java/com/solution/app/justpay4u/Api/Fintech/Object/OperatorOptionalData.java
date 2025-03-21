package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OperatorOptionalData {
    @SerializedName("operatorOptionals")
    @Expose
    public List<OperatorOptional> operatorOptionals = null;
    @SerializedName("operatorParams")
    @Expose
    public List<OperatorParams> operatorParams = null;
    @SerializedName("opOptionalDic")
    @Expose
    public List<OpOptionalDic> opOptionalDic = null;

    public List<OperatorOptional> getOperatorOptionals() {
        return operatorOptionals;
    }

    public List<OperatorParams> getOperatorParams() {
        return operatorParams;
    }

    public List<OpOptionalDic> getOpOptionalDic() {
        return opOptionalDic;
    }
}
