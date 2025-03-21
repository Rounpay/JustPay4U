package com.solution.app.justpay4u.Fintech.Recharge.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.DTHInfoRecords;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

public class DthCustInfoAdapter extends RecyclerView.Adapter<DthCustInfoAdapter.MyViewHolder> {

    int resourceId = 0;
    private ArrayList<DTHInfoRecords> listItem;
    private Context mContext;

    public DthCustInfoAdapter(ArrayList<DTHInfoRecords> listItem, Context mContext) {

        this.listItem = listItem;
        this.mContext = mContext;

    }

    @Override
    public DthCustInfoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dth_cust_info, parent, false);

        return new DthCustInfoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DthCustInfoAdapter.MyViewHolder holder, int position) {
        final DTHInfoRecords operator = listItem.get(position);

        holder.key.setText(operator.getKey() + "");
        holder.value.setText(operator.getValue() + "");
       /* holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CircleZoneActivity) mContext).ItemClick(operator.getCircle(), operator.getId(), operator.getCircle());
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView key, value;


        public MyViewHolder(View view) {
            super(view);
            key = view.findViewById(R.id.key);
            value = view.findViewById(R.id.value);
        }
    }

}
