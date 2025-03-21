package com.solution.app.justpay4u.Fintech.CommissionSlab.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.solution.app.justpay4u.Api.Fintech.Object.SlabDetailDisplayLvl;
import com.solution.app.justpay4u.Api.Fintech.Object.SlabRangeDetail;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.SlabCommissionResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.SlabRangeDetailResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.CommissionSlab.Adapter.CommissionAdapter;
import com.solution.app.justpay4u.Fintech.CommissionSlab.Adapter.CommissionSlabDetailAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.RecyclerViewStickyHeader.HeaderItemDecoration;

import java.util.ArrayList;

/**
 * Created by Vishnu on 26-04-2017.
 */

public class CommissionScreenActivity extends AppCompatActivity {

    CustomLoader loader;
    RecyclerView recycler_view;
    CommissionAdapter mAdapter;

    ArrayList<SlabDetailDisplayLvl> transactionsObjects = new ArrayList<>();
    EditText searchView;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    AppPreferences mAppPreferences;
    private AlertDialog alertDialog;
    private RequestOptions requestOptions;
    private LoginResponse mLoginDataResponse;
    private String deviceId, deviceSerialNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(CommissionScreenActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_commission_screen);
            findViews();
            requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);


            findViewById(R.id.clearIcon).setOnClickListener(v -> searchView.setText(""));
            searchView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (mAdapter != null) {
                        mAdapter.filter(s.toString());
                    }
                }
            });
            retryBtn.setOnClickListener(v -> HitApi());
            new Handler(Looper.getMainLooper()).post(() -> {
                dataParse(new Gson().fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.commListPref), SlabCommissionResponse.class));
                HitApi();
            });
        });

    }


    void findViews() {

        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Commission Slab not found");
        searchView = findViewById(R.id.search_all);

        searchView.setVisibility(View.VISIBLE);
    }

    private void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            if (!loader.isShowing()) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }

            ApiFintechUtilMethods.INSTANCE.MyCommission(this, true, loader, mLoginDataResponse,
                    deviceId, deviceSerialNum,
                    mAppPreferences, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            dataParse((SlabCommissionResponse) object);
                        }

                        @Override
                        public void onError(int error) {
                            if (transactionsObjects.size() == 0) {
                                if (error == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {

                                    noDataView.setVisibility(View.GONE);
                                    noNetworkView.setVisibility(View.VISIBLE);
                                } else {
                                    noDataView.setVisibility(View.VISIBLE);
                                    noNetworkView.setVisibility(View.GONE);
                                }
                            }
                        }
                    });

        } else {
            if (transactionsObjects.size() == 0) {
                noDataView.setVisibility(View.GONE);
                noNetworkView.setVisibility(View.VISIBLE);
            }
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    public void HitApiSlabDetail(final int oid, final String operatorValue, final String maxMinValue) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            ApiFintechUtilMethods.INSTANCE.MyCommissionDetail(this, oid, loader, mLoginDataResponse,
                    deviceId, deviceSerialNum,
                    mAppPreferences, object -> {
                        SlabRangeDetailResponse mSlabRangeDetailResponse = (SlabRangeDetailResponse) object;
                        slabDetailDialog(mSlabRangeDetailResponse.getSlabRangeDetail(), operatorValue, maxMinValue, oid);
                    });

        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    public void dataParse(SlabCommissionResponse transactions) {


        if (transactions != null) {

            transactionsObjects = transactions.getSlabDetailDisplayLvl();

            if (transactionsObjects != null && transactionsObjects.size() > 0) {

                String HeaderName = "";
                for (int i = 0; i < transactionsObjects.size(); i++) {
                    if (!HeaderName.equalsIgnoreCase(transactionsObjects.get(i).getOpType())) {
                        HeaderName = transactionsObjects.get(i).getOpType();
                        transactionsObjects.add(i, new SlabDetailDisplayLvl(transactionsObjects.get(i).getOpType(), 0,
                                "", 0, "", 0, 0, 0, null));
                    }
                }

                mAdapter = new CommissionAdapter(transactionsObjects, this, mLoginDataResponse.getData().getRoleID());
                recycler_view.addItemDecoration(new HeaderItemDecoration(mAdapter));
                recycler_view.setAdapter(mAdapter);
                recycler_view.setVisibility(View.VISIBLE);
                noDataView.setVisibility(View.GONE);
                noNetworkView.setVisibility(View.GONE);
            } else {
                if (transactionsObjects.size() == 0) {
                    noDataView.setVisibility(View.VISIBLE);
                    noNetworkView.setVisibility(View.GONE);
                    recycler_view.setVisibility(View.GONE);
                }
            }
        } else {
            if (transactionsObjects.size() == 0) {
                noDataView.setVisibility(View.VISIBLE);
                noNetworkView.setVisibility(View.GONE);
                recycler_view.setVisibility(View.GONE);
            }
        }

    }


    public void slabDetailDialog(ArrayList<SlabRangeDetail> mSlabRangeDetail, String operatorValue, String maxMinValue, int oid) {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this);
            alertDialog = dialogBuilder.create();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_slab_range_details, null);
            alertDialog.setView(dialogView);


            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView operator = dialogView.findViewById(R.id.operator);
            TextView maxMin = dialogView.findViewById(R.id.maxMin);
            TextView maxComTitle = dialogView.findViewById(R.id.maxComTitle);
            TextView comSurTitle = dialogView.findViewById(R.id.comSurTitle);
            TextView fixedChargedTitle = dialogView.findViewById(R.id.fixedChargedTitle);
            ImageView iconIv = dialogView.findViewById(R.id.icon);
            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new CommissionSlabDetailAdapter(mSlabRangeDetail, this));
            operator.setText(operatorValue);
            maxMin.setText("Range : " + maxMinValue);

            if (mSlabRangeDetail.get(0).getDmrModelID() == 2 || mSlabRangeDetail.get(0).getDmrModelID() == 3 ||
                    mSlabRangeDetail.get(0).getDmrModelID() == 4) {
                fixedChargedTitle.setVisibility(View.VISIBLE);
                maxComTitle.setVisibility(View.GONE);
            } else if (mSlabRangeDetail.get(0).getDmrModelID() == 1) {
                fixedChargedTitle.setVisibility(View.GONE);
                maxComTitle.setVisibility(View.GONE);
            } else {
                fixedChargedTitle.setVisibility(View.GONE);
                maxComTitle.setVisibility(View.VISIBLE);
            }

            if (mSlabRangeDetail.get(0).isCommType()) {
                comSurTitle.setText("Surcharge");
            } else {
                comSurTitle.setText("Commission");
            }

            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + oid + ".png")
                    .apply(requestOptions)
                    .into(iconIv);
            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });


            alertDialog.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

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
