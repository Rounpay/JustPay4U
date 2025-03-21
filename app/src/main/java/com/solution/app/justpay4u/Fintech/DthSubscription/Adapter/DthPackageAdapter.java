package com.solution.app.justpay4u.Fintech.DthSubscription.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.DthPackage;
import com.solution.app.justpay4u.Fintech.DthSubscription.Activity.DthPackageActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.List;


public class DthPackageAdapter extends RecyclerView.Adapter<DthPackageAdapter.MyViewHolder> {

    private List<DthPackage> operatorList;
    private Context mContext;

    public DthPackageAdapter(List<DthPackage> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public DthPackageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dth_package, parent, false);

        return new DthPackageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DthPackageAdapter.MyViewHolder holder, final int position) {
        final DthPackage operator = operatorList.get(position);
        holder.description.setText(operator.getDescription() + "");
        holder.name.setText(operator.getPackageName() + "");
        holder.validity.setText(operator.getValidity() + " Days");
        holder.bookingAmt.setText("Booking Amount : " + Utility.INSTANCE.formatedAmountWithRupees(operator.getBookingAmount() + ""));

        holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getPackageMRP() + ""));
        holder.viewChannel.setText("View Channels\n(" + operator.getChannelCount() + " Channels)");
        holder.viewChannel.setOnClickListener(v -> ((DthPackageActivity) mContext).ViewChannel(operator));
        holder.itemView.setOnClickListener(v -> ((DthPackageActivity) mContext).SelectedPackage(operator));
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView validity;
        public TextView amount, name, viewChannel;
        public TextView description, bookingAmt;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            amount = view.findViewById(R.id.amount);
            description = view.findViewById(R.id.description);
            validity = view.findViewById(R.id.validity);
            name = view.findViewById(R.id.name);
            bookingAmt = view.findViewById(R.id.bookingAmt);
            viewChannel = view.findViewById(R.id.viewChannel);


        }
    }

}
