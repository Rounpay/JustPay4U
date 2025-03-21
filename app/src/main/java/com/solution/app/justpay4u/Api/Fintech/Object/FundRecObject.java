package com.solution.app.justpay4u.Api.Fintech.Object;

/**
 * Created by Vishnu on 15-04-2017.
 */

public class FundRecObject {

    private String Column1;
    private String TID;
    private String From;
    private String Amount;
    private String DateTime;

    public String getColumn1() {
        return Column1;
    }

    public void setColumn1(String column1) {
        Column1 = column1;
    }

    public String getTID() {
        return TID;
    }

    public void setTID(String TID) {
        this.TID = TID;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }
}
