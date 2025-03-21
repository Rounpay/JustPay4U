package com.solution.app.justpay4u.Fintech.Employee.Api.Object;

import java.util.ArrayList;

public class GetMeetingReport {

    int id;
    int userId;
    String userName;
    double totalTravel;
    double totalExpense;
    int meetingCount;
    boolean isClosed;
    String entryDate;
    ArrayList<GetMeetingDetails> meetingDetailList;
    ArrayList<MapPoints> mapPointList;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public double getTotalTravel() {
        return totalTravel;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public int getMeetingCount() {
        return meetingCount;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public ArrayList<GetMeetingDetails> getMeetingDetailList() {
        return meetingDetailList;
    }

    public void setMeetingDetailList(ArrayList<GetMeetingDetails> meetingDetailList) {
        this.meetingDetailList = meetingDetailList;
    }

    public ArrayList<MapPoints> getMapPointList() {
        return mapPointList;
    }

    public void setMapPointList(ArrayList<MapPoints> mapPointList) {
        this.mapPointList = mapPointList;
    }
}
