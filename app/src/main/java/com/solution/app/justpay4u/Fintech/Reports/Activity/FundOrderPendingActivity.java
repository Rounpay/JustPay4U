package com.solution.app.justpay4u.Fintech.Reports.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.FundRequestForApproval;
import com.solution.app.justpay4u.Api.Fintech.Request.AppUserListRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.FundTransactions.Adapter.FundOrderPendingListAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class FundOrderPendingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView errorMsg;
    List<FundRequestForApproval> mFundRequestForApprovals = new ArrayList<>();
    CustomLoader loader;
    FundOrderPendingListAdapter mFundOrderPendingListAdapter;
    EditText searchEt;
    ImageView clearIcon;
    View noDataView, noNetworkView, retryBtn;
    LoginResponse mLoginDataResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_fund_order_pending);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            findViews();

            clearIcon.setOnClickListener(v -> searchEt.setText(""));

            searchEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (mFundOrderPendingListAdapter != null) {
                        mFundOrderPendingListAdapter.getFilter().filter(s);
                    }
                }
            });

            retryBtn.setOnClickListener(v -> fundRequestApi());

            fundRequestApi();
        });

    }

    void findViews() {

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        errorMsg = findViewById(R.id.errorMsg);
        searchEt = findViewById(R.id.search_all);
        clearIcon = findViewById(R.id.clearIcon);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        mFundOrderPendingListAdapter = new FundOrderPendingListAdapter(this, mFundRequestForApprovals, mAppPreferences, mLoginDataResponse);
        recyclerView.setAdapter(mFundOrderPendingListAdapter);
    }


    public void fundRequestApi() {
        try {
            if (!loader.isShowing()) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.FundOrderPending(new AppUserListRequest("", mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME,
                    deviceSerialNum, mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {
//                     Log.e("Payment response", "hello response : " + new Gson().toJson(response.body()).toString());
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            AppUserListResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    mFundRequestForApprovals.clear();
                                    if (data.getFundRequestForApproval() != null && data.getFundRequestForApproval().size() > 0) {
                                        noNetworkView.setVisibility(View.GONE);
                                        noDataView.setVisibility(View.GONE);
                                        mFundRequestForApprovals.addAll(data.getFundRequestForApproval());
                                    } else {
                                        setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                    }
                                    mFundOrderPendingListAdapter.notifyDataSetChanged();

                                } else if (response.body().getStatuscode() == -1) {
                                    setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                    ApiFintechUtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg() + "");
                                } else if (response.body().getStatuscode() == 0) {
                                    setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                    ApiFintechUtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg() + "");
                                } else {
                                    setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                    ApiFintechUtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg() + "");
                                }

                            } else {
                                setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                ApiFintechUtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                            }
                        } else {
                            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(FundOrderPendingActivity.this, response.code(), response.message());
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
                            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK);
                            ApiFintechUtilMethods.INSTANCE.NetworkError(FundOrderPendingActivity.this);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                            ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(FundOrderPendingActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                        } else {
                            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(FundOrderPendingActivity.this, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                        ApiFintechUtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
            ApiFintechUtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
        }

    }


    void setInfoHideShow(int errorType) {
        recyclerView.setVisibility(View.GONE);
        mFundRequestForApprovals.clear();
        mFundOrderPendingListAdapter.notifyDataSetChanged();

        if (errorType == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        } else {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);

            errorMsg.setText("Record not found.");

        }
    }


    public void fundTransferApi(boolean isMarkCredit, String securityKey, int paymentId, int uid, String remark, String amount, final String userName,
                                final AlertDialog alertDialogFundTransfer) {

        ApiFintechUtilMethods.INSTANCE.fundTransferApi(this, isMarkCredit, securityKey, paymentId, false, uid, remark, 1, amount, userName,
                alertDialogFundTransfer, loader, mLoginDataResponse, deviceId, deviceSerialNum, object -> fundRequestApi());


        /*try {
            loader.show();
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.AppFundTransfer(new FundTransferRequest(securityKey, false, uid, remark, 1,
                    paymentId, amount, LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    AppUserListResponse data = response.body();
                    if (data != null) {
                        if (data.getStatuscode() == 1) {
                            fundRequestApi();
                            alertDialogFundTransfer.dismiss();
                            UtilMethods.INSTANCE.Successful(FundOrderPendingActivity.this, data.getMsg().replace("{User}", userName));
                        } else if (response.body().getStatuscode() == -1) {

                            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg().replace("{User}", userName));
                        } else if (response.body().getStatuscode() == 0) {

                            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg().replace("{User}", userName));
                        } else {

                            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg().replace("{User}", userName));
                        }

                    } else {

                        UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {


                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                UtilMethods.INSTANCE.NetworkError(FundOrderPendingActivity.this);


                            } else {

                                UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, t.getMessage());


                            }

                        } else {

                            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();

                        UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            UtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
        }*/

    }


    public void rejectApi(boolean isMarkCredit, String securityKey, int paymentId, int uid, String remark, String amount, final String userName,
                          final AlertDialog alertDialogFundTransfer) {
        ApiFintechUtilMethods.INSTANCE.fundRejectApi(this, isMarkCredit, securityKey, paymentId, uid, remark, amount, userName,
                alertDialogFundTransfer, loader, mLoginDataResponse, deviceId, deviceSerialNum, object -> fundRequestApi());


        /*try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.AppFundReject(new FundTransferRequest(securityKey, false, uid, remark, 1,
                    paymentId, amount, mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME,
                    deviceSerialNum, mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if(response.isSuccessful()) {
                            AppUserListResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    fundRequestApi();
                                    alertDialogFundTransfer.dismiss();
                                    ApiUtilMethods.INSTANCE.Successful(FundOrderPendingActivity.this, data.getMsg().replace("{User}", userName));
                                } else if (response.body().getStatuscode() == -1) {

                                    ApiUtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg().replace("{User}", userName));
                                } else if (response.body().getStatuscode() == 0) {
                                    ApiUtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg().replace("{User}", userName));
                                } else {
                                    ApiUtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, data.getMsg().replace("{User}", userName));
                                }

                            } else {
                                ApiUtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                            }
                        }else {
                            ApiUtilMethods.INSTANCE.apiErrorHandle(FundOrderPendingActivity.this,response.code(),response.message());
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


                                ApiUtilMethods.INSTANCE.apiFailureError(FundOrderPendingActivity.this, t);

                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiUtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiUtilMethods.INSTANCE.Error(FundOrderPendingActivity.this, getString(R.string.some_thing_error));
        }*/

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
