package com.solution.app.justpay4u.Networking.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Networking.Object.EPinList;
import com.solution.app.justpay4u.Networking.Activity.EPinListNetworkingActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class EPinListNetworkingAdapter extends RecyclerView.Adapter<EPinListNetworkingAdapter.MyViewHolder> {


    private ArrayList<EPinList> rechargeStatus;
    private ArrayList<EPinList> transactionsList;
    private Activity mContext;


    public EPinListNetworkingAdapter(ArrayList<EPinList> transactionsList, Activity mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.rechargeStatus = transactionsList;

    }

    @Override
    public EPinListNetworkingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_networking_epin, parent, false);

        return new EPinListNetworkingAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final EPinListNetworkingAdapter.MyViewHolder holder, int position) {
        final EPinList item = transactionsList.get(position);
        holder.epin.setText("" + item.getePin());
        holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(item.getAmount() + ""));
        holder.date.setText(Utility.INSTANCE.formatedDate3(item.getePinGeneratedDate() + ""));


        if (item.getProjectDescription() != null && !item.getProjectDescription().isEmpty()) {
            holder.plotNameView.setVisibility(View.VISIBLE);
            holder.plotName.setText(item.getProjectDescription() + "");

        } else {
            holder.plotNameView.setVisibility(View.GONE);
        }

        if (item.getMobileNo() != null && !item.getMobileNo().isEmpty()) {
            holder.mobileNoView.setVisibility(View.VISIBLE);
            holder.mobileNo.setText(item.getMobileNo() + "");
        } else {
            holder.mobileNoView.setVisibility(View.GONE);
        }
        if (item.getAssociateName() != null && !item.getAssociateName().isEmpty()) {
            holder.associateNameView.setVisibility(View.VISIBLE);
            holder.associateName.setText(item.getAssociateName() + "");
        } else {
            holder.associateNameView.setVisibility(View.GONE);
        }
        if (item.getConsumerName() != null && !item.getConsumerName().isEmpty()) {
            holder.consumerNameView.setVisibility(View.VISIBLE);
            holder.consumerName.setText(item.getConsumerName() + " [" + item.getConsumedUserId() + "]");
        } else {
            holder.consumerNameView.setVisibility(View.GONE);
        }
        if (item.getConsumerMobile() != null && !item.getConsumerMobile().isEmpty()) {
            holder.consumerMobileView.setVisibility(View.VISIBLE);
            holder.consumerMobile.setText(item.getConsumerMobile() + "");
        } else {
            holder.consumerMobileView.setVisibility(View.GONE);
        }
        if (item.isUsed()) {
            holder.useBtn.setVisibility(View.GONE);
            holder.consumedStatus.setText("Used");
            holder.consumedStatus.setTextColor(mContext.getResources().getColor(R.color.color_red));
        } else {
            holder.useBtn.setVisibility(View.VISIBLE);
            holder.consumedStatus.setText("Unused");
            holder.consumedStatus.setTextColor(mContext.getResources().getColor(R.color.green));
        }

        if (item.isActive()) {
            holder.status.setText("Active");
            holder.statusIcon.setImageResource(R.drawable.ic_success);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.green));

        } else {
            holder.status.setText("Deactive");
            holder.statusIcon.setImageResource(R.drawable.ic_failed);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_red));

        }


        holder.transferBtn.setOnClickListener(v -> {

        });
        holder.useBtn.setOnClickListener(v -> {

            if (mContext instanceof EPinListNetworkingActivity) {
                ((EPinListNetworkingActivity) mContext).ActivateUser(item);
            }

        });


    }


    @Override
    public int getItemCount() {
        return transactionsList.size();
    }


    // Filter Class
    public void filter(String charText) {
        String txt = charText.toLowerCase(Locale.getDefault());

        ArrayList<EPinList> filterList = new ArrayList<>();
        if (txt.length() == 0) {
            filterList.addAll(rechargeStatus);
        } else {
            for (EPinList wp : rechargeStatus) {
                if ((wp.getMobileNo() + "").toLowerCase(Locale.getDefault()).contains(txt) ||
                        (wp.getePin() + "").toLowerCase(Locale.getDefault()).contains(txt) ||
                        (wp.getAmount() + "").toLowerCase(Locale.getDefault()).contains(txt) ||
                        (wp.getConsumerMobile() + "").toLowerCase(Locale.getDefault()).contains(txt) ||
                        (wp.getConsumerName() + "").toLowerCase(Locale.getDefault()).contains(txt) ||
                        (wp.getConsumedUserId() + "").toLowerCase(Locale.getDefault()).contains(txt) ||
                        (wp.getProjectDescription() + "").toLowerCase(Locale.getDefault()).contains(txt) ||
                        (wp.getAssociateNm() + "").toLowerCase(Locale.getDefault()).contains(txt) ||
                        (wp.getAssociateName() + "").toLowerCase(Locale.getDefault()).contains(txt)) {
                    filterList.add(wp);
                }
            }
        }

        transactionsList = filterList;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView epin;
        private AppCompatTextView amount;

        private LinearLayout consumerNameView, consumerMobileView, mobileNoView, associateNameView;
        private AppCompatTextView consumedStatus, consumerName, consumerMobile, mobileNo, associateName;
        private LinearLayout plotNameView;
        private AppCompatTextView plotName;
        private AppCompatTextView date;
        private AppCompatImageView statusIcon;
        private AppCompatTextView status;
        private LinearLayout btnView;
        private LinearLayout useBtn;
        private LinearLayout transferBtn;


        public MyViewHolder(View view) {
            super(view);


            epin = view.findViewById(R.id.epin);
            amount = view.findViewById(R.id.amount);
            consumedStatus = view.findViewById(R.id.consumedStatus);
            mobileNoView = view.findViewById(R.id.mobileNoView);
            associateNameView = view.findViewById(R.id.associateNameView);
            consumerNameView = view.findViewById(R.id.consumerNameView);
            consumerMobileView = view.findViewById(R.id.consumerMobileView);
            mobileNo = view.findViewById(R.id.mobileNo);
            associateName = view.findViewById(R.id.associateName);
            consumerName = view.findViewById(R.id.consumerName);
            consumerMobile = view.findViewById(R.id.consumerMobile);
            plotNameView = view.findViewById(R.id.plotNameView);
            plotName = view.findViewById(R.id.plotName);
            date = view.findViewById(R.id.date);
            statusIcon = view.findViewById(R.id.statusIcon);
            status = view.findViewById(R.id.status);
            btnView = view.findViewById(R.id.btnView);
            useBtn = view.findViewById(R.id.useBtn);
            transferBtn = view.findViewById(R.id.transfer);
        }
    }
}