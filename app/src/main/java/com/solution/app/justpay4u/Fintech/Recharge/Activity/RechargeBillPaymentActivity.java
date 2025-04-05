package com.solution.app.justpay4u.Fintech.Recharge.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.solution.app.justpay4u.Api.Fintech.Object.CommissionDisplay;
import com.solution.app.justpay4u.Api.Fintech.Object.DTHInfoData;
import com.solution.app.justpay4u.Api.Fintech.Object.DTHInfoRecords;
import com.solution.app.justpay4u.Api.Fintech.Object.IncentiveDetails;
import com.solution.app.justpay4u.Api.Fintech.Object.OpOptionalDic;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorOptional;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorParams;
import com.solution.app.justpay4u.Api.Fintech.Object.PaymentGatewayType;
import com.solution.app.justpay4u.Api.Fintech.Object.RealLapuCommissionSlab;
import com.solution.app.justpay4u.Api.Fintech.Object.RechargeStatus;
import com.solution.app.justpay4u.Api.Fintech.Object.SlabDetailDisplayLvl;
import com.solution.app.justpay4u.Api.Fintech.Request.ChoosePaymentGatwayRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GatewayTransactionRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetHLRLookUpRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.HeavyrefreshRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.OptionalOperatorRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.ROfferRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.CircleZoneListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DTHInfoResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DthPlanInfoResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.FetchBillResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetHLRLookUPResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.InitiateUpiResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.NumberSeriesListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OnboardingResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OperatorListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OperatorOptionalResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RechargeReportResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RechargeResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.SlabCommissionResponse;
import com.solution.app.justpay4u.Api.Networking.Response.GatwayStatusCheckResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.Authentication.ChangePinPassActivity;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.AddMoneyActivity;
import com.solution.app.justpay4u.Fintech.FundTransactions.Adapter.GatewayTypeAdapter;
import com.solution.app.justpay4u.Fintech.Recharge.Adapter.BillDetailAdapter;
import com.solution.app.justpay4u.Fintech.Recharge.Adapter.DthCustInfoAdapter;
import com.solution.app.justpay4u.Fintech.Recharge.Adapter.IncentiveAdapter;
import com.solution.app.justpay4u.Fintech.Reports.Activity.RechargeReportActivity;
import com.solution.app.justpay4u.Fintech.Reports.Adapter.RechargeReportAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.GetLocation;
import com.solution.app.justpay4u.Util.ServiceIcon;
import com.solution.app.justpay4u.Util.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RechargeBillPaymentActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, View.OnClickListener {

    RecyclerView custInfoRecyclerView;
    int maxAmountLength;
    int minAmountLength;
    boolean isAcountNumeric, isTakeCustomerNum;
    int minNumberLength = 0, maxNumberLength = 0;
    String AccountName = "Number", Account_Remark = "";
    boolean isBBPS = false, isPartial = false;
    boolean isBilling = false;
    String StartWith = "";
    boolean isFetchBill;
    ArrayList<String> StartWithArray = new ArrayList<>();
    String operatorSelected = "";
    int operatorSelectedId = 0, incentiveOperatorSelectedId;
    String operatorDocName;
    ArrayList<IncentiveDetails> incentiveList = new ArrayList<>();
    HashMap<String, IncentiveDetails> incentiveMap = new HashMap<>();
    String from;
    String Icon;
    CustomLoader loader;
    LoginResponse mLoginDataResponse;
    GetLocation mGetLocation;
    RequestOptions requestOptions;
    private LinearLayout topView, numberMoveView;
    private LinearLayout llPrepaid;
    private AppCompatImageView prepaidIcon;
    private AppCompatTextView prepaidTv;
    private LinearLayout llPostpaid;
    private AppCompatImageView postpaidIcon;
    private AppCompatTextView postpaidTv;
    private LinearLayout llDth;
    private AppCompatImageView dthIcon;
    private AppCompatTextView dthTv;
    private RelativeLayout selectedOperatorView;
    private ImageView ivOprator;
    private AppCompatTextView billTypeTv;
    private AppCompatTextView tvOperator;
    private AppCompatImageView ivArrowOp;
    private ImageView billLogo;
    private TextView heavyRefresh;
    private ImageView ivNumber;
    private TextView tvNumberName;
    private EditText etNumber;
    private ImageView ivNumberPhoneBook;
    private TextView AcountRemarkTv;
    private TextView MobileNoCount;
    private LinearLayout rlOption1;
    private EditText etOption1;
    private TextView option1Remark;
    private LinearLayout rlOption2;
    private EditText etOption2;
    private TextView option2Remark;
    private LinearLayout rlOption3;
    private EditText etOption3;
    private TextView option3Remark;
    private LinearLayout rlOption4;
    private EditText etOption4;
    private TextView option4Remark;
    private LinearLayout llDropDownOption1;
    private TextView tvDropDownOption1;
    private TextView dropDownOption1Remark;
    private LinearLayout llDropDownOption2;
    private TextView tvDropDownOption2;
    private TextView dropDownOption2Remark;
    private LinearLayout llDropDownOption3;
    private TextView tvDropDownOption3;
    private TextView dropDownOption3Remark;
    private LinearLayout llDropDownOption4;
    private TextView tvDropDownOption4;
    private TextView dropDownOption4Remark;
    private LinearLayout rlDthInfoDetail;
    private AppCompatImageView operatorIcon;
    private TextView operator;
    private TextView tel;
    private LinearLayout llCustomerLayout;
    private TextView customerName;
    private LinearLayout llBalAmount;
    private TextView Balance;
    private LinearLayout llPlanName;
    private TextView planname;
    private LinearLayout llRechargeDate;
    private TextView NextRechargeDate;
    private LinearLayout llPackageAmt;
    private TextView RechargeAmount;
    private LinearLayout rlBillDetail;
    private RelativeLayout rlEtAmount;
    private TextView billDetailTitle;
    private LinearLayout billDetailContent;
    private LinearLayout consumerNameView;
    private TextView consumerName;
    private LinearLayout consumerNumView;
    private TextView consumerNum;
    private LinearLayout paybleAmtView;
    private TextView paybleAmt;
    private LinearLayout dueDateView, bilDateView;
    private TextView dueDate, bilDate;
    private TextView errorMsgBilldetail;
    private LinearLayout switchView;
    private SwitchCompat lapuSwitch;
    private SwitchCompat realApiSwitch;
    private RelativeLayout rlCustNumber;
    private ImageView amountDetailArrow;
    private RecyclerView billDetailRecyclerView, additionalInfoRecyclerView;
    private View billDetailclickView, billPeriodView;
    private TextView tvName, billPeriod;
    private EditText etCustNumber;
    private ImageView ivPhoneBook;
    private EditText etAmount;
    private AppCompatTextView cashBackTv;
    private TextView desc;
    private LinearLayout llBrowsePlan;
    private TextView tvBrowsePlan;
    private TextView tvPdfPlan;
    private TextView tvRofferPlan;
    private LinearLayout historyView;
    private AppCompatTextView recentRechargeTv;
    private AppCompatTextView viewMore;
    private RecyclerView recyclerView;
    private Button btRecharge;
    private Dialog incentiveDialog;
    private String fetchBillRefId;
    private String fromDateStr, toDateStr;
    private int fromId;
    private AlertDialog alertDialogFetchBill;
    private String operatorRefCircleID;
    private CustomAlertDialog mCustomPassDialog;
    private int INTENT_ROFFER = 153;
    private int INTENT_SELECT_OPERATOR = 291;
    private int INTENT_VIEW_PLAN = 372;
    private int INTENT_SELECT_ZONE = 405;
    private int INTENT_PASSWORD_EXPIRE = 792;
    private int INTENT_RECHARGE_SLIP = 832;
    private DTHInfoRecords mDthInfoRecords;
    private String realCommisionStr;
    private Toolbar toolBar;
    private boolean isRecharge;
    private NumberSeriesListResponse numberSeriesListResponse;
    private OperatorListResponse operatorListResponse;
    private CircleZoneListResponse circleZoneListResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private TextToSpeech tts;
    private boolean isTTSInit;
    private String msgSpeak = "";
    private OnboardingResponse mOnboardingResponse;
    private boolean isOnboardingSuccess;
    private int fetchBillId;
    private String regularExpress;
    private int exactness;
    private double billAmount;
    private ArrayList<String> dropDownOption1Array, dropDownOption2Array, dropDownOption3Array, dropDownOption4Array;
    private DropDownDialog mDropDownDialog;
    private int selectedDropDownOption1Pos = -1;
    private int selectedDropDownOption2Pos = -1;
    private int selectedDropDownOption3Pos = -1;
    private int selectedDropDownOption4Pos = -1;
    private String inputPinPass = "";
    private int INTENT_ADD_MONEY = 956;
    private AlertDialog alertDialogAddMoneyConfirm;
    private BalanceResponse balanceCheckResponse;
    private int timing;
    private double currentBalance;
    private Handler balanceHandler;
    private Runnable balanceRunnable;
    private double debitedWalletAmt;
    private AlertDialog alertDialog;
    private int timeCounter;
    private double commAmount;
    private boolean isApiCalling;
    private OperatorListResponse mOperatorListResponse;
    int selectedOPId = -1;
    private String selectedMethod;
    private SlabDetailDisplayLvl selectedOperator;
    private boolean isUpi;
    private boolean isPaymentGatway;
    private Gson gson;
    ArrayList<SlabDetailDisplayLvl> operatorArray = new ArrayList<>();
    private int requestedAmtPay;
    ArrayList<PaymentGatewayType> pgList = new ArrayList<>();
    private Dialog gatewayDialog;
    private PaymentGatewayType paymentGatewayType;
    private String selectedWalletId = "1";
    private int currentPGId;
    private String upiTID;
    private String requestAmount = "0";
    private int INTENT_UPI = 8765;
    private int INTENT_UPI_WEB = 6782;
    private Dialog uiWebViewDialog;
    private boolean isAllUpiDialogCanceled;
    private boolean isActivityPause;
    private boolean isAllUPIStatusCheckRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_recharge_bill_payment);
            isActivityPause = false;
            mDropDownDialog = new DropDownDialog(this);
            gson = new Gson();
            requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_AppLogo_circleCrop();
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            // setOperator();
            from = getIntent().getExtras().getString("from");
            fromId = getIntent().getExtras().getInt("fromId", 0);
            findViews();
            setListners();

            setDataIdWise(fromId);
            new Handler(Looper.getMainLooper()).post(() -> {
                mCustomPassDialog = new CustomAlertDialog(this);
                if (!mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.voiceEnablePref)) {
                    tts = new TextToSpeech(this, this);
                }
                setOperator();
                if (fromId == 1 || fromId == 32) {
                    setNumberSeriesAndCircle();
                }

                getUiData();
                fromDateStr = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(Calendar.getInstance().getTime());
                toDateStr = fromDateStr;

                mGetLocation = new GetLocation(RechargeBillPaymentActivity.this, loader);
                if (ApiFintechUtilMethods.INSTANCE.getLattitude == 0 || ApiFintechUtilMethods.INSTANCE.getLongitude == 0) {
                    mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
                        ApiFintechUtilMethods.INSTANCE.getLattitude = lattitude;
                        ApiFintechUtilMethods.INSTANCE.getLongitude = longitude;
                    });
                }
                HitApi();
            });

        });

    }


    private void setOperator() {

        operatorListResponse = ApiFintechUtilMethods.INSTANCE.getOperatorListResponse(mAppPreferences);

        if (operatorListResponse == null || operatorListResponse.getOperators() == null) {

            ApiFintechUtilMethods.INSTANCE.OperatorList(this, deviceId, deviceSerialNum, mAppPreferences, mLoginDataResponse, object -> {
                operatorListResponse = (OperatorListResponse) object;
            });
        }

    }

    private void setNumberSeriesAndCircle() {

        if (numberSeriesListResponse == null || numberSeriesListResponse.getNumSeries() == null) {
            numberSeriesListResponse = ApiFintechUtilMethods.INSTANCE.getNumberSeriesListResponse(mAppPreferences);

            if (numberSeriesListResponse == null || numberSeriesListResponse.getNumSeries() == null) {

                ApiFintechUtilMethods.INSTANCE.NumberSeriesList(this, null, deviceId, deviceSerialNum, mAppPreferences, object -> numberSeriesListResponse = (NumberSeriesListResponse) object);
            }
        }

        if (circleZoneListResponse == null || circleZoneListResponse.getCirlces() == null) {

            circleZoneListResponse = ApiFintechUtilMethods.INSTANCE.getCircleZoneListResponse(mAppPreferences);

            if (circleZoneListResponse == null || circleZoneListResponse.getCirlces() == null) {

                ApiFintechUtilMethods.INSTANCE.CircleZoneList(this, deviceId, deviceSerialNum, mAppPreferences, object -> {
                    circleZoneListResponse = (CircleZoneListResponse) object;
                });
            }
        }

    }

    private void findViews() {
        toolBar = findViewById(R.id.toolbar);
        toolBar.setTitle(from + "");
        toolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());

        topView = findViewById(R.id.topView);
        numberMoveView = findViewById(R.id.numberMoveView);
        llPrepaid = findViewById(R.id.ll_prepaid);
        prepaidIcon = findViewById(R.id.prepaidIcon);
        prepaidTv = findViewById(R.id.prepaidTv);
        llPostpaid = findViewById(R.id.ll_postpaid);
        postpaidIcon = findViewById(R.id.postpaidIcon);
        postpaidTv = findViewById(R.id.postpaidTv);
        llDth = findViewById(R.id.ll_dth);
        dthIcon = findViewById(R.id.dthIcon);
        dthTv = findViewById(R.id.dthTv);
        selectedOperatorView = findViewById(R.id.selectedOperatorView);
        ivOprator = findViewById(R.id.iv_oprator);
        billTypeTv = findViewById(R.id.billTypeTv);
        tvOperator = findViewById(R.id.tv_operator);
        ivArrowOp = findViewById(R.id.iv_arrow_op);
        billLogo = findViewById(R.id.bill_logo);
        heavyRefresh = findViewById(R.id.heavyRefresh);
        ivNumber = findViewById(R.id.iv_number);
        tvNumberName = findViewById(R.id.tv_number_name);
        etNumber = findViewById(R.id.et_number);
        ivNumberPhoneBook = findViewById(R.id.iv_number_phone_book);
        AcountRemarkTv = findViewById(R.id.AcountRemark);
        MobileNoCount = findViewById(R.id.MobileNoCount);
        rlOption1 = findViewById(R.id.rl_option1);
        etOption1 = findViewById(R.id.et_option1);
        option1Remark = findViewById(R.id.option1_remark);
        rlOption2 = findViewById(R.id.rl_option2);
        etOption2 = findViewById(R.id.et_option2);
        option2Remark = findViewById(R.id.option2_remark);
        rlOption3 = findViewById(R.id.rl_option3);
        etOption3 = findViewById(R.id.et_option3);
        option3Remark = findViewById(R.id.option3_remark);
        rlOption4 = findViewById(R.id.rl_option4);
        etOption4 = findViewById(R.id.et_option4);
        option4Remark = findViewById(R.id.option4_remark);
        llDropDownOption1 = findViewById(R.id.ll_drop_down_option1);
        tvDropDownOption1 = findViewById(R.id.tv_drop_down_option1);
        dropDownOption1Remark = findViewById(R.id.drop_down_option1_remark);
        llDropDownOption2 = findViewById(R.id.ll_drop_down_option2);
        tvDropDownOption2 = findViewById(R.id.tv_drop_down_option2);
        dropDownOption2Remark = findViewById(R.id.drop_down_option2_remark);
        llDropDownOption3 = findViewById(R.id.ll_drop_down_option3);
        tvDropDownOption3 = findViewById(R.id.tv_drop_down_option3);
        dropDownOption3Remark = findViewById(R.id.drop_down_option3_remark);
        llDropDownOption4 = findViewById(R.id.ll_drop_down_option4);
        tvDropDownOption4 = findViewById(R.id.tv_drop_down_option4);
        dropDownOption4Remark = findViewById(R.id.drop_down_option4_remark);
        rlDthInfoDetail = findViewById(R.id.rl_DthInfoDetail);
        operatorIcon = findViewById(R.id.operatorIcon);
        operator = findViewById(R.id.operator);
        tel = findViewById(R.id.tel);
        llCustomerLayout = findViewById(R.id.ll_customer_layout);
        customerName = findViewById(R.id.customerName);
        llBalAmount = findViewById(R.id.ll_bal_amount);
        Balance = findViewById(R.id.Balance);
        llPlanName = findViewById(R.id.ll_plan_name);
        planname = findViewById(R.id.planname);
        llRechargeDate = findViewById(R.id.ll_RechargeDate);
        NextRechargeDate = findViewById(R.id.NextRechargeDate);
        llPackageAmt = findViewById(R.id.ll_packageAmt);
        custInfoRecyclerView = findViewById(R.id.custInfoRecyclerView);
        custInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RechargeAmount = findViewById(R.id.RechargeAmount);
        rlBillDetail = findViewById(R.id.rl_billDetail);
        billDetailTitle = findViewById(R.id.billDetailTitle);
        billDetailContent = findViewById(R.id.billDetailContent);
        consumerNameView = findViewById(R.id.consumerNameView);
        consumerName = findViewById(R.id.consumerName);
        consumerNumView = findViewById(R.id.consumerNumView);
        consumerNum = findViewById(R.id.consumerNum);
        paybleAmtView = findViewById(R.id.paybleAmtView);
        paybleAmt = findViewById(R.id.paybleAmt);
        dueDateView = findViewById(R.id.dueDateView);
        bilDateView = findViewById(R.id.bilDateView);
        dueDate = findViewById(R.id.dueDate);
        bilDate = findViewById(R.id.bilDate);
        errorMsgBilldetail = findViewById(R.id.errorMsgBilldetail);
        switchView = findViewById(R.id.switchView);
        lapuSwitch = findViewById(R.id.lapuSwitch);
        realApiSwitch = findViewById(R.id.realApiSwitch);
        rlCustNumber = findViewById(R.id.rl_cust_number);
        amountDetailArrow = findViewById(R.id.amountDetailArrow);
        tvName = findViewById(R.id.tv_name);
        etCustNumber = findViewById(R.id.et_cust_number);
        ivPhoneBook = findViewById(R.id.iv_phone_book);
        etAmount = findViewById(R.id.et_amount);
        cashBackTv = findViewById(R.id.cashBackTv);
        desc = findViewById(R.id.desc);
        llBrowsePlan = findViewById(R.id.ll_browse_plan);
        tvBrowsePlan = findViewById(R.id.tv_browse_plan);
        tvPdfPlan = findViewById(R.id.tv_pdf_plan);
        tvRofferPlan = findViewById(R.id.tv_roffer_plan);
        historyView = findViewById(R.id.historyView);
        recentRechargeTv = findViewById(R.id.recentRechargeTv);
        viewMore = findViewById(R.id.viewMore);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btRecharge = findViewById(R.id.bt_recharge);
        rlEtAmount = findViewById(R.id.rl_etAmount);
        billPeriod = findViewById(R.id.billPeriod);
        billPeriodView = findViewById(R.id.pbillPeriodView);
        billDetailclickView = findViewById(R.id.billDetailclickView);
        billDetailRecyclerView = findViewById(R.id.billDetailRecyclerView);
        billDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        additionalInfoRecyclerView = findViewById(R.id.additionalInfoRecyclerView);
        additionalInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void getUiData() {
        billDetailTitle.setText(from + " Bill Details");

        if (mLoginDataResponse.isRealAPIPerTransaction()) {
            switchView.setVisibility(View.VISIBLE);
            lapuSwitch.setChecked(true);
        } else {
            switchView.setVisibility(View.GONE);
        }


        OperatorList mOperatorList = getIntent().getParcelableExtra("SelectedOperator");
        if (mOperatorList != null) {
            setIntentData(mOperatorList);
        } else {
            loader.dismiss();
        }

    }

    private void setListners() {
        btRecharge.setOnClickListener(this);
        ivPhoneBook.setOnClickListener(this);
        viewMore.setOnClickListener(this);
        tvBrowsePlan.setOnClickListener(this);
        tvPdfPlan.setOnClickListener(this);
        ivNumberPhoneBook.setOnClickListener(this);
        tvRofferPlan.setOnClickListener(this);

        llDropDownOption1.setOnClickListener(view -> {
            if (dropDownOption1Array != null && dropDownOption1Array.size() > 0) {
                mDropDownDialog.showDropDownPopup(view, selectedDropDownOption1Pos, dropDownOption1Array, (clickPosition, value, object) -> {
                    tvDropDownOption1.setError(null);
                    if (selectedDropDownOption1Pos != clickPosition) {
                        tvDropDownOption1.setText(value + "");
                        selectedDropDownOption1Pos = clickPosition;
                    }
                });
            } else {
                tvDropDownOption1.setError("Data is not available for selection");
                tvDropDownOption1.requestFocus();
            }
        });

        llDropDownOption2.setOnClickListener(view -> {
            if (dropDownOption2Array != null && dropDownOption2Array.size() > 0) {
                mDropDownDialog.showDropDownPopup(view, selectedDropDownOption2Pos, dropDownOption2Array, (clickPosition, value, object) -> {
                    tvDropDownOption2.setError(null);
                    if (selectedDropDownOption2Pos != clickPosition) {
                        tvDropDownOption2.setText(value + "");
                        selectedDropDownOption2Pos = clickPosition;
                    }
                });
            } else {
                tvDropDownOption2.setError("Data is not available for selection");
                tvDropDownOption2.requestFocus();
            }
        });

        llDropDownOption3.setOnClickListener(view -> {
            if (dropDownOption3Array != null && dropDownOption3Array.size() > 0) {
                mDropDownDialog.showDropDownPopup(view, selectedDropDownOption3Pos, dropDownOption3Array, (clickPosition, value, object) -> {
                    tvDropDownOption3.setError(null);
                    if (selectedDropDownOption3Pos != clickPosition) {
                        tvDropDownOption3.setText(value + "");
                        selectedDropDownOption3Pos = clickPosition;
                    }
                });
            } else {
                tvDropDownOption3.setError("Data is not available for selection");
                tvDropDownOption3.requestFocus();
            }
        });


        llDropDownOption4.setOnClickListener(view -> {
            if (dropDownOption4Array != null && dropDownOption4Array.size() > 0) {
                mDropDownDialog.showDropDownPopup(view, selectedDropDownOption4Pos, dropDownOption4Array, (clickPosition, value, object) -> {
                    tvDropDownOption4.setError(null);
                    if (selectedDropDownOption4Pos != clickPosition) {
                        tvDropDownOption4.setText(value + "");
                        selectedDropDownOption4Pos = clickPosition;
                    }
                });
            } else {
                tvDropDownOption4.setError("Data is not available for selection");
                tvDropDownOption4.requestFocus();
            }
        });

        llPrepaid.setOnClickListener(v -> {
            if (fromId != 1) {
                from = "Prepaid";
                fromId = 1;
                setNumberSeriesAndCircle();
                toolBar.setTitle("Prepaid");
                setDataIdWise(1);
            } /*else if (fromId != 32 && fromId != 1) {
                from = "FRC Prepaid";
                fromId = 32;
                toolBar.setTitle("FRC Prepaid");
                setDataIdWise(32);
            }*/
        });

        llPostpaid.setOnClickListener(v -> {
            if (fromId != 2) {
                from = "Postpaid";
                fromId = 2;
                toolBar.setTitle("Postpaid");
                setDataIdWise(2);

            }
        });

        llDth.setOnClickListener(v -> {
            if (fromId != 3) {
                from = "DTH";
                fromId = 3;
                toolBar.setTitle("DTH");
                setDataIdWise(3);
            }/* else if (fromId != 33 && fromId != 3) {
                from = "FRC DTH";
                fromId = 33;
                toolBar.setTitle("FRC DTH");
                setDataIdWise(33);
            }*/
        });


        cashBackTv.setOnClickListener(v -> {
            if (operatorSelectedId != 0) {
                if (incentiveList != null && incentiveList.size() > 0 && incentiveOperatorSelectedId == operatorSelectedId) {
                    showPopupIncentive();
                } else {
                    HitIncentiveApi(true);
                }
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(this, "Please Select Operator.");
            }
        });

        heavyRefresh.setOnClickListener(v -> {
            if (!validateNumber()) {
                return;
            }
            HeavyRefresh();
        });
        selectedOperatorView.setOnClickListener(v -> {
            Intent i = new Intent(this, RechargeProviderActivity.class);
            i.putExtra("from", from);
            i.putExtra("fromId", fromId);
            i.putExtra("fromPhoneRecharge", true);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_SELECT_OPERATOR);
        });

        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               /* if (!validateNumber()) {
                    return;
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {

                    MobileNoCount.setVisibility(View.VISIBLE);
                    MobileNoCount.setText("Number Digit- " + s.length());
                } else {
                    MobileNoCount.setVisibility(View.GONE);
                }


                if (fromId == 1 /*|| fromId == 2*/) {
                    if (mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isLookUpFromAPIPref)) {
                        if (maxNumberLength != 0 && maxNumberLength == s.length() || s.length() == 10) {
                            getHLRLookUp();
                        }
                    } else {
                        if (s.length() == 4) {
                            SelectOperator(s.toString());
                        }
                    }


                }


                if (maxNumberLength != 0 && maxNumberLength != s.length()) {
                    tvName.setText("");
                    tvName.setVisibility(View.GONE);
                }
                if (maxNumberLength != 0 && maxNumberLength == s.length()) {
                    etAmount.requestFocus();
                    etNumber.setError(null);
                    if (fromId == 3) {
                        if (mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isDTHInfoAutoCallPref)) {
                            DTHinfo();
                        }
                    }
                }
                /*if (fromId==3 && UtilMethods.INSTANCE.getIsDTHInfoAutoCall(SecondBillPaymentActivity.this)) {
                    if (s.length() == maxNumberLength) {
                        DTHinfo();
                    }
                }*/
            }
        });

        if (fromId == 1 || fromId == 3) {
            if (mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isShowPDFPlanPref)) {
                tvPdfPlan.setVisibility(View.VISIBLE);
            } else {
                tvPdfPlan.setVisibility(View.GONE);
            }
            etAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (desc.getVisibility() == View.VISIBLE) {
                        desc.setVisibility(View.GONE);
                    }
                    desc.setText("");

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }


        lapuSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                lapuSwitch.setTextColor(getResources().getColor(R.color.colorAccent));
                realApiSwitch.setTextColor(getResources().getColor(R.color.grey));
                realApiSwitch.setChecked(false);
                if (fromId == 1 || fromId == 32) {
                    if (mLoginDataResponse.isDenominationIncentive()) {
                        cashBackTv.setVisibility(View.VISIBLE);
                    } else {
                        cashBackTv.setVisibility(View.GONE);
                    }
                    if (desc.getText().toString().contains("Cash Back")) {
                        desc.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                if (cashBackTv.getVisibility() == View.VISIBLE) {
                    cashBackTv.setVisibility(View.GONE);
                    desc.setVisibility(View.GONE);
                }
                realApiSwitch.setChecked(true);
            }
        });

        realApiSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                lapuSwitch.setTextColor(getResources().getColor(R.color.grey));
                realApiSwitch.setTextColor(getResources().getColor(R.color.colorAccent));
                lapuSwitch.setChecked(false);
                if (operatorSelectedId != 0 && realCommisionStr != null && !realCommisionStr.isEmpty()) {
                    realApiSwitch.setText("Recharge With Real " + realCommisionStr);
                } else {
                    realApiSwitch.setText("Recharge With Real");
                }

            } else {
                realApiSwitch.setText("Recharge With Real");
                lapuSwitch.setChecked(true);
            }
        });

        billDetailclickView.setOnClickListener(view -> {
            if (amountDetailArrow.getVisibility() == View.VISIBLE) {
                if (billDetailRecyclerView.getVisibility() == View.VISIBLE) {
                    billDetailRecyclerView.setVisibility(View.GONE);
                    paybleAmtView.setBackgroundColor(0);
                    amountDetailArrow.setRotation(0);
                } else {
                    amountDetailArrow.setRotation(180);
                    billDetailRecyclerView.setVisibility(View.VISIBLE);
                    paybleAmtView.setBackgroundColor(getResources().getColor(R.color.devider_color_alpha));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == viewMore) {
            Intent intent = new Intent(this, RechargeReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } /*else if (v == ivNumberPhoneBook) {
            openPhoneBook(PICK_CONTACT, INTENT_PERMISSION);
        } else if (v == ivPhoneBook) {
            openPhoneBook(PICK_CUSTOMER_CONTACT, INTENT_CUSTOMER_PERMISSION);
        }*/ else if (v == tvRofferPlan) {
            if (!validateNumber()) {
                return;
            }

            if (fromId == 3) {
                DTHinfo();
            } else {
                if (operatorSelected.toLowerCase().contains("jio")) {
                    tvBrowsePlan.performClick();
                } else {
                    Intent browseIntent = new Intent(RechargeBillPaymentActivity.this, ROfferActivity.class);
                    browseIntent.putExtra("OperatorSelectedId", operatorSelectedId + "");
                    browseIntent.putExtra("Number", etNumber.getText().toString().trim());
                    browseIntent.putExtra("DeviceId", deviceId);
                    browseIntent.putExtra("DeviceSerialNum", deviceSerialNum);
                    browseIntent.putExtra("IsPlanServiceUpdated", mLoginDataResponse.isPlanServiceUpdated());
                    startActivityForResult(browseIntent, INTENT_ROFFER);

                }
            }

        } else if (v == tvPdfPlan) {
            if (operatorSelectedId == 0 || tvOperator.getText().toString().isEmpty()) {
                tvOperator.setError("Please select operator first");
                tvOperator.requestFocus();
                msgSpeak = "Please select operator first.";
                playVoice();
                return;
            } else if (operatorDocName == null || operatorDocName.isEmpty()) {
                msgSpeak = "Document not available, please try after some time.";
                Toast.makeText(this, msgSpeak, Toast.LENGTH_LONG).show();
                playVoice();
                return;
            }
            try {
                Intent dialIntent = new Intent(Intent.ACTION_VIEW);
                dialIntent.setData(Uri.parse(ApplicationConstant.INSTANCE.baseUrl + "Image/planDoc/" + operatorDocName));
                startActivity(dialIntent);
            } catch (Exception e) {
                Intent dialIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApplicationConstant.INSTANCE.baseUrl + "Image/planDoc/" + operatorDocName));
                startActivity(dialIntent);
            }

        } else if (v == tvBrowsePlan) {

            if (operatorSelectedId == 0 || tvOperator.getText().toString().isEmpty()) {
                tvOperator.setError("Please select operator first");
                tvOperator.requestFocus();
                msgSpeak = "Please select operator first.";
                playVoice();
                return;
            }
            if (fromId == 3) {
                Intent planIntent = new Intent(RechargeBillPaymentActivity.this, DthPlanInfoActivity.class);
                planIntent.putExtra("OperatorSelectedId", operatorSelectedId + "");
                planIntent.putExtra("Number", etNumber.getText().toString().trim());
                planIntent.putExtra("DeviceId", deviceId);
                planIntent.putExtra("IsPlanServiceUpdated", mLoginDataResponse.isPlanServiceUpdated());
                planIntent.putExtra("DeviceSerialNum", deviceSerialNum);
                planIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(planIntent, INTENT_VIEW_PLAN);
            } else {
                if (operatorRefCircleID != null && !operatorRefCircleID.isEmpty()) {

                    ViewPlan();

                } else {
                    if (operatorSelectedId != 0) {
                        Intent planOptionIntent = new Intent(this, CircleZoneActivity.class);
                        planOptionIntent.putParcelableArrayListExtra("CircleList", circleZoneListResponse.getCirlces());
                        planOptionIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivityForResult(planOptionIntent, INTENT_SELECT_ZONE);
                    } else {
                        ApiFintechUtilMethods.INSTANCE.Error(this, "Please Select the Operator.");
                    }
                }

            }

        } else if (v == btRecharge) {

            if (!isOnboardingSuccess && mOnboardingResponse != null) {
                mCustomPassDialog = new CustomAlertDialog(this);
                ApiFintechUtilMethods.INSTANCE.showCallOnBoardingMsgs(this, mOnboardingResponse, mCustomPassDialog);
                return;
            } else if (!isOnboardingSuccess && mOnboardingResponse == null) {
                loader.show();
                checkOnBoarding();
                return;
            }

            if (isFetchBill) {
                if (!validateFetchBillNumber()) {
                    return;
                }
                if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(RechargeBillPaymentActivity.this)) {

                    if (ApiFintechUtilMethods.INSTANCE.getLattitude != 0 && ApiFintechUtilMethods.INSTANCE.getLongitude != 0) {
                        fetchBill(ApiFintechUtilMethods.INSTANCE.getLattitude, ApiFintechUtilMethods.INSTANCE.getLongitude);
                    } else {
                        if (mGetLocation != null) {
                            mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                                ApiFintechUtilMethods.INSTANCE.getLattitude = lattitude;
                                ApiFintechUtilMethods.INSTANCE.getLongitude = longitude;
                                fetchBill(lattitude, longitude);
                            });
                        } else {
                            mGetLocation = new GetLocation(RechargeBillPaymentActivity.this, loader);
                            mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                                ApiFintechUtilMethods.INSTANCE.getLattitude = lattitude;
                                ApiFintechUtilMethods.INSTANCE.getLongitude = longitude;
                                fetchBill(lattitude, longitude);
                            });
                        }
                    }

                } else {
                    ApiFintechUtilMethods.INSTANCE.NetworkError(RechargeBillPaymentActivity.this);
                }
            } else {
                if (!validateNumber()) {
                    return;
                } else if (rlEtAmount.getVisibility() == View.VISIBLE && !validateAmount()) {
                    return;
                }

                if (realApiSwitch.isChecked() && switchView.getVisibility() == View.VISIBLE) {
                    ApiFintechUtilMethods.INSTANCE.GetCalculateLapuRealCommission(RechargeBillPaymentActivity.this, operatorSelectedId + "", etAmount.getText().toString().trim(), loader, mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiCallBackTwoMethod() {
                        @Override
                        public void onSucess(Object object) {
                            CommissionDisplay mCommissionDisplay = (CommissionDisplay) object;
                            RechargeDialog(mCommissionDisplay);
                        }

                        @Override
                        public void onError(Object object) {

                        }
                    });
                } else {
                    RechargeDialog(null);
                }
                // RechargeDialog();

            }
        }

    }

    private void fetchBill(double lattitude, double longitude) {
        fetchBillDialog();

        String option1Value = "", option2Value = "", option3Value = "", option4Value = "";

        if (rlOption1.getVisibility() == View.VISIBLE) {
            option1Value = etOption1.getText().toString();
        } else if (llDropDownOption1.getVisibility() == View.VISIBLE) {
            option1Value = tvDropDownOption1.getText().toString();
        }

        if (rlOption2.getVisibility() == View.VISIBLE) {
            option2Value = etOption2.getText().toString();
        } else if (llDropDownOption2.getVisibility() == View.VISIBLE) {
            option2Value = tvDropDownOption2.getText().toString();
        }

        if (rlOption3.getVisibility() == View.VISIBLE) {
            option3Value = etOption3.getText().toString();
        } else if (llDropDownOption3.getVisibility() == View.VISIBLE) {
            option3Value = tvDropDownOption3.getText().toString();
        }

        if (rlOption4.getVisibility() == View.VISIBLE) {
            option4Value = etOption4.getText().toString();
        } else if (llDropDownOption4.getVisibility() == View.VISIBLE) {
            option4Value = tvDropDownOption4.getText().toString();
        }
        ApiFintechUtilMethods.INSTANCE.FetchBillApi(RechargeBillPaymentActivity.this, etCustNumber.getText().toString(), operatorSelectedId + "", etNumber.getText().toString().trim(), option1Value, option2Value, option3Value, option4Value, lattitude + "," + longitude, "10", alertDialogFetchBill, btRecharge, mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiCallBackTwoMethod() {
            @Override
            public void onSucess(Object object) {
                FetchBillResponse response = (FetchBillResponse) object;
                rlBillDetail.setVisibility(View.VISIBLE);
                billDetailContent.setVisibility(View.VISIBLE);
                billDetailTitle.setVisibility(View.VISIBLE);
                errorMsgBilldetail.setVisibility(View.GONE);
                isFetchBill = false;
                exactness = response.getbBPSResponse().getExactness();
                fetchBillRefId = response.getbBPSResponse().getRefID();
                fetchBillId = response.getbBPSResponse().getFetchBillID();
                etAmount.setText(response.getbBPSResponse().getAmount());
                try {
                    billAmount = Double.parseDouble(response.getbBPSResponse().getAmount());
                } catch (NumberFormatException nfe) {

                }
                rlEtAmount.setVisibility(View.GONE);
                btRecharge.setText("Bill Payment");

                if (response.getbBPSResponse().getCustomerName() != null && !response.getbBPSResponse().getCustomerName().isEmpty()) {
                    consumerName.setText(response.getbBPSResponse().getCustomerName() + "");
                    consumerNameView.setVisibility(View.VISIBLE);
                } else {
                    consumerNameView.setVisibility(View.GONE);
                }

                if (response.getbBPSResponse().getBillNumber() != null && !response.getbBPSResponse().getBillNumber().isEmpty()) {
                    consumerNum.setText(response.getbBPSResponse().getBillNumber() + "");
                    consumerNumView.setVisibility(View.VISIBLE);
                } else {
                    consumerNumView.setVisibility(View.GONE);
                }

                if (response.getbBPSResponse().getAmount() != null && !response.getbBPSResponse().getAmount().isEmpty()) {
                    paybleAmt.setText(getString(R.string.rupiya) + " " + response.getbBPSResponse().getAmount() + "");
                    etAmount.setText(response.getbBPSResponse().getAmount() + "");
                    paybleAmtView.setVisibility(View.VISIBLE);
                } else {
                    paybleAmtView.setVisibility(View.GONE);
                }

                if (response.getbBPSResponse().getBillDate() != null && !response.getbBPSResponse().getBillDate().isEmpty()) {
                    bilDate.setText(response.getbBPSResponse().getBillDate() + "");
                    bilDateView.setVisibility(View.VISIBLE);
                } else {
                    bilDateView.setVisibility(View.GONE);
                }
                if (response.getbBPSResponse().getDueDate() != null && !response.getbBPSResponse().getDueDate().isEmpty()) {
                    dueDate.setText(response.getbBPSResponse().getDueDate() + "");
                    dueDateView.setVisibility(View.VISIBLE);
                } else {
                    dueDateView.setVisibility(View.GONE);
                }
                if (response.getbBPSResponse().getBillPeriod() != null && !response.getbBPSResponse().getBillPeriod().isEmpty()) {
                    billPeriod.setText(response.getbBPSResponse().getBillPeriod() + "");
                    billPeriodView.setVisibility(View.VISIBLE);
                } else {
                    billPeriodView.setVisibility(View.GONE);
                }


                if (response.getbBPSResponse().getIsEditable() && exactness != 1) {
                    rlEtAmount.setVisibility(View.VISIBLE);
                } else {
                    rlEtAmount.setVisibility(View.GONE);
                }


                if (response.getbBPSResponse().getIsEnablePayment()) {
                    isFetchBill = false;
                    btRecharge.setText("Bill Payment");
                } else {
                    isFetchBill = true;
                    btRecharge.setText("Fetch Bill");

                }


                if (response.getbBPSResponse().getBillAmountOptions() != null && response.getbBPSResponse().getBillAmountOptions().size() > 0) {

                    amountDetailArrow.setVisibility(View.VISIBLE);
                    paybleAmtView.setBackgroundColor(0);
                    billDetailRecyclerView.setVisibility(View.GONE);
                    billDetailRecyclerView.setAdapter(new BillDetailAdapter(response.getbBPSResponse().getBillAmountOptions(), RechargeBillPaymentActivity.this, R.layout.adapter_bill_detail));
                } else {
                    paybleAmtView.setBackgroundColor(0);
                    billDetailRecyclerView.setVisibility(View.GONE);
                    amountDetailArrow.setVisibility(View.GONE);
                }
                if (response.getbBPSResponse().getBillAdditionalInfo() != null && response.getbBPSResponse().getBillAdditionalInfo().size() > 0) {
                    additionalInfoRecyclerView.setVisibility(View.VISIBLE);
                    additionalInfoRecyclerView.setAdapter(new BillDetailAdapter(response.getbBPSResponse().getBillAdditionalInfo(), RechargeBillPaymentActivity.this, R.layout.adapter_aditional_info));
                } else {
                    additionalInfoRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Object object) {
                isFetchBill = false;
                if (object instanceof String) {
                    billDetailContent.setVisibility(View.GONE);
                    rlBillDetail.setVisibility(View.VISIBLE);
                    billDetailTitle.setVisibility(View.VISIBLE);
                    errorMsgBilldetail.setVisibility(View.VISIBLE);
                    errorMsgBilldetail.setText((String) object);
                    rlEtAmount.setVisibility(View.VISIBLE);

                }
                if (object instanceof FetchBillResponse) {
                    FetchBillResponse response = (FetchBillResponse) object;

                    if (response != null && response.getbBPSResponse() != null) {
                        if (response.getbBPSResponse().getIsShowMsgOnly()) {
                            billDetailContent.setVisibility(View.GONE);
                            rlBillDetail.setVisibility(View.VISIBLE);
                            billDetailTitle.setVisibility(View.VISIBLE);
                            errorMsgBilldetail.setVisibility(View.VISIBLE);
                            errorMsgBilldetail.setText(response.getbBPSResponse().getErrorMsg());

                        } else {
                            billDetailContent.setVisibility(View.GONE);
                            rlBillDetail.setVisibility(View.GONE);
                            billDetailTitle.setVisibility(View.GONE);
                            errorMsgBilldetail.setVisibility(View.GONE);
                        }


                        if (response.getbBPSResponse().getIsEditable()) {
                            rlEtAmount.setVisibility(View.VISIBLE);
                        } else {
                            rlEtAmount.setVisibility(View.GONE);
                        }


                        if (response.getbBPSResponse().getIsEnablePayment()) {
                            isFetchBill = false;
                            btRecharge.setText("Bill Payment");
                        } else {
                            isFetchBill = true;
                            btRecharge.setText("Fetch Bill");

                        }

                    }

                }


            }
        });
    }


   /* void openPhoneBook(int intentRequest, int inentPermisssion) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(this, PermissionActivity.class),
                        inentPermisssion);

            } else {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, intentRequest);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, intentRequest);
        }
    }*/

    void setDataIdWise(int id) {
        billTypeTv.setText(from + "");
        ivNumber.setImageResource(ServiceIcon.INSTANCE.serviceIcon(id, true));
        if (id == 1) {
            isRecharge = true;
            isOnboardingSuccess = true;
            refreshData(id);
            ((RelativeLayout.LayoutParams) numberMoveView.getLayoutParams()).removeRule(RelativeLayout.BELOW);
            ((RelativeLayout.LayoutParams) selectedOperatorView.getLayoutParams()).addRule(RelativeLayout.BELOW, R.id.numberMoveView);
            rlCustNumber.setVisibility(View.GONE);

            if (mLoginDataResponse.isDenominationIncentive()) {
                cashBackTv.setVisibility(View.VISIBLE);
            } else {
                cashBackTv.setVisibility(View.GONE);
            }
            topView.setVisibility(View.VISIBLE);
            ivArrowOp.setVisibility(View.VISIBLE);
            if (mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isROfferPref)) {
                tvRofferPlan.setVisibility(View.VISIBLE);
                tvRofferPlan.setText("Best Offer");
            } else {
                tvRofferPlan.setVisibility(View.GONE);
            }
            llBrowsePlan.setVisibility(View.VISIBLE);
            recentRechargeTv.setText("Recent Recharges");
            //  ivNumberPhoneBook.setVisibility(View.VISIBLE);

            ViewCompat.setBackgroundTintList(prepaidIcon, ContextCompat.getColorStateList(this, R.color.white));
            ImageViewCompat.setImageTintList(prepaidIcon, ContextCompat.getColorStateList(this, R.color.colorPrimary));
            prepaidTv.setTextColor(Color.WHITE);

            ViewCompat.setBackgroundTintList(postpaidIcon, ContextCompat.getColorStateList(this, R.color.colorAccent));
            ImageViewCompat.setImageTintList(postpaidIcon, ContextCompat.getColorStateList(this, R.color.white));
            postpaidTv.setTextColor(getResources().getColor(R.color.colorBackground));

            ViewCompat.setBackgroundTintList(dthIcon, ContextCompat.getColorStateList(this, R.color.colorAccent));
            ImageViewCompat.setImageTintList(dthIcon, ContextCompat.getColorStateList(this, R.color.white));
            dthTv.setTextColor(getResources().getColor(R.color.colorBackground));
        } else if (id == 2) {
            isRecharge = false;
            isOnboardingSuccess = true;
            refreshData(id);
            ((RelativeLayout.LayoutParams) numberMoveView.getLayoutParams()).removeRule(RelativeLayout.BELOW);
            ((RelativeLayout.LayoutParams) selectedOperatorView.getLayoutParams()).addRule(RelativeLayout.BELOW, R.id.numberMoveView);


            topView.setVisibility(View.VISIBLE);
            ivArrowOp.setVisibility(View.VISIBLE);
            cashBackTv.setVisibility(View.GONE);
            llBrowsePlan.setVisibility(View.GONE);
            recentRechargeTv.setText("Recent Bill Payments");
            // ivNumberPhoneBook.setVisibility(View.VISIBLE);
            ViewCompat.setBackgroundTintList(postpaidIcon, ContextCompat.getColorStateList(this, R.color.white));
            ImageViewCompat.setImageTintList(postpaidIcon, ContextCompat.getColorStateList(this, R.color.colorPrimary));
            postpaidTv.setTextColor(Color.WHITE);

            ViewCompat.setBackgroundTintList(prepaidIcon, ContextCompat.getColorStateList(this, R.color.colorAccent));
            ImageViewCompat.setImageTintList(prepaidIcon, ContextCompat.getColorStateList(this, R.color.white));
            prepaidTv.setTextColor(getResources().getColor(R.color.colorBackground));

            ViewCompat.setBackgroundTintList(dthIcon, ContextCompat.getColorStateList(this, R.color.colorAccent));
            ImageViewCompat.setImageTintList(dthIcon, ContextCompat.getColorStateList(this, R.color.white));
            dthTv.setTextColor(getResources().getColor(R.color.colorBackground));
        } else if (id == 3) {
            isRecharge = true;
            isOnboardingSuccess = true;
            refreshData(id);
            ((RelativeLayout.LayoutParams) selectedOperatorView.getLayoutParams()).removeRule(RelativeLayout.BELOW);
            ((RelativeLayout.LayoutParams) numberMoveView.getLayoutParams()).addRule(RelativeLayout.BELOW, R.id.selectedOperatorView);

            rlCustNumber.setVisibility(View.GONE);
            topView.setVisibility(View.VISIBLE);
            ivArrowOp.setVisibility(View.VISIBLE);
            if (mLoginDataResponse.isDenominationIncentive()) {
                cashBackTv.setVisibility(View.VISIBLE);
            } else {
                cashBackTv.setVisibility(View.GONE);
            }
            if (mLoginDataResponse.isHeavyRefresh()) {
                heavyRefresh.setVisibility(View.VISIBLE);
            } else {
                heavyRefresh.setVisibility(View.GONE);
            }
            if (mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isDTHInfoPref)) {
                tvRofferPlan.setVisibility(View.VISIBLE);
                tvRofferPlan.setText("DTH Info");
            } else {
                tvRofferPlan.setVisibility(View.GONE);
            }
            llBrowsePlan.setVisibility(View.VISIBLE);
            recentRechargeTv.setText("Recent Recharges");
            //  ivNumberPhoneBook.setVisibility(View.GONE);

            ViewCompat.setBackgroundTintList(dthIcon, ContextCompat.getColorStateList(this, R.color.white));
            ImageViewCompat.setImageTintList(dthIcon, ContextCompat.getColorStateList(this, R.color.colorPrimary));
            dthTv.setTextColor(Color.WHITE);

            ViewCompat.setBackgroundTintList(postpaidIcon, ContextCompat.getColorStateList(this, R.color.colorAccent));
            ImageViewCompat.setImageTintList(postpaidIcon, ContextCompat.getColorStateList(this, R.color.white));
            postpaidTv.setTextColor(getResources().getColor(R.color.colorBackground));

            ViewCompat.setBackgroundTintList(prepaidIcon, ContextCompat.getColorStateList(this, R.color.colorAccent));
            ImageViewCompat.setImageTintList(prepaidIcon, ContextCompat.getColorStateList(this, R.color.white));
            prepaidTv.setTextColor(getResources().getColor(R.color.colorBackground));

        } else if (id == 32) {
            isOnboardingSuccess = true;
            isRecharge = true;
            refreshData(id);
            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolBar.getLayoutParams();
            params.setScrollFlags(0);
            rlCustNumber.setVisibility(View.GONE);
            topView.setVisibility(View.GONE);
            ivArrowOp.setVisibility(View.VISIBLE);
            if (mLoginDataResponse.isDenominationIncentive()) {
                cashBackTv.setVisibility(View.VISIBLE);
            } else {
                cashBackTv.setVisibility(View.GONE);
            }
            llBrowsePlan.setVisibility(View.GONE);
            // ivNumberPhoneBook.setVisibility(View.VISIBLE);
            recentRechargeTv.setText("Recent Recharges");
            ViewCompat.setBackgroundTintList(prepaidIcon, ContextCompat.getColorStateList(this, R.color.white));
            ImageViewCompat.setImageTintList(prepaidIcon, ContextCompat.getColorStateList(this, R.color.colorPrimary));
            prepaidTv.setTextColor(Color.WHITE);

            ViewCompat.setBackgroundTintList(postpaidIcon, ContextCompat.getColorStateList(this, R.color.colorAccent));
            ImageViewCompat.setImageTintList(postpaidIcon, ContextCompat.getColorStateList(this, R.color.white));
            postpaidTv.setTextColor(getResources().getColor(R.color.colorBackground));

            ViewCompat.setBackgroundTintList(dthIcon, ContextCompat.getColorStateList(this, R.color.colorAccent));
            ImageViewCompat.setImageTintList(dthIcon, ContextCompat.getColorStateList(this, R.color.white));
            dthTv.setTextColor(getResources().getColor(R.color.colorBackground));
        } else if (id == 33) {
            isOnboardingSuccess = true;
            isRecharge = true;
            refreshData(id);
            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolBar.getLayoutParams();
            params.setScrollFlags(0);
            rlCustNumber.setVisibility(View.GONE);
            topView.setVisibility(View.GONE);
            ivArrowOp.setVisibility(View.VISIBLE);
            if (mLoginDataResponse.isDenominationIncentive()) {
                cashBackTv.setVisibility(View.VISIBLE);
            } else {
                cashBackTv.setVisibility(View.GONE);
            }
            llBrowsePlan.setVisibility(View.GONE);
            // ivNumberPhoneBook.setVisibility(View.VISIBLE);
            recentRechargeTv.setText("Recent Recharges");
            ViewCompat.setBackgroundTintList(dthIcon, ContextCompat.getColorStateList(this, R.color.white));
            ImageViewCompat.setImageTintList(dthIcon, ContextCompat.getColorStateList(this, R.color.colorPrimary));
            dthTv.setTextColor(Color.WHITE);

            ViewCompat.setBackgroundTintList(postpaidIcon, ContextCompat.getColorStateList(this, R.color.colorAccent));
            ImageViewCompat.setImageTintList(postpaidIcon, ContextCompat.getColorStateList(this, R.color.white));
            postpaidTv.setTextColor(getResources().getColor(R.color.colorBackground));

            ViewCompat.setBackgroundTintList(prepaidIcon, ContextCompat.getColorStateList(this, R.color.colorAccent));
            ImageViewCompat.setImageTintList(prepaidIcon, ContextCompat.getColorStateList(this, R.color.white));
            prepaidTv.setTextColor(getResources().getColor(R.color.colorBackground));

        } else {
            isRecharge = false;
            refreshData(id);
            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolBar.getLayoutParams();
            params.setScrollFlags(0);


            recentRechargeTv.setText("Recent Bill Payments");
            topView.setVisibility(View.GONE);
            ivArrowOp.setVisibility(View.GONE);
            cashBackTv.setVisibility(View.GONE);
            llBrowsePlan.setVisibility(View.GONE);
            heavyRefresh.setVisibility(View.GONE);
            // ivNumberPhoneBook.setVisibility(View.GONE);
            selectedOperatorView.setClickable(false);
        }
        selectedOperatorView.setVisibility(View.VISIBLE);
        numberMoveView.setVisibility(View.VISIBLE);
    }

    void refreshData(int id) {
        if (id == 1 || id == 2 || id == 3 || id == 32 || id == 33) {
            operatorSelected = "";
            operatorSelectedId = 0;
            operatorDocName = "";
            minAmountLength = 0;
            maxAmountLength = 0;
            minNumberLength = 0;
            maxNumberLength = 0;
            regularExpress = "";
            isAcountNumeric = false;
            isPartial = false;
            isBBPS = false;
            isBilling = false;
            AccountName = "Number";
            Account_Remark = "";
            StartWith = "";
            Icon = "";
            billLogo.setVisibility(View.GONE);
            ivOprator.setImageResource(R.drawable.ic_tower);

            etNumber.setHint("Enter " + from + " Number");
            tvOperator.setText("");
            tvOperator.setError(null);
            AcountRemarkTv.setText("");
            AcountRemarkTv.setVisibility(View.GONE);
            desc.setText("");
            desc.setVisibility(View.GONE);
            rlEtAmount.setVisibility(View.VISIBLE);
        }
        if (!isRecharge && isBilling) {
            isFetchBill = true;
            btRecharge.setText("Fetch Bill");
            rlEtAmount.setVisibility(View.GONE);
           /* if (isPartial) {
                rlEtAmount.setVisibility(View.VISIBLE);
            } else {
                rlEtAmount.setVisibility(View.GONE);
            }*/
        } else {
            isFetchBill = false;
            if (isRecharge) {
                btRecharge.setText("Recharge");
            } else {
                btRecharge.setText("Bill Payment");
            }
            rlEtAmount.setVisibility(View.VISIBLE);
        }


        etCustNumber.setText("");
        etCustNumber.setError(null);
        etOption1.setText("");
        etOption1.setError(null);
        etOption2.setText("");
        etOption2.setError(null);
        etOption3.setText("");
        etOption3.setError(null);
        etOption4.setText("");
        etOption4.setError(null);
        etAmount.setText("");
        etAmount.setError(null);
        etNumber.setText("");
        etNumber.setError(null);
        tvName.setVisibility(View.GONE);
        tvNumberName.setVisibility(View.GONE);
        rlDthInfoDetail.setVisibility(View.GONE);
        rlBillDetail.setVisibility(View.GONE);
        heavyRefresh.setVisibility(View.GONE);

    }

    public void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            ApiFintechUtilMethods.INSTANCE.RechargeReport(this, "0", "10", 0, fromDateStr, toDateStr, "", "", "", "false", true, null, mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
                @Override
                public void onSucess(Object object) {
                    RechargeReportResponse mRechargeReportResponse = (RechargeReportResponse) object;
                    dataParse(mRechargeReportResponse);
                }

                @Override
                public void onError(int error) {

                }
            });
           /* UtilMethods.INSTANCE.LastRechargeReport(this, "0", "0", fromDateStr, toDateStr, "", "", "false", ll_comingsoon, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    RechargeReportResponse mRechargeReportResponse = (RechargeReportResponse) object;
                    dataParse(mRechargeReportResponse);
                }
            });*/
        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    public void dataParse(RechargeReportResponse response) {

        ArrayList<RechargeStatus> transactionsObjects = new ArrayList<>();
        transactionsObjects = response.getRechargeReport();
        if (transactionsObjects.size() > 0) {
            RechargeReportAdapter mAdapter = new RechargeReportAdapter(transactionsObjects, this, false, mLoginDataResponse, deviceId, deviceSerialNum);
            historyView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(mAdapter);
        } else {
            historyView.setVisibility(View.GONE);
        }
    }


    public void fetchBillDialog() {
        try {
            if (alertDialogFetchBill != null && alertDialogFetchBill.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this);

            alertDialogFetchBill = dialogBuilder.create();
            alertDialogFetchBill.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_custom_fetch_bill, null);
            alertDialogFetchBill.setView(dialogView);

            TextView titleTextDialog = dialogView.findViewById(R.id.titleText);

            AppCompatImageView billIconDialog = dialogView.findViewById(R.id.billIcon);
            View customerNumView = dialogView.findViewById(R.id.customerNumView);

            AppCompatTextView tv_customerNumber = dialogView.findViewById(R.id.tv_customerNumber);
            AppCompatTextView tvBillTitleDialog = dialogView.findViewById(R.id.tv_bill_title);
            AppCompatTextView tvBillNumberDialog = dialogView.findViewById(R.id.tv_bill_number);
            LinearLayout viewDisplay1Dialog = dialogView.findViewById(R.id.view_display_1);
            AppCompatImageView iconDisplay1Dialog = dialogView.findViewById(R.id.icon_display_1);
            AppCompatTextView tvDisplay1TitleDialog = dialogView.findViewById(R.id.tv_display1_title);
            AppCompatTextView tvDisplay1Dialog = dialogView.findViewById(R.id.tv_display1);
            LinearLayout viewDisplay2Dialog = dialogView.findViewById(R.id.view_display_2);
            AppCompatImageView iconDisplay2Dialog = dialogView.findViewById(R.id.icon_display_2);
            AppCompatTextView tvDisplay2TitleDialog = dialogView.findViewById(R.id.tv_display2_title);
            AppCompatTextView tvDisplay2Dialog = dialogView.findViewById(R.id.tv_display2);
            LinearLayout viewDisplay3Dialog = dialogView.findViewById(R.id.view_display_3);
            AppCompatImageView iconDisplay3Dialog = dialogView.findViewById(R.id.icon_display_3);
            AppCompatTextView tvDisplay3TitleDialog = dialogView.findViewById(R.id.tv_display3_title);
            AppCompatTextView tvDisplay3Dialog = dialogView.findViewById(R.id.tv_display3);
            LinearLayout viewDisplay4Dialog = dialogView.findViewById(R.id.view_display_4);
            AppCompatImageView iconDisplay4Dialog = dialogView.findViewById(R.id.icon_display_4);
            AppCompatTextView tvDisplay4TitleDialog = dialogView.findViewById(R.id.tv_display4_title);
            AppCompatTextView tvDisplay4Dialog = dialogView.findViewById(R.id.tv_display4);
            ImageView bbpsLogo = dialogView.findViewById(R.id.bill_logo);
            if (isBBPS) {
                bbpsLogo.setVisibility(View.VISIBLE);
            } else {
                bbpsLogo.setVisibility(View.GONE);
            }
            titleTextDialog.setText("Fetching " + from + " Bill Details");
            tvBillTitleDialog.setText(operatorSelected + "");
            tvBillNumberDialog.setText(etNumber.getText().toString());
            billIconDialog.setImageResource(ServiceIcon.INSTANCE.serviceIcon(fromId, true));

            if (rlOption1.getVisibility() == View.VISIBLE) {
                viewDisplay1Dialog.setVisibility(View.VISIBLE);
                //  iconDisplay1Dialog.setImageResource(ServiceIcon.INSTANCE.serviceIcon(fromServiceId));
                tvDisplay1TitleDialog.setText(etOption1.getHint().toString());
                tvDisplay1Dialog.setText(etOption1.getText().toString());
            } else if (llDropDownOption1.getVisibility() == View.VISIBLE) {
                viewDisplay1Dialog.setVisibility(View.VISIBLE);
                //  iconDisplay1Dialog.setImageResource(ServiceIcon.INSTANCE.serviceIcon(fromServiceId));
                tvDisplay1TitleDialog.setText(tvDropDownOption1.getHint().toString());
                tvDisplay1Dialog.setText(tvDropDownOption1.getText().toString());
            } else {
                viewDisplay1Dialog.setVisibility(View.GONE);
            }

            if (rlOption2.getVisibility() == View.VISIBLE) {
                viewDisplay2Dialog.setVisibility(View.VISIBLE);
                //     iconDisplay2Dialog.setImageResource(ServiceIcon.INSTANCE.serviceIcon(fromServiceId));
                tvDisplay2TitleDialog.setText(etOption2.getHint().toString());
                tvDisplay2Dialog.setText(etOption2.getText().toString());
            } else if (llDropDownOption2.getVisibility() == View.VISIBLE) {
                viewDisplay2Dialog.setVisibility(View.VISIBLE);
                //     iconDisplay2Dialog.setImageResource(ServiceIcon.INSTANCE.serviceIcon(fromServiceId));
                tvDisplay2TitleDialog.setText(tvDropDownOption2.getHint().toString());
                tvDisplay2Dialog.setText(tvDropDownOption2.getText().toString());
            } else {
                viewDisplay2Dialog.setVisibility(View.GONE);
            }

            if (rlOption3.getVisibility() == View.VISIBLE) {
                viewDisplay3Dialog.setVisibility(View.VISIBLE);
                //  iconDisplay3Dialog.setImageResource(ServiceIcon.INSTANCE.serviceIcon(fromServiceId));
                tvDisplay3TitleDialog.setText(etOption3.getHint().toString());
                tvDisplay3Dialog.setText(etOption3.getText().toString());
            } else if (llDropDownOption3.getVisibility() == View.VISIBLE) {
                viewDisplay3Dialog.setVisibility(View.VISIBLE);
                //     iconDisplay3Dialog.setImageResource(ServiceIcon.INSTANCE.serviceIcon(fromServiceId));
                tvDisplay3TitleDialog.setText(tvDropDownOption3.getHint().toString());
                tvDisplay3Dialog.setText(tvDropDownOption3.getText().toString());
            } else {
                viewDisplay3Dialog.setVisibility(View.GONE);
            }

            if (rlOption4.getVisibility() == View.VISIBLE) {
                viewDisplay4Dialog.setVisibility(View.VISIBLE);
                //  iconDisplay4Dialog.setImageResource(ServiceIcon.INSTANCE.serviceIcon(fromServiceId));
                tvDisplay4TitleDialog.setText(etOption4.getHint().toString());
                tvDisplay4Dialog.setText(etOption4.getText().toString());
            } else if (llDropDownOption4.getVisibility() == View.VISIBLE) {
                viewDisplay4Dialog.setVisibility(View.VISIBLE);
                //     iconDisplay4Dialog.setImageResource(ServiceIcon.INSTANCE.serviceIcon(fromServiceId));
                tvDisplay4TitleDialog.setText(tvDropDownOption4.getHint().toString());
                tvDisplay4Dialog.setText(tvDropDownOption4.getText().toString());
            } else {
                viewDisplay4Dialog.setVisibility(View.GONE);
            }

            if (rlCustNumber.getVisibility() == View.VISIBLE) {
                customerNumView.setVisibility(View.VISIBLE);
                tv_customerNumber.setText(etCustNumber.getText().toString());
            } else {
                customerNumView.setVisibility(View.GONE);
            }

            alertDialogFetchBill.show();
        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }

    }

    private void RechargeDialog(CommissionDisplay mCommissionDisplay) {

        if (ApiFintechUtilMethods.INSTANCE.isVpnConnected(this)) {
            return;
        }

        if (ApiFintechUtilMethods.INSTANCE.getLattitude != 0 && ApiFintechUtilMethods.INSTANCE.getLongitude != 0) {
            recharge(ApiFintechUtilMethods.INSTANCE.getLattitude, ApiFintechUtilMethods.INSTANCE.getLongitude, mCommissionDisplay);
        } else {
            if (mGetLocation != null) {
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    ApiFintechUtilMethods.INSTANCE.getLattitude = lattitude;
                    ApiFintechUtilMethods.INSTANCE.getLongitude = longitude;
                    recharge(lattitude, longitude, mCommissionDisplay);
                });
            } else {
                mGetLocation = new GetLocation(RechargeBillPaymentActivity.this, loader);
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    ApiFintechUtilMethods.INSTANCE.getLattitude = lattitude;
                    ApiFintechUtilMethods.INSTANCE.getLongitude = longitude;
                    recharge(lattitude, longitude, mCommissionDisplay);
                });
            }
        }

    }

    private void recharge(double lattitude, double longitude, CommissionDisplay mCommissionDisplay) {
        ApiFintechUtilMethods.INSTANCE.GetTotalRechargeComm(RechargeBillPaymentActivity.this, mLoginDataResponse, operatorSelectedId, etAmount.getText().toString(), deviceId, deviceSerialNum, loader, new ApiFintechUtilMethods.ApiResponseCallBack() {
            @Override
            public void onSucess(Object object) {
                RechargeResponse rechargeResponse = (RechargeResponse) object;
                commAmount = rechargeResponse.getCommAmount();
                rechargeDialog(lattitude, longitude, mCommissionDisplay);
            }

            @Override
            public void onError(int error) {

            }
        });

    }

    private void rechargeDialog(double lattitude, double longitude, CommissionDisplay mCommissionDisplay) {

        ApiFintechUtilMethods.INSTANCE.rechargeConfiormDialog(this, commAmount, incentiveMap, mCommissionDisplay, realApiSwitch.isChecked() ? true : false, mLoginDataResponse.getData().getIsDoubleFactor(), ApplicationConstant.INSTANCE.baseIconUrl + Icon, etNumber.getText().toString(), operatorSelected, etAmount.getText().toString(), isBBPS, new ApiFintechUtilMethods.DialogCallBack() {
            @Override
            public void onPositiveClick(String pinPass) {

                if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(RechargeBillPaymentActivity.this)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    rechargeApi("", lattitude, longitude, pinPass);

                } else {
                    ApiFintechUtilMethods.INSTANCE.NetworkError(RechargeBillPaymentActivity.this);
                }
            }

            @Override
            public void onResetCallback(String pinPass, double requestedAmt) {
                inputPinPass = pinPass;
                int amt;
                if (requestedAmt % 1 != 0) {
                    amt = (int) (requestedAmt + 1);
                } else {
                    amt = (int) requestedAmt;
                }
                requestedAmtPay = amt;
                new Handler(Looper.myLooper()).post(() -> {
                    mOperatorListResponse = ApiFintechUtilMethods.INSTANCE.getOperatorListResponse(mAppPreferences);
                    isUpi = mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isUpi);
                    isPaymentGatway = mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isPaymentGatway);
                    HitCommissionApi();
                });


            }

            @Override
            public void onCancelClick() {

            }
        });


    }

    private void HitCommissionApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            isApiCalling = true;
            ApiFintechUtilMethods.INSTANCE.MyCommission(this, false, loader, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, new ApiFintechUtilMethods.ApiResponseCallBack() {
                @Override
                public void onSucess(Object object) {
                    isApiCalling = false;
                    SlabCommissionResponse mSlabCommissionResponse = (SlabCommissionResponse) object;
                    if (mSlabCommissionResponse != null && mSlabCommissionResponse.getSlabDetailDisplayLvl() != null && mSlabCommissionResponse.getSlabDetailDisplayLvl().size() > 0) {
                        setUiData(mSlabCommissionResponse);
                    } else {
                        if (operatorArray != null && operatorArray.size() > 0) {
//                            noDataView.setVisibility(View.GONE);
//                            noNetworkView.setVisibility(View.GONE);
                        } else {
//                            noDataView.setVisibility(View.VISIBLE);
//                            noNetworkView.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onError(int error) {
                    isApiCalling = false;
                    if (operatorArray != null && operatorArray.size() > 0) {
//                        noDataView.setVisibility(View.GONE);
//                        noNetworkView.setVisibility(View.GONE);
                    } else {
                        if (error == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
//                            noDataView.setVisibility(View.GONE);
//                            noNetworkView.setVisibility(View.VISIBLE);
                        } else {
//                            noDataView.setVisibility(View.VISIBLE);
//                            noNetworkView.setVisibility(View.GONE);
                        }
                    }
                }
            });

        } else {
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            if (operatorArray != null && operatorArray.size() > 0) {
//                noDataView.setVisibility(View.GONE);
//                noNetworkView.setVisibility(View.GONE);
            } else {
//                noDataView.setVisibility(View.GONE);
//                noNetworkView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setUiData(SlabCommissionResponse mOperatorListResponse) {

        if (isUpi && !isPaymentGatway) {
            getOpId(mOperatorListResponse, 50);
        } else if (isUpi && isPaymentGatway) {
            getOperator(mOperatorListResponse, 50, 37);
        } else if (!isUpi && isPaymentGatway) {
            getOperator(mOperatorListResponse, 37, -1);
        }
    }

    private void getOpId(SlabCommissionResponse mSlabCommissionResponse, int op_Type) {
        if (mSlabCommissionResponse != null && mSlabCommissionResponse.getSlabDetailDisplayLvl() != null && mSlabCommissionResponse.getSlabDetailDisplayLvl().size() > 0 && mOperatorListResponse != null && mOperatorListResponse.getOperators() != null && mOperatorListResponse.getOperators().size() > 0) {
            operatorArray.clear();
            for (SlabDetailDisplayLvl slab : mSlabCommissionResponse.getSlabDetailDisplayLvl()) {
                if (slab.getOpTypeId() == op_Type) {
                    for (OperatorList op : mOperatorListResponse.getOperators()) {
                        if (slab.getOid() == op.getOid() && op.isActive()) {
                            selectedOPId = slab.getOid();
                            selectedMethod = slab.getOperator();
                            selectedOperator = slab;
                            break;
                        }
                    }
                }
            }

        } else {
            if (mSlabCommissionResponse != null && mSlabCommissionResponse.getSlabDetailDisplayLvl() != null && mSlabCommissionResponse.getSlabDetailDisplayLvl().size() > 0) {
                for (SlabDetailDisplayLvl op : mSlabCommissionResponse.getSlabDetailDisplayLvl()) {
                    if (op.getOpTypeId() == op_Type) {
                        selectedOPId = op.getOid();
                        selectedMethod = op.getOperator();
                        selectedOperator = op;
                    }
                }
            }
        }


    }


    private void getOperator(SlabCommissionResponse mSlabCommissionResponse, int op_Type1, int op_Type2) {
        if (mSlabCommissionResponse != null && mSlabCommissionResponse.getSlabDetailDisplayLvl() != null && mSlabCommissionResponse.getSlabDetailDisplayLvl().size() > 0 && mOperatorListResponse != null && mOperatorListResponse.getOperators() != null && mOperatorListResponse.getOperators().size() > 0) {
            operatorArray.clear();
            for (SlabDetailDisplayLvl slab : mSlabCommissionResponse.getSlabDetailDisplayLvl()) {
                if (slab.getOpTypeId() == op_Type1 || slab.getOpTypeId() == op_Type2) {
                    for (OperatorList op : mOperatorListResponse.getOperators()) {
                        if (slab.getOid() == op.getOid() && op.isActive()) {
                            operatorArray.add(slab);
                            break;
                        }
                    }
                }
            }
        } else {
            if (mSlabCommissionResponse != null && mSlabCommissionResponse.getSlabDetailDisplayLvl() != null && mSlabCommissionResponse.getSlabDetailDisplayLvl().size() > 0) {
                operatorArray.clear();
                for (SlabDetailDisplayLvl slab : mSlabCommissionResponse.getSlabDetailDisplayLvl()) {
                    if ((slab.getOpTypeId() == op_Type1 || slab.getOpTypeId() == op_Type2)) {
                        operatorArray.add(slab);
                    }
                }
            } else {
                if (!isApiCalling) {
                    loader.show();
                    HitCommissionApi();
                }
            }
        }
        if (operatorArray != null && operatorArray.size() == 1) {
            paymentTypeClick(operatorArray.get(0));
        } else {
            Intent i = new Intent(RechargeBillPaymentActivity.this, AddMoneyActivity.class);
            i.putExtra("IsFromRecharge", true);
//                i.putExtra("AMOUNT", (requestedAmt % 1) == 0?(int)(requestedAmt):(int)(requestedAmt+1));
            i.putExtra("AMOUNT", requestedAmtPay);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_ADD_MONEY);
        }
    }

    public void paymentTypeClick(SlabDetailDisplayLvl operator) {
        selectedMethod = operator.getOperator();
        selectedOPId = operator.getOid();
        selectedOperator = operator;
        if (operator.getOpTypeId() == 50) {
            ChoosePaymentGateway(true);
        } else {
            if (pgList != null && pgList.size() > 0) {
                if (pgList.size() == 1) {
                    startGateway(pgList.get(0));
                } else {
                    showPopupGateWay();
                }
            } else {
                ChoosePaymentGateway(false);
            }
        }
    }

    public void ChoosePaymentGateway(boolean isUPI) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.ChoosePaymentGateway(new ChoosePaymentGatwayRequest(isUPI, mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatuscode() == 1) {
                                if (response.body().getpGs() != null && response.body().getpGs().size() > 0) {
                                    pgList = response.body().getpGs();
                                    if (response.body().getpGs().size() == 1) {
                                        if (response.body().getpGs().get(0).getPgType() == 3) {
//                                            initUpi();
                                        } else {
                                            pgList = response.body().getpGs();
                                            startGateway(pgList.get(0));
                                        }
                                    } else {
                                        pgList = response.body().getpGs();
                                        showPopupGateWay();
                                    }
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Processing(RechargeBillPaymentActivity.this, "Service is currently down.");
                                }

                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(RechargeBillPaymentActivity.this, response.code(), response.message());
                        }
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, e.getMessage() + "");
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(RechargeBillPaymentActivity.this, t);

                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, getString(R.string.some_thing_error));
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, e.getMessage() + "");
        }
    }

    private void showPopupGateWay() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_select_gateway, null);
        RecyclerView recyclerView = viewMyLayout.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        View closeBtn = viewMyLayout.findViewById(R.id.closeBtn);
        GatewayTypeAdapter gatewayTypeAdapter = new GatewayTypeAdapter(pgList, RechargeBillPaymentActivity.this);
        recyclerView.setAdapter(gatewayTypeAdapter);
        gatewayDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        gatewayDialog.setCancelable(false);
        gatewayDialog.setContentView(viewMyLayout);
        gatewayDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gatewayDialog.dismiss();
            }
        });
        gatewayDialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);

    }

    public void startGateway(PaymentGatewayType paymentGatewayType) {
        if (gatewayDialog != null && gatewayDialog.isShowing()) {
            gatewayDialog.dismiss();
        }
        this.paymentGatewayType = paymentGatewayType;
        GatewayTransaction(paymentGatewayType);
    }

    public void GatewayTransaction(final PaymentGatewayType paymentGatewayType) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.GatewayTransaction(new GatewayTransactionRequest(requestedAmtPay + "", paymentGatewayType.getId() + "", selectedWalletId + "", selectedOPId + "", mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getpGModelForApp() != null) {
                                        if (response.body().getpGModelForApp().getStatuscode() == 1) {
                                            requestAmount = requestedAmtPay + "";
                                            currentPGId = response.body().getpGModelForApp().getPgid();
                                            if (currentPGId == 12 || paymentGatewayType.getPgType() == 12) {
                                                if (response.body().getpGModelForApp().getUpiGatewayRequest() != null) {
                                                    if (response.body().getpGModelForApp().getUpiGatewayRequest().getStatuscode() == 1) {
                                                        upiTID = response.body().getpGModelForApp().getTid() + "";
                                                        if (response.body().getpGModelForApp().getUpiGatewayRequest().isIntentAllowed() && response.body().getpGModelForApp().getUpiGatewayRequest().getIntentString() != null && response.body().getpGModelForApp().getUpiGatewayRequest().getIntentString().contains("upi://pay?")) {
                                                            openUpiIntent(Uri.parse(response.body().getpGModelForApp().getUpiGatewayRequest().getIntentString()));
                                                        } else {
                                                            if (response.body().getpGModelForApp().getUpiGatewayRequest().getUrl() != null && URLUtil.isValidUrl(response.body().getpGModelForApp().getUpiGatewayRequest().getUrl())) {
                                                                showUPIWebview(response.body().getpGModelForApp().getUpiGatewayRequest().getUrl());
                                                                // openUpiWeb(response.body().getpGModelForApp().getUpiGatewayRequest().getUrl());
                                                            } else {
                                                                ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, "Url is not available");
                                                            }
                                                        }
                                                    } else {
                                                        ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, response.body().getpGModelForApp().getUpiGatewayRequest().getMsg() + "");
                                                    }

                                                } else {
                                                    ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, "Data is not available");
                                                }
                                            } else {
                                                ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, "SDK is not available");

                                            }
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, response.body().getpGModelForApp().getMsg() + "");
                                        }
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, response.body().getMsg() + " " + getString(R.string.some_thing_error));
                                    }
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, response.body().getMsg() + "");
                                }

                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, getString(R.string.some_thing_error));
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(RechargeBillPaymentActivity.this, response.code(), response.message());
                        }
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, e.getMessage() + "");
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        ApiFintechUtilMethods.INSTANCE.apiFailureError(RechargeBillPaymentActivity.this, t);

                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, e.getMessage() + "");
        }
    }

    private void showUPIWebview(String url) {

        if (uiWebViewDialog != null && uiWebViewDialog.isShowing()) {
            return;
        }
        isAllUpiDialogCanceled = false;
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_upi_webview, null);
        WebView webView = viewMyLayout.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeViewClient());
        webView.loadUrl(url);
        View closeBtn = viewMyLayout.findViewById(R.id.closeIv);
        uiWebViewDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        uiWebViewDialog.setCancelable(false);
        uiWebViewDialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        uiWebViewDialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        uiWebViewDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllUpiDialogCanceled = true;
                uiWebViewDialog.dismiss();
            }
        });
        uiWebViewDialog.setOnDismissListener(dialogInterface -> {
//            loader.show();
            if (currentPGId == 12) {
                addMoneyRechargeStatusDialog(this, false, upiTID, "success");
            } else {
                addMoneyRechargeStatusDialog(this, upiTID, requestedAmtPay + "", isUpi);
            }
        });
        uiWebViewDialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
    }

    void openUpiIntent(Uri Upi) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Upi);
        Intent chooser = Intent.createChooser(intent, "Pay with...");
        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, INTENT_UPI);
        } else {
            Toast.makeText(this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }
    }


    void rechargeApi(String pgRefId, double lattitude, double longitude, String pinPass) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(RechargeBillPaymentActivity.this)) {
            String option1Value = "", option2Value = "", option3Value = "", option4Value = "";

            if (rlOption1.getVisibility() == View.VISIBLE) {
                option1Value = etOption1.getText().toString();
            } else if (llDropDownOption1.getVisibility() == View.VISIBLE) {
                option1Value = tvDropDownOption1.getText().toString();
            }

            if (rlOption2.getVisibility() == View.VISIBLE) {
                option2Value = etOption2.getText().toString();
            } else if (llDropDownOption2.getVisibility() == View.VISIBLE) {
                option2Value = tvDropDownOption2.getText().toString();
            }

            if (rlOption3.getVisibility() == View.VISIBLE) {
                option3Value = etOption3.getText().toString();
            } else if (llDropDownOption3.getVisibility() == View.VISIBLE) {
                option3Value = tvDropDownOption3.getText().toString();
            }

            if (rlOption4.getVisibility() == View.VISIBLE) {
                option4Value = etOption4.getText().toString();
            } else if (llDropDownOption4.getVisibility() == View.VISIBLE) {
                option4Value = tvDropDownOption4.getText().toString();
            }
            ApiFintechUtilMethods.INSTANCE.Recharge(RechargeBillPaymentActivity.this, INTENT_RECHARGE_SLIP, realApiSwitch.isChecked() ? true : false, operatorSelectedId, operatorSelected, isRecharge, etNumber.getText().toString().trim(), etAmount.getText().toString().trim(), option1Value, option2Value, option3Value, option4Value, etCustNumber.getText().toString(), fetchBillRefId != null ? fetchBillRefId : "", fetchBillId, lattitude + "," + longitude, pinPass, mLoginDataResponse, loader, isBBPS, deviceId, deviceSerialNum, mAppPreferences, false, pgRefId, new ApiFintechUtilMethods.ApiCallBackMultiple() {
                @Override
                public void onSucessResponse(Object object, String AccountNo, String Amount, int Opid, String opName) {
                    if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                        alertDialogAddMoneyConfirm.dismiss();
                    }

                    refreshData(fromId);
                    HitApi();
                    ApiFintechUtilMethods.INSTANCE.Balancecheck(RechargeBillPaymentActivity.this, loader, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, null, balanceObject -> {
                        balanceCheckResponse = (BalanceResponse) balanceObject;
                    });
                }

                @Override
                public void onProcess(Object object) {
                    if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                        alertDialogAddMoneyConfirm.dismiss();
                    }
                }

                @Override
                public void onNetworkError(Object object) {
                    if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                        alertDialogAddMoneyConfirm.dismiss();
                    }
                }

                @Override
                public void onError(Object object) {
                    if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                        alertDialogAddMoneyConfirm.dismiss();
                    }
                }


            });
        } else {
            loader.dismiss();
            ApiFintechUtilMethods.INSTANCE.NetworkError(RechargeBillPaymentActivity.this);
        }

    }

    void rechargeApi(String pgRefId, double lattitude, double longitude, String pinPass, boolean isFromAddMoneyDialog, RechargeCallback mRechargeCallback) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(RechargeBillPaymentActivity.this)) {

            if (!isFromAddMoneyDialog) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }

            String option1Value = "", option2Value = "", option3Value = "", option4Value = "";

            if (rlOption1.getVisibility() == View.VISIBLE) {
                option1Value = etOption1.getText().toString();
            } else if (llDropDownOption1.getVisibility() == View.VISIBLE) {
                option1Value = tvDropDownOption1.getText().toString();
            }

            if (rlOption2.getVisibility() == View.VISIBLE) {
                option2Value = etOption2.getText().toString();
            } else if (llDropDownOption2.getVisibility() == View.VISIBLE) {
                option2Value = tvDropDownOption2.getText().toString();
            }

            if (rlOption3.getVisibility() == View.VISIBLE) {
                option3Value = etOption3.getText().toString();
            } else if (llDropDownOption3.getVisibility() == View.VISIBLE) {
                option3Value = tvDropDownOption3.getText().toString();
            }

            if (rlOption4.getVisibility() == View.VISIBLE) {
                option4Value = etOption4.getText().toString();
            } else if (llDropDownOption4.getVisibility() == View.VISIBLE) {
                option4Value = tvDropDownOption4.getText().toString();
            }
            ApiFintechUtilMethods.INSTANCE.Recharge(RechargeBillPaymentActivity.this, INTENT_RECHARGE_SLIP, realApiSwitch.isChecked() ? true : false, operatorSelectedId, operatorSelected, isRecharge, etNumber.getText().toString().trim(), etAmount.getText().toString().trim(), option1Value, option2Value, option3Value, option4Value, etCustNumber.getText().toString(), fetchBillRefId != null ? fetchBillRefId : "", fetchBillId, lattitude + "," + longitude, pinPass, mLoginDataResponse, loader, isBBPS, deviceId, deviceSerialNum, mAppPreferences, isFromAddMoneyDialog, pgRefId, new ApiFintechUtilMethods.ApiCallBackMultiple() {
                @Override
                public void onSucessResponse(Object object, String AccountNo, String Amount, int Opid, final String opName) {
                    if (mRechargeCallback != null) {
                        mRechargeCallback.Sucsess((RechargeResponse) object, Amount, AccountNo, opName, Opid);
                    }
                    refreshData(fromId);
                    ApiFintechUtilMethods.INSTANCE.Balancecheck(RechargeBillPaymentActivity.this, loader, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, null, balanceObject -> {
                        balanceCheckResponse = (BalanceResponse) balanceObject;
                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                            currentBalance = balanceCheckResponse.getBalanceData().get(0).getBalance();
                        }
                    });
                }

                @Override
                public void onProcess(Object object) {
                    if (mRechargeCallback != null) {
                        mRechargeCallback.Process(object);
                    }
                }

                @Override
                public void onNetworkError(Object object) {
                    if (mRechargeCallback != null) {
                        mRechargeCallback.NetworkError(object);
                    }
                }

                @Override
                public void onError(Object object) {
                    if (mRechargeCallback != null) {
                        mRechargeCallback.Error(object);
                    }
                }

            });


        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(RechargeBillPaymentActivity.this);
        }
    }

    void getLapuRealCommission() {
        ApiFintechUtilMethods.INSTANCE.GetLapuRealCommission(this, operatorSelectedId + "", mLoginDataResponse, deviceId, deviceSerialNum, object -> {
            RealLapuCommissionSlab data = (RealLapuCommissionSlab) object;
            String comType, comm, rComType, rComm;
            if (data.getCommType() == 0) {
                comType = "(COM)";
            } else {
                comType = "(SUR)";
            }

            if (data.getAmtType() == 0) {
                comm = Utility.INSTANCE.formatedAmountWithOutRupees(data.getComm() + "") + " %";
            } else {
                comm = Utility.INSTANCE.formatedAmountWithRupees(data.getComm() + "");
            }

            if (data.getrCommType() == 0) {
                rComType = "(COM)";
            } else {
                rComType = "(SUR)";
            }

            if (data.getrAmtType() == 0) {
                rComm = Utility.INSTANCE.formatedAmountWithOutRupees(data.getrComm() + "") + " %";
            } else {
                rComm = Utility.INSTANCE.formatedAmountWithRupees(data.getrComm() + "");
            }
            lapuSwitch.setText("Lapu\n" + comm + " " + comType);
            realCommisionStr = rComm + " " + rComType;
            if (realApiSwitch.isChecked()) {
                realApiSwitch.setText("Recharge With Real " + realCommisionStr);
            }


        });
    }


    public void BBPSApi() {
        try {
            if (!loader.isShowing()) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<OperatorOptionalResponse> call = git.GetOperatorOptionals(new OptionalOperatorRequest(operatorSelectedId, mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));


            call.enqueue(new Callback<OperatorOptionalResponse>() {
                @Override
                public void onResponse(Call<OperatorOptionalResponse> call, final retrofit2.Response<OperatorOptionalResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            OperatorOptionalResponse mOperatorOptionalResponse = response.body();
                            if (mOperatorOptionalResponse != null) {

                                if (mOperatorOptionalResponse.getStatuscode() == 1) {
                                    if (mOperatorOptionalResponse.getData() != null && mOperatorOptionalResponse.getData().getOperatorParams() != null && mOperatorOptionalResponse.getData().getOperatorParams().size() > 0) {
                                        hideShowOptionalParameterIndexWise(mOperatorOptionalResponse.getData().getOperatorParams(), mOperatorOptionalResponse.getData().getOpOptionalDic());
                                    } else if (mOperatorOptionalResponse.getData() != null && mOperatorOptionalResponse.getData().getOperatorOptionals() != null && mOperatorOptionalResponse.getData().getOperatorOptionals().size() > 0) {
                                        hideShowOptionalParameter1(mOperatorOptionalResponse.getData().getOperatorOptionals());
                                    } else {
                                        if (operatorListResponse != null && operatorListResponse.isTakeCustomerNo() && isTakeCustomerNum) {
                                            rlCustNumber.setVisibility(View.VISIBLE);
                                        } else {
                                            rlCustNumber.setVisibility(View.GONE);
                                        }
                                    }

                                } else {

                                    ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, mOperatorOptionalResponse.getMsg() + "");
                                }

                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, "Something went wrong, try after some time.");
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(RechargeBillPaymentActivity.this, response.code(), response.message());
                        }

                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<OperatorOptionalResponse> call, Throwable t) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        ApiFintechUtilMethods.INSTANCE.apiFailureError(RechargeBillPaymentActivity.this, t);


                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
        }
    }


    void hideShowOptionalParameter1(List<OperatorOptional> mOperatorOptionals) {
        for (int i = 0; i < mOperatorOptionals.size(); i++) {
            if (mOperatorOptionals.get(i).getOptionalType() == 1) {
                rlOption1.setVisibility(View.VISIBLE);
                etOption1.setHint("Enter " + mOperatorOptionals.get(i).getDisplayName());
                option1Remark.setVisibility(View.VISIBLE);
                option1Remark.setText(mOperatorOptionals.get(i).getRemark() + "");
            }
            if (mOperatorOptionals.get(i).getOptionalType() == 2) {
                rlOption2.setVisibility(View.VISIBLE);
                etOption2.setHint("Enter " + mOperatorOptionals.get(i).getDisplayName());
                option2Remark.setVisibility(View.VISIBLE);
                option2Remark.setText(mOperatorOptionals.get(i).getRemark() + "");
            }
            if (mOperatorOptionals.get(i).getOptionalType() == 3) {
                rlOption3.setVisibility(View.VISIBLE);
                etOption3.setHint("Enter " + mOperatorOptionals.get(i).getDisplayName());
                option3Remark.setVisibility(View.VISIBLE);
                option3Remark.setText(mOperatorOptionals.get(i).getRemark() + "");
            }
            if (mOperatorOptionals.get(i).getOptionalType() == 4) {
                rlOption4.setVisibility(View.VISIBLE);
                etOption4.setHint("Enter " + mOperatorOptionals.get(i).getDisplayName());
                option4Remark.setVisibility(View.VISIBLE);
                option4Remark.setText(mOperatorOptionals.get(i).getRemark() + "");
            }
        }

        if (operatorListResponse != null && operatorListResponse.isTakeCustomerNo() && isTakeCustomerNum) {
            rlCustNumber.setVisibility(View.VISIBLE);
        } else {
            rlCustNumber.setVisibility(View.GONE);
        }
    }

    void hideShowOptionalParameterIndWise(List<OperatorParams> mOperatorOptionalsList, List<OpOptionalDic> mOpOptionalDic) {
        boolean isCustomerNum = false;
        for (OperatorParams mOperatorOptionals : mOperatorOptionalsList) {

            if (!isCustomerNum && mOperatorOptionals.isCustomerNo()) {
                isCustomerNum = true;
            }

            if (mOperatorOptionals.getInd() == 1) {
                if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {
                    dropDownOption1Array = new ArrayList<>();
                    for (OpOptionalDic item : mOpOptionalDic) {
                        if (item.getOptionalID() == mOperatorOptionals.getId()) {
                            dropDownOption1Array.add(item.getValue() + "");
                        }
                    }

                    if (dropDownOption1Array != null && dropDownOption1Array.size() > 0) {
                        llDropDownOption1.setVisibility(View.VISIBLE);
                        tvDropDownOption1.setHint("Select " + mOperatorOptionals.getParamName());
                        if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                            dropDownOption1Remark.setVisibility(View.VISIBLE);
                            dropDownOption1Remark.setText(mOperatorOptionals.getRemark() + "");
                        }
                    } else {
                        setupOptionalView(mOperatorOptionals, rlOption1, etOption1, option1Remark);
                    }


                } else {

                    setupOptionalView(mOperatorOptionals, rlOption1, etOption1, option1Remark);
                }
            }
            if (mOperatorOptionals.getInd() == 2) {
                if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {
                    dropDownOption2Array = new ArrayList<>();
                    for (OpOptionalDic item : mOpOptionalDic) {
                        if (item.getOptionalID() == mOperatorOptionals.getId()) {
                            dropDownOption2Array.add(item.getValue() + "");
                        }
                    }

                    if (dropDownOption2Array != null && dropDownOption2Array.size() > 0) {
                        llDropDownOption2.setVisibility(View.VISIBLE);
                        tvDropDownOption2.setHint("Select " + mOperatorOptionals.getParamName());
                        if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                            dropDownOption2Remark.setVisibility(View.VISIBLE);
                            dropDownOption2Remark.setText(mOperatorOptionals.getRemark() + "");
                        }
                    } else {
                        setupOptionalView(mOperatorOptionals, rlOption2, etOption2, option2Remark);
                    }


                } else {
                    setupOptionalView(mOperatorOptionals, rlOption2, etOption2, option2Remark);
                }
            }
            if (mOperatorOptionals.getInd() == 3) {
                if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {

                    dropDownOption3Array = new ArrayList<>();
                    for (OpOptionalDic item : mOpOptionalDic) {
                        if (item.getOptionalID() == mOperatorOptionals.getId()) {
                            dropDownOption3Array.add(item.getValue() + "");
                        }
                    }

                    if (dropDownOption3Array != null && dropDownOption3Array.size() > 0) {
                        llDropDownOption3.setVisibility(View.VISIBLE);
                        tvDropDownOption3.setHint("Select " + mOperatorOptionals.getParamName());
                        if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                            dropDownOption3Remark.setVisibility(View.VISIBLE);
                            dropDownOption3Remark.setText(mOperatorOptionals.getRemark() + "");
                        }
                    } else {
                        setupOptionalView(mOperatorOptionals, rlOption3, etOption3, option3Remark);
                    }


                } else {
                    setupOptionalView(mOperatorOptionals, rlOption3, etOption3, option3Remark);
                }
            }
            if (mOperatorOptionals.getInd() == 4) {
                if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {
                    dropDownOption4Array = new ArrayList<>();
                    for (OpOptionalDic item : mOpOptionalDic) {
                        if (item.getOptionalID() == mOperatorOptionals.getId()) {
                            dropDownOption4Array.add(item.getValue() + "");
                        }
                    }

                    if (dropDownOption4Array != null && dropDownOption4Array.size() > 0) {
                        llDropDownOption4.setVisibility(View.VISIBLE);
                        tvDropDownOption4.setHint("Select " + mOperatorOptionals.getParamName());
                        if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                            dropDownOption4Remark.setVisibility(View.VISIBLE);
                            dropDownOption4Remark.setText(mOperatorOptionals.getRemark() + "");
                        }
                    } else {
                        setupOptionalView(mOperatorOptionals, rlOption4, etOption4, option4Remark);
                    }


                } else {
                    setupOptionalView(mOperatorOptionals, rlOption4, etOption4, option4Remark);
                }
            }
        }

        if (!isCustomerNum) {
            if (operatorListResponse != null && operatorListResponse.isTakeCustomerNo() && isTakeCustomerNum) {
                rlCustNumber.setVisibility(View.VISIBLE);
            } else {
                rlCustNumber.setVisibility(View.GONE);
            }
        } else {
            rlCustNumber.setVisibility(View.GONE);
        }
    }

    void hideShowOptionalParameterIndexWise(List<OperatorParams> mOperatorOptionalsList, List<OpOptionalDic> mOpOptionalDic) {
        boolean isCustomerNum = false;
        for (int i = 0; i < mOperatorOptionalsList.size(); i++) {
            OperatorParams mOperatorOptionals = mOperatorOptionalsList.get(i);
            if (!isCustomerNum && mOperatorOptionals.isCustomerNo()) {
                isCustomerNum = true;
            }

            if (i == 0) {
                if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {
                    dropDownOption1Array = new ArrayList<>();
                    for (OpOptionalDic item : mOpOptionalDic) {
                        if (item.getOptionalID() == mOperatorOptionals.getId()) {
                            dropDownOption1Array.add(item.getValue() + "");
                        }
                    }

                    if (dropDownOption1Array != null && dropDownOption1Array.size() > 0) {
                        llDropDownOption1.setVisibility(View.VISIBLE);
                        tvDropDownOption1.setHint("Select " + mOperatorOptionals.getParamName());
                        if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                            dropDownOption1Remark.setVisibility(View.VISIBLE);
                            dropDownOption1Remark.setText(mOperatorOptionals.getRemark() + "");
                        }
                    } else {
                        setupOptionalView(mOperatorOptionals, rlOption1, etOption1, option1Remark);
                    }


                } else {

                    setupOptionalView(mOperatorOptionals, rlOption1, etOption1, option1Remark);
                }
            } else if (i == 1) {
                if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {
                    dropDownOption2Array = new ArrayList<>();
                    for (OpOptionalDic item : mOpOptionalDic) {
                        if (item.getOptionalID() == mOperatorOptionals.getId()) {
                            dropDownOption2Array.add(item.getValue() + "");
                        }
                    }

                    if (dropDownOption2Array != null && dropDownOption2Array.size() > 0) {
                        llDropDownOption2.setVisibility(View.VISIBLE);
                        tvDropDownOption2.setHint("Select " + mOperatorOptionals.getParamName());
                        if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                            dropDownOption2Remark.setVisibility(View.VISIBLE);
                            dropDownOption2Remark.setText(mOperatorOptionals.getRemark() + "");
                        }
                    } else {
                        setupOptionalView(mOperatorOptionals, rlOption2, etOption2, option2Remark);
                    }


                } else {
                    setupOptionalView(mOperatorOptionals, rlOption2, etOption2, option2Remark);
                }
            } else if (i == 2) {
                if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {

                    dropDownOption3Array = new ArrayList<>();
                    for (OpOptionalDic item : mOpOptionalDic) {
                        if (item.getOptionalID() == mOperatorOptionals.getId()) {
                            dropDownOption3Array.add(item.getValue() + "");
                        }
                    }

                    if (dropDownOption3Array != null && dropDownOption3Array.size() > 0) {
                        llDropDownOption3.setVisibility(View.VISIBLE);
                        tvDropDownOption3.setHint("Select " + mOperatorOptionals.getParamName());
                        if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                            dropDownOption3Remark.setVisibility(View.VISIBLE);
                            dropDownOption3Remark.setText(mOperatorOptionals.getRemark() + "");
                        }
                    } else {
                        setupOptionalView(mOperatorOptionals, rlOption3, etOption3, option3Remark);
                    }


                } else {
                    setupOptionalView(mOperatorOptionals, rlOption3, etOption3, option3Remark);
                }
            } else {
                if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {
                    dropDownOption4Array = new ArrayList<>();
                    for (OpOptionalDic item : mOpOptionalDic) {
                        if (item.getOptionalID() == mOperatorOptionals.getId()) {
                            dropDownOption4Array.add(item.getValue() + "");
                        }
                    }

                    if (dropDownOption4Array != null && dropDownOption4Array.size() > 0) {
                        llDropDownOption4.setVisibility(View.VISIBLE);
                        tvDropDownOption4.setHint("Select " + mOperatorOptionals.getParamName());
                        if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                            dropDownOption4Remark.setVisibility(View.VISIBLE);
                            dropDownOption4Remark.setText(mOperatorOptionals.getRemark() + "");
                        }
                    } else {
                        setupOptionalView(mOperatorOptionals, rlOption4, etOption4, option4Remark);
                    }


                } else {
                    setupOptionalView(mOperatorOptionals, rlOption4, etOption4, option4Remark);
                }
            }
        }

        if (!isCustomerNum) {
            if (operatorListResponse != null && operatorListResponse.isTakeCustomerNo() && isTakeCustomerNum) {
                rlCustNumber.setVisibility(View.VISIBLE);
            } else {
                rlCustNumber.setVisibility(View.GONE);
            }
        } else {
            rlCustNumber.setVisibility(View.GONE);
        }
    }

    /* void hideShowOptionalParameter2
             (List<OperatorParams> mOperatorOptionalsList, List<OpOptionalDic> mOpOptionalDic) {
         boolean isCustomerNum = false;
         for (OperatorParams mOperatorOptionals : mOperatorOptionalsList) {

             if (!isCustomerNum && mOperatorOptionals.isCustomerNo()) {
                 isCustomerNum = true;
             }

             if (mOperatorOptionals.getInd() == 1) {
                 if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {
                     dropDownOption1Array = new ArrayList<>();
                     for (OpOptionalDic item : mOpOptionalDic) {
                         if (item.getOptionalID() == mOperatorOptionals.getId()) {
                             dropDownOption1Array.add(item.getValue() + "");
                         }
                     }


                     llDropDownOption1.setVisibility(View.VISIBLE);
                     tvDropDownOption1.setHint("Select " + mOperatorOptionals.getParamName());
                     if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                         dropDownOption1Remark.setVisibility(View.VISIBLE);
                         dropDownOption1Remark.setText(mOperatorOptionals.getRemark() + "");
                     }


                 } else {

                     rlOption1.setTag(mOperatorOptionals);
                     rlOption1.setVisibility(View.VISIBLE);

                     if (mOperatorOptionals.getDataType().equalsIgnoreCase("NUMERIC")) {
                         etOption1.setInputType(InputType.TYPE_CLASS_NUMBER);
                     } else {
                         etOption1.setInputType(InputType.TYPE_CLASS_TEXT);
                     }

                     if (mOperatorOptionals.getMaxLength() > 0) {
                         InputFilter[] filterArray = new InputFilter[1];
                         filterArray[0] = new InputFilter.LengthFilter(mOperatorOptionals.getMaxLength());
                         etOption1.setFilters(filterArray);
                     }
                     etOption1.setHint("Enter " + mOperatorOptionals.getParamName());
                     if (mOperatorOptionals.getCustomRemark() != null && !mOperatorOptionals.getCustomRemark().isEmpty()) {
                         option1Remark.setVisibility(View.VISIBLE);
                         option1Remark.setText(mOperatorOptionals.getCustomRemark() + "");
                     }
                 }
             }
             if (mOperatorOptionals.getInd() == 2) {
                 if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {
                     dropDownOption2Array = new ArrayList<>();
                     for (OpOptionalDic item : mOpOptionalDic) {
                         if (item.getOptionalID() == mOperatorOptionals.getId()) {
                             dropDownOption2Array.add(item.getValue() + "");
                         }
                     }

                     llDropDownOption2.setVisibility(View.VISIBLE);
                     tvDropDownOption2.setHint("Select " + mOperatorOptionals.getParamName());
                     if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                         dropDownOption2Remark.setVisibility(View.VISIBLE);
                         dropDownOption2Remark.setText(mOperatorOptionals.getRemark() + "");
                     }


                 } else {
                     rlOption2.setTag(mOperatorOptionals);
                     rlOption2.setVisibility(View.VISIBLE);

                     if (mOperatorOptionals.getDataType().equalsIgnoreCase("NUMERIC")) {
                         etOption2.setInputType(InputType.TYPE_CLASS_NUMBER);
                     } else {
                         etOption2.setInputType(InputType.TYPE_CLASS_TEXT);
                     }

                     if (mOperatorOptionals.getMaxLength() > 0) {
                         InputFilter[] filterArray = new InputFilter[1];
                         filterArray[0] = new InputFilter.LengthFilter(mOperatorOptionals.getMaxLength());
                         etOption2.setFilters(filterArray);
                     }
                     etOption2.setHint("Enter " + mOperatorOptionals.getParamName());
                     if (mOperatorOptionals.getCustomRemark() != null && !mOperatorOptionals.getCustomRemark().isEmpty()) {
                         option2Remark.setVisibility(View.VISIBLE);
                         option2Remark.setText(mOperatorOptionals.getCustomRemark() + "");
                     }
                 }
             }
             if (mOperatorOptionals.getInd() == 3) {
                 if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {

                     dropDownOption3Array = new ArrayList<>();
                     for (OpOptionalDic item : mOpOptionalDic) {
                         if (item.getOptionalID() == mOperatorOptionals.getId()) {
                             dropDownOption3Array.add(item.getValue() + "");
                         }
                     }

                     llDropDownOption3.setVisibility(View.VISIBLE);
                     tvDropDownOption3.setHint("Select " + mOperatorOptionals.getParamName());
                     if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                         dropDownOption3Remark.setVisibility(View.VISIBLE);
                         dropDownOption3Remark.setText(mOperatorOptionals.getRemark() + "");
                     }


                 } else {
                     rlOption3.setTag(mOperatorOptionals);
                     rlOption3.setVisibility(View.VISIBLE);

                     if (mOperatorOptionals.getDataType().equalsIgnoreCase("NUMERIC")) {
                         etOption3.setInputType(InputType.TYPE_CLASS_NUMBER);
                     } else {
                         etOption3.setInputType(InputType.TYPE_CLASS_TEXT);
                     }

                     if (mOperatorOptionals.getMaxLength() > 0) {
                         InputFilter[] filterArray = new InputFilter[1];
                         filterArray[0] = new InputFilter.LengthFilter(mOperatorOptionals.getMaxLength());
                         etOption3.setFilters(filterArray);
                     }
                     etOption3.setHint("Enter " + mOperatorOptionals.getParamName());
                     if (mOperatorOptionals.getCustomRemark() != null && !mOperatorOptionals.getCustomRemark().isEmpty()) {
                         option3Remark.setVisibility(View.VISIBLE);
                         option3Remark.setText(mOperatorOptionals.getCustomRemark() + "");
                     }
                 }
             }
             if (mOperatorOptionals.getInd() == 4) {
                 if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {
                     dropDownOption4Array = new ArrayList<>();
                     for (OpOptionalDic item : mOpOptionalDic) {
                         if (item.getOptionalID() == mOperatorOptionals.getId()) {
                             dropDownOption4Array.add(item.getValue() + "");
                         }
                     }

                     llDropDownOption4.setVisibility(View.VISIBLE);
                     tvDropDownOption4.setHint("Select " + mOperatorOptionals.getParamName());
                     if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                         dropDownOption4Remark.setVisibility(View.VISIBLE);
                         dropDownOption4Remark.setText(mOperatorOptionals.getRemark() + "");
                     }


                 } else {
                     rlOption4.setTag(mOperatorOptionals);
                     rlOption4.setVisibility(View.VISIBLE);

                     if (mOperatorOptionals.getDataType().equalsIgnoreCase("NUMERIC")) {
                         etOption4.setInputType(InputType.TYPE_CLASS_NUMBER);
                     } else {
                         etOption4.setInputType(InputType.TYPE_CLASS_TEXT);
                     }

                     if (mOperatorOptionals.getMaxLength() > 0) {
                         InputFilter[] filterArray = new InputFilter[1];
                         filterArray[0] = new InputFilter.LengthFilter(mOperatorOptionals.getMaxLength());
                         etOption4.setFilters(filterArray);
                     }
                     etOption4.setHint("Enter " + mOperatorOptionals.getParamName());
                     if (mOperatorOptionals.getCustomRemark() != null && !mOperatorOptionals.getCustomRemark().isEmpty()) {
                         option4Remark.setVisibility(View.VISIBLE);
                         option4Remark.setText(mOperatorOptionals.getCustomRemark() + "");
                     }
                 }
             }
         }

         if (!isCustomerNum) {
             if (operatorListResponse != null && operatorListResponse.isTakeCustomerNo() && isTakeCustomerNum) {
                 rlCustNumber.setVisibility(View.VISIBLE);
             } else {
                 rlCustNumber.setVisibility(View.GONE);
             }
         } else {
             rlCustNumber.setVisibility(View.GONE);
         }
     }
 */

    private void setupOptionalView(OperatorParams mOperatorOptionals, LinearLayout rlOption, EditText etOption, TextView optionRemark) {
        rlOption.setTag(mOperatorOptionals);
        rlOption.setVisibility(View.VISIBLE);

        if (mOperatorOptionals.getDataType().equalsIgnoreCase("NUMERIC")) {
            etOption.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            etOption.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        if (mOperatorOptionals.getMaxLength() > 0) {
            InputFilter[] filterArray = new InputFilter[1];
            filterArray[0] = new InputFilter.LengthFilter(mOperatorOptionals.getMaxLength());
            etOption.setFilters(filterArray);
        }
        etOption.setHint("Enter " + mOperatorOptionals.getParamName());
        if (mOperatorOptionals.getCustomRemark() != null && !mOperatorOptionals.getCustomRemark().isEmpty()) {
            optionRemark.setVisibility(View.VISIBLE);
            optionRemark.setText(mOperatorOptionals.getCustomRemark() + "");
        }
    }


    private boolean validateNumber() {

        if ((fromId != 1 && fromId != 2) && (operatorSelectedId == 0 || tvOperator.getText().toString().isEmpty())) {
            msgSpeak = "Please select operator first.";
            tvOperator.setError(msgSpeak);
            tvOperator.requestFocus();
            playVoice();
            return false;
        } else if (etNumber.getText().toString().trim().isEmpty()) {
            msgSpeak = "Please enter valid " + AccountName.trim();
            etNumber.setError(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (StartWithArray != null && StartWithArray.size() > 0 && !checkContains(StartWithArray, etNumber.getText().toString().trim())) {
            msgSpeak = AccountName.trim() + " must be start with " + StartWith;
            etNumber.setError(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;

        } else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength != maxNumberLength && etNumber.getText().length() < minNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + minNumberLength + " to " + maxNumberLength + (isAcountNumeric ? " digits" : " characters");
            etNumber.setError(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength != maxNumberLength && etNumber.getText().length() > maxNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + minNumberLength + " to " + maxNumberLength + (isAcountNumeric ? " digits" : " characters");
            etNumber.setError(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength == maxNumberLength && etNumber.getText().length() != maxNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + maxNumberLength + (isAcountNumeric ? " digits" : " characters");
            etNumber.setError(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength == 0 && etNumber.getText().length() != minNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + minNumberLength + (isAcountNumeric ? " digits" : " characters");
            etNumber.setError(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength == 0 && maxNumberLength != 0 && etNumber.getText().length() != maxNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + maxNumberLength + (isAcountNumeric ? " digits" : " characters");
            etNumber.setError(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (regularExpress != null && !regularExpress.isEmpty() && !etNumber.getText().toString().matches(regularExpress)) {
            msgSpeak = "Please enter valid " + AccountName.trim();
            etNumber.setError(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (operatorSelectedId == 0 || tvOperator.getText().toString().isEmpty()) {
            msgSpeak = "Please select operator first.";
            tvOperator.setError(msgSpeak);
            tvOperator.requestFocus();
            playVoice();
            return false;
        } else if (rlOption1.getVisibility() == View.VISIBLE && !validateOptionalParam(rlOption1, etOption1)) {
            etOption1.setError(msgSpeak);
            etOption1.requestFocus();
            playVoice();
            return false;
        } else if (llDropDownOption1.getVisibility() == View.VISIBLE && tvDropDownOption1.getText().toString().isEmpty()) {
            msgSpeak = "Please " + tvDropDownOption1.getHint().toString() + " first.";
            tvDropDownOption1.setError(msgSpeak);
            tvDropDownOption1.requestFocus();
            playVoice();
            return false;
        } else if (rlOption2.getVisibility() == View.VISIBLE && !validateOptionalParam(rlOption2, etOption2)) {
            etOption2.setError(msgSpeak);
            etOption2.requestFocus();
            playVoice();
            return false;
        } else if (llDropDownOption2.getVisibility() == View.VISIBLE && tvDropDownOption2.getText().toString().isEmpty()) {
            msgSpeak = "Please " + tvDropDownOption2.getHint().toString() + " first.";
            tvDropDownOption2.setError(msgSpeak);
            tvDropDownOption2.requestFocus();
            playVoice();
            return false;
        } else if (rlOption3.getVisibility() == View.VISIBLE && !validateOptionalParam(rlOption3, etOption3)) {
            etOption3.setError(msgSpeak);
            etOption3.requestFocus();
            playVoice();
            return false;
        } else if (llDropDownOption3.getVisibility() == View.VISIBLE && tvDropDownOption3.getText().toString().isEmpty()) {
            msgSpeak = "Please " + tvDropDownOption3.getHint().toString() + " first.";
            tvDropDownOption3.setError(msgSpeak);
            tvDropDownOption3.requestFocus();
            playVoice();
            return false;
        } else if (rlOption4.getVisibility() == View.VISIBLE && !validateOptionalParam(rlOption4, etOption4)) {
            etOption4.setError(msgSpeak);
            etOption4.requestFocus();
            playVoice();
            return false;
        } else if (llDropDownOption4.getVisibility() == View.VISIBLE && tvDropDownOption4.getText().toString().isEmpty()) {
            msgSpeak = "Please " + tvDropDownOption4.getHint().toString() + " first.";
            tvDropDownOption4.setError(msgSpeak);
            tvDropDownOption4.requestFocus();
            playVoice();
            return false;
        } else if (rlCustNumber.getVisibility() == View.VISIBLE && etCustNumber.getText().toString().trim().isEmpty()) {
            msgSpeak = "Customer Mobile Number field can't be empty";
            etCustNumber.setError(msgSpeak);
            etCustNumber.requestFocus();
            playVoice();
            return false;
        } else if (rlCustNumber.getVisibility() == View.VISIBLE && etCustNumber.getText().toString().length() != 10) {
            msgSpeak = "Enter a valid Customer Mobile Number.";
            etCustNumber.setError(msgSpeak);
            etCustNumber.requestFocus();
            playVoice();
            return false;
        }

        return true;
    }


    private boolean validateFetchBillNumber() {

        if (etNumber.getText().toString().trim().isEmpty()) {
            msgSpeak = "Please enter valid " + AccountName.trim();
            etNumber.setError(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (StartWithArray != null && StartWithArray.size() > 0 && !checkContains(StartWithArray, etNumber.getText().toString().trim())) {
            msgSpeak = AccountName.trim() + " must be start with " + StartWith;
            etNumber.setError(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength != maxNumberLength && etNumber.getText().length() < minNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + minNumberLength + " to " + maxNumberLength + (isAcountNumeric ? " digits" : " characters");
            etNumber.setError(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength != maxNumberLength && etNumber.getText().length() > maxNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + minNumberLength + " to " + maxNumberLength + (isAcountNumeric ? " digits" : " characters");
            etNumber.setError(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength == maxNumberLength && etNumber.getText().length() != maxNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + maxNumberLength + (isAcountNumeric ? " digits" : " characters");
            etNumber.setError(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength == 0 && etNumber.getText().length() != minNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + minNumberLength + (isAcountNumeric ? " digits" : " characters");
            etNumber.setError(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength == 0 && maxNumberLength != 0 && etNumber.getText().length() != maxNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + maxNumberLength + (isAcountNumeric ? " digits" : " characters");
            etNumber.setError(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (regularExpress != null && !regularExpress.isEmpty() && !etNumber.getText().toString().matches(regularExpress)) {
            msgSpeak = "Please enter valid " + AccountName.trim();
            etNumber.setError(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (rlOption1.getVisibility() == View.VISIBLE && !validateOptionalParam(rlOption1, etOption1)) {
            etOption1.setError(msgSpeak);
            etOption1.requestFocus();
            playVoice();
            return false;
        } else if (llDropDownOption1.getVisibility() == View.VISIBLE && tvDropDownOption1.getText().toString().isEmpty()) {
            msgSpeak = "Please " + tvDropDownOption1.getHint().toString() + " first.";
            tvDropDownOption1.setError(msgSpeak);
            tvDropDownOption1.requestFocus();
            playVoice();
            return false;
        } else if (rlOption2.getVisibility() == View.VISIBLE && !validateOptionalParam(rlOption2, etOption2)) {
            etOption2.setError(msgSpeak);
            etOption2.requestFocus();
            playVoice();
            return false;
        } else if (llDropDownOption2.getVisibility() == View.VISIBLE && tvDropDownOption2.getText().toString().isEmpty()) {
            msgSpeak = "Please " + tvDropDownOption2.getHint().toString() + " first.";
            tvDropDownOption2.setError(msgSpeak);
            tvDropDownOption2.requestFocus();
            playVoice();
            return false;
        } else if (rlOption3.getVisibility() == View.VISIBLE && !validateOptionalParam(rlOption3, etOption3)) {
            etOption3.setError(msgSpeak);
            etOption3.requestFocus();
            playVoice();
            return false;
        } else if (llDropDownOption3.getVisibility() == View.VISIBLE && tvDropDownOption3.getText().toString().isEmpty()) {
            msgSpeak = "Please " + tvDropDownOption3.getHint().toString() + " first.";
            tvDropDownOption3.setError(msgSpeak);
            tvDropDownOption3.requestFocus();
            playVoice();
            return false;
        } else if (rlOption4.getVisibility() == View.VISIBLE && !validateOptionalParam(rlOption4, etOption4)) {
            etOption4.setError(msgSpeak);
            etOption4.requestFocus();
            playVoice();
            return false;
        } else if (llDropDownOption4.getVisibility() == View.VISIBLE && tvDropDownOption4.getText().toString().isEmpty()) {
            msgSpeak = "Please " + tvDropDownOption4.getHint().toString() + " first.";
            tvDropDownOption4.setError(msgSpeak);
            tvDropDownOption4.requestFocus();
            playVoice();
            return false;
        } else if (rlCustNumber.getVisibility() == View.VISIBLE && etCustNumber.getText().toString().trim().isEmpty()) {
            msgSpeak = "Customer Mobile Number field can't be empty";
            etCustNumber.setError(msgSpeak);
            etCustNumber.requestFocus();
            playVoice();
            return false;
        } else if (rlCustNumber.getVisibility() == View.VISIBLE && etCustNumber.getText().toString().length() != 10) {
            msgSpeak = "Enter a valid Customer Mobile Number.";
            etCustNumber.setError(msgSpeak);
            etCustNumber.requestFocus();
            playVoice();
            return false;
        }

        return true;
    }


    boolean validateOptionalParam(LinearLayout llOption, EditText etOption) {
        if (etOption.getText().toString().trim().isEmpty()) {
            msgSpeak = etOption.getHint() + ", field can't be empty";
            return false;
        } else if (llOption.getTag() != null) {
            OperatorParams mOperatorParams = (OperatorParams) llOption.getTag();
            if (mOperatorParams != null) {
                String postfix = " ";
                if (mOperatorParams.getDataType().equalsIgnoreCase("NUMERIC")) {
                    postfix = " digits ";
                } else {
                    postfix = " characters ";
                }
                if (mOperatorParams.getMinLength() != 0 && mOperatorParams.getMaxLength() != 0 && mOperatorParams.getMinLength() != mOperatorParams.getMaxLength() && etOption.getText().length() < mOperatorParams.getMinLength()) {
                    msgSpeak = "Invalid length of " + mOperatorParams.getParamName().trim() + ", please enter a valid " + mOperatorParams.getMinLength() + " to " + mOperatorParams.getMaxLength() + postfix + mOperatorParams.getParamName().trim();

                    return false;
                } else if (mOperatorParams.getMinLength() != 0 && mOperatorParams.getMaxLength() != 0 && mOperatorParams.getMinLength() != mOperatorParams.getMaxLength() && etOption.getText().length() > mOperatorParams.getMaxLength()) {
                    msgSpeak = "Invalid length of " + mOperatorParams.getParamName().trim() + ", please enter a valid " + mOperatorParams.getMinLength() + " to " + mOperatorParams.getMaxLength() + postfix + mOperatorParams.getParamName().trim();
                    return false;
                } else if (mOperatorParams.getMinLength() != 0 && mOperatorParams.getMaxLength() != 0 && mOperatorParams.getMinLength() == mOperatorParams.getMaxLength() && etOption.getText().length() != mOperatorParams.getMaxLength()) {
                    msgSpeak = "Invalid length of " + mOperatorParams.getParamName().trim() + ", please enter a valid " + mOperatorParams.getMaxLength() + postfix + mOperatorParams.getParamName().trim();

                    return false;
                } else if (mOperatorParams.getMinLength() != 0 && mOperatorParams.getMaxLength() == 0 && etOption.getText().length() != mOperatorParams.getMinLength()) {
                    msgSpeak = "Invalid length of " + mOperatorParams.getParamName().trim() + ", please enter a valid " + mOperatorParams.getMinLength() + postfix + mOperatorParams.getParamName().trim();

                    return false;
                } else if (mOperatorParams.getMinLength() == 0 && mOperatorParams.getMaxLength() != 0 && etOption.getText().length() != mOperatorParams.getMaxLength()) {
                    msgSpeak = "Invalid length of " + mOperatorParams.getParamName().trim() + ", please enter a valid " + mOperatorParams.getMaxLength() + postfix + mOperatorParams.getParamName().trim();

                    return false;
                } else if (mOperatorParams.getRegEx() != null && !mOperatorParams.getRegEx().isEmpty()) {

                    if (etOption.getText().toString().matches(mOperatorParams.getRegEx())) {
                        return true;
                    } else {
                        msgSpeak = "Please enter a valid " + mOperatorParams.getParamName().trim();
                        return false;
                    }
                }
            } else {
                return true;
            }
        }
        return true;
    }


    boolean checkContains(ArrayList<String> StartWithArray, String value) {
        boolean isPrefixAvailable = false;
        for (String prefix : StartWithArray) {
            if (value.startsWith(prefix)) {
                isPrefixAvailable = true;
                break;
            }
        }
        return isPrefixAvailable;
    }

    private boolean validateAmount() {
        double amount = 0;
        try {
            if (!etAmount.getText().toString().trim().isEmpty()) {
                amount = Double.parseDouble(etAmount.getText().toString().trim());
            }
        } catch (NumberFormatException nfe) {

        }

        if (etAmount.getText().toString().trim().isEmpty() || amount <= 0) {
            msgSpeak = "Please enter valid amount";
            etAmount.setError(msgSpeak);
            etAmount.requestFocus();
            playVoice();
            return false;
        } else if (exactness == 2 && billAmount != 0 && maxAmountLength != 0 && !(amount >= billAmount && amount <= maxAmountLength)) {
            msgSpeak = "Amount must be between " + Utility.INSTANCE.formatedAmountWithRupees(billAmount + "") + " to \u20B9 " + maxAmountLength;
            etAmount.setError(msgSpeak);
            etAmount.requestFocus();
            playVoice();
            return false;
        } else if (exactness == 2 && billAmount != 0 && maxAmountLength == 0 && amount < billAmount) {
            msgSpeak = "Amount can't be less then " + Utility.INSTANCE.formatedAmountWithRupees(billAmount + "");
            etAmount.setError(msgSpeak);
            etAmount.requestFocus();
            playVoice();
            return false;
        } else if (exactness == 3 && billAmount != 0 && minAmountLength != 0 && !(amount >= minAmountLength && amount <= billAmount)) {
            msgSpeak = "Amount must be between \u20B9 " + minAmountLength + " to " + Utility.INSTANCE.formatedAmountWithRupees(billAmount + "");
            etAmount.setError(msgSpeak);
            etAmount.requestFocus();
            playVoice();
            return false;
        } else if (exactness == 3 && billAmount != 0 && minAmountLength == 0 && amount > billAmount) {
            msgSpeak = "Amount can't be more then " + Utility.INSTANCE.formatedAmountWithRupees(billAmount + "");
            etAmount.setError(msgSpeak);
            etAmount.requestFocus();
            playVoice();
            return false;
        } else if (minAmountLength != 0 && amount < minAmountLength) {
            msgSpeak = "Amount can't be less then \u20B9 " + minAmountLength;
            etAmount.setError(msgSpeak);
            etAmount.requestFocus();
            playVoice();
            return false;
        } else if (maxAmountLength != 0 && amount > maxAmountLength) {
            msgSpeak = "Amount must be less then \u20B9 " + maxAmountLength;
            etAmount.setError(msgSpeak);
            etAmount.requestFocus();
            playVoice();
            return false;
        } else if (minAmountLength != 0 && maxAmountLength != 0 && !(amount >= minAmountLength && amount <= maxAmountLength)) {
            msgSpeak = "Amount must be between \u20B9 " + minAmountLength + " to \u20B9 " + maxAmountLength;
            etAmount.setError(msgSpeak);
            etAmount.requestFocus();
            playVoice();
            return false;
        } else {
            etAmount.setError(null);

        }
        return true;

    }


    void ViewPlan() {
        Intent browseIntent = new Intent(RechargeBillPaymentActivity.this, BrowsePlanScreenActivity.class);
        browseIntent.putExtra("OperatorSelectedId", "" + operatorSelectedId);
        browseIntent.putExtra("OperatorRefCircleID", "" + operatorRefCircleID);
        browseIntent.putExtra("DeviceId", "" + deviceId);
        browseIntent.putExtra("DeviceSerialNum", "" + deviceSerialNum);
        browseIntent.putExtra("IsPlanServiceUpdated", mLoginDataResponse.isPlanServiceUpdated());
        browseIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(browseIntent, INTENT_ROFFER);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_PASSWORD_EXPIRE && resultCode == INTENT_PASSWORD_EXPIRE) {
            getHLRLookUp();
        } else if (requestCode == INTENT_ADD_MONEY && resultCode == RESULT_OK) {
            if (data != null) {
                String txnId = data.getStringExtra("TXNID");
                String tid = data.getStringExtra("TID");
                String status = data.getStringExtra("Status");
                String orderid = data.getStringExtra("ORDERID");
                String banktxnid = data.getStringExtra("BANKTXNID");
                boolean isFromUpi = data.getBooleanExtra("isFromUpi", true);
                boolean isStatus = data.getBooleanExtra("cashfree", true);
                double rqstedAmt = data.getDoubleExtra("AMOUNT", 0);
                boolean isUpi = data.getBooleanExtra("ISUPI", false);
                if (balanceCheckResponse.isRechargeWithPG()) {
                    addMoneyRechargeStatusDialog(this, isFromUpi, tid, status);

                    // rechargeApi(tid, ApiFintechUtilMethods.INSTANCE.getLattitude, ApiFintechUtilMethods.INSTANCE.getLongitude, inputPinPass);
                } else {
                    addMoneyRechargeStatusDialog(this, txnId, rqstedAmt + "", isUpi);
                }

            } else {
                ApiFintechUtilMethods.INSTANCE.Error(this, getString(R.string.some_thing_error));
            }

        } else if (requestCode == INTENT_UPI && resultCode == RESULT_OK) {
            if (data != null) {
                boolean isFromUpi = true;
                String tid = upiTID;
                final String paymentResponse, txnId, status, txnRef, ApprovalRefNo, TrtxnRef, responseCode, bleTxId;
                paymentResponse = data.getStringExtra("response");
                txnId = data.getStringExtra("txnId");
                status = data.getStringExtra("Status");
                txnRef = data.getStringExtra("txnRef");
                ApprovalRefNo = data.getStringExtra("ApprovalRefNo");
                TrtxnRef = data.getStringExtra("TrtxnRef");
                responseCode = data.getStringExtra("responseCode");
                bleTxId = data.getStringExtra("bleTxId");
                if (balanceCheckResponse.isRechargeWithPG()) {
                    addMoneyRechargeStatusDialog(this, isFromUpi, tid, status);
                    // rechargeApi(tid, ApiFintechUtilMethods.INSTANCE.getLattitude, ApiFintechUtilMethods.INSTANCE.getLongitude, inputPinPass);
                } else {
                    addMoneyRechargeStatusDialog(this, txnId, requestedAmtPay + "", isUpi);
                }
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(this, getString(R.string.some_thing_error));
            }

        } else if (requestCode == INTENT_UPI_WEB) {
            if (data != null && resultCode == RESULT_OK) {
                boolean isFromUpi = true;
                String tid = upiTID;
                String paymentResponse = data.getStringExtra("response");
                String txnId = data.getStringExtra("txnId");
                String status = data.getStringExtra("Status");
                String txnRef = data.getStringExtra("txnRef");
                String ApprovalRefNo = data.getStringExtra("ApprovalRefNo");
                String TrtxnRef = data.getStringExtra("TrtxnRef");
                String responseCode = data.getStringExtra("responseCode");
                String bleTxId = data.getStringExtra("bleTxId");/*TrtxnRef*/
                if (balanceCheckResponse.isRechargeWithPG()) {
                    addMoneyRechargeStatusDialog(this, isFromUpi, tid, status);
                } else {
                    addMoneyRechargeStatusDialog(this, txnId, requestedAmtPay + "", isUpi);
                }
            }
        } else if (requestCode == INTENT_SELECT_ZONE && resultCode == RESULT_OK) {
            String circle = data.getExtras().getString("selectedCircleName");
            operatorRefCircleID = data.getExtras().getString("selectedCircleId");
            if (operatorSelectedId != 0 && operatorRefCircleID != null && !operatorRefCircleID.isEmpty()) {
                ViewPlan();

            } else {
                ApiFintechUtilMethods.INSTANCE.Error(this, "Please select Operator and Circle both");
            }


        } else if (requestCode == INTENT_SELECT_OPERATOR && resultCode == RESULT_OK) {
            OperatorList mOperatorList = data.getParcelableExtra("SelectedOperator");
            if (mOperatorList != null) {
                setIntentData(mOperatorList);

               /* if (fromId == 3) {
                    if (maxNumberLength != 0 && maxNumberLength == etNumber.getText().length() && mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isDTHInfoAutoCallPref)) {
                        DTHinfo();
                    }
                }*/
            }
        } else if (requestCode == INTENT_ROFFER && resultCode == RESULT_OK) {

            etAmount.setText(data.getStringExtra("AMOUNT"));
            try {
                desc.setVisibility(View.VISIBLE);
                desc.setText(data.getStringExtra("DESCRIPTION"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK && requestCode == INTENT_VIEW_PLAN) {
            etAmount.setText(data.getStringExtra("Amount"));
            desc.setVisibility(View.VISIBLE);
            etAmount.setError(null);
            desc.setText(data.getStringExtra("desc"));
        } else if (requestCode == INTENT_RECHARGE_SLIP) {
            if (resultCode == RESULT_OK) {
                refreshData(fromId);
                HitApi();
            } else {
                HitApi();
            }
        } else {
            if (mGetLocation != null) {
                mGetLocation.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void addMoneyRechargeStatusDialog(Activity context, boolean isFromUpi, String tid, String status) {
        if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
            return;
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, R.style.full_screen_withoutbg_dialog);
        alertDialogAddMoneyConfirm = dialogBuilder.create();
        alertDialogAddMoneyConfirm.setCancelable(true);
        // alertDialogAddMoneyConfirm.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater inflater = context.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_payment_confiorm, null);
        alertDialogAddMoneyConfirm.setView(dialogView);
        TextView serviceName = dialogView.findViewById(R.id.serviceName);
        serviceName.setText(from);
        View closeBtn = dialogView.findViewById(R.id.closeBtn);
        ImageView check1 = dialogView.findViewById(R.id.check1);
        ImageView check2 = dialogView.findViewById(R.id.check2);
        TextView paymentMsg = dialogView.findViewById(R.id.paymentMsg);
        TextView operatorTv = dialogView.findViewById(R.id.operator);
        TextView numberTv = dialogView.findViewById(R.id.number);
        TextView amountTv = dialogView.findViewById(R.id.amount);
        ImageView logoIv = dialogView.findViewById(R.id.logo);


        operatorTv.setText(operatorSelected + "");
        numberTv.setText(etNumber.getText().toString());
        amountTv.setText(context.getResources().getString(R.string.rupiya) + " " + etAmount.getText().toString());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context).load(ApplicationConstant.INSTANCE.baseIconUrl + Icon).apply(RequestOptions.circleCropTransform()).apply(RequestOptions.placeholderOf(R.drawable.placeholder_square)).into(logoIv);
        closeBtn.setOnClickListener(v -> {
            alertDialogAddMoneyConfirm.dismiss();

        });
        timeCounter = 0;
        callStatusCheckApi(isFromUpi, tid, status, check1, paymentMsg);
        alertDialogAddMoneyConfirm.show();

        //alertDialogAddMoneyConfirm.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    private void callStatusCheckApi(boolean isFromUpi, String tid, String status, ImageView check1, TextView paymentMsg) {
        if (currentPGId == 12) {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GatwayStatusCheckResponse> call = git.AllUPIStatusCheck(upiTID);
            call.enqueue(new Callback<GatwayStatusCheckResponse>() {
                @Override
                public void onResponse(Call<GatwayStatusCheckResponse> call, Response<GatwayStatusCheckResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        GatwayStatusCheckResponse apiData = response.body();
                        if (apiData.getStatuscode() == 1 && !apiData.getStatus().equalsIgnoreCase("0")) {
                            if (apiData.getStatus().equalsIgnoreCase("1")) {
                                if (isAllUpiDialogCanceled) {
                                    isAllUpiDialogCanceled = false;
                                    if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                                        alertDialogAddMoneyConfirm.dismiss();
                                    }
                                    ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(RechargeBillPaymentActivity.this, "TXN CANCEL", "Transaction cancelled by user");
                                } else if (timeCounter < 60) {
                                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                        timeCounter = timeCounter + 3;
                                        callStatusCheckApi(isFromUpi, tid, status, check1, paymentMsg);
                                    }, 3000);
                                } else {
                                    if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                                        alertDialogAddMoneyConfirm.dismiss();
                                    }
                                    ApiFintechUtilMethods.INSTANCE.Processing(RechargeBillPaymentActivity.this, "Your transaction under process, please wait 48 Hour to confirmation.");
                                }
                            } else if (apiData.getStatus().equalsIgnoreCase("2") ||
                                    apiData.getStatus().toLowerCase().equalsIgnoreCase("success")) {
                                check1.setImageResource(R.drawable.ic_check_mark_fill);
                                paymentMsg.setText("Payment successfully confirmed from the bank|wallet side");
                                paymentMsg.setTextColor(Color.BLACK);
                                rechargeApi(tid, ApiFintechUtilMethods.INSTANCE.getLattitude, ApiFintechUtilMethods.INSTANCE.getLongitude, inputPinPass);
                            } else {
                                if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                                    alertDialogAddMoneyConfirm.dismiss();
                                }
                                ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, apiData.getMsg() + "");
                            }
                        } else {
                            if (apiData.getStatuscode() == 1) {
                                if (isAllUpiDialogCanceled) {
                                    isAllUpiDialogCanceled = false;
                                    if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                                        alertDialogAddMoneyConfirm.dismiss();
                                    }
                                    ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(RechargeBillPaymentActivity.this, "TXN CANCEL", "Transaction cancelled by user");
                                } else if (timeCounter < 60) {
                                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                        timeCounter = timeCounter + 3;
                                        callStatusCheckApi(isFromUpi, tid, status, check1, paymentMsg);
                                    }, 3000);
                                } else {
                                    if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                                        alertDialogAddMoneyConfirm.dismiss();
                                    }
                                    ApiFintechUtilMethods.INSTANCE.Processing(RechargeBillPaymentActivity.this, "Your transaction under process, please wait 48 Hour to confirmation.");
                                }
                            } else if (apiData.getStatuscode() == 2) {
                                check1.setImageResource(R.drawable.ic_check_mark_fill);
                                paymentMsg.setText("Payment successfully confirmed from the bank|wallet side");
                                paymentMsg.setTextColor(Color.BLACK);
                                rechargeApi(tid, ApiFintechUtilMethods.INSTANCE.getLattitude, ApiFintechUtilMethods.INSTANCE.getLongitude, inputPinPass);

                            } else {
                                if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                                    alertDialogAddMoneyConfirm.dismiss();
                                }
                                ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, apiData.getMsg() + "");
                            }
                        }
                    } else {
                        ApiFintechUtilMethods.INSTANCE.apiErrorHandle(RechargeBillPaymentActivity.this, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GatwayStatusCheckResponse> call, Throwable t) {
                    if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                        alertDialogAddMoneyConfirm.dismiss();
                    }
                }
            });
        } else if (isFromUpi) {
            ApiFintechUtilMethods.INSTANCE.UPIPaymentUpdate(RechargeBillPaymentActivity.this, tid, status, null, mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
                @Override
                public void onSucess(Object object) {
                    InitiateUpiResponse response = (InitiateUpiResponse) object;
                    if (response.getStatuscode() == 1 && response.getStatus() > 0) {
                        if (response.getStatus() == 1) {
                            if (timeCounter < 60) {
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    timeCounter = timeCounter + 3;
                                    callStatusCheckApi(isFromUpi, tid, status, check1, paymentMsg);
                                }, 3000);
                            } else {
                                if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                                    alertDialogAddMoneyConfirm.dismiss();
                                }
                                ApiFintechUtilMethods.INSTANCE.Processing(RechargeBillPaymentActivity.this, "Your transaction under process, please wait 48 Hour to confirmation.");
                            }
                        } else if (response.getStatus() == 2) {
                            check1.setImageResource(R.drawable.ic_check_mark_fill);
                            paymentMsg.setText("Payment successfully confirmed from the bank|wallet side");
                            paymentMsg.setTextColor(Color.BLACK);
                            rechargeApi(tid, ApiFintechUtilMethods.INSTANCE.getLattitude, ApiFintechUtilMethods.INSTANCE.getLongitude, inputPinPass);
                        } else {
                            if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                                alertDialogAddMoneyConfirm.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, response.getMsg() + "");
                        }
                    } else {
                        if (response.getStatuscode() == 1) {

                            if (timeCounter < 60) {
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    timeCounter = timeCounter + 3;
                                    callStatusCheckApi(isFromUpi, tid, status, check1, paymentMsg);
                                }, 3000);
                            } else {
                                if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                                    alertDialogAddMoneyConfirm.dismiss();
                                }
                                ApiFintechUtilMethods.INSTANCE.Processing(RechargeBillPaymentActivity.this, "Your transaction under process, please wait 48 Hour to confirmation.");
                            }
                        } else if (response.getStatuscode() == 2) {
                            check1.setImageResource(R.drawable.ic_check_mark_fill);
                            paymentMsg.setText("Payment successfully confirmed from the bank|wallet side");
                            paymentMsg.setTextColor(Color.BLACK);
                            rechargeApi(tid, ApiFintechUtilMethods.INSTANCE.getLattitude, ApiFintechUtilMethods.INSTANCE.getLongitude, inputPinPass);

                        } else {
                            if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                                alertDialogAddMoneyConfirm.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, response.getMsg() + "");
                        }
                    }

                }

                @Override
                public void onError(int error) {
                    if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                        alertDialogAddMoneyConfirm.dismiss();
                    }
                }
            });
        } else {
            ApiFintechUtilMethods.INSTANCE.CashFreeStatusCheck(this, tid, loader, new ApiFintechUtilMethods.ApiResponseCallBack() {
                @Override
                public void onSucess(Object object) {
                    InitiateUpiResponse response = (InitiateUpiResponse) object;
                    if (response.getStatus() > 0) {
                        if (response.getStatus() == 1) {
                            if (timeCounter < 60) {
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    timeCounter = timeCounter + 3;
                                    callStatusCheckApi(isFromUpi, tid, status, check1, paymentMsg);
                                }, 3000);
                            } else {
                                if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                                    alertDialogAddMoneyConfirm.dismiss();
                                }
                                ApiFintechUtilMethods.INSTANCE.Processing(RechargeBillPaymentActivity.this, "Your transaction under process, please wait 48 Hour to confirmation.");
                            }
                        } else if (response.getStatus() == 2) {
                            check1.setImageResource(R.drawable.ic_check_mark_fill);
                            paymentMsg.setText("Payment successfully confirmed from the bank|wallet side");
                            paymentMsg.setTextColor(Color.BLACK);
                            rechargeApi(tid, ApiFintechUtilMethods.INSTANCE.getLattitude, ApiFintechUtilMethods.INSTANCE.getLongitude, inputPinPass);
                        } else {
                            if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                                alertDialogAddMoneyConfirm.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, response.getMsg() + "");
                        }
                    } else {
                        if (response.getStatuscode() == 1) {

                            if (timeCounter < 60) {
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    timeCounter = timeCounter + 3;
                                    callStatusCheckApi(isFromUpi, tid, status, check1, paymentMsg);
                                }, 3000);
                            } else {
                                if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                                    alertDialogAddMoneyConfirm.dismiss();
                                }
                                ApiFintechUtilMethods.INSTANCE.Processing(RechargeBillPaymentActivity.this, "Your transaction under process, please wait 48 Hour to confirmation.");
                            }
                        } else if (response.getStatuscode() == 2) {
                            check1.setImageResource(R.drawable.ic_check_mark_fill);
                            paymentMsg.setText("Payment successfully confirmed from the bank|wallet side");
                            paymentMsg.setTextColor(Color.BLACK);
                            rechargeApi(tid, ApiFintechUtilMethods.INSTANCE.getLattitude, ApiFintechUtilMethods.INSTANCE.getLongitude, inputPinPass);

                        } else {
                            if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                                alertDialogAddMoneyConfirm.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, response.getMsg() + "");
                        }
                    }
                }

                @Override
                public void onError(int error) {
                    if (alertDialogAddMoneyConfirm != null && alertDialogAddMoneyConfirm.isShowing()) {
                        alertDialogAddMoneyConfirm.dismiss();
                    }
                }
            });

        }
    }

    public void addMoneyRechargeStatusDialog(Activity context, String txnId, String requestedAmt, boolean isUpi) {
        if (alertDialog != null && alertDialog.isShowing()) {
            return;
        }
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(context, R.style.full_screen_bg_dialog);

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        // alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater inflater = context.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_money_add_recharge_status, null);
        alertDialog.setView(dialogView);
        TextView balanceTv = dialogView.findViewById(R.id.balance);
        TextView requestedAmtTv = dialogView.findViewById(R.id.requestedAmt);
        balanceTv.setText("\u20B9 " + currentBalance);
        requestedAmtTv.setText("\u20B9 " + requestedAmt);
        TextView statusErrorTv = dialogView.findViewById(R.id.statusMsg);
        TextView statusMsg1Tv = dialogView.findViewById(R.id.statusMsg1Tv);
        TextView statusMsg2Tv = dialogView.findViewById(R.id.statusMsg2Tv);
        TextView num2Tv = dialogView.findViewById(R.id.num2Tv);
        ImageView status1Iv = dialogView.findViewById(R.id.status1Iv);
        ImageView status2Iv = dialogView.findViewById(R.id.status2Iv);
        LinearLayout line = dialogView.findViewById(R.id.line);
        LinearLayout detailView = dialogView.findViewById(R.id.detailView);
        LinearLayout detailTxnView = dialogView.findViewById(R.id.detailTxnView);
        LinearLayout tidView = dialogView.findViewById(R.id.tidView);
        LinearLayout liveIdView = dialogView.findViewById(R.id.liveIdView);
        TextView txIdTv = dialogView.findViewById(R.id.tv_txid);
        TextView liveIdTv = dialogView.findViewById(R.id.tv_liveId);
        TextView operatorTv = dialogView.findViewById(R.id.operator);
        TextView numberTv = dialogView.findViewById(R.id.number);
        TextView amountTv = dialogView.findViewById(R.id.amount);
        ImageView logoIv = dialogView.findViewById(R.id.logo);
        ImageView bill_logo = dialogView.findViewById(R.id.bill_logo);
        TextView closeBtn = dialogView.findViewById(R.id.closeBtn);
        TextView receiptBtn = dialogView.findViewById(R.id.receipt);
        TextView enableBtn = dialogView.findViewById(R.id.enable);
        LinearLayout powerdByView = dialogView.findViewById(R.id.powerdByView);
        if (isUpi) {
            powerdByView.setVisibility(View.VISIBLE);
        }
        TextView warningmsg = dialogView.findViewById(R.id.warningmsg);
        operatorTv.setText(operatorSelected + "");
        numberTv.setText(etNumber.getText().toString());
        amountTv.setText(context.getResources().getString(R.string.rupiya) + " " + etAmount.getText().toString());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context).load(ApplicationConstant.INSTANCE.baseIconUrl + Icon).apply(RequestOptions.circleCropTransform()).apply(RequestOptions.placeholderOf(R.drawable.placeholder_square)).into(logoIv);
        timing = 0;
        if (balanceHandler != null && balanceRunnable != null) {
            balanceHandler.removeCallbacks(balanceRunnable);
        }
        checkBalance(new CheckBalanceCallback() {
            @Override
            public void Sucsess(BalanceResponse mBalanceResponse) {
                balanceTv.setText("\u20B9 " + mBalanceResponse.getBalanceData().get(0).getBalance());
                status1Iv.setImageResource(R.drawable.ic_check_mark);
                ViewCompat.setBackgroundTintList(line, ContextCompat.getColorStateList(RechargeBillPaymentActivity.this, R.color.green));
                statusErrorTv.setVisibility(View.GONE);
                statusMsg1Tv.setTextColor(getResources().getColor(R.color.green));
                statusMsg1Tv.setText("Your requested amount is sucessfully added in your wallet.");
                num2Tv.setVisibility(View.GONE);
                status2Iv.setVisibility(View.VISIBLE);
                statusMsg2Tv.setTextColor(getResources().getColor(R.color.greycon));
                if (isBBPS) {
                    bill_logo.setVisibility(View.VISIBLE);
                } else {
                    bill_logo.setVisibility(View.GONE);
                }

                rechargeApi(null, ApiFintechUtilMethods.INSTANCE.getLattitude, ApiFintechUtilMethods.INSTANCE.getLongitude, inputPinPass, true, new RechargeCallback() {
                    @Override
                    public void Sucsess(RechargeResponse mRechargeResponse, String Amount, String AccountNo, String opName, int OpId) {

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("Response", mRechargeResponse);
                        map.put("Amount", Amount);
                        map.put("AccountNo", AccountNo);
                        map.put("OpName", opName);
                        map.put("Opid", OpId);
                        receiptBtn.setTag(map);
                        if (mRechargeResponse.getStatuscode() == 1) {

                            //Pending
                            closeBtn.setVisibility(View.VISIBLE);
                            receiptBtn.setVisibility(View.VISIBLE);
                            enableBtn.setVisibility(View.GONE);
                            statusMsg2Tv.setTextColor(getResources().getColor(R.color.color_orange));
                            warningmsg.setVisibility(View.GONE);
                            if (isRecharge) {
                                statusErrorTv.setText("Recharge request is pending");
                                statusMsg2Tv.setText("Your recharge request has been accepted, we are awaiting response from operator");
                            } else {
                                statusErrorTv.setText("Bill Payment request is pending");
                                statusMsg2Tv.setText("Your bill payment request has been accepted, we are awaiting response from operator");
                            }
                            statusErrorTv.setVisibility(View.VISIBLE);
                            statusErrorTv.setTextColor(getResources().getColor(R.color.color_orange));

                            if (mRechargeResponse.getTransactionID() != null && !mRechargeResponse.getTransactionID().isEmpty()) {
                                detailTxnView.setVisibility(View.VISIBLE);
                                tidView.setVisibility(View.VISIBLE);
                                txIdTv.setText(mRechargeResponse.getTransactionID());
                            }
                            if (mRechargeResponse.getLiveID() != null && !mRechargeResponse.getLiveID().isEmpty()) {
                                detailTxnView.setVisibility(View.VISIBLE);
                                liveIdView.setVisibility(View.VISIBLE);
                                liveIdTv.setText(mRechargeResponse.getLiveID());
                            }


                        } else if (mRechargeResponse.getStatuscode() == 2) {
                            //Sucess
                            closeBtn.setVisibility(View.VISIBLE);
                            receiptBtn.setVisibility(View.VISIBLE);
                            enableBtn.setVisibility(View.GONE);
                            statusMsg2Tv.setTextColor(getResources().getColor(R.color.green));
                            status2Iv.setImageResource(R.drawable.ic_check_mark);
                            warningmsg.setVisibility(View.GONE);
                            if (isRecharge) {
                                statusMsg2Tv.setText("Your recharge processed successfully");
                                statusErrorTv.setText("Successfully Recharge");
                            } else {
                                statusMsg2Tv.setText("Your bill payment processed successfully");
                                statusErrorTv.setText("Successfully Bill Paid");
                            }
                            statusErrorTv.setVisibility(View.VISIBLE);
                            statusErrorTv.setTextColor(getResources().getColor(R.color.green));

                            if (mRechargeResponse.getTransactionID() != null && !mRechargeResponse.getTransactionID().isEmpty()) {
                                detailTxnView.setVisibility(View.VISIBLE);
                                tidView.setVisibility(View.VISIBLE);
                                txIdTv.setText(mRechargeResponse.getTransactionID());
                            }
                            if (mRechargeResponse.getLiveID() != null && !mRechargeResponse.getLiveID().isEmpty()) {
                                detailTxnView.setVisibility(View.VISIBLE);
                                liveIdView.setVisibility(View.VISIBLE);
                                liveIdTv.setText(mRechargeResponse.getLiveID());
                            }

                        } else if (mRechargeResponse.getStatuscode() == 3) {

                            //Failed
                            if (mRechargeResponse.getMsg().contains("(NRAF-0)")) {
                                closeBtn.setVisibility(View.VISIBLE);
                                receiptBtn.setVisibility(View.GONE);
                                enableBtn.setVisibility(View.VISIBLE);
                                String dialogMsgTxt;
                                if (isRecharge) {
                                    statusMsg2Tv.setText("Your recharge request can't be proceed");
                                    dialogMsgTxt = context.getResources().getString(R.string.realapi_popup_english_recharge_msg, opName, opName) + "\n\n" + context.getResources().getString(R.string.realapi_popup_hindi_recharge_msg, opName);
                                } else {
                                    statusMsg2Tv.setText("Your bill payment request can't be proceed");
                                    dialogMsgTxt = context.getResources().getString(R.string.realapi_popup_english_billpayment_msg, opName, opName) + "\n\n" + context.getResources().getString(R.string.realapi_popup_hindi_billpayment_msg, opName);
                                }
                                enableBtn.setTag(dialogMsgTxt);
                                statusMsg2Tv.setTextColor(getResources().getColor(R.color.color_red));
                                status2Iv.setImageResource(R.drawable.ic_cross_mark);
                                warningmsg.setVisibility(View.GONE);

                                statusErrorTv.setText(dialogMsgTxt);
                                statusErrorTv.setVisibility(View.VISIBLE);
                                statusErrorTv.setTextColor(getResources().getColor(R.color.color_red));

                            } else {
                                closeBtn.setVisibility(View.VISIBLE);
                                receiptBtn.setVisibility(View.VISIBLE);
                                enableBtn.setVisibility(View.GONE);
                                statusMsg2Tv.setTextColor(getResources().getColor(R.color.color_red));
                                status2Iv.setImageResource(R.drawable.ic_cross_mark);
                                warningmsg.setVisibility(View.GONE);
                                if (mRechargeResponse.getMsg() != null && !mRechargeResponse.getMsg().isEmpty()) {
                                    statusMsg2Tv.setText(mRechargeResponse.getMsg());
                                } else {
                                    statusMsg2Tv.setText("You request can't be proceed, please try after some time");
                                }
                                if (isRecharge) {
                                    statusErrorTv.setText("Recharge Failed");
                                } else {
                                    statusErrorTv.setText("Bill Payment Failed");
                                }
                                statusErrorTv.setVisibility(View.VISIBLE);
                                statusErrorTv.setTextColor(getResources().getColor(R.color.color_red));

                                if (mRechargeResponse.getTransactionID() != null && !mRechargeResponse.getTransactionID().isEmpty()) {
                                    detailTxnView.setVisibility(View.VISIBLE);
                                    tidView.setVisibility(View.VISIBLE);
                                    txIdTv.setText(mRechargeResponse.getTransactionID());
                                }
                                if (mRechargeResponse.getLiveID() != null && !mRechargeResponse.getLiveID().isEmpty()) {
                                    detailTxnView.setVisibility(View.VISIBLE);
                                    liveIdView.setVisibility(View.VISIBLE);
                                    liveIdTv.setText(mRechargeResponse.getLiveID());
                                }
                            }


                        }
                    }


                    @Override
                    public void Process(Object object) {
                        closeBtn.setVisibility(View.VISIBLE);
                        receiptBtn.setVisibility(View.GONE);
                        enableBtn.setVisibility(View.GONE);
                        statusMsg2Tv.setTextColor(getResources().getColor(R.color.color_orange));
                        status2Iv.setImageResource(R.drawable.ic_pending_outline);
                        warningmsg.setVisibility(View.GONE);

                        statusMsg2Tv.setText(object + "");

                        if (isRecharge) {
                            statusErrorTv.setText("Recharge Under Process");
                        } else {
                            statusErrorTv.setText("Bill Payment Under Process");
                        }
                        statusErrorTv.setVisibility(View.VISIBLE);
                        statusErrorTv.setTextColor(getResources().getColor(R.color.color_orange));

                    }

                    @Override
                    public void Error(Object object) {
                        closeBtn.setVisibility(View.VISIBLE);
                        receiptBtn.setVisibility(View.GONE);
                        enableBtn.setVisibility(View.GONE);
                        statusMsg2Tv.setTextColor(getResources().getColor(R.color.color_red));
                        status2Iv.setImageResource(R.drawable.ic_cross_mark);
                        warningmsg.setVisibility(View.GONE);

                        statusMsg2Tv.setText(object + "");

                        if (isRecharge) {
                            statusErrorTv.setText("Recharge Failed");
                        } else {
                            statusErrorTv.setText("Bill Payment Failed");
                        }
                        statusErrorTv.setVisibility(View.VISIBLE);
                        statusErrorTv.setTextColor(getResources().getColor(R.color.color_red));
                    }

                    @Override
                    public void NetworkError(Object object) {
                        closeBtn.setVisibility(View.VISIBLE);
                        receiptBtn.setVisibility(View.GONE);
                        enableBtn.setVisibility(View.GONE);
                        statusMsg2Tv.setTextColor(getResources().getColor(R.color.color_red));
                        status2Iv.setImageResource(R.drawable.ic_cross_mark);
                        warningmsg.setVisibility(View.GONE);

                        statusMsg2Tv.setText(object + "");

                        if (isRecharge) {
                            statusErrorTv.setText("Recharge Failed");
                        } else {
                            statusErrorTv.setText("Bill Payment Failed");
                        }
                        statusErrorTv.setVisibility(View.VISIBLE);
                        statusErrorTv.setTextColor(getResources().getColor(R.color.color_red));
                    }
                });
            }

            @Override
            public void Pending(BalanceResponse mBalanceResponse) {
                warningmsg.setVisibility(View.GONE);
                closeBtn.setVisibility(View.VISIBLE);

                ViewCompat.setBackgroundTintList(line, ContextCompat.getColorStateList(RechargeBillPaymentActivity.this, R.color.grey));
                statusMsg1Tv.setText("Please try after some time, we are working on your request");
                statusMsg1Tv.setTextColor(getResources().getColor(R.color.color_orange));
                statusErrorTv.setVisibility(View.VISIBLE);
                statusErrorTv.setTextColor(getResources().getColor(R.color.color_orange));
                statusErrorTv.setText("Your order " + txnId + " for amount \u20B9 " + requestedAmt + " is awaited from bank side we will update once get response from bank side.");

            }

            @Override
            public void Error(Object object) {
                if (object instanceof BalanceResponse) {

                    statusMsg1Tv.setText(((BalanceResponse) object).getMsg() + "");
                } else {
                    statusMsg1Tv.setText(((String) object) + "");
                }
                status1Iv.setImageResource(R.drawable.ic_cross_mark);
                warningmsg.setVisibility(View.GONE);
                closeBtn.setVisibility(View.VISIBLE);
                ViewCompat.setBackgroundTintList(line, ContextCompat.getColorStateList(RechargeBillPaymentActivity.this, R.color.grey));
                statusErrorTv.setVisibility(View.VISIBLE);
                statusErrorTv.setTextColor(getResources().getColor(R.color.color_red));
                statusErrorTv.setText("Sorry, we are unable to process this time, but your request has been submitted, we are working on it");
                statusMsg1Tv.setTextColor(getResources().getColor(R.color.color_red));
            }
        });
        closeBtn.setOnClickListener(v -> {
            alertDialog.dismiss();

        });

        enableBtn.setOnClickListener(v -> {
            new CustomAlertDialog(context).enableRealApiDialog(enableBtn.getTag() + "", (mobile, emailId) -> ApiFintechUtilMethods.INSTANCE.EnableDisableRealApi(context, true, loader, mLoginDataResponse, deviceId, deviceSerialNum, object -> ApiFintechUtilMethods.INSTANCE.Successful(context, ((BasicResponse) object).getMsg() + "")));
            alertDialog.dismiss();

        });

        receiptBtn.setOnClickListener(view -> {
            HashMap<String, Object> map = (HashMap<String, Object>) receiptBtn.getTag();

            if (map != null && map.size() > 0) {
                RechargeResponse mRechargeResponse = (RechargeResponse) map.get("Response");
                if (mRechargeResponse != null) {
                    if (mRechargeResponse.getStatuscode() == 1) {
                        ApiFintechUtilMethods.INSTANCE.openRechargeSlip(context, isBBPS, map.get("Amount") + "", map.get("AccountNo") + "", mRechargeResponse.getLiveID(), map.get("OpName") + "", mRechargeResponse.getTransactionID(), "PENDING", (int) map.get("Opid"), mRechargeResponse.getStatuscode(), "", "", "", false, 0);

                    } else if (mRechargeResponse.getStatuscode() == 2) {
                        ApiFintechUtilMethods.INSTANCE.openRechargeSlip(context, isBBPS, map.get("Amount") + "", map.get("AccountNo") + "", mRechargeResponse.getLiveID(), map.get("OpName") + "", mRechargeResponse.getTransactionID(), "SUCCESS", (int) map.get("Opid"), mRechargeResponse.getStatuscode(), "", "", "", false, 0);

                    } else if (mRechargeResponse.getStatuscode() == 3) {


                        ApiFintechUtilMethods.INSTANCE.openRechargeSlip(context, isBBPS, map.get("Amount") + "", map.get("AccountNo") + "", mRechargeResponse.getLiveID(), map.get("OpName") + "", mRechargeResponse.getTransactionID(), "FAILED", (int) map.get("Opid"), mRechargeResponse.getStatuscode(), "", "", "", true, INTENT_RECHARGE_SLIP);
                    }
                } else {
                    ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, getString(R.string.some_thing_error));
                }
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, getString(R.string.some_thing_error));
            }
        });

        alertDialog.show();
        /*if (isFullScreen) {
            alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }*/
    }


    void checkBalance(CheckBalanceCallback checkBalanceCallback) {
        ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse, null, null, mAppPreferences, null, new ApiFintechUtilMethods.ApiCallBack() {
            @Override
            public void onSucess(Object object) {
                balanceCheckResponse = (BalanceResponse) object;
                if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                    if (balanceCheckResponse.getBalanceData().get(0).getBalance() > currentBalance) {
                        currentBalance = balanceCheckResponse.getBalanceData().get(0).getBalance();
                        if (checkBalanceCallback != null) {
                            checkBalanceCallback.Sucsess(balanceCheckResponse);
                        }

                        if (balanceHandler != null && balanceRunnable != null) {
                            balanceHandler.removeCallbacks(balanceRunnable);
                        }
                    } else {
                        if (timing <= 120000) {
                            //2 min
                            timing = timing + 5000;

                            if (balanceHandler != null && balanceRunnable != null) {
                                balanceHandler.postDelayed(balanceRunnable, 5000);
                            } else {
                                balanceRunnable = () -> checkBalance(checkBalanceCallback);
                                balanceHandler = new Handler(Looper.myLooper());
                                balanceHandler.postDelayed(balanceRunnable, 5000);
                            }
                        } else {
                            if (checkBalanceCallback != null) {

                                checkBalanceCallback.Pending(balanceCheckResponse);
                            }
                            if (balanceHandler != null && balanceRunnable != null) {
                                balanceHandler.removeCallbacks(balanceRunnable);
                            }
                        }
                    }

                }
            }

        });
    }

    interface CheckBalanceCallback {
        void Sucsess(BalanceResponse mBalanceResponse);

        void Pending(BalanceResponse mBalanceResponse);

        void Error(Object object);
    }

    interface RechargeCallback {
        void Sucsess(RechargeResponse mRechargeResponse, String Amount, String AcountNo, String opName, int OpId);

        void Process(Object object);

        void Error(Object object);

        void NetworkError(Object object);
    }


    @Override
    protected void onPause() {
        if (mGetLocation != null) {
            mGetLocation.onPause();
        }
        super.onPause();
    }


    void setIntentData(OperatorList mOperatorList) {


        operatorSelected = mOperatorList.getName();
        operatorSelectedId = mOperatorList.getOid();
        operatorDocName = mOperatorList.getPlanDocName();
        minAmountLength = mOperatorList.getMin();
        maxAmountLength = mOperatorList.getMax();
        minNumberLength = mOperatorList.getLength();
        maxNumberLength = mOperatorList.getLengthMax();
        isAcountNumeric = mOperatorList.getIsAccountNumeric();
        isTakeCustomerNum = mOperatorList.isTakeCustomerNum();

        isPartial = mOperatorList.getIsPartial();
        isBBPS = mOperatorList.getBBPS();
        isBilling = mOperatorList.getIsBilling();
        AccountName = mOperatorList.getAccountName();
        Account_Remark = mOperatorList.getAccountRemak();
        regularExpress = mOperatorList.getRegExAccount();
        StartWith = mOperatorList.getStartWith();
        Icon = mOperatorList.getImage();

        StartWithArray.clear();

        if (StartWith != null && StartWith.length() > 0 && StartWith.contains(",")) {
            StartWithArray = new ArrayList<>(Arrays.asList(StartWith.trim().split(",")));
        } else if (StartWith != null && !StartWith.isEmpty() && !StartWith.equalsIgnoreCase("0")) {
            StartWithArray.add(StartWith);
        }

        tvOperator.setText(operatorSelected + "");
        tvOperator.setError(null);
        if (Icon != null && !Icon.isEmpty()) {
            Glide.with(this).load(ApplicationConstant.INSTANCE.baseIconUrl + Icon).apply(requestOptions).into(ivOprator);
        } else {
            ivOprator.setImageResource(R.drawable.ic_tower);
        }

        if (isAcountNumeric == true) {
            etNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            etNumber.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        if (maxNumberLength != 0 && maxNumberLength > 0) {
            InputFilter[] filterArray = new InputFilter[1];
            filterArray[0] = new InputFilter.LengthFilter(maxNumberLength);
            etNumber.setFilters(filterArray);
        }


        if (!AccountName.equals("")) {
            etNumber.setHint("Enter " + AccountName);
        }
        if (!Account_Remark.equals("")) {
            AcountRemarkTv.setVisibility(View.VISIBLE);
            AcountRemarkTv.setText(Account_Remark.trim());
        } else {
            AcountRemarkTv.setVisibility(View.GONE);
        }
        if (isBilling) {
            isFetchBill = true;
            btRecharge.setText("Fetch Bill");
            rlEtAmount.setVisibility(View.GONE);
            /*if (isPartial) {
                rlEtAmount.setVisibility(View.VISIBLE);
            } else {
                rlEtAmount.setVisibility(View.GONE);
            }*/
        } else {
            isFetchBill = false;
            rlEtAmount.setVisibility(View.VISIBLE);
        }

        /*if (operatorListResponse != null && operatorListResponse.isTakeCustomerNo() && isTakeCustomerNum) {
            rlCustNumber.setVisibility(View.VISIBLE);
        } else {
            rlCustNumber.setVisibility(View.GONE);
        }*/

        if (isBBPS) {
            billLogo.setVisibility(View.VISIBLE);
            BBPSApi();
        } else {
            loader.dismiss();
            if (operatorListResponse != null && operatorListResponse.isTakeCustomerNo() && isTakeCustomerNum) {
                rlCustNumber.setVisibility(View.VISIBLE);
            } else {
                rlCustNumber.setVisibility(View.GONE);
            }
            billLogo.setVisibility(View.GONE);
        }


        if (isPartial) {
            etAmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else {
            etAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        if (switchView.getVisibility() == View.VISIBLE) {
            if (operatorSelectedId != 0) {
                getLapuRealCommission();
            } else {
                lapuSwitch.setText("Lapu");
                realApiSwitch.setText("Recharge With Real");
            }
        }

        if (operatorSelectedId != 0 && switchView.getVisibility() == View.VISIBLE) {
            getLapuRealCommission();
        }

        if (cashBackTv.getVisibility() == View.VISIBLE) {
            HitIncentiveApi(false);
        }
        checkOnBoarding();

    }


    void checkOnBoarding() {
        if (isBBPS) {

            isOnboardingSuccess = false;
            mOnboardingResponse = null;
            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

                ApiFintechUtilMethods.INSTANCE.CallOnboarding(this, 0, false, getSupportFragmentManager(), operatorSelectedId, "", "0", "", false, true, false, null, null, null, loader, mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiCallBackTwoMethod() {
                    @Override
                    public void onSucess(Object object) {
                        if (object != null) {
                            mOnboardingResponse = (OnboardingResponse) object;
                            if (mOnboardingResponse != null) {
                                isOnboardingSuccess = true;
                            }
                        }
                    }

                    @Override
                    public void onError(Object object) {
                        mOnboardingResponse = (OnboardingResponse) object;
                        isOnboardingSuccess = false;
                    }
                });


            } else {
                isOnboardingSuccess = false;
                ApiFintechUtilMethods.INSTANCE.NetworkError(this);
            }

        } else {
            isOnboardingSuccess = true;
        }
    }


    public void HeavyRefresh() {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<DthPlanInfoResponse> call;
            if (mLoginDataResponse.isPlanServiceUpdated()) {
                call = git.GetRNPDTHHeavyRefresh(new HeavyrefreshRequest(operatorSelectedId + "", etNumber.getText().toString(), ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum));
            } else {
                call = git.DTHHeavyRefresh(new HeavyrefreshRequest(operatorSelectedId + "", etNumber.getText().toString(), ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum));
            }

            call.enqueue(new Callback<DthPlanInfoResponse>() {
                @Override
                public void onResponse(Call<DthPlanInfoResponse> call, Response<DthPlanInfoResponse> response) {
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {

                                    if (response.body().getDataRP() != null && response.body().getDataRP().getRecords() != null) {
                                        if (response.body().getDataRP().getRecords().getDesc() != null) {
                                            ApiFintechUtilMethods.INSTANCE.Successful(RechargeBillPaymentActivity.this, "" + response.body().getDataRP().getRecords().getDesc() + "");
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, "No Record Found");
                                        }
                                    } else if (response.body().getDthhrData() != null) {
                                        if (response.body().getDthhrData().getStatusCode() == 1) {
                                            if (response.body().getDthhrData().getResponse() != null) {
                                                ApiFintechUtilMethods.INSTANCE.Successful(RechargeBillPaymentActivity.this, response.body().getDthhrData().getResponse() + "");
                                            } else {
                                                ApiFintechUtilMethods.INSTANCE.Successful(RechargeBillPaymentActivity.this, "Dear Customer , HR request is captured , Please Ensure your STB is Switched on");
                                            }
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, "No Record Found");
                                        }
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, "No Record Found");
                                    }
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, "No Record Found");
                                }
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(RechargeBillPaymentActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DthPlanInfoResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    try {

                        ApiFintechUtilMethods.INSTANCE.apiFailureError(RechargeBillPaymentActivity.this, t);


                    } catch (IllegalStateException ise) {
                        ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, ise.getMessage());

                    }
                }
            });
        } catch (Exception e) {
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, e.getMessage() + "");
        }
    }

    public void DTHinfo() {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            ROfferRequest request = new ROfferRequest(operatorSelectedId + "", etNumber.getText().toString().trim(), ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum);
            Call<DTHInfoResponse> call;
            if (mLoginDataResponse.isPlanServiceUpdated()) {
                call = git.GetRNPDTHCustInfo(request);
            } else {
                call = git.DTHCustomerInfo(request);
            }

            call.enqueue(new Callback<DTHInfoResponse>() {
                @Override
                public void onResponse(Call<DTHInfoResponse> call, final retrofit2.Response<DTHInfoResponse> response) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (response.body() != null) {
                                        if (response.body().getData() != null && response.body().getData().getRecords() != null && response.body().getData().getRecords().size() > 0) {
                                            parseDthInfoData(response.body().getData());

                                        } else if (response.body().getDataPA() != null && response.body().getDataPA().getStatus() == 0 && response.body().getDataPA().getData() != null) {
                                            parseDthInfoData(response.body().getDataPA());
                                        } else if (response.body().getDthciData() != null && response.body().getDthciData().getStatusCode() == 1) {
                                            parseDthInfoData(response.body().getDthciData());
                                        } else {
                                            rlDthInfoDetail.setVisibility(View.GONE);
                                        }
                                    } else {
                                        rlDthInfoDetail.setVisibility(View.GONE);
                                        ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, "DTH Info not found, Please try after some time.");
                                    }

                                } else {
                                    rlDthInfoDetail.setVisibility(View.GONE);
                                    ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, response.body().getMsg() + "");
                                }

                            } else {
                                rlDthInfoDetail.setVisibility(View.GONE);
                                ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, "Something went wrong, try after some time.");
                            }
                        } else {
                            rlDthInfoDetail.setVisibility(View.GONE);
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(RechargeBillPaymentActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        rlDthInfoDetail.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<DTHInfoResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    rlDthInfoDetail.setVisibility(View.GONE);
                    try {

                        ApiFintechUtilMethods.INSTANCE.apiFailureError(RechargeBillPaymentActivity.this, t);


                    } catch (IllegalStateException ise) {
                        ApiFintechUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            rlDthInfoDetail.setVisibility(View.GONE);
        }
    }

    private void parseDthInfoData(DTHInfoRecords responsePlan) {

        if (responsePlan != null) {
            mDthInfoRecords = responsePlan;
        }

        if (mDthInfoRecords != null) {
            rlDthInfoDetail.setVisibility(View.VISIBLE);

            Glide.with(this).load(ApplicationConstant.INSTANCE.baseIconUrl + Icon).apply(requestOptions).into(operatorIcon);

            if (responsePlan.getAccountNo() != null && !responsePlan.getAccountNo().isEmpty()) {
                tel.setText(/*AccountName.trim() + " : " +*/ (responsePlan.getAccountNo() + "").trim());
            } else {
                tel.setText(/*AccountName.trim() + " : " +*/ etNumber.getText().toString() + "");
            }

            operator.setText(operatorSelected.trim() + "");
            if (mDthInfoRecords.getData() != null && mDthInfoRecords.getData().size() > 0) {

                ArrayList<DTHInfoRecords> mList = new ArrayList<>();
                for (DTHInfoRecords mItem : mDthInfoRecords.getData()) {
                    if (mItem.getKey() != null && !mItem.getKey().isEmpty() && mItem.getValue() != null && !mItem.getValue().isEmpty()) {
                        mList.add(mItem);
                    }
                }
                if (mList != null && mList.size() > 0) {
                    custInfoRecyclerView.setVisibility(View.VISIBLE);
                    custInfoRecyclerView.setAdapter(new DthCustInfoAdapter(mList, this));
                } else {
                    custInfoRecyclerView.setVisibility(View.GONE);
                }
            } else {
                custInfoRecyclerView.setVisibility(View.GONE);
            }

            if (mDthInfoRecords.getCustomerName() != null && !mDthInfoRecords.getCustomerName().isEmpty()) {
                llCustomerLayout.setVisibility(View.VISIBLE);
                customerName.setText((mDthInfoRecords.getCustomerName() + "").trim());
            } else {
                llCustomerLayout.setVisibility(View.GONE);
            }

            if (mDthInfoRecords.getBalance() != null && !mDthInfoRecords.getBalance().isEmpty()) {
                llBalAmount.setVisibility(View.VISIBLE);
                Balance.setText(getResources().getString(R.string.rupiya) + " " + (mDthInfoRecords.getBalance() + "").trim());
            } else {
                llBalAmount.setVisibility(View.GONE);
            }

            if (mDthInfoRecords.getPlanname() != null && !mDthInfoRecords.getPlanname().isEmpty()) {
                llPlanName.setVisibility(View.VISIBLE);
                planname.setText((mDthInfoRecords.getPlanname() + "").trim());
            } else {
                llPlanName.setVisibility(View.GONE);
            }

            if (mDthInfoRecords.getNextRechargeDate() != null && !mDthInfoRecords.getNextRechargeDate().isEmpty()) {
                llRechargeDate.setVisibility(View.VISIBLE);
                NextRechargeDate.setText((mDthInfoRecords.getNextRechargeDate() + "").trim());
            } else {
                llRechargeDate.setVisibility(View.GONE);
            }


            if (mDthInfoRecords.getMonthlyRecharge() != null && !mDthInfoRecords.getMonthlyRecharge().isEmpty()) {
                llPackageAmt.setVisibility(View.VISIBLE);
                RechargeAmount.setText(getResources().getString(R.string.rupiya) + " " + (mDthInfoRecords.getMonthlyRecharge() + "").trim());
            } else {
                llPackageAmt.setVisibility(View.GONE);
            }


            RechargeAmount.setOnClickListener(view -> etAmount.setText(mDthInfoRecords.getMonthlyRecharge() + ""));

        }
    }

    private void parseDthInfoData(DTHInfoData responsePlan) {

        if (responsePlan.getRecords() != null && responsePlan.getRecords().size() > 0) {
            mDthInfoRecords = responsePlan.getRecords().get(0);
        } else if (responsePlan.getData() != null) {
            mDthInfoRecords = responsePlan.getData();
        }

        if (mDthInfoRecords != null) {
            rlDthInfoDetail.setVisibility(View.VISIBLE);

            Glide.with(this).load(ApplicationConstant.INSTANCE.baseIconUrl + Icon).apply(requestOptions).into(operatorIcon);

            if (responsePlan.getTel() != null && !responsePlan.getTel().isEmpty()) {
                tel.setText(/*AccountName.trim() + " : " +*/ (responsePlan.getTel() + "").trim());
            } else {
                tel.setText(/*AccountName.trim() + " : " +*/ etNumber.getText().toString() + "");
            }
            if (responsePlan.getOperator() != null && !responsePlan.getOperator().isEmpty()) {
                operator.setText((responsePlan.getOperator() + "").trim());
            } else {
                operator.setText(operatorSelected.trim() + "");
            }


            if (mDthInfoRecords.getCustomerName() != null && !mDthInfoRecords.getCustomerName().isEmpty()) {
                llCustomerLayout.setVisibility(View.VISIBLE);
                customerName.setText((mDthInfoRecords.getCustomerName() + "").trim());
            } else {
                llCustomerLayout.setVisibility(View.GONE);
            }

            if (mDthInfoRecords.getBalance() != null && !mDthInfoRecords.getBalance().isEmpty()) {
                llBalAmount.setVisibility(View.VISIBLE);
                Balance.setText(getResources().getString(R.string.rupiya) + " " + (mDthInfoRecords.getBalance() + "").trim());
            } else {
                llBalAmount.setVisibility(View.GONE);
            }

            if (mDthInfoRecords.getPlanname() != null && !mDthInfoRecords.getPlanname().isEmpty()) {
                llPlanName.setVisibility(View.VISIBLE);
                planname.setText((mDthInfoRecords.getPlanname() + "").trim());
            } else {
                llPlanName.setVisibility(View.GONE);
            }

            if (mDthInfoRecords.getNextRechargeDate() != null && !mDthInfoRecords.getNextRechargeDate().isEmpty()) {
                llRechargeDate.setVisibility(View.VISIBLE);
                NextRechargeDate.setText((mDthInfoRecords.getNextRechargeDate() + "").trim());
            } else {
                llRechargeDate.setVisibility(View.GONE);
            }


            if (mDthInfoRecords.getMonthlyRecharge() != null && !mDthInfoRecords.getMonthlyRecharge().isEmpty()) {
                llPackageAmt.setVisibility(View.VISIBLE);
                RechargeAmount.setText(getResources().getString(R.string.rupiya) + " " + (mDthInfoRecords.getMonthlyRecharge() + "").trim());
            } else {
                llPackageAmt.setVisibility(View.GONE);
            }


            RechargeAmount.setOnClickListener(view -> etAmount.setText(mDthInfoRecords.getMonthlyRecharge() + ""));

        }
    }


    private void SelectOperator(String s) {
        String param = ApiFintechUtilMethods.INSTANCE.fetchOperator(this, s, numberSeriesListResponse);
        String[] parts = param.split(",");
        int opid = 0;
        if (parts.length == 2) {
            try {
                opid = Integer.parseInt(parts[0]);
            } catch (NumberFormatException nfe) {

            }
            operatorRefCircleID = parts[1];

        } else {
            opid = 0;
            operatorRefCircleID = "";
        }
        ////////////////////////////////////////////////////////

        if (opid != 0) {
            for (OperatorList op : operatorListResponse.getOperators()) {
                if (op != null && op.getOid() == opid) {

                    setIntentData(op);

                    //Code For Postpaid
                   /* if (fromId == 2 ) {
                        boolean isOperatorAvailable = false;
                        for (OperatorList opPostpaid : operatorListResponse.getOperators()) {
                            if (opPostpaid.getOpType().equalsIgnoreCase(fromId + "") && opPostpaid.getName().contains(op.getName())) {
                                setIntentData(opPostpaid);
                                 isOperatorAvailable = true;
                            }
                        }
                        if(!isOperatorAvailable){
                            tvOperator.setText("");
                            tvOperator.setError(null);
                            ivOprator.setImageResource(R.drawable.ic_tower);
                            operatorSelected = "";
                            operatorSelectedId = 0;
                            operatorDocName = "";
                            operatorCircleCode = "";
                            operatorRefCircleID = "";
                            lapuSwitch.setText("Lapu");
                            realApiSwitch.setText("Recharge With Real");
                        }

                    } else {
                        setIntentData(op);
                    }*/
                    break;
                }
            }
        } else {
            ivOprator.setImageResource(R.drawable.ic_tower);
            tvOperator.setText("");
            tvOperator.setError(null);
            operatorSelected = "";
            operatorSelectedId = 0;
            operatorDocName = "";
            lapuSwitch.setText("Lapu");
            realApiSwitch.setText("Recharge With Real");
        }


    }

    private void SelectOperator(int opretorId, int circleId) {


        operatorRefCircleID = String.valueOf(circleId);

        for (OperatorList mOperatorList : operatorListResponse.getOperators()) {
            if (mOperatorList != null && mOperatorList.getOid() == opretorId) {

                setIntentData(mOperatorList);

                //Code For Postpaid
               /* if (fromId == 2 ) {
                    boolean isOperatorAvailable = false;
                    for (OperatorList opPostpaid : operatorListResponse.getOperators()) {
                        if (opPostpaid.getOpType().equalsIgnoreCase(fromId + "") && opPostpaid.getName().contains(mOperatorList.getName())) {
                            setIntentData(opPostpaid);
                            isOperatorAvailable=true;
                        }
                    }

                    if(!isOperatorAvailable){
                        tvOperator.setText("");
                        tvOperator.setError(null);
                        ivOprator.setImageResource(R.drawable.ic_tower);
                        operatorSelected = "";
                        operatorSelectedId = 0;
                        operatorDocName = "";
                        operatorCircleCode = "";
                        operatorRefCircleID = "";
                        lapuSwitch.setText("Lapu");
                        realApiSwitch.setText("Recharge With Real");
                    }
                } else {
                    setIntentData(mOperatorList);
                }*/

                break;
            }
        }

    }


    public void getHLRLookUp() {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GetHLRLookUPResponse> call = git.GetHLRLookUp(new GetHLRLookUpRequest(etNumber.getText().toString(), mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<GetHLRLookUPResponse>() {

                @Override
                public void onResponse(Call<GetHLRLookUPResponse> call, retrofit2.Response<GetHLRLookUPResponse> response) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            GetHLRLookUPResponse mGetHLRLookUPResponse = response.body();
                            if (mGetHLRLookUPResponse != null) {

                                if (mGetHLRLookUPResponse.isPasswordExpired()) {

                                    mCustomPassDialog = new CustomAlertDialog(RechargeBillPaymentActivity.this);
                                    mCustomPassDialog.WarningWithSingleBtnCallBack(getString(R.string.password_expired_msg), "Change", false, new CustomAlertDialog.DialogCallBack() {
                                        @Override
                                        public void onPositiveClick() {
                                            startActivityForResult(new Intent(RechargeBillPaymentActivity.this, ChangePinPassActivity.class).putExtra("IsPin", false).putExtra("IsForcibly", true).putExtra("Intent_Result", INTENT_PASSWORD_EXPIRE).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_PASSWORD_EXPIRE);

                                        }

                                        @Override
                                        public void onNegativeClick() {

                                        }
                                    });

                                }


                                if (mGetHLRLookUPResponse.getStatuscode() == 1) {
                                    if (mGetHLRLookUPResponse.getOid() != 0) {
                                        SelectOperator(mGetHLRLookUPResponse.getOid(), mGetHLRLookUPResponse.getCircleID());
                                    } else {
                                        tvOperator.setText("");
                                        tvOperator.setError(null);
                                        ivOprator.setImageResource(R.drawable.ic_tower);
                                        operatorSelected = "";
                                        operatorSelectedId = 0;
                                        operatorDocName = "";
                                        operatorRefCircleID = "";
                                        lapuSwitch.setText("Lapu");
                                        realApiSwitch.setText("Recharge With Real");
                                    }


                                } else {

                                    //ApiUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, mGetHLRLookUPResponse.getMsg() + "");
                                }

                            } else {

                                //ApiUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, getString(R.string.some_thing_error));
                            }
                        } else {

                            //  ApiUtilMethods.INSTANCE.apiErrorHandle(RechargeBillPaymentActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetHLRLookUPResponse> call, Throwable t) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }


                        //ApiUtilMethods.INSTANCE.apiFailureError(RechargeBillPaymentActivity.this, t);


                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        // ApiUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, ise.getMessage() + "");
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            // ApiUtilMethods.INSTANCE.Error(RechargeBillPaymentActivity.this, e.getMessage() + "");
        }

    }


    public void HitIncentiveApi(boolean isFromClick) {
        incentiveMap.clear();
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            if (isFromClick) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }
            ApiFintechUtilMethods.INSTANCE.IncentiveDetail(this, isFromClick, operatorSelectedId + "", loader, mLoginDataResponse, deviceId, deviceSerialNum, object -> {
                AppUserListResponse mAppUserListResponse = (AppUserListResponse) object;
                incentiveOperatorSelectedId = operatorSelectedId;

                incentiveList = mAppUserListResponse.getIncentiveDetails();
                if (isFromClick) {
                    showPopupIncentive();
                }
                incentiveMap.clear();
                for (IncentiveDetails item : incentiveList) {
                    incentiveMap.put(item.getDenomination() + "", item);

                }
            });
        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }

    }


    private void showPopupIncentive() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_incentive, null);
        RecyclerView recyclerView = viewMyLayout.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        View closeBtn = viewMyLayout.findViewById(R.id.closeBtn);

        IncentiveAdapter incentiveAdapter = new IncentiveAdapter(incentiveList, RechargeBillPaymentActivity.this);
        recyclerView.setAdapter(incentiveAdapter);
        incentiveDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        incentiveDialog.setCancelable(false);
        incentiveDialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        incentiveDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incentiveDialog.dismiss();
            }
        });

        incentiveDialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);


    }

    public void setCashBackAmount(IncentiveDetails item) {
        if (incentiveDialog != null && incentiveDialog.isShowing()) {
            incentiveDialog.dismiss();
        }
        etAmount.setText(item.getDenomination() + "");
        desc.setVisibility(View.VISIBLE);
        desc.setText("You are eligible for " + item.getComm() + (item.isAmtType() ? " \u20B9 Cash Back" : " % Cash Back"));
    }

    public void SetNumber(final String Number, EditText etView) {
        String Number1 = Number.replace("+91", "");
        String Number2 = Number1.replace(" ", "");
        String Number3 = Number2.replace("(", "");
        String Number4 = Number3.replace(")", "");
        String Number5 = Number4.replace("_", "");
        String Number6 = Number5.replace("-", "");
        etView.setText(Number6);

    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            isTTSInit = true;
            if (msgSpeak != null && !msgSpeak.isEmpty()) {
                playVoice();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Init failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void playVoice() {
        if (tts != null && isTTSInit) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts.speak(msgSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                tts.speak(msgSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
            msgSpeak = "";
        }
    }

    @Override
    public void onDestroy() {
        if (alertDialogFetchBill != null && alertDialogFetchBill.isShowing()) {
            alertDialogFetchBill.dismiss();
            alertDialogFetchBill = null;
        }
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.toLowerCase().contains("upi://pay")) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    Intent chooser = Intent.createChooser(intent, "Pay with...");
                    if (null != chooser.resolveActivity(getPackageManager())) {
                        startActivityForResult(chooser, INTENT_UPI_WEB);
                    } else {
                        Toast.makeText(RechargeBillPaymentActivity.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
                    }
                } catch (ActivityNotFoundException anfe) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        Intent chooser = Intent.createChooser(intent, "Pay with...");
                        startActivityForResult(chooser, INTENT_UPI_WEB);
                    } catch (ActivityNotFoundException anfe1) {
                        Toast.makeText(RechargeBillPaymentActivity.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
                    }
                }

                return true;
            } else {
                view.loadUrl(url);
                return false;
            }

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loader.show();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loader.dismiss();

            if (url.toLowerCase().contains("pgcallback/upigatewayredirectnew") || url.toLowerCase().contains(ApplicationConstant.INSTANCE.Domain + "/allupireturn?")) {

                if (uiWebViewDialog != null && uiWebViewDialog.isShowing()) {
                    uiWebViewDialog.dismiss();
                    loader.show();
                    //  callUPIWebUpdate(false);
                }

            }
        }

    }

    private class MyWebChromeViewClient extends WebChromeClient {

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            if (consoleMessage.message().contains("qrCodeImage data:image/png;base64,")) {
                byte[] decodedString = Base64.decode(consoleMessage.message().substring(consoleMessage.message().indexOf(",")), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                saveBitmap(decodedByte);
            }
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.cancel();
            return true;
        }
    }

    private void saveBitmap(Bitmap bitmap) {
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            ContentValues values = contentValues();
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
            values.put(MediaStore.Images.Media.IS_PENDING, true);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + ".png");

            Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try {
                    saveImageToStream(bitmap, this.getContentResolver().openOutputStream(uri));
                    values.put(MediaStore.Images.Media.IS_PENDING, false);
                    this.getContentResolver().update(uri, values, null, null);
                    sendImage(uri);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            File directory = new File(Environment.getExternalStorageDirectory().toString() + "/Pictures/" + getString(R.string.app_name));
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".png";
            File file = new File(directory, fileName);
            try {
                saveImageToStream(bitmap, new FileOutputStream(file));
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                sendImage(Uri.parse("file://" + file));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        }
        return values;
    }

    private void saveImageToStream(Bitmap bitmap, OutputStream outputStream) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
            } catch (FileNotFoundException e) {
                Log.e("GREC", e.getMessage(), e);
            } catch (IOException e) {
                Log.e("GREC", e.getMessage(), e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendImage(Uri myUri) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "QR Image");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "QR Image");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }
}
