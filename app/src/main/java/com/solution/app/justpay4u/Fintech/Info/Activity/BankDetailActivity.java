package com.solution.app.justpay4u.Fintech.Info.Activity;

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
import com.solution.app.justpay4u.Api.Fintech.Object.Bank;
import com.solution.app.justpay4u.Api.Fintech.Request.BalanceRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.GetBankAndPaymentModeResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.Info.Adapter.BankDetailAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;


public class BankDetailActivity extends AppCompatActivity {

    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    ArrayList<Bank> mBankArrayList = new ArrayList<>();
    EditText search_all;
    private RecyclerView mRecyclerView;
    private BankDetailAdapter mBankDetailAdapter;
    private CustomLoader loader;
    private AppPreferences mAppPreferences;
    private LoginResponse mLoginDataResponse;
    private String deviceId, deviceSerialNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_bank_detail);


            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            mRecyclerView = findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mBankDetailAdapter = new BankDetailAdapter(mBankArrayList, BankDetailActivity.this);
            mRecyclerView.setAdapter(mBankDetailAdapter);

            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            retryBtn = findViewById(R.id.retryBtn);
            errorMsg = findViewById(R.id.errorMsg);

            search_all = findViewById(R.id.search_all);
            errorMsg.setText("Bank list not found.");
            findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));

            search_all.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence newText, int start, int before, int count) {
                    if (mBankDetailAdapter != null) {
                        mBankDetailAdapter.filter(newText.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            retryBtn.setOnClickListener(v -> GetBank());
            GetBank();
        });

    }


    public void GetBank() {
        try {
            if (!loader.isShowing()) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            BalanceRequest mBalanceRequest = new BalanceRequest(
                    mLoginDataResponse.getData().getUserID() + "", mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession());
            String str = new Gson().toJson(mBalanceRequest);
            Call<GetBankAndPaymentModeResponse> call = git.GetBank(mBalanceRequest);
            call.enqueue(new Callback<GetBankAndPaymentModeResponse>() {
                @Override
                public void onResponse(Call<GetBankAndPaymentModeResponse> call, final retrofit2.Response<GetBankAndPaymentModeResponse> response) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatuscode() != null) {
                                if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                    if (response.body().getBanks() != null && response.body().getBanks().size() > 0) {
                                        noDataView.setVisibility(View.GONE);
                                        noNetworkView.setVisibility(View.GONE);
                                        mBankArrayList.clear();
                                        mBankArrayList.addAll(response.body().getBanks());
                                        mBankDetailAdapter.notifyDataSetChanged();
                                    } else {
                                        noDataView.setVisibility(View.VISIBLE);
                                        noNetworkView.setVisibility(View.GONE);
                                        ApiFintechUtilMethods.INSTANCE.Error(BankDetailActivity.this, getString(R.string.data_not_found));
                                    }
                                } else {
                                    noDataView.setVisibility(View.VISIBLE);
                                    noNetworkView.setVisibility(View.GONE);
                                    if (response.body().getIsVersionValid() == false) {
                                        ApiFintechUtilMethods.INSTANCE.versionDialog(BankDetailActivity.this);
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(BankDetailActivity.this, response.body().getMsg() + "");
                                    }
                                }
                            } else {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                ApiFintechUtilMethods.INSTANCE.Error(BankDetailActivity.this, getString(R.string.data_not_found));
                            }
                        } else {
                            noDataView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(BankDetailActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        noDataView.setVisibility(View.VISIBLE);
                        noNetworkView.setVisibility(View.GONE);
                        ApiFintechUtilMethods.INSTANCE.Error(BankDetailActivity.this, getString(R.string.data_not_found));
                    }

                }

                @Override
                public void onFailure(Call<GetBankAndPaymentModeResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    try {

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            noDataView.setVisibility(View.GONE);
                            noNetworkView.setVisibility(View.VISIBLE);
                            ApiFintechUtilMethods.INSTANCE.NetworkError(BankDetailActivity.this);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            noDataView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                            ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(BankDetailActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                        } else {
                            noDataView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(BankDetailActivity.this, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(BankDetailActivity.this, getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        noDataView.setVisibility(View.VISIBLE);
                        noNetworkView.setVisibility(View.GONE);
                        ApiFintechUtilMethods.INSTANCE.Error(BankDetailActivity.this, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            e.printStackTrace();
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
