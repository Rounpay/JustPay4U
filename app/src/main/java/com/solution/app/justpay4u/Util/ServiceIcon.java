package com.solution.app.justpay4u.Util;

import com.solution.app.justpay4u.R;

/**
 * Created by Vishnu Agarwal on 08,February,2020
 */
public enum ServiceIcon {
    INSTANCE;

    public int serviceIcon(int serviceId, boolean isFromRecharge) {
         /*1	Prepaid
2	Postpaid
3	DTH
4	Landline
5	Electricity
6	Gas
7	Domestic Hotel
8	International Hotel
9	Domestic Flight
10	International Flight
11	Bus
12	Connection
13	MPOS
14	DMT
15	DMR Charge
16	Broadband
17	Water
18	Train
19	Shopping
20	PAN Card
22	AEPS
110 Fund Request*/
        if (serviceId == 1) {
            return R.drawable.ic_prepaid;
        } else if (serviceId == 2) {
            return R.drawable.ic_postpaid;
        } else if (serviceId == 3) {
            return R.drawable.ic_satellite_dish;
        } else if (serviceId == 4) {
            return R.drawable.ic_landline;
        } else if (serviceId == 5) {
            return R.drawable.ic_bulb;
        } else if (serviceId == 6) {
            return R.drawable.ic_gas_pipe;
        } else if (serviceId == 7) {
            return R.drawable.ic_domestic_hotel;
        } else if (serviceId == 8) {
            return R.drawable.ic_international_hotel;
        } else if (serviceId == 9) {
            return R.drawable.ic_domestic_flight;
        } else if (serviceId == 10) {
            return R.drawable.ic_international_flight;
        } else if (serviceId == 11) {
            return R.drawable.ic_bus;
        } else if (serviceId == 13) {
            return R.drawable.ic_mpos;
        } else if (serviceId == 14) {
            return R.drawable.money_transfer;
        } else if (serviceId == 15) {
            return R.drawable.ic_account_verification;
        } else if (serviceId == 16) {
            return R.drawable.ic_broadband;
        } else if (serviceId == 17) {
            return R.drawable.ic_water;
        } else if (serviceId == 18) {
            return R.drawable.ic_train;
        } else if (serviceId == 19) {
            return R.drawable.ic_shopping_online;
        } else if (serviceId == 20) {
            return R.drawable.ic_pes;
        } else if (serviceId == 21) {
            return R.drawable.ic_bid;
        } else if (serviceId == 22) { //For AEPS
            return R.drawable.ic_aeps;
        } else if (serviceId == 24) {
            return R.drawable.ic_pan;
        } else if (serviceId == 25) {
            return R.drawable.ic_loan_repayment;
        } else if (serviceId == 26) {
            return R.drawable.ic_gas;
        } else if (serviceId == 27) {
            return R.drawable.ic_life_insurance;
        } else if (serviceId == 28) {
            return R.drawable.ic_bike_insurance;
        } else if (serviceId == 29) {
            return R.drawable.ic_car_insurance;
        } else if (serviceId == 32) {
            return R.drawable.ic_prepaid;
        } else if (serviceId == 33) {
            return R.drawable.ic_satellite_dish;
        } else if (serviceId == 34) {
            return R.drawable.ic_dth_sr;
        } else if (serviceId == 35) {
            return R.drawable.ic_hd_box;
        } else if (serviceId == 36) {
            return R.drawable.ic_sd_box;
        } else if (serviceId == 38) {
            return R.drawable.ic_fastag;
        } else if (serviceId == 39) {
            return R.drawable.ic_cabale_tv;
        } else if (serviceId == 43) {
            return R.drawable.ic_health_insurance;
        } else if (serviceId == 44) {
            return R.drawable.ic_mini_atm;
        } else if (serviceId == 45) {
            return R.drawable.ic_shopping;
        } else if (serviceId == 46) {
            return R.drawable.ic_munciple_tax;
        } else if (serviceId == 47) {
            return R.drawable.ic_education_fee;
        } else if (serviceId == 48) {
            return R.drawable.ic_housing_society;
        } else if (serviceId == 49) {
            return R.drawable.ic_health_insurance;
        } else if (serviceId == 51) {
            return R.drawable.ic_indo_nepal_dmt;
        } else if (serviceId == 52) {
            return R.drawable.ic_hospital_billl;
        } else if (serviceId == 53) {
            return R.drawable.ic_xpress_dmt;
        } else if (serviceId == 54) {
            return R.drawable.ic_subscription;
        } else if (serviceId == 55) {
            return R.drawable.ic_loan_application;
        } else if (serviceId == 56) {
            return R.drawable.ic_credit_card;
        } else if (serviceId == 57) {
            return R.drawable.ic_insurance_lead;
        } else if (serviceId == 62) {
            return R.drawable.ic_upi_payment_service;
        } else if (serviceId == 63) {
            return R.drawable.ic_mnp;
        } else if (serviceId == 64) {
            return R.drawable.ic_club_associations;
        } else if (serviceId == 69) {
            return R.drawable.ic_club_associations;
        } else if (serviceId == 74) {
            return R.drawable.ic_account_openning;
        } else if (serviceId == 75) {
            return R.drawable.ic_gift_voucher;
        } else if (serviceId == 84) {
            return R.drawable.ic_google_play;
        } else if (serviceId == 88) {
            return R.drawable.ic_metro;
        } else if (serviceId == 90) {
            return R.drawable.ic_wifi;
        } else if (serviceId == 91) {
            return R.drawable.ic_challan;
        } else if (serviceId == 92) {
            return R.drawable.ic_recurring_deposit;
        }else if (serviceId == 105) {
            return R.drawable.ic_wallet;
        } else if (serviceId == 786) {
            return R.drawable.add_money;
        } else if (serviceId == 1000) {
            return R.drawable.ic_fund_request;
        } else if (serviceId == 1001) {
            return R.drawable.ic_recharge_report;
        } else if (serviceId == 1002) {
            return R.drawable.ic_ledger_report;
        } else if (serviceId == 1003) {
            return R.drawable.ic_fund_order_report;
        } else if (serviceId == 1004) {
            return R.drawable.ic_complaint_report;
        } else if (serviceId == 1005) {
            return R.drawable.ic_dmt_transaction;
        } else if (serviceId == 1006) {
            return R.drawable.ic_exchnage_fund;
        } else if (serviceId == 1007) {
            return R.drawable.ic_fund_debit_credit_report;
        } else if (serviceId == 1008) {
            return R.drawable.ic_user_day_book;
        } else if (serviceId == 1009) {
            return R.drawable.ic_fund_order;
        } else if (serviceId == 1010) {
            return R.drawable.ic_fund_request;
        } else if (serviceId == 1011) {
            return R.drawable.ic_create_user;
        } else if (serviceId == 1012) {
            return R.drawable.ic_user_list;
        } else if (serviceId == 1013) {
            return R.drawable.ic_commission_slab;
        } else if (serviceId == 1014) {
            return R.drawable.ic_right_wrong_report;
        } else if (serviceId == 1015) {
            return R.drawable.ic_daybook_dmt;
        } else if (serviceId == 1016) {
            return R.drawable.ic_call_request;
        } else if (serviceId == 1017) {
            return R.drawable.ic_scan_pay;
        }else if (serviceId == 555) {
            return R.drawable.business_plan;
        } else if (serviceId == 1018) {
            return R.drawable.ic_specific_report;
        } else if (serviceId == 1019) {
            return R.drawable.ic_add_money;
        } else if (serviceId == 1020) {
            return R.drawable.ic_aeps_report;
        } else if (serviceId == 1021) {
            return R.drawable.ic_upgrade_package;
        } else if (serviceId == 1022) {
            return R.drawable.ic_dth_subscription;
        } else if (serviceId == 1023) {
            return R.drawable.ic_sendmoney;
        } else if (serviceId == 1024) {
            return R.drawable.ic_fos_create_user;
        } else if (serviceId == 1025) {
            return R.drawable.ic_downline_fund_order_report;
        } else if (serviceId == 1026) {
            return R.drawable.ic_statement_collection;
        } else if (serviceId == 1027) {
            return R.drawable.ic_statement_channel;
        } else if (serviceId == 1028) {
            return R.drawable.ic_ledger_report_fos;
        } else if (serviceId == 1029) {
            return R.drawable.ic_areas;
        }
        else if (serviceId == 1078) {
            return R.drawable.ic_user_day_book;
        }else if (serviceId == 1030) {
            return R.drawable.ic_channel;
        } else if (serviceId == 1031) {
            return R.drawable.ic_account_statemet;
        } else if (serviceId == 1032) {
            return R.drawable.ic_voucher_entry;
        } else if (serviceId == 1035) {
            return R.drawable.ic_move_to_bank;
        } else if (serviceId == 1036) {
            return R.drawable.ic_mnp_report;
        } else if (serviceId == 2001) {
            return R.drawable.ic_recharge_report;
        } else if (serviceId == 2002) {
            return R.drawable.ic_ledger_report;
        } else if (serviceId == 2005) {
            return R.drawable.ic_dmt_transaction;
        } else if (serviceId == 2008) {
            return R.drawable.ic_user_day_book;
        } else if (serviceId == 2026) {
            return R.drawable.ic_employee;
        } else if (serviceId == 2027) {
            return R.drawable.ic_emp_user_list;
        } else if (serviceId == 2028) {
            return R.drawable.ic_meeting;
        } else if (serviceId == 2029) {
            return R.drawable.ic_attendance;
        } else if (serviceId == 2030) {
            return R.drawable.ic_fund_order_report;
        } else if (serviceId == 2031) {
            return R.drawable.ic_fund_debit_credit_report;
        } else if (serviceId == 2032) {
            return R.drawable.ic_target_report;
        } else if (serviceId == 2033) {
            return R.drawable.ic_meeting_details;
        } else if (serviceId == 2034) {
            return R.drawable.ic_meeting_report;
        }
        return R.drawable.placeholder_square;
    }

    public int parentIcon(int parentId) {
            /*
19	Genral Insurance
30	DTH Subscription
*/
        if (parentId == 1) {
            return R.drawable.ic_recharge;
        } else if (parentId == 3) {
            return R.drawable.ic_travel;
        } else if (parentId == 11) {
            return R.drawable.ic_bill_payment;
        } else if (parentId == 19) {
            return R.drawable.ic_vehicle_insurance;
        } else if (parentId == 30) {
            return R.drawable.ic_dth_subscription;
        } else if (parentId == 34) {
            return R.drawable.ic_mini_bank;
        } else if (parentId == 35) {
            return R.drawable.ic_ecommerce_shopping;
        }
        return R.drawable.placeholder_square;
    }
}
