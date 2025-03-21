package com.solution.app.justpay4u.Fintech.Recharge.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.PlanValidity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.DthPlanInfoActivity;
import com.solution.app.justpay4u.Util.Utility;

import java.util.List;


public class DthPlanValidityGridAdapter extends RecyclerView.Adapter<DthPlanValidityGridAdapter.MyViewHolder> {

    int layout;
    private List<PlanValidity> operatorList;
    private Context mContext;

    public DthPlanValidityGridAdapter(int layout, Context mContext, List<PlanValidity> operatorList) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.layout = layout;
    }

    @Override
    public DthPlanValidityGridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new DthPlanValidityGridAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DthPlanValidityGridAdapter.MyViewHolder holder, final int position) {
        final PlanValidity operator = operatorList.get(position);

        holder.amountTv.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getAmount()));
        holder.validityTv.setText("" + operator.getValidity());
        holder.itemView.setOnClickListener(v -> {
            if (mContext instanceof DthPlanInfoActivity) {
                ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getAmount(), "" + operator.getDetails());
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView validityTv;
        public TextView amountTv;
        public TextView description;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            validityTv = view.findViewById(R.id.validityTv);
            amountTv = view.findViewById(R.id.amountTv);

        }
    }

}
