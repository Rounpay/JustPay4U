package com.solution.app.justpay4u.Fintech.AppUser.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.R;

import java.util.List;

public class WalletTypeAdapter extends RecyclerView.Adapter<WalletTypeAdapter.MyViewHolder> {

    int cliclPosition = -1;
    onClick mOnClick;
    private List<BalanceData> transactionsList;
    private Context mContext;

    public WalletTypeAdapter(Context mContext, List<BalanceData> transactionsList, onClick mOnClick) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.mOnClick = mOnClick;
    }

    @Override
    public WalletTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_wallet_type, parent, false);

        return new WalletTypeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WalletTypeAdapter.MyViewHolder holder, final int position) {
        final BalanceData operator = transactionsList.get(position);
        holder.walletTv.setText(operator.getWalletType());
        if (position == 0 && cliclPosition == -1 || cliclPosition == position) {
            holder.walletTv.setBackgroundResource(R.drawable.rounded_light_green);
            holder.walletTv.setTextColor(Color.WHITE);
        } else {
            holder.walletTv.setBackgroundResource(0);
            holder.walletTv.setTextColor(mContext.getResources().getColor(R.color.lightDarkGreen));
        }
        holder.walletTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliclPosition = position;
                notifyDataSetChanged();
                if (mOnClick != null) {
                    mOnClick.onClick(operator.getId());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    interface onClick {
        void onClick(int id);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView walletTv;


        public MyViewHolder(View view) {
            super(view);
            walletTv = view.findViewById(R.id.walletTv);

        }
    }
}
