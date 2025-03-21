package com.solution.app.justpay4u.Fintech.AppUser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.BalanceType;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.List;

public class AppUserBalanceAdapter extends RecyclerView.Adapter<AppUserBalanceAdapter.MyViewHolder> {

    private List<BalanceType> transactionsList;
    private Context mContext;


    public AppUserBalanceAdapter(Context mContext, List<BalanceType> transactionsList) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public com.solution.app.justpay4u.Fintech.AppUser.Adapter.AppUserBalanceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_app_user_balance, parent, false);

        return new com.solution.app.justpay4u.Fintech.AppUser.Adapter.AppUserBalanceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final com.solution.app.justpay4u.Fintech.AppUser.Adapter.AppUserBalanceAdapter.MyViewHolder holder, final int position) {
        final BalanceType operator = transactionsList.get(position);
        holder.nameTv.setText(operator.getName());
        holder.balanceTv.setText("\u20B9 " + Utility.INSTANCE.formatedAmountWithOutRupees(operator.getAmount() + ""));


    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public interface onClick {
        void onClick(int id);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTv, balanceTv;


        public MyViewHolder(View view) {
            super(view);
            nameTv = view.findViewById(R.id.name);
            balanceTv = view.findViewById(R.id.balance);

        }
    }
}
