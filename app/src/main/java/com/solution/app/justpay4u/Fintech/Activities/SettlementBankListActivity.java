package com.solution.app.justpay4u.Fintech.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Adapter.SettlementAccountAdapter;
import com.solution.app.justpay4u.Api.Fintech.Object.SettlementAccountData;
import com.solution.app.justpay4u.Api.Fintech.Request.UpdateSettlementAccountRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.SettlementAccountResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class SettlementBankListActivity extends AppCompatActivity {

    public View noDataView;
    public boolean isSattlemntAccountVerify;
    RecyclerView recycler_view;
    View noNetworkView;
    View addBtn;
    SettlementAccountAdapter mAdapter;
    ArrayList<SettlementAccountData> mBankListObjects = new ArrayList<>();
    /*ArrayList<BankListObject> mTopBankListObjects = new ArrayList<>();*/
    EditText search_all;

    private CustomLoader loader;
    private LoginResponse mLoginDataResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private Dialog dialog;
    private int INTENT_UPDATE = 2945;
    private AlertDialog alertDialogSendReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_settlement_bank_list);

            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            findViews();
            HitApi();
        });

    }

    void findViews() {

        search_all = findViewById(R.id.search_all);
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SettlementAccountAdapter(isSattlemntAccountVerify, mBankListObjects, this);
        recycler_view.setAdapter(mAdapter);
        noDataView = findViewById(R.id.noDataView);
        addBtn = findViewById(R.id.addBtn);
        noNetworkView = findViewById(R.id.noNetworkView);
        TextView errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Settlement Account is not available, click on Add button to add new account");


       /* BankListResponse bankListResponse = getIntent().getParcelableExtra("BankList");
        getOperatorList(bankListResponse);*/


        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (mAdapter != null) {
                    mAdapter.getFilter().filter(s);
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

        addBtn.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, UpdateAddSettlementBankActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_UPDATE);
        });

    }


    private void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            ApiFintechUtilMethods.INSTANCE.GetSettlementAccount(this, loader, mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum,
                    new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            // getOperatorList((BankListResponse) object);
                            SettlementAccountResponse mSettlementAccountResponse = (SettlementAccountResponse) object;
                            isSattlemntAccountVerify = mSettlementAccountResponse.isSattlemntAccountVerify();
                            if (mSettlementAccountResponse.getData() != null && mSettlementAccountResponse.getData().size() > 0) {
                                noDataView.setVisibility(View.GONE);
                                noNetworkView.setVisibility(View.GONE);
                                mBankListObjects.clear();
                                mBankListObjects.addAll(mSettlementAccountResponse.getData());
                                mAdapter.setFlag(isSattlemntAccountVerify);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                            }
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

    public void confirmationDialog(SettlementAccountData operator) {

        if (dialog != null && dialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_beneficiary_delete_confirm, null);

        ((TextView) view.findViewById(R.id.message)).setText("Are you sure you want to delete following Account");
        ((TextView) view.findViewById(R.id.beneName)).setText(operator.getAccountHolder());
        ((TextView) view.findViewById(R.id.beneAccountNumber)).setText(operator.getAccountNumber());
        ((TextView) view.findViewById(R.id.beneBank)).setText(operator.getBankName());
        ((TextView) view.findViewById(R.id.beneIFSC)).setText(operator.getIfsc());
        dialog = new Dialog(this, R.style.alert_dialog_light);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.findViewById(R.id.cancel).setOnClickListener(v -> dialog.dismiss());
        view.findViewById(R.id.ok).setOnClickListener(v -> {
            dialog.dismiss();
            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                deleteBankApi(operator.getId());

            } else {
                ApiFintechUtilMethods.INSTANCE.NetworkError(this);
            }
        });
        dialog.show();
    }

    public void update(SettlementAccountData operator) {
        startActivityForResult(new Intent(this, UpdateAddSettlementBankActivity.class)
                .putExtra("Data", operator)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_UPDATE);

    }

    public void setDefault(SettlementAccountData operator) {
        defaultSetBankApi(operator.getId());
    }

    public void VerifyOrUtr(SettlementAccountData operator) {
        if (operator.getVerificationStatus() == 0) {
            verifyUser(operator.getId());
        } else {
            sendUTRDialog(operator);
        }
    }

    public void sendUTRDialog(SettlementAccountData data) {
        try {
            if (alertDialogSendReport != null && alertDialogSendReport.isShowing()) {
                return;
            }


            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this);
            alertDialogSendReport = dialogBuilder.create();
            alertDialogSendReport.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_update_utr, null);
            alertDialogSendReport.setView(dialogView);

            final AppCompatEditText mobileEt = dialogView.findViewById(R.id.mobileEt);


            AppCompatTextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);
            AppCompatTextView sendBtn = dialogView.findViewById(R.id.sendBtn);
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView titleTv = dialogView.findViewById(R.id.titleTv);
            TextView notice = dialogView.findViewById(R.id.notice);


            String changeUtr = data.getUtr() + "";
            int length = changeUtr.length();
            if (length > 4) {
                int midlength = length - 4;
                changeUtr = changeUtr.substring(0, 2) + repeatStr(midlength) + changeUtr.substring(length - 2, length);
            } else {
                changeUtr = repeatStr(length);
            }


            notice.setText("We have sent Rs 1 in Your Bank Account With UTR NO " + changeUtr + ". Please check your Statement and enter complete UTR to Verify your Bank Account\\n\\n\n" +
                    "\n" +
                    "हमने आपके बैंक खाते में यूटीआर नं " + changeUtr + " के साथ 1 रुपये भेजे हैं। कृपया अपना बैंक विवरण जांचें और अपना बैंक खाता सत्यापित करने के लिए पूरा UTR दर्ज करें");
            //mobileEt.setText(data.getUtr() + "");


            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogSendReport.dismiss();
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogSendReport.dismiss();
                }
            });

            sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mobileEt.getText().toString().isEmpty()) {
                        mobileEt.setError("Please Enter Valid UTR Number");
                        mobileEt.requestFocus();
                        return;
                    }
                    alertDialogSendReport.dismiss();

                    loader.show();
                    udateUTR(data.getId(), mobileEt.getText().toString());
                }
            });


            alertDialogSendReport.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (WindowManager.BadTokenException bde) {
            bde.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }
    }

    String repeatStr(int count) {
        String str = "";
        for (int i = 0; i < count; i++) {
            str = str + "*";
        }
        return str;
    }

    public void deleteBankApi(int updatedId) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<BasicResponse> call = git.DeleteSettlementAcount(new UpdateSettlementAccountRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(),
                    updatedId));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            BasicResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    if (data.getData() != null) {
                                        if (data.getData().getStatuscode() == 1) {
                                            ApiFintechUtilMethods.INSTANCE.Successful(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");

                                            HitApi();
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");

                                        }
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Successful(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getMsg() + "");

                                        HitApi();
                                    }

                                } else {
                                    //setResult(RESULT_OK);
                                    ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getMsg() + "");
                                }

                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
                            }
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        } else {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    try {

                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
        }

    }

    public void verifyUser(int updatedId) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<BasicResponse> call = git.VerifySettlementAccountOfUser(new UpdateSettlementAccountRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(),
                    updatedId));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            BasicResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    if (data.getData() != null) {
                                        if (data.getData().getStatuscode() == 1) {
                                            ApiFintechUtilMethods.INSTANCE.Processing(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");

                                            HitApi();
                                        } else if (data.getData().getStatuscode() == 2) {
                                            ApiFintechUtilMethods.INSTANCE.Successful(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");

                                            HitApi();
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");

                                        }
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Successful(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getMsg() + "");

                                        HitApi();
                                    }

                                } else {
                                    //setResult(RESULT_OK);
                                    ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getMsg() + "");
                                }

                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
                            }
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        } else {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    try {

                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
        }

    }

    public void udateUTR(int updatedId, String Utr) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<BasicResponse> call = git.UpdateUTRByUser(new UpdateSettlementAccountRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(),
                    updatedId, Utr));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            BasicResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    if (data.getData() != null) {
                                        if (data.getData().getStatuscode() == 1) {
                                            ApiFintechUtilMethods.INSTANCE.Successful(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");

                                            HitApi();
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");

                                        }
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Successful(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getMsg() + "");

                                        HitApi();
                                    }

                                } else {
                                    //setResult(RESULT_OK);
                                    ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getMsg() + "");
                                }

                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
                            }
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        } else {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    try {

                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
        }

    }

    public void defaultSetBankApi(int updatedId) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<BasicResponse> call = git.ToggleDefaulSettlementAcount(new UpdateSettlementAccountRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(),
                    updatedId));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            BasicResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    if (data.getData() != null) {
                                        if (data.getData().getStatuscode() == 1) {
                                            ApiFintechUtilMethods.INSTANCE.Successful(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");
                                            setResult(RESULT_OK);
                                            HitApi();
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");

                                        }
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Successful(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getMsg() + "");

                                        HitApi();
                                    }

                                } else {
                                    //setResult(RESULT_OK);
                                    ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, data.getMsg() + "");
                                }

                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
                            }
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        } else {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    try {

                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_UPDATE && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            HitApi();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_settlment, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_add) {
            startActivityForResult(new Intent(this, UpdateAddSettlementBankActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_UPDATE);
        }
        return super.onOptionsItemSelected(item);
    }

}