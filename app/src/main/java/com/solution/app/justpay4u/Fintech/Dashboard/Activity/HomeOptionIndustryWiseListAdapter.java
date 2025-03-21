package com.solution.app.justpay4u.Fintech.Dashboard.Activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.ApiHits.OpTypeDataIndustryWise;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;

import java.util.List;

public class HomeOptionIndustryWiseListAdapter extends RecyclerView.Adapter<HomeOptionIndustryWiseListAdapter.MyViewHolder> {

    ClickIndustryTypeOption mClickView;

    private List<OpTypeDataIndustryWise> operatorList;
    private Activity mContext;
    CustomAlertDialog mCustomAlertDialog;


    public HomeOptionIndustryWiseListAdapter(List<OpTypeDataIndustryWise> operatorList, Activity mContext, ClickIndustryTypeOption mClickView) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.mClickView = mClickView;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dashboard_industrywise_options, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final OpTypeDataIndustryWise item = operatorList.get(position);


        holder.title.setText(item.getIndustryType() + "");
        holder.recyclerView.setAdapter(new HomeOptionIndustryWiseOptionListAdapter(item.getOpTypes(),mContext,mClickView));

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public RecyclerView recyclerView;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            title = view.findViewById(R.id.title);
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));


        }
    }
}
