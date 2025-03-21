package com.solution.app.justpay4u.Fintech.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.solution.app.justpay4u.Api.Fintech.Object.EditUser;
import com.solution.app.justpay4u.Api.Fintech.Request.UpdateUserRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetUserResponse;
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

public class UpdateBankActivity extends AppCompatActivity {


    DropDownDialog mDropDownDialog;
    GetUserResponse mGetUserResponse;
    private CardView bankChooserView;
    private TextView bankTv;
    private EditText IFSCEt;
    private EditText AccountNumberEt, branchNameEt;
    private EditText AccountNameEt;
    private View submitBtn;
    private CustomLoader loader;

    private String selectedBank = "", ifsc = "";
    private LoginResponse mLoginDataResponse;
    private int INTENT_SELECT_BANK = 8904;
    private AppPreferences mAppPreferences;
    private Gson gson;
    private String deviceId, deviceSerialNum;
    private int bankId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_update_bank);

            gson = new Gson();
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            mDropDownDialog = new DropDownDialog(this);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            findViews();
            onViewClick();
            new Handler(Looper.getMainLooper()).post(() -> {
                mGetUserResponse = ApiFintechUtilMethods.INSTANCE.getUserDetailResponse(mAppPreferences);
                //mGetUserResponse =  getIntent().getParcelableExtra("UserData");
                setUserData();
            });
        });


    }

    private void findViews() {

        bankChooserView = (CardView) findViewById(R.id.bankChooserView);
        bankTv = (TextView) findViewById(R.id.bankTv);
        IFSCEt = (EditText) findViewById(R.id.IFSCEt);
        branchNameEt = (EditText) findViewById(R.id.branchNameEt);
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


    void setUserData() {
        if (mGetUserResponse != null && mGetUserResponse.getUserInfo() != null) {


            if (mGetUserResponse.getUserInfo().getIfsc() != null && !mGetUserResponse.getUserInfo().getIfsc().isEmpty()) {
                IFSCEt.setText(mGetUserResponse.getUserInfo().getIfsc());
            }
            if (mGetUserResponse.getUserInfo().getBranchName() != null && !mGetUserResponse.getUserInfo().getBranchName().isEmpty()) {
                branchNameEt.setText(mGetUserResponse.getUserInfo().getBranchName());
            }
            if (mGetUserResponse.getUserInfo().getAccountNumber() != null && !mGetUserResponse.getUserInfo().getAccountNumber().isEmpty()) {
                AccountNumberEt.setText(mGetUserResponse.getUserInfo().getAccountNumber());
            }
            if (mGetUserResponse.getUserInfo().getAccountName() != null && !mGetUserResponse.getUserInfo().getAccountName().isEmpty()) {
                AccountNameEt.setText(mGetUserResponse.getUserInfo().getAccountName());
            }

            if (mGetUserResponse.getUserInfo().getBankName() != null && !mGetUserResponse.getUserInfo().getBankName().isEmpty()) {
                selectedBank = mGetUserResponse.getUserInfo().getBankName();
                bankTv.setText(selectedBank);

            }
        }
    }


    void updateBank() {
        if (bankTv.getText().length() == 0) {
            bankTv.setError("Please Select Bank");
            bankTv.requestFocus();
        } else if (branchNameEt.getText().toString().isEmpty()) {
            branchNameEt.setError(getString(R.string.err_empty_field));
            branchNameEt.requestFocus();
        } else if (IFSCEt.getText().toString().isEmpty()) {
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
            EditUser editUser = new EditUser(selectedBank, IFSCEt.getText().toString(), AccountNumberEt.getText().toString(),
                    AccountNameEt.getText().toString(), branchNameEt.getText().toString());

            Call<BasicResponse> call = git.UpdateBank(new UpdateUserRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(), editUser));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            BasicResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    ApiFintechUtilMethods.INSTANCE.Successful(UpdateBankActivity.this, data.getMsg() + "");

                                    mGetUserResponse.getUserInfo().setAccountName(AccountNameEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setAccountNumber(AccountNumberEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setIfsc(IFSCEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setBankName(selectedBank);
                                    mGetUserResponse.getUserInfo().setBranchName(branchNameEt.getText().toString());

                                    mAppPreferences.set(ApplicationConstant.INSTANCE.UserDetailPref, gson.toJson(mGetUserResponse));
                                    setResult(RESULT_OK);
                                } else if (response.body().getStatuscode() == -1) {
                                    setResult(RESULT_OK);
                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateBankActivity.this, data.getMsg() + "");
                                } else if (response.body().getStatuscode() == 0) {

                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateBankActivity.this, data.getMsg() + "");
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateBankActivity.this, data.getMsg() + "");
                                }

                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(UpdateBankActivity.this, getString(R.string.some_thing_error));
                            }
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        } else {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(UpdateBankActivity.this, response.code(), response.message());
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
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(UpdateBankActivity.this, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(UpdateBankActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(UpdateBankActivity.this, getString(R.string.some_thing_error));
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == INTENT_SELECT_BANK && resultCode == RESULT_OK) {


            selectedBank = data.getExtras().getString("bankName");
            ifsc = data.getExtras().getString("ifsc");

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