package com.solution.app.justpay4u.Fintech.MoveToWallet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Fintech.MoveToWallet.Activity.WalletToWalletActivity;
import com.solution.app.justpay4u.Fintech.Reports.Activity.LedgerReportActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;


public class WalletBalanceAdapter extends RecyclerView.Adapter<WalletBalanceAdapter.MyViewHolder> {


    private ArrayList<BalanceData> mList;
    private Context mContext;

    public WalletBalanceAdapter(Context mContext, ArrayList<BalanceData> mList) {
        this.mList = mList;
        this.mContext = mContext;

    }

    @Override
    public WalletBalanceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_wallet_balance, parent, false);

        return new WalletBalanceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WalletBalanceAdapter.MyViewHolder holder, int position) {
        final BalanceData operator = mList.get(position);

        if (mContext instanceof WalletToWalletActivity) {
            if (((WalletToWalletActivity) mContext).balanceDataMap != null &&
                    !((WalletToWalletActivity) mContext).balanceDataMap.containsKey(operator.getId())) {
                ((WalletToWalletActivity) mContext).balanceDataMap.put(operator.getId(), operator);
            }
        }

        holder.name.setText(operator.getWalletType());
        holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getBalance() + ""));
        holder.amountView.setOnClickListener(view -> mContext.startActivity(new Intent(mContext, LedgerReportActivity.class)
                .putExtra("WALLET_NAME", operator.getWalletType())
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView name, amount;
        ImageView iv_wallet;
        View amountView;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            amount = view.findViewById(R.id.amount);
            amountView = view.findViewById(R.id.amountView);
            // iv_wallet = view.findViewById(R.id.iv_wallet);

        }
    }
}
