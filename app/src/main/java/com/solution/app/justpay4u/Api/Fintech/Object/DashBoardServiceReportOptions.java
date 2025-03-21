package com.solution.app.justpay4u.Api.Fintech.Object;

import java.util.ArrayList;

public class DashBoardServiceReportOptions {
    boolean isAddMoneyEnable;
    boolean isECollectEnable, isUpi, isUPIQR, isDmtWithPipe;

    ArrayList<AssignedOpType> retailerSericeOption = new ArrayList<>();
    ArrayList<AssignedOpType> retailerReportOption = new ArrayList<>();
    ArrayList<AssignedOpType> otherReportOption = new ArrayList<>();
    ArrayList<AssignedOpType> utilityRetailerOption = new ArrayList<>();
    ArrayList<AssignedOpType> utilityFosOption = new ArrayList<>();
    ArrayList<AssignedOpType> utilityOtherOption = new ArrayList<>();
    ArrayList<AssignedOpType> bankingOption = new ArrayList<>();


    public DashBoardServiceReportOptions(boolean isAddMoneyEnable, boolean isECollectEnable, boolean isUpi, boolean isUPIQR,
                                         boolean isDmtWithPipe, ArrayList<AssignedOpType> retailerSericeOption,
                                         ArrayList<AssignedOpType> retailerReportOption,
                                         ArrayList<AssignedOpType> otherReportOption,
                                         ArrayList<AssignedOpType> utilityRetailerOption,
                                         ArrayList<AssignedOpType> utilityFosOption,
                                         ArrayList<AssignedOpType> utilityOtherOption,
                                         ArrayList<AssignedOpType> bankingOption) {
        this.isAddMoneyEnable = isAddMoneyEnable;
        this.isECollectEnable = isECollectEnable;
        this.isUpi = isUpi;
        this.isUPIQR = isUPIQR;
        this.isDmtWithPipe = isDmtWithPipe;
        this.retailerSericeOption = retailerSericeOption;
        this.retailerReportOption = retailerReportOption;
        this.otherReportOption = otherReportOption;
        this.utilityRetailerOption = utilityRetailerOption;
        this.utilityFosOption = utilityFosOption;
        this.utilityOtherOption = utilityOtherOption;
        this.bankingOption = bankingOption;
    }

    public ArrayList<AssignedOpType> getRetailerSericeOption() {
        return retailerSericeOption;
    }

    public ArrayList<AssignedOpType> getRetailerReportOption() {
        return retailerReportOption;
    }

    public ArrayList<AssignedOpType> getOtherReportOption() {
        return otherReportOption;
    }

    public ArrayList<AssignedOpType> getBankingOption() {
        return bankingOption;
    }


    public boolean isECollectEnable() {
        return isECollectEnable;
    }


    public boolean isUpi() {
        return isUpi;
    }

    public boolean isUPIQR() {
        return isUPIQR;
    }

    public boolean isDmtWithPipe() {
        return isDmtWithPipe;
    }


    public ArrayList<AssignedOpType> getUtilityRetailerOption() {
        return utilityRetailerOption;
    }

    public ArrayList<AssignedOpType> getUtilityFosOption() {
        return utilityFosOption;
    }

    public ArrayList<AssignedOpType> getUtilityOtherOption() {
        return utilityOtherOption;
    }

    public boolean isAddMoneyEnable() {
        return isAddMoneyEnable;
    }


}
