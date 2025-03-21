package com.solution.app.justpay4u.Fintech.Recharge.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.BillAdditionalInfo;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.List;


public class BillDetailAdapter extends RecyclerView.Adapter<BillDetailAdapter.MyViewHolder> {

    int layout;
    private List<BillAdditionalInfo> listItem;
    private Context mContext;

    public BillDetailAdapter(List<BillAdditionalInfo> listItem, Context mContext, int layout) {
        this.listItem = listItem;
        this.mContext = mContext;
        this.layout = layout;
    }

    @Override
    public com.solution.app.justpay4u.Fintech.Recharge.Adapter.BillDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new com.solution.app.justpay4u.Fintech.Recharge.Adapter.BillDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final com.solution.app.justpay4u.Fintech.Recharge.Adapter.BillDetailAdapter.MyViewHolder holder, final int position) {
        final BillAdditionalInfo operator = listItem.get(position);

        if (operator.getInfoName() != null && operator.getInfoValue() != null) {
            holder.name.setText(operator.getInfoName() + "");

            /*if (operator.getInfoValue().contains("-") && operator.getInfoValue().length() > 6) {
                holder.value.setText(Utility.INSTANCE.formatedDateOfDash(operator.getInfoValue() + ""));
            } else {*/
            holder.value.setText(operator.getInfoValue() + "");
            /* }*/
        } else {
            holder.name.setText(operator.getAmountName() + "");
            holder.value.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getAmountValue() + ""));
        }

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView value;

        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            name = view.findViewById(R.id.name);
            value = view.findViewById(R.id.value);


        }
    }

}
