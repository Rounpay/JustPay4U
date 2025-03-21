package com.solution.app.justpay4u.Fintech.Reports.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.RechargeStatus;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RechargeReportResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Reports.Adapter.RechargeReportAdapter;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SpecificRechargeReportActivity extends AppCompatActivity {
    EditText et_search_number;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    private CustomLoader loader;
    private LoginResponse mLoginDataResponse;
    private RecyclerView recycler_view;
    private String todatay;
    private ArrayList<RechargeStatus> transactionsObjects = new ArrayList<>();
    private RechargeReportAdapter mAdapter;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_specific_recharge_report);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            findViews();


            todatay = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(Calendar.getInstance().getTime());


            findViewById(R.id.searchView).setOnClickListener(v -> {
                if (et_search_number.getText().toString().isEmpty()) {
                    et_search_number.setError("Please Enter Mobile Number");
                    et_search_number.requestFocus();
                    return;
                } else if (et_search_number.getText().length() != 10) {
                    et_search_number.setError("Invalid Mobile Number");
                    et_search_number.requestFocus();
                    return;
                }
                HitApi();
            });
        });
    }


    void findViews() {

        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Enter number to search Recharge History");
        et_search_number = findViewById(R.id.et_search_number);
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RechargeReportAdapter(transactionsObjects, this, false, mLoginDataResponse, deviceId, deviceSerialNum);
        recycler_view.setAdapter(mAdapter);
    }

    public void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            ApiFintechUtilMethods.INSTANCE.RechargeReport(this, "0", "50", 0, todatay, todatay, "",
                    et_search_number.getText().toString(), "", "false", false, loader,
                    mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            RechargeReportResponse mRechargeReportResponse = (RechargeReportResponse) object;
                            dataParse(mRechargeReportResponse);
                        }

                        @Override
                        public void onError(int error) {
                            transactionsObjects.clear();
                            mAdapter.notifyDataSetChanged();
                            if (error == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
                                noDataView.setVisibility(View.GONE);
                                noNetworkView.setVisibility(View.VISIBLE);
                            } else {
                                errorMsg.setText("Data not found for " + et_search_number.getText().toString());
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                            }
                        }
                    });
        } else {
            transactionsObjects.clear();
            mAdapter.notifyDataSetChanged();
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.VISIBLE);
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    public void dataParse(RechargeReportResponse transactions) {

        transactionsObjects.clear();
        transactionsObjects.addAll(transactions.getRechargeReport());

        if (transactionsObjects.size() > 0) {
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);


        } else {
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
            errorMsg.setText("Data not found for " + et_search_number.getText().toString());
            ApiFintechUtilMethods.INSTANCE.Error(this, "Record Not Found.");
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
