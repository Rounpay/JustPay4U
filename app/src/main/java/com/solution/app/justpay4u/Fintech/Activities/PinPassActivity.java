package com.solution.app.justpay4u.Fintech.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.textfield.TextInputEditText;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Networking.Request.ValidatePINRequest;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.Dashboard.Activity.HomeDashActivity;
import com.solution.app.justpay4u.Fintech.Employee.Activity.EmpDashboardActivity;
import com.solution.app.justpay4u.Networking.Activity.NetworkingDashboardActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.ShoppingDashboardActivity;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinPassActivity extends AppCompatActivity {
    private AppPreferences mAppPreferences;
    private LoginResponse mLoginDataResponse;
    private String deviceId, deviceSerialNum;
    CustomAlertDialog mCustomAlertDialog;
    private CustomLoader loader;
    private View title, bt_pinPass;
//    private PinEntryEditText pinPass;
    private LinearLayout pinPass;
    private EditText[] editTexts;
    private AppCompatTextView pinPassError;
    private TextInputEditText etOtp1 , etOtp2 , etOtp3 , etOtp4;
    private String enteredOtp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_pin_pass);

            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            mCustomAlertDialog = new CustomAlertDialog(this);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);

            findViews();
            customizingEnteringOtp();
            editTexts = new EditText[]{
                    etOtp1,
                    etOtp2,
                    etOtp3,
                    etOtp4
            };
            bt_pinPass.setOnClickListener(v ->{
                String enteredOtp = getEnteredOtp();
                        if (validateOtp(enteredOtp)) {
                            submitData(enteredOtp);
                        }
                    });



            // Request focus and show the keyboard
            pinPass.post(() -> {
                pinPass.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(pinPass, InputMethodManager.SHOW_IMPLICIT);
            });
        });
    }

    private boolean validateOtp(String enteredOtp) {
        if (enteredOtp.isEmpty()) {
            pinPassError.setText(getString(R.string.err_empty_field));
            pinPassError.requestFocus();
            return false;
        } else if (enteredOtp.length() < editTexts.length) {
            pinPassError.setText("Enter Valid PIN");
            pinPassError.requestFocus();
            return false;
        } else {
            pinPassError.setVisibility(View.GONE);
            pinPassError.setText("");
            return true;
        }
    }

    private void customizingEnteringOtp() {
        final EditText[] editTexts = {
                etOtp1,
                etOtp2,
                etOtp3,
                etOtp4
        };

        for (int i = 0; i < editTexts.length; i++) {
            final int finalI = i;
            editTexts[i].setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD); // Set input type
            editTexts[i].setTransformationMethod(new AsteriskPasswordTransformationMethod()); // Apply custom transformation method

            editTexts[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Not implemented
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Not implemented
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s != null && s.length() == 1) {
                        if (finalI < editTexts.length - 1) {
                            editTexts[finalI + 1].requestFocus();
                        }
                    } else if (s != null && s.length() == 0) {
                        if (finalI > 0) {
                            editTexts[finalI - 1].requestFocus();
                        }
                    }
                }
            });
        }
    }

    private void findViews() {
        title = findViewById(R.id.title);
        pinPass = findViewById(R.id.txt_pin_entry_View);
        pinPassError = findViewById(R.id.pinPassError);
        bt_pinPass = findViewById(R.id.bt_pinPass);
        etOtp1 = findViewById(R.id.etOtp1);
        etOtp2 = findViewById(R.id.etOtp2);
        etOtp3 = findViewById(R.id.etOtp3);
        etOtp4 = findViewById(R.id.etOtp4);

    }

    private String getEnteredOtp() {
        StringBuilder otpBuilder = new StringBuilder();
        for (EditText editText : editTexts) {
            otpBuilder.append(editText.getText().toString());
        }
        return enteredOtp = otpBuilder.toString();
    }

    private void submitData(String enteredOtp) {
            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                ValidatePIN(this, enteredOtp, loader, mLoginDataResponse);
            } else {
                ApiFintechUtilMethods.INSTANCE.NetworkError(this);
            }

    }

    private void ValidatePIN(final Activity context, final String isPin, final CustomLoader loader, LoginResponse mLoginDataResponse) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call = git.ValidatePIN(new ValidatePINRequest(isPin,
                    mLoginDataResponse.getData().getLoginTypeID(),
                    mLoginDataResponse.getData().getUserID() + "",
                    mLoginDataResponse.getData().getSession(),
                    mLoginDataResponse.getData().getSessionID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "",
                    BuildConfig.VERSION_NAME,
                    deviceSerialNum
                    ));
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    startDashboard();

                                } else {
                                    if (!response.body().isVersionValid()) {
                                        ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                    }
                                }
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(PinPassActivity.this, response.code(), response.message());
                        }
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
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

                        ApiFintechUtilMethods.INSTANCE.apiFailureError(PinPassActivity.this, t);

                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(PinPassActivity.this, getString(R.string.some_thing_error));
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


    public void startDashboard() {
        if (mLoginDataResponse.getData().getLoginTypeID() == 1) {
            finish();
            if (mLoginDataResponse.getDashPreference() == 1) {
                Intent intent = new Intent(PinPassActivity.this, NetworkingDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else if (mLoginDataResponse.getDashPreference() == 2) {
                Intent intent = new Intent(PinPassActivity.this, HomeDashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else {
                Intent intent = new Intent(PinPassActivity.this, ShoppingDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }


        } else {
            finish();
            Intent intent = new Intent(PinPassActivity.this, EmpDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

    }
}
