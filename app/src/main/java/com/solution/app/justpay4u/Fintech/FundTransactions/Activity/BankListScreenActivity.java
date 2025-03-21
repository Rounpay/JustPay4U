package com.solution.app.justpay4u.Fintech.FundTransactions.Activity;

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
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;


/**
 * Created by Vishnu on 14-04-2017.
 */


public class BankListScreenActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    View noDataView, noNetworkView;
    BankListScreenAdapter mAdapter;
    ArrayList<BankListObject> mBankListObjects = new ArrayList<>();
    ArrayList<BankListObject> mSearchBankListObjects = new ArrayList<>();
    ArrayList<BankListObject> mTopBankListObjects = new ArrayList<>();
    EditText search_all;
    private CustomLoader loader;
    private LoginResponse mLoginDataResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.myLooper()).post(() -> {
            setContentView(R.layout.activity_bank_list_screen);

            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            search_all = findViewById(R.id.search_all);
            findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));


            recycler_view = findViewById(R.id.recycler_view);
            recycler_view.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new BankListScreenAdapter(mSearchBankListObjects, BankListScreenActivity.this);
            recycler_view.setAdapter(mAdapter);

            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            TextView errorMsg = findViewById(R.id.errorMsg);
            errorMsg.setText("Bank List not available");
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
                        if (mTopBankListObjects != null && mTopBankListObjects.size() > 0) {
                            mSearchBankListObjects.addAll(mTopBankListObjects);
                        } else {
                            mSearchBankListObjects.addAll(mBankListObjects.size() > 50 ? mBankListObjects.subList(0, 50) : mBankListObjects);
                        }
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

            new Handler(Looper.myLooper()).post(() -> {
            getOperatorList(new Gson().fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.bankListPref), BankListResponse.class));

        });
        });

    }

    public void getOperatorList(BankListResponse bankListResponse) {


        if (bankListResponse != null && bankListResponse.getBankMasters() != null && bankListResponse.getBankMasters().size() > 0) {
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            mBankListObjects.clear();
            mTopBankListObjects.clear();
            mSearchBankListObjects.clear();
            mBankListObjects.addAll(bankListResponse.getBankMasters());

            for (BankListObject mBankListObject : mBankListObjects) {
                if (mBankListObject.getBankType() != null && (mBankListObject.getBankType().equalsIgnoreCase("Private") ||
                        mBankListObject.getBankType().equalsIgnoreCase("PSB"))) {
                    mTopBankListObjects.add(mBankListObject);
                }
            }
            if (mTopBankListObjects != null && mTopBankListObjects.size() > 0) {
                mSearchBankListObjects.addAll(mTopBankListObjects);
            } else {
                mSearchBankListObjects.addAll(mBankListObjects.size() > 50 ? mBankListObjects.subList(0, 50) : mBankListObjects);
            }
            mAdapter.notifyDataSetChanged();
        } else {

            HitApi();
        }

    }

    private void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            ApiFintechUtilMethods.INSTANCE.GetBanklist(this, loader, mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum,
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
        if (Utility.INSTANCE.mBankClickCallBack != null) {
            Utility.INSTANCE.mBankClickCallBack.Bank(operator);
        }
        Intent clickIntent = new Intent();
        clickIntent.putExtra("bankName", operator.getBankName());
        clickIntent.putExtra("bankId", operator.getId() + "");
        clickIntent.putExtra("accVerification", operator.getIsACVerification());
        clickIntent.putExtra("shortCode", operator.getCode());
        clickIntent.putExtra("ifsc", operator.getIfsc());
        clickIntent.putExtra("neft", operator.getIsNEFT());
        clickIntent.putExtra("imps", operator.getIsIMPS());
        clickIntent.putExtra("accLmt", operator.getAccountLimit());
        clickIntent.putExtra("ekO_BankID", operator.getEkO_BankID());
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

