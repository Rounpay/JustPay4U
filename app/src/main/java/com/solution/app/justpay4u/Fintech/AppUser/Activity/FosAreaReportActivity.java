package com.solution.app.justpay4u.Fintech.AppUser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.solution.app.justpay4u.Api.Fintech.Object.AreaMaster;
import com.solution.app.justpay4u.Api.Fintech.Response.AppGetAMResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.AppUser.Adapter.FosReportAreaListScreenAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;

public class FosAreaReportActivity extends AppCompatActivity {
    RecyclerView recycler_view_area;
    FosReportAreaListScreenAdapter fosReportAreaListScreenAdapter;

    EditText search_all;
    ArrayList<AreaMaster> mAreaListObjects = new ArrayList<>();
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    private CustomLoader loader;
    private LoginResponse mLoginDataResponse;
    private boolean isFromFOS;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_fos_area_report);
            isFromFOS = getIntent().getBooleanExtra("ISFromFOS", false);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            retryBtn = findViewById(R.id.retryBtn);
            errorMsg = findViewById(R.id.errorMsg);
            errorMsg.setText("Area not found");
            search_all = findViewById(R.id.search_all);
            findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));
            recycler_view_area = findViewById(R.id.recycler_view_area);
            recycler_view_area.setLayoutManager(new LinearLayoutManager(this));
            fosReportAreaListScreenAdapter = new FosReportAreaListScreenAdapter(mAreaListObjects, FosAreaReportActivity.this);
            recycler_view_area.setAdapter(fosReportAreaListScreenAdapter);
            search_all.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (fosReportAreaListScreenAdapter != null) {
                        fosReportAreaListScreenAdapter.getFilter().filter(s);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            retryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HitApi();
                }
            });
            new Handler(Looper.getMainLooper()).post(() -> {
                AppGetAMResponse appGetAMResponse = new Gson().fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.areaListPref), AppGetAMResponse.class);
                getOperatorList(appGetAMResponse);
            });
        });

    }

    private void getOperatorList(AppGetAMResponse areaListResponse) {

        if (areaListResponse != null && areaListResponse.getAreaMaster() != null && areaListResponse.getAreaMaster().size() > 0) {
            recycler_view_area.setVisibility(View.VISIBLE);
            mAreaListObjects.clear();
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            mAreaListObjects.addAll(areaListResponse.getAreaMaster());
            fosReportAreaListScreenAdapter.notifyDataSetChanged();
        } else {
            HitApi();

        }


    }

    private void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            ApiFintechUtilMethods.INSTANCE.GetArealist(this, loader, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences,
                    new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            AppGetAMResponse mAppGetAMResponse = (AppGetAMResponse) object;
                            if (mAppGetAMResponse != null && mAppGetAMResponse.getAreaMaster() != null && mAppGetAMResponse.getAreaMaster().size() > 0) {
                                getOperatorList(mAppGetAMResponse);
                            } else {
                                setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
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

        if (errorType == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        } else {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
        }
    }

    public void setArea(AreaMaster operator) {
        if (isFromFOS) {
            Intent i = new Intent(this, FOSAccStmtAndCollActivity.class);
            i.putExtra("areaID", operator.getAreaID());
            startActivity(i);
        } else {
            Intent intent = new Intent();
            intent.putExtra("areaID", operator.getAreaID());
            intent.putExtra("area", operator.getArea());
            setResult(RESULT_OK, intent);
            finish();
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
