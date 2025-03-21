package com.solution.app.justpay4u.Fintech.Activities;

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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Adapter.MNPClaimReportAdapter;
import com.solution.app.justpay4u.Api.Fintech.Object.MNPClaimsList;
import com.solution.app.justpay4u.Api.Fintech.Response.GetMNPStatusResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MNPClaimHistory extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recycler_view;
    MNPClaimReportAdapter mAdapter;
    ArrayList<MNPClaimsList> transactionsObjects = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    EditText search_all;
    CustomLoader loader;
    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterToDate = "";
    private String filterMobileNo = "";
    String filterTransactionId, filterChildMobileNo, filterAccountNo;
    private int filterTopValue = 50;
    private String filterDateType = "";
    private String filterStatus = "", filteWalletType = "";
    private String filterChooseCriteria = "", filterEnterCriteria = "";
    private int filterChooseCriteriaId;
    private String filterModeValue = "";
    private LoginResponse mLoginDataResponse;
    private boolean isRecent;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_mnp_claim_history);
            // Start loading the ad in the background.
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            findViews();

            new Handler(Looper.getMainLooper()).post(() -> {
                mCustomFilterDialog = new CustomFilterDialog(this);
                filterFromDate = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(new Date());
                filterToDate = filterFromDate;
                isRecent = true;
                HitApi();
            });
        });

    }

    void findViews() {
        search_all = findViewById(R.id.search_all);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);


        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));
        retryBtn.setOnClickListener(view -> HitApi());
    }

    public void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            if (!loader.isShowing()) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }
            ApiFintechUtilMethods.INSTANCE.GetUserClaimsReport(this,
                    filterTopValue + "", filterFromDate, filterToDate,
                    isRecent, loader, mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            GetMNPStatusResponse mRechargeReportResponse = (GetMNPStatusResponse) object;
                            dataParse(mRechargeReportResponse);
                        }

                        @Override
                        public void onError(int error) {
                            recycler_view.setVisibility(View.GONE);
                            transactionsObjects.clear();
                            if (mAdapter != null) {
                                mAdapter.notifyDataSetChanged();
                            }
                            if (error == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
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
                    });
        } else {
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.VISIBLE);
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }


    public void dataParse(GetMNPStatusResponse mRechargeReportResponse) {

        if(mRechargeReportResponse!=null) {
            transactionsObjects = mRechargeReportResponse.getMnpClaimsList();

            if (transactionsObjects.size() > 0) {
                noDataView.setVisibility(View.GONE);
                noNetworkView.setVisibility(View.GONE);
                recycler_view.setVisibility(View.VISIBLE);
                mAdapter = new MNPClaimReportAdapter(transactionsObjects, this);
                mLayoutManager = new LinearLayoutManager(this);
                recycler_view.setLayoutManager(mLayoutManager);
                recycler_view.setItemAnimator(new DefaultItemAnimator());
                recycler_view.setAdapter(mAdapter);
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

            } else {
                noDataView.setVisibility(View.VISIBLE);
                noNetworkView.setVisibility(View.GONE);
                recycler_view.setVisibility(View.GONE);
                transactionsObjects.clear();
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                ApiFintechUtilMethods.INSTANCE.Error(this, "No Record Found ! between \n " + filterFromDate + " to " + filterToDate);
            }
        }else {
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
            recycler_view.setVisibility(View.GONE);
            transactionsObjects.clear();
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
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

        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_filter) {
            mCustomFilterDialog.openDisputeFilter("MNP", mLoginDataResponse.getData().getRoleID(),
                    filterFromDate, filterToDate, filterMobileNo, filterTransactionId, filterChildMobileNo,
                    filterAccountNo, filterTopValue, filterStatus, filterDateType, filterModeValue, filterChooseCriteriaId,
                    filterChooseCriteria, filterEnterCriteria, filteWalletType, mLoginDataResponse, mAppPreferences, (fromDate, toDate, mobileNo, transactionId,
                                                                                                                      childMobileNo, accountNo, topValue, statusId,
                                                                                                                      statusValue, dateTypeId, dateTypeValue, modeTypeId,
                                                                                                                      modeValue, chooseCriteriaId, chooseCriteriaValue,
                                                                                                                      enterCriteriaValue, walletTypeId, walletType) -> {
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
                        filteWalletType = walletType;
                        filterModeValue = modeValue;
                        isRecent = false;
                        HitApi();
                    });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
