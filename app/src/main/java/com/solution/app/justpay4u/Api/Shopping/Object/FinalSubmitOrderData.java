package com.solution.app.justpay4u.Api.Shopping.Object;

/**
 * Created by Vishnu Agarwal on 17,January,2020
 */
public class FinalSubmitOrderData {
    String mode;
    int status;
    String msg;
    String orderNo;
    String transactionId;
    String transactionstatus;

    public String getMode() {
        return mode;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public int getTransactionstatus() {
        try {
            return Integer.parseInt(transactionstatus);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }
}
