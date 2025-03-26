package com.solution.app.justpay4u.ApiHits;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.solution.app.justpay4u.Api.Fintech.Object.AssignedOpType;
import com.solution.app.justpay4u.Api.Fintech.Object.BeneDetail;
import com.solution.app.justpay4u.Api.Fintech.Object.CommissionDisplay;
import com.solution.app.justpay4u.Api.Fintech.Object.DashBoardServiceReportOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.DthPackage;
import com.solution.app.justpay4u.Api.Fintech.Object.GetDthPackageRequest;
import com.solution.app.justpay4u.Api.Fintech.Object.IncentiveDetails;
import com.solution.app.justpay4u.Api.Fintech.Object.NumberList;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.Api.Fintech.Object.PidData;
import com.solution.app.justpay4u.Api.Fintech.Object.SenderObject;
import com.solution.app.justpay4u.Api.Fintech.Request.ASPayCollectRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.AccountOpenListRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.AchieveTargetRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.AddFundRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.AppGetAMRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.AreaWithPincodeRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.BalanceRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.CallBackRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.DFStatusRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.DTHSubscriptionRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.DmrRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.DthSubscriptionReportRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.FetchBillRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.FosAccStmtAndCollReportRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.FundDCReportRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.FundTransferRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GenerateDepositOTPRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GenerateOrderForUPIRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetAdditionalServiceRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetAepsRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetChargedAmountRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetDMTReceiptRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetDthPackageChannelRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetSenderRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.GetUserClaimsReportRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.IncentiveDetailRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.IntiateUPIRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.LapuRealCommissionRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.LedgerReportRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.LoginRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.LogoutRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.MoveToBankReportRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.NewsRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.NunberListRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.OnboardingRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.OptionalOperatorRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.OtpRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.PurchaseTokenRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.RealApiChangeRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.RechargeReportRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.RechargeRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.RefundLogRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.RefundRequestRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.RequestSendMoney;
import com.solution.app.justpay4u.Api.Fintech.Request.ResendOtpRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.SendMoneyRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.SlabRangeDetailRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.SubmitSocialDetailsRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UpdateFcmRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UpdateKycRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UpdateUPIRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UpgradePackageRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UserDayBookRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UserMNPClaimRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UserMNPRegistrationRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.W2RRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.AccountOpenListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.AppGetAMResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.AreaWithPincodeResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BankListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.CircleZoneListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DFStatusResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DMTChargedAmountResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DMTReceiptResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DthSubscriptionReportResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.FetchBillResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.FosAccStmtAndCollReportResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.FundreqToResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GenerateDepositOTPResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetAEPSResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetAvailablePackageResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetBankAndPaymentModeResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetDthPackageResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetMNPStatusResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetUserResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetVAResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.InitiateUpiResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.NumberSeriesListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OnboardingResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OpTypeResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OperatorListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RechargeReportResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RechargeResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RefundRequestResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.SettlementAccountResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.SlabCommissionResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.SlabRangeDetailResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.WalletTypeResponse;
import com.solution.app.justpay4u.Api.Networking.Object.DashboardDownlineData;
import com.solution.app.justpay4u.Api.Networking.Response.NetworkingDashboardResponse;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.AEPS.Fragment.AEPSFingerPrintEKycDialogFragment;
import com.solution.app.justpay4u.Fintech.Activities.MNPRegisterActivity;
import com.solution.app.justpay4u.Fintech.Activities.UpdateProfileActivity;
import com.solution.app.justpay4u.Fintech.Authentication.ChangePinPassActivity;
import com.solution.app.justpay4u.Fintech.Authentication.LoginActivity;
import com.solution.app.justpay4u.Fintech.Dashboard.Activity.HomeDashActivity;
import com.solution.app.justpay4u.Fintech.Employee.Activity.EmpDashboardActivity;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.EmpUserRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.GetPSTReportEmpRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.GetTargetReportEmpRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.GetTodayTransactorsRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.MapPointRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.MeetingDetailRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.PostDailyClosingRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Request.SetUserCommitmentRequest;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetEmpDownlineUserResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetEmpTodayLivePSTResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetEmployeesResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetEmployeesUserResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetLMTDVsMTDResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetLastSevenDayPSTDataResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetLastdayVsTodayChartResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetMeetingDetailResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetMeetingReportResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetPstReportResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetTargetReportResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetTargetSegmentResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetTeriatryReportResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetTodayOutletListForEmpResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetTodayTransactorsResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetUserCommitmentResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.MapPointResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.PostDailyClosingResponse;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.DMRRecieptActivity;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.PaymentRequest;
import com.solution.app.justpay4u.Fintech.Notification.app.Config;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.RechargeSlipActivity;
import com.solution.app.justpay4u.Fintech.Reports.Activity.DMRReportActivity;
import com.solution.app.justpay4u.Fintech.Reports.Activity.UserDayBookActivity;
import com.solution.app.justpay4u.Networking.Activity.NetworkingDashboardActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.ShoppingDashboardActivity;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.EKYCStepsDialog;
import com.solution.app.justpay4u.Util.PinEntryEditText;
import com.solution.app.justpay4u.Util.Utility;
import com.solution.app.justpay4u.WalletToWalletTransfer.dto.UDetailByMobRequest;
import com.solution.app.justpay4u.WalletToWalletTransfer.dto.UDetailByMobResponse;
import com.solution.app.justpay4u.WalletToWalletTransfer.dto.WalletToWalletFTRequest;

import java.io.File;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vishnu on 24-08-2017.
 */

public enum ApiFintechUtilMethods {

    INSTANCE;
    public static boolean isPassChangeDialogShowing;
    public static boolean isSocialEmailVerfiedChecked;
    public static boolean isFilterBottomSheetDialogShowing;
    public static double getLattitude, getLongitude;
    public HashMap<Integer, ArrayList<OperatorList>> mAccountOpenOPTypes = new HashMap<>();
    public int ERROR_NETWORK = 2;
    public int ERROR_OTHER = 1;
    public LoginResponse mTempLoginDataResponse;
    public String deviceId = "", deviceSerialNumber = "";
    public String appLogoUrl;
    public AppUserListResponse mCompanyProfileResponse;
    public BalanceResponse mBalanceResponse;
    public GetUserResponse mGetUserResponse;
    public RequestOptions requestOptionsPlaceHolder, requestOptionsUserIcon, requestOptionsRankImage, requestOptionsAppLogo, requestOptionsAppLogoCircleCrop;
    public DashBoardServiceReportOptions mDashBoardServiceReportOptions;
    public AppUserListResponse mBannerDataResponse;
    public boolean isReadyToUpdateBalance;
    public boolean isUserDetailUpdate;
    public boolean isDMTEnable;
    public boolean isDMTActive;
    public boolean isDMTServiceActive;
    public boolean isAdditionalService;
    public boolean isPaidService;
    Gson gson;
    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private AlertDialog alertDialogMobile;
    private CountDownTimer countDownTimer;
    private AlertDialog alertDialogAdvertisement;
    private Dialog dialogOTP, dialogUpdateApp;
    private EKYCStepsDialog eKYCStepsDialog;
    private int callOnBoardOId;
    private String onboardingOTPReffId = "0";
    private boolean isBioMatricScreenOpen;
    private AppUserListResponse mNewsDataResponse;
    private OperatorListResponse mOperatorListResponse;
    private AppPreferences mAppPreferences;
    private NumberSeriesListResponse mNumberSeriesListResponse;
    private CircleZoneListResponse mCircleZoneListResponse;
    private WalletTypeResponse mWalletTypeResponse;
    private AppGetAMResponse mAppGetAMResponse;
    public NetworkingDashboardResponse mNetworkingDashboardResponse;
    public RankListResponse mRankListResponse;
    public DashboardDownlineData singleDataResponse;
    private double requestedAmt;
    private BottomSheetDialog bottomSheetDialog;

    public AppPreferences getAppPreferences(Context mContext) {
        if (mAppPreferences != null) {
            return mAppPreferences;
        } else {
            mAppPreferences = new AppPreferences(mContext);
            return mAppPreferences;
        }

    }

    public RequestOptions getRequestOption_With_Placeholder() {
        if (requestOptionsPlaceHolder != null) {
            return requestOptionsPlaceHolder;
        } else {
            requestOptionsPlaceHolder = new RequestOptions();
            requestOptionsPlaceHolder.placeholder(R.drawable.placeholder_square);
            requestOptionsPlaceHolder.error(R.drawable.placeholder_square);
            requestOptionsPlaceHolder.diskCacheStrategy(DiskCacheStrategy.ALL);
            return requestOptionsPlaceHolder;
        }
    }

    public RequestOptions getRequestOption_With_UserIcon() {
        if (requestOptionsUserIcon != null) {
            return requestOptionsUserIcon;
        } else {
            requestOptionsUserIcon = new RequestOptions();
            requestOptionsUserIcon.placeholder(R.drawable.user_icon);
            requestOptionsUserIcon.error(R.drawable.user_icon);
            requestOptionsUserIcon.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptionsUserIcon.skipMemoryCache(true);
            requestOptionsUserIcon.transform(new CircleCrop());
            return requestOptionsUserIcon;
        }
    }

    public RequestOptions getRequestOption_With_RankImage() {
        if (requestOptionsRankImage != null) {
            return requestOptionsRankImage;
        } else {
            requestOptionsRankImage = new RequestOptions();
            requestOptionsRankImage.placeholder(R.drawable.progess_effect);
            requestOptionsRankImage.error(R.drawable.star_on);
            requestOptionsRankImage.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptionsRankImage.skipMemoryCache(true);
            requestOptionsRankImage.transform(new CircleCrop());
            return requestOptionsRankImage;
        }
    }

