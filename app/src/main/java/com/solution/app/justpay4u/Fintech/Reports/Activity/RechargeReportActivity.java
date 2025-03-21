package com.solution.app.justpay4u.Fintech.Reports.Activity;

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

import com.solution.app.justpay4u.Api.Fintech.Object.RechargeStatus;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RechargeReportResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Reports.Adapter.RechargeReportAdapter;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RechargeReportActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recycler_view;
    RechargeReportAdapter mAdapter;
    ArrayList<RechargeStatus> transactionsObjects = new ArrayList<>();
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    EditText search_all;
    CustomLoader loader;
    boolean isPSA;
    String filterTransactionId, filterChildMobileNo, filterAccountNo;
    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterToDate = "";
    private String filterMobileNo = "";
    private int filterTopValue = 50;
    private String filterDateType = "";
    private String filterStatus = "", filteWalletType = "";
    private String filterChooseCriteria = "", filterEnterCriteria = "";
    private int filterStatusId;
    private int filterChooseCriteriaId;
    private String filterModeValue = "";
    private LoginResponse mLoginDataResponse;
    private boolean isRecent;
    private AppPreferences mAppPrefrences;
    private String deviceId, deviceSerialNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_recharge_report);
            mAppPrefrences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPrefrences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPrefrences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPrefrences);
            isPSA = getIntent().getBooleanExtra("PSA", false);
            findViews();
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
        setTitle(isPSA ? "PSA Token History" : (mLoginDataResponse.getData().getLoginTypeID() == 3 ? "Transaction History" : "Recharge Report"));
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RechargeReportAdapter(transactionsObjects, this, isPSA, mLoginDataResponse, deviceId, deviceSerialNum);
        recycler_view.setAdapter(mAdapter);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Data not found for " + filterFromDate);
        search_all = findViewById(R.id.search_all);
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
        retryBtn.setOnClickListener(v -> HitApi(true));
    }

    public void HitApi(boolean isLoaderShow) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            if (isLoaderShow && !loader.isShowing()) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }
            ApiFintechUtilMethods.INSTANCE.RechargeReport(this, isPSA ? mAppPrefrences.getString(ApplicationConstant.INSTANCE.psaIdPref) : "0",
                    filterTopValue + "", filterStatusId, filterFromDate, filterToDate, filterTransactionId, filterAccountNo, filterChildMobileNo,
                    "false", isRecent, loader, mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
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

    public void dataParse(RechargeReportResponse mRechargeReportResponse) {

        if (mRechargeReportResponse != null && mRechargeReportResponse.getRechargeReport() != null) {
            transactionsObjects.clear();
            transactionsObjects.addAll(mRechargeReportResponse.getRechargeReport());
        }
        if (transactionsObjects.size() > 0) {
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();

        } else {
            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
            ApiFintechUtilMethods.INSTANCE.Error(this, "No Record Found ! between \n " + filterFromDate + " to " + filterToDate);
        }
    }

    @Override
    public void onClick(View v) {

    }

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
            mCustomFilterDialog.openDisputeFilter("Recharge", mLoginDataResponse.getData().getRoleID(),
                    filterFromDate, filterToDate, filterMobileNo, filterTransactionId, filterChildMobileNo,
                    filterAccountNo, filterTopValue, filterStatus, filterDateType, filterModeValue, filterChooseCriteriaId,
                    filterChooseCriteria, filterEnterCriteria, filteWalletType, mLoginDataResponse, mAppPrefrences,
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
                        filteWalletType = walletType;
                        filterModeValue = modeValue;
                        isRecent = false;
                        HitApi(true);
                    });
           /* if (visibleFlag) {
                visibleFlag = false;
                searchContainer.setVisibility(View.GONE);
            } else {
                visibleFlag = true;
                searchContainer.setVisibility(View.VISIBLE);
            }*/

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
