package com.solution.app.justpay4u.Fintech.Reports.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.solution.app.justpay4u.Api.Fintech.Object.DmtReportObject;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Reports.Activity.DMRReportActivity;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vishnu on 18-04-2017.
 */

public class DMRReportAdapter extends RecyclerView.Adapter<DMRReportAdapter.MyViewHolder> {

    public TextInputLayout tilRemark;
    public EditText etRemark;
    public Button okButton;
    public Button cancelButton;
    CustomLoader loader;
    CustomAlertDialog mCustomAlertDialog;
    LoginResponse mLoginDataResponse;
    String deviceId, deviceSerialNum;
    AppPreferences mAppPreferences;
    private ArrayList<DmtReportObject> transactionsList;
    private ArrayList<DmtReportObject> rechargeStatus;
    private Activity mContext;
    private String status;

    public DMRReportAdapter(ArrayList<DmtReportObject> transactionsList, Activity mContext,
                            LoginResponse mLoginDataResponse, String deviceId, String deviceSerialNum, AppPreferences mAppPreferences) {
        this.transactionsList = transactionsList;
        this.rechargeStatus = transactionsList;
        this.mContext = mContext;
        this.mLoginDataResponse = mLoginDataResponse;
        this.deviceId = deviceId;
        this.deviceSerialNum = deviceSerialNum;
        mCustomAlertDialog = new CustomAlertDialog(mContext);
        this.mAppPreferences = mAppPreferences;
    }

