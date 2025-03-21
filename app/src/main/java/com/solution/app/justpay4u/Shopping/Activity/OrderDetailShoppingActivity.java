package com.solution.app.justpay4u.Shopping.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Shopping.Object.Address;
import com.solution.app.justpay4u.Api.Shopping.Object.OrderDetail;
import com.solution.app.justpay4u.Api.Shopping.Response.OrderListResponse;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.OrderListShoppingAdapter;
import com.solution.app.justpay4u.Util.CustomLoader;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class OrderDetailShoppingActivity extends AppCompatActivity {

    View noDataView, noNetworkView, retryBtn, addressView, statusView;
    TextView OrderId, date, qty, errorMsg, priceDetailLabel, totalMrp, totalPrice, totalDisc, totalShip, totalAmt, status;
    RecyclerView recyclerView;
    TextView address, city, mobile, title, deliverToName;
    NestedScrollView scrollView;
    int intentOrderId;
    private CustomLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_order_detail);
            intentOrderId = getIntent().getIntExtra("OrderId", 0);
            SectionedRecyclerViewAdapter sectionedAdapter = new SectionedRecyclerViewAdapter();
            findViews();
        });
    }

    void findViews() {

        OrderId = findViewById(R.id.OrderId);
        date = findViewById(R.id.date);
        statusView = findViewById(R.id.statusView);
        status = findViewById(R.id.status);
        qty = findViewById(R.id.qty);
        addressView = findViewById(R.id.addressView);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        mobile = findViewById(R.id.mobile);
        title = findViewById(R.id.title);
        deliverToName = findViewById(R.id.deliverToName);
        priceDetailLabel = findViewById(R.id.priceDetailLabel);
        totalMrp = findViewById(R.id.totalMrp);
        totalPrice = findViewById(R.id.totalPrice);
        totalDisc = findViewById(R.id.totalDisc);
        totalShip = findViewById(R.id.totalShip);
        totalAmt = findViewById(R.id.totalAmt);
        scrollView = findViewById(R.id.scrollView);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Cart is empty.");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        retryBtn.setOnClickListener(v ->
                getOrdrDetails(this, true)
        );

        getOrdrDetails(this, true);
    }

    void getOrdrDetails(Activity mContext, boolean isLoaderShow) {
        if (loader != null && !loader.isShowing()&& isLoaderShow) {
            loader.show();
        }
        ApiShoppingUtilMethods.INSTANCE.getOrderList(mContext, intentOrderId, loader, new ApiShoppingUtilMethods.ApiResponseCallBack() {
            @Override
            public void onSucess(Object object) {
                ApplicationConstant.INSTANCE.isCartChange = true;
                OrderListResponse mOrderDetailResponse = (OrderListResponse) object;
                if (mOrderDetailResponse != null && mOrderDetailResponse.getOrderDetail() != null
                        && mOrderDetailResponse.getOrderDetail().size() > 0) {
                    OrderDetail mOrderDetail = mOrderDetailResponse.getOrderDetail().get(0);
                    noDataView.setVisibility(View.GONE);
                    noNetworkView.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    setAddressData(mOrderDetail.getShippingAddress());

                    OrderId.setText("Order Id- " + mOrderDetail.getOrderId() + "");
                    date.setText("Order Date- " + ApiShoppingUtilMethods.INSTANCE.formatedDateOfSlash(mOrderDetail.getOrderDate() + ""));

                    if (mOrderDetail.getStatus() == 1) {
                        status.setText("Order Placed");
                        status.setTextColor(getResources().getColor(R.color.color_delete));
                    } else if (mOrderDetail.getStatus() == 2) {
                        status.setText("Order Approved");
                        status.setTextColor(getResources().getColor(R.color.green));
                    } else if (mOrderDetail.getStatus() == 3) {
                        status.setText("Order Disapproved");
                        status.setTextColor(getResources().getColor(R.color.color_red));
                    } else if (mOrderDetail.getStatus() == 4) {
                        status.setText("Order Canceled");
                        status.setTextColor(getResources().getColor(R.color.color_red));
                    } else if (mOrderDetail.getStatus() == 5) {
                        status.setText("Order Dispatched");
                        status.setTextColor(getResources().getColor(R.color.color_delete));
                    } else if (mOrderDetail.getStatus() == 6) {
                        status.setText("Order Delivered");
                        status.setTextColor(getResources().getColor(R.color.color_indigo));
                    } else if (mOrderDetail.getStatus() == 7) {
                        status.setText("Order Closed");
                        status.setTextColor(getResources().getColor(R.color.black));
                    } else {
                        statusView.setVisibility(View.GONE);
                    }

                    qty.setText("Total Quantity- " + mOrderDetail.getQuantity());

                    totalAmt.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(mOrderDetail.getTotalDebit() + ""));
                    totalPrice.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(mOrderDetail.getTotalCost() + ""));
                    totalMrp.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(mOrderDetail.getTotalMRP() + ""));
                    totalShip.setText(mOrderDetail.getTotalShipping() == 0 ? "Free" : ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(mOrderDetail.getTotalShipping() + ""));
                    totalDisc.setText("- " + ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(mOrderDetail.getTotalDiscount() + ""));

                    recyclerView.setAdapter(new OrderListShoppingAdapter(mOrderDetail.getOrderDetailList(), OrderDetailShoppingActivity.this));

                } else {
                    scrollView.setVisibility(View.GONE);
                    noDataView.setVisibility(View.VISIBLE);
                    noNetworkView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(int error) {
                if (error == 1) {
                    scrollView.setVisibility(View.GONE);
                    noDataView.setVisibility(View.GONE);
                    noNetworkView.setVisibility(View.VISIBLE);
                } else {
                    scrollView.setVisibility(View.GONE);
                    noDataView.setVisibility(View.VISIBLE);
                    noNetworkView.setVisibility(View.GONE);
                }
            }
        });
    }

    void setAddressData(Address mAddressData) {
        if (mAddressData != null && mAddressData.getId() != 0) {
            addressView.setVisibility(View.VISIBLE);
            address.setText(mAddressData.getAddress() + "");

            String city_state = "";
            if (mAddressData.getCity() != null && !mAddressData.getCity().isEmpty()) {
                city_state = mAddressData.getCity();
            }
            if (mAddressData.getState() != null && !mAddressData.getState().isEmpty()) {
                if (!city_state.isEmpty()) {
                    city_state = city_state + ", " + mAddressData.getState();
                } else {
                    city_state = mAddressData.getState();
                }
            }
            city.setText(city_state + " - " + mAddressData.getPinCode());

            mobile.setText(mAddressData.getMobileNo() + "");
            title.setText(mAddressData.getTitle() + "");
            deliverToName.setText(mAddressData.getCustomerName() + "");
        } else {
            addressView.setVisibility(View.GONE);
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
