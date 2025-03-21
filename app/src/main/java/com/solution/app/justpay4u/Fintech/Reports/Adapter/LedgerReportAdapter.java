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

import com.solution.app.justpay4u.Api.Fintech.Object.LedgerObject;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.Locale;

public class LedgerReportAdapter extends RecyclerView.Adapter<LedgerReportAdapter.MyViewHolder> {
    String charText = "";
    int resourceId = 0;
    private ArrayList<LedgerObject> mList = new ArrayList<>();
    private ArrayList<LedgerObject> mFilterList = new ArrayList<>();
    private Context mContext;

    public LedgerReportAdapter(ArrayList<LedgerObject> transactionsList, Context mContext) {
        this.mContext = mContext;
        this.mFilterList = transactionsList;
        this.mList = transactionsList;

    }

    @Override
    public LedgerReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_ledger_report, parent, false);

        return new LedgerReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LedgerReportAdapter.MyViewHolder holder, int position) {
        final LedgerObject operator = mFilterList.get(position);
        if (operator.getDebit() == 0) {
            holder.amountLabel.setText("Credit Amount");
            holder.amount.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero(operator.getCredit()));
            holder.amount.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (operator.getCredit() == 0) {
            holder.amountLabel.setText("Debit Amount");
            holder.amount.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero(operator.getDebit()));
            holder.amount.setTextColor(mContext.getResources().getColor(R.color.red));
        }


        holder.old.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero(operator.getLastAmount()));
        holder.balance.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero(operator.getCurentBalance()));
        holder.date.setText(operator.getEntryDate());
        holder.description.setText(operator.getDescription());

        if (operator.getRemark() != null && !operator.getRemark().isEmpty()) {
            holder.remarkView.setVisibility(View.VISIBLE);
            holder.remarks.setText(operator.getRemark() + "");
        } else {
            holder.remarkView.setVisibility(View.GONE);
        }

        String searchDescription = operator.getDescription().toLowerCase(Locale.getDefault());
        if (searchDescription.contains(charText)) {
            int startPos = searchDescription.indexOf(charText);
            int endPos = startPos + charText.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(operator.getDescription()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.description.setText(spanText, TextView.BufferType.SPANNABLE);
        } else {
            holder.description.setText(operator.getDescription());
        }


        String searchTid = operator.getTid().toLowerCase(Locale.getDefault());
        if (searchTid.contains(charText)) {
            int startPos = searchTid.indexOf(charText);
            int endPos = startPos + charText.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(operator.getTid()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tid.setText(spanText, TextView.BufferType.SPANNABLE);
        } else {
            holder.tid.setText("" + operator.getTid());
        }
    }

    @Override
    public int getItemCount() {
        return mFilterList.size();
    }

    // Filter Class
    public void filter(String str) {
        this.charText = str.toLowerCase(Locale.getDefault());

        ArrayList<LedgerObject> filterList = new ArrayList<>();
        if (charText.length() == 0) {
            filterList.addAll(mList);
        } else {
            for (LedgerObject wp : mList) {
                if (wp.getDescription().toLowerCase(Locale.getDefault()).contains(charText) || wp.getTid().toLowerCase(Locale.getDefault()).contains(charText)) {
                    filterList.add(wp);
                }
            }
        }

        mFilterList = filterList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView amount, amountLabel, balance, date, description, remarks, old, tid;
        View remarkView;

        public MyViewHolder(View view) {
            super(view);
            amount = view.findViewById(R.id.amount);
            amountLabel = view.findViewById(R.id.amountLabel);
            balance = view.findViewById(R.id.balance);
            old = view.findViewById(R.id.old);
            date = view.findViewById(R.id.date);
            remarkView = view.findViewById(R.id.remarkView);
            remarks = view.findViewById(R.id.remark);
            description = view.findViewById(R.id.description);
            tid = view.findViewById(R.id.tid);
        }
    }
}
