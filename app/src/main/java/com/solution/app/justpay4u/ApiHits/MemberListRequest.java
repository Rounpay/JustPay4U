package com.solution.app.justpay4u.ApiHits;


import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;

public class MemberListRequest {

    BasicRequest appSession;
    MemberListRequest request;
    String DateFilter;
    String SearchType;
    String FromDate;
    String ToDate;
    String Status, Leg, BusinessType, SponserId;
    int LevelNo;
    String strStatus;
    String downlineID;
    String searchUserId ,UserID;
    boolean IsRecent;


    public MemberListRequest(BasicRequest appSession, MemberListRequest request) {
        this.appSession = appSession;
        this.request = request;
    }


    public MemberListRequest(String Leg, String FromDate, String ToDate) {
        this.Leg = Leg;
        this.FromDate = FromDate;
        this.ToDate = ToDate;

    }

    public MemberListRequest(String DateFilter, String SearchType, String Leg, int LevelNo, String FromDate, String ToDate, String Status,boolean isRecent) {
        this.DateFilter = DateFilter;
        this.SearchType = SearchType;
        this.Leg = Leg;
        this.LevelNo = LevelNo;
        this.FromDate = FromDate;
        this.ToDate = ToDate;
        this.Status = Status;
        this.IsRecent = isRecent;
    }

    public MemberListRequest(int LevelNo, String FromDate, String ToDate, String Status) {
        this.LevelNo = LevelNo;
        this.FromDate = FromDate;
        this.ToDate = ToDate;
        this.Status = Status;
    }

    public MemberListRequest(boolean IsRecent,String downlineID, String SearchType,int LevelNo, String FromDate, String ToDate, String Status) {
        this.IsRecent = IsRecent;
        this.searchUserId = downlineID;
        this.SearchType = SearchType;
        this.LevelNo = LevelNo;
        this.FromDate = FromDate;
        this.ToDate = ToDate;
        this.Status = Status;
    }

    public MemberListRequest(String SponserId, String BusinessType, int LevelNo, String FromDate, String ToDate, boolean IsRecent) {
        this.SponserId = SponserId;
        this.BusinessType = BusinessType;
        this.LevelNo = LevelNo;
        this.FromDate = FromDate;
        this.ToDate = ToDate;
        this.IsRecent = IsRecent;
    }

    public MemberListRequest(String Leg, String BusinessType, String FromDate, String ToDate) {
        this.Leg = Leg;
        this.BusinessType = BusinessType;
        this.FromDate = FromDate;
        this.ToDate = ToDate;
    }

    public MemberListRequest(String Leg) {
        this.Leg = Leg;
    }

    public MemberListRequest(int LevelNo, String Leg, String BusinessType, String FromDate, String ToDate) {
        this.LevelNo = LevelNo;
        this.Leg = Leg;
        this.BusinessType = BusinessType;
        this.FromDate = FromDate;
        this.ToDate = ToDate;
    }

    public MemberListRequest(int LevelNo, String Status) {
        this.LevelNo = LevelNo;
        this.Status = Status;
    }
}
