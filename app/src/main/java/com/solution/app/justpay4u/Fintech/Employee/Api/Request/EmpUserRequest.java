package com.solution.app.justpay4u.Fintech.Employee.Api.Request;

public class EmpUserRequest {

    String criteriaText;
    int criteriaID;
    int employeeRole;
    boolean sortById;
    boolean isDesc;
    int uid;
    int topRows;

    public String userID;

    public String sessionID;

    public String session;

    public String appid;

    public String imei;

    public String regKey;

    public String version;

    public String serialNo;

    public int loginTypeID;


    public EmpUserRequest(int criteriaID, String criteriaText, int employeeRole, boolean sortById, boolean isDesc, int uid,
                          int topRows, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.criteriaID = criteriaID;
        this.criteriaText = criteriaText;

        this.employeeRole = employeeRole;
        this.sortById = sortById;
        this.isDesc = isDesc;
        this.uid = uid;
        this.topRows = topRows;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
    }

}
