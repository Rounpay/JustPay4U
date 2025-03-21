package com.solution.app.justpay4u.Fintech.Reports.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.DmtReportObject;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RechargeReportResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RefundRequestResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Reports.Adapter.DMRReportAdapter;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Vishnu on 18-04-2017.
 */

public class DMRReportActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    DMRReportAdapter mAdapter;
    ArrayList<DmtReportObject> transactionsObjects = new ArrayList<>();
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    CustomLoader loader;
    EditText search_all;
    String filterTransactionId, filterChildMobileNo, filterAccountNo;
    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterToDate = "";
    private String filterMobileNo = "";
    private int filterTopValue = 50;
    private String filterDateType = "";
    private String filterStatus = "", filterWalletType = "";
    private String filterChooseCriteria = "", filterEnterCriteria = "";
    private int filterStatusId;
    private int filterChooseCriteriaId;
    private boolean isRecent;
    private String filterModeValue = "";
    private LoginResponse mLoginDataResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_dmr_report);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            filterChildMobileNo = mLoginDataResponse.getData().getMobileNo() + "";
            findViews();
            retryBtn.setOnClickListener(v -> HitApi(true));
            findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));
            search_all.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence newText, int start, int before, int count) {
                    if (mAdapter != null) {
                        mAdapter.filter(newText.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            new Handler(Looper.getMainLooper()).post(() -> {
                mCustomFilterDialog = new CustomFilterDialog(this);
                filterFromDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(Calendar.getInstance().getTime());
                filterToDate = filterFromDate;
                isRecent = true;
                HitApi(true);
            });
        });


    }


    void findViews() {

        noDataView = findViewById(R.id.noDataView);
        search_all = findViewById(R.id.search_all);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DMRReportAdapter(transactionsObjects, DMRReportActivity.this, mLoginDataResponse,
                deviceId, deviceSerialNum, mAppPreferences);
        recycler_view.setAdapter(mAdapter);
    }

    private void HitApi(boolean isLoaderShow) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            if (isLoaderShow && !loader.isShowing()) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }
            ApiFintechUtilMethods.INSTANCE.DMTReport(this, filterStatusId, filterTopValue + "", isRecent, "0",
                    filterFromDate, filterToDate, filterTransactionId, filterAccountNo, filterChildMobileNo,
                    loader, mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            RechargeReportResponse mRechargeReportResponse = (RechargeReportResponse) object;
                            dataParse(mRechargeReportResponse);
                        }

                        @Override
                        public void onError(int error) {
                            setInfoHideShow(error);
                        }
                    });

        } else {
            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK);
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    void setInfoHideShow(int errorType) {
        recycler_view.setVisibility(View.GONE);
        transactionsObjects.clear();
        mAdapter.notifyDataSetChanged();

        if (errorType == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        } else {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
            if (filterFromDate.equalsIgnoreCase(filterToDate)) {
                errorMsg.setText("Record not found for " + filterFromDate);
            } else {
                errorMsg.setText("Record not found between\n" + filterFromDate + " to " + filterToDate);
            }
        }
    }

    public void dataParse(RechargeReportResponse transactions) {

        if (transactions != null && transactions.getDmtReport() != null) {
            transactionsObjects.clear();
            transactionsObjects.addAll(transactions.getDmtReport());
        }
        if (transactionsObjects.size() > 0 && transactionsObjects != null) {
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        } else {
            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
//            ApiFintechUtilMethods.INSTANCE.Error(this, "Record not found between\n" + filterFromDate + " to " + filterToDate);
            recycler_view.setVisibility(View.GONE);
            filterShow();
        }

    }
   /* public void dataParse(RechargeReportResponse response) {

        transactions = response;
        transactionsObjects = transactions.getDmtReport();
        if (transactionsObjects.size() > 0) {
            mAdapter = new DMRReportAdapter(transactionsObjects, DMRReport.this);
            mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);
        } else {

            UtilMethods.INSTANCE.Error(this, "Record not found between\n" + filterFromDate + " to " + filterToDate);

        }
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_filter) {
            filterShow();
        }

        return super.onOptionsItemSelected(item);
    }

    public void filterShow() {
        mCustomFilterDialog.openDisputeFilter("DMT", mLoginDataResponse.getData().getRoleID(),
                filterFromDate, filterToDate, filterMobileNo, filterTransactionId, filterChildMobileNo,
                filterAccountNo, filterTopValue, filterStatus, filterDateType, filterModeValue, filterChooseCriteriaId,
                filterChooseCriteria, filterEnterCriteria, filterWalletType, mLoginDataResponse, mAppPreferences,
                (fromDate, toDate, mobileNo, transactionId, childMobileNo, accountNo, topValue, statusId, statusValue, dateTypeId,
                 dateTypeValue, modeTypeId, modeValue, chooseCriteriaId, chooseCriteriaValue, enterCriteriaValue, walletTypeId, walletType) -> {
                    filterFromDate = fromDate;
                    filterToDate = toDate;
                    filterMobileNo = mobileNo;
                    filterTransactionId = transactionId;
                    filterChildMobileNo = childMobileNo;
                    filterAccountNo = accountNo;
                    filterTopValue = topValue;
                    filterStatusId = statusId;
                    filterStatus = statusValue;
                    filterDateType = dateTypeValue;
                    filterChooseCriteriaId = chooseCriteriaId;
                    filterChooseCriteria = chooseCriteriaValue;
                    filterEnterCriteria = enterCriteriaValue;
                    filterWalletType = walletType;
                    filterModeValue = modeValue;
                    isRecent = false;
                    HitApi(true);
                });
    }

    public void Dispute(String tid, String transactionId, String otpValue, boolean isResend, TextView timerTv, TextView resendCodeTv,
                        final Dialog mDialog, View button) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            ApiFintechUtilMethods.INSTANCE.RefundRequest(this, tid + "", transactionId + "",
                    loader, otpValue, isResend, mLoginDataResponse, deviceId, deviceSerialNum, object -> {
                        RefundRequestResponse mRefundRequestResponse = (RefundRequestResponse) object;
                        if (mRefundRequestResponse.getOTPRequired()) {
                            if (mDialog != null && mDialog.isShowing()) {
                                ApiFintechUtilMethods.INSTANCE.Successful(DMRReportActivity.this, mRefundRequestResponse.getMsg() + "");
                                if (timerTv != null && resendCodeTv != null) {
                                    ApiFintechUtilMethods.INSTANCE.setTimer(timerTv, resendCodeTv);
                                }
                            } else {

                                ApiFintechUtilMethods.INSTANCE.openOtpDialog(DMRReportActivity.this, 6, mLoginDataResponse.getData().getMobileNo() + "", new ApiFintechUtilMethods.DialogOTPCallBack() {
                                    @Override
                                    public void onPositiveClick(String otpValue1, TextView timerTv1, TextView resendCodeTv1, Dialog mDialog1) {
                                        Dispute(tid, transactionId, otpValue1, false, timerTv1, resendCodeTv1, mDialog1, button);
                                    }

                                    @Override
                                    public void onResetCallback(String otpValue1, TextView timerTv1, TextView resendCodeTv1, Dialog mDialog1) {
                                        Dispute(tid, transactionId, "", true, timerTv1, resendCodeTv1, mDialog1, button);
                                    }
                                });

                            }
                        } else {
                            if (mDialog != null && mDialog.isShowing()) {
                                mDialog.dismiss();
                            }
                            if (button != null) {
                                button.setClickable(false);
                            }
                            ApiFintechUtilMethods.INSTANCE.Successful(DMRReportActivity.this, mRefundRequestResponse.getMsg() + "");
                            HitApi(false);
                        }
                    });
        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }


}
