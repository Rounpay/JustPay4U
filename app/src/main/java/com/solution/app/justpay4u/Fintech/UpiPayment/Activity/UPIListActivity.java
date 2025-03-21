package com.solution.app.justpay4u.Fintech.UpiPayment.Activity;

import android.app.Activity;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Object.SenderObject;
import com.solution.app.justpay4u.Api.Fintech.Object.VPAList;
import com.solution.app.justpay4u.Api.Fintech.Request.UPIPaymentRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetEKYCDetailResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.VPAListResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.MoveToWallet.Adapter.WalletBalanceAdapter;
import com.solution.app.justpay4u.Fintech.UpiPayment.Adapter.RecentVPALoginAdapter;
import com.solution.app.justpay4u.Fintech.UpiPayment.Adapter.VPAListAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.EKYCStepsDialog;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class UPIListActivity extends AppCompatActivity {


    private TextView senderNum, bhimUPIText;
    private EditText mobileEdt;
    private RecyclerView vpaListRecyclerView;
    private View senderDetailView;
    private View senderLayout;
    private View upiLoginView;
    private View recentLoginView;
    private View vpaListView;
    private View searchLine;
    private View clearIcon;
    private View loaderView;
    public View noDataView;
    private View noNetworkView;
    private View retryBtn;
    private View addVPAView;
    private View scanPayView;
    private View buttonView;
    private EditText searchEt;
    ArrayList<String> mRecentVPALogins = new ArrayList<>();
    private AppPreferences mAppPreferences;
    ArrayList<BalanceData> mBalanceTypes = new ArrayList<>();
    BalanceResponse balanceCheckResponse;
    private WalletBalanceAdapter mWalletBalanceAdapter;
    String deviceId;
    String deviceSerialNum;
    private VPAListAdapter mVpaListAdapter;
    private int INTENT_ADD_VPA = 7453;
    RecyclerView recentRecyclerView;
    private Gson gson;
    private boolean isEKYCCompleted;
    private LoginResponse mLoginDataResponse;
    private EKYCStepsDialog mEKYCStepsDialog;
    private String senderMobNum = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_upi_list);

            findViewsId();

            new Handler(Looper.getMainLooper()).post(() -> {
                gson = new Gson();
                mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
                deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
                deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
                mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
                mEKYCStepsDialog = new EKYCStepsDialog(this, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);
                isEKYCCompleted = mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isEKYCComplete);

                mRecentVPALogins = gson.fromJson(mAppPreferences.getNonRemovalString(ApplicationConstant.INSTANCE.RecentVPALoginPref), new TypeToken<ArrayList<String>>() {
                }.getType());

                senderMobNum = mAppPreferences.getString(ApplicationConstant.INSTANCE.UPISenderNumber);
                if (senderMobNum != null && !senderMobNum.isEmpty()) {
                    getVpaList(this, senderMobNum, true);
                } else {
                    bhimUPIText.setVisibility(View.VISIBLE);
                    upiLoginView.setVisibility(View.VISIBLE);
                    if (mRecentVPALogins != null && mRecentVPALogins.size() > 0) {
                        recentLoginView.setVisibility(View.VISIBLE);
                        recentRecyclerView.setAdapter(new RecentVPALoginAdapter(mRecentVPALogins, this));
                    } else {
                        recentLoginView.setVisibility(View.GONE);
                    }
                }
            });
        });


    }

    void findViewsId() {
        upiLoginView = findViewById(R.id.upi_login);
        bhimUPIText = findViewById(R.id.bhimUPIText);
        senderNum = findViewById(R.id.sender_num);
        senderLayout = findViewById(R.id.sender_layout);
        senderDetailView = findViewById(R.id.senderDetailView);
        recentRecyclerView = findViewById(R.id.recentRecyclerView);
        recentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recentLoginView = findViewById(R.id.recent_login_view);
        vpaListView = findViewById(R.id.vpaListView);
        searchLine = findViewById(R.id.searchLine);
        clearIcon = findViewById(R.id.clearIcon);
        loaderView = findViewById(R.id.loaderView);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        TextView errorMsg= findViewById(R.id.errorMsg);
        errorMsg.setText("VPA List is not available, to display VPA list you have to pay");
        searchEt = findViewById(R.id.search_all);
        retryBtn = findViewById(R.id.retryBtn);
        addVPAView = findViewById(R.id.addVPAView);
        scanPayView = findViewById(R.id.scanPayView);
        buttonView = findViewById(R.id.buttonView);
        vpaListRecyclerView = findViewById(R.id.vpaListRecyclerView);
        vpaListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mobileEdt = findViewById(R.id.edit_mobile);
        RecyclerView balanceRecyclerView = findViewById(R.id.balanceRecyclerView);
        balanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWalletBalanceAdapter = new WalletBalanceAdapter(this, mBalanceTypes);
        balanceRecyclerView.setAdapter(mWalletBalanceAdapter);
        mobileEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                senderMobNum = editable.toString().trim();
                if (senderMobNum.length() != 10) {

                    /*if (mRecentVPALogins != null && mRecentVPALogins.size() > 0 &&
                            recentLoginView.getVisibility() == View.GONE) {
                        recentLoginView.setVisibility(View.VISIBLE);
                    }*/

                    if (buttonView.getVisibility() == View.VISIBLE) {
                        buttonView.setVisibility(View.GONE);
                    }
                    if (loaderView.getVisibility() == View.VISIBLE) {
                        loaderView.setVisibility(View.GONE);
                    }
                    if (vpaListView.getVisibility() == View.VISIBLE) {
                        vpaListView.setVisibility(View.GONE);
                    }
                    if (senderDetailView.getVisibility() == View.VISIBLE) {
                        senderDetailView.setVisibility(View.GONE);
                    }
                    if (noDataView.getVisibility() == View.VISIBLE) {
                        noDataView.setVisibility(View.GONE);
                    }
                    if (noNetworkView.getVisibility() == View.VISIBLE) {
                        noNetworkView.setVisibility(View.GONE);
                    }


                } else {

                    getVpaList(UPIListActivity.this, senderMobNum, true);
                }
            }
        });
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mVpaListAdapter != null) {
                    mVpaListAdapter.getFilter().filter(charSequence);
                }
                if (charSequence.length() > 0) {
                    clearIcon.setVisibility(View.VISIBLE);
                    searchLine.setVisibility(View.VISIBLE);

                } else {
                    clearIcon.setVisibility(View.GONE);
                    searchLine.setVisibility(View.GONE);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        clearIcon.setOnClickListener(view -> searchEt.setText(""));

        findViewById(R.id.dmr_logout).setOnClickListener(view -> {

            if (mRecentVPALogins != null && mRecentVPALogins.size() > 0) {
                recentLoginView.setVisibility(View.VISIBLE);
                recentRecyclerView.setAdapter(new RecentVPALoginAdapter(mRecentVPALogins, this));
            }
            mobileEdt.setText("");
            senderMobNum = "";
            mAppPreferences.set(ApplicationConstant.INSTANCE.UPISenderNumber, senderMobNum);
            bhimUPIText.setVisibility(View.VISIBLE);
            upiLoginView.setVisibility(View.VISIBLE);
            senderLayout.setVisibility(View.GONE);
            buttonView.setVisibility(View.GONE);
            senderDetailView.setVisibility(View.GONE);
            loaderView.setVisibility(View.GONE);
            vpaListView.setVisibility(View.GONE);
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);


        });
        retryBtn.setOnClickListener(view -> {
            if (mobileEdt.getText().length() == 10) {
                noNetworkView.setVisibility(View.GONE);
                noDataView.setVisibility(View.GONE);
                getVpaList(UPIListActivity.this, senderMobNum.trim(), true);
            }
        });

        addVPAView.setOnClickListener(view ->
                startActivityForResult(new Intent(this, UPIPayActivity.class)
                        .putExtra("SenderNum", senderMobNum)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_ADD_VPA));
        scanPayView.setOnClickListener(view ->
                startActivityForResult(new Intent(this, QRScanActivity.class)
                        .putExtra("SenderNum", senderMobNum)
                        .putExtra("FROM_SCANPAY", true)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_ADD_VPA));
    }

    void setRecentLoginData(String mobNumber) {

        if (mRecentVPALogins != null) {

            if (!mRecentVPALogins.contains(mobNumber.trim())) {
                mRecentVPALogins.add(mobNumber.trim());
                mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.RecentVPALoginPref, gson.toJson(mRecentVPALogins));
            }

        } else {
            mRecentVPALogins = new ArrayList<>();
            mRecentVPALogins.add(mobNumber.trim());
            mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.RecentVPALoginPref, gson.toJson(mRecentVPALogins));
        }

        if (mRecentVPALogins != null && mRecentVPALogins.size() > 0) {
            recentRecyclerView.setAdapter(new RecentVPALoginAdapter(mRecentVPALogins, this));
        }
    }

    private void showBalanceData() {
        mBalanceTypes.clear();
        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
            mBalanceTypes.clear();
            mBalanceTypes.addAll(balanceCheckResponse.getBalanceData());

          /*  if (mLoginDataResponse.isAccountStatement()) {
                mBalanceTypes.add(new BalanceType("Outstanding Wallet", mBalanceData.getOsBalance()));
            }*/

            mWalletBalanceAdapter.notifyDataSetChanged();
        } else {
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
            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                showBalanceData();
            } else {
                ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse, deviceId, deviceSerialNum,
                        mAppPreferences, mEKYCStepsDialog, object -> {
                            balanceCheckResponse = (BalanceResponse) object;
                            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                                showBalanceData();
                            }
                        });
            }

        }

    }

    public void getVpaList(final Activity context, String mobNum, boolean isShowBalance) {
        try {
            loaderView.setVisibility(View.VISIBLE);
            senderDetailView.setVisibility(View.VISIBLE);
            if (vpaListView.getVisibility() == View.VISIBLE) {
                vpaListView.setVisibility(View.GONE);
            }


            UPIPaymentRequest paymentReq = new UPIPaymentRequest(new SenderObject(mobNum),
                    mLoginDataResponse.getData().getUserID() + "",
                    mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginDataResponse.getData().getSession(),
                    mLoginDataResponse.getData().getSessionID());

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<VPAListResponse> call = git.GetVPAListUPIPayement(paymentReq);

            call.enqueue(new Callback<VPAListResponse>() {

                @Override
                public void onResponse(Call<VPAListResponse> call, retrofit2.Response<VPAListResponse> response) {

                    loaderView.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        if (response.body() != null) {

                            if (response.body().getStatuscode() == 1) {
                                buttonView.setVisibility(View.VISIBLE);
                                if (response.body().getVpaList() != null && response.body().getVpaList().size() > 0) {
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.UPISenderNumber, mobNum);
                                    senderNum.setText(mobNum);
                                    noDataView.setVisibility(View.GONE);
                                    noNetworkView.setVisibility(View.GONE);
                                    mVpaListAdapter = new VPAListAdapter(response.body().getVpaList(), context);
                                    vpaListRecyclerView.setAdapter(mVpaListAdapter);
                                    vpaListView.setVisibility(View.VISIBLE);
                                    senderLayout.setVisibility(View.VISIBLE);
                                    recentLoginView.setVisibility(View.GONE);
                                    upiLoginView.setVisibility(View.GONE);
                                    bhimUPIText.setVisibility(View.GONE);
                                    if (isShowBalance) {
                                        showBalanceData();
                                    }
                                    setRecentLoginData(mobNum);
                                } else {
                                    senderLayout.setVisibility(View.GONE);
                                    upiLoginView.setVisibility(View.VISIBLE);
                                    bhimUPIText.setVisibility(View.VISIBLE);
                                    vpaListView.setVisibility(View.GONE);
                                    noDataView.setVisibility(View.VISIBLE);
                                    noNetworkView.setVisibility(View.GONE);
                                }
                            } else {
                                senderLayout.setVisibility(View.GONE);
                                upiLoginView.setVisibility(View.VISIBLE);
                                bhimUPIText.setVisibility(View.VISIBLE);
                                buttonView.setVisibility(View.GONE);
                                vpaListView.setVisibility(View.GONE);
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                if (!response.body().isVersionValid()) {
                                    ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        senderLayout.setVisibility(View.GONE);
                        buttonView.setVisibility(View.GONE);
                        upiLoginView.setVisibility(View.VISIBLE);
                        bhimUPIText.setVisibility(View.VISIBLE);
                        vpaListView.setVisibility(View.GONE);
                        noDataView.setVisibility(View.VISIBLE);
                        noNetworkView.setVisibility(View.GONE);
                        ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<VPAListResponse> call, Throwable t) {
                    buttonView.setVisibility(View.GONE);
                    upiLoginView.setVisibility(View.VISIBLE);
                    bhimUPIText.setVisibility(View.VISIBLE);
                    senderLayout.setVisibility(View.GONE);
                    loaderView.setVisibility(View.GONE);
                    vpaListView.setVisibility(View.GONE);
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            noDataView.setVisibility(View.GONE);
                            noNetworkView.setVisibility(View.VISIBLE);
                            ApiFintechUtilMethods.INSTANCE.NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            noDataView.setVisibility(View.GONE);
                            noNetworkView.setVisibility(View.VISIBLE);
                            ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                ApiFintechUtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        ApiFintechUtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            senderLayout.setVisibility(View.GONE);
            buttonView.setVisibility(View.GONE);
            upiLoginView.setVisibility(View.VISIBLE);
            bhimUPIText.setVisibility(View.VISIBLE);
            vpaListView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
            loaderView.setVisibility(View.GONE);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_ADD_VPA && resultCode == RESULT_OK) {
            if (mobileEdt.getText().length() == 10) {
                ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse, deviceId, deviceSerialNum,
                        mAppPreferences, mEKYCStepsDialog, object -> {
                            balanceCheckResponse = (BalanceResponse) object;
                            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                                showBalanceData();
                            }
                        });
                getVpaList(UPIListActivity.this, senderMobNum.trim(), false);
            }
        }
    }

    public void sendMoneyClick(VPAList operator) {
        startActivityForResult(new Intent(this, UPIPayActivity.class)
                .putExtra("VPAData", operator)
                .putExtra("SenderNum", operator.getSenderNo() != null && !operator.getSenderNo().isEmpty() ? operator.getSenderNo() : senderMobNum)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_ADD_VPA);
    }

    public void recentLogin(String operator) {
        mobileEdt.setText(operator + "");
    }
}