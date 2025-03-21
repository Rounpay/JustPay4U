package com.solution.app.justpay4u.Fintech.UpiPayment.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.VPAList;
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

public class UPIPayActivity extends AppCompatActivity implements View.OnClickListener {

    private String vpaVal, nameVal, senderNum;
    private ImageView vpaIv;
    private TextView appName;
    private View veryfyBtn;
    private EditText vpaEdt, amountEdt, beneNamEdt;
    private Button upiPayBtn;
    private CustomLoader loader;
    private RecyclerView walletBalance;
    private BalanceResponse balanceCheckResponse;
    private AppPreferences mAppPreferences;
    private VPAList mVPAData;
    private RequestOptions requestOptions;
    private LoginResponse loginResponse;
    private String deviceSerialNum, deviceId;
    private Dialog dialog;
    private String onlyCharacterPattern = "[a-zA-Z ]+";
    private boolean isAutoVerifyVPA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_upi_pay);

            appName = findViewById(R.id.appName);
            veryfyBtn = findViewById(R.id.veryfyBtn);
            upiPayBtn = findViewById(R.id.btn_upiPay);
            vpaIv = findViewById(R.id.vpaIv);
            walletBalance = findViewById(R.id.walletBalance);
            walletBalance.setLayoutManager(new LinearLayoutManager(this));
            vpaEdt = findViewById(R.id.edit_vpa);
            amountEdt = findViewById(R.id.edit_amount);
            beneNamEdt = findViewById(R.id.edit_beneName);


            upiPayBtn.setOnClickListener(this::onClick);
            vpaEdt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!beneNamEdt.getText().toString().isEmpty()) {
                        beneNamEdt.setText("");
                        appName.setText("");
                        appName.setVisibility(View.GONE);
                        vpaIv.setVisibility(View.GONE);
                        vpaIv.setImageResource(R.drawable.ic_upi_icon);
                        if (isAutoVerifyVPA) {
                            veryfyBtn.setVisibility(View.GONE);
                        } else {
                            veryfyBtn.setVisibility(View.VISIBLE);
                        }
                        beneNamEdt.setFocusable(true);
                        beneNamEdt.setClickable(true);
                        beneNamEdt.setLongClickable(true);
                        beneNamEdt.setFocusableInTouchMode(true);
                        ViewCompat.setBackgroundTintList(beneNamEdt, null);

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            veryfyBtn.setOnClickListener(view -> {
                if (!vpaEdt.getText().toString().isEmpty() && vpaEdt.getText().toString().contains("@")) {
                    VerifyUPI(this, vpaEdt.getText().toString());
                } else {
                    vpaEdt.setError("Please enter valid UPI Id");
                    vpaEdt.requestFocus();
                    return;
                }
            });

            new Handler(Looper.getMainLooper()).post(() -> {
                mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
                deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
                deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
                loginResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
                loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);


                requestOptions = new RequestOptions();
                requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
                requestOptions.error(R.drawable.ic_upi_icon);
                requestOptions.placeholder(R.drawable.ic_upi_icon);
                if (getIntent() != null) {
                    vpaVal = getIntent().getStringExtra("UPI");
                    nameVal = getIntent().getStringExtra("Name");
                    senderNum = getIntent().getStringExtra("SenderNum");
                    mVPAData = getIntent().getParcelableExtra("VPAData");
                }

                balanceCheckResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
                showWalletListPopupWindow();

            });

        });

    }


    void setVpaData() {
        if (isAutoVerifyVPA) {
            veryfyBtn.setVisibility(View.GONE);
            beneNamEdt.setOnFocusChangeListener((view, b) -> {
                if (b && !vpaEdt.getText().toString().isEmpty() && vpaEdt.getText().toString().contains("@")) {
                    VerifyUPI(this, vpaEdt.getText().toString());
                }
            });
        } else {
            veryfyBtn.setVisibility(View.VISIBLE);
        }


        if (mVPAData != null) {
            if (mVPAData.getVpa() != null && !mVPAData.getVpa().isEmpty()) {
                vpaEdt.setText(mVPAData.getVpa());
                vpaEdt.setFocusable(false);
                vpaEdt.setClickable(false);
                vpaEdt.setLongClickable(false);
                vpaEdt.setFocusableInTouchMode(false);
                ViewCompat.setBackgroundTintList(vpaEdt, ContextCompat.getColorStateList(this, R.color.devider_color));

                if (mVPAData.isVerified()) {
                    veryfyBtn.setVisibility(View.GONE);
                    setHandleImage(mVPAData.getVpa());
                } else {
                    if (isAutoVerifyVPA) {
                        if (Utility.INSTANCE.isSpecialCharacter(mVPAData.getAccountHolder())) {
                            veryfyBtn.setVisibility(View.GONE);
                            VerifyUPI(this, mVPAData.getVpa());
                        } else {
                            veryfyBtn.setVisibility(View.VISIBLE);
                        }
                        /*veryfyBtn.setVisibility(View.GONE);
                        VerifyUPI(this, mVPAData.getVpa());*/

                    } else {
                        veryfyBtn.setVisibility(View.VISIBLE);
                    }
                }
            }
            if (mVPAData.getAccountHolder() != null && !mVPAData.getAccountHolder().isEmpty()) {
                beneNamEdt.setText(mVPAData.getAccountHolder());
                beneNamEdt.setFocusable(false);
                beneNamEdt.setClickable(false);
                beneNamEdt.setLongClickable(false);
                beneNamEdt.setFocusableInTouchMode(false);

                ViewCompat.setBackgroundTintList(beneNamEdt, ContextCompat.getColorStateList(this, R.color.devider_color));
            }

        } else {
            if (vpaVal != null && !vpaVal.isEmpty()) {
                vpaEdt.setText(vpaVal);
            }

            if (nameVal != null && !nameVal.isEmpty()) {
                beneNamEdt.setText(nameVal.replaceAll("[^A-Za-z ]", ""));
            }
        }
    }

    void setHandleImage(String vpaStr) {
        if (vpaStr != null && vpaStr.contains("@")) {
            String vpaHandle = vpaStr.substring(vpaStr.indexOf("@") + 1);
            appName.setText(UpiHandleApp.INSTANCE.getAppName(vpaHandle));
            appName.setVisibility(View.VISIBLE);
            vpaIv.setVisibility(View.VISIBLE);
            veryfyBtn.setVisibility(View.GONE);
            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.upiHandleLogoUrl + vpaHandle.trim() + ".png")
                    .apply(requestOptions).into(vpaIv);
        }
    }

    private void showWalletListPopupWindow() {

        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {

            isAutoVerifyVPA = balanceCheckResponse.isAutoVerifyVPA();
            setVpaData();

            WalletBalanceAdapter mAdapter = new WalletBalanceAdapter(this, balanceCheckResponse.getBalanceData());
            walletBalance.setAdapter(mAdapter);
        } else {
            EKYCStepsDialog mEKYCStepsDialog = new EKYCStepsDialog(this, loginResponse, deviceId, deviceSerialNum, mAppPreferences);
            ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, loginResponse, deviceId, deviceSerialNum, mAppPreferences, mEKYCStepsDialog, object -> {
                balanceCheckResponse = (BalanceResponse) object;
                if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                    showWalletListPopupWindow();
                }
            });

        }

    }

    @Override
    public void onClick(View clickView) {

        if (clickView == upiPayBtn) {
            if (validView()) {
                confirmationDialog();
                //doUPIPayment(this);
            }
        }
    }


    public void doUPIPayment(final Activity context, String pinpass) {
        try {
            loader.show();

            UPIPaymentRequest paymentReq = new UPIPaymentRequest(pinpass, new SendMoneyUPIRequest(vpaEdt.getText().toString().trim(),
                    amountEdt.getText().toString(), beneNamEdt.getText().toString(), senderNum), loginResponse.getData().getUserID() + "", loginResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, loginResponse.getData().getSession(), loginResponse.getData().getSessionID());

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.doUPIPayment(paymentReq);

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {


                    if (response.isSuccessful()) {
                        if (response.body() != null) {

                            if (response.body().getStatuscode() == 1 || response.body().getStatuscode() == 2) {

                                // ApiFintechUtilMethods.INSTANCE.Successfulok(response.body().getMsg(), context);
                                if (mVPAData == null) {
                                    vpaEdt.setText("");
                                }
                                amountEdt.setText("");
                                ApiFintechUtilMethods.INSTANCE.GetDMTReceipt(context, response.body().getTransactionID(), "All", loginResponse,
                                        deviceId, deviceSerialNum, loader, mAppPreferences);

                            } else if (response.body().getStatuscode() == 3) {
                                if (loader != null && loader.isShowing())
                                    loader.dismiss();
                                ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");


                            } else {
                                if (loader != null && loader.isShowing())
                                    loader.dismiss();
                                ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                               /* if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }*/
                            }

                        }
                    } else {
                        ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

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
            if (loader != null && loader.isShowing())
                loader.dismiss();
        }

    }

    public void VerifyUPI(final Activity context, String vpaStr) {
        try {
            loader.show();
            UPIPaymentRequest paymentReq = new UPIPaymentRequest(vpaStr, loginResponse.getData().getUserID() + "", loginResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, loginResponse.getData().getSession(), loginResponse.getData().getSessionID());

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<VPAVerifyResponse> call = git.VerifyUPI(paymentReq);

            call.enqueue(new Callback<VPAVerifyResponse>() {

                @Override
                public void onResponse(Call<VPAVerifyResponse> call, retrofit2.Response<VPAVerifyResponse> response) {

                    if (loader != null && loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {

                            if (response.body().getStatuscode() == 1) {
                                if (response.body().getData() != null) {
                                    if (response.body().getData().getAccountHolder() != null && !response.body().getData().getAccountHolder().isEmpty()) {
                                        nameVal = response.body().getData().getAccountHolder().trim();
                                        beneNamEdt.setText(nameVal.replaceAll("[^A-Za-z ]", ""));
                                        beneNamEdt.setFocusable(false);
                                        beneNamEdt.setClickable(false);
                                        beneNamEdt.setLongClickable(false);
                                        beneNamEdt.setFocusableInTouchMode(false);
                                        veryfyBtn.setVisibility(View.GONE);
                                        ViewCompat.setBackgroundTintList(beneNamEdt, ContextCompat.getColorStateList(UPIPayActivity.this, R.color.devider_color));
                                        setHandleImage(vpaStr);
                                    } else {
                                        if (response.body().getData().getStatuscode() == -1) {
                                            ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getData().getMsg() + "");
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(context, "UPI is not available");
                                        }

                                    }
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(context, "UPI is not available");
                                }


                            } else {

                                if (!response.body().isVersionValid()) {
                                    ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<VPAVerifyResponse> call, Throwable t) {

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
            if (loader != null && loader.isShowing())
                loader.dismiss();
        }

    }

    public void confirmationDialog() {

        if (dialog != null && dialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_upi_transfer_confiormation, null);

        View pinPassView = view.findViewById(R.id.pinPassView);
        EditText pinPassEt = view.findViewById(R.id.pinPassEt);
        ((TextView) view.findViewById(R.id.bankName)).setText(appName.getText().toString() + "");
        ((TextView) view.findViewById(R.id.accountNo)).setText(vpaEdt.getText().toString() + "");
        ((TextView) view.findViewById(R.id.name)).setText(beneNamEdt.getText().toString());
        ((ImageView) view.findViewById(R.id.vpaIv)).setImageDrawable(vpaIv.getDrawable());
        /* ((TextView) view.findViewById(R.id.mode)).setText(paymentModeTv.getText().toString());*/

        ((TextView) view.findViewById(R.id.amountOnlyTv)).setText(Utility.INSTANCE.formatedAmountWithRupees(amountEdt.getText().toString() + ""));
        ((TextView) view.findViewById(R.id.amountTv)).setText(Utility.INSTANCE.formatedAmountWithRupees(amountEdt.getText().toString() + ""));
        ((TextView) view.findViewById(R.id.dmtChargeTv)).setText(Utility.INSTANCE.formatedAmountWithRupees("0"));
        ((TextView) view.findViewById(R.id.totalAmtTv)).setText(Utility.INSTANCE.formatedAmountWithRupees(amountEdt.getText().toString() + ""));

        dialog = new Dialog(this, R.style.alert_dialog_light);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (loginResponse.getData().getIsPinRequired() || loginResponse.getData().getIsDoubleFactor()) {
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
                setResult(RESULT_OK);
                loader.show();
                doUPIPayment(UPIPayActivity.this, pinPassEt.getText().toString());

            } else {
                ApiFintechUtilMethods.INSTANCE.NetworkError(this);
            }
        });
        dialog.show();

    }

    private boolean validView() {

        if (vpaEdt.getText().toString().isEmpty()) {
            vpaEdt.setError(getResources().getString(R.string.err_empty_field));
            vpaEdt.requestFocus();
            return false;
        }
        if (beneNamEdt.getText().toString().isEmpty()) {
            beneNamEdt.setError(getResources().getString(R.string.err_empty_field));
            beneNamEdt.requestFocus();
            return false;
        } else if (amountEdt.getText().toString().isEmpty()) {
            amountEdt.setError(getResources().getString(R.string.err_empty_field));
            amountEdt.requestFocus();
            return false;
        }
        vpaEdt.setError(null);
        beneNamEdt.setError(null);
        amountEdt.setError(null);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}