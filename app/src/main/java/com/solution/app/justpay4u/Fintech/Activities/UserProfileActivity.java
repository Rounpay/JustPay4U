package com.solution.app.justpay4u.Fintech.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dreamseller.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DFStatusResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetEKYCDetailResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetUserResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Networking.Object.DashboardDownlineData;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.AppUserListActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.FosUserListActivity;
import com.solution.app.justpay4u.Fintech.Authentication.ChangePinPassActivity;
import com.solution.app.justpay4u.Fintech.Dashboard.Adapter.WalletBalanceAdapter;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.AddMoneyActivity;
import com.solution.app.justpay4u.Fintech.MoveToWallet.Activity.MoveToWalletActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.AddressListShoppingActivity;
import com.solution.app.justpay4u.Shopping.Activity.CartDetailShoppingActivity;
import com.solution.app.justpay4u.Shopping.Activity.OrderListShoppingActivity;
import com.solution.app.justpay4u.Shopping.Activity.WishlistShoppingActivity;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.EKYCStepsDialog;
import com.solution.app.justpay4u.Util.Utility;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Administrator on 04-11-2017.
 */

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    View editProfile, editBankView, settlementAccountView, userIconView;
    TextView kycStatus, kycStatusMsg, sendMoneyTv;
    View sendMoneyView, addMoneyView, myOrderView, myWishListView, myAddressView, myCartView;
    CustomLoader loader;
    View changePassword, changePinPassword;
    ArrayList<BalanceData> mBalanceTypes = new ArrayList<>();
    BalanceResponse balanceCheckResponse;
    View kycDetailCard;
    GetUserResponse mGetUserResponse;
    DashboardDownlineData singleDataResponse;
    SwitchCompat toggleDoubleFactor, toggleRealApi, toggleVoice;
    CustomAlertDialog mCustomPassDialog;
    private TextView outletName;
    private View addressView, outletNameView;
    private TextView address,roleTv,verifyUserIv;
    private View rankView;
    private View rankDetail,teamDetail,incomeDetail,holdComm;
    private TextView todayActive,todayDeactive;
    private TextView todayIncome,totalIncome, holdIncome;
    private TextView rankTV;
    private TextView lastPkgTV;
    private TextView rank;
    private AppCompatImageView rankIV;
    private View pincodeView;
    private TextView pincode, voiceLabel;
    private View stateView;
    private TextView state;
    private ImageView userImage;
    private View cityView;
    private TextView city;
    private View gstinView;
    private TextView gstin;
    private View aadharView;
    private TextView aadhar;
    private View panView;
    private TextView pan, doubleFactorLabel, realApiLabel;
    private TextView name;
    private TextView mobile;
    private TextView email, role;
    private View doubleFactorLayoutContainer, realApiLayoutContainer, voiceLayoutContainer;
    private boolean isDoubleFactorEnable;
    private DFStatusResponse mDfStatusResponse;
    private WalletBalanceAdapter mWalletBalanceAdapter;
    private int INTENT_EDIT_PROFILE = 578;
    private int INTENT_ADD_MONEY = 5454;
    private LoginResponse mLoginDataResponse;
    private int INTENT_PASSWORD_EXPIRE = 8923;
    private AppPreferences mAppPreferences;
    private Gson gson;
    private String deviceId, deviceSerialNum;
    private ImagePicker imagePicker;
    private int INTENT_PERMISSION_IMAGE = 4621;
    private boolean isKYCClick;
    private EKYCStepsDialog mEKYCStepsDialog;
    private boolean isEKYCCompleted;
    private RecyclerView balanceRecyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_user_profile);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
            gson = new Gson();
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            singleDataResponse = ApiFintechUtilMethods.INSTANCE.getSingleData(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            findViews();

            new Handler(Looper.getMainLooper()).post(() -> {

                mGetUserResponse = ApiFintechUtilMethods.INSTANCE.getUserDetailResponse(mAppPreferences);
                balanceCheckResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);

                isEKYCCompleted = mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isEKYCComplete);
                mEKYCStepsDialog = new EKYCStepsDialog(this, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);

                initialValues();
                showBalanceData();
                mCustomPassDialog = new CustomAlertDialog(UserProfileActivity.this);
                setUserData();
                getUserDetail();
            });
        });


    }


    void findViews() {

        userIconView = findViewById(R.id.userIconView);
        userImage = findViewById(R.id.userImage);
        name = findViewById(R.id.name);
        doubleFactorLabel = findViewById(R.id.doubleFactorLabel);
        toggleDoubleFactor = findViewById(R.id.toggleDoubleFactor);
        realApiLabel = findViewById(R.id.realApiLabel);
        toggleRealApi = findViewById(R.id.toggleRealApi);
        voiceLabel = findViewById(R.id.voiceLabel);
        toggleVoice = findViewById(R.id.toggleVoice);
        doubleFactorLayoutContainer = findViewById(R.id.doubleFactorLayoutContainer);
        realApiLayoutContainer = findViewById(R.id.realApiLayoutContainer);
        voiceLayoutContainer = findViewById(R.id.voiceLayoutContainer);
        editProfile = findViewById(R.id.editProfile);
        editBankView = findViewById(R.id.editBankView);
        settlementAccountView = findViewById(R.id.settlementAccountView);
        outletName = findViewById(R.id.outletName);
        outletNameView = findViewById(R.id.outletNameView);
        addressView = findViewById(R.id.addressView);
        address = findViewById(R.id.address);
        kycDetailCard = findViewById(R.id.kycDetailCard);
        kycStatus = findViewById(R.id.kycStatus);
        kycStatusMsg = findViewById(R.id.kycStatusMsg);
        pincodeView = findViewById(R.id.pincodeView);
        pincode = findViewById(R.id.pincode);
        stateView = findViewById(R.id.stateView);
        state = findViewById(R.id.state);
        cityView = findViewById(R.id.cityView);
        city = findViewById(R.id.city);
        gstinView = findViewById(R.id.gstinView);
        gstin = findViewById(R.id.gstin);
        aadharView = findViewById(R.id.aadharView);
        aadhar = findViewById(R.id.aadhar);
        panView = findViewById(R.id.panView);
        pan = findViewById(R.id.pan);
        rankView = findViewById(R.id.rankView);
        rankDetail = findViewById(R.id.rankDetail);
        rank = findViewById(R.id.rank);
        lastPkgTV = findViewById(R.id.lastPkgTV);
        teamDetail = findViewById(R.id.teamDetail);
        todayActive = findViewById(R.id.todayActive);
        todayDeactive = findViewById(R.id.todayDeactive);
        incomeDetail = findViewById(R.id.incomeDetail);
        holdComm = findViewById(R.id.holdComm);
        todayIncome = findViewById(R.id.todayincome);
        totalIncome = findViewById(R.id.totalIncome);
        holdIncome = findViewById(R.id.holdincome);
        rankIV = findViewById(R.id.rankIV);
        rankTV = findViewById(R.id.rankTV);
        mobile = findViewById(R.id.mobile);
        addMoneyView = findViewById(R.id.addMoneyView);
        sendMoneyView = findViewById(R.id.sendMoneyView);
        sendMoneyTv = findViewById(R.id.sendMoneyTv);
        myOrderView = findViewById(R.id.myOrder);
        myWishListView = findViewById(R.id.myWishList);
        myAddressView = findViewById(R.id.myAddress);
        myCartView = findViewById(R.id.myCart);
        changePassword = findViewById(R.id.changePassword);
        changePinPassword = findViewById(R.id.changePinPassword);
        email = findViewById(R.id.email);
        role = findViewById(R.id.role);
        roleTv = findViewById(R.id.roleTv);
        verifyUserIv = findViewById(R.id.verifyUserIv);

        if (mLoginDataResponse.isECommerceAllowed()) {
            myOrderView.setVisibility(View.VISIBLE);
            myWishListView.setVisibility(View.VISIBLE);
            myCartView.setVisibility(View.VISIBLE);
            myAddressView.setVisibility(View.VISIBLE);
        } else {
            myOrderView.setVisibility(View.GONE);
            myWishListView.setVisibility(View.GONE);
            myCartView.setVisibility(View.GONE);
            myAddressView.setVisibility(View.GONE);
        }
         balanceRecyclerView = findViewById(R.id.balanceRecyclerView);
        balanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        addMoneyView.setOnClickListener(this);
        sendMoneyView.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        changePinPassword.setOnClickListener(this);

        doubleFactorLayoutContainer.setOnClickListener(this);
        realApiLayoutContainer.setOnClickListener(this);
        voiceLayoutContainer.setOnClickListener(this);
        editProfile.setOnClickListener(this);
        editBankView.setOnClickListener(this);
        settlementAccountView.setOnClickListener(this);
        userIconView.setOnClickListener(this);
        kycStatus.setOnClickListener(this);
        myOrderView.setOnClickListener(this);
        myWishListView.setOnClickListener(this);
        myCartView.setOnClickListener(this);
        myAddressView.setOnClickListener(this);
    }


    private void showBalanceData() {
        if (balanceCheckResponse != null && balanceCheckResponse.isEKYCForced() && !isEKYCCompleted) {
            mEKYCStepsDialog.GetKycDetails(new EKYCStepsDialog.ApiCallBackTwoMethod() {
                @Override
                public void onSucess(GetEKYCDetailResponse object) {

                    isEKYCCompleted = object.getData().isIsEKYCDone();
                }

                @Override
                public void onError(Object object) {

                }
            });

        }
        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                && balanceCheckResponse.getBalanceData().size() > 0) {
            if (balanceCheckResponse.isUPI() || balanceCheckResponse.isPaymentGatway()) {
                addMoneyView.setVisibility(View.VISIBLE);
            } else {
                addMoneyView.setVisibility(View.GONE);
            }
            mBalanceTypes = balanceCheckResponse.getBalanceData();

            /*if (mLoginDataResponse.isAccountStatement()) {
                mBalanceTypes.add(new BalanceType("Outstanding Wallet", mBalanceData.getOsBalance()));
            }*/

            mWalletBalanceAdapter = new WalletBalanceAdapter(this, mBalanceTypes, R.layout.adapter_wallet_balance);
            balanceRecyclerView.setAdapter(mWalletBalanceAdapter);

            if (mBalanceTypes.size() > 1) {
                sendMoneyView.setVisibility(View.GONE);//
            } else {
                sendMoneyView.setVisibility(View.GONE);
            }
            mWalletBalanceAdapter.notifyDataSetChanged();

            if (balanceCheckResponse.isECommerceAllowed()) {
                myOrderView.setVisibility(View.VISIBLE);
                myWishListView.setVisibility(View.VISIBLE);
                myCartView.setVisibility(View.VISIBLE);
                myAddressView.setVisibility(View.VISIBLE);
            } else {
                myOrderView.setVisibility(View.GONE);
                myWishListView.setVisibility(View.GONE);
                myCartView.setVisibility(View.GONE);
                myAddressView.setVisibility(View.GONE);
            }
        } else {

            ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse, deviceId,
                    deviceSerialNum, mAppPreferences, mEKYCStepsDialog, object -> {
                        balanceCheckResponse = (BalanceResponse) object;
                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                && balanceCheckResponse.getBalanceData().size() > 0) {
                            showBalanceData();
                        }
                    });


        }

    }

    private void initialValues() {
        if (mLoginDataResponse.getData().getRoleID() == 3 || mLoginDataResponse.getData().getRoleID() == 2) {
            sendMoneyTv.setText("AEPS Settlement");
        } else {
            sendMoneyTv.setText("Fund Transfer");
        }
        if (mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.voiceEnablePref)) {
            voiceLabel.setText("Enable Voice Instructions");
            toggleVoice.setChecked(false);
        } else {
            voiceLabel.setText("Disable Voice Instructions");
            toggleVoice.setChecked(true);
        }
        if (mLoginDataResponse.getData().getRankName() != null && mLoginDataResponse.getData().getRankName().length() > 0) {
            rankDetail.setVisibility(View.VISIBLE);
            rank.setText("Rank\n"+mLoginDataResponse.getData().getRankName() + "");
        }




      /*  if (getIntent().getBooleanExtra("IsAddMoneyVisible", false)) {
            addMoneyView.setVisibility(View.VISIBLE);
        } else {
            addMoneyView.setVisibility(View.GONE);
        }*/
        if (mLoginDataResponse.getData().getMobileNo() != null && mLoginDataResponse.getData().getMobileNo().length() > 0) {
            mobile.setText(mLoginDataResponse.getData().getMobileNo() + "");
        }
        if (mLoginDataResponse.getData().getEmailID() != null && mLoginDataResponse.getData().getEmailID().length() > 0) {
            email.setText(mLoginDataResponse.getData().getEmailID() + "");
        }
        if (mLoginDataResponse.getData().getUserID() != null && mLoginDataResponse.getData().getUserID().length() > 0) {
            roleTv.setText(mLoginDataResponse.getData().getPrefix() + "" + mLoginDataResponse.getData().getUserID() + "" );
        }

        if (mLoginDataResponse.getData().getName() != null && mLoginDataResponse.getData().getName().length() > 0) {
            name.setText(mLoginDataResponse.getData().getName() + "");
        }

        if (mLoginDataResponse.getData().getRoleName() != null && mLoginDataResponse.getData().getRoleName().length() > 0) {
            name.setText(name.getText() + " (" + mLoginDataResponse.getData().getRoleName() + ")");
            role.setText(mLoginDataResponse.getData().getRoleName() + "");
        }

        if (balanceCheckResponse.getIsTopup() == 1) {

            verifyUserIv.setText("Active");
            verifyUserIv.setTextColor(Color.GREEN);
        } else {
            verifyUserIv.setText("Inactive");
            verifyUserIv.setTextColor(Color.RED);
        }

        RequestOptions mRequestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_UserIcon();
        Glide.with(this)
                .load(ApplicationConstant.INSTANCE.baseProfilePicUrl + mLoginDataResponse.getData().getUserID() + ".png")
                .apply(mRequestOptions)
                .into(userImage);

        imagePicker = new ImagePicker(this, null, imageUri -> {
            Uri imgUri = imageUri;
            File selectedImageFile = new File(imgUri.getPath());
            // image.setImageURI(imgUri);


            Glide.with(this)
                    .load(selectedImageFile)
                    .apply(mRequestOptions)
                    .into(userImage);

            uploadPicApi(selectedImageFile);
        }).setWithImageCrop();
    }

    @Override
    public void onClick(View v) {
        if (v == myOrderView) {
            startActivity(new Intent(this, OrderListShoppingActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        } else if (v == myWishListView) {
            startActivity(new Intent(this, WishlistShoppingActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        } else if (v == myAddressView) {
            startActivity(new Intent(this, AddressListShoppingActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        } else if (v == myCartView) {
            startActivity(new Intent(this, CartDetailShoppingActivity.class).
                    setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        } else if (v == kycStatus) {
            if (isKYCClick) {
                startActivityForResult(new Intent(this, UpdateProfileActivity.class)
                        .putExtra("UserData", mGetUserResponse)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_EDIT_PROFILE);
            }
        } else if (v == userIconView) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(new Intent(this, PermissionActivity.class),
                            INTENT_PERMISSION_IMAGE);
                } else {
                    imagePicker.choosePictureWithoutPermission(true, false);
                }
            } else {
                imagePicker.choosePictureWithoutPermission(true, false);
            }
        } else if (v == editProfile) {
            startActivityForResult(new Intent(this, UpdateProfileActivity.class)
                    .putExtra("UserData", mGetUserResponse)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_EDIT_PROFILE);
        } else if (v == editBankView) {
            startActivityForResult(new Intent(this, UpdateBankActivity.class)
                    .putExtra("UserData", mGetUserResponse)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_EDIT_PROFILE);
        } else if (v == settlementAccountView) {
            startActivity(new Intent(this, SettlementBankListActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        } else if (v == sendMoneyView) {
            if (mLoginDataResponse.getData().getRoleID() == 3 || mLoginDataResponse.getData().getRoleID() == 2) {
                startActivity(new Intent(this, MoveToWalletActivity.class)
                        .putParcelableArrayListExtra("items", mBalanceTypes)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            } else if (mLoginDataResponse.getData().getRoleID() == 8) {
                Intent i = new Intent(this, FosUserListActivity.class);
                i.putExtra("Title", "Send Money To User");
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                Intent i = new Intent(this, AppUserListActivity.class);
                i.putExtra("Title", "Send Money To User");
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        } else if (v == addMoneyView) {
            Intent i = new Intent(UserProfileActivity.this, AddMoneyActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_ADD_MONEY);
        } else if (v == changePinPassword) {
            startActivity(new Intent(this, ChangePinPassActivity.class)
                    .putExtra("IsPin", true)
                    .putExtra("IsForcibly", false)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        } else if (v == changePassword) {
            startActivity(new Intent(this, ChangePinPassActivity.class)
                    .putExtra("IsPin", false)
                    .putExtra("IsForcibly", false)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        } else if (v == doubleFactorLayoutContainer) {

            otpApi(!isDoubleFactorEnable, "", "", null, null, null);
        } else if (v == realApiLayoutContainer) {
            EnableDisableRealApi();
        } else if (v == voiceLayoutContainer) {
            if (toggleVoice.isChecked()) {
                voiceLabel.setText("Enable Voice Instructions");
                toggleVoice.setChecked(false);
                mAppPreferences.set(ApplicationConstant.INSTANCE.voiceEnablePref, true);
            } else {
                voiceLabel.setText("Disable Voice Instructions");
                toggleVoice.setChecked(true);
                mAppPreferences.set(ApplicationConstant.INSTANCE.voiceEnablePref, false);
            }

            setResult(RESULT_OK);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("photopath", ImagePicker.currentCameraFileName);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("photopath") && ImagePicker.currentCameraFileName.length() < 5) {
                ImagePicker.currentCameraFileName = savedInstanceState.getString("photopath");
            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    void EnableDisableRealApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            ApiFintechUtilMethods.INSTANCE.EnableDisableRealApi(this, toggleRealApi.isChecked() ? false : true, loader, mLoginDataResponse,
                    deviceId, deviceSerialNum, object -> {
                        mAppPreferences.set(ApplicationConstant.INSTANCE.IsRealApiPref, toggleRealApi.isChecked() ? false : true);

                        toggleRealApi.setChecked(toggleRealApi.isChecked() ? false : true);
                        if (toggleRealApi.isChecked()) {
                            realApiLabel.setText("Disable Real Api");
                        } else {
                            realApiLabel.setText("Enable Real Api");
                        }

                    });

        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }


    private void uploadPicApi(File selectedImageFile) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            ApiFintechUtilMethods.INSTANCE.UploadProfilePic(this, selectedImageFile, loader, mLoginDataResponse,
                    deviceId, deviceSerialNum, object -> {

                        setResult(RESULT_OK);
                    });

        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }


    void otpApi(boolean isDoubleFactor, String otp, final String refId, TextView timerTv, TextView resendCodeTv, Dialog mDialog) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            ApiFintechUtilMethods.INSTANCE.DoubleFactorOtp(this, isDoubleFactor, otp, refId, loader, mLoginDataResponse,
                    deviceId, deviceSerialNum, object -> {
                        mDfStatusResponse = (DFStatusResponse) object;

                        if (mDfStatusResponse.isOTPRequired()) {
                            if (mDialog != null && mDialog.isShowing()) {
                                if (timerTv != null && resendCodeTv != null) {
                                    ApiFintechUtilMethods.INSTANCE.setTimer(timerTv, resendCodeTv);
                                }
                                ApiFintechUtilMethods.INSTANCE.Successful(UserProfileActivity.this, mDfStatusResponse.getMsg() + "");

                            } else {

                                ApiFintechUtilMethods.INSTANCE.openOtpDialog(UserProfileActivity.this, 6,
                                        mLoginDataResponse.getData().getMobileNo() + "", new ApiFintechUtilMethods.DialogOTPCallBack() {
                                            @Override
                                            public void onPositiveClick(String otpValue, TextView timerTv1, TextView resendCodeTv1, Dialog mDialog1) {
                                                if (mDfStatusResponse != null) {
                                                    otpApi(!isDoubleFactorEnable, otpValue, mDfStatusResponse.getRefID(), timerTv1, resendCodeTv1, mDialog1);
                                                } else {
                                                    otpApi(!isDoubleFactorEnable, otpValue, "", timerTv1, resendCodeTv1, mDialog1);
                                                }
                                            }

                                            @Override
                                            public void onResetCallback(String value, TextView timerTv1, TextView resendCodeTv1, Dialog mDialog1) {
                                                if (mDfStatusResponse != null) {
                                                    otpApi(!isDoubleFactorEnable, "OTP", mDfStatusResponse.getRefID(), timerTv1, resendCodeTv1, mDialog1);
                                                } else {
                                                    otpApi(!isDoubleFactorEnable, "OTP", "", timerTv1, resendCodeTv1, mDialog1);
                                                }
                                            }
                                        });

                            }
                        } else if (mDfStatusResponse.getRefID() == null && !mDfStatusResponse.isOTPRequired() || mDfStatusResponse.getRefID().isEmpty() && !mDfStatusResponse.isOTPRequired()) {
                            ApiFintechUtilMethods.INSTANCE.Successful(UserProfileActivity.this, mDfStatusResponse.getMsg() + "");
                            if (mDialog != null && mDialog.isShowing()) {
                                mDialog.dismiss();
                            }
                            isDoubleFactorEnable = !isDoubleFactorEnable;

                            mLoginDataResponse.getData().setDoubleFactor(isDoubleFactorEnable);
                            ApiFintechUtilMethods.INSTANCE.mTempLoginDataResponse = mLoginDataResponse;
                            mAppPreferences.set(ApplicationConstant.INSTANCE.LoginPref, gson.toJson(mLoginDataResponse));

                            if (isDoubleFactorEnable) {
                                doubleFactorLabel.setText("Disable Double Factor");
                                toggleDoubleFactor.setChecked(true);
                            } else {
                                doubleFactorLabel.setText("Enable Double Factor");
                                toggleDoubleFactor.setChecked(false);
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.Successful(UserProfileActivity.this, mDfStatusResponse.getMsg() + "");
                        }
                    });

        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }


    public void getUserDetail() {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetUserResponse> call = git.GetProfile(new BasicRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<GetUserResponse>() {

                @Override
                public void onResponse(Call<GetUserResponse> call, retrofit2.Response<GetUserResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            mGetUserResponse = response.body();
                            if (mGetUserResponse != null) {
                                ApiFintechUtilMethods.INSTANCE.mGetUserResponse = mGetUserResponse;
                                if (mGetUserResponse.getPasswordExpired()) {


                                    mCustomPassDialog.WarningWithSingleBtnCallBack(getString(R.string.password_expired_msg), "Change", false, new CustomAlertDialog.DialogCallBack() {
                                        @Override
                                        public void onPositiveClick() {
                                            startActivityForResult(new Intent(UserProfileActivity.this, ChangePinPassActivity.class)
                                                    .putExtra("IsPin", false)
                                                    .putExtra("IsForcibly", true)
                                                    .putExtra("Intent_Result", INTENT_PASSWORD_EXPIRE)
                                                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_PASSWORD_EXPIRE);

                                        }

                                        @Override
                                        public void onNegativeClick() {

                                        }
                                    });

                                }

                                if (mGetUserResponse.getStatuscode() == 1) {
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.UserDetailPref, gson.toJson(mGetUserResponse));
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.IsRealApiPref, mGetUserResponse.getUserInfo().isRealAPI());

                                    mLoginDataResponse.getData().setDoubleFactor(mGetUserResponse.getUserInfo().isDoubleFactor());
                                    ApiFintechUtilMethods.INSTANCE.mTempLoginDataResponse = mLoginDataResponse;
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.LoginPref, gson.toJson(mLoginDataResponse));
                                    setUserData();


                                } else if (response.body().getStatuscode() == -1) {

                                    ApiFintechUtilMethods.INSTANCE.Error(UserProfileActivity.this, mGetUserResponse.getMsg() + "");
                                } else if (response.body().getStatuscode() == 0) {

                                    ApiFintechUtilMethods.INSTANCE.Error(UserProfileActivity.this, mGetUserResponse.getMsg() + "");
                                } else {

                                    ApiFintechUtilMethods.INSTANCE.Error(UserProfileActivity.this, mGetUserResponse.getMsg() + "");
                                }

                            } else {

                               // ApiFintechUtilMethods.INSTANCE.Error(UserProfileActivity.this, getString(R.string.some_thing_error));
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(UserProfileActivity.this, response.code(), response.message());
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
                public void onFailure(Call<GetUserResponse> call, Throwable t) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        ApiFintechUtilMethods.INSTANCE.apiFailureError(UserProfileActivity.this, t);


                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        ApiFintechUtilMethods.INSTANCE.Error(UserProfileActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(this, getString(R.string.some_thing_error));
        }

    }


    void setUserData() {

        if (mGetUserResponse != null && mGetUserResponse.getUserInfo() != null) {
            if (mGetUserResponse.getUserInfo().getName() != null && !mGetUserResponse.getUserInfo().getName().isEmpty()) {
                name.setText(mGetUserResponse.getUserInfo().getName());
            }
            if (mGetUserResponse.getUserInfo().getRole() != null && !mGetUserResponse.getUserInfo().getRole().isEmpty()) {
                name.setText(name.getText() + " (" + mGetUserResponse.getUserInfo().getRole() + ")");
                role.setText(mGetUserResponse.getUserInfo().getRole());
            }
            if (mGetUserResponse.getUserInfo().getEmailID() != null && !mGetUserResponse.getUserInfo().getEmailID().isEmpty()) {
                email.setText(mGetUserResponse.getUserInfo().getEmailID());
            }
            if (mGetUserResponse.getUserInfo().getOutletName() != null && !mGetUserResponse.getUserInfo().getOutletName().isEmpty()) {
                outletName.setText(mGetUserResponse.getUserInfo().getOutletName());
            } else {
                outletNameView.setVisibility(View.GONE);
            }

            if (singleDataResponse!= null || mGetUserResponse.getUserInfo().getRankInfo() != null && mGetUserResponse.getUserInfo().getRankInfo().getRankName() != null && mGetUserResponse.getUserInfo().getRankInfo().getRankName().length() > 0) {
                rankDetail.setVisibility(View.VISIBLE);
                lastPkgTV.setText(singleDataResponse.getLastPackageName()+ "");
                rank.setText(mGetUserResponse.getUserInfo().getRankInfo().getRankName() + "");
            }else {
                rankDetail.setVisibility(View.GONE);
            }
            if(singleDataResponse!= null ){
                incomeDetail.setVisibility(View.VISIBLE);
                todayIncome.setText(Utility.INSTANCE.formatedAmountWithRupees(singleDataResponse.getTodayIncome()+ ""));
                totalIncome.setText(Utility.INSTANCE.formatedAmountWithRupees(singleDataResponse.getTotalIncome()+ ""));
            }else {
                incomeDetail.setVisibility(View.GONE);
            }
            if(singleDataResponse!= null){
                teamDetail.setVisibility(View.VISIBLE);
                todayActive.setText(singleDataResponse.getTodayActive()+ "");
                todayDeactive.setText(singleDataResponse.getTodayDeactive()+ "");
            }else {
                teamDetail.setVisibility(View.GONE);
            }
            if(singleDataResponse!= null ){
                holdComm.setVisibility(View.VISIBLE);
                holdIncome.setText(Utility.INSTANCE.formatedAmountWithRupees(singleDataResponse.getTotalHoldCommAmt()+ ""));
            }else {
                holdComm.setVisibility(View.GONE);
            }


            if (mGetUserResponse.getUserInfo().getAddress() != null && !mGetUserResponse.getUserInfo().getAddress().isEmpty()) {
                address.setText(mGetUserResponse.getUserInfo().getAddress());
            } else {
                addressView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getPincode() != null && !mGetUserResponse.getUserInfo().getPincode().isEmpty()) {
                pincode.setText(mGetUserResponse.getUserInfo().getPincode());
            } else {
                pincodeView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getStateName() != null && !mGetUserResponse.getUserInfo().getStateName().isEmpty()) {
                state.setText(mGetUserResponse.getUserInfo().getStateName());
            } else {
                stateView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getCity() != null && !mGetUserResponse.getUserInfo().getCity().isEmpty()) {
                city.setText(mGetUserResponse.getUserInfo().getCity());
            } else {
                cityView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getGstin() != null && !mGetUserResponse.getUserInfo().getGstin().isEmpty()) {
                kycDetailCard.setVisibility(View.VISIBLE);
                gstin.setText(mGetUserResponse.getUserInfo().getGstin());
            } else {
                gstinView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getAadhar() != null && !mGetUserResponse.getUserInfo().getAadhar().isEmpty()) {
                kycDetailCard.setVisibility(View.VISIBLE);
                aadhar.setText(mGetUserResponse.getUserInfo().getAadhar());
            } else {
                aadharView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getPan() != null && !mGetUserResponse.getUserInfo().getPan().isEmpty()) {
                kycDetailCard.setVisibility(View.VISIBLE);
                pan.setText(mGetUserResponse.getUserInfo().getPan());
            } else {
                panView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getKycStatus() == 2 || mGetUserResponse.getUserInfo().getKycStatus() == 3) {
                editProfile.setVisibility(View.GONE);
                // editBankView.setVisibility(View.VISIBLE);
                settlementAccountView.setVisibility(View.VISIBLE);
            } else {
                editProfile.setVisibility(View.VISIBLE);
                // editBankView.setVisibility(View.GONE);
                settlementAccountView.setVisibility(View.GONE);
            }

            if (mGetUserResponse.getUserInfo().getKycStatus() == 1) {
                kycStatus.setText("Apply for KYC");
                kycStatusMsg.setVisibility(View.VISIBLE);
                kycStatusMsg.setText("Click here to apply");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    kycStatus.setElevation(4);
                }
                isKYCClick = true;
                ViewCompat.setBackgroundTintList(kycStatus, ColorStateList.valueOf
                        (getResources().getColor(R.color.bottommenu)));
            } else if (mGetUserResponse.getUserInfo().getKycStatus() == 2) {
                kycStatus.setText("KYC Applied");
                kycStatusMsg.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    kycStatus.setElevation(0);
                }
                isKYCClick = false;
                ViewCompat.setBackgroundTintList(kycStatus, ColorStateList.valueOf
                        (getResources().getColor(R.color.style_color_accent)));
            } else if (mGetUserResponse.getUserInfo().getKycStatus() == 3) {
                kycStatus.setText("KYC Completed");
                kycStatusMsg.setVisibility(View.GONE);
                isKYCClick = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    kycStatus.setElevation(0);
                }
                ViewCompat.setBackgroundTintList(kycStatus, ColorStateList.valueOf
                        (getResources().getColor(R.color.green)));
            } else if (mGetUserResponse.getUserInfo().getKycStatus() == 4) {
                kycStatus.setText("Apply for Re-KYC");
                kycStatusMsg.setVisibility(View.VISIBLE);
                kycStatusMsg.setText("Click here to Re-KYC");
                isKYCClick = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    kycStatus.setElevation(4);
                }
                ViewCompat.setBackgroundTintList(kycStatus, ColorStateList.valueOf
                        (getResources().getColor(R.color.bottommenu)));
            } else if (mGetUserResponse.getUserInfo().getKycStatus() == 5) {
                kycStatus.setText("KYC rejected Re-Apply");
                kycStatusMsg.setVisibility(View.VISIBLE);
                kycStatusMsg.setText("Click here to Re-Apply");
                isKYCClick = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    kycStatus.setElevation(4);
                }
                ViewCompat.setBackgroundTintList(kycStatus, ColorStateList.valueOf
                        (getResources().getColor(R.color.red)));
            }


            toggleDoubleFactor.setChecked(mGetUserResponse.getUserInfo().isDoubleFactor());
            if (mGetUserResponse.getUserInfo().isDoubleFactor()) {
                isDoubleFactorEnable = true;
                doubleFactorLabel.setText("Disable Double Factor");
            } else {
                isDoubleFactorEnable = false;
                doubleFactorLabel.setText("Enable Double Factor");
            }

            toggleRealApi.setChecked(mGetUserResponse.getUserInfo().isRealAPI());
            if (mGetUserResponse.getUserInfo().isRealAPI()) {
                realApiLabel.setText("Disable Real Api");
            } else {
                realApiLabel.setText("Enable Real Api");
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_EDIT_PROFILE && resultCode == Activity.RESULT_OK) {
            loader.show();
            getUserDetail();
        } else if (requestCode == INTENT_PASSWORD_EXPIRE && resultCode == INTENT_PASSWORD_EXPIRE) {
            loader.show();
            getUserDetail();
        } else if (requestCode == INTENT_ADD_MONEY && resultCode == INTENT_PASSWORD_EXPIRE) {
            loader.show();
            ApiFintechUtilMethods.INSTANCE.Balancecheck(this, loader, mLoginDataResponse, deviceId,
                    deviceSerialNum, mAppPreferences, mEKYCStepsDialog, object -> {
                        balanceCheckResponse = (BalanceResponse) object;
                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                && balanceCheckResponse.getBalanceData().size() > 0) {
                            showBalanceData();
                        }
                    });
        } else if (requestCode == INTENT_PERMISSION_IMAGE && resultCode == RESULT_OK) {
            imagePicker.choosePictureWithoutPermission(true, true);
        } else {
            imagePicker.handleActivityResult(resultCode, requestCode, data);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == android.R.id.home) {
            finish();
        } else*/
        if (id == R.id.action_logout) {
            mCustomPassDialog.Successfullogout(loader, "Do you really want to Logout?", this,
                    mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
