package com.solution.app.justpay4u.Fintech.Reports.Activity;

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

import com.solution.app.justpay4u.Api.Fintech.Object.UserDaybookDMRReport;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Reports.Adapter.UserDayBookDMTListAdapter;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserDayBookDMTActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recycler_view;
    UserDayBookDMTListAdapter mAdapter;
    CustomLoader loader;
    LoginResponse mLoginPrefResponse;
    View totalCommView;
    TextView totalSelfComm, totalTeamComm;
    CustomFilterDialog mCustomFilterDialog;
    String filterFromDate, filterToDate, filterChildMobileNo;
    List<UserDaybookDMRReport> mUserDaybookDMRReports = new ArrayList<>();
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_user_day_book_dmt);

            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginPrefResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);


            findViews();
            new Handler(Looper.getMainLooper()).post(() -> {
            mCustomFilterDialog = new CustomFilterDialog(this);
            initData();
        }); });
    }


    void findViews() {


        totalCommView = findViewById(R.id.totalCommView);
        totalSelfComm = findViewById(R.id.totalSelfComm);
        totalTeamComm = findViewById(R.id.totalTeamComm);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new UserDayBookDMTListAdapter(UserDayBookDMTActivity.this, mUserDaybookDMRReports, mLoginPrefResponse.getData().getRoleID());
        recycler_view.setAdapter(mAdapter);


        retryBtn.setOnClickListener(this);

    }

    void initData() {

        filterFromDate = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(new Date());
        filterToDate = filterFromDate;

        errorMsg.setText("Data not found for " + filterFromDate);


        if (mLoginPrefResponse.getData().getRoleID() == 3 || mLoginPrefResponse.getData().getRoleID() == 2) {
            totalCommView.setVisibility(View.GONE);
        } else {
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

            if (mLoginPrefResponse.getData().getRoleID() == 3 || mLoginPrefResponse.getData().getRoleID() == 2) {
                filterChildMobileNo = "";
            } else {
                if (filterChildMobileNo == null || filterChildMobileNo.isEmpty()) {
                    filterChildMobileNo = mLoginPrefResponse.getData().getMobileNo();
                }
            }

            ApiFintechUtilMethods.INSTANCE.UserDayBookDmt(this, filterChildMobileNo, filterFromDate, filterToDate,
                    loader, mLoginPrefResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            dataParseDMT((AppUserListResponse) object);
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


    public void dataParseDMT(AppUserListResponse response) {
        try {
            if (response != null) {
                mUserDaybookDMRReports.clear();
                mUserDaybookDMRReports.addAll(response.getUserDaybookDMTReport());
                if (mUserDaybookDMRReports != null && mUserDaybookDMRReports.size() > 0) {
                    if (totalCommView.getVisibility() == View.VISIBLE) {
                        double selfComm = 0, teamComm = 0;
                        for (UserDaybookDMRReport item : mUserDaybookDMRReports) {
                            selfComm = selfComm + item.getSelfCommission();
                            teamComm = teamComm + item.getTeamCommission();
                        }

                        totalSelfComm.setText("\u20B9 " + selfComm);
                        totalTeamComm.setText("\u20B9 " + teamComm);
                    }
                    noNetworkView.setVisibility(View.GONE);
                    noDataView.setVisibility(View.GONE);
                    recycler_view.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged();

                } else {
                    setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                    ApiFintechUtilMethods.INSTANCE.Error(this, "Record not Found!");
                    recycler_view.setVisibility(View.GONE);
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
        mUserDaybookDMRReports.clear();
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
            mCustomFilterDialog.openDayBookFilter(mLoginPrefResponse.getData().getRoleID(),
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
