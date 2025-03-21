package com.solution.app.justpay4u.Fintech.Recharge.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.IncentiveDetails;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.RechargeBillPaymentActivity;

import java.util.ArrayList;

/**
 * Created by Vishnu on 18-01-2017.
 */

public class IncentiveAdapter extends RecyclerView.Adapter<IncentiveAdapter.MyViewHolder> {

    int resourceId = 0;
    private ArrayList<IncentiveDetails> operatorList;
    private Context mContext;

    public IncentiveAdapter(ArrayList<IncentiveDetails> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incentive_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final IncentiveDetails operator = operatorList.get(position);
        holder.denomination.setText("\u20B9 " + operator.getDenomination());
        if (operator.isAmtType()) {
            holder.comm.setText("\u20B9 " + operator.getComm());
        } else {
            holder.comm.setText(operator.getComm() + " %");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof RechargeBillPaymentActivity) {
                    ((RechargeBillPaymentActivity) mContext).setCashBackAmount(operator);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView denomination, comm;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;

            denomination = (TextView) view.findViewById(R.id.denomination);
            comm = (TextView) view.findViewById(R.id.comm);


        }
    }

}
