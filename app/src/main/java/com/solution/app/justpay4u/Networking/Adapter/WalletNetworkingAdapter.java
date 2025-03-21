package com.solution.app.justpay4u.Networking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Networking.Activity.NetworkingDashboardActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;


public class WalletNetworkingAdapter extends RecyclerView.Adapter<WalletNetworkingAdapter.MyViewHolder> {

    int layout;
    private ArrayList<BalanceData> mList;
    private Context mContext;

    public WalletNetworkingAdapter(Context mContext, ArrayList<BalanceData> mList, int layout) {
        this.mList = mList;
        this.mContext = mContext;
        this.layout = layout;
    }

    public void changeLayout(int layout) {
        this.layout = layout;

    }

    @Override
    public WalletNetworkingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new WalletNetworkingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WalletNetworkingAdapter.MyViewHolder holder, int position) {
        final BalanceData item = mList.get(position);

        holder.name.setText(item.getWalletType());
        holder.amount.setText(Utility.INSTANCE.formatedAmountReplaceLastZero(item.getBalance()));
        if (position % 2 == 1) {
            holder.amountLabel.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryLight));
            ViewCompat.setBackgroundTintList(holder.transferBtn, ContextCompat.getColorStateList(mContext, R.color.colorPrimaryLight));
            ViewCompat.setBackgroundTintList(holder.withdrawalBtn, ContextCompat.getColorStateList(mContext, R.color.colorPrimaryLight));
            ViewCompat.setBackgroundTintList(holder.itemView, ContextCompat.getColorStateList(mContext, R.color.colorPrimaryMoreMoreLight));
        } else {
            holder.amountLabel.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccentDark));

            ViewCompat.setBackgroundTintList(holder.transferBtn, ContextCompat.getColorStateList(mContext, R.color.colorAccentDark));
            ViewCompat.setBackgroundTintList(holder.withdrawalBtn, ContextCompat.getColorStateList(mContext, R.color.colorAccentDark));
            ViewCompat.setBackgroundTintList(holder.itemView, ContextCompat.getColorStateList(mContext, R.color.colorPrimaryMoreLight));
        }

        if (item.isWithdrawal() && item.getWithdrawalType() > 0) {
            holder.withdrawalBtn.setVisibility(View.VISIBLE);
        } else {
            holder.withdrawalBtn.setVisibility(View.GONE);

        }
    /*    if (item.getWalletType().equalsIgnoreCase("Global Rewards")){
            holder.reserve.setVisibility(View.VISIBLE);
            ViewCompat.setBackgroundTintList(holder.reserve,ContextCompat.getColorStateList(mContext,R.color.colorAccentDark));
        }else {
            holder.reserve.setVisibility(View.GONE);
        }*/

        if (item.getWalletTransferType() > 0) {
            holder.transferBtn.setVisibility(View.VISIBLE);
        } else {
            holder.transferBtn.setVisibility(View.GONE);
        }
        holder.transferBtn.setOnClickListener(view -> {
            if (mContext instanceof NetworkingDashboardActivity) {
                ((NetworkingDashboardActivity) mContext).openMoveToWallet(item);
            }
        });
        holder.withdrawalBtn.setOnClickListener(view -> {
            if (mContext instanceof NetworkingDashboardActivity) {
                String val=item.getWalletCurrencySymbol() + " " + Utility.INSTANCE.formatedAmountReplaceLastZero(item.getBalance());
                ((NetworkingDashboardActivity) mContext).Withdrawal(item,val,item.getWalletType());
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, amount, amountLabel, withdrawalBtn, transferBtn,reserve;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            amountLabel = view.findViewById(R.id.amountLabel);
            amount = view.findViewById(R.id.amount);
            withdrawalBtn = view.findViewById(R.id.withdrawalBtn);
            transferBtn = view.findViewById(R.id.transferBtn);
          //  reserve = view.findViewById(R.id.reserve);

        }
    }
}
