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

import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceType;
import com.solution.app.justpay4u.Api.Fintech.Object.UserList;
import com.solution.app.justpay4u.Api.Fintech.Request.AppUserListRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetEKYCDetailResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.Fintech.AppUser.Adapter.AppUserListAdapter;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.EKYCStepsDialog;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class FosUserListActivity extends AppCompatActivity {
    RecyclerView recycler_view;

    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    EditText search_all;
    CustomLoader loader;
    AppUserListAdapter mAppUserListAdapter;
    private LoginResponse mLoginDataResponse;
    private ArrayList<UserList> mUserLists = new ArrayList<>();
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private BalanceResponse balanceCheckResponse;
    private int INTENT_COLLECTION = 7272;
    private EKYCStepsDialog mEKYCStepsDialog;
    private boolean isEKYCCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_fos_user_list);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            findViews();
            mAppUserListAdapter = new AppUserListAdapter(this, mUserLists, 0, mLoginDataResponse, mAppPreferences, null,
                    new AppUserListAdapter.FundTransferCallBAck() {
                        @Override
                        public void onSucessFund() {
                            appUserListApi();
                        }

                        @Override
                        public void onSucessEdit() {
                            appUserListApi();
                        }
                    });
            recycler_view.setAdapter(mAppUserListAdapter);

            search_all.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence newText, int start, int before, int count) {
                    if (mAppUserListAdapter != null) {
                        mAppUserListAdapter.getFilter().filter(newText.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            retryBtn.setOnClickListener(v -> appUserListApi());

            new Handler(Looper.getMainLooper()).post(() -> {
                isEKYCCompleted = mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isEKYCComplete);
                mEKYCStepsDialog = new EKYCStepsDialog(this, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);
                balanceCheckResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
                if (balanceCheckResponse != null && balanceCheckResponse.isEKYCForced() && !isEKYCCompleted) {
                    mEKYCStepsDialog.GetKycDetails(new EKYCStepsDialog.ApiCallBackTwoMethod() {
                        @Override
                        public void onSucess(GetEKYCDetailResponse object) {

                            isEKYCCompleted = object.getData().isIsEKYCDone();
                        }

                        @Override
                        public void onError(Object object) {

                        }
                    });

                }
                if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                        && balanceCheckResponse.getBalanceData().size() > 0) {
                    appUserListApi();
                } else {
                    ApiFintechUtilMethods.INSTANCE.Balancecheck(this, loader, mLoginDataResponse, deviceId, deviceSerialNum,
                            mAppPreferences, mEKYCStepsDialog, object -> {
                                balanceCheckResponse = (BalanceResponse) object;
                                if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                        && balanceCheckResponse.getBalanceData().size() > 0) {
                                    appUserListApi();
                                }
                            });
                }
            });
        });


    }


    void findViews() {
        setTitle(getIntent().getStringExtra("Title") + "");
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        search_all = findViewById(R.id.search_all);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("FOS User List not available.");
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));
    }

    public void appUserListApi() {
        try {
            if (!loader.isShowing()) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.FOSRetailerList(new AppUserListRequest(null, mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            AppUserListResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {

                                    if (data.getFosList() != null && data.getFosList().getUserReports() != null &&
                                            data.getFosList().getUserReports().size() > 0) {

                                        noDataView.setVisibility(View.GONE);
                                        noNetworkView.setVisibility(View.GONE);
                                        ArrayList<UserList> mResponseUserLists = data.getFosList().getUserReports();
                                        int gridSize = 3;
                                        for (int i = 0; i < mResponseUserLists.size(); i++) {

                                            ArrayList<BalanceType> mBalanceTypes = new ArrayList<>();
                                            for (BalanceData item : balanceCheckResponse.getBalanceData()) {
                                                if (item.getId() == 1) {
                                                    mBalanceTypes.add(new BalanceType(item.getWalletType() + " Balance", mResponseUserLists.get(i).getBalance()));
                                                }
                                                if (item.getId() == 2) {
                                                    mBalanceTypes.add(new BalanceType(item.getWalletType() + " Balance", mResponseUserLists.get(i).getuBalance()));
                                                }
                                                if (item.getId() == 3) {
                                                    mBalanceTypes.add(new BalanceType(item.getWalletType() + " Balance", mResponseUserLists.get(i).getbBalance()));
                                                }
                                                if (item.getId() == 4) {
                                                    mBalanceTypes.add(new BalanceType(item.getWalletType() + " Balance", mResponseUserLists.get(i).getcBalance()));
                                                }
                                                if (item.getId() == 5) {
                                                    mBalanceTypes.add(new BalanceType(item.getWalletType() + " Balance", mResponseUserLists.get(i).getIdBalnace()));
                                                }
                                                if (item.getId() == 6&&(mResponseUserLists.get(i).getRoleID() != 3 ||
                                                        !balanceCheckResponse.isPackageDeducionForRetailor() &&
                                                                mResponseUserLists.get(i).getRoleID() == 3)) {
                                                    mBalanceTypes.add(new BalanceType(item.getWalletType() + " Balance", mResponseUserLists.get(i).getPacakgeBalance()));
                                                }
                                            }

                                            if (mLoginDataResponse.isAccountStatement()) {
                                                mBalanceTypes.add(new BalanceType("Outstanding Balance", mResponseUserLists.get(i).getOsBalance()));
                                            }
                                            if (i == 0) {
                                                gridSize = mBalanceTypes.size();
                                            }
                                            mResponseUserLists.get(i).setBalanceTypes(mBalanceTypes);
                                        }
                                        mUserLists.clear();
                                        mUserLists.addAll(mResponseUserLists);
                                        mAppUserListAdapter.setGridSize(gridSize);
                                        mAppUserListAdapter.notifyDataSetChanged();

                                    } else {
                                        noDataView.setVisibility(View.VISIBLE);
                                        noNetworkView.setVisibility(View.GONE);
                                    }


                                } else {
                                    noDataView.setVisibility(View.VISIBLE);
                                    noNetworkView.setVisibility(View.GONE);
                                    ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.AppUser.Activity.FosUserListActivity.this, data.getMsg() + "");
                                }

                            } else {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.AppUser.Activity.FosUserListActivity.this, getString(R.string.some_thing_error));
                            }
                        } else {
                            noDataView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(com.solution.app.justpay4u.Fintech.AppUser.Activity.FosUserListActivity.this, response.code(), response.message());
                        }
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t instanceof UnknownHostException || t instanceof IOException) {
                                noDataView.setVisibility(View.GONE);
                                noNetworkView.setVisibility(View.VISIBLE);
                                ApiFintechUtilMethods.INSTANCE.NetworkError(com.solution.app.justpay4u.Fintech.AppUser.Activity.FosUserListActivity.this);
                            } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(com.solution.app.justpay4u.Fintech.AppUser.Activity.FosUserListActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                            } else {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                    ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(com.solution.app.justpay4u.Fintech.AppUser.Activity.FosUserListActivity.this, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.AppUser.Activity.FosUserListActivity.this, getString(R.string.some_thing_error));
                                }
                            }
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();
                        noDataView.setVisibility(View.VISIBLE);
                        noNetworkView.setVisibility(View.GONE);
                        ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.AppUser.Activity.FosUserListActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.AppUser.Activity.FosUserListActivity.this, getString(R.string.some_thing_error));
        }

    }


    public void CollectionFromFosActivity(UserList mItem) {
        Intent intent = new Intent(this, FosCollectionActivity.class);
        intent.putExtra("id", mItem.getId());
        intent.putExtra("mobile", mItem.getMobileNo());
        intent.putExtra("outletName", mItem.getOutletName());
        startActivityForResult(intent, INTENT_COLLECTION);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_COLLECTION && resultCode == RESULT_OK) {
            appUserListApi();
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



