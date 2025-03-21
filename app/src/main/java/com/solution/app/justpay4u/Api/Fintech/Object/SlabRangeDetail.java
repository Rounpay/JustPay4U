package com.solution.app.justpay4u.Api.Fintech.Object;

public class SlabRangeDetail {

    int dmrModelID;
    int id;
    int oid;
    int slabID;
    int rangeId;
    int minRange;
    int maxRange;
    double comm;
    double maxComm;
    double fixedCharge;
    boolean commType;
    boolean amtType;
    String operator;

    public int getId() {
        return id;
    }

    public int getOid() {
        return oid;
    }

    public int getSlabID() {
        return slabID;
    }

    public int getRangeId() {
        return rangeId;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public double getComm() {
        return comm;
    }

    public double getMaxComm() {
        return maxComm;
    }

    public double getFixedCharge() {
        return fixedCharge;
    }

    public boolean isCommType() {
        return commType;
    }

    public boolean isAmtType() {
        return amtType;
    }

    public int getDmrModelID() {
        return dmrModelID;
    }

    public String getOperator() {
        return operator;
    }
}
