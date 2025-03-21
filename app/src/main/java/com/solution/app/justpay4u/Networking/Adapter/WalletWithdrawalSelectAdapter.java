package com.solution.app.justpay4u.Networking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.AllowedWithdrawalType;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.Networking.Activity.NetworkingDashboardActivity;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

public class WalletWithdrawalSelectAdapter extends RecyclerView.Adapter<WalletWithdrawalSelectAdapter.MyViewHolder> {

    RequestOptions requestOptions;
    private ArrayList<AllowedWithdrawalType> operatorList;
    private Context mContext;
    BalanceData mBalanceData;
    String val;
    String name;

    public WalletWithdrawalSelectAdapter(ArrayList<AllowedWithdrawalType> operatorList,String val,String name, Context mContext,
                                         BalanceData mBalanceData) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.mBalanceData = mBalanceData;
        this.val = val;
        this.name = name;
        requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_networking_deposit_coin_select, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AllowedWithdrawalType operator = operatorList.get(position);
        holder.title.setText(operator.getServiceName());

        if (operator.getServiceId() == 2) {
            holder.opImage.setImageResource(R.drawable.ic_dialog_money_transfer);
        }else if (operator.getServiceId() == 42) {
            holder.opImage.setImageResource(R.drawable.ic_bank_transfer);
        } else if (operator.getServiceId() == 62) {
            holder.opImage.setImageResource(R.drawable.ic_dialog_upi_payment);
        } else if (operator.getServiceId() == 76) {
            holder.opImage.setImageResource(R.drawable.ic_dialog_crypto_coin_transfer);
        } else {
            holder.opImage.setImageResource(R.drawable.ic_dialog_wallet_to_wallet);
        }
        holder.ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NetworkingDashboardActivity) mContext).openWalletWithdrawal(operator,mBalanceData,val,name);
            }
        });


    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView opImage;
        LinearLayout ll_top;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            opImage = view.findViewById(R.id.opImage);
            ll_top = view.findViewById(R.id.ll_top);

        }
    }

}
