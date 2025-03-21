package com.solution.app.justpay4u.Fintech.Dashboard.Activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.ApiHits.AssignedOpTypeIndustryWise;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.ServiceIcon;

import java.util.List;

public class HomeOptionIndustryWiseOptionListAdapter extends RecyclerView.Adapter<HomeOptionIndustryWiseOptionListAdapter.MyViewHolder> {

    ClickIndustryTypeOption mClickView;
    private List<AssignedOpTypeIndustryWise> operatorList;
    private Activity mContext;


    public HomeOptionIndustryWiseOptionListAdapter(List<AssignedOpTypeIndustryWise> operatorList, Activity mContext, ClickIndustryTypeOption mClickView) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.mClickView = mClickView;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dashboard_service_option_grid, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AssignedOpTypeIndustryWise item = operatorList.get(position);


//        holder.name.setText(item.getOpType().replaceAll("\n", " "));
        holder.icon.setImageResource(ServiceIcon.INSTANCE.serviceIcon(item.getId(), false));
        holder.name.setText(item.getOpType());


        holder.itemView.setOnClickListener(v -> {

            if (mClickView != null) {

                mClickView.onClick(item);
            }


        });

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, comingsoonTv;
        public ImageView icon;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            name = view.findViewById(R.id.name);
            comingsoonTv = view.findViewById(R.id.comingsoonTv);
            icon = view.findViewById(R.id.icon);
            comingsoonTv.setVisibility(View.GONE);


        }
    }
}
