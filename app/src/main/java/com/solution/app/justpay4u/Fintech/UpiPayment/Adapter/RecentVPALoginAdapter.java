package com.solution.app.justpay4u.Fintech.UpiPayment.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.UpiPayment.Activity.UPIListActivity;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 29-12-2017.
 */

public class RecentVPALoginAdapter extends RecyclerView.Adapter<RecentVPALoginAdapter.MyViewHolder> {


    Activity activity;
    private ArrayList<String> operatorList;


    public RecentVPALoginAdapter(ArrayList<String> operatorList, Activity activity) {
        this.operatorList = operatorList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_recent_vpa_login, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String operator = operatorList.get(position);

        holder.beneName.setText(operator + "");


        holder.itemView.setOnClickListener(v -> {
            if (activity instanceof UPIListActivity) {
                ((UPIListActivity) activity).recentLogin(operator);
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
            beneName = view.findViewById(R.id.beneName);
            beneNumber = view.findViewById(R.id.beneNumber);


        }
    }


}
