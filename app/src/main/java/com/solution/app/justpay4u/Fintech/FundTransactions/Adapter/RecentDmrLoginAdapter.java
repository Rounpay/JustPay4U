package com.solution.app.justpay4u.Fintech.FundTransactions.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.RecentDmrLogin;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.DMRActivity;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.DMRWithPipeActivity;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 29-12-2017.
 */

public class RecentDmrLoginAdapter extends RecyclerView.Adapter<RecentDmrLoginAdapter.MyViewHolder> {


    Activity activity;
    private ArrayList<RecentDmrLogin> operatorList;


    public RecentDmrLoginAdapter(ArrayList<RecentDmrLogin> operatorList, Activity activity) {
        this.operatorList = operatorList;
        this.activity = activity;
    }

    @Override
    public RecentDmrLoginAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_recent_dmr_login, parent, false);


        return new RecentDmrLoginAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecentDmrLoginAdapter.MyViewHolder holder, final int position) {
        final RecentDmrLogin operator = operatorList.get(position);

        if (operator.getName() != null && !operator.getName().isEmpty() && !operator.getName().equalsIgnoreCase("null")) {
            holder.beneName.setText(operator.getName());
            holder.beneName.setVisibility(View.VISIBLE);
        } else {
            holder.beneName.setVisibility(View.GONE);
        }

        holder.beneNumber.setText(operator.getNumber());


        holder.itemView.setOnClickListener(v -> {
            if (activity instanceof DMRActivity) {
                ((DMRActivity) activity).recentLogin(operator);
            } else if (activity instanceof DMRWithPipeActivity) {
                ((DMRWithPipeActivity) activity).recentLogin(operator);
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView beneName;
        public TextView beneNumber;
        View itemView;


        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            beneName = (TextView) view.findViewById(R.id.beneName);
            beneNumber = (TextView) view.findViewById(R.id.beneNumber);


        }
    }


}
