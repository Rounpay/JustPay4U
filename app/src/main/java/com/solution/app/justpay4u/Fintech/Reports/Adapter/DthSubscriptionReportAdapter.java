package com.solution.app.justpay4u.Fintech.Reports.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.solution.app.justpay4u.Api.Fintech.Object.DthSubscriptionReport;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.RechargeSlipActivity;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.MyPrintDocumentAdapter;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class DthSubscriptionReportAdapter extends RecyclerView.Adapter<DthSubscriptionReportAdapter.MyViewHolder> {
    public TextInputLayout tilRemark;
    public EditText etRemark;
    public Button okButton;
    public Button cancelButton;
    String charText = "";
    int resourceId = 0;
    CustomAlertDialog mCustomAlertDialog;

    int rollId;
    RequestOptions requestOptions;
    LoginResponse mLoginDataResponse;
    String deviceid, deviceSerialNum;
    private ArrayList<DthSubscriptionReport> rechargeStatus;
    private ArrayList<DthSubscriptionReport> transactionsList;
    private Activity mContext;
    private CustomLoader mCustomLoader;

    public DthSubscriptionReportAdapter(ArrayList<DthSubscriptionReport> transactionsList, Activity mContext, RequestOptions requestOptions,
                                        LoginResponse mLoginDataResponse, CustomAlertDialog mCustomAlertDialog, CustomLoader mCustomLoader,
                                        String deviceid, String deviceSerialNum) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.deviceid = deviceid;
        this.deviceSerialNum = deviceSerialNum;
        this.rollId = mLoginDataResponse.getData().getRoleID();
        this.rechargeStatus = new ArrayList<>();
        this.rechargeStatus.addAll(transactionsList);
        this.mLoginDataResponse = mLoginDataResponse;
        this.mCustomAlertDialog = mCustomAlertDialog;
        this.mCustomLoader = mCustomLoader;
        this.requestOptions = requestOptions;
    }

    @Override
    public DthSubscriptionReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dth_subscription_report, parent, false);

        return new DthSubscriptionReportAdapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(final DthSubscriptionReportAdapter.MyViewHolder holder, int position) {
        final DthSubscriptionReport operator = transactionsList.get(position);
        holder.operatorName.setText("" + operator.getOperator());
        holder.packageName.setText(operator.getPackageName() + "");
        holder.customerName.setText(operator.getCustomerName() + "");
        holder.address.setText(operator.getAddress() + " - " + operator.getPincode());
        holder.txn.setText("" + operator.getTransactionID());
        holder.balance.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getBalance());
        holder.lastbalance.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getOpening());
        holder.amount.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getRequestedAmount());
        holder.debit.setText("" + mContext.getResources().getString(R.string.rupiya) + " -" + operator.getAmount());
        holder.comm.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getCommission());
        holder.source.setText(operator.getRequestMode());

        if (operator.getTechnicianName() != null && !operator.getTechnicianName().isEmpty()) {
            holder.techName.setText(operator.getTechnicianName());
            holder.techNameView.setVisibility(View.VISIBLE);
        } else {
            holder.techNameView.setVisibility(View.GONE);
        }
        if (operator.getTechnicianMobile() != null && !operator.getTechnicianMobile().isEmpty()) {
            holder.techMobile.setText(operator.getTechnicianMobile());
            holder.techMobileView.setVisibility(View.VISIBLE);
        } else {
            holder.techMobileView.setVisibility(View.GONE);
        }
        if (operator.getCustomerID() != null && !operator.getCustomerID().isEmpty()) {
            holder.customerId.setText(operator.getCustomerID());
            holder.customerIdView.setVisibility(View.VISIBLE);
        } else {
            holder.customerIdView.setVisibility(View.GONE);
        }
        if (operator.getStbid() != null && !operator.getStbid().isEmpty()) {
            holder.stbId.setText(operator.getStbid());
            holder.stbIdView.setVisibility(View.VISIBLE);
        } else {
            holder.stbIdView.setVisibility(View.GONE);
        }
        if (operator.getVcno() != null && !operator.getVcno().isEmpty()) {
            holder.vc.setText(operator.getVcno());
            holder.vcView.setVisibility(View.VISIBLE);
        } else {
            holder.vcView.setVisibility(View.GONE);
        }
        if (operator.getInstalltionCharges() != null && !operator.getInstalltionCharges().isEmpty()) {
            holder.installCharge.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getInstalltionCharges()));
            holder.installChargeView.setVisibility(View.VISIBLE);
        } else {
            holder.installChargeView.setVisibility(View.GONE);
        }
        if (operator.getInstallationTime() != null && !operator.getInstallationTime().isEmpty()) {
            holder.installTime.setText(Utility.INSTANCE.formatedDate(operator.getInstallationTime()));
            holder.installTimeView.setVisibility(View.VISIBLE);
        } else {
            holder.installTimeView.setVisibility(View.GONE);
        }
        if (operator.getApprovalTime() != null && !operator.getApprovalTime().isEmpty()) {
            holder.approveTime.setText(Utility.INSTANCE.formatedDate(operator.getApprovalTime()));
            holder.approveTimeView.setVisibility(View.VISIBLE);
        } else {
            holder.approveTimeView.setVisibility(View.GONE);
        }
        if (operator.getRemark() != null && !operator.getRemark().isEmpty()) {
            holder.remark.setText(operator.getRemark());
            holder.remarkView.setVisibility(View.VISIBLE);
        } else {
            holder.remarkView.setVisibility(View.GONE);
        }


        if (rollId == 3 || rollId == 2) {
            holder.outletDetailView.setVisibility(View.GONE);
        } else {
            holder.outletDetailView.setVisibility(View.VISIBLE);
            holder.outlet.setText(operator.getOutletName() + "");
            holder.outletNo.setText(operator.getMobileNo() + "");
        }

        if (operator.isCommType() == true) {
            holder.CommissionLabel.setText("Sur");
            holder.comm.setTextColor(mContext.getResources().getColor(R.color.green));
        } else {
            holder.comm.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.CommissionLabel.setText("Comm");
        }

        //  holder.opid.setText(" "  + operator.getOpID());
        holder.date.setText(Utility.INSTANCE.formatedDate(operator.getEntryDate() + ""));
        holder.mobile.setText("" + operator.getAccount());

        if (operator.getLiveID() != null && !operator.getLiveID().isEmpty()) {
            holder.liveIdView.setVisibility(View.VISIBLE);
            holder.liveID.setText("" + operator.getLiveID());

        } else {
            holder.liveIdView.setVisibility(View.GONE);
        }

        holder.status.setText(operator.getStatus_() + "");
        if (operator.getStatus() == 2) {
            holder.share.setVisibility(View.VISIBLE);
            //  holder.print.setVisibility(View.VISIBLE);
            holder.statusIcon.setImageResource(R.drawable.ic_success);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (operator.getStatus() == 3) {
            holder.share.setVisibility(View.GONE);
            //  holder.print.setVisibility(View.GONE);
            holder.statusIcon.setImageResource(R.drawable.ic_failed);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_red));
        } else if (operator.getBookingStatus() == 1) {
            holder.share.setVisibility(View.GONE);
            // holder.print.setVisibility(View.GONE);
            holder.statusIcon.setImageResource(R.drawable.ic_pending);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_amber));
        } else {
            holder.share.setVisibility(View.GONE);
            // holder.print.setVisibility(View.GONE);
            holder.statusIcon.setImageResource(R.drawable.ic_warning_other);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_deep_orange));

        }


        if (operator.getBookingStatus() == 4) {
            holder.statusSubscription.setTextColor(mContext.getResources().getColor(R.color.green));
            if (operator.getBookingStatus_() != null && !operator.getBookingStatus_().isEmpty()) {
                holder.statusSubscription.setText(operator.getBookingStatus_());
            } else {
                holder.statusSubscription.setText("Completed");
            }
        } else if (operator.getBookingStatus() == 5) {
            holder.statusSubscription.setTextColor(mContext.getResources().getColor(R.color.color_red));
            if (operator.getBookingStatus_() != null && !operator.getBookingStatus_().isEmpty()) {
                holder.statusSubscription.setText(operator.getBookingStatus_());
            } else {
                holder.statusSubscription.setText("Rejected");
            }
        } else if (operator.getBookingStatus() == 2) {
            holder.statusSubscription.setTextColor(mContext.getResources().getColor(R.color.color_cyan));
            if (operator.getBookingStatus_() != null && !operator.getBookingStatus_().isEmpty()) {
                holder.statusSubscription.setText(operator.getBookingStatus_());
            } else {
                holder.statusSubscription.setText("Forward To Engineer");
            }
        } else if (operator.getBookingStatus() == 1) {
            holder.statusSubscription.setTextColor(mContext.getResources().getColor(R.color.color_amber));
            if (operator.getBookingStatus_() != null && !operator.getBookingStatus_().isEmpty()) {
                holder.statusSubscription.setText(operator.getBookingStatus_());
            } else {
                holder.statusSubscription.setText("Requested");
            }
        } else if (operator.getBookingStatus() == 3) {
            holder.statusSubscription.setTextColor(mContext.getResources().getColor(R.color.color_amber));
            if (operator.getBookingStatus_() != null && !operator.getBookingStatus_().isEmpty()) {
                holder.statusSubscription.setText(operator.getBookingStatus_());
            } else {
                holder.statusSubscription.setText("Installing");
            }
        } else {
            holder.statusSubscription.setTextColor(mContext.getResources().getColor(R.color.color_deep_orange));
            if (operator.getBookingStatus_() != null && !operator.getBookingStatus_().isEmpty()) {
                holder.statusSubscription.setText(operator.getBookingStatus_());
            } else {
                holder.statusSubscription.setText("Processing");
            }
        }

        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getOid() + ".png")
                .apply(requestOptions)
                .into(holder.operatorImage);


        holder.share.setOnClickListener(v -> {

            Intent i = new Intent(mContext, RechargeSlipActivity.class);

            i.putExtra("amount", "" + operator.getRequestedAmount());
            i.putExtra("RechargeMobileNo", "" + operator.getAccount());
            i.putExtra("liveId", "" + operator.getLiveID());
            i.putExtra("OID", operator.getOid());
            i.putExtra("operatorname", "" + operator.getOperator());
            i.putExtra("pdate", "" + operator.getEntryDate());
            i.putExtra("ptime", "" + operator.getModifyDate());
            i.putExtra("PackageName", "" + operator.getPackageName());
            i.putExtra("CustomerAddress", "" + operator.getAddress() + " - " + operator.getPincode());
            i.putExtra("CustomerName", "" + operator.getCustomerName());
            i.putExtra("txid", "" + operator.getTransactionID());
            i.putExtra("StatusCode", operator.getStatus());
            i.putExtra("txStatus", "" + operator.getStatus_());
            i.putExtra("typerecharge", "" + operator.getStatus_());
            i.putExtra("imageurl", "" + ApplicationConstant.INSTANCE.baseIconUrl + operator.getOid() + ".png");

            mContext.startActivity(i);

        });
        holder.print.setOnClickListener(v -> {

            sendReport(operator.getTransactionID());

        });
        /*holder.dispute.setOnClickListener(v -> {

            mCustomAlertDialog.WarningWithDoubleBtnCallBack(mContext.getResources().getString(R.string.dispute_msg), "Complaint", true, new CustomAlertDialog.DialogCallBack() {
                @Override
                public void onPositiveClick() {
                    if (ApiUtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                        ApiUtilMethods.INSTANCE.Dispute(mContext, operator.getTransactionID(), operator.getTid() + "",
                                mCustomLoader, mLoginDataResponse, deviceid,deviceSerialNum,object -> {
                                    if (mContext instanceof DthSubscriptionActivity) {
                                        ((DthSubscriptionActivity) mContext).HitApi();
                                    }
                                });

                    } else {
                        ApiUtilMethods.INSTANCE.NetworkError(mContext);
                    }
                }

                @Override
                public void onNegativeClick() {

                }
            });

        });

        if (operator.getIsWTR()) {
            if (operator.getStatus() == 1
                    || operator.getStatus() == 2
                    || operator.getStatus() == 5) {
                holder.w2r.setVisibility(View.VISIBLE);
            } else {
                holder.w2r.setVisibility(View.GONE);
            }
        } else {
            holder.w2r.setVisibility(View.GONE);
        }

        holder.w2r.setOnClickListener(v -> {

            w2rDialog(operator.getTid() + "", operator.getTransactionID(), holder.w2r, position);

        });*/
    }


    void w2rDialog(String tid, String transactionID, View w2r, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = inflater.inflate(R.layout.dialog_w2r, null);

        final EditText RightAccountEt = vi.findViewById(R.id.RightAccountEt);
        final TextView submitButton = vi.findViewById(R.id.submitButton);
        final TextView cancelButton = vi.findViewById(R.id.cancelButton);
        final Dialog dialog = new Dialog(mContext);
        dialog.setCancelable(false);
        dialog.setContentView(vi);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vi.findViewById(R.id.closeIv).setOnClickListener(v1 -> dialog.dismiss());
        cancelButton.setOnClickListener(v12 -> dialog.dismiss());

        submitButton.setOnClickListener(v13 -> {
            if (!RightAccountEt.getText().toString().isEmpty()) {
                mCustomLoader.show();
                ApiFintechUtilMethods.INSTANCE.MakeW2RRequest(mContext, tid, transactionID,
                        RightAccountEt.getText().toString(), mCustomLoader, mLoginDataResponse, deviceid, deviceSerialNum, object -> {
                            w2r.setVisibility(View.GONE);
                            transactionsList.get(position).setWTR(false);
                            notifyDataSetChanged();
                        });
                dialog.dismiss();
            } else {
                RightAccountEt.setError("Enter Password");
                RightAccountEt.requestFocus();
            }
        });
        dialog.show();
    }


    private boolean validateText() {
        if (etRemark.getText().toString().trim().isEmpty()) {
            tilRemark.setError(mContext.getString(R.string.err_msg_text));
            okButton.setEnabled(false);
            return false;
        } else {
            okButton.setEnabled(true);
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveandprint(String string) {
        MyPrintDocumentAdapter pd = new MyPrintDocumentAdapter(mContext, string);
        pd.printDocument(string);
    }

    // Filter Class
    public void filter(String charText) {
        this.charText = charText.toLowerCase(Locale.getDefault());

        transactionsList.clear();
        if (charText.length() == 0) {
            transactionsList.addAll(rechargeStatus);
        } else {
            for (DthSubscriptionReport wp : rechargeStatus) {
                if (wp.getStatus_().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getAccount().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getOperator().toLowerCase(Locale.getDefault()).contains(charText)) {
                    transactionsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    void sendReport(String reportId) {
        if (mCustomAlertDialog != null) {
            mCustomAlertDialog.sendReportDialog(1, "", (mobile, emailId) -> {

            });
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView openingBalanceLabel;
        private AppCompatTextView lastbalance;
        private AppCompatTextView debitTitle;
        private AppCompatTextView debit;
        private AppCompatTextView balanceTitle;
        private AppCompatTextView balance;
        private AppCompatImageView operatorImage;
        private AppCompatTextView operatorName;
        private AppCompatTextView packageName;
        private AppCompatTextView amount;
        private AppCompatTextView CommissionLabel;
        private AppCompatTextView comm;
        private LinearLayout transactionDetailView;
        private LinearLayout transactionIdView;
        private AppCompatTextView transactionIdLabel;
        private AppCompatTextView txn;
        private LinearLayout liveIdView;
        private AppCompatTextView liveIdLabel;
        private AppCompatTextView liveID;
        private LinearLayout customerNameView;
        private AppCompatTextView customerNameLabel;
        private AppCompatTextView customerName;
        private LinearLayout mobileView;
        private AppCompatTextView mobileLabel;
        private AppCompatTextView mobile;
        private LinearLayout addressView;
        private AppCompatTextView addressLabel;
        private AppCompatTextView address;
        private LinearLayout outletDetailView;
        private LinearLayout outletNameView;
        private AppCompatTextView OutletLabel;
        private AppCompatTextView outlet;
        private LinearLayout outletNoView;
        private AppCompatTextView outletNoLabel;
        private AppCompatTextView outletNo;
        private AppCompatTextView source;
        private LinearLayout userNameView;
        private AppCompatTextView userNameLabel;
        private AppCompatTextView userName;
        private AppCompatTextView date;
        private AppCompatImageView statusIcon;
        private AppCompatTextView status;
        private LinearLayout disputeStatusView, subscriptionStatusView;
        private AppCompatTextView statusDisputeLabel;
        private AppCompatTextView statusDispute, statusSubscription;
        private LinearLayout btnView;
        private LinearLayout dispute;
        private LinearLayout w2r;
        private LinearLayout print;
        private LinearLayout share;
        private AppCompatTextView fundTransferTv;
        private LinearLayout techNameView;
        private AppCompatTextView techNameLabel;
        private AppCompatTextView techName;
        private LinearLayout techMobileView;
        private AppCompatTextView techMobileLabel;
        private AppCompatTextView techMobile;
        private LinearLayout customerIdView;
        private AppCompatTextView customerIdLabel;
        private AppCompatTextView customerId;
        private LinearLayout stbIdView;
        private AppCompatTextView stbIdLabel;
        private AppCompatTextView stbId;
        private LinearLayout vcView;
        private AppCompatTextView vcLabel;
        private AppCompatTextView vc;
        private LinearLayout installChargeView;
        private AppCompatTextView installChargeLabel;
        private AppCompatTextView installCharge;
        private LinearLayout installTimeView, approveTimeView;
        private AppCompatTextView installTimeLabel;
        private AppCompatTextView installTime, approveTime;
        private LinearLayout remarkView;
        private AppCompatTextView remarkLabel;
        private AppCompatTextView remark;


        public MyViewHolder(View view) {
            super(view);
            openingBalanceLabel = view.findViewById(R.id.openingBalanceLabel);
            lastbalance = view.findViewById(R.id.lastbalance);
            debitTitle = view.findViewById(R.id.debitTitle);
            debit = view.findViewById(R.id.debit);
            balanceTitle = view.findViewById(R.id.balanceTitle);
            balance = view.findViewById(R.id.balance);
            operatorImage = view.findViewById(R.id.operatorImage);
            operatorName = view.findViewById(R.id.operatorName);
            packageName = view.findViewById(R.id.packageName);
            amount = view.findViewById(R.id.amount);
            CommissionLabel = view.findViewById(R.id.CommissionLabel);
            comm = view.findViewById(R.id.comm);
            transactionDetailView = view.findViewById(R.id.transactionDetailView);
            transactionIdView = view.findViewById(R.id.transactionIdView);
            transactionIdLabel = view.findViewById(R.id.transactionIdLabel);
            txn = view.findViewById(R.id.txn);
            liveIdView = view.findViewById(R.id.liveIdView);
            liveIdLabel = view.findViewById(R.id.liveIdLabel);
            liveID = view.findViewById(R.id.liveID);
            customerNameView = view.findViewById(R.id.customerNameView);
            customerNameLabel = view.findViewById(R.id.customerNameLabel);
            customerName = view.findViewById(R.id.customerName);
            mobileView = view.findViewById(R.id.mobileView);
            mobileLabel = view.findViewById(R.id.mobileLabel);
            mobile = view.findViewById(R.id.mobile);
            addressView = view.findViewById(R.id.addressView);
            addressLabel = view.findViewById(R.id.addressLabel);
            address = view.findViewById(R.id.address);
            outletDetailView = view.findViewById(R.id.outletDetailView);
            outletNameView = view.findViewById(R.id.outletNameView);
            OutletLabel = view.findViewById(R.id.OutletLabel);
            outlet = view.findViewById(R.id.outlet);
            outletNoView = view.findViewById(R.id.outletNoView);
            outletNoLabel = view.findViewById(R.id.outletNoLabel);
            outletNo = view.findViewById(R.id.outletNo);
            source = view.findViewById(R.id.source);
            userNameView = view.findViewById(R.id.userNameView);
            userNameLabel = view.findViewById(R.id.userNameLabel);
            userName = view.findViewById(R.id.userName);
            date = view.findViewById(R.id.date);
            statusIcon = view.findViewById(R.id.statusIcon);
            status = view.findViewById(R.id.status);
            disputeStatusView = view.findViewById(R.id.disputeStatusView);
            subscriptionStatusView = view.findViewById(R.id.subscriptionStatusView);
            statusDisputeLabel = view.findViewById(R.id.statusDisputeLabel);
            statusDispute = view.findViewById(R.id.statusDispute);
            statusSubscription = view.findViewById(R.id.statusSubscription);
            btnView = view.findViewById(R.id.btnView);
            dispute = view.findViewById(R.id.dispute);
            w2r = view.findViewById(R.id.w2r);
            print = view.findViewById(R.id.print);
            share = view.findViewById(R.id.share);
            fundTransferTv = view.findViewById(R.id.fundTransferTv);
            techNameView = (LinearLayout) view.findViewById(R.id.techNameView);
            techNameLabel = (AppCompatTextView) view.findViewById(R.id.techNameLabel);
            techName = (AppCompatTextView) view.findViewById(R.id.techName);
            techMobileView = (LinearLayout) view.findViewById(R.id.techMobileView);
            techMobileLabel = (AppCompatTextView) view.findViewById(R.id.techMobileLabel);
            techMobile = (AppCompatTextView) view.findViewById(R.id.techMobile);
            customerIdView = (LinearLayout) view.findViewById(R.id.customerIdView);
            customerIdLabel = (AppCompatTextView) view.findViewById(R.id.customerIdLabel);
            customerId = (AppCompatTextView) view.findViewById(R.id.customerId);
            stbIdView = (LinearLayout) view.findViewById(R.id.stbIdView);
            stbIdLabel = (AppCompatTextView) view.findViewById(R.id.stbIdLabel);
            stbId = (AppCompatTextView) view.findViewById(R.id.stbId);
            vcView = (LinearLayout) view.findViewById(R.id.vcView);
            vcLabel = (AppCompatTextView) view.findViewById(R.id.vcLabel);
            vc = (AppCompatTextView) view.findViewById(R.id.vc);
            installChargeView = (LinearLayout) view.findViewById(R.id.installChargeView);
            installChargeLabel = (AppCompatTextView) view.findViewById(R.id.installChargeLabel);
            installCharge = (AppCompatTextView) view.findViewById(R.id.installCharge);
            installTimeView = (LinearLayout) view.findViewById(R.id.installTimeView);
            approveTimeView = (LinearLayout) view.findViewById(R.id.approveTimeView);
            installTimeLabel = (AppCompatTextView) view.findViewById(R.id.installTimeLabel);
            installTime = (AppCompatTextView) view.findViewById(R.id.installTime);
            approveTime = (AppCompatTextView) view.findViewById(R.id.approveTime);
            remarkView = (LinearLayout) view.findViewById(R.id.remarkView);
            remarkLabel = (AppCompatTextView) view.findViewById(R.id.remarkLabel);
            remark = (AppCompatTextView) view.findViewById(R.id.remark);
        }
    }
}