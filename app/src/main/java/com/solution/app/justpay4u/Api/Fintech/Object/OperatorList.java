package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu on 18-01-2017.
 */

public class OperatorList implements Parcelable {


    @SerializedName("redirectURL")
    @Expose
    public String redirectURL;
    String header;
    int stateID;
    String tollFree;
    double charge;
    boolean chargeAmtType;
    boolean isActive;
    boolean isTakeCustomerNum;
    private String name;
    private int oid;
    private int opType;
    private boolean isBBPS;
    private boolean isBilling;
    private int min;
    private int max;
    private int length;
    private String startWith;
    private String image;
    private boolean isPartial;
    private String accountName;
    private String accountRemak;
    private String regExAccount;
    private String planDocName;
    private boolean isAccountNumeric;
    private int lengthMax;

    public OperatorList() {
    }

    protected OperatorList(Parcel in) {
        redirectURL = in.readString();
        header = in.readString();
        stateID = in.readInt();
        tollFree = in.readString();
        charge = in.readDouble();
        chargeAmtType = in.readByte() != 0;
        isActive = in.readByte() != 0;
        isTakeCustomerNum = in.readByte() != 0;
        name = in.readString();
        oid = in.readInt();
        opType = in.readInt();
        isBBPS = in.readByte() != 0;
        isBilling = in.readByte() != 0;
        min = in.readInt();
        max = in.readInt();
        length = in.readInt();
        startWith = in.readString();
        image = in.readString();
        isPartial = in.readByte() != 0;
        accountName = in.readString();
        accountRemak = in.readString();
        regExAccount = in.readString();
        planDocName = in.readString();
        isAccountNumeric = in.readByte() != 0;
        lengthMax = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(redirectURL);
        dest.writeString(header);
        dest.writeInt(stateID);
        dest.writeString(tollFree);
        dest.writeDouble(charge);
        dest.writeByte((byte) (chargeAmtType ? 1 : 0));
        dest.writeByte((byte) (isActive ? 1 : 0));
        dest.writeByte((byte) (isTakeCustomerNum ? 1 : 0));
        dest.writeString(name);
        dest.writeInt(oid);
        dest.writeInt(opType);
        dest.writeByte((byte) (isBBPS ? 1 : 0));
        dest.writeByte((byte) (isBilling ? 1 : 0));
        dest.writeInt(min);
        dest.writeInt(max);
        dest.writeInt(length);
        dest.writeString(startWith);
        dest.writeString(image);
        dest.writeByte((byte) (isPartial ? 1 : 0));
        dest.writeString(accountName);
        dest.writeString(accountRemak);
        dest.writeString(regExAccount);
        dest.writeString(planDocName);
        dest.writeByte((byte) (isAccountNumeric ? 1 : 0));
        dest.writeInt(lengthMax);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<OperatorList> CREATOR = new Creator<OperatorList>() {
        @Override
        public OperatorList createFromParcel(Parcel in) {
            return new OperatorList(in);
        }

        @Override
        public OperatorList[] newArray(int size) {
            return new OperatorList[size];
        }
    };

    public double getCharge() {
        return charge;
    }

    public boolean isChargeAmtType() {
        return chargeAmtType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOid() {
        return oid;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public int getOpType() {
        return opType;
    }

    public boolean getBBPS() {
        return isBBPS;
    }

    public boolean getIsBilling() {
        return isBilling;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }


    public int getLength() {
        if (length > 0) {
            return length;
        } else {
            return 1;
        }
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getStartWith() {
        return startWith;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getIsPartial() {
        return isPartial;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountRemak() {
        return accountRemak;
    }

    public boolean getIsAccountNumeric() {
        return isAccountNumeric;
    }

    public int getLengthMax() {
        if (lengthMax > 0) {
            return lengthMax;
        } else {
            return 50;
        }

    }

    public boolean isTakeCustomerNum() {
        return isTakeCustomerNum;
    }

    public String getTollFree() {
        return tollFree != null && !tollFree.isEmpty() ? tollFree : "";
    }

    public int getStateID() {
        return stateID;
    }

    public String getRegExAccount() {
        return regExAccount;
    }

    public String getPlanDocName() {
        return planDocName;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public boolean isActive() {
        return isActive;
    }
}
