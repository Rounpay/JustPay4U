package com.solution.app.justpay4u.Networking.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Networking.Object.DashboardIncomeDetail;
import com.solution.app.justpay4u.Networking.Activity.IncomeReportNetworkingActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.List;

/**
 * Created by Vishnu on 24/02/2021.
 */

public class IncomeNetworkingAdapter extends RecyclerView.Adapter<IncomeNetworkingAdapter.MyViewHolder> {

    private final List<DashboardIncomeDetail> incomeList;
    private final Activity mContext;
    private BalanceResponse mBalanceResponse;


    public IncomeNetworkingAdapter(List<DashboardIncomeDetail> incomeList, Activity mContext) {
        this.incomeList = incomeList;
        this.mContext = mContext;


    }

    public void setBalanceData(BalanceResponse mBalanceResponse) {
        this.mBalanceResponse = mBalanceResponse;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_networking_income, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final DashboardIncomeDetail item = incomeList.get(position);

        holder.name.setText(item.getIncomeName() + "");
        if (mBalanceResponse != null) {
            holder.amt.setText(mBalanceResponse.getDefaultCurrencySymbol() + " " + Utility.INSTANCE.formatedAmountReplaceLastZero(item.getIncomeFigure()));
        } else {
            holder.amt.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero(item.getIncomeFigure()));

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, IncomeReportNetworkingActivity.class)
                        .putExtra("Title", item.getIncomeName() + "")
                       // .putExtra("Symbol", mBalanceResponse.getDefaultCurrencySymbol() + "")
                        .putExtra("IncomeCategoryId", item.getIncomeCategoryID())
                        .putExtra("IncomeOPID", item.getIncomeOPID()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return incomeList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final TextView name, amt;
        View itemView;


        public MyViewHolder(View view) {
            super(view);
            itemView = view;

            name = view.findViewById(R.id.name);
            amt = view.findViewById(R.id.amt);

        }
    }


}