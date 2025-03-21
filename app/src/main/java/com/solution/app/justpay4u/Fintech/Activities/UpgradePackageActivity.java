package com.solution.app.justpay4u.Fintech.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Adapter.UpgradePackageAdapter;
import com.solution.app.justpay4u.Api.Fintech.Object.PackageDetails;
import com.solution.app.justpay4u.Api.Fintech.Response.GetAvailablePackageResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;

public class UpgradePackageActivity extends AppCompatActivity {


    RecyclerView mRecyclerView;
    CustomAlertDialog customAlertDialog;
    ArrayList<PackageDetails> mPackageDetails = new ArrayList<>();
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    private CustomLoader loader;
    private String uid;
    private UpgradePackageAdapter mUpgradePackageAdapter;
    private LoginResponse mLoginDataResponse;
    private AppPreferences mAppPreferences;
    private boolean isFromAdditionalService;
    private String deviceId, deviceSerialNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_upgrade_package);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            customAlertDialog = new CustomAlertDialog(this);
            isFromAdditionalService = getIntent().getBooleanExtra("FromAdditionalService", false);
            findViews();

            retryBtn.setOnClickListener(v -> hitApi());
            hitApi();
        });

    }

    void findViews() {
        setTitle(isFromAdditionalService ? "Upgrade Additional Service" : "Upgrade Package");


        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);

        errorMsg.setText(isFromAdditionalService ? "Service list not found." : "Package list not found.");
        TextView beneName = findViewById(R.id.beneName);
        TextView beneMobile = findViewById(R.id.beneMobile);
        beneName.setText(getIntent().getStringExtra("BENE_NAME") + "");
        beneMobile.setText(getIntent().getStringExtra("BENE_MOBILE") + "");

        uid = getIntent().getStringExtra("UID");
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    void hitApi() {

        if (isFromAdditionalService) {
            ApiFintechUtilMethods.INSTANCE.GetAdditionalService(this,
                    getIntent().getIntExtra("OPTypeId", 0), loader,
                    mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            GetAvailablePackageResponse mGetAvailablePackageResponse = (GetAvailablePackageResponse) object;
                            mPackageDetails = mGetAvailablePackageResponse.getPackageDetail();
                            mUpgradePackageAdapter = new UpgradePackageAdapter(UpgradePackageActivity.this, isFromAdditionalService, mPackageDetails, customAlertDialog);
                            mRecyclerView.setAdapter(mUpgradePackageAdapter);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                            noDataView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(int error) {
                            setInfoHideShow(error);
                        }
                    });
        } else {
            ApiFintechUtilMethods.INSTANCE.GetAvailablePackage(this, loader, mLoginDataResponse, deviceId, deviceSerialNum,
                    mAppPreferences, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            GetAvailablePackageResponse mGetAvailablePackageResponse = (GetAvailablePackageResponse) object;
                            mPackageDetails = mGetAvailablePackageResponse.getPackageDetail();
                            mUpgradePackageAdapter = new UpgradePackageAdapter(UpgradePackageActivity.this, isFromAdditionalService, mPackageDetails, customAlertDialog);
                            mRecyclerView.setAdapter(mUpgradePackageAdapter);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                            noDataView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(int error) {
                            setInfoHideShow(error);
                        }
                    });
        }
    }

    void setInfoHideShow(int errorType) {
        mRecyclerView.setVisibility(View.GONE);

        if (errorType == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        } else {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
        }
    }

    public void upgradePackage(PackageDetails operator, final int pos) {
        loader.show();
        ApiFintechUtilMethods.INSTANCE.UpgradePackage(this, uid, operator.getPackageId(), loader, mLoginDataResponse,
                deviceId, deviceSerialNum, mAppPreferences, object -> {
                    ApiFintechUtilMethods.INSTANCE.isReadyToUpdateBalance=true;
                    mPackageDetails.get(pos).setDefault(true);
                    mUpgradePackageAdapter.notifyDataSetChanged();
                    setResult(RESULT_OK);
                });
    }

    public void upgradeService(PackageDetails operator, final int pos) {
        loader.show();
        ApiFintechUtilMethods.INSTANCE.UpgradeService(this, operator.getOid(), operator.getOpTypeID(), loader,
                mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, object -> {
                    mPackageDetails.get(pos).setActive(true);
                    mUpgradePackageAdapter.notifyDataSetChanged();
                    setResult(RESULT_OK);
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
