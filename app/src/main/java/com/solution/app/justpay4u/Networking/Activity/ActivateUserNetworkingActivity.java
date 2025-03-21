package com.solution.app.justpay4u.Networking.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Networking.Object.GetTopupDetailsByUserIdData;
import com.solution.app.justpay4u.Api.Networking.Request.ActivateUserRequest;
import com.solution.app.justpay4u.Api.Networking.Request.GetTopupDetailsByUserIdRequest;
import com.solution.app.justpay4u.Api.Networking.Response.FindUserDetailsByIdResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApiNetworkingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.NetworkingEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.MoveToWallet.Adapter.WalletBalanceAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class ActivateUserNetworkingActivity extends AppCompatActivity {

    double selectedPackageAmount;
    CustomLoader loader;
    CustomAlertDialog mCustomAlertDialog;
    ArrayList<GetTopupDetailsByUserIdData> mBuisnessTypeList = new ArrayList<>();
    ArrayList<GetTopupDetailsByUserIdData> mPackagesList = new ArrayList<>();
    ArrayList<GetTopupDetailsByUserIdData> mWalletTypeList = new ArrayList<>();


    private AppCompatTextView packageTv, walletTypeTv;
    private AppCompatTextView amountTv, amountPkgTv, amountWalletBalanceTv;
    private AppCompatEditText userIdEt;
    private AppCompatTextView userIdTitle;
    private AppCompatTextView register, typeTv, receiverNameTv;
    View userIdView, typeView, typeContatinerView, packageView, packageContainerView, walletContainerView,
            walletTypeView, receiverDetailView, searchIv;
    RadioGroup radioGroup;
    private String userId, userName;
    private int selectedEPinId;
    private int selectedPackageId;

    RecyclerView walletBalance;
    private String selectedWallet = "E";
    private LoginResponse mLoginDataResponse;
    private BalanceResponse balanceCheckResponse;
    private AppPreferences mAppPreferences;
    private int userTopupType;
    private String deviceId, deviceSerialNum;
    private DropDownDialog mDropDownDialog;
    private int selectedPackageIndex = -1;
    private int selectedTypeIndex = -1;
    private String beneficiaryUserId = "0";
    ArrayList<BalanceData> balanceTypes = new ArrayList<>();
    private int selectedTypeOId;
    private int selectedWalletIndex = -1;
    private int selectedWalletTypeId;
    private double selectedWalletBalance;
    private double packageAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_networking_activate_user);
            setTitle(getIntent().getStringExtra("Title") + "");
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            balanceTypes = getIntent().getParcelableArrayListExtra("items");
            userTopupType = getIntent().getIntExtra("TopupType", 0);
            mDropDownDialog = new DropDownDialog(this);

            mCustomAlertDialog = new CustomAlertDialog(this);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            userId = mLoginDataResponse.getData().getUserID();
            userName = mLoginDataResponse.getData().getName();
            findViews();


            typeContatinerView.setOnClickListener(v -> {
                if (mBuisnessTypeList != null && mBuisnessTypeList.size() > 0) {
                    mDropDownDialog.showDropDownPopup(v, selectedTypeIndex, 1, mBuisnessTypeList,
                            (clickPosition, item) -> {
                                setBuisnessTypeData(item);
                            });
                } else {

                    Toast.makeText(this, "Type is not available", Toast.LENGTH_SHORT).show();
                }
            });
            packageContainerView.setOnClickListener(v -> {
                if (mPackagesList != null && mPackagesList.size() > 0) {
                    mDropDownDialog.showDropDownPopup(v, selectedPackageIndex, 2, mPackagesList,
                            (clickPosition, item) -> {
                                setPackageTypeData(item);
                                /*packageTv.setText(item.getPackageName() + "");
                                packageTv.setError(null);
                                amountPkgTv.setText(Utility.INSTANCE.formatedAmountWithRupees(item.getPackageCost() + ""));
                                selectedPackageId = item.getPackageId();
                                selectedPackageAmount = item.getPackageCost();*/
                            });
                } else {

                    Toast.makeText(this, "Package is not available", Toast.LENGTH_SHORT).show();
                }
            });
            walletContainerView.setOnClickListener(v -> {
                if (mWalletTypeList != null && mWalletTypeList.size() > 0) {
                    mDropDownDialog.showDropDownPopup(v, selectedWalletIndex, 2, mWalletTypeList,
                            (clickPosition, item) -> {
                                setWalletTypeData(item);
                                /*walletTypeTv.setText(item.getName() + "");
                                walletTypeTv.setError(null);
                                amountWalletBalanceTv.setText(Utility.INSTANCE.formatedAmountWithRupees(item.getUserBalance() + ""));
                                selectedWalletTypeId = item.getId();
                                selectedWalletBalance = item.getUserBalance();*/
                            });
                } else {

                    Toast.makeText(this, "Wallet is not available", Toast.LENGTH_SHORT).show();
                }
            });


            searchIv.setOnClickListener(v -> {
                if (userIdEt.getText().toString().isEmpty()) {
                    userIdEt.setError("Please Enter Valid User Id");
                    userIdEt.requestFocus();
                    return;
                }

                getDataByUserIdApi();
            });

            userIdEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (receiverDetailView.getVisibility() == View.VISIBLE) {
                        receiverDetailView.setVisibility(View.GONE);
                        typeView.setVisibility(View.GONE);
                        packageView.setVisibility(View.GONE);
                        walletTypeView.setVisibility(View.GONE);
                        searchIv.setVisibility(View.VISIBLE);
                        beneficiaryUserId = "0";
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            register.setOnClickListener(v -> submitDetail());
            showBalanceData();

        });
    }

    private void setBuisnessTypeData(GetTopupDetailsByUserIdData item) {
        typeTv.setText(item.getName() + "");
        typeTv.setError(null);
        selectedTypeOId = item.getOid();

        if (item.getPackageList() != null && item.getPackageList().size() > 0) {
            mPackagesList = item.getPackageList();
            if (mPackagesList.size() > 0) {
                packageView.setVisibility(View.VISIBLE);
            } else {
                packageView.setVisibility(View.GONE);
                setPackageTypeData(mPackagesList.get(0));

            }

        } else {
            packageView.setVisibility(View.GONE);
        }
        if (item.getWalletTypeList() != null && item.getWalletTypeList().size() > 0) {
            mWalletTypeList = item.getWalletTypeList();
            if (mWalletTypeList.size() > 1) {
                walletTypeView.setVisibility(View.VISIBLE);

            } else {
                walletTypeView.setVisibility(View.GONE);
                setWalletTypeData(mWalletTypeList.get(0));

            }

        } else {
            walletTypeView.setVisibility(View.GONE);
        }
    }

    void setPackageTypeData(GetTopupDetailsByUserIdData item) {
        packageTv.setText(item.getPackageName() + "");
        packageTv.setError(null);
        amountPkgTv.setText(Utility.INSTANCE.formatedAmountWithRupees(item.getPackageCost() + ""));
        packageAmount=item.getPackageCost();
        selectedPackageId = item.getPackageId();
        selectedPackageAmount = item.getPackageCost();
    }

    void setWalletTypeData(GetTopupDetailsByUserIdData item) {
        walletTypeTv.setText(item.getName() + "");
        walletTypeTv.setError(null);
        amountWalletBalanceTv.setText(Utility.INSTANCE.formatedAmountWithRupees(item.getUserBalance() + ""));
        selectedWalletTypeId = item.getId();
        selectedWalletBalance = item.getUserBalance();
    }

    void findViews() {
        radioGroup = findViewById(R.id.radioGroup);
        walletBalance = findViewById(R.id.walletBalance);
        walletBalance.setLayoutManager(new LinearLayoutManager(this));
        searchIv = findViewById(R.id.searchIv);
        packageTv = findViewById(R.id.packageTv);
        walletTypeTv = findViewById(R.id.walletType);
        amountTv = findViewById(R.id.amount);
        amountPkgTv = findViewById(R.id.amountPkg);
        amountWalletBalanceTv = findViewById(R.id.amountWalletBalance);
        userIdEt = findViewById(R.id.userId);
        userIdTitle = findViewById(R.id.userIdTitle);
        userIdView = findViewById(R.id.userIdView);
        typeView = findViewById(R.id.typeView);
        typeTv = findViewById(R.id.type);
        typeContatinerView = findViewById(R.id.typeContatiner);
        packageContainerView = findViewById(R.id.packageContainer);
        walletContainerView = findViewById(R.id.walletContainer);
        packageView = findViewById(R.id.packageView);
        walletTypeView = findViewById(R.id.walletTypeView);
        receiverDetailView = findViewById(R.id.receiverDetailView);
        receiverNameTv = findViewById(R.id.receiverName);
        register = findViewById(R.id.register);

    }

    private void showBalanceData() {
        if (userTopupType == 1) {
            userIdEt.setText(userId + "");
            userIdEt.setFocusable(false);
            userIdEt.setFocusableInTouchMode(false);
            userIdEt.setClickable(false);
            userIdEt.setLongClickable(false);
            getDataByUserIdApi();
        }
        if (balanceTypes != null && balanceTypes.size() > 0) {
            WalletBalanceAdapter mAdapter = new WalletBalanceAdapter(this, balanceTypes);
            walletBalance.setAdapter(mAdapter);
        } else {
            balanceCheckResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                    && balanceCheckResponse.getBalanceData().size() > 0) {

                balanceTypes = balanceCheckResponse.getBalanceData();
                if (balanceTypes != null && balanceTypes.size() > 0) {
                    showBalanceData();
                }
            } else {
                ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse,
                        deviceId, deviceSerialNum,
                        mAppPreferences, null, object -> {
                            balanceCheckResponse = (BalanceResponse) object;
                            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                    && balanceCheckResponse.getBalanceData().size() > 0) {
                                balanceTypes = balanceCheckResponse.getBalanceData();
                                if (balanceTypes != null && balanceTypes.size() > 0) {
                                    showBalanceData();
                                }
                            }
                        });
            }
        }
    }


    void submitDetail() {
        if (userIdView.getVisibility() == View.VISIBLE && receiverDetailView.getVisibility() != View.VISIBLE) {
            userIdEt.setError("Please Enter Valid User id");
            userIdEt.requestFocus();
            return;
        } else if (typeView.getVisibility() == View.VISIBLE && typeTv.getText().toString().isEmpty()) {
            typeTv.setError("Please Select Type");
            typeTv.requestFocus();
            return;
        } else if (packageView.getVisibility() == View.VISIBLE && packageTv.getText().toString().isEmpty()) {
            packageTv.setError("Please Select Package");
            packageTv.requestFocus();
            return;
        } else if (walletTypeView.getVisibility() == View.VISIBLE && walletTypeTv.getText().toString().isEmpty()) {
            walletTypeTv.setError("Please Select Wallet");
            walletTypeTv.requestFocus();
            return;
        }

        ActivateUserApi();
    }


    void getDataByUserIdApi() {
        try {

            if (ApiNetworkingUtilMethods.INSTANCE.isVpnConnected(this)) {
                mCustomAlertDialog.ErrorVPN(this);
            } else {
                loader.show();
                NetworkingEndPointInterface git = ApiClient.getClient().create(NetworkingEndPointInterface.class);

                Call<FindUserDetailsByIdResponse> call = git.GetTopupDetailsByUserID(new GetTopupDetailsByUserIdRequest(new BasicRequest(
                        userId, mLoginDataResponse.getData().getLoginTypeID(),
                        ApplicationConstant.INSTANCE.APP_ID,
                        deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                        mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()),
                        new GetTopupDetailsByUserIdRequest( TextUtils.isDigitsOnly(userIdEt.getText().toString()) ? userIdEt.getText().toString() : userIdEt.getText().toString().replaceAll("[^0-9]", ""),1)));

                call.enqueue(new Callback<FindUserDetailsByIdResponse>() {
                    @Override
                    public void onResponse(Call<FindUserDetailsByIdResponse> call, final retrofit2.Response<FindUserDetailsByIdResponse> response) {
                        if (response != null) {
                            if (response.isSuccessful()) {

                                try {
                                    if (response.body() != null) {

                                        FindUserDetailsByIdResponse data = response.body();
                                        loader.dismiss();
                                        if (data.getStatuscode() == 1) {

                                            if (data.getName() != null && !data.getName().isEmpty()) {
                                                beneficiaryUserId = data.getUserId() + "";
                                                receiverDetailView.setVisibility(View.VISIBLE);
                                                searchIv.setVisibility(View.GONE);
                                                userIdEt.setError(null);
                                                setReceiverDetails(data.getName() + "",
                                                        data.getMobile() + "",
                                                        data.getEmailId() + "");

                                                if (data.getData() != null && data.getData().size() > 0) {

                                                    mBuisnessTypeList = data.getData();
                                                    if (mBuisnessTypeList.size() > 1) {
                                                        typeView.setVisibility(View.VISIBLE);
                                                    } else {
                                                        typeView.setVisibility(View.GONE);
                                                        setBuisnessTypeData(mBuisnessTypeList.get(0));
                                                    }
                                                } else {
                                                    typeView.setVisibility(View.GONE);
                                                    packageView.setVisibility(View.GONE);
                                                    walletTypeView.setVisibility(View.GONE);
                                                }
                                            } else {
                                                typeView.setVisibility(View.GONE);
                                                packageView.setVisibility(View.GONE);
                                                walletTypeView.setVisibility(View.GONE);
                                                receiverDetailView.setVisibility(View.GONE);
                                                searchIv.setVisibility(View.VISIBLE);
                                                ApiNetworkingUtilMethods.INSTANCE.Error(ActivateUserNetworkingActivity.this, "User does not exists");
                                            }


                                        } else {
                                            typeView.setVisibility(View.GONE);
                                            packageView.setVisibility(View.GONE);
                                            walletTypeView.setVisibility(View.GONE);
                                            receiverDetailView.setVisibility(View.GONE);
                                            searchIv.setVisibility(View.VISIBLE);
                                            if (!data.isVersionValid()) {
                                                ApiFintechUtilMethods.INSTANCE.versionDialog(ActivateUserNetworkingActivity.this);
                                            } else {
                                                ApiNetworkingUtilMethods.INSTANCE.Error(ActivateUserNetworkingActivity.this, data.getMsg() + "");
                                            }
                                        }

                                    } else {
                                        typeView.setVisibility(View.GONE);
                                        packageView.setVisibility(View.GONE);
                                        walletTypeView.setVisibility(View.GONE);
                                        loader.dismiss();
                                        receiverDetailView.setVisibility(View.GONE);
                                        searchIv.setVisibility(View.VISIBLE);
                                        ApiNetworkingUtilMethods.INSTANCE.Error(ActivateUserNetworkingActivity.this, getString(R.string.some_thing_error));

                                    }
                                } catch (Exception ex) {
                                    loader.dismiss();
                                    typeView.setVisibility(View.GONE);
                                    packageView.setVisibility(View.GONE);
                                    walletTypeView.setVisibility(View.GONE);
                                    receiverDetailView.setVisibility(View.GONE);
                                    searchIv.setVisibility(View.VISIBLE);
                                    ApiNetworkingUtilMethods.INSTANCE.Error(ActivateUserNetworkingActivity.this, ex.getMessage() + "");

                                }
                            } else {
                                loader.dismiss();
                                receiverDetailView.setVisibility(View.GONE);
                                typeView.setVisibility(View.GONE);
                                packageView.setVisibility(View.GONE);
                                walletTypeView.setVisibility(View.GONE);
                                searchIv.setVisibility(View.VISIBLE);
                                ApiNetworkingUtilMethods.INSTANCE.apiErrorHandle(ActivateUserNetworkingActivity.this, response.code(),
                                        response.message());
                            }
                        } else {
                            typeView.setVisibility(View.GONE);
                            packageView.setVisibility(View.GONE);
                            walletTypeView.setVisibility(View.GONE);
                            loader.dismiss();
                            receiverDetailView.setVisibility(View.GONE);
                            searchIv.setVisibility(View.VISIBLE);
                            ApiNetworkingUtilMethods.INSTANCE.Error(ActivateUserNetworkingActivity.this, getString(R.string.some_thing_error));

                        }
                    }

                    @Override
                    public void onFailure(Call<FindUserDetailsByIdResponse> call, Throwable t) {
                        receiverDetailView.setVisibility(View.GONE);
                        searchIv.setVisibility(View.VISIBLE);
                        typeView.setVisibility(View.GONE);
                        packageView.setVisibility(View.GONE);
                        walletTypeView.setVisibility(View.GONE);
                        try {

                            loader.dismiss();
                            ApiNetworkingUtilMethods.INSTANCE.apiFailureError(ActivateUserNetworkingActivity.this, t);
                        } catch (IllegalStateException ise) {
                            loader.dismiss();
                            ApiNetworkingUtilMethods.INSTANCE.Error(ActivateUserNetworkingActivity.this, ise.getMessage() + "");


                        }
                    }
                });
            }
        } catch (Exception ex) {
            loader.dismiss();
            searchIv.setVisibility(View.VISIBLE);
            typeView.setVisibility(View.GONE);
            packageView.setVisibility(View.GONE);
            walletTypeView.setVisibility(View.GONE);
            receiverDetailView.setVisibility(View.GONE);
            ApiNetworkingUtilMethods.INSTANCE.Error(ActivateUserNetworkingActivity.this, ex.getMessage() + "");

        }
    }

    void setReceiverDetails(String name, String mob, String email) {

        receiverNameTv.setText(Html.fromHtml("<font color='#c9c6c3'><b>Name :</b></font> " + name
                + "<br/><font color='#c9c6c3'><b>Mobile No :</b></font> " + mob
                + "<br/><font color='#c9c6c3'><b>Email Id :</b></font> " + email));
    }

    void ActivateUserApi() {
        try {

            if (ApiNetworkingUtilMethods.INSTANCE.isVpnConnected(this)) {
                mCustomAlertDialog.ErrorVPN(this);
            } else {
                loader.show();
                NetworkingEndPointInterface git = ApiClient.getClient().create(NetworkingEndPointInterface.class);

                Call<BasicResponse> call = git.ActivateUser(new ActivateUserRequest(new BasicRequest(
                        userId, mLoginDataResponse.getData().getLoginTypeID(),
                        ApplicationConstant.INSTANCE.APP_ID,
                        deviceId,
                        "", BuildConfig.VERSION_NAME, deviceSerialNum,
                        mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()),
                        new ActivateUserRequest(beneficiaryUserId + "",
                        selectedWalletTypeId, selectedPackageId, selectedTypeOId,packageAmount, "")));
                call.enqueue(new Callback<BasicResponse>() {
                    @Override
                    public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                try {

                                    if (response.body() != null) {

                                        BasicResponse data = response.body();
                                        loader.dismiss();
                                        if (data.getStatuscode() == 1) {
                                            ApiNetworkingUtilMethods.INSTANCE.Successful(ActivateUserNetworkingActivity.this, data.getMsg() + "");
                                        } else {
                                            if (!data.isVersionValid()) {
                                                ApiFintechUtilMethods.INSTANCE.versionDialog(ActivateUserNetworkingActivity.this);
                                            } else {
                                                ApiNetworkingUtilMethods.INSTANCE.Error(ActivateUserNetworkingActivity.this, data.getMsg() + "");
                                            }
                                        }

                                    } else {

                                        loader.dismiss();

                                        ApiNetworkingUtilMethods.INSTANCE.Error(ActivateUserNetworkingActivity.this, getString(R.string.some_thing_error));

                                    }
                                } catch (Exception ex) {
                                    loader.dismiss();

                                    ApiNetworkingUtilMethods.INSTANCE.Error(ActivateUserNetworkingActivity.this, ex.getMessage() + "");

                                }
                            } else {
                                ApiNetworkingUtilMethods.INSTANCE.apiErrorHandle(ActivateUserNetworkingActivity.this,
                                        response.code(), response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BasicResponse> call, Throwable t) {
                        try {

                            loader.dismiss();
                            ApiNetworkingUtilMethods.INSTANCE.apiFailureError(ActivateUserNetworkingActivity.this, t);

                        } catch (IllegalStateException ise) {
                            loader.dismiss();
                            ApiNetworkingUtilMethods.INSTANCE.Error(ActivateUserNetworkingActivity.this, ise.getMessage() + "");


                        }
                    }
                });
            }
        } catch (Exception ex) {
            loader.dismiss();
            ApiNetworkingUtilMethods.INSTANCE.Error(ActivateUserNetworkingActivity.this, ex.getMessage() + "");

        }
    }


   /* void getEPinListApi() {
        try {

            if (UtilMethods.INSTANCE.isVpnConnected(this)) {
                mCustomAlertDialog.ErrorVPN(this);
            } else {
                loader.show();
                EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
                char c = 160;
                String tokenCode = String.valueOf(c);
                Call<EPinListResponse> call = git.GetEPinForReg(new NativeUtil().stringKey() + tokenCode + mAppPreferences.get(getString(R.string.deviceId)) + tokenCode + userId + tokenCode + mAppPreferences.get(getString(R.string.session_id)), mAppPreferences.get(getString(R.string.user_password)), String.valueOf(selectedPlanId));
                call.enqueue(new Callback<EPinListResponse>() {
                    @Override
                    public void onResponse(Call<EPinListResponse> call, final retrofit2.Response<EPinListResponse> response) {
                        if (response != null) {

                            try {
                                if (response.body() != null) {

                                    EPinListResponse data = response.body();
                                    loader.dismiss();
                                    if (data.getStatus().equalsIgnoreCase("1")) {

                                        if (data.getePinReport() != null && data.getePinReport().size() > 0) {

                                            mEPinList.addAll(data.getePinReport());

                                        } else {
                                            mCustomAlertDialog.showOneBtnDialog("Error!", data.getMessage(), "Ok", true);
                                        }

                                    } else {

                                        mCustomAlertDialog.showOneBtnDialog("Error!", data.getMessage(), "Ok", true);
                                    }

                                } else {

                                    loader.dismiss();

                                    mCustomAlertDialog.showOneBtnDialog("Error!", getString(R.string.something_error), "Ok", true);

                                }
                            } catch (Exception ex) {
                                Log.e("exception", ex.getMessage());

                                loader.dismiss();

                                mCustomAlertDialog.showOneBtnDialog("Error!", ex.getMessage(), "Ok", true);

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<EPinListResponse> call, Throwable t) {
                        try {

                            loader.dismiss();
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                                if (t.getMessage().contains("No address associated with hostname")) {

                                    mCustomAlertDialog.showOneBtnDialog("Network issue!", getString(R.string.internet_error_msg), "Ok", true);

                                } else {
                                    mCustomAlertDialog.showOneBtnDialog("Error!", t.getMessage(), "Ok", true);
                                }

                            } else {

                                mCustomAlertDialog.showOneBtnDialog("Error!", getString(R.string.something_error), "Ok", true);

                            }
                        } catch (IllegalStateException ise) {

                            mCustomAlertDialog.showOneBtnDialog("Error!", ise.getMessage(), "Ok", true);

                        }
                    }
                });
            }
        } catch (Exception ex) {
            loader.dismiss();
            mCustomAlertDialog.showOneBtnDialog("Error!", ex.getMessage(), "Ok", true);

        }
    }

    void getUplineNameApi() {
        try {
            if (UtilMethods.INSTANCE.isVpnConnected(this)) {
                mCustomAlertDialog.ErrorVPN(this);
            } else {
                loader.show();
                EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
                char c = 160;
                String tokenCode = String.valueOf(c);
                Call<UplineNameResponse> call = git.GetUplineDetail(new NativeUtil().stringKey() + tokenCode + mAppPreferences.get(getString(R.string.deviceId)) + tokenCode + userId + tokenCode + mAppPreferences.get(getString(R.string.session_id)), mAppPreferences.get(getString(R.string.user_password)), userIdEt.getText().toString());
                call.enqueue(new Callback<UplineNameResponse>() {
                    @Override
                    public void onResponse(Call<UplineNameResponse> call, final retrofit2.Response<UplineNameResponse> response) {
                        if (response != null) {

                            try {
                                if (response.body() != null) {

                                    UplineNameResponse data = response.body();
                                    loader.dismiss();
                                    if (data.getRESPONSESTATUS().equalsIgnoreCase("1")) {

                                        if (data.getUserName() != null) {
                                            userNameTv.setError(null);
                                            userNameTv.setText(data.getUserName());

                                        } else {
                                            userNameTv.setError(data.getMessage());
                                            userNameTv.requestFocus();
                                            mCustomAlertDialog.showOneBtnDialog("Error!", data.getMessage(), "Ok", true);
                                        }

                                    } else {

                                        mCustomAlertDialog.showOneBtnDialog("Error!", data.getMessage(), "Ok", true);
                                    }

                                } else {

                                    loader.dismiss();

                                    mCustomAlertDialog.showOneBtnDialog("Error!", getString(R.string.something_error), "Ok", true);

                                }
                            } catch (Exception ex) {
                                Log.e("exception", ex.getMessage());

                                loader.dismiss();

                                mCustomAlertDialog.showOneBtnDialog("Error!", ex.getMessage(), "Ok", true);

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UplineNameResponse> call, Throwable t) {
                        try {

                            loader.dismiss();
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                                if (t.getMessage().contains("No address associated with hostname")) {

                                    mCustomAlertDialog.showOneBtnDialog("Network issue!", getString(R.string.internet_error_msg), "Ok", true);

                                } else {
                                    mCustomAlertDialog.showOneBtnDialog("Error!", t.getMessage(), "Ok", true);
                                }

                            } else {

                                mCustomAlertDialog.showOneBtnDialog("Error!", getString(R.string.something_error), "Ok", true);

                            }
                        } catch (IllegalStateException ise) {

                            mCustomAlertDialog.showOneBtnDialog("Error!", ise.getMessage(), "Ok", true);

                        }
                    }
                });
            }
        } catch (Exception ex) {
            loader.dismiss();
            mCustomAlertDialog.showOneBtnDialog("Error!", ex.getMessage(), "Ok", true);

        }
    }

    void ActivateUserApi() {

        try {
            if (UtilMethods.INSTANCE.isVpnConnected(this)) {
                mCustomAlertDialog.ErrorVPN(this);
            }else {
                loader.show();
                EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
                char c = 160;
                String tokenCode = String.valueOf(c);
                Call<BasicResponse> call;

                if (type == 1) {

                    call = git.UpgradeMOneyfyUser(new NativeUtil().stringKey() + tokenCode + mAppPreferences.get(getString(R.string.deviceId)) + tokenCode + userId + tokenCode + mAppPreferences.get(getString(R.string.session_id)),
                            mAppPreferences.get(getString(R.string.user_password)),
                            userIdEt.getText().toString(),selectedWallet,
                            selectedAmount + "");
                } else {
                    call = git.ActivateMOneyfyUser(new NativeUtil().stringKey() + tokenCode + mAppPreferences.get(getString(R.string.deviceId)) + tokenCode + userId + tokenCode + mAppPreferences.get(getString(R.string.session_id)),
                            mAppPreferences.get(getString(R.string.user_password)),
                            userIdEt.getText().toString(),selectedWallet,
                            selectedAmount + "");
                }

                call.enqueue(new Callback<BasicResponse>() {
                    @Override
                    public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {
                        if (response != null) {

                            try {
                                if (response.body() != null) {

                                    BasicResponse data = response.body();
                                    loader.dismiss();
                                    if (data.getStatus().equalsIgnoreCase("1")) {
                                        if(selectedWallet.equalsIgnoreCase("E")){
                                            try {

                                                Double remainBalance = Double.parseDouble(mAppPreferences.get(getString(R.string.user_epin_wallet))) - selectedAmount;
                                                ePinBalanceTv.setText(getString(R.string.rupees) + " " + new Utility().formatedAmount(String.valueOf(remainBalance)));
                                                mAppPreferences.set(getString(R.string.user_epin_wallet), new Utility().formatedAmount(String.valueOf(remainBalance)));

                                            } catch (NumberFormatException nfe) {

                                            }
                                        }else{
                                            try {

                                                Double remainBalance = Double.parseDouble(mAppPreferences.get(getString(R.string.user_dmr_wallet))) - selectedAmount;
                                                ePinBalanceTv.setText(getString(R.string.rupees) + " " + new Utility().formatedAmount(String.valueOf(remainBalance)));
                                                mAppPreferences.set(getString(R.string.user_dmr_wallet), new Utility().formatedAmount(String.valueOf(remainBalance)));

                                            } catch (NumberFormatException nfe) {

                                            }
                                        }

                                        if (data.getUserId() != null) {

                                            mCustomAlertDialog.showOneBtnDialogWithCallback("Success!", data.getMessage() + "\nUserId : " + data.getUserId(), "Ok", false, new PositiveDialogCallBack() {
                                                @Override
                                                public void onPositiveClick() {
                                                    finish();
                                                }
                                            });

                                        } else {
                                            mCustomAlertDialog.showOneBtnDialogWithCallback("Success!", data.getMessage(), "Ok", false, new PositiveDialogCallBack() {
                                                @Override
                                                public void onPositiveClick() {
                                                    finish();
                                                }
                                            });
                                        }

                                    } else {

                                        mCustomAlertDialog.showOneBtnDialog("Error!", data.getMessage(), "Ok", true);
                                    }

                                } else {

                                    loader.dismiss();

                                    mCustomAlertDialog.showOneBtnDialog("Error!", getString(R.string.something_error), "Ok", true);

                                }
                            } catch (Exception ex) {
                                Log.e("exception", ex.getMessage());

                                loader.dismiss();

                                mCustomAlertDialog.showOneBtnDialog("Error!", ex.getMessage(), "Ok", true);

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BasicResponse> call, Throwable t) {
                        try {

                            loader.dismiss();
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                                if (t.getMessage().contains("No address associated with hostname")) {

                                    mCustomAlertDialog.showOneBtnDialog("Network issue!", getString(R.string.internet_error_msg), "Ok", true);

                                } else {
                                    mCustomAlertDialog.showOneBtnDialog("Error!", t.getMessage(), "Ok", true);
                                }

                            } else {

                                mCustomAlertDialog.showOneBtnDialog("Error!", getString(R.string.something_error), "Ok", true);

                            }
                        } catch (IllegalStateException ise) {

                            mCustomAlertDialog.showOneBtnDialog("Error!", ise.getMessage(), "Ok", true);

                        }
                    }
                });
            }
        } catch (Exception ex) {
            loader.dismiss();
            mCustomAlertDialog.showOneBtnDialog("Error!", ex.getMessage(), "Ok", true);

        }

    }*/


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
