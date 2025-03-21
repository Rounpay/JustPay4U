package com.solution.app.justpay4u.Fintech.UpiPayment.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Request.SendMoneyUPIRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UPIPaymentRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RechargeReportResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.VPAVerifyResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.MoveToWallet.Adapter.WalletBalanceAdapter;
import com.solution.app.justpay4u.Fintech.UpiPayment.UpiHandleApp;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.EKYCStepsDialog;
import com.solution.app.justpay4u.Util.Utility;

import retrofit2.Call;
import retrofit2.Callback;

public class QRPayActivity extends AppCompatActivity {

    private EditText amountEt, remarkEt, nameEt;
    TextView amtErr, upiAppName, error, payBtn;
    ImageView upiAppLogo;
    View upiDetailView,/* amountView,*/veryfyBtn;
    private String amountQr,nameQr, upiQr;
    private ProgressBar progress;
    private CustomLoader loader;
    private LoginResponse LoginDataResponse;
    RecyclerView walletBalance;
    private BalanceResponse balanceCheckResponse;
    TextView upiIdTv, shortNameTv, amtWordTv;
    private String deviceId, deviceSerialNum;
    private AppPreferences mAppPreferences;
    private String senderNum;
    private RequestOptions requestOptions;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_qr_pay);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            senderNum = getIntent().getStringExtra("SenderNum");
            nameQr = getIntent().getStringExtra("Name");
            upiQr = getIntent().getStringExtra("UPI");
            amountQr = getIntent().getStringExtra("Amount");
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            LoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            requestOptions.error(R.drawable.ic_upi_icon);
            requestOptions.placeholder(R.drawable.ic_upi_icon);

            walletBalance = findViewById(R.id.walletBalance);
            walletBalance.setLayoutManager(new LinearLayoutManager(this));
            nameEt = findViewById(R.id.name);
            upiDetailView = findViewById(R.id.upiDetailView);
            progress = findViewById(R.id.progress);
            error = findViewById(R.id.error);
