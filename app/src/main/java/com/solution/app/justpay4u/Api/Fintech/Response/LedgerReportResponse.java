package com.solution.app.justpay4u.Api.Fintech.Response;


import com.solution.app.justpay4u.Api.Fintech.Object.LedgerObject;

import java.util.ArrayList;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class LedgerReportResponse {

    private String RESPONSESTATUS;
    private String message;
    private ArrayList<LedgerObject> Ledger;

    public String getRESPONSESTATUS() {
        return RESPONSESTATUS;
    }

    public void setRESPONSESTATUS(String RESPONSESTATUS) {
        this.RESPONSESTATUS = RESPONSESTATUS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<LedgerObject> getLedger() {
        return Ledger;
    }

    public void setLedger(ArrayList<LedgerObject> ledger) {
        Ledger = ledger;
    }
}
