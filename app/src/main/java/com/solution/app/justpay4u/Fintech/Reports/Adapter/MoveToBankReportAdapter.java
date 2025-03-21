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
import com.solution.app.justpay4u.Api.Fintech.Object.MoveToWalletReportData;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vishnu on 18-04-2017.
 */

public class MoveToBankReportAdapter extends RecyclerView.Adapter<MoveToBankReportAdapter.MyViewHolder> {

    public TextInputLayout tilRemark;
    public EditText etRemark;
    public Button okButton;
    public Button cancelButton;
    CustomLoader loader;
    CustomAlertDialog mCustomAlertDialog;
    LoginResponse mLoginDataResponse;
    AppPreferences mAppPreferences;
    private ArrayList<MoveToWalletReportData> transactionsList;
    private ArrayList<MoveToWalletReportData> rechargeStatus;
    private Activity mContext;
    private String status;

    public MoveToBankReportAdapter(ArrayList<MoveToWalletReportData> transactionsList, Activity mContext, LoginResponse mLoginDataResponse, AppPreferences mAppPreferences) {
        this.transactionsList = transactionsList;
        this.rechargeStatus = transactionsList;
        this.mContext = mContext;
        this.mLoginDataResponse = mLoginDataResponse;
        mCustomAlertDialog = new CustomAlertDialog(mContext);
        this.mAppPreferences = mAppPreferences;
    }

