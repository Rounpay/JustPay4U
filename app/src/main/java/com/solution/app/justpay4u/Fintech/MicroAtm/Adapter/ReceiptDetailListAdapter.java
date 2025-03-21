package com.solution.app.justpay4u.Fintech.MicroAtm.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.ReceiptObject;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;

import java.util.List;

public class ReceiptDetailListAdapter extends RecyclerView.Adapter<ReceiptDetailListAdapter.MyViewHolder> {

    CustomAlertDialog mCustomAlertDialog;
    private List<ReceiptObject> operatorList;
    private Activity mContext;

    public ReceiptDetailListAdapter(List<ReceiptObject> operatorList, Activity mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        mCustomAlertDialog = new CustomAlertDialog(mContext);
    }

    @Override
    public ReceiptDetailListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_receipt_details, parent, false);

        return new ReceiptDetailListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ReceiptDetailListAdapter.MyViewHolder holder, int position) {
        final ReceiptObject operator = operatorList.get(position);
        holder.name.setText(operator.getName() + "");
        holder.value.setText(operator.getValue() + "");

        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.name.setBackgroundColor(mContext.getResources().getColor(R.color.devider_color));
            holder.value.setBackgroundColor(mContext.getResources().getColor(R.color.devider_color));
        } else {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.devider_color));
            holder.name.setBackgroundColor(Color.WHITE);
            holder.value.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        private AppCompatTextView name, value;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            name = view.findViewById(R.id.name);

            value = view.findViewById(R.id.value);
        }
    }
}



