package com.solution.app.justpay4u.Fintech.DthSubscription.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.DthChannels;
import com.solution.app.justpay4u.Api.Fintech.Object.DthPackage;
import com.solution.app.justpay4u.Api.Fintech.Response.GetDthPackageResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.Fintech.DthSubscription.Adapter.DthChannelAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.RecyclerViewStickyHeader.HeaderItemDecoration;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;

public class DthChannelActivity extends AppCompatActivity {

    public TextView validity;
    public TextView amount, name;
    public TextView description, bookingAmt;
    CustomLoader loader;
    RecyclerView recycler_view;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    private EditText searchView;
    private DthPackage mDthPackage;
    private DthChannelAdapter mAdapter;
    private AppPreferences mAppPreferences;
    private LoginResponse mLoginDataResponse;
    private String deviceId, deviceSerialNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_dth_channel);
            Toolbar toolBar = findViewById(R.id.toolbar);
            toolBar.setTitle("DTH Channel");
            toolBar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(toolBar);
            toolBar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
            toolBar.setNavigationOnClickListener(v -> onBackPressed());
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            mDthPackage = getIntent().getParcelableExtra("DTH_PACKAGE");
            searchView = (EditText) findViewById(R.id.search_all);
            amount = findViewById(R.id.amount);
            description = findViewById(R.id.description);
            validity = findViewById(R.id.validity);
            name = findViewById(R.id.name);
            bookingAmt = findViewById(R.id.bookingAmt);
            recycler_view = findViewById(R.id.recycler_view);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            recycler_view.setLayoutManager(gridLayoutManager);


            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            retryBtn = findViewById(R.id.retryBtn);
            errorMsg = findViewById(R.id.errorMsg);
            errorMsg.setText("Channel Not Available");
            description.setText(mDthPackage.getDescription() + "");
            name.setText(mDthPackage.getPackageName() + "");
            validity.setText(mDthPackage.getValidity() + " Days");
            bookingAmt.setText("Booking Amount : " + Utility.INSTANCE.formatedAmountWithRupees(mDthPackage.getBookingAmount() + ""));

            amount.setText(Utility.INSTANCE.formatedAmountWithRupees(mDthPackage.getPackageMRP() + ""));

            findViewById(R.id.clearIcon).setOnClickListener(v -> searchView.setText(""));

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {


                    if (mAdapter != null && mAdapter.getItemViewType(position) == DthChannelAdapter.Header) {
                        return 2;
                    }

                    return 1;

                }
            });

            retryBtn.setOnClickListener(v -> getDthPackage());
            getDthPackage();
        });

    }


    void getDthPackage() {
        if (!loader.isShowing()) {
            loader.show();
        }
        ApiFintechUtilMethods.INSTANCE.GetDthChannel(DthChannelActivity.this, mDthPackage.getId() + "", mDthPackage.getOid() + "",
                loader, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, new ApiFintechUtilMethods.ApiResponseCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        GetDthPackageResponse mGetDthPackageResponse = (GetDthPackageResponse) object;
                        dataParse(mGetDthPackageResponse.getDthChannels());
                    }

                    @Override
                    public void onError(int error) {
                        if (error == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
                            noDataView.setVisibility(View.GONE);
                            noNetworkView.setVisibility(View.VISIBLE);
                        } else {
                            noDataView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                        }
                    }
                });

    }

    public void dataParse(ArrayList<DthChannels> channelsArrayList) {


        if (channelsArrayList != null && channelsArrayList.size() > 0) {

            int categoryId = 0;
            ArrayList<DthChannels> modifiedList = new ArrayList<>();
            for (DthChannels mItem : channelsArrayList) {
                if (categoryId != mItem.getCategoryID()) {
                    modifiedList.add(new DthChannels(mItem.getCategoryName(), 0,
                            "", 0, "",
                            "", false, false));

                    categoryId = mItem.getCategoryID();
                }

                modifiedList.add(mItem);
            }

            mAdapter = new DthChannelAdapter(modifiedList, this);
            recycler_view.setAdapter(mAdapter);
            new HeaderItemDecoration(mAdapter);
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            searchView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    mAdapter.filter(s.toString());
                }
            });

        } else {
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
            recycler_view.setVisibility(View.GONE);
        }


    }

}