    public String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        return dateFormat.format(date);
    }

    public RequestOptions getRequestOption_With_AppLogo() {
        if (requestOptionsAppLogo != null) {
            return requestOptionsAppLogo;
        } else {
            requestOptionsAppLogo = new RequestOptions();
            requestOptionsAppLogo.placeholder(R.drawable.app_logo);
            requestOptionsAppLogo.error(R.drawable.app_logo);
            requestOptionsAppLogo.diskCacheStrategy(DiskCacheStrategy.ALL);
            return requestOptionsAppLogo;
        }
    }

    public RequestOptions getRequestOption_With_AppLogo_circleCrop() {
        if (requestOptionsAppLogoCircleCrop != null) {
            return requestOptionsAppLogoCircleCrop;
        } else {
            requestOptionsAppLogoCircleCrop = new RequestOptions();
            requestOptionsAppLogoCircleCrop.circleCropTransform();
            requestOptionsAppLogoCircleCrop.placeholder(R.drawable.app_logo);
            requestOptionsAppLogoCircleCrop.error(R.drawable.app_logo);
            requestOptionsAppLogoCircleCrop.diskCacheStrategy(DiskCacheStrategy.ALL);
            return requestOptionsAppLogoCircleCrop;
        }
    }

    public LoginResponse getLoginResponse(AppPreferences mAppPreferences) {
        if (mTempLoginDataResponse != null && mTempLoginDataResponse.getData() != null) {
            return mTempLoginDataResponse;
        } else {
            if (gson == null) {
                gson = new Gson();
            }
            mTempLoginDataResponse = gson.fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.LoginPref), LoginResponse.class);
            return mTempLoginDataResponse;
        }

    }

    public OperatorListResponse getOperatorListResponse(AppPreferences mAppPreferences) {
        if (mOperatorListResponse != null && mOperatorListResponse.getOperators() != null) {
            return mOperatorListResponse;
        } else {
            if (gson == null) {
                gson = new Gson();
            }
            mOperatorListResponse = gson.fromJson(mAppPreferences.getNonRemovalString(ApplicationConstant.INSTANCE.operatorListPref), OperatorListResponse.class);
            return mOperatorListResponse;
        }

    }

    public NumberSeriesListResponse getNumberSeriesListResponse(AppPreferences mAppPreferences) {
        if (mNumberSeriesListResponse != null && mNumberSeriesListResponse.getNumSeries() != null) {
            return mNumberSeriesListResponse;
        } else {
            if (gson == null) {
                gson = new Gson();
            }
            mNumberSeriesListResponse = gson.fromJson(mAppPreferences.getNonRemovalString(ApplicationConstant.INSTANCE.numberSeriesListPref), NumberSeriesListResponse.class);
            return mNumberSeriesListResponse;
        }

    }

    public CircleZoneListResponse getCircleZoneListResponse(AppPreferences mAppPreferences) {
        if (mCircleZoneListResponse != null && mCircleZoneListResponse.getCirlces() != null) {
            return mCircleZoneListResponse;
        } else {
            if (gson == null) {
                gson = new Gson();
            }
            mCircleZoneListResponse = gson.fromJson(mAppPreferences.getNonRemovalString(ApplicationConstant.INSTANCE.circleZoneListPref), CircleZoneListResponse.class);
            return mCircleZoneListResponse;
        }

    }

    public WalletTypeResponse getWalletTypeResponse(AppPreferences mAppPreferences) {
        if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null) {
            return mWalletTypeResponse;
        } else {
            if (gson == null) {
                gson = new Gson();
            }
            mWalletTypeResponse = gson.fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.walletType), WalletTypeResponse.class);
            return mWalletTypeResponse;
        }

    }

    public AppUserListResponse getCompanyProfile(AppPreferences mAppPreferences) {
        if (mCompanyProfileResponse != null && mCompanyProfileResponse.getCompanyProfile() != null) {
            return mCompanyProfileResponse;
        } else {
            if (gson == null) {
                gson = new Gson();
            }
            mCompanyProfileResponse = gson.fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.contactUsPref), AppUserListResponse.class);
            return mCompanyProfileResponse;
        }

    }

    public AppUserListResponse getNewsData(AppPreferences mAppPreferences) {
        if (mNewsDataResponse != null && mNewsDataResponse.getCompanyProfile() != null) {
            return mNewsDataResponse;
        } else {
            if (gson == null) {
                gson = new Gson();
            }
            mNewsDataResponse = gson.fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.newsDataPref), AppUserListResponse.class);
            return mNewsDataResponse;
        }

    }

    public AppUserListResponse getBannerData(AppPreferences mAppPreferences) {
        if (mBannerDataResponse != null && mBannerDataResponse.getCompanyProfile() != null) {
            return mBannerDataResponse;
        } else {
            if (gson == null) {
                gson = new Gson();
            }
            mBannerDataResponse = gson.fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.bannerDataPref), AppUserListResponse.class);
            return mBannerDataResponse;
        }

    }

    public BalanceResponse getBalanceResponse(AppPreferences mAppPreferences) {
        if (mBalanceResponse != null && mBalanceResponse.getBalanceData() != null
                && mBalanceResponse.getBalanceData().size() > 0) {
            return mBalanceResponse;
        } else {
            if (gson == null) {
                gson = new Gson();
            }
            mBalanceResponse = gson.fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.balancePref), BalanceResponse.class);
            return mBalanceResponse;
        }

    }

    public GetUserResponse getUserDetailResponse(AppPreferences mAppPreferences) {
        if (mGetUserResponse != null && mGetUserResponse.getUserInfo() != null) {
            return mGetUserResponse;
        } else {
            if (gson == null) {
                gson = new Gson();
            }
            mGetUserResponse = gson.fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.UserDetailPref), GetUserResponse.class);
            return mGetUserResponse;
        }

    }

    public DashBoardServiceReportOptions getActiveService(AppPreferences mAppPreferences) {
        if (mDashBoardServiceReportOptions != null) {
            return mDashBoardServiceReportOptions;
        } else {
            if (gson == null) {
                gson = new Gson();
            }
            mDashBoardServiceReportOptions = gson.fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.activeServicePref), DashBoardServiceReportOptions.class);
            return mDashBoardServiceReportOptions;
        }

    }

    public AppGetAMResponse getAppGetAMResponse(AppPreferences mAppPreferences) {
        if (mAppGetAMResponse != null) {
            return mAppGetAMResponse;
        } else {
            if (gson == null) {
                gson = new Gson();
            }

            mAppGetAMResponse = gson.fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.areaListPref), AppGetAMResponse.class);
            return mAppGetAMResponse;
        }

    }

    public boolean isFlatCommission(AppPreferences mAppPreferences) {
        if (mBalanceResponse != null && mBalanceResponse.getBalanceData() != null
                && mBalanceResponse.getBalanceData().size() > 0) {
            return mBalanceResponse.isFlatCommission();
        } else {
            return mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isFlatCommissionPref);
        }

    }

    public String getDeviceId(AppPreferences mAppPreferences) {
        if (deviceId != null && !deviceId.isEmpty()) {
            return deviceId;
        } else {
            deviceId = mAppPreferences.getNonRemovalString(ApplicationConstant.INSTANCE.DeviceIdPref);
            return deviceId;
        }

    }

    public String getSerialNumber(AppPreferences mAppPreferences) {
        if (deviceSerialNumber != null && !deviceSerialNumber.isEmpty()) {
            return deviceSerialNumber;
        } else {
            deviceSerialNumber = mAppPreferences.getNonRemovalString(ApplicationConstant.INSTANCE.DeviceSerialNumberPref);
            return deviceSerialNumber;
        }

    }

    public String getAppLogoUrl(AppPreferences mAppPreferences) {
        if (appLogoUrl != null && !appLogoUrl.isEmpty()) {
            return appLogoUrl;
        } else {
            appLogoUrl = mAppPreferences.getNonRemovalString(ApplicationConstant.INSTANCE.appLogoUrlPref);
            return appLogoUrl;
        }

    }

    public String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        try {
            if (Build.VERSION.SDK_INT >= 29) {
                //deviceId = getUniqueID(context).replaceAll(" ", "").replaceAll("-", "").substring(0, 15);
                deviceId = androidId(context);
            } else if (Build.VERSION.SDK_INT >= 26 && Build.VERSION.SDK_INT <= 28) {

                if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                    }
                    deviceId = telephonyManager.getImei();

                } else if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
                    deviceId = telephonyManager.getImei();


                } else if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_SIP) {
                    deviceId = telephonyManager.getImei();

                } else if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
                    deviceId = telephonyManager.getImei();
                }
            } else {
                deviceId = telephonyManager.getDeviceId();
            }

        } catch (SecurityException e) {
            deviceId = androidId(context);
        } catch (Exception ex) {
            deviceId = androidId(context);
        }
        return deviceId;
    }


    public String getIMEI(Context context, AppPreferences mAppPreferences) {

        if (mAppPreferences != null) {
            return getDeviceId(mAppPreferences);
        } else {
            return getDeviceId(getAppPreferences(context));
        }
    }

   /* @RequiresApi(api = Build.VERSION_CODES.O)
    String getUniqueID(Context mContext) {
        UUID wideVineUuid = new UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L);
        try {
            MediaDrm wvDrm = new MediaDrm(wideVineUuid);
            byte[] wideVineId = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID);
            String deviceID = Base64.getEncoder().encodeToString(wideVineId);
            return deviceID;
        } catch (Exception e) {
            // Inspect exception
            return androidId(mContext);
        }
        // Close resources with close() or release() depending on platform API
        // Use ARM on Android P platform or higher, where MediaDrm has the close() method
    }*/

    public String androidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public String getSerialNo(Context context, AppPreferences mAppPreferences) {

        if (mAppPreferences != null) {
            return getSerialNumber(mAppPreferences);
        } else {
            return getSerialNumber(getAppPreferences(context));
        }
    }

    public String getDeviceSerialNo(Context context) {


        if (Build.VERSION.SDK_INT >= 26 && Build.VERSION.SDK_INT <= 28) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                deviceSerialNumber = androidId(context);
                return deviceSerialNumber;
            }
            deviceSerialNumber = Build.getSerial();
        } else if (android.os.Build.VERSION.SDK_INT < 26) {
            deviceSerialNumber = Build.SERIAL;
        } else {
            deviceSerialNumber = androidId(context);
        }

        return deviceSerialNumber;
    }


    public void NumberSeriesList(final Activity context, final ImageView refreshIv, String deviceId, String deviceSerialNum,
                                 AppPreferences mAppPreferences, ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<NumberSeriesListResponse> call = git.GetNumberSeries(new NunberListRequest(
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum));
            call.enqueue(new Callback<NumberSeriesListResponse>() {
                @Override
                public void onResponse(Call<NumberSeriesListResponse> call, final retrofit2.Response<NumberSeriesListResponse> response) {

                    try {
                        if (refreshIv != null) {
                            refreshIv.clearAnimation();
                        }
                        if (response.isSuccessful()) {
                            if (response != null && response.body() != null) {
                                mNumberSeriesListResponse = response.body();
                                if (mNumberSeriesListResponse.getStatuscode() == 1) {
                                    if (mNumberSeriesListResponse.getNumSeries() != null && mNumberSeriesListResponse.getNumSeries().size() > 0) {
                                        mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.numberSeriesListPref, new Gson().toJson(mNumberSeriesListResponse));
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(mNumberSeriesListResponse);
                                        }
                                    } else {
                                        Error(context, "Data not found");
                                    }
                                } else {
                                    if (mNumberSeriesListResponse.getMsg() != null) {
                                        Error(context, mNumberSeriesListResponse.getMsg());
                                    } else {
                                        Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                                    }

                                }
                            } else {
                                Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Error(context, e.getMessage());
                        if (refreshIv != null) {
                            refreshIv.clearAnimation();
                        }
                    }

                }

                @Override
                public void onFailure(Call<NumberSeriesListResponse> call, Throwable t) {
                    if (refreshIv != null) {
                        refreshIv.clearAnimation();
                    }
                    try {

                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Error(context, e.getMessage());
            if (refreshIv != null) {
                refreshIv.clearAnimation();
            }
        }
    }

    public void OperatorList(final Activity context, String deviceId, String deviceSerialNum, AppPreferences mAppPreferences,
                             ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<OperatorListResponse> call = git.GetOperators(new NunberListRequest(
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum));
            call.enqueue(new Callback<OperatorListResponse>() {
                @Override
                public void onResponse(Call<OperatorListResponse> call, final retrofit2.Response<OperatorListResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            if (response != null && response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    mOperatorListResponse = response.body();
                                    if (mOperatorListResponse.getOperators() != null && mOperatorListResponse.getOperators().size() > 0) {

                                        ArrayList<OperatorList> mDMTOperatorLists = new ArrayList<>();
                                        for (OperatorList op : mOperatorListResponse.getOperators()) {
                                            if (op.getOpType() == 14 && op.isActive()) {
                                                mDMTOperatorLists.add(op);
                                            }
                                        }
                                        mOperatorListResponse.setDmtOperatorLists(mDMTOperatorLists);
                                        mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.operatorListPref, new Gson().toJson(mOperatorListResponse));

                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(mOperatorListResponse);
                                        }
                                    } else {
                                        Error(context, "Data not found");
                                    }
                                } else {
                                    if (mOperatorListResponse.getMsg() != null) {
                                        Error(context, mOperatorListResponse.getMsg());
                                    } else {
                                        Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                                    }

                                }
                            } else {
                                Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<OperatorListResponse> call, Throwable t) {
                    try {

                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Error(context, e.getMessage());

        }
    }

    public void GetSettlementAccount(final Activity context, final CustomLoader loader, LoginResponse LoginDataResponse, AppPreferences mAppPreferences,
                                     String deviceId, String deviceSerialNum, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<SettlementAccountResponse> call = git.GetSettlementAccount(new BasicRequest(LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME,
                    deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<SettlementAccountResponse>() {
                @Override
                public void onResponse(Call<SettlementAccountResponse> call, final retrofit2.Response<SettlementAccountResponse> response) {

                    try {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getData() != null && response.body().getData().size() > 0) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    } else {
                                        Error(context, "Bank not found");
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(ERROR_OTHER);
                                        }
                                    }

                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }


                }

                @Override
                public void onFailure(Call<SettlementAccountResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Error(context, e.getMessage());
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
        }
    }


    public void OperatorList(final Activity context, CustomLoader loader, String deviceId, String deviceSerialNum, AppPreferences mAppPreferences,
                             LoginResponse LoginDataResponse, ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            /*Call<OperatorListResponse> call = git.GetOperators(new NunberListRequest(
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum));*/
            Call<OperatorListResponse> call = git.GetOperatorSession(new BasicRequest(LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<OperatorListResponse>() {
                @Override
                public void onResponse(Call<OperatorListResponse> call, final retrofit2.Response<OperatorListResponse> response) {

                    try {
                        if (loader != null) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response != null && response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    mOperatorListResponse = response.body();
                                    if (mOperatorListResponse.getOperators() != null && mOperatorListResponse.getOperators().size() > 0) {

                                        ArrayList<OperatorList> mDMTOperatorLists = new ArrayList<>();
                                        for (OperatorList op : mOperatorListResponse.getOperators()) {
                                            if (op.getOpType() == 14 && op.isActive()) {
                                                mDMTOperatorLists.add(op);
                                            }
                                        }
                                        mOperatorListResponse.setDmtOperatorLists(mDMTOperatorLists);
                                        mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.operatorListPref, new Gson().toJson(mOperatorListResponse));
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(mOperatorListResponse);
                                        }
                                    } else {
                                        Error(context, "Data not found");
                                    }
                                } else {
                                    if (mOperatorListResponse.getMsg() != null) {
                                        Error(context, mOperatorListResponse.getMsg());
                                    } else {
                                        Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                                    }

                                }
                            } else {
                                Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null) {
                            loader.dismiss();
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<OperatorListResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            loader.dismiss();
                        }
                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            loader.dismiss();
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                loader.dismiss();
            }
            Error(context, e.getMessage());

        }
    }

    public void OperatorList(final Activity context, String deviceId, String deviceSerialNum, AppPreferences mAppPreferences,
                             LoginResponse LoginDataResponse, ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            /*Call<OperatorListResponse> call = git.GetOperators(new NunberListRequest(
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum));*/
            Call<OperatorListResponse> call = git.GetOperatorSession(new BasicRequest(LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<OperatorListResponse>() {
                @Override
                public void onResponse(Call<OperatorListResponse> call, final retrofit2.Response<OperatorListResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            if (response != null && response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    mOperatorListResponse = response.body();
                                    if (mOperatorListResponse.getOperators() != null && mOperatorListResponse.getOperators().size() > 0) {

                                        ArrayList<OperatorList> mDMTOperatorLists = new ArrayList<>();
                                        for (OperatorList op : mOperatorListResponse.getOperators()) {
                                            if (op.getOpType() == 14 && op.isActive()) {
                                                mDMTOperatorLists.add(op);
                                            }
                                        }
                                        mOperatorListResponse.setDmtOperatorLists(mDMTOperatorLists);
                                        mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.operatorListPref, new Gson().toJson(mOperatorListResponse));

                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(mOperatorListResponse);
                                        }
                                    } else {
                                        Error(context, "Data not found");
                                    }
                                } else {
                                    if (mOperatorListResponse.getMsg() != null) {
                                        Error(context, mOperatorListResponse.getMsg());
                                    } else {
                                        Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                                    }

                                }
                            } else {
                                Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<OperatorListResponse> call, Throwable t) {
                    try {

                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Error(context, e.getMessage());

        }
    }

    public void CircleZoneList(final Activity context, String deviceId, String deviceSerialNum, AppPreferences mAppPreferences,
                               ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<CircleZoneListResponse> call = git.GetCircles(new NunberListRequest(
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum));
            call.enqueue(new Callback<CircleZoneListResponse>() {
                @Override
                public void onResponse(Call<CircleZoneListResponse> call, final retrofit2.Response<CircleZoneListResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            if (response != null && response.body() != null) {
                                mCircleZoneListResponse = response.body();
                                if (mCircleZoneListResponse.getStatuscode() == 1) {
                                    if (mCircleZoneListResponse.getCirlces() != null && mCircleZoneListResponse.getCirlces().size() > 0) {
                                        mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.circleZoneListPref, new Gson().toJson(mCircleZoneListResponse));
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(mCircleZoneListResponse);
                                        }
                                    } else {
                                        Error(context, "Data not found");
                                    }
                                } else {
                                    if (mCircleZoneListResponse.getMsg() != null) {
                                        Error(context, mCircleZoneListResponse.getMsg());
                                    } else {
                                        Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                                    }

                                }
                            } else {
                                Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<CircleZoneListResponse> call, Throwable t) {
                    try {

                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Error(context, e.getMessage());

        }
    }


    public void GetCallMeUserReq(final Activity mActivity, String mobNo, final CustomLoader loader, String deviceId, String deviceSerialNum, LoginResponse LoginDataResponse) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call = git.GetCallMeUserReq(new CallBackRequest(mobNo,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            BasicResponse mBasicResponse = response.body();
                            if (mBasicResponse != null) {
                                if (mBasicResponse.getStatuscode() == 1) {
                                    Successful(mActivity, mBasicResponse.getMsg());

                                } else if (response.body().getStatuscode() == -1) {

                                    Processing(mActivity, mBasicResponse.getMsg());
                                } else {
                                    Processing(mActivity, mBasicResponse.getMsg());
                                }

                            } else {

                                Error(mActivity, mActivity.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(mActivity, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(mActivity, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(mActivity, mActivity.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mActivity, mActivity.getResources().getString(R.string.some_thing_error));
        }

    }


    public void secureLogin(final Activity context, int loginTypeID, String mobile, final String password, final CustomLoader loader, String deviceId,
                            String deviceSerialNum, AppPreferences mAppPreferences) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<LoginResponse> call = git.secureLogin(new LoginRequest(loginTypeID, mobile, password,
                    ApplicationConstant.INSTANCE.Domain,
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "",
                    BuildConfig.VERSION_NAME, deviceSerialNum));

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (response.isSuccessful()) {
                            if (response != null && response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    mTempLoginDataResponse = response.body();
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.LoginPref, new Gson().toJson(response.body()));
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isLookUpFromAPIPref, response.body().isLookUpFromAPI());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isDTHInfoAutoCallPref, response.body().isDTHInfoCall());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isROfferPref, response.body().isRoffer());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isDTHInfoPref, response.body().isDTHInfo());

                                    openDefaultDashBorad(context, response.body(), mAppPreferences);


                                } else if (response.body().getStatuscode() == 2) {
                                    String mobNum = mobile;
                                    if (response.body().getMobileNo() != null && response.body().getMobileNo().length() == 10) {
                                        mobNum = response.body().getMobileNo();
                                    } else if (response.body().getData() != null && response.body().getData().getMobileNo() != null &&
                                            response.body().getData().getMobileNo().length() == 10) {
                                        mobNum = response.body().getData().getMobileNo();
                                    }
                                    openOtpDialog(context, 6, mobNum, new DialogOTPCallBack() {
                                        @Override
                                        public void onPositiveClick(String otpValue, TextView timerTv, TextView resendCodeTv, Dialog mDialog) {
                                            ValidateOTP(context, otpValue, response.body().getOtpSession(), 1, loader, mDialog, deviceId, deviceSerialNum, mAppPreferences);
                                        }

                                        @Override
                                        public void onResetCallback(String value, TextView timerTv, TextView resendCodeTv, Dialog mDialog) {
                                            ResendOTP(context, response.body().getOtpSession() != null ? response.body().getOtpSession() : "",
                                                    loader, deviceId, deviceSerialNum, object -> setTimer(timerTv, resendCodeTv));
                                        }
                                    });

                                } else if (response.body().getStatuscode() == 3) {

                                    openGAuthDialog(context, new DialogOTPCallBack() {
                                        @Override
                                        public void onPositiveClick(String otpValue, TextView timerTv, TextView resendCodeTv, Dialog mDialog) {
                                            ValidateTOTP(context, otpValue, response.body().getOtpSession(), 1, loader, mDialog, deviceId, deviceSerialNum, mAppPreferences);
                                        }

                                        @Override
                                        public void onResetCallback(String value, TextView timerTv, TextView resendCodeTv, Dialog mDialog) {
                                            /*ResendOTP(context, response.body().getOtpSession() != null ? response.body().getOtpSession() : "",
                                                    loader, deviceId, deviceSerialNum, object -> setTimer(timerTv, resendCodeTv));
                                        */
                                        }
                                    });

                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            } else {
                                Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
        }
    }

    public void ValidateOTP(final Activity context, String Otp, final String Otpsession, final int otpType,
                            final CustomLoader loader, Dialog mDialog, String deviceId, String deviceSerialNum, AppPreferences mAppPreferences) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<LoginResponse> call = git.ValidateOTP(new OtpRequest(Otp, Otpsession, otpType,
                    ApplicationConstant.INSTANCE.Domain,
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum));
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response != null && response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (mDialog != null && mDialog.isShowing()) {
                                        mDialog.dismiss();
                                    }
                                    mTempLoginDataResponse = response.body();
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.LoginPref, new Gson().toJson(response.body()));

                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isLookUpFromAPIPref, response.body().isLookUpFromAPI());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isDTHInfoAutoCallPref, response.body().isDTHInfoCall());

                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isROfferPref, response.body().isRoffer());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isDTHInfoPref, response.body().isDTHInfo());

                                    openDefaultDashBorad(context, response.body(), mAppPreferences);


                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }

                                }
                            } else {
                                Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }
    }

    public void ValidateTOTP(final Activity context, String Otp, final String Otpsession, final int otpType,
                             final CustomLoader loader, Dialog mDialog, String deviceId, String deviceSerialNum, AppPreferences mAppPreferences) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<LoginResponse> call = git.ValidateGAuthPIN(new OtpRequest(Otp, Otpsession, otpType,
                    ApplicationConstant.INSTANCE.Domain,
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum));
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response != null && response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (mDialog != null && mDialog.isShowing()) {
                                        mDialog.dismiss();
                                    }
                                    mTempLoginDataResponse = response.body();
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.LoginPref, new Gson().toJson(response.body()));

                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isLookUpFromAPIPref, response.body().isLookUpFromAPI());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isDTHInfoAutoCallPref, response.body().isDTHInfoCall());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isROfferPref, response.body().isRoffer());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isDTHInfoPref, response.body().isDTHInfo());

                                    openDefaultDashBorad(context, response.body(), mAppPreferences);


                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }

                                }
                            } else {
                                Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }
    }


    public void ResendOTP(final Activity context, String otpSession, final CustomLoader loader, String deviceId,
                          String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<BasicResponse> call = git.ResendOTP(new ResendOtpRequest(otpSession, ApplicationConstant.INSTANCE.Domain,
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum));
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response != null && response.body() != null) {

                                if (response.body().getStatuscode() == 2) {
                                    Successful(context, response.body().getMsg());
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    Error(context, response.body().getMsg());
                                }
                            } else {
                                Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }
    }


    public void openDefaultDashBorad(Activity context, LoginResponse response, AppPreferences mAppPreferences) {
        updateFcm(context, response, mAppPreferences);
        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
        context.finishAffinity();

        if (response.getData().getLoginTypeID() == 1) {
            if (response.getDashPreference() == 1) {
                Intent intent = new Intent(context, NetworkingDashboardActivity.class);
                intent.putExtra("FROM_LOGIN", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (response.getDashPreference() == 2) {
                Intent intent = new Intent(context, HomeDashActivity.class);
                intent.putExtra("FROM_LOGIN", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(context, ShoppingDashboardActivity.class);
                intent.putExtra("FROM_LOGIN", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } else {
            Intent intent = new Intent(context, EmpDashboardActivity.class);
            intent.putExtra("FROM_LOGIN", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public void ForgotPass(final Activity context, int loginTypeId, String userId, String deviceId, String deviceSerialNum, final CustomLoader loader) {
        try {


            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<BasicResponse> call = git.ForgetPassword(new LoginRequest(loginTypeId, userId, "",
                    ApplicationConstant.INSTANCE.Domain,
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum));

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    Successful(context, response.body().getMsg());
                                } else if (response.body().getStatuscode() == 2) {
                                    Failed(context, response.body().getMsg());
                                } else if (response.body().getStatuscode() == -1) {
                                    Failed(context, response.body().getMsg());
                                }
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            e.printStackTrace();
        }
    }


    public void updateFcm(final Context context, LoginResponse LoginDataResponse, AppPreferences mAppPreferences) {
        try {
            final String fcmId = mAppPreferences.getString(ApplicationConstant.INSTANCE.regFCMKeyPref);

            if (fcmId == null || fcmId.isEmpty()) {
                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new FCM registration token
                        String newToken = task.getResult();
                        if (newToken != null && !newToken.isEmpty()) {
                            mAppPreferences.set(ApplicationConstant.INSTANCE.regFCMKeyPref, newToken);
                            updateFcm(context, fcmId, mAppPreferences, LoginDataResponse);
                        } else {
                            updateFcm(context, fcmId, mAppPreferences, LoginDataResponse);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        updateFcm(context, fcmId, mAppPreferences, LoginDataResponse);
                    }
                });
            } else {
                updateFcm(context, fcmId, mAppPreferences, LoginDataResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateFcm(final Context context, String fcmId, AppPreferences mAppPreferences, LoginResponse LoginDataResponse) {
        try {


            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
           /* String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);*/
            Call<BasicResponse> call = git.UpdateFCMID(new UpdateFcmRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context, mAppPreferences),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context, mAppPreferences),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(), fcmId));

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {

                                } else if (response.body().getStatuscode() == 2) {

                                } else if (response.body().getStatuscode() == -1) {

                                }
                            }
                        } else {
                            /* apiErrorHandle(context, response.code(), response.message());*/
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetASCollectBank(final Activity context, final CustomLoader loader, LoginResponse LoginDataResponse,
                                 String deviceId, String deviceSerialNum, AppPreferences mAppPreferences,
                                 final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BankListResponse> call = git.GetASCollectBank(new BalanceRequest(LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME,
                    deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BankListResponse>() {
                @Override
                public void onResponse(Call<BankListResponse> call, final retrofit2.Response<BankListResponse> response) {
                    try {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getBankMasters() != null && response.body().getBankMasters().size() > 0) {
                                        mAppPreferences.set(ApplicationConstant.INSTANCE.fosBankListPref, new Gson().toJson(response.body()));
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    } else {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(ERROR_OTHER);
                                        }
                                        Error(context, "Bank not found");

                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }


                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BankListResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {

                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
            Error(context, e.getMessage());
            e.printStackTrace();
        }
    }

    public void MoveToBankReport(final Activity context, int status, String topRow, int oid,
                                 String fromDate, String toDate, String transactionID, String childMobileNo,
                                 final CustomLoader loader, LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.MoveToBankReport(new MoveToBankReportRequest(topRow, status, oid, fromDate, toDate, transactionID,
                    childMobileNo,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getLoginTypeID()));


            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else if (response.body().getStatuscode() == 3) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    Error(context, response.body().getMsg());
                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, e.getMessage());
                    }


                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            e.printStackTrace();
            Error(context, e.getMessage());
        }

    }

    public void GetArealist(final Activity context, final CustomLoader loader, LoginResponse LoginDataResponse
            , String deviceId, String deviceSerialNum, AppPreferences mAppPreferences, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppGetAMResponse> call = git.AppGetAM(new AppGetAMRequest(ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", deviceSerialNum, LoginDataResponse.getData().getSession(),
                    LoginDataResponse.getData().getSessionID(), BuildConfig.VERSION_NAME, LoginDataResponse.getData().getLoginTypeID(), LoginDataResponse.getData().getUserID()
            ));
            call.enqueue(new Callback<AppGetAMResponse>() {
                @Override
                public void onResponse(Call<AppGetAMResponse> call, final retrofit2.Response<AppGetAMResponse> response) {


                    try {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                mAppGetAMResponse = response.body();
                                if (mAppGetAMResponse.getStatuscode() == 1) {
                                    /*  if (response.body().getAreaMaster() != null && !response.body().getAreaMaster().isEmpty()) {*/

                                    if (mAppGetAMResponse != null && mAppGetAMResponse.getAreaMaster() != null && mAppGetAMResponse.getAreaMaster().size() > 0) {
                                        mAppPreferences.set(ApplicationConstant.INSTANCE.areaListPref, new Gson().toJson(response.body()));


                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    } else {
                                        Error(context, "Area not found");
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(ERROR_OTHER);
                                        }
                                    }


                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }

                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, e.getMessage());
                    }


                }

                @Override
                public void onFailure(Call<AppGetAMResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {

                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }


            });

        } catch (Exception e) {
            e.printStackTrace();
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
            Error(context, e.getMessage());
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }

        }
    }

    public void AccStmtReport(final Activity context, String mobile, String topRows, String fromDate, String toDate, String utype, final CustomLoader loader,
                              LoginResponse mLoginDataResponse, String deviceId, String deviceSerialNum,
                              int areaid, final ApiResponseCallBack mApiCallBack) {
        try {


            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<FosAccStmtAndCollReportResponse> call = git.GetASSumm(new FosAccStmtAndCollReportRequest(mobile, ApplicationConstant.INSTANCE.APP_ID,
                    fromDate, ""
                    , deviceSerialNum, mLoginDataResponse.getData().getSession(), mLoginDataResponse.getData().getSessionID(), toDate, topRows
                    , BuildConfig.VERSION_NAME, utype, areaid,
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(), deviceId
            ));
            call.enqueue(new Callback<FosAccStmtAndCollReportResponse>() {

                @Override
                public void onResponse(Call<FosAccStmtAndCollReportResponse> call, final retrofit2.Response<FosAccStmtAndCollReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {


                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    // setAscReportPref(context, new Gson().toJson( response.body() ) );
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<FosAccStmtAndCollReportResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {

                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }

            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
            Error(context, e.getMessage());
            e.printStackTrace();
        }
    }


    public void AccStmtAndCollFilterFosClick(final Activity context, String topRows,
                                             String fromDate, String toDate,
                                             String utype,
                                             final CustomLoader loader,
                                             LoginResponse mLoginDataResponse, String deviceId, String deviceSerialNum,
                                             int areaid, final ApiResponseCallBack mApiCallBack) {
        try {


            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<FosAccStmtAndCollReportResponse> call = git.AccStmtAndColl(new FosAccStmtAndCollReportRequest(ApplicationConstant.INSTANCE.APP_ID,
                    fromDate, ""
                    , deviceSerialNum, mLoginDataResponse.getData().getSession(), mLoginDataResponse.getData().getSessionID(), toDate, topRows
                    , BuildConfig.VERSION_NAME, utype, areaid,
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(), deviceId
            ));
            call.enqueue(new Callback<FosAccStmtAndCollReportResponse>() {

                @Override
                public void onResponse(Call<FosAccStmtAndCollReportResponse> call, final retrofit2.Response<FosAccStmtAndCollReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {


                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    // setAscReportPref(context, new Gson().toJson( response.body() ) );
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<FosAccStmtAndCollReportResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {

                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }

            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
            Error(context, e.getMessage());
            e.printStackTrace();
        }
    }

    public void ASPayCollect(Activity mContext, int uid, String remark, String amount,
                             final String userName, CustomLoader loader, final String collectionMode, String bankUtr, String bankName,
                             LoginResponse mLoginDataResponse, String deviceId, String deviceSerialNum,
                             final ApiCallBack mFundTransferCallBAck) {

        try {
            loader.show();

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.ASPayCollect(new ASPayCollectRequest(ApplicationConstant.INSTANCE.APP_ID
                    , deviceId, "", deviceSerialNum, mLoginDataResponse.getData().getSession()
                    , mLoginDataResponse.getData().getSessionID(), BuildConfig.VERSION_NAME
                    , mLoginDataResponse.getData().getLoginTypeID(), mLoginDataResponse.getData().getUserID()
                    , uid, collectionMode, amount, remark, bankName, bankUtr));

            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }


                        if (response.isSuccessful()) {
                            AppUserListResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {

                                    if (mFundTransferCallBAck != null) {
                                        mFundTransferCallBAck.onSucess(data);
                                    }
                                    Successful(mContext, data.getMsg().replace("{User}", userName));

                                } else {
                                    Error(mContext, data.getMsg().replace("{User}", userName));
                                }

                            } else {
                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }


                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }

                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(mContext, t);
                    } catch (IllegalStateException ise) {
                        Error(mContext, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
//                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage());
        }
    }


    public void Balancecheck(final Activity context, final CustomLoader loader, LoginResponse LoginDataResponse,
                             String deviceId, String deviceSerialNum, AppPreferences mAppPreferences,
                             final EKYCStepsDialog mEKYCStepsDialog, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<BalanceResponse> call = git.BalancecheckV2(new BalanceRequest(LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId != null && !deviceId.isEmpty() ? deviceId : getIMEI(context, mAppPreferences), "",
                    BuildConfig.VERSION_NAME,
                    deviceSerialNum != null && !deviceSerialNum.isEmpty() ? deviceSerialNum : getSerialNo(context, mAppPreferences),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BalanceResponse>() {
                @Override
                public void onResponse(Call<BalanceResponse> call, final retrofit2.Response<BalanceResponse> response) {
                    Log.d("bakkkk","Success");
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            mBalanceResponse = response.body();
                            if (mBalanceResponse != null) {
                                if (!isPassChangeDialogShowing) {
                                    if (mBalanceResponse.isPN()) {
                                        CustomAlertDialog customPassDialog = new CustomAlertDialog(context);
                                        customPassDialog.WarningWithSingleBtnCallBack(context.getResources().getString(R.string.pin_password_expired_msg), "Create", false, new CustomAlertDialog.DialogCallBack() {
                                            @Override
                                            public void onPositiveClick() {
                                                context.startActivity(new Intent(context, ChangePinPassActivity.class)
                                                        .putExtra("IsPin", true)
                                                        .putExtra("IsForcibly", true)
                                                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                            }

                                            @Override
                                            public void onNegativeClick() {

                                            }
                                        });

                                    } else if (mBalanceResponse.isP()) {
                                        CustomAlertDialog customPassDialog = new CustomAlertDialog(context);
                                        customPassDialog.WarningWithSingleBtnCallBack(context.getResources().getString(R.string.pin_password_expired_msg), "Create", false, new CustomAlertDialog.DialogCallBack() {
                                            @Override
                                            public void onPositiveClick() {
                                                context.startActivity(new Intent(context, ChangePinPassActivity.class)
                                                        .putExtra("IsPin", true)
                                                        .putExtra("IsForcibly", true)
                                                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                            }

                                            @Override
                                            public void onNegativeClick() {

                                            }
                                        });

                                    } else if (mBalanceResponse.isPasswordExpired()) {
                                        CustomAlertDialog customPassDialog = new CustomAlertDialog(context);
                                        customPassDialog.WarningWithSingleBtnCallBack(context.getResources().getString(R.string.password_expired_msg), "Change", false, new CustomAlertDialog.DialogCallBack() {
                                            @Override
                                            public void onPositiveClick() {
                                                context.startActivity(new Intent(context, ChangePinPassActivity.class)
                                                        .putExtra("IsPin", false)
                                                        .putExtra("IsForcibly", true)
                                                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                            }

                                            @Override
                                            public void onNegativeClick() {

                                            }
                                        });

                                    } else if (mBalanceResponse.isEKYCForced() && !mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isEKYCComplete)) {
                                        if (mEKYCStepsDialog == null) {
                                            eKYCStepsDialog = new EKYCStepsDialog(context, LoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);
                                        } else {
                                            eKYCStepsDialog = mEKYCStepsDialog;
                                        }
                                        eKYCStepsDialog.GetKycDetails(null);
                                    } else if (!isSocialEmailVerfiedChecked) {
                                        if (!mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isEmailVerifiedPref) ||
                                                !mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isSocialLinkSavedPref)) {
                                            long time = hourDifference(mAppPreferences.getLong(ApplicationConstant.INSTANCE.SocialorEmailDialogTimePref), System.currentTimeMillis());
                                            if (time >= 8) {
                                                CheckFlagsEmail(context, loader, deviceId, deviceSerialNum, LoginDataResponse, mAppPreferences);
                                            }
                                        }
                                        isSocialEmailVerfiedChecked = true;
                                    }

                                }

                                if (mBalanceResponse.getStatuscode() == 1) {


                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isUpi, mBalanceResponse.isUPI());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isPaymentGatway, mBalanceResponse.isPaymentGatway());

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mBalanceResponse);
                                    }
                                   /* if (mBalanceResponse.getBalanceData() != null&& mBalanceResponse.getBalanceData().size() > 0) {
                                        if (LoginDataResponse.getData().getRoleID() == 3 || LoginDataResponse.getData().getRoleID() == 2) {
                                            if (mBalanceResponse.getBalanceData().getIsPacakgeBalance()) {
                                                mBalanceResponse.getBalanceData().setIsPacakgeBalance(false);
                                                mBalanceResponse.getBalanceData().setIsPacakgeBalanceFund(false);
                                            }
                                        }
                                    }*/

                                    mAppPreferences.set(ApplicationConstant.INSTANCE.balancePref, new Gson().toJson(mBalanceResponse));
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isLookUpFromAPIPref, mBalanceResponse.isLookUpFromAPI());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isDTHInfoAutoCallPref, mBalanceResponse.isDTHInfoCall());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isFlatCommissionPref, mBalanceResponse.isFlatCommission());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isROfferPref, mBalanceResponse.isRoffer());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isDTHInfoPref, mBalanceResponse.isDTHInfo());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isShowPDFPlanPref, mBalanceResponse.isShowPDFPlan());
                                } else {
                                    if (!mBalanceResponse.isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, mBalanceResponse.getMsg());
                                    }
                                }
                            }

                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Log.d("bakkkk",e+"");
                    }

                }

                @Override
                public void onFailure(Call<BalanceResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
        }
    }


    public void GetAccountOpenlist(final Activity context, int opTypeId, final CustomLoader loader,
                                   LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, AppPreferences mAppPreferences,
                                   final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<AccountOpenListResponse> call = git.GetAccountOpeningList(new AccountOpenListRequest(opTypeId,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AccountOpenListResponse>() {
                @Override
                public void onResponse(Call<AccountOpenListResponse> call, final retrofit2.Response<AccountOpenListResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getAccountOpeningDeatils() != null &&
                                            response.body().getAccountOpeningDeatils().size() > 0) {
                                        mAppPreferences.set(ApplicationConstant.INSTANCE.accountOpenListPref + "_" + opTypeId, new Gson().toJson(response.body()));
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    } else {
                                        Error(context, "Record not found");
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(ERROR_OTHER);
                                        }
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }

                }

                @Override
                public void onFailure(Call<AccountOpenListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
        }
    }


    public void BalancecheckNew(final Activity context, CustomLoader loader, CustomAlertDialog customAlertDialog, int INTENT_PASSWORD_EXPIRE,
                                String deviceId, String deviceSerialNum, AppPreferences mAppPreferences, LoginResponse LoginDataResponse,
                                TextToSpeech tts, final EKYCStepsDialog mEKYCStepsDialog, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BalanceResponse> call = git.BalancecheckV2(new BalanceRequest(LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, deviceId, "",
                    BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BalanceResponse>() {
                @Override
                public void onResponse(Call<BalanceResponse> call, final retrofit2.Response<BalanceResponse> response) {

                    if (response.isSuccessful()) {
                        try {
                            mBalanceResponse = response.body();
                            if (mBalanceResponse != null) {
                                if (!isPassChangeDialogShowing) {
                                    if (mBalanceResponse.isPN() || mBalanceResponse.isP()) {

                                        if (customAlertDialog != null) {
                                            customAlertDialog.WarningWithSingleBtnCallBack(context.getResources().getString(R.string.pin_password_expired_msg), "Create", false, new CustomAlertDialog.DialogCallBack() {
                                                @Override
                                                public void onPositiveClick() {
                                                    context.startActivityForResult(new Intent(context, ChangePinPassActivity.class)
                                                            .putExtra("IsPin", true)
                                                            .putExtra("IsForcibly", true)
                                                            .putExtra("Intent_Result", INTENT_PASSWORD_EXPIRE)
                                                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_PASSWORD_EXPIRE);
                                                }

                                                @Override
                                                public void onNegativeClick() {

                                                }
                                            });
                                        }

                                    } else if (mBalanceResponse.isPasswordExpired()) {

                                        if (customAlertDialog != null) {

                                            customAlertDialog.WarningWithSingleBtnCallBack(context.getResources().getString(R.string.password_expired_msg), "Change", false, new CustomAlertDialog.DialogCallBack() {
                                                @Override
                                                public void onPositiveClick() {
                                                    context.startActivityForResult(new Intent(context, ChangePinPassActivity.class)
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
                                    } else if (mBalanceResponse.isEKYCForced() && !mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isEKYCComplete)) {
                                        if (mEKYCStepsDialog == null) {
                                            eKYCStepsDialog = new EKYCStepsDialog(context, LoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);
                                        } else {
                                            eKYCStepsDialog = mEKYCStepsDialog;
                                        }
                                        eKYCStepsDialog.GetKycDetails(null);
                                    } else if (!isSocialEmailVerfiedChecked) {
                                        if (!mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isEmailVerifiedPref) ||
                                                !mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isSocialLinkSavedPref)) {
                                            long time = hourDifference(mAppPreferences.getLong(ApplicationConstant.INSTANCE.SocialorEmailDialogTimePref), System.currentTimeMillis());
                                            if (time >= 8) {
                                                CheckFlagsEmail(context, loader, deviceId, deviceSerialNum, LoginDataResponse, mAppPreferences);
                                            }
                                        }
                                        isSocialEmailVerfiedChecked = true;
                                    }
                                }

                                if (mBalanceResponse.getStatuscode() == 1) {
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isUpi, mBalanceResponse.isUPI());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isPaymentGatway, mBalanceResponse.isPaymentGatway());

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mBalanceResponse);
                                    }
                                    /*if (mBalanceResponse.getBalanceData() != null && mBalanceResponse.getBalanceData().size() > 0) {
                                        if (LoginDataResponse.getData().getRoleID() == 3 || LoginDataResponse.getData().getRoleID() == 2) {
                                            if (mBalanceResponse.getBalanceData().getIsPacakgeBalance()) {
                                                mBalanceResponse.getBalanceData().setIsPacakgeBalance(false);
                                                mBalanceResponse.getBalanceData().setIsPacakgeBalanceFund(false);
                                            }
                                        }
                                    }*/
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.balancePref, new Gson().toJson(mBalanceResponse));
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isLookUpFromAPIPref, mBalanceResponse.isLookUpFromAPI());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isDTHInfoAutoCallPref, mBalanceResponse.isDTHInfoCall());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isFlatCommissionPref, mBalanceResponse.isFlatCommission());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isROfferPref, mBalanceResponse.isRoffer());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isDTHInfoPref, mBalanceResponse.isDTHInfo());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isShowPDFPlanPref, mBalanceResponse.isShowPDFPlan());

                                    if (mBalanceResponse.getBalanceData() != null && mBalanceResponse.getBalanceData().size() > 0) {
                                        long time = hourDifference(mAppPreferences.getLong(ApplicationConstant.INSTANCE.balanceLowTimePref), System.currentTimeMillis());
                                        if (mBalanceResponse.isLowBalance() && time >= 1) {
                                            if (customAlertDialog != null) {
                                                if (customAlertDialog.returnDialog() != null && customAlertDialog.returnDialog().isShowing()) {
                                                } else {
                                                    mAppPreferences.set(ApplicationConstant.INSTANCE.balanceLowTimePref, System.currentTimeMillis());

                                                    if (tts != null) {
                                                        tts.speak("Your Balance is low. Your current balance is " + Utility.INSTANCE.formatedAmountWithRupees(mBalanceResponse.getBalanceData().get(0).getBalance() + ""), TextToSpeech.QUEUE_FLUSH, null);
                                                    }
                                                    String msg = "Your Balance is low.<br>Current Balance - " + Utility.INSTANCE.formatedAmountWithRupees(mBalanceResponse.getBalanceData().get(0).getBalance() + "");
                                                    customAlertDialog.WarningWithCallBack(msg, "Low Balance", "Fund Request", true, new CustomAlertDialog.DialogCallBack() {
                                                        @Override
                                                        public void onPositiveClick() {
                                                            Intent i = new Intent(context, PaymentRequest.class);
                                                            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                            context.startActivity(i);
                                                        }

                                                        @Override
                                                        public void onNegativeClick() {

                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }

                                } else {
                                    if (!mBalanceResponse.isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        //Error(context, mBalanceResponse.getMsg() + "");
                                    }
                                }
                            }

                        } catch (Exception e) {
                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }


                }

                @Override
                public void onFailure(Call<BalanceResponse> call, Throwable t) {


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GetPopupAfterLogin(final Activity context, String deviceId, String deviceSerialNum, LoginResponse LoginDataResponse) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call = git.GetPopupAfterLogin(new BalanceRequest(LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {


                    if (response.isSuccessful()) {
                        try {
                            if (response != null && response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getPopup() != null && !response.body().getPopup().isEmpty()) {
                                        RequestOptions requestOptions = new RequestOptions();
                                        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

                                        Glide.with(context)
                                                .asBitmap()
                                                .load(ApplicationConstant.INSTANCE.baseUrl + response.body().getPopup())
                                                .apply(requestOptions)
                                                .into(new SimpleTarget<Bitmap>() {
                                                    @Override
                                                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                                        if (resource != null) {
                                                            showPopupDialog(context, resource);
                                                        }
                                                    }
                                                });
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    t.getMessage();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long hourDifference(long millisFirst, long millisSecond) {
        return TimeUnit.MILLISECONDS.toHours(millisSecond - millisFirst);
    }


    public void GetBanklist(final Activity context, final CustomLoader loader, LoginResponse LoginDataResponse, AppPreferences mAppPreferences,
                            String deviceId, String deviceSerialNum, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BankListResponse> call = git.GetBankList(new BalanceRequest(LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME,
                    deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BankListResponse>() {
                @Override
                public void onResponse(Call<BankListResponse> call, final retrofit2.Response<BankListResponse> response) {

                    try {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getBankMasters() != null && response.body().getBankMasters().size() > 0) {
                                        mAppPreferences.set(ApplicationConstant.INSTANCE.bankListPref, new Gson().toJson(response.body()));

                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    } else {
                                        Error(context, "Bank not found");
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(ERROR_OTHER);
                                        }
                                    }

                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }


                }

                @Override
                public void onFailure(Call<BankListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Error(context, e.getMessage());
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
        }
    }


    public void GetAEPSBanklist(final Activity context, final CustomLoader loader, LoginResponse LoginDataResponse, AppPreferences mAppPreferences,
                                String deviceId, String deviceSerialNum, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BankListResponse> call = git.GetAEPSBanks(new BalanceRequest(LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME,
                    deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BankListResponse>() {
                @Override
                public void onResponse(Call<BankListResponse> call, final retrofit2.Response<BankListResponse> response) {

                    try {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getBankMasters() != null && response.body().getBankMasters().size() > 0) {
                                        mAppPreferences.set(ApplicationConstant.INSTANCE.bankListPref, new Gson().toJson(response.body()));

                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    } else {
                                        Error(context, "Bank not found");
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(ERROR_OTHER);
                                        }
                                    }

                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }


                }

                @Override
                public void onFailure(Call<BankListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Error(context, e.getMessage());
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
        }
    }

    public void GetBalanceAEPS(final Activity context, String pidDataXML, String lati, String longi, PidData mPidData, String aadhar, int bankIIn, int interfaceType,
                               final CustomLoader loader, LoginResponse LoginDataResponse, String deviceId, String serialNum, final ApiCallBack mApiCallBack) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetAEPSResponse> call = git.GetBalanceAEPS(new GetAepsRequest(pidDataXML, lati, longi, mPidData, aadhar, interfaceType, bankIIn,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, serialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetAEPSResponse>() {
                @Override
                public void onResponse(Call<GetAEPSResponse> call, final retrofit2.Response<GetAEPSResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetAEPSResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GenerateDepositOTP(final Activity context, String lati, String longi, String aadhar, String amount, int bankIIn, int interfaceType,
                                   final CustomLoader loader, LoginResponse LoginDataResponse, String deviceId, String serialNum, final ApiCallBack mApiCallBack) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GenerateDepositOTPResponse> call = git.GenerateDepositOTP(new GenerateDepositOTPRequest(lati, longi, aadhar, amount, interfaceType, bankIIn,
                    LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, serialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GenerateDepositOTPResponse>() {
                @Override
                public void onResponse(Call<GenerateDepositOTPResponse> call, final retrofit2.Response<GenerateDepositOTPResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GenerateDepositOTPResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void VerifyDepositOTP(final Activity context, String lati, String longi, String reff1, String reff2, String reff3, String otp, String aadhar, String amount, int bankIIn, int interfaceType,
                                 final CustomLoader loader, LoginResponse LoginDataResponse, String deviceId, String serialNum, final ApiCallBack mApiCallBack) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GenerateDepositOTPResponse> call = git.VerifyDepositOTP(new GenerateDepositOTPRequest(lati, longi,
                    reff1, reff2, reff3, otp, aadhar, amount, interfaceType, bankIIn,
                    LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, serialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GenerateDepositOTPResponse>() {
                @Override
                public void onResponse(Call<GenerateDepositOTPResponse> call, final retrofit2.Response<GenerateDepositOTPResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GenerateDepositOTPResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DepositNow(final Activity context, String lati, String longi, String reff1, String reff2, String reff3, String otp, String aadhar, String amount, int bankIIn, int interfaceType,
                           final CustomLoader loader, LoginResponse LoginDataResponse, String deviceId, String serialNum, final ApiCallBack mApiCallBack) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GenerateDepositOTPResponse> call = git.DepositNow(new GenerateDepositOTPRequest(lati, longi,
                    reff1, reff2, reff3, otp, aadhar, amount, interfaceType, bankIIn,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, serialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GenerateDepositOTPResponse>() {
                @Override
                public void onResponse(Call<GenerateDepositOTPResponse> call, final retrofit2.Response<GenerateDepositOTPResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GenerateDepositOTPResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GetMINIStatementAEPS(final Activity context, String pidDataXML, String lati, String longi, PidData mPidData, String aadhar, int bankIIn, String bankName, int interfaceType,
                                     final CustomLoader loader, LoginResponse LoginDataResponse, String deviceId, String serialNum, final ApiCallBack mApiCallBack) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetAEPSResponse> call = git.BankMiniStatement(new GetAepsRequest(pidDataXML, lati, longi, mPidData, aadhar, interfaceType, bankIIn, bankName,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, serialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetAEPSResponse>() {
                @Override
                public void onResponse(Call<GetAEPSResponse> call, final retrofit2.Response<GetAEPSResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getStatements() != null && response.body().getStatements().size() > 0) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    } else {
                                        Error(context, "Mini Statements not available.");
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetAEPSResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetWithdrawlAEPS(final Activity context, String pidDataXML, String lati, String longi, PidData mPidData, String aadhar, String amount, int bankIIn, int interfaceType,
                                 final CustomLoader loader, LoginResponse LoginDataResponse, String deviceId, String serialNum, final ApiCallBack mApiCallBack) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetAEPSResponse> call = git.AEPSWithdrawal(new GetAepsRequest(pidDataXML, lati, longi, mPidData, aadhar, amount, interfaceType, bankIIn,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, serialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetAEPSResponse>() {
                @Override
                public void onResponse(Call<GetAEPSResponse> call, final retrofit2.Response<GetAEPSResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetAEPSResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetAadharPay(final Activity context, String pidDataXML, String lati, String longi, PidData mPidData, String aadhar, String amount, int bankIIn, int interfaceType,
                             final CustomLoader loader, LoginResponse LoginDataResponse, String deviceId, String serialNum, final ApiCallBack mApiCallBack) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetAEPSResponse> call = git.Aadharpay(new GetAepsRequest(pidDataXML, lati, longi, mPidData, aadhar, amount, interfaceType, bankIIn,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, serialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetAEPSResponse>() {
                @Override
                public void onResponse(Call<GetAEPSResponse> call, final retrofit2.Response<GetAEPSResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetAEPSResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void FundRequestTo(final Activity context, final CustomLoader loader, LoginResponse LoginDataResponse,
                              String deviceId, String deviceSerialNum, AppPreferences mAppPreferences, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<FundreqToResponse> call = git.FundRequestTo(new BalanceRequest(LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<FundreqToResponse>() {
                @Override
                public void onResponse(Call<FundreqToResponse> call, final retrofit2.Response<FundreqToResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatuscode() != null) {

                                if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.FundreqTo, new Gson().toJson(response.body()));
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                               /* FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage(new Gson().toJson(response.body()).toString(), "SelectedRole");
                                GlobalBus.getBus().post(activityActivityMessage);*/
                                } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                    if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<FundreqToResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
        }
    }


    public void fundTransferApi(Activity mContext, boolean isMarkCredit, String securityKey, int paymentId, boolean oType, int uid, String remark, int walletType, String amount,
                                final String userName, final AlertDialog alertDialogFundTransfer, CustomLoader loader,
                                LoginResponse mLoginDataResponse, String deviceId, String deviceSerialNum,
                                final ApiCallBack mFundTransferCallBAck) {
        try {
            loader.show();

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.AppFundTransfer(new FundTransferRequest(isMarkCredit, securityKey, oType, uid, remark, walletType,
                    paymentId, amount, mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            AppUserListResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    isReadyToUpdateBalance = true;
                                    if (mFundTransferCallBAck != null) {
                                        mFundTransferCallBAck.onSucess(data);
                                    }
                                    alertDialogFundTransfer.dismiss();
                                    Successful(mContext, data.getMsg().replace("{User}", userName));
                                } else if (response.body().getStatuscode() == -1) {
                                    Error(mContext, data.getMsg().replace("{User}", userName));
                                } else if (response.body().getStatuscode() == 0) {

                                    Error(mContext, data.getMsg().replace("{User}", userName));
                                } else {
                                    Error(mContext, data.getMsg().replace("{User}", userName));
                                }

                            } else {
                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(mContext, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, ise.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage());
        }

    }


    public void fundRejectApi(Activity mContext, boolean isMarkCredit, String securityKey, int paymentId, int uid, String remark, String amount, final String userName,
                              final AlertDialog alertDialogFundTransfer, CustomLoader loader,
                              LoginResponse mLoginDataResponse, String deviceId, String deviceSerialNum,
                              final ApiCallBack mFundTransferCallBAck) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.AppFundReject(new FundTransferRequest(isMarkCredit, securityKey, false, uid, remark, 1,
                    paymentId, amount, mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME,
                    deviceSerialNum, mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            AppUserListResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    isReadyToUpdateBalance = true;
                                    if (mFundTransferCallBAck != null) {
                                        mFundTransferCallBAck.onSucess(data);
                                    }
                                    alertDialogFundTransfer.dismiss();
                                    ApiFintechUtilMethods.INSTANCE.Successful(mContext, data.getMsg().replace("{User}", userName));
                                } else if (response.body().getStatuscode() == -1) {

                                    ApiFintechUtilMethods.INSTANCE.Error(mContext, data.getMsg().replace("{User}", userName));
                                } else if (response.body().getStatuscode() == 0) {
                                    ApiFintechUtilMethods.INSTANCE.Error(mContext, data.getMsg().replace("{User}", userName));
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(mContext, data.getMsg().replace("{User}", userName));
                                }

                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }


                        ApiFintechUtilMethods.INSTANCE.apiFailureError(mContext, t);

                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
        }

    }


    public void CheckFlagsEmail(Activity mContext, CustomLoader loader, String deviceId, String deviceSerialNum,
                                LoginResponse LoginDataResponse, AppPreferences mAppPreferences) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call = git.CheckFlagsEmail(new BasicRequest(LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        try {

                            BasicResponse mBasicResponse = response.body();
                            if (mBasicResponse != null) {
                                if (mBasicResponse.getStatuscode() == 1) {
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.SocialorEmailDialogTimePref, System.currentTimeMillis());
                                    if (!mBasicResponse.isEmailVerified() || !mBasicResponse.isSocialAlert()) {
                                        emailVerifySocialLinkDialog(mContext, mBasicResponse.isEmailVerified(), mBasicResponse.isSocialAlert(),
                                                loader, deviceId, deviceSerialNum, LoginDataResponse, mAppPreferences);
                                    } else {
                                        mAppPreferences.set(ApplicationConstant.INSTANCE.isEmailVerifiedPref, true);
                                        mAppPreferences.set(ApplicationConstant.INSTANCE.isSocialLinkSavedPref, true);
                                    }
                                } else {
                                    if (response.body().isVersionValid()) {
                                        if (mBasicResponse.getStatuscode() == 0) {
                                            mAppPreferences.set(ApplicationConstant.INSTANCE.isEmailVerifiedPref, true);
                                            mAppPreferences.set(ApplicationConstant.INSTANCE.isSocialLinkSavedPref, true);
                                            mAppPreferences.set(ApplicationConstant.INSTANCE.SocialorEmailDialogTimePref, System.currentTimeMillis());
                                        }
                                    }

                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        apiErrorHandle(mContext, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    t.printStackTrace();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    public void VerifyEmail(Activity mContext, CustomLoader loader, String deviceId, String deviceSerialNum,
                            LoginResponse LoginDataResponse, AppPreferences mAppPreferences, ApiCallBack mApiCallBack) {
        try {
            if (loader != null) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call = git.SendEmailVerification(new BasicRequest(LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            BasicResponse mBasicResponse = response.body();
                            if (mBasicResponse != null) {
                                if (mBasicResponse.getStatuscode() == 1) {
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.SocialorEmailDialogTimePref, System.currentTimeMillis());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isEmailVerifiedPref, true);
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mBasicResponse);
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(mContext);
                                    } else {
                                        Error(mContext, mBasicResponse.getMsg());
                                    }

                                }

                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage());
                    }


                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    t.printStackTrace();

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(mContext, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(mContext, ise.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage());
        }

    }

    public void submitSocialDetails(Activity mContext, String whatsappNum, String telegramNum, String hangoutId, CustomLoader loader, String deviceId, String deviceSerialNum,
                                    LoginResponse LoginDataResponse, AppPreferences mAppPreferences, ApiCallBack mApiCallBack) {
        try {
            if (loader != null) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call = git.SaveSocialAlertSetting(new SubmitSocialDetailsRequest(whatsappNum, telegramNum, hangoutId,
                    LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            BasicResponse mBasicResponse = response.body();
                            if (mBasicResponse != null) {
                                if (mBasicResponse.getStatuscode() == 1) {
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.SocialorEmailDialogTimePref, System.currentTimeMillis());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.isSocialLinkSavedPref, true);
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mBasicResponse);
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(mContext);
                                    } else {
                                        Error(mContext, mBasicResponse.getMsg());
                                    }

                                }

                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    t.printStackTrace();

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(mContext, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(mContext, ise.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage());
        }

    }


    public void GeUserCommissionRate(Activity mContext, int uid, CustomLoader loader, String deviceId, String deviceSerialNum, LoginResponse LoginDataResponse, ApiCallBack mApiCallBack) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call = git.GeUserCommissionRate(new UpdateKycRequest(uid,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            BasicResponse mBasicResponse = response.body();
                            if (mBasicResponse != null) {
                                if (mBasicResponse.getStatuscode() == 1) {
                                    //UtilMethods.INSTANCE.Successful(UpdateProfileActivity.this, data.getMsg() + "");
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mBasicResponse);
                                    }
                                } else if (response.body().getStatuscode() == -1) {

                                    Error(mContext, mBasicResponse.getMsg());
                                } else if (response.body().getStatuscode() == 0) {

                                    Error(mContext, mBasicResponse.getMsg());
                                } else {

                                    Error(mContext, mBasicResponse.getMsg());
                                }

                            } else {

                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(mContext, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
        }

    }

    public void GetVADetails(final Activity context, final CustomLoader loader, LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum,
                             final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetVAResponse> call = git.GetVADetail(new BasicRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetVAResponse>() {

                @Override
                public void onResponse(Call<GetVAResponse> call, retrofit2.Response<GetVAResponse> response) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    if (response.isSuccessful()) {
                        GetVAResponse apiData = response.body();
                        if (apiData != null) {
                            if (apiData.getStatuscode() == 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(apiData.getUserQRInfo());
                                }

//
                            } else if (apiData.getStatuscode() == -1) {
                                if (!apiData.getVersionValid()) {
                                    versionDialog(context);
                                } else {
                                    Error(context, apiData.getMsg());
                                }
                            }

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetVAResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void GetSmartCollectDetail(final Activity context, final CustomLoader loader, LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum,
                                      final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            //GetVADetail Replace with GetSmartCollectDetail
            Call<GetVAResponse> call = git.GetSmartCollectDetail(new BasicRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetVAResponse>() {

                @Override
                public void onResponse(Call<GetVAResponse> call, retrofit2.Response<GetVAResponse> response) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    if (response.isSuccessful()) {
                        GetVAResponse apiData = response.body();
                        if (apiData != null) {
                            if (apiData.getStatuscode() == 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(apiData.getUserQRInfo());
                                }


                            } else {
                                if (!apiData.getVersionValid()) {
                                    versionDialog(context);
                                } else {
                                    Error(context, apiData.getMsg());
                                }
                            }

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetVAResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
        }

    }


    public void IntiateUPI(final Activity context, String walletID, String oid, String amount, String upiid, final CustomLoader loader,
                           LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<InitiateUpiResponse> call = git.IntiateUPI(new IntiateUPIRequest(walletID, oid, amount, upiid,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<InitiateUpiResponse>() {

                @Override
                public void onResponse(Call<InitiateUpiResponse> call, retrofit2.Response<InitiateUpiResponse> response) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    if (response.isSuccessful()) {
                        InitiateUpiResponse apiData = response.body();
                        if (apiData != null) {
                            if (apiData.getStatuscode() == 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(apiData);
                                }

//
                            } else if (apiData.getStatuscode() == -1) {
                                if (!apiData.isVersionValid()) {
                                    versionDialog(context);
                                } else {
                                    Error(context, apiData.getMsg());
                                }
                            }

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<InitiateUpiResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

        public void UPIPaymentUpdate(final Activity context, String tid, String bankStatus, final CustomLoader loader,
                                 LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<InitiateUpiResponse> call = git.UPIPaymentUpdate(new UpdateUPIRequest(tid, bankStatus,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<InitiateUpiResponse>() {

                @Override
                public void onResponse(Call<InitiateUpiResponse> call, retrofit2.Response<InitiateUpiResponse> response) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    if (response.isSuccessful()) {
                        InitiateUpiResponse apiData = response.body();
                        if (apiData != null) {
                            if (apiData.getStatuscode() == 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(apiData);
                                }

//
                            } else if (apiData.getStatuscode() == -1) {
                                if (!apiData.isVersionValid()) {
                                    // versionDialog(context);
                                } else {
                                    //Error(context, apiData.getMsg() + "");
                                }
                            }

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<InitiateUpiResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    try {

                    } catch (IllegalStateException ise) {


                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void CashFreeStatusCheck(Activity  context,String tid, final CustomLoader loader,
                                    final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<InitiateUpiResponse> call = git.CashFreeStatusCheck(tid);
            call.enqueue(new Callback<InitiateUpiResponse>() {

                @Override
                public void onResponse(Call<InitiateUpiResponse> call, retrofit2.Response<InitiateUpiResponse> response) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    if (response.isSuccessful()) {
                        InitiateUpiResponse apiData = response.body();
                        if (apiData != null) {
                            if (apiData.getStatuscode() == 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(apiData);
                                }

//
                            } else if (apiData.getStatuscode() == -1) {
                                if (!apiData.isVersionValid()) {
                                    // versionDialog(context);
                                } else {
                                    //Error(context, apiData.getMsg() + "");
                                }
                            }

                        }
                    } else {
                        //  apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<InitiateUpiResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    try {

                    } catch (IllegalStateException ise) {


                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void GetBankAndPaymentMode(final Activity context, String Parentid, final CustomLoader loader,
                                      String deviceId, String deviceSerialNum, LoginResponse LoginDataResponse, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            BalanceRequest mBalanceRequest = new BalanceRequest(Parentid, "",
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession());
            String str = new Gson().toJson(mBalanceRequest);
            Call<GetBankAndPaymentModeResponse> call = git.GetBankAndPaymentMode(mBalanceRequest);
            call.enqueue(new Callback<GetBankAndPaymentModeResponse>() {
                @Override
                public void onResponse(Call<GetBankAndPaymentModeResponse> call, final retrofit2.Response<GetBankAndPaymentModeResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatuscode() != null) {
                                if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                    // setFundreqToList(context, new Gson().toJson(response.body()).toString());
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                /*FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage(new Gson().toJson(response.body()).toString(), "SelectedBank");
                                GlobalBus.getBus().post(activityActivityMessage);*/
                                } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetBankAndPaymentModeResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {


                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
        }
    }


    public void GetAvailablePackage(final Activity context, final CustomLoader loader,
                                    LoginResponse LoginDataResponse, String deviceId, String deviceSerialNo,
                                    AppPreferences mAppPreferences, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GetAvailablePackageResponse> call = git.GetAvailablePackages(new BasicRequest(LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNo, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));


            call.enqueue(new Callback<GetAvailablePackageResponse>() {
                @Override
                public void onResponse(Call<GetAvailablePackageResponse> call, final retrofit2.Response<GetAvailablePackageResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getPackageDetail() != null && response.body().getPackageDetail().size() > 0) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    } else {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(ERROR_OTHER);
                                        }
                                        Error(context, context.getResources().getString(R.string.data_not_found));
                                    }

                                } else if (response.body().getStatuscode() == -1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetAvailablePackageResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }

                }
            });

        } catch (Exception e) {
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
        }
    }

    public void GetAdditionalService(final Activity context, int opTypeId, final CustomLoader loader,
                                     LoginResponse LoginDataResponse, AppPreferences mAppPreferences,
                                     String deviceId, String deviceSerialNumber, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GetAvailablePackageResponse> call = git.GetAdditionalService(new GetAdditionalServiceRequest(LoginDataResponse.getData().getOutletID(), opTypeId, LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNumber, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));


            call.enqueue(new Callback<GetAvailablePackageResponse>() {
                @Override
                public void onResponse(Call<GetAvailablePackageResponse> call, final retrofit2.Response<GetAvailablePackageResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getPackageDetail() != null && response.body().getPackageDetail().size() > 0) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    } else {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(ERROR_OTHER);
                                        }
                                        Error(context, context.getResources().getString(R.string.data_not_found));
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetAvailablePackageResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }

                }
            });

        } catch (Exception e) {
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
        }
    }

    public void UpgradePackage(final Activity context, String uid, int packageId, final CustomLoader loader,
                               LoginResponse LoginDataResponse, String deviceId, String deviceSerialNumber, AppPreferences mAppPreferences, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call = git.UpgradePackage(new UpgradePackageRequest(uid, packageId, LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNumber, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));


            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }


                                } else if (response.body().getStatuscode() == -1) {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                } else {
                                    Error(context, response.body().getMsg());
                                }
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }
    }

    public void UpgradeService(final Activity context, int oid, int opTypeId, final CustomLoader loader,
                               LoginResponse LoginDataResponse, String deviceId, String deviceSerialNumber, AppPreferences mAppPreferences, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call = git.ActivateAdditionalService(new UpgradePackageRequest(opTypeId, oid, LoginDataResponse.getData().getOutletID(),
                    LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNumber, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));


            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }


                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }
    }


    public void Recharge(final Activity context, int INTENT_RECHARGE_SLIP, boolean isReal, int Opid, final String opName, final boolean isRecharge,
                         String AccountNo, String Amount, String o1, String o2, String o3, String o4, String customerNo,
                         String refID, int fetchBillID, String GeoCode, String securityKey, LoginResponse LoginDataResponse,
                         final CustomLoader loader, boolean isBBPS, String deviceId, String deviceSerialNum,
                         AppPreferences mAppPreferences,boolean isFromAddMoneyDialog,String PaymentRefid, final ApiCallBackMultiple mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeResponse> call = git.Recharge(new RechargeRequest(isReal,
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getLoginTypeID(), Opid,
                    AccountNo, Amount, o1, o2, o3, o4, customerNo, refID, fetchBillID, GeoCode,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getSession(), securityKey)
            );
            call.enqueue(new Callback<RechargeResponse>() {
                @Override
                public void onResponse(Call<RechargeResponse> call, final retrofit2.Response<RechargeResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss:SSS aa");
                                String dateStr = inputFormat.format(new Date());
                                if (response.body().getStatuscode() == 1) {
                                    isReadyToUpdateBalance = true;
                                    openRechargeSlip(context, isBBPS, Amount, AccountNo, response.body().getLiveID(), opName,
                                            response.body().getTransactionID(), "PENDING", Opid, response.body().getStatuscode(),
                                            "", "", "", false, 0);
//                                    if (mApiCallBack != null) {
//                                        mApiCallBack.onSucessResponse(response.body());
//                                    }
                                } else if (response.body().getStatuscode() == 2) {
                                    isReadyToUpdateBalance = true;
                                    if (!isFromAddMoneyDialog) {
                                        openRechargeSlip(context, isBBPS, Amount, AccountNo, response.body().getLiveID(), opName,
                                                response.body().getTransactionID(), "SUCCESS", Opid, response.body().getStatuscode(),
                                                "", "", "", false, 0);
                                    }
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucessResponse(response.body(), AccountNo, Amount, Opid, opName);
                                    }
                                } else if (response.body().getStatuscode() == 3) {
                                    if (response.body().getMsg().contains("(NRAF-0)")) {
                                        String dialogMsgTxt;
                                        if (isRecharge) {
                                            dialogMsgTxt = context.getResources().getString(R.string.realapi_popup_english_recharge_msg, opName, opName) + "\n\n" +
                                                    context.getResources().getString(R.string.realapi_popup_hindi_recharge_msg, opName);
                                        } else {
                                            dialogMsgTxt = context.getResources().getString(R.string.realapi_popup_english_billpayment_msg, opName, opName) + "\n\n" +
                                                    context.getResources().getString(R.string.realapi_popup_hindi_billpayment_msg, opName);
                                        }

                                        new CustomAlertDialog(context).enableRealApiDialog(dialogMsgTxt, (mobile, emailId) ->
                                                EnableDisableRealApi(context, true, loader, LoginDataResponse, deviceId, deviceSerialNum, object ->
                                                        Successful(context, ((BasicResponse) object).getMsg())));
                                    } else {
                                        openRechargeSlip(context, isBBPS, Amount, AccountNo, response.body().getLiveID(), opName,
                                                response.body().getTransactionID(), "FAILED", Opid, response.body().getStatuscode(),
                                                "", "", "", true, INTENT_RECHARGE_SLIP);
                                        // Failed(context, response.body().getMsg());
                                    }

                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                                Balancecheck(context, loader, LoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, null, null);
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<RechargeResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);

                        } else {
                            Processing(context, "Recharge request accepted");
                        }

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }
    }


    public void openRechargeSlip(Activity context, boolean isBBPS, String Amount, String AccountNo, String liveId, String opName, String txnId,
                          String status, int opId, int statusCode, String packageName, String address, String custName, boolean isFromRecharge, int INTENT_RECHARGE_SLIP) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss:SSS aa");
        String dateStr = inputFormat.format(new Date());
        Intent i = new Intent(context, RechargeSlipActivity.class);
        i.putExtra("amount", Amount);
        i.putExtra("RechargeMobileNo", AccountNo);
        i.putExtra("liveId", liveId);
        i.putExtra("operatorname", opName);
        i.putExtra("pdate", dateStr);
        i.putExtra("ptime", dateStr);
        i.putExtra("txid", txnId);
        i.putExtra("OID", opId);
        i.putExtra("txStatus", status);
        i.putExtra("StatusCode", statusCode);
        i.putExtra("IsBBPS", isBBPS);
        i.putExtra("IsFromRecharge", isFromRecharge);
        i.putExtra("typerecharge", status);
        i.putExtra("PackageName", packageName);
        i.putExtra("CustomerAddress", address /*operator.getAddress() + " - " + operator.getPincode()*/);
        i.putExtra("CustomerName", custName);
        i.putExtra("imageurl", ApplicationConstant.INSTANCE.baseIconUrl + opId + ".png");
        if (isFromRecharge) {
            context.startActivityForResult(i, INTENT_RECHARGE_SLIP);
        } else {
            context.startActivity(i);
        }

    }


    public void RechargeReport(final Activity context, String opTypeId, String topValue,
                               int status, String fromDate, String toDate, String transactionID, String accountNo,
                               String childMobileNumnber, String isExport, boolean IsRecent, final CustomLoader loader,
                               LoginResponse mLoginDataResponse, String deviceId, String deviceSerialNum, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call;
            if (mLoginDataResponse.getData().getLoginTypeID() == 3) {

                call = git.RechargeReportForEmployee(new RechargeReportRequest(IsRecent, opTypeId, "0",
                        ApplicationConstant.INSTANCE.APP_ID,
                        deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                        topValue, status, fromDate, toDate, transactionID, accountNo, childMobileNumnber, isExport,
                        mLoginDataResponse.getData().getUserID()
                        , mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(),
                        mLoginDataResponse.getData().getLoginTypeID()));
            } else {
                call = git.RechargeReport(new RechargeReportRequest(IsRecent, opTypeId, "0",
                        ApplicationConstant.INSTANCE.APP_ID,
                        deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                        topValue, status, fromDate, toDate, transactionID, accountNo, childMobileNumnber, isExport,
                        mLoginDataResponse.getData().getUserID()
                        , mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(),
                        mLoginDataResponse.getData().getLoginTypeID()));
            }
            call.enqueue(new Callback<RechargeReportResponse>() {
                @Override
                public void onResponse(Call<RechargeReportResponse> call, final retrofit2.Response<RechargeReportResponse> response) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Error(context, e.getMessage());
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
        }
    }

    public void AEPSReport(final Activity context, String opTypeId, String topValue,
                           int status, String fromDate, String toDate, String transactionID, String accountNo,
                           String childMobileNumnber, String isExport, boolean IsRecent, final CustomLoader loader,
                           LoginResponse mLoginDataResponse, String deviceId, String deviceSerialNum, final ApiResponseCallBack mApiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.AEPSReport(new RechargeReportRequest(IsRecent, opTypeId, "0",
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    topValue, status, fromDate, toDate, transactionID, accountNo, childMobileNumnber, isExport,
                    mLoginDataResponse.getData().getUserID()
                    , mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(),
                    mLoginDataResponse.getData().getLoginTypeID()));
            call.enqueue(new Callback<RechargeReportResponse>() {
                @Override
                public void onResponse(Call<RechargeReportResponse> call, final retrofit2.Response<RechargeReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
        }
    }

    public void IncentiveDetail(final Activity context, boolean isFromClick, String opTypeId, final CustomLoader loader, LoginResponse LoginDataResponse,
                                String deviceId, String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.IncentiveDetail(new IncentiveDetailRequest(opTypeId,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getIncentiveDetails() != null && response.body().getIncentiveDetails().size() > 0) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    } else {
                                        if (isFromClick) {
                                            Error(context, "Data Not Found");
                                        }
                                    }
                                } else if (response.body().getStatuscode() == -1) {
                                    if (isFromClick) {
                                        if (!response.body().isVersionValid()) {
                                            versionDialog(context);
                                        } else {
                                            Error(context, response.body().getMsg());
                                        }
                                    }
                                }
                            } else {
                                if (isFromClick) {
                                    Error(context, context.getResources().getString(R.string.some_thing_error));
                                }
                            }
                        } else {
                            if (isFromClick) {
                                apiErrorHandle(context, response.code(), response.message());
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (isFromClick) {
                            Error(context, e.getMessage());
                        }
                    }


                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    if (isFromClick) {
                        try {
                            apiFailureError(context, t);

                        } catch (IllegalStateException ise) {
                            Error(context, ise.getMessage());

                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (isFromClick) {
                Error(context, e.getMessage());
            }
        }
    }

    public void FundOrderReport(final Activity context, String oid,
                                String status, String fromDate, String toDate, String transactionID, String accountNo,
                                String isExport, String tMode, String isSelf, String uMobile,
                                final CustomLoader loader, LoginResponse mLoginDataResponse,
                                String deviceId, String deviceSerialNum, final ApiResponseCallBack mApiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.FundOrderReport(new LedgerReportRequest(
                    "100", status, oid, fromDate, toDate, transactionID, accountNo, isExport,
                    mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginDataResponse.getData().getLoginTypeID(),
                    tMode, isSelf, uMobile));
            call.enqueue(new Callback<RechargeReportResponse>() {
                @Override
                public void onResponse(Call<RechargeReportResponse> call, final retrofit2.Response<RechargeReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    //Balancecheck(context, loader, null);

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
            e.printStackTrace();
        }
    }

    public void Logout(CustomLoader loader, final Activity context, String sessType,
                       LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, AppPreferences mAppPreferences) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeResponse> call = git.Logout(new LogoutRequest(sessType,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeResponse>() {
                @Override
                public void onResponse(Call<RechargeResponse> call, final retrofit2.Response<RechargeResponse> response) {
                    loader.dismiss();
                    if (response.isSuccessful()) {
                        try {

                            if (response.body() != null) {
                                logout(context, mAppPreferences);
                                context.finish();

                            }
                        } catch (Exception e) {

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<RechargeResponse> call, Throwable t) {
                    loader.dismiss();
                    try {

                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            loader.dismiss();
            e.printStackTrace();
        }
    }

    public void NewsApi(final Activity context, boolean isLoginNews, final TextView mNewsView, final View newsCard,
                        LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, AppPreferences mAppPreferences) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.GetAppNews(new NewsRequest(isLoginNews,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {
                    if (response.isSuccessful()) {
                        try {

                            if (response.body() != null && response.body().getStatuscode() == 1) {
                                mNewsDataResponse = response.body();
                                mAppPreferences.set(ApplicationConstant.INSTANCE.newsDataPref, new Gson().toJson(response.body()));
                                if (response.body().getNewsContent() != null && response.body().getNewsContent().getNewsDetail() != null && !response.body().getNewsContent().getNewsDetail().isEmpty()) {
                                    //String news = "<body style='margin: 0; padding: 0'>" + response.body().getNewsContent().getNewsDetail() + "</body>";
                                    mNewsView.setText(Html.fromHtml(response.body().getNewsContent().getNewsDetail()));
                                    newsCard.setVisibility(View.VISIBLE);
                                } else {
                                    newsCard.setVisibility(View.GONE);
                                }

                            } else {
                                newsCard.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BannerApi(LoginResponse mLoginDataResponse, String deviceId, String deviceSerialNum,
                          AppPreferences mAppPreferences, ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<AppUserListResponse> call = git.GetAppBanner(new BasicRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatuscode() == 1) {
                                mBannerDataResponse = response.body();
                                appLogoUrl = mBannerDataResponse.getAppLogoUrl();
                                mAppPreferences.set(ApplicationConstant.INSTANCE.appLogoUrlPref, appLogoUrl);

                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(mBannerDataResponse);
                                }


                            }
                        } else {
                            //ApiUtilMethods.INSTANCE.apiErrorHandle(HomeDashActivity.this,response.code(),response.message());
                        }
                    } catch (Exception e) {

                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void FundDCReport(final Activity context, boolean isSelf, int walletTypeID, int serviceID,
                             String otherUserMob, String fromDate, String toDate, String accountNo,
                             final CustomLoader loader, LoginResponse mLoginDataResponse,
                             String deviceId, String deviceSerialNum, final ApiResponseCallBack mApiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            FundDCReportRequest mFundDCReportRequest = new FundDCReportRequest(isSelf, walletTypeID,
                    serviceID, otherUserMob, fromDate, toDate, accountNo, mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getLoginTypeID());
            Call<RechargeReportResponse> call;
            if (mLoginDataResponse.getData().getLoginTypeID() == 3) {
                call = git.FundDCReportForEmployee(mFundDCReportRequest);
            } else {
                call = git.FundDCReport(mFundDCReportRequest);
            }

            call.enqueue(new Callback<RechargeReportResponse>() {
                @Override
                public void onResponse(Call<RechargeReportResponse> call, final retrofit2.Response<RechargeReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void RefundLog(final Activity context, int topRows, int criteria, String criteriaText,
                          int status, String fromDate, String toDate, int dateType, String transactionID,
                          final CustomLoader loader, LoginResponse mLoginDataResponse,
                          String deviceId, String deviceSerialNum, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            RefundLogRequest mRefundLogRequest = new RefundLogRequest(topRows, criteria,
                    criteriaText, status, fromDate, toDate, dateType, transactionID, mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getLoginTypeID());
            /* String str =new Gson().toJson(mFundDCReportRequest);*/
            Call<AppUserListResponse> call = git.RefundLog(mRefundLogRequest);
            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1 && response.body().getRefundLog() != null && response.body().getRefundLog().size() > 0) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else if (response.body().getStatuscode() == -1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    Error(context, "Report not found.");
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
            Error(context, e.getMessage());
        }
    }

    public void UserAchieveTarget(final Activity context, final boolean isTotal, final CustomLoader loader, LoginResponse LoginDataResponse,
                                  AppPreferences mAppPreferences, String deviceId, String deviceSerialNum,
                                  final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.GetTargetAchieved(new AchieveTargetRequest(isTotal,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));


            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (isTotal) {
                                        mAppPreferences.set(ApplicationConstant.INSTANCE.totalTargetDataPref, new Gson().toJson(response.body()));
                                    }
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else if (response.body().getStatuscode() == -1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!isTotal) {
                                        if (!response.body().getVersionValid()) {
                                            versionDialog(context);
                                        } else {
                                            Error(context, response.body().getMsg());
                                        }
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    if (!isTotal) {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
        }
    }

    public void UserDayBook(final Activity context, String mobileNo, String fromDate, String toDate, final CustomLoader loader,
                            LoginResponse mLoginPrefResponse, AppPreferences mAppPreferences,
                            String deviceId, String deviceSerialNum, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call;
            if (mLoginPrefResponse.getData().getLoginTypeID() == 3) {

                call = git.UserDaybookForEmployee(new UserDayBookRequest(fromDate, toDate, mobileNo,
                        mLoginPrefResponse.getData().getUserID(), mLoginPrefResponse.getData().getLoginTypeID(),
                        ApplicationConstant.INSTANCE.APP_ID,
                        deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginPrefResponse.getData().getSessionID(),
                        mLoginPrefResponse.getData().getSession()));
            } else {
                call = git.UserDaybook(new UserDayBookRequest(fromDate, toDate, mobileNo,
                        mLoginPrefResponse.getData().getUserID(), mLoginPrefResponse.getData().getLoginTypeID(),
                        ApplicationConstant.INSTANCE.APP_ID,
                        deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginPrefResponse.getData().getSessionID(),
                        mLoginPrefResponse.getData().getSession()));

            }
            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.dayBookDataPref, new Gson().toJson(response.body()));
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (context instanceof UserDayBookActivity) {
                                        if (!response.body().getVersionValid()) {
                                            versionDialog(context);
                                        } else {
                                            Error(context, response.body().getMsg());
                                        }
                                    }

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                }
                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(ERROR_OTHER);
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (context instanceof UserDayBookActivity) {
                            Error(context, e.getMessage());
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }

                    if (context instanceof UserDayBookActivity) {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    }
                }
            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (context instanceof UserDayBookActivity) {
                Error(context, e.getMessage());
            }
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
        }
    }

    public void UserDayBookDmt(final Activity context, String mobileNo, String fromDate, String toDate, final CustomLoader loader,
                               LoginResponse mLoginPrefResponse, String deviceId, String deviceSerialNum,
                               final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.UserDaybookDmt(new UserDayBookRequest(fromDate, toDate, mobileNo,
                    mLoginPrefResponse.getData().getUserID(), mLoginPrefResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginPrefResponse.getData().getSessionID(),
                    mLoginPrefResponse.getData().getSession()));


            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (!response.body().getVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                }
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(ERROR_OTHER);
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }

                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (Exception e) {
                        Error(context, e.getMessage());
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Error(context, e.getMessage());
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
        }
    }


    public void LedgerReport(final Activity context, String oid, String topRow,
                             String status, String fromDate, String toDate, int walletTypeId,
                             String transactionID, String accountNo, String isExport,
                             final CustomLoader loader, LoginResponse mLoginDataResponse,
                             String deviceId, String deviceSerialNum, final ApiResponseCallBack mApiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call;
            if (mLoginDataResponse.getData().getLoginTypeID() == 3) {
                call = git.LedgerReportForEmployee(new LedgerReportRequest(
                        topRow, oid, status, fromDate, toDate, transactionID, accountNo, isExport, mLoginDataResponse.getData().getUserID(),
                        mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(),
                        ApplicationConstant.INSTANCE.APP_ID, deviceId,
                        "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginDataResponse.getData().getLoginTypeID(),
                        walletTypeId == 0 ? 1 : walletTypeId));

            } else {
                call = git.LedgerReport(new LedgerReportRequest(
                        topRow, oid, status, fromDate, toDate, transactionID, accountNo, isExport, mLoginDataResponse.getData().getUserID(),
                        mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(),
                        ApplicationConstant.INSTANCE.APP_ID, deviceId,
                        "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginDataResponse.getData().getLoginTypeID(), walletTypeId));
            }
            call.enqueue(new Callback<RechargeReportResponse>() {
                @Override
                public void onResponse(Call<RechargeReportResponse> call, final retrofit2.Response<RechargeReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
            e.printStackTrace();
        }
    }


    //Emp API

    public void GetTargetSegment(Activity mContext, LoginResponse mLoginResponse, String deviceId, String deviceSerialNum,
                                 CustomLoader loader, ApiCallBack mApiCallBack) {
        try {
            if (loader != null) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetTargetSegmentResponse> call = git.GetTargetSegment(new BasicRequest(mLoginResponse.getData().getUserID(),
                    mLoginResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginResponse.getData().getSessionID(), mLoginResponse.getData().getSession()));

            call.enqueue(new Callback<GetTargetSegmentResponse>() {

                @Override
                public void onResponse(Call<GetTargetSegmentResponse> call, retrofit2.Response<GetTargetSegmentResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (response.isSuccessful()) {
                            GetTargetSegmentResponse mGetTargetSegmentResponse = response.body();
                            if (mGetTargetSegmentResponse != null) {
                                if (mGetTargetSegmentResponse.getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mGetTargetSegmentResponse);
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(mContext);
                                    } else {
                                        Error(mContext, mGetTargetSegmentResponse.getMsg());
                                    }

                                }

                            } else {

                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<GetTargetSegmentResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(mContext);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(mContext, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(mContext, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage());
        }

    }

    public void GetTodayTransactors(Activity mContext, int criteria, LoginResponse mLoginResponse, String deviceId,
                                    String deviceSerialNum, CustomLoader loader, ApiCallBack mApiCallBack) {
        try {
            if (loader != null) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetTodayTransactorsResponse> call = git.GetTodayTransactors(new GetTodayTransactorsRequest(criteria,
                    mLoginResponse.getData().getUserID(),
                    mLoginResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginResponse.getData().getSessionID(), mLoginResponse.getData().getSession()));

            call.enqueue(new Callback<GetTodayTransactorsResponse>() {

                @Override
                public void onResponse(Call<GetTodayTransactorsResponse> call, retrofit2.Response<GetTodayTransactorsResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (response.isSuccessful()) {
                            GetTodayTransactorsResponse mGetTodayTransactorsResponse = response.body();
                            if (mGetTodayTransactorsResponse != null) {
                                if (mGetTodayTransactorsResponse.getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mGetTodayTransactorsResponse);
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(mContext);
                                    } else {
                                        Error(mContext, mGetTodayTransactorsResponse.getMsg());
                                    }

                                }

                            } else {

                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<GetTodayTransactorsResponse> call, Throwable t) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(mContext);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(mContext, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(mContext, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage());
        }

    }

    public void GetTodayOutletsListForEmp(Activity mContext, LoginResponse mLoginResponse, String deviceId, String deviceSerialNum,
                                          CustomLoader loader, ApiCallBack mApiCallBack) {
        try {
            if (loader != null) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetTodayOutletListForEmpResponse> call = git.GetTodayOutletsListForEmp(new BasicRequest(mLoginResponse.getData().getUserID(),
                    mLoginResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginResponse.getData().getSessionID(), mLoginResponse.getData().getSession()));

            call.enqueue(new Callback<GetTodayOutletListForEmpResponse>() {

                @Override
                public void onResponse(Call<GetTodayOutletListForEmpResponse> call, retrofit2.Response<GetTodayOutletListForEmpResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (response.isSuccessful()) {
                            GetTodayOutletListForEmpResponse mResponse = response.body();
                            if (mResponse != null) {
                                if (mResponse.getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mResponse);
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(mContext);
                                    } else {
                                        Error(mContext, mResponse.getMsg());
                                    }

                                }

                            } else {

                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<GetTodayOutletListForEmpResponse> call, Throwable t) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(mContext);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(mContext, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(mContext, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage());
        }

    }

    public void GetTodayRecord(Activity mContext, LoginResponse mLoginResponse, String deviceId, String deviceSerialNum,
                               CustomLoader loader, ApiCallBack mApiCallBack) {
        try {
            if (loader != null) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetEmpTodayLivePSTResponse> call = git.GetEmpTodayLivePST(new BasicRequest(mLoginResponse.getData().getUserID(),
                    mLoginResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, deviceId, "",
                    BuildConfig.VERSION_NAME, deviceSerialNum, mLoginResponse.getData().getSessionID(),
                    mLoginResponse.getData().getSession()));

            call.enqueue(new Callback<GetEmpTodayLivePSTResponse>() {

                @Override
                public void onResponse(Call<GetEmpTodayLivePSTResponse> call, retrofit2.Response<GetEmpTodayLivePSTResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (response.isSuccessful()) {
                            GetEmpTodayLivePSTResponse mGetEmpTodayLivePSTResponse = response.body();
                            if (mGetEmpTodayLivePSTResponse != null) {
                                if (mGetEmpTodayLivePSTResponse.getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mGetEmpTodayLivePSTResponse);
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(mContext);
                                    } else {
                                        Error(mContext, mGetEmpTodayLivePSTResponse.getMsg());
                                    }

                                }

                            } else {

                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<GetEmpTodayLivePSTResponse> call, Throwable t) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(mContext);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(mContext, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(mContext, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage());
        }


    }

    public void GetLMTDVsMTD(Activity mContext, LoginResponse mLoginResponse, String deviceId, String deviceSerialNum,
                             CustomLoader loader, ApiCallBack mApiCallBack) {
        try {
            if (loader != null) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetLMTDVsMTDResponse> call = git.GetComparisionChart(new BasicRequest(mLoginResponse.getData().getUserID(),
                    mLoginResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginResponse.getData().getSessionID(), mLoginResponse.getData().getSession()));

            call.enqueue(new Callback<GetLMTDVsMTDResponse>() {

                @Override
                public void onResponse(Call<GetLMTDVsMTDResponse> call, retrofit2.Response<GetLMTDVsMTDResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (response.isSuccessful()) {
                            GetLMTDVsMTDResponse mGetLMTDVsMTDResponse = response.body();
                            if (mGetLMTDVsMTDResponse != null) {
                                if (mGetLMTDVsMTDResponse.getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mGetLMTDVsMTDResponse);
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(mContext);
                                    } else {
                                        Error(mContext, mGetLMTDVsMTDResponse.getMsg());
                                    }

                                }

                            } else {

                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<GetLMTDVsMTDResponse> call, Throwable t) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(mContext);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(mContext, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(mContext, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage());
        }


    }


    public void GetLastdayVsTodayChart(Activity mContext, LoginResponse mLoginResponse, String deviceId, String deviceSerialNum,
                                       CustomLoader loader, ApiCallBack mApiCallBack) {
        try {
            if (loader != null) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetLastdayVsTodayChartResponse> call = git.GetLastdayVsTodayChart(new BasicRequest(mLoginResponse.getData().getUserID(),
                    mLoginResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, deviceId, "",
                    BuildConfig.VERSION_NAME, deviceSerialNum, mLoginResponse.getData().getSessionID(), mLoginResponse.getData().getSession()));

            call.enqueue(new Callback<GetLastdayVsTodayChartResponse>() {

                @Override
                public void onResponse(Call<GetLastdayVsTodayChartResponse> call, retrofit2.Response<GetLastdayVsTodayChartResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (response.isSuccessful()) {
                            GetLastdayVsTodayChartResponse mResponse = response.body();
                            if (mResponse != null) {
                                if (mResponse.getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mResponse);
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(mContext);
                                    } else {
                                        Error(mContext, mResponse.getMsg());
                                    }

                                }

                            } else {

                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<GetLastdayVsTodayChartResponse> call, Throwable t) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(mContext);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(mContext, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(mContext, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage());
        }


    }

    public void GetLastSevenDayPSTChart(Activity mContext, LoginResponse mLoginResponse, String deviceId, String deviceSerialNum,
                                        CustomLoader loader, ApiCallBack mApiCallBack) {
        try {
            if (loader != null) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetLastSevenDayPSTDataResponse> call = git.GetLastSevenDayPSTDataChart(new BasicRequest(mLoginResponse.getData().getUserID(),
                    mLoginResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, deviceId, "",
                    BuildConfig.VERSION_NAME, deviceSerialNum, mLoginResponse.getData().getSessionID(), mLoginResponse.getData().getSession()));

            call.enqueue(new Callback<GetLastSevenDayPSTDataResponse>() {

                @Override
                public void onResponse(Call<GetLastSevenDayPSTDataResponse> call, retrofit2.Response<GetLastSevenDayPSTDataResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (response.isSuccessful()) {
                            GetLastSevenDayPSTDataResponse mResponse = response.body();
                            if (mResponse != null) {
                                if (mResponse.getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mResponse);
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(mContext);
                                    } else {
                                        Error(mContext, mResponse.getMsg());
                                    }

                                }

                            } else {

                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<GetLastSevenDayPSTDataResponse> call, Throwable t) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(mContext);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(mContext, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(mContext, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage());
        }


    }

    public void GetUserCommitment(Activity mContext, LoginResponse mLoginResponse, String deviceId, String deviceSerialNum,
                                  CustomLoader loader, ApiCallBack mApiCallBack) {
        try {
            if (loader != null) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetUserCommitmentResponse> call = git.GetUserCommitment(new BasicRequest(mLoginResponse.getData().getUserID(),
                    mLoginResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginResponse.getData().getSessionID(), mLoginResponse.getData().getSession()));

            call.enqueue(new Callback<GetUserCommitmentResponse>() {

                @Override
                public void onResponse(Call<GetUserCommitmentResponse> call, retrofit2.Response<GetUserCommitmentResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (response.isSuccessful()) {
                            GetUserCommitmentResponse mResponse = response.body();
                            if (mResponse != null) {
                                if (mResponse.getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mResponse);
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(mContext);
                                    } else {
                                        Error(mContext, mResponse.getMsg());
                                    }

                                }

                            } else {

                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<GetUserCommitmentResponse> call, Throwable t) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(mContext);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(mContext, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(mContext, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage());
        }


    }


    public void GetUserCommitmentChart(Activity mContext, LoginResponse mLoginResponse, String deviceId, String deviceSerialNum,
                                       CustomLoader loader, ApiCallBack mApiCallBack) {
        try {
            if (loader != null) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<BasicResponse> call = git.GetUserCommitmentChart(new BasicRequest(mLoginResponse.getData().getUserID(),
                    mLoginResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginResponse.getData().getSessionID(), mLoginResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (response.isSuccessful()) {
                            BasicResponse mResponse = response.body();
                            if (mResponse != null) {
                                if (mResponse.getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mResponse);
                                    }
                                } else {
                                    if (!response.body().getVersionValid()) {
                                        versionDialog(mContext);
                                    } else {
                                        Error(mContext, mResponse.getMsg());
                                    }

                                }

                            } else {

                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(mContext);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(mContext, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(mContext, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage());
        }


    }

    public void SetUserCommitment(Activity mContext, String requestedDate, int uid, String commitment, double longitute,
                                  double latitude, int empID, String roleID, LoginResponse mLoginResponse, String deviceId,
                                  String deviceSerialNum, CustomLoader loader, ApiCallBack mApiCallBack) {
        try {
            if (loader != null) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<BasicResponse> call = git.SetUserCommitment(new SetUserCommitmentRequest(requestedDate, uid, commitment, longitute, latitude, empID, roleID,
                    mLoginResponse.getData().getUserID(),
                    mLoginResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginResponse.getData().getSessionID(), mLoginResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (response.isSuccessful()) {
                            BasicResponse mResponse = response.body();
                            if (mResponse != null) {
                                if (mResponse.getStatuscode() == 1) {
                                    Successful(mContext, mResponse.getMsg());
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mResponse);
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(mContext);
                                    } else {
                                        Error(mContext, mResponse.getMsg());
                                    }

                                }

                            } else {

                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(mContext);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(mContext, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(mContext, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage());
        }


    }


    public void GetEmpDownlineUser(Activity mContext, LoginResponse mLoginResponse, String deviceId, String deviceSerialNum,
                                   CustomLoader loader, ApiCallBack mApiCallBack) {
        try {
            if (loader != null) {
                loader.show();
            }
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetEmpDownlineUserResponse> call = git.GetEmpDownlineUser(new BasicRequest(mLoginResponse.getData().getUserID(),
                    mLoginResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginResponse.getData().getSessionID(), mLoginResponse.getData().getSession()));

            call.enqueue(new Callback<GetEmpDownlineUserResponse>() {

                @Override
                public void onResponse(Call<GetEmpDownlineUserResponse> call, retrofit2.Response<GetEmpDownlineUserResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (response.isSuccessful()) {
                            GetEmpDownlineUserResponse mGetEmpDownlineUserResponse = response.body();
                            if (mGetEmpDownlineUserResponse != null) {
                                if (mGetEmpDownlineUserResponse.getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mGetEmpDownlineUserResponse);
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(mContext);
                                    } else {
                                        Error(mContext, mGetEmpDownlineUserResponse.getMsg());
                                    }

                                }

                            } else {

                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<GetEmpDownlineUserResponse> call, Throwable t) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(mContext);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(mContext, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(mContext, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage());
        }


    }

    public void GetPSTReportEmp(final Activity context, String date, int roleid,
                                LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final CustomLoader loader,
                                final ApiResponseCallBack mApiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GetPstReportResponse> call = git.GetPSTReportEmp(new GetPSTReportEmpRequest(date, roleid,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetPstReportResponse>() {
                @Override
                public void onResponse(Call<GetPstReportResponse> call, final retrofit2.Response<GetPstReportResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    // Balancecheck(context, loader, null);
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }

                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetPstReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }

                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (mApiCallBack != null) {
                mApiCallBack.onError(0);
            }
            Error(context, e.getMessage());
        }


    }

    public void GetTertiaryReportEmp(final Activity context, String date, int roleid,
                                     LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum,
                                     final CustomLoader loader,
                                     final ApiResponseCallBack mApiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GetTeriatryReportResponse> call = git.GetTertiaryReportEmp(new GetPSTReportEmpRequest(date, roleid,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetTeriatryReportResponse>() {
                @Override
                public void onResponse(Call<GetTeriatryReportResponse> call, final retrofit2.Response<GetTeriatryReportResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    // Balancecheck(context, loader, null);
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<GetTeriatryReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }

                    try {

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (mApiCallBack != null) {
                mApiCallBack.onError(0);
            }
            Error(context, e.getMessage());
        }


    }

    public void PostDailyClosing(final Activity context, String travel, String expense, final CustomLoader loader,
                                 LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<PostDailyClosingResponse> call = git.PostDailyClosing(new PostDailyClosingRequest(travel, expense,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getSession())
            );
            call.enqueue(new Callback<PostDailyClosingResponse>() {
                @Override
                public void onResponse(Call<PostDailyClosingResponse> call, final retrofit2.Response<PostDailyClosingResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getData() != null) {
                                        if (response.body().getData().getStatuscode() == 1) {
                                            Successful(context, response.body().getData().getMsg());
                                        } else {
                                            Error(context, response.body().getData().getMsg());
                                        }
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    } else {
                                        Error(context, "Something went wrong, please try after some time");
                                    }

                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<PostDailyClosingResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);

                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());

                        } else {

                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetEmployeeTargetReport(final Activity context, String date, int roleid, int empId,
                                        LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum,
                                        final CustomLoader loader,
                                        final ApiResponseCallBack mApiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GetTargetReportResponse> call = git.GetEmployeeTargetReport(new GetTargetReportEmpRequest(date, roleid, empId,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetTargetReportResponse>() {
                @Override
                public void onResponse(Call<GetTargetReportResponse> call, final retrofit2.Response<GetTargetReportResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    // Balancecheck(context, loader, null);
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<GetTargetReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }

                    try {

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (mApiCallBack != null) {
                mApiCallBack.onError(0);
            }
            Error(context, e.getMessage());
        }


    }

    public void GetEmployees(final Activity context, String criteriaValue, int criteriaId, int employeeRole, boolean sortById,
                             boolean isDesc, int uid,
                             int topRows,
                             LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final CustomLoader loader,
                             final ApiResponseCallBack mApiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GetEmployeesResponse> call = git.GetEmployees(new EmpUserRequest(criteriaId, criteriaValue, employeeRole, sortById,
                    isDesc, uid, topRows,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetEmployeesResponse>() {
                @Override
                public void onResponse(Call<GetEmployeesResponse> call, final retrofit2.Response<GetEmployeesResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    // Balancecheck(context, loader, null);
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<GetEmployeesResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }

                    try {

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (mApiCallBack != null) {
                mApiCallBack.onError(0);
            }
            Error(context, e.getMessage());
        }


    }


    public void GetMeetingDetail(final Activity context, String criteriaValue, int criteriaId, String fromDate, String tillDate,
                                 int topRows,
                                 LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final CustomLoader loader,
                                 final ApiResponseCallBack mApiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GetMeetingDetailResponse> call = git.GetMeetingDetail(new MeetingDetailRequest(criteriaId, criteriaValue, fromDate, tillDate,
                    topRows, LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetMeetingDetailResponse>() {
                @Override
                public void onResponse(Call<GetMeetingDetailResponse> call, final retrofit2.Response<GetMeetingDetailResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    // Balancecheck(context, loader, null);
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<GetMeetingDetailResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }

                    try {


                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (mApiCallBack != null) {
                mApiCallBack.onError(0);
            }
            Error(context, e.getMessage());
        }


    }

    public void GetMeetingReport(final Activity context, String criteriaValue, int criteriaId, String fromDate, String tillDate,
                                 int topRows, LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum,
                                 final CustomLoader loader,
                                 final ApiResponseCallBack mApiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GetMeetingReportResponse> call = git.GetMeetingReport(new MeetingDetailRequest(criteriaId, criteriaValue, fromDate, tillDate,
                    topRows, LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetMeetingReportResponse>() {
                @Override
                public void onResponse(Call<GetMeetingReportResponse> call, final retrofit2.Response<GetMeetingReportResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    // Balancecheck(context, loader, null);
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<GetMeetingReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }

                    try {


                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (mApiCallBack != null) {
                mApiCallBack.onError(0);
            }
            Error(context, e.getMessage());
        }


    }

    public void AreaByPincode(final Activity context, String pincode, LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum,
                              final CustomLoader loader, ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<AreaWithPincodeResponse> call = git.GetAreabyPincode(new AreaWithPincodeRequest(pincode,
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getLoginTypeID(),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getSession())
            );
            call.enqueue(new Callback<AreaWithPincodeResponse>() {
                @Override
                public void onResponse(Call<AreaWithPincodeResponse> call, final retrofit2.Response<AreaWithPincodeResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null) {

                            if (response.body().getStatuscode() == 1) {
                                if (response.body().getAreas() != null && response.body().getAreas().size() > 0) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    Error(context, "Area not available or may be pincode doesn't exist.");
                                }

                            } else {
                                if (!response.body().isVersionValid()) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg());
                                }
                            }

                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<AreaWithPincodeResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            NetworkError(context);
                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetSubMeetingReport(final Activity context, int SearchId, String criteriaValue, int criteriaId, String fromDate, String tillDate,
                                    int topRows, LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final CustomLoader loader,
                                    final ApiCallBackTwoMethod mApiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GetMeetingDetailResponse> call = git.GetMeetingSubReport(new MeetingDetailRequest(SearchId, criteriaId, criteriaValue, fromDate, tillDate,
                    topRows, LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetMeetingDetailResponse>() {
                @Override
                public void onResponse(Call<GetMeetingDetailResponse> call, final retrofit2.Response<GetMeetingDetailResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(response.body().getMsg());
                                    }
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<GetMeetingDetailResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    if (mApiCallBack != null) {
                        mApiCallBack.onError(t.getMessage());
                    }
                    try {


                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }


    }

    public void GetMapPoints(final Activity context, int SearchId,
                             LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final CustomLoader loader,
                             final ApiCallBackTwoMethod mApiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<MapPointResponse> call = git.GetMapPoints(new MapPointRequest(SearchId,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<MapPointResponse>() {
                @Override
                public void onResponse(Call<MapPointResponse> call, final retrofit2.Response<MapPointResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(response.body().getMsg());
                                    }
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<MapPointResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    if (mApiCallBack != null) {
                        mApiCallBack.onError(t.getMessage());
                    }
                    try {

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }


    }


    public void GetEmployeesUser(final Activity context, String criteriaValue, int criteriaId, int employeeRole, boolean sortById, boolean isDesc, int uid,
                                 int topRows,
                                 LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final CustomLoader loader,
                                 final ApiResponseCallBack mApiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GetEmployeesUserResponse> call = git.GetEmployeeeUser(new EmpUserRequest(criteriaId, criteriaValue, employeeRole, sortById,
                    isDesc, uid, topRows,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetEmployeesUserResponse>() {
                @Override
                public void onResponse(Call<GetEmployeesUserResponse> call, final Response<GetEmployeesUserResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    // Balancecheck(context, loader, null);
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(0);
                                    }
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<GetEmployeesUserResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }

                    try {

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(1);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(0);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(0);
                        }
                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (mApiCallBack != null) {
                mApiCallBack.onError(0);
            }
            Error(context, e.getMessage());
        }


    }

    //Emp API


    public void OrderForUPI(final Activity context, String amt, String upiID, CustomLoader loader, LoginResponse LoginDataResponse,
                            String deviceId, String deviceSerialNum, ApiCallBack mApiCallBack) {
        try {
            loader.show();

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.AppGenerateOrderForUPI(new GenerateOrderForUPIRequest(amt, upiID,
                    LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getLoginTypeID()));
            call.enqueue(new Callback<RechargeReportResponse>() {
                @Override
                public void onResponse(Call<RechargeReportResponse> call, final retrofit2.Response<RechargeReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body().getOrderID());
                                    }

                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        apiFailureError(context, t);


                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Error(context, e.getMessage());
        }
    }


    public void PaymentRequest(final Activity context, String upiid, String orderID, String checksum, File selectedFile, int bankid, String txttranferAmount,
                               String accountNumber, String transactionId, String ChecknumberID, String txtCardNo, String txtMobileNo,
                               String txtAccountID, int PaymentModeID, String walletType, final View btnPaymentSubmit,
                               final CustomLoader loader, LoginResponse LoginDataResponse,
                               String deviceId, String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {
            btnPaymentSubmit.setEnabled(false);
            int isImage = 0;
            if (selectedFile != null) {
                isImage = 1;
            }
           /* else if(selectedFile.exists()){
                isImage=true;
            }*/

            AddFundRequest mAddFundRequest = new AddFundRequest(upiid, orderID, checksum, isImage, bankid + "",
                    txttranferAmount, transactionId, txtMobileNo, ChecknumberID, txtCardNo, txtAccountID, accountNumber, PaymentModeID,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(), walletType);

            String req = new Gson().toJson(mAddFundRequest);
            // Parsing any Media type file
            MultipartBody.Part fileToUpload = null;
            if (selectedFile != null) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), selectedFile);
                if (selectedFile != null) {
                    fileToUpload = MultipartBody.Part.createFormData("file", selectedFile.getName(), requestBody);
                }
            }
            RequestBody requestStr = RequestBody.create(MediaType.parse("text/plain"), req);
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GetBankAndPaymentModeResponse> call = git.AppFundOrder(fileToUpload, requestStr);
            call.enqueue(new Callback<GetBankAndPaymentModeResponse>() {

                @Override
                public void onResponse(Call<GetBankAndPaymentModeResponse> call, retrofit2.Response<GetBankAndPaymentModeResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {

                            if (response.body() != null && response.body().getStatuscode() != null) {
                                if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                    // setFundreqToList(context, new Gson().toJson(response.body()).toString());
                                    isReadyToUpdateBalance = true;
                                    SuccessfulWithFinish(response.body().getMsg(), context, false);
                                    btnPaymentSubmit.setEnabled(false);
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                            /*FragmentActivityMessage activityActivityMessage =
                                    new FragmentActivityMessage(new Gson().toJson(response.body()).toString(), "refreshvalue");
                            GlobalBus.getBus().post(activityActivityMessage);*/
                                } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                    btnPaymentSubmit.setEnabled(true);
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                } else {
                                    btnPaymentSubmit.setEnabled(true);
                                }

                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                                btnPaymentSubmit.setEnabled(true);
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        btnPaymentSubmit.setEnabled(true);
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<GetBankAndPaymentModeResponse> call, Throwable t) {

                    btnPaymentSubmit.setEnabled(true);
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            btnPaymentSubmit.setEnabled(true);
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
            e.printStackTrace();
        }

    }


    public void UploadProfilePic(final Activity context, File selectedFile, final CustomLoader loader, LoginResponse LoginDataResponse,
                                 String deviceId, String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {


            BasicRequest mBasicRequest = new BasicRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession());

            String req = new Gson().toJson(mBasicRequest);
            // Parsing any Media type file
            MultipartBody.Part fileToUpload = null;
            if (selectedFile != null) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), selectedFile);
                if (selectedFile != null) {
                    fileToUpload = MultipartBody.Part.createFormData("file", selectedFile.getName(), requestBody);
                }
            }
            RequestBody requestStr = RequestBody.create(MediaType.parse("text/plain"), req);
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call = git.UploadProfile(fileToUpload, requestStr);
            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    // setFundreqToList(context, new Gson().toJson(response.body()).toString());
                                    Successful(context, response.body().getMsg());

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else if (response.body().getStatuscode() == -1) {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                } else {
                                    Error(context, response.body().getMsg());
                                }

                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {

            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
            e.printStackTrace();
        }

    }


    public void MyCommission(final Activity context, boolean isErrorShow, final CustomLoader loader,
                             LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum,
                             AppPreferences mAppPreferences, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<SlabCommissionResponse> call = git.DisplayCommission(new BalanceRequest("", LoginDataResponse.getData().getSlabID(),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<SlabCommissionResponse>() {

                @Override
                public void onResponse(Call<SlabCommissionResponse> call, retrofit2.Response<SlabCommissionResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {

                                    mAppPreferences.set(ApplicationConstant.INSTANCE.commListPref, new Gson().toJson(response.body()));

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (isErrorShow) {
                                        if (!response.body().getVersionValid()) {
                                            versionDialog(context);
                                        } else {
                                            Error(context, response.body().getMsg());
                                        }
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(ERROR_OTHER);
                                        }
                                    }


                                }

                            }
                        } else {

                            if (isErrorShow) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(ERROR_OTHER);
                                }
                                apiErrorHandle(context, response.code(), response.message());
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (isErrorShow) {
                            Error(context, e.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        }

                    }

                }

                @Override
                public void onFailure(Call<SlabCommissionResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    if (isErrorShow) {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (isErrorShow) {
                Error(context, e.getMessage());
                if (mApiCallBack != null) {
                    mApiCallBack.onError(ERROR_OTHER);
                }
            }
        }

    }

    public void MyCommissionDetail(final Activity context, int oid, final CustomLoader loader,
                                   LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum,
                                   AppPreferences mAppPreferences, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<SlabRangeDetailResponse> call = git.SlabRangDetail(new SlabRangeDetailRequest(oid,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME,
                    deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<SlabRangeDetailResponse>() {

                @Override
                public void onResponse(Call<SlabRangeDetailResponse> call, retrofit2.Response<SlabRangeDetailResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getSlabRangeDetail() != null && response.body().getSlabRangeDetail().size() > 0) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    } else {
                                        Error(context, "Slab Range Data not found.");
                                    }

                                } else if (response.body().getStatuscode() == -1) {
                                    if (!response.body().getVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                } else {
                                    Error(context, context.getResources().getString(R.string.some_thing_error));
                                }

                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<SlabRangeDetailResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }
    }

    public void GetNotifications(final Activity context, LoginResponse LoginDataResponse, AppPreferences mAppPreferences,
                                 String deviceId, String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.GetAppNotification(new BasicRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {
                    if (response.isSuccessful()) {
                        try {
                            AppUserListResponse apiData = response.body();
                            if (apiData != null) {
                                if (apiData.getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        ArrayList<Integer> visibleId = new ArrayList<>();
                                        int visibleCount = 0;
                                        AppUserListResponse storedData = new Gson().fromJson(mAppPreferences.getNonRemovalString(ApplicationConstant.INSTANCE.notificationListPref), AppUserListResponse.class);
                                        if (storedData != null && storedData.getNotifications() != null && storedData.getNotifications().size() > 0) {

                                            for (int i = 0; i < storedData.getNotifications().size(); i++) {
                                                if (storedData.getNotifications().get(i).isSeen()) {
                                                    visibleId.add(storedData.getNotifications().get(i).getId());
                                                }
                                            }
                                        }
                                        if (visibleId.size() > 0) {
                                            for (int i = 0; i < apiData.getNotifications().size(); i++) {
                                                if (visibleId.contains(apiData.getNotifications().get(i).getId())) {
                                                    apiData.getNotifications().get(i).setSeen(true);
                                                    visibleCount++;
                                                }
                                            }
                                        }
                                        mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.notificationListPref, new Gson().toJson(apiData));

                                        mApiCallBack.onSucess(apiData.getNotifications().size() - visibleCount);
                                    }

//
                                } else if (apiData.getStatuscode() == -1) {
                                    if (!apiData.getVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, apiData.getMsg());
                                    }
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    //  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void GetActiveService(final Activity context, LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum,
                                 AppPreferences mAppPreferences, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<OpTypeResponse> call = git.GetOpTypes(new BalanceRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<OpTypeResponse>() {

                @Override
                public void onResponse(Call<OpTypeResponse> call, retrofit2.Response<OpTypeResponse> response) {
                    if (response.isSuccessful()) {
                        try {
                            OpTypeResponse apiData = response.body();
                            if (apiData != null) {
                                if (apiData.getStatuscode() == 1) {


                                    if (apiData.getData() != null && apiData.getData().getAssignedOpTypes() != null &&
                                            apiData.getData().getAssignedOpTypes().size() > 0) {

                                        int parentId = 0;

                                        ArrayList<AssignedOpType> retailerSericeOption = new ArrayList<>();
                                        ArrayList<AssignedOpType> retailerReportOption = new ArrayList<>();
                                        ArrayList<AssignedOpType> otherReportOption = new ArrayList<>();
                                        ArrayList<AssignedOpType> utilityRetailerOption = new ArrayList<>();
                                        ArrayList<AssignedOpType> utilityFOSOption = new ArrayList<>();
                                        ArrayList<AssignedOpType> utilityOtherOption = new ArrayList<>();
                                        ArrayList<AssignedOpType> bankingOption = new ArrayList<>();


                                        boolean isDMTEnable = false;
                                        boolean isDMTActive = false;
                                        boolean isDMTServiceActive = false;
                                        boolean isAdditionalService = false;
                                        boolean isPaidService = false;
                                        boolean isAccountStatment = LoginDataResponse.isAccountStatement();


                                        ArrayList<Integer> groupServiceid = new ArrayList<>();

                                        // Create a static item for "Add Money" and add it to bankingOption
                                        AssignedOpType addMoneyItem = new AssignedOpType(786, 0, "Add Money", "Bill Payment", true, true, false, false, true, "Admin", "7466001234", "7466001234", new ArrayList<>());

                                        for (AssignedOpType item : apiData.getData().getAssignedOpTypes()) {


                                            if (item.getServiceID() == 14) {
                                                isDMTEnable = true;
                                                isDMTActive = item.getIsActive();
                                                isDMTServiceActive = item.getIsServiceActive();
                                                isAdditionalService = item.isAdditionalServiceType();
                                                isPaidService = item.isPaidAdditional();
                                                bankingOption.add(item);

                                            } else if (item.getServiceID() == 22) {
                                                bankingOption.add(item);

                                            } else if (item.getServiceID() == 44 && !item.getIsDisplayService()) {
                                                bankingOption.add(item);

                                            } else if (item.getServiceID() == 13 && !item.getIsDisplayService()) {
                                                bankingOption.add(item);

                                            } else if ((item.getServiceID() == 62) && !item.getIsDisplayService()) {
                                                bankingOption.add(item);

                                            } else {
                                                if (item.getIsDisplayService() && !groupServiceid.contains(item.getServiceID())) {
                                                    ArrayList<AssignedOpType> selectedSubAssignOpType = new ArrayList<>();
                                                    for (AssignedOpType subItem : apiData.getData().getAssignedOpTypes()) {
                                                        if (subItem.getParentID() == item.getParentID() && subItem.getIsDisplayService()) {
                                                            groupServiceid.add(subItem.getServiceID());
                                                            selectedSubAssignOpType.add(subItem);
                                                        }
                                                    }
                                                    if (item.getParentID() == 34) {
                                                        bankingOption.add(new AssignedOpType(item.getServiceID(), item.getParentID(), item.getName(), item.getService(),
                                                                item.getIsServiceActive(), item.getIsActive(), item.isAdditionalServiceType(), item.isPaidAdditional(), item.getIsDisplayService(), item.getUpline(),
                                                                item.getUplineMobile(), item.getCcContact(), selectedSubAssignOpType));
                                                    } else {
                                                        retailerSericeOption.add(new AssignedOpType(item.getServiceID(), item.getParentID(), item.getName(), item.getService(),
                                                                item.getIsServiceActive(), item.getIsActive(), item.isAdditionalServiceType(), item.isPaidAdditional(), item.getIsDisplayService(), item.getUpline(),
                                                                item.getUplineMobile(), item.getCcContact(), selectedSubAssignOpType));
                                                    }
                                                } else if (!groupServiceid.contains(item.getServiceID())) {
                                                    if (item.getParentID() == 34) {
                                                        bankingOption.add(new AssignedOpType(item.getServiceID(), item.getParentID(), item.getName(),
                                                                item.getService(), item.getIsServiceActive(), item.getIsActive(), item.isAdditionalServiceType(), item.isPaidAdditional(), item.getIsDisplayService(),
                                                                item.getUpline(), item.getUplineMobile(), item.getCcContact(), new ArrayList<>()));
                                                    } else {
                                                        retailerSericeOption.add(new AssignedOpType(item.getServiceID(), item.getParentID(), item.getName(),
                                                                item.getService(), item.getIsServiceActive(), item.getIsActive(), item.isAdditionalServiceType(), item.isPaidAdditional(), item.getIsDisplayService(),
                                                                item.getUpline(), item.getUplineMobile(), item.getCcContact(), new ArrayList<>()));
                                                    }
                                                }


                                            }


                                        }

                                        if (response.body().isAddMoneyEnable()) {
                                            bankingOption.add(addMoneyItem);
                                        }
                                        utilityRetailerOption.addAll(apiData.getData().getUtilityRetailerOptions());
                                        utilityFOSOption.addAll(apiData.getData().getUtilityFOSOptions(isAccountStatment));
                                        utilityOtherOption.addAll(apiData.getData().getUtilityOtherOptions(isAccountStatment));
                                        otherReportOption.addAll(apiData.getData().getOtherReportSeriveOptions(isDMTEnable, isDMTActive, isDMTServiceActive, isAdditionalService, isPaidService));
//                                        retailerReportOption.addAll(apiData.getData().getRetailerReportOptions(isDMTEnable,
//                                                isDMTActive, isDMTServiceActive, isAccountStatment, isAdditionalService, isPaidService));

                                        mDashBoardServiceReportOptions = new DashBoardServiceReportOptions(apiData.isAddMoneyEnable(), apiData.isECollectEnable(),
                                                apiData.isUPI(), apiData.isUPIQR(), apiData.isDMTWithPipe(),
                                                retailerSericeOption,
                                                retailerReportOption, otherReportOption, utilityRetailerOption, utilityFOSOption, utilityOtherOption,
                                                bankingOption);

                                        mAppPreferences.set(ApplicationConstant.INSTANCE.activeServicePref, new Gson().toJson(mDashBoardServiceReportOptions));
                                        mAppPreferences.set(ApplicationConstant.INSTANCE.isUpi, apiData.isUPI());
                                        mAppPreferences.set(ApplicationConstant.INSTANCE.isPaymentGatway, apiData.isPaymentGatway());


                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(mDashBoardServiceReportOptions);
                                        }
                                    } else {
                                        Error(context, context.getResources().getString(R.string.some_thing_error));
                                    }


//
                                } else if (apiData.getStatuscode() == -1) {
                                    if (!apiData.getVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        // Error(context, apiData.getMsg() + "");
                                    }
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<OpTypeResponse> call, Throwable t) {


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void GetActiveServiceIndustryWise(final Activity context, LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum,
                                             AppPreferences mAppPreferences, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<OpTypeIndustryWiseResponse> call = git.GetOpTypesIndustryWise(new BalanceRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<OpTypeIndustryWiseResponse>() {

                @Override
                public void onResponse(Call<OpTypeIndustryWiseResponse> call, retrofit2.Response<OpTypeIndustryWiseResponse> response) {
                    if (response.isSuccessful()) {
                        try {
                            OpTypeIndustryWiseResponse apiData = response.body();
                            if (apiData != null) {
                                if (apiData.getStatuscode() == 1) {
                                    if (apiData.getData() != null && apiData.getData().size() > 0) {
                                        mAppPreferences.set(ApplicationConstant.INSTANCE.activeServicePref, new Gson().toJson(apiData));
                               /*         mAppPreferences.set(ApplicationConstant.INSTANCE.isUpi, apiData.isUPI());
                                        mAppPreferences.set(ApplicationConstant.INSTANCE.isPaymentGatway, apiData.isPaymentGatway());
                                        mAppPreferences.set(ApplicationConstant.INSTANCE.isECollectEnable, apiData.isECollectEnable());*/
                                        // mAppPreferences.set(ApplicationConstant.INSTANCE.isDMTWithPipePref, apiData.isDMTWithPipe());
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(apiData);
                                        }
                                    } else {
                                        Error(context, "Menu Options is not available");
                                    }
//
                                } else if (apiData.getStatuscode() == -1) {
                                    if (!apiData.isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        // Error(context, apiData.getMsg() + "");
                                    }
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<OpTypeIndustryWiseResponse> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void WalletType(final Activity context, LoginResponse LoginDataResponse,
                           AppPreferences mAppPreferences, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<WalletTypeResponse> call = git.GetWalletType(new BalanceRequest("", LoginDataResponse.getData().getSlabID(),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, getIMEI(context, mAppPreferences), "", BuildConfig.VERSION_NAME,
                    getSerialNo(context, mAppPreferences), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<WalletTypeResponse>() {

                @Override
                public void onResponse(Call<WalletTypeResponse> call, retrofit2.Response<WalletTypeResponse> response) {
                    if (response.isSuccessful()) {
                        try {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    mWalletTypeResponse = response.body();
                                    if (mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {


                                        if (LoginDataResponse.getData().getRoleID() == 3 ||
                                                LoginDataResponse.getData().getRoleID() == 2) {

                                            for (int i = 0; i < mWalletTypeResponse.getWalletTypes().size(); i++) {
                                                if (mWalletTypeResponse.getWalletTypes().get(i).getId() == 6) {
                                                    mWalletTypeResponse.getWalletTypes().remove(i);
                                                    break;
                                                }

                                            }
                                        }

                                        if (mApiCallBack != null) {

                                            mApiCallBack.onSucess(mWalletTypeResponse);
                                        }

                                    }
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.walletType, new Gson().toJson(mWalletTypeResponse));

                                } else if (mWalletTypeResponse.getStatuscode() == -1) {
                                    if (!mWalletTypeResponse.getVersionValid()) {
                                        if (!(context instanceof HomeDashActivity)) {
                                            versionDialog(context);
                                        }
                                    } else {
                                        if (!(context instanceof HomeDashActivity)) {
                                            Error(context, mWalletTypeResponse.getMsg());
                                        }
                                    }
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (!(context instanceof HomeDashActivity)) {
                                Error(context, e.getMessage());
                            }
                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<WalletTypeResponse> call, Throwable t) {
                    if (!(context instanceof HomeDashActivity)) {

                        try {
                            apiFailureError(context, t);

                        } catch (IllegalStateException ise) {
                            Error(context, ise.getMessage());

                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (!(context instanceof HomeDashActivity)) {
                Error(context, e.getMessage());
            }
        }

    }


    public void getUserDetail(final Activity context, CustomLoader loader, LoginResponse mLoginDataResponse,
                              String deviceId, String deviceSerialNum, AppPreferences mAppPreferences,
                              final ApiCallBack mApiCallBack) {
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

                                if (mGetUserResponse.getStatuscode() == 1) {
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.UserDetailPref, new Gson().toJson(mGetUserResponse));
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.IsRealApiPref, mGetUserResponse.getUserInfo().isRealAPI());

                                    mLoginDataResponse.getData().setDoubleFactor(mGetUserResponse.getUserInfo().isDoubleFactor());
                                    mTempLoginDataResponse = mLoginDataResponse;
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.LoginPref, new Gson().toJson(mLoginDataResponse));
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mGetUserResponse);
                                    }
                                } else if (response.body().getStatuscode() == -1) {

                                    Error(context, mGetUserResponse.getMsg());
                                } else if (response.body().getStatuscode() == 0) {

                                    Error(context, mGetUserResponse.getMsg());
                                } else {

                                    Error(context, mGetUserResponse.getMsg());
                                }

                            } else {

                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
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

                        apiFailureError(context, t);


                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            Error(context, context.getResources().getString(R.string.some_thing_error));
        }

    }

    public void GetCompanyProfile(final Activity context, CustomLoader loader, LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, AppPreferences mAppPreferences, final ApiCallBack mApiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.GetCompanyProfile(new BasicRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            mCompanyProfileResponse = response.body();
                            if (mCompanyProfileResponse != null) {
                                if (mCompanyProfileResponse.getStatuscode() == 1) {
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.contactUsPref, new Gson().toJson(mCompanyProfileResponse));

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mCompanyProfileResponse);
                                    }

//
                                } else if (mCompanyProfileResponse.getStatuscode() == -1) {
                                    if (!mCompanyProfileResponse.getVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, mCompanyProfileResponse.getMsg());
                                    }
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
        }

    }

    public void FetchBillApi(final Activity context, String customerNo, String oid, String accountNum, String o1, String o2, String o3,
                             String o4, String geoCode, String amount, final AlertDialog loader, View submitBtn,
                             LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final ApiCallBackTwoMethod mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<FetchBillResponse> call = git.FetchBill(new FetchBillRequest(customerNo, oid, accountNum, o1, o2, o3,
                    o4, geoCode, amount, LoginDataResponse.getData().getOutletID() + "",
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME,
                    deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<FetchBillResponse>() {

                @Override
                public void onResponse(Call<FetchBillResponse> call, retrofit2.Response<FetchBillResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {

                            FetchBillResponse apiData = response.body();
                            if (apiData != null) {
                                if (apiData.getStatuscode() == 1) {
                                    if (apiData.getbBPSResponse() != null && apiData.getbBPSResponse().getStatuscode() == 1) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(apiData);
                                        }

                                    } else if (apiData.getbBPSResponse() != null && apiData.getbBPSResponse().getStatuscode() != 1) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(apiData);
                                        }
                                    }
                                } else if (apiData.getStatuscode() == -1) {
                                    if (!apiData.getVersionValid()) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(apiData.getMsg());
                                        }
                                        versionDialog(context);
                                    } else {
                                        if (mApiCallBack != null) {
                                            if (apiData.getMsg() != null) {
                                                mApiCallBack.onError(apiData.getMsg());
                                                Error(context, apiData.getMsg());
                                            } else if (apiData.getbBPSResponse() != null && apiData.getbBPSResponse().getErrorMsg() != null) {
                                                mApiCallBack.onError(apiData.getbBPSResponse().getErrorMsg());
                                                Error(context, apiData.getbBPSResponse().getErrorMsg());
                                            } else {
                                                mApiCallBack.onError(context.getResources().getString(R.string.some_thing_error));
                                                Error(context, context.getResources().getString(R.string.some_thing_error));
                                            }

                                        }
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        if (apiData.getMsg() != null) {
                                            mApiCallBack.onError(apiData.getMsg());
                                            Error(context, apiData.getMsg());
                                        } else if (apiData.getbBPSResponse() != null && apiData.getbBPSResponse().getErrorMsg() != null) {
                                            mApiCallBack.onError(apiData.getbBPSResponse().getErrorMsg());
                                            Error(context, apiData.getbBPSResponse().getErrorMsg());
                                        } else {
                                            mApiCallBack.onError(context.getResources().getString(R.string.some_thing_error));
                                            Error(context, context.getResources().getString(R.string.some_thing_error));
                                        }

                                    }

                                }

                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(context.getResources().getString(R.string.some_thing_error));
                                }
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(response.message());
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<FetchBillResponse> call, Throwable t) {

                    if (mApiCallBack != null) {
                        mApiCallBack.onError(t.getMessage());
                    }
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }

    }


    public void GetBeneficiary(final Activity context, String MobileNumber, LoginResponse LoginDataResponse,
                               final CustomLoader loader, AppPreferences mAppPreferences,
                               String deviceId, String deviceSerialNum, ApiResponseCallBack mApiResponseCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.GetBeneficiary(new GetSenderRequest(new SenderObject(MobileNumber),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    //  mAppPreferences.set(ApplicationConstant.INSTANCE.beneficiaryListPref, new Gson().toJson(response.body()));

                                    if (response.body().getBenis() != null && response.body().getBenis().size() > 0) {
                                        if (mApiResponseCallBack != null) {
                                            mApiResponseCallBack.onSucess(response.body().getBenis());
                                        }
                                    } else {
                                        if (mApiResponseCallBack != null) {
                                            mApiResponseCallBack.onError(ERROR_OTHER);
                                        }
                                    }
                           /* Intent i = new Intent(context, BeneficiaryListScreen.class);
                            context.startActivity(i);*/
                                } else {
                                    if (mApiResponseCallBack != null) {
                                        mApiResponseCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            } else {
                                if (mApiResponseCallBack != null) {
                                    mApiResponseCallBack.onError(ERROR_OTHER);
                                }
                            }
                        } else {
                            if (mApiResponseCallBack != null) {
                                mApiResponseCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                        if (mApiResponseCallBack != null) {
                            mApiResponseCallBack.onError(ERROR_OTHER);
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiResponseCallBack != null) {
                                mApiResponseCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiResponseCallBack != null) {
                                mApiResponseCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiResponseCallBack != null) {
                                mApiResponseCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        if (mApiResponseCallBack != null) {
                            mApiResponseCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());

            if (mApiResponseCallBack != null) {
                mApiResponseCallBack.onError(ERROR_OTHER);
            }
        }

    }

    public void GetBeneficiaryWithPipe(final Activity context, String oid, String MobileNumber, LoginResponse LoginDataResponse,
                                       final CustomLoader loader, AppPreferences mAppPreferences,
                                       String deviceId, String deviceSerialNum, ApiResponseCallBack mApiResponseCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.GetBeneficiaryWithPipe(new GetSenderRequest(oid, new SenderObject(MobileNumber),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    //  mAppPreferences.set(ApplicationConstant.INSTANCE.beneficiaryListPref, new Gson().toJson(response.body()));

                                    if (response.body().getBenis() != null && response.body().getBenis().size() > 0) {
                                        if (mApiResponseCallBack != null) {
                                            mApiResponseCallBack.onSucess(response.body().getBenis());
                                        }
                                    } else {
                                        if (mApiResponseCallBack != null) {
                                            mApiResponseCallBack.onError(ERROR_OTHER);
                                        }
                                    }
                           /* Intent i = new Intent(context, BeneficiaryListScreen.class);
                            context.startActivity(i);*/
                                } else {
                                    if (mApiResponseCallBack != null) {
                                        mApiResponseCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            } else {
                                if (mApiResponseCallBack != null) {
                                    mApiResponseCallBack.onError(ERROR_OTHER);
                                }
                            }
                        } else {
                            if (mApiResponseCallBack != null) {
                                mApiResponseCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                        if (mApiResponseCallBack != null) {
                            mApiResponseCallBack.onError(ERROR_OTHER);
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiResponseCallBack != null) {
                                mApiResponseCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiResponseCallBack != null) {
                                mApiResponseCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiResponseCallBack != null) {
                                mApiResponseCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        if (mApiResponseCallBack != null) {
                            mApiResponseCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());

            if (mApiResponseCallBack != null) {
                mApiResponseCallBack.onError(ERROR_OTHER);
            }
        }

    }

    public void GetChargedAmount(final Activity context, final String Amount, final CustomLoader loader,
                                 LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum,
                                 AppPreferences mAppPreferences, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<DMTChargedAmountResponse> call = git.GetChargedAmount(new GetChargedAmountRequest(Amount,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<DMTChargedAmountResponse>() {

                @Override
                public void onResponse(Call<DMTChargedAmountResponse> call, retrofit2.Response<DMTChargedAmountResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<DMTChargedAmountResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }

    }

    public void GetChargedAmountWithPipe(final Activity context, String oid, final String Amount, final CustomLoader loader,
                                         LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum,
                                         AppPreferences mAppPreferences, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<DMTChargedAmountResponse> call = git.GetChargedAmountWithPipe(new GetChargedAmountRequest(oid, Amount,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<DMTChargedAmountResponse>() {

                @Override
                public void onResponse(Call<DMTChargedAmountResponse> call, retrofit2.Response<DMTChargedAmountResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<DMTChargedAmountResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }

    }

    public void SendMoney(final Activity context, String securityKey, String beneID, String mobileNo, String ifsc, String accountNo,
                          String amount, String channel, String bank, String beneName,
                          final CustomLoader loader, AppPreferences mAppPreferences,
                          LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.SendMoney(new SendMoneyRequest(new RequestSendMoney(beneID, mobileNo, ifsc, accountNo,
                    amount, channel, bank, beneName), securityKey, LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    // Pending
                                   /* if (response.body().getGroupID() != null && !response.body().getGroupID().isEmpty()) {
                                        GetDMTReceipt(context, response.body().getGroupID(), "All", LoginDataResponse, loader, mAppPreferences);

                                    } else {*/
                                    isReadyToUpdateBalance = true;
                                    String detailTxt = "";
                                    if (bank != null && !bank.isEmpty()) {
                                        detailTxt = "Bank : " + bank;
                                    }
                                    if (accountNo != null && !accountNo.isEmpty()) {
                                        detailTxt = detailTxt + "\nA/C Number : " + accountNo;
                                    }
                                    if (amount != null && !amount.isEmpty()) {
                                        detailTxt = detailTxt + "\nAmount : \u20B9" + amount;
                                    }
                                    if (response.body().getTransactionID() != null && !response.body().getTransactionID().isEmpty()) {
                                        detailTxt = detailTxt + "\nTxn. Id : " + response.body().getTransactionID();
                                    }
                                    new CustomAlertDialog(context).DmtPendingDialog((response.body().getMsg()).trim(), detailTxt, new CustomAlertDialog.DialogCallBack() {
                                        @Override
                                        public void onPositiveClick() {
                                            Intent i = new Intent(context, DMRReportActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                            context.startActivity(i);
                                            context.finish();
                                        }

                                        @Override
                                        public void onNegativeClick() {
                                            context.finish();
                                        }
                                    });
                                    /*}*/

                                } else if (response.body().getStatuscode() == 2) {
                                    // Success
                                    isReadyToUpdateBalance = true;
                                    if (response.body().getGroupID() != null && !response.body().getGroupID().isEmpty()) {
                                        GetDMTReceipt(context, response.body().getGroupID(), "All",
                                                LoginDataResponse, deviceId, deviceSerialNum, loader, mAppPreferences);

                                    } else {
                                        Successfulok(response.body().getMsg(), context);
                                    }

                                } else if (response.body().getStatuscode() == 3) {
                                    //Error
                                    if (response.body().getGroupID() != null && !response.body().getGroupID().isEmpty()) {
                                        GetDMTReceipt(context, response.body().getGroupID(), "All",
                                                LoginDataResponse, deviceId, deviceSerialNum, loader, mAppPreferences);

                                    } else {
                                        Error(context, response.body().getMsg());
                                    }

                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }
    }

    public void SendMoneyWithPipe(final Activity context, String oid, String securityKey, String beneID, String mobileNo, String ifsc, String accountNo,
                                  String amount, String channel, String bank, String beneName,
                                  final CustomLoader loader, AppPreferences mAppPreferences,
                                  LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.SendMoneyWithPipe(new SendMoneyRequest(oid, new RequestSendMoney(oid, oid,
                    beneID, mobileNo, ifsc, accountNo, amount, channel, bank, beneName),
                    securityKey, LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    // Pending
                                   /* if (response.body().getGroupID() != null && !response.body().getGroupID().isEmpty()) {
                                        GetDMTReceipt(context, response.body().getGroupID(), "All", LoginDataResponse, loader, mAppPreferences);

                                    } else {*/
                                    isReadyToUpdateBalance = true;
                                    String detailTxt = "";
                                    if (bank != null && !bank.isEmpty()) {
                                        detailTxt = "Bank : " + bank;
                                    }
                                    if (accountNo != null && !accountNo.isEmpty()) {
                                        detailTxt = detailTxt + "\nA/C Number : " + accountNo;
                                    }
                                    if (amount != null && !amount.isEmpty()) {
                                        detailTxt = detailTxt + "\nAmount : \u20B9" + amount;
                                    }
                                    if (response.body().getTransactionID() != null && !response.body().getTransactionID().isEmpty()) {
                                        detailTxt = detailTxt + "\nTxn. Id : " + response.body().getTransactionID();
                                    }
                                    new CustomAlertDialog(context).DmtPendingDialog((response.body().getMsg()).trim(), detailTxt, new CustomAlertDialog.DialogCallBack() {
                                        @Override
                                        public void onPositiveClick() {
                                            Intent i = new Intent(context, DMRReportActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                            context.startActivity(i);
                                            context.finish();
                                        }

                                        @Override
                                        public void onNegativeClick() {

                                            context.finish();
                                        }
                                    });
                                    /*}*/

                                } else if (response.body().getStatuscode() == 2) {
                                    // Success
                                    isReadyToUpdateBalance = true;
                                    if (response.body().getGroupID() != null && !response.body().getGroupID().isEmpty()) {
                                        GetDMTReceipt(context, response.body().getGroupID(), "All",
                                                LoginDataResponse, deviceId, deviceSerialNum, loader, mAppPreferences);

                                    } else {
                                        Successfulok(response.body().getMsg(), context);
                                    }

                                } else if (response.body().getStatuscode() == 3) {
                                    //Error
                                    if (response.body().getGroupID() != null && !response.body().getGroupID().isEmpty()) {
                                        GetDMTReceipt(context, response.body().getGroupID(), "All",
                                                LoginDataResponse, deviceId, deviceSerialNum, loader, mAppPreferences);

                                    } else {
                                        Error(context, response.body().getMsg());
                                    }

                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }
    }


    public void GetDMTReceipt(final Activity context, final String GroupID, final String flag,
                              LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum,
                              final CustomLoader loader, AppPreferences mAppPreferences) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<DMTReceiptResponse> call = git.GetDMTReceipt(new GetDMTReceiptRequest(GroupID,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<DMTReceiptResponse>() {

                @Override
                public void onResponse(Call<DMTReceiptResponse> call, retrofit2.Response<DMTReceiptResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getTransactionDetail() != null && response.body().getTransactionDetail().getLists() != null && response.body().getTransactionDetail().getLists().size() > 0) {
                                        Intent i = new Intent(context, DMRRecieptActivity.class);
                                        i.putExtra("response", response.body());
                                        i.putExtra("flag", flag);
                                        context.startActivity(i);

                                        if (flag.equalsIgnoreCase("All")) {
                                            context.finish();
                                        }
                                    } else {
                                        Error(context, "Failed!!");
                                    }

                                    // Successful(context, response.body().getMsg());
                                } else if (response.body().getStatuscode() == -1) {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                } else if (response.body().getStatuscode() == 3) {
                                    Error(context, response.body().getMsg());
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<DMTReceiptResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }

    }

    public void DMTReport(final Activity context, int status, String topRow, boolean isRecent, String apiid,
                          String fromDate, String toDate, String transactionID, String accountNo, String childMobileNo,
                          final CustomLoader loader, LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call;
            if (LoginDataResponse.getData().getLoginTypeID() == 3) {
                call = git.DMTReportForEmployee(new DmrRequest(topRow, status, apiid, fromDate, toDate, transactionID, accountNo, childMobileNo, false,
                        LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                        ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                        LoginDataResponse.getData().getLoginTypeID(), isRecent));

            } else {
                call = git.DMTReport(new DmrRequest(topRow, status, apiid, fromDate, toDate, transactionID, accountNo, childMobileNo, false,
                        LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                        ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                        LoginDataResponse.getData().getLoginTypeID(), isRecent));
            }
            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else if (response.body().getStatuscode() == -1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                } else if (response.body().getStatuscode() == 3) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    Error(context, response.body().getMsg());
                                }

                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, e.getMessage());
                    }


                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            e.printStackTrace();
            Error(context, e.getMessage());
        }

    }


    public void Deletebeneficiary(final Activity context, final String MobileNumber, String beneID, final CustomLoader loader,
                                  LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.DeleteBeneficiary(new GetSenderRequest(new SenderObject(MobileNumber), new BeneDetail(beneID),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    try {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    Successful(context, response.body().getMsg());
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }

    }

    public void DeletebeneficiaryWithPipe(final Activity context, TextView timerTv, TextView resendCodeTv, String oid, String sid, String otp, final String MobileNumber, String beneID, final CustomLoader loader,
                                          LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.DeleteBeneficiaryWithPipe(new GetSenderRequest(oid, sid, otp, new SenderObject(MobileNumber), new BeneDetail(beneID),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    try {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {

                                    if (response.body().isOTPRequired()) {
                                        if (timerTv != null && resendCodeTv != null) {
                                            setTimer(timerTv, resendCodeTv);
                                        } else {
                                            openOtpDialog(context, response.body().isResendAvailable(), MobileNumber, new ApiFintechUtilMethods.DialogOTPCallBack() {
                                                @Override
                                                public void onPositiveClick(String otpValue, TextView timerTv, TextView resendCodeTv, Dialog mDialog) {
                                                    loader.show();
                                                    DeletebeneficiaryWithPipe(context, null, null, oid, sid, otpValue, MobileNumber, beneID, loader,
                                                            LoginDataResponse, deviceId, deviceSerialNum, mApiCallBack);

                                                }

                                                @Override
                                                public void onResetCallback(String otpValue, TextView timerTv, TextView resendCodeTv, Dialog mDialog) {
                                                    loader.show();
                                                    DeletebeneficiaryWithPipe(context, timerTv, resendCodeTv, oid, sid, "", MobileNumber, beneID, loader,
                                                            LoginDataResponse, deviceId, deviceSerialNum, mApiCallBack);
                                                }
                                            });
                                        }
                                    } else {
                                        Successful(context, response.body().getMsg());
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    }
                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }

    }

    public void AddBeneficiary(final Activity context, String SenderNO, String BeneMobileNO, String beniName, String ifsc, String accountNo,
                               String bankName, final String bankId, final CustomLoader loader, final Activity activity,
                               LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.AddBeneficiary(new GetSenderRequest(new SenderObject(SenderNO), new BeneDetail(BeneMobileNO, beniName, ifsc, accountNo, bankName, bankId),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    try {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                    Successfulok(response.body().getMsg(), activity);

                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void AddBeneficiaryWithPipe(final Activity context, String oid, String sid, String otp, String SenderNO, String BeneMobileNO, String beniName, String ifsc, String accountNo,
                                       String bankName, final String bankId, final CustomLoader loader, final Activity activity,
                                       LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.AddBeneficiaryWithPipe(new GetSenderRequest(oid, sid, otp, new SenderObject(SenderNO),
                    new BeneDetail(BeneMobileNO, beniName, ifsc, accountNo, bankName, bankId, 2),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    try {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                    Successfulok(response.body().getMsg(), activity);

                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            e.printStackTrace();
        }

    }

    public void VerifyAccount(final Activity context, String mobileNo, String ifsc, String accountNo,
                              String bankName, final CustomLoader loader, LoginResponse LoginDataResponse,
                              String deviceId, String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.VerifyAccount(new GetSenderRequest(new SenderObject(mobileNo), new BeneDetail(mobileNo, ifsc, accountNo, bankName),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    try {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 2) {
                                    isReadyToUpdateBalance = true;
                                    Successful(context, "Verifications successfully done.");
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body().getBeneName());
                                    }

                                } else if (response.body().getStatuscode() == 3 || response.body().getStatuscode() == -1) {
                                    Error(context, response.body().getMsg());
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void VerifyAccountWithPipe(final Activity context, String oid, String beneName, String bankId, String mobileNo, String ifsc, String accountNo,
                                      String bankName, final CustomLoader loader, LoginResponse LoginDataResponse,
                                      String deviceId, String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.VerifyAccountWithPipe(new GetSenderRequest(oid, new SenderObject(mobileNo),
                    new BeneDetail(mobileNo, beneName, ifsc, accountNo, bankName, bankId),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    try {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 2) {
                                    isReadyToUpdateBalance = true;
                                    Successful(context, "Verifications successfully done.");
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body().getBeneName());
                                    }

                                } else if (response.body().getStatuscode() == 3 || response.body().getStatuscode() == -1) {
                                    Error(context, response.body().getMsg());
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void GetLapuRealCommission(final Activity context, String oid, LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.GetRealLapuCommission(new LapuRealCommissionRequest(oid,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatuscode() == 1 && response.body().getRealLapuCommissionSlab() != null) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body().getRealLapuCommissionSlab());
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    //Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void GetCalculateLapuRealCommission(final Activity context, String oid, String amount, final CustomLoader loader,
                                               LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final ApiCallBackTwoMethod mApiCallBack) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.GetCalculatedCommission(new LapuRealCommissionRequest(oid, amount,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatuscode() == 1 && response.body().getCommissionDisplay() != null) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body().getCommissionDisplay());
                                }

                            } else if (response.body() != null && response.body().getStatuscode() != 1) {
                                Error(context, response.body().getMsg());
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError("");
                                }
                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError("");
                                }
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError("");
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError("");
                        }
                        Error(context, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    if (mApiCallBack != null) {
                        mApiCallBack.onError("");
                    }
                    try {
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (mApiCallBack != null) {
                mApiCallBack.onError("");
            }
            e.printStackTrace();
        }

    }

    public void RefundRequest(final Activity context, final String tid, final String transactionID,
                              final CustomLoader loader, String otpRequired, boolean isResend,
                              LoginResponse mLoginDataResponse, String deviceId, String deviceSerialNum, ApiCallBack mApiCallBack) {
        /*Your Code*/
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<RefundRequestResponse> call = git.RefundRequest(
                    new RefundRequestRequest(tid
                            , transactionID
                            , mLoginDataResponse.getData().getUserID()
                            , mLoginDataResponse.getData().getSessionID()
                            , mLoginDataResponse.getData().getSession()
                            , ApplicationConstant.INSTANCE.APP_ID
                            , deviceId
                            , ""
                            , BuildConfig.VERSION_NAME
                            , deviceSerialNum
                            , mLoginDataResponse.getData().getLoginTypeID()
                            , otpRequired
                            , isResend));
            call.enqueue(new Callback<RefundRequestResponse>() {
                @Override
                public void onResponse(Call<RefundRequestResponse> call, final Response<RefundRequestResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (response.body() != null && response.body().getMsg() != null) {
                                        Error(context, response.body().getMsg());
                                    } else {
                                        Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                                    }
                                }

                            } else {
                                Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(context, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<RefundRequestResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }
    }

    public void Dispute(final Activity context, final String transactionId, final String tid, final CustomLoader loader,
                        LoginResponse mLoginDataResponse, String deviceId, String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {
            loader.show();

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RefundRequestResponse> call = git.RefundRequest(new RefundRequestRequest(tid
                    , transactionId
                    , mLoginDataResponse.getData().getUserID()
                    , mLoginDataResponse.getData().getSessionID()
                    , mLoginDataResponse.getData().getSession()
                    , ApplicationConstant.INSTANCE.APP_ID
                    , deviceId
                    , ""
                    , BuildConfig.VERSION_NAME
                    , deviceSerialNum
                    , mLoginDataResponse.getData().getLoginTypeID()
                    , ""
                    , false));

            call.enqueue(new Callback<RefundRequestResponse>() {

                @Override
                public void onResponse(Call<RefundRequestResponse> call, retrofit2.Response<RefundRequestResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                    Successful(context, response.body().getMsg());


                                } else if (response.body().getStatuscode() == -1) {
                                    if (!response.body().getVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(context, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<RefundRequestResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void DoubleFactorOtp(final Activity context, boolean isDoubleFactor, final String otp, String reffid,
                                final CustomLoader loader, LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum,
                                final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<DFStatusResponse> call = git.ChangeDFStatus(new DFStatusRequest(isDoubleFactor, otp, reffid,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<DFStatusResponse>() {

                @Override
                public void onResponse(Call<DFStatusResponse> call, retrofit2.Response<DFStatusResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else if (response.body().getStatuscode() == -1) {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<DFStatusResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void EnableDisableRealApi(final Activity context, boolean isRealApi, final CustomLoader loader, LoginResponse LoginDataResponse,
                                     String deviceId, String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call = git.ChangeRealAPIStatus(new RealApiChangeRequest(isRealApi,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (!response.body().getVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(context, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            e.printStackTrace();
        }

    }

    public void DTHSubscription(final Activity context, boolean isBBPS, boolean isReal, int Opid, int pid, String surname, String gender, int areaId, final String opName, String customer, String address, String pincode,
                                String customerNo, String GeoCode, String securityKey, DthPackage mthPackage, LoginResponse LoginDataResponse,
                                final CustomLoader loader, AppPreferences mAppPreferences, String deviceId, String deviceSerialNum, ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeResponse> call = git.DTHSubscription(new DTHSubscriptionRequest(
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getLoginTypeID(), pid,
                    customer, customerNo, address, pincode,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(), GeoCode,
                    securityKey, isReal, surname, gender, areaId)
            );
            call.enqueue(new Callback<RechargeResponse>() {
                @Override
                public void onResponse(Call<RechargeResponse> call, final retrofit2.Response<RechargeResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    isReadyToUpdateBalance = true;
                                    // Processing(context, response.body().getMsg() + "");
                                    openRechargeSlip(context, isBBPS, mthPackage.getPackageMRP() + "", customerNo, response.body().getLiveID(),
                                            opName, response.body().getTransactionID(), "PENDING", Opid, response.body().getStatuscode(),
                                            mthPackage.getPackageName(), address + " - " + pincode, customer, false, 0);

                                } else if (response.body().getStatuscode() == 2) {
                                    isReadyToUpdateBalance = true;
                                    openRechargeSlip(context, isBBPS, mthPackage.getPackageMRP() + "", customerNo, response.body().getLiveID(),
                                            opName, response.body().getTransactionID(), "SUCCESS", Opid, response.body().getStatuscode(),
                                            mthPackage.getPackageName(), address + " - " + pincode, customer, false, 0);

                                } else if (response.body().getStatuscode() == 3) {
                                    if (response.body().getMsg().contains("(NRAF-0)")) {
                                        String dialogMsgTxt = context.getResources().getString(R.string.realapi_popup_english_dthsubscription_msg, opName, opName) + "\n\n" +
                                                context.getResources().getString(R.string.realapi_popup_hindi_dthsubscription_msg, opName);


                                        new CustomAlertDialog(context).enableRealApiDialog(dialogMsgTxt, (mobile, emailId) ->
                                                EnableDisableRealApi(context, true, loader, LoginDataResponse,
                                                        deviceId, deviceSerialNum, object ->
                                                                Successful(context, ((BasicResponse) object).getMsg())));
                                    } else {
                                        openRechargeSlip(context, isBBPS, mthPackage.getPackageMRP() + "", customerNo, response.body().getLiveID(),
                                                opName, response.body().getTransactionID(), "FAILED", Opid, response.body().getStatuscode(),
                                                mthPackage.getPackageName(), address + " - " + pincode, customer, false, 0);


                                    }
                                } else if (response.body().getStatuscode() == -1) {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess("");
                                }
                                Balancecheck(context, loader, LoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, null, null);
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);

                        } else {
                            Processing(context, "Recharge request accepted");
                        }


                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void DthSubscriptionReport(final Activity context, String opTypeId, String topValue,
                                      int status, int bookingStatus, String fromDate, String toDate, String transactionID, String accountNo,
                                      String childMobileNumnber, String isExport, boolean IsRecent,
                                      final CustomLoader loader, LoginResponse LoginDataResponse,
                                      String deviceId, String deviceSerialNum, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<DthSubscriptionReportResponse> call = git.DTHSubscriptionReport(new DthSubscriptionReportRequest(IsRecent, opTypeId, "0",
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    topValue, status, bookingStatus, fromDate, toDate, transactionID, accountNo, childMobileNumnber, isExport,
                    LoginDataResponse.getData().getUserID()
                    , LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    LoginDataResponse.getData().getLoginTypeID()));
            call.enqueue(new Callback<DthSubscriptionReportResponse>() {
                @Override
                public void onResponse(Call<DthSubscriptionReportResponse> call, final retrofit2.Response<DthSubscriptionReportResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatuscode() != null) {
                                if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<DthSubscriptionReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            e.printStackTrace();
        }
    }

    public void GetDthChannel(final Activity context, String pid, String oid, CustomLoader loader,
                              LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum,
                              AppPreferences mAppPreferences, final ApiResponseCallBack mApiCallBack) {
        try {


            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GetDthPackageResponse> call = git.DTHChannelByPackageID(new GetDthPackageChannelRequest(pid, oid,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<GetDthPackageResponse>() {

                @Override
                public void onResponse(Call<GetDthPackageResponse> call, retrofit2.Response<GetDthPackageResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatuscode() == 1) {
                                if (response.body().getDthChannels() != null && response.body().getDthChannels().size() > 0) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    Error(context, "Channel Not Found.");
                                }

                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(ERROR_OTHER);
                                }
                                if (response.body() != null && response.body().getMsg() != null) {
                                    Error(context, response.body().getMsg());
                                } else {
                                    Error(context, context.getResources().getString(R.string.some_thing_error));
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<GetDthPackageResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
            Error(context, e.getMessage());
        }

    }

    public void PincodeArea(final Activity context, String pincode, final CustomLoader loader, LoginResponse LoginDataResponse,
                            String deviceId, String deviceSerialNum, ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<AreaWithPincodeResponse> call = git.GetPincodeArea(new AreaWithPincodeRequest(pincode,
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getLoginTypeID(),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getSession())
            );
            call.enqueue(new Callback<AreaWithPincodeResponse>() {
                @Override
                public void onResponse(Call<AreaWithPincodeResponse> call, final retrofit2.Response<AreaWithPincodeResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getAreas() != null && response.body().getAreas().size() > 0) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    } else {
                                        Error(context, "Area not available or may be pincode doesn't exist.");
                                    }

                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<AreaWithPincodeResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetDthPackage(final Activity context, String oid, CustomLoader loader, LoginResponse LoginDataResponse,
                              String deviceId, String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {
            loader.show();

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GetDthPackageResponse> call = git.GetDTHPackage(new GetDthPackageRequest(oid,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<GetDthPackageResponse>() {

                @Override
                public void onResponse(Call<GetDthPackageResponse> call, retrofit2.Response<GetDthPackageResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatuscode() == 1) {
                                if (response.body().getDthPackage() != null && response.body().getDthPackage().size() > 0) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {
                                    Error(context, "Package Not Found.");
                                }

                            } else {
                                if (response.body() != null && response.body().getMsg() != null) {
                                    Error(context, response.body().getMsg());
                                } else {
                                    Error(context, context.getResources().getString(R.string.some_thing_error));
                                }
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<GetDthPackageResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }

    }


    public void CallOnboarding(final Activity context, int bioAuthType, boolean isBiometric, FragmentManager fragmentManager, final int aepsId, final String otp, final String otpRefID, final String pidData, final boolean isPan, boolean isFromRecharge, final boolean isOperatorRequired,
                               TextView timerTv, TextView resendCodeTv, Dialog mDialog, final CustomLoader mProgressDialog,
                               LoginResponse LoginDataResponse, AppPreferences mAppPreferences,
                               String deviceId, String deviceSerialNum, final ApiCallBackTwoMethod mApiCallBack) {
        CustomAlertDialog customPassDialog = new CustomAlertDialog(context);
        try {
            if (isOperatorRequired) {
                OperatorListResponse mOperatorList = getOperatorListResponse(mAppPreferences);
                if (mOperatorList != null) {
                    ArrayList<OperatorList> operatorsList = mOperatorList.getOperators();
                    if (operatorsList != null && operatorsList.size() > 0) {
                        for (OperatorList op : operatorsList) {
                            if (op.isActive() && op.getOpType() == aepsId) { //For Aeps opType is 22
                                callOnBoardOId = op.getOid();
                                break;
                            }

                        }
                    }
                }
            } else {
                callOnBoardOId = aepsId;
            }

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<OnboardingResponse> call = git.CallOnboarding(new OnboardingRequest(bioAuthType, isBiometric, otp, otpRefID, pidData, callOnBoardOId + "", LoginDataResponse.getData().getOutletID() + "",
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, mAppPreferences.getString(ApplicationConstant.INSTANCE.regFCMKeyPref), BuildConfig.VERSION_NAME,
                    deviceSerialNum, LoginDataResponse.getData().getLoginTypeID()));

            call.enqueue(new Callback<OnboardingResponse>() {
                @Override
                public void onResponse(Call<OnboardingResponse> call, Response<OnboardingResponse> response) {


                    try {

                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        if (response.isSuccessful()) {
                            OnboardingResponse mOnboardingResponse = response.body();
                            if (mOnboardingResponse != null) {

                                if (mOnboardingResponse.getOtpRefID() != null && !mOnboardingResponse.getOtpRefID().isEmpty()) {
                                    onboardingOTPReffId = mOnboardingResponse.getOtpRefID();
                                }

                                if (mOnboardingResponse.getGiurl() != null && !mOnboardingResponse.getGiurl().isEmpty()) {
                                    String webUrl = "";
                                    if (URLUtil.isValidUrl(mOnboardingResponse.getGiurl())) {
                                        webUrl = mOnboardingResponse.getGiurl();
                                    } else {
                                        webUrl = ApplicationConstant.INSTANCE.baseUrl + mOnboardingResponse.getGiurl();
                                    }
                                    if (webUrl.contains("GIRedirectApp?")) {
                                        webUrl = webUrl + "&userID=" + LoginDataResponse.getData().getUserID() +
                                                "&appid=" + ApplicationConstant.INSTANCE.APP_ID + "&imei=" + deviceId + "&regKey=&version=" + BuildConfig.VERSION_NAME +
                                                "&serialNo=" + deviceSerialNum + "&sessionID=" + LoginDataResponse.getData().getSessionID() +
                                                "&session=" + LoginDataResponse.getData().getSession() + "&loginTypeID=" + LoginDataResponse.getData().getLoginTypeID();
                                    }
                                    try {
                                        if (mDialog != null && mDialog.isShowing()) {
                                            mDialog.dismiss();
                                        }
                                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        Intent dialIntent = new Intent(Intent.ACTION_VIEW);
                                        dialIntent.setData(Uri.parse(webUrl));
                                        context.startActivity(dialIntent);
                                    }
                                } else if (mOnboardingResponse.isRedirectToExternal() &&
                                        mOnboardingResponse.getExternalURL() != null &&
                                        !mOnboardingResponse.getExternalURL().isEmpty()) {
                                    String webUrl = "";
                                    if (URLUtil.isValidUrl(mOnboardingResponse.getExternalURL())) {
                                        webUrl = mOnboardingResponse.getExternalURL();
                                    } else {
                                        webUrl = ApplicationConstant.INSTANCE.baseUrl + mOnboardingResponse.getExternalURL();
                                    }
                                    try {
                                        if (mDialog != null && mDialog.isShowing()) {
                                            mDialog.dismiss();
                                        }
                                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        Intent dialIntent = new Intent(Intent.ACTION_VIEW);
                                        dialIntent.setData(Uri.parse(webUrl));
                                        context.startActivity(dialIntent);
                                    }
                                } else if (mOnboardingResponse.isBioMetricRequired()) {
                                    if (mDialog != null && mDialog.isShowing()) {
                                        mDialog.dismiss();
                                    }
                                    isBioMatricScreenOpen = true;
                                    AEPSFingerPrintEKycDialogFragment mAEPSFingerPrintEKycDialog =
                                            new AEPSFingerPrintEKycDialogFragment();
                                    if (mOnboardingResponse.getStatuscode() == -1) {
                                        mAEPSFingerPrintEKycDialog.setOnBoardingData(context, mOnboardingResponse.getMsg(), fragmentManager, callOnBoardOId, mOnboardingResponse.getBioAuthType(),
                                                onboardingOTPReffId, mProgressDialog, LoginDataResponse, mAppPreferences, deviceId,
                                                deviceSerialNum, mApiCallBack);
                                    } else {
                                        mAEPSFingerPrintEKycDialog.setOnBoardingData(context, "", fragmentManager, callOnBoardOId, mOnboardingResponse.getBioAuthType(),
                                                onboardingOTPReffId, mProgressDialog, LoginDataResponse, mAppPreferences, deviceId,
                                                deviceSerialNum, mApiCallBack);
                                    }
                                    mAEPSFingerPrintEKycDialog.show(fragmentManager, "");
                                    //  context.startActivity(new Intent(context, AEPSFingerPrintEKycActivity.class));
                                } else if (mOnboardingResponse.isOTPRequired()) {
                                    if (mDialog != null && mDialog.isShowing()) {
                                        Successful(context, "OTP has been resend successfully");
                                        if (timerTv != null && resendCodeTv != null) {
                                            setTimer(timerTv, resendCodeTv);
                                        }
                                    } else {

                                        openOtpDialog(context, 7, LoginDataResponse.getData().getMobileNo(), new DialogOTPCallBack() {
                                            @Override
                                            public void onPositiveClick(String otpValue, TextView timerTv, TextView resendCodeTv, Dialog mDialog) {
                                                mProgressDialog.show();
                                                CallOnboarding(context, bioAuthType, false, fragmentManager, aepsId, otpValue, onboardingOTPReffId, pidData, isPan,
                                                        isFromRecharge, isOperatorRequired, timerTv, resendCodeTv, mDialog, mProgressDialog,
                                                        LoginDataResponse, mAppPreferences, deviceId, deviceSerialNum, mApiCallBack);
                                            }

                                            @Override
                                            public void onResetCallback(String otpValue, TextView timerTv, TextView resendCodeTv, Dialog mDialog) {
                                                mProgressDialog.show();
                                                CallOnboarding(context, bioAuthType, false, fragmentManager, aepsId, "", "0", "", isPan,
                                                        isFromRecharge, isOperatorRequired, timerTv, resendCodeTv, mDialog, mProgressDialog,
                                                        LoginDataResponse, mAppPreferences, deviceId, deviceSerialNum, mApiCallBack);
                                            }
                                        });

                                    }

                                } else {
                                    if (mDialog != null && mDialog.isShowing()) {
                                        mDialog.dismiss();
                                    }

                                    if (!showCallOnBoardingMsgs(context, mOnboardingResponse, customPassDialog)) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(mOnboardingResponse);
                                        }
                                    } else if (mOnboardingResponse.getStatuscode() == 1) {
                                        if (isFromRecharge) {
                                            if (mApiCallBack != null) {
                                                mApiCallBack.onSucess(mOnboardingResponse);
                                            }
                                        } else if (isPan) {
                                            if (mOnboardingResponse.getPanid() != null && !mOnboardingResponse.getPanid().isEmpty()) {
                                                mAppPreferences.set(ApplicationConstant.INSTANCE.psaIdPref, aepsId + "");

                                                if (mApiCallBack != null) {
                                                    mApiCallBack.onSucess(mOnboardingResponse);
                                                }

                                            } else {
                                                Error(context, "Pan Id is not found!!");
                                            }
                                        } else {
                                            if (isBioMatricScreenOpen &&
                                                    (mOnboardingResponse.getSdkType() == 0 || mOnboardingResponse.getSdkDetail() == null)) {
                                                mProgressDialog.show();
                                                CallOnboarding(context, 0, false, fragmentManager, aepsId, "", "0", "", isPan,
                                                        isFromRecharge, isOperatorRequired, timerTv, resendCodeTv, mDialog, mProgressDialog,
                                                        LoginDataResponse, mAppPreferences, deviceId, deviceSerialNum, mApiCallBack);
                                                isBioMatricScreenOpen = false;

                                            } else {
                                                if (mApiCallBack != null) {
                                                    mOnboardingResponse.setoId(callOnBoardOId + "");
                                                    mApiCallBack.onSucess(mOnboardingResponse);
                                                }
                                            }
                                        }
                                    } else {
                                        if (!mOnboardingResponse.getIsVersionValid()) {
                                            versionDialog(context);
                                        } else {
                                            Error(context, mOnboardingResponse.getMsg());
                                        }
                                    }

                                }

                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception ex) {
                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        Error(context, ex.getMessage());
                    }


                }

                @Override
                public void onFailure(Call<OnboardingResponse> call, Throwable t) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }

                    apiFailureError(context, t);

                }
            });
        } catch (Exception e) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            Error(context, e.getMessage());
        }

    }

    public boolean showCallOnBoardingMsgs(Activity context, OnboardingResponse mOnboardingResponse, CustomAlertDialog customPassDialog) {
        if (mOnboardingResponse.getIsDown() && mOnboardingResponse.getMsg() != null) {
            customPassDialog.showMessage(context.getResources().getString(R.string.SERVICEDOWN), mOnboardingResponse.getMsg(), R.drawable.servicedown8, 0);
            return false;
        } else if (mOnboardingResponse.getIsWaiting() && mOnboardingResponse.getMsg() != null) {
            customPassDialog.showMessage(context.getResources().getString(R.string.UNDERSCREENING), mOnboardingResponse.getMsg(), R.drawable.underscreaning7, 0);
            return false;
        } else if (mOnboardingResponse.getIsUnathorized() && mOnboardingResponse.getMsg() != null) {
            customPassDialog.showMessage(context.getResources().getString(R.string.UNAUTHORISED), mOnboardingResponse.getMsg(), R.drawable.unauthorized6, 0);
            return false;
        } else if (mOnboardingResponse.getIsIncomplete() && mOnboardingResponse.getMsg() != null) {
            customPassDialog.showMessage(context.getResources().getString(R.string.INCOMPLETE), mOnboardingResponse.getMsg(), R.drawable.incomplete5, 1);
            return false;
        } else if (mOnboardingResponse.getIsRejected() & mOnboardingResponse.getMsg() != null) {
            customPassDialog.showMessage(context.getResources().getString(R.string.REJECT), mOnboardingResponse.getMsg(), R.drawable.reject4, 1);
            return false;
        } else if (mOnboardingResponse.getIsRedirection() & mOnboardingResponse.getMsg() != null) {
            customPassDialog.showMessage(context.getResources().getString(R.string.Redirection), mOnboardingResponse.getMsg(), R.drawable.incomplete5, 1);
            return false;
        }
        return true;
    }

    public void GenerateTokenGI(final Activity context, int serviceId, final CustomLoader mProgressDialog,
                                LoginResponse LoginDataResponse, AppPreferences mAppPreferences,
                                String deviceId, String deviceSerialNum) {

        try {

            OperatorListResponse mOperatorList = new Gson().fromJson(mAppPreferences.getNonRemovalString(ApplicationConstant.INSTANCE.operatorListPref), OperatorListResponse.class);

            if (mOperatorList != null) {
                ArrayList<OperatorList> operatorsList = mOperatorList.getOperators();
                if (operatorsList != null && operatorsList.size() > 0) {
                    for (OperatorList op : operatorsList) {
                        if (op.isActive() && op.getOpType() == serviceId) { //For Aeps opType is 22
                            serviceId = op.getOid();
                            break;
                        }

                    }
                }
            }


            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<OnboardingResponse> call = git.GenerateTokenGI(new OptionalOperatorRequest(serviceId,
                    LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, deviceId, "",
                    BuildConfig.VERSION_NAME,
                    deviceSerialNum, LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()
            ));

            call.enqueue(new Callback<OnboardingResponse>() {
                @Override
                public void onResponse(Call<OnboardingResponse> call, Response<OnboardingResponse> response) {
                    try {
                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        if (response.isSuccessful()) {
                            OnboardingResponse mOnboardingResponse = response.body();
                            if (mOnboardingResponse != null) {
                                if (mOnboardingResponse.getStatuscode() == 1) {
                                    if (mOnboardingResponse.getRedirectURL() != null &&
                                            URLUtil.isValidUrl(mOnboardingResponse.getRedirectURL())) {
                                        try {

                                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mOnboardingResponse.getRedirectURL()))
                                                    .setPackage("com.android.chrome"));
                                        } catch (android.content.ActivityNotFoundException anfe) {
                                            try {
                                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setData(Uri.parse(mOnboardingResponse.getRedirectURL()));
                                                context.startActivity(intent);
                                            } catch (
                                                    android.content.ActivityNotFoundException anfe1) {
                                                Error(context, "App is not available");
                                            }
                                        }
                                    } else {
                                        Error(context, "Redirection link is not available");
                                    }
                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception ex) {
                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        Error(context, ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<OnboardingResponse> call, Throwable t) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    apiFailureError(context, t);
                }
            });
        } catch (Exception e) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            Error(context, e.getMessage());
        }

    }


    public void GetAppPurchageToken(final Activity context, final String TotalToken, final String oid, final String PANID,
                                    final CustomLoader loader, LoginResponse LoginDataResponse,
                                    String deviceId, String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.GetAppPurchageToken(new PurchaseTokenRequest(oid,
                    PANID,
                    TotalToken,
                    LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "",
                    BuildConfig.VERSION_NAME,
                    deviceSerialNum,
                    LoginDataResponse.getData().getLoginTypeID(),
                    LoginDataResponse.getData().getOutletID() + ""));

            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final Response<AppUserListResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 2) {
                                    if (response.body().getMsg() != null) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                        Successful(context, response.body().getMsg());
                                    }

                                } else if (response.body().getStatuscode() == 3) {
                                    if (response.body().getMsg() != null) {
                                        Error(context, response.body().getMsg());
                                    }
                                } else if (response.body().getStatuscode() == 1) {
                                    if (response.body().getMsg() != null) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                        Successful(context, response.body().getMsg());
                                    }
                                } else {
                                    if (response.body().getMsg() != null) {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {

                    }


                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GetUserClaimsReport(final Activity context, String topValue,
                                    String fromDate, String toDate, boolean IsRecent, final CustomLoader loader,
                                    LoginResponse LoginDataResponse, String deviceId, String deviceSerialNumber,
                                    final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetMNPStatusResponse> call = git.GetUserClaimsReport(new GetUserClaimsReportRequest(IsRecent,
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNumber,
                    topValue, fromDate, toDate,
                    LoginDataResponse.getData().getUserID()
                    , LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    LoginDataResponse.getData().getLoginTypeID()));


            call.enqueue(new Callback<GetMNPStatusResponse>() {
                @Override
                public void onResponse(Call<GetMNPStatusResponse> call, final retrofit2.Response<GetMNPStatusResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else if (response.body().getStatuscode() == -1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetMNPStatusResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }

                    if (t instanceof UnknownHostException || t instanceof IOException) {
                        NetworkError(context);
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_NETWORK);
                        }
                    } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                        ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    } else {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                            ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));
                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UserMNPRegistration(final Activity context, int Opid, String mnpmobile, String securityKey,
                                    final CustomLoader loader, LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum) {
        try {


            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<GetMNPStatusResponse> call = git.UserMNPRegistration(new UserMNPRegistrationRequest(Opid, mnpmobile,
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, LoginDataResponse.getData().getLoginTypeID(),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getSession(), securityKey)
            );
            call.enqueue(new Callback<GetMNPStatusResponse>() {
                @Override
                public void onResponse(Call<GetMNPStatusResponse> call, final retrofit2.Response<GetMNPStatusResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    Successful(context, response.body().getMnpRemark());
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            }
                        } else {

                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetMNPStatusResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);

                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());

                        } else {

                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }


                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetMNPStatus(final Activity context, final CustomLoader mProgressDialog,
                             LoginResponse LoginDataResponse, String deviceId, String deviceSerialNumber,
                             final ApiCallBack mApiCallBack) {
        CustomAlertDialog customPassDialog = new CustomAlertDialog(context);
        mProgressDialog.show();
        FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
        Call<GetMNPStatusResponse> call = git.GetMNPStatus(new BasicRequest(LoginDataResponse.getData().getUserID()
                , LoginDataResponse.getData().getLoginTypeID(),
                ApplicationConstant.INSTANCE.APP_ID, deviceId, mAppPreferences.getString(ApplicationConstant.INSTANCE.regFCMKeyPref), BuildConfig.VERSION_NAME,
                deviceSerialNumber,
                LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
        call.enqueue(new Callback<GetMNPStatusResponse>() {
            @Override
            public void onResponse(Call<GetMNPStatusResponse> call, Response<GetMNPStatusResponse> response) {


                try {
                    if (mProgressDialog != null) {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {

                                if (response.body().getMnpStatus() == 1) {
                                    if (response.body().getMnpRemark() != null) {
                                        customPassDialog.showMessageMNP(context.getResources().getString(R.string.UNDERSCREENING), response.body().getMnpRemark(), "OK", R.drawable.underscreaning7, null);
                                    }
                                } else if (response.body().getMnpStatus() == 2) {
                                    context.startActivity(new Intent(context, MNPRegisterActivity.class)
                                            .putExtra("Register", false)
                                            .putExtra("Response", response.body())
                                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                } else if (response.body().getMnpStatus() == 3) {
                                    if (response.body().getMnpRemark() != null) {
                                        customPassDialog.showMessageMNP(context.getResources().getString(R.string.REJECT), response.body().getMnpRemark(), "Re apply", R.drawable.reject4, () ->
                                                context.startActivity(new Intent(context, MNPRegisterActivity.class)
                                                        .putExtra("Register", true)
                                                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));
                                    }
                                } else {
                                    if (response.body().getMnpRemark() != null) {
                                        customPassDialog.showMessageMNP(context.getResources().getString(R.string.UNAUTHORISED), response.body().getMnpRemark(), "Register", R.drawable.incomplete5, () ->
                                                context.startActivity(new Intent(context, MNPRegisterActivity.class)
                                                        .putExtra("Register", true)
                                                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));
                                    }
                                }


                            } else {
                                if (!response.body().isVersionValid()) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg());
                                }
                            }
                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));
                        }
                    } else {

                        apiErrorHandle(context, response.code(), response.message());
                    }
                } catch (Exception ex) {
                    Error(context, ex.getMessage());
                }


            }

            @Override
            public void onFailure(Call<GetMNPStatusResponse> call, Throwable t) {
                if (mProgressDialog != null) {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                }
                try {
                    if (t instanceof UnknownHostException || t instanceof IOException) {
                        NetworkError(context);

                    } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                        ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());

                    } else {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                            ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));
                        }
                    }
                } catch (IllegalStateException ise) {
                    Error(context, ise.getMessage());

                }

            }
        });


    }


    public void UserMNPClaim(final Activity context, String oid,
                             String mnpMobile, String refid, String amount, final CustomLoader loader,
                             LoginResponse LoginDataResponse, String deviceId, String deviceSerialNum, final ApiCallBackTwoMethod mApiCallBack) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<BasicResponse> call = git.UserMNPClaim(new UserMNPClaimRequest(refid,
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    oid, mnpMobile, amount,
                    LoginDataResponse.getData().getUserID()
                    , LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    LoginDataResponse.getData().getLoginTypeID()));


            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    Successful(context, response.body().getMsg());
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else if (response.body().getStatuscode() == -1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(response.body().getMsg());
                                    }
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
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
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    if (mApiCallBack != null) {
                        mApiCallBack.onError(t.getMessage());
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_NETWORK);
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void WTRLogReport(final Activity context, int topRows, int criteria, String criteriaText,
                             int status, String fromDate, String toDate, int dateType, String transactionID,
                             final CustomLoader loader, LoginResponse mLoginPrefResponse,
                             String deviceId, String deviceSerialNum, final ApiResponseCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            RefundLogRequest mRefundLogRequest = new RefundLogRequest("0", topRows, criteria,
                    criteriaText, status, fromDate, toDate, dateType, transactionID, mLoginPrefResponse.getData().getUserID(),
                    mLoginPrefResponse.getData().getSessionID(), mLoginPrefResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginPrefResponse.getData().getLoginTypeID());
            /* String str =new Gson().toJson(mFundDCReportRequest);*/
            Call<AppUserListResponse> call = git.WTRLog(mRefundLogRequest);
            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1 && response.body().getRefundLog() != null && response.body().getRefundLog().size() > 0) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else if (response.body().getStatuscode() == -1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    if (!response.body().getVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg());
                                    }
                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(ERROR_OTHER);
                                    }
                                    Error(context, "Report not found.");
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(ERROR_OTHER);
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }

                    if (t instanceof UnknownHostException || t instanceof IOException) {
                        NetworkError(context);
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_NETWORK);
                        }
                    } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                        ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                    } else {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(ERROR_OTHER);
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                            ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));
                        }
                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (mApiCallBack != null) {
                mApiCallBack.onError(ERROR_OTHER);
            }
            Error(context, e.getMessage());
        }
    }

    public void MakeW2RRequest(final Activity context, String tid, String transactionID, String RightAccount, final CustomLoader loader,
                               LoginResponse mLoginDataResponse, String deviceId, String deviceSerialNum, ApiCallBack mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RefundRequestResponse> call = git.MakeW2RRequest(
                    new W2RRequest(ApplicationConstant.INSTANCE.APP_ID,
                            deviceId,
                            mLoginDataResponse.getData().getLoginTypeID(),
                            "", deviceSerialNum,
                            mLoginDataResponse.getData().getSession(),
                            mLoginDataResponse.getData().getSessionID(),
                            mLoginDataResponse.getData().getUserID(),
                            BuildConfig.VERSION_NAME,
                            tid,
                            transactionID,
                            RightAccount));
            call.enqueue(new Callback<RefundRequestResponse>() {
                @Override
                public void onResponse(Call<RefundRequestResponse> call, Response<RefundRequestResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {

                                    if (response.body() != null && response.body().getMsg() != null) {
                                        Successful(context, response.body().getMsg());
                                    }

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else if (response.body().getStatuscode() == -1) {
                                    if (response.body() != null && response.body().getMsg() != null) {
                                        Error(context, response.body().getMsg());
                                    }
                                } else {
                                    if (response.body() != null && response.body().getMsg() != null) {
                                        Error(context, response.body().getMsg());
                                    }
                                }

                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(context, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<RefundRequestResponse> call, Throwable t) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });
        } catch (Exception ex) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }

            Error(context, ex.getMessage());
        }
    }


    public boolean isVpnConnected(Context mContext) {
        ConnectivityManager m_ConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        List<NetworkInfo> connectedNetworks = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= 21) {
            Network[] networks = m_ConnectivityManager.getAllNetworks();

            for (Network n : networks) {
                NetworkInfo ni = m_ConnectivityManager.getNetworkInfo(n);

                if (ni.isConnectedOrConnecting()) {
                    connectedNetworks.add(ni);
                }
            }
            boolean bHasVPN = false;
            for (NetworkInfo ni : connectedNetworks) {
                bHasVPN |= (ni.getType() == ConnectivityManager.TYPE_VPN);
            }
            return bHasVPN;
        } else {
            List<String> networkList = new ArrayList<>();
            try {
                for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                    if (networkInterface.isUp())
                        networkList.add(networkInterface.getName());
                }
            } catch (Exception ex) {

            }
            return networkList.contains("tun0") || networkList.contains("ppp0");
        }


    }


    public ArrayList<String> getNetworkProvider(Activity context) {
        ArrayList<String> carrierNameArray = new ArrayList<>();
        int permissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_PHONE_STATE);

        Activity activity = context;
        String networkProvider = "";

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_EXTERNAL_STORAGE);
        } else {
            TelephonyManager telephonyManager = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);

            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                    final SubscriptionManager subscriptionManager;
                    subscriptionManager = SubscriptionManager.from(context);
                    final List<SubscriptionInfo> activeSubscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
                    for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                        final CharSequence carrierName = subscriptionInfo.getCarrierName();
                        carrierNameArray.add(carrierName.toString());
                    }
                } else {
                    networkProvider = telephonyManager.getNetworkOperatorName();
                    if (networkProvider != null && !networkProvider.isEmpty()) {
                        carrierNameArray.add(networkProvider);
                    }
                }
            } catch (Exception e) {

            }
        }


        return carrierNameArray;
    }

    public void versionDialog(final Context mContext) {


        if (mContext instanceof HomeDashActivity) {
            ((HomeDashActivity) mContext).UpdateApp();
        } else {
            versionOldDialog(mContext);
        }
    }

    public void versionOldDialog(final Context context) {

        if (dialogUpdateApp != null && dialogUpdateApp.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_update_available, null);

        final View btLogin = view.findViewById(R.id.bt_login);
        dialogUpdateApp = new Dialog(context, R.style.full_screen_bg_dialog);
        dialogUpdateApp.setCancelable(false);
        dialogUpdateApp.setContentView(view);

        btLogin.setOnClickListener(v -> {
            goToVersionUpdate(context);
        });

        dialogUpdateApp.show();
    }


    private void goToVersionUpdate(Context mContext) {

        try {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));
        } catch (android.content.ActivityNotFoundException anfe) {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" +
                            BuildConfig.APPLICATION_ID)));
        }
        // finish();
    }


    private void makeLinkClickable(final Activity context, SpannableStringBuilder strBuilder, final URLSpan span) {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(span.getURL()))
                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                } catch (android.content.ActivityNotFoundException anfe) {

                }
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    public void setTextViewHTML(Activity context, TextView text, String html) {
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for (URLSpan span : urls) {
            makeLinkClickable(context, strBuilder, span);
        }
        text.setText(strBuilder);
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void rechargeConfiormDialog(Activity context,double commAmount, HashMap<String, IncentiveDetails> incentiveMap, CommissionDisplay mCommissionDisplay, final boolean isReal,
                                       final boolean isPinPass, String logo, String number, String operator, String amount, boolean isBBPS,
                                       final DialogCallBack mDialogCallBack) {
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            return;
        }

        bottomSheetDialog = new BottomSheetDialog(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_recharge_confiorm, null);

        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.setCancelable(true);

        bottomSheetDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        bottomSheetDialog.show();
        LinearLayout commView = dialogView.findViewById(R.id.commView);
        LinearLayout lapuView = dialogView.findViewById(R.id.lapuView);
        LinearLayout realView = dialogView.findViewById(R.id.realView);
        TextView lapuTitle = dialogView.findViewById(R.id.lapuTitle);
        ImageView billLogo = dialogView.findViewById(R.id.bill_logo);
        TextView realTitle = dialogView.findViewById(R.id.realTitle);
        TextView walletAmountTv = dialogView.findViewById(R.id.walletAmountTv);
        TextView balanceAmt = dialogView.findViewById(R.id.balanceAmt);
        TextView walletAmt = dialogView.findViewById(R.id.walletAmt);
        TextView requiredAmtTv = dialogView.findViewById(R.id.requiredAmt);
        TextView requiredAmtWallet = dialogView.findViewById(R.id.requiredAmtWallet);
        TextView requiredAmtWalletLabel = dialogView.findViewById(R.id.requiredAmtWalletLabel);
        LinearLayout walletView = dialogView.findViewById(R.id.walletView);
        TextView commAmountLabel = dialogView.findViewById(R.id.commAmountLabel);
        TextView commAmountText = dialogView.findViewById(R.id.commAmount);
        TextView rechargeAmountLabel = dialogView.findViewById(R.id.rechargeAmountLabel);
        TextView rechAmountWallet = dialogView.findViewById(R.id.rechAmountWallet);
        TextView UseAmtWalletLabel = dialogView.findViewById(R.id.UseAmtWalletLabel);
        TextView useAmtWallet = dialogView.findViewById(R.id.useAmtWallet);

        // TextView realAmt = dialogView.findViewById(R.id.realAmt);

        if (isBBPS) {
            billLogo.setVisibility(View.VISIBLE);
        } else {
            billLogo.setVisibility(View.GONE);
        }
        if (mCommissionDisplay != null) {
            commView.setVisibility(View.VISIBLE);
            if (isReal) {
                lapuView.setVisibility(View.GONE);
                realView.setVisibility(View.VISIBLE);
            } else {
                lapuView.setVisibility(View.VISIBLE);
                realView.setVisibility(View.GONE);
            }

            lapuTitle.setText((mCommissionDisplay.isCommType() ? "Surcharge " : "Commission ") + Utility.INSTANCE.formatedAmountWithRupees(mCommissionDisplay.getCommission() + ""));
            realTitle.setText((mCommissionDisplay.isrCommType() ? "Surcharge " : "Commission ") + Utility.INSTANCE.formatedAmountWithRupees(mCommissionDisplay.getrCommission() + ""));
        } else {
            commView.setVisibility(View.GONE);
        }

        AppCompatTextView amountTv = dialogView.findViewById(R.id.amount);
        amountTv.setText(context.getResources().getString(R.string.rupiya) + " " + amount);
        rechAmountWallet.setText(context.getResources().getString(R.string.rupiya) + " " + amount);
        walletAmountTv.setText(context.getResources().getString(R.string.rupiya) + " " + mBalanceResponse.getBalanceData().get(0).getBalance());
        balanceAmt.setText("Your Remaining Balance : "+Utility.INSTANCE.formatedAmountWithRupees(mBalanceResponse.getBalanceData().get(0).getBalance()+""));
        final View pinPassView = dialogView.findViewById(R.id.pinPassView);
        final View forPgDetails = dialogView.findViewById(R.id.forPgDetails);
        final EditText pinPassEt = dialogView.findViewById(R.id.pinPassEt);
        if (isPinPass) {
            pinPassView.setVisibility(View.VISIBLE);
        } else {
            pinPassView.setVisibility(View.GONE);
        }
        AppCompatTextView operatorTv = dialogView.findViewById(R.id.operator);
        operatorTv.setText(operator);
        AppCompatTextView numberTv = dialogView.findViewById(R.id.number);
        numberTv.setText(number);
        TextView incentive = dialogView.findViewById(R.id.incentive);
        if (incentiveMap != null && incentiveMap.size() > 0 && incentiveMap.containsKey(amount)) {
            incentive.setVisibility(View.VISIBLE);
            if (incentiveMap.get(amount).isAmtType()) {
                incentive.setText("You are eligible for " + "\u20B9 " + incentiveMap.get(amount).getComm() + " Cash Back");

            } else {
                incentive.setText("You are eligible for " + incentiveMap.get(amount).getComm() + " % Cash Back");

            }
        }
        AppCompatTextView cancelTv = dialogView.findViewById(R.id.cancel);
        AppCompatTextView okTv = dialogView.findViewById(R.id.ok);
        AppCompatTextView addMoneyTv = dialogView.findViewById(R.id.addMoney);
        AppCompatImageView logoIv = dialogView.findViewById(R.id.logo);
        TextView commAmountWalletLabel = dialogView.findViewById(R.id.commAmountWalletLabel);
        TextView commAmountWallet = dialogView.findViewById(R.id.commAmountWallet);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        double enteredAmount = Double.parseDouble(amount);
        if (mBalanceResponse.isRechargeWithPG()) {
            walletView.setVisibility(View.GONE);
            addMoneyTv.setVisibility(View.VISIBLE);
            forPgDetails.setVisibility(View.VISIBLE);
            okTv.setVisibility(View.GONE);
            try {
                double debitedWalletAmt = /*enteredAmount - */((mBalanceResponse.getRechargeWalletPercentage() * enteredAmount) / 100);
                if(mBalanceResponse.getBalanceData().get(0).getBalance()<debitedWalletAmt){
                    walletAmt.setText(Utility.INSTANCE.formatedAmountWithRupees(mBalanceResponse.getBalanceData().get(0).getBalance() + ""));
                }
                else{
                    walletAmt.setText(Utility.INSTANCE.formatedAmountWithRupees(debitedWalletAmt + ""));
                }

                requestedAmt = enteredAmount - debitedWalletAmt;
                if (mBalanceResponse.getBalanceData().get(0).getBalance() < debitedWalletAmt) {
                    requestedAmt += (debitedWalletAmt - mBalanceResponse.getBalanceData().get(0).getBalance());
                }
                requiredAmtTv.setText(Utility.INSTANCE.formatedAmountWithRupees(requestedAmt + ""));
            } catch (NumberFormatException nfe) {

            }
        } else {
            if (enteredAmount > mBalanceResponse.getBalanceData().get(0).getBalance()) {
                walletView.setVisibility(View.VISIBLE);
                addMoneyTv.setVisibility(View.VISIBLE);
                requiredAmtWalletLabel.setVisibility(View.VISIBLE);
                requiredAmtWallet.setVisibility(View.VISIBLE);
                UseAmtWalletLabel.setVisibility(View.VISIBLE);
                useAmtWallet.setVisibility(View.VISIBLE);
                okTv.setVisibility(View.GONE);
                forPgDetails.setVisibility(View.GONE);
                useAmtWallet.setText(Utility.INSTANCE.formatedAmountWithRupees(mBalanceResponse.getBalanceData().get(0).getBalance()+""));
                requestedAmt = (enteredAmount - mBalanceResponse.getBalanceData().get(0).getBalance());
                requiredAmtWallet.setText(Utility.INSTANCE.formatedAmountWithRupees(requestedAmt + ""));
            } else {
                walletView.setVisibility(View.VISIBLE);
                forPgDetails.setVisibility(View.GONE);
                addMoneyTv.setVisibility(View.GONE);
                requiredAmtWalletLabel.setVisibility(View.GONE);
                requiredAmtWallet.setVisibility(View.GONE);
                UseAmtWalletLabel.setVisibility(View.GONE);
                useAmtWallet.setVisibility(View.GONE);
                okTv.setVisibility(View.VISIBLE);
            }
            if(commAmount!=0){
                commAmountWallet.setText(commAmount+"");
            }else {
                commAmountLabel.setVisibility(View.GONE);
                commAmountText.setVisibility(View.GONE);
            }

        }


        Glide.with(context)
                .load(logo)
                .apply(getRequestOption_With_AppLogo_circleCrop())
                .into(logoIv);

        okTv.setOnClickListener(v -> {
            if (isPinPass && pinPassEt.getText().toString().isEmpty()) {
                pinPassEt.setError("Field can't be empty");
                pinPassEt.requestFocus();
                return;
            }
            bottomSheetDialog.dismiss();
            if (mDialogCallBack != null) {
                mDialogCallBack.onPositiveClick(pinPassEt.getText().toString());
            }
        });
        cancelTv.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            if (mDialogCallBack != null) {
                mDialogCallBack.onCancelClick();
            }
        });
        addMoneyTv.setOnClickListener(v -> {
            if (isPinPass && pinPassEt.getText().toString().isEmpty()) {
                pinPassEt.setError("Field can't be empty");
                pinPassEt.requestFocus();
                return;
            }
            bottomSheetDialog.dismiss();
            if (mDialogCallBack != null) {
                mDialogCallBack.onResetCallback(pinPassEt.getText().toString(), requestedAmt);
            }
        });

        bottomSheetDialog.show();
        bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    void emailVerifySocialLinkDialog(Activity context, final boolean isEmailVerified, final boolean isSocialAlert, CustomLoader loader, String deviceId, String deviceSerialNum, LoginResponse loginDataResponse, AppPreferences mAppPreferences) {

        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context);
        View sheetView = context.getLayoutInflater().inflate(R.layout.dialog_email_verify_social_link_update, null);


        ImageView closeBtn = sheetView.findViewById(R.id.closeBtn);
        TextView title = sheetView.findViewById(R.id.title);
        LinearLayout verifyEmailView = sheetView.findViewById(R.id.verifyEmailView);
        LinearLayout socialView = sheetView.findViewById(R.id.socialView);
        LinearLayout socialInputView = sheetView.findViewById(R.id.socialInputView);
        TextView verifyEmailTxt = sheetView.findViewById(R.id.verifyEmailTxt);
        TextView verifyEmailBtn = sheetView.findViewById(R.id.verifyEmailBtn);
        View line = sheetView.findViewById(R.id.line);
        TextView socialSaveTxt = sheetView.findViewById(R.id.socialSaveTxt);
        TextView whatsappTitle = sheetView.findViewById(R.id.whatsappTitle);
        EditText whatsappNumberEt = sheetView.findViewById(R.id.whatsappNumberEt);
        TextView telegramTitle = sheetView.findViewById(R.id.telegramTitle);
        EditText telegramNumberEt = sheetView.findViewById(R.id.telegramNumberEt);
        TextView hangoutTitle = sheetView.findViewById(R.id.hangoutTitle);
        EditText hangoutEt = sheetView.findViewById(R.id.hangoutEt);
        TextView submitBtn = sheetView.findViewById(R.id.submitBtn);

        if (!isEmailVerified && isSocialAlert) {
            title.setText("Verify Email Id");
        } else if (isEmailVerified && !isSocialAlert) {
            title.setText("Update Social Link");
        } else {
            title.setText("Verify Email Id And Update Social Link");
        }
        if (isEmailVerified) {
            line.setVisibility(View.GONE);
            verifyEmailView.setVisibility(View.GONE);
        }
        if (isSocialAlert) {
            line.setVisibility(View.GONE);
            socialView.setVisibility(View.GONE);
        } else {
            hangoutEt.setText(loginDataResponse.getData().getEmailID());
            whatsappNumberEt.setText(loginDataResponse.getData().getMobileNo());
        }
        closeBtn.setOnClickListener(v -> mBottomSheetDialog.dismiss());
        verifyEmailBtn.setOnClickListener(v -> {
            if (isNetworkAvialable(context)) {
                VerifyEmail(context, loader, deviceId, deviceSerialNum, loginDataResponse, mAppPreferences, object -> {
                    BasicResponse mBasicResponse = (BasicResponse) object;
                    verifyEmailTxt.setText(mBasicResponse.getMsg());
                    verifyEmailTxt.setTextColor(context.getResources().getColor(R.color.green));
                    verifyEmailBtn.setVisibility(View.GONE);

                    if (socialView.getVisibility() == View.GONE || socialInputView.getVisibility() == View.GONE) {
                        mBottomSheetDialog.setCancelable(true);
                    }
                });
            } else {
                NetworkError(context);
            }

        });

        submitBtn.setOnClickListener(v -> {
            if (whatsappNumberEt.getText().toString().trim().isEmpty() || whatsappNumberEt.getText().toString().trim().length() != 10) {
                whatsappNumberEt.setError("Please enter valid Whatsapp Number");
                whatsappNumberEt.requestFocus();
                return;
            } else if (telegramNumberEt.getText().toString().trim().isEmpty() || telegramNumberEt.getText().toString().trim().length() != 10) {
                telegramNumberEt.setError("Please enter valid Telegram Number");
                telegramNumberEt.requestFocus();
                return;
            } else if (hangoutEt.getText().toString().trim().isEmpty() || !hangoutEt.getText().toString().trim().contains("@")
                    || !hangoutEt.getText().toString().trim().contains(".")) {
                hangoutEt.setError("Please enter valid Hangout Email Id");
                hangoutEt.requestFocus();
                return;
            }

            if (isNetworkAvialable(context)) {
                submitSocialDetails(context, whatsappNumberEt.getText().toString().trim(), telegramNumberEt.getText().toString().trim(),
                        hangoutEt.getText().toString().trim(), loader, deviceId, deviceSerialNum, loginDataResponse, mAppPreferences, object -> {
                            BasicResponse mBasicResponse = (BasicResponse) object;
                            socialSaveTxt.setText(mBasicResponse.getMsg());
                            socialSaveTxt.setTextColor(context.getResources().getColor(R.color.green));
                            socialSaveTxt.setVisibility(View.VISIBLE);
                            socialInputView.setVisibility(View.GONE);

                            if (verifyEmailView.getVisibility() == View.GONE || verifyEmailBtn.getVisibility() == View.GONE) {
                                mBottomSheetDialog.setCancelable(true);
                            }
                        });
            } else {
                NetworkError(context);
            }
        });
        mBottomSheetDialog.setCancelable(false);
        mBottomSheetDialog.setContentView(sheetView);

        BottomSheetBehavior
                .from(mBottomSheetDialog.findViewById(R.id.design_bottom_sheet))
                .setState(BottomSheetBehavior.STATE_EXPANDED);
        mBottomSheetDialog.show();
    }

    public void showPopupDialog(Activity mActivity, Bitmap mBitmap) {
        if (alertDialogAdvertisement != null && alertDialogAdvertisement.isShowing()) {
            return;
        }
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(mActivity, R.style.full_screen_dialog);
        alertDialogAdvertisement = dialogBuilder.create();
        alertDialogAdvertisement.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_popup, null);
        alertDialogAdvertisement.setView(dialogView);

        AppCompatImageView imageBanner = dialogView.findViewById(R.id.imageBanner);
        View parentView = dialogView.findViewById(R.id.parentView);
        AppCompatTextView closeIcon = dialogView.findViewById(R.id.btn_skip);
        closeIcon.setClickable(false);

        imageBanner.setImageBitmap(mBitmap);


        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogAdvertisement.dismiss();
            }
        });


        alertDialogAdvertisement.show();


    }

    public void NetworkError(final Activity context) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.NetworkError("Network Error!", "Slow or No Internet Connection.");
    }

    public void Processing(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.Warning(message);
    }

    public void ProcessingWithTitle(final Activity context, final String title, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.Warning(title, message);
    }

    public void Failed(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.Failed(message);
    }

    public void Successful(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.Successful(message);
    }

    public void SuccessfulWithTwoButton(final Activity context, final String title, final String message, final String btnTxt,
                                        boolean isCancel, CustomAlertDialog.DialogCallBack mDialogCallBack) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.SuccessfulWithCallBack(title, message, btnTxt, isCancel, mDialogCallBack);
    }

    public void SuccessfulWithTitle(final Activity context, final String title, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.SuccessfulWithTitle(title, message);
    }

    public void SuccessfulWithFinsh(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.SuccessfulWithFinsh(message);
    }

    public void Successfulok(final String message, Activity activity) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(activity);
        customAlertDialog.Successfulok(message, activity);
    }

    public void SuccessfulWithFinish(final String message, Activity activity, boolean isCancelable) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(activity);
        customAlertDialog.SuccessfulWithFinsh(message, isCancelable);
    }

    public void Errorok(final Activity context, final String message, Activity activity) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.Errorok(message, activity);
    }

    public void Error(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.Error(message);
    }

    public void ErrorWithTitle(final Activity context, final String title, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.ErrorWithTitle(title, message);
    }

    public void Alert(final Activity context, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setContentText(message)
                .setCustomImage(R.drawable.ic_done_black_24dp)
                .show();
    }

    public boolean isNetworkAvialable(Activity context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public String fetchOperator(Activity context, String param, NumberSeriesListResponse numberListResponse) {
        int opid = 0;
        String circle = "";

        for (NumberList numberList : numberListResponse.getNumSeries()) {
            if (numberList.getSeries().equalsIgnoreCase(param)) {
                opid = numberList.getOid();
                circle = numberList.getCircleCode();
                break;
            }
        }
        return opid + "," + circle;
    }

    public void setSenderNumber(String senderNumber, String senderName, String remainingbal, String senderRemainigLimit, String senderTotalLimit,
                                String senderInfo, AppPreferences mAppPreferences) {

        mAppPreferences.set(ApplicationConstant.INSTANCE.senderNumberPref, senderNumber);
        mAppPreferences.set(ApplicationConstant.INSTANCE.senderNamePref, senderName);
        mAppPreferences.set(ApplicationConstant.INSTANCE.senderBalance, remainingbal);
        mAppPreferences.set(ApplicationConstant.INSTANCE.senderRemainigLimit, senderRemainigLimit);
        mAppPreferences.set(ApplicationConstant.INSTANCE.senderTotalLimit, senderTotalLimit);
        mAppPreferences.set(ApplicationConstant.INSTANCE.senderInfoPref, senderInfo);

    }

    public void logout(Context context, AppPreferences mAppPreferences) {
        if (mAppPreferences != null) {
            mAppPreferences.clear();
        } else {
            getAppPreferences(context).clear();
        }
        mTempLoginDataResponse = null;
        mBalanceResponse = null;
        mGetUserResponse = null;
        mWalletTypeResponse = null;
        mDashBoardServiceReportOptions = null;
        Intent startIntent = new Intent(context, LoginActivity.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(startIntent);
    }

    public void dthSubscriptionConfiormDialog(Activity context, CommissionDisplay mCommissionDisplay,
                                              final boolean isReal, final boolean isPinPass,
                                              String logo, String number, String operator,
                                              String amount, String packageName, String name, String address, final DialogCallBack mDialogCallBack) {
        if (alertDialogMobile != null && alertDialogMobile.isShowing()) {
            return;
        }
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder;
        dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);

        alertDialogMobile = dialogBuilder.create();
        alertDialogMobile.setCancelable(true);

        alertDialogMobile.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        LayoutInflater inflater = context.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_dth_subscription_confiorm, null);
        alertDialogMobile.setView(dialogView);
        LinearLayout commView = dialogView.findViewById(R.id.commView);
        LinearLayout lapuView = dialogView.findViewById(R.id.lapuView);
        LinearLayout realView = dialogView.findViewById(R.id.realView);
        TextView packageTv = dialogView.findViewById(R.id.packageTv);
        packageTv.setText(packageName);
        TextView lapuTitle = dialogView.findViewById(R.id.lapuTitle);
        // TextView lapuAmt = dialogView.findViewById(R.id.lapuAmt);
        TextView realTitle = dialogView.findViewById(R.id.realTitle);
        // TextView realAmt = dialogView.findViewById(R.id.realAmt);
        if (mCommissionDisplay != null) {
            commView.setVisibility(View.VISIBLE);
            if (isReal) {
                lapuView.setVisibility(View.GONE);
                realView.setVisibility(View.VISIBLE);
            } else {
                lapuView.setVisibility(View.VISIBLE);
                realView.setVisibility(View.GONE);
            }

            lapuTitle.setText((mCommissionDisplay.isCommType() ? "Surcharge " : "Commission ") + Utility.INSTANCE.formatedAmountWithRupees(mCommissionDisplay.getCommission() + ""));
            realTitle.setText((mCommissionDisplay.isrCommType() ? "Surcharge " : "Commission ") + Utility.INSTANCE.formatedAmountWithRupees(mCommissionDisplay.getrCommission() + ""));

        } else {
            commView.setVisibility(View.GONE);
        }
        AppCompatTextView amountTv = dialogView.findViewById(R.id.amount);
        amountTv.setText(amount);
        final TextInputLayout til_pinPass = dialogView.findViewById(R.id.til_pinPass);
        final EditText pinPassEt = dialogView.findViewById(R.id.pinPassEt);
        if (isPinPass) {
            til_pinPass.setVisibility(View.VISIBLE);
        } else {
            til_pinPass.setVisibility(View.GONE);
        }
        AppCompatTextView operatorTv = dialogView.findViewById(R.id.operator);
        operatorTv.setText(operator);
        AppCompatTextView numberTv = dialogView.findViewById(R.id.number);
        numberTv.setText(number);
        AppCompatTextView nameTv = dialogView.findViewById(R.id.name);
        nameTv.setText(name);
        AppCompatTextView addressTv = dialogView.findViewById(R.id.address);
        addressTv.setText(address);
        AppCompatTextView cancelTv = dialogView.findViewById(R.id.cancel);
        AppCompatTextView okTv = dialogView.findViewById(R.id.ok);
        AppCompatImageView logoIv = dialogView.findViewById(R.id.logo);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(logo)
                .apply(getRequestOption_With_AppLogo_circleCrop())
                .into(logoIv);

        okTv.setOnClickListener(v -> {
            if (isPinPass && pinPassEt.getText().toString().isEmpty()) {
                pinPassEt.setError("Field can't be empty");
                pinPassEt.requestFocus();
                return;
            }
            alertDialogMobile.dismiss();
            if (mDialogCallBack != null) {
                mDialogCallBack.onPositiveClick(pinPassEt.getText().toString());
            }
        });
        cancelTv.setOnClickListener(v -> {
            alertDialogMobile.dismiss();
            if (mDialogCallBack != null) {
                mDialogCallBack.onCancelClick();
            }
        });

        alertDialogMobile.show();
        /*if (isFullScreen) {
            alertDialogMobile.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }*/
    }

    public void openOtpDialog(final Activity context, boolean isResend, String mobileNum, final DialogOTPCallBack mDialogCallBack) {

        if (dialogOTP != null && dialogOTP.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_otp_verify, null);

        TextView numberTv = view.findViewById(R.id.number);
        PinEntryEditText edMobileOtp = view.findViewById(R.id.txt_pin_entry);
        final ImageView closeIv = view.findViewById(R.id.closeIv);
        final View btLogin = view.findViewById(R.id.bt_login);
        final TextView timerTv = view.findViewById(R.id.timer);
        final TextView resendTv = view.findViewById(R.id.resend);
        final TextView otpErrorTv = view.findViewById(R.id.otpError);
        View resendView = view.findViewById(R.id.resendView);

        numberTv.setText(mobileNum.replace(mobileNum.substring(0, 7), "XXXXXXX"));
        dialogOTP = new Dialog(context, R.style.full_screen_bg_dialog);
        dialogOTP.setCancelable(false);
        dialogOTP.setContentView(view);

        dialogOTP.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        if (isResend) {
            timerTv.setVisibility(View.VISIBLE);
            setTimer(timerTv, resendTv);
        } else {
            timerTv.setVisibility(View.GONE);
        }

        closeIv.setOnClickListener(v -> dialogOTP.dismiss());


        btLogin.setOnClickListener(v -> {
            if (edMobileOtp.getText().length() != 6) {
                otpErrorTv.setText("Enter Valid OTP");
                otpErrorTv.requestFocus();
            } else {
                otpErrorTv.setText("");
                if (mDialogCallBack != null) {
                    mDialogCallBack.onPositiveClick(edMobileOtp.getText().toString(), timerTv, resendTv, dialogOTP);
                }
            }
        });

        resendTv.setOnClickListener(v -> {
           /* if (edMobileOtp.getText().length() != 6) {
                otpErrorTv.setText("Enter Valid OTP");
                otpErrorTv.requestFocus();
            } else {
                otpErrorTv.setText("");*/
            if (mDialogCallBack != null) {
                mDialogCallBack.onResetCallback(edMobileOtp.getText().toString(), timerTv, resendTv, dialogOTP);
            }
            /*}*/
        });

        dialogOTP.setOnDismissListener(dialog1 -> {
            if (countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer = null;
            }
        });

        dialogOTP.show();
    }


    public void openGAuthDialog(final Activity context, final DialogOTPCallBack mDialogCallBack) {

        try {
            if (dialogOTP != null && dialogOTP.isShowing()) {
                return;
            }
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_gauth_verify, null);

            TextView numberTv = view.findViewById(R.id.number);
            PinEntryEditText edMobileOtp = view.findViewById(R.id.txt_pin_entry);
            final ImageView closeIv = view.findViewById(R.id.closeIv);
            final View btLogin = view.findViewById(R.id.bt_login);
            final TextView timerTv = view.findViewById(R.id.timer);
            final TextView resendTv = view.findViewById(R.id.resend);
            final TextView otpErrorTv = view.findViewById(R.id.otpError);
            View resendView = view.findViewById(R.id.resendView);

            // numberTv.setText(mobileNum.replace(mobileNum.substring(0, 7), "XXXXXXX"));
            dialogOTP = new Dialog(context, R.style.full_screen_bg_dialog);
            dialogOTP.setCancelable(false);
            dialogOTP.setContentView(view);

            dialogOTP.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            //setTimer(timerTv, resendTv);

            closeIv.setOnClickListener(v -> dialogOTP.dismiss());


            btLogin.setOnClickListener(v -> {
                if (edMobileOtp.getText().length() != 6) {
                    otpErrorTv.setText("Enter Valid OTP");
                    otpErrorTv.requestFocus();
                } else {
                    otpErrorTv.setText("");
                    if (mDialogCallBack != null) {
                        mDialogCallBack.onPositiveClick(edMobileOtp.getText().toString(), timerTv, resendTv, dialogOTP);
                    }
                }
            });

       /* resendTv.setOnClickListener(v -> {
            if (edMobileOtp.getText().length() != 6) {
                otpErrorTv.setText("Enter Valid OTP");
                otpErrorTv.requestFocus();
            } else {
                otpErrorTv.setText("");
                if (mDialogCallBack != null) {
                    mDialogCallBack.onResetCallback(edMobileOtp.getText().toString(), timerTv, resendTv, dialogOTP);
                }
            }
        });*/

            dialogOTP.setOnDismissListener(dialog1 -> {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
            });

            dialogOTP.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openOtpDialog(final Activity context, int otpLength, String mobileNum, final DialogOTPCallBack mDialogCallBack) {

        if (dialogOTP != null && dialogOTP.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_otp_verify, null);

        TextView numberTv = view.findViewById(R.id.number);
        PinEntryEditText edMobileOtp = view.findViewById(R.id.txt_pin_entry);
        if (otpLength != 6) {
            edMobileOtp.setMaxLength(otpLength);
        }
        final ImageView closeIv = view.findViewById(R.id.closeIv);
        final View btLogin = view.findViewById(R.id.bt_login);
        final TextView timerTv = view.findViewById(R.id.timer);
        final TextView resendTv = view.findViewById(R.id.resend);
        final TextView otpErrorTv = view.findViewById(R.id.otpError);
        View numberView = view.findViewById(R.id.numberView);
        View resendView = view.findViewById(R.id.resendView);

        if (mobileNum != null && mobileNum.length() > 7) {
            numberView.setVisibility(View.VISIBLE);
            numberTv.setText(mobileNum.replace(mobileNum.substring(0, 7), "XXXXXXX"));
        } else {
            numberView.setVisibility(View.GONE);
        }
        dialogOTP = new Dialog(context, R.style.full_screen_bg_dialog);
        dialogOTP.setCancelable(false);
        dialogOTP.setContentView(view);

        dialogOTP.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setTimer(timerTv, resendTv);

        closeIv.setOnClickListener(v -> dialogOTP.dismiss());


        btLogin.setOnClickListener(v -> {
            if (edMobileOtp.getText().length() != otpLength) {
                otpErrorTv.setText("Enter Valid OTP");
                otpErrorTv.requestFocus();
            } else {
                otpErrorTv.setText("");
                if (mDialogCallBack != null) {
                    mDialogCallBack.onPositiveClick(edMobileOtp.getText().toString(), timerTv, resendTv, dialogOTP);
                }
            }
        });

        resendTv.setOnClickListener(v -> {
            if (edMobileOtp.getText().length() != 6) {
                otpErrorTv.setText("Enter Valid OTP");
                otpErrorTv.requestFocus();
            } else {
                otpErrorTv.setText("");
                if (mDialogCallBack != null) {
                    mDialogCallBack.onResetCallback(edMobileOtp.getText().toString(), timerTv, resendTv, dialogOTP);
                }
            }
        });

        dialogOTP.setOnDismissListener(dialog1 -> {
            if (countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer = null;
            }
        });

        dialogOTP.show();
    }

    public void openOtpDepositDialog(final Activity context, String mobileNum, final DialogOTPCallBack mDialogCallBack) {

        if (dialogOTP != null && dialogOTP.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_otp_verify, null);

        TextView numberTv = view.findViewById(R.id.number);
        PinEntryEditText edMobileOtp = view.findViewById(R.id.txt_pin_entry);
        final ImageView closeIv = view.findViewById(R.id.closeIv);
        final View btLogin = view.findViewById(R.id.bt_login);
        final TextView timerTv = view.findViewById(R.id.timer);
        final TextView noticeResetMsg = view.findViewById(R.id.noticeResetMsg);
        final TextView resendTv = view.findViewById(R.id.resend);
        final TextView otpErrorTv = view.findViewById(R.id.otpError);
        View resendView = view.findViewById(R.id.resendView);
        noticeResetMsg.setText(context.getResources().getString(R.string.verify_deposit_otp_notice));
        numberTv.setText(mobileNum.replace(mobileNum.substring(0, 7), "XXXXXXX"));
        dialogOTP = new Dialog(context, R.style.full_screen_bg_dialog);
        dialogOTP.setCancelable(false);
        dialogOTP.setContentView(view);

        dialogOTP.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setTimer(timerTv, resendTv);

        closeIv.setOnClickListener(v -> dialogOTP.dismiss());


        btLogin.setOnClickListener(v -> {
            if (edMobileOtp.getText().length() != 6) {
                otpErrorTv.setText("Enter Valid OTP");
                otpErrorTv.requestFocus();
            } else {
                otpErrorTv.setText("");
                if (mDialogCallBack != null) {
                    mDialogCallBack.onPositiveClick(edMobileOtp.getText().toString(), timerTv, resendTv, dialogOTP);
                }
            }
        });

        resendTv.setOnClickListener(v -> {

            if (mDialogCallBack != null) {
                mDialogCallBack.onResetCallback(edMobileOtp.getText().toString(), timerTv, resendTv, dialogOTP);
            }

        });

        dialogOTP.setOnDismissListener(dialog1 -> {
            if (countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer = null;
            }
        });

        dialogOTP.show();
    }

    public void setTimer(final TextView timer, final TextView resendcode) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        timer.setVisibility(View.VISIBLE);
        resendcode.setVisibility(View.GONE);
        timer.setText("Resend OTP - 00:00");
        countDownTimer = new CountDownTimer(30000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                timer.setText("Resend OTP - " + String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                timer.setText("");
                timer.setVisibility(View.GONE);
                resendcode.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void apiErrorHandle(Activity context, int code, String msg) {
        if (code == 401) {
            ErrorWithTitle(context, "UNAUTHENTICATED " + code, msg);
        } else if (code == 404) {
            ErrorWithTitle(context, "API ERROR " + code, msg);
        } else if (code >= 400 && code < 500) {
            ErrorWithTitle(context, "CLIENT ERROR " + code, msg);
        } else if (code >= 500 && code < 600) {

            ErrorWithTitle(context, "SERVER ERROR " + code, msg);
        } else {
            ErrorWithTitle(context, "FATAL/UNKNOWN ERROR " + code, msg);
        }
    }

    public void apiFailureError(Activity context, Throwable t) {
        if (t instanceof UnknownHostException || t instanceof IOException) {
            NetworkError(context);
        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage());
        } else {
            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                ErrorWithTitle(context, "FATAL ERROR", t.getMessage());
            } else {
                Error(context, context.getResources().getString(R.string.some_thing_error));
            }
        }
    }

    public String getLoginPref(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        return prefs.getString(ApplicationConstant.INSTANCE.LoginPref, "");
    }

    public void GetUDetailByMob(final Activity context, UDetailByMobRequest uDetailByMobRequest, final CustomLoader loader, final ApiCallBack apiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<UDetailByMobResponse> call = git.getUDetailByMob(uDetailByMobRequest);

            call.enqueue(new Callback<UDetailByMobResponse>() {

                @Override
                public void onResponse(Call<UDetailByMobResponse> call, retrofit2.Response<UDetailByMobResponse> response) {
                    if (loader != null && loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null) {
                        if (response.body().getStatuscode() == 1) {
                            if (apiCallBack != null) {
                                apiCallBack.onSucess(response.body());

                            }
                        } else if (response.body().getStatuscode() == -1) {
                            if (!response.body().isVersionValid()) {
                                versionDialog(context);
                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg());
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<UDetailByMobResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing())
                        loader.dismiss();
                    apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing())
                loader.dismiss();
        }
    }

    public boolean getDoubleFactorPref(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.DoubleFactorPref, false);

    }


    public String getWalletType(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        return prefs.getString(ApplicationConstant.INSTANCE.walletType, "");

    }

    public void walletToWalletFT(final Activity context, WalletToWalletFTRequest walletFTRequest, final CustomLoader loader, final ApiCallBack apiCallBack) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<UDetailByMobResponse> call = git.walletToWalletFT(walletFTRequest);

            call.enqueue(new Callback<UDetailByMobResponse>() {

                @Override
                public void onResponse(Call<UDetailByMobResponse> call, retrofit2.Response<UDetailByMobResponse> response) {
                    if (loader != null && loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null) {
                        if (response.body().getStatuscode() == 1) {
                            if (apiCallBack != null) {
                                apiCallBack.onSucess(response.body());

                            }
                        } else if (response.body().getStatuscode() == -1) {
                            if (!response.body().isVersionValid()) {
                                versionDialog(context);
                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg());
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<UDetailByMobResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing())
                        loader.dismiss();
                    apiFailureError(context, t);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing())
                loader.dismiss();
        }
    }

    public NetworkingDashboardResponse getNetworkingDashboard(AppPreferences mAppPreferences) {
        if (mNetworkingDashboardResponse != null && mNetworkingDashboardResponse.getData() != null) {
            return mNetworkingDashboardResponse;
        } else {
            if (gson == null) {
                gson = new Gson();
            }
            mNetworkingDashboardResponse = gson.fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.dashboardDownlineDataPref), NetworkingDashboardResponse.class);
            return mNetworkingDashboardResponse;
        }

    }

    public RankListResponse getRankListResponse(AppPreferences mAppPreferences) {
        if (mRankListResponse != null && mRankListResponse.getData() != null) {
            return mRankListResponse;
        } else {
            if (gson == null) {
                gson = new Gson();
            }
            mRankListResponse = gson.fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.mRankListPref), RankListResponse.class);
            return mRankListResponse;
        }

    }

    public DashboardDownlineData getSingleData(AppPreferences mAppPreferences) {
        if (singleDataResponse != null) {
            return singleDataResponse;
        } else {
            if (gson == null) {
                gson = new Gson();
            }
            singleDataResponse = gson.fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.singleDataPref), DashboardDownlineData.class);
            return singleDataResponse;
        }

    }

    public void GetTotalRechargeComm(Activity context,LoginResponse mLoginDataResponse, int operatorSelectedId, String amount,
                                     String deviceId, String deviceSerialNum,CustomLoader loader, ApiResponseCallBack apiResponseCallBack) {
        FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
        Call<RechargeResponse> call = git.GetTotalRechargeComm(new BalanceRequest(operatorSelectedId,amount,0,
                ApplicationConstant.INSTANCE.APP_ID,deviceId,mLoginDataResponse.getData().getLoginTypeID(),
                "",deviceSerialNum, mLoginDataResponse.getData().getSession()
                ,mLoginDataResponse.getData().getSessionID(),mLoginDataResponse.getData().getUserID(),BuildConfig.VERSION_NAME));
call.enqueue(new Callback<RechargeResponse>() {
    @Override
    public void onResponse(Call<RechargeResponse> call, Response<RechargeResponse> response) {
        try {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            if (response.isSuccessful()) {
                if (response.body() != null) {
                  if (apiResponseCallBack!=null){
                      apiResponseCallBack.onSucess(response.body());
                  }
                }
            } else {
                apiErrorHandle(context, response.code(), response.message());
            }
        } catch (Exception e) {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage());
        }
    }

    @Override
    public void onFailure(Call<RechargeResponse> call, Throwable t) {
        if (loader != null) {
            if (loader.isShowing()) {
                loader.dismiss();
            }
        }
        try {

            if (t instanceof UnknownHostException || t instanceof IOException) {
                NetworkError(context);

            } else {
                Processing(context, t.getMessage());
            }

        } catch (IllegalStateException ise) {
            Error(context, ise.getMessage());
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
        }


    }
});
    }

    public interface ApiCallBack {
        void onSucess(Object object);
    }


    public interface ApiResponseCallBack {
        void onSucess(Object object);

        void onError(int error);
    }

    public interface ApiCallBackTwoMethod {
        void onSucess(Object object);

        void onError(Object object);
    }


    public interface DialogCallBack {
        void onPositiveClick(String value);

        void onResetCallback(String value, double amount);

        void onCancelClick();
    }

    public interface DialogOTPCallBackAadhaar {
        void onPositiveClick(String otpValue, CustomLoader loader, String aadhaarNo, int referenceId, TextView timerTv, TextView resendCodeTv, Dialog mDialog);

        void onResetCallback(String otpValue, CustomLoader loader, String aadhaarNo, int referenceId, TextView timerTv, TextView resendCodeTv, Dialog mDialog);
    }

    public interface DialogOTPCallBack {
        void onPositiveClick(String otpValue, TextView timerTv, TextView resendCodeTv, Dialog mDialog);

        void onResetCallback(String otpValue, TextView timerTv, TextView resendCodeTv, Dialog mDialog);
    }

    public void openOtpDepositDialog(UpdateProfileActivity activity, CustomLoader loader, String aadhaar, int referenceID, DialogOTPCallBackAadhaar dialogOTPCallBack) {
        if (dialogOTP != null && dialogOTP.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_otp_verify, null);
        PinEntryEditText edMobileOtp = view.findViewById(R.id.txt_pin_entry);
        final ImageView closeIv = view.findViewById(R.id.closeIv);
        final View btLogin = view.findViewById(R.id.bt_login);
        final TextView timerTv = view.findViewById(R.id.timer);
        final TextView noticeResetMsg = view.findViewById(R.id.noticeResetMsg);
        final TextView resendTv = view.findViewById(R.id.resend);
        final TextView otpErrorTv = view.findViewById(R.id.otpError);
        noticeResetMsg.setText(activity.getResources().getString(R.string.verify_deposit_otp_notice));
        noticeResetMsg.setVisibility(View.GONE);
        dialogOTP = new Dialog(activity, R.style.full_screen_bg_dialog);
        dialogOTP.setCancelable(false);
        dialogOTP.setContentView(view);
        dialogOTP.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setTimer(timerTv, resendTv);
        closeIv.setOnClickListener(v -> dialogOTP.dismiss());
        btLogin.setOnClickListener(v -> {
            if (edMobileOtp.getText().length() != 6) {
                otpErrorTv.setText("Enter Valid OTP");
                otpErrorTv.requestFocus();
            } else {
                otpErrorTv.setText("");
                if (dialogOTPCallBack != null) {
                    dialogOTPCallBack.onPositiveClick(edMobileOtp.getText().toString(), loader, aadhaar, referenceID, timerTv, resendTv, dialogOTP);
                }
            }
        });
        resendTv.setOnClickListener(v -> {
            if (dialogOTPCallBack != null) {
                dialogOTPCallBack.onResetCallback(edMobileOtp.getText().toString(), loader, aadhaar, referenceID, timerTv, resendTv, dialogOTP);
            }
        });

        dialogOTP.setOnDismissListener(dialog1 -> {
            if (countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer = null;
            }
        });

        dialogOTP.show();
    }

    public interface ApiCallBackMultiple {
        void onSucessResponse(Object object, String AccountNo, String Amount, int Opid, final String opName);

        void onProcess(Object object);

        void onNetworkError(Object object);

        void onError(Object object);
    }
}
