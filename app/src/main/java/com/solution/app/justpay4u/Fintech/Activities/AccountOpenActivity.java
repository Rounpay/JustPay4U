package com.solution.app.justpay4u.Fintech.Activities;

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
import com.solution.app.justpay4u.Fintech.Adapter.AccountOpenListAdapter;
import com.solution.app.justpay4u.Api.Fintech.Object.AccountOpData;
import com.solution.app.justpay4u.Api.Fintech.Response.AccountOpenListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;


/**
 * Created by Vishnu on 14-04-2017.
 */


public class AccountOpenActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    AccountOpenListAdapter mAdapter;
    ArrayList<AccountOpData> operator = new ArrayList<>();
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    CustomLoader loader;
    EditText search_all;
    private LoginResponse mLoginDataResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private int serviceId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_account_open);
            setTitle(getIntent().getStringExtra("name"));
            serviceId = getIntent().getIntExtra("SERVICE_ID", 0);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            retryBtn = findViewById(R.id.retryBtn);
            errorMsg = findViewById(R.id.errorMsg);
            errorMsg.setText("Record not found.");
            search_all = findViewById(R.id.search_all);
            findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));
            recycler_view = findViewById(R.id.recycler_view);
            mAdapter = new AccountOpenListAdapter(operator, this);
            recycler_view.setLayoutManager(new LinearLayoutManager(this));
            recycler_view.setAdapter(mAdapter);

            new Handler(Looper.getMainLooper()).post(() -> getOperatorList());


            search_all.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String newText = s.toString().toLowerCase();
                    ArrayList<AccountOpData> newlist = new ArrayList<>();
                    for (AccountOpData op : operator) {

                        if ((op.getName() + "").toLowerCase().contains(newText) || (op.getContent() + "").toLowerCase().contains(newText)) {
                            newlist.add(op);
                        }
                    }
                    mAdapter.filter(newlist);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            retryBtn.setOnClickListener(view -> HitApi());
        });

    }

    public void getOperatorList() {


        AccountOpenListResponse mAccountOpenListResponse = new Gson().fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.accountOpenListPref + "_" + serviceId), AccountOpenListResponse.class);
        if (mAccountOpenListResponse != null && mAccountOpenListResponse.getAccountOpeningDeatils() != null &&
                mAccountOpenListResponse.getAccountOpeningDeatils().size() > 0) {
            operator.clear();
            operator.addAll(mAccountOpenListResponse.getAccountOpeningDeatils());
            if (operator != null && operator.size() > 0) {
                noDataView.setVisibility(View.GONE);
                noNetworkView.setVisibility(View.GONE);
                recycler_view.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();

            } else {
                noDataView.setVisibility(View.VISIBLE);
                noNetworkView.setVisibility(View.GONE);
                recycler_view.setVisibility(View.GONE);
            }
        }
        HitApi();
    }


    void HitApi() {
        ApiFintechUtilMethods.INSTANCE.GetAccountOpenlist(this, serviceId, loader,
                mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, new ApiFintechUtilMethods.ApiResponseCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        AccountOpenListResponse mAccountOpenListResponse = (AccountOpenListResponse) object;
                        if (mAccountOpenListResponse != null && mAccountOpenListResponse.getAccountOpeningDeatils() != null &&
                                mAccountOpenListResponse.getAccountOpeningDeatils().size() > 0) {
                            operator.clear();
                            operator.addAll(mAccountOpenListResponse.getAccountOpeningDeatils());
                            noDataView.setVisibility(View.GONE);
                            noNetworkView.setVisibility(View.GONE);
                            recycler_view.setVisibility(View.VISIBLE);
                            mAdapter.notifyDataSetChanged();


                        } else {
                            if (operator == null || operator.size() == 0) {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                recycler_view.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onError(int error) {
                        if (operator == null || operator.size() == 0) {
                            recycler_view.setVisibility(View.GONE);

                            if (error == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
                                noNetworkView.setVisibility(View.VISIBLE);
                                noDataView.setVisibility(View.GONE);
                            } else {
                                noNetworkView.setVisibility(View.GONE);
                                noDataView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

