package com.solution.app.justpay4u.Fintech.Authentication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import com.solution.app.justpay4u.Api.Fintech.Request.ChangePinPasswordRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RechargeReportResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePinPassActivity extends AppCompatActivity {
    View closeIv;
    int intentResult;
    CustomAlertDialog mCustomAlertDialog;
    private TextView title, titleNewPass, titleConfirmPass , noticeMsg,oldPassTitle;
    private AppCompatEditText etOldPass;
    private AppCompatEditText etNewPass;
    private AppCompatEditText etConfPass;
    private AppCompatTextView changePassword;
    private CardView btLogin;
    private LoginResponse mLoginDataResponse;
    private CustomLoader loader;
    private boolean isPin, isForcibly;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_change_pin_pass);

            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            mCustomAlertDialog = new CustomAlertDialog(this);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            isPin = getIntent().getBooleanExtra("IsPin", false);
            isForcibly = getIntent().getBooleanExtra("IsForcibly", false);
            intentResult = getIntent().getIntExtra("Intent_Result", 0);

            findViews();
        });


    }

    private void findViews() {

        title =  findViewById(R.id.title);
        etOldPass =  findViewById(R.id.et_old_pass);
        etNewPass =  findViewById(R.id.et_new_pass);
        etConfPass =  findViewById(R.id.et_conf_pass);
        changePassword =  findViewById(R.id.changePassword);
        btLogin =  findViewById(R.id.bt_login);
        closeIv = findViewById(R.id.closeIv);
        titleNewPass = findViewById(R.id.titleNewPass);
        oldPassTitle = findViewById(R.id.oldPassTitle);
        titleConfirmPass = findViewById(R.id.titleConfPass);
        noticeMsg = findViewById(R.id.noticeMsg);
        int maxLength = 4; // Set your desired maximum length
        EditText editText = new EditText(this); // Create an EditText instance
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(maxLength);
        editText.setFilters(filters);
        if (isPin) {
            title.setText("Change MPIN");
            etOldPass.setHint("Enter Current Login Password");
            oldPassTitle.setText("Enter Current Login Password");
            etNewPass.setHint("Enter New MPIN");
            titleNewPass.setText("Enter New MPIN");
            titleConfirmPass.setText("Enter Confirm MPIN");
            etConfPass.setHint("Enter Confirm MPIN");
            changePassword.setText("CHANGE MPIN");
            noticeMsg.setText(R.string.change_mpin_msg);
            etNewPass.setFilters(filters);
            etConfPass.setFilters(filters);
            etNewPass.setInputType(InputType.TYPE_CLASS_NUMBER);
            etConfPass.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            int maxLengthPass = 10;
            EditText editTextPass = new EditText(this);
            InputFilter[] filtersPass = new InputFilter[1];
            filtersPass[0] = new InputFilter.LengthFilter(maxLengthPass);
            editTextPass.setFilters(filtersPass);
            etNewPass.setFilters(filtersPass);
            etConfPass.setFilters(filtersPass);
        }

        if (isForcibly) {
            closeIv.setVisibility(View.GONE);
        }
        closeIv.setOnClickListener(v -> finish());

        btLogin.setOnClickListener(v -> submitData());
    }


    void submitData() {
        if (etOldPass.getText().toString().trim().isEmpty()) {
            etOldPass.setError(getString(R.string.password_error));
            etOldPass.requestFocus();
            return;
        } else if (etNewPass.getText().toString().trim().isEmpty()) {
            etNewPass.setError(getString(R.string.password_error));
            etNewPass.requestFocus();
            return;
        } else if (etNewPass.getText().toString().trim().equalsIgnoreCase(etOldPass.getText().toString().trim())) {
            etNewPass.setError(getString(R.string.samepass_error));
            etNewPass.requestFocus();
            return;
        } else if (!etConfPass.getText().toString().trim().equalsIgnoreCase(etNewPass.getText().toString().trim())) {
            etConfPass.setError(getString(R.string.newpass_error));
            etConfPass.requestFocus();
            return;
        }

        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            ChangePinPassword(ChangePinPassActivity.this, isPin, etOldPass.getText().toString().trim()
                    , etNewPass.getText().toString().trim(), etConfPass.getText().toString().trim(), loader, mLoginDataResponse);
        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }


    public void ChangePinPassword(final Activity context, final boolean isPin, final String oldPass, String newPass, String confPass,
                                  final CustomLoader loader, LoginResponse LoginDataResponse) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.ChangePinOrPassword(new ChangePinPasswordRequest(isPin, oldPass, newPass, confPass,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME,
                    deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, Response<RechargeReportResponse> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (intentResult != 0) {
                                        setResult(intentResult);
                                    }

                                    ApiFintechUtilMethods.INSTANCE.SuccessfulWithFinish(response.body().getMsg() + "", context, false);
                           /* if (!isPin) {
                                logout(context);
                            }*/

                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                    }
                                }

                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(ChangePinPassActivity.this, response.code(), response.message());
                        }
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        ApiFintechUtilMethods.INSTANCE.apiFailureError(ChangePinPassActivity.this, t);

                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(ChangePinPassActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(context, e.getMessage() + "");
        }

    }


    @Override
    public void onBackPressed() {
        if (!isForcibly) {
            super.onBackPressed();
        }

    }
}
