package com.solution.app.justpay4u.Fintech.Authentication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.solution.app.justpay4u.Api.Fintech.Object.ChildRoles;
import com.solution.app.justpay4u.Api.Fintech.Object.UserCreateSignup;
import com.solution.app.justpay4u.Api.Fintech.Request.GetRoleForReferralRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.SignupRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class SignupActivity extends AppCompatActivity {

    CustomLoader loader;
    CustomFilterDialog mCustomFilterDialog;
    String apiCalledReferal;
    View legView, searchIv, personalInfoLabelView, outletNameView;
    RadioButton leftRadio, rightRadio;
    String prefix="";
    private EditText etName;
    private EditText etOutletName;
    private TextView tvRoll, referralName;
    private EditText etMobile;
    private EditText etEmail;
    private EditText etAddress;
    private EditText etPincode;
    private EditText etReferral;
    private CardView btLogin;
    private TextView tvLogin,aadhaarNo;
    private HashMap<String, Integer> rollsMap = new HashMap<>();
    private String[] rollArray;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private View referralView, referralDetailView, roleView;
    private boolean isFromPlayStore;
    private String refName;
    private Dialog responseDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
//        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_signup);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            mCustomFilterDialog = new CustomFilterDialog(this);
            findViews();
        });
    }

    private void findViews() {
        etName = findViewById(R.id.et_name);
        searchIv = findViewById(R.id.searchIv);
        personalInfoLabelView = findViewById(R.id.personalInfoLabelView);
        legView = findViewById(R.id.legView);
        leftRadio = findViewById(R.id.leftRadio);
        rightRadio = findViewById(R.id.rightRadio);
        referralName = findViewById(R.id.referralName);
        outletNameView = findViewById(R.id.outletNameView);
        etOutletName = findViewById(R.id.et_outletName);
        tvRoll = findViewById(R.id.tv_roll);
        referralView = findViewById(R.id.referralView);
        referralDetailView = findViewById(R.id.referralDetailView);
        roleView = findViewById(R.id.roleView);
        etMobile = findViewById(R.id.et_mobile);
        etEmail = findViewById(R.id.et_email);
        etAddress = findViewById(R.id.et_address);
        etPincode = findViewById(R.id.et_pincode);
        etReferral = findViewById(R.id.et_refferal);
        if (mAppPreferences.getNonRemovalString(ApplicationConstant.INSTANCE.UserReferralPref).isEmpty()) {
            etReferral.setText("");
            isFromPlayStore = false;
            etReferral.setFocusable(true);
            etReferral.setClickable(true);
            etReferral.setFocusableInTouchMode(true);
            etReferral.setLongClickable(true);
            referralDetailView.setVisibility(View.VISIBLE);
            personalInfoLabelView.setVisibility(View.VISIBLE);
        } else {
            etReferral.setText(mAppPreferences.getNonRemovalString(ApplicationConstant.INSTANCE.UserReferralPref) + "");
            isFromPlayStore = true;
        }

        btLogin = findViewById(R.id.bt_login);
        tvLogin = findViewById(R.id.tv_login);
        aadhaarNo = findViewById(R.id.aadhaarNo);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etReferral.setError(null);
                if (etReferral.getText().toString().isEmpty()) {
                    etReferral.setError(getString(R.string.err_empty_field));
                    etReferral.requestFocus();
                    return;
                }
                stvRoll(true);
            }
        });
        searchIv.setOnClickListener(v -> {
            etReferral.setError(null);
            if (etReferral.getText().toString().isEmpty()) {
                etReferral.setError(getString(R.string.err_empty_field));
                etReferral.requestFocus();
                return;
            }
            Role(this, false);
        });

        etReferral.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (apiCalledReferal != null && !s.toString().isEmpty()) {
                    if (!apiCalledReferal.equalsIgnoreCase(s.toString())) {
                        referralName.setText("");
                        searchIv.setVisibility(View.VISIBLE);
                    } else {
                        searchIv.setVisibility(View.GONE);
                    }
                } else {
//                    searchIv.setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.closeIv).setOnClickListener(v -> finish());


        if (!etReferral.getText().toString().isEmpty()) {
            stvRoll(false);
        }
    }


    void stvRoll(boolean isFromClick) {
        Toast.makeText(this, isFromClick+"", Toast.LENGTH_SHORT).show();

        if (rollArray != null && rollArray.length > 0) {
            if (apiCalledReferal != null && !apiCalledReferal.equalsIgnoreCase(etReferral.getText().toString())) {
                Role(this, isFromClick);
            } else {
                selectRole();
            }

        } else {
            Role(this, isFromClick);
        }
    }


    public void Role(final Activity context, boolean isFromClick) {
        try {
            if (!loader.isShowing()) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<LoginResponse> call = git.GetRoleForReferral(new GetRoleForReferralRequest(etReferral.getText().toString(),
                    ApplicationConstant.INSTANCE.Domain,
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum));


            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {


                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {


                            if (response != null && response.body() != null) {
                                if (response.body().getStatuscode() == 1) {

                                    referralDetailView.setVisibility(View.VISIBLE);
                                    personalInfoLabelView.setVisibility(View.VISIBLE);
                                    if (response.body().getName() != null) {
                                       refName= response.body().getName();

                                        searchIv.setVisibility(View.GONE);

                                        if (response.body().isReferralEditable==true){
                             prefix=response.body().getChildRoles().get(0).getPrefix();


                                        }


                                    } else {
                                        referralName.setText("");
                                        searchIv.setVisibility(View.VISIBLE);
                                    }

                                   /* if (!isFromPlayStore) {*/

                                        if (response.body().getIsBinaryon() == 2) {
                                            legView.setVisibility(View.VISIBLE);
                                            if (etReferral.getText().toString().equalsIgnoreCase("1")) {
                                                if (response.body().getLeg() != null) {
                                                    if (response.body().getLeg().equalsIgnoreCase("L")) {
                                                        leftRadio.setVisibility(View.VISIBLE);
                                                        leftRadio.setChecked(true);
                                                        rightRadio.setVisibility(View.GONE);
                                                    } else {
                                                        leftRadio.setVisibility(View.GONE);
                                                        rightRadio.setChecked(true);
                                                        rightRadio.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                            } else {
                                                leftRadio.setVisibility(View.VISIBLE);
                                                leftRadio.setChecked(true);
                                                rightRadio.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        else {
                                            legView.setVisibility(View.GONE);
                                        }
                                        apiCalledReferal = response.body().getSignupRefferalId() + "";
                                        etReferral.setText(prefix+apiCalledReferal);
                                        referralName.setText(refName);

                                        if (response.body().isReferralEditable()) {
                                            etReferral.setFocusable(true);
                                            etReferral.setClickable(true);
                                            etReferral.setFocusableInTouchMode(true);
                                            etReferral.setLongClickable(true);
                                        } else {
                                            etReferral.setFocusable(false);
                                            etReferral.setClickable(false);
                                            etReferral.setFocusableInTouchMode(false);
                                            etReferral.setLongClickable(false);
                                        }
                                   /* }*/


                                    ArrayList<ChildRoles> mChildRolesArray = response.body().getChildRoles();
                                    if (mChildRolesArray != null && mChildRolesArray.size() > 0) {
                                        if (mChildRolesArray.size() > 1) {
                                            roleView.setVisibility(View.VISIBLE);

                                        } else {
                                            roleView.setVisibility(View.GONE);
                                        }


                                        rollArray = new String[mChildRolesArray.size()];
                                        rollsMap.clear();
                                        for (int i = 0; i < mChildRolesArray.size(); i++) {
                                            rollArray[i] = mChildRolesArray.get(i).getRole() + "";
                                            rollsMap.put(mChildRolesArray.get(i).getRole() + "", mChildRolesArray.get(i).getId());
                                        }

                                        if (rollArray.length == 1) {
                                            tvRoll.setText(rollArray[0]);
                                        }
                                        if (isFromClick) {
                                            selectRole();
                                        }
                                    }
                                } else {
                                    referralName.setText("");
                                    searchIv.setVisibility(View.VISIBLE);
                                    if (!response.body().getIsVersionValid()) {
                                        ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                    }
                                }
                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(context, getString(R.string.some_thing_error) + "");
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }


                    ApiFintechUtilMethods.INSTANCE.apiFailureError(context, t);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
        }
    }

    void selectRole() {
        int selectedIndex = 0;
        if (tvRoll.getText().toString().length() == 0) {
            selectedIndex = -1;
        } else {
            selectedIndex = Arrays.asList(rollArray).indexOf(tvRoll.getText().toString());
        }
        mCustomFilterDialog.showSingleChoiceAlert(rollArray, selectedIndex, "Date Type", "Choose Date Type", new CustomFilterDialog.SingleChoiceDialogCallBack() {
            @Override
            public void onPositiveClick(int index) {
                if (index != -1) {
                    tvRoll.setText(rollArray[index]);
                    tvRoll.setError(null);
                }
            }

            @Override
            public void onNegativeClick() {

            }
        });
    }

    void signup() {
        tvRoll.setError(null);
        etName.setError(null);
        etOutletName.setError(null);
        etMobile.setError(null);
        aadhaarNo.setError(null);
        etEmail.setError(null);
        etAddress.setError(null);
        etPincode.setError(null);
        if (roleView.getVisibility() == View.VISIBLE && tvRoll.getText().toString().isEmpty()) {
            tvRoll.setError(getString(R.string.err_empty_field));
            tvRoll.requestFocus();
            return;
        } else if (etName.getText().toString().isEmpty()) {
            etName.setError(getString(R.string.err_empty_field));
            etName.requestFocus();
            return;
        } else if (outletNameView.getVisibility() == View.VISIBLE && etOutletName.getText().toString().isEmpty()) {
            etOutletName.setError(getString(R.string.err_empty_field));
            etOutletName.requestFocus();
            return;
        } else if (etMobile.getText().toString().isEmpty()) {
            etMobile.setError(getString(R.string.err_empty_field));
            etMobile.requestFocus();
            return;
        } else if (etMobile.getText().toString().length() != 10) {
            etMobile.setError(getString(R.string.err_msg_mobile_length));
            etMobile.requestFocus();
            return;
        }else if (aadhaarNo.getText().toString().isEmpty()) {
            aadhaarNo.setError(getString(R.string.err_empty_field));
            aadhaarNo.requestFocus();
            return;
        }else if (aadhaarNo.getText().toString().length() != 12) {
            aadhaarNo.setError(getString(R.string.aadhaar));
            aadhaarNo.requestFocus();
            return;
        } else if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError(getString(R.string.err_empty_field));
            etEmail.requestFocus();
            return;
        } else if (!etEmail.getText().toString().contains("@") || !etEmail.getText().toString().contains(".")) {
            etEmail.setError(getString(R.string.err_msg_email));
            etEmail.requestFocus();
            return;
        } else if (etAddress.getText().toString().isEmpty()) {
            etAddress.setError(getString(R.string.err_empty_field));
            etAddress.requestFocus();
            return;
        } else if (etPincode.getText().toString().isEmpty()) {
            etPincode.setError(getString(R.string.err_empty_field));
            etPincode.requestFocus();
            return;
        } else if (etPincode.getText().toString().length() != 6) {
            etPincode.setError(getString(R.string.pincode_error));
            etPincode.requestFocus();
            return;
        }

        Register(this);
    }


    public void Register(final Activity context) {

        try {
            loader.show();

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            UserCreateSignup mUserCreateSignup = new UserCreateSignup(Integer.parseInt(apiCalledReferal), etAddress.getText().toString(),
                    etMobile.getText().toString(),aadhaarNo.getText().toString(), etName.getText().toString(),
                    (etOutletName.getText().length() == 0 ? etName.getText().toString() : etOutletName.getText().toString()),
                    etEmail.getText().toString(),etReferral.getText().toString(), rollsMap.get(tvRoll.getText().toString()), etPincode.getText().toString(),
                    leftRadio.isChecked() ? "L" : "R");
            Call<LoginResponse> call = git.AppUserSignup(new SignupRequest(Integer.parseInt(apiCalledReferal),
                    ApplicationConstant.INSTANCE.Domain,
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, leftRadio.isChecked() ? "L" : "R", mUserCreateSignup));


            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response != null && response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (isFromPlayStore) {
                                        mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.UserReferralPref, "");
                                    }
                                    showDialogResponse(response.body());
//                                    ApiFintechUtilMethods.INSTANCE.SuccessfulWithFinsh(context, response.body().getMsg() + "");
                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                    }
                                }
                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(context, getString(R.string.some_thing_error) + "");
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    ApiFintechUtilMethods.INSTANCE.apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
        }
    }

    private void showDialogResponse(LoginResponse data) {
        if (responseDialog != null && responseDialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_networking_register, null);

        LinearLayout shareView = viewMyLayout.findViewById(R.id.shareView);
        View closeBtn = viewMyLayout.findViewById(R.id.closeIv);
        ImageView statusIcon = viewMyLayout.findViewById(R.id.statusIcon);
        TextView statusTv = viewMyLayout.findViewById(R.id.statusTv);
        TextView statusMsg = viewMyLayout.findViewById(R.id.statusMsg);

        TextView loginUrl = viewMyLayout.findViewById(R.id.loginUrl);
        /*ImageView copy = viewMyLayout.findViewById(R.id.copyIv);*/
        TextView name = viewMyLayout.findViewById(R.id.name);
        TextView userId = viewMyLayout.findViewById(R.id.userId);
        TextView password = viewMyLayout.findViewById(R.id.password);
        loginUrl.setText(data.getLoginUrl() + "");
        userId.setText(data.getUserId() + "");
        password.setText(data.getPassword() + "");
        name.setText(data.getUserName() + "");

        responseDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        responseDialog.setCancelable(false);
        responseDialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        responseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closeBtn.setOnClickListener(v -> {
            responseDialog.dismiss();
            finish();
        });
        /*copy.setOnClickListener(view -> Utility.INSTANCE.setClipboard(this, loginUrl.getText().toString(), "Login Url"));
         */

        responseDialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);


    }
}
