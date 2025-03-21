package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vishnu Agarwal on 17/01/2022.
 */
public class EKycDetailData {
    @SerializedName("ekycid")
    @Expose
    private int ekycid;
    @SerializedName("companyTypeID")
    @Expose
    private int companyTypeID;
    @SerializedName("stepCompleted")
    @Expose
    private int stepCompleted;
    @SerializedName("isGSTINEKYCDone")
    @Expose
    private boolean isGSTINEKYCDone;
    @SerializedName("isAadharEKYCDone")
    @Expose
    private boolean isAadharEKYCDone;
    @SerializedName("isPANEKYCDone")
    @Expose
    private boolean isPANEKYCDone;
    @SerializedName("isBanckAccountEKYCDone")
    @Expose
    private boolean isBanckAccountEKYCDone;
    @SerializedName("isGSTSkipped")
    @Expose
    private boolean isGSTSkipped;
    @SerializedName("isEKYCDone")
    @Expose
    private boolean isEKYCDone;
    @SerializedName("isGSTIN")
    @Expose
    private boolean isGSTIN;
    @SerializedName("gstAuthorizedSignatory")
    @Expose
    private String gstAuthorizedSignatory;
    @SerializedName("directors")
    @Expose
    private List<EKycDirectorOrCompany> directors = null;
    @SerializedName("gstin")
    @Expose
    private String gstin;
    @SerializedName("pan")
    @Expose
    private String pan;
    @SerializedName("aadharNo")
    @Expose
    private String aadharNo;
    @SerializedName("selectedDirector")
    @Expose
    private String selectedDirector;
    @SerializedName("selectedBank")
    @Expose
    private String selectedBank;
    @SerializedName("panOfDirector")
    @Expose
    private String panOfDirector;
    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("accountHolder")
    @Expose
    private String accountHolder;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("editStepID")
    @Expose
    private int editStepID;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("outletName")
    @Expose
    private String outletName;
    @SerializedName("ekycType")
    @Expose
    private List<EkycType> ekycType = null;
    @SerializedName("bankList")
    @Expose
    private Object bankList;
    @SerializedName("companyTypeSelect")
    @Expose
    private List<EKycDirectorOrCompany> companyTypeSelect = null;

    public int getEkycid() {
        return ekycid;
    }


    public int getCompanyTypeID() {
        return companyTypeID;
    }

    public int getStepCompleted() {
        return stepCompleted;
    }


    public boolean isIsGSTINEKYCDone() {
        return isGSTINEKYCDone;
    }


    public boolean isIsAadharEKYCDone() {
        return isAadharEKYCDone;
    }

    public String getSelectedBank() {
        return selectedBank;
    }

    public boolean isIsPANEKYCDone() {
        return isPANEKYCDone;
    }


    public boolean isIsBanckAccountEKYCDone() {
        return isBanckAccountEKYCDone;
    }


    public boolean isIsGSTSkipped() {
        return isGSTSkipped;
    }

    public boolean isIsEKYCDone() {
        return isEKYCDone;
    }


    public boolean isIsGSTIN() {
        return isGSTIN;
    }


    public String getGstAuthorizedSignatory() {
        return gstAuthorizedSignatory;
    }


    public List<EKycDirectorOrCompany> getDirectors() {
        return directors;
    }


    public String getGstin() {
        return gstin;
    }


    public String getPan() {
        return pan;
    }


    public String getAadharNo() {
        return aadharNo;
    }

    public String getSelectedDirector() {
        return selectedDirector;
    }


    public String getPanOfDirector() {
        return panOfDirector;
    }


    public String getAccountNumber() {
        return accountNumber;
    }


    public String getAccountHolder() {
        return accountHolder;
    }

    public String getIfsc() {
        return ifsc;
    }


    public int getEditStepID() {
        return editStepID;
    }

    public String getDob() {
        return dob;
    }


    public String getName() {
        return name;
    }


    public String getOutletName() {
        return outletName;
    }


    public List<EkycType> getEkycType() {
        return ekycType;
    }


    public Object getBankList() {
        return bankList;
    }


    public List<EKycDirectorOrCompany> getCompanyTypeSelect() {
        return companyTypeSelect;
    }


}
