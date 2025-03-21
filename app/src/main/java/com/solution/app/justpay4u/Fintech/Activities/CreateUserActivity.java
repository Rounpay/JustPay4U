package com.solution.app.justpay4u.Fintech.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;

import com.solution.app.justpay4u.Api.Fintech.Object.Role;
import com.solution.app.justpay4u.Api.Fintech.Object.Slabs;
import com.solution.app.justpay4u.Api.Fintech.Object.UserInfo;
import com.solution.app.justpay4u.Api.Fintech.Request.AppUserReffDetailRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.AppUserRegisterRequest;
import com.solution.app.justpay4u.Api.Fintech.Object.UserCreate;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserReffDetailResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class CreateUserActivity extends AppCompatActivity {
    DropDownDialog mDropDownDialog;
    LoginResponse mLoginDataResponse;
    CustomLoader loader;
    ArrayList<DropDownModel> chooseRoleArray = new ArrayList<>();
    ArrayList<DropDownModel> chooseSlabArray = new ArrayList<>();
    int selectedRollId, selectedSlabId;
    String currentRefferalId = "";
    AppUserReffDetailResponse mAppUserReffDetailResponse;
    boolean isIntentFos;
    private TextView referralTv;
    private AppCompatTextView referralDetailTv;
    private View roleChooserView;
    private TextView chooseRoleTv;
    private AppCompatImageView chooseRoleArrow;
    private LinearLayout slabView, roleView;
    private View chooseSlabView;
    private TextView chooseSlabTv;
    private AppCompatImageView chooseSlabArrow;
    private AppCompatTextView realApiText;
    private View realApiSwitchView;
    private SwitchCompat realApiSwitch;
    private LinearLayout websiteView;
    private EditText websiteEt;
    private AppCompatTextView websiteText;
    private View websiteSwitchView;
    private SwitchCompat websiteSwitch;
    private EditText nameEt;
    private EditText outletNameEt;
    private EditText emailIdEt;
    private EditText mobileNoEt;
    private EditText pincodeEt;
    private EditText commRateEt;
    private EditText addressEt;
    private LinearLayout pinPassView;
    private EditText pinPassEt;
    private RelativeLayout gstSwitchView;
    /*ArrayList<String> chooseRoleStrArrayList = new ArrayList<String>();
    ArrayList<Role> chooseRoleArrayList = new ArrayList<>();
    ArrayList<String> chooseSlabStrArrayList = new ArrayList<String>();
    ArrayList<Slabs> chooseSlabArrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapterRole, arrayAdapterSlab;*/
    private AppCompatTextView gstText;
    private SwitchCompat gstSwitch;
    private RelativeLayout tdsSwitchView;
    private AppCompatTextView tdsText;
    private SwitchCompat tdsSwitch;
    private Button submitBtn;
    private int selectedRolePos, selectedSlabsPos;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_create_user);
            isIntentFos = getIntent().getBooleanExtra("IsFOS", false);
            mDropDownDialog = new DropDownDialog(this);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            findViews();


            chooseSlabView.setOnClickListener(v -> {
                if (currentRefferalId != null && !currentRefferalId.equalsIgnoreCase(referralTv.getText().toString())) {
                    AppUserReffDetailApi(referralTv.getText().toString());
                } else {
                    mDropDownDialog.showDropDownPopup(v, selectedSlabsPos, chooseSlabArray, new DropDownDialog.ClickDropDownItem() {
                        @Override
                        public void onClick(int clickPosition, String value, Object object) {
                            selectedSlabsPos = clickPosition;
                            selectedSlabId = ((Slabs) object).getId();
                            chooseSlabTv.setText(value + "");
                        }
                    });
                }
            });


            if (isIntentFos) {
                selectedRollId = 8;
                chooseRoleTv.setText("FOS");
                roleView.setVisibility(View.GONE);
            } else {
                roleView.setVisibility(View.VISIBLE);
                roleChooserView.setOnClickListener(v -> {
                    if (currentRefferalId != null && !currentRefferalId.equalsIgnoreCase(referralTv.getText().toString())) {
                        AppUserReffDetailApi(referralTv.getText().toString());
                    } else {
                        mDropDownDialog.showDropDownPopup(v, selectedRolePos, chooseRoleArray, new DropDownDialog.ClickDropDownItem() {
                            @Override
                            public void onClick(int clickPosition, String value, Object object) {
                                selectedRolePos = clickPosition;
                                selectedRollId = ((Role) object).getId();
                                chooseRoleTv.setText(value + "");
                            }
                        });
                    }
                });

           /* referralTv.setOnFocusChangeListener((v, hasFocus) -> {
                if (currentRefferalId != null && !currentRefferalId.equalsIgnoreCase(referralTv.getText().toString())) {
                    AppUserReffDetailApi(referralTv.getText().toString());
                }
            });*/
            }


            websiteSwitchView.setOnClickListener(v -> websiteSwitch.setChecked(websiteSwitch.isChecked() ? false : true));

            realApiSwitchView.setOnClickListener(v -> realApiSwitch.setChecked(realApiSwitch.isChecked() ? false : true));

            gstSwitchView.setOnClickListener(v -> gstSwitch.setChecked(gstSwitch.isChecked() ? false : true));

            tdsSwitchView.setOnClickListener(v -> tdsSwitch.setChecked(tdsSwitch.isChecked() ? false : true));

            submitBtn.setOnClickListener(v -> submitData());

            referralTv.setText(mLoginDataResponse.getData().getUserID());
            /*referralTv.setSelection(referralTv.getText().length());*/

            AppUserReffDetailApi(mLoginDataResponse.getData().getUserID());
        });

    }


    private void findViews() {
        setTitle(isIntentFos ? "Create FOS User" : "Create User");

        referralTv = findViewById(R.id.referralTv);

        referralDetailTv = findViewById(R.id.referralDetailTv);
        roleChooserView = findViewById(R.id.roleChooserView);
        chooseRoleTv = findViewById(R.id.chooseRoleTv);
        chooseRoleArrow = findViewById(R.id.chooseRoleArrow);
        slabView = findViewById(R.id.slabView);
        roleView = findViewById(R.id.roleView);
        chooseSlabView = findViewById(R.id.chooseSlabView);
        chooseSlabTv = findViewById(R.id.chooseSlabTv);
        chooseSlabArrow = findViewById(R.id.chooseSlabArrow);
        realApiText = findViewById(R.id.realApiText);
        realApiSwitchView = findViewById(R.id.realApiSwitchView);
        realApiSwitch = findViewById(R.id.realApiSwitch);
        websiteView = findViewById(R.id.websiteView);
        websiteEt = findViewById(R.id.websiteEt);
        websiteText = findViewById(R.id.websiteText);
        websiteSwitchView = findViewById(R.id.websiteSwitchView);
        websiteSwitch = findViewById(R.id.websiteSwitch);
        nameEt = findViewById(R.id.nameEt);
        outletNameEt = findViewById(R.id.outletNameEt);
        emailIdEt = findViewById(R.id.emailIdEt);
        mobileNoEt = findViewById(R.id.mobileNoEt);
        pincodeEt = findViewById(R.id.pincodeEt);
        commRateEt = findViewById(R.id.commRateEt);
        addressEt = findViewById(R.id.addressEt);
        pinPassView = findViewById(R.id.pinPassView);
        pinPassEt = findViewById(R.id.pinPassEt);
        gstSwitchView = findViewById(R.id.gstSwitchView);
        gstText = findViewById(R.id.gstText);
        gstSwitch = findViewById(R.id.gstSwitch);
        tdsSwitchView = findViewById(R.id.tdsSwitchView);
        tdsText = findViewById(R.id.tdsText);
        tdsSwitch = findViewById(R.id.tdsSwitch);
        submitBtn = findViewById(R.id.submitBtn);

        if (mLoginDataResponse.getData().getIsDoubleFactor()) {
            pinPassView.setVisibility(View.VISIBLE);
        } else {
            pinPassView.setVisibility(View.GONE);
        }
    }


    void submitData() {
        if (mAppUserReffDetailResponse != null && !mAppUserReffDetailResponse.getUserRegModel().getUserInfo().getAdminDefined() && selectedSlabId == 0) {
            ApiFintechUtilMethods.INSTANCE.Error(CreateUserActivity.this, "Please choose a slab");
            return;
        } else if (mAppUserReffDetailResponse != null && !mAppUserReffDetailResponse.getUserRegModel().getUserInfo().getAdminDefined()
                && websiteSwitch.isChecked() && websiteEt.getText().toString().isEmpty()) {
            websiteEt.setError(getString(R.string.err_empty_field));
            websiteEt.requestFocus();
            return;
        } else if (nameEt.getText().toString().isEmpty()) {
            nameEt.setError(getString(R.string.err_empty_field));
            nameEt.requestFocus();
            return;
        } else if (outletNameEt.getText().toString().isEmpty()) {
            outletNameEt.setError(getString(R.string.err_empty_field));
            outletNameEt.requestFocus();
            return;
        } else if (emailIdEt.getText().toString().isEmpty()) {
            emailIdEt.setError(getString(R.string.err_empty_field));
            emailIdEt.requestFocus();
            return;
        } else if (!emailIdEt.getText().toString().contains(".")) {
            emailIdEt.setError(getString(R.string.err_msg_email));
            emailIdEt.requestFocus();
            return;
        } else if (!emailIdEt.getText().toString().contains("@")) {
            emailIdEt.setError(getString(R.string.err_msg_email));
            emailIdEt.requestFocus();
            return;
        } else if (mobileNoEt.getText().toString().isEmpty()) {
            mobileNoEt.setError(getString(R.string.err_empty_field));
            mobileNoEt.requestFocus();
            return;
        } else if (mobileNoEt.getText().toString().length() < 10) {
            mobileNoEt.setError(getString(R.string.mobilenumber_error));
            mobileNoEt.requestFocus();
            return;
        } else if (pincodeEt.getText().toString().isEmpty()) {
            pincodeEt.setError(getString(R.string.err_empty_field));
            pincodeEt.requestFocus();
            return;
        } else if (pincodeEt.getText().toString().length() < 6) {
            pincodeEt.setError(getString(R.string.pincode_error));
            pincodeEt.requestFocus();
            return;
        } else if (addressEt.getText().toString().isEmpty()) {
            addressEt.setError(getString(R.string.err_empty_field));
            addressEt.requestFocus();
            return;
        } else if (pinPassView.getVisibility() == View.VISIBLE && pinPassEt.getText().toString().isEmpty()) {
            pinPassEt.setError(getString(R.string.err_empty_field));
            pinPassEt.requestFocus();
            return;
        }

        createUserApi();
    }


    private void createUserApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            try {
                FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
                loader.show();
                UserCreate userCreate = new UserCreate(addressEt.getText().toString(), "", websiteEt.getText().toString(),
                        mAppUserReffDetailResponse.getUserRegModel().getToken(), realApiSwitch.isChecked(), 0.0,
                        mobileNoEt.getText().toString(), nameEt.getText().toString(), outletNameEt.getText().toString(),
                        emailIdEt.getText().toString(), selectedRollId, selectedSlabId, gstSwitch.isChecked(),
                        tdsSwitch.isChecked(), mAppUserReffDetailResponse.getUserRegModel().getUserInfo().getVirtual(), websiteSwitch.isChecked(),
                        mAppUserReffDetailResponse.getUserRegModel().getUserInfo().getAdminDefined(),
                        pincodeEt.getText().toString());

                Call<BasicResponse> call = git.AppUserRegistraion(new AppUserRegisterRequest(pinPassEt.getText().toString(),
                        mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                        ApplicationConstant.INSTANCE.APP_ID, deviceId,
                        "", BuildConfig.VERSION_NAME, deviceSerialNum,
                        mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(), userCreate));
                call.enqueue(new Callback<BasicResponse>() {
                    @Override
                    public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                        try {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            if (response.isSuccessful()) {
                                BasicResponse data = response.body();
                                if (data != null) {

                                    if (data.getStatuscode() == 1) {
                                        ApiFintechUtilMethods.INSTANCE.Successfulok(data.getMsg(), CreateUserActivity.this);
                                    } else if (data.getStatuscode() == -1) {
                                        if (!data.getVersionValid()) {
                                            ApiFintechUtilMethods.INSTANCE.versionDialog(CreateUserActivity.this);
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(CreateUserActivity.this, data.getMsg() + "");
                                        }
                                    }
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(CreateUserActivity.this, getString(R.string.some_thing_error) + "");
                                }
                            } else {
                                ApiFintechUtilMethods.INSTANCE.apiErrorHandle(CreateUserActivity.this, response.code(), response.message());
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
                            ApiFintechUtilMethods.INSTANCE.apiFailureError(CreateUserActivity.this, t);

                        } catch (IllegalStateException ise) {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.Error(CreateUserActivity.this, getString(R.string.some_thing_error));
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                if (loader != null && loader.isShowing()) {
                    loader.dismiss();
                }
            }
        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    private void AppUserReffDetailApi(String referralId) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            try {
                FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
                Call<AppUserReffDetailResponse> call = git.AppUserReffDetail(new AppUserReffDetailRequest(referralId,
                        mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID,
                        deviceId,
                        "", BuildConfig.VERSION_NAME, deviceSerialNum,
                        mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));
                call.enqueue(new Callback<AppUserReffDetailResponse>() {
                    @Override
                    public void onResponse(Call<AppUserReffDetailResponse> call, final retrofit2.Response<AppUserReffDetailResponse> response) {

                        try {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            if (response.isSuccessful()) {
                                mAppUserReffDetailResponse = response.body();
                                if (mAppUserReffDetailResponse != null) {

                                    if (mAppUserReffDetailResponse.getStatuscode() == 1) {
                                        currentRefferalId = referralTv.getText().toString();
                                        if (mAppUserReffDetailResponse.getUserRegModel() != null) {


                                            UserInfo mUserInfo = mAppUserReffDetailResponse.getUserRegModel().getUserInfo();
                                            if (mUserInfo != null && mUserInfo.getOutletName() != null && mUserInfo.getResultCode() == 1) {
                                                referralDetailTv.setVisibility(View.VISIBLE);
                                                submitBtn.setVisibility(View.VISIBLE);
                                                gstSwitch.setChecked(mUserInfo.getGSTApplicable());
                                                tdsSwitch.setChecked(mUserInfo.getTDSApplicable());
                                                referralDetailTv.setText(mUserInfo.getOutletName() + " " + mUserInfo.getMobileNo());
                                                referralDetailTv.setTextColor(getResources().getColor(R.color.colorAccent));
                                                if (mUserInfo.getAdminDefined()) {
                                                    slabView.setVisibility(View.GONE);
                                                    websiteView.setVisibility(View.GONE);
                                                } else {
                                                    slabView.setVisibility(View.VISIBLE);
                                                    websiteView.setVisibility(View.VISIBLE);
                                                }
                                                if (mAppUserReffDetailResponse.getUserRegModel().getRoleSlab() != null && mAppUserReffDetailResponse.getUserRegModel().getRoleSlab().getRoles() != null) {

                                                    if (isIntentFos) {
                                                        selectedRollId = 8;
                                                        chooseRoleTv.setText("FOS");
                                                        roleView.setVisibility(View.GONE);
                                                    } else {
                                                        roleView.setVisibility(View.VISIBLE);
                                                        chooseRoleArray.clear();

                                                        boolean isFirstSet = false;
                                                        for (int i = 0; i < mAppUserReffDetailResponse.getUserRegModel().getRoleSlab().getRoles().size(); i++) {

                                                            Role mRole = mAppUserReffDetailResponse.getUserRegModel().getRoleSlab().getRoles().get(i);
                                                            //remove fos
                                                            if (mRole.getId() != 8) {
                                                                chooseRoleArray.add(new DropDownModel(mRole.getRole(), mRole));
                                                                if (!isFirstSet) {
                                                                    selectedRolePos = i;
                                                                    selectedRollId = mRole.getId();
                                                                    chooseRoleTv.setText(mRole.getRole());
                                                                    isFirstSet = true;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    chooseSlabArray.clear();

                                                    for (int i = 0; i < mAppUserReffDetailResponse.getUserRegModel().getRoleSlab().getSlabs().size(); i++) {
                                                        Slabs mSlabs = mAppUserReffDetailResponse.getUserRegModel().getRoleSlab().getSlabs().get(i);
                                                        chooseSlabArray.add(new DropDownModel(mSlabs.getSlab(), mSlabs));
                                                        if (i == 0) {
                                                            selectedSlabsPos = i;
                                                            selectedSlabId = mSlabs.getId();
                                                            chooseSlabTv.setText(mSlabs.getSlab());
                                                        }
                                                    }

                                                }
                                            } else {
                                                chooseRoleArray.clear();
                                                chooseSlabArray.clear();
                                                chooseSlabTv.setText("");
                                                chooseRoleTv.setText("");
                                                selectedRollId = 0;
                                                selectedSlabId = 0;
                                                gstSwitch.setChecked(false);
                                                tdsSwitch.setChecked(false);
                                                referralDetailTv.setVisibility(View.VISIBLE);
                                                referralDetailTv.setText(mUserInfo.getMsg());
                                                referralDetailTv.setTextColor(getResources().getColor(R.color.style_color_accent));
                                                submitBtn.setVisibility(View.GONE);
                                            }

                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(CreateUserActivity.this, getString(R.string.some_thing_error) + "");
                                        }
                                    } else if (mAppUserReffDetailResponse.getStatuscode() == -1) {
                                        if (!mAppUserReffDetailResponse.getVersionValid()) {
                                            ApiFintechUtilMethods.INSTANCE.versionDialog(CreateUserActivity.this);
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(CreateUserActivity.this, mAppUserReffDetailResponse.getMsg() + "");
                                        }
                                    }
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(CreateUserActivity.this, getString(R.string.some_thing_error) + "");
                                }
                            } else {
                                ApiFintechUtilMethods.INSTANCE.apiErrorHandle(CreateUserActivity.this, response.code(), response.message());
                            }
                        } catch (Exception e) {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<AppUserReffDetailResponse> call, Throwable t) {


                        try {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.apiFailureError(CreateUserActivity.this, t);
                        } catch (IllegalStateException ise) {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.Error(CreateUserActivity.this, getString(R.string.some_thing_error));
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                if (loader != null && loader.isShowing()) {
                    loader.dismiss();
                }
            }
        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
