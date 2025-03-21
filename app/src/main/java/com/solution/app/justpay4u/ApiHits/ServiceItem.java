package com.solution.app.justpay4u.ApiHits;

public class ServiceItem {
        private int serviceID;
        private int parentID;
        private boolean isServiceActive;
        private boolean isShowMore;
        private String upline;
        private String uplineMobile;
        private String ccContact;
        private String name;
        private String service;
        private String sCode;
        private boolean isActive;
        private boolean isDisplayService;

        // Constructor, getters, and setters


    public ServiceItem(int serviceID, int parentID, boolean isServiceActive, boolean isShowMore, String upline, String uplineMobile, String ccContact, String name, String service, String sCode, boolean isActive, boolean isDisplayService) {
        this.serviceID = serviceID;
        this.parentID = parentID;
        this.isServiceActive = isServiceActive;
        this.isShowMore = isShowMore;
        this.upline = upline;
        this.uplineMobile = uplineMobile;
        this.ccContact = ccContact;
        this.name = name;
        this.service = service;
        this.sCode = sCode;
        this.isActive = isActive;
        this.isDisplayService = isDisplayService;
    }


    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public boolean isServiceActive() {
        return isServiceActive;
    }

    public void setServiceActive(boolean serviceActive) {
        isServiceActive = serviceActive;
    }

    public boolean isShowMore() {
        return isShowMore;
    }

    public void setShowMore(boolean showMore) {
        isShowMore = showMore;
    }

    public String getUpline() {
        return upline;
    }

    public void setUpline(String upline) {
        this.upline = upline;
    }

    public String getUplineMobile() {
        return uplineMobile;
    }

    public void setUplineMobile(String uplineMobile) {
        this.uplineMobile = uplineMobile;
    }

    public String getCcContact() {
        return ccContact;
    }

    public void setCcContact(String ccContact) {
        this.ccContact = ccContact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isDisplayService() {
        return isDisplayService;
    }

    public void setDisplayService(boolean displayService) {
        isDisplayService = displayService;
    }
}
