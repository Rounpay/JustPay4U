package com.solution.app.justpay4u.Fintech.AppUser.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.solution.app.justpay4u.Api.Fintech.Object.ChildRoles;
import com.solution.app.justpay4u.Api.Fintech.Request.AppUserListRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.Fintech.AppUser.Adapter.FragmentViewPagerAdapter;
import com.solution.app.justpay4u.Fintech.AppUser.Fragment.AppUserListFragment;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.EKYCStepsDialog;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class AppUserListActivity extends AppCompatActivity {

    public LoginResponse mLoginDataResponse;
    public AppPreferences mAppPreferences;
    public String deviceId, deviceSerialNum;
    public BalanceResponse balanceCheckResponse;
    public EKYCStepsDialog mEKYCStepsDialog;
    public boolean isEKYCCompleted;
    TabLayout sliding_tabs;
    ViewPager viewPager;
    FragmentViewPagerAdapter mFragmentViewPagerAdapter;
    List<ChildRoles> mchildRolesList = new ArrayList<>();
    CustomLoader loader;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_app_user_list);

            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(getIntent().getStringExtra("Title"));
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            balanceCheckResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
            isEKYCCompleted = mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isEKYCComplete);

            mEKYCStepsDialog = new EKYCStepsDialog(this, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);


            errorMsg = findViewById(R.id.errorMsg);
            sliding_tabs = findViewById(R.id.sliding_tabs);
            viewPager = findViewById(R.id.viewpager);
            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            retryBtn = findViewById(R.id.retryBtn);
            errorMsg = findViewById(R.id.errorMsg);
            errorMsg.setText("You don't have any user.");
            mFragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager());
            if (balanceCheckResponse == null || balanceCheckResponse.getBalanceData() == null) {
                ApiFintechUtilMethods.INSTANCE.Balancecheck(this, loader, mLoginDataResponse, deviceId, deviceSerialNum,
                        mAppPreferences, mEKYCStepsDialog, object -> {
                            balanceCheckResponse = (BalanceResponse) object;

                        });
            }
            appUserChildRolesApi();
            retryBtn.setOnClickListener(v -> appUserChildRolesApi());
        });

    }

    private void setDataInUI() {

        mFragmentViewPagerAdapter.removeFragment();

        for (int i = 0; i < mchildRolesList.size(); i++) {

            AppUserListFragment mAppUserListFragment = new AppUserListFragment();
            Bundle arg = new Bundle();
            arg.putString("Id", mchildRolesList.get(i).getId() + "");
            arg.putString("Role", mchildRolesList.get(i).getRole());
            arg.putInt("Ind", mchildRolesList.get(i).getInd());

            mAppUserListFragment.setArguments(arg);

            mFragmentViewPagerAdapter.addFragment(mAppUserListFragment, mchildRolesList.get(i).getRole());

        }
        viewPager.setAdapter(mFragmentViewPagerAdapter);
        viewPager.setOffscreenPageLimit(mchildRolesList.size());
        sliding_tabs.setVisibility(View.VISIBLE);
        sliding_tabs.setupWithViewPager(viewPager);

    }


    public void appUserChildRolesApi() {
        try {
            if (!loader.isShowing()) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.AppUserChildRoles(new AppUserListRequest("", mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {
                    try {

                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            AppUserListResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    if (data.getChildRoles() != null && data.getChildRoles().size() > 0) {
                                        noDataView.setVisibility(View.GONE);
                                        noNetworkView.setVisibility(View.GONE);
                                        mchildRolesList.clear();
                                        mchildRolesList.addAll(data.getChildRoles());
                                        setDataInUI();
                                    } else {
                                        noDataView.setVisibility(View.VISIBLE);
                                        noNetworkView.setVisibility(View.GONE);
                                    }


                                } else {
                                    noDataView.setVisibility(View.VISIBLE);
                                    noNetworkView.setVisibility(View.GONE);
                                    ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.AppUser.Activity.AppUserListActivity.this, data.getMsg() + "");
                                }

                            } else {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.AppUser.Activity.AppUserListActivity.this, getString(R.string.some_thing_error));
                            }
                        } else {
                            noDataView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(com.solution.app.justpay4u.Fintech.AppUser.Activity.AppUserListActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            noDataView.setVisibility(View.GONE);
                            noNetworkView.setVisibility(View.VISIBLE);
                            ApiFintechUtilMethods.INSTANCE.NetworkError(com.solution.app.justpay4u.Fintech.AppUser.Activity.AppUserListActivity.this);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            noDataView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                            ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(com.solution.app.justpay4u.Fintech.AppUser.Activity.AppUserListActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                        } else {
                            noDataView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(com.solution.app.justpay4u.Fintech.AppUser.Activity.AppUserListActivity.this, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.AppUser.Activity.AppUserListActivity.this, getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        noDataView.setVisibility(View.VISIBLE);
                        noNetworkView.setVisibility(View.GONE);
                        ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.AppUser.Activity.AppUserListActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            errorMsg.setText(getString(R.string.some_thing_error));
            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.AppUser.Activity.AppUserListActivity.this, getString(R.string.some_thing_error));
        }
    }


}
