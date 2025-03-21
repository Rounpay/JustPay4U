package com.solution.app.justpay4u.Shopping.Activity;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.core.widget.NestedScrollView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ft.PageIndicatorView;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Shopping.Object.DashbaordInfoBannerProducts;
import com.solution.app.justpay4u.Api.Shopping.Object.DashbaordInfoMenu;
import com.solution.app.justpay4u.Api.Shopping.Object.DashbaordInfoTopCategory;
import com.solution.app.justpay4u.Api.Shopping.Object.DashboardProductListData;
import com.solution.app.justpay4u.Api.Shopping.Response.DashbaordInfoResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.MainCategoryWiseProductResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.NewArrivalAndSaleDataResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.Activities.UserProfileActivity;
import com.solution.app.justpay4u.Fintech.Dashboard.Activity.HomeDashActivity;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.AddMoneyActivity;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.PaymentRequest;
import com.solution.app.justpay4u.Fintech.Info.Activity.InfoActivity;
import com.solution.app.justpay4u.Fintech.Notification.NotificationListActivity;
import com.solution.app.justpay4u.Networking.Activity.NetworkingDashboardActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.DashboardGridShoppingAdapter;
import com.solution.app.justpay4u.Shopping.Adapter.DashboardTopCategoryShoppingAdapter;
import com.solution.app.justpay4u.Shopping.Adapter.SliderImageShoppingAdapter;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.EKYCStepsDialog;
import com.solution.app.justpay4u.Util.LoopingPager.LoopingViewPager;
import com.solution.app.justpay4u.Util.RecyclerViewItemDecoration;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingDashboardActivity extends AppCompatActivity {
    public ArrayList<DashboardProductListData> recentViewProductListData = new ArrayList<>();
    public HashMap<Integer, DashbaordInfoMenu> manuCategoryMap = new HashMap<>();

    boolean isNetworkErrorVisible = false;
    ArrayList<DashbaordInfoTopCategory> mTopCategoryList = new ArrayList<>();
    ArrayList<DashbaordInfoMenu> drawerDashbaordInfoMenuList = new ArrayList<>();
    View loaderView, ll_fundrequest, ll_addmoney;
    CustomLoader mCustomLoader;
    ArrayList<Integer> listSetMainCat = new ArrayList<>();


    private RelativeLayout pagerView;

    private int notificationCount;
    private LinearLayout llSearchBar;
    private View networkErrorView, retryBtn, orderView, wishlistView, addressView, otherOfferView, fintechView, networkingView;
    private LoopingViewPager viewpager;
    private PageIndicatorView pagerIndicator;
    private RecyclerView topCategoryRecyclerView;

    private int mCartItemCount;

    private int exit_check;
    private TextView textCartItemCount;
    private LinearLayout containerView;
    private DashboardTopCategoryShoppingAdapter dashboardTopCategoryAdapter;
    /*private DashboardCategoryShoppingAdapter dashboardNewArrivalCategoryAdapter, dashboardCategoryAdapter;*/
    private DashbaordInfoResponse mDashbaordInfoResponse;
    private NewArrivalAndSaleDataResponse mNewArrivalAndSaleDataResponse;
    private boolean isScrollBottomCategoryApiCalled;
    private boolean isGetWebsiteinfoCalled;
    private boolean isScrollBottomRecentApiCalled;
    private boolean isMainCategoryDataSet;
    private boolean isScrollApiCalling;
    private boolean isNotFirst;
    private int count;
    private int rotation;
    private NestedScrollView scrollView;
    ArrayList<View> productViews = new ArrayList<>();
    ArrayList<Integer> patterList = new ArrayList<>();
    ArrayList<String> listLayoutManagerTypeList = new ArrayList<>();
    private int menuSize;
    private TextView badges_Notification;
    private AppPreferences mAppPreferences;
    private LoginResponse mLoginDataResponse;
    private int INTENT_NOTIFICATIONS = 9435;
    private int INTENT_ADD_MONEY = 6576;
    private int bgDrawable;
    private int scrollProductSize = 4;
    TextView userNameTv, roleTv, mobileNumTv, emailTv, prepaidBalTV;
    ImageView userImage;
    private int INTENT_PROFILE = 4376;
    private RequestOptions requestOptionsUserImage;
    private DropDownDialog mDropDownDialog;
    ArrayList<BalanceData> mBalanceDataList = new ArrayList<>();
    private BalanceResponse balanceCheckResponse;
    ImageView arrowBalanceDown;
    private EKYCStepsDialog mEKYCStepsDialog;
    private boolean isBgNotSet;
    private boolean isFromLogin;
    int fromScreen, fromPreviousScreen; // 1=Fintech,2=Shopping, 3=Networking

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomLoader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        mCustomLoader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_dashboard);
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

            initToolbar();

            findViews();
            onClick();

            new Handler(Looper.myLooper()).post(() -> {
                isFromLogin = getIntent().getBooleanExtra("FROM_LOGIN", false);
                fromScreen = getIntent().getIntExtra("FROM_SCREEN", 0);
                fromPreviousScreen = getIntent().getIntExtra("FROM_PRE_SCREEN", 0);
                showHideBottomBtn(mLoginDataResponse.isMLM(), mLoginDataResponse.isFintechServiceOn());
                mEKYCStepsDialog = new EKYCStepsDialog(this, mLoginDataResponse,
                        ApiShoppingUtilMethods.INSTANCE.mDeviceId, ApiShoppingUtilMethods.INSTANCE.mDeviceSerialNum, mAppPreferences);
                mDropDownDialog = new DropDownDialog(this);
                setUserUiData();
                showBalanceData();

                if (fromScreen == 0) {
                    LocalBroadcastManager.getInstance(this).registerReceiver(mNewNotificationReciver, new IntentFilter("New_Notification_Detect"));
                }

                refresData();
            });
        });


    }


    private void findViews() {

        networkErrorView = findViewById(R.id.networkErrorView);
        retryBtn = findViewById(R.id.retryBtn);
        userNameTv = findViewById(R.id.userNameTv);
        roleTv = findViewById(R.id.roleTv);
        mobileNumTv = findViewById(R.id.mobileNumTv);
        emailTv = findViewById(R.id.emailTv);
        userImage = findViewById(R.id.userImage);
        arrowBalanceDown = findViewById(R.id.arrowBalanceDown);
        prepaidBalTV = findViewById(R.id.prepaidBalTV);
        pagerView = findViewById(R.id.pagerView);
        pagerView.setVisibility(View.GONE);
        llSearchBar = findViewById(R.id.ll_search_bar);
        loaderView = findViewById(R.id.loaderView);
        viewpager = findViewById(R.id.viewpager);
        pagerIndicator = findViewById(R.id.indicator);
        otherOfferView = findViewById(R.id.otherOfferView);
        addressView = findViewById(R.id.addressView);
        orderView = findViewById(R.id.orderView);
        wishlistView = findViewById(R.id.wishlistView);
        fintechView = findViewById(R.id.fintechView);
        ll_fundrequest = findViewById(R.id.ll_fundrequest);
        ll_addmoney = findViewById(R.id.ll_addmoney);
        networkingView = findViewById(R.id.networkingView);
        topCategoryRecyclerView = findViewById(R.id.topCategoryRecyclerView);
        topCategoryRecyclerView.setNestedScrollingEnabled(false);
        topCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dashboardTopCategoryAdapter = new DashboardTopCategoryShoppingAdapter(this, mTopCategoryList);
        topCategoryRecyclerView.setAdapter(dashboardTopCategoryAdapter);


        containerView = findViewById(R.id.containerView);
        scrollView = findViewById(R.id.scrollView);

        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                if (!isScrollApiCalling && !isScrollBottomCategoryApiCalled && isGetWebsiteinfoCalled) {
                    if (mDashbaordInfoResponse.getDashbaordInfoData().getMenus() != null &&
                            mDashbaordInfoResponse.getDashbaordInfoData().getMenus().size() > 0) {

                        rotation = 0;
                        productViews.clear();
                        menuSize = mDashbaordInfoResponse.getDashbaordInfoData().getMenus().size();
                        if (menuSize <= scrollProductSize) {
                            scrollProductSize = menuSize;
                        } else {
                            if (count < menuSize) {
                                if ((menuSize - count) < scrollProductSize) {
                                    scrollProductSize = menuSize - count;
                                }
                            }
                        }

                        String indexStr = "";

                        for (int i = count; i < menuSize; i++) {
                            rotation = rotation + 1;
                            count = count + 1;
                            if (indexStr.isEmpty()) {
                                indexStr = mDashbaordInfoResponse.getDashbaordInfoData().getMenus().get(i).getMainCategoryID() + "";
                            } else {
                                indexStr = indexStr + "," + mDashbaordInfoResponse.getDashbaordInfoData().getMenus().get(i).getMainCategoryID() + "";
                            }
                            if (rotation == scrollProductSize) {
                                break;
                            }

                        }

                        if (!indexStr.isEmpty()) {
                            getMultiMainCategoryWiseProduct(indexStr);
                        } else {
                            if (menuSize == count) {
                                isMainCategoryDataSet = true;
                                isScrollApiCalling = false;
                                if (!isScrollBottomRecentApiCalled) {
                                    getRecentProduct();
                                }
                            } else {
                                isMainCategoryDataSet = false;
                                isScrollApiCalling = false;
                                isScrollBottomCategoryApiCalled = false;
                            }
                        }
                    }
                }
                if (!isScrollApiCalling && isMainCategoryDataSet && !isScrollBottomRecentApiCalled && isGetWebsiteinfoCalled) {
                    getRecentProduct();
                }


            }
        });
    }

    void showHideBottomBtn(boolean isMLM, boolean isFintechServiceOn) {

        if (fromScreen == 3 && fromPreviousScreen == 1) {
            networkingView.setVisibility(View.GONE);
            fintechView.setVisibility(View.GONE);
            orderView.setVisibility(View.VISIBLE);
            wishlistView.setVisibility(View.VISIBLE);
        } else if (fromScreen == 1 && fromPreviousScreen == 3) {
            networkingView.setVisibility(View.GONE);
            fintechView.setVisibility(View.GONE);
            orderView.setVisibility(View.VISIBLE);
            wishlistView.setVisibility(View.VISIBLE);
        } else if (fromScreen == 3 && fromPreviousScreen != 1) {
            networkingView.setVisibility(View.GONE);
            orderView.setVisibility(View.VISIBLE);
            if (isFintechServiceOn) {
                fintechView.setVisibility(View.VISIBLE);
                wishlistView.setVisibility(View.GONE);
            } else {
                fintechView.setVisibility(View.GONE);
                wishlistView.setVisibility(View.VISIBLE);
            }


        } else if (fromScreen == 1 && fromPreviousScreen != 3) {
            if (isMLM) {
                networkingView.setVisibility(View.VISIBLE);
                wishlistView.setVisibility(View.GONE);
            } else {
                networkingView.setVisibility(View.GONE);
                wishlistView.setVisibility(View.VISIBLE);
            }

            fintechView.setVisibility(View.GONE);
            orderView.setVisibility(View.VISIBLE);

        } else {
            if (isMLM && isFintechServiceOn) {
                networkingView.setVisibility(View.VISIBLE);
                fintechView.setVisibility(View.VISIBLE);
                orderView.setVisibility(View.GONE);
                wishlistView.setVisibility(View.GONE);
            } else if (!isMLM && isFintechServiceOn) {
                networkingView.setVisibility(View.GONE);
                fintechView.setVisibility(View.VISIBLE);
                orderView.setVisibility(View.VISIBLE);
                wishlistView.setVisibility(View.GONE);
            } else if (isMLM && !isFintechServiceOn) {
                networkingView.setVisibility(View.VISIBLE);
                fintechView.setVisibility(View.GONE);
                orderView.setVisibility(View.VISIBLE);
                wishlistView.setVisibility(View.GONE);
            } else {
                networkingView.setVisibility(View.GONE);
                fintechView.setVisibility(View.GONE);
                orderView.setVisibility(View.VISIBLE);
                wishlistView.setVisibility(View.VISIBLE);
            }

        }
    }

    private void initToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


    }

    void setUserUiData() {
        userNameTv.setText(mLoginDataResponse.getData().getName() /*+ " - " + mLoginDataResponse.getData().getRoleName()*/);
        roleTv.setText(mLoginDataResponse.getData().getRoleName() + "");
        mobileNumTv.setText(mLoginDataResponse.getData().getMobileNo() + "");
        emailTv.setText(mLoginDataResponse.getData().getEmailID() + "");
        if (requestOptionsUserImage == null) {
            requestOptionsUserImage = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_UserIcon();
        }
        Glide.with(this)
                .load(ApplicationConstant.INSTANCE.baseProfilePicUrl + mLoginDataResponse.getData().getUserID() + ".png")
                .apply(requestOptionsUserImage)
                .into(userImage);

    }

    private void showBalanceData() {

        mBalanceDataList.clear();
        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                && balanceCheckResponse.getBalanceData().size() > 0) {
            mBalanceDataList = balanceCheckResponse.getBalanceData();
            prepaidBalTV.setText(Utility.INSTANCE.formatedAmountWithRupees(mBalanceDataList.get(0).getBalance() + ""));


           /* if (mLoginDataResponse.isAccountStatement()) {
                mBalanceDataList.add(new BalanceData("Outstanding Wallet", balanceCheckResponse.getOsBalance()));
            }*/


            if (mBalanceDataList.size() > 1) {
                arrowBalanceDown.setVisibility(View.VISIBLE);
            } else {
                arrowBalanceDown.setVisibility(View.GONE);
            }
            showHideBottomBtn(balanceCheckResponse.isMLM(), balanceCheckResponse.isFintechServiceOn());
            if (balanceCheckResponse.isUPI() || balanceCheckResponse.isPaymentGatway()) {
                ll_addmoney.setVisibility(View.VISIBLE);
                ll_fundrequest.setVisibility(View.GONE);
            } else {
                ll_addmoney.setVisibility(View.GONE);
                ll_fundrequest.setVisibility(View.VISIBLE);
            }
        } else {


            balanceCheckResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                    && balanceCheckResponse.getBalanceData().size() > 0) {
                showBalanceData();
            } else {
                ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse,
                        ApiShoppingUtilMethods.INSTANCE.mDeviceId, ApiShoppingUtilMethods.INSTANCE.mDeviceSerialNum,
                        mAppPreferences, mEKYCStepsDialog, object -> {
                            balanceCheckResponse = (BalanceResponse) object;
                            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                    && balanceCheckResponse.getBalanceData().size() > 0) {
                                showBalanceData();
                            }
                        });
            }

        }

    }

    void onClick() {
        retryBtn.setOnClickListener(view -> {
            if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(ShoppingDashboardActivity.this)) {
                onResume();
            } else {
                networkErrorView.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.walletView).setOnClickListener(v -> {
            if (mBalanceDataList != null && mBalanceDataList.size() > 0) {
                if (mBalanceDataList.size() > 1) {
                    mDropDownDialog.showDropDownBalanceHomePopup(v, mBalanceDataList, R.layout.dialog_shopping_drop_down_balance_home);
                }
            } else {
                showBalanceData();
            }

        });
        findViewById(R.id.ll_category).setOnClickListener(v -> {

            startActivityForResult(new Intent(this, ShoppingCategoryActivity.class)
                    .putParcelableArrayListExtra("MenuData", drawerDashbaordInfoMenuList)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_PROFILE);
        });
        findViewById(R.id.userDetailView).setOnClickListener(v -> {

            startActivityForResult(new Intent(this, UserProfileActivity.class)
                    .putExtra("IsAddMoneyVisible", true)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_PROFILE);
        });
        ll_addmoney.setOnClickListener(v -> {

            startActivityForResult(new Intent(this, AddMoneyActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_ADD_MONEY);
        });
        ll_fundrequest.setOnClickListener(v -> {

            startActivityForResult(new Intent(this, PaymentRequest.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_ADD_MONEY);
        });

        llSearchBar.setOnClickListener(v ->
                startActivity(new Intent(ShoppingDashboardActivity.this, SearchResultShoppingActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));


        orderView.setOnClickListener(v -> {


            startActivity(new Intent(ShoppingDashboardActivity.this, OrderListShoppingActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));


        });
        wishlistView.setOnClickListener(v -> {


            startActivity(new Intent(ShoppingDashboardActivity.this, WishlistShoppingActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));


        });
        addressView.setOnClickListener(v -> {


            startActivity(new Intent(ShoppingDashboardActivity.this, AddressListShoppingActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));


        });


        otherOfferView.setOnClickListener(v -> {


            startActivity(new Intent(ShoppingDashboardActivity.this, OtherOfferShoppingActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));


        });
        fintechView.setOnClickListener(v -> {
            startActivity(new Intent(ShoppingDashboardActivity.this, HomeDashActivity.class)
                    .putExtra("FROM_LOGIN", isFromLogin)
                    .putExtra("FROM_SCREEN", 2)
                    .putExtra("FROM_PRE_SCREEN", fromScreen)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));


        });
        networkingView.setOnClickListener(v -> {
            startActivity(new Intent(ShoppingDashboardActivity.this, NetworkingDashboardActivity.class)
                    .putExtra("FROM_LOGIN", isFromLogin)
                    .putExtra("FROM_SCREEN", 2)
                    .putExtra("FROM_PRE_SCREEN", fromScreen)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));


        });

        findViewById(R.id.ll_support).setOnClickListener(v ->
                startActivity(new Intent(this, InfoActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));
    }


    void refresData() {
        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            networkErrorView.setVisibility(View.GONE);
            if (!mCustomLoader.isShowing()) {
                mCustomLoader.show();
            }
            ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse,
                    ApiShoppingUtilMethods.INSTANCE.mDeviceId, ApiShoppingUtilMethods.INSTANCE.mDeviceSerialNum,
                    mAppPreferences, mEKYCStepsDialog, object -> {
                        balanceCheckResponse = (BalanceResponse) object;
                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                && balanceCheckResponse.getBalanceData().size() > 0) {
                            showBalanceData();
                        }
                    });
            if (fromScreen == 0) {
                ApiFintechUtilMethods.INSTANCE.GetNotifications(this, mLoginDataResponse, mAppPreferences, ApiShoppingUtilMethods.INSTANCE.mDeviceId, ApiShoppingUtilMethods.INSTANCE.mDeviceSerialNum, object -> {
                    notificationCount = ((int) object);
                    setNotificationCount();
                });
            }
            ApiShoppingUtilMethods.INSTANCE.GetDashboardInfo(ShoppingDashboardActivity.this, new ApiShoppingUtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(Object response) {
                    mDashbaordInfoResponse = (DashbaordInfoResponse) response;

                    productViews.clear();
                    listSetMainCat.clear();
                    loaderView.setVisibility(View.GONE);
                    isMainCategoryDataSet = false;
                    isScrollApiCalling = false;
                    isScrollBottomRecentApiCalled = false;
                    isScrollBottomCategoryApiCalled = false;
                    menuSize = 0;
                    containerView.removeAllViews();
                    count = 0;
                    isGetWebsiteinfoCalled = false;

                    getNewArrivalData();

                    manageDrawerMenuData();
                    manageTopCategoryData();
                    manageSliderImageData();

                    isGetWebsiteinfoCalled = true;

                }

                @Override
                public void onError(int ErrorType, String msg) {
                    loaderView.setVisibility(View.GONE);
                    mCustomLoader.dismiss();
                    if (ErrorType == ApiShoppingUtilMethods.INSTANCE.ERRORR_NETWORK) {
                        isNetworkErrorVisible = true;

                        networkErrorView.setVisibility(View.VISIBLE);
                    } else {
                        isNetworkErrorVisible = false;
                    }
                    ApiShoppingUtilMethods.INSTANCE.Error(ShoppingDashboardActivity.this, msg);
                }
            });


        } else {
            isNetworkErrorVisible = true;
            networkErrorView.setVisibility(View.VISIBLE);
            loaderView.setVisibility(View.GONE);
            mCustomLoader.dismiss();
            ApiShoppingUtilMethods.INSTANCE.Error(ShoppingDashboardActivity.this, getResources().getString(R.string.err_msg_network));

        }
    }

    void getNewArrivalData() {
        if (!mCustomLoader.isShowing()) {
            mCustomLoader.show();
        }
        ApiShoppingUtilMethods.INSTANCE.GetNewArrivalOnSale(ShoppingDashboardActivity.this, new ApiShoppingUtilMethods.ApiHitCallBack() {
            @Override
            public void onSucess(Object response) {
                mNewArrivalAndSaleDataResponse = (NewArrivalAndSaleDataResponse) response;
                // UtilMethods.INSTANCE.setShopNewArrivalDashData(ShoppingDashboardActivity.this, new Gson().toJson(mNewArrivalAndSaleDataResponse));
                setNewArrivalDataAndSaleData();

            }

            @Override
            public void onError(int ErrorType, String msg) {
                mCustomLoader.dismiss();
                if (ErrorType == ApiShoppingUtilMethods.INSTANCE.ERRORR_NETWORK) {
                    isNetworkErrorVisible = true;
                    networkErrorView.setVisibility(View.VISIBLE);
                } else {
                    isNetworkErrorVisible = false;
                }
                ApiShoppingUtilMethods.INSTANCE.Error(ShoppingDashboardActivity.this, msg);
            }
        });

    }

    void getMultiMainCategoryWiseProduct(String mainCatId) {
        isScrollApiCalling = true;
        loaderView.setVisibility(View.VISIBLE);
        scrollView.post(() -> {
            scrollView.fullScroll(View.FOCUS_DOWN);
        });


        ApiShoppingUtilMethods.INSTANCE.GetMultiMainCategoryWiseProducts(ShoppingDashboardActivity.this, mainCatId, 0, new ApiShoppingUtilMethods.ApiHitCallBack() {
            @Override
            public void onSucess(Object response) {

                final MainCategoryWiseProductResponse mMainCategoryWiseProductResponse = (MainCategoryWiseProductResponse) response;
                isScrollBottomCategoryApiCalled = true;
                if (mMainCategoryWiseProductResponse != null && mMainCategoryWiseProductResponse.getMainCategoryWiseProductData() != null &&
                        mMainCategoryWiseProductResponse.getMainCategoryWiseProductData().size() > 0) {

                    for (DashboardProductListData item :
                            mMainCategoryWiseProductResponse.getMainCategoryWiseProductData()) {

                        if (item.getMainCategoryWiseProductList() != null &&
                                item.getMainCategoryWiseProductList().size() > 0 &&
                                !listSetMainCat.contains(item.getMainCategoryId())) {
                            listSetMainCat.add(item.getMainCategoryId());
                            setMultiMainCategoryData(item.getMainCategoryName(), item.getMainCategoryId(),
                                    item.getMainCategoryWiseProductList());

                        }

                    }

                    new Handler(Looper.getMainLooper()).post(() -> {

                        for (View viewItem : productViews) {
                            containerView.addView(viewItem);
                        }

                        if (menuSize == count) {
                            isMainCategoryDataSet = true;
                            isScrollApiCalling = false;
                        } else {
                            isMainCategoryDataSet = false;
                            isScrollApiCalling = false;
                            isScrollBottomCategoryApiCalled = false;
                        }

                        loaderView.setVisibility(View.GONE);
                    });

                }

            }

            @Override
            public void onError(int ErrorType, String msg) {


                if (productViews.size() > 0) {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        for (View viewItem : productViews) {
                            try {
                                containerView.removeView(viewItem);
                            } catch (Exception e) {
                            }
                            containerView.addView(viewItem);
                        }
                        if (menuSize == count) {
                            isMainCategoryDataSet = true;
                            isScrollApiCalling = false;
                        } else {
                            isMainCategoryDataSet = false;
                            isScrollApiCalling = false;
                            isScrollBottomCategoryApiCalled = false;
                        }
                        loaderView.setVisibility(View.GONE);
                    });
                } else {
                    loaderView.setVisibility(View.GONE);
                    isScrollBottomCategoryApiCalled = false;
                    isScrollApiCalling = false;
                    if (ErrorType == ApiShoppingUtilMethods.INSTANCE.ERRORR_NETWORK) {
                        isNetworkErrorVisible = true;

                        networkErrorView.setVisibility(View.VISIBLE);
                    } else {

                        isNetworkErrorVisible = false;
                        //   ApiShoppingUtilMethods.INSTANCE.Error(ShoppingDashboardActivity.this,true, msg);
                    }
                }

            }
        });


    }


    void setMultiMainCategoryData(String title, int mainCategoryId,
                                  ArrayList<DashboardProductListData> mainCategoryWiseProductList) {
        String ViewType = "Horizontal";
        if (listLayoutManagerTypeList.size() >= 3) {
            listLayoutManagerTypeList.clear();
        }
        if (!listLayoutManagerTypeList.contains("Horizontal")) {
            ViewType = "Horizontal";
            listLayoutManagerTypeList.add("Horizontal");
        } else if (!listLayoutManagerTypeList.contains("Grid_Horizontal")) {
            ViewType = "Grid_Horizontal";
            listLayoutManagerTypeList.add("Grid_Horizontal");
        } else if (!listLayoutManagerTypeList.contains("Grid_Vertical")) {
            ViewType = "Grid_Vertical";
            listLayoutManagerTypeList.add("Grid_Vertical");
        }

        int bg = 0;
        int bgcolor = getResources().getColor(R.color.lightWhite);

        if (isBgNotSet) {

            if (patterList.size() >= 8) {
                patterList.clear();
            }

            if (!patterList.contains(1)) {
                patterList.add(1);
                bg = R.drawable.ic_bg_pattern_1;
                bgcolor = getResources().getColor(R.color.pattern_color_1);
            } else if (!patterList.contains(2)) {
                patterList.add(2);
                bg = R.drawable.ic_bg_pattern_2;
                bgcolor = getResources().getColor(R.color.pattern_color_2);
            } else if (!patterList.contains(3)) {
                patterList.add(3);
                bg = R.drawable.ic_bg_pattern_3;
                bgcolor = getResources().getColor(R.color.pattern_color_3);
            } else if (!patterList.contains(4)) {
                patterList.add(4);
                bg = R.drawable.ic_bg_pattern_4;
                bgcolor = getResources().getColor(R.color.pattern_color_4);
            } else if (!patterList.contains(5)) {
                patterList.add(5);
                bg = R.drawable.ic_bg_pattern_5;
                bgcolor = getResources().getColor(R.color.pattern_color_5);
            } else if (!patterList.contains(6)) {
                patterList.add(6);
                bg = R.drawable.ic_bg_pattern_6;
                bgcolor = getResources().getColor(R.color.pattern_color_6);
            } else if (!patterList.contains(7)) {
                patterList.add(7);
                bg = R.drawable.ic_bg_pattern_7;
                bgcolor = getResources().getColor(R.color.pattern_color_7);
            } else if (!patterList.contains(8)) {
                patterList.add(8);
                bg = R.drawable.ic_bg_pattern_8;
                bgcolor = getResources().getColor(R.color.pattern_color_8);
            }

        }
        isBgNotSet = !isBgNotSet;
        setMultiProductData(title, mainCategoryId, ViewType, mainCategoryWiseProductList, bg, bgcolor);

    }


    void setMultiProductData(String title, int mainCategoryId, String viewType,
                             ArrayList<DashboardProductListData> mDashboardProductListItems,
                             int titleBg, int bgcolor) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_shopping_dashboard_product, null);

        View mainView = view.findViewById(R.id.mainView);
        View titleview = view.findViewById(R.id.rl_cat_view);
        mainView.setBackgroundColor(bgcolor);
        titleview.setBackgroundResource(titleBg);

        TextView tvCategory = view.findViewById(R.id.tv_category);
        TextView sub_category = view.findViewById(R.id.sub_category);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        Button viewAllBtn = view.findViewById(R.id.viewAllBtn);


        tvCategory.setText(title + "".trim());
        if (manuCategoryMap != null && manuCategoryMap.containsKey(mainCategoryId)) {
            setSubCategoryString(sub_category, manuCategoryMap.get(mainCategoryId).getCategoryString());
        } else {
            sub_category.setVisibility(View.GONE);
        }
        if (mainCategoryId == 0) {
            viewAllBtn.setVisibility(View.GONE);
        } else {
            viewAllBtn.setVisibility(View.VISIBLE);
        }
        viewAllBtn.setOnClickListener(v -> {

            if (mainCategoryId == -2) {
                startActivity(new Intent(ShoppingDashboardActivity.this, SimilarProductShoppingActivity.class)
                        .putExtra("TITLE", "Recent View Products")
                        .putExtra("SIMILAR_PRODUCT_LIST", recentViewProductListData)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            } else {
                Intent intent = new Intent(ShoppingDashboardActivity.this, MainCategoryDetailShoppingActivity.class);
                intent.putExtra("Id", mainCategoryId);
                intent.putExtra("CategoryId", 0);
                intent.putExtra("isFromNavigationDrawer", false);
                intent.putExtra("CategoryName", title);
                intent.putExtra("CategoryImage", manuCategoryMap.get(mainCategoryId).getMainCategoryImage());
                intent.putParcelableArrayListExtra("CategoryList", manuCategoryMap.get(mainCategoryId).getCategoryList());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        dataparse(viewType, recyclerView, mDashboardProductListItems);
        productViews.add(view);


    }

    void setProductData(String title, int mainCategoryId, String viewType,
                        ArrayList<DashboardProductListData> mDashboardProductListItems,
                        int titleBg, int bgcolor) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_shopping_dashboard_product, null);

        View mainView = view.findViewById(R.id.mainView);
        View titleview = view.findViewById(R.id.rl_cat_view);
        mainView.setBackgroundColor(bgcolor);
        titleview.setBackgroundResource(titleBg);

        TextView tvCategory = view.findViewById(R.id.tv_category);
        TextView sub_category = view.findViewById(R.id.sub_category);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        Button viewAllBtn = view.findViewById(R.id.viewAllBtn);


        tvCategory.setText(title + "".trim());
        if (manuCategoryMap != null && manuCategoryMap.containsKey(mainCategoryId)) {
            setSubCategoryString(sub_category, manuCategoryMap.get(mainCategoryId).getCategoryString());
        } else {
            sub_category.setVisibility(View.GONE);
        }
        if (mainCategoryId == 0) {
            viewAllBtn.setVisibility(View.GONE);
        } else {
            viewAllBtn.setVisibility(View.VISIBLE);
            viewAllBtn.setOnClickListener(v -> {

                if (mainCategoryId == -2) {
                    startActivity(new Intent(ShoppingDashboardActivity.this, SimilarProductShoppingActivity.class)
                            .putExtra("TITLE", "Recent View Products")
                            .putExtra("SIMILAR_PRODUCT_LIST", recentViewProductListData)
                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                } else {
                    Intent intent = new Intent(ShoppingDashboardActivity.this, MainCategoryDetailShoppingActivity.class);
                    intent.putExtra("Id", mainCategoryId);
                    intent.putExtra("CategoryId", 0);
                    intent.putExtra("isFromNavigationDrawer", false);
                    intent.putExtra("CategoryName", title);
                    intent.putExtra("CategoryImage", manuCategoryMap.get(mainCategoryId).getMainCategoryImage());
                    intent.putParcelableArrayListExtra("CategoryList", manuCategoryMap.get(mainCategoryId).getCategoryList());
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
            });
        }


        dataparse(viewType, recyclerView, mDashboardProductListItems);
        containerView.addView(view);

    }

    private void setSubCategoryString(TextView textView, String subCategoryString) {


        if (subCategoryString != null && !subCategoryString.isEmpty() && subCategoryString.length() > 0) {
            if (subCategoryString.length() > 45) {
                String str = subCategoryString.substring(0, 46);
                str = str.substring(0, str.lastIndexOf(","));
                textView.setText(str + " & More");
            } else {
                textView.setText(subCategoryString);
            }

        } else {
            textView.setVisibility(View.GONE);
        }


    }

    private void dataparse(String type, RecyclerView recyclerView,
                           List<DashboardProductListData> dashboardProductList) {
        if (dashboardProductList.size() > 2) {
            if (type.equalsIgnoreCase("Grid_Vertical")) {

                recyclerView.addItemDecoration(new RecyclerViewItemDecoration(8, RecyclerViewItemDecoration.GRID, false));
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                recyclerView.setAdapter(new DashboardGridShoppingAdapter(this,
                        dashboardProductList, R.layout.adapter_shopping_grid_item_layout_vertical));

            } else if (type.equalsIgnoreCase("Grid_Horizontal")) {
                recyclerView.addItemDecoration(new RecyclerViewItemDecoration(8, RecyclerViewItemDecoration.HORIZONTAL, true));
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(new DashboardGridShoppingAdapter(this, dashboardProductList, R.layout.adapter_shopping_grid_item_layout_horizontal));

            } else {

                recyclerView.addItemDecoration(new RecyclerViewItemDecoration(8, RecyclerViewItemDecoration.HORIZONTAL, true));
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(new DashboardGridShoppingAdapter(this, dashboardProductList, R.layout.adapter_shopping_grid_item_layout));

            }
        } else {
            recyclerView.addItemDecoration(new RecyclerViewItemDecoration(8, RecyclerViewItemDecoration.GRID, false));
            recyclerView.setLayoutManager(new GridLayoutManager(this, dashboardProductList.size()));
            recyclerView.setAdapter(new DashboardGridShoppingAdapter(this,
                    dashboardProductList, R.layout.adapter_shopping_grid_item_layout_vertical));
        }

    }

    private void setNewArrivalDataAndSaleData() {
        //mDashboardNewArrivalCategoryWiseList.clear();
        if (mNewArrivalAndSaleDataResponse != null && mNewArrivalAndSaleDataResponse.getNewArrivalAndSaleData() != null) {

            if (mNewArrivalAndSaleDataResponse.getNewArrivalAndSaleData().getNewArrivals() != null && mNewArrivalAndSaleDataResponse.getNewArrivalAndSaleData().getNewArrivals().size() > 0) {
                listLayoutManagerTypeList.add("Horizontal");
                setProductData("New Arrival", 0, "Horizontal",
                        mNewArrivalAndSaleDataResponse.getNewArrivalAndSaleData().getNewArrivals(),
                        R.drawable.ic_bg_pattern_1, getResources().getColor(R.color.pattern_color_1));
                patterList.add(1);

            }
            if (mNewArrivalAndSaleDataResponse.getNewArrivalAndSaleData().getOnSale() != null && mNewArrivalAndSaleDataResponse.getNewArrivalAndSaleData().getOnSale().size() > 0) {
                listLayoutManagerTypeList.add("Grid_Horizontal");
                setProductData("On Sale", 0, "Grid_Horizontal",
                        mNewArrivalAndSaleDataResponse.getNewArrivalAndSaleData().getOnSale(), 0, getResources().getColor(R.color.lightWhite));
            }
            if (mNewArrivalAndSaleDataResponse.getNewArrivalAndSaleData().getBestSellerList() != null && mNewArrivalAndSaleDataResponse.getNewArrivalAndSaleData().getBestSellerList().size() > 0) {
                listLayoutManagerTypeList.add("Grid_Vertical");
                setProductData("Best Seller", 0, "Grid_Vertical",
                        mNewArrivalAndSaleDataResponse.getNewArrivalAndSaleData().getBestSellerList(),
                        R.drawable.ic_bg_pattern_2, getResources().getColor(R.color.pattern_color_2));
                patterList.add(2);
            }
        }

        // dashboardNewArrivalCategoryAdapter.notifyDataSetChanged();

        mCustomLoader.dismiss();
    }

    private void getRecentProduct() {
        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            // if (loaderView.getVisibility() == View.GONE) {
            loaderView.setVisibility(View.VISIBLE);
            scrollView.post(() -> {
                scrollView.fullScroll(View.FOCUS_DOWN);

            });
            //  }
            //loaderView.setVisibility(View.VISIBLE);
            isScrollApiCalling = true;
            ApiShoppingUtilMethods.INSTANCE.GetRecentViewProductInfo(this, new ApiShoppingUtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(final Object response) {
                    final MainCategoryWiseProductResponse mMainCategoryWiseProductResponse = (MainCategoryWiseProductResponse) response;
                    isScrollBottomRecentApiCalled = true;
                    isScrollApiCalling = false;

                    if (mMainCategoryWiseProductResponse != null && mMainCategoryWiseProductResponse.getMainCategoryWiseProductData() != null && mMainCategoryWiseProductResponse.getMainCategoryWiseProductData().size() > 0) {
                        recentViewProductListData = mMainCategoryWiseProductResponse.getMainCategoryWiseProductData();
                        listLayoutManagerTypeList.add("Grid_Horizontal");
                        setProductData("Recent View", recentViewProductListData.size() > 6 ? -2 : 0,
                                "Grid_Horizontal",
                                recentViewProductListData.size() > 6 ? new ArrayList<>(recentViewProductListData.subList(0, 6)) : recentViewProductListData,
                                R.drawable.ic_bg_pattern_6, getResources().getColor(R.color.pattern_color_6));
                        patterList.add(6);
                        // mDashboardProductMainCategoryWiseList.add(new DashboardProductCategoryWiseList(false, "Recent View", recentViewProductListData.size() > 6 ? -2 : 0, "Grid_Horizonatl", recentViewProductListData.size() > 6 ? new ArrayList<>(recentViewProductListData.subList(0, 6)) : recentViewProductListData));
                        //dashboardCategoryAdapter.notifyDataSetChanged();

                    }
                    loaderView.setVisibility(View.GONE);
                }

                @Override
                public void onError(int ErrorType, String msg) {
                    isScrollApiCalling = false;
                    loaderView.setVisibility(View.GONE);

                    if (ErrorType == ApiShoppingUtilMethods.INSTANCE.ERRORR_NETWORK) {
                        networkErrorView.setVisibility(View.VISIBLE);
                        isScrollBottomRecentApiCalled = false;
                    } else {
                        isScrollBottomRecentApiCalled = true;
                    }
                }
            });


        } else {
            isScrollApiCalling = false;
            isScrollBottomRecentApiCalled = false;
            loaderView.setVisibility(View.GONE);
            networkErrorView.setVisibility(View.VISIBLE);

        }
    }

    private void manageDrawerMenuData() {

        drawerDashbaordInfoMenuList.clear();
        if (mDashbaordInfoResponse != null && mDashbaordInfoResponse.getDashbaordInfoData() != null &&
                mDashbaordInfoResponse.getDashbaordInfoData().getMenus() != null &&
                mDashbaordInfoResponse.getDashbaordInfoData().getMenus().size() > 0) {
            menuSize = mDashbaordInfoResponse.getDashbaordInfoData().getMenus().size();
            drawerDashbaordInfoMenuList.addAll(mDashbaordInfoResponse.getDashbaordInfoData().getMenus());
            manuCategoryMap = new HashMap<>();
            for (DashbaordInfoMenu item : drawerDashbaordInfoMenuList) {
                manuCategoryMap.put(item.getMainCategoryID(), item);
            }
        }

    }

    private void manageTopCategoryData() {

        mTopCategoryList.clear();
        if (mDashbaordInfoResponse != null && mDashbaordInfoResponse.getDashbaordInfoData() != null
                && mDashbaordInfoResponse.getDashbaordInfoData().getTopcategories() != null &&
                mDashbaordInfoResponse.getDashbaordInfoData().getTopcategories().size() > 4) {
            mTopCategoryList.addAll(mDashbaordInfoResponse.getDashbaordInfoData().getTopcategories());
            dashboardTopCategoryAdapter.notifyDataSetChanged();
            topCategoryRecyclerView.setVisibility(View.VISIBLE);
        } else {
            topCategoryRecyclerView.setVisibility(View.GONE);
        }

    }

    private void manageSliderImageData() {
        ArrayList<DashbaordInfoBannerProducts> mImageList = new ArrayList<>();
        if (mDashbaordInfoResponse != null && mDashbaordInfoResponse.getDashbaordInfoData() != null &&
                mDashbaordInfoResponse.getDashbaordInfoData().getBannerProductsFront() != null &&
                mDashbaordInfoResponse.getDashbaordInfoData().getBannerProductsFront().size() > 0) {
            mImageList.addAll(mDashbaordInfoResponse.getDashbaordInfoData().getBannerProductsFront());
        }
       /* if (mDashbaordInfoResponse.getmDashbaordInfoData().getBannerProductsRight() != null && mDashbaordInfoResponse.getmDashbaordInfoData().getBannerProductsRight().size() > 0) {
            mImageList.addAll(mDashbaordInfoResponse.getmDashbaordInfoData().getBannerProductsRight());
        }*/
        dataParseSliderImages(mImageList);
    }

    private void dataParseSliderImages(ArrayList<DashbaordInfoBannerProducts> sliderList) {
        // For Inflate Banner Images...
        if (sliderList != null && sliderList.size() > 0) {
            pagerView.setVisibility(View.VISIBLE);
            SliderImageShoppingAdapter mSliderImageAdapter = new SliderImageShoppingAdapter(ShoppingDashboardActivity.this, sliderList, true) {
                @Override
                protected void onImageClick(View v, String url) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } catch (ActivityNotFoundException anfe) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW)
                                    .setData(Uri.parse(url)));
                        } catch (ActivityNotFoundException anf) {

                        }
                    } catch (Exception e) {

                    }
                }
            };

            viewpager.setAdapter(mSliderImageAdapter);
            viewpager.setOffscreenPageLimit(mSliderImageAdapter.getCount());

            //A little space between pages
            //viewpager.setPageMargin(10);
            viewpager.setCurrentItem(1);
            //viewpager.setPadding(30, 8, 30, 8);
            // viewpager.setClipToPadding(false);

            //If hardware acceleration is enabled, you should also remove
            // clipping on the pager for its children.
            viewpager.setClipChildren(false);
            viewpager.setIndicatorSmart(true);
            pagerIndicator.setCount(viewpager.getIndicatorCount());
            viewpager.setIndicatorPageChangeListener(new LoopingViewPager.IndicatorPageChangeListener() {
                @Override
                public void onIndicatorProgress(int selectingPosition, float progress) {
                    //indicatorView.setProgress(selectingPosition, progress);
                }

                @Override
                public void onIndicatorPageChange(int newIndicatorPosition) {
                    pagerIndicator.setSelection(newIndicatorPosition);
                }
            });
        }
    }


    /*void WalletBalance() {
        if (userId != null && !userId.isEmpty()) {
            if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

                UtilMethods.INSTANCE.GetWalletBalance(this, pref, new UtilMethods.ApiHitCallBack() {
                    @Override
                    public void onSucess(final Object response) {

                        WalletBalanceResponse data = (WalletBalanceResponse) response;
                        if (data.getStatus()) {
                            mCartItemCount = data.getData1();

                            setupBadge();
                            setBalanceAmount();
                        }
                    }

                    @Override
                    public void onError(int errorCode, String msg) {
                        if (errorCode == UtilMethods.INSTANCE.ERRORR_NETWORK) {

                        } else {

                        }
                    }
                });
            }
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        if (ApiFintechUtilMethods.INSTANCE.isUserDetailUpdate) {
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            userNameTv.setText(mLoginDataResponse.getData().getName() /*+ " - " + mLoginDataResponse.getData().getRoleName()*/);
            emailTv.setText(mLoginDataResponse.getData().getEmailID() + "");
            ApiFintechUtilMethods.INSTANCE.isUserDetailUpdate = false;
        }
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


    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping, menu);
        if (fromScreen == 0) {
            menu.getItem(1).setVisible(true);
        } else {
            menu.getItem(1).setVisible(false);
        }

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


        final MenuItem menuItemNoti = menu.findItem(R.id.action_notification);
        View actionViewNoti = MenuItemCompat.getActionView(menuItemNoti);
        badges_Notification = actionViewNoti.findViewById(R.id.count_badge);
        actionViewNoti.setOnClickListener(v -> {
            Intent i = new Intent(this, NotificationListActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_NOTIFICATIONS);
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

            Intent intent = new Intent(ShoppingDashboardActivity.this, CartDetailShoppingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);


        } else if (id == R.id.action_notification) {

            Intent i = new Intent(this, NotificationListActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_NOTIFICATIONS);


        }

        return super.onOptionsItemSelected(item);
    }


    private BroadcastReceiver mNewNotificationReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ApiFintechUtilMethods.INSTANCE.GetNotifications(ShoppingDashboardActivity.this,
                    mLoginDataResponse, mAppPreferences, ApiShoppingUtilMethods.INSTANCE.mDeviceId,
                    ApiShoppingUtilMethods.INSTANCE.mDeviceSerialNum, object -> {
                        notificationCount = ((int) object);
                        setNotificationCount();
                    });
        }
    };

    void setNotificationCount() {
        if (notificationCount != 0) {
            badges_Notification.setVisibility(View.VISIBLE);

            if (notificationCount > 99) {
                badges_Notification.setText("99+");

            } else {
                badges_Notification.setText(notificationCount + "");

            }
        } else {
            badges_Notification.setVisibility(View.GONE);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_NOTIFICATIONS && resultCode == RESULT_OK) {
            notificationCount = notificationCount - data.getIntExtra("Notification_Count", 0);
            setNotificationCount();
        } else if (requestCode == INTENT_PROFILE && resultCode == RESULT_OK) {
            setUserUiData();
        } else if (requestCode == INTENT_ADD_MONEY && resultCode == RESULT_OK) {
            ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse,
                    ApiShoppingUtilMethods.INSTANCE.mDeviceId, ApiShoppingUtilMethods.INSTANCE.mDeviceSerialNum,
                    mAppPreferences, mEKYCStepsDialog, object -> {
                        balanceCheckResponse = (BalanceResponse) object;
                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                && balanceCheckResponse.getBalanceData().size() > 0) {
                            showBalanceData();
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        if (fromScreen == 0) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mNewNotificationReciver);
        }
        super.onDestroy();
    }

    /*void checkAppVersion() {

        String version = "0";

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BasicApiResponse> call = git.VersionCheck(version, UtilMethods.INSTANCE.getWebsiteId(this));
            call.enqueue(new Callback<BasicApiResponse>() {
                @Override
                public void onResponse(Call<BasicApiResponse> call, final retrofit2.Response<BasicApiResponse> response) {

                    if (response != null) {
                        try {

                            BasicApiResponse data = response.body();
                            if (data != null) {
                                if (data.getStatus()) {
                                    UtilMethods.INSTANCE.versionDialog(ShoppingDashboardActivity.this);

                                } else {

                                }

                            } else {

                            }
                        } catch (Exception ex) {

                        }
                    } else {


                    }
                }

                @Override
                public void onFailure(Call<BasicApiResponse> call, Throwable t) {


                }
            });
        } catch (Exception ex) {

        }

    }*/


}
