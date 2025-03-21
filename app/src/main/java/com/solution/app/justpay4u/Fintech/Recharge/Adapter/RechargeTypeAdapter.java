package com.solution.app.justpay4u.Fintech.Recharge.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.PlanDataDetails;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.BrowsePlanScreenActivity;

import java.util.ArrayList;
import java.util.List;

public class RechargeTypeAdapter extends RecyclerView.Adapter<RechargeTypeAdapter.MyViewHolder> implements Filterable {

    private List<PlanDataDetails> filterListItem, listItem;
    private Context mContext;

    public RechargeTypeAdapter(List<PlanDataDetails> listItem, Context mContext) {
        this.filterListItem = listItem;
        this.listItem = listItem;
        this.mContext = mContext;
    }

    @Override
    public RechargeTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_recharge_plan, parent, false);

        return new RechargeTypeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RechargeTypeAdapter.MyViewHolder holder, final int position) {
        final PlanDataDetails operator = filterListItem.get(position);
        //     holder.talktime.setText("N/A");
        if (operator.getValidity() != null && operator.getValidity().length() > 0)
            holder.validity.setText(operator.getValidity());
        else
            holder.validity.setText("N/A");
        holder.amount.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getRs());
        holder.itemView.setOnClickListener(v ->
                ((BrowsePlanScreenActivity) mContext).ItemClick("" + operator.getRs(), "" + operator.getDesc()));
        if (operator.getDesc() != null && operator.getDesc().length() > 0)
            holder.description.setText(operator.getDesc().trim());
        else
            holder.description.setText("N/A");

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
                    ArrayList<PlanDataDetails> filteredList = new ArrayList<>();
                    for (PlanDataDetails row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getDesc().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getValidity().toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getRs() + "").toLowerCase().contains(charString.toLowerCase())) {
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
                filterListItem = (ArrayList<PlanDataDetails>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //public TextView talktime;
        public TextView validity;
        public TextView amount;
        public TextView description;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            //  talktime = view.findViewById(R.id.talktime);
            validity = view.findViewById(R.id.validity);
            amount = view.findViewById(R.id.amount);
            description = view.findViewById(R.id.description);

        }
    }

}
