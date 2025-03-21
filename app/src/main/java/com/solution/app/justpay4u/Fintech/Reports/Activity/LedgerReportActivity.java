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

import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Object.LedgerObject;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RechargeReportResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.Fintech.Reports.Adapter.LedgerReportAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class LedgerReportActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    LedgerReportAdapter mAdapter;
    ArrayList<LedgerObject> transactionsObjects = new ArrayList<>();
    EditText search_all;
    CustomLoader loader;
    String filterTransactionId, filterChildMobileNo, filterAccountNo;
    int filteWalletTypeId = 1, filterModeTypeId = 1;
    String filteWalletType;
    String intentWalletName;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterToDate = "";
    private String filterMobileNo = "";
    private int filterTopValue = 50;
    private String filterDateType = "";
    private String filterStatus = "";
    private String filterChooseCriteria = "", filterEnterCriteria = "";
    private String filterModeValue = "";
    private int filterChooseCriteriaId;
    private LoginResponse loginPrefResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_ledger_report);

            intentWalletName = getIntent().getStringExtra("WALLET_NAME");
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            loginPrefResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            findViews();
            clickView();


            filterChildMobileNo = loginPrefResponse.getData().getMobileNo() + "";
            filterMobileNo = loginPrefResponse.getData().getMobileNo() + "";


            new Handler(Looper.getMainLooper()).post(() -> {
                mCustomFilterDialog = new CustomFilterDialog(this);
                filterFromDate = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(new Date());
                filterToDate = filterFromDate;
                if (intentWalletName != null && !intentWalletName.isEmpty()) {
                    BalanceResponse mBalanceResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);

                    if (mBalanceResponse != null && mBalanceResponse.getBalanceData() != null &&
                            mBalanceResponse.getBalanceData().size() > 0) {
                        for (BalanceData type : mBalanceResponse.getBalanceData()) {
                            if (intentWalletName.contains(type.getWalletType())) {
                                filteWalletTypeId = type.getId();
                                filteWalletType = type.getWalletType();
                                break;
                            }
                        }
                    }
                }
                HitApi();
            });
        });

    }


    void findViews() {
        setTitle(loginPrefResponse.getData().getLoginTypeID() == 3 ? "Account Ledger" : "Ledger Report");
        search_all = findViewById(R.id.search_all);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);


        recycler_view = findViewById(R.id.recycler_view);
        mAdapter = new LedgerReportAdapter(transactionsObjects, LedgerReportActivity.this);
        recycler_view.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recycler_view.setAdapter(mAdapter);

    }


    void clickView() {
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));

        retryBtn.setOnClickListener(v -> HitApi());

        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                mAdapter.filter(newText.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            if (!loader.isShowing()) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }

            ApiFintechUtilMethods.INSTANCE.LedgerReport(this, "0", filterTopValue + "", "0",
                    filterFromDate, filterToDate, filteWalletTypeId, filterTransactionId, filterChildMobileNo,
                    "false", loader, loginPrefResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
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

        if (transactions != null && transactions.getLedgerReport() != null) {
            transactionsObjects.clear();
            transactionsObjects.addAll(transactions.getLedgerReport());
        }
        if (transactionsObjects.size() > 0 && transactionsObjects != null) {
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        } else {
            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
            ApiFintechUtilMethods.INSTANCE.Error(this, "Record not found between\n" + filterFromDate + " to " + filterToDate);
            recycler_view.setVisibility(View.GONE);
        }
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


            mCustomFilterDialog.openDisputeFilter("Ledger", loginPrefResponse.getData().getRoleID(),
                    filterFromDate, filterToDate, filterMobileNo, filterTransactionId, filterChildMobileNo,
                    filterAccountNo, filterTopValue, filterStatus, filterDateType, filterModeValue, filterChooseCriteriaId,
                    filterChooseCriteria, filterEnterCriteria, filteWalletType, loginPrefResponse, mAppPreferences,
                    (fromDate, toDate, mobileNo, transactionId, childMobileNo, accountNo, topValue, statusId, statusValue, dateTypeId,
                     dateTypeValue, modeTypeId, modeValue, chooseCriteriaId, chooseCriteriaValue, enterCriteriaValue, walletTypeId, walletType) -> {
                        filterFromDate = fromDate;
                        filterToDate = toDate;
                        filterMobileNo = mobileNo;
                        filterTransactionId = transactionId;
                        filterChildMobileNo = childMobileNo;
                        filterAccountNo = accountNo;
                        filterTopValue = topValue;
                        filterStatus = statusValue;
                        filterDateType = dateTypeValue;
                        filterChooseCriteriaId = chooseCriteriaId;
                        filterChooseCriteria = chooseCriteriaValue;
                        filterEnterCriteria = enterCriteriaValue;
                        filteWalletTypeId = walletTypeId;
                        filteWalletType = walletType;
                        filterModeValue = modeValue;
                        filterModeTypeId = modeTypeId;
                        HitApi();
                    });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
