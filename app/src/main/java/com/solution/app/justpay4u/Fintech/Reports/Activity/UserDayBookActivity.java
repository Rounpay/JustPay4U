package com.solution.app.justpay4u.Fintech.Reports.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.UserDaybookReport;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Reports.Adapter.UserDayBookListAdapter;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserDayBookActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recycler_view;
    UserDayBookListAdapter mAdapter;
    CustomLoader loader;
    TextView totalsuccess, totalpending, totalfailed, totalcomm, totalAmt;
    LoginResponse mLoginPrefResponse;
    View totalCommView, bottomView, pendingView, totalView;
    TextView totalSelfComm, totalTeamComm;
    CustomFilterDialog mCustomFilterDialog;
    String filterFromDate, filterToDate;
    List<UserDaybookReport> mUserDaybookReports = new ArrayList<>();
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    private String filterChildMobileNo = "";
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private int rollId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_user_day_book);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginPrefResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            rollId = mLoginPrefResponse.getData().getRoleID();

            findViews();
            new Handler(Looper.getMainLooper()).post(() -> {
                mCustomFilterDialog = new CustomFilterDialog(this);
                initData();
            });
        });

    }


    void findViews() {

        totalsuccess = findViewById(R.id.totalSuccess);
        totalpending = findViewById(R.id.totalPending);
        totalfailed = findViewById(R.id.totalfailed);
        totalcomm = findViewById(R.id.totalcomm);
        totalAmt = findViewById(R.id.totalAmt);
        bottomView = findViewById(R.id.bottomView);
        pendingView = findViewById(R.id.pendingView);
        totalView = findViewById(R.id.totalView);

        totalCommView = findViewById(R.id.totalCommView);
        totalSelfComm = findViewById(R.id.totalSelfComm);
        totalTeamComm = findViewById(R.id.totalTeamComm);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);

        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new UserDayBookListAdapter(UserDayBookActivity.this, mUserDaybookReports, rollId);
        recycler_view.setAdapter(mAdapter);


        retryBtn.setOnClickListener(this);

    }

    void initData() {

        filterFromDate = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(new Date());
        filterToDate = filterFromDate;

        errorMsg.setText("Data not found for " + filterFromDate);

        if (rollId == 3 || rollId == 2) {
            pendingView.setVisibility(View.GONE);
            totalView.setVisibility(View.VISIBLE);
            totalCommView.setVisibility(View.GONE);
        } else {
            pendingView.setVisibility(View.VISIBLE);
            totalView.setVisibility(View.GONE);
            totalCommView.setVisibility(View.VISIBLE);
        }


        HitApi();
    }

    @Override
    public void onClick(View v) {


        if (v == retryBtn) {
            HitApi();
        }
    }

    private void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            if (!loader.isShowing()) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }

            if (rollId == 3 || rollId == 2) {
                filterChildMobileNo = "";
            } else {
                if (filterChildMobileNo == null || filterChildMobileNo.isEmpty()) {
                    filterChildMobileNo = mLoginPrefResponse.getData().getMobileNo();
                }
            }

            ApiFintechUtilMethods.INSTANCE.UserDayBook(this, filterChildMobileNo, filterFromDate, filterToDate,
                    loader, mLoginPrefResponse, mAppPreferences, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            dataParse((AppUserListResponse) object);
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

    @SuppressLint("SetTextI18n")
    public void dataParse(AppUserListResponse response) {
        try {
            if (response != null) {
                mUserDaybookReports.clear();
                mUserDaybookReports.addAll(response.getUserDaybookReport());

                if (mUserDaybookReports != null && mUserDaybookReports.size() > 0) {
                    /* if (totalCommView.getVisibility() == View.VISIBLE) {*/
                    double selfComm = 0, teamComm = 0, totalSuccess = 0, totalFailed = 0, totalPending = 0, totalCommission = 0, totalAmount = 0;

                    for (UserDaybookReport item : mUserDaybookReports) {
                        selfComm = selfComm + item.getSelfCommission();
                        teamComm = teamComm + item.getTeamCommission();


                        totalSuccess = totalSuccess + item.getSuccessAmount();
                        totalFailed = totalFailed + item.getFailedAmount();
                        totalPending = totalPending + item.getPendingAmount();
                        totalCommission = totalCommission + item.getCommission();
                        totalAmount = totalAmount + item.getTotalAmount();
                    }

                    totalSelfComm.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( selfComm));
                    totalTeamComm.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( teamComm));

                    totalsuccess.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( totalSuccess));
                    totalpending.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( totalPending));
                    totalfailed.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( totalFailed));
                    totalcomm.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( totalCommission));
                    totalAmt.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( totalAmount));
                    /* }*/
                    noNetworkView.setVisibility(View.GONE);
                    noDataView.setVisibility(View.GONE);
                    recycler_view.setVisibility(View.VISIBLE);
                    bottomView.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged();


                } else {
                    setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                    ApiFintechUtilMethods.INSTANCE.Error(this, "Record not Found!");
                    recycler_view.setVisibility(View.GONE);
                    bottomView.setVisibility(View.GONE);
                }
            } else {
                setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setInfoHideShow(int errorType) {
        recycler_view.setVisibility(View.GONE);
        bottomView.setVisibility(View.GONE);
        mUserDaybookReports.clear();
        mAdapter.notifyDataSetChanged();

        if (errorType == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        } else {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
            if (filterFromDate.equalsIgnoreCase(filterToDate)) {
                errorMsg.setText("Data not found for " + filterFromDate);
            } else {
                errorMsg.setText("Data not found from " + filterFromDate + " to " + filterToDate);
            }

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

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_filter) {
            mCustomFilterDialog.openDayBookFilter(rollId,
                    filterFromDate, filterToDate, filterChildMobileNo,
                    new CustomFilterDialog.LedgerDayBookCallBack() {
                        @Override
                        public void onSubmitClick(String fromDate, String toDate, String childMobileNo) {
                            filterFromDate = fromDate;
                            filterToDate = toDate;
                            filterChildMobileNo = childMobileNo;

                            HitApi();
                        }
                    });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
