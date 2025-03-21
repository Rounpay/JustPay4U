package com.solution.app.justpay4u.Fintech.Recharge.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Response.PlanRPResponse;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.DthPlanChannelListActivity;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.DthPlanInfoActivity;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.List;


public class DthPlanListRPAdapter extends RecyclerView.Adapter<DthPlanListRPAdapter.MyViewHolder> implements Filterable {

    String opId;
    boolean isPlanServiceUpdated;
    private List<PlanRPResponse> filterListItem, listItem;
    private Context mContext;

    public DthPlanListRPAdapter(boolean isPlanServiceUpdated, List<PlanRPResponse> listItem, Context mContext, String opId) {
        this.filterListItem = listItem;
        this.listItem = listItem;
        this.mContext = mContext;
        this.opId = opId;
        this.isPlanServiceUpdated = isPlanServiceUpdated;
    }

    @Override
    public DthPlanListRPAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dth_plan, parent, false);

        return new DthPlanListRPAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DthPlanListRPAdapter.MyViewHolder holder, final int position) {
        final PlanRPResponse operator = filterListItem.get(position);

        holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getRechargeAmount() + ""));
        holder.itemView.setOnClickListener(v ->
                ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getRechargeAmount(), "" + operator.getDetails()));
        holder.planName.setText(operator.getRechargeValidity());

        if (operator.getPackagelanguage() != null && !operator.getPackagelanguage().isEmpty()) {
            holder.languageLayout.setVisibility(View.VISIBLE);
            holder.language.setText(operator.getPackagelanguage() + "");
        } else {
            holder.languageLayout.setVisibility(View.GONE);
        }
        if (operator.getDetails() != null && operator.getDetails().length() > 0) {
            holder.description.setText(operator.getDetails());
        } else {
            holder.description.setText("N/A");
        }
        if (operator.getChannelcount() != 0) {
            holder.channelCount.setText(operator.getChannelcount() + "");
        } else {
            holder.channelCount.setText("View");
        }

        holder.countView.setOnClickListener(v ->
                mContext.startActivity(new Intent(mContext, DthPlanChannelListActivity.class)
                        .putExtra("Data", operator)
                        .putExtra("OperatorId", opId)
                        .putExtra("IsPlanServiceUpdated", isPlanServiceUpdated)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));
    }

    @Override
    public int getItemCount() {
        return filterListItem.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterListItem = listItem;
                } else {
                    ArrayList<PlanRPResponse> filteredList = new ArrayList<>();
                    for (PlanRPResponse row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getDetails() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getPackagelanguage() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getRechargeAmount() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getRechargeValidity() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getChannelcount() + "").contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filterListItem = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterListItem;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterListItem = (ArrayList<PlanRPResponse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView planName, channelLabel, channelCount;
        public TextView amount;
        public TextView description, language;
        View itemView, countView, languageLayout;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            planName = view.findViewById(R.id.planName);
            channelLabel = view.findViewById(R.id.channelLabel);
            amount = view.findViewById(R.id.amount);
            channelCount = view.findViewById(R.id.channelCount);
            countView = view.findViewById(R.id.countView);
            description = view.findViewById(R.id.description);
            language = view.findViewById(R.id.language);
            languageLayout = view.findViewById(R.id.languageLayout);

        }
    }

}