    @Override
    public MoveToBankReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_movetobank_report/*adapter_dmr_report*/, parent, false);


        return new MoveToBankReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MoveToBankReportAdapter.MyViewHolder holder, int position) {
        final MoveToWalletReportData operator = transactionsList.get(position);

        // holder.status.setText("" + operator.getType_());
        // holder.openingBalance.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getOpening() + ""));
        // holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getRequestedAmount() + ""));
        holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getAmount() + ""));
        holder.charges.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getCharge() + ""));
        // holder.balance.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getBalance() + ""));


        holder.bankName.setText(operator.getBankName() + " (" + operator.getIfsc() + ")");
        holder.transactionMode.setText(operator.getTransMode() + "");


        holder.outletName.setText("" + operator.getUserName());
        holder.outletNo.setText("" + operator.getMobile());
        holder.accountNumber.setText("" + operator.getAccountNumber());
        holder.operatorName.setText("" + operator.getAccountHolder());
        holder.txnId.setText("" + operator.getTransactionId());


        holder.liveID.setText(operator.getBankRRN() + "");

        holder.createdDate.setText("" + operator.getEntryDate());
        holder.acceptRejectDate.setText("" + operator.getApprovalDate());
        holder.source.setText(operator.getRemark() + "");
        /*holder.senderNo.setText("" + operator.getOptional2());
         */

        /*if (operator.isWTR()) {
            holder.w2r.setVisibility(View.VISIBLE);
        } else {
            holder.w2r.setVisibility(View.GONE);
        }
*/
       /* holder.surcharge.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getSurcharge() + ""));
        holder.commission.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getCommission() + ""));
        holder.credited.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getCreditedAmount() + ""));
        holder.ccf.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getCcf() + ""));
        holder.amtWithTds.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getAmtWithTDS() + ""));
        holder.refundGst.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getRefundGST() + ""));
        holder.gstAmt.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getGstAmount() + ""));
        holder.tdsAmtAmt.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTdsAmount() + ""));
*/


        if (operator.getStatus() == 1) {
            holder.share.setVisibility(View.GONE);
            //  holder.print.setVisibility(View.GONE);
            holder.status.setText("Requested");
            holder.acceptRejectDateLabel.setText("Requested Date");
            holder.statusIcon.setImageResource(R.drawable.ic_pending);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_amber));
        } else if (operator.getStatus() == 2) {
            holder.share.setVisibility(View.VISIBLE);
            //  holder.print.setVisibility(View.VISIBLE);
            holder.status.setText("Approved");
            holder.acceptRejectDateLabel.setText("Approved Date");
            holder.statusIcon.setImageResource(R.drawable.ic_success);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (operator.getStatus() == 3) {
            holder.share.setVisibility(View.GONE);
            //  holder.print.setVisibility(View.GONE);
            holder.status.setText("Rejected");
            holder.acceptRejectDateLabel.setText("Rejected Date");
            holder.statusIcon.setImageResource(R.drawable.ic_failed);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_red));
        } /*else if (operator.get_Type() == 4) {
            holder.share.setVisibility(View.GONE);
            //  holder.print.setVisibility(View.GONE);
            holder.acceptRejectDateLabel.setText("Refunded Date");
            holder.statusIcon.setImageResource(R.drawable.ic_refund);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_cyan));
        } */ else {
            holder.status.setText("Not Applied");
            holder.acceptRejectDateLabel.setText("Not Applied Date");
            holder.share.setVisibility(View.GONE);
            //   holder.print.setVisibility(View.GONE);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.bottommenu));
        }


        /*holder.statusDispute.setText(operator.getRefundStatus_() + "");

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
        }*/


       /* holder.dispute.setOnClickListener(v ->
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
            if (ApiUtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                CustomLoader loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
                loader.show();
                ApiUtilMethods.INSTANCE.GetDMTReceipt(mContext, operator.getGroupID(), "Specific", mLoginDataResponse, loader, mAppPreferences);
            } else {
                ApiUtilMethods.INSTANCE.NetworkError(mContext);
            }


        });*/
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        ArrayList<MoveToWalletReportData> filterList = new ArrayList<>();
        if (charText.length() == 0) {
            filterList.addAll(rechargeStatus);
        } else {
            for (MoveToWalletReportData wp : rechargeStatus) {
                if ((wp.getBankRRN() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getAccountNumber() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getTransactionId() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getMobile() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getUserName() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getTransMode() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getBankName() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getAmount() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getCharge() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getIfsc() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getTid() + "").toLowerCase(Locale.getDefault()).contains(charText)) {
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
        private AppCompatTextView createdDate, charges;
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
            openingBalanceLabel = view.findViewById(R.id.openingBalanceLabel);
            openingBalance = view.findViewById(R.id.openingBalance);
            debitTitle = view.findViewById(R.id.debitTitle);
            debit = view.findViewById(R.id.debit);
            balanceTitle = view.findViewById(R.id.balanceTitle);
            balance = view.findViewById(R.id.balance);
            operatorImage = view.findViewById(R.id.operatorImage);
            bankName = view.findViewById(R.id.bankName);
            accountNumber = view.findViewById(R.id.accountNumber);
            operatorName = view.findViewById(R.id.operatorName);
            amount = view.findViewById(R.id.amount);
            transactionDetailView = view.findViewById(R.id.transactionDetailView);
            transactionIdView = view.findViewById(R.id.transactionIdView);
            transactionIdLabel = view.findViewById(R.id.transactionIdLabel);
            txnId = view.findViewById(R.id.txnId);
            transactionModeView = view.findViewById(R.id.transactionModeView);
            transactionModeLabel = view.findViewById(R.id.transactionModeLabel);
            transactionMode = view.findViewById(R.id.transactionMode);
            liveIdView = view.findViewById(R.id.liveIdView);
            liveIdLabel = view.findViewById(R.id.liveIdLabel);
            liveID = view.findViewById(R.id.liveID);
            charges = view.findViewById(R.id.charges);
            outletDetailView = view.findViewById(R.id.outletDetailView);
            outletNameView = view.findViewById(R.id.outletNameView);
            OutletLabel = view.findViewById(R.id.OutletLabel);
            outletName = view.findViewById(R.id.outletName);
            outletNoView = view.findViewById(R.id.outletNoView);
            outletNoLabel = view.findViewById(R.id.outletNoLabel);
            outletNo = view.findViewById(R.id.outletNo);
            senderNoView = view.findViewById(R.id.senderNoView);
            senderNoLabel = view.findViewById(R.id.senderNoLabel);
            senderNo = view.findViewById(R.id.senderNo);
            dateDetailView = view.findViewById(R.id.dateDetailView);
            requestDateView = view.findViewById(R.id.requestDateView);
            requestDateLabel = view.findViewById(R.id.requestDateLabel);
            createdDate = view.findViewById(R.id.createdDate);
            acceptRejectDateView = view.findViewById(R.id.acceptRejectDateView);
            acceptRejectDateLabel = view.findViewById(R.id.acceptRejectDateLabel);
            acceptRejectDate = view.findViewById(R.id.acceptRejectDate);
            slabCommView = view.findViewById(R.id.slabCommView);
            slabCommView.setVisibility(View.GONE);
            surchargeView = view.findViewById(R.id.surchargeView);
            surchargeLabel = view.findViewById(R.id.surchargeLabel);
            surcharge = view.findViewById(R.id.surcharge);
            amtWithTdsView = view.findViewById(R.id.amtWithTdsView);
            amtWithTdsLabel = view.findViewById(R.id.amtWithTdsLabel);
            amtWithTds = view.findViewById(R.id.amtWithTds);
            commissionView = view.findViewById(R.id.commissionView);
            commissionLabel = view.findViewById(R.id.commissionLabel);
            commission = view.findViewById(R.id.commission);
            refundGstView = view.findViewById(R.id.refundGstView);
            refundGstLabel = view.findViewById(R.id.refundGstLabel);
            refundGst = view.findViewById(R.id.refundGst);
            creditedView = view.findViewById(R.id.creditedView);
            creditedLabel = view.findViewById(R.id.creditedLabel);
            credited = view.findViewById(R.id.credited);
            gstAmtView = view.findViewById(R.id.gstAmtView);
            gstAmtLabel = view.findViewById(R.id.gstAmtLabel);
            gstAmt = view.findViewById(R.id.gstAmt);
            ccfView = view.findViewById(R.id.ccfView);
            ccfLabel = view.findViewById(R.id.ccfLabel);
            ccf = view.findViewById(R.id.ccf);
            tdsAmtView = view.findViewById(R.id.tdsAmtView);
            tdsAmtLabel = view.findViewById(R.id.tdsAmtLabel);
            tdsAmtAmt = view.findViewById(R.id.tdsAmtAmt);
            source = view.findViewById(R.id.source);
            dispute = view.findViewById(R.id.dispute);
            print = view.findViewById(R.id.print);
            share = view.findViewById(R.id.share);
            statusView = view.findViewById(R.id.statusView);
            status = view.findViewById(R.id.status);
            statusIcon = view.findViewById(R.id.statusIcon);
            disputeStatusView = view.findViewById(R.id.disputeStatusView);
            statusDispute = view.findViewById(R.id.statusDispute);
            w2r = view.findViewById(R.id.w2r);
        }
    }
}
