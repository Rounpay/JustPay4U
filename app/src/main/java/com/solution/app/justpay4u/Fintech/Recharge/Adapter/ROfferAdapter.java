package com.solution.app.justpay4u.Fintech.Recharge.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.ROfferObject;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.ROfferActivity;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;


public class ROfferAdapter extends RecyclerView.Adapter<ROfferAdapter.MyViewHolder> {


    private ArrayList<ROfferObject> transactionsList;
    private Context mContext;

    public ROfferAdapter(ArrayList<ROfferObject> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public ROfferAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_recharge_plan, parent, false);

        return new ROfferAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ROfferAdapter.MyViewHolder holder, int position) {
        final ROfferObject operator = transactionsList.get(position);

        holder.description.setText(operator.getDesc() + "");
        holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getRs()));

        holder.itemView.setOnClickListener(v -> ((ROfferActivity) mContext).ItemClick("" + operator.getRs(), operator.getDesc()));
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView validity;
        public TextView amount;
        public TextView description;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            //  talktime = view.findViewById(R.id.talktime);
            validity = view.findViewById(R.id.validity);
            validity.setVisibility(View.GONE);
            amount = view.findViewById(R.id.amount);
            description = view.findViewById(R.id.description);
            view.findViewById(R.id.line).setVisibility(View.GONE);
            view.findViewById(R.id.validityLabel).setVisibility(View.GONE);
        }
    }

}
