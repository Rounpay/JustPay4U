package com.solution.app.justpay4u.Fintech.AEPS.Activity;

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
import com.solution.app.justpay4u.Api.Fintech.Object.BankListObject;
import com.solution.app.justpay4u.Api.Fintech.Response.BankListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.FundTransactions.Adapter.BankListScreenAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;


/**
 * Created by Vishnu on 14-04-2017.
 */


public class AEPSBankListActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    View noDataView, noNetworkView;
    BankListScreenAdapter mAdapter;
    ArrayList<BankListObject> mBankListObjects = new ArrayList<>();
    ArrayList<BankListObject> mSearchBankListObjects = new ArrayList<>();
    /*ArrayList<BankListObject> mTopBankListObjects = new ArrayList<>();*/
    EditText search_all;
    private CustomLoader loader;
    private LoginResponse mLoginDataResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_aeps_bank_list_screen);

            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            search_all = findViewById(R.id.search_all);
            findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));


            recycler_view = findViewById(R.id.recycler_view);
            recycler_view.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new BankListScreenAdapter(mSearchBankListObjects, AEPSBankListActivity.this);
            recycler_view.setAdapter(mAdapter);

            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            TextView errorMsg = findViewById(R.id.errorMsg);
            errorMsg.setText("Bank List not available");


            BankListResponse bankListResponse = getIntent().getParcelableExtra("BankList");
            getOperatorList(bankListResponse);


            search_all.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mSearchBankListObjects.clear();
                    String newText = s.toString().trim().toLowerCase();
                    if (newText.length() > 0) {
                        for (BankListObject op : mBankListObjects) {
                            if (op.getBankName().toLowerCase().contains(newText)) {
                                mSearchBankListObjects.add(op);
                            }
                        }
                    } else {
                        mSearchBankListObjects.addAll(mBankListObjects);
                    }
                    if (mAdapter != null) {
                        mAdapter.filter(mSearchBankListObjects);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            findViewById(R.id.retryBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HitApi();
                }
            });
        });

    }

    public void getOperatorList(BankListResponse bankListResponse) {


        if (bankListResponse != null && bankListResponse.getBankMasters() != null && bankListResponse.getBankMasters().size() > 0) {
            if (loader.isShowing()) {
                loader.dismiss();
            }
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            mBankListObjects.clear();
            /* mTopBankListObjects.clear();*/
            mSearchBankListObjects.clear();
            mBankListObjects.addAll(bankListResponse.getBankMasters());

            /*for (BankListObject mBankListObject : bankListResponse.getBankMasters()) {
                if (mBankListObject.getBankType().equalsIgnoreCase("Private") ||
                        mBankListObject.getBankType().equalsIgnoreCase("PSB")) {
                    mTopBankListObjects.add(mBankListObject);
                }
            }*/
            /* mSearchBankListObjects.addAll(mTopBankListObjects);*/
            mSearchBankListObjects.addAll(mBankListObjects);
            mAdapter.notifyDataSetChanged();
        } else {
            BankListResponse mBankListResponse = new Gson().fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.bankListPref),
                    BankListResponse.class);
            if (mBankListResponse != null && mBankListResponse.getBankMasters() != null && mBankListResponse.getBankMasters().size() > 0) {
                getOperatorList(mBankListResponse);
            } else {
                HitApi();
            }
        }

    }

    private void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            ApiFintechUtilMethods.INSTANCE.GetAEPSBanklist(this, loader, mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum,
                    new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            getOperatorList((BankListResponse) object);
                        }

                        @Override
                        public void onError(int error) {
                            if (error == ApiFintechUtilMethods.INSTANCE.ERROR_OTHER) {
                                noNetworkView.setVisibility(View.GONE);
                                noDataView.setVisibility(View.VISIBLE);
                            } else {
                                noNetworkView.setVisibility(View.VISIBLE);
                                noDataView.setVisibility(View.GONE);
                            }
                        }
                    });

        } else {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    public void ItemClick(BankListObject operator) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("Bank", operator);
        setResult(RESULT_OK, clickIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

