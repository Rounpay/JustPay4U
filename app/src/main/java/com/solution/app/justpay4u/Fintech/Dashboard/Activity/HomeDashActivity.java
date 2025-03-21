package com.solution.app.justpay4u.Fintech.Dashboard.Activity;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Telephony;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.gson.Gson;
import com.roundpay.emoneylib.EMoneyLoginActivity;
import com.roundpay.emoneylib.Object.MiniStatements;
import com.roundpay.emoneylib.Utils.KeyConstant;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Object.Banners;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.Api.Fintech.Object.SDKDetail;
import com.solution.app.justpay4u.Api.Fintech.Object.TargetAchieved;
import com.solution.app.justpay4u.Api.Fintech.Object.UserDaybookReport;
import com.solution.app.justpay4u.Api.Fintech.Response.AppGetAMResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BcResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OnboardingResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OperatorListResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.AssignedOpTypeIndustryWise;
import com.solution.app.justpay4u.ApiHits.OpTypeDataIndustryWise;
import com.solution.app.justpay4u.ApiHits.OpTypeIndustryWiseResponse;
import com.solution.app.justpay4u.Fintech.AEPS.Activity.AEPSDashboardActivity;
import com.solution.app.justpay4u.Fintech.AEPS.Activity.AEPSMiniStatementRPActivity;
import com.solution.app.justpay4u.Fintech.AEPS.Activity.AEPSStatusRPActivity;
import com.solution.app.justpay4u.Fintech.Activities.AccountOpenActivity;
import com.solution.app.justpay4u.Fintech.Activities.AchievedTargetActivity;
import com.solution.app.justpay4u.Fintech.Activities.CreateUserActivity;
import com.solution.app.justpay4u.Fintech.Activities.QrBankActivity;
import com.solution.app.justpay4u.Fintech.Activities.UpgradePackageActivity;
import com.solution.app.justpay4u.Fintech.Activities.UserProfileActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.AccountStatementReportActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.AppUserListActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.ChannelReportActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.FosAreaReportActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.FosReportActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.FosUserListActivity;
import com.solution.app.justpay4u.Fintech.CommissionSlab.Activity.CommissionScreenActivity;
import com.solution.app.justpay4u.Fintech.Dashboard.Adapter.CustomPagerAdapter;
import com.solution.app.justpay4u.Fintech.DthSubscription.Activity.DthSubscriptionActivity;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.AddMoneyActivity;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.DMRActivity;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.DMRWithPipeActivity;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.PaymentRequest;
import com.solution.app.justpay4u.Fintech.Info.Activity.InfoActivity;
import com.solution.app.justpay4u.Fintech.Info.Adapter.InfoContactDataListAdapter;
import com.solution.app.justpay4u.Fintech.Info.Model.InfoContactDataItem;
import com.solution.app.justpay4u.Fintech.MicroAtm.Activity.MicroATMStatusRPActivity;
import com.solution.app.justpay4u.Fintech.MicroAtm.Activity.MicroAtmActivity;
import com.solution.app.justpay4u.Fintech.MoveToWallet.Activity.MoveToWalletActivity;
import com.solution.app.justpay4u.Fintech.Notification.NotificationListActivity;
import com.solution.app.justpay4u.Fintech.PSA.Activity.PanApplicationActivity;
import com.solution.app.justpay4u.Fintech.Paynear.Activity.PaynearActivationActivity;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.RechargeBillPaymentActivity;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.RechargeProviderActivity;
import com.solution.app.justpay4u.Fintech.Reports.Activity.FundOrderPendingActivity;
import com.solution.app.justpay4u.Fintech.Reports.Activity.ReportActivity;
import com.solution.app.justpay4u.Fintech.Reports.Activity.UserDayBookActivity;
import com.solution.app.justpay4u.Fintech.UpiPayment.Activity.UPIListActivity;
import com.solution.app.justpay4u.Networking.Activity.NetworkingDashboardActivity;
import com.solution.app.justpay4u.Networking.Activity.WalletToWalletTransferNetworkingActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.ShoppingDashboardActivity;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.EKYCStepsDialog;
import com.solution.app.justpay4u.Util.GetLocation;
import com.solution.app.justpay4u.Util.TooltipPopup.BubbleDialog;
import com.solution.app.justpay4u.Util.Utility;
import com.solution.app.justpay4u.WalletToWalletTransfer.Activity.WalletToWalletTransferActivity;

import org.egram.aepslib.DashboardActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class HomeDashActivity extends AppCompatActivity {

    ImageView vaIv, qrIv;
    TextToSpeech tts;
    ImageView arrowBalanceDown, refresh;
    View newsCard;
    TextView userNameTv, roleTv, verifyUserIv, mobileNumTv, emailTv, prepaidBalTV;
    ImageView userImage;
    ImageView userIcon;
    TextView newsTv, newsTitle, sendMoneyTv;
    ViewPager mViewPager;
    View pagerContainer;
    LinearLayout dotsCount, ll_home;
    TextView badges_Notification;
    RecyclerView rechargeRecyclerView, transactionRecyclerView, utilityRecyclerView;
    View targetView, notificationView, summaryDashboard, addMoneyBtnView, sendMoneyBtnView, referandearn, planPdf, legals;

    BalanceResponse balanceCheckResponse;
    int mDotsCount;
    String fromDate, toDate;
    CustomLoader loader;
    View earningView, successView, pendingView, failedView;
    String deviceId, deviceSerialNum;
    ArrayList<OperatorList> mDMTOperatorLists = new ArrayList<>();
    private Handler handler;
    private Runnable mRunnable;
    private TextView remainTargetTv, salesTargetTv, todaySalesTv;
    private TextView successAmountTv, failedAmountTv, pendingAmountTv, commissionAmountTv;
    private LoginResponse mLoginDataResponse;
    private int userRollId;
    private ArrayList<Banners> bannerList = new ArrayList<>();
    private TextView[] mDotsText;
    private int pagerTop, pagerleft, padding25;
    private boolean isLoaderShouldShow;
    private CustomAlertDialog customAlertDialog;
    private View ll_report, ll_logout, ll_contactus, networkingView, shoppingView;
    private int notificationCount;
    private int INTENT_MINI_ATM_RP = 142;
    private int INTENT_MINI_ATM = 253;
    private int INTENT_PROFILE = 362;
    private int INTENT_NOTIFICATIONS = 438;
    private int INTENT_CODE_APES = 520;
    private int INTENT_AEPS_RP = 691;
    private int INTENT_UPGRADE_PACKAGE = 789;
    private int INTENT_PASSWORD_EXPIRE = 820;
    private int MY_REQUEST_UPDATE_APP_CODE = 967;
    private int INTENT_ADD_MONEY = 4545;

    private AppPreferences mAppPreferences;
    private boolean isUpdateDialogShowing;
    private AppUpdateManager appUpdateManager;
    private GetLocation mGetLocation;
    private OperatorListResponse operatorListResponse;
    private RequestOptions requestOptions;
    private boolean isDmtWithPipe;
    private boolean isBulkQRGeneration, isQRMappedToUser;
    private AppGetAMResponse mAppGetAMResponse;
    private EKYCStepsDialog mEKYCStepsDialog;
    private boolean isECollectEnable;
    private DropDownDialog mDropDownDialog;
    ArrayList<BalanceData> mBalanceDataList = new ArrayList<>();
    private boolean isFromLogin;
    int fromScreen, fromPreviousScreen; // 1=Fintech,2=Shopping, 3=Networking
    private String URL_HERE = "http://justpay4u.com/siteadmin/pdf/Plan.pdf";
    private String URL_legals_HERE = "http://justpay4u.com/siteadmin/pdf/Plan.pdf";
//    private String URL_legals_HERE = "http://JustPay4u.com/siteadmin/pdf/legals.pdf";
    private View moneyTransferContainerView, paymentTypeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_home_dash);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            findViewIds();
            setClickListner();
            new Handler(Looper.getMainLooper()).post(() -> {
                isFromLogin = getIntent().getBooleanExtra("FROM_LOGIN", false);
                fromScreen = getIntent().getIntExtra("FROM_SCREEN", 0);
                fromPreviousScreen = getIntent().getIntExtra("FROM_PRE_SCREEN", 0);

                showHideBottomBtn(mLoginDataResponse.isECommerceAllowed(), mLoginDataResponse.isMLM());

                mDropDownDialog = new DropDownDialog(this);
                loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
                setUserUiData();
                mEKYCStepsDialog = new EKYCStepsDialog(this, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);

                if (!mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.voiceEnablePref)) {
                    tts = new TextToSpeech(this, null);
                }
                customAlertDialog = new CustomAlertDialog(this);
                appUpdateManager = AppUpdateManagerFactory.create(this);
                if (fromScreen == 0) {
                    notificationView.setVisibility(View.VISIBLE);
                    LocalBroadcastManager.getInstance(this).registerReceiver(mNewNotificationReciver, new IntentFilter("New_Notification_Detect"));
                } else {
                    notificationView.setVisibility(View.GONE);
                }
                fromDate = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(new Date());
                toDate = fromDate;
                setStoredData();
                if (isFromLogin) {
//                    loader.show();
                    ApiFintechUtilMethods.INSTANCE.GetPopupAfterLogin(this, deviceId, deviceSerialNum, mLoginDataResponse);
                    DashboardApi(true);
                } else {
                    DashboardApi(false);
                }

            });

        });
    }

    void findViewIds() {
        pagerTop = (int) getResources().getDimension(R.dimen._3sdp);
        pagerleft = (int) getResources().getDimension(R.dimen._6sdp);
        padding25 = (int) getResources().getDimension(R.dimen._25sdp);
        pagerContainer = findViewById(R.id.pagerContainer);
        mViewPager = findViewById(R.id.pager);
        newsCard = findViewById(R.id.newsCard);
        sendMoneyTv = findViewById(R.id.sendMoneyTv);
        newsTv = findViewById(R.id.newsTv);
        newsTitle = findViewById(R.id.newsTitle);
        dotsCount = findViewById(R.id.image_count);
        earningView = findViewById(R.id.earningView);
        vaIv = findViewById(R.id.vaIv);
        qrIv = findViewById(R.id.qrIv);
        successView = findViewById(R.id.successView);
        pendingView = findViewById(R.id.pendingView);
        failedView = findViewById(R.id.failedView);
        arrowBalanceDown = findViewById(R.id.arrowBalanceDown);

        paymentTypeView = findViewById(R.id.paymentTypeView);
        paymentTypeView.setVisibility(View.GONE);


        rechargeRecyclerView = findViewById(R.id.rechargeRecyclerView);
        rechargeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        rechargeRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        transactionRecyclerView = findViewById(R.id.transactionRecyclerView);
        transactionRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        utilityRecyclerView = findViewById(R.id.utilityRecyclerView);
        utilityRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));


        userNameTv = findViewById(R.id.userNameTv);
        roleTv = findViewById(R.id.roleTv);
        verifyUserIv = findViewById(R.id.verifyUserIv);
        mobileNumTv = findViewById(R.id.mobileNumTv);
        emailTv = findViewById(R.id.emailTv);
        userImage = findViewById(R.id.userImage);
        userIcon = findViewById(R.id.userIcon);

        prepaidBalTV = findViewById(R.id.prepaidBalTV);
        targetView = findViewById(R.id.targetView);
        notificationView = findViewById(R.id.notificationView);
        summaryDashboard = findViewById(R.id.summaryDashboard);
        remainTargetTv = findViewById(R.id.remainTargetAmountTv);
        salesTargetTv = findViewById(R.id.saleTargetAmountTv);
        todaySalesTv = findViewById(R.id.todaySalesAmountTv);
        addMoneyBtnView = findViewById(R.id.addMoneyView);
        sendMoneyBtnView = findViewById(R.id.sendMoneyView);
        successAmountTv = findViewById(R.id.successAmountTv);
        failedAmountTv = findViewById(R.id.failedAmountTv);
        pendingAmountTv = findViewById(R.id.pendingAmountTv);
        commissionAmountTv = findViewById(R.id.commissionAmountTv);
        ll_report = findViewById(R.id.ll_report);
        ll_logout = findViewById(R.id.ll_logout);
        ll_contactus = findViewById(R.id.ll_contactus);
        badges_Notification = findViewById(R.id.badgesNotification);
        refresh = findViewById(R.id.refresh);
        networkingView = findViewById(R.id.networkingView);
        shoppingView = findViewById(R.id.shoppingView);
        ll_home = findViewById(R.id.ll_home);
        referandearn = findViewById(R.id.referandearn);
        planPdf = findViewById(R.id.planPdf);
        legals = findViewById(R.id.legals);
        referandearn.setOnClickListener(v -> shareIt(null));
        planPdf.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(URL_HERE));
            startActivity(intent);
