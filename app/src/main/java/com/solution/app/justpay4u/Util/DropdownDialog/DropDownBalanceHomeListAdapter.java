package com.solution.app.justpay4u.Util.DropdownDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.List;

public class DropDownBalanceHomeListAdapter extends RecyclerView.Adapter<DropDownBalanceHomeListAdapter.MyViewHolder> {

    Context context;
    private List<BalanceData> mList;

    public DropDownBalanceHomeListAdapter(Context context, List<BalanceData> mList) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_home_wallet_balance, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final BalanceData item = mList.get(position);
        holder.name.setText(item.getWalletType() + "");

        holder.amount.setVisibility(View.VISIBLE);
        holder.amount.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero(item.getBalance()));


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView name, amount;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            name = view.findViewById(R.id.name);
            amount = view.findViewById(R.id.amount);
        }


    }
}
