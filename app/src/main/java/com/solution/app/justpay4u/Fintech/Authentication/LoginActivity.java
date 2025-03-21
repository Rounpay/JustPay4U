package com.solution.app.justpay4u.Fintech.Authentication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.solution.app.justpay4u.Fintech.Activities.PermissionActivity;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.InstallReferral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    CustomLoader loader;
    ArrayList<String> recentNumber = new ArrayList<>();
    HashMap<String, String> recentNumberMap = new HashMap<>();
    // Declare RelativeLayout.....
    ArrayAdapter<String> adapter;
    AppPreferences mAppPreferences;
    String deviceId, deviceSerialNum;
    private LinearLayout iconView;
    private TextView title;
    private LinearLayout llLogin;
    private AutoCompleteTextView etMobile;
    private TextView tilMobileNum;
    private AutoCompleteTextView etPass;
    private TextView tilPassword;
    private CheckBox checkPass;
    private CardView btLogin;
    private TextView tvForgotpass, userTypeTv, userTypeFwpTv;
    private TextView tvSignup;
    private LinearLayout llFwdPass;
    private AutoCompleteTextView etMobileFwp;
    private TextView tilFrgtMobileNum;
    private CardView okButton;
    private CardView cancelButton;
    private TextView currentVersion;
    private View rightView, rightFwpView;
    private int INTENT_PERMISSION = 321;
    private DropDownDialog mDropDownDialog;
    private int selectedUserTypePos = 0, selectedUserTypeFWPPos = 0;
    private ArrayList<String> arrayListUserType = new ArrayList<>();
    private ImageView mobIcon, mobFwpIcon;
     private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_login);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mDropDownDialog = new DropDownDialog(this);
            arrayListUserType.add("User");
            arrayListUserType.add("Employee");
            getId();
            new Handler(Looper.getMainLooper()).post(() -> {
                RecentNumbers();
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(this)
                        .asBitmap()
                        .load(ApplicationConstant.INSTANCE.baseUrl + "Image/Website/1/Popup/AppBeforeLogin.png")
                        .apply(requestOptions)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                if (resource != null) {
                                    ApiFintechUtilMethods.INSTANCE.showPopupDialog(LoginActivity.this, resource);
                                }
                            }
                        });
                if (!mAppPreferences.getNonRemovalBoolean(ApplicationConstant.INSTANCE.isUserReferralSetPref)) {
                    new InstallReferral(this).InstllReferralData(mAppPreferences);
                }
                setDeviceSerialAndId();
            });
        });
    }

    public void RecentNumbers() {
        String abcd = mAppPreferences.getNonRemovalString(ApplicationConstant.INSTANCE.regRecentLoginPref);
        if (abcd != null && abcd.length() > 4) {
            recentNumberMap = new Gson().fromJson(abcd, new TypeToken<HashMap<String, String>>() {
            }.getType());


            for (Map.Entry<String, String> e : recentNumberMap.entrySet()) {
                String key = e.getKey();
                /* String value = e.getValue();*/
                recentNumber.add(key);

            }

            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recentNumber);
            etMobile.setAdapter(adapter);
            etMobile.setThreshold(1);
        }


        etMobile.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            etPass.setText(recentNumberMap.get(selected));
        });
    }

    public void getId() {
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        iconView = findViewById(R.id.iconView);
        title = findViewById(R.id.title);
        llLogin = findViewById(R.id.ll_login);
        mobIcon = findViewById(R.id.mobIcon);
        mobFwpIcon = findViewById(R.id.mobFwpIcon);
        etMobile = findViewById(R.id.et_mobile);
        tilMobileNum = findViewById(R.id.til_mobile_num);
        rightView = findViewById(R.id.rightView);
        rightFwpView = findViewById(R.id.rightFwpView);
        userTypeTv = findViewById(R.id.userTypeTv);
        userTypeFwpTv = findViewById(R.id.userTypeFwpTv);
        etPass = findViewById(R.id.et_pass);
        tilPassword = findViewById(R.id.til_password);
        checkPass = findViewById(R.id.check_pass);
        btLogin = findViewById(R.id.bt_login);
        tvForgotpass = findViewById(R.id.tv_forgotpass);
        tvSignup = findViewById(R.id.tv_signup);
        llFwdPass = findViewById(R.id.ll_fwd_pass);
        etMobileFwp = findViewById(R.id.et_mobile_fwp);
        animationView = (LottieAnimationView) findViewById(R.id.animation);
        tilFrgtMobileNum = findViewById(R.id.til_frgt_mobile_num);
        okButton = findViewById(R.id.okButton);
        cancelButton = findViewById(R.id.cancelButton);
        currentVersion = findViewById(R.id.currentVersion);
        currentVersion.setText(getString(R.string.app_name) + " Version " + BuildConfig.VERSION_NAME);
        TextView termAndPrivacyTxt = findViewById(R.id.term_and_privacy_txt);
        termAndPrivacyTxt.setMovementMethod(LinkMovementMethod.getInstance());
        setListners();
        animationView.setAnimation(R.raw.bg_new);
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!validateMobile()) {
                    return;
                }
            }
        });
        etMobileFwp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!validateMobileFwp()) {
                    return;
                }
            }
        });
        etPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!validatePass()) {
                    return;
                }
            }
        });


        findViewById(R.id.tv_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });
    }

    public void setListners() {
        btLogin.setOnClickListener(this);
        rightView.setOnClickListener(this);
        rightFwpView.setOnClickListener(this);
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        tvForgotpass.setOnClickListener(this);

    }


    void setDeviceSerialAndId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(this, PermissionActivity.class), INTENT_PERMISSION);
            } else {
                deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(this);
                deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getDeviceSerialNo(this);
                mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.DeviceIdPref, deviceId);
                mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.DeviceSerialNumberPref, deviceSerialNum);
            }
        } else {
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(this);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getDeviceSerialNo(this);
            mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.DeviceIdPref, deviceId);
            mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.DeviceSerialNumberPref, deviceSerialNum);
        }
    }

    private boolean validateMobile() {
        boolean isNumeric;
        try {
            Long.parseLong(etMobile.getText().toString());
            isNumeric = true;
            mobIcon.setImageResource(R.drawable.ic_phone);
        } catch (NumberFormatException nfe) {
            isNumeric = false;
            mobIcon.setImageResource(R.drawable.ic_person);
        }
        if (etMobile.getText().toString().trim().isEmpty()) {
            tilMobileNum.setText(getString(R.string.err_empty_field));
            etMobile.requestFocus();
            //  btLogin.setEnabled(false);
            return false;
        } else if (isNumeric && etMobile.getText().toString().trim().length() != 10) {
            tilMobileNum.setText(getString(R.string.err_msg_mobile_length));
            etMobile.requestFocus();
            //  btLogin.setEnabled(false);
            return false;
        } else if (!isNumeric && etMobile.getText().toString().trim().length() < 3) {
            tilMobileNum.setText("Please enter valid user id");
            etMobile.requestFocus();
            //  btLogin.setEnabled(false);
            return false;
        } else {
            tilMobileNum.setText("");
            // etPass.requestFocus();
        }

        return true;
    }

    private boolean validatePass() {
        if (etPass.getText().toString().trim().isEmpty()) {
            tilPassword.setText(getString(R.string.err_msg_pass));
            etPass.requestFocus();
            //    btLogin.setEnabled(false);
            return false;
        } else {
            tilPassword.setText("");
            //  btLogin.setEnabled(true);
        }
        return true;
    }

    public boolean validateMobileFwp() {
        boolean isNumeric;
        try {
            Long.parseLong(etMobileFwp.getText().toString().trim());
            isNumeric = true;
            mobFwpIcon.setImageResource(R.drawable.ic_phone);
        } catch (NumberFormatException nfe) {
            isNumeric = false;
            mobFwpIcon.setImageResource(R.drawable.ic_person);
        }
        if (etMobileFwp.getText().toString().trim().isEmpty()) {
            tilFrgtMobileNum.setText(getString(R.string.err_msg_mobile));
            etMobileFwp.requestFocus();
            return false;
        } else if (isNumeric && etMobileFwp.getText().toString().trim().length() != 10) {
            tilFrgtMobileNum.setText(getString(R.string.err_msg_mobile_length));
            etMobileFwp.requestFocus();
            //  btLogin.setEnabled(false);
            return false;
        } else if (!isNumeric && etMobileFwp.getText().toString().trim().length() < 3) {
            tilFrgtMobileNum.setText("Please enter valid user id");
            etMobileFwp.requestFocus();
            //  btLogin.setEnabled(false);
            return false;
        } else {
            tilFrgtMobileNum.setText("");
            okButton.setEnabled(true);
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if (v == rightView) {
            mDropDownDialog.showDropDownPopup(v, selectedUserTypePos, arrayListUserType, (clickPosition, value, object) -> {
                        if (selectedUserTypePos != clickPosition) {
                            selectedUserTypePos = clickPosition;
                            userTypeTv.setText(value + "");
                        }
                    }
            );
        } else if (v == rightFwpView) {

            mDropDownDialog.showDropDownPopup(v, selectedUserTypeFWPPos, arrayListUserType, (clickPosition, value, object) -> {
                        if (selectedUserTypeFWPPos != clickPosition) {
                            selectedUserTypeFWPPos = clickPosition;
                            userTypeFwpTv.setText(value + "");
                        }
                    }
            );
        } else if (v == btLogin) {
            if (!validateMobile()) {
                return;
            }

            if (!validatePass()) {
                return;
            }

            if (checkPass.isChecked()) {
                recentNumberMap.put(etMobile.getText().toString(), etPass.getText().toString());
                recentNumber.add(etMobile.getText().toString());
                adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recentNumber);
                etMobile.setAdapter(adapter);
                etMobile.setThreshold(1);
                mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.regRecentLoginPref, new Gson().toJson(recentNumberMap));
            }

            /* if (!(UtilMethods.INSTANCE.isVpnConnected(this) || UtilMethods.INSTANCE.isSimAvailable(this))) {*/
            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(LoginActivity.this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                ApiFintechUtilMethods.INSTANCE.secureLogin(LoginActivity.this, selectedUserTypePos == 0 ? 1 : 3, etMobile.getText().toString().trim(),
                        etPass.getText().toString().trim(), loader, deviceId, deviceSerialNum, mAppPreferences);
            } else {
                ApiFintechUtilMethods.INSTANCE.NetworkError(LoginActivity.this);
            }
        } else if (v == tvForgotpass) {
            llLogin.setVisibility(View.GONE);
            llFwdPass.setVisibility(View.VISIBLE);
            //OpenDialogFwd();
        } else if (v == okButton) {
            if (!validateMobileFwp()) {
                return;
            }

            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(LoginActivity.this)) {
                loader.show();
                ApiFintechUtilMethods.INSTANCE.ForgotPass(LoginActivity.this, selectedUserTypeFWPPos == 0 ? 1 : 3, etMobileFwp.getText().toString().trim(), deviceId, deviceSerialNum, loader);

            } else {
                ApiFintechUtilMethods.INSTANCE.NetworkError(LoginActivity.this);
            }
        } else if (v == cancelButton) {
            llLogin.setVisibility(View.VISIBLE);
            llFwdPass.setVisibility(View.GONE);
            etMobileFwp.setText("");
        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_PERMISSION && resultCode == RESULT_OK) {
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(this);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getDeviceSerialNo(this);
            mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.DeviceIdPref, deviceId);
            mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.DeviceSerialNumberPref, deviceSerialNum);
        }
    }
}