//            startActivity(Intent(Intent.ACTION_VIEW));
        });
        legals.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(URL_legals_HERE));
            startActivity(intent);
//            startActivity(Intent(Intent.ACTION_VIEW));
        });

        requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_UserIcon();

        Glide.with(this)
                .load(ApplicationConstant.INSTANCE.baseProfilePicUrl + mLoginDataResponse.getData().getUserID() + ".png")
                .apply(requestOptions)
                .into(userImage);
        Glide.with(this)
                .load(ApplicationConstant.INSTANCE.baseProfilePicUrl + mLoginDataResponse.getData().getUserID() + ".png")
                .apply(requestOptions)
                .into(userIcon);
    }

    private void shareIt(String packageName) {

        String shareMessage = getString(R.string.share_msg, ApplicationConstant.INSTANCE.inviteUrl + mLoginDataResponse.getData().getUserID());

        try {
            if (packageName != null && packageName.equalsIgnoreCase("sms")) {
                Uri uri = Uri.parse("smsto:");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", shareMessage);
                startActivity(Intent.createChooser(intent, "choose one"));
            } else if (packageName != null && packageName.equalsIgnoreCase("email")) {
                Uri uri = Uri.parse("mailto:");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(intent, "choose one"));
            } else {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                if (packageName != null) {
                    shareIntent.setPackage(packageName);
                }
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            }


        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            if (packageName.equalsIgnoreCase("sms")) {
                sendSms(shareMessage);
            } else if (packageName.equalsIgnoreCase("email")) {
                sendEmail(shareMessage);
            } else if (packageName.contains("facebook")) {
                String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + ApplicationConstant.INSTANCE.inviteUrl + mLoginDataResponse.getData().getUserID();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                startActivity(intent);
            } else if (packageName.contains("whatsapp")) {
                String sharerUrl = "https://wa.me/send?text=" + shareMessage;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                startActivity(intent);
            } else if (packageName.contains("twitter")) {
                String sharerUrl = "https://twitter.com/intent/tweet?text=" + shareMessage;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Sorry, App not found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sendEmail(String shareMessage) {
        try {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(intent, "choose one"));
        } catch (ActivityNotFoundException anfe) {

        } catch (Exception e) {

        }
    }

    void sendSms(String shareMessage) {
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) //At least KitKat
            {
                String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); //Need to change the build to API 19
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                if (defaultSmsPackageName != null)//Can be null in case that there is no default, then the user would be able to choose any app that support this intent.
                {
                    sendIntent.setPackage(defaultSmsPackageName);
                }
                startActivity(Intent.createChooser(sendIntent, "choose one"));

            } else //For early versions, do what worked for you before.
            {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.putExtra("sms_body", shareMessage);
                startActivity(Intent.createChooser(sendIntent, "choose one"));
            }
        } catch (ActivityNotFoundException anfe) {

        } catch (Exception e) {

        }
    }

    void showHideBottomBtn(boolean isECommerceAllowed, boolean isMLM) {
        if (fromScreen == 3 && fromPreviousScreen == 2) {
            networkingView.setVisibility(View.GONE);
            shoppingView.setVisibility(View.GONE);
            ll_contactus.setVisibility(View.VISIBLE);
            ll_logout.setVisibility(View.VISIBLE);
        } else if (fromScreen == 2 && fromPreviousScreen == 3) {
            networkingView.setVisibility(View.GONE);
            shoppingView.setVisibility(View.GONE);
            ll_contactus.setVisibility(View.VISIBLE);
            ll_logout.setVisibility(View.VISIBLE);
        } else if (fromScreen == 3 && fromPreviousScreen != 2) {
            networkingView.setVisibility(View.GONE);
            ll_contactus.setVisibility(View.VISIBLE);
            if (isECommerceAllowed) {
                shoppingView.setVisibility(View.VISIBLE);
                ll_logout.setVisibility(View.GONE);
            } else {
                shoppingView.setVisibility(View.GONE);
                ll_logout.setVisibility(View.VISIBLE);
            }

        } else if (fromScreen == 2 && fromPreviousScreen != 3) {

            if (isMLM) {
                networkingView.setVisibility(View.VISIBLE);
                ll_logout.setVisibility(View.GONE);
            } else {
                networkingView.setVisibility(View.GONE);
                ll_logout.setVisibility(View.VISIBLE);
            }
            ll_contactus.setVisibility(View.VISIBLE);
            shoppingView.setVisibility(View.GONE);
        } else {
            if (isECommerceAllowed && isMLM) {
                ll_contactus.setVisibility(View.GONE);
                ll_logout.setVisibility(View.GONE);
                shoppingView.setVisibility(View.VISIBLE);
                networkingView.setVisibility(View.VISIBLE);
            } else if (!isECommerceAllowed && isMLM) {
                ll_contactus.setVisibility(View.VISIBLE);
                ll_logout.setVisibility(View.GONE);
                shoppingView.setVisibility(View.GONE);
                networkingView.setVisibility(View.VISIBLE);
            } else if (isECommerceAllowed && !isMLM) {
                ll_logout.setVisibility(View.GONE);
                ll_contactus.setVisibility(View.VISIBLE);
                shoppingView.setVisibility(View.VISIBLE);
                networkingView.setVisibility(View.GONE);
            } else {
                ll_contactus.setVisibility(View.VISIBLE);
                ll_logout.setVisibility(View.VISIBLE);
                networkingView.setVisibility(View.GONE);
                shoppingView.setVisibility(View.GONE);
            }

        }
    }

    void setClickListner() {
        networkingView.setOnClickListener(v -> {
            startActivity(new Intent(this, NetworkingDashboardActivity.class)
                    .putExtra("FROM_LOGIN", isFromLogin)
                    .putExtra("FROM_SCREEN", 1)
                    .putExtra("FROM_PRE_SCREEN", fromScreen)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));


        });
        shoppingView.setOnClickListener(v -> {

            startActivity(new Intent(this, ShoppingDashboardActivity.class)
                    .putExtra("FROM_LOGIN", isFromLogin)
                    .putExtra("FROM_SCREEN", 1)
                    .putExtra("FROM_PRE_SCREEN", fromScreen)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));


        });
        ll_report.setOnClickListener(v -> {
            startActivity(new Intent(HomeDashActivity.this, ReportActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });
    /*    ll_home.setOnClickListener(v -> {
            startActivity(new Intent(HomeDashActivity.this, NetworkingDashboardActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });
*/
        //  findViewById(R.id.ll_fundrequest).setOnClickListener(v -> openNewScreen(1010, 0, ""));

        ll_contactus.setOnClickListener(v -> contactUsData(v));

        ll_logout.setOnClickListener(v ->
                customAlertDialog.Successfullogout(loader, "Do you really want to Logout?", this,
                        mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences));

        qrIv.setOnClickListener(v -> startActivity(new Intent(HomeDashActivity.this, QRCodePaymentActivity.class)
                .putExtra("isQRMappedToUser", isQRMappedToUser)
                .putExtra("isBulkQRGeneration", isBulkQRGeneration)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));


        vaIv.setOnClickListener(v -> startActivity(new Intent(HomeDashActivity.this, VirtualAccountActivity.class)
                .putExtra("isQRMappedToUser", isQRMappedToUser)
                .putExtra("isBulkQRGeneration", isBulkQRGeneration)
                .putExtra("isECollectEnable", isECollectEnable)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));

        findViewById(R.id.ll_support).setOnClickListener(v ->
                startActivity(new Intent(HomeDashActivity.this, InfoActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));


        findViewById(R.id.walletView).setOnClickListener(v -> {
            if (mBalanceDataList != null && mBalanceDataList.size() > 0) {
                if (mBalanceDataList.size() > 1) {
                    mDropDownDialog.showDropDownBalanceHomePopup(v, mBalanceDataList, R.layout.dialog_drop_down_balance_home);
                }
            } else {
                showBalanceData();
            }

        });

        targetView.setOnClickListener(v -> {
            Intent i = new Intent(this, AchievedTargetActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });

        summaryDashboard.setOnClickListener(v -> {
            Intent i = new Intent(this, UserDayBookActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });

        notificationView.setOnClickListener(v -> {
            Intent i = new Intent(this, NotificationListActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_NOTIFICATIONS);
        });

        findViewById(R.id.userDetailView).setOnClickListener(v -> {

            startActivityForResult(new Intent(HomeDashActivity.this, UserProfileActivity.class)
                    .putExtra("IsAddMoneyVisible", addMoneyBtnView.getVisibility() == View.VISIBLE)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_PROFILE);
        });
        findViewById(R.id.userIcon).setOnClickListener(v -> {

            startActivityForResult(new Intent(HomeDashActivity.this, UserProfileActivity.class)
                    .putExtra("IsAddMoneyVisible", addMoneyBtnView.getVisibility() == View.VISIBLE)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_PROFILE);
        });


        refresh.setOnClickListener(v -> {
            ApiFintechUtilMethods.INSTANCE.mAccountOpenOPTypes = new HashMap<>();

            RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(500);
            rotate.setRepeatCount(Animation.INFINITE);
            rotate.setInterpolator(new LinearInterpolator());
            refresh.startAnimation(rotate);


            DashboardApi(true);

        });

        addMoneyBtnView.setOnClickListener(v -> {
            Intent i = new Intent(HomeDashActivity.this, AddMoneyActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_ADD_MONEY);
        });

        sendMoneyBtnView.setOnClickListener(v -> {

            if (userRollId == 3 || userRollId == 2) {
                startActivity(new Intent(this, MoveToWalletActivity.class)
                        .putParcelableArrayListExtra("items", mBalanceDataList)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            } else if (userRollId == 8) {
                startActivity(new Intent(this, FosUserListActivity.class)
                        .putExtra("Title", "Send Money To User")
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            } else {
                Intent i = new Intent(HomeDashActivity.this, AppUserListActivity.class);
                i.putExtra("Title", "Send Money To User");
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        });
        findViewById(R.id.toTransfer).setOnClickListener(v -> {
            openPipeDmt();
        });
        findViewById(R.id.addMoney).setOnClickListener(v -> {
//            isNewScreenOpen = true;
            Intent i = new Intent(HomeDashActivity.this, AddMoneyActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        findViewById(R.id.toUpi).setOnClickListener(v -> {
//            isNewScreenOpen = true;
            Intent i = new Intent(HomeDashActivity.this, UPIListActivity.class);
            i.putExtra("BalanceData", balanceCheckResponse);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        findViewById(R.id.toMobile).setOnClickListener(v -> {
//            isNewScreenOpen = true;
            Intent intent = new Intent(HomeDashActivity.this, WalletToWalletTransferNetworkingActivity.class)
                    .putExtra("BalanceData", balanceCheckResponse)
                    .putExtra("OnlyBalanceData", balanceCheckResponse.getBalanceData().get(0))
                    .putExtra("WalletTransferType", balanceCheckResponse.getBalanceData().get(0).getWalletTransferType())
                    .putExtra("FromWalletId", balanceCheckResponse.getBalanceData().get(0).getId())
                    .putParcelableArrayListExtra("WalletList", balanceCheckResponse.getBalanceData().get(0).getAllowedWallet());
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }

    void setStoredData() {


        AppUserListResponse newsData = ApiFintechUtilMethods.INSTANCE.getNewsData(mAppPreferences);
        if (newsData != null && newsData.getNewsContent() != null && newsData.getNewsContent().getNewsDetail() != null && !newsData.getNewsContent().getNewsDetail().isEmpty()) {
            newsTv.setText(Html.fromHtml(newsData.getNewsContent().getNewsDetail() + ""));
            newsCard.setVisibility(View.VISIBLE);
        } else {
            newsCard.setVisibility(View.GONE);
        }
        showBalanceData();
        setDashboardData(new Gson().fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.activeServicePref), OpTypeIndustryWiseResponse.class));


        new Handler(Looper.getMainLooper()).post(() -> {
//            setDashboardData(ApiFintechUtilMethods.INSTANCE.getActiveService(mAppPreferences));
            AppUserListResponse bannerData = ApiFintechUtilMethods.INSTANCE.getBannerData(mAppPreferences);
            setBannerData(bannerData);


       /* dataDayBookParse(new Gson().fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.dayBookDataPref), AppUserListResponse.class));
        if (targetView.getVisibility() == View.VISIBLE) {
            achieveTargetParse(new Gson().fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.totalTargetDataPref), AppUserListResponse.class));
        }*/

        });


    }

    void setUserUiData() {
        userNameTv.setText(mLoginDataResponse.getData().getName() /*+ " - " + mLoginDataResponse.getData().getRoleName()*/);
        roleTv.setText(mLoginDataResponse.getData().getPrefix() + "" + mLoginDataResponse.getData().getUserID() + "");
        mobileNumTv.setText(mLoginDataResponse.getData().getMobileNo() + "");
        emailTv.setText(mLoginDataResponse.getData().getEmailID() + "");
        userRollId = mLoginDataResponse.getData().getRoleID();
        if (userRollId == 3 || userRollId == 2) {
            failedView.setVisibility(View.VISIBLE);
            pendingView.setVisibility(View.VISIBLE);
            sendMoneyTv.setText("AEPS Settlement");
            mGetLocation = new GetLocation(this, loader);
            /*if (ApiUtilMethods.INSTANCE.getLattitude == 0 || ApiUtilMethods.INSTANCE.getLongitude == 0) {
                mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
                    ApiUtilMethods.INSTANCE.getLattitude = lattitude;
                    ApiUtilMethods.INSTANCE.getLongitude = longitude;
                });
            }*/
        } else {
            failedView.setVisibility(View.GONE);
            pendingView.setVisibility(View.GONE);
            sendMoneyTv.setText("Fund Transfer");
        }
    }

    @SuppressLint("SetTextI18n")
    private void showBalanceData() {

        mBalanceDataList.clear();
        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                && balanceCheckResponse.getBalanceData().size() > 0) {
            isBulkQRGeneration = balanceCheckResponse.isBulkQRGeneration();
            isQRMappedToUser = balanceCheckResponse.isQRMappedToUser();
            mBalanceDataList = balanceCheckResponse.getBalanceData();
            prepaidBalTV.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero(mBalanceDataList.get(0).getBalance()));

           /* if (mLoginDataResponse.isAccountStatement()) {
                mBalanceDataList.add(new BalanceData("Outstanding Wallet", balanceCheckResponse.getOsBalance()));

            }
*/

            if (mBalanceDataList.size() > 1) {

                arrowBalanceDown.setVisibility(View.VISIBLE);
                sendMoneyBtnView.setVisibility(View.VISIBLE);

                /*if (transactionRecyclerView.getPaddingTop() == transactionRecyclerView.getPaddingBottom()) {
                    transactionRecyclerView.setPadding(transactionRecyclerView.getPaddingLeft(),
                            padding25,
                            transactionRecyclerView.getPaddingRight(), transactionRecyclerView.getPaddingBottom());
                }*/

            } else {
                arrowBalanceDown.setVisibility(View.GONE);
                sendMoneyBtnView.setVisibility(View.GONE);
                /*if (addMoneyBtnView.getVisibility() == View.GONE) {
                    transactionRecyclerView.setPadding(transactionRecyclerView.getPaddingLeft(),
                            transactionRecyclerView.getPaddingBottom(),
                            transactionRecyclerView.getPaddingRight(), transactionRecyclerView.getPaddingBottom());
                }*/
            }

            showHideBottomBtn(balanceCheckResponse.isECommerceAllowed(), balanceCheckResponse.isMLM());

            if (balanceCheckResponse.isUPI() || balanceCheckResponse.isPaymentGatway()) {
                addMoneyBtnView.setVisibility(View.VISIBLE);
                //its code for add money show
                //change this
            } else {
                addMoneyBtnView.setVisibility(View.GONE);
            }
            if (balanceCheckResponse.getIsTopup() == 1) {

                verifyUserIv.setText("Active");
                verifyUserIv.setTextColor(Color.GREEN);
            } else {
                verifyUserIv.setText("Inactive");
                verifyUserIv.setTextColor(Color.RED);
            }
        } else {
            balanceCheckResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                    && balanceCheckResponse.getBalanceData().size() > 0) {
                showBalanceData();
            } else {
                ApiFintechUtilMethods.INSTANCE.Balancecheck(this, loader, mLoginDataResponse, deviceId, deviceSerialNum,
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

    public void DashboardApi(boolean isRefresh) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            try {
                numberListApiAndData(isRefresh);
                ApiFintechUtilMethods.INSTANCE.NewsApi(this, false, newsTv, newsCard, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);
                BannerApi();
                ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, null, object -> {
                    balanceCheckResponse = (BalanceResponse) object;
                    if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null && balanceCheckResponse.getBalanceData().size() > 0) {
                        showBalanceData();
                    }
                });
                isLoaderShouldShow = false;
                HitDayReportApi(isRefresh);
                if (fromScreen == 0) {
                    ApiFintechUtilMethods.INSTANCE.GetNotifications(this, mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum, object -> {
                        notificationCount = ((int) object);
                        setNotificationCount();
                    });
                }
                if (isRefresh) {
                    if (userRollId != 2 && userRollId != 3) {
                        ApiFintechUtilMethods.INSTANCE.GetArealist(this, null, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, new ApiFintechUtilMethods.ApiResponseCallBack() {
                            @Override
                            public void onSucess(Object object) {
                                mAppGetAMResponse = (AppGetAMResponse) object;
                            }

                            @Override
                            public void onError(int error) {

                            }
                        });
                    }
                    ApiFintechUtilMethods.INSTANCE.WalletType(this, mLoginDataResponse, mAppPreferences, null);
                    getActiveService();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    void numberListApiAndData(boolean isRefresh) {
        if (isRefresh) {
            ApiFintechUtilMethods.INSTANCE.NumberSeriesList(this, isRefresh ? refresh : null, deviceId, deviceSerialNum, mAppPreferences, null);
        }

        if (isRefresh) {
            ApiFintechUtilMethods.INSTANCE.OperatorList(this, deviceId, deviceSerialNum, mAppPreferences, object -> {
                operatorListResponse = (OperatorListResponse) object;
                if (operatorListResponse != null && operatorListResponse.getDmtOperatorLists() != null) {
                    mDMTOperatorLists = operatorListResponse.getDmtOperatorLists();
                }
            });
        } else {
            operatorListResponse = ApiFintechUtilMethods.INSTANCE.getOperatorListResponse(mAppPreferences);
            if (operatorListResponse != null && operatorListResponse.getDmtOperatorLists() != null) {
                mDMTOperatorLists = operatorListResponse.getDmtOperatorLists();
            }
        }
        if (isRefresh) {
            ApiFintechUtilMethods.INSTANCE.CircleZoneList(this, deviceId, deviceSerialNum, mAppPreferences, null);
        }
    }

    public void BannerApi() {
        try {

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
           /* FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<AppUserListResponse> call = git.GetAppBanner(new BasicRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatuscode() == 1) {
                                mAppPreferences.set(ApplicationConstant.INSTANCE.appLogoUrlPref, response.body().getAppLogoUrl() + "");
                                ApiFintechUtilMethods.INSTANCE.appLogoUrl = response.body().getAppLogoUrl() + "";
                                if (response.body().getBanners() != null && response.body().getBanners().size() > 0) {
                                    ApiFintechUtilMethods.INSTANCE.mBannerDataResponse = response.body();
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.bannerDataPref, new Gson().toJson(response.body()));
                                    setBannerData(response.body());
                                } else {
                                    pagerContainer.setVisibility(View.GONE);
                                }

                            }
                        } else {
                            //ApiUtilMethods.INSTANCE.apiErrorHandle(HomeDashActivity.this,response.code(),response.message());
                        }
                    } catch (Exception e) {

                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {


                }
            });*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

                mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        mRunnable = new Runnable() {
            public void run() {

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
            }
        };
        handler.postDelayed(mRunnable, 2500);

    }

    private void HitDayReportApi(boolean isRefresh) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {


            String childNumber;
            if (userRollId == 3 || userRollId == 2) {
                childNumber = "";
            } else {
                childNumber = mLoginDataResponse.getData().getMobileNo();
            }
            if (mLoginDataResponse.isTargetShow()) {
                ApiFintechUtilMethods.INSTANCE.UserAchieveTarget(this, true, null, mLoginDataResponse, mAppPreferences,
                        deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
                            @Override
                            public void onSucess(Object object) {

                                achieveTargetParse((AppUserListResponse) object, isRefresh);
                            }

                            @Override
                            public void onError(int error) {
                                targetView.setVisibility(View.GONE);
                                if (isRefresh && utilityRecyclerView.getPaddingBottom() > utilityRecyclerView.getPaddingTop()) {
                                    utilityRecyclerView.setPadding(utilityRecyclerView.getPaddingLeft(), utilityRecyclerView.getPaddingTop(),
                                            utilityRecyclerView.getPaddingRight(), utilityRecyclerView.getPaddingTop());
                                }
                            }
                        });
            } else {
                targetView.setVisibility(View.GONE);
                if (isRefresh && utilityRecyclerView.getPaddingBottom() > utilityRecyclerView.getPaddingTop()) {
                    utilityRecyclerView.setPadding(utilityRecyclerView.getPaddingLeft(), utilityRecyclerView.getPaddingTop(),
                            utilityRecyclerView.getPaddingRight(), utilityRecyclerView.getPaddingTop());
                }
            }

            if (isLoaderShouldShow && !loader.isShowing()) {
                loader.show();
            }
            ApiFintechUtilMethods.INSTANCE.UserDayBook(this, childNumber, fromDate, toDate, isLoaderShouldShow ? loader : null, mLoginDataResponse,
                    mAppPreferences, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {

                            dataDayBookParse((AppUserListResponse) object);
                        }

                        @Override
                        public void onError(int error) {

                        }
                    });

        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    public void achieveTargetParse(AppUserListResponse response, boolean isRefresh) {
        try {
            if (response != null) {

                List<TargetAchieved> transactionsObjects = response.getTargetAchieveds();
                if (transactionsObjects != null && transactionsObjects.size() > 0) {

                    int remainigTarget = (int) (transactionsObjects.get(0).getTarget() - transactionsObjects.get(0).getTargetTillDate());
                    if (remainigTarget < 0) {
                        remainTargetTv.setTextColor(getResources().getColor(R.color.darkGreen));
                    }
                    remainTargetTv.setText(getString(R.string.rupiya) + " " + remainigTarget);
                    salesTargetTv.setText(getString(R.string.rupiya) + " " + (int) transactionsObjects.get(0).getTarget());
                    todaySalesTv.setText(getString(R.string.rupiya) + " " + (int) transactionsObjects.get(0).getTodaySale());

                    targetView.setVisibility(View.VISIBLE);
                    utilityRecyclerView.setPadding(utilityRecyclerView.getPaddingLeft(), utilityRecyclerView.getPaddingTop(),
                            utilityRecyclerView.getPaddingRight(), padding25);
                } else {
                    targetView.setVisibility(View.GONE);

                    if (isRefresh && utilityRecyclerView.getPaddingBottom() > utilityRecyclerView.getPaddingTop()) {
                        utilityRecyclerView.setPadding(utilityRecyclerView.getPaddingLeft(), utilityRecyclerView.getPaddingTop(),
                                utilityRecyclerView.getPaddingRight(), utilityRecyclerView.getPaddingTop());
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dataDayBookParse(AppUserListResponse response) {
        try {
            if (response != null) {
                summaryDashboard.setVisibility(View.GONE);
                //  summaryDashboard.setVisibility(View.VISIBLE);
                double successAmount = 0, pendingAmount = 0, failedAmount = 0, commissionAmount = 0;
                int successHit = 0, pendingHit = 0, failedHit = 0, commissionHit = 0;
                List<UserDaybookReport> transactionsObjects = response.getUserDaybookReport();
                if (transactionsObjects != null && transactionsObjects.size() > 0) {

                    for (UserDaybookReport mUserDaybookReport : transactionsObjects) {
                        try {
                            successAmount = successAmount + mUserDaybookReport.getSuccessAmount();
                        } catch (NumberFormatException nfe) {

                        }

                        try {
                            successHit = successHit + mUserDaybookReport.getSuccessHits();
                        } catch (NumberFormatException nfe) {

                        }
                        try {
                            if (userRollId == 3 || userRollId == 2) {
                                commissionAmount = commissionAmount + mUserDaybookReport.getCommission();
                            } else {
                                commissionAmount = commissionAmount + mUserDaybookReport.getSelfCommission();
                            }
                        } catch (NumberFormatException nfe) {

                        }

                        try {
                            commissionHit = commissionHit + mUserDaybookReport.getTotalHits();
                        } catch (NumberFormatException nfe) {
                        }
                        try {
                            pendingAmount = pendingAmount + mUserDaybookReport.getPendingAmount();
                        } catch (NumberFormatException nfe) {

                        }
                        try {
                            pendingHit = pendingHit + mUserDaybookReport.getPendingHits();
                        } catch (NumberFormatException nfe) {
                        }
                        try {
                            failedAmount = failedAmount + mUserDaybookReport.getFailedAmount();
                        } catch (NumberFormatException nfe) {


                        }
                        try {
                            failedHit = failedHit + mUserDaybookReport.getFailedHits();
                        } catch (NumberFormatException nfe) {

                        }
                    }
                }
                //  int todaySales = (int) (successAmount + pendingAmount);
                // todaySalesTv.setText(getString(R.string.rupiya) + " " + todaySales);
                successAmountTv.setText(Utility.INSTANCE.formatedAmountWithRupees(String.format("%.2f", successAmount)) + " (" + successHit + ")");
                failedAmountTv.setText(Utility.INSTANCE.formatedAmountWithRupees(String.format("%.2f", failedAmount)) + " (" + failedHit + ")");
                pendingAmountTv.setText(Utility.INSTANCE.formatedAmountWithRupees(String.format("%.2f", pendingAmount)) + " (" + pendingHit + ")");
                commissionAmountTv.setText(Utility.INSTANCE.formatedAmountWithRupees(String.format("%.2f", commissionAmount)) + " (" + commissionHit + ")");
            } else {
                isLoaderShouldShow = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDashboardData(OpTypeIndustryWiseResponse mOpTypeIndustryWiseResponse) {
        if (mOpTypeIndustryWiseResponse != null && mOpTypeIndustryWiseResponse.getData() != null &&
                mOpTypeIndustryWiseResponse.getData().size() > 0) {
            /*if (mOpTypeIndustryWiseResponse.isUpiServiceEnable()) {
                sendMoneyIv.setVisibility(View.GONE);
                paymentTypeView.setVisibility(View.VISIBLE);
                toScanView.setVisibility(View.VISIBLE);
            } else {
                paymentTypeView.setVisibility(View.GONE);
                sendMoneyIv.setVisibility(View.VISIBLE);
                toScanView.setVisibility(View.GONE);
            }*/
            paymentTypeView.setVisibility(View.VISIBLE);
//            sendMoneyIv.setVisibility(View.GONE);
            if (balanceCheckResponse.isUPI()) {
                qrIv.setVisibility(View.VISIBLE);
            } else {
                qrIv.setVisibility(View.GONE);
            }

            if (balanceCheckResponse.isECollectEnable()) {
                vaIv.setVisibility(View.VISIBLE);
            } else {
                vaIv.setVisibility(View.GONE);
            }
            isDmtWithPipe = mOpTypeIndustryWiseResponse.isDMTWithPipe();


            rechargeRecyclerView.setVisibility(View.VISIBLE);
            setOptionListData(mOpTypeIndustryWiseResponse.getData(), rechargeRecyclerView);


        } else {
            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(HomeDashActivity.this)) {
                getActiveService();
            } else {
                ApiFintechUtilMethods.INSTANCE.NetworkError(HomeDashActivity.this);
            }
        }

    }

 /*   private void setDashboardData(final DashBoardServiceReportOptions mActiveServiceData) {

        if (userRollId == 0) {
            userRollId = mLoginDataResponse.getData().getRoleID();

            if (userRollId == 3 || userRollId == 2) {
                failedView.setVisibility(View.VISIBLE);
                pendingView.setVisibility(View.VISIBLE);
                sendMoneyTv.setText("AEPS Settlement");

                mGetLocation = new GetLocation(this, loader);
                */


    /*if (ApiUtilMethods.INSTANCE.getLattitude == 0 || ApiUtilMethods.INSTANCE.getLongitude == 0) {
                    mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
                        ApiUtilMethods.INSTANCE.getLattitude = lattitude;
                        ApiUtilMethods.INSTANCE.getLongitude = longitude;
                    });
                }*//*

            } else {
                failedView.setVisibility(View.GONE);
                pendingView.setVisibility(View.GONE);
                sendMoneyTv.setText("Fund Transfer");
            }
        }

        if (mActiveServiceData != null) {
           *//* if (mActiveServiceData.isUpi()) {
                qrIv.setVisibility(View.VISIBLE);
            } else {
                qrIv.setVisibility(View.GONE);
            }*//*
            isDmtWithPipe = mActiveServiceData.isDmtWithPipe();
            isECollectEnable = mActiveServiceData.isECollectEnable();
            if (mActiveServiceData.isUPIQR()) {
                vaIv.setVisibility(View.VISIBLE);
            } else {
                vaIv.setVisibility(View.GONE);
            }

            *//*if (mActiveServiceData.isAddMoneyEnable()) {
                addMoneyBtnView.setVisibility(View.VISIBLE);

                if (transactionRecyclerView.getPaddingTop() == transactionRecyclerView.getPaddingBottom()) {
                    transactionRecyclerView.setPadding(transactionRecyclerView.getPaddingLeft(),
                            padding25,
                            transactionRecyclerView.getPaddingRight(), transactionRecyclerView.getPaddingBottom());
                }
            } else {
                addMoneyBtnView.setVisibility(View.GONE);
                if (sendMoneyBtnView.getVisibility() == View.GONE) {
                    transactionRecyclerView.setPadding(transactionRecyclerView.getPaddingLeft(),
                            transactionRecyclerView.getPaddingBottom(),
                            transactionRecyclerView.getPaddingRight(), transactionRecyclerView.getPaddingBottom());
                }
            }*//*

            if (userRollId == 3 || userRollId == 2) {
                ll_report.setVisibility(View.VISIBLE);
                //Services
                if (mActiveServiceData.getRetailerSericeOption() != null && mActiveServiceData.getRetailerSericeOption().size() > 0) {
                    rechargeRecyclerView.setVisibility(View.VISIBLE);
                    setOptionListData(mActiveServiceData.getRetailerSericeOption(), rechargeRecyclerView, 2, R.layout.adapter_dashboard_service_option_grid);
                }
                //Utility
                if (mActiveServiceData.getUtilityRetailerOption() != null && mActiveServiceData.getUtilityRetailerOption().size() > 0) {
                    utilityRecyclerView.setVisibility(View.VISIBLE);
                    setOptionListData(mActiveServiceData.getUtilityRetailerOption(), utilityRecyclerView, 3, R.layout.adapter_dashboard_option_grid);
                }


                //Money Transfer
                if (mActiveServiceData.getBankingOption() != null && mActiveServiceData.getBankingOption().size() > 0) {

                    *//*if (sendMoneyBtnView.getVisibility() == View.VISIBLE) {
                        mActiveServiceData.getBankingOption().addAll(new DataOpType().getSendMoneyOptions());
                    }*//*
                    transactionRecyclerView.setVisibility(View.VISIBLE);
                    if (mActiveServiceData.getBankingOption().size() == 4) {
                        transactionRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
                    }
                    setOptionListData(mActiveServiceData.getBankingOption(), transactionRecyclerView, 2, R.layout.adapter_dashboard_option_grid);
                }

            } else if (userRollId == 8) {
                ll_report.setVisibility(View.GONE);
                //Utility
                if (mActiveServiceData != null && mActiveServiceData.getUtilityFosOption() != null && mActiveServiceData.getUtilityFosOption().size() > 0) {
                    utilityRecyclerView.setVisibility(View.VISIBLE);
                    setOptionListData(mActiveServiceData.getUtilityFosOption(), utilityRecyclerView, 3, R.layout.adapter_dashboard_option_grid);
                }
            } else {
                ll_report.setVisibility(View.VISIBLE);
                //Utility
                if (mActiveServiceData.getUtilityOtherOption() != null && mActiveServiceData.getUtilityOtherOption().size() > 0) {
                    utilityRecyclerView.setVisibility(View.VISIBLE);
                    setOptionListData(mActiveServiceData.getUtilityOtherOption(), utilityRecyclerView, 3, R.layout.adapter_dashboard_option_grid);
                }

            }
            loader.dismiss();

        } else {
            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(HomeDashActivity.this)) {

                getActiveService();

            } else {
                loader.dismiss();
                ApiFintechUtilMethods.INSTANCE.NetworkError(HomeDashActivity.this);
            }
        }

    }
*/
    void setOptionListData(List<OpTypeDataIndustryWise> mListData, RecyclerView mRecyclerView) {
        HomeOptionIndustryWiseListAdapter mDashboardOptionListAdapter = new HomeOptionIndustryWiseListAdapter(mListData,
                HomeDashActivity.this, new ClickIndustryTypeOption() {
            @Override
            public void onClick(AssignedOpTypeIndustryWise item) {

                openNewScreen(item.getId(), item.getParentID(), item.getOpType());


            }


        });
        mRecyclerView.setAdapter(mDashboardOptionListAdapter);
    }

/*
    void setOptionListData(ArrayList<AssignedOpType> mListData, RecyclerView mRecyclerView, int type, int layout) {
        HomeOptionListAdapter mDashboardOptionListAdapter = new HomeOptionListAdapter(mListData, HomeDashActivity.this, new HomeOptionListAdapter.ClickView() {
            @Override
            public void onClick(AssignedOpType operator) {
                */
    /*int serviceId, int parentId, String name, ArrayList<AssignedOpType> subOpTypeList*//*

                if (operator.getSubOpTypeList() != null && operator.getSubOpTypeList().size() > 0) {
                    customAlertDialog.serviceListDialog(operator.getParentID(), operator.getService(),
                            operator.getSubOpTypeList(), type, new CustomAlertDialog.DialogServiceListCallBack() {
                                @Override
                                public void onIconClick(AssignedOpType opType) {
                                    openNewScreen(opType.getServiceID(), opType.getParentID(), opType.getName());
                                }

                                @Override
                                public void onUpgradePackage(boolean isFromAdditionalService, int serviceId) {

                                    startActivityForResult(new Intent(HomeDashActivity.this, UpgradePackageActivity.class)
                                            .putExtra("UID", mLoginDataResponse.getData().getUserID() + "")
                                            .putExtra("BENE_NAME", mLoginDataResponse.getData().getName() + " (" + mLoginDataResponse.getData().getRoleName() + ")")
                                            .putExtra("BENE_MOBILE", mLoginDataResponse.getData().getMobileNo() + "")
                                            .putExtra("FromAdditionalService", isFromAdditionalService)
                                            .putExtra("OPTypeId", serviceId)
                                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_UPGRADE_PACKAGE);
                                }
                            });
                } else {
                    openNewScreen(operator.getServiceID(), operator.getParentID(), operator.getName());
                }

            }

            @Override
            public void onPackageUpgradeClick(boolean isFromAdditionalService, int serviceId) {

                startActivityForResult(new Intent(HomeDashActivity.this, UpgradePackageActivity.class)
                        .putExtra("UID", mLoginDataResponse.getData().getUserID() + "")
                        .putExtra("BENE_NAME", mLoginDataResponse.getData().getName() + " (" + mLoginDataResponse.getData().getRoleName() + ")")
                        .putExtra("BENE_MOBILE", mLoginDataResponse.getData().getMobileNo() + "")
                        .putExtra("FromAdditionalService", isFromAdditionalService)
                        .putExtra("OPTypeId", serviceId)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_UPGRADE_PACKAGE);
            }
        }, layout, type);
        mRecyclerView.setAdapter(mDashboardOptionListAdapter);
    }
*/

    /*  void getActiveService() {

          try {
              ApiFintechUtilMethods.INSTANCE.GetActiveService(HomeDashActivity.this, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, object -> {
                  DashBoardServiceReportOptions mActiveServiceData = (DashBoardServiceReportOptions) object;
                  if (mActiveServiceData != null) {


                      setDashboardData(mActiveServiceData);
                  } else {
                      loader.dismiss();
                  }
              });
          } catch (Exception e) {
              loader.dismiss();
              e.printStackTrace();
          }


      }
  */
    void getActiveService() {
        try {
            ApiFintechUtilMethods.INSTANCE.GetActiveServiceIndustryWise(HomeDashActivity.this, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, object -> {
                OpTypeIndustryWiseResponse mOpTypeIndustryWiseResponse = (OpTypeIndustryWiseResponse) object;
                if (mOpTypeIndustryWiseResponse != null && mOpTypeIndustryWiseResponse.getData() != null && mOpTypeIndustryWiseResponse.getData().size() > 0) {
                    setDashboardData(mOpTypeIndustryWiseResponse);
                } else {
                    loader.dismiss();
                }
            });
        } catch (Exception e) {
            loader.dismiss();
            e.printStackTrace();
        }


    }

    void openNewScreen(int id, int parentId, String name) {
        if (id == 1) {

            if (userRollId != 2) {

                Intent clickIntent = new Intent(HomeDashActivity.this, RechargeBillPaymentActivity.class);
                clickIntent.putExtra("from", name);
                clickIntent.putExtra("fromId", id);
                clickIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(clickIntent);

            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }
        } else if (id == 2) {
            if (userRollId != 2) {
                Intent clickIntent = new Intent(HomeDashActivity.this, RechargeBillPaymentActivity.class);
                clickIntent.putExtra("from", name);
                clickIntent.putExtra("fromId", id);
                clickIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(clickIntent);

            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }
        } else if (id == 3) {
            if (userRollId != 2) {
                Intent i = new Intent(HomeDashActivity.this, RechargeBillPaymentActivity.class);
                i.putExtra("from", name);
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }
        } else if (parentId == 11) {

            if (userRollId != 2) {
                Intent i = new Intent(HomeDashActivity.this, RechargeProviderActivity.class);
                i.putExtra("from", name);
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }
        } else if (parentId == 19) {
            if (userRollId != 2) {
                if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(HomeDashActivity.this)) {
                    loader.show();
                    ApiFintechUtilMethods.INSTANCE.GenerateTokenGI(HomeDashActivity.this, id,
                            loader, mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum);
                } else {
                    ApiFintechUtilMethods.INSTANCE.NetworkError(HomeDashActivity.this);
                }
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }
        } else if (parentId == 47) {

            if (userRollId != 2) {

                accountOpenSetup(id, name);
                /*Intent i = new Intent(this, AccountOpenActivity.class);
                i.putExtra("SERVICE_ID", id);
                i.putExtra("name",name);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);*/
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }
        } else if (id == 13) {
            openMPOS();
        } else if (id == 14) {

            if (isDmtWithPipe) {
                if (mDMTOperatorLists == null || mDMTOperatorLists != null && mDMTOperatorLists.size() == 0) {

                    if (operatorListResponse != null && operatorListResponse.getDmtOperatorLists() != null &&
                            operatorListResponse.getDmtOperatorLists().size() > 0) {
                        mDMTOperatorLists = operatorListResponse.getDmtOperatorLists();
                        openPipeDmt();
                    } else {
                        operatorListResponse = ApiFintechUtilMethods.INSTANCE.getOperatorListResponse(mAppPreferences);
                        if (operatorListResponse != null && operatorListResponse.getDmtOperatorLists() != null &&
                                operatorListResponse.getDmtOperatorLists().size() > 0) {
                            mDMTOperatorLists = operatorListResponse.getDmtOperatorLists();
                            openPipeDmt();
                        } else {
                            ApiFintechUtilMethods.INSTANCE.OperatorList(this, deviceId, deviceSerialNum, mAppPreferences, object -> {
                                operatorListResponse = (OperatorListResponse) object;
                                if (operatorListResponse != null && operatorListResponse.getDmtOperatorLists() != null &&
                                        operatorListResponse.getDmtOperatorLists().size() > 0) {
                                    mDMTOperatorLists = operatorListResponse.getDmtOperatorLists();
                                    openPipeDmt();
                                }
                            });
                        }
                    }

                } else {

                    openPipeDmt();
                }
            } else {
                Intent i = new Intent(HomeDashActivity.this, DMRActivity.class);
                i.putExtra("from", "Dmr");
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        } else if (id == 18) {
            if (userRollId != 2) {
                if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(HomeDashActivity.this)) {
                    loader.show();
                    ApiFintechUtilMethods.INSTANCE.CallOnboarding(HomeDashActivity.this, 0, false, getSupportFragmentManager(),
                            id, "", "0", "", false, false, true,
                            null, null, null,
                            loader, mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum, null);
                } else {
                    ApiFintechUtilMethods.INSTANCE.NetworkError(HomeDashActivity.this);
                }
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }

        } else if (id == 20) {
            customAlertDialog.showMessagePES("PES", "You can use this service only on web portal");
        } else if (id == 786) {
            Intent i = new Intent(this, AddMoneyActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_ADD_MONEY);
        } else if (id == 22) {
            //aeps
            if (userRollId != 2) {
                if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(HomeDashActivity.this)) {
                    if (ApiFintechUtilMethods.INSTANCE.getLattitude != 0 && ApiFintechUtilMethods.INSTANCE.getLongitude != 0) {

                        CallOnboardingAEPS(id);
                    } else {
                        if (mGetLocation != null) {
                            mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                                ApiFintechUtilMethods.INSTANCE.getLattitude = lattitude;
                                ApiFintechUtilMethods.INSTANCE.getLongitude = longitude;
                                CallOnboardingAEPS(id);
                            });
                        } else {
                            mGetLocation = new GetLocation(this, loader);
                            mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                                ApiFintechUtilMethods.INSTANCE.getLattitude = lattitude;
                                ApiFintechUtilMethods.INSTANCE.getLongitude = longitude;
                                CallOnboardingAEPS(id);
                            });
                        }
                    }

                } else {
                    ApiFintechUtilMethods.INSTANCE.NetworkError(HomeDashActivity.this);
                }
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }
        } else if (id == 24) {
            //pan
            if (userRollId != 2) {
                if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(HomeDashActivity.this)) {
                    loader.show();

                    ApiFintechUtilMethods.INSTANCE.CallOnboarding(HomeDashActivity.this, 0, false, getSupportFragmentManager(), id, "", "0", "", true, false,
                            true, null, null, null, loader,
                            mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiCallBackTwoMethod() {
                                @Override
                                public void onSucess(Object object) {
                                    if (object != null && object instanceof OnboardingResponse) {
                                        OnboardingResponse mOnboardingResponse = (OnboardingResponse) object;
                                        if (mOnboardingResponse != null) {
                                            Intent i = new Intent(HomeDashActivity.this, PanApplicationActivity.class);
                                            i.putExtra("PANID", mOnboardingResponse.getPanid());
                                            i.putExtra("outletId", mLoginDataResponse.getData().getOutletID() + "");
                                            i.putExtra("userId", mLoginDataResponse.getData().getUserID() + "");
                                            i.putExtra("emailId", mOnboardingResponse.getEmailID());
                                            i.putExtra("mobileNo", mOnboardingResponse.getMobileNo());
                                            // i.putExtra("panList", new Gson().toJson(mOnboardingResponse).toString());
                                            startActivity(i);
                                        }
                                    }
                                }

                                @Override
                                public void onError(Object object) {

                                }
                            });


                } else {
                    ApiFintechUtilMethods.INSTANCE.NetworkError(HomeDashActivity.this);
                }
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }

        } else if (id == 28) {
            if (userRollId != 2) {
                if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(HomeDashActivity.this)) {
                    loader.show();
                    ApiFintechUtilMethods.INSTANCE.CallOnboarding(HomeDashActivity.this, 0, false, getSupportFragmentManager(),
                            id, "", "0", "", false, false, true,
                            null, null, null,
                            loader, mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum, null);
                } else {
                    ApiFintechUtilMethods.INSTANCE.NetworkError(HomeDashActivity.this);
                }
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }

        } else if (id == 29) {
            if (userRollId != 2) {
                if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(HomeDashActivity.this)) {
                    loader.show();
                    ApiFintechUtilMethods.INSTANCE.CallOnboarding(HomeDashActivity.this, 0, false, getSupportFragmentManager(),
                            id, "", "0", "", false, false, true,
                            null, null, null, loader,
                            mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum, null);
                } else {
                    ApiFintechUtilMethods.INSTANCE.NetworkError(HomeDashActivity.this);
                }
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }

        } else if (id == 32) {
            if (userRollId != 2) {
                Intent clickIntent = new Intent(HomeDashActivity.this, RechargeBillPaymentActivity.class);
                clickIntent.putExtra("from", name);
                clickIntent.putExtra("fromId", id);
                clickIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(clickIntent);

            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }
        } else if (id == 33) {
            if (userRollId != 2) {
                Intent i = new Intent(HomeDashActivity.this, RechargeBillPaymentActivity.class);
                i.putExtra("from", name);
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }
        } else if (id == 35) {
            if (userRollId != 2) {

                Intent i = new Intent(HomeDashActivity.this, DthSubscriptionActivity.class);
                i.putExtra("from", name);
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }
        } else if (id == 36) {
            if (userRollId != 2) {
                Intent i = new Intent(HomeDashActivity.this, DthSubscriptionActivity.class);
                i.putExtra("from", name);
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }
        } else if (id == 44) {
            //Mini ATM
            if (userRollId != 2) {
                if (ApiFintechUtilMethods.INSTANCE.getLattitude != 0 && ApiFintechUtilMethods.INSTANCE.getLongitude != 0) {

                    CallOnBoardingMiniAtm(id);
                } else {
                    if (mGetLocation != null) {
                        mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                            ApiFintechUtilMethods.INSTANCE.getLattitude = lattitude;
                            ApiFintechUtilMethods.INSTANCE.getLongitude = longitude;
                            CallOnBoardingMiniAtm(id);
                        });
                    } else {
                        mGetLocation = new GetLocation(this, loader);
                        mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                            ApiFintechUtilMethods.INSTANCE.getLattitude = lattitude;
                            ApiFintechUtilMethods.INSTANCE.getLongitude = longitude;
                            CallOnBoardingMiniAtm(id);
                        });
                    }
                }
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }
        } else if (id == 45) {
            if (userRollId != 2) {

                startActivity(new Intent(HomeDashActivity.this, ShoppingDashboardActivity.class)
                        .putExtra("FROM_LOGIN", isFromLogin)
                        .putExtra("FROM_SCREEN", 1)
                        .putExtra("FROM_PRE_SCREEN", fromScreen)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
            }
        } else if (id == 62) {
            //customAlertDialog.UPIListDialog("Select Payment Method", balanceCheckResponse);
            startActivity(new Intent(HomeDashActivity.this, UPIListActivity.class)
                    .putExtra("BalanceData", balanceCheckResponse)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

        } else if (id == 63) {
            apiMNPStatus(id);
        } else if (id == 75) {

            Intent i = new Intent(HomeDashActivity.this, RechargeProviderActivity.class);
            i.putExtra("from", name + "");
            i.putExtra("fromId", id);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 105) {
            Intent i = new Intent(HomeDashActivity.this, WalletToWalletTransferActivity.class);
            i.putExtra("from", name + "");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (/*id == 100 ||*/ id == 1010) {

            Intent i = new Intent(HomeDashActivity.this, PaymentRequest.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 1009) {

            Intent i = new Intent(HomeDashActivity.this, FundOrderPendingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 1011) {
            Intent i = new Intent(HomeDashActivity.this, CreateUserActivity.class);

            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 1012) {

            if (userRollId == 8) {
                startActivity(new Intent(this, FosUserListActivity.class)
                        .putExtra("Title", "FOS User List")
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            } else {
                Intent i = new Intent(HomeDashActivity.this, AppUserListActivity.class);
                i.putExtra("Title", "User List");
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        } else if (id == 1013) {
            Intent n = new Intent(HomeDashActivity.this, CommissionScreenActivity.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        } else if (id == 1016) {
            //Call back request
            callBack();
        } else if (id == 555) {
            //Call back request
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(URL_HERE));
            startActivity(intent);
        } else if (id == 1017) {
            Intent n = new Intent(HomeDashActivity.this, QrBankActivity.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        } else if (id == 1019) {
            Intent i = new Intent(HomeDashActivity.this, AddMoneyActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_ADD_MONEY);

        } else if (id == 1021) {
            startActivityForResult(new Intent(HomeDashActivity.this, UpgradePackageActivity.class)
                    .putExtra("UID", mLoginDataResponse.getData().getUserID() + "")
                    .putExtra("BENE_NAME", mLoginDataResponse.getData().getName() + " (" + mLoginDataResponse.getData().getRoleName() + ")")
                    .putExtra("BENE_MOBILE", mLoginDataResponse.getData().getMobileNo() + "")
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_UPGRADE_PACKAGE);

        } else if (id == 1023) {


            if (userRollId == 3 || userRollId == 2) {
                startActivity(new Intent(this, MoveToWalletActivity.class)
                        .putParcelableArrayListExtra("items", mBalanceDataList)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            } else if (userRollId == 8) {
                startActivity(new Intent(this, FosUserListActivity.class)
                        .putExtra("Title", "Send Money To User")
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            } else {
                Intent i = new Intent(HomeDashActivity.this, AppUserListActivity.class);
                i.putExtra("Title", "Send Money To User");
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }

        } else if (id == 1024) {
            Intent i = new Intent(HomeDashActivity.this, CreateUserActivity.class);
            i.putExtra("IsFOS", true);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 1026 || id == 1031) {
            // UtilMethods.INSTANCE.GetArealist(getActivity(), loader, loginPrefResponse, null);
            Intent intent = new Intent(this, AccountStatementReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == 1027) {

            //customAlertDialog.channelFosListDialog();
            Intent intent = new Intent(this, FosReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == 1030) {

            if (mLoginDataResponse.isAreaMaster()) {
                if (mAppGetAMResponse == null || mAppGetAMResponse.getAreaMaster() == null) {
                    mAppGetAMResponse = new Gson().fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.areaListPref), AppGetAMResponse.class);
                    if (mAppGetAMResponse == null || mAppGetAMResponse.getAreaMaster() == null) {
                        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
                            loader.show();
                            ApiFintechUtilMethods.INSTANCE.GetArealist(this, loader, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences,
                                    new ApiFintechUtilMethods.ApiResponseCallBack() {
                                        @Override
                                        public void onSucess(Object object) {
                                            mAppGetAMResponse = (AppGetAMResponse) object;
                                            if (mAppGetAMResponse != null && mAppGetAMResponse.getAreaMaster() != null && mAppGetAMResponse.getAreaMaster().size() > 0) {
                                                customAlertDialog.channelAreaListDialog(mAppGetAMResponse.getAreaMaster());
                                            }
                                        }

                                        @Override
                                        public void onError(int error) {

                                        }
                                    });


                        } else {
                            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
                        }
                    } else {
                        customAlertDialog.channelAreaListDialog(mAppGetAMResponse.getAreaMaster());
                    }
                } else {
                    customAlertDialog.channelAreaListDialog(mAppGetAMResponse.getAreaMaster());
                }

            } else {
                Intent intent = new Intent(this, ChannelReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }

        } else if (id == 1029) {

            // UtilMethods.INSTANCE.GetArealist(getActivity(), loader, loginPrefResponse, null);
            Intent intent = new Intent(this, FosAreaReportActivity.class);
            intent.putExtra("ISFromFOS", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            startActivity(intent);
        } else if (id == 1032) {

            customAlertDialog.channelFosListDialog(loader, mLoginDataResponse, deviceId, deviceSerialNum);
        }
    }

    // Function to open a PDF using an external PDF viewer app
    private void openPdf(Uri pdfUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(pdfUri, "application/pdf");

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Handle the case where no PDF viewer app is available
            e.printStackTrace();
            // You can show a message to the user to install a PDF viewer app
        }
    }

    // Call the openPdf function with the PDF file's URI


    private void openPipeDmt() {
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

    void apiMNPStatus(int id) {
        if (userRollId != 2) {
            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
                ApiFintechUtilMethods.INSTANCE.GetMNPStatus(this, loader,
                        mLoginDataResponse, deviceId, deviceSerialNum, object -> {
                            if (object != null) {

                            }
                        });
            } else {
                ApiFintechUtilMethods.INSTANCE.NetworkError(this);
            }
        } else {
            ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getResources().getString(R.string.NotAuthorized));
        }

    }

    private void CallOnBoardingMiniAtm(int id) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(HomeDashActivity.this)) {
            loader.show();

            ApiFintechUtilMethods.INSTANCE.CallOnboarding(HomeDashActivity.this, 0, false, getSupportFragmentManager(),
                    id, "", "0", "", false, false,
                    true, null, null, null, loader,
                    mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiCallBackTwoMethod() {
                        @Override
                        public void onSucess(Object object) {
                            if (object != null && object instanceof OnboardingResponse) {
                                OnboardingResponse mOnboardingResponse = (OnboardingResponse) object;
                                if (mOnboardingResponse != null) {

                                    if (mOnboardingResponse.getSdkType() == 1 && mOnboardingResponse.getSdkDetail() != null) {
                                        Intent i = new Intent(HomeDashActivity.this, MicroAtmActivity.class);
                                        i.putExtra("SDKType", mOnboardingResponse.getSdkType() + "");
                                        i.putExtra("OID", mOnboardingResponse.getoId() + "");
                                        i.putExtra("SDKDetails", mOnboardingResponse.getSdkDetail());
                                        startActivity(i);
                                    } else if ((mOnboardingResponse.getSdkType() == 2 || mOnboardingResponse.getSdkType() == 4) &&
                                            mOnboardingResponse.getBcResponse() != null) {
                                        if (mOnboardingResponse.getBcResponse().size() > 0) {
                                            if (mOnboardingResponse.getBcResponse().get(0).getErrorCode() == 0) {
                                                showMicroATMBCDetail(mOnboardingResponse.getBcResponse().get(0));
                                            } else {
                                                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "Something went wrong!!");
                                            }
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "BcDetails Data not found!!");
                                        }

                                    } else if ((mOnboardingResponse.getSdkType() == 2 || mOnboardingResponse.getSdkType() == 4)
                                            && mOnboardingResponse.getSdkDetail() != null && mOnboardingResponse.getSdkDetail().getBcResponse() != null) {
                                        if (mOnboardingResponse.getSdkDetail().getBcResponse().size() > 0) {
                                            if (mOnboardingResponse.getSdkDetail().getBcResponse().get(0).getErrorCode() == 0) {
                                                showMicroATMBCDetail(mOnboardingResponse.getSdkDetail().getBcResponse().get(0));
                                            } else {
                                                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "Something went wrong!!");
                                            }
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "BcDetails Data not found!!");
                                        }

                                    } else if (mOnboardingResponse.getSdkType() == 3 && mOnboardingResponse.getSdkDetail() != null) {
                                        int partnerid = 0;
                                        int outletId = 0;
                                        try {
                                            partnerid = Integer.parseInt(mOnboardingResponse.getSdkDetail().getApiPartnerID());
                                        } catch (NumberFormatException nfe) {
                                            ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "Invalid partner id its should be integer value");
                                        }

                                        try {
                                            outletId = Integer.parseInt(mOnboardingResponse.getSdkDetail().getApiOutletID());
                                        } catch (NumberFormatException nfe) {
                                            ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "Invalid outlat id its should be integer value");
                                        }

                                        startActivityForResult(new Intent(HomeDashActivity.this, EMoneyLoginActivity.class)
                                                .putExtra(KeyConstant.USER_ID, partnerid)
                                                .putExtra(KeyConstant.OUTLET_ID, outletId)
                                                .putExtra(KeyConstant.PARTNER_ID, 0)
                                                .putExtra(KeyConstant.PIN, mOnboardingResponse.getSdkDetail().getApiOutletPassword())
                                                .putExtra(KeyConstant.SERVICE_TYPE, KeyConstant.MICRO_ATM), INTENT_MINI_ATM_RP);
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "Merchent details is not found or SDK not integrated");
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Object object) {

                        }
                    });


        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(HomeDashActivity.this);
        }
    }

    private void CallOnboardingAEPS(int id) {
        loader.show();
        ApiFintechUtilMethods.INSTANCE.CallOnboarding(HomeDashActivity.this, 0, false, getSupportFragmentManager(),
                id, "", "0", "", false, false,
                true, null, null, null, loader, mLoginDataResponse, mAppPreferences,
                deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiCallBackTwoMethod() {
                    @Override
                    public void onSucess(Object object) {
                        if (object != null && object instanceof OnboardingResponse) {
                            OnboardingResponse mOnboardingResponse = (OnboardingResponse) object;
                            if (mOnboardingResponse != null) {
                                if (mOnboardingResponse.getSdkType() == 1) {
                                    startActivity(new Intent(HomeDashActivity.this, AEPSDashboardActivity.class)
                                            .putExtra("SDKDetail", mOnboardingResponse.getSdkDetail())
                                            .putExtra("SDKType", mOnboardingResponse.getSdkType())
                                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                } else if ((mOnboardingResponse.getSdkType() == 2 || mOnboardingResponse.getSdkType() == 4)
                                        && mOnboardingResponse.getBcResponse() != null) {
                                    if (mOnboardingResponse.getBcResponse().size() > 0) {
                                        if (mOnboardingResponse.getBcResponse().get(0).getErrorCode() == 0) {

                                            showBCDetail(mOnboardingResponse.getBcResponse().get(0));
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "Something went wrong!!");
                                        }
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "BcDetails Data not found!!");
                                    }
                                } else if ((mOnboardingResponse.getSdkType() == 2 || mOnboardingResponse.getSdkType() == 4)
                                        && mOnboardingResponse.getSdkDetail() != null && mOnboardingResponse.getSdkDetail().getBcResponse() != null) {
                                    if (mOnboardingResponse.getSdkDetail().getBcResponse().size() > 0) {
                                        if (mOnboardingResponse.getSdkDetail().getBcResponse().get(0).getErrorCode() == 0) {

                                            showBCDetail(mOnboardingResponse.getSdkDetail().getBcResponse().get(0));
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "Something went wrong!!");
                                        }
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "BcDetails Data not found!!");
                                    }
                                } else if (mOnboardingResponse.getSdkType() == 3 && mOnboardingResponse.getSdkDetail() != null) {
                                    SDKDetail sdkDetail = mOnboardingResponse.getSdkDetail();

                                    int partnerid = 0;
                                    int outletId = 0;
                                    try {
                                        partnerid = Integer.parseInt(sdkDetail.getApiPartnerID());
                                    } catch (NumberFormatException nfe) {
                                        ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "Invalid partner id its should be integer value");
                                    }

                                    try {
                                        outletId = Integer.parseInt(sdkDetail.getApiOutletID());
                                    } catch (NumberFormatException nfe) {
                                        ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "Invalid outlat id its should be integer value");
                                    }

                                    startActivityForResult(new Intent(HomeDashActivity.this, EMoneyLoginActivity.class)
                                            .putExtra(KeyConstant.USER_ID, partnerid)
                                            .putExtra(KeyConstant.OUTLET_ID, outletId)
                                            .putExtra(KeyConstant.PARTNER_ID, 0)
                                            .putExtra(KeyConstant.PIN, sdkDetail.getApiOutletPassword())
                                            .putExtra(KeyConstant.SERVICE_TYPE, KeyConstant.AEPS), INTENT_AEPS_RP);

                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "Merchent details is not found or SDK not integrated");
                                }

                            }
                        }
                    }

                    @Override
                    public void onError(Object object) {

                    }
                });

    }

    void callBack() {
        if (customAlertDialog != null) {
            customAlertDialog.sendReportDialog(3, mLoginDataResponse.getData().getMobileNo(), (mobile, emailId) ->
                    ApiFintechUtilMethods.INSTANCE.GetCallMeUserReq(HomeDashActivity.this, mobile, loader, deviceId, deviceSerialNum, mLoginDataResponse));
        }
    }

    private void showBCDetail(BcResponse bcDetail) {
        try {
            String mobileno = bcDetail.getMobileno();
            String secretKey = bcDetail.getSecretKey();
            String saltKey = bcDetail.getSaltKey();
            String bcid = bcDetail.getBcid();
            String userId = bcDetail.getUserId();
            String cpid = bcDetail.getCpid() != null && bcDetail.getCpid().length() > 0 ? bcDetail.getCpid() : "";
            String emailId = bcDetail.getEmailId();
            String aepsOutletId = bcDetail.getAepsOutletId();
            String password = bcDetail.getPassword();
            String merchantId = bcDetail.getMerchantId();


            if (bcid != null && bcid.length() > 0) {

                Intent intent = new Intent(HomeDashActivity.this, DashboardActivity.class);
                intent.putExtra("saltKey", saltKey);
                intent.putExtra("secretKey", secretKey);
                intent.putExtra("BcId", bcid);
                intent.putExtra("UserId", userId);
                intent.putExtra("bcEmailId", emailId);
                intent.putExtra("Phone1", mobileno);
                intent.putExtra("cpid", cpid);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, INTENT_CODE_APES);
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getString(R.string.AepsNotApproved));
            }

        } catch (Exception ex) {
            ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, getString(R.string.some_thing_error) + "due to " + ex.getMessage());
        }
    }

    private void openMPOS() {
        Intent intent = new Intent(HomeDashActivity.this, PaynearActivationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }

    private void showMicroATMBCDetail(BcResponse bcDetail) {
        try {
            String mobileno = bcDetail.getMobileno();
            String secretKey = bcDetail.getSecretKey();
            String saltKey = bcDetail.getSaltKey();
            String bcid = bcDetail.getBcid();
            String userId = bcDetail.getUserId();
            String cpid = bcDetail.getCpid() != null && bcDetail.getCpid().length() > 0 ? bcDetail.getCpid() : "";
            String emailId = bcDetail.getEmailId();
            String aepsOutletId = bcDetail.getAepsOutletId();
            String password = bcDetail.getPassword();
            String merchantId = bcDetail.getMerchantId();

            if (bcid != null && bcid.length() > 0) {
                PackageManager packageManager = getPackageManager();
                if (Utility.INSTANCE.isPackageInstalled("org.egram.microatm", packageManager)) {
                    Intent intent = new Intent();
                    intent.setComponent(new
                            ComponentName("org.egram.microatm", "org.egram.microatm.BluetoothMacSearchActivity"));
                    intent.putExtra("saltkey", saltKey);
                    intent.putExtra("secretkey", secretKey);
                    intent.putExtra("bcid", bcid);
                    intent.putExtra("userid", userId);
                    intent.putExtra("bcemailid", emailId);
                    intent.putExtra("phone1", mobileno);
                    intent.putExtra("clientrefid", cpid);
                    intent.putExtra("vendorid", "");
                    intent.putExtra("udf1", "");
                    intent.putExtra("udf2", "");
                    intent.putExtra("udf3", "");
                    intent.putExtra("udf4", "");
                    startActivityForResult(intent, INTENT_MINI_ATM);

                } else {
                    customAlertDialog.WarningWithCallBack("MicroATM Service not installed. Click OK to download.", "Get Service", "Download", false, new CustomAlertDialog.DialogCallBack() {
                        @Override
                        public void onPositiveClick() {
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=org.egram.microatm")));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });
                }

            } else {
                ApiFintechUtilMethods.INSTANCE.Error(this, getResources().getString(R.string.microATMApproved));
            }


        } catch (Exception ex) {
            ApiFintechUtilMethods.INSTANCE.Error(this, getString(R.string.some_thing_error) + "due to " + ex.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_PASSWORD_EXPIRE && resultCode == INTENT_PASSWORD_EXPIRE) {
            ApiFintechUtilMethods.INSTANCE.BalancecheckNew(this, loader, customAlertDialog, INTENT_PASSWORD_EXPIRE, deviceId, deviceSerialNum,
                    mAppPreferences, mLoginDataResponse, tts, mEKYCStepsDialog, object -> {
                        balanceCheckResponse = (BalanceResponse) object;
                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                && balanceCheckResponse.getBalanceData().size() > 0) {
                            showBalanceData();
                        }
                    });
        } else if (requestCode == INTENT_AEPS_RP) {
            if (resultCode == RESULT_OK && data != null) {
                int error_code = data.getIntExtra(KeyConstant.ERROR_CODE, 0);
                String error_msg = data.getStringExtra(KeyConstant.ERROR_MSG);
                String aadharNum = data.getStringExtra(KeyConstant.AADHAR_NUM);
                String bankName = data.getStringExtra(KeyConstant.BANK_NAME);
                String deviceName = data.getStringExtra(KeyConstant.DEVICE_NAME);
                int type = data.getIntExtra(KeyConstant.TYPE, 0);
                double balAmount = data.getDoubleExtra(KeyConstant.BALANCE_AMOUNT, 0);
                ArrayList<MiniStatements> miniStatements = (ArrayList<MiniStatements>) data.getSerializableExtra(KeyConstant.MINI_STATEMENT_LIST);
                boolean status = data.getBooleanExtra(KeyConstant.TRANS_STATUS, false);
                if (status) {
                    if (type == KeyConstant.MINI_STATEMENT && miniStatements != null && miniStatements.size() > 0) {
                        Intent intent = new Intent(HomeDashActivity.this, AEPSMiniStatementRPActivity.class);
                        intent.putExtra("OP_NAME", bankName + "");
                        intent.putExtra("BALANCE", Utility.INSTANCE.formatedAmountWithRupees(balAmount + ""));
                        intent.putExtra("MINI_STATEMENT", miniStatements);
                        intent.putExtra("NUMBER", aadharNum + "");
                        intent.putExtra("Device_NAME", deviceName + "");
                        startActivity(intent);

                    } else if (type != 0) {
                        Intent intent = new Intent(HomeDashActivity.this, AEPSStatusRPActivity.class);
                        intent.putExtras(data);
                        startActivity(intent);
                    } else {
                        ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "StatusCode - " + error_code + "\nMessage- " + error_msg);

                    }
                } else {
                    ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "StatusCode - " + error_code + "\nMessage- " + error_msg);
                }


            } else {
                if (data != null) {
                    String status_code = data.getStringExtra(KeyConstant.STATUS_CODE);
                    int error_code = data.getIntExtra(KeyConstant.ERROR_CODE, 0);
                    String error_msg = data.getStringExtra(KeyConstant.ERROR_MSG);
                    if (error_msg != null) {
                        ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "StatusCode - " + error_code + "\nMessage- " + error_msg);
                    } else {
                        ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "Canceled");
                    }
                } else {
                    ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "Canceled");
                }
            }
        } else if (requestCode == INTENT_CODE_APES) {
            if (resultCode == RESULT_OK) {
                ApiFintechUtilMethods.INSTANCE.isReadyToUpdateBalance = true;
                //String StatusCode= data.getStringExtra("StatusCode"); //to get status code
                //String Message= data.getStringExtra("Message"); //to get response message
                String TransactionType = data.getStringExtra("TransactionType"); //to get transaction name
                String response = data.getStringExtra("Response");

                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.optJSONObject(i);
                        startActivity(new Intent(this, AEPSRecriptActivity.class)
                                .putExtra("TransactionType", TransactionType)
                                .putExtra("ResponseData", jsonobject.toString()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ApiFintechUtilMethods.INSTANCE.Error(this, e.getMessage() + "");
                }

            } else {
                String statuscode = data.getStringExtra("StatusCode"); //to get status code
                String message = data.getStringExtra("Message"); //to get response message
                ApiFintechUtilMethods.INSTANCE.Error(this, "Error Code - " + statuscode + "<br/>" + message);
                // Toast.makeText(HomeDashActivity.this, "StatusCode - " + data.getStringExtra("StatusCode") + " Message- " + data.getStringExtra("Message") + "", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == INTENT_UPGRADE_PACKAGE && resultCode == RESULT_OK) {
            getActiveService();
        } else if (requestCode == INTENT_NOTIFICATIONS && resultCode == RESULT_OK) {
            notificationCount = notificationCount - data.getIntExtra("Notification_Count", 0);
            setNotificationCount();
        } else if (requestCode == MY_REQUEST_UPDATE_APP_CODE) {
            if (resultCode == RESULT_CANCELED) {
                isUpdateDialogShowing = false;
                UpdateApp();
            } else if (resultCode == RESULT_OK) {
                isUpdateDialogShowing = false;
                UpdateApp();
            } else {
                isUpdateDialogShowing = false;
                ApiFintechUtilMethods.INSTANCE.versionOldDialog(this);
            }

        } else if (requestCode == INTENT_MINI_ATM_RP) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    boolean status = data.getBooleanExtra(KeyConstant.TRANS_STATUS, false);
                    String message = data.getStringExtra(KeyConstant.MESSAGE);
                    if (!status && message.equalsIgnoreCase("Canceled")) {
                        ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "Canceled");
                    } else {
                        startActivity(new Intent(HomeDashActivity.this, MicroATMStatusRPActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                .putExtras(data));
                    }
                }


            } else {
                if (data != null) {
                    String status_code = data.getStringExtra(KeyConstant.STATUS_CODE);
                    int error_code = data.getIntExtra(KeyConstant.ERROR_CODE, 0);
                    String error_msg = data.getStringExtra(KeyConstant.ERROR_MSG);
                    if (error_msg != null) {
                        ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "StatusCode - " + error_code + "\nMessage- " + error_msg);
                    } else {
                        ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "Canceled");
                    }
                } else {
                    ApiFintechUtilMethods.INSTANCE.Error(HomeDashActivity.this, "Canceled");
                }
            }
        } else if (requestCode == INTENT_MINI_ATM) {
            if (resultCode == RESULT_OK) {
                startActivity(new Intent(this, MiniAtmRecriptActivity.class)
                        .putExtras(data));

            } else {
                String statuscode = data.getStringExtra("statuscode ");//to get status code
                String message = data.getStringExtra("message");//to get response message
                ApiFintechUtilMethods.INSTANCE.Error(this, "StatusCode - " + statuscode + " Message- " + message);
            }
        } else if (requestCode == INTENT_PROFILE && resultCode == RESULT_OK) {
            if (!mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.voiceEnablePref)) {
                tts = new TextToSpeech(this, null);
            } else {
                tts = null;
            }

            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseProfilePicUrl + mLoginDataResponse.getData().getUserID() + ".png")
                    .apply(requestOptions)
                    .into(userImage);
            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseProfilePicUrl + mLoginDataResponse.getData().getUserID() + ".png")
                    .apply(requestOptions)
                    .into(userIcon);
        } else if (requestCode == INTENT_ADD_MONEY && resultCode == RESULT_OK) {
            ApiFintechUtilMethods.INSTANCE.Balancecheck(this, loader, mLoginDataResponse, deviceId, deviceSerialNum,
                    mAppPreferences, mEKYCStepsDialog, object -> {
                        balanceCheckResponse = (BalanceResponse) object;
                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                && balanceCheckResponse.getBalanceData().size() > 0) {
                            showBalanceData();
                        }
                    });
        } else {
            if (mGetLocation != null) {
                mGetLocation.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    protected void onPause() {
        if (mGetLocation != null) {
            mGetLocation.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (ApiFintechUtilMethods.INSTANCE.isReadyToUpdateBalance) {
            ApiFintechUtilMethods.INSTANCE.BalancecheckNew(this, loader, customAlertDialog, INTENT_PASSWORD_EXPIRE, deviceId,
                    deviceSerialNum, mAppPreferences, mLoginDataResponse, tts, mEKYCStepsDialog, object -> {
                        balanceCheckResponse = (BalanceResponse) object;
                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                && balanceCheckResponse.getBalanceData().size() > 0) {
                            showBalanceData();
                        }
                    });
            ApiFintechUtilMethods.INSTANCE.isReadyToUpdateBalance = false;
        }

        if (ApiFintechUtilMethods.INSTANCE.isUserDetailUpdate) {
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            userNameTv.setText(mLoginDataResponse.getData().getName() /*+ " - " + mLoginDataResponse.getData().getRoleName()*/);
            emailTv.setText(mLoginDataResponse.getData().getEmailID() + "");
            ApiFintechUtilMethods.INSTANCE.isUserDetailUpdate = false;
        }

        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (fromScreen == 0) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mNewNotificationReciver);
        }
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        if (handler != null && mRunnable != null) {
            handler.removeCallbacks(mRunnable);
        }
        super.onDestroy();
    }

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


    public void UpdateApp() {

        if (!isUpdateDialogShowing) {

            appUpdateManager
                    .getAppUpdateInfo()
                    .addOnSuccessListener(appUpdateInfo -> {
                        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE ||
                                appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                            try {
                                isUpdateDialogShowing = true;
                                if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(HomeDashActivity.this)) {
                                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, IMMEDIATE, this, MY_REQUEST_UPDATE_APP_CODE);
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.versionOldDialog(this);
                                }
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                                isUpdateDialogShowing = false;
                                ApiFintechUtilMethods.INSTANCE.versionOldDialog(this);
                            }
                        }
                    });
        }

    }

    /*   @Override
       public void onBackPressed() {
           Intent intent = new Intent(this,NetworkingDashboardActivity.class);
           startActivity(intent);
           finish();

       }
   */
    @Override
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
    }


    public void contactUsData(View v) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            try {
                loader.show();
                ApiFintechUtilMethods.INSTANCE.GetCompanyProfile(this, loader, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, object -> {
                    AppUserListResponse companyData = (AppUserListResponse) object;
                    showContactUs(companyData, v);
                });

            } catch (Exception e) {
                e.printStackTrace();
                AppUserListResponse companyData = ApiFintechUtilMethods.INSTANCE.getCompanyProfile(mAppPreferences);
                showContactUs(companyData, v);
            }

        } else {
            AppUserListResponse companyData = ApiFintechUtilMethods.INSTANCE.getCompanyProfile(mAppPreferences);
            showContactUs(companyData, v);
        }


    }


    void showContactUs(AppUserListResponse mContactData, View v) {

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_contact_us, null);
        RecyclerView customerCareRecyclerView = view.findViewById(R.id.customerCareRecyclerView);
        TextView errorMsg = view.findViewById(R.id.errorMsg);
        customerCareRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (mContactData != null && mContactData.getCompanyProfile() != null) {
            ArrayList<InfoContactDataItem> mSupportDataCustomerContacts = new ArrayList<>();

            if (mContactData.getCompanyProfile().getCustomerCareMobileNos() != null && !mContactData.getCompanyProfile().getCustomerCareMobileNos().isEmpty()) {
                mSupportDataCustomerContacts.addAll(getListData(1, mContactData.getCompanyProfile().getCustomerCareMobileNos()));
            }

            if (mContactData.getCompanyProfile().getCustomerPhoneNos() != null && !mContactData.getCompanyProfile().getCustomerPhoneNos().isEmpty()) {
                mSupportDataCustomerContacts.addAll(getListData(2, mContactData.getCompanyProfile().getCustomerPhoneNos()));
            }

            if (mContactData.getCompanyProfile().getCustomerWhatsAppNos() != null && !mContactData.getCompanyProfile().getCustomerWhatsAppNos().isEmpty()) {

                mSupportDataCustomerContacts.addAll(getListData(3, mContactData.getCompanyProfile().getCustomerWhatsAppNos()));
            }

            if (mContactData.getCompanyProfile().getCustomerCareEmailIds() != null && !mContactData.getCompanyProfile().getCustomerCareEmailIds().isEmpty()) {

                mSupportDataCustomerContacts.addAll(getListData(4, mContactData.getCompanyProfile().getCustomerCareEmailIds()));
            }


            if (mSupportDataCustomerContacts.size() > 0) {
                errorMsg.setVisibility(View.GONE);
                customerCareRecyclerView.setVisibility(View.VISIBLE);
                InfoContactDataListAdapter mInfoContactDataListAdapter = new InfoContactDataListAdapter(this, mSupportDataCustomerContacts);
                customerCareRecyclerView.setAdapter(mInfoContactDataListAdapter);
            } else {
                customerCareRecyclerView.setVisibility(View.GONE);
                errorMsg.setVisibility(View.VISIBLE);
            }

        } else {

            customerCareRecyclerView.setVisibility(View.GONE);
            errorMsg.setVisibility(View.VISIBLE);

        }

        BubbleDialog dialog = new BubbleDialog(this);
        dialog.addContentView(view);
        dialog.setClickedView(v);
        dialog.calBar(false);
        dialog.show();
        dialog.setBubbleLayoutBackgroundColor(Color.WHITE);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private ArrayList<InfoContactDataItem> getListData(int type, String editableData) {
        int icon = R.drawable.ic_call_black_24dp;
        int iconMain = R.drawable.ic_contact_mobile;
        String headerText = "Call Us";
        if (type == 1) {
            icon = R.drawable.ic_call_black_24dp;
            iconMain = R.drawable.ic_contact_mobile;
            headerText = "Call Us";
        }
        if (type == 2) {
            icon = R.drawable.ic_call_black_24dp;
            iconMain = R.drawable.ic_telephone;
            headerText = "Call Us";
        }
        if (type == 3) {
            icon = R.drawable.ic_whatsapp_outline;
            iconMain = R.drawable.ic_whatsapp;
            headerText = "Whatsapp";
        }
        if (type == 4) {
            icon = R.drawable.ic_email_outline;
            iconMain = R.drawable.ic_email;
            headerText = "Email";
        }
        ArrayList<InfoContactDataItem> mSupportDataItems = new ArrayList<>();
        if (editableData.contains(",")) {
            String[] emailArray = editableData.split(",");
            if (emailArray.length > 0) {
                for (String value : emailArray) {
                    mSupportDataItems.add(new InfoContactDataItem(icon, headerText, type, value.trim(), iconMain));
                }
            }
        } else {
            mSupportDataItems.add(new InfoContactDataItem(icon, headerText, type, editableData.trim(), iconMain));
        }
        return mSupportDataItems;
    }

    void accountOpenSetup(int serviceId, String name) {
        if (balanceCheckResponse.isDrawOpImage()) {

            if (ApiFintechUtilMethods.INSTANCE.mAccountOpenOPTypes == null ||
                    ApiFintechUtilMethods.INSTANCE.mAccountOpenOPTypes.size() == 0 ||
                    !ApiFintechUtilMethods.INSTANCE.mAccountOpenOPTypes.containsKey(serviceId)) {

                if (operatorListResponse == null || operatorListResponse.getOperators() == null || operatorListResponse.getOperators().size() == 0) {
                    operatorListResponse = ApiFintechUtilMethods.INSTANCE.getOperatorListResponse(mAppPreferences);
                    if (operatorListResponse != null) {
                        mDMTOperatorLists = operatorListResponse.getDmtOperatorLists();
                    }
                }

                if (operatorListResponse != null && operatorListResponse.getOperators() != null &&
                        operatorListResponse.getOperators().size() > 0) {
                    setOpenAccountMap(serviceId);
                } else {
                    ApiFintechUtilMethods.INSTANCE.OperatorList(this, deviceId, deviceSerialNum, mAppPreferences, object -> {
                        operatorListResponse = (OperatorListResponse) object;
                        if (operatorListResponse != null) {
                            mDMTOperatorLists = operatorListResponse.getDmtOperatorLists();
                            if (operatorListResponse.getOperators() != null &&
                                    operatorListResponse.getOperators().size() > 0) {
                                setOpenAccountMap(serviceId);
                            }
                        }

                    });
                }
            }

            if (ApiFintechUtilMethods.INSTANCE.mAccountOpenOPTypes.containsKey(serviceId) &&
                    ApiFintechUtilMethods.INSTANCE.mAccountOpenOPTypes.get(serviceId) != null &&
                    ApiFintechUtilMethods.INSTANCE.mAccountOpenOPTypes.get(serviceId).size() > 0) {

                customAlertDialog.accountOpenOPListDialog(name, serviceId, ApiFintechUtilMethods.INSTANCE.mAccountOpenOPTypes.get(serviceId),
                        (mOperatorList, serviceId1) -> {

                            try {
                                if (mOperatorList.getRedirectURL() != null && !mOperatorList.getRedirectURL().isEmpty()) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mOperatorList.getRedirectURL() + "")));
                                } else {
                                    Toast.makeText(HomeDashActivity.this, "Link is not available to open detail", Toast.LENGTH_SHORT).show();
                                }
                            } catch (ActivityNotFoundException anfe) {
                                Toast.makeText(HomeDashActivity.this, "App is not available to open details", Toast.LENGTH_SHORT).show();
                            }

                        });
            } else {
                Toast.makeText(HomeDashActivity.this, "List is not available to open detail", Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent i = new Intent(HomeDashActivity.this, AccountOpenActivity.class);
            i.putExtra("SERVICE_ID", serviceId);
            i.putExtra("name", name);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }

    }

    void setOpenAccountMap(int serviceId) {
        ArrayList<OperatorList> lists = new ArrayList<>();
        for (OperatorList op : operatorListResponse.getOperators()) {
            if (op.isActive() && op.getOpType() == serviceId) {
                lists.add(op);
            }
        }
        ApiFintechUtilMethods.INSTANCE.mAccountOpenOPTypes.put(serviceId, lists);
    }


    private BroadcastReceiver mNewNotificationReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ApiFintechUtilMethods.INSTANCE.GetNotifications(HomeDashActivity.this, mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum, object -> {
                notificationCount = ((int) object);
                setNotificationCount();
            });
        }
    };
}
