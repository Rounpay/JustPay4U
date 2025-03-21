package com.solution.app.justpay4u.Fintech.Dashboard.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.solution.app.justpay4u.Api.Fintech.Object.UserQRInfo;
import com.solution.app.justpay4u.Api.Fintech.Object.UserSmartDetail;
import com.solution.app.justpay4u.Api.Fintech.Request.MapQRToUserRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.Activities.PermissionActivity;
import com.solution.app.justpay4u.Fintech.Dashboard.Adapter.VirtualSmartAddressPagerAdapter;
import com.solution.app.justpay4u.Fintech.UpiPayment.Activity.QRScanActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class VirtualAccountActivity extends AppCompatActivity {
    public LoginResponse mLoginDataResponse;
    public String deviceId, deviceSerialNum;
    View tabView;
    TextView custCare, upiId;
    ImageView qrcode, cameraView;
    TextView detail, labelTV;
    String detailsICICIStr = "", detailsRazorPayStr = "";
    ViewPager mViewPager;
    View pagerContainer;
    LinearLayout dotsCount, razorPayView, cashfreeView, iciciView;
    int mDotsCount;
    UserQRInfo mUserQRInfo;
    RequestOptions requestOptions;
    int tabPos = 1;
    private CustomLoader loader;
    private View shareView, btnView, titleView;
    private boolean isDownload;
    private int INTENT_PERMISSION_IMAGE = 4563;
    private int INTENT_SCAN = 8476;
    private boolean isECollectEnable;
    private TextView[] mDotsText;
    private Bitmap iciciQRDrawable;
    private Bitmap razorpayQRDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_vertual_account);


            AppPreferences mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            findViewId();
            new Handler(Looper.getMainLooper()).post(() -> {
                isECollectEnable = getIntent().getBooleanExtra("isECollectEnable", false);
                boolean isQRMappedToUser = getIntent().getBooleanExtra("isQRMappedToUser", false);
                boolean isBulkQRGeneration = getIntent().getBooleanExtra("isBulkQRGeneration", false);
                if (isBulkQRGeneration && !isQRMappedToUser) {
                    cameraView.setVisibility(View.VISIBLE);
                } else {
                    cameraView.setVisibility(View.GONE);
                }
                requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.placeholder_square);
                requestOptions.error(R.drawable.placeholder_square);
                requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
                requestOptions.skipMemoryCache(true);

                AppUserListResponse companyProfileData = ApiFintechUtilMethods.INSTANCE.getCompanyProfile(mAppPreferences);
                if (companyProfileData != null && companyProfileData.getCompanyProfile() != null) {
                    String value = "";
                    if (companyProfileData.getCompanyProfile().getCustomerCareMobileNos() != null && !companyProfileData.getCompanyProfile().getCustomerCareMobileNos().isEmpty()) {
                        value = value + getHtml("Mobile No", companyProfileData.getCompanyProfile().getCustomerCareMobileNos());
                    }
                    if (companyProfileData.getCompanyProfile().getCustomerPhoneNos() != null && !companyProfileData.getCompanyProfile().getCustomerPhoneNos().isEmpty()) {
                        if (value.length() > 0) {
                            value = value + "<br/>";
                        }
                        value = value + getHtml("Landline No", companyProfileData.getCompanyProfile().getCustomerPhoneNos());
                    }
                    if (companyProfileData.getCompanyProfile().getCustomerWhatsAppNosLink() != null && !companyProfileData.getCompanyProfile().getCustomerWhatsAppNosLink().isEmpty()) {
                        if (value.length() > 0) {
                            value = value + "<br/>";
                        }
                        value = value + "Whatsapp - " + companyProfileData.getCompanyProfile().getCustomerWhatsAppNosLink();
                    }

                    Utility.INSTANCE.setTextViewHTML(this, custCare, "Customer Care<br/>" + value);

                } else {
                    custCare.setVisibility(View.GONE);
                }

                setQRCode();
            });


        });

    }

    void findViewId() {
        detail = findViewById(R.id.detail);
        labelTV = findViewById(R.id.labelTV);
        custCare = findViewById(R.id.custCare);
        upiId = findViewById(R.id.upiId);
        qrcode = findViewById(R.id.qrcode);
        cameraView = findViewById(R.id.cameraView);
        shareView = findViewById(R.id.shareView);
        btnView = findViewById(R.id.btnView);
        titleView = findViewById(R.id.titleView);
        pagerContainer = findViewById(R.id.pagerContainer);
        mViewPager = findViewById(R.id.pager);
        dotsCount = findViewById(R.id.image_count);
        tabView = findViewById(R.id.tabView);
        razorPayView = findViewById(R.id.razorPayView);
        cashfreeView = findViewById(R.id.cashfreeView);
        iciciView = findViewById(R.id.iciciView);
        iciciView.setVisibility(View.GONE);
        razorPayView.setVisibility(View.GONE);
        cashfreeView.setVisibility(View.GONE);

        TextView OutletName = findViewById(R.id.OutletName);
        OutletName.setText(mLoginDataResponse.getData().getName() + "");


        findViewById(R.id.closeIv).setOnClickListener(v -> finish());
        findViewById(R.id.download).setOnClickListener(v -> {
            isDownload = true;
            shareit();
        });
        cameraView.setOnClickListener(v ->
                startActivityForResult(new Intent(VirtualAccountActivity.this, QRScanActivity.class), INTENT_SCAN));
        findViewById(R.id.share).setOnClickListener(v -> {
            isDownload = false;
            shareit();
        });


        iciciView.setOnClickListener(view -> {

            if (tabPos != 1) {
                if (mUserQRInfo.getIciciCollectData() != null && mUserQRInfo.getIciciCollectData().isQRShow() &&
                        mUserQRInfo.getIciciCollectData().getData() != null) {
                    setICICIData(mUserQRInfo.getIciciCollectData());
                }

            }
        });
        razorPayView.setOnClickListener(view -> {
            if (tabPos != 2) {
                if (mUserQRInfo.getRazorpayCollectData() != null && mUserQRInfo.getRazorpayCollectData().isQRShow() &&
                        mUserQRInfo.getRazorpayCollectData().getData() != null) {
                    setRazorPayData(mUserQRInfo.getRazorpayCollectData());
                }

            }
        });
        cashfreeView.setOnClickListener(view -> {
            if (tabPos != 3) {

                if (mUserQRInfo.getCashfreeCollectData() != null && mUserQRInfo.getCashfreeCollectData().isQRShow() &&
                        mUserQRInfo.getCashfreeCollectData().getData() != null
                        && mUserQRInfo.getCashfreeCollectData().getData().size() > 0) {
                    setCashFreeData(mUserQRInfo.getCashfreeCollectData());
                }
            }
        });
    }

    private void setQRCode() {
        if (isECollectEnable) {
            /* ApiUtilMethods.INSTANCE.GetVADetails(this, loader, mLoginDataResponse, deviceId, deviceSerialNum, object -> {
                mUserQRInfo = (UserQRInfo) object;
                setApiData();
            });*/
            if (!loader.isShowing()) {
                loader.show();
            }
            ApiFintechUtilMethods.INSTANCE.GetSmartCollectDetail(this, loader, mLoginDataResponse, deviceId, deviceSerialNum, object -> {
                mUserQRInfo = (UserQRInfo) object;
                setApiDataNew();
            });
        } else {
            loader.dismiss();
            pagerContainer.setVisibility(View.GONE);
            titleView.setVisibility(View.VISIBLE);
            detail.setVisibility(View.GONE);
            tabView.setVisibility(View.GONE);

            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseQrImageUrl + mLoginDataResponse.getData().getUserID() +
                            "&appid=" + ApplicationConstant.INSTANCE.APP_ID + "&imei=" + deviceId + "&regKey=&version=" + BuildConfig.VERSION_NAME +
                            "&serialNo=" + deviceSerialNum + "&sessionID=" + mLoginDataResponse.getData().getSessionID() +
                            "&session=" + mLoginDataResponse.getData().getSession() + "&loginTypeID=" + mLoginDataResponse.getData().getLoginTypeID())
                    .apply(requestOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            btnView.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(qrcode);
        }
    }

    void setApiData() {
        if (mUserQRInfo != null) {
            titleView.setVisibility(View.GONE);

            int tabCount = 0;
            boolean isFirstDataSet = false;
            if (mUserQRInfo.getVirtualAccount() != null && !mUserQRInfo.getVirtualAccount().isEmpty()) {
                tabCount = tabCount + 1;
                iciciView.setVisibility(View.VISIBLE);
                if (!isFirstDataSet) {
                    setICICIData();
                    isFirstDataSet = true;
                }
            }
            if (mUserQRInfo.getUserSDetail() != null && mUserQRInfo.getUserSDetail().getSmartAccountNo() != null &&
                    !mUserQRInfo.getUserSDetail().getSmartAccountNo().isEmpty()) {
                tabCount = tabCount + 1;
                razorPayView.setVisibility(View.VISIBLE);
                if (!isFirstDataSet) {
                    setRazorPayData();
                    isFirstDataSet = true;
                }
            }
            if (mUserQRInfo.getCashfreeSmartDetail() != null && mUserQRInfo.getCashfreeSmartDetail().size() > 0) {
                tabCount = tabCount + 1;
                cashfreeView.setVisibility(View.VISIBLE);
                if (!isFirstDataSet) {
                    setCashFreeData();
                    isFirstDataSet = true;
                }
            }
            if (tabCount > 1) {
                tabView.setVisibility(View.VISIBLE);
            } else {
                tabView.setVisibility(View.GONE);
            }
        } else {
            pagerContainer.setVisibility(View.GONE);
            titleView.setVisibility(View.VISIBLE);
            detail.setVisibility(View.GONE);
            tabView.setVisibility(View.GONE);
        }
    }

    void setApiDataNew() {
        if (mUserQRInfo != null) {
            if (mUserQRInfo.getStatuscode() == 1) {
                titleView.setVisibility(View.GONE);

                int tabCount = 0;
                boolean isFirstDataSet = false;
                if (mUserQRInfo.getIciciCollectData() != null && mUserQRInfo.getIciciCollectData().isQRShow() &&
                        mUserQRInfo.getIciciCollectData().getData() != null) {
                    tabCount = tabCount + 1;
                    iciciView.setVisibility(View.VISIBLE);
                    if (!isFirstDataSet) {
                        setICICIData(mUserQRInfo.getIciciCollectData());
                        isFirstDataSet = true;
                    }
                }

                if (mUserQRInfo.getRazorpayCollectData() != null && mUserQRInfo.getRazorpayCollectData().isQRShow()
                        && mUserQRInfo.getRazorpayCollectData().getData() != null) {
                    tabCount = tabCount + 1;
                    razorPayView.setVisibility(View.VISIBLE);
                    if (!isFirstDataSet) {
                        setRazorPayData(mUserQRInfo.getRazorpayCollectData());
                        isFirstDataSet = true;
                    }
                }


                if (mUserQRInfo.getCashfreeCollectData() != null && mUserQRInfo.getCashfreeCollectData().isQRShow() &&
                        mUserQRInfo.getCashfreeCollectData().getData() != null
                        && mUserQRInfo.getCashfreeCollectData().getData().size() > 0) {
                    tabCount = tabCount + 1;
                    cashfreeView.setVisibility(View.VISIBLE);
                    if (!isFirstDataSet) {
                        setCashFreeData(mUserQRInfo.getCashfreeCollectData());
                        isFirstDataSet = true;
                    }
                }
                if (tabCount > 1) {
                    tabView.setVisibility(View.VISIBLE);
                } else {
                    tabView.setVisibility(View.GONE);
                }
            } else {
                pagerContainer.setVisibility(View.GONE);
                titleView.setVisibility(View.VISIBLE);
                detail.setVisibility(View.GONE);
                tabView.setVisibility(View.GONE);
            }
        } else {
            pagerContainer.setVisibility(View.GONE);
            titleView.setVisibility(View.VISIBLE);
            detail.setVisibility(View.GONE);
            tabView.setVisibility(View.GONE);
        }
    }

    void setICICIData(UserSmartDetail iciciData) {
        tabPos = 1;
        iciciView.setBackgroundResource(R.drawable.rounded_primary_border);
        razorPayView.setBackgroundResource(R.drawable.rounded_grey_border);
        cashfreeView.setBackgroundResource(R.drawable.rounded_grey_border);
        pagerContainer.setVisibility(View.GONE);
        UserSmartDetail iciciSmartData = (UserSmartDetail) iciciData.getData();
        if (detailsICICIStr == null || detailsICICIStr.isEmpty()) {
            if (iciciData.isVirtualShow()) {
                if (iciciSmartData.getBankName() != null && !iciciSmartData.getBankName().isEmpty()) {
                    detailsICICIStr = "Bank Name : " + iciciSmartData.getBankName();
                }
                if (iciciSmartData.getBeneName() != null && !iciciSmartData.getBeneName().isEmpty()) {
                    detailsICICIStr = detailsICICIStr + "\n" + "Benificiary Name : " + iciciSmartData.getBeneName();
                }
                if (iciciSmartData.getAccountHolder() != null && !iciciSmartData.getAccountHolder().isEmpty()) {
                    detailsICICIStr = detailsICICIStr + "\n" + "Account Holder : " + iciciSmartData.getAccountHolder();
                }
                if (iciciSmartData.getSmartAccountNo() != null && !iciciSmartData.getSmartAccountNo().isEmpty()) {
                    detailsICICIStr = detailsICICIStr + "\n" + "Account Number : " + iciciSmartData.getSmartAccountNo();
                }
                if (iciciSmartData.getBranch() != null && !iciciSmartData.getBranch().isEmpty()) {
                    detailsICICIStr = detailsICICIStr + "\n" + "Branch : " + iciciSmartData.getBranch();
                }
                if (iciciSmartData.getIfsc() != null && !iciciSmartData.getIfsc().isEmpty()) {
                    detailsICICIStr = detailsICICIStr + "\n" + "IFSC : " + iciciSmartData.getIfsc();
                }
                if (iciciSmartData.getCustomerID() != null && !iciciSmartData.getCustomerID().isEmpty()) {
                    detailsRazorPayStr = detailsRazorPayStr + "\n" + "Customer ID : " + iciciSmartData.getCustomerID();
                }
                if (iciciSmartData.getVirtualAccount() != null && !iciciSmartData.getVirtualAccount().isEmpty()) {
                    detailsICICIStr = detailsICICIStr + "\n" + "Virtual Account : " + iciciSmartData.getVirtualAccount();
                }
            }
            if (iciciData.isVPAShow() && iciciSmartData.getSmartVPA() != null && !iciciSmartData.getSmartVPA().isEmpty()) {
                detailsICICIStr = detailsICICIStr + "\n" + "VPA : " + iciciSmartData.getSmartVPA();
            }

        }


        if (!detailsICICIStr.isEmpty()) {
            detail.setVisibility(View.VISIBLE);
            titleView.setVisibility(View.GONE);
            detail.setText(detailsICICIStr);
        } else {
            detail.setVisibility(View.GONE);
            titleView.setVisibility(View.VISIBLE);
        }


        if (iciciQRDrawable != null) {
            btnView.setVisibility(View.VISIBLE);
            qrcode.setVisibility(View.VISIBLE);
            labelTV.setVisibility(View.VISIBLE);
            qrcode.setImageBitmap(iciciQRDrawable);
        } else {
            if (iciciData.isQRShow()) {
                if (iciciSmartData.getSmartQRShortURL() != null && !iciciSmartData.getSmartQRShortURL().isEmpty()) {
                    String id = iciciSmartData.getSmartQRShortURL();
                    if (id.contains("/")) {
                        id = id.substring(id.indexOf("/") + 1);
                    }
                    Glide.with(this)
                            .asBitmap()
                            .load(ApplicationConstant.INSTANCE.QRImageForCollectURL + mLoginDataResponse.getData().getUserID() +
                                    "&appid=" + ApplicationConstant.INSTANCE.APP_ID + "&imei=" + deviceId + "&regKey=&version=" + BuildConfig.VERSION_NAME +
                                    "&serialNo=" + deviceSerialNum + "&sessionID=" + mLoginDataResponse.getData().getSessionID() +
                                    "&session=" + mLoginDataResponse.getData().getSession() + "&loginTypeID=" + mLoginDataResponse.getData().getLoginTypeID()
                                    + "&id=" + id)
                            .apply(requestOptions)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onLoadStarted(Drawable placeholder) {
                                    super.onLoadStarted(placeholder);
                                    if (tabPos == 1) {
                                        qrcode.setVisibility(View.VISIBLE);
                                        btnView.setVisibility(View.GONE);
                                        labelTV.setVisibility(View.GONE);
                                        qrcode.setImageResource(R.drawable.placeholder_square);
                                    }
                                }

                                @Override
                                public void onLoadFailed(Drawable errorDrawable) {
                                    super.onLoadFailed(errorDrawable);
                                    qrcode.setVisibility(View.GONE);
                                    btnView.setVisibility(View.GONE);
                                    labelTV.setVisibility(View.GONE);
                                }

                                @Override
                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                    if (tabPos == 1) {
                                        btnView.setVisibility(View.VISIBLE);
                                        qrcode.setVisibility(View.VISIBLE);
                                        labelTV.setVisibility(View.VISIBLE);
                                        qrcode.setImageBitmap(resource);
                                    }
                                    iciciQRDrawable = resource;
                                }
                            });
                } else {

                    Glide.with(this)
                            .asBitmap()
                            .load(ApplicationConstant.INSTANCE.baseQrImageUrl + mLoginDataResponse.getData().getUserID() +
                                    "&appid=" + ApplicationConstant.INSTANCE.APP_ID + "&imei=" + deviceId + "&regKey=&version=" + BuildConfig.VERSION_NAME +
                                    "&serialNo=" + deviceSerialNum + "&sessionID=" + mLoginDataResponse.getData().getSessionID() +
                                    "&session=" + mLoginDataResponse.getData().getSession() + "&loginTypeID=" + mLoginDataResponse.getData().getLoginTypeID())
                            .apply(requestOptions)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onLoadStarted(Drawable placeholder) {
                                    super.onLoadStarted(placeholder);
                                    if (tabPos == 1) {
                                        qrcode.setVisibility(View.VISIBLE);
                                        btnView.setVisibility(View.GONE);
                                        labelTV.setVisibility(View.GONE);
                                        qrcode.setImageResource(R.drawable.placeholder_square);
                                    }
                                }

                                @Override
                                public void onLoadFailed(Drawable errorDrawable) {
                                    super.onLoadFailed(errorDrawable);
                                    qrcode.setVisibility(View.GONE);
                                    btnView.setVisibility(View.GONE);
                                    labelTV.setVisibility(View.GONE);
                                }

                                @Override
                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                    if (tabPos == 1) {
                                        btnView.setVisibility(View.VISIBLE);
                                        qrcode.setVisibility(View.VISIBLE);
                                        labelTV.setVisibility(View.VISIBLE);
                                        qrcode.setImageBitmap(resource);
                                    }
                                    iciciQRDrawable = resource;
                                }
                            });
                }
            } else {
                btnView.setVisibility(View.GONE);
                qrcode.setVisibility(View.GONE);
                labelTV.setVisibility(View.GONE);
            }
        }


    }

    void setICICIData() {
        tabPos = 1;
        iciciView.setBackgroundResource(R.drawable.rounded_primary_border);
        razorPayView.setBackgroundResource(R.drawable.rounded_grey_border);
        cashfreeView.setBackgroundResource(R.drawable.rounded_grey_border);
        detail.setVisibility(View.VISIBLE);
        pagerContainer.setVisibility(View.GONE);

        if (detailsICICIStr == null || detailsICICIStr.isEmpty()) {

            if (mUserQRInfo.getBeneName() != null && !mUserQRInfo.getBeneName().isEmpty()) {
                detailsICICIStr = "Benificiary Name : " + mUserQRInfo.getBeneName() + "\n";
            }
            if (mUserQRInfo.getBankName() != null && !mUserQRInfo.getBankName().isEmpty()) {
                detailsICICIStr = detailsICICIStr + "Bank Name : " + mUserQRInfo.getBankName() + "\n";
            }
            if (mUserQRInfo.getBranch() != null && !mUserQRInfo.getBranch().isEmpty()) {
                detailsICICIStr = detailsICICIStr + "Branch : " + mUserQRInfo.getBranch() + "\n";
            }
            if (mUserQRInfo.getIfsc() != null && !mUserQRInfo.getIfsc().isEmpty()) {
                detailsICICIStr = detailsICICIStr + "IFSC : " + mUserQRInfo.getIfsc() + "\n";
            }
            if (mUserQRInfo.getVirtualAccount() != null && !mUserQRInfo.getVirtualAccount().isEmpty()) {
                detailsICICIStr = detailsICICIStr + "Virtual Account : " + mUserQRInfo.getVirtualAccount();
            }

        }
        if (!detailsICICIStr.isEmpty()) {
            detail.setText(detailsICICIStr);
        }

        if (iciciQRDrawable != null) {
            btnView.setVisibility(View.VISIBLE);
            qrcode.setVisibility(View.VISIBLE);
            labelTV.setVisibility(View.VISIBLE);
            qrcode.setImageBitmap(iciciQRDrawable);
        } else {

            Glide.with(this)
                    .asBitmap()
                    .load(ApplicationConstant.INSTANCE.baseQrImageUrl + mLoginDataResponse.getData().getUserID() +
                            "&appid=" + ApplicationConstant.INSTANCE.APP_ID + "&imei=" + deviceId + "&regKey=&version=" + BuildConfig.VERSION_NAME +
                            "&serialNo=" + deviceSerialNum + "&sessionID=" + mLoginDataResponse.getData().getSessionID() +
                            "&session=" + mLoginDataResponse.getData().getSession() + "&loginTypeID=" + mLoginDataResponse.getData().getLoginTypeID())
                    .apply(requestOptions)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onLoadStarted(Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                            if (tabPos == 1) {
                                qrcode.setVisibility(View.VISIBLE);
                                btnView.setVisibility(View.GONE);
                                labelTV.setVisibility(View.GONE);
                                qrcode.setImageResource(R.drawable.placeholder_square);
                            }
                        }

                        @Override
                        public void onLoadFailed(Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            qrcode.setVisibility(View.GONE);
                            btnView.setVisibility(View.GONE);
                            labelTV.setVisibility(View.GONE);
                        }

                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            if (tabPos == 1) {
                                btnView.setVisibility(View.VISIBLE);
                                qrcode.setVisibility(View.VISIBLE);
                                labelTV.setVisibility(View.VISIBLE);
                                qrcode.setImageBitmap(resource);
                            }
                            iciciQRDrawable = resource;
                        }
                    });
        }

    }

    void setRazorPayData(UserSmartDetail razorPayData) {
        tabPos = 2;
        iciciView.setBackgroundResource(R.drawable.rounded_grey_border);
        razorPayView.setBackgroundResource(R.drawable.rounded_primary_border);
        cashfreeView.setBackgroundResource(R.drawable.rounded_grey_border);
        pagerContainer.setVisibility(View.GONE);

        UserSmartDetail razorPaySmartData = (UserSmartDetail) razorPayData.getData();
        if (detailsRazorPayStr == null || detailsRazorPayStr.isEmpty()) {
            if (razorPayData.isVirtualShow()) {
                if (razorPaySmartData.getBankName() != null && !razorPaySmartData.getBankName().isEmpty()) {
                    detailsRazorPayStr = "Bank Name : " + razorPaySmartData.getBankName();
                }
                if (razorPaySmartData.getBeneName() != null && !razorPaySmartData.getBeneName().isEmpty()) {
                    detailsRazorPayStr = detailsRazorPayStr + "\n" + "Benificiary Name : " + razorPaySmartData.getBeneName();
                }
                if (razorPaySmartData.getAccountHolder() != null && !razorPaySmartData.getAccountHolder().isEmpty()) {
                    detailsRazorPayStr = detailsRazorPayStr + "\n" + "Account Holder : " + razorPaySmartData.getAccountHolder();
                }
                if (razorPaySmartData.getSmartAccountNo() != null && !razorPaySmartData.getSmartAccountNo().isEmpty()) {
                    detailsRazorPayStr = detailsRazorPayStr + "\n" + "Account Number : " + razorPaySmartData.getSmartAccountNo();
                }
                if (razorPaySmartData.getBranch() != null && !razorPaySmartData.getBranch().isEmpty()) {
                    detailsRazorPayStr = detailsRazorPayStr + "\n" + "Branch : " + razorPaySmartData.getBranch();
                }
                if (razorPaySmartData.getIfsc() != null && !razorPaySmartData.getIfsc().isEmpty()) {
                    detailsRazorPayStr = detailsRazorPayStr + "\n" + "IFSC : " + razorPaySmartData.getIfsc();
                }
                if (razorPaySmartData.getCustomerID() != null && !razorPaySmartData.getCustomerID().isEmpty()) {
                    detailsRazorPayStr = detailsRazorPayStr + "\n" + "Customer ID : " + razorPaySmartData.getCustomerID();
                }
                if (razorPaySmartData.getVirtualAccount() != null && !razorPaySmartData.getVirtualAccount().isEmpty()) {
                    detailsRazorPayStr = detailsRazorPayStr + "\n" + "Virtual Account : " + razorPaySmartData.getVirtualAccount();
                }
            }
            if (razorPayData.isVPAShow() && razorPaySmartData.getSmartVPA() != null && !razorPaySmartData.getSmartVPA().isEmpty()) {
                detailsRazorPayStr = detailsRazorPayStr + "\n" + "VPA : " + razorPaySmartData.getSmartVPA();
            }

        }
        if (!detailsRazorPayStr.isEmpty()) {
            detail.setVisibility(View.VISIBLE);
            titleView.setVisibility(View.GONE);
            detail.setText(detailsRazorPayStr);
        } else {
            detail.setVisibility(View.GONE);
            titleView.setVisibility(View.VISIBLE);
        }


        if (razorpayQRDrawable != null) {
            btnView.setVisibility(View.VISIBLE);
            qrcode.setVisibility(View.VISIBLE);
            labelTV.setVisibility(View.VISIBLE);
            qrcode.setImageBitmap(razorpayQRDrawable);
        } else {
            if (razorPayData.isQRShow() && razorPaySmartData.getSmartQRShortURL() != null && !razorPaySmartData.getSmartQRShortURL().isEmpty()) {
                String id = razorPaySmartData.getSmartQRShortURL();
                if (id.contains("/")) {
                    id = id.substring(id.indexOf("/") + 1);
                }
                Glide.with(this)
                        .asBitmap()
                        .load(ApplicationConstant.INSTANCE.QRImageForCollectURL + mLoginDataResponse.getData().getUserID() +
                                "&appid=" + ApplicationConstant.INSTANCE.APP_ID + "&imei=" + deviceId + "&regKey=&version=" + BuildConfig.VERSION_NAME +
                                "&serialNo=" + deviceSerialNum + "&sessionID=" + mLoginDataResponse.getData().getSessionID() +
                                "&session=" + mLoginDataResponse.getData().getSession() + "&loginTypeID=" + mLoginDataResponse.getData().getLoginTypeID()
                                + "&id=" + id)
                        .apply(requestOptions)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                super.onLoadStarted(placeholder);
                                if (tabPos == 2) {
                                    qrcode.setVisibility(View.VISIBLE);
                                    btnView.setVisibility(View.GONE);
                                    labelTV.setVisibility(View.GONE);
                                    qrcode.setImageResource(R.drawable.placeholder_square);
                                }
                            }

                            @Override
                            public void onLoadFailed(Drawable errorDrawable) {
                                qrcode.setVisibility(View.GONE);
                                labelTV.setVisibility(View.GONE);
                                btnView.setVisibility(View.GONE);
                                super.onLoadFailed(errorDrawable);
                            }

                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                if (tabPos == 2) {
                                    btnView.setVisibility(View.VISIBLE);
                                    qrcode.setVisibility(View.VISIBLE);
                                    labelTV.setVisibility(View.VISIBLE);
                                    qrcode.setImageBitmap(resource);
                                }
                                razorpayQRDrawable = resource;
                            }
                        });
            } else {
                qrcode.setVisibility(View.GONE);
                btnView.setVisibility(View.GONE);
                labelTV.setVisibility(View.GONE);
            }
        }

    }

    void setRazorPayData() {
        tabPos = 2;
        iciciView.setBackgroundResource(R.drawable.rounded_grey_border);
        razorPayView.setBackgroundResource(R.drawable.rounded_primary_border);
        cashfreeView.setBackgroundResource(R.drawable.rounded_grey_border);
        detail.setVisibility(View.VISIBLE);
        pagerContainer.setVisibility(View.GONE);
        if (detailsRazorPayStr == null || detailsRazorPayStr.isEmpty()) {
            if (mUserQRInfo.getUserSDetail().getBankName() != null && !mUserQRInfo.getUserSDetail().getBankName().isEmpty()) {
                detailsRazorPayStr = "Bank Name : " + mUserQRInfo.getUserSDetail().getBankName() + "\n";
            }
            if (mUserQRInfo.getUserSDetail().getIfsc() != null && !mUserQRInfo.getUserSDetail().getIfsc().isEmpty()) {
                detailsRazorPayStr = detailsRazorPayStr + "IFSC : " + mUserQRInfo.getUserSDetail().getIfsc() + "\n";
            }
            if (mUserQRInfo.getUserSDetail().getSmartVPA() != null && !mUserQRInfo.getUserSDetail().getSmartVPA().isEmpty()) {
                detailsRazorPayStr = detailsRazorPayStr + "VPA : " + mUserQRInfo.getUserSDetail().getSmartVPA() + "\n";
            }
            if (mUserQRInfo.getUserSDetail().getCustomerID() != null && !mUserQRInfo.getUserSDetail().getCustomerID().isEmpty()) {
                detailsRazorPayStr = detailsRazorPayStr + "Customer ID : " + mUserQRInfo.getUserSDetail().getCustomerID() + "\n";
            }
            if (mUserQRInfo.getUserSDetail().getSmartAccountNo() != null && !mUserQRInfo.getUserSDetail().getSmartAccountNo().isEmpty()) {
                detailsRazorPayStr = detailsRazorPayStr + "Virtual Account : " + mUserQRInfo.getUserSDetail().getSmartAccountNo();
            }


        }
        if (!detailsRazorPayStr.isEmpty()) {
            detail.setText(detailsRazorPayStr);
        }
        if (razorpayQRDrawable != null) {
            btnView.setVisibility(View.VISIBLE);
            qrcode.setVisibility(View.VISIBLE);
            labelTV.setVisibility(View.VISIBLE);
            qrcode.setImageBitmap(razorpayQRDrawable);
        } else {
            if (mUserQRInfo.getUserSDetail().getSmartQRShortURL() != null &&
                    URLUtil.isValidUrl(mUserQRInfo.getUserSDetail().getSmartQRShortURL())) {

                Glide.with(this)
                        .asBitmap()
                        .load(mUserQRInfo.getUserSDetail().getSmartQRShortURL())
                        .apply(requestOptions)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                super.onLoadStarted(placeholder);
                                if (tabPos == 2) {
                                    qrcode.setVisibility(View.VISIBLE);
                                    btnView.setVisibility(View.GONE);
                                    labelTV.setVisibility(View.GONE);
                                    qrcode.setImageResource(R.drawable.placeholder_square);
                                }
                            }

                            @Override
                            public void onLoadFailed(Drawable errorDrawable) {
                                qrcode.setVisibility(View.GONE);
                                labelTV.setVisibility(View.GONE);
                                btnView.setVisibility(View.GONE);
                                super.onLoadFailed(errorDrawable);
                            }

                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                if (tabPos == 2) {
                                    btnView.setVisibility(View.VISIBLE);
                                    qrcode.setVisibility(View.VISIBLE);
                                    labelTV.setVisibility(View.VISIBLE);
                                    qrcode.setImageBitmap(resource);
                                }
                                razorpayQRDrawable = resource;
                            }
                        });
            } else {
                qrcode.setVisibility(View.GONE);
                btnView.setVisibility(View.GONE);
                labelTV.setVisibility(View.GONE);
            }
        }

    }

    void setCashFreeData(UserSmartDetail cashfreeData) {
        tabPos = 3;
        iciciView.setBackgroundResource(R.drawable.rounded_grey_border);
        razorPayView.setBackgroundResource(R.drawable.rounded_grey_border);
        cashfreeView.setBackgroundResource(R.drawable.rounded_primary_border);
        pagerContainer.setVisibility(View.VISIBLE);
        titleView.setVisibility(View.GONE);
        detail.setVisibility(View.GONE);
        qrcode.setVisibility(View.GONE);
        btnView.setVisibility(View.GONE);
        labelTV.setVisibility(View.GONE);
        if (mViewPager.getAdapter() == null) {
            setBannerData(cashfreeData);
        } else {
            View view = mViewPager.getChildAt(mViewPager.getCurrentItem());
            ImageView qrcode = view.findViewById(R.id.qrcode);
            if (qrcode.getVisibility() == View.VISIBLE) {
                btnView.setVisibility(View.VISIBLE);
                labelTV.setVisibility(View.VISIBLE);
            } else {
                btnView.setVisibility(View.GONE);
                labelTV.setVisibility(View.GONE);
            }
        }
    }

    void setCashFreeData() {
        tabPos = 3;
        iciciView.setBackgroundResource(R.drawable.rounded_grey_border);
        razorPayView.setBackgroundResource(R.drawable.rounded_grey_border);
        cashfreeView.setBackgroundResource(R.drawable.rounded_primary_border);
        pagerContainer.setVisibility(View.VISIBLE);
        detail.setVisibility(View.GONE);
        qrcode.setVisibility(View.GONE);
        btnView.setVisibility(View.GONE);
        labelTV.setVisibility(View.GONE);
        if (mViewPager.getAdapter() == null) {
            setBannerData(mUserQRInfo.getCashfreeSmartDetail());
        } else {
            View view = mViewPager.getChildAt(mViewPager.getCurrentItem());
            ImageView qrcode = view.findViewById(R.id.qrcode);
            if (qrcode.getVisibility() == View.VISIBLE) {
                btnView.setVisibility(View.VISIBLE);
                labelTV.setVisibility(View.VISIBLE);
            } else {
                btnView.setVisibility(View.GONE);
                labelTV.setVisibility(View.GONE);
            }
        }
    }

    void setBannerData(UserSmartDetail cashfreeData) {

        if (cashfreeData != null && cashfreeData.getData() != null) {
            ArrayList<UserSmartDetail> cashfreeDataArrayList = (ArrayList<UserSmartDetail>) cashfreeData.getData();
            if (cashfreeDataArrayList != null && cashfreeDataArrayList.size() > 0) {
                pagerContainer.setVisibility(View.VISIBLE);
                VirtualSmartAddressPagerAdapter mPagerAdapter = new VirtualSmartAddressPagerAdapter(cashfreeDataArrayList, this, requestOptions, cashfreeData);
                mViewPager.setAdapter(mPagerAdapter);
                mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());
           /* mViewPager.setPageMargin(pagerleft);
            mViewPager.setPadding(pagerleft, pagerTop, pagerleft, pagerTop);
            mViewPager.setClipToPadding(false);*/
                mDotsCount = mViewPager.getAdapter().getCount();
                mDotsText = new TextView[mDotsCount];
                //here we set the dots
                if (mDotsCount > 1) {
                    dotsCount.setVisibility(View.VISIBLE);
                    dotsCount.removeAllViews();
                    for (int i = 0; i < mDotsCount; i++) {
                        mDotsText[i] = new TextView(this);
                        mDotsText[i].setText(".");
                        mDotsText[i].setTextSize(50);
                        mDotsText[i].setTypeface(null, Typeface.BOLD);
                        mDotsText[i].setTextColor(getResources().getColor(R.color.light_grey));
                        dotsCount.addView(mDotsText[i]);
                    }

                } else {
                    dotsCount.setVisibility(View.GONE);
                }
                mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        if (mDotsCount > 1) {
                            for (int i = 0; i < mDotsCount; i++) {
                                mDotsText[i].setTextColor(getResources().getColor(R.color.light_grey));
                            }
                            mDotsText[position].setTextColor(getResources().getColor(R.color.colorPrimary));
                        }
                    }

                    @Override
                    public void onPageSelected(int position) {
                        View view = mViewPager.getChildAt(position);
                        ImageView qrcode = view.findViewById(R.id.qrcode);
                        if (qrcode.getVisibility() == View.VISIBLE) {
                            btnView.setVisibility(View.VISIBLE);
                            labelTV.setVisibility(View.VISIBLE);
                        } else {
                            btnView.setVisibility(View.GONE);
                            labelTV.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

            } else {
                pagerContainer.setVisibility(View.GONE);
            }
        } else {
            pagerContainer.setVisibility(View.GONE);
        }
    }

    void setBannerData(ArrayList<UserSmartDetail> responseArray) {
        if (responseArray != null && responseArray.size() > 0) {
            pagerContainer.setVisibility(View.VISIBLE);

            VirtualSmartAddressPagerAdapter mPagerAdapter = new VirtualSmartAddressPagerAdapter(responseArray, this, requestOptions, null);
            mViewPager.setAdapter(mPagerAdapter);
            mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());
           /* mViewPager.setPageMargin(pagerleft);
            mViewPager.setPadding(pagerleft, pagerTop, pagerleft, pagerTop);
            mViewPager.setClipToPadding(false);*/
            mDotsCount = mViewPager.getAdapter().getCount();
            mDotsText = new TextView[mDotsCount];
            //here we set the dots
            if (mDotsCount > 1) {
                dotsCount.setVisibility(View.VISIBLE);
                dotsCount.removeAllViews();
                for (int i = 0; i < mDotsCount; i++) {
                    mDotsText[i] = new TextView(this);
                    mDotsText[i].setText(".");
                    mDotsText[i].setTextSize(50);
                    mDotsText[i].setTypeface(null, Typeface.BOLD);
                    mDotsText[i].setTextColor(getResources().getColor(R.color.light_grey));
                    dotsCount.addView(mDotsText[i]);
                }

            } else {
                dotsCount.setVisibility(View.GONE);
            }
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (mDotsCount > 1) {
                        for (int i = 0; i < mDotsCount; i++) {
                            mDotsText[i].setTextColor(getResources().getColor(R.color.light_grey));
                        }
                        mDotsText[position].setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                }

                @Override
                public void onPageSelected(int position) {
                    View view = mViewPager.getChildAt(position);
                    ImageView qrcode = view.findViewById(R.id.qrcode);
                    if (qrcode.getVisibility() == View.VISIBLE) {
                        btnView.setVisibility(View.VISIBLE);
                        labelTV.setVisibility(View.VISIBLE);
                    } else {
                        btnView.setVisibility(View.GONE);
                        labelTV.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        } else {
            pagerContainer.setVisibility(View.GONE);
        }
    }


    String getHtml(String prefix, String number) {
        if (number.contains(",")) {
            String[] array = number.split(",");
            String num = "";

            for (int i = 0; i < array.length; i++) {

                if (i > 0) {
                    num = num + ", <a href=tel:" + array[i] + ">" + array[i] + "</a>";
                } else {
                    num = num + "<a href=tel:" + array[i] + ">" + array[i] + "</a>";
                }

            }
            return prefix + " - " + num;
        } else {
            return prefix + " - <a href=tel:" + number + ">" + number + "</a>";
        }
    }

    public void shareit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(this, PermissionActivity.class),
                        INTENT_PERMISSION_IMAGE);

            } else {
                getBitmap();
            }
        } else {
            getBitmap();
        }


    }

    void getBitmap() {
        shareView.setDrawingCacheEnabled(true);
        Bitmap myBitmap = shareView.getDrawingCache();
        saveImage(isDownload, myBitmap);
        shareView.setDrawingCacheEnabled(false);
    }

    private void saveImage(boolean isDownload, Bitmap bitmap) {
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            ContentValues values = contentValues();
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
            values.put(MediaStore.Images.Media.IS_PENDING, true);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "QR_CODE");

            Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try {
                    saveImageToStream(bitmap, this.getContentResolver().openOutputStream(uri));
                    values.put(MediaStore.Images.Media.IS_PENDING, false);
                    this.getContentResolver().update(uri, values, null, null);
                    if (isDownload) {
                        Toast.makeText(this, "Successfully Download", Toast.LENGTH_SHORT).show();
                        MediaScannerConnection.scanFile(this, new String[]{uri.getPath()}, new String[]{"image/png"}, null);
                    } else {
                        sendMail(uri);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } else {
            File directory = new File(Environment.getExternalStorageDirectory().toString() + "/Pictures/" + getString(R.string.app_name));

            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = "QR_CODE.png";
            File file = new File(directory, fileName);
            try {
                saveImageToStream(bitmap, new FileOutputStream(file));
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (isDownload) {
                    Toast.makeText(this, "Successfully Download", Toast.LENGTH_SHORT).show();
                    MediaScannerConnection.scanFile(this, new String[]{file.getPath()}, new String[]{"image/png"}, null);
                } else {
                    sendMail(Uri.parse("file://" + file));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        }
        return values;
    }

    private void saveImageToStream(Bitmap bitmap, OutputStream outputStream) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
            } catch (FileNotFoundException e) {
                Log.e("GREC", e.getMessage(), e);
            } catch (IOException e) {
                Log.e("GREC", e.getMessage(), e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

   /* public void saveBitmap(boolean isDownload, Bitmap bitmap) {

        File filePath = new File(Environment.getExternalStorageDirectory().toString() + "/" + getString(R.string.app_name));

        if (!filePath.exists()) {
            filePath.mkdir();
        }
        File imagePath = new File(filePath + "/Virtual_Account.jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            if (isDownload) {
                Toast.makeText(this, "Successfully Download", Toast.LENGTH_SHORT).show();
                MediaScannerConnection.scanFile(this, new String[]{imagePath.getPath()}, new String[]{"image/jpeg"}, null);
            } else {
                sendMail(imagePath.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void sendMail(Uri myUri) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "Virtual Account");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Virtual Account");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_PERMISSION_IMAGE && resultCode == RESULT_OK) {
            getBitmap();
        } else if (requestCode == INTENT_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String codeValue = data.getStringExtra("codeValue");
                Uri mUri = Uri.parse(codeValue);
                String pa = mUri.getQueryParameter("pa");
                String pn = mUri.getQueryParameter("pn");
                String tr = mUri.getQueryParameter("tr");
                String mc = mUri.getQueryParameter("mc");
                if (pa != null && mc != null && tr != null && pn != null) {


                    new CustomAlertDialog(this).WarningWithDoubleBtnCallBack(pn + " : " + pa, "QR Detais", "Link", true, new CustomAlertDialog.DialogCallBack() {
                        @Override
                        public void onPositiveClick() {
                            MapQRToUser(VirtualAccountActivity.this, codeValue, loader);
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });
                }
            }


        }
    }


    public void MapQRToUser(final Activity context, String qrData, final CustomLoader loader) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call = git.MapQRToUser(new MapQRToUserRequest(qrData,
                    mLoginDataResponse.getData().getUserID() + "",
                    mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                ApiFintechUtilMethods.INSTANCE.Successful(context, response.body().getMsg() + "");
                                cameraView.setVisibility(View.GONE);
                                setQRCode();
                            } else {
                                if (response.body().getVersionValid() == false) {
                                    ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();

                    try {
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        ApiFintechUtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            ApiFintechUtilMethods.INSTANCE.Error(context, e.getMessage());
        }

    }

    public void VisibleBtnView() {
        btnView.setVisibility(View.VISIBLE);
        labelTV.setVisibility(View.VISIBLE);
    }
}
