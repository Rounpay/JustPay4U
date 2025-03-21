package com.solution.app.justpay4u.Fintech.Employee.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.Fintech.Employee.Adapter.TargetReportAdapter;
import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetTargetReport;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetTargetReportResponse;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TargetReport extends AppCompatActivity {

    RecyclerView recycler_view;
    TargetReportAdapter mAdapter;
    ImageView search;
    TextView searchDate;
    EditText search_all;
    CustomLoader loader;
    private LoginResponse mLoginDataResponse;
    private String todatay, deviceId, deviceSerialNum;
    private AppPreferences mAppPreferences;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_target_report);
            // Start loading the ad in the background.

            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            noDataView = findViewById(R.id.noDataView);
            search_all = findViewById(R.id.search_all);
            noNetworkView = findViewById(R.id.noNetworkView);
            retryBtn = findViewById(R.id.retryBtn);
            errorMsg = findViewById(R.id.errorMsg);
            recycler_view = findViewById(R.id.recycler_view);
            recycler_view.setLayoutManager(new LinearLayoutManager(this));
            searchDate = findViewById(R.id.searchDate);
            search = findViewById(R.id.search);
            search_all = findViewById(R.id.search_all);
            findViewById(R.id.searchContainer).setVisibility(View.VISIBLE);

            findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HitApi();
                }
            });
            new Handler(Looper.getMainLooper()).post(() -> {

                final Calendar myCalendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                todatay = sdf.format(myCalendar.getTime());
                searchDate.setText(todatay + "");
                HitApi();
                final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    searchDate.setText(sdf.format(myCalendar.getTime()));
                    /*Date date1 = myCalendar.getTime();*/

                };
                searchDate.setOnClickListener(v -> {
                    DatePickerDialog mDatePickerDialog = new DatePickerDialog(this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    mDatePickerDialog.show();
                });
                /////////////////////////////////////////////////////////////////////

            });
        });
    }

    public void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            if (!loader.isShowing()) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }
            ApiFintechUtilMethods.INSTANCE.GetEmployeeTargetReport(this, searchDate.getText().toString(), 0, 0, mLoginDataResponse, deviceId,
                    deviceSerialNum, loader, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            GetTargetReportResponse mGetTargetReportResponse = (GetTargetReportResponse) object;
                            dataParse(mGetTargetReportResponse);
                        }

                        @Override
                        public void onError(int error) {
                            setInfoHideShow(error);
                        }
                    });
        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    void setInfoHideShow(int errorType) {
        recycler_view.setVisibility(View.GONE);


        if (errorType == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        } else {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);

            errorMsg.setText("Record not found");

        }
    }

    public void dataParse(GetTargetReportResponse mRechargeReportResponse) {

        ArrayList<GetTargetReport> transactionsObjects = mRechargeReportResponse.getData();

        if (transactionsObjects.size() > 0) {
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            mAdapter = new TargetReportAdapter(transactionsObjects, this);
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
            ApiFintechUtilMethods.INSTANCE.Error(this, "No Record Found");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
