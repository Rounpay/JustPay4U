package com.solution.app.justpay4u.Networking.Activity;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Networking.Object.EPinList;
import com.solution.app.justpay4u.Api.Networking.Request.FindUserDetailsByIdRequest;
import com.solution.app.justpay4u.Api.Networking.Response.EPinListResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApiNetworkingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.NetworkingEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Networking.Adapter.EPinListNetworkingAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class EPinListNetworkingActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recycler_view;
    EPinListNetworkingAdapter mAdapter;
    ArrayList<EPinList> transactionsObjects = new ArrayList<>();
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    EditText search_all;
    CustomLoader loader;
    private CustomFilterDialog mCustomFilterDialog;

    private int filterTypeValue = -1;
    private LoginResponse mLoginDataResponse;
    private AppPreferences mAppPrefrences;
    private String deviceId, deviceSerialNum;
    private int userTopupType;
    ArrayList<BalanceData> balanceTypes = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_networking_epin_list);
            mAppPrefrences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPrefrences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPrefrences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPrefrences);
            userTopupType = getIntent().getIntExtra("TopupType", 0);
            balanceTypes = getIntent().getParcelableArrayListExtra("items");
            findViews();

            new Handler(Looper.getMainLooper()).post(() -> {
                mCustomFilterDialog = new CustomFilterDialog(this);
                HitApi();
            });
        });

    }


    void findViews() {

        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new EPinListNetworkingAdapter(transactionsObjects, this);
        recycler_view.setAdapter(mAdapter);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Epin is not available");
        search_all = findViewById(R.id.search_all);
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));
        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                if (mAdapter != null) {
                    mAdapter.filter(newText.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        retryBtn.setOnClickListener(v -> HitApi());
    }


    void HitApi() {

        try {

            if (ApiNetworkingUtilMethods.INSTANCE.isVpnConnected(this)) {
                new CustomAlertDialog(this).ErrorVPN(this);
            } else {
                if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

                    if (!loader.isShowing()) {
                        loader.show();
                    }
                    NetworkingEndPointInterface git = ApiClient.getClient().create(NetworkingEndPointInterface.class);

                    Call<EPinListResponse> call = git.GetEPinList(new FindUserDetailsByIdRequest(new BasicRequest(
                            mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                            ApplicationConstant.INSTANCE.APP_ID,
                            deviceId,
                            "", BuildConfig.VERSION_NAME, deviceSerialNum,
                            mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()),
                            filterTypeValue + ""));

                    call.enqueue(new Callback<EPinListResponse>() {
                        @Override
                        public void onResponse(Call<EPinListResponse> call, final retrofit2.Response<EPinListResponse> response) {
                            if (response != null) {
                                if (response.isSuccessful()) {

                                    try {
                                        if (response.body() != null) {

                                            EPinListResponse data = response.body();
                                            loader.dismiss();
                                            if (data.getStatuscode() == 1) {
                                                if (data.getData() != null && data.getData().size() > 0) {
                                                    dataParse(data.getData());
                                                } else {
                                                    setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                                    ApiNetworkingUtilMethods.INSTANCE.Error(EPinListNetworkingActivity.this, "EPin list is not available");

                                                }


                                            } else {
                                                setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                                if (!data.isVersionValid()) {
                                                    ApiFintechUtilMethods.INSTANCE.versionDialog(EPinListNetworkingActivity.this);
                                                } else {
                                                    ApiNetworkingUtilMethods.INSTANCE.Error(EPinListNetworkingActivity.this, data.getMsg() + "");
                                                }
                                            }

                                        } else {

                                            loader.dismiss();
                                            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                            ApiNetworkingUtilMethods.INSTANCE.Error(EPinListNetworkingActivity.this, getString(R.string.some_thing_error));

                                        }
                                    } catch (Exception ex) {
                                        loader.dismiss();
                                        setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                        ApiNetworkingUtilMethods.INSTANCE.Error(EPinListNetworkingActivity.this, ex.getMessage() + "");

                                    }
                                } else {
                                    loader.dismiss();
                                    setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                    ApiNetworkingUtilMethods.INSTANCE.apiErrorHandle(EPinListNetworkingActivity.this, response.code(),
                                            response.message());
                                }
                            } else {

                                loader.dismiss();
                                setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                ApiNetworkingUtilMethods.INSTANCE.Error(EPinListNetworkingActivity.this, getString(R.string.some_thing_error));

                            }
                        }

                        @Override
                        public void onFailure(Call<EPinListResponse> call, Throwable t) {

                            try {

                                loader.dismiss();
                                if (t instanceof UnknownHostException || t instanceof IOException) {
                                    setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK);
                                    ApiNetworkingUtilMethods.INSTANCE.NetworkError(EPinListNetworkingActivity.this);
                                } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                    setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK);
                                    ApiNetworkingUtilMethods.INSTANCE.ErrorWithTitle(EPinListNetworkingActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                                } else {
                                    setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                    if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                        ApiNetworkingUtilMethods.INSTANCE.ErrorWithTitle(EPinListNetworkingActivity.this, "FATAL ERROR", t.getMessage() + "");
                                    } else {
                                        ApiNetworkingUtilMethods.INSTANCE.Error(EPinListNetworkingActivity.this, getString(R.string.some_thing_error));
                                    }
                                }
                            } catch (IllegalStateException ise) {
                                loader.dismiss();
                                setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                ApiNetworkingUtilMethods.INSTANCE.Error(EPinListNetworkingActivity.this, ise.getMessage() + "");


                            }
                        }
                    });
                } else {
                    setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK);
                    ApiFintechUtilMethods.INSTANCE.NetworkError(this);
                }
            }
        } catch (Exception ex) {
            loader.dismiss();
            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
            ApiNetworkingUtilMethods.INSTANCE.Error(EPinListNetworkingActivity.this, ex.getMessage() + "");

        }
    }


    void setInfoHideShow(int errorType) {
        recycler_view.setVisibility(View.GONE);
        transactionsObjects.clear();
        mAdapter.notifyDataSetChanged();

        if (errorType == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        } else {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);

        }
    }

    public void dataParse(ArrayList<EPinList> mList) {


        if (mList != null && mList.size() > 0) {
            transactionsObjects.clear();
            transactionsObjects.addAll(mList);
        }
        if (transactionsObjects.size() > 0) {
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();

        } else {
            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
            ApiFintechUtilMethods.INSTANCE.Error(this, "EPin is not available");
        }
    }

    @Override
    public void onClick(View v) {

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
        }

        return super.onOptionsItemSelected(item);
    }


    public void ActivateUser(EPinList item) {

        startActivity(new Intent(this, ActivateUserByEpinNetworkingActivity.class)
                .putExtra("Title", "Activate User")
                .putExtra("TopupType", userTopupType)
                .putExtra("EPinData", item)
                .putParcelableArrayListExtra("items", balanceTypes)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }
}
