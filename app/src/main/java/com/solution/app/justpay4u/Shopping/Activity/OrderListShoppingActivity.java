package com.solution.app.justpay4u.Shopping.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Shopping.Object.OrderDetail;
import com.solution.app.justpay4u.Api.Shopping.Response.OrderListResponse;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.OrderListSectionAdapter;
import com.solution.app.justpay4u.Util.CustomLoader;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class OrderListShoppingActivity extends AppCompatActivity {

    private final int CART_DELETE_REQUEST = 4872;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    RecyclerView recyclerView;
    private CustomLoader loader;
    private SectionedRecyclerViewAdapter sectionedAdapter;
    private int mCartItemCount;
    private TextView textCartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_order_list);
            sectionedAdapter = new SectionedRecyclerViewAdapter();
            findViews();
        });


    }


    void findViews() {

        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Order is not available.");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /*mOrderListAdapter = new OrderListAdapter(mOrderList, this, object -> {
            OrderDetail mOrderDetail = (OrderDetail) object;
            startActivity(new Intent(this, OrderDetailActivity.class)
                    .putExtra("OrderId", mOrderDetail.getOrderId())
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });
        recyclerView.setAdapter(mOrderListAdapter);*/
        retryBtn.setOnClickListener(v ->
                getOrderList(this)
        );
        getOrderList(this);

    }


    public void getOrderList(Activity mContext) {


        if (loader != null&&!loader.isShowing()) {
            loader.show();
        }

        ApiShoppingUtilMethods.INSTANCE.getOrderList(mContext, 0, loader, new ApiShoppingUtilMethods.ApiResponseCallBack() {
            @Override
            public void onSucess(Object object) {
                OrderListResponse mOrderListResponse = (OrderListResponse) object;
                if (mOrderListResponse != null && mOrderListResponse.getOrderDetail() != null && mOrderListResponse.getOrderDetail().size() > 0) {

                    noDataView.setVisibility(View.GONE);
                    noNetworkView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    for (OrderDetail mOrderDetail : mOrderListResponse.getOrderDetail()) {
                        sectionedAdapter.addSection(new OrderListSectionAdapter(OrderListShoppingActivity.this, mOrderDetail));
                    }
                    recyclerView.setAdapter(sectionedAdapter);
                                /*mOrderList.clear();
                                mOrderList.addAll(mOrderListResponse.getOrderDetail().get(0).getOrderDetailList());
                                mOrderListAdapter.notifyDataSetChanged();*/

                } else {
                    recyclerView.setVisibility(View.GONE);
                    noDataView.setVisibility(View.VISIBLE);
                    noNetworkView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(int error) {
                if (error == 1) {
                    recyclerView.setVisibility(View.GONE);
                    noDataView.setVisibility(View.GONE);
                    noNetworkView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    noDataView.setVisibility(View.VISIBLE);
                    noNetworkView.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping, menu);
        menu.getItem(0).setVisible(true);
        menu.getItem(1).setVisible(false);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount =  actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    private void setupBadge() {
        mCartItemCount = ApiShoppingUtilMethods.INSTANCE.getCartCount(this);
        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_search) {
            startActivity(new Intent(this, SearchResultShoppingActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

        } else if (id == R.id.action_cart) {

            Intent intent = new Intent(this, CartDetailShoppingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, CART_DELETE_REQUEST);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CART_DELETE_REQUEST && resultCode == RESULT_OK) {
            mCartItemCount = ApiShoppingUtilMethods.INSTANCE.getCartCount(this);
            setupBadge();
        }
    }


}
