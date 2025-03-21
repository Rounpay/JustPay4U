package com.solution.app.justpay4u.Fintech.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Adapter.AchievedTargetAdapter;
import com.solution.app.justpay4u.Api.Fintech.Object.TargetAchieved;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.List;


public class AchievedTargetActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    CustomLoader loader;
    private LoginResponse mLoginDataResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieved_target);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {


            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            retryBtn = findViewById(R.id.retryBtn);
            errorMsg = findViewById(R.id.errorMsg);
            errorMsg.setText("Record not found.");
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            hitApi();
        });
    }


    void hitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            ApiFintechUtilMethods.INSTANCE.UserAchieveTarget(this, false, loader, mLoginDataResponse, mAppPreferences,
                    deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            AppUserListResponse mAppUserListResponse = (AppUserListResponse) object;
                            if (mAppUserListResponse != null) {
                                List<TargetAchieved> transactionsObjects = mAppUserListResponse.getTargetAchieveds();
                                if (transactionsObjects != null && transactionsObjects.size() > 0) {
                                    AchievedTargetAdapter mAdapter = new AchievedTargetAdapter(transactionsObjects, AchievedTargetActivity.this);
                                    recyclerView.setAdapter(mAdapter);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    noNetworkView.setVisibility(View.GONE);
                                    noDataView.setVisibility(View.GONE);

                                } else {
                                    setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                    ApiFintechUtilMethods.INSTANCE.Error(AchievedTargetActivity.this, "Data not found.");
                                }
                            }
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
        recyclerView.setVisibility(View.GONE);

        if (errorType == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        } else {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
