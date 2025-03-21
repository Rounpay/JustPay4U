package com.solution.app.justpay4u.Util.CustomFilterDialogUtils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class CustomFilterDialog {
    private final int currentYear, currentMonth, currentDay;
    public String[] dthSubscriptionBookingStatusArray = {":: Select Status ::", "Requested", "ForwardToEngineer", "Installing", "Completed", "Rejected"};
    public String[] dthSubscriptionStatusArray = {":: Select Status ::", "PENDING", "SUCCESS", "FAILED", "REFUND", "REQUESTSENT"};
    Activity mActivity;
    private Calendar myCalendarTo, myCalendarFrom;
    private long to_Mill, from_mill;
    private HashMap<String, Integer> debitCreditTypesMap = new HashMap<>();
    private String[] debitCreditArray = {"Fund Credit", "Fund Debit"};
    private HashMap<String, Integer> walletTypesMap = new HashMap<>();
    private String[] walletTypesArray = {};
    private int selectedIndex;
    private AlertDialog singleChoiceDialog;
    private String[] ledgerChooseTopArray = {"50", "100", "200", "500", "1000", "1500", "ALL"};
    private HashMap<String, Integer> fundOrderStatusMap = new HashMap<>();
    private String[] fundOrderStatusArray = {":: Select Status ::", "REQUESTED", "ACCEPTED", "REJECTED"};
    private HashMap<String, Integer> disputeStatusMap = new HashMap<>();
    private String[] disputeStatusArray = {":: Select Status ::", "UNDER REVIEW", "REFUNDED", "REJECTED"};
    private HashMap<String, Integer> rechargeStatusMap = new HashMap<>();
    private String[] rechargeStatusArray = {":: Select Status ::", "SUCCESS", "FAILED", "REFUND", "PENDING"};
    private HashMap<String, Integer> dthSubscriptionBookingStatusMap = new HashMap<>();
    private HashMap<String, Integer> dthSubscriptionStatusMap = new HashMap<>();
    private HashMap<String, Integer> disputeDateTypeMap = new HashMap<>();
    private String[] disputeDateTypeArray = {"Accept Reject Date", "Recharge Date", "Request Date"};

    private HashMap<String, Integer> disputeCriteriaMap = new HashMap<>();
    private String[] disputeCriteriaArray = {":: Select Criteria ::", "Account No.", "Transaction ID"};

    private HashMap<String, Integer> chooseModeMap = new HashMap<>();
    private String[] chooseModeArray = {":: Select Mode ::", "NEFT", "RTGS", "Third Party Transfer", "Cheque", "IMPS", "EC-Exchange", "CASH DEPOSIT", "GCC (Green Channel Card)", "QR Code"};

    private HashMap<String, Integer> empRoleMap = new HashMap<>();
    public String[] empRoleArray = {":: Choose Role ::", "Sales Head", "State Head", "Cluster Head", "ASM", "TSM"};

    private HashMap<String, Integer> empCriteriaMap = new HashMap<>();
    private String[] empCriteriaArray = {":: Choose Criteria ::", "User ID", "Outlet Mobile", "EmailID", "Name"};
    private BalanceResponse mBalanceResponse;


    public CustomFilterDialog(Activity mActivity) {
        this.mActivity = mActivity;
        setMapData();
        final Calendar currentCalendar = Calendar.getInstance(TimeZone.getDefault());

        currentYear = currentCalendar.get(Calendar.YEAR);
        currentMonth = currentCalendar.get(Calendar.MONTH) + 1;
        currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        from_mill = currentCalendar.getTimeInMillis();
        to_Mill = from_mill;
        myCalendarTo = Calendar.getInstance();
        myCalendarFrom = Calendar.getInstance();
    }




    void setMapData() {
        debitCreditTypesMap.put("Fund Credit", 4);
        debitCreditTypesMap.put("Fund Debit", 5);


        chooseModeMap.put(":: Select Mode ::", 0);
        chooseModeMap.put("NEFT", 1);
        chooseModeMap.put("RTGS", 2);
        chooseModeMap.put("Third Party Transfer", 3);
        chooseModeMap.put("Cheque", 4);
        chooseModeMap.put("IMPS", 5);
        chooseModeMap.put("EC-Exchange", 6);
        chooseModeMap.put("CASH DEPOSIT", 7);
        chooseModeMap.put("GCC (Green Channel Card)", 8);
        chooseModeMap.put("QR Code", 9);

        fundOrderStatusMap.put(":: Select Status ::", 0);
        fundOrderStatusMap.put("REQUESTED", 1);
        fundOrderStatusMap.put("ACCEPTED", 2);
        fundOrderStatusMap.put("REJECTED", 3);


        disputeStatusMap.put(":: Select Status ::", 0);
        disputeStatusMap.put("UNDER REVIEW", 2);
        disputeStatusMap.put("REFUNDED", 3);
        disputeStatusMap.put("REJECTED", 4);

        rechargeStatusMap.put(":: Select Status ::", 0);
        rechargeStatusMap.put("SUCCESS", 2);
        rechargeStatusMap.put("FAILED", 3);
        rechargeStatusMap.put("REFUND", 4);
        rechargeStatusMap.put("PENDING", 1);

        dthSubscriptionBookingStatusMap.put(":: Select Status ::", 0);
        dthSubscriptionBookingStatusMap.put("Requested", 1);
        dthSubscriptionBookingStatusMap.put("ForwardToEngineer", 2);
        dthSubscriptionBookingStatusMap.put("Installing", 3);
        dthSubscriptionBookingStatusMap.put("Completed", 4);
        dthSubscriptionBookingStatusMap.put("Rejected", 5);


        dthSubscriptionStatusMap.put(":: Select Status ::", 0);
        dthSubscriptionStatusMap.put("PENDING", 1);
        dthSubscriptionStatusMap.put("SUCCESS", 2);
        dthSubscriptionStatusMap.put("FAILED", 3);
        dthSubscriptionStatusMap.put("REFUND", 4);
        dthSubscriptionStatusMap.put("REQUESTSENT", 5);

        disputeDateTypeMap.put("Accept Reject Date", 1);
        disputeDateTypeMap.put("Recharge Date", 2);
        disputeDateTypeMap.put("Request Date", 3);

        disputeCriteriaMap.put(":: Select Criteria ::", 0);
        disputeCriteriaMap.put("Account No.", 2);
        disputeCriteriaMap.put("Transaction ID", 4);

        empCriteriaMap.put(":: Choose Criteria ::", 0);
        empCriteriaMap.put("User ID", 10);
        empCriteriaMap.put("Outlet Mobile", 1);
        empCriteriaMap.put("EmailID", 11);
        empCriteriaMap.put("Name", 12);

        empRoleMap.put(":: Choose Role ::", 0);
        empRoleMap.put("Sales Head", 2);
        empRoleMap.put("State Head", 3);
        empRoleMap.put("Cluster Head", 4);
        empRoleMap.put("ASM", 5);
        empRoleMap.put("TSM", 6);
    }

    public void openFundDCFilter(final String fromDate, String toDate, String filterMobieNum, boolean isSelfFilter,
                                 String filterOtherMobile, String filterService, String filterWalletType, String serviceTypeTitleStr, LoginResponse mLoginResponse,
                                 AppPreferences mAppPreferences, final FundDebitCreditCallBack mFundDebitCreditCallBack) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);

        View sheetView = mActivity.getLayoutInflater().inflate(R.layout.dialog_fund_debit_credit_filter, null);
        LinearLayout container = sheetView.findViewById(R.id.container);
        final LinearLayout walletTypeView = sheetView.findViewById(R.id.walletTypeView);
        final AppCompatTextView walletTypeChooser = sheetView.findViewById(R.id.walletTypeChooser);
        LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
        final AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);
        startDate.setText(fromDate);

        LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);
        endDate.setText(toDate);

        final AppCompatEditText mobileNoEt = sheetView.findViewById(R.id.mobileNoEt);
        mobileNoEt.setText(filterMobieNum);
        final SwitchCompat isSelfSwitch = sheetView.findViewById(R.id.isSelfSwitch);
        isSelfSwitch.setChecked(isSelfFilter);


        final AppCompatTextView debitCreditChooser = sheetView.findViewById(R.id.debitCreditChooser);
        if (filterService != null && !filterService.isEmpty()) {
            debitCreditChooser.setText(filterService);
        } else {
            debitCreditChooser.setText(debitCreditArray[0]);
        }
        final TextView receivedByTitle = sheetView.findViewById(R.id.receivedByTitle);
        receivedByTitle.setText(serviceTypeTitleStr);

        final AppCompatEditText receivedByEt = sheetView.findViewById(R.id.receivedByEt);
        receivedByEt.setHint("Enter " + serviceTypeTitleStr);
        receivedByEt.setText(filterOtherMobile);
        Button filter = sheetView.findViewById(R.id.filter);
        debitCreditChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (debitCreditChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(debitCreditArray).indexOf(debitCreditChooser.getText().toString());
                }
                showSingleChoiceAlert(debitCreditArray, selectedIndex, "Debit/Credit", "Choose Transaction Type", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        debitCreditChooser.setText(debitCreditArray[index]);
                        if (index == 0) {
                            receivedByTitle.setText("Received By");
                            receivedByEt.setHint("Enter Received By");
                        } else {
                            receivedByTitle.setText("Fund Transfered To");
                            receivedByEt.setHint("Enter Fund Transfered To");
                        }
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });


        if (mLoginResponse.getData().getLoginTypeID() == 3) {
            walletTypeView.setVisibility(View.GONE);
        } else {
            mBalanceResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
            if (mBalanceResponse != null && mBalanceResponse.getBalanceData() != null &&
                    mBalanceResponse.getBalanceData().size() > 0) {
                setWalletData(mBalanceResponse.getBalanceData(), filterWalletType, walletTypeChooser);

            } else {
                walletTypeView.setVisibility(View.GONE);
                ApiFintechUtilMethods.INSTANCE.Balancecheck(mActivity, null, mLoginResponse, null, null, mAppPreferences,
                        null, object -> {
                            mBalanceResponse = (BalanceResponse) object;
                            if (mBalanceResponse != null && mBalanceResponse.getBalanceData() != null &&
                                    mBalanceResponse.getBalanceData().size() > 0) {
                                setWalletData(mBalanceResponse.getBalanceData(), filterWalletType, walletTypeChooser);

                            }

                        });
            }
        }

        walletTypeChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (walletTypeChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(walletTypesArray).indexOf(walletTypeChooser.getText().toString());
            }
            showSingleChoiceAlert(walletTypesArray, selectedIndex, "Wallet Type", "Choose Wallet Type", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    walletTypeChooser.setText(walletTypesArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });
        startDateView.setOnClickListener(v -> setDCFromDate(startDate, endDate));
        endDateView.setOnClickListener(v -> setDCToDate(startDate, endDate));
        filter.setOnClickListener(v -> {
            mBottomSheetDialog.dismiss();
            if (mFundDebitCreditCallBack != null) {
                mFundDebitCreditCallBack.onSubmitClick(startDate.getText().toString(), endDate.getText().toString(), mobileNoEt.getText().toString()
                        , isSelfSwitch.isChecked(), walletTypeView.getVisibility() == View.VISIBLE ? walletTypesMap.get(walletTypeChooser.getText().toString()) : 0, walletTypeChooser.getText().toString(),
                        debitCreditTypesMap.get(debitCreditChooser.getText().toString()), debitCreditChooser.getText().toString(), receivedByEt.getText().toString());
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

    }

    public void openDisputeFilter(final String dialogType, int rollID, final String filterFromDate, String filterToDate,
                                  String filterMobileNo, String filterTransactionId, String filterChildMobileNo, String filterAccountNo, int filterTopValue, String filterStatus, String filterDateType, String filterModeValue,
                                  int filterChooseCriteriaId, String filterChooseCriteria, String filterEnterCriteria, String filterWalletType,
                                  LoginResponse mLoginResponse, AppPreferences mAppPreferences, final LedgerFilterCallBack mLedgerFilterCallBack) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);

        View sheetView = mActivity.getLayoutInflater().inflate(R.layout.dialog_ledger_filter, null);
        final LinearLayout walletTypeView = sheetView.findViewById(R.id.walletTypeView);
        final AppCompatTextView walletTypeChooser = sheetView.findViewById(R.id.walletTypeChooser);
        LinearLayout container = sheetView.findViewById(R.id.container);
        ImageView mobileNumberLeftIcon = sheetView.findViewById(R.id.mobileNumberLeftIcon);
        LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
        AppCompatTextView mobileNumTitle = sheetView.findViewById(R.id.mobileNumTitle);
        final AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);
        startDate.setText(filterFromDate);
        LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);
        endDate.setText(filterToDate);
        LinearLayout mobileView = sheetView.findViewById(R.id.mobileView);
        final AppCompatEditText mobileNoEt = sheetView.findViewById(R.id.mobileNoEt);
        mobileNoEt.setText(filterMobileNo);
        LinearLayout transactionIdView = sheetView.findViewById(R.id.transactionIdView);
        final AppCompatEditText transactionIdEt = sheetView.findViewById(R.id.transactionIdEt);
        transactionIdEt.setText(filterTransactionId);
        LinearLayout childMobileView = sheetView.findViewById(R.id.childMobileView);
        final AppCompatEditText childMobileNoEt = sheetView.findViewById(R.id.childMobileNoEt);
        childMobileNoEt.setText(filterChildMobileNo);
        LinearLayout accountNoView = sheetView.findViewById(R.id.accountNoView);
        TextView accountNoTitle = sheetView.findViewById(R.id.accountNoTitle);
        final AppCompatEditText accountNoEt = sheetView.findViewById(R.id.accountNoEt);
        accountNoEt.setText(filterAccountNo);
        LinearLayout topChooserView = sheetView.findViewById(R.id.topChooserView);
        final AppCompatTextView topChooser = sheetView.findViewById(R.id.topChooser);
        AppCompatTextView statusTitleTv = sheetView.findViewById(R.id.statusTitleTv);
        topChooser.setText(filterTopValue + "");
        TextView dateTypeTitle = sheetView.findViewById(R.id.dateTypeTitle);
        LinearLayout refundStatusChooserView = sheetView.findViewById(R.id.refundStatusChooserView);
        final AppCompatTextView refundStatusChooser = sheetView.findViewById(R.id.refundStatusChooser);
        LinearLayout dateTypeChooserView = sheetView.findViewById(R.id.dateTypeChooserView);
        final AppCompatTextView dateTypeChooser = sheetView.findViewById(R.id.dateTypeChooser);
        LinearLayout modeChooserView = sheetView.findViewById(R.id.modeChooserView);
        final AppCompatTextView modeChooser = sheetView.findViewById(R.id.modeChooser);
        LinearLayout criteriaChooserView = sheetView.findViewById(R.id.criteriaChooserView);
        final AppCompatTextView criteriaChooser = sheetView.findViewById(R.id.criteriaChooser);
        LinearLayout criteriaView = sheetView.findViewById(R.id.criteriaView);
        final TextView criteriaTitle = sheetView.findViewById(R.id.criteriaTitle);
        final AppCompatEditText criteriaEt = sheetView.findViewById(R.id.criteriaEt);
        Button filter = sheetView.findViewById(R.id.filter);
        if (dialogType.equalsIgnoreCase("Dispute")) {
            mobileView.setVisibility(View.GONE);
            walletTypeView.setVisibility(View.GONE);
            transactionIdView.setVisibility(View.GONE);
            accountNoView.setVisibility(View.GONE);
            childMobileView.setVisibility(View.GONE);
            modeChooserView.setVisibility(View.GONE);
            modeChooserView.setVisibility(View.GONE);
            dateTypeChooserView.setVisibility(View.VISIBLE);
            criteriaChooserView.setVisibility(View.VISIBLE);
            criteriaView.setVisibility(View.VISIBLE);
            refundStatusChooserView.setVisibility(View.VISIBLE);
            refundStatusChooser.setText(filterStatus != null && !filterStatus.isEmpty() ? filterStatus : disputeStatusArray[0]);
            dateTypeChooser.setText(filterDateType != null && !filterDateType.isEmpty() ? filterDateType : disputeDateTypeArray[0]);
            criteriaChooser.setText(filterChooseCriteria != null && !filterChooseCriteria.isEmpty() ? filterChooseCriteria : disputeCriteriaArray[0]);
            criteriaTitle.setText(filterChooseCriteriaId != 0 ? "Enter " + filterChooseCriteria : "Enter Criteria");
            criteriaEt.setText(filterEnterCriteria);
            criteriaEt.setHint(criteriaTitle.getText());

        } else if (dialogType.equalsIgnoreCase("MNP")) {
            mobileView.setVisibility(View.GONE);
            walletTypeView.setVisibility(View.GONE);
            transactionIdView.setVisibility(View.GONE);
            accountNoView.setVisibility(View.GONE);
            childMobileView.setVisibility(View.GONE);
            modeChooserView.setVisibility(View.GONE);
            modeChooserView.setVisibility(View.GONE);
            dateTypeChooserView.setVisibility(View.GONE);
            criteriaChooserView.setVisibility(View.GONE);
            criteriaView.setVisibility(View.GONE);
            refundStatusChooserView.setVisibility(View.GONE);


        } else if (dialogType.equalsIgnoreCase("Recharge")) {
            mobileView.setVisibility(View.GONE);
            walletTypeView.setVisibility(View.GONE);
            transactionIdView.setVisibility(View.VISIBLE);
            accountNoView.setVisibility(View.VISIBLE);
            refundStatusChooserView.setVisibility(View.VISIBLE);
            if (rollID == 3 || rollID == 2) {
                childMobileView.setVisibility(View.GONE);
            } else {
                childMobileView.setVisibility(View.VISIBLE);
            }
            statusTitleTv.setText("Choose Status");
            refundStatusChooser.setText("Choose Status");
            dateTypeChooserView.setVisibility(View.GONE);
            modeChooserView.setVisibility(View.GONE);
            criteriaChooserView.setVisibility(View.GONE);
            criteriaView.setVisibility(View.GONE);
            refundStatusChooser.setText(filterStatus != null && !filterStatus.isEmpty() ? filterStatus : rechargeStatusArray[0]);

            /*criteriaChooser.setText(filterChooseCriteria != null && !filterChooseCriteria.isEmpty() ? filterChooseCriteria : disputeCriteriaArray[0]);
            criteriaTitle.setText(filterChooseCriteriaId != 0 ? "Enter " + filterChooseCriteria : "Enter Criteria");
            criteriaEt.setText(filterEnterCriteria);
            criteriaEt.setHint(criteriaTitle.getText());*/

        } else if (dialogType.equalsIgnoreCase("AEPS")) {
            mobileView.setVisibility(View.GONE);
            walletTypeView.setVisibility(View.GONE);
            transactionIdView.setVisibility(View.VISIBLE);
            accountNoView.setVisibility(View.VISIBLE);
            refundStatusChooserView.setVisibility(View.VISIBLE);
            if (rollID == 3 || rollID == 2) {
                childMobileView.setVisibility(View.GONE);
            } else {
                childMobileView.setVisibility(View.VISIBLE);
            }
            statusTitleTv.setText("Choose Status");
            refundStatusChooser.setText("Choose Status");
            accountNoTitle.setText("AADHAR Number/Card Number");
            accountNoEt.setHint("AADHAR Number/Card Number");
            dateTypeChooserView.setVisibility(View.GONE);
            modeChooserView.setVisibility(View.GONE);
            criteriaChooserView.setVisibility(View.GONE);
            criteriaView.setVisibility(View.GONE);
            refundStatusChooser.setText(filterStatus != null && !filterStatus.isEmpty() ? filterStatus : rechargeStatusArray[0]);

            /*criteriaChooser.setText(filterChooseCriteria != null && !filterChooseCriteria.isEmpty() ? filterChooseCriteria : disputeCriteriaArray[0]);
            criteriaTitle.setText(filterChooseCriteriaId != 0 ? "Enter " + filterChooseCriteria : "Enter Criteria");
            criteriaEt.setText(filterEnterCriteria);
            criteriaEt.setHint(criteriaTitle.getText());*/

        } else if (dialogType.equalsIgnoreCase("DthSubscription")) {
            mobileView.setVisibility(View.GONE);
            walletTypeView.setVisibility(View.GONE);
            transactionIdView.setVisibility(View.VISIBLE);
            accountNoView.setVisibility(View.VISIBLE);
            refundStatusChooserView.setVisibility(View.VISIBLE);
            if (rollID == 3 || rollID == 2) {
                childMobileView.setVisibility(View.GONE);
            } else {
                childMobileView.setVisibility(View.VISIBLE);
            }
            statusTitleTv.setText("Choose Subscription Status");
            refundStatusChooser.setText("Choose Subscription Status");
            accountNoTitle.setText("Account Number");
            accountNoEt.setHint("Account Number");
            dateTypeChooserView.setVisibility(View.GONE);
            modeChooserView.setVisibility(View.GONE);
            criteriaChooserView.setVisibility(View.GONE);
            criteriaView.setVisibility(View.GONE);
            refundStatusChooser.setText(filterStatus != null && !filterStatus.isEmpty() ? filterStatus : dthSubscriptionStatusArray[0]);

            /*criteriaChooser.setText(filterChooseCriteria != null && !filterChooseCriteria.isEmpty() ? filterChooseCriteria : disputeCriteriaArray[0]);
            criteriaTitle.setText(filterChooseCriteriaId != 0 ? "Enter " + filterChooseCriteria : "Enter Criteria");
            criteriaEt.setText(filterEnterCriteria);
            criteriaEt.setHint(criteriaTitle.getText());*/

        } else if (dialogType.equalsIgnoreCase("DMT")) {
            mobileView.setVisibility(View.GONE);
            walletTypeView.setVisibility(View.GONE);
            transactionIdView.setVisibility(View.VISIBLE);
            accountNoView.setVisibility(View.VISIBLE);
            refundStatusChooserView.setVisibility(View.VISIBLE);
            if (rollID == 3 || rollID == 2) {
                childMobileView.setVisibility(View.GONE);
            } else {
                childMobileView.setVisibility(View.VISIBLE);
            }
            accountNoTitle.setText("Account Number");
            accountNoEt.setHint("Account Number");
            statusTitleTv.setText("Choose Status");
            refundStatusChooser.setText("Choose Status");
            dateTypeChooserView.setVisibility(View.GONE);
            modeChooserView.setVisibility(View.GONE);
            criteriaChooserView.setVisibility(View.GONE);
            criteriaView.setVisibility(View.GONE);
            refundStatusChooser.setText(filterStatus != null && !filterStatus.isEmpty() ? filterStatus : rechargeStatusArray[0]);

            /*criteriaChooser.setText(filterChooseCriteria != null && !filterChooseCriteria.isEmpty() ? filterChooseCriteria : disputeCriteriaArray[0]);
            criteriaTitle.setText(filterChooseCriteriaId != 0 ? "Enter " + filterChooseCriteria : "Enter Criteria");
            criteriaEt.setText(filterEnterCriteria);
            criteriaEt.setHint(criteriaTitle.getText());*/

        } else if (dialogType.equalsIgnoreCase("Ledger")) {
            mobileView.setVisibility(View.GONE);
            accountNoView.setVisibility(View.GONE);
            walletTypeView.setVisibility(View.VISIBLE);
            if (rollID == 3 || rollID == 2) {
                childMobileView.setVisibility(View.GONE);
            } else {
                childMobileView.setVisibility(View.VISIBLE);
            }
            transactionIdView.setVisibility(View.VISIBLE);

            modeChooserView.setVisibility(View.GONE);
            refundStatusChooserView.setVisibility(View.GONE);
            dateTypeChooserView.setVisibility(View.GONE);
            criteriaChooserView.setVisibility(View.GONE);
            criteriaView.setVisibility(View.GONE);


        } else if (dialogType.equalsIgnoreCase("FundOrder")) {
            transactionIdView.setVisibility(View.VISIBLE);
            walletTypeView.setVisibility(View.VISIBLE);
            accountNoView.setVisibility(View.VISIBLE);
            dateTypeChooserView.setVisibility(View.GONE);
            modeChooserView.setVisibility(View.VISIBLE);
            childMobileView.setVisibility(View.GONE);
            mobileView.setVisibility(View.GONE);
            criteriaChooserView.setVisibility(View.GONE);
            criteriaView.setVisibility(View.GONE);
            accountNoTitle.setText("Account Number");
            accountNoEt.setHint("Account Number");
            statusTitleTv.setText("Choose Status");
            refundStatusChooser.setText(filterStatus != null && !filterStatus.isEmpty() ? filterStatus : fundOrderStatusArray[0]);
            modeChooser.setText(filterModeValue != null && !filterModeValue.isEmpty() ? filterModeValue : chooseModeArray[0]);

        }


        criteriaChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (criteriaChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(disputeCriteriaArray).indexOf(criteriaChooser.getText().toString());
            }
            showSingleChoiceAlert(disputeCriteriaArray, selectedIndex, "Choose Criteria", "Choose Any Criteria", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    criteriaChooser.setText(disputeCriteriaArray[index]);
                    if (index == 0) {
                        criteriaTitle.setText("Enter Criteria");
                        criteriaEt.setHint("Enter Criteria");
                    } else {
                        criteriaTitle.setText("Enter " + disputeCriteriaArray[index]);
                        criteriaEt.setHint("Enter " + disputeCriteriaArray[index]);
                    }
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });
        topChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (topChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(ledgerChooseTopArray).indexOf(topChooser.getText().toString());
            }
            showSingleChoiceAlert(ledgerChooseTopArray, selectedIndex, "Select Top", "Choose Top", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    topChooser.setText(ledgerChooseTopArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });
        dateTypeChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (dateTypeChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(disputeDateTypeArray).indexOf(dateTypeChooser.getText().toString());
            }
            showSingleChoiceAlert(disputeDateTypeArray, selectedIndex, "Date Type", "Choose Date Type", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    dateTypeChooser.setText(disputeDateTypeArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });


        modeChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (modeChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(chooseModeArray).indexOf(modeChooser.getText().toString());
            }
            showSingleChoiceAlert(chooseModeArray, selectedIndex, "Date Type", "Choose Date Type", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    modeChooser.setText(chooseModeArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });

        refundStatusChooser.setOnClickListener(v -> {
            if (dialogType.equalsIgnoreCase("FundOrder")) {
                int selectedIndex = 0;
                if (refundStatusChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(fundOrderStatusArray).indexOf(refundStatusChooser.getText().toString());
                }
                showSingleChoiceAlert(fundOrderStatusArray, selectedIndex, "Status", "Choose Status", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        refundStatusChooser.setText(fundOrderStatusArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            } else if (dialogType.equalsIgnoreCase("Dispute")) {
                int selectedIndex = 0;
                if (refundStatusChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(disputeStatusArray).indexOf(refundStatusChooser.getText().toString());
                }
                showSingleChoiceAlert(disputeStatusArray, selectedIndex, "Status", "Choose Status", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        refundStatusChooser.setText(disputeStatusArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            } else if (dialogType.equalsIgnoreCase("Recharge") || dialogType.equalsIgnoreCase("AEPS") || dialogType.equalsIgnoreCase("DMT")) {
                int selectedIndex = 0;
                if (refundStatusChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(rechargeStatusArray).indexOf(refundStatusChooser.getText().toString());
                }
                showSingleChoiceAlert(rechargeStatusArray, selectedIndex, "Status", "Choose Status", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        refundStatusChooser.setText(rechargeStatusArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            } else if (dialogType.equalsIgnoreCase("DthSubscription")) {
                int selectedIndex = 0;
                if (refundStatusChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(dthSubscriptionStatusArray).indexOf(refundStatusChooser.getText().toString());
                }
                showSingleChoiceAlert(dthSubscriptionStatusArray, selectedIndex, "DTH Subscription Status", "Choose DTH Subscription Status", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        refundStatusChooser.setText(dthSubscriptionStatusArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }

        });
        startDateView.setOnClickListener(v -> setDCFromDate(startDate, endDate));
        endDateView.setOnClickListener(v -> setDCToDate(startDate, endDate));

        if (mLoginResponse.getData().getLoginTypeID() == 3) {
            walletTypeView.setVisibility(View.GONE);
        } else {
            mBalanceResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);

            if (mBalanceResponse != null && mBalanceResponse.getBalanceData() != null &&
                    mBalanceResponse.getBalanceData().size() > 0) {
                setWalletData(mBalanceResponse.getBalanceData(), filterWalletType, walletTypeChooser);

            } else {
                walletTypeView.setVisibility(View.GONE);
                ApiFintechUtilMethods.INSTANCE.Balancecheck(mActivity, null, mLoginResponse, null, null, mAppPreferences,
                        null, object -> {
                            mBalanceResponse = (BalanceResponse) object;
                            if (mBalanceResponse != null && mBalanceResponse.getBalanceData() != null &&
                                    mBalanceResponse.getBalanceData().size() > 0) {
                                setWalletData(mBalanceResponse.getBalanceData(), filterWalletType, walletTypeChooser);

                            }

                        });
            }
        }

        walletTypeChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (walletTypeChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(walletTypesArray).indexOf(walletTypeChooser.getText().toString());
            }
            showSingleChoiceAlert(walletTypesArray, selectedIndex, "Wallet Type", "Choose Wallet Type", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    walletTypeChooser.setText(walletTypesArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });


        filter.setOnClickListener(v -> {
            mBottomSheetDialog.dismiss();
            if (mLedgerFilterCallBack != null) {

                if (dialogType.equalsIgnoreCase("Dispute")) {
                    mLedgerFilterCallBack.onSubmitClick(startDate.getText().toString(),
                            endDate.getText().toString(),
                            mobileNoEt.getText().toString(),
                            transactionIdEt.getText().toString(),
                            childMobileNoEt.getText().toString(),
                            accountNoEt.getText().toString(),
                            topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()),
                            !refundStatusChooser.getText().toString().isEmpty() ? (disputeStatusMap.get(refundStatusChooser.getText().toString())) : 0,
                            refundStatusChooser.getText().toString(),
                            !dateTypeChooser.getText().toString().isEmpty() ? (disputeDateTypeMap.get(dateTypeChooser.getText().toString())) : 0,
                            dateTypeChooser.getText().toString(),
                            !modeChooser.getText().toString().isEmpty() ? (chooseModeMap.get(modeChooser.getText().toString())) : 0,
                            modeChooser.getText().toString(),
                            !criteriaChooser.getText().toString().isEmpty() ? (disputeCriteriaMap.get(criteriaChooser.getText().toString())) : 0,
                            criteriaChooser.getText().toString(),
                            criteriaEt.getText().toString(),
                            walletTypeView.getVisibility() == View.VISIBLE ? walletTypesMap.get(walletTypeChooser.getText().toString()) : 0, walletTypeChooser.getText().toString());
                } else if (dialogType.equalsIgnoreCase("MNP")) {
                    mLedgerFilterCallBack.onSubmitClick(startDate.getText().toString(),
                            endDate.getText().toString(),
                            mobileNoEt.getText().toString(),
                            transactionIdEt.getText().toString(),
                            childMobileNoEt.getText().toString(),
                            accountNoEt.getText().toString(),
                            topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()),
                            !refundStatusChooser.getText().toString().isEmpty() ? (disputeStatusMap.get(refundStatusChooser.getText().toString())) : 0,
                            refundStatusChooser.getText().toString(),
                            !dateTypeChooser.getText().toString().isEmpty() ? (disputeDateTypeMap.get(dateTypeChooser.getText().toString())) : 0,
                            dateTypeChooser.getText().toString(),
                            !modeChooser.getText().toString().isEmpty() ? (chooseModeMap.get(modeChooser.getText().toString())) : 0,
                            modeChooser.getText().toString(),
                            !criteriaChooser.getText().toString().isEmpty() ? (disputeCriteriaMap.get(criteriaChooser.getText().toString())) : 0,
                            criteriaChooser.getText().toString(),
                            criteriaEt.getText().toString(),
                            walletTypeView.getVisibility() == View.VISIBLE ? walletTypesMap.get(walletTypeChooser.getText().toString()) : 0, walletTypeChooser.getText().toString());
                } else if (dialogType.equalsIgnoreCase("FundOrder")) {
                    mLedgerFilterCallBack.onSubmitClick(startDate.getText().toString(),
                            endDate.getText().toString(),
                            mobileNoEt.getText().toString(),
                            transactionIdEt.getText().toString(),
                            childMobileNoEt.getText().toString(),
                            accountNoEt.getText().toString(),
                            topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()),
                            !refundStatusChooser.getText().toString().isEmpty() ? (fundOrderStatusMap.get(refundStatusChooser.getText().toString())) : 0,
                            refundStatusChooser.getText().toString(),
                            !dateTypeChooser.getText().toString().isEmpty() ? (disputeDateTypeMap.get(dateTypeChooser.getText().toString())) : 0,
                            dateTypeChooser.getText().toString(),
                            !modeChooser.getText().toString().isEmpty() ? (chooseModeMap.get(modeChooser.getText().toString())) : 0,
                            modeChooser.getText().toString(),
                            !criteriaChooser.getText().toString().isEmpty() ? (disputeCriteriaMap.get(criteriaChooser.getText().toString())) : 0,
                            criteriaChooser.getText().toString(),
                            criteriaEt.getText().toString(),
                            walletTypeView.getVisibility() == View.VISIBLE ? walletTypesMap.get(walletTypeChooser.getText().toString()) : 0, walletTypeChooser.getText().toString());
                } else if (dialogType.equalsIgnoreCase("Recharge") || dialogType.equalsIgnoreCase("AEPS") || dialogType.equalsIgnoreCase("DMT")) {
                    mLedgerFilterCallBack.onSubmitClick(startDate.getText().toString(),
                            endDate.getText().toString(),
                            mobileNoEt.getText().toString(),
                            transactionIdEt.getText().toString(), childMobileNoEt.getText().toString(),
                            accountNoEt.getText().toString(),
                            topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()),
                            !refundStatusChooser.getText().toString().isEmpty() ? (rechargeStatusMap.get(refundStatusChooser.getText().toString())) : 0,
                            refundStatusChooser.getText().toString(),
                            0,
                            dateTypeChooser.getText().toString(),
                            !modeChooser.getText().toString().isEmpty() ? (chooseModeMap.get(modeChooser.getText().toString())) : 0,
                            modeChooser.getText().toString(),
                            !criteriaChooser.getText().toString().isEmpty() ? (disputeCriteriaMap.get(criteriaChooser.getText().toString())) : 0,
                            criteriaChooser.getText().toString(),
                            criteriaEt.getText().toString(),
                            walletTypeView.getVisibility() == View.VISIBLE ? walletTypesMap.get(walletTypeChooser.getText().toString()) : 0, walletTypeChooser.getText().toString());
                } else if (dialogType.equalsIgnoreCase("DthSubscription")) {
                    mLedgerFilterCallBack.onSubmitClick(startDate.getText().toString(),
                            endDate.getText().toString(),
                            mobileNoEt.getText().toString(),
                            transactionIdEt.getText().toString(), childMobileNoEt.getText().toString(),
                            accountNoEt.getText().toString(),
                            topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()),
                            !refundStatusChooser.getText().toString().isEmpty() ? (dthSubscriptionStatusMap.get(refundStatusChooser.getText().toString())) : 0,
                            refundStatusChooser.getText().toString(),
                            0,
                            dateTypeChooser.getText().toString(),
                            !modeChooser.getText().toString().isEmpty() ? (chooseModeMap.get(modeChooser.getText().toString())) : 0,
                            modeChooser.getText().toString(),
                            !criteriaChooser.getText().toString().isEmpty() ? (disputeCriteriaMap.get(criteriaChooser.getText().toString())) : 0,
                            criteriaChooser.getText().toString(),
                            criteriaEt.getText().toString(),
                            walletTypeView.getVisibility() == View.VISIBLE ? walletTypesMap.get(walletTypeChooser.getText().toString()) : 0, walletTypeChooser.getText().toString());
                } else if (dialogType.equalsIgnoreCase("Ledger")) {
                    mLedgerFilterCallBack.onSubmitClick(startDate.getText().toString(),
                            endDate.getText().toString(),
                            mobileNoEt.getText().toString(), transactionIdEt.getText().toString(), childMobileNoEt.getText().toString(),
                            accountNoEt.getText().toString(),
                            topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()),
                            0, "", 0, "",
                            !modeChooser.getText().toString().isEmpty() ? (chooseModeMap.get(modeChooser.getText().toString())) : 0,
                            modeChooser.getText().toString(),
                            0, "", "",
                            walletTypeView.getVisibility() == View.VISIBLE ? walletTypesMap.get(walletTypeChooser.getText().toString()) : 0, walletTypeChooser.getText().toString());
                }

            }
        });
        mBottomSheetDialog.setContentView(sheetView);

        BottomSheetBehavior
                .from(mBottomSheetDialog.findViewById(R.id.design_bottom_sheet))
                .setState(BottomSheetBehavior.STATE_EXPANDED);

        mBottomSheetDialog.show();

    }


    void setWalletData(ArrayList<BalanceData> mBalanceDataList, String filterWalletType, AppCompatTextView walletTypeChooser) {
        walletTypesArray = new String[mBalanceDataList.size()];
        for (int i = 0; i < mBalanceDataList.size(); i++) {
            walletTypesMap.put(mBalanceDataList.get(i).getWalletType(),
                    mBalanceDataList.get(i).getId());
            walletTypesArray[i] = mBalanceDataList.get(i).getWalletType();
        }
        if (filterWalletType != null && !filterWalletType.isEmpty()) {
            walletTypeChooser.setText(filterWalletType);
        } else {
            walletTypeChooser.setText(walletTypesArray[0]);
        }
    }

    public void openDthSubscriptionFilter(int rollID, final String filterFromDate, String filterToDate,
                                          String filterMobileNo, String filterTransactionId, String filterChildMobileNo, String filterAccountNo,
                                          int filterTopValue, String filterStatus, String filterBookingStatus, String filterDateType, String filterModeValue,
                                          int filterChooseCriteriaId, String filterChooseCriteria, String filterEnterCriteria, String filterWalletType,
                                          final DthSubscriptionFilterCallBack mDthSubscriptionFilterCallBack) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);

        View sheetView = mActivity.getLayoutInflater().inflate(R.layout.dialog_ledger_filter, null);
        final LinearLayout walletTypeView = sheetView.findViewById(R.id.walletTypeView);
        final AppCompatTextView walletTypeChooser = sheetView.findViewById(R.id.walletTypeChooser);
        LinearLayout container = sheetView.findViewById(R.id.container);
        ImageView mobileNumberLeftIcon = sheetView.findViewById(R.id.mobileNumberLeftIcon);
        LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
        AppCompatTextView mobileNumTitle = sheetView.findViewById(R.id.mobileNumTitle);
        final AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);
        startDate.setText(filterFromDate);
        LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);
        endDate.setText(filterToDate);
        LinearLayout mobileView = sheetView.findViewById(R.id.mobileView);
        final AppCompatEditText mobileNoEt = sheetView.findViewById(R.id.mobileNoEt);
        mobileNoEt.setText(filterMobileNo);
        LinearLayout transactionIdView = sheetView.findViewById(R.id.transactionIdView);
        final AppCompatEditText transactionIdEt = sheetView.findViewById(R.id.transactionIdEt);
        transactionIdEt.setText(filterTransactionId);
        LinearLayout childMobileView = sheetView.findViewById(R.id.childMobileView);
        final AppCompatEditText childMobileNoEt = sheetView.findViewById(R.id.childMobileNoEt);
        childMobileNoEt.setText(filterChildMobileNo);
        LinearLayout accountNoView = sheetView.findViewById(R.id.accountNoView);
        TextView accountNoTitle = sheetView.findViewById(R.id.accountNoTitle);
        final AppCompatEditText accountNoEt = sheetView.findViewById(R.id.accountNoEt);
        accountNoEt.setText(filterAccountNo);
        LinearLayout topChooserView = sheetView.findViewById(R.id.topChooserView);
        final AppCompatTextView topChooser = sheetView.findViewById(R.id.topChooser);
        AppCompatTextView statusTitleTv = sheetView.findViewById(R.id.statusTitleTv);
        AppCompatTextView modeTitleTv = sheetView.findViewById(R.id.modeTitle);
        topChooser.setText(filterTopValue + "");
        TextView dateTypeTitle = sheetView.findViewById(R.id.dateTypeTitle);
        LinearLayout refundStatusChooserView = sheetView.findViewById(R.id.refundStatusChooserView);
        final AppCompatTextView refundStatusChooser = sheetView.findViewById(R.id.refundStatusChooser);
        LinearLayout dateTypeChooserView = sheetView.findViewById(R.id.dateTypeChooserView);
        final AppCompatTextView dateTypeChooser = sheetView.findViewById(R.id.dateTypeChooser);
        LinearLayout modeChooserView = sheetView.findViewById(R.id.modeChooserView);
        final AppCompatTextView modeChooser = sheetView.findViewById(R.id.modeChooser);
        LinearLayout criteriaChooserView = sheetView.findViewById(R.id.criteriaChooserView);
        final AppCompatTextView criteriaChooser = sheetView.findViewById(R.id.criteriaChooser);
        LinearLayout criteriaView = sheetView.findViewById(R.id.criteriaView);
        final TextView criteriaTitle = sheetView.findViewById(R.id.criteriaTitle);
        final AppCompatEditText criteriaEt = sheetView.findViewById(R.id.criteriaEt);
        Button filter = sheetView.findViewById(R.id.filter);

        //Condition accouding filter
        mobileView.setVisibility(View.GONE);
        walletTypeView.setVisibility(View.GONE);
        transactionIdView.setVisibility(View.VISIBLE);
        accountNoView.setVisibility(View.VISIBLE);
        refundStatusChooserView.setVisibility(View.VISIBLE);
        modeChooserView.setVisibility(View.VISIBLE);
        if (rollID == 3 || rollID == 2) {
            childMobileView.setVisibility(View.GONE);
        } else {
            childMobileView.setVisibility(View.VISIBLE);
        }
        statusTitleTv.setText("Choose Subscription Status");
        refundStatusChooser.setHint("Choose Subscription Status");
        modeTitleTv.setText("Choose Status");
        modeChooser.setHint("Choose Status");
        accountNoTitle.setText("Account Number");
        accountNoEt.setHint("Account Number");
        dateTypeChooserView.setVisibility(View.GONE);
        criteriaChooserView.setVisibility(View.GONE);
        criteriaView.setVisibility(View.GONE);
        refundStatusChooser.setText(filterBookingStatus != null && !filterBookingStatus.isEmpty() ? filterBookingStatus : dthSubscriptionBookingStatusArray[0]);
        modeChooser.setText(filterStatus != null && !filterStatus.isEmpty() ? filterStatus : dthSubscriptionStatusArray[0]);


        topChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (topChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(ledgerChooseTopArray).indexOf(topChooser.getText().toString());
            }
            showSingleChoiceAlert(ledgerChooseTopArray, selectedIndex, "Select Top", "Choose Top", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    topChooser.setText(ledgerChooseTopArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });


        modeChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (modeChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(dthSubscriptionStatusArray).indexOf(modeChooser.getText().toString());
            }
            showSingleChoiceAlert(dthSubscriptionStatusArray, selectedIndex, "Status", "Choose Status", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    modeChooser.setText(dthSubscriptionStatusArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });

        refundStatusChooser.setOnClickListener(v -> {

            int selectedIndex = 0;
            if (refundStatusChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(dthSubscriptionBookingStatusArray).indexOf(refundStatusChooser.getText().toString());
            }
            showSingleChoiceAlert(dthSubscriptionBookingStatusArray, selectedIndex, "DTH Subscription Status", "Choose DTH Subscription Status", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    refundStatusChooser.setText(dthSubscriptionBookingStatusArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });


        });
        startDateView.setOnClickListener(v -> setDCFromDate(startDate, endDate));
        endDateView.setOnClickListener(v -> setDCToDate(startDate, endDate));

        filter.setOnClickListener(v -> {
            mBottomSheetDialog.dismiss();
            if (mDthSubscriptionFilterCallBack != null) {
                mDthSubscriptionFilterCallBack.onSubmitClick(startDate.getText().toString(), endDate.getText().toString(),
                        mobileNoEt.getText().toString(), transactionIdEt.getText().toString(), childMobileNoEt.getText().toString(),
                        accountNoEt.getText().toString(),
                        topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()),
                        !modeChooser.getText().toString().trim().isEmpty() ? (dthSubscriptionStatusMap.get(modeChooser.getText().toString())) : 0,
                        modeChooser.getText().toString(),
                        !refundStatusChooser.getText().toString().trim().isEmpty() ? (dthSubscriptionBookingStatusMap.get(refundStatusChooser.getText().toString())) : 0,
                        refundStatusChooser.getText().toString(),
                        0, "", 0, "", 0, "", "", 0, "");

            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        BottomSheetBehavior
                .from(mBottomSheetDialog.findViewById(R.id.design_bottom_sheet))
                .setState(BottomSheetBehavior.STATE_EXPANDED);
        mBottomSheetDialog.show();

    }


    public void openDayBookFilter(int rollID, final String filterFromDate, String filterToDate,
                                  String filterChildMobileNo,
                                  final LedgerDayBookCallBack mLedgerDayBookCallBack) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);

        View sheetView = mActivity.getLayoutInflater().inflate(R.layout.dialog_ledger_filter, null);

        LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
        final AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);
        startDate.setText(filterFromDate);

        LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);
        endDate.setText(filterToDate);

        LinearLayout childMobileView = sheetView.findViewById(R.id.childMobileView);
        if (rollID == 3 || rollID == 2) {
            childMobileView.setVisibility(View.GONE);
        } else {
            childMobileView.setVisibility(View.VISIBLE);
        }

        final AppCompatEditText childMobileNoEt = sheetView.findViewById(R.id.childMobileNoEt);
        childMobileNoEt.setText(filterChildMobileNo);
        LinearLayout walletTypeView = sheetView.findViewById(R.id.walletTypeView);
        walletTypeView.setVisibility(View.GONE);
        LinearLayout mobileView = sheetView.findViewById(R.id.mobileView);
        mobileView.setVisibility(View.GONE);

        LinearLayout transactionIdView = sheetView.findViewById(R.id.transactionIdView);
        transactionIdView.setVisibility(View.GONE);

        LinearLayout accountNoView = sheetView.findViewById(R.id.accountNoView);
        accountNoView.setVisibility(View.GONE);

        LinearLayout topChooserView = sheetView.findViewById(R.id.topChooserView);
        topChooserView.setVisibility(View.GONE);

        LinearLayout refundStatusChooserView = sheetView.findViewById(R.id.refundStatusChooserView);
        refundStatusChooserView.setVisibility(View.GONE);

        LinearLayout dateTypeChooserView = sheetView.findViewById(R.id.dateTypeChooserView);
        dateTypeChooserView.setVisibility(View.GONE);

        LinearLayout modeChooserView = sheetView.findViewById(R.id.modeChooserView);
        modeChooserView.setVisibility(View.GONE);

        LinearLayout criteriaChooserView = sheetView.findViewById(R.id.criteriaChooserView);
        criteriaChooserView.setVisibility(View.GONE);

        LinearLayout criteriaView = sheetView.findViewById(R.id.criteriaView);
        criteriaView.setVisibility(View.GONE);

        Button filter = sheetView.findViewById(R.id.filter);

        startDateView.setOnClickListener(v -> setDCFromDate(startDate, endDate));
        endDateView.setOnClickListener(v -> setDCToDate(startDate, endDate));

        filter.setOnClickListener(v -> {
            mBottomSheetDialog.dismiss();
            if (mLedgerDayBookCallBack != null) {
                mLedgerDayBookCallBack.onSubmitClick(startDate.getText().toString(), endDate.getText().toString(), childMobileNoEt.getText().toString());
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        BottomSheetBehavior
                .from(mBottomSheetDialog.findViewById(R.id.design_bottom_sheet))
                .setState(BottomSheetBehavior.STATE_EXPANDED);
        mBottomSheetDialog.show();

    }

    /* public void openFundOrderFilter(final String dialogType, final String filterFromDate, String filterToDate, String filterMobileNo, String filterChildMobileNo, String filterAccountNo, String filterTransactionId, int filterTopValue, String filterStatus, String filterModeValue, final FundOrderFilterCallBack mFundOrderFilterCallBack) {
         final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);

         View sheetView = mActivity.getLayoutInflater().inflate(R.layout.dialog_ledger_filter, null);
         final LinearLayout walletTypeView = sheetView.findViewById(R.id.walletTypeView);
         final AppCompatTextView walletTypeChooser = sheetView.findViewById(R.id.walletTypeChooser);
         LinearLayout container = sheetView.findViewById(R.id.container);
         ImageView mobileNumberLeftIcon = sheetView.findViewById(R.id.mobileNumberLeftIcon);
         LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
         AppCompatTextView mobileNumTitle = sheetView.findViewById(R.id.mobileNumTitle);
         LinearLayout childMobileView = sheetView.findViewById(R.id.childMobileView);
         final AppCompatEditText childMobileNoEt = sheetView.findViewById(R.id.childMobileNoEt);
         childMobileNoEt.setText(filterChildMobileNo);
         LinearLayout accountNoView = sheetView.findViewById(R.id.accountNoView);
         TextView accountNoTitle = sheetView.findViewById(R.id.accountNoTitle);
         final AppCompatEditText accountNoEt = sheetView.findViewById(R.id.accountNoEt);
         accountNoEt.setText(filterAccountNo);
         final AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);
         startDate.setText(filterFromDate);
         LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
         final AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);
         endDate.setText(filterToDate);
         LinearLayout mobileView = sheetView.findViewById(R.id.mobileView);
         final AppCompatEditText mobileNoEt = sheetView.findViewById(R.id.mobileNoEt);
         mobileNoEt.setText(filterMobileNo);
         TextView dateTypeTitle = sheetView.findViewById(R.id.dateTypeTitle);
         LinearLayout transactionIdView = sheetView.findViewById(R.id.transactionIdView);
         final AppCompatEditText transactionIdEt = sheetView.findViewById(R.id.transactionIdEt);
         transactionIdEt.setText(filterTransactionId);
         LinearLayout topChooserView = sheetView.findViewById(R.id.topChooserView);
         final AppCompatTextView topChooser = sheetView.findViewById(R.id.topChooser);
         AppCompatTextView statusTitleTv = sheetView.findViewById(R.id.statusTitleTv);
         topChooser.setText(filterTopValue + "");
         LinearLayout refundStatusChooserView = sheetView.findViewById(R.id.refundStatusChooserView);
         final AppCompatTextView refundStatusChooser = sheetView.findViewById(R.id.refundStatusChooser);
         LinearLayout dateTypeChooserView = sheetView.findViewById(R.id.dateTypeChooserView);
         final AppCompatTextView dateTypeChooser = sheetView.findViewById(R.id.dateTypeChooser);
         LinearLayout criteriaChooserView = sheetView.findViewById(R.id.criteriaChooserView);
         final AppCompatTextView criteriaChooser = sheetView.findViewById(R.id.criteriaChooser);
         LinearLayout criteriaView = sheetView.findViewById(R.id.criteriaView);
         final TextView criteriaTitle = sheetView.findViewById(R.id.criteriaTitle);
         final AppCompatEditText criteriaEt = sheetView.findViewById(R.id.criteriaEt);
         Button filter = sheetView.findViewById(R.id.filter);
         if (dialogType.equalsIgnoreCase("Fund Order")) {
             transactionIdView.setVisibility(View.VISIBLE);
             walletTypeView.setVisibility(View.VISIBLE);
             accountNoView.setVisibility(View.VISIBLE);
             childMobileView.setVisibility(View.GONE);
             mobileView.setVisibility(View.GONE);
             criteriaChooserView.setVisibility(View.GONE);
             criteriaView.setVisibility(View.GONE);
             accountNoTitle.setText("Account Number");
             accountNoEt.setHint("Account Number");
             statusTitleTv.setText("Choose Status");
             dateTypeTitle.setText("Choose Mode");
             refundStatusChooser.setText(filterStatus != null && !filterStatus.isEmpty() ? filterStatus : fundOrderStatusArray[0]);
             dateTypeChooser.setText(filterModeValue != null && !filterModeValue.isEmpty() ? filterModeValue : chooseModeArray[0]);
            *//* criteriaChooser.setText(filterChooseCriteria != null && !filterChooseCriteria.isEmpty() ? filterChooseCriteria : disputeCriteriaArray[0]);
            criteriaTitle.setText(filterChooseCriteriaId != 0 ? "Enter " + filterChooseCriteria : "Enter Criteria");
            criteriaEt.setText(filterEnterCriteria);
            criteriaEt.setHint(criteriaTitle.getText());*//*

        }


        criteriaChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (criteriaChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(disputeCriteriaArray).indexOf(criteriaChooser.getText().toString());
                }
                showSingleChoiceAlert(disputeCriteriaArray, selectedIndex, "Choose Criteria", "Choose Any Criteria", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        criteriaChooser.setText(disputeCriteriaArray[index]);
                        if (index == 0) {
                            criteriaTitle.setText("Enter Criteria");
                            criteriaEt.setHint("Enter Criteria");
                        } else {
                            criteriaTitle.setText("Enter " + disputeCriteriaArray[index]);
                            criteriaEt.setHint("Enter " + disputeCriteriaArray[index]);
                        }
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });
        topChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (topChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(ledgerChooseTopArray).indexOf(topChooser.getText().toString());
                }
                showSingleChoiceAlert(ledgerChooseTopArray, selectedIndex, "Select Top", "Choose Top", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        topChooser.setText(ledgerChooseTopArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });
        dateTypeChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (dateTypeChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(chooseModeArray).indexOf(dateTypeChooser.getText().toString());
                }
                showSingleChoiceAlert(chooseModeArray, selectedIndex, "Date Type", "Choose Date Type", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        dateTypeChooser.setText(chooseModeArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });

        refundStatusChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogType.equalsIgnoreCase("Fund Order")) {
                    int selectedIndex = 0;
                    if (refundStatusChooser.getText().toString().length() == 0) {
                        selectedIndex = 0;
                    } else {
                        selectedIndex = Arrays.asList(fundOrderStatusArray).indexOf(refundStatusChooser.getText().toString());
                    }
                    showSingleChoiceAlert(fundOrderStatusArray, selectedIndex, "Status", "Choose Status", new SingleChoiceDialogCallBack() {
                        @Override
                        public void onPositiveClick(int index) {
                            refundStatusChooser.setText(fundOrderStatusArray[index]);
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });
                }
            }
        });
        startDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDCFromDate(startDate, endDate);
            }
        });
        endDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDCToDate(startDate, endDate);
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                if (mFundOrderFilterCallBack != null) {

                    if (dialogType.equalsIgnoreCase("Fund Order")) {
                        mFundOrderFilterCallBack.onSubmitClick(startDate.getText().toString(),
                                endDate.getText().toString(),
                                mobileNoEt.getText().toString(),
                                transactionIdEt.getText().toString(),
                                topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()),
                                fundOrderStatusMap.get(refundStatusChooser.getText().toString()),
                                refundStatusChooser.getText().toString(),
                                chooseModeMap.get(dateTypeChooser.getText().toString()),
                                dateTypeChooser.getText().toString());
                    }


                }
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        BottomSheetBehavior
              .from(mBottomSheetDialog.findViewById(R.id.design_bottom_sheet))
              .setState(BottomSheetBehavior.STATE_EXPANDED);
        mBottomSheetDialog.show();

    }
*/

    public void openReportFosFilter(final String filterFromDate, String filterToDate,
                                    int filterTopValue,
                                    final LedgerReportFosFilterCallBack mLedgerFilterCallBack) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);

        View sheetView = mActivity.getLayoutInflater().inflate(R.layout.dialog_ledger_filter, null);
        LinearLayout childMobileView = sheetView.findViewById(R.id.childMobileView);
        childMobileView.setVisibility(View.GONE);

        LinearLayout mobileView = sheetView.findViewById(R.id.mobileView);
        mobileView.setVisibility(View.GONE);

        LinearLayout accountNoView = sheetView.findViewById(R.id.accountNoView);
        accountNoView.setVisibility(View.GONE);

        LinearLayout transactionIdView = sheetView.findViewById(R.id.transactionIdView);
        transactionIdView.setVisibility(View.GONE);

        LinearLayout walletTypeView = sheetView.findViewById(R.id.walletTypeView);
        walletTypeView.setVisibility(View.GONE);

        LinearLayout refundStatusChooserView = sheetView.findViewById(R.id.refundStatusChooserView);
        refundStatusChooserView.setVisibility(View.GONE);

        LinearLayout dateTypeChooserView = sheetView.findViewById(R.id.dateTypeChooserView);
        dateTypeChooserView.setVisibility(View.GONE);

        LinearLayout modeChooserView = sheetView.findViewById(R.id.modeChooserView);
        modeChooserView.setVisibility(View.GONE);

        LinearLayout criteriaView = sheetView.findViewById(R.id.criteriaView);
        criteriaView.setVisibility(View.GONE);

        LinearLayout criteriaChooserView = sheetView.findViewById(R.id.criteriaChooserView);
        criteriaChooserView.setVisibility(View.GONE);


        LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
        final AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);
        startDate.setText(filterFromDate);
        LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);
        endDate.setText(filterToDate);


        LinearLayout topChooserView = sheetView.findViewById(R.id.topChooserView);
        final AppCompatTextView topChooser = sheetView.findViewById(R.id.topChooser);
        topChooser.setText(filterTopValue + "");


        Button filter = sheetView.findViewById(R.id.filter);


        topChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (topChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(ledgerChooseTopArray).indexOf(topChooser.getText().toString());
                }
                showSingleChoiceAlert(ledgerChooseTopArray, selectedIndex, "Select Top", "Choose Top", new SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        topChooser.setText(ledgerChooseTopArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });
        startDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDCFromDate(startDate, endDate);
            }
        });
        endDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDCToDate(startDate, endDate);
            }
        });


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                if (mLedgerFilterCallBack != null) {
                    mLedgerFilterCallBack.onSubmitClick(startDate.getText().toString(),
                            endDate.getText().toString(), topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()));


                }
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

    }


   /* public void setDCFromDate(final TextView fromDateTv, final TextView toDateTv) {
        DatePickerDialog mDatePicker = new DatePickerDialog(mActivity, (view, year, monthOfYear, dayOfMonth) -> {
            myCalendarFrom.set(Calendar.YEAR, year);
            myCalendarFrom.set(Calendar.MONTH, monthOfYear);
            myCalendarFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd MMM yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            fromDateTv.setText(sdf.format(myCalendarFrom.getTime()));
            Date dateFrom = myCalendarFrom.getTime();
            from_mill = dateFrom.getTime();
            Date dateTo = myCalendarTo.getTime();
            to_Mill = dateTo.getTime();

            if (from_mill > to_Mill) {
                toDateTv.setText(fromDateTv.getText());
                myCalendarTo.setTime(myCalendarFrom.getTime());
            } else if (currentDay == dayOfMonth && currentMonth == (monthOfYear + 1) && currentYear == year) {
                toDateTv.setText(fromDateTv.getText());
                myCalendarTo.setTime(myCalendarFrom.getTime());
            } else if (currentDay != dayOfMonth && currentMonth == (monthOfYear + 1) && currentYear == year) {
                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                cal.add(Calendar.DAY_OF_YEAR, -1);
                if (myCalendarFrom.get(Calendar.MONTH) != myCalendarTo.get(Calendar.MONTH) ||
                        myCalendarTo.get(Calendar.DAY_OF_MONTH) == currentDay) {
                    toDateTv.setText(sdf.format(cal.getTime()));
                    myCalendarTo.setTime(cal.getTime());
                }
            } else if (currentMonth != (monthOfYear + 1) && currentYear == year ||
                    currentMonth == (monthOfYear + 1) && currentYear != year ||
                    currentMonth != (monthOfYear + 1) && currentYear != year) {
                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                cal.setTime(myCalendarFrom.getTime());
                cal.add(Calendar.MONTH, 1);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.add(Calendar.DATE, -1);

                if (myCalendarFrom.get(Calendar.MONTH) != myCalendarTo.get(Calendar.MONTH) ||
                        myCalendarFrom.get(Calendar.YEAR) != myCalendarTo.get(Calendar.YEAR)) {
                    toDateTv.setText(sdf.format(cal.getTime()));
                    myCalendarTo.setTime(cal.getTime());
                }
            }
        }, myCalendarFrom
                .get(Calendar.YEAR), myCalendarFrom.get(Calendar.MONTH),
                myCalendarFrom.get(Calendar.DAY_OF_MONTH));
        mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        mDatePicker.show();
    }

    public void setDCToDate(final TextView fromDateTv, final TextView toDateTv) {
        DatePickerDialog mDatePicker = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarTo.set(Calendar.YEAR, year);
                myCalendarTo.set(Calendar.MONTH, monthOfYear);
                myCalendarTo.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                String myFormat = "dd MMM yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                toDateTv.setText(sdf.format(myCalendarTo.getTime()));
                Date dateFrom = myCalendarFrom.getTime();
                from_mill = dateFrom.getTime();
                Date dateTo = myCalendarTo.getTime();
                to_Mill = dateTo.getTime();

                if (from_mill > to_Mill) {
                    fromDateTv.setText(toDateTv.getText());
                    myCalendarFrom.setTime(myCalendarTo.getTime());
                } else if (currentDay == dayOfMonth && currentMonth == (monthOfYear + 1) && currentYear == year) {
                    fromDateTv.setText(toDateTv.getText());
                    myCalendarFrom.setTime(myCalendarTo.getTime());
                } else if (currentDay != dayOfMonth && currentMonth == (monthOfYear + 1) && currentYear == year ||
                        currentMonth != (monthOfYear + 1) && currentYear == year ||
                        currentMonth == (monthOfYear + 1) && currentYear != year ||
                        currentMonth != (monthOfYear + 1) && currentYear != year) {
                    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                    cal.setTime(myCalendarTo.getTime());
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    if (myCalendarFrom.get(Calendar.MONTH) != myCalendarTo.get(Calendar.MONTH) ||
                            myCalendarFrom.get(Calendar.YEAR) != myCalendarTo.get(Calendar.YEAR)) {
                        fromDateTv.setText(sdf.format(cal.getTime()));
                        myCalendarFrom.setTime(cal.getTime());
                    }
                }
            }

        }, myCalendarTo
                .get(Calendar.YEAR), myCalendarTo.get(Calendar.MONTH),
                myCalendarTo.get(Calendar.DAY_OF_MONTH));
        mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        mDatePicker.show();
    }*/
   public void setDCFromDate(final TextView fromDateTv, final TextView toDateTv) {
       DatePickerDialog mDatePicker = new DatePickerDialog(mActivity, (view, year, monthOfYear, dayOfMonth) -> {
           myCalendarFrom.set(Calendar.YEAR, year);
           myCalendarFrom.set(Calendar.MONTH, monthOfYear);
           myCalendarFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth);

           updateToCalendar();

           String myFormat = "dd MMM yyyy"; //In which you need put here
           SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
           fromDateTv.setText(sdf.format(myCalendarFrom.getTime()));
           toDateTv.setText(sdf.format(myCalendarTo.getTime()));
       }, myCalendarFrom
               .get(Calendar.YEAR), myCalendarFrom.get(Calendar.MONTH),
               myCalendarFrom.get(Calendar.DAY_OF_MONTH));
       mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
       mDatePicker.show();
   }

    public void setDCToDate(final TextView fromDateTv, final TextView toDateTv) {
        DatePickerDialog mDatePicker = new DatePickerDialog(mActivity, (view, year, monthOfYear, dayOfMonth) -> {
            myCalendarTo.set(Calendar.YEAR, year);
            myCalendarTo.set(Calendar.MONTH, monthOfYear);
            myCalendarTo.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateToCalendar();

            String myFormat = "dd MMM yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            toDateTv.setText(sdf.format(myCalendarTo.getTime()));
            fromDateTv.setText(sdf.format(myCalendarFrom.getTime()));
        }, myCalendarTo
                .get(Calendar.YEAR), myCalendarTo.get(Calendar.MONTH),
                myCalendarTo.get(Calendar.DAY_OF_MONTH));
        mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        mDatePicker.show();
    }

    private void updateToCalendar() {
        if (myCalendarFrom.getTimeInMillis() > myCalendarTo.getTimeInMillis()) {
            myCalendarTo.setTimeInMillis(myCalendarFrom.getTimeInMillis());
        }
    }



    public void showSingleChoiceAlert(String[] singleChoiceItems, int itemSelected, String title, String msg, final SingleChoiceDialogCallBack singleChoiceDialogCallBack) {

        selectedIndex = itemSelected;
        if (singleChoiceDialog != null && singleChoiceDialog.isShowing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity/*, themeResId*/);

        builder.setTitle(title);
        if (singleChoiceItems.length == 0) {
            builder.setMessage(Html.fromHtml("<font color='#646464'>" + msg + "</font>"));
        }
        builder.setSingleChoiceItems(singleChoiceItems, selectedIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int clickIndex) {
                selectedIndex = clickIndex;

            }
        });
        builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (singleChoiceDialogCallBack != null) {
                    singleChoiceDialogCallBack.onPositiveClick(selectedIndex);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (singleChoiceDialogCallBack != null) {
                    singleChoiceDialogCallBack.onNegativeClick();
                }
            }
        });
        singleChoiceDialog = builder.create();
        singleChoiceDialog.show();


    }

    public void openEmpUserListFilter(int filterChooseCriteriaId, String filterChooseCriteria, String filterEnterCriteria,
                                      int filterTopValue, String filterRoleValue, final EmpFilterCallBack mEmpFilterCallBack) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);

        View sheetView = mActivity.getLayoutInflater().inflate(R.layout.dialog_ledger_filter, null);
        LinearLayout dateView = sheetView.findViewById(R.id.dateView);
        dateView.setVisibility(View.GONE);
        LinearLayout childMobileView = sheetView.findViewById(R.id.childMobileView);
        childMobileView.setVisibility(View.GONE);
        LinearLayout mobileView = sheetView.findViewById(R.id.mobileView);
        mobileView.setVisibility(View.GONE);
        LinearLayout accountNoView = sheetView.findViewById(R.id.accountNoView);
        accountNoView.setVisibility(View.GONE);
        LinearLayout transactionIdView = sheetView.findViewById(R.id.transactionIdView);
        transactionIdView.setVisibility(View.GONE);
        LinearLayout walletTypeView = sheetView.findViewById(R.id.walletTypeView);
        walletTypeView.setVisibility(View.GONE);
        LinearLayout refundStatusChooserView = sheetView.findViewById(R.id.refundStatusChooserView);
        refundStatusChooserView.setVisibility(View.GONE);
        LinearLayout dateTypeChooserView = sheetView.findViewById(R.id.dateTypeChooserView);
        dateTypeChooserView.setVisibility(View.GONE);
        TextView modeTitle = sheetView.findViewById(R.id.modeTitle);
        TextView modeChooser = sheetView.findViewById(R.id.modeChooser);
        TextView topChooser = sheetView.findViewById(R.id.topChooser);
        TextView criteriaTitle = sheetView.findViewById(R.id.criteriaTitle);
        TextView criteriaChooser = sheetView.findViewById(R.id.criteriaChooser);
        EditText criteriaEt = sheetView.findViewById(R.id.criteriaEt);
        modeTitle.setText("Choose Role");
        modeChooser.setHint(":: Choose Role ::");
        modeChooser.setText(filterRoleValue != null && !filterRoleValue.isEmpty() ? filterRoleValue : empRoleArray[0]);
        topChooser.setText(filterTopValue != 0 ? filterTopValue + "" : ledgerChooseTopArray[0]);
        criteriaChooser.setText(filterChooseCriteria != null && !filterChooseCriteria.isEmpty() ? filterChooseCriteria : empCriteriaArray[0]);
        criteriaEt.setText(filterEnterCriteria != null && !filterEnterCriteria.isEmpty() ? filterEnterCriteria : "");
        if (filterChooseCriteriaId != 0) {
            criteriaTitle.setText("Enter " + filterChooseCriteria);
            criteriaEt.setHint("Enter " + filterChooseCriteria);
        }
        Button filter = sheetView.findViewById(R.id.filter);

        criteriaChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (criteriaChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(empCriteriaArray).indexOf(criteriaChooser.getText().toString());
            }
            showSingleChoiceAlert(empCriteriaArray, selectedIndex, "Choose Criteria", "Choose Any Criteria", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    criteriaChooser.setText(empCriteriaArray[index]);
                    if (index == 0) {
                        criteriaTitle.setText("Enter Criteria");
                        criteriaEt.setHint("Enter Criteria");
                    } else {
                        criteriaTitle.setText("Enter " + empCriteriaArray[index]);
                        criteriaEt.setHint("Enter " + empCriteriaArray[index]);
                    }
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });
        topChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (topChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(ledgerChooseTopArray).indexOf(topChooser.getText().toString());
            }
            showSingleChoiceAlert(ledgerChooseTopArray, selectedIndex, "Select Top", "Choose Top", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    topChooser.setText(ledgerChooseTopArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });

        modeChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (modeChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(empRoleArray).indexOf(modeChooser.getText().toString());
            }
            showSingleChoiceAlert(empRoleArray, selectedIndex, "Role", "Choose Role", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    modeChooser.setText(empRoleArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                if (mEmpFilterCallBack != null) {
                    mEmpFilterCallBack.onSubmitClick(topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()),
                            criteriaEt.getText().toString(),
                            empCriteriaMap.get(criteriaChooser.getText().toString()),
                            criteriaChooser.getText().toString(),
                            modeChooser.getText().toString(), empRoleMap.get(modeChooser.getText().toString()));
                }
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

    }


    public void openEmpMeetingFilter(final String filterFromDate, String filterToDate, int filterChooseCriteriaId, String filterChooseCriteria, String filterEnterCriteria,
                                     int filterTopValue, String filterRoleValue, final EmpMeetingFilterCallBack mEmpFilterCallBack) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);

        View sheetView = mActivity.getLayoutInflater().inflate(R.layout.dialog_ledger_filter, null);

        LinearLayout childMobileView = sheetView.findViewById(R.id.childMobileView);
        childMobileView.setVisibility(View.GONE);
        LinearLayout mobileView = sheetView.findViewById(R.id.mobileView);
        mobileView.setVisibility(View.GONE);
        LinearLayout accountNoView = sheetView.findViewById(R.id.accountNoView);
        accountNoView.setVisibility(View.GONE);
        LinearLayout transactionIdView = sheetView.findViewById(R.id.transactionIdView);
        transactionIdView.setVisibility(View.GONE);
        LinearLayout walletTypeView = sheetView.findViewById(R.id.walletTypeView);
        walletTypeView.setVisibility(View.GONE);
        LinearLayout refundStatusChooserView = sheetView.findViewById(R.id.refundStatusChooserView);
        refundStatusChooserView.setVisibility(View.GONE);
        LinearLayout dateTypeChooserView = sheetView.findViewById(R.id.dateTypeChooserView);
        dateTypeChooserView.setVisibility(View.GONE);
        TextView modeTitle = sheetView.findViewById(R.id.modeTitle);
        TextView modeChooser = sheetView.findViewById(R.id.modeChooser);
        TextView topChooser = sheetView.findViewById(R.id.topChooser);
        TextView criteriaTitle = sheetView.findViewById(R.id.criteriaTitle);
        TextView criteriaChooser = sheetView.findViewById(R.id.criteriaChooser);
        EditText criteriaEt = sheetView.findViewById(R.id.criteriaEt);
        LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
        final AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);
        startDate.setText(filterFromDate);

        LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);
        endDate.setText(filterToDate);

        modeTitle.setText("Choose Role");
        modeChooser.setHint(":: Choose Role ::");
        modeChooser.setText(filterRoleValue != null && !filterRoleValue.isEmpty() ? filterRoleValue : empRoleArray[0]);
        topChooser.setText(filterTopValue != 0 ? filterTopValue + "" : ledgerChooseTopArray[0]);
        criteriaChooser.setText(filterChooseCriteria != null && !filterChooseCriteria.isEmpty() ? filterChooseCriteria : empCriteriaArray[0]);
        criteriaEt.setText(filterEnterCriteria != null && !filterEnterCriteria.isEmpty() ? filterEnterCriteria : "");
        if (filterChooseCriteriaId != 0) {
            criteriaTitle.setText("Enter " + filterChooseCriteria);
            criteriaEt.setHint("Enter " + filterChooseCriteria);
        }
        Button filter = sheetView.findViewById(R.id.filter);

        criteriaChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (criteriaChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(empCriteriaArray).indexOf(criteriaChooser.getText().toString());
            }
            showSingleChoiceAlert(empCriteriaArray, selectedIndex, "Choose Criteria", "Choose Any Criteria", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    criteriaChooser.setText(empCriteriaArray[index]);
                    if (index == 0) {
                        criteriaTitle.setText("Enter Criteria");
                        criteriaEt.setHint("Enter Criteria");
                    } else {
                        criteriaTitle.setText("Enter " + empCriteriaArray[index]);
                        criteriaEt.setHint("Enter " + empCriteriaArray[index]);
                    }
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });
        topChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (topChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(ledgerChooseTopArray).indexOf(topChooser.getText().toString());
            }
            showSingleChoiceAlert(ledgerChooseTopArray, selectedIndex, "Select Top", "Choose Top", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    topChooser.setText(ledgerChooseTopArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });

        modeChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (modeChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(empRoleArray).indexOf(modeChooser.getText().toString());
            }
            showSingleChoiceAlert(empRoleArray, selectedIndex, "Role", "Choose Role", new SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    modeChooser.setText(empRoleArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });

        startDateView.setOnClickListener(v -> setDCFromDate(startDate, endDate));
        endDateView.setOnClickListener(v -> setDCToDate(startDate, endDate));
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                if (mEmpFilterCallBack != null) {
                    mEmpFilterCallBack.onSubmitClick(startDate.getText().toString(), endDate.getText().toString(), topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString()),
                            criteriaEt.getText().toString(),
                            empCriteriaMap.get(criteriaChooser.getText().toString()),
                            criteriaChooser.getText().toString(),
                            modeChooser.getText().toString(), empRoleMap.get(modeChooser.getText().toString()));
                }
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

    }

    public interface EmpMeetingFilterCallBack {

        void onSubmitClick(String fromDate, String tillDate, int topValue, String enterCriteria, int criteriaId, String criteriaValue, String roleValue, int roleId);
    }

    public interface EmpFilterCallBack {

        void onSubmitClick(int topValue, String enterCriteria, int criteriaId, String criteriaValue, String roleValue, int roleId);
    }

    public interface SingleChoiceDialogCallBack {
        void onPositiveClick(int index);

        void onNegativeClick();
    }

    public interface FundDebitCreditCallBack {

        void onSubmitClick(String fromDate, String toDate, String mobileNo, boolean isSelf, int walletTypeId, String walletType, int serviceTypeId, String serviceType, String otherMobile);
    }

    public interface LedgerFilterCallBack {

        void onSubmitClick(String fromDate, String toDate, String mobileNo, String transactionId, String childMobileNo,
                           String accountNo, int topValue, int statusId, String statusValue, int dateTypeId,
                           String dateTypeValue, int modeId,
                           String modeValue, int chooseCriteriaId, String chooseCriteriaValue, String enterCriteriaValue,
                           int walletTypeId, String walletType);
    }


    public interface LedgerDayBookCallBack {

        void onSubmitClick(String fromDate, String toDate, String childMobileNo);
    }

    public interface DthSubscriptionFilterCallBack {

        void onSubmitClick(String fromDate, String toDate, String mobileNo, String transactionId, String childMobileNo,
                           String accountNo, int topValue, int statusId, String statusValue, int bookingStatusId,
                           String bookingStatusValue, int dateTypeId,
                           String dateTypeValue, int modeId,
                           String modeValue, int chooseCriteriaId, String chooseCriteriaValue, String enterCriteriaValue,
                           int walletTypeId, String walletType);
    }

    public interface LedgerReportFosFilterCallBack {

        void onSubmitClick(String fromDate, String toDate, int topValue);
    }

    public interface FundOrderFilterCallBack {

        void onSubmitClick(String fromDate, String toDate, String mobileNo, String transactionId, int topValue, int statusId, String statusValue, int modeId, String modeValue);
    }
}
