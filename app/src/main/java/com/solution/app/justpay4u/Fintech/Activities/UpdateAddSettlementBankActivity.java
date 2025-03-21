package com.solution.app.justpay4u.Fintech.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;

import com.solution.app.justpay4u.Api.Fintech.Object.SettlementAccountData;
import com.solution.app.justpay4u.Api.Fintech.Request.UpdateSettlementAccountRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.BankListScreenActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;

import retrofit2.Call;
import retrofit2.Callback;

public class UpdateAddSettlementBankActivity extends AppCompatActivity {
    DropDownDialog mDropDownDialog;
    SettlementAccountData mSettlementAccountData;
    private CardView bankChooserView;
    private TextView bankTv;
    private AppCompatImageView bankArrow;
    private EditText IFSCEt;
    private EditText AccountNumberEt;
    private EditText AccountNameEt;
    private View submitBtn;
    private CustomLoader loader;
    private String selectedBank = "", ifsc = "";
    private LoginResponse mLoginDataResponse;
    private int INTENT_SELECT_BANK = 8904;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private int updatedId;
    private int bankId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_update_add_settlement_bank);

            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            mDropDownDialog = new DropDownDialog(this);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            findViews();
            onViewClick();
            new Handler(Looper.getMainLooper()).post(() -> {
                mSettlementAccountData = getIntent().getParcelableExtra("Data");
                setData();
            });
        });


    }

    private void findViews() {

        bankChooserView = (CardView) findViewById(R.id.bankChooserView);
        bankTv = (TextView) findViewById(R.id.bankTv);
        bankArrow = (AppCompatImageView) findViewById(R.id.bankArrow);
        IFSCEt = (EditText) findViewById(R.id.IFSCEt);
        AccountNumberEt = (EditText) findViewById(R.id.AccountNumberEt);
        AccountNameEt = (EditText) findViewById(R.id.AccountNameEt);
        submitBtn = findViewById(R.id.bt_submit);


    }

    void onViewClick() {
        findViewById(R.id.closeIv).setOnClickListener(v -> finish());


        bankChooserView.setOnClickListener(v -> {
            Intent bankIntent = new Intent(this, BankListScreenActivity.class);
            bankIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(bankIntent, INTENT_SELECT_BANK);
        });
        submitBtn.setOnClickListener(v -> updateBank());

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


    void setData() {
        if (mSettlementAccountData != null) {

            updatedId = mSettlementAccountData.getId();
            bankId = mSettlementAccountData.getBankID();
            if (mSettlementAccountData.getIfsc() != null && !mSettlementAccountData.getIfsc().isEmpty()) {
                IFSCEt.setText(mSettlementAccountData.getIfsc());
            }
            /*if (mSettlementAccountData.getBranchName() != null && !mSettlementAccountData.getBranchName().isEmpty()) {
                branchNameEt.setText(mSettlementAccountData.getBranchName());
            }*/
            if (mSettlementAccountData.getAccountNumber() != null && !mSettlementAccountData.getAccountNumber().isEmpty()) {
                AccountNumberEt.setText(mSettlementAccountData.getAccountNumber());
            }
            if (mSettlementAccountData.getAccountHolder() != null && !mSettlementAccountData.getAccountHolder().isEmpty()) {
                AccountNameEt.setText(mSettlementAccountData.getAccountHolder());
            }

            if (mSettlementAccountData.getBankName() != null && !mSettlementAccountData.getBankName().isEmpty()) {
                selectedBank = mSettlementAccountData.getBankName();
                bankTv.setText(selectedBank);

            }
        }
    }


    void updateBank() {
        if (bankTv.getText().length() == 0) {
            bankTv.setError("Please Select Bank");
            bankTv.requestFocus();
        } /*else if (branchNameEt.getText().toString().isEmpty()) {
            branchNameEt.setError(getString(R.string.err_empty_field));
            branchNameEt.requestFocus();
        } */ else if (IFSCEt.getText().toString().isEmpty()) {
            IFSCEt.setError(getString(R.string.err_empty_field));
            IFSCEt.requestFocus();
        } else if (AccountNumberEt.getText().toString().isEmpty()) {
            AccountNumberEt.setError(getString(R.string.err_empty_field));
            AccountNumberEt.requestFocus();
        } else if (AccountNameEt.getText().toString().isEmpty()) {
            AccountNameEt.setError(getString(R.string.err_empty_field));
            AccountNameEt.requestFocus();
        } else {
            updateBankApi();
        }
    }


    public void updateBankApi() {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<BasicResponse> call = git.UpdateSettlementAccount(new UpdateSettlementAccountRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(), AccountNameEt.getText().toString(),
                    AccountNumberEt.getText().toString(), bankId, selectedBank, updatedId, IFSCEt.getText().toString()));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            BasicResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    if (data.getData() != null) {
                                        if (data.getData().getStatuscode() == 1) {
                                            ApiFintechUtilMethods.INSTANCE.SuccessfulWithFinish(data.getData().getMsg() + "", com.solution.app.justpay4u.Fintech.Activities.UpdateAddSettlementBankActivity.this, false);

                                            setResult(RESULT_OK);
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.UpdateAddSettlementBankActivity.this, data.getData().getMsg() + "");

                                        }
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Successful(com.solution.app.justpay4u.Fintech.Activities.UpdateAddSettlementBankActivity.this, data.getMsg() + "");

                                        setResult(RESULT_OK);
                                    }

                                } else {
                                    //setResult(RESULT_OK);
                                    ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.UpdateAddSettlementBankActivity.this, data.getMsg() + "");
                                }

                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.UpdateAddSettlementBankActivity.this, getString(R.string.some_thing_error));
                            }
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        } else {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(com.solution.app.justpay4u.Fintech.Activities.UpdateAddSettlementBankActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    try {

                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(com.solution.app.justpay4u.Fintech.Activities.UpdateAddSettlementBankActivity.this, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.UpdateAddSettlementBankActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Activities.UpdateAddSettlementBankActivity.this, getString(R.string.some_thing_error));
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == INTENT_SELECT_BANK && resultCode == RESULT_OK) {


            selectedBank = data.getExtras().getString("bankName");
            ifsc = data.getExtras().getString("ifsc");

            //bankId = data.getExtras().getString("bankId");
            bankId = data.getExtras().getInt("bankIdINT");
            // accVerification = data.getExtras().getString("accVerification");
            // shortCode = data.getExtras().getString("shortCode");
            // isNeft = data.getExtras().getString("neft");
            // isImps = data.getExtras().getString("imps");
            // accLmt = data.getExtras().getString("accLmt");
            // ekO_BankID = data.getExtras().getString("ekO_BankID");

            bankTv.setText("" + selectedBank);
            bankTv.setError(null);
            if (ifsc != null && ifsc.length() > 0 && ifsc.length() > 4) {
                IFSCEt.setText(ifsc + "");
            } else {
                IFSCEt.setText("");
            }
        }
    }


}