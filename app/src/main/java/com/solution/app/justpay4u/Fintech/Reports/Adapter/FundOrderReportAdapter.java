package com.solution.app.justpay4u.Fintech.Reports.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.FundOrderReportObject;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vishnu on 15-04-2017.
 */

public class FundOrderReportAdapter extends RecyclerView.Adapter<FundOrderReportAdapter.MyViewHolder> {
    String charText = "";
    private ArrayList<FundOrderReportObject> rechargeStatus;
    private ArrayList<FundOrderReportObject> transactionsList;
    private Context mContext;

    public FundOrderReportAdapter(ArrayList<FundOrderReportObject> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.rechargeStatus = transactionsList;
    }

    @Override
    public FundOrderReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_fund_order_report, parent, false);

        return new FundOrderReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FundOrderReportAdapter.MyViewHolder holder, int position) {
        final FundOrderReportObject operator = transactionsList.get(position);

        holder.accountNumber.setText(operator.getAccountNo() + "");
        if (operator.getAccountNo() != null && !operator.getAccountNo().isEmpty()) {
            holder.accountNumber.setText(operator.getAccountNo() + "");
            holder.accountNumber.setVisibility(View.VISIBLE);
        } else {
            holder.accountNumber.setVisibility(View.GONE);
        }

        if (operator.getAccountHolder() != null && !operator.getAccountHolder().isEmpty()) {
            holder.acHolderName.setText(operator.getAccountHolder() + "");
            holder.acHolderName.setVisibility(View.VISIBLE);
        } else {
            holder.acHolderName.setVisibility(View.GONE);
        }

        if (operator.getBank() != null && !operator.getBank().isEmpty()) {
            holder.bankName.setText(operator.getBank() + "");
            holder.bankName.setVisibility(View.VISIBLE);
        } else {
            holder.bankName.setVisibility(View.GONE);
        }

        if (operator.getBranch() != null && !operator.getBranch().isEmpty()) {
            holder.branchName.setText(operator.getBranch() + "");
            holder.branchName.setVisibility(View.VISIBLE);
        } else {
            holder.branchName.setVisibility(View.GONE);
        }
        holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getAmount()));
        holder.txnId.setText(" " + operator.getTransactionId());

        if (operator.getMode() != null && !operator.getMode().isEmpty()) {
            holder.transactionMode.setText(operator.getMode() + "");
            holder.transactionModeView.setVisibility(View.VISIBLE);
        } else {
            holder.transactionModeView.setVisibility(View.GONE);
        }

        if (operator.getRemark() != null && !operator.getRemark().isEmpty()) {
            holder.remark.setText(operator.getRemark() + "");
            holder.remarkView.setVisibility(View.VISIBLE);
        } else {
            holder.remarkView.setVisibility(View.GONE);
        }


        if (operator.getMobileNo() != null && !operator.getMobileNo().isEmpty()) {
            holder.mobileNo.setText(operator.getMobileNo() + "");
            holder.mobileNoView.setVisibility(View.VISIBLE);
        } else {
            holder.mobileNoView.setVisibility(View.GONE);
        }


        if (operator.getChequeNo() != null && !operator.getChequeNo().isEmpty()) {
            holder.chequeNo.setText(operator.getChequeNo() + "");
            holder.chequeNoView.setVisibility(View.VISIBLE);
        } else {
            holder.chequeNoView.setVisibility(View.GONE);
        }

        if (operator.getCardNumber() != null && !operator.getCardNumber().isEmpty()) {
            holder.cardNo.setText(operator.getCardNumber() + "");
            holder.cardNoView.setVisibility(View.VISIBLE);
        } else {
            holder.cardNoView.setVisibility(View.GONE);
        }

        if (operator.getUpiid() != null && !operator.getUpiid().isEmpty()) {
            holder.upiId.setText(operator.getUpiid() + "");
            holder.upiIdView.setVisibility(View.VISIBLE);
        } else {
            holder.upiIdView.setVisibility(View.GONE);
        }


        if (operator.getDescription() != null && !operator.getDescription().isEmpty()) {
            holder.description.setText(operator.getDescription() + "");
            holder.descripionView.setVisibility(View.VISIBLE);
        } else {
            holder.descripionView.setVisibility(View.GONE);
        }
        holder.requestDateTv.setText(operator.getEntryDate());
        holder.approveRejectDateTv.setText(operator.getApproveDate() + "");
        if (operator.getStatus() != null && !operator.getStatus().isEmpty()) {
            holder.statusView.setVisibility(View.VISIBLE);
            holder.statusTv.setText(operator.getStatus() + "");
            if (operator.getStatus().equalsIgnoreCase("Accepted")) {
                holder.statusIcon.setImageResource(R.drawable.ic_success);
                holder.approveRejectDateLabel.setText("Accepted Date");
                holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.green));
            } else if (operator.getStatus().equalsIgnoreCase("Rejected")) {
                holder.statusIcon.setImageResource(R.drawable.ic_failed);
                holder.approveRejectDateLabel.setText("Rejected Date");
                holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.color_red));
            } else {
                holder.approveRejectDateLabel.setText("Pending Date");
                holder.statusIcon.setImageResource(R.drawable.ic_pending);
                holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.color_amber));
            }
        } else {
            holder.statusView.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    // Filter Class
    public void filter(String searchStr) {
        this.charText = searchStr.toLowerCase(Locale.getDefault());

        ArrayList<FundOrderReportObject> filterList = new ArrayList<>();
        if (charText.length() == 0) {
            filterList.addAll(rechargeStatus);
        } else {
            for (FundOrderReportObject wp : rechargeStatus) {
                if ((wp.getMode() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getMobileNo() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getAccountNo() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getBank() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getTransactionId() + "").toLowerCase(Locale.getDefault()).contains(charText)) {
                    filterList.add(wp);
                }
            }
        }
        transactionsList = filterList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView operatorImage;
        private AppCompatTextView bankName;
        private AppCompatTextView branchName;
        private AppCompatTextView acHolderName;
        private AppCompatTextView accountNumber;
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
        private LinearLayout mobileNoView;
        private AppCompatTextView mobileNoLabel;
        private AppCompatTextView mobileNo;
        private LinearLayout chequeNoView;
        private AppCompatTextView chequeNoLabel;
        private AppCompatTextView chequeNo;
        private LinearLayout cardNoView;
        private AppCompatTextView cardNoLabel;
        private AppCompatTextView cardNo;
        private LinearLayout upiIdView;
        private AppCompatTextView upiIdLabel;
        private AppCompatTextView upiId;
        private LinearLayout remarkView;
        private AppCompatTextView remarkLabel;
        private AppCompatTextView remark;
        private LinearLayout descripionView;
        private AppCompatTextView descriptionLabel;
        private AppCompatTextView description;
        private LinearLayout joinByView;
        private AppCompatImageView joinByIcon;
        private AppCompatTextView requestDateLabel;
        private AppCompatTextView requestDateTv;
        private LinearLayout joinDateView;
        private AppCompatImageView calanderIcon;
        private AppCompatTextView approveRejectDateLabel;
        private AppCompatTextView approveRejectDateTv;
        private LinearLayout statusView;
        private AppCompatImageView statusIcon;
        private AppCompatTextView statusLabel;
        private AppCompatTextView statusTv;

        public MyViewHolder(View view) {
            super(view);
            operatorImage = view.findViewById(R.id.operatorImage);
            operatorImage.setVisibility(View.GONE);
            bankName = view.findViewById(R.id.bankName);
            branchName = view.findViewById(R.id.branchName);
            acHolderName = view.findViewById(R.id.acHolderName);
            accountNumber = view.findViewById(R.id.accountNumber);
            amount = view.findViewById(R.id.amount);
            transactionDetailView = view.findViewById(R.id.transactionDetailView);
            transactionIdView = view.findViewById(R.id.transactionIdView);
            transactionIdLabel = view.findViewById(R.id.transactionIdLabel);
            txnId = view.findViewById(R.id.txnId);
            transactionModeView = view.findViewById(R.id.transactionModeView);
            transactionModeLabel = view.findViewById(R.id.transactionModeLabel);
            transactionMode = view.findViewById(R.id.transactionMode);
            liveIdView = view.findViewById(R.id.liveIdView);
            liveIdView.setVisibility(View.GONE);
            liveIdLabel = view.findViewById(R.id.liveIdLabel);
            liveID = view.findViewById(R.id.liveID);
            mobileNoView = view.findViewById(R.id.mobileNoView);
            mobileNoLabel = view.findViewById(R.id.mobileNoLabel);
            mobileNo = view.findViewById(R.id.mobileNo);
            chequeNoView = view.findViewById(R.id.chequeNoView);
            chequeNoLabel = view.findViewById(R.id.chequeNoLabel);
            chequeNo = view.findViewById(R.id.chequeNo);
            cardNoView = view.findViewById(R.id.cardNoView);
            cardNoLabel = view.findViewById(R.id.cardNoLabel);
            cardNo = view.findViewById(R.id.cardNo);
            upiIdView = view.findViewById(R.id.upiIdView);
            upiIdLabel = view.findViewById(R.id.upiIdLabel);
            upiId = view.findViewById(R.id.upiId);
            remarkView = view.findViewById(R.id.remarkView);
            remarkLabel = view.findViewById(R.id.remarkLabel);
            remark = view.findViewById(R.id.remark);
            descripionView = view.findViewById(R.id.descripionView);
            descriptionLabel = view.findViewById(R.id.descriptionLabel);
            description = view.findViewById(R.id.description);
            joinByView = view.findViewById(R.id.joinByView);
            joinByIcon = view.findViewById(R.id.joinByIcon);
            requestDateLabel = view.findViewById(R.id.requestDateLabel);
            requestDateTv = view.findViewById(R.id.requestDateTv);
            joinDateView = view.findViewById(R.id.joinDateView);
            calanderIcon = view.findViewById(R.id.calanderIcon);
            approveRejectDateLabel = view.findViewById(R.id.approveRejectDateLabel);
            approveRejectDateTv = view.findViewById(R.id.approveRejectDateTv);
            statusView = view.findViewById(R.id.statusView);
            statusIcon = view.findViewById(R.id.statusIcon);
            statusLabel = view.findViewById(R.id.statusLabel);
            statusTv = view.findViewById(R.id.statusTv);

        }
    }
}
