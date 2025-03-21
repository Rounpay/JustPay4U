package com.solution.app.justpay4u.Shopping.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Shopping.Object.Address;
import com.solution.app.justpay4u.Api.Shopping.Object.CartDetail;
import com.solution.app.justpay4u.Api.Shopping.Request.PlaceOrderRequest;
import com.solution.app.justpay4u.Api.Shopping.Response.AddressListResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.CartDetailResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.PlaceOrderResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.ShoppingEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.AddMoneyActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.CartListShoppingAdapter;
import com.solution.app.justpay4u.Shopping.Interfaces.ShoppingSelectAddress;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class CartDetailShoppingActivity extends AppCompatActivity {

    private final int INTENT_ADD_ADDRESS = 6291;
    View noDataView, noNetworkView, retryBtn, addressView, addressDetailView, addressAddView, walletDetailView;
    TextView errorMsg, priceDetailLabel, totalMrp, totalPrice, totalDisc, totalShip, totalAmt, pWalletLabel, sWalletLabel, pWalletAmt, sWalletAmt;
    RecyclerView recyclerView;
    TextView placeBtn, address, city, mobile, title, deliverToName, changeBtn;
    NestedScrollView scrollView;
    ArrayList<CartDetail> mProduct = new ArrayList<>();
    CustomAlertDialog mCustomAlertDialog;
    View loadingView;
    ProgressBar progressBar;
    ImageView statusIcon, closeIv;
    TextView loadingMsg, noticeMsg, orderViewBtn;
    private CustomLoader loader;
    private CartListShoppingAdapter mCartListAdapter;
    private AddressListResponse mAddressListResponse;
    private int addressId;
    private int orderId;
    private int cartCount;
    private double totalPaybleAmt;
    private BalanceResponse balanceCheckResponse;
    private int INTENT_ADD_MONEY = 8765;
    private AppPreferences mAppPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_cart_detail);
            mCustomAlertDialog = new CustomAlertDialog(this);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            findViews();
            getBalanceData();
        });
    }

    void findViews() {

        loadingView = findViewById(R.id.loadingView);
        progressBar = findViewById(R.id.progressBar);
        statusIcon = findViewById(R.id.statusIcon);
        closeIv = findViewById(R.id.closeIv);
        loadingMsg = findViewById(R.id.loadingMsg);
        noticeMsg = findViewById(R.id.noticeMsg);
        orderViewBtn = findViewById(R.id.orderViewBtn);

        addressView = findViewById(R.id.addressView);
        addressDetailView = findViewById(R.id.addressDetailView);
        addressAddView = findViewById(R.id.addressAddView);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        mobile = findViewById(R.id.mobile);
        title = findViewById(R.id.title);
        deliverToName = findViewById(R.id.deliverToName);
        changeBtn = findViewById(R.id.changeBtn);

        priceDetailLabel = findViewById(R.id.priceDetailLabel);
        totalMrp = findViewById(R.id.totalMrp);
        totalPrice = findViewById(R.id.totalPrice);
        totalDisc = findViewById(R.id.totalDisc);
        totalShip = findViewById(R.id.totalShip);
        totalAmt = findViewById(R.id.totalAmt);
        pWalletLabel = findViewById(R.id.pWalletLabel);
        sWalletLabel = findViewById(R.id.sWalletLabel);
        pWalletAmt = findViewById(R.id.pWalletAmt);
        sWalletAmt = findViewById(R.id.sWalletAmt);
        walletDetailView = findViewById(R.id.walletDetailView);
        placeBtn = findViewById(R.id.placeBtn);
        scrollView = findViewById(R.id.scrollView);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Cart is empty.");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCartListAdapter = new CartListShoppingAdapter(mProduct, this);
        recyclerView.setAdapter(mCartListAdapter);

        retryBtn.setOnClickListener(v ->
                getCartDetails(this, true)
        );

        orderViewBtn.setOnClickListener(v -> {
            loadingView.setVisibility(View.GONE);
            startActivity(new Intent(this, OrderDetailShoppingActivity.class)
                    .putExtra("OrderId", orderId)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });

        closeIv.setOnClickListener(v -> loadingView.setVisibility(View.GONE));

        placeBtn.setOnClickListener(v -> {
            if (addressId != 0) {
                if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                        && balanceCheckResponse.getBalanceData().size() > 0 && totalPaybleAmt >
                        balanceCheckResponse.getBalanceData().get(0).getBalance()) {

                    mCustomAlertDialog = new CustomAlertDialog(CartDetailShoppingActivity.this);
                    mCustomAlertDialog.WarningWithCallBack("You have insufficient balance, please add money before placing this order",
                            "Add Money", true, new CustomAlertDialog.DialogCallBack() {
                                @Override
                                public void onPositiveClick() {
                                    startActivityForResult(new Intent(CartDetailShoppingActivity.this, AddMoneyActivity.class)
                                            .putExtra("isFromCart", true)
                                            .putExtra("AMOUNT", (totalPaybleAmt - balanceCheckResponse.getBalanceData().get(0).getBalance()))
                                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_ADD_MONEY);
                                }

                                @Override
                                public void onNegativeClick() {

                                }
                            });

                } else {
                    placeOrder(this);
                }

            } else {
                mCustomAlertDialog = new CustomAlertDialog(CartDetailShoppingActivity.this);
                mCustomAlertDialog.WarningWithCallBack("Please add shipping address to place this order",
                        "Add Address", true, new CustomAlertDialog.DialogCallBack() {
                            @Override
                            public void onPositiveClick() {
                                startActivityForResult(new Intent(CartDetailShoppingActivity.this, AddAddressShoppingActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_ADD_ADDRESS);
                            }

                            @Override
                            public void onNegativeClick() {

                            }
                        });
            }
        });

        findViewById(R.id.addAddressBtn).setOnClickListener(v -> startActivityForResult(new Intent(CartDetailShoppingActivity.this, AddAddressShoppingActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_ADD_ADDRESS));


        changeBtn.setOnClickListener(v -> {
            if (mAddressListResponse != null) {
                if (mAddressListResponse.getAddresses() != null && mAddressListResponse.getAddresses().size() > 0) {
                    mCustomAlertDialog.openAddressListDialog(CartDetailShoppingActivity.this, mAddressListResponse.getAddresses(),
                            addressId, INTENT_ADD_ADDRESS, new ShoppingSelectAddress() {
                                @Override
                                public void onSelect(Address mAddress) {
                                    setAddressData(mAddress);
                                }
                            });
                } else {
                    mCustomAlertDialog = new CustomAlertDialog(CartDetailShoppingActivity.this);
                    mCustomAlertDialog.WarningWithCallBack("Please add shipping address to place this order",
                            "Add Address", true, new CustomAlertDialog.DialogCallBack() {
                                @Override
                                public void onPositiveClick() {
                                    startActivityForResult(new Intent(CartDetailShoppingActivity.this, AddAddressShoppingActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_ADD_ADDRESS);
                                }

                                @Override
                                public void onNegativeClick() {

                                }
                            });
                }
            } else {
                getAddressList(this, true);
            }

        });
        getCartDetails(this, true);

    }

    private void getBalanceData() {


        balanceCheckResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
        if (balanceCheckResponse == null || balanceCheckResponse != null && balanceCheckResponse.getBalanceData() == null) {

            ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null,
                    ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences), ApiShoppingUtilMethods.INSTANCE.mDeviceId,
                    ApiShoppingUtilMethods.INSTANCE.mDeviceSerialNum, mAppPreferences, null, object ->
                            balanceCheckResponse = (BalanceResponse) object);
        }


    }


    void getCartDetails(Activity mContext, boolean isLoaderShow) {
        if (loader != null && !loader.isShowing() && isLoaderShow) {
            loader.show();
        }
        ApiShoppingUtilMethods.INSTANCE.getCartDetails(mContext, loader, new ApiShoppingUtilMethods.ApiResponseCallBack() {
            @Override
            public void onSucess(Object object) {
                ApplicationConstant.INSTANCE.isCartChange = true;
                CartDetailResponse mCartDetailResponse = (CartDetailResponse) object;
                if (mCartDetailResponse != null && mCartDetailResponse.getCartDetail() != null &&
                        mCartDetailResponse.getCartDetail().size() > 0) {
                    noDataView.setVisibility(View.GONE);
                    noNetworkView.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    placeBtn.setVisibility(View.VISIBLE);
                    if (cartCount != mCartDetailResponse.getQuantity()) {
                        cartCount = mCartDetailResponse.getQuantity();
                        ApiShoppingUtilMethods.INSTANCE.setCartCount(CartDetailShoppingActivity.this, mCartDetailResponse.getQuantity());
                        setResult(RESULT_OK);
                    }
                    setAddressData(mCartDetailResponse.getAddress());
                    int walletCount = 0;
                    if (mCartDetailResponse.getpWallet() != null && !mCartDetailResponse.getpWallet().isEmpty()
                            && mCartDetailResponse.getpDeduction() != 0) {
                        pWalletLabel.setVisibility(View.VISIBLE);
                        pWalletAmt.setVisibility(View.VISIBLE);
                        walletCount++;
                        pWalletLabel.setText("From " + mCartDetailResponse.getpWallet() + " Wallet");
                        pWalletAmt.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(mCartDetailResponse.getpDeduction() + ""));

                    } else {
                        pWalletLabel.setVisibility(View.GONE);
                        pWalletAmt.setVisibility(View.GONE);
                    }


                    if (mCartDetailResponse.getsWallet() != null && !mCartDetailResponse.getsWallet().isEmpty()
                            && mCartDetailResponse.getsDeduction() != 0) {
                        sWalletLabel.setVisibility(View.VISIBLE);
                        sWalletAmt.setVisibility(View.VISIBLE);
                        walletCount++;
                        sWalletLabel.setText("From " + mCartDetailResponse.getsWallet() + " Wallet");
                        sWalletAmt.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(mCartDetailResponse.getsDeduction() + ""));

                    } else {
                        sWalletLabel.setVisibility(View.GONE);
                        sWalletAmt.setVisibility(View.GONE);
                    }

                    if (walletCount > 1) {
                        walletDetailView.setVisibility(View.VISIBLE);
                    } else {
                        walletDetailView.setVisibility(View.GONE);
                    }
                    totalPaybleAmt = mCartDetailResponse.getTotalCost() + mCartDetailResponse.getShippingCharge();
                    totalAmt.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees((mCartDetailResponse.getTotalCost() + mCartDetailResponse.getShippingCharge()) + ""));
                    totalPrice.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(mCartDetailResponse.getTotalCost() + ""));
                    totalMrp.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(mCartDetailResponse.getTotalMRP() + ""));
                    totalShip.setText(mCartDetailResponse.getShippingCharge() == 0 ? "Free" : ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(mCartDetailResponse.getShippingCharge() + ""));
                    totalDisc.setText("- " + ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(mCartDetailResponse.getTotalDiscount() + ""));
                    mProduct.clear();
                    mProduct.addAll(mCartDetailResponse.getCartDetail());
                    mCartListAdapter.notifyDataSetChanged();


                } else {
                    ApiShoppingUtilMethods.INSTANCE.cartProductIdList.clear();
                    ApiShoppingUtilMethods.INSTANCE.setCartCount(CartDetailShoppingActivity.this, 0);
                    placeBtn.setVisibility(View.GONE);
                    scrollView.setVisibility(View.GONE);
                    noDataView.setVisibility(View.VISIBLE);
                    noNetworkView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(int error) {
                if (error == 1) {
                    placeBtn.setVisibility(View.GONE);
                    scrollView.setVisibility(View.GONE);
                    noDataView.setVisibility(View.GONE);
                    noNetworkView.setVisibility(View.VISIBLE);
                } else {
                    placeBtn.setVisibility(View.GONE);
                    scrollView.setVisibility(View.GONE);
                    noDataView.setVisibility(View.VISIBLE);
                    noNetworkView.setVisibility(View.GONE);
                }
            }
        });

    }


    void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        statusIcon.setVisibility(View.GONE);
        loadingMsg.setVisibility(View.VISIBLE);
        noticeMsg.setVisibility(View.VISIBLE);
        orderViewBtn.setVisibility(View.GONE);
    }

    void showSuccessLoadingView(String msg) {
        progressBar.setVisibility(View.GONE);
        statusIcon.setVisibility(View.VISIBLE);
        loadingMsg.setVisibility(View.VISIBLE);
        loadingMsg.setText(msg != null && !msg.isEmpty() ? msg + "" : "Order Placed Successfully.");
        loadingMsg.setTextColor(getResources().getColor(R.color.gre));
        noticeMsg.setVisibility(View.GONE);
        orderViewBtn.setVisibility(View.VISIBLE);
        statusIcon.setImageResource(R.drawable.ic_check_mark);
       /* Glide.with(this)
                .load(R.drawable.check_mark_success)
                .into(new DrawableImageViewTarget(statusIcon) {
                    @Override
                    public void onResourceReady(Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (resource instanceof GifDrawable) {
                            ((GifDrawable) resource).setLoopCount(1);
                        }
                        super.onResourceReady(resource, transition);
                    }
                });*/
    }

    void showErrorLoadingView(String msg) {
        progressBar.setVisibility(View.GONE);
        statusIcon.setVisibility(View.VISIBLE);
        loadingMsg.setVisibility(View.VISIBLE);
        loadingMsg.setText(msg + "");
        loadingMsg.setTextColor(getResources().getColor(R.color.color_red));
        noticeMsg.setVisibility(View.GONE);
        orderViewBtn.setVisibility(View.GONE);
        statusIcon.setImageResource(R.drawable.ic_cross_mark);
        /*Glide.with(this)

                .load(R.drawable.cross_mark_failure)
                .into(new DrawableImageViewTarget(statusIcon) {
                    @Override
                    public void onResourceReady(Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (resource instanceof GifDrawable) {
                            ((GifDrawable) resource).setLoopCount(1);
                        }
                        super.onResourceReady(resource, transition);
                    }
                });*/
    }


    private void placeOrder(final Activity context) {
        try {
            showLoadingView();
            ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);

            Call<PlaceOrderResponse> call = git.PlaceOrder(new PlaceOrderRequest(addressId,
                    ApiShoppingUtilMethods.INSTANCE.mUserID, ApiShoppingUtilMethods.INSTANCE.mLoginTypeID + "",
                    ApplicationConstant.INSTANCE.APP_ID, ApiShoppingUtilMethods.INSTANCE.mDeviceId,
                    "", BuildConfig.VERSION_NAME, ApiShoppingUtilMethods.INSTANCE.mDeviceSerialNum,
                    ApiShoppingUtilMethods.INSTANCE.mSessionID, ApiShoppingUtilMethods.INSTANCE.mSession));

            call.enqueue(new Callback<PlaceOrderResponse>() {

                @Override
                public void onResponse(Call<PlaceOrderResponse> call, retrofit2.Response<PlaceOrderResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            ApplicationConstant.INSTANCE.isCartChange = true;
                            PlaceOrderResponse mPlaceOrderResponse = response.body();
                            if (mPlaceOrderResponse != null) {

                                if (mPlaceOrderResponse.getStatuscode() == 1) {
                                    orderId = mPlaceOrderResponse.getOrderId();
                                    //UtilMethods.INSTANCE.cartProductIdList.clear();
                                    showSuccessLoadingView(mPlaceOrderResponse.getMsg() + "");
                                    getCartDetails(context, false);
                                } else {
                                    showErrorLoadingView(mPlaceOrderResponse.getMsg() + "");
                                    ApiShoppingUtilMethods.INSTANCE.Error(context, mPlaceOrderResponse.getMsg() + "");
                                }

                            } else {
                                loadingView.setVisibility(View.GONE);
                                ApiShoppingUtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            loadingView.setVisibility(View.GONE);
                            ApiShoppingUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                        }


                    } catch (Exception e) {
                        loadingView.setVisibility(View.GONE);
                        ApiShoppingUtilMethods.INSTANCE.Error(context, e.getMessage() + "");
                    }
                }

                @Override
                public void onFailure(Call<PlaceOrderResponse> call, Throwable t) {
                    loadingView.setVisibility(View.GONE);
                    try {

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            ApiShoppingUtilMethods.INSTANCE.NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ApiShoppingUtilMethods.INSTANCE.ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
                        } else {

                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ApiShoppingUtilMethods.INSTANCE.ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                ApiShoppingUtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        ApiShoppingUtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            loadingView.setVisibility(View.GONE);
            ApiShoppingUtilMethods.INSTANCE.Error(context, e.getMessage() + "");
        }

    }


    void setAddressData(Address mAddressData) {
        if (mAddressData != null && mAddressData.getId() != 0) {
            addressDetailView.setVisibility(View.VISIBLE);
            addressAddView.setVisibility(View.GONE);
            addressId = mAddressData.getId();
            address.setText(mAddressData.getAddressOnly() + "");

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
            addressId = 0;
            addressDetailView.setVisibility(View.GONE);
            addressAddView.setVisibility(View.VISIBLE);
        }
    }


    void getAddressList(Activity mContext, boolean isFromClick) {
        if (loader != null) {
            loader.show();
        }
        ApiShoppingUtilMethods.INSTANCE.getAddressList(mContext, loader, new ApiShoppingUtilMethods.ApiResponseCallBack() {
            @Override
            public void onSucess(Object object) {

                mAddressListResponse = (AddressListResponse) object;
                if (isFromClick) {
                    if (mAddressListResponse != null && mAddressListResponse.getAddresses() != null &&
                            mAddressListResponse.getAddresses().size() > 0) {
                        mCustomAlertDialog.openAddressListDialog(CartDetailShoppingActivity.this, mAddressListResponse.getAddresses(),
                                addressId, INTENT_ADD_ADDRESS, mAddress -> setAddressData(mAddress));
                    } else {
                        mCustomAlertDialog = new CustomAlertDialog(CartDetailShoppingActivity.this);
                        mCustomAlertDialog.WarningWithCallBack("Please add shipping address to place this order",
                                "Add Address", true, new CustomAlertDialog.DialogCallBack() {
                                    @Override
                                    public void onPositiveClick() {
                                        startActivityForResult(new Intent(CartDetailShoppingActivity.this, AddAddressShoppingActivity.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_ADD_ADDRESS);
                                    }

                                    @Override
                                    public void onNegativeClick() {

                                    }
                                });
                    }
                }
            }

            @Override
            public void onError(int error) {

            }
        });

    }

    public void changeQuantity(int quantity, int itemId, int position, TextView btnTv, TextView quantityTv) {
        if (loader != null) {
            loader.show();
        }
        ApiShoppingUtilMethods.INSTANCE.cahangeQuantity(CartDetailShoppingActivity.this, quantity, itemId, loader, new ApiShoppingUtilMethods.ApiResponseCallBack() {
            @Override
            public void onSucess(Object object) {
                BasicResponse mBasicResponse = (BasicResponse) object;
                if (mBasicResponse != null) {
                    quantityTv.setText(quantity + "");
                    setResult(RESULT_OK);
                    getCartDetails(CartDetailShoppingActivity.this, false);

                    if (quantity <= 1) {
                        btnTv.setClickable(false);
                        btnTv.setTextColor(getResources().getColor(R.color.light_grey));
                    } else {
                        btnTv.setClickable(true);
                        btnTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                } else {
                    btnTv.setClickable(false);
                    btnTv.setTextColor(getResources().getColor(R.color.light_grey));
                }
            }

            @Override
            public void onError(int error) {

            }
        });

    }


    public void remove(int itemId, int productId, int position) {
        if (loader != null) {
            loader.show();
        }
        ApiShoppingUtilMethods.INSTANCE.RemoveFromCart(CartDetailShoppingActivity.this, itemId, loader, new ApiShoppingUtilMethods.ApiResponseCallBack() {
            @Override
            public void onSucess(Object object) {
                BasicResponse mBasicResponse = (BasicResponse) object;
                if (mBasicResponse != null) {
                    setResult(RESULT_OK);
                    getCartDetails(CartDetailShoppingActivity.this, true);
                    ApiShoppingUtilMethods.INSTANCE.cartProductIdList.remove(Integer.valueOf(productId));
                }
            }

            @Override
            public void onError(int error) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_ADD_ADDRESS && resultCode == RESULT_OK/* && data != null*/) {
           /* Address mAddress = (Address) data.getSerializableExtra("Address");
            setAddressData(mAddress);*/
            getCartDetails(this, true);
            getAddressList(this, false);
        } else if (requestCode == INTENT_ADD_MONEY && resultCode == RESULT_OK) {
            loader.show();


            ApiFintechUtilMethods.INSTANCE.Balancecheck(this, loader,
                    ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences), ApiShoppingUtilMethods.INSTANCE.mDeviceId,
                    ApiShoppingUtilMethods.INSTANCE.mDeviceSerialNum, mAppPreferences, null, object -> {
                        balanceCheckResponse = (BalanceResponse) object;
                        if (balanceCheckResponse.getBalanceData().get(0).getBalance() >= totalPaybleAmt) {
                            placeOrder(this);
                        }
                    });

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
