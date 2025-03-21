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

import com.solution.app.justpay4u.Api.Fintech.Object.FundDCObject;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RechargeReportResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Reports.Adapter.FundDcReportAdapter;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class FundDcReportActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    FundDcReportAdapter mAdapter;
    ArrayList<FundDCObject> transactionsObjects = new ArrayList<>();
    CustomLoader loader;
    EditText search_all;
    CustomFilterDialog mCustomFilterDialog;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    private String filterFromDate, filterToDate;
    private String serviceTypeTitleStr = "Received By";
    private String filterMobieNum;
    private boolean isSelfFilter = true;
    private String filterOtherMobile;
    private String filterWalletType, filterService;
    private LoginResponse loginPrefResponse;
    private int filterWalletTypeId, filterServiceTypeId;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_fund_dc_report);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            loginPrefResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            findViews();
            filterMobieNum = loginPrefResponse.getData().getMobileNo() + "";
            filterOtherMobile = "";
            filterWalletTypeId = 1;
            filterServiceTypeId = 4;
            new Handler(Looper.getMainLooper()).post(() -> {
                mCustomFilterDialog = new CustomFilterDialog(this);
                filterFromDate = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(new Date());
                filterToDate = filterFromDate;
                HitApi();
            });
        });
    }


    void findViews() {
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        mAdapter = new FundDcReportAdapter(transactionsObjects, FundDcReportActivity.this, serviceTypeTitleStr);
        recycler_view.setAdapter(mAdapter);

        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        search_all = findViewById(R.id.search_all);
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));
        retryBtn.setOnClickListener(v -> HitApi());

        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.filter(s.toString());
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
            ApiFintechUtilMethods.INSTANCE.FundDCReport(this, isSelfFilter, filterWalletTypeId, filterServiceTypeId,
                    filterOtherMobile, filterFromDate, filterToDate, filterMobieNum,
                    loader, loginPrefResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
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

        if (transactions != null && transactions.getFundDCReport() != null) {
            transactionsObjects.clear();
            transactionsObjects.addAll(transactions.getFundDCReport());
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
           /* if (visibleFlag) {
                visibleFlag = false;
                searchContainer.setVisibility(View.GONE);
            } else {
                visibleFlag = true;
                searchContainer.setVisibility(View.VISIBLE);
            }*/

            mCustomFilterDialog.openFundDCFilter(filterFromDate, filterToDate, filterMobieNum, isSelfFilter, filterOtherMobile, filterService,
                    filterWalletType, serviceTypeTitleStr, loginPrefResponse, mAppPreferences,
                    (fromDate, toDate, mobileNo, isSelf, walletTypeId, walletType, serviceTypeId, serviceType, otherMobile) -> {
                        filterFromDate = fromDate;
                        filterToDate = toDate;
                        filterMobieNum = mobileNo;
                        isSelfFilter = isSelf;
                        filterOtherMobile = otherMobile;
                        filterService = serviceType;
                        filterWalletType = walletType;
                        filterWalletTypeId = walletTypeId;
                        filterServiceTypeId = serviceTypeId;
                        serviceTypeTitleStr = serviceTypeId == 4 ? "Received By" : "Fund Transfered To";
                        if (serviceTypeId == 4) {
                           setTitle("Fund Credit Report");
                        } else {
                            setTitle("Fund Debit Report");
                        }
                        HitApi();
                    });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}