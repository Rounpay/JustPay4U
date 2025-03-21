package com.solution.app.justpay4u.Fintech.Reports.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.RefundLog;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Reports.Activity.W2RHistoryActivity;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vishnu on 18-04-2017.
 */

public class DisputeReportAdapter extends RecyclerView.Adapter<DisputeReportAdapter.MyViewHolder> {
    private final RequestOptions requestOptions;
    String charText = "";
    private List<RefundLog> rechargeStatus;
    private List<RefundLog> transactionsList;
    private Context mContext;

    public DisputeReportAdapter(List<RefundLog> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.rechargeStatus = transactionsList;
        requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
    }

    @Override
    public DisputeReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dispute_report, parent, false);

        return new DisputeReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DisputeReportAdapter.MyViewHolder holder, int position) {
        final RefundLog operator = transactionsList.get(position);
        holder.txn.setText("" + operator.getTransactionID());
        holder.operator.setText("" + operator.getOperator());
        holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getRequestedAmount() + ""));
        holder.number.setText("" + operator.getAccountNo());
        holder.acceptRejectDate.setText("" + operator.getRefundActionDate());

        holder.requestDate.setText("" + operator.getRefundRequestDate());
        holder.rechargeDate.setText("" + operator.getEntryDate());
        if (operator.getLiveID() != null && !operator.getLiveID().isEmpty()) {
            holder.liveId.setText("" + operator.getLiveID());
            holder.liveIdView.setVisibility(View.VISIBLE);
        } else {
            holder.liveIdView.setVisibility(View.GONE);
        }
        holder.refundRemark.setText("" + operator.getRefundRemark());
        if (mContext instanceof W2RHistoryActivity) {
            holder.refundRemarkView.setVisibility(View.VISIBLE);
        } else {
            holder.refundRemarkView.setVisibility(View.GONE);
        }

        holder.status.setText("" + operator.getrStatus());
        if (operator.getrStatus().equalsIgnoreCase("SUCCESS")) {
            holder.statusIcon.setImageResource(R.drawable.ic_success);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.green));

        } else if (operator.getrStatus().equalsIgnoreCase("FAILED")) {
            holder.statusIcon.setImageResource(R.drawable.ic_failed);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_red));

        } else if (operator.getrStatus().equalsIgnoreCase("REFUNDED") || operator.getrStatus().equalsIgnoreCase("REFUND")) {
            holder.statusIcon.setImageResource(R.drawable.ic_refund);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_cyan));

        } else if (operator.getrStatus().equalsIgnoreCase("PENDING")) {
            holder.statusIcon.setImageResource(R.drawable.ic_pending);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_amber));
        } else {
            holder.statusIcon.setImageResource(R.drawable.ic_warning_other);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_deep_orange));
        }


        if (operator.getOid() != 0) {
            Glide.with(mContext)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getOid() + ".png")
                    .apply(requestOptions)
                    .into(holder.operatorImage);
        } else {
            holder.operatorImage.setImageResource(R.drawable.ic_tower);
        }
        holder.refundStatus.setText("" + operator.getRefundType());

        if (operator.getRefundType().equalsIgnoreCase("UNDER REVIEW")) {
            holder.acceptRejectDateLabel.setText("Review Date");

        } else if (operator.getrStatus().equalsIgnoreCase("REJECTED") || operator.getrStatus().equalsIgnoreCase("REJECT")) {
            holder.acceptRejectDateLabel.setText("Rejected Date");

        } else if (operator.getrStatus().equalsIgnoreCase("REFUNDED") || operator.getrStatus().equalsIgnoreCase("REFUND")) {
            holder.acceptRejectDateLabel.setText("Refunded Date");

        } else {
            holder.acceptRejectDateLabel.setText("Process Date");
        }

    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    // Filter Class
    public void filter(String str) {
        this.charText = str.toLowerCase(Locale.getDefault());

        List<RefundLog> filterList = new ArrayList<>();
        if (charText.length() == 0) {
            filterList.addAll(rechargeStatus);
        } else {
            for (RefundLog wp : rechargeStatus) {
                if (wp.getAccountNo().toLowerCase(Locale.getDefault()).contains(charText) || wp.getOperator().toLowerCase(Locale.getDefault()).contains(charText)
                        || wp.getOutletMobile().toLowerCase(Locale.getDefault()).contains(charText) || wp.getOutletName().toLowerCase(Locale.getDefault()).contains(charText)
                        || wp.getTransactionID().toLowerCase(Locale.getDefault()).contains(charText) || wp.getrStatus().toLowerCase(Locale.getDefault()).contains(charText)) {
                    filterList.add(wp);
                }
            }
        }

        transactionsList = filterList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View refundRemarkView;
        View liveIdView;
        private ImageView statusIcon;
        private AppCompatTextView status;
        private AppCompatTextView amount;
        private AppCompatTextView operator;
        private AppCompatTextView txnLabel;
        private AppCompatTextView txn;
        private AppCompatTextView liveIdLabel;
        private AppCompatTextView liveId;
        private ImageView operatorImage;
        private AppCompatTextView number;
        private AppCompatTextView requestDateLabel;
        private AppCompatTextView requestDate;
        private AppCompatTextView acceptRejectDateLabel;
        private AppCompatTextView acceptRejectDate;
        private AppCompatTextView rechargeDateLabel;
        private AppCompatTextView rechargeDate, refundRemark;
        private AppCompatTextView refundStatusLabel;
        private AppCompatTextView refundStatus;


        public MyViewHolder(View view) {
            super(view);
            refundRemarkView = view.findViewById(R.id.refundRemarkView);
            statusIcon = view.findViewById(R.id.statusIcon);
            status = view.findViewById(R.id.status);
            amount = view.findViewById(R.id.amount);
            operator = view.findViewById(R.id.operator);
            txnLabel = view.findViewById(R.id.txnLabel);
            txn = view.findViewById(R.id.txn);
            liveIdView = view.findViewById(R.id.liveIdView);
            liveIdLabel = view.findViewById(R.id.liveIdLabel);
            liveId = view.findViewById(R.id.liveId);
            operatorImage = view.findViewById(R.id.operatorImage);
            number = view.findViewById(R.id.number);
            requestDateLabel = view.findViewById(R.id.requestDateLabel);
            requestDate = view.findViewById(R.id.requestDate);
            acceptRejectDateLabel = view.findViewById(R.id.acceptRejectDateLabel);
            acceptRejectDate = view.findViewById(R.id.acceptRejectDate);
            rechargeDateLabel = view.findViewById(R.id.rechargeDateLabel);
            rechargeDate = view.findViewById(R.id.rechargeDate);
            refundStatusLabel = view.findViewById(R.id.refundStatusLabel);
            refundStatus = view.findViewById(R.id.refundStatus);
            refundRemark = view.findViewById(R.id.refundRemark);
        }
    }
}