package com.solution.app.justpay4u.Fintech.AppUser.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.LedgerObject;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.Locale;

public class AccountStatementReportAdapter extends RecyclerView.Adapter<AccountStatementReportAdapter.MyViewHolder> implements Filterable {


    CustomLoader loader;
    private ArrayList<LedgerObject> filterListItem = new ArrayList<>();
    private ArrayList<LedgerObject> listItem = new ArrayList<>();
    private Activity mContext;
    private String charText = "";

    public AccountStatementReportAdapter(ArrayList<LedgerObject> transactionsList,
                                         Context mContext) {
        this.mContext = (Activity) mContext;
        this.filterListItem = transactionsList;
        this.listItem = transactionsList;

    }


    @Override
    public AccountStatementReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_account_statement_report, parent, false);

        return new AccountStatementReportAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final AccountStatementReportAdapter.MyViewHolder holder, int position) {
        final LedgerObject operator = filterListItem.get(position);
        if (operator.getDebit() == 0) {
            holder.amountLabel.setText("Credit Amount" + " : ");
            holder.amount.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( operator.getCredit()));
            holder.amount.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (operator.getCredit() == 0) {
            holder.amountLabel.setText("Debit Amount" + " : ");
            holder.amount.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( operator.getDebit()));
            holder.amount.setTextColor(mContext.getResources().getColor(R.color.red));
        }


        holder.old.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( operator.getLastAmount()));
        holder.balance.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( operator.getCurentBalance()));
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
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                charText = charSequence.toString();
                if (charText.isEmpty()) {
                    filterListItem = listItem;
                } else {
                    ArrayList<LedgerObject> filteredList = new ArrayList<>();
                    for (LedgerObject row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getMobileNo() + "").toLowerCase().contains(charText.toLowerCase()) ||
                                (row.getUser() + "").toLowerCase().contains(charText.toLowerCase()) ||
                                (row.getCurentBalance() + "").toLowerCase().contains(charText.toLowerCase()) ||
                                (row.getTid() + "").toLowerCase().contains(charText.toLowerCase()) ||
                                (row.getDescription() + "").toLowerCase().contains(charText.toLowerCase())
                        ) {
                            filteredList.add(row);
                        }
                    }

                    filterListItem = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterListItem;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterListItem = (ArrayList<LedgerObject>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getItemCount() {
        return filterListItem.size();
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
