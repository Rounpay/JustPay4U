package com.solution.app.justpay4u.Fintech.UpiPayment;

/**
 * Created by Vishnu Agarwal on 16/05/2022.
 */
public enum UpiHandleApp {
    INSTANCE;

    /* String apl = "Amazon Pay";
    String yapl = "Amazon Pay";
   String ybl = "Phonepe";
    String ibl = "Phonepe";
    String axl = "Phonepe";
    String waicici = "WhatsApp";
    String waaxis = "WhatsApp";
    String wahdfcbank = "WhatsApp";
    String wasbi = "WhatsApp";
    String okaxis = "Google Pay";
    String okhdfcbank = "Google Pay";
    String okicici = "Google Pay";
    String oksbi = "Google Pay";
    String abfspay = "Bajaj Finserv, Finserv Markets";
    String fbl = "CoinTab";
    String axisb = "CRED";
    String idfcbank = "IDFC Bank - Fave (Pinelabs), Ultracash";
     String rmhdfcbank = "FinShell Pay";
    String icici = "ICIC Bank - Goibibo, Make My Trip";
    String indus = "Make My Trip";
    String axisbank = "Groww";
    String jupiteraxis = "Jupiter Money";
    String hdfcbankjd = "JustDial";
      String hsbc = "Maxwholesale";
    String myicici = "Mi Pay";
    String ikwik = "MobiKwik";
    String pingpay = "Samsung Pay";
    String kmbl = "SuperPay (Chintamoney)";
    String timecosmos = "Timepay";
    String yesbank = "Yes Bank - tvam (Atyati), YuvaPay";*/


    public String getAppName(String handle) {
        if (handle.equalsIgnoreCase("apl") || handle.equalsIgnoreCase("yapl")) {
            return "Amazon Pay";
        } else if (handle.equalsIgnoreCase("ybl") || handle.equalsIgnoreCase("ibl") ||
                handle.equalsIgnoreCase("axl")) {
            return "Phonepe";
        } else if (handle.equalsIgnoreCase("waicici") || handle.equalsIgnoreCase("waaxis") ||
                handle.equalsIgnoreCase("wahdfcbank") || handle.equalsIgnoreCase("wasbi")) {
            return "WhatsApp";
        } else if (handle.equalsIgnoreCase("okaxis") || handle.equalsIgnoreCase("okhdfcbank") ||
                handle.equalsIgnoreCase("oksbi") || handle.equalsIgnoreCase("okicici")) {
            return "Google Pay";
        } else if (handle.equalsIgnoreCase("upi")) {
            return "BHIM";
        } else if (handle.equalsIgnoreCase("rbl")) {
            return "BHIM RBL Pay";
        } else if (handle.equalsIgnoreCase("aubank")) {
            return "BHIM BANDHAN UPI";
        } else if (handle.equalsIgnoreCase("bandhan")) {
            return "BHIM AUPay";
        } else if (handle.equalsIgnoreCase("abfspay")) {
            return "Bajaj Finserv, Finserv Markets";
        } else if (handle.equalsIgnoreCase("fbl")) {
            return "CoinTab";
        } else if (handle.equalsIgnoreCase("axisb")) {
            return "CRED";
        } else if (handle.equalsIgnoreCase("idfcbank")) {
            return "IDFC Bank - Fave (Pinelabs), Ultracash";
        } else if (handle.equalsIgnoreCase("rmhdfcbank")) {
            return "FinShell Pay";
        } else if (handle.equalsIgnoreCase("icici") || handle.equalsIgnoreCase("imobile") || handle.equalsIgnoreCase("pocket") ||
                handle.equalsIgnoreCase("ezeepay") || handle.equalsIgnoreCase("eazypay")) {
            return "iMobile by ICICI Bank";
        } else if (handle.equalsIgnoreCase("indus")) {
            return "BHIM IndusPay";
        } else if (handle.equalsIgnoreCase("uco")) {
            return "BHIM UCO UPI";
        } else if (handle.equalsIgnoreCase("axisbank")) {
            return "Axis Bank";
        } else if (handle.equalsIgnoreCase("jupiteraxis")) {
            return "Jupiter Money";
        } else if (handle.equalsIgnoreCase("hdfcbankjd")) {
            return "JustDial";
        } else if (handle.equalsIgnoreCase("hsbc")) {
            return "HSBC Simply Pay";
        } else if (handle.equalsIgnoreCase("myicici")) {
            return "Mi Pay";
        } else if (handle.equalsIgnoreCase("ikwik")) {
            return "MobiKwik";
        } else if (handle.equalsIgnoreCase("pingpay")) {
            return "Samsung Pay";
        } else if (handle.equalsIgnoreCase("kmbl")) {
            return "SuperPay (Chintamoney)";
        } else if (handle.equalsIgnoreCase("timecosmos")) {
            return "Timepay";
        } else if (handle.equalsIgnoreCase("yesbank")) {
            return "Yes Bank";
        } else if (handle.equalsIgnoreCase("citi") || handle.equalsIgnoreCase("citigold")) {
            return "Citi Mobile";
        } else if (handle.equalsIgnoreCase("paytm")) {
            return "Paytm";
        } else if (handle.equalsIgnoreCase("freecharge")) {
            return "Freecharge";
        } else if (handle.equalsIgnoreCase("sib")) {
            return "SIB Mirror+";
        } else if (handle.equalsIgnoreCase("barodampay")) {
            return "BHIM Baroda Pay";
        } else if (handle.equalsIgnoreCase("idbi")) {
            return "IDBI Bank GO Mobile+, BHIM PayWiz";
        } else if (handle.equalsIgnoreCase("kotak")) {
            return "Kotak Mobile Banking App";
        } else if (handle.equalsIgnoreCase("sbi")) {
            return "State Bank Of India Pay";
        } else if (handle.equalsIgnoreCase("payzapp")) {
            return "HDFC Bank Pay";
        } else if (handle.equalsIgnoreCase("barodampay")) {
            return "Bank Of Baroda Pay";
        }
        return "";
    }


}
