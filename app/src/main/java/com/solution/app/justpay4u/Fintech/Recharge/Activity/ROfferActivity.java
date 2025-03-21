package com.solution.app.justpay4u.Fintech.Recharge.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.ROfferObject;
import com.solution.app.justpay4u.Api.Fintech.Request.ROfferRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.RofferResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Adapter.ROfferAdapter;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class ROfferActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recycler_view;
    ArrayList<ROfferObject> transactionsObjects = new ArrayList<>();
    RofferResponse mRofferResponse = new RofferResponse();
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg, tv_note;
    private CustomLoader loader;
    private boolean isPlanServiceUpdated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_roffer);

            isPlanServiceUpdated = getIntent().getBooleanExtra("IsPlanServiceUpdated", false);
            // mRofferResponse = (RofferResponse) getIntent().getParcelableExtra("response");

            recycler_view = findViewById(R.id.recycler_view);


            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            retryBtn = findViewById(R.id.retryBtn);
            errorMsg = findViewById(R.id.errorMsg);
            tv_note = findViewById(R.id.tv_note);
            errorMsg.setText("Roffer not available.");
            retryBtn.setOnClickListener(v -> HitApi());
            HitApi();
        });

    }



    void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            if (!loader.isShowing()) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }

            try {
                FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
                Call<RofferResponse> call;
                if (isPlanServiceUpdated) {
                    call = git.GetRNPRoffer(new ROfferRequest(getIntent().getStringExtra("OperatorSelectedId"),
                            getIntent().getStringExtra("Number"),
                            ApplicationConstant.INSTANCE.APP_ID,
                            getIntent().getStringExtra("DeviceId"),
                            "", BuildConfig.VERSION_NAME,
                            getIntent().getStringExtra("DeviceSerialNum")));
                } else {
                    call = git.ROffer(new ROfferRequest(getIntent().getStringExtra("OperatorSelectedId"),
                            getIntent().getStringExtra("Number"),
                            ApplicationConstant.INSTANCE.APP_ID,
                            getIntent().getStringExtra("DeviceId"),
                            "", BuildConfig.VERSION_NAME,
                            getIntent().getStringExtra("DeviceSerialNum")));
                }

                call.enqueue(new Callback<RofferResponse>() {
                    @Override
                    public void onResponse(Call<RofferResponse> call, final retrofit2.Response<RofferResponse> response) {
                        try {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            if (response.isSuccessful()) {
                                mRofferResponse = response.body();
                                if (mRofferResponse != null) {

                                    if (mRofferResponse.getStatuscode() == 1) {

                                        if (mRofferResponse.getData() != null && mRofferResponse.getData().getRecords() != null && mRofferResponse.getData().getRecords().size() > 0 ||
                                                mRofferResponse.getRofferData() != null && mRofferResponse.getRofferData().size() > 0 ||
                                                mRofferResponse.getDataPA() != null && mRofferResponse.getDataPA().getError() == 0 && mRofferResponse.getDataPA().getRecords() != null && mRofferResponse.getDataPA().getRecords().size() > 0) {

                                            hideShowView(0);
                                            parseData();


                                        } else if (mRofferResponse.getDataPA() != null && mRofferResponse.getDataPA().getError() != 0) {
                                            hideShowView(1);
                                            ApiFintechUtilMethods.INSTANCE.Error(ROfferActivity.this, mRofferResponse.getDataPA().getMessage() + "");
                                        } else {
                                            hideShowView(1);
                                            ApiFintechUtilMethods.INSTANCE.Error(ROfferActivity.this, "Records not found");
                                        }
                                    } else {
                                        hideShowView(1);
                                        if (!mRofferResponse.isVersionValid()) {
                                            ApiFintechUtilMethods.INSTANCE.versionDialog(ROfferActivity.this);
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(ROfferActivity.this, mRofferResponse.getMsg() + "");
                                        }
                                    }

                                } else {
                                    hideShowView(1);
                                    ApiFintechUtilMethods.INSTANCE.Error(ROfferActivity.this, "Records not found");
                                }
                            } else {
                                hideShowView(1);
                                ApiFintechUtilMethods.INSTANCE.apiErrorHandle(ROfferActivity.this, response.code(), response.message());
                            }
                        } catch (Exception e) {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            hideShowView(1);
                        }

                    }

                    @Override
                    public void onFailure(Call<RofferResponse> call, Throwable t) {
                        try {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            if (t instanceof UnknownHostException || t instanceof IOException) {
                                hideShowView(2);
                                ApiFintechUtilMethods.INSTANCE.NetworkError(ROfferActivity.this);
                            } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                hideShowView(1);
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(ROfferActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                            } else {
                                hideShowView(1);
                                if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                    ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(ROfferActivity.this, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(ROfferActivity.this, getString(R.string.some_thing_error));
                                }
                            }
                        } catch (IllegalStateException ise) {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.Error(ROfferActivity.this, ise.getMessage());
                            hideShowView(1);
                        }

                    }
                });

            } catch (Exception e) {
                if (loader != null && loader.isShowing()) {
                    loader.dismiss();
                }
                e.printStackTrace();
                hideShowView(1);
            }


        } else {

            hideShowView(2);
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    void hideShowView(int type) {
        if (type == 1) {
            recycler_view.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            recycler_view.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        } else {
            tv_note.setVisibility(View.VISIBLE);
            recycler_view.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.GONE);
        }

    }

    public void parseData() {
        try {

            if (mRofferResponse != null && mRofferResponse.getData() != null && mRofferResponse.getData().getRecords() != null && mRofferResponse.getData().getRecords().size() > 0) {
                transactionsObjects = mRofferResponse.getData().getRecords();

            } else if (mRofferResponse != null && mRofferResponse.getRofferData() != null && mRofferResponse.getRofferData().size() > 0) {
                transactionsObjects = mRofferResponse.getRofferData();

            } else if (mRofferResponse != null && mRofferResponse.getDataPA() != null && mRofferResponse.getDataPA().getRecords() != null && mRofferResponse.getDataPA().getRecords().size() > 0) {
                transactionsObjects = mRofferResponse.getDataPA().getRecords();

            } else if (mRofferResponse != null && mRofferResponse.getDataPA() != null && mRofferResponse.getDataPA().getError() != 0) {
                ApiFintechUtilMethods.INSTANCE.Error(this, mRofferResponse.getDataPA().getMessage() + "");
                hideShowView(1);
            } else {
                hideShowView(1);
                ApiFintechUtilMethods.INSTANCE.Error(this, "No Offer Found");
            }
            ROfferAdapter mAdapter = new ROfferAdapter(transactionsObjects, ROfferActivity.this);
            recycler_view.setLayoutManager(new LinearLayoutManager(this));
            recycler_view.setAdapter(mAdapter);
        } catch (Exception e) {
            hideShowView(1);
            ApiFintechUtilMethods.INSTANCE.Error(this, "No Offer Found");
        }


    }


    @Override
    public void onClick(View v) {

    }

    public void ItemClick(String amount, String desc) {


        Intent intent = new Intent();
        intent.putExtra("AMOUNT", amount + "");
        intent.putExtra("DESCRIPTION", desc + "");
        setResult(RESULT_OK, intent);

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
