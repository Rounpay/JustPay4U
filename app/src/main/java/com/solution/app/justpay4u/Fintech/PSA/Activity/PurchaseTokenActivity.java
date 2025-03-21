package com.solution.app.justpay4u.Fintech.PSA.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OperatorListResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Reports.Activity.RechargeReportActivity;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.EKYCStepsDialog;

import java.util.ArrayList;

public class PurchaseTokenActivity extends AppCompatActivity {
    RadioGroup radioType;
    TextView tv_url, balanceTv, amountTv;
    EditText et_tokenNo;
    Button purchase, cancle;
    WebView webView;
    CustomLoader loader;
    View tokenphysical, tokendigital;
    TextView tokenphysicalTv, tokendigitalTv;
    RadioButton radioPhysical, radioDigital;
    String PANID;
    int oid;
    double amount = 0.0;
    TextView purchaseHistory;
    AppPreferences mAppPreferences;
    private LoginResponse mLoginDataResponse;
    private String deviceId, deviceSerialNum;
    private EKYCStepsDialog mEKYCStepsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_purchase_token);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            PANID = getIntent().getExtras().getString("PANID", "");
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            mEKYCStepsDialog = new EKYCStepsDialog(this, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);

            radioType = findViewById(R.id.radioType);
            balanceTv = findViewById(R.id.balanceTv);
            amountTv = findViewById(R.id.amountTv);
            et_tokenNo = findViewById(R.id.et_tokenNo);
            purchase = findViewById(R.id.purchase);
            cancle = findViewById(R.id.cancle);
            purchaseHistory = findViewById(R.id.purchaseHistory);
            tokendigital = findViewById(R.id.tokendigital);
            tokenphysical = findViewById(R.id.tokenphysical);
            tokenphysicalTv = findViewById(R.id.tokenphysicalTv);
            tokendigitalTv = findViewById(R.id.tokendigitalTv);
            radioPhysical = findViewById(R.id.radioPhysical);
            radioDigital = findViewById(R.id.radioDigital);
            tv_url = findViewById(R.id.tv_url);


            new Handler(Looper.getMainLooper()).post(() -> {

                OperatorListResponse mOperatorListResponse = ApiFintechUtilMethods.INSTANCE.getOperatorListResponse(mAppPreferences);

                final ArrayList<OperatorList> opIdList = new ArrayList<>();
                if (mOperatorListResponse != null) {
                    ArrayList<OperatorList> operatorsList = mOperatorListResponse.getOperators();
                    if (operatorsList != null && operatorsList.size() > 0) {
                        for (OperatorList op : operatorsList) {
                            if (op.getOpType() == 24) { //For PSA opType is 24
                                opIdList.add(op);
                            }

                        }
                    }
                }

                for (OperatorList operatorList : opIdList) {
                    if (operatorList.getName().contains("Digital")) {
                        tokendigitalTv.setText("1 Token = " + getResources().getString(R.string.rupiya) + " " + operatorList.getMin() + " (Inclusive of Tax) " + operatorList.getName());
                        amount = Double.parseDouble("0" + operatorList.getMin());
                        oid = operatorList.getOid();
                        tokendigital.setVisibility(View.VISIBLE);
                        radioDigital.setVisibility(View.VISIBLE);
                        radioDigital.setChecked(true);
                    }
                    if (operatorList.getName().contains("Physical")) {
                        tokenphysicalTv.setText("1 Token = " + getResources().getString(R.string.rupiya) + " " + operatorList.getMin() + " (Inclusive of Tax) " + operatorList.getName());
                        amount = Double.parseDouble("0" + operatorList.getMin());
                        oid = operatorList.getOid();
                        tokenphysical.setVisibility(View.VISIBLE);
                        radioPhysical.setVisibility(View.VISIBLE);
                        radioPhysical.setChecked(true);
                    }
                }

                BalanceResponse balanceCheckResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);

                if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                        && balanceCheckResponse.getBalanceData().size() > 0) {
                    double prepaidWallet = balanceCheckResponse.getBalanceData().get(0).getBalance();
                    balanceTv.setText(getString(R.string.rupiya) + " " + prepaidWallet);
                }
                findViewById(R.id.radioPhysical).setSelected(true);

                radioType.setOnCheckedChangeListener((group, checkedId) -> {
                    switch (checkedId) {
                        case R.id.radioPhysical:
                            for (OperatorList operatorList : opIdList) {
                                if (operatorList.getName().contains("Physical")) {
                                    oid = operatorList.getOid();
                                    amount = Double.parseDouble("0" + operatorList.getMin());
                                    amountTv.setText("" + Double.parseDouble("0" + et_tokenNo.getText().toString()) * amount);
                                }
                            }
                            break;

                        case R.id.radioDigital:
                            for (OperatorList operatorList : opIdList) {
                                if (operatorList.getName().contains("Digital")) {
                                    oid = operatorList.getOid();
                                    amount = Double.parseDouble("0" + operatorList.getMin());
                                    amountTv.setText("" + Double.parseDouble("0" + et_tokenNo.getText().toString()) * amount);
                                }
                            }
                            break;

                    }
                });
            });
            tv_url.setText("http://www.psaonline.utiitsl.com/psaonline/");

            tv_url.setOnClickListener(view -> {
                webView = new WebView(PurchaseTokenActivity.this);
                webView.loadUrl("http://www.psaonline.utiitsl.com/psaonline/");
            });

            purchaseHistory.setOnClickListener(v ->
                    startActivity(new Intent(PurchaseTokenActivity.this, RechargeReportActivity.class)
                            .putExtra("PSA", true)
                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));


            et_tokenNo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    amountTv.setText("" + Double.parseDouble("0" + et_tokenNo.getText().toString()) * amount);
                }
            });

            purchase.setOnClickListener(view -> {

                if (!et_tokenNo.getText().toString().isEmpty()) {
                    if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(PurchaseTokenActivity.this)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        ApiFintechUtilMethods.INSTANCE.GetAppPurchageToken(PurchaseTokenActivity.this, et_tokenNo.getText().toString(), oid + "",
                                PANID, loader, mLoginDataResponse, deviceId, deviceSerialNum, object -> {
                                    loader.show();
                                    ApiFintechUtilMethods.INSTANCE.Balancecheck(PurchaseTokenActivity.this, loader, mLoginDataResponse,
                                            deviceId, deviceSerialNum, mAppPreferences, mEKYCStepsDialog, object1 -> {
                                                BalanceResponse mBalanceResponse = (BalanceResponse) object1;
                                                if (mBalanceResponse != null && mBalanceResponse.getBalanceData() != null
                                                        && mBalanceResponse.getBalanceData().size() > 0) {
                                                    double prepaidWallet = mBalanceResponse.getBalanceData().get(0).getBalance();
                                                    balanceTv.setText(getString(R.string.rupiya) + " " + prepaidWallet);
                                                }
                                            });
                                });
                    } else {
                        ApiFintechUtilMethods.INSTANCE.NetworkError(PurchaseTokenActivity.this);
                    }
                } else {
                    et_tokenNo.setError("Enter Tokens");
                }

            });

            cancle.setOnClickListener(v -> onBackPressed());
            et_tokenNo.setText("1");
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}

