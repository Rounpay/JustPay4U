package com.solution.app.justpay4u.ApiHits;

import java.util.ArrayList;

public class IndustryWiseData {

    int id;
    String industryType;
    String remark;
    String upline;
    String uplineMobile;
    String ccContact;
    ArrayList<IndustryWiseOpType> opTypes;

    public int getId() {
        return id;
    }

    public String getIndustryType() {
        return industryType;
    }

    public String getRemark() {
        return remark;
    }

    public String getUpline() {
        return upline;
    }

    public String getUplineMobile() {
        return uplineMobile;
    }

    public String getCcContact() {
        return ccContact;
    }

    public ArrayList<IndustryWiseOpType> getOpTypes() {
        return opTypes;
    }
}
