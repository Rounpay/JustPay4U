package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataOpType {
    @SerializedName("assignedOpTypes")
    @Expose
    private ArrayList<AssignedOpType> assignedOpTypes = null;


    public ArrayList<AssignedOpType> getAssignedOpTypes() {
        return assignedOpTypes;
    }

   /* public void setAssignedOpTypes(ArrayList<AssignedOpType> assignedOpTypes) {
        this.assignedOpTypes = assignedOpTypes;
    }


    public ArrayList<AssignedOpType> getRetailerSeriveOptions() {
        ArrayList<AssignedOpType> reportSerive= new ArrayList<>();

        reportSerive.add(new AssignedOpType(1016, "Call Back\nRequest", true, true, true, true));
        reportSerive.add(new AssignedOpType(1021, "Upgrade\nPackage", true, true, true, true));

        return reportSerive;
    }*/

    public ArrayList<AssignedOpType> getOtherReportSeriveOptions(boolean isDmtEnable, boolean isDMTActive,
                                                                 boolean isDMTServiceActive,
                                                                 boolean isAdditionalServiceType, boolean isPaidAdditional) {

        ArrayList<AssignedOpType> reportSerive = new ArrayList<>();
        //reportSerive.add(new AssignedOpType(1009, "Fund Order Pending", true, true, true, true));

        //reportSerive.add(new AssignedOpType(1010, "Fund\nRequest", true, true, true, true));
        reportSerive.add(new AssignedOpType(1001, "Recharge Report", true, true, true, true));
        // reportSerive.add(new AssignedOpType(1022, "DTH Subscription Report", true, true, true, true));
        if (isDmtEnable) {
            reportSerive.add(new AssignedOpType(1005, "DMT Report", isDMTActive, isDMTServiceActive, isAdditionalServiceType, isPaidAdditional));
        }
       // reportSerive.add(new AssignedOpType(1020, "AEPS Report", true, true, true, true));
        reportSerive.add(new AssignedOpType(1018, "Specific Recharge\nReport", true, true, true, true));
        reportSerive.add(new AssignedOpType(1002, "Ledger Report", true, true, true, true));
        reportSerive.add(new AssignedOpType(1003, "Fund Order Report", true, true, true, true));
        //reportSerive.add(new AssignedOpType(1025, "Downline Fund Order Report", true, true, true, true));
        reportSerive.add(new AssignedOpType(1004, "Complaint Report", true, true, true, true));
        // reportSerive.add(new ActiveSerive(1006, "Fund Tran\nReport", true,true));
        reportSerive.add(new AssignedOpType(1007, "Fund Debit Credit", true, true, true, true));
        reportSerive.add(new AssignedOpType(1008, "User Daybook", true, true, true, true));

        reportSerive.add(new AssignedOpType(1014, "W2R Report", true, true, true, true));
        reportSerive.add(new AssignedOpType(1015, "Daybook DMT", true, true, true, true));
        reportSerive.add(new AssignedOpType(1035, "MoveToBank Report", true, true, true, true));
        reportSerive.add(new AssignedOpType(1036, "MNP Claim Report", true, true, true, true));
//        reportSerive.add(new AssignedOpType(1078, "UpLine Report", true, true, true, true));

        return reportSerive;
    }

    public ArrayList<AssignedOpType> getRetailerReportOptions(boolean isDmtEnable, boolean isDMTActive, boolean isDMTServiceActive, boolean isAccountStatment,
                                                              boolean isAdditionalServiceType, boolean isPaidAdditional) {

        ArrayList<AssignedOpType> reportSerive = new ArrayList<>();
        reportSerive.add(new AssignedOpType(1001, "Recharge Report", true, true, true, true));
        // reportSerive.add(new AssignedOpType(1022, "DTH Subscription Report", true, true, true, true));
        if (isDmtEnable) {
            reportSerive.add(new AssignedOpType(1005, "DMT Report", true, true, true, true));

            //reportSerive.add(new AssignedOpType(1005, "DMT Report", isDMTActive, isDMTServiceActive, isAdditionalServiceType, isPaidAdditional));
        }
       // reportSerive.add(new AssignedOpType(1020, "AEPS Report", true, true, true, true));
        reportSerive.add(new AssignedOpType(1018, "Specific Recharge Report", true, true, true, true));
        reportSerive.add(new AssignedOpType(1002, "Ledger Report", true, true, true, true));
        reportSerive.add(new AssignedOpType(1003, "Fund Order Report", true, true, true, true));
        reportSerive.add(new AssignedOpType(1004, "Complaint Report", true, true, true, true));
        // reportSerive.add(new AssignedOpType(1006, "Fund Tran\nReport", true,true));
        reportSerive.add(new AssignedOpType(1007, "Fund Debit Credit", true, true, true, true));
        reportSerive.add(new AssignedOpType(1008, "User Daybook", true, true, true, true));

        reportSerive.add(new AssignedOpType(1014, "W2R Report", true, true, true, true));
        reportSerive.add(new AssignedOpType(1015, "Daybook DMT", true, true, true, true));
        if (isAccountStatment) {
            reportSerive.add(new AssignedOpType(1031, "Account Statement", true, true, true, true));
        }
        reportSerive.add(new AssignedOpType(1035, "MoveToBank Report", true, true, true, true));
        reportSerive.add(new AssignedOpType(1036, "MNP Claim Report", true, true, true, true));
//        reportSerive.add(new AssignedOpType(1078, "UpLine Report", true, true, true, true));

        return reportSerive;
    }


    /* public ArrayList<AssignedOpType> getBankingOptions(boolean) {

         ArrayList<AssignedOpType> reportSerive = new ArrayList<>();

         reportSerive.add(new AssignedOpType(1023, "Send\nMoney", true, true, true, true));



         return reportSerive;
     }*/
    public ArrayList<AssignedOpType> getSendMoneyOptions() {

        ArrayList<AssignedOpType> reportSerive = new ArrayList<>();

        reportSerive.add(new AssignedOpType(1023, "AEPS Settlement", true, true, true, true));

        return reportSerive;
    }


    public ArrayList<AssignedOpType> getUtilityRetailerOptions() {

        ArrayList<AssignedOpType> reportSerive = new ArrayList<>();

        // reportSerive.add(new AssignedOpType(1010, "Fund\nRequest", true, true, true, true));


        reportSerive.add(new AssignedOpType(1016, "Call Back\nRequest", true, true, true, true));
       // reportSerive.add(new AssignedOpType(1021, "Upgrade\nPackage", true, true, true, true));
//        reportSerive.add(new AssignedOpType(1013, "Commission\nSlab", true, true, true, true));
        reportSerive.add(new AssignedOpType(555, "Business\nPlan", true, true, true, true));

        return reportSerive;
    }

    public ArrayList<AssignedOpType> getUtilityFOSOptions(boolean isAccountStatment) {

        ArrayList<AssignedOpType> reportSerive = new ArrayList<>();


        reportSerive.add(new AssignedOpType(1012, "Users List", true, true, true, true));
        reportSerive.add(new AssignedOpType(1028, "Ledger\nReport", true, true, true, true));
        if (isAccountStatment) {
            reportSerive.add(new AssignedOpType(1029, "Areas", true, true, true, true));
            reportSerive.add(new AssignedOpType(1030, "Channel Outstanding Report", true, true, true, true));
        }
        return reportSerive;
    }

    public ArrayList<AssignedOpType> getUtilityOtherOptions(boolean isAccountStatment) {

        ArrayList<AssignedOpType> reportSerive = new ArrayList<>();

        // reportSerive.add(new AssignedOpType(1010, "Fund\nRequest", true, true, true, true));

        // reportSerive.add(new AssignedOpType(1017, "Scan & Pay", true, true, true, true));
        reportSerive.add(new AssignedOpType(1012, "Users List", true, true, true, true));
        reportSerive.add(new AssignedOpType(1009, "Fund Order", true, true, true, true));
        reportSerive.add(new AssignedOpType(1013, "Commission Slab", true, true, true, true));
        reportSerive.add(new AssignedOpType(1011, "Create\nUser", true, true, true, true));
        reportSerive.add(new AssignedOpType(1024, "Create FOS\nUser", true, true, true, true));
        reportSerive.add(new AssignedOpType(1016, "Call Back\nRequest", true, true, true, true));
        if (isAccountStatment) {
            reportSerive.add(new AssignedOpType(1026, "Account Statement", true, true, true, true));
            reportSerive.add(new AssignedOpType(1027, "FOS Outstanding Report", true, true, true, true));
            reportSerive.add(new AssignedOpType(1030, "Channel Outstanding Report", true, true, true, true));
            reportSerive.add(new AssignedOpType(1032, "Voucher Entry", true, true, true, true));
        }
        // reportSerive.add(new AssignedOpType(1021, "Upgrade\nPackage", true, true, true, true));


        return reportSerive;
    }

    public ArrayList<AssignedOpType> getEmpDashboardOption() {

        ArrayList<AssignedOpType> reportSerive = new ArrayList<>();
        reportSerive.add(new AssignedOpType(2026, "Employee List", true, true, true, true));
        reportSerive.add(new AssignedOpType(2027, "User List", true, true, true, true));
        reportSerive.add(new AssignedOpType(2028, "New Meeting", true, true, true, true));
        reportSerive.add(new AssignedOpType(2029, "Daily Attendance", true, true, true, true));

        return reportSerive;
    }

    public ArrayList<AssignedOpType> getEmpReportSerive() {

        ArrayList<AssignedOpType> reportSerive = new ArrayList<>();
        reportSerive.add(new AssignedOpType(2030, "PST", true, true, true, true));
        reportSerive.add(new AssignedOpType(2031, "Tertiary", true, true, true, true));
        reportSerive.add(new AssignedOpType(2032, "Target", true, true, true, true));
        reportSerive.add(new AssignedOpType(2001, "Transaction History", true, true, true, true));
        reportSerive.add(new AssignedOpType(2002, "Account Ledger", true, true, true, true));
        /* reportSerive.add(new AssignedOpType(2023, "Sales\nSummary", true, true, true, true));*/
        reportSerive.add(new AssignedOpType(2008, "User Daybook", true, true, true, true));
        reportSerive.add(new AssignedOpType(2005, "DMT Report", true, true, true, true));
        reportSerive.add(new AssignedOpType(2033, "Meeting Details", true, true, true, true));
        reportSerive.add(new AssignedOpType(2034, "Meeting Report", true, true, true, true));

        return reportSerive;
    }
}
