package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettlementAccountData implements Parcelable {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("bankID")
    @Expose
    public int bankID;
    @SerializedName("bankName")
    @Expose
    public String bankName;
    @SerializedName("ifsc")
    @Expose
    public String ifsc;
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;
    @SerializedName("accountHolder")
    @Expose
    public String accountHolder;
    @SerializedName("entryBy")
    @Expose
    public int entryBy;
    @SerializedName("entryDate")
    @Expose
    public String entryDate;
    @SerializedName("approvedBY")
    @Expose
    public String approvedBY;
    @SerializedName("approvalIp")
    @Expose
    public String approvalIp;
    @SerializedName("approvalDate")
    @Expose
    public String approvalDate;
    @SerializedName("actualname")
    @Expose
    public String actualname;
    @SerializedName("utr")
    @Expose
    public String utr;
    @SerializedName("apiid")
    @Expose
    public String apiid;
    @SerializedName("approvalStatus")
    @Expose
    public int approvalStatus;
    @SerializedName("verificationStatus")
    @Expose
    public int verificationStatus;
    @SerializedName("isdeleted")
    @Expose
    public String isdeleted;
    @SerializedName("isDefault")
    @Expose
    public boolean isDefault;
    @SerializedName("verificationText")
    @Expose
    public String verificationText;
    @SerializedName("approvalText")
    @Expose
    public String approvalText;
    @SerializedName("bankselect")
    @Expose
    public String bankselect;
    @SerializedName("requestdate")
    @Expose
    public String requestdate;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("requestID")
    @Expose
    public int requestID;
    @SerializedName("loginID")
    @Expose
    public int loginID;
    @SerializedName("loginTypeID")
    @Expose
    public int loginTypeID;
    @SerializedName("userID")
    @Expose
    public int userID;
    @SerializedName("commonInt")
    @Expose
    public int commonInt;
    @SerializedName("commonInt2")
    @Expose
    public int commonInt2;
    @SerializedName("commonStr")
    @Expose
    public String commonStr;
    @SerializedName("commonStr2")
    @Expose
    public String commonStr2;
    @SerializedName("isListType")
    @Expose
    public boolean isListType;
    @SerializedName("str")
    @Expose
    public String str;
    @SerializedName("commonInt3")
    @Expose
    public int commonInt3;
    @SerializedName("commonDecimal")
    @Expose
    public double commonDecimal;
    @SerializedName("commonInt4")
    @Expose
    public int commonInt4;
    @SerializedName("commonStr3")
    @Expose
    public String commonStr3;
    @SerializedName("commonStr4")
    @Expose
    public String commonStr4;
    @SerializedName("commonBool")
    @Expose
    public boolean commonBool;
    @SerializedName("commonBool1")
    @Expose
    public boolean commonBool1;
    @SerializedName("commonBool2")
    @Expose
    public boolean commonBool2;
    @SerializedName("commonChar")
    @Expose
    public String commonChar;


    protected SettlementAccountData(Parcel in) {
        id = in.readInt();
        bankID = in.readInt();
        bankName = in.readString();
        ifsc = in.readString();
        accountNumber = in.readString();
        accountHolder = in.readString();
        entryBy = in.readInt();
        entryDate = in.readString();
        approvedBY = in.readString();
        approvalIp = in.readString();
        approvalDate = in.readString();
        actualname = in.readString();
        utr = in.readString();
        apiid = in.readString();
        approvalStatus = in.readInt();
        verificationStatus = in.readInt();
        isdeleted = in.readString();
        isDefault = in.readByte() != 0;
        verificationText = in.readString();
        approvalText = in.readString();
        bankselect = in.readString();
        requestdate = in.readString();
        mobileNo = in.readString();
        name = in.readString();
        requestID = in.readInt();
        loginID = in.readInt();
        loginTypeID = in.readInt();
        userID = in.readInt();
        commonInt = in.readInt();
        commonInt2 = in.readInt();
        commonStr = in.readString();
        commonStr2 = in.readString();
        isListType = in.readByte() != 0;
        str = in.readString();
        commonInt3 = in.readInt();
        commonDecimal = in.readDouble();
        commonInt4 = in.readInt();
        commonStr3 = in.readString();
        commonStr4 = in.readString();
        commonBool = in.readByte() != 0;
        commonBool1 = in.readByte() != 0;
        commonBool2 = in.readByte() != 0;
        commonChar = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(bankID);
        dest.writeString(bankName);
        dest.writeString(ifsc);
        dest.writeString(accountNumber);
        dest.writeString(accountHolder);
        dest.writeInt(entryBy);
        dest.writeString(entryDate);
        dest.writeString(approvedBY);
        dest.writeString(approvalIp);
        dest.writeString(approvalDate);
        dest.writeString(actualname);
        dest.writeString(utr);
        dest.writeString(apiid);
        dest.writeInt(approvalStatus);
        dest.writeInt(verificationStatus);
        dest.writeString(isdeleted);
        dest.writeByte((byte) (isDefault ? 1 : 0));
        dest.writeString(verificationText);
        dest.writeString(approvalText);
        dest.writeString(bankselect);
        dest.writeString(requestdate);
        dest.writeString(mobileNo);
        dest.writeString(name);
        dest.writeInt(requestID);
        dest.writeInt(loginID);
        dest.writeInt(loginTypeID);
        dest.writeInt(userID);
        dest.writeInt(commonInt);
        dest.writeInt(commonInt2);
        dest.writeString(commonStr);
        dest.writeString(commonStr2);
        dest.writeByte((byte) (isListType ? 1 : 0));
        dest.writeString(str);
        dest.writeInt(commonInt3);
        dest.writeDouble(commonDecimal);
        dest.writeInt(commonInt4);
        dest.writeString(commonStr3);
        dest.writeString(commonStr4);
        dest.writeByte((byte) (commonBool ? 1 : 0));
        dest.writeByte((byte) (commonBool1 ? 1 : 0));
        dest.writeByte((byte) (commonBool2 ? 1 : 0));
        dest.writeString(commonChar);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<SettlementAccountData> CREATOR = new Creator<SettlementAccountData>() {
        @Override
        public SettlementAccountData createFromParcel(Parcel in) {
            return new SettlementAccountData(in);
        }

        @Override
        public SettlementAccountData[] newArray(int size) {
            return new SettlementAccountData[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getBankID() {
        return bankID;
    }

    public String getBankName() {
        return bankName;
    }

    public String getIfsc() {
        return ifsc;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public int getEntryBy() {
        return entryBy;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getApprovedBY() {
        return approvedBY;
    }

    public String getApprovalIp() {
        return approvalIp;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public String getActualname() {
        return actualname;
    }

    public String getUtr() {
        return utr;
    }

    public String getApiid() {
        return apiid;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public int getVerificationStatus() {
        return verificationStatus;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public String getVerificationText() {
        return verificationText;
    }

    public String getApprovalText() {
        return approvalText;
    }

    public String getBankselect() {
        return bankselect;
    }

    public String getRequestdate() {
        return requestdate;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getName() {
        return name;
    }

    public int getRequestID() {
        return requestID;
    }

    public int getLoginID() {
        return loginID;
    }

    public int getLoginTypeID() {
        return loginTypeID;
    }

    public int getUserID() {
        return userID;
    }

    public int getCommonInt() {
        return commonInt;
    }

    public int getCommonInt2() {
        return commonInt2;
    }

    public String getCommonStr() {
        return commonStr;
    }

    public String getCommonStr2() {
        return commonStr2;
    }

    public boolean isListType() {
        return isListType;
    }

    public String getStr() {
        return str;
    }

    public int getCommonInt3() {
        return commonInt3;
    }

    public double getCommonDecimal() {
        return commonDecimal;
    }

    public int getCommonInt4() {
        return commonInt4;
    }

    public String getCommonStr3() {
        return commonStr3;
    }

    public String getCommonStr4() {
        return commonStr4;
    }

    public boolean isCommonBool() {
        return commonBool;
    }

    public boolean isCommonBool1() {
        return commonBool1;
    }

    public boolean isCommonBool2() {
        return commonBool2;
    }

    public String getCommonChar() {
        return commonChar;
    }
}

