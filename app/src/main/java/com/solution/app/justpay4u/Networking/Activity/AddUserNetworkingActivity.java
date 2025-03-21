package com.solution.app.justpay4u.Networking.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.solution.app.justpay4u.Api.Fintech.Object.AreaWithPincode;
import com.solution.app.justpay4u.Api.Fintech.Object.ChildRoles;
import com.solution.app.justpay4u.Api.Fintech.Object.UserCreateSignup;
import com.solution.app.justpay4u.Api.Fintech.Request.GetRoleForReferralRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.SignupRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.AreaWithPincodeResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownModel;
import com.solution.app.justpay4u.Util.UtilsEditTextValidate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class AddUserNetworkingActivity extends AppCompatActivity {
    CustomLoader loader;
    CustomAlertDialog mCustomAlertDialog;
     Dialog  responseDialog ;
    private LinearLayout pinCodeLayout;
    private AppCompatImageView logo;
    private AppCompatTextView referralIdTitle;
    private AppCompatEditText referralIdEt;
    private ImageView searchIv;
    private AppCompatTextView referralNameEt, stateNameTv, cityNameTv, areaNameTv;
    private AppCompatTextView referralNameTitle;
    private RelativeLayout roleView, stateNameRelative, cityNameRelative, areaNameRelative;
    private AppCompatTextView roleTitle;
    private AppCompatTextView roleTv;
    private LinearLayout legView;
    private AppCompatTextView downlineTitle;
    private RadioGroup legRadioGroup;
    private RadioButton leftRadio;
    private RadioButton rightRadio;
    private AppCompatEditText nameEt;
    private AppCompatTextView nameTitle;
    private AppCompatEditText mobileEt;
    private AppCompatEditText aadhaarNoEt;
    private AppCompatTextView mobileTitle;
    private AppCompatEditText whatsappEt;
    private AppCompatTextView whatsappTitle;
    private AppCompatEditText emailIdEt;
    private AppCompatTextView emailIdTitle;
    private AppCompatEditText addressEt;
    private AppCompatTextView addressTitle;
    private AppCompatEditText pincodeEt;
    private AppCompatTextView pincodeTitle;
    private AppCompatEditText passwordEt;
    private AppCompatTextView passwordTitle;
    private AppCompatEditText confPasswordEt;
    private AppCompatTextView confPasswordTitle;
    private AppCompatTextView register;

    private DropDownDialog mDropDownDialog;
    private String userId, userName;
    private LoginResponse mLoginDataResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private UtilsEditTextValidate mUtilsEditTextValidate;
    private String apiCalledReferal;
    private HashMap<String, Integer> rollsMap = new HashMap<>();
    private String[] rollArray;
    private CustomFilterDialog mCustomFilterDialog;
    private String refName;
    private String prefix;
    String prefixItem;
    private String dataOfPinCode = "";
    private ArrayList<DropDownModel> arrayListArea = new ArrayList<>();

    private ArrayList<DropDownModel> arrayListCity = new ArrayList<>();
    private ArrayList<DropDownModel> arrayListState = new ArrayList<>();

    private int selectedAreaPos, selectedCityPos = -1;
    private int selectedAreaId;
    private int selectedCityId;
    private int selectedStatePos = -1;
    private int selectedStateId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_networking_add_user);
            mUtilsEditTextValidate = new UtilsEditTextValidate();
            mCustomAlertDialog = new CustomAlertDialog(this);
            mCustomFilterDialog = new CustomFilterDialog(this);
            mDropDownDialog = new DropDownDialog(this);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            userId = mLoginDataResponse.getData().getUserID();
            userName = mLoginDataResponse.getData().getName();
            String prefixItem = mLoginDataResponse.getData().getPrefix();
            findViews();

          responseDialog   = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
            roleTv.setOnClickListener(v -> {
                referralIdEt.setError(null);
                if (referralIdEt.getText().toString().isEmpty()) {
                    referralIdEt.setError(getString(R.string.err_empty_field));
                    referralIdEt.requestFocus();
                    return;
                }
                stvRoll(true);
            });
            searchIv.setOnClickListener(v -> {
                referralIdEt.setError(null);
                if (referralIdEt.getText().toString().isEmpty()) {
                    referralIdEt.setError(getString(R.string.err_empty_field));
                    referralIdEt.requestFocus();
                    return;
                }
                Role(this, false);
            });

            referralIdEt.addTextChangedListener(new TextWatcher() {
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
                            referralNameEt.setText("");
                            searchIv.setVisibility(View.VISIBLE);
                        } else {
                            if (referralNameEt.getText().length() > 0) {
                                searchIv.setVisibility(View.GONE);
                            } else {
                                searchIv.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        if (referralNameEt.getText().length() > 0) {
                            searchIv.setVisibility(View.GONE);
                        } else {
                            searchIv.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
            referralIdEt.setText(prefixItem + userId);
            stvRoll(false);
            register.setOnClickListener(v -> submitForm());
        });
    }


    private void findViews() {
        logo = findViewById(R.id.logo);
        referralIdTitle = findViewById(R.id.referralIdTitle);
        referralIdEt = findViewById(R.id.referralIdEt);
        searchIv = findViewById(R.id.searchIv);
        pinCodeLayout = findViewById(R.id.pinCodeLayout);
        referralNameEt = findViewById(R.id.referralNameEt);
        referralNameTitle = findViewById(R.id.referralNameTitle);
        roleView = findViewById(R.id.roleView);
        stateNameRelative = findViewById(R.id.stateNameRelative);
        cityNameRelative = findViewById(R.id.cityNameRelative);
        areaNameRelative = findViewById(R.id.areaNameRelative);
        roleTitle = findViewById(R.id.roleTitle);
        roleTv = findViewById(R.id.roleTv);
        legView = findViewById(R.id.legView);
        stateNameTv = findViewById(R.id.stateNameTv);
        cityNameTv = findViewById(R.id.cityNameTv);
        areaNameTv = findViewById(R.id.areaNameTv);
        downlineTitle = findViewById(R.id.downlineTitle);
        legRadioGroup = findViewById(R.id.legRadioGroup);
        leftRadio = findViewById(R.id.leftRadio);
        rightRadio = findViewById(R.id.rightRadio);
        nameEt = findViewById(R.id.nameEt);
        nameTitle = findViewById(R.id.nameTitle);
        mobileEt = findViewById(R.id.mobileEt);
        aadhaarNoEt = findViewById(R.id.aadhaarNoEt);
        mobileTitle = findViewById(R.id.mobileTitle);
        whatsappEt = findViewById(R.id.whatsappEt);
        whatsappTitle = findViewById(R.id.whatsappTitle);
        emailIdEt = findViewById(R.id.emailIdEt);
        emailIdTitle = findViewById(R.id.emailIdTitle);
        addressEt = findViewById(R.id.addressEt);
        addressTitle = findViewById(R.id.addressTitle);
        pincodeEt = findViewById(R.id.pincodeEt);
        pincodeTitle = findViewById(R.id.pincodeTitle);
        passwordEt = findViewById(R.id.passwordEt);
        passwordTitle = findViewById(R.id.passwordTitle);
        confPasswordEt = findViewById(R.id.confPasswordEt);
        confPasswordTitle = findViewById(R.id.confPasswordTitle);
        register = findViewById(R.id.register);

        if (getIntent().getIntExtra("IsBinaryOn", 0) == 2) {
            legView.setVisibility(View.VISIBLE);
        } else {
            legView.setVisibility(View.GONE);
        }

        stateNameRelative.setOnClickListener(this::clickStateView);
        cityNameRelative.setOnClickListener(this::clickCityView);
        areaNameRelative.setOnClickListener(this::clickAreaView);


        pincodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    // Clear all data
                    arrayListArea.clear();
                    arrayListState.clear();
                    arrayListCity.clear();
                    stateNameTv.setText("");
                    cityNameTv.setText("");
                    areaNameTv.setText("");
                    pinCodeLayout.setVisibility(View.GONE);
                } else if (dataOfPinCode != null && !dataOfPinCode.equalsIgnoreCase(s.toString()) && s.toString().trim().length() == 6) {
                    arrayListArea.clear();
                    arrayListState.clear();
                    arrayListCity.clear();
                    stateNameTv.setText("");
                    cityNameTv.setText("");
                    areaNameTv.setText("");
                    PinCodeArea();
                }
            }
        });
    }

    private void clickAreaView(View v) {
        pincodeEt.setError(null);
        if (TextUtils.isEmpty(pincodeEt.getText())) {
            pincodeEt.setError("Please Enter Valid Area PinCode");
            pincodeEt.requestFocus();
            return;
        } else if (pincodeEt.getText().toString().replaceAll(" ", "").length() != 6) {
            pincodeEt.setError("Please Enter 6 Digit Area PinCode");
            pincodeEt.requestFocus();
            return;
        } else if (arrayListArea == null || arrayListArea.size() == 0) {
            pincodeEt.setError("Please Enter Valid Area PinCode");
            pincodeEt.requestFocus();
            return;
        }
        if (arrayListArea.size() == 1) {
            AreaWithPincode selectedArea = (AreaWithPincode) arrayListArea.get(0).getDataObject();
            areaNameTv.setText(selectedArea.getArea());
            selectedAreaPos = 0;
            selectedAreaId = selectedArea.getId();
            selectedStateId=selectedArea.getStateID();
            selectedCityId=selectedArea.getCityID();
        } else {
            mDropDownDialog.showDropDownPopup(v, selectedAreaPos, arrayListArea, (clickPosition, value, object) -> {
                if (selectedAreaPos != clickPosition) {
                    AreaWithPincode mPinCdeArea = (AreaWithPincode) object;
                    areaNameTv.setText(value);
                    selectedAreaPos = clickPosition;
                    selectedAreaId = mPinCdeArea.getId();
                    selectedStateId=mPinCdeArea.getStateID();
                    selectedCityId=mPinCdeArea.getCityID();
                }
            });

        }
    }

    private void clickCityView(View view) {
        pincodeEt.setError(null);
        if (TextUtils.isEmpty(pincodeEt.getText())) {
            pincodeEt.setError("Please Enter Valid Area PinCode");
            pincodeEt.requestFocus();
            return;
        } else if (pincodeEt.getText().toString().replaceAll(" ", "").length() != 6) {
            pincodeEt.setError("Please Enter 6 Digit Area PinCode");
            pincodeEt.requestFocus();
            return;
        } /*else if (arrayListCity == null || arrayListCity.size() == 0) {
            pincodeEt.setError("Please Enter Valid Area PinCode");
            pincodeEt.requestFocus();
            return;
        }*/
        if (arrayListCity.size()!=0) {
            AreaWithPincode selectedArea = (AreaWithPincode) arrayListCity.get(0).getDataObject();
            cityNameTv.setText(selectedArea.getDistrictname());
            selectedCityPos = 0;
            selectedCityId = selectedArea.getCityID();
        } /*else {
            mDropDownDialog.showDropDownPopup(view, selectedCityPos, arrayListCity, (clickPosition, value, object) -> {
                if (selectedCityPos != clickPosition) {
                    AreaWithPincode mPinCdeArea = (AreaWithPincode) object;
                    cityNameTv.setText(value);
                    selectedCityPos = clickPosition;
                    selectedCityId = mPinCdeArea.getCityID();
                }
            });

        }*/
    }


    void clickStateView(View v) {
        pincodeEt.setError(null);
        if (TextUtils.isEmpty(pincodeEt.getText())) {
            pincodeEt.setError("Please Enter Valid Area PinCode");
            pincodeEt.requestFocus();
            return;
        } else if (pincodeEt.getText().toString().replaceAll(" ", "").length() != 6) {
            pincodeEt.setError("Please Enter 6 Digit Area PinCode");
            pincodeEt.requestFocus();
            return;
        }/* else if (arrayListState == null || arrayListState.size() == 0) {
            pincodeEt.setError("Please Enter Valid Area PinCode");
            pincodeEt.requestFocus();
            return;
        }*/
        if (arrayListState.size()!=0) {
            AreaWithPincode selectedState = (AreaWithPincode) arrayListState.get(0).getDataObject();
            stateNameTv.setText(selectedState.getStatename());
            selectedStatePos = 0;
            selectedStateId = selectedState.getStateID();
        } /*else {
            mDropDownDialog.showDropDownPopup(v, selectedStatePos, arrayListState, (clickPosition, value, object) -> {
                if (selectedStatePos != clickPosition) {
                    AreaWithPincode mPinCdeArea = (AreaWithPincode) object;
                    stateNameTv.setText(value);
                    selectedStatePos = clickPosition;
                    selectedStateId = mPinCdeArea.getStateID();
                }
            });

        }*/
    }

    void PinCodeArea() {
        loader.show();
        ApiFintechUtilMethods.INSTANCE.PincodeArea(this, pincodeEt.getText().toString().trim(), loader,
                mLoginDataResponse, deviceId, deviceSerialNum, object -> {
                    AreaWithPincodeResponse pinCodeAreaResponse = (AreaWithPincodeResponse) object;
                    dataOfPinCode = pincodeEt.getText().toString();
                    if (pinCodeAreaResponse != null && pinCodeAreaResponse.getAreas() != null && pinCodeAreaResponse.getAreas().size() > 0) {
                        pinCodeLayout.setVisibility(View.VISIBLE);
                        for (AreaWithPincode pinCodeArea : pinCodeAreaResponse.getAreas()) {
                            arrayListState.add(new DropDownModel(pinCodeArea.getArea() + "", pinCodeArea));
                            arrayListCity.add(new DropDownModel(pinCodeArea.getArea() + "", pinCodeArea));
                            arrayListArea.add(new DropDownModel(pinCodeArea.getArea() + "", pinCodeArea));
                        }
                        if (arrayListState.size() > 0) {
                            AreaWithPincode selectedState = (AreaWithPincode) arrayListState.get(0).getDataObject();
                            stateNameTv.setText(selectedState.getStatename());
                            selectedStatePos = 0;
                            selectedStateId = selectedState.getStateID();
                        }
                        if (arrayListCity.size() > 0) {
                            AreaWithPincode selectedCity = (AreaWithPincode) arrayListCity.get(0).getDataObject();
                            selectedCityPos = 0;
                            cityNameTv.setText(selectedCity.getDistrictname());
                            selectedCityId = selectedCity.getCityID();
                            selectedCityId=selectedCity.getCityID();
                            selectedStateId=selectedCity.getStateID();
                        }
                        if (arrayListArea.size() > 0) {
                            AreaWithPincode area = (AreaWithPincode) arrayListArea.get(0).getDataObject();
                            selectedAreaPos = 0;
                            areaNameTv.setText(area.getArea());
                            selectedAreaId = area.getId();
                            selectedCityId=area.getCityID();
                            selectedStateId=area.getStateID();
                        }
                    } else {
                        pinCodeLayout.setVisibility(View.GONE);
                    }

                });
    }

    void submitForm() {
        roleTv.setError(null);
        nameEt.setError(null);
        mobileEt.setError(null);
        aadhaarNoEt.setError(null);
        emailIdEt.setError(null);
        addressEt.setError(null);
        pincodeEt.setError(null);
        if (referralIdEt.getText().toString().isEmpty()) {
            referralIdEt.setError(getString(R.string.err_empty_field));
            referralIdEt.requestFocus();
            return;
        } else if (referralNameEt.getText().toString().isEmpty()) {
            referralNameEt.setError(getString(R.string.err_empty_field));
            referralNameEt.requestFocus();
            return;
        } else if (roleView.getVisibility() == View.VISIBLE && roleTv.getText().toString().isEmpty()) {
            roleTv.setError(getString(R.string.err_empty_field));
            roleTv.requestFocus();
            return;
        } else if (nameEt.getText().toString().isEmpty()) {
            nameEt.setError(getString(R.string.err_empty_field));
            nameEt.requestFocus();
            return;
        } else if (aadhaarNoEt.getText().toString().isEmpty()) {
            aadhaarNoEt.setError(getString(R.string.err_empty_field));
            aadhaarNoEt.requestFocus();
            return;
        } else if (aadhaarNoEt.getText().toString().length() != 12) {
            aadhaarNoEt.setError(getString(R.string.aadhaar));
            aadhaarNoEt.requestFocus();
            return;
        }else if (mobileEt.getText().toString().isEmpty()) {
            mobileEt.setError(getString(R.string.err_empty_field));
            mobileEt.requestFocus();
            return;
        } else if (mobileEt.getText().toString().length() != 10) {
            mobileEt.setError(getString(R.string.err_msg_mobile_length));
            mobileEt.requestFocus();
            return;
        } else if (emailIdEt.getText().toString().isEmpty()) {
            emailIdEt.setError(getString(R.string.err_empty_field));
            emailIdEt.requestFocus();
            return;
        } else if (!emailIdEt.getText().toString().contains("@") || !emailIdEt.getText().toString().contains(".")) {
            emailIdEt.setError(getString(R.string.err_msg_email));
            emailIdEt.requestFocus();
            return;
        } else if (addressEt.getText().toString().isEmpty()) {
            addressEt.setError(getString(R.string.err_empty_field));
            addressEt.requestFocus();
            return;
        } else if (pincodeEt.getText().toString().isEmpty()) {
            pincodeEt.setError(getString(R.string.err_empty_field));
            pincodeEt.requestFocus();
            return;
        } else if (pincodeEt.getText().toString().length() != 6) {
            pincodeEt.setError(getString(R.string.pincode_error));
            pincodeEt.requestFocus();
            return;
        }

        Register(this);


    }

    void stvRoll(boolean isFromClick) {

        if (rollArray != null && rollArray.length > 0) {
            if (apiCalledReferal != null && !apiCalledReferal.equalsIgnoreCase(referralIdEt.getText().toString())) {
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

            Call<LoginResponse> call = git.GetRoleForReferral(new GetRoleForReferralRequest(referralIdEt.getText().toString(),
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


                                    if (response.body().getName() != null) {
                                        refName = response.body().getName();
                                        // referralNameEt.setText(response.body().getName() + "");

                                        if (response.body().isReferralEditable == true) {
                                            prefix = response.body().getChildRoles().get(0).getPrefix();
                                        } else {
                                            //referralIdEt.setText(prefixItem+userId);
                                        }
                                    } else {
                                        referralNameEt.setText("");
                                    }


                                    if (response.body().getIsBinaryon() == 2) {
                                        legView.setVisibility(View.VISIBLE);
                                        if (referralIdEt.getText().toString().equalsIgnoreCase("1")) {
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
                                    } else {
                                        legView.setVisibility(View.GONE);
                                    }
                                    apiCalledReferal = response.body().getSignupRefferalId() + "";
                                    apiCalledReferal = response.body().getSignupRefferalId() + "";
                                    //   referralIdEt.setText(prefix+apiCalledReferal);
                                    //referralIdEt.setText(prefixItem+userId);
                                    referralNameEt.setText(refName);
                                    // referralIdEt.setText(apiCalledReferal);
                                    searchIv.setVisibility(View.GONE);


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
                                            roleTv.setText(rollArray[0]);
                                        }
                                        if (isFromClick) {
                                            selectRole();
                                        }
                                    }
                                } else {
                                    referralNameEt.setText("");
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
        if (roleTv.getText().toString().length() == 0) {
            selectedIndex = -1;
        } else {
            selectedIndex = Arrays.asList(rollArray).indexOf(roleTv.getText().toString());
        }
        mCustomFilterDialog.showSingleChoiceAlert(rollArray, selectedIndex, "Date Type", "Choose Date Type", new CustomFilterDialog.SingleChoiceDialogCallBack() {
            @Override
            public void onPositiveClick(int index) {
                if (index != -1) {
                    roleTv.setText(rollArray[index]);
                    roleTv.setError(null);
                }
            }

            @Override
            public void onNegativeClick() {

            }
        });
    }

    public void Register(final Activity context) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            UserCreateSignup mUserCreateSignup = new UserCreateSignup(Integer.parseInt(apiCalledReferal),
                    addressEt.getText().toString(),
                    mobileEt.getText().toString(),aadhaarNoEt.getText().toString(), nameEt.getText().toString(),
                    nameEt.getText().toString(),
                    emailIdEt.getText().toString(), referralIdEt.getText().toString(), rollsMap.get(roleTv.getText().toString()), pincodeEt.getText().toString(),
                    leftRadio.isChecked() ? "L" : "R",
                    selectedCityId+"",selectedStateId+"",selectedAreaId+"");
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
        TextView labelTv = viewMyLayout.findViewById(R.id.labelTv);
        labelTv.setText("Add Member");
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
        /* copy.setOnClickListener(view -> Utility.INSTANCE.setClipboard(this, loginUrl.getText().toString(), "Login Url"));
         */

        responseDialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
