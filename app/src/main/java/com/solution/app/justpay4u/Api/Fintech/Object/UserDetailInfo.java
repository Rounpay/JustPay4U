package com.solution.app.justpay4u.Api.Fintech.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDetailInfo implements Parcelable {
    @SerializedName("ekycid")
    @Expose
    public int ekycid;
    @SerializedName("commRate")
    @Expose
    public int commRate;
    @SerializedName("stateID")
    @Expose
    public int stateID;
    @SerializedName("profilePic")
    @Expose
    public String profilePic;
    @SerializedName("kycStatus")
    @Expose
    public int kycStatus;
    @SerializedName("aadhar")
    @Expose
    public String aadhar;
    @SerializedName("branchName")
    @Expose
    public String branchName;
    @SerializedName("pan")
    @Expose
    public String pan;
    @SerializedName("gstin")
    @Expose
    public String gstin;

    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("loginID")
    @Expose
    public int loginID;
    @SerializedName("lt")
    @Expose
    public int lt;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("stateName")
    @Expose
    public String stateName;
    @SerializedName("roles")
    @Expose
    public List<Role> roles = null;
    @SerializedName("slabs")
    @Expose
    public String slabs;
    @SerializedName("states")
    @Expose
    public String states;
    @SerializedName("ip")
    @Expose
    public String ip;
    @SerializedName("rank")
    @Expose
    private RankInfo rankInfo;

    @SerializedName("browser")
    @Expose
    public String browser;
    @SerializedName("resultCode")
    @Expose
    public int resultCode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("userID")
    @Expose
    public int userID;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("outletName")
    @Expose
    public String outletName;
    @SerializedName("emailID")
    @Expose
    public String emailID;
    @SerializedName("roleID")
    @Expose
    public int roleID;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("referalID")
    @Expose
    public int referalID;
    @SerializedName("slabID")
    @Expose
    public int slabID;
    @SerializedName("isGSTApplicable")
    @Expose
    public boolean isGSTApplicable;
    @SerializedName("isTDSApplicable")
    @Expose
    public boolean isTDSApplicable;
    @SerializedName("isCCFGstApplicable")
    @Expose
    public boolean isCCFGstApplicable;
    @SerializedName("isVirtual")
    @Expose
    public boolean isVirtual;
    @SerializedName("isWebsite")
    @Expose
    public boolean isWebsite;
    @SerializedName("isAdminDefined")
    @Expose
    public boolean isAdminDefined;
    @SerializedName("isSurchargeGST")
    @Expose
    public boolean isSurchargeGST;
    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("outletID")
    @Expose
    public int outletID;
    @SerializedName("pincode")
    @Expose
    public String pincode;
    @SerializedName("wid")
    @Expose
    public int wid;
    @SerializedName("isDoubleFactor")
    @Expose
    public boolean isDoubleFactor;

    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("shopType")
    @Expose
    private String shopType;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("poupulation")
    @Expose
    private String poupulation;
    @SerializedName("locationType")
    @Expose
    private String locationType;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("alternateMobile")
    @Expose
    private String alternateMobile;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("accountName")
    @Expose
    private String accountName;

    @SerializedName("isRealAPI")
    @Expose
    private boolean isRealAPI;
    @SerializedName(value = "isAadharVerified")
    @Expose
    private boolean isAadharVerified;

    @SerializedName("isPANVerified")
    @Expose
    private boolean isPANVerified;
    protected UserDetailInfo(Parcel in) {
        ekycid = in.readInt();
        rankInfo = in.readParcelable(RankInfo.class.getClassLoader());
        commRate = in.readInt();
        stateID = in.readInt();
        profilePic = in.readString();
        kycStatus = in.readInt();
        aadhar = in.readString();
        branchName = in.readString();
        pan = in.readString();
        gstin = in.readString();
        address = in.readString();
        loginID = in.readInt();
        lt = in.readInt();
        city = in.readString();
        stateName = in.readString();
        roles = in.createTypedArrayList(Role.CREATOR);
        slabs = in.readString();
        states = in.readString();
        ip = in.readString();
        browser = in.readString();
        resultCode = in.readInt();
        msg = in.readString();
        userID = in.readInt();
        mobileNo = in.readString();
        name = in.readString();
        outletName = in.readString();
        emailID = in.readString();
        roleID = in.readInt();
        role = in.readString();
        referalID = in.readInt();
        slabID = in.readInt();
        isGSTApplicable = in.readByte() != 0;
        isTDSApplicable = in.readByte() != 0;
        isCCFGstApplicable = in.readByte() != 0;
        isVirtual = in.readByte() != 0;
        isWebsite = in.readByte() != 0;
        isAdminDefined = in.readByte() != 0;
        isSurchargeGST = in.readByte() != 0;
        prefix = in.readString();
        outletID = in.readInt();
        pincode = in.readString();
        wid = in.readInt();
        isDoubleFactor = in.readByte() != 0;
        dob = in.readString();
        shopType = in.readString();
        qualification = in.readString();
        poupulation = in.readString();
        locationType = in.readString();
        landmark = in.readString();
        alternateMobile = in.readString();
        bankName = in.readString();
        ifsc = in.readString();
        accountNumber = in.readString();
        accountName = in.readString();
        isRealAPI = in.readByte() != 0;
        isAadharVerified = in.readByte() != 0;
        isPANVerified = in.readByte() != 0;
    }

    public boolean isAadharVerified() {
        return isAadharVerified;
    }

    public void setAadharVerified(boolean aadharVerified) {
        isAadharVerified = aadharVerified;
    }

    public boolean isPANVerified() {
        return isPANVerified;
    }

    public void setPANVerified(boolean PANVerified) {
        isPANVerified = PANVerified;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ekycid);
        dest.writeInt(commRate);
        dest.writeParcelable( rankInfo, flags);
        dest.writeInt(stateID);
        dest.writeString(profilePic);
        dest.writeInt(kycStatus);
        dest.writeString(aadhar);
        dest.writeString(branchName);
        dest.writeString(pan);
        dest.writeString(gstin);
        dest.writeString(address);
        dest.writeInt(loginID);
        dest.writeInt(lt);
        dest.writeString(city);
        dest.writeString(stateName);
        dest.writeTypedList(roles);
        dest.writeString(slabs);
        dest.writeString(states);
        dest.writeString(ip);
        dest.writeString(browser);
        dest.writeInt(resultCode);
        dest.writeString(msg);
        dest.writeInt(userID);
        dest.writeString(mobileNo);
        dest.writeString(name);
        dest.writeString(outletName);
        dest.writeString(emailID);
        dest.writeInt(roleID);
        dest.writeString(role);
        dest.writeInt(referalID);
        dest.writeInt(slabID);
        dest.writeByte((byte) (isGSTApplicable ? 1 : 0));
        dest.writeByte((byte) (isTDSApplicable ? 1 : 0));
        dest.writeByte((byte) (isCCFGstApplicable ? 1 : 0));
        dest.writeByte((byte) (isVirtual ? 1 : 0));
        dest.writeByte((byte) (isWebsite ? 1 : 0));
        dest.writeByte((byte) (isAdminDefined ? 1 : 0));
        dest.writeByte((byte) (isSurchargeGST ? 1 : 0));
        dest.writeString(prefix);
        dest.writeInt(outletID);
        dest.writeString(pincode);
        dest.writeInt(wid);
        dest.writeByte((byte) (isDoubleFactor ? 1 : 0));
        dest.writeString(dob);
        dest.writeString(shopType);
        dest.writeString(qualification);
        dest.writeString(poupulation);
        dest.writeString(locationType);
        dest.writeString(landmark);
        dest.writeString(alternateMobile);
        dest.writeString(bankName);
        dest.writeString(ifsc);
        dest.writeString(accountNumber);
        dest.writeString(accountName);
        dest.writeByte((byte) (isRealAPI ? 1 : 0));
        dest.writeByte((byte) (isAadharVerified ? 1 : 0));
        dest.writeByte((byte) (isPANVerified ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<UserDetailInfo> CREATOR = new Creator<UserDetailInfo>() {
        @Override
        public UserDetailInfo createFromParcel(Parcel in) {
            return new UserDetailInfo(in);
        }

        @Override
        public UserDetailInfo[] newArray(int size) {
            return new UserDetailInfo[size];
        }
    };
    public RankInfo getRankInfo() {
        return rankInfo;
    }


    public boolean isRealAPI() {
        return isRealAPI;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getShoptype() {
        return shopType;
    }

    public void setShoptype(String shoptype) {
        this.shopType = shoptype;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getPoupulation() {
        return poupulation;
    }

    public void setPoupulation(String poupulation) {
        this.poupulation = poupulation;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAlternateMobile() {
        return alternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getCommRate() {
        return commRate;
    }

    public void setCommRate(int commRate) {
        this.commRate = commRate;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public boolean isDoubleFactor() {
        return isDoubleFactor;
    }

    public void setDoubleFactor(boolean doubleFactor) {
        isDoubleFactor = doubleFactor;
    }

    public int getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(int kycStatus) {
        this.kycStatus = kycStatus;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLoginID() {
        return loginID;
    }

    public void setLoginID(int loginID) {
        this.loginID = loginID;
    }

    public int getLt() {
        return lt;
    }

    public void setLt(int lt) {
        this.lt = lt;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getSlabs() {
        return slabs;
    }

    public void setSlabs(String slabs) {
        this.slabs = slabs;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getEkycid() {
        return ekycid;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getReferalID() {
        return referalID;
    }

    public void setReferalID(int referalID) {
        this.referalID = referalID;
    }

    public int getSlabID() {
        return slabID;
    }

    public void setSlabID(int slabID) {
        this.slabID = slabID;
    }

    public boolean getGSTApplicable() {
        return isGSTApplicable;
    }

    public void setGSTApplicable(boolean GSTApplicable) {
        isGSTApplicable = GSTApplicable;
    }

    public boolean getTDSApplicable() {
        return isTDSApplicable;
    }

    public void setTDSApplicable(boolean TDSApplicable) {
        isTDSApplicable = TDSApplicable;
    }

    public boolean getCCFGstApplicable() {
        return isCCFGstApplicable;
    }

    public void setCCFGstApplicable(boolean CCFGstApplicable) {
        isCCFGstApplicable = CCFGstApplicable;
    }

    public boolean getVirtual() {
        return isVirtual;
    }

    public void setVirtual(boolean virtual) {
        isVirtual = virtual;
    }

    public boolean getWebsite() {
        return isWebsite;
    }

    public void setWebsite(boolean website) {
        isWebsite = website;
    }

    public boolean getAdminDefined() {
        return isAdminDefined;
    }

    public void setAdminDefined(boolean adminDefined) {
        isAdminDefined = adminDefined;
    }

    public boolean getSurchargeGST() {
        return isSurchargeGST;
    }

    public void setSurchargeGST(boolean surchargeGST) {
        isSurchargeGST = surchargeGST;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getOutletID() {
        return outletID;
    }

    public void setOutletID(int outletID) {
        this.outletID = outletID;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }
}
