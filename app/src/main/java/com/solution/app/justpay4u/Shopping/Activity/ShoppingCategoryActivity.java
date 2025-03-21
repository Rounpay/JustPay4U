package com.solution.app.justpay4u.Shopping.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Shopping.Object.DashbaordInfoMenu;
import com.solution.app.justpay4u.Api.Shopping.Response.DashbaordInfoResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.MenuListShoppingAdapter;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCategoryActivity extends AppCompatActivity {


    public HashMap<Integer, DashbaordInfoMenu> manuCategoryMap = new HashMap<>();
    DrawerLayout mDrawerLayout;
    View subChild;
    boolean isCallApi;
    boolean isNetworkErrorVisible = false;

    List<DashbaordInfoMenu> drawerDashbaordInfoMenuList = new ArrayList<>();
    CustomLoader mCustomLoader;


    private int mCartItemCount;
    private MenuListShoppingAdapter menuListAdapter;
    private TextView textCartItemCount;
    private DashbaordInfoResponse mDashbaordInfoResponse;


    private AppPreferences mAppPreferences;
    private LoginResponse mLoginDataResponse;
    private boolean isNotFirst;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomLoader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        mCustomLoader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_category);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            ApiShoppingUtilMethods.INSTANCE.mDeviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            ApiShoppingUtilMethods.INSTANCE.mDeviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            ApiShoppingUtilMethods.INSTANCE.mLoginTypeID = mLoginDataResponse.getData().getLoginTypeID();
            ApiShoppingUtilMethods.INSTANCE.mWebsiteID = mLoginDataResponse.getData().getWid();
            ApiShoppingUtilMethods.INSTANCE.mSessionID = mLoginDataResponse.getData().getSessionID();
            ApiShoppingUtilMethods.INSTANCE.mSession = mLoginDataResponse.getData().getSession();
            ApiShoppingUtilMethods.INSTANCE.mUserID = mLoginDataResponse.getData().getUserID();
            ApiShoppingUtilMethods.INSTANCE.mMobileNumber = mLoginDataResponse.getData().getMobileNo();


            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            drawerDashbaordInfoMenuList = getIntent().getParcelableArrayListExtra("MenuData");
            if (drawerDashbaordInfoMenuList != null && drawerDashbaordInfoMenuList.size() > 0) {
                menuListAdapter = new MenuListShoppingAdapter(this, drawerDashbaordInfoMenuList);
                recyclerView.setAdapter(menuListAdapter);
                mCustomLoader.dismiss();
            } else {
                refresData();
            }

        });


    }


    void onClick() {




    /*    rlSubCatBack.setOnClickListener(v -> {
            // Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_from_left);
            subChild.setVisibility(View.GONE);
            llMainCat.setVisibility(View.VISIBLE);

            //llMainCat.startAnimation(animation1);
        });*/


    }


    void refresData() {
        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            if (!mCustomLoader.isShowing()) {
                mCustomLoader.show();
            }


            ApiShoppingUtilMethods.INSTANCE.GetDashboardInfo(ShoppingCategoryActivity.this, new ApiShoppingUtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(Object response) {
                    mDashbaordInfoResponse = (DashbaordInfoResponse) response;

                    mCustomLoader.dismiss();
                    manageDrawerMenuData();


                }

                @Override
                public void onError(int ErrorType, String msg) {
                    mCustomLoader.dismiss();
                    if (ErrorType == ApiShoppingUtilMethods.INSTANCE.ERRORR_NETWORK) {
                        isNetworkErrorVisible = true;

                        networkErrorSnackBar();
                    } else {
                        isNetworkErrorVisible = false;
                    }

                }
            });


        } else {
            isNetworkErrorVisible = true;
            networkErrorSnackBar();
            mCustomLoader.dismiss();

        }
    }


    void networkErrorSnackBar() {

        Snackbar mSnackBar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.err_msg_network),
                Snackbar.LENGTH_INDEFINITE).setAction("Retry",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(ShoppingCategoryActivity.this)) {
                            isCallApi = true;
                            onResume();
                        } else {
                            networkErrorSnackBar();
                        }
                    }
                });

        mSnackBar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        TextView mainTextView = (mSnackBar.getView()).
                findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen._12sdp));
        mainTextView.setMaxLines(4);
        mSnackBar.show();
    }


    private void manageDrawerMenuData() {

        drawerDashbaordInfoMenuList.clear();
        if (mDashbaordInfoResponse != null && mDashbaordInfoResponse.getDashbaordInfoData() != null &&
                mDashbaordInfoResponse.getDashbaordInfoData().getMenus() != null &&
                mDashbaordInfoResponse.getDashbaordInfoData().getMenus().size() > 0) {
            drawerDashbaordInfoMenuList.addAll(mDashbaordInfoResponse.getDashbaordInfoData().getMenus());
            if (drawerDashbaordInfoMenuList != null && drawerDashbaordInfoMenuList.size() > 0) {
                menuListAdapter = new MenuListShoppingAdapter(this, drawerDashbaordInfoMenuList);
                recyclerView.setAdapter(menuListAdapter);
                mCustomLoader.dismiss();
            }
            manuCategoryMap = new HashMap<>();
            for (DashbaordInfoMenu item : drawerDashbaordInfoMenuList) {
                manuCategoryMap.put(item.getMainCategoryID(), item);
            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        /*UtilMethods.INSTANCE.BalancecheckNew(this, mCustomAlertDialog, mChangePassUtils, null);*/

        if (isNotFirst) {
            mCartItemCount = ApiShoppingUtilMethods.INSTANCE.getCartCount(this);
            setupBadge();
        }
        isNotFirst = true;
        if (isNetworkErrorVisible) {
            isNetworkErrorVisible = false;

            refresData();
        }

    }







   /* void setBalanceAmount() {

        String normalWalletBalance = pref.get(getString(R.string.wallet_balance));
        String rechargeWalletBalance = pref.get(getString(R.string.recharge_balance));

        normalWalletView.setVisibility(View.GONE);
        balanceWalletView.setVisibility(View.VISIBLE);
        if (normalWalletBalance != null && !normalWalletBalance.isEmpty()) {
            balanceTv.setText("\u20B9 " + normalWalletBalance);
            shoppingWalletTv.setText("\u20B9 " + normalWalletBalance);
        } else {
            balanceTv.setText("\u20B9 0");
            shoppingWalletTv.setText("\u20B9 0");
        }
        if (rechargeWalletBalance != null && !rechargeWalletBalance.isEmpty()) {
            rechargeWalletTv.setText("\u20B9 " + rechargeWalletBalance);
        } else {
            rechargeWalletTv.setText("\u20B9 0");
        }


    }*/

    @Override
    public void onBackPressed() {
/*
        if (mDrawerLayout.isDrawerOpen(sideList)) {
            if (subChild.getVisibility() == View.VISIBLE) {
                rlSubCatBack.performClick();
            } else {
                mDrawerLayout.closeDrawer(sideList);
            }
        } else {*/
        super.onBackPressed();
            /*if (exit_check == 0) {
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
                exit_check = 1;
            } else {
                finish();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }
            }*/

        /* }*/
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping, menu);
        menu.getItem(0).setVisible(true);
        menu.getItem(1).setVisible(false);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = actionView.findViewById(R.id.cart_badge);
        mCartItemCount = ApiShoppingUtilMethods.INSTANCE.getCartCount(this);
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

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                if (mCartItemCount < 100) {
                    // textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                    textCartItemCount.setText(mCartItemCount + "");
                } else {
                    textCartItemCount.setText("99+");
                }

                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    public void cartUpdate(int count) {
        mCartItemCount = count;
        setupBadge();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_cart) {

            Intent intent = new Intent(ShoppingCategoryActivity.this, CartDetailShoppingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);


        } else if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_search) {
            startActivity(new Intent(this, SearchResultShoppingActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

        }

        return super.onOptionsItemSelected(item);
    }


}
