package com.solution.app.justpay4u.Shopping.Activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.solution.app.justpay4u.Api.Shopping.Object.DeliveryStatus;
import com.solution.app.justpay4u.Api.Shopping.Object.OrderList;
import com.solution.app.justpay4u.Api.Shopping.Response.TrackOrderResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ShoppingEndPointInterface;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.OrderTrackListShoppingAdapter;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class OrderTrackShoppingActivity extends AppCompatActivity {
    private final ArrayList<DeliveryStatus> mDeliveryStatuses = new ArrayList<>();
    OrderTrackListShoppingAdapter mOrderTrackListAdapter;
    RecyclerView mRecyclerView;
    CustomLoader loader;
    OrderList mSubOrderListItem;
    View allDataView;
    private CustomAlertDialog mCustomAlertDialog;
    private View noDataView;
    private View noNetworkView;
    private RelativeLayout llMainCat;
    private ImageView ivProductImage;
    private TextView titleText;
    private TextView priceText;
    private TextView mrpText, filterText, shippingText;
    private LinearLayout discountView;
    private TextView discountText;
    private TextView qtyTxt, orderNo;
    private RequestOptions optionsRectangleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_order_track);
            mSubOrderListItem = (OrderList) getIntent().getSerializableExtra("Product");
            optionsRectangleImage = new RequestOptions()
                    .fitCenter()
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .timeout(65000)
                    .placeholder(R.drawable.placeholder_square)
                    .error(R.drawable.defaultlogo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            loader.setCancelable(false);
            mCustomAlertDialog = new CustomAlertDialog(this);

            allDataView = findViewById(R.id.allDataView);
            View retryBtn = findViewById(R.id.retryBtn);
            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            TextView errorMsg = findViewById(R.id.errorMsg);
            errorMsg.setText("No Order Track found.");
            llMainCat = findViewById(R.id.ll_main_cat);
            ivProductImage =  findViewById(R.id.iv_product_image);
            titleText =  findViewById(R.id.tv_description);
            priceText =  findViewById(R.id.tv_orignal_price);
            mrpText =  findViewById(R.id.tv_mrp);
            discountView =  findViewById(R.id.discountView);
            discountText =  findViewById(R.id.discountText);
            shippingText =  findViewById(R.id.tv_shipping);
            filterText = findViewById(R.id.subDescription);
            qtyTxt = findViewById(R.id.qtyTxt);
            orderNo = findViewById(R.id.orderNo);
            mRecyclerView = findViewById(R.id.recyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mOrderTrackListAdapter = new OrderTrackListShoppingAdapter(this, mDeliveryStatuses);
            mRecyclerView.setAdapter(mOrderTrackListAdapter);

            setProductData();
            getOrderTrackApi();
            retryBtn.setOnClickListener(v -> {
                hideShowErrorView(View.GONE, View.GONE);
                getOrderTrackApi();
            });
        });
    }

    void setProductData() {
        if (mSubOrderListItem != null) {
            orderNo.setText("Order No - " + mSubOrderListItem.getOrderNo());
            if (mSubOrderListItem.getDiscount() == 0) {
                discountView.setVisibility(View.GONE);
                mrpText.setVisibility(View.GONE);
            } else {
                discountView.setVisibility(View.VISIBLE);
                mrpText.setVisibility(View.VISIBLE);

                mrpText.setText("MRP: \u20B9 " + ApiShoppingUtilMethods.INSTANCE.formatedAmount(mSubOrderListItem.getMrp() + ""));
                mrpText.setPaintFlags(mrpText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                if (mSubOrderListItem.isDiscountType()) {
                    discountText.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmount(mSubOrderListItem.getDiscount() + "") + "% Off");
                } else {
                    discountText.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees((mSubOrderListItem.getDiscount() ) + "") + " Off");
                }
                /*discountText.setText("\u20B9 " + ApiShoppingUtilMethods.INSTANCE.formatedAmount(mSubOrderListItem.getDiscount() + "") + " Off");*/
            }
            shippingText.setText("Shipping Charge: " + ApiShoppingUtilMethods.INSTANCE.formatedAmount(mSubOrderListItem.getShippingAmount() + ""));
            titleText.setText(mSubOrderListItem.getTitle());
            qtyTxt.setText("Qty: " + mSubOrderListItem.getQty());
            priceText.setText("\u20B9 " + ApiShoppingUtilMethods.INSTANCE.formatedAmount(mSubOrderListItem.getUnitAmount() + ""));
            if (mSubOrderListItem.getSetName() != null && !mSubOrderListItem.getSetName().isEmpty()) {
                filterText.setVisibility(View.VISIBLE);
                filterText.setText(mSubOrderListItem.getSetName().trim());
            } else {
                filterText.setVisibility(View.GONE);
            }
            Glide.with(this)
                    .load(mSubOrderListItem.getFrontImage())
                    .thumbnail(0.5f)
                    .apply(optionsRectangleImage)
                    .into(ivProductImage);


            llMainCat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mSubOrderListItem.getPosId() != 0) {
                        Intent intent = new Intent(OrderTrackShoppingActivity.this, ItemDetailsShoppingActivity.class);
                        intent.putExtra("ProductId", mSubOrderListItem.getPosId() + "");
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }


                }
            });
        }

    }


    public void getOrderTrackApi() {

        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            try {

                if (!loader.isShowing()) {
                    loader.show();
                }
                ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
                Call<TrackOrderResponse> call = git.TrackOrder(mSubOrderListItem.getCourierTrackingNo(), mSubOrderListItem.getOrderNo());
                call.enqueue(new Callback<TrackOrderResponse>() {
                    @Override
                    public void onResponse(Call<TrackOrderResponse> call, final retrofit2.Response<TrackOrderResponse> response) {

                        if (response != null) {
                            try {

                                //loader.dismiss();
                                TrackOrderResponse data = response.body();
                                if (data != null) {
                                    if (data.getStatus()) {
                                        if (data.getDeliveryStatus().size() > 0) {
                                            hideShowErrorView(View.GONE, View.GONE);
                                            allDataView.setVisibility(View.VISIBLE);
                                            mDeliveryStatuses.clear();
                                            mDeliveryStatuses.addAll(data.getDeliveryStatus());
                                            /*Collections.reverse(mDeliveryStatuses);*/
                                            mOrderTrackListAdapter.notifyDataSetChanged();
                                        } else {
                                            mCustomAlertDialog.Error(data.getMessage());
                                            hideShowErrorView(View.VISIBLE, View.GONE);
                                        }
                                    } else {
                                        mCustomAlertDialog.Error(data.getMessage());
                                        hideShowErrorView(View.VISIBLE, View.GONE);
                                    }

                                } else {
                                    hideShowErrorView(View.VISIBLE, View.GONE);
                                }
                                loader.dismiss();
                            } catch (Exception ex) {
                                loader.dismiss();
                                hideShowErrorView(View.VISIBLE, View.GONE);

                            }
                        } else {
                            loader.dismiss();
                            hideShowErrorView(View.VISIBLE, View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<TrackOrderResponse> call, Throwable t) {


                        try {
                            loader.dismiss();
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                                if (t.getMessage().contains("No address associated with hostname")) {
                                    ApiShoppingUtilMethods.INSTANCE.NetworkError(OrderTrackShoppingActivity.this);
                                    hideShowErrorView(View.GONE, View.VISIBLE);

                                } else {
                                    mCustomAlertDialog.Error(t.getMessage());
                                    hideShowErrorView(View.VISIBLE, View.GONE);
                                }

                            } else {
                                mCustomAlertDialog.Error(getString(R.string.some_thing_error));
                                hideShowErrorView(View.VISIBLE, View.GONE);
                            }
                        } catch (IllegalStateException ise) {
                            loader.dismiss();
                            mCustomAlertDialog.Error(ise.getMessage() + "");
                            hideShowErrorView(View.VISIBLE, View.GONE);
                        }
                    }
                });
            } catch (Exception ex) {
                loader.dismiss();
                // loader.dismiss();
                mCustomAlertDialog.Error(ex.getMessage() + "");
                hideShowErrorView(View.VISIBLE, View.GONE);
            }
        } else {
            ApiShoppingUtilMethods.INSTANCE.NetworkError(OrderTrackShoppingActivity.this);
            hideShowErrorView(View.GONE, View.VISIBLE);
        }
    }

    void hideShowErrorView(int noDataViewVisibility, int noNetworkVisibility) {

        noDataView.setVisibility(noDataViewVisibility);
        noNetworkView.setVisibility(noNetworkVisibility);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
