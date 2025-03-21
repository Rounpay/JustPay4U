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

import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.DthSubscriptionReport;
import com.solution.app.justpay4u.Api.Fintech.Response.DthSubscriptionReportResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Reports.Adapter.DthSubscriptionReportAdapter;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DthSubscriptionReportActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recycler_view;
    DthSubscriptionReportAdapter mAdapter;
    ArrayList<DthSubscriptionReport> transactionsObjects = new ArrayList<>();

    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    EditText search_all;
    CustomLoader loader;
    String filterTransactionId, filterChildMobileNo, filterAccountNo;
    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterToDate = "";
    private String filterMobileNo = "";
    private int filterTopValue = 50;
    private String filterDateType = "";
    private String filterStatus = "", filterBookingStatus = "", filteWalletType = "";
    private String filterChooseCriteria = "", filterEnterCriteria = "";
    private int filterChooseCriteriaId;
    private String filterModeValue = "";

    private LoginResponse mLoginDataResponse;
    private boolean isRecent;
    private AppPreferences mAppPreferences;
    private RequestOptions requestOptionsAdapter;
    private CustomAlertDialog mCustomAlertDialog;
    private int filterStatusId, filterBookingStatusId;
    private String deviceId, deviceSerialNum;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_dth_subscription_report);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            requestOptionsAdapter = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_AppLogo();
            findViews();
            findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));
            errorMsg.setText("Data not found for " + filterFromDate);
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
            retryBtn.setOnClickListener(v -> HitApi());

            new Handler(Looper.getMainLooper()).post(() -> {
                filterFromDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(Calendar.getInstance().getTime());
                filterToDate = filterFromDate;
                mCustomAlertDialog = new CustomAlertDialog(this);
                mCustomFilterDialog = new CustomFilterDialog(this);
                isRecent = true;
                HitApi();
        }); });
    }

    void findViews() {


        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DthSubscriptionReportAdapter(transactionsObjects, this, requestOptionsAdapter, mLoginDataResponse,
                mCustomAlertDialog, loader, deviceId, deviceSerialNum);
        recycler_view.setAdapter(mAdapter);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);

        search_all = findViewById(R.id.search_all);
    }

    public void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            if (!loader.isShowing()) {
                loader.show();
            }

            ApiFintechUtilMethods.INSTANCE.DthSubscriptionReport(this, "", filterTopValue + "", filterStatusId, filterBookingStatusId, filterFromDate,
                    filterToDate, filterTransactionId, filterAccountNo, filterChildMobileNo,
                    "false", isRecent, loader, mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            DthSubscriptionReportResponse mRechargeReportResponse = (DthSubscriptionReportResponse) object;
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


    public void dataParse(DthSubscriptionReportResponse mRechargeReportResponse) {


        if (mRechargeReportResponse != null && mRechargeReportResponse.getDTHSubscriptionReport() != null) {
            transactionsObjects.clear();
            transactionsObjects.addAll(mRechargeReportResponse.getDTHSubscriptionReport());
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
    /*public void dataParse(DthSubscriptionReportResponse mRechargeReportResponse) {


        transactionsObjects = mRechargeReportResponse.getDTHSubscriptionReport();

        if (transactionsObjects.size() > 0) {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.GONE);

            recycler_view.setVisibility(View.VISIBLE);
            mAdapter = new DthSubscriptionReportAdapter(transactionsObjects, this, requestOptionsAdapter, mLoginDataResponse,
                    mCustomAlertDialog, loader);
            mLayoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);


        } else {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
            ApiUtilMethods.INSTANCE.Error(this, "No Record Found ! between \n " + filterFromDate + " to " + filterToDate);
        }
    }*/

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

        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_filter) {
            mCustomFilterDialog.openDthSubscriptionFilter(mLoginDataResponse.getData().getRoleID(),
                    filterFromDate, filterToDate, filterMobileNo, filterTransactionId, filterChildMobileNo,
                    filterAccountNo, filterTopValue, filterStatus, filterBookingStatus, filterDateType, filterModeValue, filterChooseCriteriaId,
                    filterChooseCriteria, filterEnterCriteria, filteWalletType, (fromDate, toDate, mobileNo, transactionId,
                                                                                 childMobileNo, accountNo, topValue, statusId, statusValue,
                                                                                 bookingStatusId, bookingStatusValue, dateTypeId, dateTypeValue, modeTypeId,
                                                                                 modeValue, chooseCriteriaId, chooseCriteriaValue,
                                                                                 enterCriteriaValue, walletTypeId, walletType) -> {
                        filterFromDate = fromDate;
                        filterToDate = toDate;
                        filterMobileNo = mobileNo;
                        filterTransactionId = transactionId;
                        filterChildMobileNo = childMobileNo;
                        filterAccountNo = accountNo;
                        filterTopValue = topValue;
                        filterBookingStatusId = bookingStatusId;
                        filterBookingStatus = bookingStatusValue;
                        filterStatus = statusValue;
                        filterStatusId = statusId;
                        filterDateType = dateTypeValue;
                        filterChooseCriteriaId = chooseCriteriaId;
                        filterChooseCriteria = chooseCriteriaValue;
                        filterEnterCriteria = enterCriteriaValue;
                        filteWalletType = walletType;
                        filterModeValue = modeValue;
                        isRecent = false;
                        HitApi();
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
