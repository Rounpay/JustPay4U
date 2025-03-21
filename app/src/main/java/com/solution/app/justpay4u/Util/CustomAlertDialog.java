package com.solution.app.justpay4u.Util;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.solution.app.justpay4u.Api.Shopping.Object.Address;
import com.solution.app.justpay4u.Api.Shopping.Object.StatesCities;
import com.solution.app.justpay4u.Fintech.Activities.UpdateProfileActivity;
import com.solution.app.justpay4u.Fintech.Adapter.AccountOpenOPListAdapter;
import com.solution.app.justpay4u.Api.Fintech.Object.AreaMaster;
import com.solution.app.justpay4u.Api.Fintech.Object.AssignedOpType;
import com.solution.app.justpay4u.Api.Fintech.Object.NotificationData;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.FosAccStmtAndCollReportResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetUserResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.VoucherEntryActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Adapter.ChannelAreaListAdapter;
import com.solution.app.justpay4u.Fintech.Dashboard.Adapter.HomeOptionListAdapter;
import com.solution.app.justpay4u.Fintech.FundTransactions.Adapter.DMTOptionListAdapter;
import com.solution.app.justpay4u.Fintech.Notification.Adapter.FundRequestSliderAdapter;

import com.solution.app.justpay4u.Fintech.UpiPayment.Activity.QRScanActivity;
import com.solution.app.justpay4u.Fintech.UpiPayment.Activity.UPIPayActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Reports.Activity.FundOrderPendingActivity;
import com.solution.app.justpay4u.Shopping.Activity.AddAddressShoppingActivity;
import com.solution.app.justpay4u.Shopping.Adapter.AddressBottomSheetShoppingAdapter;
import com.solution.app.justpay4u.Shopping.Adapter.StatesCitiesListShoppingAdapter;
import com.solution.app.justpay4u.Shopping.Interfaces.ShoppingSelectAddress;
import com.solution.app.justpay4u.Shopping.Interfaces.ShoppingSelectStateCities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class CustomAlertDialog {

    AlertDialog alertDialogLogout;
    private Activity context;
    private SweetAlertDialog alertDialog;
    private AlertDialog alertDialogSendReport;
    private AlertDialog alertDialogServiceList;
    private AlertDialog mDialog;
    private Dialog mFundRequestNotificationDialog;
    private FundRequestSliderAdapter mSliderImageAdapter;
    private AlertDialog alertDialogFundTransfer;

    public CustomAlertDialog() {

    }

    public CustomAlertDialog(Activity context) {
        try {
            this.context = context;
            alertDialog = new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            alertDialog.setOnShowListener(dialog -> {
                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                TextView text = alertDialog.findViewById(R.id.content_text);
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                text.setSingleLine(false);


            });
       /* TextView text =  alertDialog.findViewById(R.id.content_text);
        text.setGravity(Gravity.CENTER);
        //text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        text.setSingleLine(false);
        text.setMaxLines(10);
        text.setLines(6);*/
        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (WindowManager.BadTokenException bte) {
            bte.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void showSimErrorDialog(String msg) {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                return;
            }
            mDialog = new AlertDialog.Builder(context)
                    .setTitle("No Authorize!")
                    .setCancelable(false)
                    .setMessage(msg)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                context.finishAffinity();
                            } else {
                                context.finish();
                            }
                        }
                    })

                    .show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (RuntimeException rte) {

        } catch (Exception e) {

        }
    }

    public void showVpnEnableDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                return;
            }
            mDialog = new AlertDialog.Builder(context)
                    .setTitle("No Authorize!")
                    .setCancelable(false)
                    .setMessage(context.getResources().getString(R.string.vpn_enable))
                    .setPositiveButton("Disable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                context.finishAffinity();
                            } else {
                                context.finish();
                            }
                            Intent intent = new Intent("android.net.vpn.SETTINGS");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    })

                    .show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (RuntimeException rte) {

        } catch (Exception e) {

        }
    }

    public void Failed(final String message) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            if (message != null && !message.isEmpty() && message.length() > 1) {
                alertDialog.setContentText(message);
            } else {
                alertDialog.setContentText("Failed");
            }

            // alertDialog.setCustomImage(R.drawable.ic_error_red_24dp);
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void SuccessfulWithTitle(final String title, final String message) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            alertDialog.setTitle(title);
            alertDialog.setContentText(message);

            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void Successful(final String message) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            if (message != null && !message.isEmpty() && message.length() > 1) {
                alertDialog.setContentText(message);
            } else {
                alertDialog.setContentText("Success");
            }
            // alertDialog.setCustomImage(R.drawable.ic_success);
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }


    public void SuccessfulWithFinsh(final String message) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            alertDialog.setContentText(message);
            // alertDialog.setCustomImage(R.drawable.ic_success);
            alertDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    ((Activity) context).finish();
                }
            });
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void SuccessfulWithFinsh(final String message, boolean isCancelable) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            alertDialog.setContentText(message);
            alertDialog.setCancelable(isCancelable);
            // alertDialog.setCustomImage(R.drawable.ic_success);
            alertDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    context.finish();
                }
            });
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void SuccessfulWithCallBack(final String title, final String message, final String btnTxt,
                                       boolean isCancel, DialogCallBack mDialogCallBack) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            alertDialog.setContentText(message);
            alertDialog.setTitle(title);
            alertDialog.setCancelable(isCancel);
            // alertDialog.setCustomImage(R.drawable.ic_success);
            alertDialog.setConfirmButton(btnTxt, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                if (mDialogCallBack != null) {
                    mDialogCallBack.onPositiveClick();
                }
            });
            alertDialog.setCancelButton("Cancel", sweetAlertDialog -> sweetAlertDialog.dismiss());
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void SuccessfulWithCallBack(final String message, final Activity activity) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            alertDialog.setContentText(message);
            // alertDialog.setCustomImage(R.drawable.ic_success);
            alertDialog.setConfirmButton("Ok", sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                activity.finish();
            });
            alertDialog.setCancelButton("Cancel", sweetAlertDialog -> sweetAlertDialog.dismiss());
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void ExitWithCallBack(final String message, final Activity activity) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            alertDialog.setContentText(message);
            // alertDialog.setCustomImage(R.drawable.ic_success);
            alertDialog.setConfirmButton("Exit", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    activity.finish();
                }
            });
            alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();

                }
            });
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void showMessage(final String title, final String message, int icon, final int flag) {

        try {
            if (title != null && !title.isEmpty()) {
                alertDialog.setTitle(title);
            } else {
                alertDialog.setTitle("Attention!");
            }
            if (message != null && !message.isEmpty() && message.length() > 1) {
                alertDialog.setContentText(message);
            } else {
                alertDialog.setContentText("Failed");
            }
            alertDialog.setCustomImage(icon);
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    //1 For Update profile
                    if (flag == 1) {
                        context.startActivity(new Intent(context, UpdateProfileActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    }
                    sweetAlertDialog.cancel();
                }
            });
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void showMessagePES(final String title, final String message) {

        try {

            alertDialog.setTitle(title);
            alertDialog.setContentText(message);
            alertDialog.setCustomImage(R.drawable.ic_pes);
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);

            alertDialog.setConfirmButton("Open Portal", sweetAlertDialog -> {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ApplicationConstant.INSTANCE.baseUrl))
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

                sweetAlertDialog.dismiss();
            });
            alertDialog.setCancelButton("Cancel", sweetAlertDialog -> sweetAlertDialog.dismiss());
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void sendReportDialog(final int type, String phoneNum, final DialogSingleCallBack mDialogSingleCallBack) {
        try {
            if (alertDialogSendReport != null && alertDialogSendReport.isShowing()) {
                return;
            }

           /* type = 1 //Recharge Report
            type = 2 // Bank List
            type = 3 // Call Back Request*/
            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(context);
            alertDialogSendReport = dialogBuilder.create();
            alertDialogSendReport.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = context.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_send_report, null);
            alertDialogSendReport.setView(dialogView);

            final AppCompatEditText mobileEt = dialogView.findViewById(R.id.mobileEt);

            final AppCompatEditText emailEt = dialogView.findViewById(R.id.emailEt);
            TextView emailTitleTv = dialogView.findViewById(R.id.emailTitleTv);
            AppCompatTextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);
            AppCompatTextView sendBtn = dialogView.findViewById(R.id.sendBtn);
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView titleTv = dialogView.findViewById(R.id.titleTv);
            if (phoneNum != null) {
                mobileEt.setText(phoneNum);
            }
            if (type == 1) {
                titleTv.setText("Send Report");
            }
            if (type == 2) {
                titleTv.setText("Send Bank detail");
            }
            if (type == 3) {
                emailEt.setVisibility(View.GONE);
                emailTitleTv.setVisibility(View.GONE);
                titleTv.setText("Call Back Request");
            }


            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogSendReport.dismiss();
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogSendReport.dismiss();
                }
            });

            sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mobileEt.getText().toString().isEmpty()) {
                        mobileEt.setError("Please Enter Valid Mobile Number");
                        mobileEt.requestFocus();
                        return;
                    } else if (mobileEt.getText().toString().length() != 10) {
                        mobileEt.setError("Please Enter Valid Mobile Number");
                        mobileEt.requestFocus();
                        return;
                    } else if (type != 3 && emailEt.getText().toString().isEmpty()) {
                        emailEt.setError("Please Enter Valid Email Id");
                        emailEt.requestFocus();
                        return;
                    } else if (type != 3 && !emailEt.getText().toString().contains("@") || type != 3 && !emailEt.getText().toString().contains(".")) {
                        emailEt.setError("Please Enter Valid Email Id");
                        emailEt.requestFocus();
                        return;
                    }
                    if (mDialogSingleCallBack != null) {
                        alertDialogSendReport.dismiss();
                        mDialogSingleCallBack.onPositiveClick(mobileEt.getText().toString(), emailEt.getText().toString());
                    }
                }
            });


            alertDialogSendReport.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (WindowManager.BadTokenException bde) {
            bde.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }
    }

    public void enableRealApiDialog(String msgTxt, final DialogSingleCallBack mDialogSingleCallBack) {
        try {
            if (alertDialogSendReport != null && alertDialogSendReport.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(context);
            alertDialogSendReport = dialogBuilder.create();
            alertDialogSendReport.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = context.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_enable_realapi, null);
            alertDialogSendReport.setView(dialogView);


            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView msg = dialogView.findViewById(R.id.msg);
            msg.setText(msgTxt);
            View realApiLayoutContainer = dialogView.findViewById(R.id.realApiLayoutContainer);

            realApiLayoutContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDialogSingleCallBack != null) {
                        alertDialogSendReport.dismiss();
                        mDialogSingleCallBack.onPositiveClick("", "");
                    }
                }
            });
            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogSendReport.dismiss();
                }
            });


            alertDialogSendReport.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (WindowManager.BadTokenException bde) {
            bde.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }
    }


    public void upgradePackageDialog(String contactTxt, boolean isFromAdditionalService, final UpgradePackageDialogCallBack mUpgradePackageDialogCallBack) {
        try {
            if (alertDialogSendReport != null && alertDialogSendReport.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(context);
            alertDialogSendReport = dialogBuilder.create();
            alertDialogSendReport.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = context.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_upgrade_package, null);
            alertDialogSendReport.setView(dialogView);

            TextView btnUpgrade = dialogView.findViewById(R.id.btnUpgrade);
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView conatctTv = dialogView.findViewById(R.id.conatctTv);
            conatctTv.setText(contactTxt);

            btnUpgrade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUpgradePackageDialogCallBack != null) {
                        alertDialogSendReport.dismiss();
                        mUpgradePackageDialogCallBack.onUpgradeClick(isFromAdditionalService);
                    }
                }
            });
            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogSendReport.dismiss();
                }
            });


            alertDialogSendReport.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (WindowManager.BadTokenException bde) {
            bde.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }
    }

    public void DmtPendingDialog(String msg, String contactTxt, final DialogCallBack mDialogCallBack) {
        try {
            if (alertDialogSendReport != null && alertDialogSendReport.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(context);
            alertDialogSendReport = dialogBuilder.create();
            alertDialogSendReport.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            LayoutInflater inflater = context.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_dmt_pending, null);
            alertDialogSendReport.setView(dialogView);
            alertDialogSendReport.setCancelable(false);
            TextView btnUpgrade = dialogView.findViewById(R.id.btnUpgrade);
            TextView msgTv = dialogView.findViewById(R.id.msg);
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            AppCompatImageView ivMsg = dialogView.findViewById(R.id.ivMsg);
            TextView conatctTv = dialogView.findViewById(R.id.conatctTv);
            conatctTv.setText(contactTxt);
            if (contactTxt == null || contactTxt.isEmpty()) {
                conatctTv.setVisibility(View.GONE);
            }
            if (msg != null && !msg.isEmpty() && !msg.toLowerCase().equalsIgnoreCase("pending")) {
                msgTv.setText(msg);
            }
            Glide.with(context)
                    .asGif()
                    .load(R.drawable.pending_clock)
                    .into(ivMsg);
            btnUpgrade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDialogCallBack != null) {
                        alertDialogSendReport.dismiss();
                        mDialogCallBack.onPositiveClick();
                    }
                }
            });
            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogSendReport.dismiss();
                    if (mDialogCallBack != null) {
                        mDialogCallBack.onNegativeClick();
                    }
                }
            });


            alertDialogSendReport.show();
            alertDialogSendReport.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (WindowManager.BadTokenException bde) {
            bde.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }
    }

    public void serviceListDialog(final int parentId, String title, final ArrayList<AssignedOpType> opTypes, int type, final DialogServiceListCallBack mDialogServiceListCallBack) {
        try {
            if (alertDialogServiceList != null && alertDialogServiceList.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(context);
            alertDialogServiceList = dialogBuilder.create();
            alertDialogServiceList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = context.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_service_list, null);
            alertDialogServiceList.setView(dialogView);

            ImageView iconTv = dialogView.findViewById(R.id.icon);
            ImageView bgView = dialogView.findViewById(R.id.bgView);
            RelativeLayout imageContainer = dialogView.findViewById(R.id.imageContainer);
            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(context, opTypes.size() > 2 ? 3 : 2));
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView titleTv = dialogView.findViewById(R.id.titleTv);
            if (type == 2) {
                CardView cardView = dialogView.findViewById(R.id.cardView);
                cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimaryLight));
                titleTv.setTextColor(Color.WHITE);
            }
            titleTv.setText(title);
            //   iconTv.setImageResource(ServiceIcon.INSTANCE.parentIcon(parentId));

            HomeOptionListAdapter mDashboardOptionListAdapter = new HomeOptionListAdapter(opTypes, context,
                    new HomeOptionListAdapter.ClickView() {
                        @Override
                        public void onClick(AssignedOpType operator) {
                            alertDialogServiceList.dismiss();
                            if (mDialogServiceListCallBack != null) {
                                mDialogServiceListCallBack.onIconClick(operator);
                            }
                        }

                        @Override
                        public void onPackageUpgradeClick(boolean isFromAdditionalService, int serviceId) {
                            if (mDialogServiceListCallBack != null) {
                                mDialogServiceListCallBack.onUpgradePackage(isFromAdditionalService, serviceId);
                            }
                        }

                    }, R.layout.adapter_dashboard_option_grid, type);
            recyclerView.setAdapter(mDashboardOptionListAdapter);

            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogServiceList.dismiss();
                }
            });


            alertDialogServiceList.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (WindowManager.BadTokenException bde) {
            bde.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }
    }

    public void Successfullogout(CustomLoader loader, final String message, final Activity activity,
                                 LoginResponse mLoginResponse, String deviceId, String deviceSerialNum, AppPreferences mAppPreferences) {

        try {
            if (alertDialogLogout != null && alertDialogLogout.isShowing()) {
                return;
            }
            alertDialogLogout = new AlertDialog.Builder(activity).create();

            alertDialogLogout.setTitle("Logout!");

            alertDialogLogout.setMessage(message);

            alertDialogLogout.setButton(AlertDialog.BUTTON_POSITIVE, "Logout From All Device", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {
                    ApiFintechUtilMethods.INSTANCE.Logout(loader, activity, "3", mLoginResponse, deviceId, deviceSerialNum, mAppPreferences);

                }
            });

            alertDialogLogout.setButton(AlertDialog.BUTTON_NEGATIVE, "Logout ", (dialog, id) ->
                    ApiFintechUtilMethods.INSTANCE.Logout(loader, activity, "1", mLoginResponse, deviceId, deviceSerialNum, mAppPreferences));

            alertDialogLogout.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel", (dialog, id) -> dissmiss());
            alertDialogLogout.show();

              /*  alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                alertDialog.setContentText(message);
                // alertDialog.setCustomImage(R.drawable.ic_success);
                alertDialog.setConfirmButton("Logout From All Device", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        UtilMethods.INSTANCE.logout(activity);
                        activity.finish();
                    }
                });
                alertDialog.setNeutralButton("Logout", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        UtilMethods.INSTANCE.logout(activity);
                        activity.finish();
                    }
                });
                alertDialog.show();
                alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();

                    }
                });
                alertDialog.show();*/
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void channelFosListDialog(final CustomLoader loader,
                                     LoginResponse mLoginDataResponse, String deviceId, String deviceSerialNum) {
        try {
            if (alertDialogServiceList != null && alertDialogServiceList.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(context);
            alertDialogServiceList = dialogBuilder.create();
            alertDialogServiceList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = context.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_fos_channel_list, null);
            alertDialogServiceList.setView(dialogView);

            View imageContainerChannel = dialogView.findViewById(R.id.imageContainerChannel);

            View imageContainerFos = dialogView.findViewById(R.id.imageContainerFos);
            dialogView.findViewById(R.id.closeIv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogServiceList.dismiss();
                }
            });
            imageContainerChannel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HitFosChannelApi(false, loader,
                            mLoginDataResponse, deviceId, deviceSerialNum);
                    alertDialogServiceList.dismiss();
                }
            });
            imageContainerFos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HitFosChannelApi(true, loader,
                            mLoginDataResponse, deviceId, deviceSerialNum);

                    alertDialogServiceList.dismiss();
                }
            });


            alertDialogServiceList.show();


        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }

    public void HitFosChannelApi(boolean isFromFos, final CustomLoader loader,
                                 LoginResponse mLoginDataResponse, String deviceId, String deviceSerialNum) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(context)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            final Calendar myCalendar = Calendar.getInstance();
            String myFormat = "dd-MMM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            String today = sdf.format(myCalendar.getTime());
            String filterFromDate = today;
            String filterToDate = today;
            ApiFintechUtilMethods.INSTANCE.AccStmtAndCollFilterFosClick(context, "5000", filterFromDate,
                    filterToDate, isFromFos ? "1" : "2", loader, mLoginDataResponse, deviceId, deviceSerialNum
                    , 0, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            FosAccStmtAndCollReportResponse fosAccStmtAndCollReportResponse = (FosAccStmtAndCollReportResponse) object;
                            if (fosAccStmtAndCollReportResponse != null && fosAccStmtAndCollReportResponse.getAscReport() != null &&
                                    fosAccStmtAndCollReportResponse.getAscReport().size() > 0) {
                                Intent i = new Intent(context, VoucherEntryActivity.class);
                                i.putParcelableArrayListExtra("Data", fosAccStmtAndCollReportResponse.getAscReport());
                                context.startActivity(i);
                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(context, "Data not found");
                            }

                            /*
                            dataParse(fosAccStmtAndCollReportResponse);*/
                        }

                        @Override
                        public void onError(int error) {

                        }


                    });
        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(context);
        }
    }

    public void channelAreaListDialog(ArrayList<AreaMaster> areaMaster) {
        try {
            if (alertDialogServiceList != null && alertDialogServiceList.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(context);
            alertDialogServiceList = dialogBuilder.create();
            alertDialogServiceList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = context.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_channel_area_list, null);
            alertDialogServiceList.setView(dialogView);

            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 3));

            if (!areaMaster.get(0).getArea().equalsIgnoreCase("All")) {
                AreaMaster master = new AreaMaster();
                master.setArea("All");
                master.setAreaID(0);
                areaMaster.add(0, master);
            }

            recyclerView.setAdapter(new ChannelAreaListAdapter(areaMaster, context, alertDialogServiceList));

            dialogView.findViewById(R.id.closeIv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogServiceList.dismiss();
                }
            });


            alertDialogServiceList.show();


        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }

    public void Successfulok(final String message, final Activity activity) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            alertDialog.setContentText(message);
            // alertDialog.setCustomImage(R.drawable.ic_success);
            alertDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    activity.finish();
                }
            });
            alertDialog.show();

        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void Errorok(final String message, final Activity activity) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            alertDialog.setContentText(message);
            // alertDialog.setCustomImage(R.drawable.ic_success);
            alertDialog.setConfirmButton("Ok", sweetAlertDialog -> activity.finish());
            alertDialog.show();

        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void WarningWithDoubleBtnCallBack(final String message, final String tite, String posBtn, boolean isCancelable, final DialogCallBack dialogCallBack) {

        try {

            if (alertDialog != null && alertDialog.isShowing()) {
                return;
            }

            alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
            alertDialog.setContentText(message);
            alertDialog.setTitleText(tite);
            alertDialog.setCancelable(isCancelable);
            // alertDialogSingleBtn.setCustomImage(R.drawable.ic_success);
            alertDialog.setConfirmButton(posBtn, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    if (dialogCallBack != null) {
                        dialogCallBack.onPositiveClick();
                    }
                }
            });
            alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();

                }
            });

            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (Exception e) {

        }

    }

    public void WarningWithDoubleBtnCallBack(final String message, String posBtn, boolean isCancelable, final DialogCallBack dialogCallBack) {

        try {

            if (alertDialog != null && alertDialog.isShowing()) {
                return;
            }

            alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
            alertDialog.setContentText(message);
            alertDialog.setCancelable(isCancelable);
            // alertDialogSingleBtn.setCustomImage(R.drawable.ic_success);
            alertDialog.setConfirmButton(posBtn, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    if (dialogCallBack != null) {
                        dialogCallBack.onPositiveClick();
                    }
                }
            });
            alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();

                }
            });

            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void Warning(final String message) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
            alertDialog.setContentText(message);
            // alertDialog.setCustomImage(R.drawable.ic_error_red_24dp);
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void NetworkError(String title, final String message) {
        try {
            alertDialog.changeAlertType(SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            alertDialog.setContentText(message);
            alertDialog.setTitleText(title);
            alertDialog.setCustomImage(R.drawable.ic_connection_lost_24dp);
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void WarningWithSingleBtnCallBack(final String title, final String message, final String btnTxt,
                                             boolean isCancelable, final DialogCallBack dialogCallBack) {

        try {

            if (alertDialog != null && alertDialog.isShowing()) {
                return;
            }

            alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
            alertDialog.setContentText(message);
            alertDialog.setTitle(title);
            alertDialog.setCancelable(isCancelable);

            alertDialog.setConfirmButton(btnTxt, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                if (dialogCallBack != null) {
                    dialogCallBack.onPositiveClick();
                }
            });
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void ErrorWithSingleBtnCallBack(final String title, final String message, final String btnTxt,
                                           boolean isCancelable, final DialogCallBack dialogCallBack) {

        try {

            if (alertDialog != null && alertDialog.isShowing()) {
                return;
            }

            alertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            alertDialog.setContentText(message);
            alertDialog.setCancelable(isCancelable);

            alertDialog.setConfirmButton(btnTxt, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                if (dialogCallBack != null) {
                    dialogCallBack.onPositiveClick();
                }
            });
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void Warning(final String title, final String message, final String btnTxt, boolean isCancel, final DialogCallBack dialogCallBack) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
            alertDialog.setContentText(message);
            alertDialog.setTitle(title);
            alertDialog.setCancelable(isCancel);
            // alertDialog.setCustomImage(R.drawable.ic_error_red_24dp);
            alertDialog.setCancelButton(btnTxt, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                if (dialogCallBack != null) {
                    dialogCallBack.onNegativeClick();
                }
            });
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void Warning(final String title, final String message) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
            alertDialog.setContentText(message);
            alertDialog.setTitle(title);
            // alertDialog.setCustomImage(R.drawable.ic_error_red_24dp);
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void Error(final String message) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            if (message != null && !message.isEmpty() && message.length() > 1) {
                alertDialog.setContentText(message);
                if (message.contains("(redirectToLogin)")) {
                    alertDialog.setConfirmButton("Ok", sweetAlertDialog -> ApiFintechUtilMethods.INSTANCE.logout(context, ApiFintechUtilMethods.INSTANCE.getAppPreferences(context)));
                }
            } else {
                alertDialog.setContentText("Error");
            }
            // alertDialog.setCustomImage(R.drawable.ic_error_red_24dp);
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void ErrorWithTitle(final String title, final String message) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            alertDialog.setTitle(title);
            alertDialog.setContentText(message);
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {


        }
    }

    public void ErrorWithFinsh(final String message) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            alertDialog.setContentText(message);
            // alertDialog.setCustomImage(R.drawable.ic_error_red_24dp);
            alertDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    ((Activity) context).finish();
                }
            });
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void dissmiss() {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }
    }

    public void WarningWithCallBack(final String message, String posBtn, boolean isCancelable, final DialogCallBack dialogCallBack) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
            alertDialog.setContentText(message);
            alertDialog.setCancelable(isCancelable);
            // alertDialog.setCustomImage(R.drawable.ic_success);
            alertDialog.setConfirmButton(posBtn, sweetAlertDialog -> {
                alertDialog.dismiss();
                if (dialogCallBack != null) {
                    dialogCallBack.onPositiveClick();
                }
            });
            alertDialog.setCancelButton("Cancel", sweetAlertDialog -> {
                alertDialog.dismiss();
                if (dialogCallBack != null) {
                    dialogCallBack.onNegativeClick();
                }
            });
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void WarningWithCallBack(final String message, final String title, String posBtn, boolean isCancelable, final DialogCallBack dialogCallBack) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
            alertDialog.setContentText(message);
            alertDialog.setCancelable(isCancelable);
            alertDialog.setTitle(title);
            alertDialog.setConfirmButton(posBtn, sweetAlertDialog -> {
                alertDialog.dismiss();
                if (dialogCallBack != null) {
                    dialogCallBack.onPositiveClick();
                }
            });
            alertDialog.setCancelButton("Cancel", sweetAlertDialog -> {
                alertDialog.dismiss();
                if (dialogCallBack != null) {
                    dialogCallBack.onNegativeClick();
                }
            });
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void WarningWithSingleBtnCallBack(final String message, String posBtn, boolean isCancelable, final DialogCallBack dialogCallBack) {

        try {

            if (alertDialog != null && alertDialog.isShowing()) {
                return;
            }

            alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
            alertDialog.setContentText(message);
            alertDialog.setCancelable(isCancelable);
            // alertDialogSingleBtn.setCustomImage(R.drawable.ic_success);
            alertDialog.setConfirmButton(posBtn, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                ApiFintechUtilMethods.INSTANCE.isPassChangeDialogShowing = false;
                if (dialogCallBack != null) {
                    dialogCallBack.onPositiveClick();
                }
            });
            ApiFintechUtilMethods.INSTANCE.isPassChangeDialogShowing = true;
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }


    public void dmtListDialog(String title, final ArrayList<OperatorList> opTypes, final DialogDMTListCallBack mDialogDMTListCallBack) {
        try {
            if (alertDialogServiceList != null && alertDialogServiceList.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(context);
            alertDialogServiceList = dialogBuilder.create();
            alertDialogServiceList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = context.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_service_list, null);
            alertDialogServiceList.setView(dialogView);
            CardView cardView = dialogView.findViewById(R.id.cardView);
            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            ImageView iconTv = dialogView.findViewById(R.id.icon);
            ImageView bgView = dialogView.findViewById(R.id.bgView);
            RelativeLayout imageContainer = dialogView.findViewById(R.id.imageContainer);
            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(context, opTypes.size() > 2 ? 3 : 2));
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView titleTv = dialogView.findViewById(R.id.titleTv);
            titleTv.setTextColor(Color.BLACK);
            titleTv.setText(title);
            //   iconTv.setImageResource(ServiceIcon.INSTANCE.parentIcon(parentId));

            DMTOptionListAdapter mDMTOptionListAdapter = new DMTOptionListAdapter(opTypes, context, new DMTOptionListAdapter.ClickView() {
                @Override
                public void onClick(OperatorList mOperatorList) {
                    alertDialogServiceList.dismiss();
                    if (mDialogDMTListCallBack != null) {
                        mDialogDMTListCallBack.onIconClick(mOperatorList);
                    }
                }


            }, R.layout.adapter_dashboard_option_grid);
            recyclerView.setAdapter(mDMTOptionListAdapter);

            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogServiceList.dismiss();
                }
            });


            alertDialogServiceList.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }

    public void UPIListDialog(String title, BalanceResponse balanceCheckResponse) {
        try {
            if (alertDialogServiceList != null && alertDialogServiceList.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(context);
            alertDialogServiceList = dialogBuilder.create();
            alertDialogServiceList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = context.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_upi_payment_list, null);
            alertDialogServiceList.setView(dialogView);
            /*CardView cardView = dialogView.findViewById(R.id.cardView);
            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
*/
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView titleTv = dialogView.findViewById(R.id.titleTv);
            titleTv.setTextColor(Color.WHITE);
            titleTv.setText(title);
            //   iconTv.setImageResource(ServiceIcon.INSTANCE.parentIcon(parentId));

            dialogView.findViewById(R.id.upiBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogServiceList.dismiss();
                    context.startActivity(new Intent(context, UPIPayActivity.class)
                            .putExtra("BalanceData", balanceCheckResponse)
                            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                }
            });
            dialogView.findViewById(R.id.scanBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogServiceList.dismiss();
                    context.startActivity(new Intent(context, QRScanActivity.class)
                            .putExtra("BalanceData", balanceCheckResponse)
                            .putExtra("FROM_SCANPAY", true)
                            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    );
                }
            });

            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogServiceList.dismiss();
                }
            });


            alertDialogServiceList.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }


    public void FundRequestNotificationDialog(Activity mActivity, List<NotificationData> sliderList, AppPreferences mAppPreferences) {
        if (mFundRequestNotificationDialog != null && mFundRequestNotificationDialog.isShowing()) {
            return;
        }
        mFundRequestNotificationDialog = new Dialog(mActivity, R.style.full_screen_dialog);

        View dialogtView = mActivity.getLayoutInflater().inflate(R.layout.dialog_fund_request_notification, null);

        ImageView closeIv = dialogtView.findViewById(R.id.closeIv);

        ViewPager viewpager = dialogtView.findViewById(R.id.viewpager);

        if (sliderList != null && sliderList.size() > 0) {
            mSliderImageAdapter = new FundRequestSliderAdapter(mActivity, sliderList, new FundRequestSliderAdapter.BtnClicks() {
                @Override
                public void onCloseClick(int position) {

                    sliderList.remove(position);
                    mSliderImageAdapter.notifyDataSetChanged();
                    mAppPreferences.set(ApplicationConstant.INSTANCE.FunRqstNotificationList, new Gson().toJson(sliderList));

                    if (sliderList.size() == 0) {
                        mFundRequestNotificationDialog.dismiss();
                    }


                }

                @Override
                public void onAcceptClick(int position) {

                }

                @Override
                public void onRejectClick(int position) {

                }
            });

            viewpager.setAdapter(mSliderImageAdapter);
            viewpager.setOffscreenPageLimit(mSliderImageAdapter.getCount());

            //A little space between pages
            viewpager.setPageMargin(10);
            viewpager.setPadding(20, 20, 20, 20);
            viewpager.setClipToPadding(false);

            //If hardware acceleration is enabled, you should also remove
            // clipping on the pager for its children.
            viewpager.setClipChildren(false);

        } else {
            mFundRequestNotificationDialog.dismiss();
        }


        closeIv.setOnClickListener(v -> mFundRequestNotificationDialog.dismiss());
        mFundRequestNotificationDialog.setContentView(dialogtView);
        mFundRequestNotificationDialog.setCancelable(false);
        mFundRequestNotificationDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        mFundRequestNotificationDialog.show();
    }

    public void showRejectDialog(Activity mContext, final int uId, final String name, String mobile, String amount,
                                 final int paymentId, LoginResponse mLoginDataResponse, AppPreferences mAppPreferences) {
        try {
            if (alertDialogFundTransfer != null && alertDialogFundTransfer.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(mContext, R.style.alert_dialog_light);
            alertDialogFundTransfer = dialogBuilder.create();
            alertDialogFundTransfer.setCancelable(false);
            alertDialogFundTransfer.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = mContext.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_fund_transfer, null);
            alertDialogFundTransfer.setView(dialogView);

            SwitchCompat actCreditSwitch = dialogView.findViewById(R.id.actCreditSwitch);
            if (mLoginDataResponse.isAccountStatement()) {
                actCreditSwitch.setVisibility(View.VISIBLE);
                actCreditSwitch.setChecked(true);
            } else {
                actCreditSwitch.setVisibility(View.GONE);
            }
            View creditDebitView = dialogView.findViewById(R.id.creditDebitView);


            if (mLoginDataResponse.getData().isCanDebit()) {
                creditDebitView.setVisibility(View.VISIBLE);
            } else {
                creditDebitView.setVisibility(View.GONE);
            }
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            LinearLayout changeTypeView = dialogView.findViewById(R.id.changetTypeView);
            changeTypeView.setVisibility(View.GONE);

            final AppCompatTextView titleTv = dialogView.findViewById(R.id.titleTv);
            titleTv.setText("Fund Reject Form");
            final AppCompatTextView prepaidTv = dialogView.findViewById(R.id.prepaidTv);
            final AppCompatTextView utilityTv = dialogView.findViewById(R.id.utilityTv);
            final AppCompatTextView creditTv = dialogView.findViewById(R.id.creditTv);
            final AppCompatTextView debitTv = dialogView.findViewById(R.id.debitTv);
            AppCompatTextView nameTv = dialogView.findViewById(R.id.nameTv);
            AppCompatTextView mobileTv = dialogView.findViewById(R.id.mobileTv);
            final AppCompatEditText amountEt = dialogView.findViewById(R.id.amountEt);
            final AppCompatTextView amountTv = dialogView.findViewById(R.id.amountTv);
            final AppCompatTextView pinPassTv = dialogView.findViewById(R.id.pinPassTv);
            final AppCompatEditText pinPassEt = dialogView.findViewById(R.id.pinPassEt);
            if (mLoginDataResponse.getData().getIsDoubleFactor()) {
                pinPassTv.setVisibility(View.VISIBLE);
                pinPassEt.setVisibility(View.VISIBLE);
            } else {
                pinPassTv.setVisibility(View.GONE);
                pinPassEt.setVisibility(View.GONE);
            }
            amountTv.setVisibility(View.VISIBLE);
            amountEt.setVisibility(View.GONE);

            AppCompatEditText commisionTv = dialogView.findViewById(R.id.commisionTv);
            commisionTv.setVisibility(View.GONE);
            AppCompatTextView commisionTitleTv = dialogView.findViewById(R.id.commisionTitleTv);
            commisionTitleTv.setVisibility(View.GONE);
            final AppCompatEditText remarksEt = dialogView.findViewById(R.id.remarksEt);
            final AppCompatTextView amountTxtTv = dialogView.findViewById(R.id.amountTxtTv);
            View totalAmtView = dialogView.findViewById(R.id.totalAmtView);
            totalAmtView.setVisibility(View.GONE);
            AppCompatTextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);
            AppCompatTextView fundTransferBtn = dialogView.findViewById(R.id.fundTransferBtn);
            nameTv.setText(name);
            mobileTv.setText(mobile);

            amountTv.setText(amount);


            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogFundTransfer.dismiss();
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogFundTransfer.dismiss();
                }
            });
            fundTransferBtn.setText("Reject");
            fundTransferBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pinPassEt.getVisibility() == View.VISIBLE && pinPassEt.getText().toString().isEmpty()) {
                        pinPassEt.setError("Please Enter Pin Password");
                        pinPassEt.requestFocus();
                        return;
                    }
                    if (mContext instanceof FundOrderPendingActivity) {
                        ((FundOrderPendingActivity) mContext).rejectApi(actCreditSwitch.isChecked(), pinPassEt.getText().toString(), paymentId, uId, remarksEt.getText().toString(), amountTv.getText().toString(), name, alertDialogFundTransfer);
                    }
                }
            });


            alertDialogFundTransfer.show();
            alertDialogFundTransfer.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showKycMsg(final String title, final String message, int icon, final boolean isCanclable, GetUserResponse mGetUserResponse) {

        try {
            if (title != null && !title.isEmpty()) {
                alertDialog.setTitle(title);
            } else {
                alertDialog.setTitle("Attention!");
            }
            if (message != null && !message.isEmpty() && message.length() > 1) {
                alertDialog.setContentText(message);
            } else {
                alertDialog.setContentText("Failed");
            }
            alertDialog.setCustomImage(icon);
            alertDialog.setCancelable(isCanclable);
            alertDialog.setCanceledOnTouchOutside(isCanclable);
            alertDialog.setConfirmClickListener(sweetAlertDialog -> {
                if (!isCanclable) {
                    context.startActivity(new Intent(context, UpdateProfileActivity.class)
                            .putExtra("UserData", mGetUserResponse)
                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                }

                sweetAlertDialog.cancel();
            });
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void showMessageMNP(final String title, final String message, String btnTxt, int id, DialogOneCallBack mCallBack) {

        try {
            if (title != null && !title.isEmpty()) {
                alertDialog.setTitle(title);
            } else {
                alertDialog.setTitle("Attention!");
            }
            if (message != null && !message.isEmpty() && message.length() > 1) {
                alertDialog.setContentText(message);
            } else {
                alertDialog.setContentText("Failed");
            }
            alertDialog.setCustomImage(id);
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setConfirmText(btnTxt);
            alertDialog.setConfirmClickListener(sweetAlertDialog -> {
                if (mCallBack != null) {
                    mCallBack.onPositiveClick();
                }
                sweetAlertDialog.cancel();
            });
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (Exception e) {

        }

    }

    public interface DialogOneCallBack {
        void onPositiveClick();
    }

    public void showFundTransferDialog(Activity mContext, final int uId, final String name, String mobile, final double commission,
                                       String amount, final int paymentId, LoginResponse mLoginDataResponse, AppPreferences mAppPreferences) {
        try {
            if (alertDialogFundTransfer != null && alertDialogFundTransfer.isShowing()) {
                return;
            }


            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(mContext, R.style.alert_dialog_light);
            alertDialogFundTransfer = dialogBuilder.create();
            alertDialogFundTransfer.setCancelable(false);
            alertDialogFundTransfer.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = mContext.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_fund_transfer, null);
            alertDialogFundTransfer.setView(dialogView);

            SwitchCompat actCreditSwitch = dialogView.findViewById(R.id.actCreditSwitch);
            if (mLoginDataResponse.isAccountStatement()) {
                actCreditSwitch.setVisibility(View.VISIBLE);
                actCreditSwitch.setChecked(true);
            } else {
                actCreditSwitch.setVisibility(View.GONE);
            }
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            LinearLayout changeTypeView = dialogView.findViewById(R.id.changetTypeView);
            changeTypeView.setVisibility(View.GONE);
            final AppCompatTextView prepaidTv = dialogView.findViewById(R.id.prepaidTv);
            final AppCompatTextView utilityTv = dialogView.findViewById(R.id.utilityTv);
            final AppCompatTextView creditTv = dialogView.findViewById(R.id.creditTv);
            final AppCompatTextView debitTv = dialogView.findViewById(R.id.debitTv);
            AppCompatTextView nameTv = dialogView.findViewById(R.id.nameTv);
            AppCompatTextView mobileTv = dialogView.findViewById(R.id.mobileTv);
            final AppCompatEditText amountEt = dialogView.findViewById(R.id.amountEt);
            final AppCompatTextView amountTv = dialogView.findViewById(R.id.amountTv);
            amountTv.setVisibility(View.VISIBLE);
            amountEt.setVisibility(View.GONE);

            AppCompatEditText commisionTv = dialogView.findViewById(R.id.commisionTv);
            final AppCompatEditText remarksEt = dialogView.findViewById(R.id.remarksEt);
            final AppCompatTextView amountTxtTv = dialogView.findViewById(R.id.amountTxtTv);
            final AppCompatTextView pinPassTv = dialogView.findViewById(R.id.pinPassTv);
            final AppCompatEditText pinPassEt = dialogView.findViewById(R.id.pinPassEt);
            if (mLoginDataResponse.getData().getIsDoubleFactor()) {
                pinPassTv.setVisibility(View.VISIBLE);
                pinPassEt.setVisibility(View.VISIBLE);
            } else {
                pinPassTv.setVisibility(View.GONE);
                pinPassEt.setVisibility(View.GONE);
            }
            AppCompatTextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);
            AppCompatTextView fundTransferBtn = dialogView.findViewById(R.id.fundTransferBtn);
            nameTv.setText(name);
            mobileTv.setText(mobile);
            commisionTv.setText(commission + "");


            View creditDebitView = dialogView.findViewById(R.id.creditDebitView);

            if (mLoginDataResponse.getData().isCanDebit()) {
                creditDebitView.setVisibility(View.VISIBLE);
            } else {
                creditDebitView.setVisibility(View.GONE);
            }

            try {
                double amountVal = Double.parseDouble(amount);

                double calculatedVal = amountVal + ((amountVal * commission) / 100);
                amountTxtTv.setText("\u20B9 " + calculatedVal);
            } catch (NumberFormatException nfe) {

            }
            amountTv.setText(amount);


            closeIv.setOnClickListener(v -> alertDialogFundTransfer.dismiss());

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogFundTransfer.dismiss();
                }
            });

            fundTransferBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pinPassEt.getVisibility() == View.VISIBLE && pinPassEt.getText().toString().isEmpty()) {
                        pinPassEt.setError("Please Enter Pin Password");
                        pinPassEt.requestFocus();
                        return;
                    }
                    if (mContext instanceof FundOrderPendingActivity) {
                        ((FundOrderPendingActivity) mContext).fundTransferApi(actCreditSwitch.isChecked(), pinPassEt.getText().toString(), paymentId, uId, remarksEt.getText().toString(), amountTv.getText().toString(), name, alertDialogFundTransfer);
                    }
                }
            });


            alertDialogFundTransfer.show();
            alertDialogFundTransfer.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }

    public void ErrorVPN(final Activity activity) {

            try {
                alertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                alertDialog.setContentText("In your device VPN enable please disable VPN before using app.");
                // alertDialog.setCustomImage(R.drawable.ic_success);
                alertDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            activity.finishAffinity();
                        } else {
                            activity.finish();
                        }
                    }
                });
                alertDialog.show();

            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {


        }
    }

    public SweetAlertDialog returnDialog() {
        return alertDialog;
    }

    public void accountOpenOPListDialog(String title, int serviceId, ArrayList<OperatorList> mAccountOpenOPTypes,
                                        final DialogOpenAccountOpListCallBack mDialogOpenAccountOpListCallBack) {
        try {
            if (alertDialogServiceList != null && alertDialogServiceList.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(context);
            alertDialogServiceList = dialogBuilder.create();
            alertDialogServiceList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = context.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_service_list, null);
            alertDialogServiceList.setView(dialogView);
            CardView cardView = dialogView.findViewById(R.id.cardView);
            // cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            ImageView iconTv = dialogView.findViewById(R.id.icon);
            ImageView bgView = dialogView.findViewById(R.id.bgView);
            RelativeLayout imageContainer = dialogView.findViewById(R.id.imageContainer);
            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView titleTv = dialogView.findViewById(R.id.titleTv);
            //  titleTv.setTextColor(Color.WHITE);
            titleTv.setText(title);

            AccountOpenOPListAdapter mAccountOpenOPListAdapter = new AccountOpenOPListAdapter(mAccountOpenOPTypes, context, new AccountOpenOPListAdapter.ClickView() {
                @Override
                public void onClick(OperatorList mOperatorList) {
                    alertDialogServiceList.dismiss();
                    if (mDialogOpenAccountOpListCallBack != null) {
                        mDialogOpenAccountOpListCallBack.onIconClick(mOperatorList, serviceId);
                    }
                }


            });
            recyclerView.setAdapter(mAccountOpenOPListAdapter);

            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogServiceList.dismiss();
                }
            });


            alertDialogServiceList.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }

    public void openAddressListDialog(Activity mActivity, List<Address> mAddresses, int addressId, int INTENT_ADD_ADDRESS,
                                      ShoppingSelectAddress mSelectAddress) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);

        View sheetView = mActivity.getLayoutInflater().inflate(R.layout.dialog_shopping_address_list, null);
        RecyclerView recyclerView = sheetView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        ImageView closeIv = sheetView.findViewById(R.id.closeIv);
        TextView addBtn = sheetView.findViewById(R.id.addBtn);

        AddressBottomSheetShoppingAdapter mAddressListAdapter = new AddressBottomSheetShoppingAdapter(mAddresses, mActivity, mSelectAddress, addressId, mBottomSheetDialog);
        recyclerView.setAdapter(mAddressListAdapter);

        closeIv.setOnClickListener(v -> mBottomSheetDialog.dismiss());
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                mActivity.startActivityForResult(new Intent(mActivity, AddAddressShoppingActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_ADD_ADDRESS);
            }
        });

        mBottomSheetDialog.setCancelable(false);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

    }

    public void openStateCitiesListDialog(Activity mActivity, List<StatesCities> mList, boolean isState, ShoppingSelectStateCities mSelectStateCities) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mActivity);

        View sheetView = mActivity.getLayoutInflater().inflate(R.layout.dialog_shopping_state_city_list, null);
        RecyclerView recyclerView = sheetView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        ImageView closeIv = sheetView.findViewById(R.id.closeIv);
        EditText search_all = sheetView.findViewById(R.id.search_all);
        ImageView clearIcon = sheetView.findViewById(R.id.clearIcon);
        clearIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_all.setText("");
            }
        });
        StatesCitiesListShoppingAdapter mStatesCitiesListAdapter = new StatesCitiesListShoppingAdapter(mList, mActivity, mSelectStateCities, isState, mBottomSheetDialog);
        recyclerView.setAdapter(mStatesCitiesListAdapter);
        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mStatesCitiesListAdapter != null) {
                    mStatesCitiesListAdapter.getFilter().filter(s);
                }
            }
        });


        closeIv.setOnClickListener(v -> mBottomSheetDialog.dismiss());
        mBottomSheetDialog.setContentView(sheetView);
       /* BottomSheetBehavior
                .from(mBottomSheetDialog.findViewById(R.id.design_bottom_sheet))
                .setState(BottomSheetBehavior.STATE_EXPANDED);*/

        mBottomSheetDialog.show();

    }

    public interface UpgradePackageDialogCallBack {
        void onUpgradeClick(boolean isFromAdditionalService);


    }

    public interface DialogOpenAccountOpListCallBack {
        void onIconClick(OperatorList mOperatorList, int serviceId);
    }

    public interface DialogSingleCallBack {
        void onPositiveClick(String mobile, String emailId);


    }

    public interface DialogServiceListCallBack {
        void onIconClick(AssignedOpType opType);

        void onUpgradePackage(boolean isFromAdditionalService, int serviceId);

    }

    public interface DialogDMTListCallBack {
        void onIconClick(OperatorList mOperatorList);
    }

    public interface DialogCallBack {
        void onPositiveClick();

        void onNegativeClick();
    }


}
