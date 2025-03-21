package com.solution.app.justpay4u.Fintech.Dashboard.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;


public class WalletBalanceAdapter extends RecyclerView.Adapter<WalletBalanceAdapter.MyViewHolder> {

    int layout;
    private ArrayList<BalanceData> mList;
    private Context mContext;

    public WalletBalanceAdapter(Context mContext, ArrayList<BalanceData> mList, int layout) {
        this.mList = mList;
        this.mContext = mContext;
        this.layout = layout;
    }

    @Override
    public WalletBalanceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new WalletBalanceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WalletBalanceAdapter.MyViewHolder holder, int position) {
        final BalanceData operator = mList.get(position);

        holder.name.setText(operator.getWalletType());
        holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getBalance() + ""));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView name, amount;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            amount = view.findViewById(R.id.amount);

        }
    }
}
