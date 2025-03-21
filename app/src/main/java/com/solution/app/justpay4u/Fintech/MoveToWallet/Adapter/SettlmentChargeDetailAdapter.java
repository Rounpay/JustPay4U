package com.solution.app.justpay4u.Fintech.MoveToWallet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.SlabRangeDetail;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;

/**
 * Created by Vishnu on 26-04-2017.
 */

public class SettlmentChargeDetailAdapter extends RecyclerView.Adapter<SettlmentChargeDetailAdapter.MyViewHolder> {

    private ArrayList<SlabRangeDetail> transactionsList;
    private Context mContext;

    public SettlmentChargeDetailAdapter(ArrayList<SlabRangeDetail> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_settlement_charge_detail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SlabRangeDetail operator = transactionsList.get(position);
        holder.range.setText(operator.getMinRange() + " - " + operator.getMaxRange());
        String comType, amtType;
        if (operator.isCommType()) {
            comType = "(SUR.)";

        } else {
            comType = "(COMM.)";
        }

        if (operator.isAmtType()) {
            amtType = Utility.INSTANCE.formatedAmountWithRupees(operator.getComm() + "");
        } else {
            amtType = Utility.INSTANCE.formatedAmountWithOutRupees(operator.getComm() + "") + " %";
        }

        /*if (operator.getDmrModelID() == 2 || operator.getDmrModelID() == 3 ||
                operator.getDmrModelID() == 4) {
            holder.fixed.setVisibility(View.VISIBLE);
            holder.maxCom.setVisibility(View.GONE);
        } else if (operator.getDmrModelID() == 1) {
            holder.fixed.setVisibility(View.GONE);
            holder.maxCom.setVisibility(View.GONE);
        } else {
            holder.fixed.setVisibility(View.GONE);
            holder.maxCom.setVisibility(View.VISIBLE);
        }*/

        holder.operator.setText(operator.getOperator() + "");
        holder.comSur.setText(amtType + " " + comType);
        /*holder.fixed.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getFixedCharge() + ""));
        holder.maxCom.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getMaxComm() + ""));*/
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView comSur, operator, maxCom, range, fixed;


        public MyViewHolder(View view) {
            super(view);
            operator = (AppCompatTextView) view.findViewById(R.id.operator);
            fixed = (AppCompatTextView) view.findViewById(R.id.fixed);
            comSur = (AppCompatTextView) view.findViewById(R.id.comSur);
            maxCom = (AppCompatTextView) view.findViewById(R.id.maxCom);
            range = (AppCompatTextView) view.findViewById(R.id.range);
        }
    }

}