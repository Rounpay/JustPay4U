package com.solution.app.justpay4u.Fintech.Recharge.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.DthPlanChannels;
import com.solution.app.justpay4u.Api.Fintech.Object.PlanInfoPlan;
import com.solution.app.justpay4u.Api.Fintech.Object.PlanValidity;
import com.solution.app.justpay4u.Api.Fintech.Request.DTHChannelPlanInfoRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.DthPlanChannelAllResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DthPlanInfoResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.PlanRPResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.DthSubscription.Adapter.DthChannelAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Adapter.DthPlanChannelListAdapter;
import com.solution.app.justpay4u.Fintech.Recharge.Adapter.DthPlanValidityGridAdapter;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.RecyclerViewStickyHeader.HeaderItemDecoration;
import com.solution.app.justpay4u.Util.Utility;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class DthPlanChannelListActivity extends AppCompatActivity {
    public TextView validity;
    public TextView amount, planName, channel, language;
    public TextView description;
    CustomLoader loader;
    View languageView, amountView, validityView;
    RecyclerView recycler_view, rsGrid;
    View noDataView, noNetworkView, retryBtn, channelCountView, topView, descriptionLayout;
    TextView errorMsg;
    int packageId;
    private EditText searchView;
    private DthPlanChannelListAdapter mAdapter;
    private AppPreferences mAppPreferences;
    private LoginResponse mLoginDataResponse;
    private String deviceId, deviceSerialNum;
    private RequestOptions requestOptionsAdapter;
    private PlanRPResponse mPlanRPResponse;
    private PlanInfoPlan mPlanInfoPlan;
    private boolean isPlanServiceUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_dth_plan_channel_list);

            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);


            requestOptionsAdapter = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_AppLogo();
            isPlanServiceUpdated = getIntent().getBooleanExtra("IsPlanServiceUpdated", false);
            mPlanRPResponse = getIntent().getParcelableExtra("Data");
            mPlanInfoPlan = getIntent().getParcelableExtra("DataAll");


            findViews();

            findViewById(R.id.clearIcon).setOnClickListener(v -> searchView.setText(""));

            retryBtn.setOnClickListener(v -> GetDthChannel());

            searchView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (mAdapter != null) {
                        mAdapter.filter(s.toString());
                    }
                }
            });
            setUiData();
            GetDthChannel();
        });

    }

    void findViews() {
        Toolbar toolBar = findViewById(R.id.toolbar);
        toolBar.setTitle("DTH Channel");
        toolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());
        searchView = findViewById(R.id.search_all);
        topView = findViewById(R.id.topView);
        planName = findViewById(R.id.planName);
        amount = findViewById(R.id.amount);
        language = findViewById(R.id.language);
        validityView = findViewById(R.id.validityView);
        languageView = findViewById(R.id.languageView);
        amountView = findViewById(R.id.amountView);
        channel = findViewById(R.id.channel);
        description = findViewById(R.id.description);
        channelCountView = findViewById(R.id.channelCountView);
        validity = findViewById(R.id.validity);
        descriptionLayout = findViewById(R.id.descriptionLayout);
        rsGrid = findViewById(R.id.rsGrid);
        recycler_view = findViewById(R.id.recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recycler_view.setLayoutManager(gridLayoutManager);

        rsGrid.setLayoutManager(new GridLayoutManager(this, 5));


        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Channel Not Available");

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {


                if (mAdapter != null && mAdapter.getItemViewType(position) == DthChannelAdapter.Header) {
                    return 3;
                }

                return 1;

            }
        });
    }


    void setUiData() {
        if (mPlanRPResponse != null) {
            topView.setVisibility(View.VISIBLE);
            packageId = mPlanRPResponse.getPackageId();
            planName.setVisibility(View.GONE);
            description.setText(mPlanRPResponse.getDetails() + "");
            channel.setText(mPlanRPResponse.getChannelcount() + "");
            validity.setText(mPlanRPResponse.getRechargeValidity() + "");
            amount.setText(Utility.INSTANCE.formatedAmountWithRupees(mPlanRPResponse.getRechargeAmount() + ""));

            if (mPlanRPResponse.getPackagelanguage() != null && !mPlanRPResponse.getPackagelanguage().isEmpty()) {
                languageView.setVisibility(View.VISIBLE);
                language.setText(mPlanRPResponse.getPackagelanguage() + "");
            } else {
                languageView.setVisibility(View.GONE);
            }
        } else if (mPlanInfoPlan != null) {
            topView.setVisibility(View.VISIBLE);
            packageId = mPlanInfoPlan.getPackageId();

            if (mPlanInfoPlan.getPlanName() != null && !mPlanInfoPlan.getPlanName().isEmpty()) {
                planName.setVisibility(View.VISIBLE);
                planName.setText(mPlanInfoPlan.getPlanName() + "");
            } else {
                planName.setVisibility(View.GONE);
            }
            if (mPlanInfoPlan.getDesc() != null && !mPlanInfoPlan.getDesc().isEmpty()) {
                descriptionLayout.setVisibility(View.VISIBLE);
                description.setText(mPlanInfoPlan.getDesc() + "");
            } else {
                descriptionLayout.setVisibility(View.GONE);
            }
            if (mPlanInfoPlan.getpChannelCount() != 0) {
                channelCountView.setVisibility(View.VISIBLE);
                channel.setText(mPlanInfoPlan.getpChannelCount() + "");
            } else {
                channelCountView.setVisibility(View.GONE);
            }


            if (mPlanInfoPlan.getpLangauge() != null && !mPlanInfoPlan.getpLangauge().isEmpty()) {
                languageView.setVisibility(View.VISIBLE);
                language.setText(mPlanInfoPlan.getpLangauge() + "");
            } else {
                languageView.setVisibility(View.GONE);
            }

            List<PlanValidity> mPlanValidities = new ArrayList<>();

            if (mPlanInfoPlan.getRs() != null) {
                if (mPlanInfoPlan.getRs().get1MONTHS() != null && !mPlanInfoPlan.getRs().get1MONTHS().isEmpty()) {
                    mPlanValidities.add(new PlanValidity(mPlanInfoPlan.getRs().get1MONTHS(), "1 Month",
                            mPlanInfoPlan.getDesc() != null && !mPlanInfoPlan.getDesc().isEmpty() ? mPlanInfoPlan.getDesc() : mPlanInfoPlan.getPlanName() + ""));
                }
                if (mPlanInfoPlan.getRs().get3MONTHS() != null && !mPlanInfoPlan.getRs().get3MONTHS().isEmpty()) {
                    mPlanValidities.add(new PlanValidity(mPlanInfoPlan.getRs().get3MONTHS(), "3 Months",
                            mPlanInfoPlan.getDesc() != null && !mPlanInfoPlan.getDesc().isEmpty() ? mPlanInfoPlan.getDesc() : mPlanInfoPlan.getPlanName() + ""));
                }
                if (mPlanInfoPlan.getRs().get6MONTHS() != null && !mPlanInfoPlan.getRs().get6MONTHS().isEmpty()) {
                    mPlanValidities.add(new PlanValidity(mPlanInfoPlan.getRs().get6MONTHS(), "6 Months",
                            mPlanInfoPlan.getDesc() != null && !mPlanInfoPlan.getDesc().isEmpty() ? mPlanInfoPlan.getDesc() : mPlanInfoPlan.getPlanName() + ""));
                }
                if (mPlanInfoPlan.getRs().get9MONTHS() != null && !mPlanInfoPlan.getRs().get9MONTHS().isEmpty()) {
                    mPlanValidities.add(new PlanValidity(mPlanInfoPlan.getRs().get9MONTHS(), "9 Months",
                            mPlanInfoPlan.getDesc() != null && !mPlanInfoPlan.getDesc().isEmpty() ? mPlanInfoPlan.getDesc() : mPlanInfoPlan.getPlanName() + ""));
                }
                if (mPlanInfoPlan.getRs().get1YEAR() != null && !mPlanInfoPlan.getRs().get1YEAR().isEmpty()) {
                    mPlanValidities.add(new PlanValidity(mPlanInfoPlan.getRs().get1YEAR(), "1 Year",
                            mPlanInfoPlan.getDesc() != null && !mPlanInfoPlan.getDesc().isEmpty() ? mPlanInfoPlan.getDesc() : mPlanInfoPlan.getPlanName() + ""));
                }
                if (mPlanInfoPlan.getRs().get5YEAR() != null && !mPlanInfoPlan.getRs().get5YEAR().isEmpty()) {
                    mPlanValidities.add(new PlanValidity(mPlanInfoPlan.getRs().get5YEAR(), "5 Years",
                            mPlanInfoPlan.getDesc() != null && !mPlanInfoPlan.getDesc().isEmpty() ? mPlanInfoPlan.getDesc() : mPlanInfoPlan.getPlanName() + ""));
                }
            }

            if (mPlanValidities != null && mPlanValidities.size() > 0) {
                if (mPlanValidities.size() > 1) {
                    amountView.setVisibility(View.GONE);
                    validityView.setVisibility(View.GONE);
                    rsGrid.setVisibility(View.VISIBLE);
                    if (mPlanValidities.size() <= 5) {
                        rsGrid.setLayoutManager(new GridLayoutManager(this, mPlanValidities.size()));
                    }
                    DthPlanValidityGridAdapter mDthPlanValidityGridAdapter = new DthPlanValidityGridAdapter(R.layout.adapter_plan_validity_single, this, mPlanValidities);
                    rsGrid.setAdapter(mDthPlanValidityGridAdapter);
                } else {
                    amount.setText(Utility.INSTANCE.formatedAmountWithRupees(mPlanValidities.get(0).getAmount() + ""));
                    validity.setText(mPlanValidities.get(0).getValidity() + "");
                    amountView.setVisibility(View.VISIBLE);
                    validityView.setVisibility(View.VISIBLE);
                    rsGrid.setVisibility(View.GONE);
                }
            } else {
                amountView.setVisibility(View.GONE);
                validityView.setVisibility(View.GONE);
                rsGrid.setVisibility(View.GONE);
            }
        } else {
            topView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
        }
    }

    public void GetDthChannel() {
        try {
            if (!loader.isShowing()) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            DTHChannelPlanInfoRequest mDthChannelPlanInfoRequest = new DTHChannelPlanInfoRequest(packageId + "",
                    getIntent().getStringExtra("OperatorId"),
                    mLoginDataResponse.getData().getUserID() + "", mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession());

            if (isPlanServiceUpdated) {
                Call<DthPlanChannelAllResponse> call = git.GetDTHChannelList(mDthChannelPlanInfoRequest);
                call.enqueue(new Callback<DthPlanChannelAllResponse>() {

                    @Override
                    public void onResponse(Call<DthPlanChannelAllResponse> call, retrofit2.Response<DthPlanChannelAllResponse> response) {
                        try {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            if (response.isSuccessful()) {
                                if (response.body() != null && response.body().getStatuscode() == 1) {
                                    if (response.body().getData() != null &&
                                            response.body().getData().size() > 0) {
                                        noDataView.setVisibility(View.GONE);
                                        noNetworkView.setVisibility(View.GONE);
                                        channel.setText(response.body().getData().size() + "");
                                        dataParse(response.body().getData());
                                    } else {
                                        noDataView.setVisibility(View.VISIBLE);
                                        noNetworkView.setVisibility(View.GONE);
                                        ApiFintechUtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, "Channel Not Found.");
                                    }

                                } else {
                                    noDataView.setVisibility(View.VISIBLE);
                                    noNetworkView.setVisibility(View.GONE);
                                    if (response.body() != null && response.body().getMsg() != null) {
                                        ApiFintechUtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, response.body().getMsg() + "");
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, getString(R.string.some_thing_error) + "");
                                    }
                                }
                            } else {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                ApiFintechUtilMethods.INSTANCE.apiErrorHandle(DthPlanChannelListActivity.this, response.code(), response.message());
                            }
                        } catch (Exception e) {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, e.getMessage() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<DthPlanChannelAllResponse> call, Throwable t) {

                        try {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            if (t instanceof UnknownHostException || t instanceof IOException) {
                                noDataView.setVisibility(View.GONE);
                                noNetworkView.setVisibility(View.VISIBLE);
                                ApiFintechUtilMethods.INSTANCE.NetworkError(DthPlanChannelListActivity.this);
                            } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(DthPlanChannelListActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                            } else {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                    ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(DthPlanChannelListActivity.this, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, getString(R.string.some_thing_error));
                                }
                            }
                        } catch (IllegalStateException ise) {
                            noDataView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                            ApiFintechUtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, ise.getMessage() + "");
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                });
            } else {

                Call<DthPlanInfoResponse> call = git.DTHChannelPlanInfo(mDthChannelPlanInfoRequest);
                call.enqueue(new Callback<DthPlanInfoResponse>() {

                    @Override
                    public void onResponse(Call<DthPlanInfoResponse> call, retrofit2.Response<DthPlanInfoResponse> response) {
                        try {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            if (response.isSuccessful()) {
                                if (response.body() != null && response.body().getStatuscode() == 1) {
                                    if (response.body().getDataRPDTHChannelList() != null &&
                                            response.body().getDataRPDTHChannelList().getChannels() != null &&
                                            response.body().getDataRPDTHChannelList().getChannels().size() > 0) {
                                        noDataView.setVisibility(View.GONE);
                                        noNetworkView.setVisibility(View.GONE);
                                        channel.setText(response.body().getDataRPDTHChannelList().getChannels().size() + "");
                                        dataParse(response.body().getDataRPDTHChannelList().getChannels());
                                    } else {
                                        noDataView.setVisibility(View.VISIBLE);
                                        noNetworkView.setVisibility(View.GONE);
                                        ApiFintechUtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, "Channel Not Found.");
                                    }

                                } else {
                                    noDataView.setVisibility(View.VISIBLE);
                                    noNetworkView.setVisibility(View.GONE);
                                    if (response.body() != null && response.body().getMsg() != null) {
                                        ApiFintechUtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, response.body().getMsg() + "");
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, getString(R.string.some_thing_error) + "");
                                    }
                                }
                            } else {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                ApiFintechUtilMethods.INSTANCE.apiErrorHandle(DthPlanChannelListActivity.this, response.code(), response.message());
                            }
                        } catch (Exception e) {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, e.getMessage() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<DthPlanInfoResponse> call, Throwable t) {

                        try {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            if (t instanceof UnknownHostException || t instanceof IOException) {
                                noDataView.setVisibility(View.GONE);
                                noNetworkView.setVisibility(View.VISIBLE);
                                ApiFintechUtilMethods.INSTANCE.NetworkError(DthPlanChannelListActivity.this);
                            } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(DthPlanChannelListActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                            } else {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                    ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(DthPlanChannelListActivity.this, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, getString(R.string.some_thing_error));
                                }
                            }
                        } catch (IllegalStateException ise) {
                            noDataView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                            ApiFintechUtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, ise.getMessage() + "");
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                });
            }


        } catch (
                Exception e) {
            e.printStackTrace();
            loader.dismiss();
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
            ApiFintechUtilMethods.INSTANCE.Error(DthPlanChannelListActivity.this, e.getMessage() + "");
        }

    }


    public void dataParse(ArrayList<DthPlanChannels> channelsArrayList) {


        if (channelsArrayList != null && channelsArrayList.size() > 0) {
            Collections.sort(channelsArrayList, (o1, o2) -> o1.getGenre().compareToIgnoreCase(o2.getGenre()));

            if (channelsArrayList != null && channelsArrayList.size() > 0) {
                String category = "";
                ArrayList<DthPlanChannels> mModifiedList = new ArrayList<>();
                for (DthPlanChannels mItem : channelsArrayList) {
                    if (!category.equalsIgnoreCase(mItem.getGenre())) {
                        mModifiedList.add(new DthPlanChannels(mItem.getGenre(), "", "", ""));
                        category = mItem.getGenre();
                    }
                    mModifiedList.add(mItem);
                }
                mAdapter = new DthPlanChannelListAdapter(mModifiedList, this, requestOptionsAdapter);
                recycler_view.setAdapter(mAdapter);
                new HeaderItemDecoration(mAdapter);
                noDataView.setVisibility(View.GONE);
                noNetworkView.setVisibility(View.GONE);
                recycler_view.setVisibility(View.VISIBLE);


            } else {
                noDataView.setVisibility(View.VISIBLE);
                noNetworkView.setVisibility(View.GONE);
                recycler_view.setVisibility(View.GONE);
            }
        } else {
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
            recycler_view.setVisibility(View.GONE);
        }


    }
}

