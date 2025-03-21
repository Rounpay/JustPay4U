package com.solution.app.justpay4u.Networking.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Networking.Response.GetPoolUpLineResponse;
import com.solution.app.justpay4u.Api.Networking.Response.ListUserPoolDetail;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.NetworkingEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Networking.Adapter.ListUserPoolDetailAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPoolUpLineReport extends AppCompatActivity {

    RecyclerView recycler_view;
    ListUserPoolDetailAdapter mAdapter;
    ArrayList<ListUserPoolDetail> listUserPoolDetail = new ArrayList<>();
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    EditText search_all;
    CustomLoader loader;
    private CustomFilterDialog mCustomFilterDialog;
    private AppPreferences mAppPreference;
    private String deviceId, deviceSerialNum;
    private LoginResponse mLoginDataResponse;
    private String filterFromDate = "", filterToDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pool_up_line_report);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAppPreference = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
        mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreference);
        deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreference);
        deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreference);
        filterFromDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(Calendar.getInstance().getTime());
        filterToDate = filterFromDate;
        findViews();
        GetPoolUpLineUserApp();
    }


    @SuppressLint("SetTextI18n")
    private void findViews() {
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Data not found for " + filterFromDate);
        search_all = findViewById(R.id.search_all);
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));
    }

    private void GetPoolUpLineUserApp() {
        try {
            loader.show();
            NetworkingEndPointInterface git = ApiClient.getClient().create(NetworkingEndPointInterface.class);
            Call<GetPoolUpLineResponse> call = git.GetPoolUpLineUserApp(new BasicRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetPoolUpLineResponse>() {
                @Override
                public void onResponse(Call<GetPoolUpLineResponse> call, Response<GetPoolUpLineResponse> response) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    if (response.isSuccessful()){
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1 && response.body().getData()!=null
                                    && response.body().getData().getListUserPoolDetails()!=null ) {
                                dataParse(response.body().getData().getListUserPoolDetails());
                            }
                            else {
                                ApiFintechUtilMethods.INSTANCE.Error(GetPoolUpLineReport.this, response.body().getMsg() + "");
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetPoolUpLineResponse> call, Throwable t) {
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(GetPoolUpLineReport.this, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(GetPoolUpLineReport.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dataParse(List<ListUserPoolDetail> listUserPoolDetails) {
        mAdapter = new ListUserPoolDetailAdapter(listUserPoolDetails, GetPoolUpLineReport.this);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        // Custom back press behavior, if needed
        super.onBackPressed();
    }
}