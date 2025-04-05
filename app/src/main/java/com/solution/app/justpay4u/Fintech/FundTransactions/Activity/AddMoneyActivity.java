package com.solution.app.justpay4u.Fintech.FundTransactions.Activity;
import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_NOTIFY_URL;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;
import static com.cashfree.pg.CFPaymentService.PARAM_PAYMENT_MODES;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cashfree.pg.CFPaymentService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceType;
import com.solution.app.justpay4u.Api.Fintech.Object.CashFreeData;
import com.solution.app.justpay4u.Api.Fintech.Object.KeyVals;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.Api.Fintech.Object.PaymentGatewayType;
import com.solution.app.justpay4u.Api.Fintech.Object.RequestPTM;
import com.solution.app.justpay4u.Api.Fintech.Object.RequestRazorPay;
import com.solution.app.justpay4u.Api.Fintech.Object.SlabDetailDisplayLvl;
import com.solution.app.justpay4u.Api.Fintech.Object.SlabRangeDetail;
import com.solution.app.justpay4u.Api.Fintech.Object.UPIGatewayRequest;
import com.solution.app.justpay4u.Api.Fintech.Object.WalletType;
import com.solution.app.justpay4u.Api.Fintech.Request.AggrePayTransactionUpdateRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.ChoosePaymentGatwayRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GatewayTransactionRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.PayTMTransactionUpdateRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.InitiateUpiResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OperatorListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.PayTmSuccessItemResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.SlabCommissionResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.SlabRangeDetailResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.WalletTypeResponse;
import com.solution.app.justpay4u.Api.Networking.Response.GatwayStatusCheckResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.CommissionSlab.Adapter.CommissionSlabDetailAdapter;
import com.solution.app.justpay4u.Fintech.FundTransactions.Adapter.AddMoneyTypeAdapter;
import com.solution.app.justpay4u.Fintech.FundTransactions.Adapter.GatewayTypeAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownModel;
import com.solution.app.justpay4u.Util.EKYCStepsDialog;
import com.solution.app.justpay4u.Util.Utility;
import com.test.pg.secure.pgsdkv4.PGConstants;
import com.test.pg.secure.pgsdkv4.PaymentGatewayPaymentInitializer;
import com.test.pg.secure.pgsdkv4.PaymentParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class AddMoneyActivity extends AppCompatActivity implements PaymentResultListener, PaytmPaymentTransactionCallback {
    private static final String TAG = AddMoneyActivity.class.getSimpleName();
    View walletView,noClickView, msgView, noDataView, noNetworkView, retryBtn;
    TextView walletTv, walletAmountTv;
    TextView upiBtn;
    ImageView arrowIv, bhimLogo;
    EditText amountEt;
    RecyclerView recyclerView;
    CustomLoader loader;
    BalanceResponse balanceCheckResponse;

    ArrayList<SlabDetailDisplayLvl> operatorArray = new ArrayList<>();
    ArrayList<PaymentGatewayType> pgList = new ArrayList<>();
    ArrayList<BalanceData> mBalanceDataList = new ArrayList<>();
    int selectedOPId = -1;
    LoginResponse mLoginDataResponse;
    boolean isActivityPause;
    DropDownDialog mDropDownDialog;
    int INTENT_REQUEST_CODE_PAYTM = 7365;
    private WalletTypeResponse mWalletTypeResponse;
    HashMap<String, Integer> walletIdMap = new HashMap<>();
    ArrayList<DropDownModel> mDropDownModels = new ArrayList<>();
    ArrayList<BalanceType> mActiveBalanceTypes = new ArrayList<>();

    private int selectedWalletId = 1;
    private Dialog gatewayDialog;
    private String selectedMethod;
    private boolean isDialogShowBackground;
    private String dialogMsg;
    private boolean isSucessDialog;
    private int selectedWalletPos;
    private AppPreferences mAppPreferences;
    private Gson gson;
    private String deviceId, deviceSerialNum;
    private int INTENT_UPI = 8765;
    private int INTENT_UPI_WEB = 6782;
    private String upiTID;
    private SlabDetailDisplayLvl selectedOperator;
    private boolean isUpi;
    private boolean isPaymentGatway;
    private OperatorListResponse mOperatorListResponse;
    private AlertDialog alertDialog;
    private RequestOptions requestOptions;
    private boolean isApiCalling;
    private RequestPTM ptmRequest;
    private EKYCStepsDialog mEKYCStepsDialog;
    private String transactioID, token, orderId;
    private CashFreeData cFItemData;
    private boolean isTransactionCancelledByUser;
    private Dialog uiWebViewDialog;
    private double intentAmount;
    private boolean isFromCart,isFromRecharge;
    private int currentPGId;
    private String requestAmount = "0";
    private boolean isAllUpiDialogCanceled;
    private boolean isPendingTimerComplete;
    private boolean isAllUPIStatusCheckRunning;
    private int sucessDialogStatus;
    private Dialog pendingTimerDialog;
    private CountDownTimer countDownTimerPendingTxn;
    private PaymentGatewayType paymentGatewayType;
    private String razorpayOrderid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.myLooper()).post(() -> {
            setContentView(R.layout.activity_add_money);
            isActivityPause = false;
            mDropDownDialog = new DropDownDialog(this);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
            gson = new Gson();
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            isFromRecharge = getIntent().getBooleanExtra("IsFromRecharge", false);
            intentAmount = getIntent().getDoubleExtra("AMOUNT", 0);
            isFromCart = getIntent().getBooleanExtra("isFromCart", false);
            findViews();

//            walletView.setOnClickListener(v -> showPopupWindow(v));
            upiBtn.setOnClickListener(v -> {
                amountEt.setError(null);
                if (selectedOPId != -1 || selectedOperator != null) {
                    if (amountEt.getText().toString().trim().isEmpty()) {
                        amountEt.setError("Please Enter Amount");
                        amountEt.requestFocus();
                        return;
                    } else if (selectedOperator.getMin() != 0 && Integer.parseInt(amountEt.getText().toString().trim()) < selectedOperator.getMin()) {
                        amountEt.setError("Amount can't be less then \u20B9 " + selectedOperator.getMin());
                        amountEt.requestFocus();
                        return;
                    } else if (selectedOperator.getMax() != 0 && Integer.parseInt(amountEt.getText().toString().trim()) > selectedOperator.getMax()) {
                        amountEt.setError("Amount can't be greater then \u20B9 " + selectedOperator.getMax());
                        amountEt.requestFocus();
                        return;
                    }

                    /* initUpi();*/
                    ChoosePaymentGateway(true);
                } else {
                    Toast.makeText(AddMoneyActivity.this, "Invalid Operator Id", Toast.LENGTH_SHORT).show();
                }
            });
            retryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loader.show();
                    noDataView.setVisibility(View.GONE);
                    noNetworkView.setVisibility(View.GONE);
                    HitCommissionApi();
                }
            });

            new Handler(Looper.myLooper()).post(() -> {
                mOperatorListResponse = ApiFintechUtilMethods.INSTANCE.getOperatorListResponse(mAppPreferences);
                isUpi = mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isUpi);
                isPaymentGatway = mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isPaymentGatway);

                mEKYCStepsDialog = new EKYCStepsDialog(this, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);
                showWalletListPopupWindow();
                SlabCommissionResponse mSlabCommissionResponse = gson.fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.commListPref), SlabCommissionResponse.class);
                setUiData(mSlabCommissionResponse);
                HitCommissionApi();
            });
        });

    }

    private void setUiData(SlabCommissionResponse mOperatorListResponse) {
        if (isUpi && !isPaymentGatway) {
            upiBtn.setVisibility(View.VISIBLE);
            msgView.setVisibility(View.VISIBLE);
            bhimLogo.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            getOpId(mOperatorListResponse, 50);
        } else if (isUpi && isPaymentGatway){
            upiBtn.setVisibility(View.GONE);
            msgView.setVisibility(View.GONE);
            bhimLogo.setVisibility(View.GONE);
            getOperator(mOperatorListResponse, 50, 37);
        } else if (!isUpi && isPaymentGatway) {
            upiBtn.setVisibility(View.GONE);
            msgView.setVisibility(View.GONE);
            bhimLogo.setVisibility(View.GONE);
            getOperator(mOperatorListResponse, 37, -1);
        } else {
            upiBtn.setVisibility(View.GONE);
            msgView.setVisibility(View.GONE);
            bhimLogo.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
        }
    }

    void findViews() {
        upiBtn = findViewById(R.id.upiBtn);
        bhimLogo = findViewById(R.id.bhimLogo);
        msgView = findViewById(R.id.msg);
        walletView = findViewById(R.id.walletView);
        walletTv = findViewById(R.id.walletTv);
        walletAmountTv = findViewById(R.id.walletAmountTv);
        arrowIv = findViewById(R.id.arrowIv);
        amountEt = findViewById(R.id.amountEt);
        noClickView = findViewById(R.id.noClickView);
        if (isFromRecharge == true) {
            if (intentAmount % 1 != 0) {
                int amt = (int) (intentAmount + 1);
                amountEt.setText(Utility.INSTANCE.formatedAmountWithOutRupees(amt + ""));
            } else {
                int amt = (int) intentAmount;
                amountEt.setText(Utility.INSTANCE.formatedAmountWithOutRupees(amt + ""));
            }
            noClickView.setVisibility(View.VISIBLE);
        } else {
            noClickView.setVisibility(View.GONE);
        }
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                        noDataView.setVisibility(View.GONE);
                        noNetworkView.setVisibility(View.GONE);
                        setUiData(mSlabCommissionResponse);
                    } else {
                        if (operatorArray != null && operatorArray.size() > 0) {
                            noDataView.setVisibility(View.GONE);
                            noNetworkView.setVisibility(View.GONE);
                        } else {
                            noDataView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onError(int error) {
                    isApiCalling = false;
                    if (operatorArray != null && operatorArray.size() > 0) {
                        noDataView.setVisibility(View.GONE);
                        noNetworkView.setVisibility(View.GONE);
                    } else {
                        if (error == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
                            noDataView.setVisibility(View.GONE);
                            noNetworkView.setVisibility(View.VISIBLE);
                        }
                        else {
                            noDataView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                        }
                    }
                }
            });

        } else {
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            if (operatorArray != null && operatorArray.size() > 0) {
                noDataView.setVisibility(View.GONE);
                noNetworkView.setVisibility(View.GONE);
            } else {
                noDataView.setVisibility(View.GONE);
                noNetworkView.setVisibility(View.VISIBLE);
            }
        }
    }
    private void showWalletListPopupWindow() {

        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null && balanceCheckResponse.getBalanceData().size() > 0) {
            mBalanceDataList.clear();
            mBalanceDataList = balanceCheckResponse.getBalanceData();
            for (int i = 0; i < mBalanceDataList.size(); i++) {
                if (!mBalanceDataList.get(i).isInFundProcess()) {
                    mBalanceDataList.remove(i);
                }
            }


            if (mBalanceDataList != null && mBalanceDataList.size() > 0) {
                walletTv.setText(mBalanceDataList.get(0).getWalletType());
                walletAmountTv.setText(Utility.INSTANCE.formatedAmountWithRupees(mBalanceDataList.get(0).getBalance() + ""));
                selectedWalletId = mBalanceDataList.get(0).getId();
                if (mBalanceDataList.size() == 1) {
                    arrowIv.setVisibility(View.GONE);
                    walletView.setClickable(false);
                } else {
                    arrowIv.setVisibility(View.VISIBLE);
                    walletView.setClickable(true);
//                  its be change for one wallet 12/07/2023
                    arrowIv.setVisibility(View.GONE);
                    walletView.setClickable(true);
                }
                setWalletIds();
            }
        } else {
            balanceCheckResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
            if (balanceCheckResponse != null) {
                showWalletListPopupWindow();
            }
            return;
        }
    }

    private void showPopupWindow(View anchor) {
        if (mBalanceDataList != null && mBalanceDataList.size() > 0) {
            mDropDownDialog.showDropDownBalancePopup(anchor, selectedWalletPos, mBalanceDataList, (clickPosition, value, balanceData) -> {
                selectedWalletPos = clickPosition;
                walletTv.setText(value + "");
                walletAmountTv.setText("\u20B9 " + balanceData.getBalance() + "");
                selectedWalletId = balanceData.getId();
            });
        } else {
            showWalletListPopupWindow();
        }
    }


    void setWalletIds() {

        if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
            int count = 0;
            for (WalletType object : mWalletTypeResponse.getWalletTypes()) {
                if (object.getInFundProcess()) {

                    for (int i = 0; i < mDropDownModels.size(); i++) {
                        BalanceType mBalanceType = ((BalanceType) mDropDownModels.get(i).getDataObject());
                        if (mBalanceType.getName().contains(object.getName())) {
                            walletIdMap.put(mBalanceType.getName(), object.getId());
                            if (count == 0) {
                                walletTv.setText(mBalanceType.getName());
                                walletAmountTv.setText("\u20B9 " + mBalanceType.getAmount());
                                selectedWalletId = object.getId();
                            }
                            count++;
                        }
                    }
                }
            }

        } else {
            mWalletTypeResponse = ApiFintechUtilMethods.INSTANCE.getWalletTypeResponse(mAppPreferences);
            if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
                setWalletIds();
            } else {
                ApiFintechUtilMethods.INSTANCE.WalletType(AddMoneyActivity.this, mLoginDataResponse, mAppPreferences, object -> {
                    mWalletTypeResponse = (WalletTypeResponse) object;
                    if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
                        setWalletIds();
                    }
                });
            }
        }
    }


    void initUpi() {
        loader.show();
        upiTID = "";
        ApiFintechUtilMethods.INSTANCE.IntiateUPI(this, selectedWalletId + "", selectedOPId + "", amountEt.getText().toString(), "", loader, mLoginDataResponse, deviceId, deviceSerialNum, object -> {
            InitiateUpiResponse mInitiateUpiResponse = (InitiateUpiResponse) object;
            upiTID = mInitiateUpiResponse.getTid();
            if (mInitiateUpiResponse.getQRIntent() != null && mInitiateUpiResponse.getQRIntent().contains("upi://pay?")) {
                openUpiIntent(Uri.parse(mInitiateUpiResponse.getQRIntent()));
            } else {
                openUpiIntent(getUPIString(mInitiateUpiResponse.getMvpa(), mLoginDataResponse.getData().getName(), mInitiateUpiResponse.getTerminalID(), mInitiateUpiResponse.getBankOrderID(), getString(R.string.app_name).replaceAll(" ", "") + "UPITransaction", amountEt.getText().toString().trim(), ApplicationConstant.INSTANCE.baseUrl));
            }
        });
    }

    private Uri getUPIString(String payeeAddress, String payeeName, String merchentCode, String tref, String trxnNote, String payeeAmount, String refUrl) {
        Uri uri = new Uri.Builder().scheme("upi").authority("pay").appendQueryParameter("pa", payeeAddress).appendQueryParameter("pn", payeeName).appendQueryParameter("mc", merchentCode).appendQueryParameter("tr", tref).appendQueryParameter("tn", trxnNote).appendQueryParameter("am", payeeAmount).appendQueryParameter("cu", "INR").appendQueryParameter("url", refUrl).build();
        return uri;
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



        if (isFromRecharge && operatorArray != null && operatorArray.size() == 1) {
            paymentTypeClick(operatorArray.get(0));
        }else {
            if(operatorArray != null && !operatorArray.isEmpty()) {
                operatorArray.size();
                AddMoneyTypeAdapter addMoneyTypeAdapter = new AddMoneyTypeAdapter(operatorArray, mLoginDataResponse.getData().getRoleID(), this);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(addMoneyTypeAdapter);
                if (loader != null && loader.isShowing()) {
                    loader.dismiss();
                }
            }
        }


    }

    /*private void getOpId(OperatorListResponse mOperatorListResponse, int op_Type) {

        for (OperatorList op : mOperatorListResponse.getOperators()) {
            if (op.isActive() && op.getOpType() == op_Type) {
                selectedOPId = op.getOid();
                selectedMethod = op.getName();
                selectedOperator = op;
            }

        }

    }
*/
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

    public void HitApiSlabDetail(final int oid, final String operatorValue, final String maxMinValue) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            ApiFintechUtilMethods.INSTANCE.MyCommissionDetail(this, oid, loader, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, object -> {
                SlabRangeDetailResponse mSlabRangeDetailResponse = (SlabRangeDetailResponse) object;
                slabDetailDialog(mSlabRangeDetailResponse.getSlabRangeDetail(), operatorValue, maxMinValue, oid);
            });

        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    public void slabDetailDialog(ArrayList<SlabRangeDetail> mSlabRangeDetail, String operatorValue, String maxMinValue, int oid) {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this);
            alertDialog = dialogBuilder.create();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_slab_range_details, null);
            alertDialog.setView(dialogView);


            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView operator = dialogView.findViewById(R.id.operator);
            TextView maxMin = dialogView.findViewById(R.id.maxMin);
            TextView maxComTitle = dialogView.findViewById(R.id.maxComTitle);
            TextView comSurTitle = dialogView.findViewById(R.id.comSurTitle);
            TextView fixedChargedTitle = dialogView.findViewById(R.id.fixedChargedTitle);
            ImageView iconIv = dialogView.findViewById(R.id.icon);
            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new CommissionSlabDetailAdapter(mSlabRangeDetail, this));
            operator.setText(operatorValue);
            maxMin.setText("Range : " + maxMinValue);

            if (mSlabRangeDetail.get(0).getDmrModelID() == 2 || mSlabRangeDetail.get(0).getDmrModelID() == 3 || mSlabRangeDetail.get(0).getDmrModelID() == 4) {
                fixedChargedTitle.setVisibility(View.VISIBLE);
                maxComTitle.setVisibility(View.GONE);
            } else if (mSlabRangeDetail.get(0).getDmrModelID() == 1) {
                fixedChargedTitle.setVisibility(View.GONE);
                maxComTitle.setVisibility(View.GONE);
            } else {
                fixedChargedTitle.setVisibility(View.GONE);
                maxComTitle.setVisibility(View.VISIBLE);
            }

            if (mSlabRangeDetail.get(0).isCommType()) {
                comSurTitle.setText("Surcharge");
            } else {
                comSurTitle.setText("Commission");
            }

            Glide.with(this).load(ApplicationConstant.INSTANCE.baseIconUrl + oid + ".png").apply(requestOptions).into(iconIv);
            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });


            alertDialog.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }

    public void paymentTypeClick(SlabDetailDisplayLvl operator) {
        amountEt.setError(null);
        if (amountEt.getText().toString().trim().isEmpty()) {
            amountEt.setError("Please Enter Amount");
            amountEt.requestFocus();
            return;
        } else if (operator.getMin() != 0 && Integer.parseInt(amountEt.getText().toString().trim()) < operator.getMin()) {
            amountEt.setError("Amount can't be less then \u20B9 " + operator.getMin());
            amountEt.requestFocus();
            return;
        } else if (operator.getMax() != 0 && Integer.parseInt(amountEt.getText().toString().trim()) > operator.getMax()) {
            amountEt.setError("Amount can't be greater then \u20B9 " + operator.getMax());
            amountEt.requestFocus();
            return;
        }
        selectedMethod = operator.getOperator();
        selectedOPId = operator.getOid();
        selectedOperator = operator;
        if (operator.getOpTypeId() == 50) {
            /*initUpi();*/
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
                                            initUpi();
                                        } else {
                                            pgList = response.body().getpGs();
                                            startGateway(pgList.get(0));
                                        }
                                    } else {
                                        pgList = response.body().getpGs();
                                        showPopupGateWay();
                                    }

                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Processing(AddMoneyActivity.this, "Service is currently down.");
                                }

                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(AddMoneyActivity.this, response.code(), response.message());
                        }
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }


                        ApiFintechUtilMethods.INSTANCE.apiFailureError(AddMoneyActivity.this, t);

                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
        }
    }


    private void showPopupGateWay() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_select_gateway, null);
        RecyclerView recyclerView = viewMyLayout.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        View closeBtn = viewMyLayout.findViewById(R.id.closeBtn);

        GatewayTypeAdapter gatewayTypeAdapter = new GatewayTypeAdapter(pgList, AddMoneyActivity.this);
        recyclerView.setAdapter(gatewayTypeAdapter);
        gatewayDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        gatewayDialog.setCancelable(false);
        gatewayDialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
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

    /*-------Cash Free SDK Integrations..----*/

    private void initCashFreeSdk() {

        try {
            CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
            cfPaymentService.setOrientation(0);
            cfPaymentService.setConfirmOnExit(true);
            String colorValue1 = "#3E457C", colorValue2 = "#FFFFFF";

            try {
                colorValue1 = "#" + Integer.toHexString(ContextCompat.getColor(this, R.color.colorPrimary) & 0x00ffffff);
            } catch (NullPointerException npe) {

            } catch (Exception e) {

            }

            if (selectedMethod.toLowerCase().contains("upi")) {

                Intent upiIntent = new Intent(Intent.ACTION_VIEW);
                upiIntent.setData(Uri.parse("upi://pay"));

                /*isAppAvailable(upiIntent)*/
                if (upiIntent.resolveActivity(getPackageManager()) != null) {
                    cfPaymentService.upiPayment(this, getInputParams(), cFItemData.getToken(), ApplicationConstant.INSTANCE.CFStage);

                } else {
                    cfPaymentService.doPayment(this, getInputParams(), cFItemData.getToken(), ApplicationConstant.INSTANCE.CFStage, colorValue1, colorValue2, false);

                }

            } else {
                cfPaymentService.doPayment(this, getInputParams(), cFItemData.getToken(), ApplicationConstant.INSTANCE.CFStage, colorValue1, colorValue2, false);

            }

            /* Log.d(TAG, "ReqCode : " + new Gson().toJson(cfPaymentService));*/


        } catch (Exception exception) {
            ApiFintechUtilMethods.INSTANCE.Error(this, "" + exception.getMessage());
        }

    }

    private boolean isAppAvailable(Intent intent) {
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private Map<String, String> getInputParams() {

        String orderAppId = "", orderAmount = "", orderId = "", orderNote = "", customerName = "", customerPhone = "", customerEmail = "", orderCurrency = "INR", orderNotifyURL = "";

        if (cFItemData.getAppId() != null && !cFItemData.getAppId().isEmpty())
            orderAppId = cFItemData.getAppId();


        if (cFItemData.getOrderAmount() != null)
            orderAmount = String.valueOf(cFItemData.getOrderAmount());

        else orderAmount = amountEt.getText().toString();

        if (cFItemData.getOrderID() != null && !cFItemData.getOrderID().isEmpty())
            orderId = cFItemData.getOrderID();

        customerName = mLoginDataResponse.getData().getName();

        if (cFItemData.getCustomerPhone() != null && !cFItemData.getCustomerPhone().isEmpty())
            customerPhone = cFItemData.getCustomerPhone();
        else customerPhone = mLoginDataResponse.getData().getMobileNo();

        if (cFItemData.getCustomerEmail() != null && !cFItemData.getCustomerEmail().isEmpty())
            customerEmail = cFItemData.getCustomerEmail();
        else customerEmail = mLoginDataResponse.getData().getEmailID();

        if (cFItemData.getOrderCurrency() != null && !cFItemData.getOrderCurrency().isEmpty())
            orderCurrency = cFItemData.getOrderCurrency().trim();

        if (cFItemData.getNotifyUrl() != null) orderNotifyURL = cFItemData.getNotifyUrl();

        Map<String, String> params = new HashMap<>();

        params.put(PARAM_APP_ID, orderAppId);
        params.put(PARAM_ORDER_ID, orderId);
        if (selectedMethod.toLowerCase().contains("upi")) {
            params.put(PARAM_PAYMENT_MODES, "upi");
        } else if (selectedMethod.toLowerCase().contains("credit")) {
            params.put(PARAM_PAYMENT_MODES, "cc");
        } else if (selectedMethod.toLowerCase().contains("debit")) {
            params.put(PARAM_PAYMENT_MODES, "dc");
        } else if (selectedMethod.toLowerCase().contains("net")) {
            params.put(PARAM_PAYMENT_MODES, "nb");
        } else if (selectedMethod.toLowerCase().contains("wallet")) {
            params.put(PARAM_PAYMENT_MODES, "wallet");
        }

        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_ORDER_NOTE, orderNote);
        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(PARAM_CUSTOMER_EMAIL, customerEmail);
        params.put(PARAM_ORDER_CURRENCY, orderCurrency);
        params.put(PARAM_NOTIFY_URL, orderNotifyURL);

       /* for(Map.Entry entry : params.entrySet()) {
            Log.e("CFSKDRequest", entry.getKey() + " " + entry.getValue());
        }*/
        return params;
    }

    private String transResponseBundleToString(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String response = "";
        Map<String, String> mapResponseData = new HashMap<>();
        for (String key : bundle.keySet()) {
            if (bundle.getString(key) != null) {
                response = response.concat(String.format("%s : %s \n", key.toUpperCase(), bundle.get(key)));
                mapResponseData.put(key, bundle.getString(key));
            }
        }
        /*for (String key : bundle.keySet()) {
            if (bundle.getString(key) != null) {
                Log.e("CashFreeCallBack : ", key + " : " + bundle.getString(key));
                response=response.concat(String.format("%$ : %$\n",key,bundle.get(key)));
                Object value = bundle.get(key);
                mapResponseData.put(key,bundle.getString(key));
                stringBuilder.append(
                        String.format("%s %s (%s)\n", key, value*/
        /*, value == null ? "null" : value.getClass().getName()*//*));

            }
        }*/
        /*return stringBuilder.substring(0, stringBuilder.length() - 1);*/
        return response;

    }

    private JSONObject transResponseBundleToJsonOb(Bundle bundle) {
        {
            if (bundle == null) {
                return null;
            }
            JSONObject json = new JSONObject();
            JsonObject json1 = new JsonObject();
            for (String key : bundle.keySet()) {
                if (bundle.getString(key) != null) {
                    try {
                        // json.put(key, bundle.get(key)); see edit below
                        json.put(key, JSONObject.wrap(bundle.get(key)));
                        json1.addProperty(key, (String) bundle.get(key));
                    } catch (JSONException e) {
                        //Handle exception here
                    }

                }
            }
            /*return stringBuilder.substring(0, stringBuilder.length() - 1);*/
            return json;

        }
    }

    private void showResponse(String response) {
        new MaterialAlertDialogBuilder(this).setMessage(response).setTitle("Alert").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()).show();
    }

    private Bundle cashFreeSuccessData(CashFreeData cFResponseItem, JSONObject sdkResponseItem, int statusCode) {
        Bundle inResponse = new Bundle();
        try {
            inResponse.putString("STATUS", sdkResponseItem.getString("txStatus"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("STATUS", "");//STATUS
        }
        try {
            /*signature : 4Kt4VfWYu2yRvFqXFrKO5sufD9N5/9fSvXCVaUN0F/4=*/
            inResponse.putString("CHECKSUMHASH", "" + sdkResponseItem.getString("signature"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("CHECKSUMHASH", "");
        }

        inResponse.putString("BANKNAME", "");
        try {
            inResponse.putString("ORDERID", sdkResponseItem.getString("orderId"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("ORDERID", cFResponseItem.getOrderID());//ORDERID
        }

        try {
            inResponse.putString("TXNAMOUNT", String.valueOf(sdkResponseItem.getDouble("orderAmount")));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("TXNAMOUNT", amountEt.getText().toString() + "");//TXNAMOUNT
        }
        inResponse.putString("MID", "");

        try {
            inResponse.putString("TXNID", sdkResponseItem.getString("referenceId"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("TXNID", "");//TXNID
        }
        /*if(statusCode==1){

        }*/
        inResponse.putString("RESPCODE", statusCode + "");//RESPCODE
        try {
            inResponse.putString("PAYMENTMODE", sdkResponseItem.getString("paymentMode"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("PAYMENTMODE", "" + cFResponseItem.getPaymentModes());//PAYMENTMODE
        }
        try {
            inResponse.putString("BANKTXNID", sdkResponseItem.getString("referenceId"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("BANKTXNID", "");//BANKTXNID
        }
        inResponse.putString("CURRENCY", "INR");
        try {
            inResponse.putString("GATEWAYNAME", sdkResponseItem.getString("type"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("GATEWAYNAME", "");//GATEWAYNAME
        }
        try {
            inResponse.putString("TXNDATE", sdkResponseItem.getString("txTime"));//txTime
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("TXNDATE", "");//TXNDATE
        }
        try {
            inResponse.putString("RESPMSG", sdkResponseItem.getString("txMsg"));//txMsg
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("RESPMSG", "");//txMsg
        }
        return inResponse;
    }

    private Bundle cashFreeFailedData(CashFreeData requestCFData, int errorCode, String status, String errorMsg) {
        Bundle inResponse = new Bundle();
        inResponse.putString("STATUS", status);
        inResponse.putString("CHECKSUMHASH", "");
        inResponse.putString("BANKNAME", "");
        inResponse.putString("ORDERID", requestCFData.getOrderID());
        inResponse.putString("TXNAMOUNT", String.valueOf(requestCFData.getOrderAmount()));
        inResponse.putString("MID", "");
        inResponse.putString("TXNID", "");
        inResponse.putString("RESPCODE", errorCode + "");
        inResponse.putString("PAYMENTMODE", "");
        inResponse.putString("BANKTXNID", "");
        inResponse.putString("CURRENCY", "INR");
        inResponse.putString("GATEWAYNAME", "");
        inResponse.putString("RESPMSG", errorMsg);

        return inResponse;
    }

    private void cashFreeCallBackApi(Bundle inResponse) {
        JsonObject json = new JsonObject();
        if (inResponse != null) {
            Set<String> keys = inResponse.keySet();
            for (String key : keys) {
                json.addProperty(key, (String) inResponse.get(key));
            }
        }
        CashFreeTransactionUpdate(json);
    }

    public void CashFreeTransactionUpdate(JsonObject jsonObj) {
        try {
            if (!isActivityPause) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call;
            call = git.CashFreeTransactionUpdate(new PayTMTransactionUpdateRequest(jsonObj, mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    ApiFintechUtilMethods.INSTANCE.isReadyToUpdateBalance = true;
                                    if (!isFromRecharge) {
                                        amountEt.setText("");
                                    }
                                    ApiFintechUtilMethods.INSTANCE.Balancecheck(AddMoneyActivity.this, loader, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, mEKYCStepsDialog, object -> {
                                        balanceCheckResponse = (BalanceResponse) object;
                                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null && balanceCheckResponse.getBalanceData().size() > 0) {
                                            showWalletListPopupWindow();
                                        }
                                    });

                                    if (isTransactionCancelledByUser) {
                                        ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TXN CANCEL", "Cancelled By User");
                                    } else {

                                        if (isFromRecharge) {
                                            Intent intent = new Intent();
                                            intent.putExtra("TID", jsonObj.get("TXNID").getAsString());
                                            intent.putExtra("ORDERID", jsonObj.get("ORDERID").getAsString());
                                            intent.putExtra("Status",jsonObj.get("STATUS").getAsString());
                                            intent.putExtra("BANKTXNID", jsonObj.get("BANKTXNID").getAsString());
                                            intent.putExtra("TXNID", jsonObj.get("TXNID").getAsString());
                                            intent.putExtra("txnRef", jsonObj.get("TXNID").getAsString());
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        } else if (isFromCart) {
                                            setResult(RESULT_OK);
                                            finish();
                                        } else {
                                            setResult(RESULT_OK);
                                            if (response.body().getMsg() != null && response.body().getMsg().equalsIgnoreCase("PENDING")) {
                                                ApiFintechUtilMethods.INSTANCE.Processing(AddMoneyActivity.this, "Your Order" + cFItemData.getOrderID() + " for Amount " + getString(R.string.rupiya) + cFItemData.getOrderAmount() + " is awaited from Bank Side \n" + "We will Update once we get Response From bank Side ");

                                            } else if (response.body().getMsg() != null && response.body().getMsg().equalsIgnoreCase("Transaction Success")) {
                                                if (!isActivityPause) {
                                                    ApiFintechUtilMethods.INSTANCE.Successful(AddMoneyActivity.this, "Money Added Successfully");
                                                } else {
                                                    isDialogShowBackground = true;
                                                    dialogMsg = "Money Added Successfully";
                                                    isSucessDialog = true;
                                                }
                                            } else {
                                                showResponse(response.body().getMsg());
                                            }
                                        }
                                    }


                                } else {

                                    if (!isActivityPause) {
                                        ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getMsg() + "");
                                    } else {
                                        isDialogShowBackground = true;
                                        dialogMsg = response.body().getMsg() + "";
                                        isSucessDialog = false;
                                    }
                                }

                            } else {

                                if (!isActivityPause) {
                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = getString(R.string.some_thing_error);
                                    isSucessDialog = false;
                                }
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(AddMoneyActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = e.getMessage() + "";
                            isSucessDialog = false;
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (!isActivityPause) {
                                ApiFintechUtilMethods.INSTANCE.NetworkError(AddMoneyActivity.this);

                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = getString(R.string.err_msg_network);
                                isSucessDialog = false;
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (!isActivityPause) {
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = t.getMessage();
                                isSucessDialog = false;
                            }
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                if (!isActivityPause) {
                                    ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    isSucessDialog = false;
                                }
                            } else {
                                if (!isActivityPause) {
                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    isSucessDialog = false;
                                }
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = getString(R.string.some_thing_error);
                            isSucessDialog = false;
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing()) {
                loader.dismiss();
            }

            if (!isActivityPause) {
                ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
            } else {
                isDialogShowBackground = true;
                dialogMsg = e.getMessage() + "";
                isSucessDialog = false;
            }
        }
    }

    /*-------Cash Free SDK Integrations..----*/

    void initPaytmSdk(final RequestPTM requestPTM) {
        PaytmPGService mService = /*BuildConfig.DEBUG ? PaytmPGService.getStagingService() :*/ PaytmPGService.getProductionService();
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", requestPTM.getMid() + "");
        paramMap.put("ORDER_ID", requestPTM.getOrdeR_ID() + "");
        paramMap.put("CUST_ID", requestPTM.getCusT_ID() + "");
        paramMap.put("MOBILE_NO", requestPTM.getMobilE_NO() + "");
        paramMap.put("EMAIL", requestPTM.getEmail() + "");
        paramMap.put("CHANNEL_ID", requestPTM.getChanneL_ID() + "");
        paramMap.put("TXN_AMOUNT", requestPTM.getTxN_AMOUNT() + "");
        paramMap.put("WEBSITE", requestPTM.getWebsite() + "");
        paramMap.put("INDUSTRY_TYPE_ID", requestPTM.getIndustrY_TYPE_ID() + "");
        //BuildConfig.DEBUG ? "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=" + data.getNewOrderNo() : "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=" + data.getNewOrderNo()
        paramMap.put("CALLBACK_URL", requestPTM.getCallbacK_URL() + "");
        paramMap.put("CHECKSUMHASH", requestPTM.getChecksumhash() + "");
        PaytmOrder Order = new PaytmOrder(paramMap);
        mService.initialize(Order, null);
        ptmRequest = requestPTM;
        mService.startPaymentTransaction(this, true, true, this);
        /*mService.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
         *//*  Call Backs*//*
            public void someUIErrorOccurred(String inErrorMessage) {
                paytmCallBackApi(paytmFailedData(requestPTM, 0, "TXN_CANCEL", inErrorMessage));
                // Toast.makeText(getApplicationContext(), "UI Error " + inErrorMessage, Toast.LENGTH_LONG).show();
            }

            public void onTransactionResponse(Bundle inResponse) {
                paytmCallBackApi(inResponse);
            }

            public void networkNotAvailable() {
                paytmCallBackApi(paytmFailedData(requestPTM, 0, "TXN_CANCEL", "Network not available"));
                // UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.err_msg_network));
            }

            public void clientAuthenticationFailed(String inErrorMessage) {
                paytmCallBackApi(paytmFailedData(requestPTM, 0, "TXN_CANCEL", inErrorMessage));
                // Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inErrorMessage, Toast.LENGTH_LONG).show();
            }

            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                paytmCallBackApi(paytmFailedData(requestPTM, iniErrorCode, "TXN_CANCEL", inErrorMessage));
                // Toast.makeText(getApplicationContext(), "Unable to load webpage " + inErrorMessage, Toast.LENGTH_LONG).show();
            }

            public void onBackPressedCancelTransaction() {
                paytmCallBackApi(paytmFailedData(requestPTM, 0, "TXN_CANCEL", "Transaction canceled by user"));
                //Toast.makeText(getApplicationContext(), "Transaction cancelled", Toast.LENGTH_LONG).show();
            }

            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                paytmCallBackApi(inResponse);
            }
        });*/
    }


    void paytmCallBackApi(Bundle inResponse) {
        JsonObject json = new JsonObject();
        if (inResponse != null) {
            Set<String> keys = inResponse.keySet();
            for (String key : keys) {
                /* String value=inResponse.getString(key);
                if(value.startsWith("{")&&value.endsWith("}") ){
                    json= new Gson().fromJson(value, JsonObject.class);
                }else {
                    json.addProperty(key, (String) inResponse.get(key));
                }*/
                json.addProperty(key, (String) inResponse.get(key));
            }
        }
        PayTMTransactionUpdate(json);
    }

    private Bundle payTmSuccessData(PayTmSuccessItemResponse itemResponse) throws JSONException {
        Bundle inResponse = new Bundle();
        inResponse.putString("STATUS", itemResponse.getStatus());
        inResponse.putString("CHECKSUMHASH", itemResponse.getChecksumhash());
        inResponse.putString("BANKNAME", "");
        inResponse.putString("ORDERID", itemResponse.getOrderid());
        inResponse.putString("TXNAMOUNT", itemResponse.getTxnamount());
        inResponse.putString("MID", itemResponse.getMid());
        inResponse.putString("TXNID", itemResponse.getTxnid());
        inResponse.putString("RESPCODE", itemResponse.getRespcode());
        inResponse.putString("PAYMENTMODE", itemResponse.getPaymentmode());
        inResponse.putString("BANKTXNID", itemResponse.getBanktxnid());
        inResponse.putString("CURRENCY", itemResponse.getCurrency());
        inResponse.putString("GATEWAYNAME", itemResponse.getGatewayname());
        inResponse.putString("TXNDATE", itemResponse.getTxndate());
        inResponse.putString("RESPMSG", itemResponse.getRespmsg());
        return inResponse;

        /*{"BANKTXNID":"124393918986","CHECKSUMHASH":"APrEn/NS/lJIxkogDFQIGY+Z6msurKi5w97QWr3kSJsS0+5UlFVMTZzFcJtCcrdQS6awldvrvWI0D8C0lVBoUOHQqCtgIlu/5beg8brAtNs\u003d","CURRENCY":"INR","GATEWAYNAME":"PPBEX","MID":"Ambika03730389235960","ORDERID":"2720070","PAYMENTMODE":"UPI","RESPCODE":"01","RESPMSG":"Txn Success","STATUS":"TXN_SUCCESS","TXNAMOUNT":"1.00","TXNDATE":"2021-08-31 11:44:54.0","TXNID":"20210831111212800110168220137664247"}*/
    }

    private Bundle paytmFailedData(RequestPTM requestPTM, int errorCode, String status, String errorMsg) {
        Bundle inResponse = new Bundle();
        inResponse.putString("STATUS", status);
        inResponse.putString("CHECKSUMHASH", requestPTM.getChecksumhash());
        inResponse.putString("BANKNAME", "");
        inResponse.putString("ORDERID", requestPTM.getOrdeR_ID());
        inResponse.putString("TXNAMOUNT", requestPTM.getTxN_AMOUNT());
        inResponse.putString("MID", requestPTM.getMid());
        inResponse.putString("TXNID", "");
        inResponse.putString("RESPCODE", errorCode + "");
        inResponse.putString("PAYMENTMODE", "");
        inResponse.putString("BANKTXNID", "");
        inResponse.putString("CURRENCY", "INR");
        inResponse.putString("GATEWAYNAME", "");
        inResponse.putString("RESPMSG", errorMsg);

        return inResponse;
    }

    private void initUpdatePaytmSdk(RequestPTM requestPTM, String token) {
        String host = "https://securegw.paytm.in/";
        ptmRequest = requestPTM;
        PaytmOrder paytmOrder = new PaytmOrder(requestPTM.getOrdeR_ID(), requestPTM.getMid(), token, amountEt.getText().toString(), requestPTM.getCallbacK_URL());

        TransactionManager transactionManager = new TransactionManager(paytmOrder, this);
        // transactionManager.setAppInvokeEnabled(false);
        transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(this, INTENT_REQUEST_CODE_PAYTM);
    }

    @Override
    public void onTransactionResponse(Bundle inResponse) {
        paytmCallBackApi(inResponse);
    }

    @Override
    public void networkNotAvailable() {
        paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", "Network not available"));
    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {
        paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", inErrorMessage));
    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {
        paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", inErrorMessage));
    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
        paytmCallBackApi(paytmFailedData(ptmRequest, iniErrorCode, "TXN_CANCEL", inErrorMessage));
    }

    @Override
    public void onBackPressedCancelTransaction() {
        paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", "Transaction canceled by user"));
    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
        paytmCallBackApi(inResponse);
    }

    @Override
    public void onErrorProceed(String string) {
        paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", string));
    }

    /*---------------Initiate UPIGatway-----------*/
    private void initUPIPayGateWay(String amount, final UPIGatewayRequest upiGatewayRequest) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_payment_upi, null);
        View closeBtn = viewMyLayout.findViewById(R.id.closeBtn);
        View btn_pay = viewMyLayout.findViewById(R.id.upiBtn);
        final EditText amountTv = viewMyLayout.findViewById(R.id.amountEt);
        final EditText vpaEt = viewMyLayout.findViewById(R.id.vpaEt);

        amountTv.setText("" + amount);
        amountTv.setEnabled(false);

        gatewayDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        gatewayDialog.setCancelable(false);
        gatewayDialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        gatewayDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gatewayDialog.dismiss();
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amountEt.getText().toString().isEmpty()) {
                    amountEt.setError(getResources().getString(R.string.err_empty_field));
                    amountEt.requestFocus();
                } else if (vpaEt.getText().toString().isEmpty()) {
                    vpaEt.setError(getResources().getString(R.string.err_empty_field));
                    vpaEt.requestFocus();
                } else {
                    if (!isUpiValid(vpaEt.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), "Invalid UPI", Toast.LENGTH_LONG).show();
                        return;
                    }

                    openPaymentGatwayUPI(upiGatewayRequest.getKeyVals(), vpaEt);

                }
            }


        });

        gatewayDialog.show();
        Window window = gatewayDialog.getWindow();
        window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
    }

    private void openPaymentGatwayUPI(KeyVals vals, EditText vpaEt) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority("upigateway.com").appendPath("gateway").appendPath("android").appendQueryParameter("key", vals.getKey()).appendQueryParameter("client_vpa", vpaEt.getText().toString().trim()).appendQueryParameter("client_txn_id", vals.getClientTxnId()).appendQueryParameter("amount", vals.getAmount() + "").appendQueryParameter("p_info", vals.getpInfo()).appendQueryParameter("client_name", vals.getClientName()).appendQueryParameter("client_email", vals.getClientEmail()).appendQueryParameter("client_mobile", vals.getClientMobile()).appendQueryParameter("udf1", vals.getUdf1()).appendQueryParameter("udf2", vals.getUdf2()).appendQueryParameter("udf3", vals.getUdf3()).appendQueryParameter("redirect_url", vals.getRedirectUrl());

        Intent intent = new Intent(Intent.ACTION_VIEW, builder.build());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        gatewayDialog.dismiss();
        if (!isFromRecharge) {
            amountEt.setText("");
        }
        try {
            getApplicationContext().startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null);
            getApplicationContext().startActivity(intent);
        }
    }

    public boolean isUpiValid(String text) {
        return text.matches("^[\\w-]+@\\w+$");
    }


    /*---------------Initiate AggrePay Gat way-----------*/

    void initAggrePaySdk(KeyVals mKeyVals) {
        PaymentParams pgPaymentParams = new PaymentParams();
        pgPaymentParams.setAPiKey(mKeyVals.getApiKey() + "");
        pgPaymentParams.setAmount(mKeyVals.getAmount() + "");
        pgPaymentParams.setEmail(mKeyVals.getEmail() + "");
        pgPaymentParams.setName(mKeyVals.getName() + "");
        pgPaymentParams.setPhone(mKeyVals.getPhone() + "");
        pgPaymentParams.setOrderId(mKeyVals.getOrderId() + "");
        pgPaymentParams.setCurrency(mKeyVals.getCurrency() + "");
        pgPaymentParams.setDescription(mKeyVals.getDescription() + "");
        pgPaymentParams.setCity(mKeyVals.getCity() + "");
        pgPaymentParams.setState(mKeyVals.getState() + "");
        /*pgPaymentParams.setAddressLine1(SampleAppConstants.PG_ADD_1);
        pgPaymentParams.setAddressLine2(SampleAppConstants.PG_ADD_2);*/
        pgPaymentParams.setZipCode(mKeyVals.getZipCode() + "");
        pgPaymentParams.setCountry(mKeyVals.getCountry() + "");
        pgPaymentParams.setReturnUrl(mKeyVals.getReturnUrl() != null && (mKeyVals.getReturnUrl().contains("http") || mKeyVals.getReturnUrl().contains("www.")) ? mKeyVals.getReturnUrl() + "" : ApplicationConstant.INSTANCE.baseUrl + mKeyVals.getReturnUrl() + "");
        pgPaymentParams.setMode(mKeyVals.getMode() + "");
        /*pgPaymentParams.setUdf1(SampleAppConstants.PG_UDF1);
        pgPaymentParams.setUdf2(SampleAppConstants.PG_UDF2);
        pgPaymentParams.setUdf3(SampleAppConstants.PG_UDF3);
        pgPaymentParams.setUdf4(SampleAppConstants.PG_UDF4);
        pgPaymentParams.setUdf5(SampleAppConstants.PG_UDF5);*/

        PaymentGatewayPaymentInitializer pgPaymentInitialzer = new PaymentGatewayPaymentInitializer(pgPaymentParams, this);
        pgPaymentInitialzer.initiatePaymentProcess();
    }

    void initRazorPaySdk(RequestRazorPay mRequestRazorPay) {

        Checkout checkout = new Checkout();
        checkout.setKeyID(mRequestRazorPay.getKey_id());
        checkout.setImage(R.drawable.app_full_logo);
        razorpayOrderid = mRequestRazorPay.getOrder_id() + "";
        try {
            JSONObject options = new JSONObject();
            options.put("name", mRequestRazorPay.getPrefill_name());
            options.put("theme.color", "#" + Integer.toHexString(ContextCompat.getColor(this, R.color.colorPrimary) & 0x00ffffff));
            options.put("prefill.contact", mRequestRazorPay.getPrefill_contact());
            options.put("prefill.name", mRequestRazorPay.getPrefill_name());
            options.put("prefill.email", mRequestRazorPay.getPrefill_email());

            if (selectedMethod != null) {
                if (selectedMethod.toLowerCase().contains("card")) {
                    options.put("prefill.method", "card");
                }
                if (selectedMethod.toLowerCase().contains("net banking") || selectedMethod.toLowerCase().contains("netbanking")) {
                    options.put("prefill.method", "netbanking");
                }
                if (selectedMethod.toLowerCase().contains("upi")) {
                    options.put("prefill.method", "upi");
                }
                if (selectedMethod.toLowerCase().contains("wallet")) {
                    options.put("prefill.method", "wallet");
                }
                if (selectedMethod.toLowerCase().contains("emi")) {
                    options.put("prefill.method", "emi");
                }
            }
            if (mRequestRazorPay.getDescription() != null && !mRequestRazorPay.getDescription().isEmpty()) {
                options.put("description", mRequestRazorPay.getDescription());
            }
            options.put("image", ApplicationConstant.INSTANCE.baseUrl + mRequestRazorPay.getImage());
            options.put("order_id", mRequestRazorPay.getOrder_id() + "");
            options.put("currency", "INR");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", (mRequestRazorPay.getAmount() * 100));

            checkout.open(AddMoneyActivity.this, options);
        } catch (Exception e) {
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        setResult(RESULT_OK);
        ApiFintechUtilMethods.INSTANCE.isReadyToUpdateBalance = true;
        if (isFromRecharge) {
            Intent intent = new Intent();
            intent.putExtra("TID", razorpayOrderid);
            intent.putExtra("ORDERID", razorpayOrderid);
            intent.putExtra("BANKTXNID", razorpayPaymentID);
            intent.putExtra("Status","Success");
            intent.putExtra("TXNID", razorpayPaymentID);
            intent.putExtra("txnRef", razorpayPaymentID);
            setResult(RESULT_OK, intent);
            finish();
        } else if (isFromCart) {
            finish();
        } else {
            setResult(RESULT_OK);
            if (!isFromRecharge) {
                amountEt.setText("");
            }
            ApiFintechUtilMethods.INSTANCE.Successful(AddMoneyActivity.this, "Money Sucessfully Added");
            ApiFintechUtilMethods.INSTANCE.Balancecheck(AddMoneyActivity.this, loader, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, mEKYCStepsDialog, object -> {
                balanceCheckResponse = (BalanceResponse) object;
                if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null && balanceCheckResponse.getBalanceData().size() > 0) {
                    showWalletListPopupWindow();
                }
            });
        }
    }

    @Override
    public void onPaymentError(int code, String response) {
        ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, response + "");
    }


    public void AggrePayTransactionUpdate(String tid, int amount, String hash) {
        try {
            if (!isActivityPause) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call = git.AggrePayTransactionUpdate(new AggrePayTransactionUpdateRequest(tid, amount, hash, mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 2) {
                                ApiFintechUtilMethods.INSTANCE.isReadyToUpdateBalance = true;
                                if (!isFromRecharge) {
                                    amountEt.setText("");
                                }
                                ApiFintechUtilMethods.INSTANCE.Balancecheck(AddMoneyActivity.this, loader, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, mEKYCStepsDialog, new ApiFintechUtilMethods.ApiCallBack() {
                                    @Override
                                    public void onSucess(Object object) {
                                        balanceCheckResponse = (BalanceResponse) object;
                                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null && balanceCheckResponse.getBalanceData().size() > 0) {
                                            showWalletListPopupWindow();
                                        }
                                    }
                                });
                                if (isFromRecharge) {

                                    Intent intent = new Intent();
                                    intent.putExtra("TID", tid);
                                    intent.putExtra("ORDERID", tid);
                                    intent.putExtra("BANKTXNID", tid);
                                    intent.putExtra("TXNID", tid);
                                    intent.putExtra("Status","Success");
                                    intent.putExtra("txnRef", hash);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else if (isFromCart) {
                                    setResult(RESULT_OK);
                                    finish();
                                } else {
                                setResult(RESULT_OK);
                                    if (!isActivityPause) {
                                        ApiFintechUtilMethods.INSTANCE.Successful(AddMoneyActivity.this, "Money Added Sucessfully");
                                    } else {
                                        isDialogShowBackground = true;
                                        dialogMsg = "Money Added Sucessfully";
                                        isSucessDialog = true;
                                    }
                                }
                            } else if (response.body().getStatuscode() == 1) {
                                ApiFintechUtilMethods.INSTANCE.isReadyToUpdateBalance = true;
                                if (!isFromRecharge) {
                                    amountEt.setText("");
                                }
                                if (isFromRecharge) {
                                    Intent intent = new Intent();
                                    intent.putExtra("TID", tid);
                                    intent.putExtra("ORDERID", tid);
                                    intent.putExtra("BANKTXNID", tid);
                                    intent.putExtra("TXNID", tid);
                                    intent.putExtra("Status","Pending");
                                    intent.putExtra("txnRef", hash);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else if (isFromCart) {
                                    setResult(RESULT_OK);
                                    finish();
                                } else {
                                    setResult(RESULT_OK);
                                    if (!isActivityPause) {
                                        ApiFintechUtilMethods.INSTANCE.Processing(AddMoneyActivity.this, "Your transcation under process, please wait 48 Hour to confirmation.");
                                    } else {
                                        isDialogShowBackground = true;
                                        dialogMsg = "Your transcation under process, please wait 48 Hour to confirmation.";
                                        isSucessDialog = true;
                                    }
                                }
                            } else {

                                if (!isActivityPause) {
                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getMsg() + "");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = response.body().getMsg() + "";
                                    isSucessDialog = false;
                                }
                            }

                        } else {

                            if (!isActivityPause) {
                                ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = getString(R.string.some_thing_error);
                                isSucessDialog = false;
                            }
                        }

                    } catch (Exception e) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = e.getMessage() + "";
                            isSucessDialog = false;
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                if (!isActivityPause) {
                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.err_msg_network));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = getString(R.string.err_msg_network);
                                    isSucessDialog = false;
                                }
                            } else {

                                if (!isActivityPause) {
                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, t.getMessage());
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    isSucessDialog = false;
                                }
                            }

                        } else {

                            if (!isActivityPause) {
                                ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = getString(R.string.some_thing_error);
                                isSucessDialog = false;
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = getString(R.string.some_thing_error);
                            isSucessDialog = false;
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing()) {
                loader.dismiss();
            }

            if (!isActivityPause) {
                ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
            } else {
                isDialogShowBackground = true;
                dialogMsg = e.getMessage() + "";
                isSucessDialog = false;
            }
        }
    }

    public void GatewayTransaction(final PaymentGatewayType paymentGatewayType) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<AppUserListResponse> call = git.GatewayTransaction(new GatewayTransactionRequest(amountEt.getText().toString(), paymentGatewayType.getId() + "", selectedWalletId + "", selectedOPId + "", mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getpGModelForApp() != null) {
                                        if (response.body().getpGModelForApp().getStatuscode() == 1) {
                                            requestAmount = amountEt.getText().toString();
                                            currentPGId = response.body().getpGModelForApp().getPgid();
                                            if (response.body().getpGModelForApp().getPgid() == 1 || paymentGatewayType.getPgType() == 1) {
                                                if (response.body().getpGModelForApp().getRequestPTM() != null) {
                                                    initPaytmSdk(response.body().getpGModelForApp().getRequestPTM());
                                                } else {
                                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getpGModelForApp().getMsg() + "");
                                                }
                                            } else if (response.body().getpGModelForApp().getPgid() == 2 || paymentGatewayType.getPgType() == 2) {
                                                if (response.body().getpGModelForApp().getrPayRequest() != null) {
                                                    initRazorPaySdk(response.body().getpGModelForApp().getrPayRequest());
                                                } else {
                                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getpGModelForApp().getMsg() + "");
                                                }
                                            } else if (response.body().getpGModelForApp().getPgid() == 3 || paymentGatewayType.getPgType() == 3) {

                                                initUpi();

                                            } else if (response.body().getpGModelForApp().getPgid() == 4 || paymentGatewayType.getPgType() == 4) {
                                                if (response.body().getpGModelForApp().getAggrePayRequest() != null) {
                                                    if (response.body().getpGModelForApp().getAggrePayRequest().getStatuscode() == 1) {
                                                        if (response.body().getpGModelForApp().getAggrePayRequest().getKeyVals() != null) {
                                                            initAggrePaySdk(response.body().getpGModelForApp().getAggrePayRequest().getKeyVals());
                                                        } else {
                                                            ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, "Keys values not found.");
                                                        }
                                                        //AggrePay
                                                    } else {
                                                        ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getpGModelForApp().getAggrePayRequest().getMsg() + "");
                                                    }

                                                } else {
                                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getpGModelForApp().getMsg() + "");
                                                }
                                            } else if (response.body().getpGModelForApp().getPgid() == 5 || paymentGatewayType.getPgType() == 5) {
                                                if (response.body().getpGModelForApp().getUpiGatewayRequest() != null && response.body().getpGModelForApp().getUpiGatewayRequest().getKeyVals() != null) {
                                                    initUPIPayGateWay(amountEt.getText().toString().trim(), response.body().getpGModelForApp().getUpiGatewayRequest());

                                                } else {
                                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getpGModelForApp().getMsg() + "");
                                                }
                                            } else if (response.body().getpGModelForApp().getPgid() == 7 || paymentGatewayType.getPgType() == 7) {
                                                if (response.body().getpGModelForApp().getRequestPTM() != null && response.body().getpGModelForApp().getToken() != null && !response.body().getpGModelForApp().getToken().isEmpty()) {
                                                    initUpdatePaytmSdk(response.body().getpGModelForApp().getRequestPTM(), response.body().getpGModelForApp().getToken());
                                                } else {
                                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getpGModelForApp().getMsg() + "");
                                                }
                                            } else if (response.body().getpGModelForApp().getPgid() == 8 || paymentGatewayType.getPgType() == 8) {
                                                if (response.body().getpGModelForApp().getCashFreeResponse() != null) {
                                                    if (response.body().getpGModelForApp().getCashFreeResponse().getToken() != null && !response.body().getpGModelForApp().getCashFreeResponse().getToken().isEmpty()) {

                                                        if (response.body().getpGModelForApp().getCashFreeResponse().getOrderID() != null && !response.body().getpGModelForApp().getCashFreeResponse().getOrderID().isEmpty()) {
                                                            transactioID = response.body().getpGModelForApp().getTransactionID();
                                                            token = response.body().getpGModelForApp().getCashFreeResponse().getToken();
                                                            orderId = response.body().getpGModelForApp().getCashFreeResponse().getOrderID();
                                                            //cFItemData=response.body().getpGModelForApp().getCashFreeResponse();
                                                            cFItemData = response.body().getpGModelForApp().getCashFreeResponse();
                                                            initCashFreeSdk();
                                                        } else {
                                                            ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, "OrderId Required");
                                                        }
                                                    } else {
                                                        ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, "Token Required");
                                                    }

                                                } else {
                                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, "Data is not available");
                                                }
                                            } else if (response.body().getpGModelForApp().getPgid() == 10 || paymentGatewayType.getPgType() == 10) {
                                                if (response.body().getpGModelForApp().getUpiGatewayRequest() != null) {
                                                    if (response.body().getpGModelForApp().getUpiGatewayRequest().getUrl() != null && URLUtil.isValidUrl(response.body().getpGModelForApp().getUpiGatewayRequest().getUrl())) {
                                                        upiTID = response.body().getpGModelForApp().getTid() + "";
                                                        showUPIWebview(response.body().getpGModelForApp().getUpiGatewayRequest().getUrl());
                                                        // openUpiWeb(response.body().getpGModelForApp().getUpiGatewayRequest().getUrl());
                                                    } else {
                                                        ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, "Url is not available");
                                                    }

                                                } else {
                                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, "Data is not available");
                                                }
                                            } else if (currentPGId == 12 || paymentGatewayType.getPgType() == 12) {
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
                                                                ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, "Url is not available");
                                                            }
                                                        }
                                                    } else {
                                                        ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getpGModelForApp().getUpiGatewayRequest().getMsg() + "");
                                                    }

                                                } else {
                                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, "Data is not available");
                                                }
                                            } else {
                                                ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, "SDK is not available");

                                            }
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getpGModelForApp().getMsg() + "");
                                        }
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getMsg() + " " + getString(R.string.some_thing_error));
                                    }
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getMsg() + "");
                                }

                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(AddMoneyActivity.this, response.code(), response.message());
                        }
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        ApiFintechUtilMethods.INSTANCE.apiFailureError(AddMoneyActivity.this, t);

                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
        }
    }


    private void openUpiWeb(String url) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (ActivityNotFoundException ex1) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
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
            loader.show();
            if (currentPGId == 12) {
                isPendingTimerComplete = false;
                callAllUPIStatusUpdate(true);
            } else {
                callUPIWebUpdate(true);
            }
        });
        uiWebViewDialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
    }

    public void callAllUPIStatusUpdate(boolean isLoaderShow) {
        try {
            if (!isActivityPause && isLoaderShow) {
                loader.show();
            }
            isAllUPIStatusCheckRunning = true;
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GatwayStatusCheckResponse> call = git.AllUPIStatusCheck(upiTID);

            call.enqueue(new Callback<GatwayStatusCheckResponse>() {
                @Override
                public void onResponse(Call<GatwayStatusCheckResponse> call, final retrofit2.Response<GatwayStatusCheckResponse> response) {
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                GatwayStatusCheckResponse apiData = response.body();
                                if (apiData.getStatus() != null && !apiData.getStatus().equalsIgnoreCase("0")
                                        && apiData.getStatuscode() == 1) {
                                    if (apiData.getStatus().equalsIgnoreCase("2") ||
                                            apiData.getStatus().toLowerCase().equalsIgnoreCase("success")) {
                                        if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
                                            pendingTimerDialog.dismiss();
                                        }
                                        if (!isFromRecharge) {
                                            amountEt.setText("");
                                        }
                                        ApiFintechUtilMethods.INSTANCE.Balancecheck(AddMoneyActivity.this, loader,
                                                mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, mEKYCStepsDialog, new ApiFintechUtilMethods.ApiCallBack() {
                                                    @Override
                                                    public void onSucess(Object object) {
                                                        balanceCheckResponse = (BalanceResponse) object;
                                                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                                                && balanceCheckResponse.getBalanceData().size() > 0) {
                                                            showWalletListPopupWindow();
                                                        }
                                                    }
                                                });

                                        dialogMsg = "Your transaction is successfully completed";
                                        if (!isActivityPause) {
                                            ApiFintechUtilMethods.INSTANCE.SuccessfulWithTitle(AddMoneyActivity.this, "TXN SUCCESS", dialogMsg);
                                        } else {
                                            isDialogShowBackground = true;
                                            sucessDialogStatus = 2;
                                        }

                                    } else if (apiData.getStatus().equalsIgnoreCase("3") ||
                                            apiData.getStatus().toLowerCase().equalsIgnoreCase("failed")) {
                                        if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
                                            pendingTimerDialog.dismiss();
                                        }
                                        dialogMsg = "Your transaction failed";
                                        if (!isActivityPause) {
                                            ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TXN FAILED", dialogMsg);
                                        } else {
                                            isDialogShowBackground = true;
                                            sucessDialogStatus = 3;
                                        }

                                    } else {
                                        if (!isFromRecharge) {
                                            amountEt.setText("");
                                        }


                                        if (isAllUpiDialogCanceled) {
                                            isAllUpiDialogCanceled = false;
                                            dialogMsg = "Transaction cancelled by user";
                                            if (!isActivityPause) {
                                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TXN CANCEL", dialogMsg);
                                            } else {
                                                isDialogShowBackground = true;
                                                sucessDialogStatus = 3;
                                            }
                                        } else {

                                            if (!isPendingTimerComplete && (pendingTimerDialog == null || !pendingTimerDialog.isShowing())) {
                                                showPendingTimerDialog();
                                            } else {
                                                if (isPendingTimerComplete) {
                                                    isPendingTimerComplete = false;
                                                    if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
                                                        pendingTimerDialog.dismiss();
                                                    }
                                                    dialogMsg = "Your Order TID-" + upiTID + " for Amount " + getString(R.string.rupiya) + requestAmount + " is awaited from Bank Side \n" + "We will Update once we get Response From bank Side";
                                                    if (!isActivityPause) {
                                                        ApiFintechUtilMethods.INSTANCE.ProcessingWithTitle(AddMoneyActivity.this, "TXN PENDING", dialogMsg);
                                                    } else {
                                                        isDialogShowBackground = true;
                                                        sucessDialogStatus = 1;
                                                    }
                                                }
                                            }

                                        }


                                    }

                                } else if (apiData.getStatuscode() == 0 || apiData.getStatuscode() == 1) {
                                    if (!isFromRecharge) {
                                        amountEt.setText("");
                                    }
                                    if (isAllUpiDialogCanceled) {
                                        isAllUpiDialogCanceled = false;
                                        dialogMsg = "Transaction cancelled by user";
                                        if (!isActivityPause) {
                                            ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TXN CANCEL", dialogMsg);
                                        } else {
                                            isDialogShowBackground = true;
                                            sucessDialogStatus = 3;
                                        }
                                    } else {

                                        if (!isPendingTimerComplete && (pendingTimerDialog == null ||
                                                (pendingTimerDialog != null && !pendingTimerDialog.isShowing()))) {
                                            showPendingTimerDialog();
                                        } else {
                                            if (isPendingTimerComplete) {
                                                isPendingTimerComplete = false;
                                                if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
                                                    pendingTimerDialog.dismiss();
                                                }
                                                dialogMsg = "Your Order TID-" + upiTID + " for Amount " + getString(R.string.rupiya) + requestAmount + " is awaited from Bank Side \n" + "We will Update once we get Response From bank Side";
                                                if (!isActivityPause) {
                                                    ApiFintechUtilMethods.INSTANCE.ProcessingWithTitle(AddMoneyActivity.this, "TXN PENDING", dialogMsg);
                                                } else {
                                                    isDialogShowBackground = true;
                                                    sucessDialogStatus = 1;
                                                }
                                            }
                                        }

                                    }


                                } else if (apiData.getStatuscode() == 2) {
                                    if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
                                        pendingTimerDialog.dismiss();
                                    }
                                    if (!isFromRecharge) {
                                        amountEt.setText("");
                                    }
                                    ApiFintechUtilMethods.INSTANCE.Balancecheck(AddMoneyActivity.this, loader,
                                            mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, mEKYCStepsDialog, new ApiFintechUtilMethods.ApiCallBack() {
                                                @Override
                                                public void onSucess(Object object) {
                                                    balanceCheckResponse = (BalanceResponse) object;
                                                    if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                                            && balanceCheckResponse.getBalanceData().size() > 0) {
                                                        showWalletListPopupWindow();
                                                    }
                                                }
                                            });

                                    dialogMsg = "Your transaction is successfully completed";
                                    if (!isActivityPause) {
                                        ApiFintechUtilMethods.INSTANCE.SuccessfulWithTitle(AddMoneyActivity.this, "TXN SUCCESS", dialogMsg);
                                    } else {
                                        isDialogShowBackground = true;
                                        sucessDialogStatus = 2;
                                    }

                                } else if (apiData.getStatuscode() == 3) {
                                    if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
                                        pendingTimerDialog.dismiss();
                                    }
                                    dialogMsg = "Your transaction failed";
                                    if (!isActivityPause) {
                                        ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TXN FAILED", dialogMsg);
                                    } else {
                                        isDialogShowBackground = true;
                                        sucessDialogStatus = 3;
                                    }

                                } else {
                                    if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
                                        pendingTimerDialog.dismiss();
                                    }
                                    if (apiData.getStatus() != null && !apiData.getStatus().equalsIgnoreCase("3")) {
                                        dialogMsg = "Your transaction failed";
                                        if (!isActivityPause) {
                                            ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TXN FAILED", dialogMsg);
                                        } else {
                                            isDialogShowBackground = true;
                                            sucessDialogStatus = 3;
                                        }
                                    } else {

                                        if (!isActivityPause) {
                                            ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                                        } else {
                                            isDialogShowBackground = true;
                                            dialogMsg = getString(R.string.some_thing_error);
                                            sucessDialogStatus = 3;
                                        }
                                    }
                                }


                            } else {

                                if (!isActivityPause) {
                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = getString(R.string.some_thing_error);
                                    sucessDialogStatus = 3;
                                }
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(AddMoneyActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = e.getMessage() + "";
                            sucessDialogStatus = 3;
                        }
                    }
                    isAllUPIStatusCheckRunning = false;
                }

                @Override
                public void onFailure(Call<GatwayStatusCheckResponse> call, Throwable t) {
                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (!isActivityPause) {
                                ApiFintechUtilMethods.INSTANCE.NetworkError(AddMoneyActivity.this);

                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = getString(R.string.err_msg_network);
                                sucessDialogStatus = 3;
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (!isActivityPause) {
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = t.getMessage();
                                sucessDialogStatus = 3;
                            }
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                if (!isActivityPause) {
                                    ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    sucessDialogStatus = 3;
                                }
                            } else {
                                if (!isActivityPause) {
                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    sucessDialogStatus = 3;
                                }
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = getString(R.string.some_thing_error);
                            sucessDialogStatus = 3;
                        }
                    }
                    isAllUPIStatusCheckRunning = false;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            isAllUPIStatusCheckRunning = false;
            if (loader.isShowing()) {
                loader.dismiss();
            }
            if (!isActivityPause) {
                ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
            } else {
                isDialogShowBackground = true;
                dialogMsg = e.getMessage() + "";
                sucessDialogStatus = 3;
            }
        }
    }

    private void showPendingTimerDialog() {

        if (pendingTimerDialog != null && pendingTimerDialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_pending_timer, null);

        View closeBtn = viewMyLayout.findViewById(R.id.closeBtn);
        TextView minuteTv = viewMyLayout.findViewById(R.id.minute);
        TextView secondTv = viewMyLayout.findViewById(R.id.second);


        pendingTimerDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        pendingTimerDialog.setCancelable(false);
        pendingTimerDialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        pendingTimerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closeBtn.setOnClickListener(v -> {

            isPendingTimerComplete = true;
            pendingTimerDialog.dismiss();
            callAllUPIStatusUpdate(true);
        });

        pendingTimerDialog.setOnDismissListener(dialogInterface -> {
            if (countDownTimerPendingTxn != null) {
                countDownTimerPendingTxn.cancel();
                countDownTimerPendingTxn = null;
            }
        });
        if (countDownTimerPendingTxn != null) {
            countDownTimerPendingTxn.cancel();
            countDownTimerPendingTxn = null;
        }
        isPendingTimerComplete = false;
        countDownTimerPendingTxn = new CountDownTimer((long) (61 * 1000), 1000) {
            int count = -3;

            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
               /* String ms = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                bonusTime.setText(ms); */
                // String hr = String.format("%02d", TimeUnit.MILLISECONDS.toHours(millis));
                String min = String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(millis) /*- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))*/);
                String sec = String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

                // hourTv.setText(hr);
                minuteTv.setText(min);
                secondTv.setText(sec);
                if (!isAllUPIStatusCheckRunning) {
                    count = count + 1;
                    if (count == 13) {
                        count = 6;
                        callAllUPIStatusUpdate(false);
                    }
                }
            }

            public void onFinish() {
                isPendingTimerComplete = true;
                pendingTimerDialog.dismiss();
                callAllUPIStatusUpdate(true);
            }
        }.start();
        pendingTimerDialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);


    }

    private void callUPIWebUpdate(boolean isStatus) {


        // loader.show();
        final boolean finalIsStatus = isStatus;
        ApiFintechUtilMethods.INSTANCE.CashFreeStatusCheck(this,upiTID, loader, new ApiFintechUtilMethods.ApiResponseCallBack() {
            @Override
            public void onSucess(Object response) {
                ApiFintechUtilMethods.INSTANCE.isReadyToUpdateBalance = true;
                setResult(RESULT_OK);
                if (isFromCart) {
                    finish();
                } else {
                    if (!finalIsStatus) {
                        InitiateUpiResponse apiData = (InitiateUpiResponse) response;
                        if (/*apiData.getStatuscode() == 0 || */apiData.getStatuscode() == 1) {
                            if (!isFromRecharge) {
                                amountEt.setText("");
                            }
                            ApiFintechUtilMethods.INSTANCE.Balancecheck(AddMoneyActivity.this, loader, mLoginDataResponse,
                                    deviceId, deviceSerialNum, mAppPreferences, mEKYCStepsDialog, object -> {
                                        balanceCheckResponse = (BalanceResponse) object;
                                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                                            showWalletListPopupWindow();
                                        }
                                    });

                            dialogMsg = "Your transaction is pending";
                            if (!isActivityPause) {
                                ApiFintechUtilMethods.INSTANCE.Processing(AddMoneyActivity.this, dialogMsg);
                            } else {
                                isDialogShowBackground = true;
                                isSucessDialog = true;
                            }

                        }
                        else if (apiData.getStatuscode() == 2) {
                            if (!isFromRecharge) {
                                amountEt.setText("");
                            }
                            ApiFintechUtilMethods.INSTANCE.Balancecheck(AddMoneyActivity.this, loader, mLoginDataResponse,
                                    deviceId, deviceSerialNum, mAppPreferences, mEKYCStepsDialog, object -> {
                                        balanceCheckResponse = (BalanceResponse) object;
                                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                                            showWalletListPopupWindow();
                                        }
                                    });

                            dialogMsg = "Your transaction is successfully completed";
                            if (!isActivityPause) {
                                ApiFintechUtilMethods.INSTANCE.Successful(AddMoneyActivity.this, dialogMsg);
                            } else {
                                isDialogShowBackground = true;
                                isSucessDialog = true;
                            }

                        } else {
                            dialogMsg = "Your transaction failed";
                            if (!isActivityPause) {
                                ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, dialogMsg);
                            } else {
                                isDialogShowBackground = true;
                                isSucessDialog = false;
                            }

                        }

                    }

                }
            }

            @Override
            public void onError(int error) {
                if(error ==ApiFintechUtilMethods.INSTANCE.ERROR_OTHER) {
                    dialogMsg = "Your transaction failed";
                    if (!isActivityPause) {
                        //ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, dialogMsg);
                    } else {
                        isDialogShowBackground = true;
                        isSucessDialog = false;
                    }
                }
            }
        });

    }

    public void PayTMTransactionUpdate(JsonObject jsonObj) {
        try {
            if (!isActivityPause) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call = git.PayTMTransactionUpdate(new PayTMTransactionUpdateRequest(jsonObj, mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    ApiFintechUtilMethods.INSTANCE.isReadyToUpdateBalance = true;
                                    if (!isFromRecharge) {
                                        amountEt.setText("");
                                    }
                                    ApiFintechUtilMethods.INSTANCE.Balancecheck(AddMoneyActivity.this, loader,
                                            mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, mEKYCStepsDialog, object -> {
                                                balanceCheckResponse = (BalanceResponse) object;
                                                if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                                        && balanceCheckResponse.getBalanceData().size() > 0) {
                                                    showWalletListPopupWindow();
                                                }
                                            });

                                    if (isFromRecharge) {

                                        Intent intent = new Intent();
                                        intent.putExtra("TID", jsonObj.get("TXNID").getAsString());
                                        intent.putExtra("ORDERID", jsonObj.get("ORDERID").getAsString());
                                        intent.putExtra("Status",jsonObj.get("STATUS").getAsString());
                                        intent.putExtra("BANKTXNID", jsonObj.get("BANKTXNID").getAsString());
                                        intent.putExtra("TXNID", jsonObj.get("TXNID").getAsString());
                                        intent.putExtra("txnRef", jsonObj.get("TXNID").getAsString());
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    } else if (isFromCart) {
                                        setResult(RESULT_OK);
                                        finish();
                                    } else {
                                        setResult(RESULT_OK);
                                        if (!isActivityPause) {
                                            ApiFintechUtilMethods.INSTANCE.Successful(AddMoneyActivity.this, "Money Added Sucessfully");
                                        } else {
                                            isDialogShowBackground = true;
                                            dialogMsg = "Money Added Sucessfully";
                                            isSucessDialog = true;
                                        }
                                    }
                                } else {

                                    if (!isActivityPause) {
                                        ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getMsg() + "");
                                    } else {
                                        isDialogShowBackground = true;
                                        dialogMsg = response.body().getMsg() + "";
                                        isSucessDialog = false;
                                    }
                                }

                            } else {

                                if (!isActivityPause) {
                                    ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = getString(R.string.some_thing_error);
                                    isSucessDialog = false;
                                }
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(AddMoneyActivity.this, response.code(), response.message());
                        }
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = e.getMessage() + "";
                            isSucessDialog = false;
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t instanceof UnknownHostException || t instanceof IOException) {
                                if (!isActivityPause) {
                                    ApiFintechUtilMethods.INSTANCE.NetworkError(AddMoneyActivity.this);
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = getString(R.string.err_msg_network);
                                    isSucessDialog = false;
                                }
                            } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                if (!isActivityPause) {
                                    ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = getString(R.string.err_msg_network);
                                    isSucessDialog = false;
                                }
                            } else {
                                if (!isActivityPause) {
                                    if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                        ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "FATAL ERROR", t.getMessage() + "");
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                                    }
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = getString(R.string.err_msg_network);
                                    isSucessDialog = false;
                                }
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = getString(R.string.some_thing_error);
                            isSucessDialog = false;
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }

            if (!isActivityPause) {
                ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
            } else {
                isDialogShowBackground = true;
                dialogMsg = e.getMessage() + "";
                isSucessDialog = false;
            }
        }
    }

    @Override
    protected void onResume() {
        isActivityPause = false;
        super.onResume();

        if (isDialogShowBackground) {
            isDialogShowBackground = false;

            if (isSucessDialog) {
                ApiFintechUtilMethods.INSTANCE.Successful(AddMoneyActivity.this, dialogMsg);
            } else {
                ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, dialogMsg);
            }
        }
    }

    @Override
    protected void onPause() {
        isActivityPause = true;
        super.onPause();
    }

    @Override
    protected void onStart() {
        isActivityPause = false;
        super.onStart();
    }

    @Override
    protected void onStop() {
        isActivityPause = true;
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_UPI) {

            if (data != null && resultCode == RESULT_OK) {
                final String paymentResponse, txnId, status, txnRef, ApprovalRefNo, TrtxnRef, responseCode, bleTxId;
                paymentResponse = data.getStringExtra("response");
                txnId = data.getStringExtra("txnId");
                status = data.getStringExtra("Status");
                txnRef = data.getStringExtra("txnRef");
                ApprovalRefNo = data.getStringExtra("ApprovalRefNo");
                TrtxnRef = data.getStringExtra("TrtxnRef");
                responseCode = data.getStringExtra("responseCode");
                bleTxId = data.getStringExtra("bleTxId");/*TrtxnRef*/

                /*if ((status == null || status.isEmpty() || status.toLowerCase().contains("null") || status.toLowerCase().contains("undefined"))
                        && paymentResponse != null && !paymentResponse.isEmpty() && !paymentResponse.toLowerCase().equalsIgnoreCase("undefined")
                        && paymentResponse.contains("&") && paymentResponse.contains("=")) {

                    String[] splitArray = TextUtils.split(paymentResponse, "&");

                    for (int i = 0; i < splitArray.length; i++) {
                        if (splitArray[i].contains("txnId=")) {
                            txnId = splitArray[i].replace("txnId=", "").trim();
                        }
                        if (splitArray[i].contains("Status=")) {
                            status = splitArray[i].replace("Status=", "").trim();
                        }
                        if (splitArray[i].contains("txnRef=")) {
                            txnRef = splitArray[i].replace("txnRef=", "").trim();
                        }
                        if (splitArray[i].contains("ApprovalRefNo=")) {
                            ApprovalRefNo = splitArray[i].replace("ApprovalRefNo=", "").trim();
                        }
                        if (splitArray[i].contains("TrtxnRef=")) {
                            TrtxnRef = splitArray[i].replace("TrtxnRef=", "").trim();
                        }
                        if (splitArray[i].contains("responseCode=")) {
                            responseCode = splitArray[i].replace("responseCode=", "").trim();
                        }
                        if (splitArray[i].contains("bleTxId=")) {
                            bleTxId = splitArray[i].replace("bleTxId=", "").trim();
                        }
                    }

                }*/
                if (isFromRecharge) {
                    Intent intent = new Intent();
                    intent.putExtra("TID", upiTID);
                    intent.putExtra("ORDERID", txnRef);
                    intent.putExtra("BANKTXNID", bleTxId);
                    intent.putExtra("Status",status);
                    intent.putExtra("TXNID", txnId);
                    intent.putExtra("txnRef", TrtxnRef);
                    intent.putExtra("isFromUpi", true);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    boolean isStatus = false;
                    if (status != null && !status.isEmpty()) {
                        isStatus = true;
                        if (status.toLowerCase().equalsIgnoreCase("success")) {
                            ApiFintechUtilMethods.INSTANCE.SuccessfulWithTitle(AddMoneyActivity.this, "Success", "Transaction Successful.");
                        } else if (status.toLowerCase().equalsIgnoreCase("submitted")) {
                            ApiFintechUtilMethods.INSTANCE.ProcessingWithTitle(AddMoneyActivity.this, "Submitted", "Transaction submitted, Please wait some time for confirmation.");
                        } else {
                            ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "Failed", "Transaction Failed, Please Try After Some Time.");
                        }

                    }

                    // loader.show();
                    final boolean finalIsStatus = isStatus;
                    ApiFintechUtilMethods.INSTANCE.UPIPaymentUpdate(AddMoneyActivity.this, upiTID, status + "", loader,
                            mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
                                @Override
                                public void onSucess(Object object) {
                                    ApiFintechUtilMethods.INSTANCE.isReadyToUpdateBalance = true;
                                    setResult(RESULT_OK);
                                    if (isFromCart) {
                                        finish();
                                    } else {
                                        if (!finalIsStatus) {
                                            InitiateUpiResponse apiData = (InitiateUpiResponse) object;
                                            ApiFintechUtilMethods.INSTANCE.Successful(AddMoneyActivity.this, apiData.getMsg() + "");
                                        }
                                    }
                                }

                                @Override
                                public void onError(int error) {

                                }
                            });

                /*Toast.makeText(PaymentRequest.this, data.getExtras() + "", Toast.LENGTH_LONG).show();
                 Log.e("UPI",paymentResponse+"");
                   Log.e("UPI",data.getExtras()+"");
                   Log.e("UPI",data.getData()+"");*/

                    /*Bundle[{Status=Success, isExternalMerchant=true, txnRef=uDK7591578462474072lpif, TRANSACTION_STATUS=3, response=txnId=Airpay1139Rjc1578462474071&txnRef=uDK7591578462474072lpif&Status=Success&responseCode=00, bleTxId=P2001081118229818376376, txnId=Airpay1139Rjc1578462474071, responseCode=00}]*/
                    /*Bundle[{Status=SUCCESS, txnRef=fjD8091578483039469owLn, ApprovalRefNo=, response=txnId=Airpay1297Fyo1578483039469&responseCode=0&Status=SUCCESS&txnRef=fjD8091578483039469owLn, txnId=Airpay1297Fyo1578483039469, responseCode=0, TrtxnRef=fjD8091578483039469owLn}]*/

                    /*txnId=AXI17152979abdf4b2b9a9e141083936913&responseCode=&Status=SUBMITTED&txnRef=*/
                    /*txnId=SBIfa2b4b8c907a4226bf8829e8769b55f7&responseCode=UP00&Status=SUCCESS&txnRef=000817212057*/
                    /*txnId=139Zas1578403940921Ys4T&responseCode=ZD&Status=FAILURE&txnRef=1375qE1578403940921BFMf*/
                }
            }
        } else if (requestCode == INTENT_UPI_WEB) {

            if (data != null && resultCode == RESULT_OK) {

                String paymentResponse = data.getStringExtra("response");
                String txnId = data.getStringExtra("txnId");
                String status = data.getStringExtra("Status");
                String txnRef = data.getStringExtra("txnRef");
                String ApprovalRefNo = data.getStringExtra("ApprovalRefNo");
                String TrtxnRef = data.getStringExtra("TrtxnRef");
                String responseCode = data.getStringExtra("responseCode");
                String bleTxId = data.getStringExtra("bleTxId");/*TrtxnRef*/


                boolean isStatus = false;
                if (status != null && !status.isEmpty()) {
                    isStatus = true;
                    if (status.toLowerCase().equalsIgnoreCase("success")) {
                        ApiFintechUtilMethods.INSTANCE.SuccessfulWithTitle(AddMoneyActivity.this, "Success", "Transaction Successful.");
                    } else if (status.toLowerCase().equalsIgnoreCase("submitted")) {
                        ApiFintechUtilMethods.INSTANCE.ProcessingWithTitle(AddMoneyActivity.this, "Submitted", "Transaction submitted, Please wait some time for confirmation.");
                    } else {
                        ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "Failed", "Transaction Failed, Please Try After Some Time.");
                    }

                }


                callUPIWebUpdate(isStatus);


            }
        } else if (requestCode == PGConstants.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    String paymentResponse = data.getStringExtra(PGConstants.PAYMENT_RESPONSE);

                    if (paymentResponse == null || paymentResponse.equals("null")) {
                        ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, "Transaction Error!");
                        // transactionIdView.setText("Transaction ID: NIL");
                        //transactionStatusView.setText("Transaction Status: Transaction Error!");
                    } else {
                        JSONObject response = new JSONObject(paymentResponse);
                        AggrePayTransactionUpdate(response.optString("order_id"), response.optInt("amount"), response.optString("hash"));
                        //Log.e("Aggre Pay", "Transaction ID: " + response.getString("transaction_id"));

                        //Log.e("Aggre Pay", "Transaction Status: " + response.getString("response_message"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                ApiFintechUtilMethods.INSTANCE.Error(AddMoneyActivity.this, "Transaction Canceled!");
            }

        } else if (requestCode == INTENT_REQUEST_CODE_PAYTM) {
            if (resultCode == RESULT_OK && data != null) {

                if (data.getStringExtra("response") != null && !data.getStringExtra("response").isEmpty()) {


                    try {
                        PayTmSuccessItemResponse itemResponse = gson.fromJson(data.getStringExtra("response"), PayTmSuccessItemResponse.class);
                   /* JSONObject jsonObject=new JSONObject();
                    JSONObject jsonItem=  jsonObject.getJSONObject(data.getStringExtra("response"));*/
                        paytmCallBackApi(payTmSuccessData(itemResponse));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", "Transaction canceled"));
                }


               /* Log.e(TAG, " PayTm response 1- " + data.getStringExtra("response"));
                Log.e(TAG, " PayTm response 2- " + data.getStringExtra("nativeSdkForMerchantMessage"));
*/
            } else {
                paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", "Transaction Failed"));
            }
        } else if (requestCode == CFPaymentService.REQ_CODE) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {

                    JSONObject responseJson = transResponseBundleToJsonOb(bundle);
                    try {
                        String txnStatus = responseJson.getString("txStatus");
                        //SUCCESS--
                        if (txnStatus != null && !txnStatus.isEmpty() && txnStatus.equalsIgnoreCase("SUCCESS")) {
                            isTransactionCancelledByUser = false;
                            cashFreeCallBackApi(cashFreeSuccessData(cFItemData, responseJson, 2));

                        } else if (txnStatus != null && !txnStatus.isEmpty() && txnStatus.equalsIgnoreCase("PENDING")) {
                            isTransactionCancelledByUser = false;
                            cashFreeCallBackApi(cashFreeSuccessData(cFItemData, responseJson, 1));

                        } else if (txnStatus != null && !txnStatus.isEmpty() && txnStatus.equalsIgnoreCase("CANCELLED")) {
                            isTransactionCancelledByUser = true;
                            cashFreeCallBackApi(cashFreeFailedData(cFItemData, 0, "TXN_CANCEL", "Transaction canceled by user"));

                        } else if (txnStatus != null && !txnStatus.isEmpty() && txnStatus.equalsIgnoreCase("FAILED")) {
                            isTransactionCancelledByUser = false;
                            cashFreeCallBackApi(cashFreeSuccessData(cFItemData, responseJson, 3));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            } else {
                ApiFintechUtilMethods.INSTANCE.Error(this, "Transaction Failed");

            }
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
                        Toast.makeText(AddMoneyActivity.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
                    }
                } catch (ActivityNotFoundException anfe) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        Intent chooser = Intent.createChooser(intent, "Pay with...");
                        startActivityForResult(chooser, INTENT_UPI_WEB);
                    } catch (ActivityNotFoundException anfe1) {
                        Toast.makeText(AddMoneyActivity.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
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
}
