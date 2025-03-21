package com.solution.app.justpay4u.Api.Fintech.Object;

public class PackageService {

    int serviceID;
    String serviceName;
    int packageId;
    boolean isActive;
    boolean isServiceActive;
    boolean isVisible;
    String sCode;
    int walletTypeID;
    boolean selfAssigned;

    public int getServiceID() {
        return serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public int getPackageId() {
        return packageId;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isServiceActive() {
        return isServiceActive;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public String getsCode() {
        return sCode;
    }

    public int getWalletTypeID() {
        return walletTypeID;
    }

    public boolean isSelfAssigned() {
        return selfAssigned;
    }
}
