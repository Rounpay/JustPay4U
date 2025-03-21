package com.solution.app.justpay4u.Networking.Adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Networking.Activity.DashboardTeamDetailsList;
import com.solution.app.justpay4u.Networking.Activity.NetworkingDashboardActivity;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

public class PoolNetworkingAdapter extends RecyclerView.Adapter<PoolNetworkingAdapter.MyViewHolder> {

    private ArrayList<DashboardTeamDetailsList> mPoolList = new ArrayList<>();
    private Activity mContext;

    public PoolNetworkingAdapter(ArrayList<DashboardTeamDetailsList> mPoolList, Activity mContext) {
        this.mPoolList = mPoolList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PoolNetworkingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_networking_pool, parent, false);
        return new PoolNetworkingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PoolNetworkingAdapter.MyViewHolder holder, int position) {
        final DashboardTeamDetailsList item = mPoolList.get(position);
        if (item.getDisplayName() != null){
            holder.displayName.setText(item.getDisplayName());
        }
        else {
            holder.displayName.setText("N/A");
        }
        holder.headerName1.setText(item.getHeaderName1());
        holder.headerValue1.setText(item.getHeaderValue1() + "");
        holder.headerName2.setText(item.getHeaderName2());
        holder.headerValue2.setText(item.getHeaderValue2() + "");
        holder.headerName3.setText(item.getHeaderName3());
        holder.headerValue3.setText(item.getHeaderValue3() + "");


        if (item.getHeaderValue4() == 1)
            holder.targetStatus.setImageResource(R.drawable.active_user);
        else
            holder.targetStatus.setImageResource(R.drawable.deactive_user);
        if (item.getUrl()!=null && !item.getUrl().isEmpty()) {
            if(item.getUrl().equalsIgnoreCase("InternalTree")){
                holder.treeReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mContext instanceof NetworkingDashboardActivity){
                            ((NetworkingDashboardActivity)mContext).poolItemClick(item);
                        }
                    }
                });
            } else {
                holder.treeReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mContext instanceof NetworkingDashboardActivity){
                            ((NetworkingDashboardActivity)mContext).poolItemGenelogyClick(item);
                        }
                    }
                });
            }
        }

        holder.viewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mContext instanceof NetworkingDashboardActivity){
                    ((NetworkingDashboardActivity)mContext).poolReportItemClick(item);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPoolList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView headerName1,headerValue1,headerValue2,headerValue3,headerName2,headerName3, viewReport,treeReport;
        private final ImageView targetStatus;
        AppCompatTextView displayName;
        View itemPoolView;


        public MyViewHolder(View view) {
            super(view);
            headerName1 = view.findViewById(R.id.headerName1);
            headerValue1 = view.findViewById(R.id.headerValue1);
            headerName2 = view.findViewById(R.id.headerName2);
            headerValue2 = view.findViewById(R.id.headerValue2);
            headerName3 = view.findViewById(R.id.headerName3);
            headerValue3 = view.findViewById(R.id.headerValue3);
            viewReport = view.findViewById(R.id.viewReport);
            treeReport = view.findViewById(R.id.treeReport);
            targetStatus = view.findViewById(R.id.targetStatus);
            itemPoolView = view.findViewById(R.id.itemPoolView);
            displayName = view.findViewById(R.id.displayName);

        }
    }
}

