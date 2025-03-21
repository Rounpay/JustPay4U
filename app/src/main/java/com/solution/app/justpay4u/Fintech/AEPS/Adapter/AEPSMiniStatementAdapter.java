package com.solution.app.justpay4u.Fintech.AEPS.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.MiniStatements;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;

/**
 * Created by Vishnu on 14-04-2017.
 */

public class AEPSMiniStatementAdapter extends RecyclerView.Adapter<AEPSMiniStatementAdapter.MyViewHolder> {


    private ArrayList<MiniStatements> operatorList;
    private Context mContext;

    public AEPSMiniStatementAdapter(ArrayList<MiniStatements> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public AEPSMiniStatementAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_mini_statement, parent, false);

        return new AEPSMiniStatementAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AEPSMiniStatementAdapter.MyViewHolder holder, int position) {
        final MiniStatements operator = operatorList.get(position);
        holder.date.setText(operator.getTransactionDate());
        holder.narration.setText(operator.getNarration());
        holder.amt.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getAmount() + ""));

        if (operator.getTransactionType().toLowerCase().equalsIgnoreCase("cr")) {
            holder.amt.setTextColor(mContext.getResources().getColor(R.color.green));
        } else {
            holder.amt.setTextColor(mContext.getResources().getColor(R.color.color_red));
        }
    }


    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, amt, narration;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            date = view.findViewById(R.id.date);
            amt = view.findViewById(R.id.amt);
            narration = view.findViewById(R.id.narration);

        }
    }

}



