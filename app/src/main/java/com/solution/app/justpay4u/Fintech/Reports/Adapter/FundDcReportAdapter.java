package com.solution.app.justpay4u.Fintech.Reports.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.FundDCObject;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vishnu on 15-04-2017.
 */

public class FundDcReportAdapter extends RecyclerView.Adapter<FundDcReportAdapter.MyViewHolder> {
    public int Prepaid = 1;
    public int Utilities = 2;
    public int Bank = 3;
    public int Card = 4;
    public int RegID = 5;
    public int Package = 6;
    String charText = "";
    String ServiceType;
    private ArrayList<FundDCObject> rechargeStatus;
    private ArrayList<FundDCObject> transactionsList;
    private Context mContext;

    public FundDcReportAdapter(ArrayList<FundDCObject> transactionsList, Context mContext, String ServiceType) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.rechargeStatus = transactionsList;
        this.ServiceType = ServiceType;
    }

    @Override
    public FundDcReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_fund_dc_report, parent, false);

        return new FundDcReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FundDcReportAdapter.MyViewHolder holder, int position) {
        final FundDCObject operator = transactionsList.get(position);

        // holder.description.setText(" " + operator.getDescription() + "");
        holder.balance.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getCurrentAmount() + ""));
        holder.Amount.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getAmount() + ""));
        holder.date.setText(operator.getEntryDate() + "");

        if (operator.getServiceTypeID() == 4) {
            holder.AmountLabel.setText("Credit Amount");
            holder.dateLabel.setText("Received Date");
            holder.Amount.setTextColor(mContext.getResources().getColor(R.color.green));
        } else {
            holder.AmountLabel.setText("Debit Amount");
            holder.dateLabel.setText("Transaction Date");
            holder.Amount.setTextColor(mContext.getResources().getColor(R.color.color_red));
        }
        holder.receivedByLabel.setText(ServiceType + " : ");

        holder.walletType.setText(GetWalletType(operator.getWalletID()) + "");
        if (operator.getRemark() != null && !operator.getRemark().isEmpty()) {
            holder.remarkView.setVisibility(View.VISIBLE);
            holder.remark.setText(operator.getRemark() + "");
        } else {
            holder.remarkView.setVisibility(View.GONE);
        }


        String searchOtherUser = operator.getOtherUser().toLowerCase(Locale.getDefault()) + "";
        if (charText != null && !charText.isEmpty() && searchOtherUser.contains(charText)) {
            int startPos = searchOtherUser.indexOf(charText);
            int endPos = startPos + charText.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(operator.getOtherUser()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.receivedBy.setText(spanText, TextView.BufferType.SPANNABLE);

        } else {
            holder.receivedBy.setText(operator.getOtherUser() + "");
        }

        String searchTid = operator.getTransactionID().toLowerCase(Locale.getDefault()) + "";
        if (charText != null && !charText.isEmpty() && searchTid.contains(charText)) {
            int startPos = searchTid.indexOf(charText);
            int endPos = startPos + charText.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(operator.getTransactionID()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tid.setText(spanText, TextView.BufferType.SPANNABLE);

        } else {
            holder.tid.setText(operator.getTransactionID() + "");
        }


        String searchDescription = operator.getDescription().toLowerCase(Locale.getDefault()) + "";
        if (charText != null && !charText.isEmpty() && searchDescription.contains(charText)) {
            int startPos = searchDescription.indexOf(charText);
            int endPos = startPos + charText.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(operator.getDescription()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.description.setText(spanText, TextView.BufferType.SPANNABLE);

        } else {
            holder.description.setText(operator.getDescription() + "");
        }
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public String GetWalletType(int T) {
        if (T == Prepaid)
            return "Prepaid";
        if (T == Utilities)
            return "Utility";
        if (T == Bank)
            return "Bank";
        if (T == Card)
            return "Card";
        if (T == RegID)
            return "RegID";
        if (T == Package)
            return "Package";
        return "";
    }

    // Filter Class
    public void filter(String str) {
        this.charText = str.toLowerCase(Locale.getDefault());

        ArrayList<FundDCObject> filterList = new ArrayList<>();
        if (charText.length() == 0) {
            filterList.addAll(rechargeStatus);
        } else {
            for (FundDCObject wp : rechargeStatus) {
                if ((wp.getDescription() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getTransactionID() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getOtherUser() + "").toLowerCase(Locale.getDefault()).contains(charText)) {
                    filterList.add(wp);
                }
            }
        }

        transactionsList = filterList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView remarkLabel, remark, dateLabel, description, descriptionLabel, balance, date, tidLabel, tid, Amount, AmountLabel, receivedByLabel, receivedBy, walletType;
        View remarkView;

        public MyViewHolder(View view) {
            super(view);
            description = (AppCompatTextView) view.findViewById(R.id.description);
            descriptionLabel = (AppCompatTextView) view.findViewById(R.id.descriptionLabel);
            balance = (AppCompatTextView) view.findViewById(R.id.balance);
            date = (AppCompatTextView) view.findViewById(R.id.date);
            dateLabel = view.findViewById(R.id.dateLabel);
            tidLabel = (AppCompatTextView) view.findViewById(R.id.tidLabel);
            tid = (AppCompatTextView) view.findViewById(R.id.tid);
            Amount = (AppCompatTextView) view.findViewById(R.id.Amount);
            AmountLabel = (AppCompatTextView) view.findViewById(R.id.AmountLabel);
            remarkLabel = (AppCompatTextView) view.findViewById(R.id.remarkLabel);
            remark = (AppCompatTextView) view.findViewById(R.id.remark);
            remarkView = view.findViewById(R.id.remarkView);
            receivedByLabel = (AppCompatTextView) view.findViewById(R.id.receivedByLabel);
            receivedBy = (AppCompatTextView) view.findViewById(R.id.receivedBy);
            walletType = (AppCompatTextView) view.findViewById(R.id.walletType);
        }
    }
}
