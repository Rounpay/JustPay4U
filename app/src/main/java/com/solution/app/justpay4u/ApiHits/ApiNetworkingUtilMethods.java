package com.solution.app.justpay4u.ApiHits;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Networking.Response.NetworkingDashboardResponse;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.PinEntryEditText;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Vishnu Agarwal on 19/07/2022.
 */
public enum ApiNetworkingUtilMethods {
    INSTANCE;

    public NetworkingDashboardResponse mNetworkingDashboardResponse;
    public RankListResponse mRankListResponse;
    private CountDownTimer countDownTimer;
    private Dialog dialogGauthOTP;

    public int maxReportDisplay = 1;

    public boolean isNetworkAvialable(Activity context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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

    public void Successfulok(final String message, Activity activity) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(activity);
        customAlertDialog.Successfulok(message, activity);
    }
    public void Error(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.Error(message);
    }
    public void Successful(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.Successful(message);
    }
    public void ErrorWithTitle(final Activity context, final String title, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.ErrorWithTitle(title, message);
    }
    public void NetworkError(final Activity context) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
        customAlertDialog.NetworkError("Network Error!", "Slow or No Internet Connection.");
    }

    public void apiErrorHandle(Activity context, int code, String msg) {
        if (code == 401) {
            ErrorWithTitle(context, "UNAUTHENTICATED " + code, msg + "");
        } else if (code == 404) {
            ErrorWithTitle(context, "API ERROR " + code, msg + "");
        } else if (code >= 400 && code < 500) {
            ErrorWithTitle(context, "CLIENT ERROR " + code, msg + "");
        } else if (code >= 500 && code < 600) {

            ErrorWithTitle(context, "SERVER ERROR " + code, msg + "");
        } else {
            ErrorWithTitle(context, "FATAL/UNKNOWN ERROR " + code, msg + "");
        }
    }

    public void apiFailureError(Activity context, Throwable t) {
        if (t instanceof UnknownHostException || t instanceof IOException) {
            NetworkError(context);
        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
        } else {
            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
            } else {
                Error(context, context.getResources().getString(R.string.some_thing_error));
            }
        }
    }

    public void getLevel(Activity context, String deviceId, String deviceSerialNum, LoginResponse loginPrefResponse,
                         ApiGetLevelCallBack mApiGetLevelCallBack) {

        try {
            NetworkingEndPointInterface git = ApiClient.getClient().create(NetworkingEndPointInterface.class);
            Call<MemberListResponse> call = git.getLevel(new BasicRequest(new BasicRequest(loginPrefResponse.getData().getUserID(),
                    loginPrefResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum, loginPrefResponse.getData().getSessionID(),
                    loginPrefResponse.getData().getSession())));

            call.enqueue(new Callback<MemberListResponse>() {
                @Override
                public void onResponse(Call<MemberListResponse> call, final retrofit2.Response<MemberListResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {

                                    if (mApiGetLevelCallBack != null) {
                                        mApiGetLevelCallBack.onSuccess(response.body());
                                    }

                                } else {

                                    if (!response.body().isVersionValid()) {
                                        ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg() + "");
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
                public void onFailure(Call<MemberListResponse> call, Throwable t) {

                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);

                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");

                        } else {

                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
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
    public interface DialogOTPCallBack {
        void onPositiveClick(String otpValue, TextView timerTv, TextView resendCodeTv, Dialog mDialog);

        void onResetCallback(String otpValue, TextView timerTv, TextView resendCodeTv, Dialog mDialog);
    }
    public interface ApiGetLevelCallBack {
        void onSuccess(MemberListResponse mResponse);
    }
    public void openOtpGAuthDialog(final Activity context, String mobileNumber, final DialogOTPCallBack mDialogCallBack) {

        if (dialogGauthOTP != null && dialogGauthOTP.isShowing()) {
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
        noticeResetMsg.setText(context.getResources().getString(R.string.verify_gauth_otp_notice));
        numberTv.setText(mobileNumber + "");
        dialogGauthOTP = new Dialog(context, R.style.full_screen_bg_dialog);
        dialogGauthOTP.setCancelable(false);
        dialogGauthOTP.setContentView(view);

        dialogGauthOTP.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setTimer(timerTv, resendTv);

        closeIv.setOnClickListener(v -> dialogGauthOTP.dismiss());


        btLogin.setOnClickListener(v -> {
            if (edMobileOtp.getText().length() != 6) {
                otpErrorTv.setText("Enter Valid OTP");
                otpErrorTv.requestFocus();
            } else {
                otpErrorTv.setText("");
                if (mDialogCallBack != null) {
                    mDialogCallBack.onPositiveClick(edMobileOtp.getText().toString(), timerTv, resendTv, dialogGauthOTP);
                }
            }
        });

        resendTv.setOnClickListener(v -> {

            if (mDialogCallBack != null) {
                mDialogCallBack.onResetCallback(edMobileOtp.getText().toString(), timerTv, resendTv, dialogGauthOTP);
            }

        });

        dialogGauthOTP.setOnDismissListener(dialog1 -> {
            if (countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer = null;
            }
        });

        dialogGauthOTP.show();
    }
}