//            amountView = findViewById(R.id.amountView);
            upiAppLogo = findViewById(R.id.appLogo);
            upiAppName = findViewById(R.id.appName);
            upiIdTv = findViewById(R.id.upiId);
            shortNameTv = findViewById(R.id.shortName);
            amtWordTv = findViewById(R.id.amtWord);
            payBtn = findViewById(R.id.payBtn);
            veryfyBtn = findViewById(R.id.veryfyBtn);
            amtErr = findViewById(R.id.amtErr);
            amountEt = findViewById(R.id.amount);
            remarkEt = findViewById(R.id.remark);
            if (amountQr != null && !amountQr.isEmpty()) {
                amountEt.setText(Utility.INSTANCE.formatedAmountWithOutRupees(amountQr));
                amountEt.clearFocus();
            }

      /*  amountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    amtErr.setVisibility(View.GONE);
                    amtWordTv.setVisibility(View.VISIBLE);
                    try {
                        amtWordTv.setText(NumberToWords(Integer.parseInt(s.toString())));
                    } catch (NumberFormatException nfe) {

                    } catch (Exception e) {

                    }
                } else {
                    amtErr.setVisibility(View.GONE);
                    amtWordTv.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
            findViewById(R.id.closeIv).setOnClickListener(v -> finish());

            veryfyBtn.setOnClickListener(view -> {
                if (upiQr != null && upiQr.contains("@")) {
                    VerifyUPI(this, upiQr);
                } else {
                    ApiFintechUtilMethods.INSTANCE.Error(QRPayActivity.this, "UPI is invalid");
                }
            });
            payBtn.setOnClickListener(v -> {
                amtErr.setVisibility(View.GONE);
                nameEt.setError(null);
                if (nameEt.getText().toString().trim().isEmpty()) {
                    nameEt.setError("Please enter name");
                    nameEt.requestFocus();
                    return;
                } else if (amountEt.getText().toString().trim().isEmpty()) {
                    amtErr.setText("Please enter valid amount");
                    amtErr.setVisibility(View.VISIBLE);
                    amountEt.requestFocus();
                    return;
                }


                confirmationDialog();

            });
            balanceCheckResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
            showWalletListPopupWindow();
        });
    }


    private void showWalletListPopupWindow() {

        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {

            checkSetIntentData();

            WalletBalanceAdapter mAdapter = new WalletBalanceAdapter(this, balanceCheckResponse.getBalanceData());
            walletBalance.setAdapter(mAdapter);

        } else {
            EKYCStepsDialog mEKYCStepsDialog = new EKYCStepsDialog(this, LoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);
            ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, LoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, mEKYCStepsDialog, object -> {
                balanceCheckResponse = (BalanceResponse) object;
                if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {

                    showWalletListPopupWindow();
                }
            });

        }

    }

    void checkSetIntentData() {
        upiIdTv.setText(upiQr + "");
        setHandleImage(upiQr + "");
        if (nameQr != null && !nameQr.isEmpty()) {
            nameEt.setText(nameQr.replaceAll("[^A-Za-z ]", ""));
            setShortName();
            if (balanceCheckResponse.isAutoVerifyVPA()) {
                if (upiQr != null && upiQr.contains("@")) {

                    if (Utility.INSTANCE.isSpecialCharacter(nameQr)) {
                        veryfyBtn.setVisibility(View.GONE);
                        VerifyUPI(this, upiQr);
                    } else {
                        payBtn.setVisibility(View.VISIBLE);
                        veryfyBtn.setVisibility(View.VISIBLE);
                    }

                }else {
                    ApiFintechUtilMethods.INSTANCE.Error(QRPayActivity.this, "UPI is invalid");
                }
            } else {
                payBtn.setVisibility(View.VISIBLE);
                veryfyBtn.setVisibility(View.VISIBLE);
            }

        } else {
            nameEt.setFocusable(true);
            nameEt.setFocusableInTouchMode(true);
            nameEt.setClickable(true);
            nameEt.setLongClickable(true);
            shortNameTv.setVisibility(View.GONE);
            payBtn.setVisibility(View.VISIBLE);
        }


    }



    public void VerifyUPI(final Activity context, String vpaStr) {
        try {
            progress.setVisibility(View.VISIBLE);
            UPIPaymentRequest paymentReq = new UPIPaymentRequest(vpaStr, LoginDataResponse.getData().getUserID() + "",
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSession(),
                    LoginDataResponse.getData().getSessionID());

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<VPAVerifyResponse> call = git.VerifyUPI(paymentReq);

            call.enqueue(new Callback<VPAVerifyResponse>() {

                @Override
                public void onResponse(Call<VPAVerifyResponse> call, retrofit2.Response<VPAVerifyResponse> response) {

                    progress.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        payBtn.setVisibility(View.VISIBLE);
                        if (response.body() != null) {

                            if (response.body().getStatuscode() == 1) {
                                if (response.body().getData() != null) {
                                    if (response.body().getData().getAccountHolder() != null && !response.body().getData().getAccountHolder().isEmpty()) {

                                        veryfyBtn.setVisibility(View.GONE);
                                        nameQr = response.body().getData().getAccountHolder().trim();
                                        nameEt.setText(nameQr.replaceAll("[^A-Za-z ]", ""));
                                        nameEt.setFocusable(false);
                                        nameEt.setFocusableInTouchMode(false);
                                        nameEt.setClickable(false);
                                        nameEt.setLongClickable(false);
                                        setShortName();
                                    } else {

                                        veryfyBtn.setVisibility(View.GONE);
                                        nameEt.setFocusable(true);
                                        nameEt.setFocusableInTouchMode(true);
                                        nameEt.setClickable(true);
                                        nameEt.setLongClickable(true);
                                        error.setVisibility(View.VISIBLE);
//                                        amountView.setVisibility(View.VISIBLE);
                                        if (response.body().getData().getStatuscode() == -1) {
                                            ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getData().getMsg() + "");
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(context, "UPI is not available");
                                        }

                                    }
                                } else {
                                    error.setVisibility(View.VISIBLE);
//                                    amountView.setVisibility(View.VISIBLE);
                                    ApiFintechUtilMethods.INSTANCE.Error(context, "UPI is not available");
                                }


                            } else {
                                error.setVisibility(View.VISIBLE);
//                                amountView.setVisibility(View.VISIBLE);
                                if (!response.body().isVersionValid()) {
                                    ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                       /* error.setVisibility(View.VISIBLE);
                        amountView.setVisibility(View.VISIBLE);*/
                        ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<VPAVerifyResponse> call, Throwable t) {
                    /*error.setVisibility(View.VISIBLE);
                    amountView.setVisibility(View.VISIBLE);*/
                    progress.setVisibility(View.GONE);

                    try {
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        ApiFintechUtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
           /* error.setVisibility(View.VISIBLE);
            amountView.setVisibility(View.VISIBLE);*/
            progress.setVisibility(View.GONE);
        }

    }

    void setShortName() {
        String initials = "";
        String[] nameList = nameQr.trim().split(" ");
        for (String s : nameList) {
            if (initials.length() < 2 && !s.matches("\\s*")) {
                initials += s.charAt(0);
            } else {
                break;
            }
        }
        if (initials != null && !initials.isEmpty()) {
            shortNameTv.setVisibility(View.VISIBLE);
            shortNameTv.setText(initials + "");
        } else {
            shortNameTv.setVisibility(View.GONE);
        }
    }

    void setHandleImage(String vpaStr) {
        if (vpaStr != null && vpaStr.contains("@")) {
            String vpaHandle = vpaStr.substring(vpaStr.indexOf("@") + 1);
            upiDetailView.setVisibility(View.VISIBLE);
//            amountView.setVisibility(View.VISIBLE);
            error.setVisibility(View.GONE);
            upiAppName.setText(UpiHandleApp.INSTANCE.getAppName(vpaHandle));
            upiAppLogo.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.upiHandleLogoUrl + vpaHandle.trim() + ".png")
                    .apply(requestOptions).into(upiAppLogo);
        }
    }

    public String NumberToWords(int number) {
        if (number == 0) {
            return "zero";
        }
        if (number < 0) {
            return "minus " + NumberToWords(Math.abs(number));
        }
        String words = "";
        if ((number / 10000000) > 0) {
            words += NumberToWords(number / 10000000) + " Crore ";
            number %= 10000000;
        }
        if ((number / 100000) > 0) {
            words += NumberToWords(number / 100000) + " Lakh ";
            number %= 100000;
        }
        if ((number / 1000) > 0) {
            words += NumberToWords(number / 1000) + " Thousand ";
            number %= 1000;
        }
        if ((number / 100) > 0) {
            words += NumberToWords(number / 100) + " Hundred ";
            number %= 100;
        }
        if (number > 0) {
            if (words != "") {
                words += ""/*"and "*/;
            }
            String[] unitsMap = new String[]{"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
            String[] tensMap = new String[]{"Zero", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "seventy", "Eighty", "Ninety"};
            if (number < 20) {
                words += unitsMap[number];
            } else {
                words += tensMap[number / 10];
                if ((number % 10) > 0) {
                    words += " "/*"-"*/ + unitsMap[number % 10];
                }
            }
        }
        return words;
    }

    public void confirmationDialog() {

        if (dialog != null && dialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_upi_transfer_confiormation, null);

        View pinPassView = view.findViewById(R.id.pinPassView);
        EditText pinPassEt = view.findViewById(R.id.pinPassEt);
        ((TextView) view.findViewById(R.id.bankName)).setText(upiAppName.getText().toString() + "");
        ((TextView) view.findViewById(R.id.accountNo)).setText(upiIdTv.getText().toString() + "");
        ((TextView) view.findViewById(R.id.name)).setText(nameEt.getText().toString());
        ((ImageView) view.findViewById(R.id.vpaIv)).setImageDrawable(upiAppLogo.getDrawable());
        /* ((TextView) view.findViewById(R.id.mode)).setText(paymentModeTv.getText().toString());*/

        ((TextView) view.findViewById(R.id.amountOnlyTv)).setText(Utility.INSTANCE.formatedAmountWithRupees(amountEt.getText().toString() + ""));
        ((TextView) view.findViewById(R.id.amountTv)).setText(Utility.INSTANCE.formatedAmountWithRupees(amountEt.getText().toString() + ""));
        ((TextView) view.findViewById(R.id.dmtChargeTv)).setText(Utility.INSTANCE.formatedAmountWithRupees("0"));
        ((TextView) view.findViewById(R.id.totalAmtTv)).setText(Utility.INSTANCE.formatedAmountWithRupees(amountEt.getText().toString() + ""));

        dialog = new Dialog(this, R.style.alert_dialog_light);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (LoginDataResponse.getData().getIsPinRequired() || LoginDataResponse.getData().getIsDoubleFactor()) {
            pinPassView.setVisibility(View.VISIBLE);
        } else {
            pinPassView.setVisibility(View.GONE);
        }
        view.findViewById(R.id.cancel).setOnClickListener(v -> dialog.dismiss());
        view.findViewById(R.id.ok).setOnClickListener(v -> {

            if (pinPassView.getVisibility() == View.VISIBLE && pinPassEt.getText().length() == 0) {
                pinPassEt.setError(getString(R.string.err_empty_field));
                pinPassEt.requestFocus();
                return;
            }
            dialog.dismiss();
            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
                amountEt.setFocusable(false);
                remarkEt.setFocusable(false);
                setResult(RESULT_OK);
                loader.show();
                doUPIPayment(QRPayActivity.this, pinPassEt.getText().toString());

            } else {
                ApiFintechUtilMethods.INSTANCE.NetworkError(this);
            }
        });
        dialog.show();

    }

    public void doUPIPayment(final Activity context, String pinPass) {
        try {
            loader.show();
            UPIPaymentRequest paymentReq = new UPIPaymentRequest(pinPass, new SendMoneyUPIRequest(upiIdTv.getText().toString().trim(), amountEt.getText().toString(),
                    nameEt.getText().toString().trim().replaceAll("[^A-Za-z ]", ""), senderNum), LoginDataResponse.getData().getUserID() + "",
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSession(), LoginDataResponse.getData().getSessionID());

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.doUPIPayment(paymentReq);

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    if (loader != null && loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {

                            if (response.body().getStatuscode() == 1 || response.body().getStatuscode() == 2) {

                                //ApiFintechUtilMethods.INSTANCE.Successfulok(response.body().getMsg(), context);
                                amountEt.setText("");
                                remarkEt.setText("");
                                amountEt.setFocusable(true);
                                remarkEt.setFocusable(true);
                                amountEt.setFocusableInTouchMode(true);
                                remarkEt.setFocusableInTouchMode(true);
                                ApiFintechUtilMethods.INSTANCE.GetDMTReceipt(context, response.body().getTransactionID(), "All",
                                        LoginDataResponse, deviceId, deviceSerialNum, loader, mAppPreferences);

                            } else if (response.body().getStatuscode() == 3) {
                                amountEt.setFocusable(true);
                                remarkEt.setFocusable(true);
                                amountEt.setFocusableInTouchMode(true);
                                remarkEt.setFocusableInTouchMode(true);
                                ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");


                            } else {
                                amountEt.setFocusable(true);
                                remarkEt.setFocusable(true);
                                amountEt.setFocusableInTouchMode(true);
                                remarkEt.setFocusableInTouchMode(true);
                              /*  if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }*/
                                ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                            }

                        }
                    } else {
                        amountEt.setFocusable(true);
                        remarkEt.setFocusable(true);
                        amountEt.setFocusableInTouchMode(true);
                        remarkEt.setFocusableInTouchMode(true);
                        ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    amountEt.setFocusable(true);
                    remarkEt.setFocusable(true);
                    amountEt.setFocusableInTouchMode(true);
                    remarkEt.setFocusableInTouchMode(true);
                    if (loader != null && loader.isShowing())
                        loader.dismiss();

                    try {
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        ApiFintechUtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            amountEt.setFocusable(true);
            remarkEt.setFocusable(true);
            amountEt.setFocusableInTouchMode(true);
            remarkEt.setFocusableInTouchMode(true);
            if (loader != null && loader.isShowing())
                loader.dismiss();
        }

    }


}
