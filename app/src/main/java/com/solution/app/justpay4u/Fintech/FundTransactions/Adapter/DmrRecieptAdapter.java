package com.solution.app.justpay4u.Fintech.FundTransactions.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.ListOblect;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

/**
 * Created by Administrator on 29-12-2017.
 */

public class DmrRecieptAdapter extends RecyclerView.Adapter<DmrRecieptAdapter.MyViewHolder> {


    private java.util.List<ListOblect> operatorList;
    private Context mContext;

    public DmrRecieptAdapter(java.util.List<ListOblect> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public DmrRecieptAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dmr_receipt_list, parent, false);


        return new DmrRecieptAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DmrRecieptAdapter.MyViewHolder holder, final int position) {
        final ListOblect operator = operatorList.get(position);

        holder.desc.setText(operator.getTransactionID() + "");
        holder.liveId.setText(operator.getLiveID() + "");
        int StatusValue = operator.getStatuscode();
        if (StatusValue == 2) {
            holder.status.setImageResource(R.drawable.ic_check_circle_black_24dp);
        } else if (StatusValue == 3) {
            holder.status.setImageResource(R.drawable.ic_cancel_black_24dp);
        } else if (StatusValue == 4) {
            holder.status.setImageResource(R.drawable.ic_status_refund);
        } else {
            holder.status.setImageResource(R.drawable.ic_pending_black_24dp);
        }
        holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getRequestedAmount() + ""));

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView desc, liveId;
        public ImageView status;
        public TextView amount;

        public RelativeLayout transferLayout;
        public RelativeLayout deleteLayout;

        public MyViewHolder(View view) {
            super(view);

            desc = view.findViewById(R.id.desc);
            status = view.findViewById(R.id.status);
            amount = view.findViewById(R.id.amount);
            liveId = view.findViewById(R.id.liveId);
        }
    }


}
