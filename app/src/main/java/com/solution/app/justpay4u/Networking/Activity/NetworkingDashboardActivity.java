package com.solution.app.justpay4u.Networking.Activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.solution.app.justpay4u.Api.Fintech.Object.AllowedWithdrawalType;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Object.Banners;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OperatorListResponse;
import com.solution.app.justpay4u.Api.Networking.Object.DashboardDownlineData;
import com.solution.app.justpay4u.Api.Networking.Object.DashboardIncomeDetail;
import com.solution.app.justpay4u.Api.Networking.Object.NetworkingDashboardData;
import com.solution.app.justpay4u.Api.Networking.Object.TeamData;
import com.solution.app.justpay4u.Api.Networking.Request.DataTypeRequest;
import com.solution.app.justpay4u.Api.Networking.Response.NetworkingDashboardResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApiNetworkingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.NetworkingEndPointInterface;
import com.solution.app.justpay4u.ApiHits.RankList;
import com.solution.app.justpay4u.ApiHits.RankListResponse;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.Activities.UserProfileActivity;
import com.solution.app.justpay4u.Fintech.Dashboard.Activity.HomeDashActivity;
import com.solution.app.justpay4u.Fintech.Dashboard.Adapter.CustomPagerAdapter;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.AddMoneyActivity;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.DMRWithPipeActivity;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.PaymentRequest;
import com.solution.app.justpay4u.Fintech.Info.Activity.InfoActivity;
import com.solution.app.justpay4u.Fintech.MoveToWallet.Activity.WalletToWalletActivity;
import com.solution.app.justpay4u.Fintech.Notification.NotificationListActivity;
import com.solution.app.justpay4u.Fintech.Reports.Activity.ReportActivity;
import com.solution.app.justpay4u.Fintech.UpiPayment.Activity.UPIListActivity;
import com.solution.app.justpay4u.Networking.Adapter.BuisnessNetworkingAdapter;
import com.solution.app.justpay4u.Networking.Adapter.IncomeNetworkingAdapter;
import com.solution.app.justpay4u.Networking.Adapter.PoolNetworkingAdapter;
import com.solution.app.justpay4u.Networking.Adapter.RankListAdapter;
import com.solution.app.justpay4u.Networking.Adapter.WalletNetworkingAdapter;
import com.solution.app.justpay4u.Networking.Adapter.WalletWithdrawalSelectAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.ShoppingDashboardActivity;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkingDashboardActivity extends AppCompatActivity {


    private FrameLayout notificationView;
    private AppCompatImageView refresh;
    private LinearLayout userDetailView;
    private LinearLayout addMember;
    private LinearLayout activateMember;
    private LinearLayout walletView;
    private LinearLayout incomeView;
    TextView rankTV;
    AppCompatImageView rankIV;
    View rankView;

    String filterStatus = "";
    ImageView vaIv;
    private RecyclerView incomeRecyclerView, walletRecyclerView;
    private LinearLayout downlineView;
    private LinearLayout leftTeamView;
    private TextView leftTeamTitle;
    private TextView leftTeamUserLabel;
    private TextView leftTeamUser, verifyUserIv;
    private TextView leftTeamBusinessLabel;
    private TextView leftTeamBusiness;
    private ImageView leftTeamIv;
    private TextView leftTeamDeactive;
    private ImageView leftTeamDeactiveIv;
    private TextView leftTeamActive;
    private ImageView leftTeamActiveIv;
    private RelativeLayout leftUserShareView;
    private TextView leftUserShareTv;
    private TextView leftUserCopy;
    private TextView leftUserShare;
    private LinearLayout rightTeamView;
    private TextView rightTeamTitle;
    private TextView rightTeamUserLabel;
    private TextView rightTeamUser;
    private TextView rightTeamBusinessLabel;
    private TextView rightTeamBusiness;
    private ImageView rightTeamIv;
    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterToDate = "", filterStaus = "All";
    private String[] DefaultUserArray = {"Today", "Last Day", "Last 7 Day", "This Month", "Last Month", "All", "Custom Range"};
    private TextView tv_DefaultUser,retopupBlink;
    private RelativeLayout defaultUserView;
    private LinearLayout dateView, startDateView, endDateView;
    private AppCompatTextView startDate, endDate;
    private Button applyBtn;
    private TextView rightTeamDeactive;
    private ImageView rightTeamDeactiveIv;
    private TextView rightTeamActive;
    private ImageView rightTeamActiveIv;
    private RelativeLayout rightUserShareView;
    private TextView rightUserShareTv;
    private TextView rightUserCopy;
    private TextView rightUserShare;
    private LinearLayout directTeamView;


    private RelativeLayout directTeamDeactiveClick, directTeamActivateClick;
    private TextView directTeamTitle;
    private TextView directTeamUserLabel;
    private TextView directTeamUser;
    private TextView directTeamBusinessLabel;
    private TextView directTeamBusiness, directTeamBusinessValue;
    private ImageView directTeamIv;
    private TextView directTeamDeactive;
    private ImageView directTeamDeactiveIv;
    private TextView directTeamActive;
    private ImageView directTeamActiveIv;
    private RelativeLayout directUserShareView;
    private TextView directUserShareTv;
    private TextView directUserCopy;
    private TextView directUserShare;

    private RelativeLayout totalTeamDeactivate, totalTeamActivated;
    private LinearLayout totalTeamView;
    private TextView totalTeamTitle;
    private TextView totalTeamUserLabel;
    private TextView totalTeamUser;
    private TextView totalTeamBusinessLabel;
    private TextView totalTeamBusiness, selfTeamBusiness;
    private ImageView totalTeamIv;
    private TextView totalTeamDeactive;
    private ImageView totalTeamDeactiveIv;
    private TextView totalTeamActive;
    private ImageView totalTeamActiveIv;
    String getFilterStatusFalse = "0";

    String getFilterStatusTrue = "1";
    private RelativeLayout totalUserShareView, sponserTeamDeactivaClick, sponserTeamActivateClick;
    private TextView totalUserShareTv;
    private TextView totalUserCopy;
    private TextView totalUserShare;
    private LinearLayout generationActiveTeamView, generationDeactiveTeamView, sponserTeamAllStatusView, sponserTeamView;
    private TextView generationActiveTeamTitle, generationDeactiveTeamTitle, sponserTeamTitle;
    private TextView generationActiveTeamUserLabel, generationDeactiveTeamUserLabel, sponserTeamUserLabel;
    private TextView generationActiveTeamUser, generationDeactiveTeamUser, sponserTeamUser;
    private TextView generationActiveTeamBusinessLabel, generationDeactiveTeamBusinessLabel, sponserTeamBusinessLabel;
    private TextView sponserTeamBusiness, generationActiveTeamBusinessValue, generationDeactiveTeamBusinessValue, sponserTeamBusinessValue, sponserTeamBusinesslabe;
    private ImageView generationActiveTeamIv, generationDeactiveTeamIv, sponserTeamIv;
    private TextView sponserTeamDeactive;
    private ImageView sponserTeamDeactiveIv;
    private TextView sponserTeamActive;
    private ImageView sponserTeamActiveIv;
    private RelativeLayout sponserUserShareView;
    private TextView sponserUserShareTv;
    private TextView sponserUserCopy;
    private TextView sponserUserShare;
    private LinearLayout bottomView;
    private LinearLayout homeView;
    private LinearLayout fundRequest;
    private LinearLayout supportView;
    private LinearLayout reportView;
    private LinearLayout shoppingView;
    private LinearLayout fintechView, ll_logout, addMoneyView, genelogyView;
    private ImageView genelogyGlobalView;

    IncomeNetworkingAdapter mIncomeNetworkingAdapter;
    private TextView badges_Notification, newsTv, genelogyGlobalViewTxt;
    private View newsCard, leftBusinessView, rightBusinessView, totalBusinessView, directBusinessView, sponserBusinessView, selfBusinessView;
    ViewPager mViewPager;
    View pagerContainer;
    LinearLayout dotsCount;
    TextView userNameTv, roleTv, mobileNumTv, emailTv;
    ImageView userImage;
    TextView lastPkgTV, lastPkgAmtTv;
    View lastPkgView;
    private int INTENT_PROFILE = 4376;
    private int notificationCount;
    private AppPreferences mAppPreferences;
    private LoginResponse mLoginDataResponse;
    String deviceId, deviceSerialNum;
    private int INTENT_NOTIFICATIONS = 7231;
    private int INTENT_ADD_MONEY = 5454;
    private ArrayList<Banners> bannerList = new ArrayList<>();
    private TextView[] mDotsText;
    private int pagerTop, pagerleft;
    private int mDotsCount;
    private Handler handler;
    private Runnable mRunnable;
    private RequestOptions requestOptionsUserImage;
    private BalanceResponse balanceCheckResponse;
    private boolean isFromCanBalanceChange;
    private boolean isLevelIncome, isReTopupAllow, isFintechServiceOn, isPaymentGatway, isAddMoneyEnable, isMLM, isGlobalPoolTree;
    int isBinaryOn;
    private boolean isFromLogin;
    int fromScreen, fromPreviousScreen; // 1=Fintech,2=Shopping, 3=Networking
    private CustomLoader loader;
    private List<DashboardIncomeDetail> incomeList = new ArrayList<>();
    private WalletNetworkingAdapter mWalletNetworkingAdapter;
    private ArrayList<BalanceData> mBalanceDataList = new ArrayList<>();
    private int INTENT_TRANSFER = 6732;
    ArrayList<OperatorList> mDMTOperatorLists = new ArrayList<>();
    private OperatorListResponse operatorListResponse;
    private CustomAlertDialog customAlertDialog;
    private String getFilterStatusAll = "All";
    private boolean isCryptoRefresh;
    private Dialog withdrawalSelectDialog;
    private int downLineDataFilter;
    private NetworkingDashboardResponse mNetworkingDashboardResponse;
    private RankListResponse mRankListResponse;
    private ArrayList<RankList> mRankList = new ArrayList<>();
    private ArrayList<DashboardTeamDetailsList> mteamDetailsList = new ArrayList<>();
    private ArrayList<DashboardTeamDetailsList> mBusinessDetailsList = new ArrayList<>();
    private ArrayList<DashboardTeamDetailsList> mPoolDetailsList = new ArrayList<>();
    private TeamDetailsListAdapter mteamDetailsListAdapter;
    PoolNetworkingAdapter mPoolNetworkingAdapter;
    private View teamDetailsView;
    private RecyclerView teamRecyclerView;
    private LinearLayout buisnessView;
    private ImageView coinIV;
    private RecyclerView businessRecyclerView, poolRecyclerView;
    private BuisnessNetworkingAdapter mbuisnessNetworkingAdapter;
    private Dialog dialogInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_networking_dashboard);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);


            findViewId();
            clickView();
            new Handler(Looper.getMainLooper()).post(() -> {
                customAlertDialog = new CustomAlertDialog(this);
                isFromLogin = getIntent().getBooleanExtra("FROM_LOGIN", false);
                fromScreen = getIntent().getIntExtra("FROM_SCREEN", 0);
                fromPreviousScreen = getIntent().getIntExtra("FROM_PRE_SCREEN", 0);
                showHideBottomBtn(mLoginDataResponse.isECommerceAllowed(), mLoginDataResponse.isFintechServiceOn());
                if (fromScreen == 0) {
                    notificationView.setVisibility(View.VISIBLE);
                    LocalBroadcastManager.getInstance(NetworkingDashboardActivity.this).registerReceiver(mNewNotificationReciver, new IntentFilter("New_Notification_Detect"));
                } else {
                    notificationView.setVisibility(View.GONE);
                }
                setStoredData();

                mCustomFilterDialog = new CustomFilterDialog(this);
                dahsboardApi(false);
                mNetworkingDashboardResponse = ApiFintechUtilMethods.INSTANCE.getNetworkingDashboard(mAppPreferences);
                if (mNetworkingDashboardResponse != null && mNetworkingDashboardResponse.getData() != null) {
                    setUserUiData(mNetworkingDashboardResponse);
                }
                setUserUiData(null);
            });
        });

    }

    void findViewId() {

        pagerTop = (int) getResources().getDimension(R.dimen._3sdp);
        pagerleft = (int) getResources().getDimension(R.dimen._6sdp);
        badges_Notification = findViewById(R.id.badgesNotification);
        pagerContainer = findViewById(R.id.pagerContainer);
        mViewPager = findViewById(R.id.pager);
        newsCard = findViewById(R.id.newsCard);
        newsTv = findViewById(R.id.newsTv);
        dotsCount = findViewById(R.id.image_count);
        userNameTv = findViewById(R.id.userNameTv);
        roleTv = findViewById(R.id.roleTv);
        mobileNumTv = findViewById(R.id.mobileNumTv);
        emailTv = findViewById(R.id.emailTv);
        lastPkgTV = findViewById(R.id.lastPkgTV);
        lastPkgAmtTv = findViewById(R.id.lastPkgAmtTv);
        lastPkgView = findViewById(R.id.lastPkgView);
        userImage = findViewById(R.id.userImage);
        directTeamDeactiveClick = findViewById(R.id.directTeamDeactiveClick);
        directTeamActivateClick = findViewById(R.id.directTeamActivateClick);
        leftBusinessView = findViewById(R.id.leftBusinessView);
        rightBusinessView = findViewById(R.id.rightBusinessView);
        directBusinessView = findViewById(R.id.directBusinessView);
        sponserBusinessView = findViewById(R.id.sponserBusinessView);
        selfBusinessView = findViewById(R.id.selfBusinessView);
        totalBusinessView = findViewById(R.id.totalBusinessView);
        genelogyView = findViewById(R.id.genelogyView);
        genelogyGlobalView = findViewById(R.id.genelogyGlobalView);
        genelogyGlobalViewTxt = findViewById(R.id.genelogyGlobalViewTxt);
        verifyUserIv = findViewById(R.id.verifyUserIv);
        vaIv = findViewById(R.id.vaIv);


        notificationView = findViewById(R.id.notificationView);
        refresh = findViewById(R.id.refresh);
        userDetailView = findViewById(R.id.userDetailView);

        addMember = findViewById(R.id.addMember);
        activateMember = findViewById(R.id.activateMember);
        walletView = findViewById(R.id.walletView);
        incomeView = findViewById(R.id.incomeView);
        rankView = findViewById(R.id.rankView);
        rankTV = findViewById(R.id.rankTV);
        rankIV = findViewById(R.id.rankIV);
        downlineView = findViewById(R.id.downlineView);
        leftTeamView = findViewById(R.id.leftTeamView);
        leftTeamTitle = findViewById(R.id.leftTeamTitle);
        leftTeamUserLabel = findViewById(R.id.leftTeamUserLabel);
        leftTeamUser = findViewById(R.id.leftTeamUser);
        leftTeamBusinessLabel = findViewById(R.id.leftTeamBusinessLabel);
        leftTeamBusiness = findViewById(R.id.leftTeamBusiness);
        leftTeamIv = findViewById(R.id.leftTeamIv);
        leftTeamDeactive = findViewById(R.id.leftTeamDeactive);
        leftTeamDeactiveIv = findViewById(R.id.leftTeamDeactiveIv);
        leftTeamActive = findViewById(R.id.leftTeamActive);
        leftTeamActiveIv = findViewById(R.id.leftTeamActiveIv);
        leftUserShareView = findViewById(R.id.leftUserShareView);
        leftUserShareTv = findViewById(R.id.leftUserShareTv);
        leftUserCopy = findViewById(R.id.leftUserCopy);
        leftUserShare = findViewById(R.id.leftUserShare);
        rightTeamView = findViewById(R.id.rightTeamView);
        rightTeamTitle = findViewById(R.id.rightTeamTitle);
        rightTeamUserLabel = findViewById(R.id.rightTeamUserLabel);
        rightTeamUser = findViewById(R.id.rightTeamUser);
        rightTeamBusinessLabel = findViewById(R.id.rightTeamBusinessLabel);
        rightTeamBusiness = findViewById(R.id.rightTeamBusiness);
        rightTeamIv = findViewById(R.id.rightTeamIv);
        rightTeamDeactive = findViewById(R.id.rightTeamDeactive);
        rightTeamDeactiveIv = findViewById(R.id.rightTeamDeactiveIv);
        rightTeamActive = findViewById(R.id.rightTeamActive);
        rightTeamActiveIv = findViewById(R.id.rightTeamActiveIv);
        rightUserShareView = findViewById(R.id.rightUserShareView);
        rightUserShareTv = findViewById(R.id.rightUserShareTv);
        rightUserCopy = findViewById(R.id.rightUserCopy);
        rightUserShare = findViewById(R.id.rightUserShare);
        directTeamView = findViewById(R.id.directTeamView);
        directTeamTitle = findViewById(R.id.directTeamTitle);
        directTeamUserLabel = findViewById(R.id.directTeamUserLabel);
        directTeamUser = findViewById(R.id.directTeamUser);
        directTeamBusinessLabel = findViewById(R.id.directTeamBusinessLabel);
        directTeamBusiness = findViewById(R.id.directTeamBusiness);
        directTeamBusinessValue = findViewById(R.id.directTeamBusinessValue);
        directTeamIv = findViewById(R.id.directTeamIv);
        directTeamDeactive = findViewById(R.id.directTeamDeactive);
        directTeamDeactiveIv = findViewById(R.id.directTeamDeactiveIv);
        directTeamActive = findViewById(R.id.directTeamActive);
        directTeamActiveIv = findViewById(R.id.directTeamActiveIv);
        directUserShareView = findViewById(R.id.directUserShareView);
        directUserShareTv = findViewById(R.id.directUserShareTv);
        directUserCopy = findViewById(R.id.directUserCopy);
        directUserShare = findViewById(R.id.directUserShare);
        totalTeamView = findViewById(R.id.totalTeamView);
        totalTeamDeactivate = findViewById(R.id.totalTeamDeactivate);
        totalTeamActivated = findViewById(R.id.totalTeamActivated);
        totalTeamTitle = findViewById(R.id.totalTeamTitle);
        totalTeamUserLabel = findViewById(R.id.totalTeamUserLabel);
        totalTeamUser = findViewById(R.id.totalTeamUser);
        totalTeamBusinessLabel = findViewById(R.id.totalTeamBusinessLabel);
        totalTeamBusiness = findViewById(R.id.totalTeamBusiness);
        selfTeamBusiness = findViewById(R.id.selfTeamBusiness);
        // totalTeamIv = findViewById(R.id.totalTeamIv);
        totalTeamDeactive = findViewById(R.id.totalTeamDeactive);
        totalTeamDeactiveIv = findViewById(R.id.totalTeamDeactiveIv);
        totalTeamActive = findViewById(R.id.totalTeamActive);
        totalTeamActiveIv = findViewById(R.id.totalTeamActiveIv);
        totalUserShareView = findViewById(R.id.totalUserShareView);
        sponserTeamDeactivaClick = findViewById(R.id.sponserTeamDeactivaClick);
        sponserTeamAllStatusView = findViewById(R.id.sponserTeamAllStatusView);
        sponserTeamActivateClick = findViewById(R.id.sponserTeamActivateClick);
        totalUserShareTv = findViewById(R.id.totalUserShareTv);
        totalUserCopy = findViewById(R.id.totalUserCopy);
        totalUserShare = findViewById(R.id.totalUserShare);


        generationActiveTeamView = findViewById(R.id.generationActiveTeamView);
        generationActiveTeamTitle = findViewById(R.id.generationActiveTeamTitle);
        generationActiveTeamUserLabel = findViewById(R.id.generationActiveTeamUserLabel);
        generationActiveTeamUser = findViewById(R.id.generationActiveTeamUser);
//        generationActiveTeamBusinessLabel = findViewById(R.id.generationActiveTeamBusinesslabe);
//        generationActiveTeamBusinessValue = findViewById(R.id.generationActiveTeamBusinessValue);
        generationActiveTeamIv = findViewById(R.id.generationActiveTeamIv);

        generationDeactiveTeamView = findViewById(R.id.generationDeactiveTeamView);
        generationDeactiveTeamTitle = findViewById(R.id.generationDeactiveTeamTitle);
        generationDeactiveTeamUserLabel = findViewById(R.id.generationDeactiveTeamUserLabel);
        generationDeactiveTeamUser = findViewById(R.id.generationDeactiveTeamUser);
//        generationDeactiveTeamBusinessLabel = findViewById(R.id.generationDeactiveTeamBusinesslabe);
//        generationDeactiveTeamBusinessValue = findViewById(R.id.generationDeactiveTeamBusinessValue);
        generationDeactiveTeamIv = findViewById(R.id.generationDeactiveTeamIv);

        sponserTeamView = findViewById(R.id.sponserTeamView);
        sponserTeamTitle = findViewById(R.id.sponserTeamTitle);
        sponserTeamUserLabel = findViewById(R.id.sponserTeamUserLabel);
        sponserTeamUser = findViewById(R.id.sponserTeamUser);
        sponserTeamBusinessLabel = findViewById(R.id.sponserTeamBusinessLabel);
        sponserTeamBusiness = findViewById(R.id.sponserTeamBusiness);
        sponserTeamBusinessValue = findViewById(R.id.sponserTeamBusinessValue);
        sponserTeamBusinesslabe = findViewById(R.id.sponserTeamBusinesslabe);
        sponserTeamIv = findViewById(R.id.sponserTeamIv);
        sponserTeamDeactive = findViewById(R.id.sponserTeamDeactive);
        sponserTeamDeactiveIv = findViewById(R.id.sponserTeamDeactiveIv);
        sponserTeamActive = findViewById(R.id.sponserTeamActive);
        sponserTeamActiveIv = findViewById(R.id.sponserTeamActiveIv);
        sponserUserShareView = findViewById(R.id.sponserUserShareView);
        sponserUserShareTv = findViewById(R.id.sponserUserShareTv);
        sponserUserCopy = findViewById(R.id.sponserUserCopy);
        sponserUserShare = findViewById(R.id.sponserUserShare);
        bottomView = findViewById(R.id.bottomView);
        homeView = findViewById(R.id.homeView);
        fundRequest = findViewById(R.id.fundRequest);
        supportView = findViewById(R.id.supportView);
        reportView = findViewById(R.id.reportView);
        shoppingView = findViewById(R.id.shoppingView);
        fintechView = findViewById(R.id.fintechView);
        ll_logout = findViewById(R.id.ll_logout);
        addMoneyView = findViewById(R.id.addMoneyView);
        defaultUserView = findViewById(R.id.defaultUserView);
        tv_DefaultUser = findViewById(R.id.tv_DefaultUser);
        dateView = findViewById(R.id.dateView);
        startDateView = findViewById(R.id.startDateView);
        startDate = findViewById(R.id.startDate);
        startDate.setText(filterFromDate);
        endDateView = findViewById(R.id.endDateView);
        endDate = findViewById(R.id.endDate);
        endDate.setText(filterToDate);
        applyBtn = findViewById(R.id.applyBtn);
        applyBtn.setOnClickListener(v -> {
            String defaultUser = tv_DefaultUser.getText().toString();
            /*if (defaultUser.equals("Today")) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String today = sdf.format(calendar.getTime());
                filterFromDate = today;
                filterToDate = today;
                DashboardDownlineApi();
            } else if (defaultUser.equals("Last Day")) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                Date lastDay = calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                filterFromDate = sdf.format(lastDay);
                filterToDate = sdf.format(lastDay);
                DashboardDownlineApi();
            } else if (defaultUser.equals("Last 7 Day")) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, -7);
                Date last7Days = calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                filterFromDate = sdf.format(last7Days);
                filterToDate = sdf.format(new Date());
                DashboardDownlineApi();
            } else if (defaultUser.equals("This Month")) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                Date firstDayOfMonth = calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                filterFromDate = sdf.format(firstDayOfMonth);
                filterToDate = sdf.format(new Date());
                DashboardDownlineApi();
            } else if (defaultUser.equals("Last Month")) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, -1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                Date firstDayOfLastMonth = calendar.getTime();
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                Date lastDayOfLastMonth = calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                filterFromDate = sdf.format(firstDayOfLastMonth);
                filterToDate = sdf.format(lastDayOfLastMonth);
                DashboardDownlineApi();

            } else if (defaultUser.equals("All")) {
                filterFromDate = "";
                filterToDate = "";
                DashboardDownlineApi();
            } */

            if (defaultUser.equals("Custom Range")) {
                filterFromDate = startDate.getText().toString();
                filterToDate = endDate.getText().toString();
                //filterFromDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(new Date());
                // filterToDate = filterFromDate;
                applyBtn.setVisibility(View.VISIBLE);
                dateView.setVisibility(View.VISIBLE);
                DashboardDownlineApi();
            } else {
                applyBtn.setVisibility(View.GONE);
                dateView.setVisibility(View.GONE);
            }
        });

        teamDetailsView = findViewById(R.id.teamDetailsView);
        teamRecyclerView = findViewById(R.id.teamRecyclerView);
        teamRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        poolRecyclerView = findViewById(R.id.poolRecyclerView);
        poolRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        coinIV = findViewById(R.id.coinIV);
        buisnessView = findViewById(R.id.buisnessView);
        businessRecyclerView = findViewById(R.id.businessRecyclerView);
        businessRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        incomeRecyclerView = findViewById(R.id.incomeRecyclerView);
        incomeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mIncomeNetworkingAdapter = new IncomeNetworkingAdapter(incomeList, this);
        incomeRecyclerView.setAdapter(mIncomeNetworkingAdapter);
        walletRecyclerView = findViewById(R.id.walletRecyclerView);
        walletRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    void showHideBottomBtn(boolean isECommerceAllowed, boolean isFintechServiceOn) {

        if (fromScreen == 2 && fromPreviousScreen == 1) {
            shoppingView.setVisibility(View.GONE);
            fintechView.setVisibility(View.GONE);
            ll_logout.setVisibility(View.VISIBLE);
        } else if (fromScreen == 1 && fromPreviousScreen == 2) {
            shoppingView.setVisibility(View.GONE);
            fintechView.setVisibility(View.GONE);
            ll_logout.setVisibility(View.VISIBLE);
        } else if (fromScreen == 2 && fromPreviousScreen != 1) {
            shoppingView.setVisibility(View.GONE);
            ll_logout.setVisibility(View.VISIBLE);
            if (isFintechServiceOn) {
                fintechView.setVisibility(View.VISIBLE);
            } else {
                fintechView.setVisibility(View.GONE);
            }
        } else if (fromScreen == 1 && fromPreviousScreen != 2) {
            if (isECommerceAllowed) {
                shoppingView.setVisibility(View.VISIBLE);
            } else {
                shoppingView.setVisibility(View.GONE);
            }
            ll_logout.setVisibility(View.VISIBLE);
            fintechView.setVisibility(View.GONE);
        } else {
            if (isECommerceAllowed && isFintechServiceOn) {
                shoppingView.setVisibility(View.VISIBLE);
                fintechView.setVisibility(View.VISIBLE);
                ll_logout.setVisibility(View.GONE);
            } else if (!isECommerceAllowed && isFintechServiceOn) {
                shoppingView.setVisibility(View.GONE);
                fintechView.setVisibility(View.VISIBLE);
                ll_logout.setVisibility(View.VISIBLE);
            } else if (isECommerceAllowed) {
                shoppingView.setVisibility(View.VISIBLE);
                fintechView.setVisibility(View.GONE);
                ll_logout.setVisibility(View.VISIBLE);
            } else {
                shoppingView.setVisibility(View.GONE);
                fintechView.setVisibility(View.GONE);
                ll_logout.setVisibility(View.VISIBLE);
            }

        }
    }

    void clickView() {
        vaIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NetworkingDashboardActivity.this, QRScanNewActivity.class);
                startActivity(i);
            }
        });

        ll_logout.setOnClickListener(v -> new CustomAlertDialog(this).Successfullogout(loader, "Do you really want to Logout?", this, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences));
        addMoneyView.setOnClickListener(v -> {
            Intent i = new Intent(this, AddMoneyActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_ADD_MONEY);
        });
        genelogyView.setOnClickListener(v -> {
            Intent i = new Intent(this, GenelogyNetworkingActivity.class);
            i.putExtra("UserId", mLoginDataResponse.getData().getUserID() + "");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        genelogyGlobalView.setOnClickListener(v -> {
            Intent i = new Intent(this, GenelogyGlobalNetworkingActivity.class);
            i.putExtra("UserId", mLoginDataResponse.getData().getUserID() + "");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        genelogyGlobalViewTxt.setOnClickListener(v -> {
            Intent i = new Intent(this, GenelogyGlobalNetworkingActivity.class);
            i.putExtra("UserId", mLoginDataResponse.getData().getUserID() + "");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });

        notificationView.setOnClickListener(v -> {
            Intent i = new Intent(this, NotificationListActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_NOTIFICATIONS);
        });

        userDetailView.setOnClickListener(v -> {
            isFromCanBalanceChange = true;
            startActivityForResult(new Intent(this, UserProfileActivity.class).putExtra("IsAddMoneyVisible", true).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_PROFILE);
        });
        addMember.setOnClickListener(v -> {

            startActivity(new Intent(this, AddUserNetworkingActivity.class).putExtra("IsBinaryOn", isBinaryOn).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });
        activateMember.setOnClickListener(v -> {
            isFromCanBalanceChange = true;
            startActivity(new Intent(NetworkingDashboardActivity.this, balanceCheckResponse.isTopupByEpin() ? EPinListNetworkingActivity.class : ActivateUserNetworkingActivity.class).putExtra("Title", "Activate User").putExtra("TopupType", balanceCheckResponse.getUserTopupType()).putParcelableArrayListExtra("items", mBalanceDataList).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });
        fintechView.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeDashActivity.class).putExtra("FROM_LOGIN", isFromLogin).putExtra("FROM_SCREEN", 3).putExtra("FROM_PRE_SCREEN", fromScreen).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });
        shoppingView.setOnClickListener(v -> {

            startActivity(new Intent(this, ShoppingDashboardActivity.class).putExtra("FROM_LOGIN", isFromLogin).putExtra("FROM_SCREEN", 3).putExtra("FROM_PRE_SCREEN", fromScreen).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));


        });

        reportView.setOnClickListener(v -> {
            startActivity(new Intent(this, ReportActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });
        homeView.setOnClickListener(v -> {
            Intent i = new Intent(this, HomeDashActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        fundRequest.setOnClickListener(v -> {
            Intent i = new Intent(this, PaymentRequest.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        directTeamView.setOnClickListener(v -> {
            Intent i = new Intent(this, DirectTeamNetworkingActivity.class);
            i.putExtra("Deactive", getFilterStatusAll);
            i.putExtra("BalanceCheckResponse", balanceCheckResponse.isBinaryon);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });

        directTeamDeactiveClick.setOnClickListener(v -> {
            Intent i = new Intent(this, DirectTeamNetworkingActivity.class);
            i.putExtra("Deactive", getFilterStatusFalse);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });

        directTeamActivateClick.setOnClickListener(v -> {
            Intent i = new Intent(this, DirectTeamNetworkingActivity.class);
            i.putExtra("Deactive", getFilterStatusTrue);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        sponserTeamView.setOnClickListener(v -> {
            Intent i = new Intent(this, UplinePoolCountTeamNetworkingActivity.class);
            i.putExtra("status", getFilterStatusAll);
            i.putExtra("BusinessType", balanceCheckResponse.isBinaryon);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        generationDeactiveTeamView.setOnClickListener(v -> {
            Intent i = new Intent(this, UplinePoolCountTeamNetworkingActivity.class);
            i.putExtra("status", "Deactive");
            i.putExtra("BusinessType", balanceCheckResponse.isBinaryon);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        generationActiveTeamView.setOnClickListener(v -> {
            Intent i = new Intent(this, UplinePoolCountTeamNetworkingActivity.class);
            i.putExtra("status", "Active");
            i.putExtra("BusinessType", balanceCheckResponse.isBinaryon);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        sponserTeamDeactivaClick.setOnClickListener(v -> {
            Intent i = new Intent(this, SponserTeamNetworkingActivity.class);
            i.putExtra("Deactive", getFilterStatusFalse);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        sponserTeamActivateClick.setOnClickListener(v -> {
            Intent i = new Intent(this, SponserTeamNetworkingActivity.class);
            i.putExtra("Deactive", getFilterStatusTrue);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        leftBusinessView.setOnClickListener(v -> {
            Intent i = new Intent(this, TotalBuisnessNetworkingActivity.class);
            i.putExtra("Leg", "L");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        rightBusinessView.setOnClickListener(v -> {
            Intent i = new Intent(this, TotalBuisnessNetworkingActivity.class);
            i.putExtra("Leg", "R");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        directBusinessView.setOnClickListener(v -> {
            Intent i = new Intent(this, DirectBuisnessNetworkingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        sponserBusinessView.setOnClickListener(v -> {
            Intent i = new Intent(this, SponserBuisnessNetworkingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        selfBusinessView.setOnClickListener(v -> {
            Intent i = new Intent(this, SelfBuisnessNetworkingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        totalBusinessView.setOnClickListener(v -> {
            Intent i = new Intent(this, TotalBuisnessNetworkingActivity.class);
            i.putExtra("Leg", "All");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });


        supportView.setOnClickListener(v -> startActivity(new Intent(this, InfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));


        directUserCopy.setOnClickListener(view -> {
            if (directUserShareTv.getText().toString().trim().length() > 0) {
                setClipboard(directUserShareTv.getText().toString().trim());
            }
        });
        sponserUserCopy.setOnClickListener(view -> {
            if (sponserUserShareTv.getText().toString().trim().length() > 0) {
                setClipboard(sponserUserShareTv.getText().toString().trim());
            }
        });
        totalUserCopy.setOnClickListener(view -> {
            if (totalUserShareTv.getText().toString().trim().length() > 0) {
                setClipboard(totalUserShareTv.getText().toString().trim());
            }
        });

        leftUserCopy.setOnClickListener(view -> {
            if (leftUserShareTv.getText().toString().trim().length() > 0) {
                setClipboard(leftUserShareTv.getText().toString().trim());
            }
        });

        rightUserCopy.setOnClickListener(view -> {
            if (rightUserShareTv.getText().toString().trim().length() > 0) {
                setClipboard(rightUserShareTv.getText().toString().trim());
            }
        });

        directUserShare.setOnClickListener(view -> {
            if (directUserShareTv.getText().toString().trim().length() > 0) {
                shareContent(null, directUserShareTv.getText().toString().trim());
            }
        });
        sponserUserShare.setOnClickListener(view -> {
            if (sponserUserShareTv.getText().toString().trim().length() > 0) {
                shareContent(null, sponserUserShareTv.getText().toString().trim());
            }
        });
        totalUserShare.setOnClickListener(view -> {
            if (totalUserShareTv.getText().toString().trim().length() > 0) {
                shareContent(null, totalUserShareTv.getText().toString().trim());
            }
        });
        leftUserShare.setOnClickListener(view -> {
            if (leftUserShareTv.getText().toString().trim().length() > 0) {
                shareContent(null, leftUserShareTv.getText().toString().trim());
            }
        });
        rightUserShare.setOnClickListener(view -> {
            if (rightUserShareTv.getText().toString().trim().length() > 0) {
                shareContent(null, rightUserShareTv.getText().toString().trim());
            }
        });
        rankView.setOnClickListener(v -> {
            if (mRankList != null && !mRankList.isEmpty()) {
//                Toast.makeText(this, mRankList.size()+"", Toast.LENGTH_SHORT).show();
                showRankList(true, mRankList);
            } else {
                getRankList();
            }
//            getRankList();

        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(500);
                rotate.setRepeatCount(Animation.INFINITE);
                rotate.setInterpolator(new LinearInterpolator());
                refresh.startAnimation(rotate);
                dahsboardApi(true);
            }
        });
    }

    private void showRankList(boolean isCanclable, ArrayList<RankList> RankList) {
        if (dialogInfo != null && dialogInfo.isShowing()) {
            return;
        }

        View view = getLayoutInflater().inflate(R.layout.dialog_networking_rank_list, null);

        final View closeBtn = view.findViewById(R.id.closeBtn);
        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerViewInfo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        dialogInfo = new Dialog(this, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        mRecyclerView.setAdapter(new RankListAdapter(this, RankList));

        dialogInfo.setContentView(view);
        dialogInfo.setCancelable(true);
        dialogInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (!isCanclable) {
            closeBtn.setVisibility(View.GONE);
            dialogInfo.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (dialogInfo.isShowing()) {
                        dialogInfo.dismiss();
                        finish();
                    }
                }
                return false;
            });
        }
        closeBtn.setOnClickListener(v -> dialogInfo.dismiss());
//        shareBtn.setOnClickListener(view1 -> shareit(shareContaint, loginPrefResponse.getData().getName() + " Sharing " + bonanzaItem.getRewardname() + " Detail"));
        dialogInfo.show();
    }

    void setUserUiData(NetworkingDashboardResponse response) {
        userNameTv.setText(mLoginDataResponse.getData().getName() /*+ " - " + mLoginDataResponse.getData().getRoleName()*/);
        roleTv.setText(mLoginDataResponse.getData().getPrefix() + "" + mLoginDataResponse.getData().getUserID() + "");
        mobileNumTv.setText(mLoginDataResponse.getData().getMobileNo() + "");
        emailTv.setText(mLoginDataResponse.getData().getEmailID() + "");
        try {
            NetworkingDashboardData mNetworkingDashboardData = response.getData();
            DashboardDownlineData mDashboardDownlineData = mNetworkingDashboardData.getSingleData();
            if (mDashboardDownlineData.getLastPackageName() != null && !mDashboardDownlineData.getLastPackageName().isEmpty()) {
                lastPkgView.setVisibility(View.VISIBLE);
                lastPkgTV.setText(mDashboardDownlineData.getLastPackageName() + "");
                lastPkgAmtTv.setText("(" + Utility.INSTANCE.formatedAmountWithOutRupees(mDashboardDownlineData.getLastPackageAmount() + "") + ")");
                lastPkgAmtTv.setTextColor(Color.GREEN);
                rankView.setVisibility(View.VISIBLE);
                rankTV.setText(mDashboardDownlineData.getRankName() + "");
            } else {
                lastPkgView.setVisibility(View.GONE);
                rankView.setVisibility(View.GONE);
            }

            if (mDashboardDownlineData.getRankImageUrl() != null && mDashboardDownlineData.getRankImageUrl().length() > 0) {
                RequestOptions mRequestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_RankImage();
                Glide.with(this)
                        .load(ApplicationConstant.INSTANCE.baseUrl + mDashboardDownlineData.getRankImageUrl())
                        .apply(mRequestOptions)
                        .into(rankIV);
            } else {
                rankTV.setVisibility(View.GONE);
                rankView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }

        if (requestOptionsUserImage == null) {
            requestOptionsUserImage = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_UserIcon();
        }
        Glide.with(this)
                .load(ApplicationConstant.INSTANCE.baseProfilePicUrl + mLoginDataResponse.getData().getUserID() + ".png")
                .apply(requestOptionsUserImage)
                .into(userImage);

    }

    void setStoredData() {


        AppUserListResponse newsData = ApiFintechUtilMethods.INSTANCE.getNewsData(mAppPreferences);
        if (newsData != null && newsData.getNewsContent() != null && newsData.getNewsContent().getNewsDetail() != null && !newsData.getNewsContent().getNewsDetail().isEmpty()) {
            newsTv.setText(Html.fromHtml(newsData.getNewsContent().getNewsDetail() + ""));
            newsCard.setVisibility(View.VISIBLE);
        } else {
            newsCard.setVisibility(View.GONE);
        }
        AppUserListResponse bannerData = ApiFintechUtilMethods.INSTANCE.getBannerData(mAppPreferences);
        setBannerData(bannerData);

        showWalletData();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeDashActivity.class);
        startActivity(intent);
        finish();

    }

/*    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }*/


    void setBannerData(AppUserListResponse response) {
        if (response != null && response.getBanners() != null && response.getBanners().size() > 0) {
            bannerList.clear();
            bannerList.addAll(response.getBanners());
            if (bannerList != null && bannerList.size() > 0) {
                pagerContainer.setVisibility(View.VISIBLE);
                CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(bannerList, this, 1);
                mViewPager.setAdapter(mCustomPagerAdapter);
                mViewPager.setOffscreenPageLimit(mCustomPagerAdapter.getCount());
                mViewPager.setPageMargin(pagerleft);
                mViewPager.setPadding(pagerleft, pagerTop, pagerleft, pagerTop);
                mViewPager.setClipToPadding(false);
                mDotsCount = mViewPager.getAdapter().getCount();
                mDotsText = new TextView[mDotsCount];
                //here we set the dots
                for (int i = 0; i < mDotsCount; i++) {
                    mDotsText[i] = new TextView(this);
                    mDotsText[i].setText(".");
                    mDotsText[i].setTextSize(50);
                    mDotsText[i].setTypeface(null, Typeface.BOLD);
                    mDotsText[i].setTextColor(getResources().getColor(R.color.light_grey));
                    dotsCount.addView(mDotsText[i]);
                }

                                    /*mViewPager.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            return false;
                                        }
                                    });*/

                mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        for (int i = 0; i < mDotsCount; i++) {
                            mDotsText[i].setTextColor(getResources().getColor(R.color.light_grey));
                        }
                        mDotsText[position].setTextColor(getResources().getColor(R.color.colorPrimary));
                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

                postDelayedScrollNext();
            } else {
                pagerContainer.setVisibility(View.GONE);
            }
        } else {
            pagerContainer.setVisibility(View.GONE);
        }
    }

    private void postDelayedScrollNext() {
        handler = new Handler();
        mRunnable = () -> {

            if (mViewPager.getAdapter() != null) {
                if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1) {
                    mViewPager.setCurrentItem(0);
                    postDelayedScrollNext();
                    return;
                }
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                // postDelayedScrollNext(position+1);
                postDelayedScrollNext();
            }

            // onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
        };
        handler.postDelayed(mRunnable, 2500);

    }


    private void showWalletData() {


        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null && balanceCheckResponse.getBalanceData().size() > 0) {

            isLevelIncome = balanceCheckResponse.isLevelIncome();
            isBinaryOn = balanceCheckResponse.getIsBinaryon();
            isReTopupAllow = balanceCheckResponse.isReTopupAllow();
            isFintechServiceOn = balanceCheckResponse.isFintechServiceOn();
            isPaymentGatway = balanceCheckResponse.isPaymentGatway();
            isAddMoneyEnable = balanceCheckResponse.isAddMoneyEnable();
            isMLM = balanceCheckResponse.isMLM();
            isGlobalPoolTree = balanceCheckResponse.isGlobalPoolTree();
         /*   if (isGlobalPoolTree) {
                totalTeamView.setVisibility(View.VISIBLE);
                sponserUserShareView.setVisibility(View.GONE);
            } else {
                totalTeamView.setVisibility(View.GONE);
                sponserUserShareView.setVisibility(View.VISIBLE);
            }*/

            if (isBinaryOn == 2) {
                leftTeamView.setVisibility(View.VISIBLE);
                rightTeamView.setVisibility(View.VISIBLE);
                genelogyView.setVisibility(View.VISIBLE);
                leftBusinessView.setVisibility(View.VISIBLE);
                rightBusinessView.setVisibility(View.VISIBLE);
                totalBusinessView.setVisibility(View.VISIBLE);
            } else {
                leftTeamView.setVisibility(View.GONE);
                rightTeamView.setVisibility(View.GONE);
                genelogyView.setVisibility(View.GONE);
                leftBusinessView.setVisibility(View.GONE);
                rightBusinessView.setVisibility(View.GONE);
                sponserUserShareView.setVisibility(View.GONE);
                totalBusinessView.setVisibility(View.GONE);

            }


            mBalanceDataList.clear();
            mBalanceDataList.addAll(balanceCheckResponse.getBalanceData());


            if (balanceCheckResponse.getIsTopup() == 1) {

                verifyUserIv.setText("Active");
                verifyUserIv.setTextColor(Color.GREEN);
            } else {
                verifyUserIv.setText("Inactive");
                verifyUserIv.setTextColor(Color.RED);
            }

            /* if (mLoginDataResponse.isAccountStatement()) {
                mBalanceDataList.add(new BalanceData("Outstanding Wallet",balanceCheckResponse.getOsBalance()));
            }*/


            if (mWalletNetworkingAdapter != null) {
                mWalletNetworkingAdapter.notifyDataSetChanged();
            } else {
                if (mBalanceDataList.size() == 1) {
                    walletRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    mWalletNetworkingAdapter = new WalletNetworkingAdapter(this, mBalanceDataList, R.layout.adapter_networking_wallet);
                    walletRecyclerView.setAdapter(mWalletNetworkingAdapter);
                } else if (mBalanceDataList.size() == 2) {
                    walletRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                    mWalletNetworkingAdapter = new WalletNetworkingAdapter(this, mBalanceDataList, R.layout.adapter_networking_wallet);
                    walletRecyclerView.setAdapter(mWalletNetworkingAdapter);
                } else {
                    walletRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    mWalletNetworkingAdapter = new WalletNetworkingAdapter(this, mBalanceDataList, R.layout.adapter_networking_wallet_half);
                    walletRecyclerView.setAdapter(mWalletNetworkingAdapter);
                }
            }

            showHideBottomBtn(balanceCheckResponse.isECommerceAllowed(), balanceCheckResponse.isFintechServiceOn());

            if (balanceCheckResponse.isUPI() || balanceCheckResponse.isPaymentGatway()) {
                addMoneyView.setVisibility(View.VISIBLE);
            } else {
                addMoneyView.setVisibility(View.GONE);
            }
            // Set Gloabal Team Gone


        } else {
            balanceCheckResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null && balanceCheckResponse.getBalanceData().size() > 0) {
                showWalletData();
            } else {
                ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, null, object -> {
                    balanceCheckResponse = (BalanceResponse) object;
                    if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null && balanceCheckResponse.getBalanceData().size() > 0) {
                        showWalletData();
                    }
                });
            }

        }

    }

    void dahsboardApi(boolean isRefresh) {

        if (isRefresh) {
            ApiFintechUtilMethods.INSTANCE.WalletType(this, mLoginDataResponse, mAppPreferences, null);
        }


        getRankList();
        ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, null, object -> {
            balanceCheckResponse = (BalanceResponse) object;
            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null && balanceCheckResponse.getBalanceData().size() > 0) {
                showWalletData();
            }
        });
        if (balanceCheckResponse != null && balanceCheckResponse.getDefaultUserDashboardDate() != 0) {
            downLineDataFilter = balanceCheckResponse.getDefaultUserDashboardDate();
            tv_DefaultUser.setText(DefaultUserArray[downLineDataFilter - 1]);
        } else {
            tv_DefaultUser.setText(DefaultUserArray[5]);
        }
        String defaultUser = tv_DefaultUser.getText().toString();
        if (defaultUser.equals("Today")) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String today = sdf.format(calendar.getTime());
            filterFromDate = today;
            filterToDate = today;
        } else if (defaultUser.equals("Last Day")) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            Date lastDay = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            filterFromDate = sdf.format(lastDay);
            filterToDate = sdf.format(lastDay);
        } else if (defaultUser.equals("Last 7 Day")) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -7);
            Date last7Days = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            filterFromDate = sdf.format(last7Days);
            filterToDate = sdf.format(new Date());
        } else if (defaultUser.equals("This Month")) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date firstDayOfMonth = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            filterFromDate = sdf.format(firstDayOfMonth);
            filterToDate = sdf.format(new Date());
        } else if (defaultUser.equals("Last Month")) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date firstDayOfLastMonth = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date lastDayOfLastMonth = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            filterFromDate = sdf.format(firstDayOfLastMonth);
            filterToDate = sdf.format(lastDayOfLastMonth);
        } else if (defaultUser.equals("All")) {
            filterFromDate = "";
            filterToDate = "";
        } else if (defaultUser.equals("Custom Range")) {
            filterFromDate = startDate.getText().toString();
            filterToDate = endDate.getText().toString();
            dateView.setVisibility(View.VISIBLE);
            applyBtn.setVisibility(View.VISIBLE);
        } else {
            // Handle default case, when option is not recognized
        }
        // Visibility of applyBtn for non-custom range options
        if (!defaultUser.equals("Custom Range")) {
            dateView.setVisibility(View.GONE);
            applyBtn.setVisibility(View.GONE);
        }

        defaultUserView.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (tv_DefaultUser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(DefaultUserArray).indexOf(tv_DefaultUser.getText().toString());
            }
            mCustomFilterDialog.showSingleChoiceAlert(DefaultUserArray, selectedIndex, "Choose Type", "Choose Any Type", new CustomFilterDialog.SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    tv_DefaultUser.setText(DefaultUserArray[index]);
                    String defaultUser = tv_DefaultUser.getText().toString();
                    switch (index) {
                        case 1:
                            fetchTodayData();
                            break;
                        case 2:
                            fetchLastDayData();
                            break;
                        case 3:
                            fetchLast7DayData();
                            break;
                        case 4:
                            fetchThisMonthData();
                            break;
                        case 5:
                            fetchLastMonthData();
                            break;
                        case 6:
                            fetchAllData();
                            break;
                        default:
                            filterFromDate = startDate.getText().toString();
                            filterToDate = endDate.getText().toString();
                            // Default logic if index doesn't match any specific condition
                            break;
                    }
                    if (defaultUser.equals("Today")) {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
                                Locale.getDefault());
                        String today = sdf.format(calendar.getTime());
                        filterFromDate = today;
                        filterToDate = today;
                    } else if (defaultUser.equals("Last Day")) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, -1);
                        Date lastDay = calendar.getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
                                Locale.getDefault());
                        filterFromDate = sdf.format(lastDay);
                        filterToDate = sdf.format(lastDay);
                    } else if (defaultUser.equals("Last 7 Day")) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, -7);
                        Date last7Days = calendar.getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
                                Locale.getDefault());
                        filterFromDate = sdf.format(last7Days);
                        filterToDate = sdf.format(new Date());
                    } else if (defaultUser.equals("This Month")) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                        Date firstDayOfMonth = calendar.getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
                                Locale.getDefault());
                        filterFromDate = sdf.format(firstDayOfMonth);
                        filterToDate = sdf.format(new Date());
                    } else if (defaultUser.equals("Last Month")) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.MONTH, -1);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                        Date firstDayOfLastMonth = calendar.getTime();
                        calendar.add(Calendar.MONTH, 1);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                        Date lastDayOfLastMonth = calendar.getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        filterFromDate = sdf.format(firstDayOfLastMonth);
                        filterToDate = sdf.format(lastDayOfLastMonth);
                    } else if (defaultUser.equals("All")) {
                        filterFromDate = "";
                        filterToDate = "";
                    } else if (defaultUser.equals("Custom Range")) {
                        filterFromDate = startDate.getText().toString();
                        filterToDate = endDate.getText().toString();
                        dateView.setVisibility(View.VISIBLE);
                        applyBtn.setVisibility(View.VISIBLE);
                    } else {
                        // Handle default case, when option is not recognized
                    }
                    // Visibility of applyBtn for non-custom range options
                    if (!defaultUser.equals("Custom Range")) {
                        dateView.setVisibility(View.GONE);
                        applyBtn.setVisibility(View.GONE);
                    }
                    DashboardDownlineApi();
                }
                @Override
                public void onNegativeClick() {
                }
            });
        });
        startDateView.setOnClickListener(v -> mCustomFilterDialog.setDCFromDate(startDate, endDate));
        endDateView.setOnClickListener(v -> mCustomFilterDialog.setDCToDate(startDate, endDate));
        DashboardDownlineApi();

        if (fromScreen == 0) {
            ApiFintechUtilMethods.INSTANCE.GetNotifications(this, mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum, object -> {
                notificationCount = ((int) object);
                setNotificationCount();
            });

        }
        ApiFintechUtilMethods.INSTANCE.NewsApi(this, false, newsTv, newsCard, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);
        ApiFintechUtilMethods.INSTANCE.BannerApi(mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, new ApiFintechUtilMethods.ApiCallBack() {
            @Override
            public void onSucess(Object object) {
                AppUserListResponse response = (AppUserListResponse) object;
                if (response.getBanners() != null && response.getBanners().size() > 0) {
                    mAppPreferences.set(ApplicationConstant.INSTANCE.bannerDataPref, new Gson().toJson(response));
                    setBannerData(response);
                } else {
                    pagerContainer.setVisibility(View.GONE);
                }
            }
        });

    }


    private void fetchTodayData() {
        filterFromDate = getCurrentDate();
        filterToDate = getCurrentDate();
//        DashboardDownlineApi();
    }

    private void fetchLastDayData() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        filterFromDate = formatDate(calendar.getTime());
        filterToDate = formatDate(calendar.getTime());
//        DashboardDownlineApi();
    }

    private void fetchLast7DayData() {

        Calendar calendar = Calendar.getInstance();
        filterToDate = formatDate(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -6);
        filterFromDate = formatDate(calendar.getTime());
//        DashboardDownlineApi();
    }

    private void fetchThisMonthData() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        filterFromDate = formatDate(calendar.getTime());
        filterToDate = getCurrentDate();
//        DashboardDownlineApi();
    }

    private void fetchLastMonthData() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        filterFromDate = formatDate(calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        filterToDate = formatDate(calendar.getTime());
//        DashboardDownlineApi();
    }

    private void fetchAllData() {
        filterFromDate = "";
        filterToDate = "";
//        DashboardDownlineApi();
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(date);
    }


    private void getRankList() {
        NetworkingEndPointInterface git = ApiClient.getClient().create(NetworkingEndPointInterface.class);
        Call<RankListResponse> call = git.RankList(new BasicRequest(mLoginDataResponse.getData().getUserID(),
                mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID,
                deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

        call.enqueue(new Callback<RankListResponse>() {
            @Override
            public void onResponse(@NonNull Call<RankListResponse> call, @NonNull Response<RankListResponse> response) {
                try {
                    refresh.clearAnimation();
                    if (response.body() != null && response.body().getStatuscode() == 1) {
                        ApiNetworkingUtilMethods.INSTANCE.mRankListResponse = response.body();
                        mAppPreferences.set(ApplicationConstant.INSTANCE.mRankListPref, new Gson().toJson(response.body()));
                        mRankListResponse = response.body();
                        mRankList = (ArrayList<RankList>) mRankListResponse.getData();
//                        showRankList(true, mRankList);

                    }
                } catch (Exception e) {
                    refresh.clearAnimation();
                }
            }

            @Override
            public void onFailure(Call<RankListResponse> call, Throwable t) {
//                Toast.makeText(NetworkingDashboardActivity.this, "bye", Toast.LENGTH_SHORT).show();

            }
        });

    /*    } catch (Exception e) {
            e.printStackTrace();
            refresh.clearAnimation();
        }*/
    }


    public void DashboardDownlineApi() {
        try {
            NetworkingEndPointInterface git = ApiClient.getClient().create(NetworkingEndPointInterface.class);
            Call<NetworkingDashboardResponse> call = git.DownlineData(new BasicRequest(new BasicRequest(mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession()), new DataTypeRequest(filterFromDate, filterToDate)));
            call.enqueue(new Callback<NetworkingDashboardResponse>() {
                @Override
                public void onResponse(Call<NetworkingDashboardResponse> call, final retrofit2.Response<NetworkingDashboardResponse> response) {

                    try {

                        refresh.clearAnimation();

                        if (response.body() != null && response.body().getStatuscode() == 1) {
                            ApiNetworkingUtilMethods.INSTANCE.mNetworkingDashboardResponse = response.body();
                            mAppPreferences.set(ApplicationConstant.INSTANCE.dashboardDownlineDataPref, new Gson().toJson(response.body()));
                            setDashboardDownlineData(response.body());
                            mAppPreferences.set(ApplicationConstant.INSTANCE.singleDataPref, new Gson().toJson(mNetworkingDashboardResponse.getData().getSingleData()));
                            mNetworkingDashboardResponse = response.body();
                            setUserUiData(mNetworkingDashboardResponse);


                        }
                    } catch (Exception e) {
                        refresh.clearAnimation();
                    }

                }

                @Override
                public void onFailure(Call<NetworkingDashboardResponse> call, Throwable t) {

                    refresh.clearAnimation();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            refresh.clearAnimation();
        }
    }

    void setDashboardDownlineData(NetworkingDashboardResponse response) {
        if (response != null && response.getData() != null) {
            NetworkingDashboardData mNetworkingDashboardData = response.getData();
            if (mNetworkingDashboardData.getIncomeDetails() != null && mNetworkingDashboardData.getIncomeDetails().size() > 0) {
                incomeList.clear();
                for (DashboardIncomeDetail incomeDetails : mNetworkingDashboardData.getIncomeDetails()) {
                    if (incomeDetails.isActive()) {
                        incomeList.add(incomeDetails);
                    }
                }
                setIncomeData();
            }


            if (mNetworkingDashboardData.getSingleData() != null) {
                DashboardDownlineData mDashboardDownlineData = mNetworkingDashboardData.getSingleData();
                lastPkgView.setVisibility(View.VISIBLE);
                lastPkgTV.setText(mDashboardDownlineData.getLastPackageName() + "");
                lastPkgAmtTv.setText("(" + Utility.INSTANCE.formatedAmountWithOutRupees(mDashboardDownlineData.getLastPackageAmount() + "") + ")");
                lastPkgAmtTv.setTextColor(Color.GREEN);


                directTeamUser.setText(mDashboardDownlineData.getDirectDownlinelUser() + "");
                directTeamActive.setText(mDashboardDownlineData.getDirectDownlinelUserActive() + "");
                directTeamDeactive.setText(mDashboardDownlineData.getDirectDownlinelUserDeactive() + "");
                directTeamBusiness.setText(Utility.INSTANCE.formatedAmountWithOutRupees(mDashboardDownlineData.getDirectBusiness() + ""));
                directTeamBusinessValue.setText(Utility.INSTANCE.formatedAmountWithOutRupees(mDashboardDownlineData.getDirectBusiness() + ""));

                sponserTeamUser.setText(mDashboardDownlineData.getTotalSponser() + "");
                sponserTeamActive.setText(mDashboardDownlineData.getTotalSponserActive() + "");
                sponserTeamDeactive.setText(mDashboardDownlineData.getTotalSponserDeactive() + "");
                sponserTeamBusiness.setText(Utility.INSTANCE.formatedAmountWithOutRupees(mDashboardDownlineData.getSponserBusiness() + ""));
                sponserTeamBusinessValue.setText(Utility.INSTANCE.formatedAmountWithOutRupees(mDashboardDownlineData.getSponserBusiness() + ""));

                totalTeamUser.setText(mDashboardDownlineData.getTotalTeam() + "");
                totalTeamActive.setText(mDashboardDownlineData.getTotalActive() + "");
                totalTeamDeactive.setText(mDashboardDownlineData.getTotalDeactive() + "");
                totalTeamBusiness.setText(Utility.INSTANCE.formatedAmountWithOutRupees(mDashboardDownlineData.getTotalBusiness() + ""));

                leftTeamUser.setText(mDashboardDownlineData.getTotalLeftTeam() + "");
                leftTeamActive.setText(mDashboardDownlineData.getTotalLeftActive() + "");
                leftTeamDeactive.setText(mDashboardDownlineData.getTotalLeftDeactive() + "");
                leftTeamBusiness.setText(Utility.INSTANCE.formatedAmountWithOutRupees(mDashboardDownlineData.getLeftBusiness() + ""));

                rightTeamUser.setText(mDashboardDownlineData.getTotalRightTeam() + "");
                rightTeamActive.setText(mDashboardDownlineData.getTotalRightActive() + "");
                rightTeamDeactive.setText(mDashboardDownlineData.getTotalRightDeactive() + "");
                rightTeamBusiness.setText(Utility.INSTANCE.formatedAmountWithOutRupees(mDashboardDownlineData.getRightBusiness() + ""));

                selfTeamBusiness.setText(Utility.INSTANCE.formatedAmountWithOutRupees(mDashboardDownlineData.getSelfBusiness() + ""));

                if (isBinaryOn == 2) {
                    if (mDashboardDownlineData.getLeftReferralLink() != null && !mDashboardDownlineData.getLeftReferralLink().isEmpty()) {
                        leftUserShareView.setVisibility(View.VISIBLE);
                        leftUserShareTv.setText(mDashboardDownlineData.getLeftReferralLink() + "");
                    } else {
                        leftUserShareView.setVisibility(View.GONE);
                    }

                    if (mDashboardDownlineData.getRightReferralLink() != null && !mDashboardDownlineData.getRightReferralLink().isEmpty()) {
                        rightUserShareView.setVisibility(View.VISIBLE);
                        rightUserShareTv.setText(mDashboardDownlineData.getRightReferralLink() + "");
                    } else {
                        rightUserShareView.setVisibility(View.GONE);
                    }

                    totalUserShareView.setVisibility(View.GONE);
                } else {
                    if (mDashboardDownlineData.getSingleLink() != null && !mDashboardDownlineData.getSingleLink().isEmpty()) {

                        totalUserShareView.setVisibility(View.VISIBLE);
                        totalUserShareTv.setText(mDashboardDownlineData.getSingleLink() + "");
                    } else {

                        totalUserShareView.setVisibility(View.GONE);
                    }
                    leftUserShareView.setVisibility(View.GONE);
                    rightUserShareView.setVisibility(View.GONE);
                }
                downlineView.setVisibility(View.VISIBLE);
            }
            //Team Details List
            setTeamData(mNetworkingDashboardData.getTeamDetailsList(), mNetworkingDashboardData);
        }
    }

    private void setTeamData(ArrayList<DashboardTeamDetailsList> teamDetailsList, NetworkingDashboardData mNetworkingDashboardData) {
        if (mNetworkingDashboardData != null && teamDetailsList != null && teamDetailsList.size() > 0) {
            mteamDetailsList.clear();
            mBusinessDetailsList.clear();
            mPoolDetailsList.clear();
            for (DashboardTeamDetailsList teamItem : mNetworkingDashboardData.getTeamDetailsList()) {
             /*   if (teamItem.getId() == 3) {
//                    mteamDetailsList.addAll(teamItem);
                    mPoolDetailsList.add(teamItem);
                }*/
                if (teamItem.getType().toLowerCase().equalsIgnoreCase("pool")) {
                    mPoolDetailsList.add(teamItem);
                }
                if (teamItem.getType().toLowerCase().equalsIgnoreCase("team")) {
//                    mteamDetailsList.addAll(teamItem);
                    mteamDetailsList.add(teamItem);
                }
                if (teamItem.getType().toLowerCase().equalsIgnoreCase("business")) {
//                    mBusinessDetailsList.addAll(teamDetailsList);
                    mBusinessDetailsList.add(teamItem);
                }

            }
        }

        if (mteamDetailsList != null && mteamDetailsList.size() > 0) {
            directTeamView.setVisibility(View.GONE);
            sponserTeamView.setVisibility(View.GONE);
            totalTeamView.setVisibility(View.GONE);
            teamDetailsView.setVisibility(View.VISIBLE);
            mteamDetailsListAdapter = new TeamDetailsListAdapter(mteamDetailsList, mNetworkingDashboardData, this);
            teamRecyclerView.setAdapter(mteamDetailsListAdapter);
        } else {
            teamDetailsView.setVisibility(View.GONE);
            directTeamView.setVisibility(View.VISIBLE);
            sponserTeamView.setVisibility(View.VISIBLE);
            totalTeamView.setVisibility(View.VISIBLE);
        }
        if (mBusinessDetailsList != null && !mBusinessDetailsList.isEmpty()) {
            buisnessView.setVisibility(View.VISIBLE);
            mbuisnessNetworkingAdapter = new BuisnessNetworkingAdapter(mBusinessDetailsList, balanceCheckResponse, this);
            businessRecyclerView.setAdapter(mbuisnessNetworkingAdapter);
        } else {
            buisnessView.setVisibility(View.GONE);
        }
        if (mPoolDetailsList != null && mPoolDetailsList.size() > 0) {
            mPoolNetworkingAdapter = new PoolNetworkingAdapter(mPoolDetailsList, this);
            poolRecyclerView.setAdapter(mPoolNetworkingAdapter);
        }


    }


    void setIncomeData() {
        if (incomeList != null && incomeList.size() > 0) {
            incomeView.setVisibility(View.VISIBLE);
            mIncomeNetworkingAdapter.notifyDataSetChanged();
        } else {
            incomeView.setVisibility(View.GONE);
        }

    }

    private final BroadcastReceiver mNewNotificationReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ApiFintechUtilMethods.INSTANCE.GetNotifications(NetworkingDashboardActivity.this, mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum, object -> {
                notificationCount = ((int) object);
                setNotificationCount();
            });
        }
    };

    void setNotificationCount() {
        if (badges_Notification != null) {
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
    }

    public void setClipboard(String text) {

        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Share Link", text);
                clipboard.setPrimaryClip(clip);
            }

            Toast.makeText(this, "Link Copied to clipboard", Toast.LENGTH_LONG).show();
        } catch (Exception e) {

        }
    }

    public void shareContent(String packageName, String link) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
//        String extraText = "<h>Hey your friend</h> <b>" + mLoginDataResponse.getData().getName() + "</b> <h>Inviting you to join</h> <b>" + getString(R.string.app_name) + "</b> <h>click on given link " + link + " and create account for online earning. </h>";
        String extraText = "<h>Install app</h> <b>" + link + "</b> <h> and become Merchant of </h> <b>" + getString(R.string.app_name) + "</b> <h>and Start Recharge, Bill Payments, Money Transfer,  Payment System, Services and Get attractive commission on Every transaction. \n" + "365 Days Hassle free Services with </h> <b>" + getString(R.string.app_name) + "</b> <h> Customer Support. </h>";

        /*  String extraText = "Demo share";*/
        sendIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(extraText).toString());
        sendIntent.setType("text/plain");
        String subText = getResources().getString(R.string.app_name) + " Refral link";
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subText);
        if (packageName != null) {
            sendIntent.setPackage(packageName);
        }
        startActivity(Intent.createChooser(sendIntent, "Share via"));
    }

    @Override
    protected void onDestroy() {
        if (fromScreen == 0) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mNewNotificationReciver);
        }
        if (handler != null && mRunnable != null) {
            handler.removeCallbacks(mRunnable);
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ApiFintechUtilMethods.INSTANCE.isUserDetailUpdate) {
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            userNameTv.setText(mLoginDataResponse.getData().getName() /*+ " - " + mLoginDataResponse.getData().getRoleName()*/);
            emailTv.setText(mLoginDataResponse.getData().getEmailID() + "");
            ApiFintechUtilMethods.INSTANCE.isUserDetailUpdate = false;
        }

        if (isFromCanBalanceChange) {
            isFromCanBalanceChange = false;
            ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, null, object -> {
                balanceCheckResponse = (BalanceResponse) object;
                if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null && balanceCheckResponse.getBalanceData().size() > 0) {
                    showWalletData();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_NOTIFICATIONS && resultCode == RESULT_OK) {
            notificationCount = notificationCount - data.getIntExtra("Notification_Count", 0);
            setNotificationCount();
        } else if (requestCode == INTENT_PROFILE && resultCode == RESULT_OK) {
            setUserUiData(null);
        } else if (requestCode == INTENT_ADD_MONEY && resultCode == RESULT_OK) {
            ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, null, object -> {
                balanceCheckResponse = (BalanceResponse) object;
                if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null && balanceCheckResponse.getBalanceData().size() > 0) {
                    showWalletData();
                }
            });
        } else if (requestCode == INTENT_TRANSFER && resultCode == RESULT_OK) {
            ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, null, object -> {
                balanceCheckResponse = (BalanceResponse) object;
                if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null && balanceCheckResponse.getBalanceData().size() > 0) {
                    showWalletData();
                }
            });
        }
    }

    public void openMoveToWallet(BalanceData item) {

        startActivityForResult(new Intent(this, WalletToWalletActivity.class).putParcelableArrayListExtra("items", mBalanceDataList).putExtra("Data", item).putExtra("FromWalletId", item.getId()).putParcelableArrayListExtra("WalletList", item.getAllowedWallet()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_TRANSFER);


    }

    public void Withdrawal(BalanceData item, String val, String name) {

        isCryptoRefresh = true;
        isFromCanBalanceChange = true;
        if (item.getAllowedWithdrawalType() != null && item.getAllowedWithdrawalType().size() > 0) {
            if (item.getAllowedWithdrawalType().size() > 1) {
                showPopupWithdrawal(item.getAllowedWithdrawalType(), item, val, name);
            } else {
                openWalletWithdrawal(item.getAllowedWithdrawalType().get(0), item, val, item.getWalletType());

            }
        } else {
            startActivity(new Intent(this, WalletToWalletTransferNetworkingActivity.class).putExtra("BalanceData", balanceCheckResponse).putExtra("OnlyBalanceData", balanceCheckResponse.getBalanceData().get(0)).putExtra("amount", val).putExtra("name", name).putExtra("WalletTransferType", item.getWalletTransferType()).putExtra("FromWalletId", item.getId()).putParcelableArrayListExtra("WalletList", item.getAllowedWallet()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

        }

        /*if (item.getWithdrawalType() == 1) {

            openDMT();


        } else if (item.getWithdrawalType() == 2) {
            startActivity(new Intent(this, MoveToWalletActivity.class)
                    .putParcelableArrayListExtra("items", mBalanceDataList)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        } else if (item.getWithdrawalType() == 3) {
            startActivity(new Intent(this, CryptoNetworkingWithdrawalActivity.class)
                    .putParcelableArrayListExtra("items", mBalanceDataList)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        } else {
            Toast.makeText(this, getString(R.string.some_thing_error), Toast.LENGTH_SHORT).show();
        }*/
    }

    private void showPopupWithdrawal(ArrayList<AllowedWithdrawalType> dataList, BalanceData mBalanceData, String val, String name) {

        if (withdrawalSelectDialog != null && withdrawalSelectDialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_networking_withdrawal_select, null);
        RecyclerView recyclerView = viewMyLayout.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        View closeBtn = viewMyLayout.findViewById(R.id.closeBtn);

        WalletWithdrawalSelectAdapter mWalletWithdrawalSelectAdapter = new WalletWithdrawalSelectAdapter(dataList, val, name, this, mBalanceData);
        recyclerView.setAdapter(mWalletWithdrawalSelectAdapter);
        withdrawalSelectDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        withdrawalSelectDialog.setCancelable(false);
        withdrawalSelectDialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        withdrawalSelectDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdrawalSelectDialog.dismiss();
            }
        });

        withdrawalSelectDialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);


    }

    public void openWalletWithdrawal(AllowedWithdrawalType operator, BalanceData mBalanceData, String val, String name) {
        if (withdrawalSelectDialog != null && withdrawalSelectDialog.isShowing()) {
            withdrawalSelectDialog.dismiss();
        }
        if (operator.getServiceId() == 2) {
            openDMT();
        } else if (operator.getServiceId() == 42) {
            startActivity(new Intent(this, BankTransferNetworkingActivity.class).putExtra("BalanceData", balanceCheckResponse).putExtra("FromWalletId", mBalanceData.getId()).putExtra("name", name).putExtra("amount", val).putExtra("OpTypeId", operator.getServiceId()).putExtra("FromWallet", mBalanceData.getWalletType()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        } else if (operator.getServiceId() == 62) {
            startActivity(new Intent(this, UPIListActivity.class).putExtra("BalanceData", balanceCheckResponse).putExtra("FromWalletId", mBalanceData.getId()).putExtra("OpTypeId", operator.getServiceId()).putExtra("FromWallet", mBalanceData.getWalletType()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        } else if (operator.getServiceId() == 76) {
            //     startActivity(new Intent(this, WalletBlockChainTransferNetworkingActivity.class).putExtra("BalanceData", balanceCheckResponse).putExtra("FromWalletId", mBalanceData.getId()).putExtra("FromWallet", mBalanceData.getWalletType()).putExtra("CurrencySymbolId", mBalanceData.getWalletCurrencyID()).putExtra("CurrencySymbol", mBalanceData.getWalletCurrencySymbol()).putParcelableArrayListExtra("WalletToCryptoList", mBalanceData.getAllowedWalletToCripto()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        } else {


            startActivity(new Intent(this, WalletToWalletTransferNetworkingActivity.class).putExtra("amount", val).putExtra("BalanceData", balanceCheckResponse).putExtra("name", name).putExtra("WalletTransferType", mBalanceData.getWalletTransferType()).putExtra("FromWalletId", mBalanceData.getId()).

                    putParcelableArrayListExtra("WalletList", mBalanceData.getAllowedWallet()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));


        }
    }

    void openDMT() {
        if (mDMTOperatorLists == null || mDMTOperatorLists != null && mDMTOperatorLists.size() == 0) {

            if (operatorListResponse != null && operatorListResponse.getDmtOperatorLists() != null && operatorListResponse.getDmtOperatorLists().size() > 0) {
                mDMTOperatorLists = operatorListResponse.getDmtOperatorLists();
                openPipeDMT();
            } else {
                operatorListResponse = ApiFintechUtilMethods.INSTANCE.getOperatorListResponse(mAppPreferences);
                if (operatorListResponse != null && operatorListResponse.getDmtOperatorLists() != null && operatorListResponse.getDmtOperatorLists().size() > 0) {
                    mDMTOperatorLists = operatorListResponse.getDmtOperatorLists();
                    openPipeDMT();
                } else {
                    ApiFintechUtilMethods.INSTANCE.OperatorList(this, deviceId, deviceSerialNum, mAppPreferences, object -> {
                        operatorListResponse = (OperatorListResponse) object;
                        if (operatorListResponse != null && operatorListResponse.getDmtOperatorLists() != null && operatorListResponse.getDmtOperatorLists().size() > 0) {
                            mDMTOperatorLists = operatorListResponse.getDmtOperatorLists();
                            openPipeDMT();
                        }
                    });
                }
            }

        } else {
            openPipeDMT();
        }
    }


    void openPipeDMT() {
        if (mDMTOperatorLists != null && mDMTOperatorLists.size() > 0) {
            if (mDMTOperatorLists.size() > 1) {
                customAlertDialog.dmtListDialog("Select Money Transfer", mDMTOperatorLists, mOperatorList -> {
                    Intent i = new Intent(this, DMRWithPipeActivity.class);
                    i.putExtra("OpType", mOperatorList.getOpType());
                    i.putExtra("OID", mOperatorList.getOid());
                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                });
            } else {
                Intent i = new Intent(this, DMRWithPipeActivity.class);
                i.putExtra("OpType", mDMTOperatorLists.get(0).getOpType());
                i.putExtra("OID", mDMTOperatorLists.get(0).getOid());
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        } else {
            Intent i = new Intent(this, DMRWithPipeActivity.class);
            i.putExtra("OpType", 0);
            i.putExtra("OID", 0);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
    }

    public void teamActiveClick(TeamData item) {

        if (item.getId() == 4) {
            Intent i = new Intent(this, DirectTeamNetworkingActivity.class);
            i.putExtra("Deactive", getFilterStatusTrue);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 5) {
            Intent i = new Intent(this, UplinePoolCountTeamNetworkingActivity.class);
            i.putExtra("status", "Active");
            i.putExtra("BusinessType", balanceCheckResponse.isBinaryon);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 10) {
            Intent i = new Intent(this, TotalTeamNetworkingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
    }

    public void teamDeActiveClick(TeamData item) {

        if (item.getId() == 4) {
            Intent i = new Intent(this, DirectTeamNetworkingActivity.class);
            i.putExtra("Deactive", getFilterStatusFalse);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 5) {
            Intent i = new Intent(this, UplinePoolCountTeamNetworkingActivity.class);
            i.putExtra("status", "Deactive");
            i.putExtra("BusinessType", balanceCheckResponse.isBinaryon);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 10) {
            Intent i = new Intent(this, TotalTeamNetworkingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
    }

    public void teamItemClick(DashboardTeamDetailsList item, String filterStatus) {

        if (item.getId() == 1) {
            Intent i = new Intent(this, GenelogyGlobalNetworkingActivity.class);
            i.putExtra("UserId", mLoginDataResponse.getData().getUserID() + "");
            i.putExtra("Url", item.getUrl() + "");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 2) {
            Intent i = new Intent(this, GenelogyGlobalNetworkingActivity.class);
            i.putExtra("UserId", mLoginDataResponse.getData().getUserID() + "");
            i.putExtra("Url", item.getUrl() + "");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 3) {
            Intent i = new Intent(this, GenelogyGlobalNetworkingActivity.class);
            i.putExtra("UserId", mLoginDataResponse.getData().getUserID() + "");
            i.putExtra("Url", item.getUrl() + "");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 4) {
            Intent i = new Intent(this, DirectTeamNetworkingActivity.class);
            i.putExtra("Deactive", filterStatus);
            i.putExtra("BusinessType", balanceCheckResponse.isBinaryon);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 5) {
            Intent i = new Intent(this, UplinePoolCountTeamNetworkingActivity.class);
            i.putExtra("status", "All");
            i.putExtra("BusinessType", balanceCheckResponse.isBinaryon);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 6) {
            Intent i = new Intent(this, UplinePoolCountTeamNetworkingActivity.class);
            i.putExtra("status", "Active");
            i.putExtra("BusinessType", balanceCheckResponse.isBinaryon);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 7) {
            Intent i = new Intent(this, UplinePoolCountTeamNetworkingActivity.class);
            i.putExtra("status", "Deactive");
            i.putExtra("BusinessType", balanceCheckResponse.isBinaryon);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 8) { // Left Team
            Intent i = new Intent(this, TotalTeamNetworkingActivity.class);
            i.putExtra("Leg", "L");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 9) {//Right Team
            Intent i = new Intent(this, TotalTeamNetworkingActivity.class);
            i.putExtra("Leg", "R");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 10) {
            Intent i = new Intent(this, TotalTeamNetworkingActivity.class);
            i.putExtra("BusinessType", balanceCheckResponse.isBinaryon);
            i.putExtra("Deactive", getFilterStatusTrue);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
    }

    public void businessItemClick(DashboardTeamDetailsList item) {
        if (item.getId() == 11) {
            Intent i = new Intent(this, TotalBuisnessNetworkingActivity.class);
            i.putExtra("Leg", "L");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 12) {
            Intent i = new Intent(this, TotalBuisnessNetworkingActivity.class);
            i.putExtra("Leg", "R");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 13) {
            Intent i = new Intent(this, DirectBuisnessNetworkingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 14) {
            Intent i = new Intent(this, SponserBuisnessNetworkingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 15) {
            Intent i = new Intent(this, SelfBuisnessNetworkingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 17) {
            Intent i = new Intent(this, TotalBuisnessNetworkingActivity.class);
            i.putExtra("Leg", "All");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
    }

    public void poolItemClick(DashboardTeamDetailsList item) {
        if (item.getId() == 1) {
            Intent i = new Intent(this, PoolTeamNetworkingActivity.class);
            i.putExtra("PoolMatrixTree", true);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 3) {
            Intent i = new Intent(this, PoolTeamNetworkingActivity.class);
            i.putExtra("PoolMatrixTree", true);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
    }

    public void poolReportItemClick(DashboardTeamDetailsList item) {
        if (item.getId() == 1) {
            Intent i = new Intent(this, PoolTeamNetworkingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 3) {
            Intent i = new Intent(this, PoolTeamNetworkingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
    }

    public void poolItemGenelogyClick(DashboardTeamDetailsList item) {
        if (item.getId() == 3) {
            Intent i = new Intent(this, GenelogyGlobalNetworkingActivity.class);
            i.putExtra("UserId", mLoginDataResponse.getData().getUserID() + "");
            i.putExtra("Url", item.getUrl() + "");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (item.getId() == 1) {
            Intent i = new Intent(this, GenelogyGlobalNetworkingActivity.class);
            i.putExtra("UserId", mLoginDataResponse.getData().getUserID() + "");
            i.putExtra("Url", item.getUrl() + "");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
    }
}