    @Override
    public DMRReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dmt_report/*adapter_dmr_report*/, parent, false);


        return new DMRReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DMRReportAdapter.MyViewHolder holder, int position) {
        final DmtReportObject operator = transactionsList.get(position);

        // holder.status.setText("" + operator.getType_());
        holder.openingBalance.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getOpening() + ""));
        holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getRequestedAmount() + ""));
        holder.debit.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getAmount() + ""));
        holder.balance.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getBalance() + ""));

        if (operator.getOptional3() != null && !operator.getOptional3().isEmpty()) {
            holder.bankName.setText("" + operator.getOptional1() + " (" + operator.getOptional3() + ")");
        } else {
            holder.bankName.setText("" + operator.getOptional1());
        }

        if (operator.getOptional4() != null && !operator.getOptional4().isEmpty()) {
            holder.transactionModeView.setVisibility(View.VISIBLE);
            holder.transactionMode.setText("" + operator.getOptional4());
        } else {
            holder.transactionModeView.setVisibility(View.GONE);
        }

        holder.outletName.setText("" + operator.getOutlet());
        holder.outletNo.setText("" + operator.getOutletNo());
        holder.accountNumber.setText("" + operator.getAccount());
        holder.operatorName.setText("" + operator.getOperator());
        holder.txnId.setText("" + operator.getTransactionID());

        if (operator.getLiveID() != null && !operator.getLiveID().isEmpty()) {
            holder.liveIdView.setVisibility(View.VISIBLE);
            holder.liveID.setText("" + operator.getLiveID());
        } else {
            holder.liveIdView.setVisibility(View.GONE);
        }
        holder.createdDate.setText("" + operator.getEntryDate());
        holder.acceptRejectDate.setText("" + operator.getModifyDate());
        holder.senderNo.setText("" + operator.getOptional2());
        holder.source.setText("" + operator.getRequestMode());

        if (operator.isWTR()) {
            holder.w2r.setVisibility(View.VISIBLE);
        } else {
            holder.w2r.setVisibility(View.GONE);
        }

        holder.surcharge.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getSurcharge() + ""));
        holder.commission.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getCommission() + ""));
        holder.credited.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getCreditedAmount() + ""));
        holder.ccf.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getCcf() + ""));
        holder.amtWithTds.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getAmtWithTDS() + ""));
        holder.refundGst.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getRefundGST() + ""));
        holder.gstAmt.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getGstAmount() + ""));
        holder.tdsAmtAmt.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTdsAmount() + ""));


        holder.status.setText(operator.getType_() + "");

        if (operator.get_Type() == 1) {
            holder.share.setVisibility(View.GONE);
            //  holder.print.setVisibility(View.GONE);
            holder.acceptRejectDateLabel.setText("Accepted Date");
            holder.statusIcon.setImageResource(R.drawable.ic_pending);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_amber));
        } else if (operator.get_Type() == 2) {
            holder.share.setVisibility(View.VISIBLE);
            //  holder.print.setVisibility(View.VISIBLE);
            holder.acceptRejectDateLabel.setText("Success Date");
            holder.statusIcon.setImageResource(R.drawable.ic_success);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (operator.get_Type() == 3) {
            holder.share.setVisibility(View.GONE);
            //  holder.print.setVisibility(View.GONE);
            holder.acceptRejectDateLabel.setText("Failed Date");
            holder.statusIcon.setImageResource(R.drawable.ic_failed);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_red));
        } else if (operator.get_Type() == 4) {
            holder.share.setVisibility(View.GONE);
            //  holder.print.setVisibility(View.GONE);
            holder.acceptRejectDateLabel.setText("Refunded Date");
            holder.statusIcon.setImageResource(R.drawable.ic_refund);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_cyan));
        } else {
            holder.acceptRejectDateLabel.setText("Modify Date");
            holder.share.setVisibility(View.GONE);
            //   holder.print.setVisibility(View.GONE);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.bottommenu));
        }


        holder.statusDispute.setText(operator.getRefundStatus_() + "");

        if (operator.get_Type() == 2 || operator.get_Type() == 4) {
            if (operator.getRefundStatus() == 1) {
                holder.dispute.setVisibility(View.VISIBLE);
                holder.disputeStatusView.setVisibility(View.GONE);

            } else if (operator.getRefundStatus() == 3) {
                holder.statusDispute.setTextColor(mContext.getResources().getColor(R.color.green));
                holder.dispute.setVisibility(View.GONE);
                holder.disputeStatusView.setVisibility(View.VISIBLE);

            } else if (operator.getRefundStatus() == 4) {
                holder.statusDispute.setTextColor(mContext.getResources().getColor(R.color.color_red));
                holder.dispute.setVisibility(View.GONE);
                holder.disputeStatusView.setVisibility(View.VISIBLE);
            } else if (operator.getRefundStatus() == 2) {
                holder.dispute.setVisibility(View.GONE);
                holder.disputeStatusView.setVisibility(View.VISIBLE);
                holder.statusDispute.setTextColor(mContext.getResources().getColor(R.color.color_deep_orange));

            } else {
                holder.dispute.setVisibility(View.GONE);
                holder.disputeStatusView.setVisibility(View.GONE);
            }
        } else {
            holder.dispute.setVisibility(View.GONE);
            holder.disputeStatusView.setVisibility(View.GONE);
        }


        holder.dispute.setOnClickListener(v ->
                mCustomAlertDialog.WarningWithDoubleBtnCallBack("Are you sure you want to dispute?", "Dispute", true, new CustomAlertDialog.DialogCallBack() {
                    @Override
                    public void onPositiveClick() {
                        ((DMRReportActivity) mContext).Dispute(operator.getTid() + "", operator.getTransactionID(), "", false, null, null, null, v);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                }));
        holder.share.setOnClickListener(v -> {
            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                CustomLoader loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
                loader.show();
                ApiFintechUtilMethods.INSTANCE.GetDMTReceipt(mContext, operator.getGroupID(), "Specific", mLoginDataResponse,
                        deviceId, deviceSerialNum, loader, mAppPreferences);
            } else {
                ApiFintechUtilMethods.INSTANCE.NetworkError(mContext);
            }


        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        ArrayList<DmtReportObject> filterList = new ArrayList<>();
        if (charText.length() == 0) {
            filterList.addAll(rechargeStatus);
        } else {
            for (DmtReportObject wp : rechargeStatus) {
                if ((wp.getType_() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getAccount() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getOutlet() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getOutletNo() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getOptional1() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getOptional2() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getOptional3() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getOptional4() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getOutletUserMobile() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getSenderMobile() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getCustomerNo() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getTid() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getTransactionID() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getLiveID() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getOperator() + "").toLowerCase(Locale.getDefault()).contains(charText)) {
                    filterList.add(wp);
                }
            }
        }

        transactionsList = filterList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView statusIcon;
        private AppCompatTextView openingBalanceLabel;
        private AppCompatTextView openingBalance;
        private AppCompatTextView debitTitle;
        private AppCompatTextView debit;
        private AppCompatTextView balanceTitle;
        private AppCompatTextView balance;
        private AppCompatImageView operatorImage;
        private AppCompatTextView bankName;
        private AppCompatTextView accountNumber;
        private AppCompatTextView operatorName;
        private AppCompatTextView amount;
        private LinearLayout transactionDetailView;
        private LinearLayout transactionIdView;
        private AppCompatTextView transactionIdLabel;
        private AppCompatTextView txnId;
        private LinearLayout transactionModeView;
        private AppCompatTextView transactionModeLabel;
        private AppCompatTextView transactionMode;
        private LinearLayout liveIdView;
        private AppCompatTextView liveIdLabel;
        private AppCompatTextView liveID;
        private LinearLayout outletDetailView;
        private LinearLayout outletNameView;
        private AppCompatTextView OutletLabel;
        private AppCompatTextView outletName;
        private LinearLayout outletNoView;
        private AppCompatTextView outletNoLabel;
        private AppCompatTextView outletNo;
        private LinearLayout senderNoView;
        private AppCompatTextView senderNoLabel;
        private AppCompatTextView senderNo;
        private LinearLayout dateDetailView;
        private LinearLayout requestDateView;
        private AppCompatTextView requestDateLabel;
        private AppCompatTextView createdDate;
        private LinearLayout acceptRejectDateView;
        private AppCompatTextView acceptRejectDateLabel;
        private AppCompatTextView acceptRejectDate;
        private LinearLayout slabCommView;
        private LinearLayout surchargeView;
        private AppCompatTextView surchargeLabel;
        private AppCompatTextView surcharge;
        private LinearLayout amtWithTdsView;
        private AppCompatTextView amtWithTdsLabel;
        private AppCompatTextView amtWithTds;
        private LinearLayout commissionView;
        private AppCompatTextView commissionLabel;
        private AppCompatTextView commission;
        private LinearLayout refundGstView;
        private AppCompatTextView refundGstLabel;
        private AppCompatTextView refundGst;
        private LinearLayout creditedView;
        private AppCompatTextView creditedLabel;
        private AppCompatTextView credited;
        private LinearLayout gstAmtView;
        private AppCompatTextView gstAmtLabel;
        private AppCompatTextView gstAmt;
        private LinearLayout ccfView;
        private AppCompatTextView ccfLabel;
        private AppCompatTextView ccf;
        private LinearLayout tdsAmtView;
        private AppCompatTextView tdsAmtLabel;
        private AppCompatTextView tdsAmtAmt;
        private AppCompatTextView source;
        private LinearLayout dispute;
        private LinearLayout w2r;
        private LinearLayout print;
        private LinearLayout share;
        private LinearLayout statusView;
        private AppCompatTextView status, statusDispute;
        private View disputeStatusView;

        public MyViewHolder(View view) {
            super(view);
            openingBalanceLabel = (AppCompatTextView) view.findViewById(R.id.openingBalanceLabel);
            openingBalance = (AppCompatTextView) view.findViewById(R.id.openingBalance);
            debitTitle = (AppCompatTextView) view.findViewById(R.id.debitTitle);
            debit = (AppCompatTextView) view.findViewById(R.id.debit);
            balanceTitle = (AppCompatTextView) view.findViewById(R.id.balanceTitle);
            balance = (AppCompatTextView) view.findViewById(R.id.balance);
            operatorImage = (AppCompatImageView) view.findViewById(R.id.operatorImage);
            bankName = (AppCompatTextView) view.findViewById(R.id.bankName);
            accountNumber = (AppCompatTextView) view.findViewById(R.id.accountNumber);
            operatorName = (AppCompatTextView) view.findViewById(R.id.operatorName);
            amount = (AppCompatTextView) view.findViewById(R.id.amount);
            transactionDetailView = (LinearLayout) view.findViewById(R.id.transactionDetailView);
            transactionIdView = (LinearLayout) view.findViewById(R.id.transactionIdView);
            transactionIdLabel = (AppCompatTextView) view.findViewById(R.id.transactionIdLabel);
            txnId = (AppCompatTextView) view.findViewById(R.id.txnId);
            transactionModeView = (LinearLayout) view.findViewById(R.id.transactionModeView);
            transactionModeLabel = (AppCompatTextView) view.findViewById(R.id.transactionModeLabel);
            transactionMode = (AppCompatTextView) view.findViewById(R.id.transactionMode);
            liveIdView = (LinearLayout) view.findViewById(R.id.liveIdView);
            liveIdLabel = (AppCompatTextView) view.findViewById(R.id.liveIdLabel);
            liveID = (AppCompatTextView) view.findViewById(R.id.liveID);
            outletDetailView = (LinearLayout) view.findViewById(R.id.outletDetailView);
            outletNameView = (LinearLayout) view.findViewById(R.id.outletNameView);
            OutletLabel = (AppCompatTextView) view.findViewById(R.id.OutletLabel);
            outletName = (AppCompatTextView) view.findViewById(R.id.outletName);
            outletNoView = (LinearLayout) view.findViewById(R.id.outletNoView);
            outletNoLabel = (AppCompatTextView) view.findViewById(R.id.outletNoLabel);
            outletNo = (AppCompatTextView) view.findViewById(R.id.outletNo);
            senderNoView = (LinearLayout) view.findViewById(R.id.senderNoView);
            senderNoLabel = (AppCompatTextView) view.findViewById(R.id.senderNoLabel);
            senderNo = (AppCompatTextView) view.findViewById(R.id.senderNo);
            dateDetailView = (LinearLayout) view.findViewById(R.id.dateDetailView);
            requestDateView = (LinearLayout) view.findViewById(R.id.requestDateView);
            requestDateLabel = (AppCompatTextView) view.findViewById(R.id.requestDateLabel);
            createdDate = (AppCompatTextView) view.findViewById(R.id.createdDate);
            acceptRejectDateView = (LinearLayout) view.findViewById(R.id.acceptRejectDateView);
            acceptRejectDateLabel = (AppCompatTextView) view.findViewById(R.id.acceptRejectDateLabel);
            acceptRejectDate = (AppCompatTextView) view.findViewById(R.id.acceptRejectDate);
            slabCommView = (LinearLayout) view.findViewById(R.id.slabCommView);
            slabCommView.setVisibility(View.GONE);
            surchargeView = (LinearLayout) view.findViewById(R.id.surchargeView);
            surchargeLabel = (AppCompatTextView) view.findViewById(R.id.surchargeLabel);
            surcharge = (AppCompatTextView) view.findViewById(R.id.surcharge);
            amtWithTdsView = (LinearLayout) view.findViewById(R.id.amtWithTdsView);
            amtWithTdsLabel = (AppCompatTextView) view.findViewById(R.id.amtWithTdsLabel);
            amtWithTds = (AppCompatTextView) view.findViewById(R.id.amtWithTds);
            commissionView = (LinearLayout) view.findViewById(R.id.commissionView);
            commissionLabel = (AppCompatTextView) view.findViewById(R.id.commissionLabel);
            commission = (AppCompatTextView) view.findViewById(R.id.commission);
            refundGstView = (LinearLayout) view.findViewById(R.id.refundGstView);
            refundGstLabel = (AppCompatTextView) view.findViewById(R.id.refundGstLabel);
            refundGst = (AppCompatTextView) view.findViewById(R.id.refundGst);
            creditedView = (LinearLayout) view.findViewById(R.id.creditedView);
            creditedLabel = (AppCompatTextView) view.findViewById(R.id.creditedLabel);
            credited = (AppCompatTextView) view.findViewById(R.id.credited);
            gstAmtView = (LinearLayout) view.findViewById(R.id.gstAmtView);
            gstAmtLabel = (AppCompatTextView) view.findViewById(R.id.gstAmtLabel);
            gstAmt = (AppCompatTextView) view.findViewById(R.id.gstAmt);
            ccfView = (LinearLayout) view.findViewById(R.id.ccfView);
            ccfLabel = (AppCompatTextView) view.findViewById(R.id.ccfLabel);
            ccf = (AppCompatTextView) view.findViewById(R.id.ccf);
            tdsAmtView = (LinearLayout) view.findViewById(R.id.tdsAmtView);
            tdsAmtLabel = (AppCompatTextView) view.findViewById(R.id.tdsAmtLabel);
            tdsAmtAmt = (AppCompatTextView) view.findViewById(R.id.tdsAmtAmt);
            source = (AppCompatTextView) view.findViewById(R.id.source);
            dispute = view.findViewById(R.id.dispute);
            print = view.findViewById(R.id.print);
            share = view.findViewById(R.id.share);
            statusView = (LinearLayout) view.findViewById(R.id.statusView);
            status = (AppCompatTextView) view.findViewById(R.id.status);
            statusIcon = view.findViewById(R.id.statusIcon);
            disputeStatusView = view.findViewById(R.id.disputeStatusView);
            statusDispute = view.findViewById(R.id.statusDispute);
            w2r = view.findViewById(R.id.w2r);
        }
    }
}
