package com.solution.app.justpay4u.Fintech.Recharge.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.DthPlanLanguages;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.DthPlanInfoActivity;

import java.util.ArrayList;
import java.util.List;


public class DthPlanListLanguageAdapter extends RecyclerView.Adapter<DthPlanListLanguageAdapter.MyViewHolder> implements Filterable {

    private List<DthPlanLanguages> filterListItem, listItem;
    private Context mContext;

    public DthPlanListLanguageAdapter(List<DthPlanLanguages> listItem, Context mContext) {
        this.filterListItem = listItem;
        this.listItem = listItem;
        this.mContext = mContext;
    }

    @Override
    public DthPlanListLanguageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dth_plan_language, parent, false);

        return new DthPlanListLanguageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DthPlanListLanguageAdapter.MyViewHolder holder, final int position) {
        final DthPlanLanguages operator = filterListItem.get(position);

        holder.packageCount.setText(operator.getPackageCount() + "");
        holder.itemView.setOnClickListener(v -> {
            if (mContext instanceof DthPlanInfoActivity) {
                ((DthPlanInfoActivity) mContext).languageClick(operator);
            }
        });
        holder.language.setText(operator.getLanguage() + "");


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
                    ArrayList<DthPlanLanguages> filteredList = new ArrayList<>();
                    for (DthPlanLanguages row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getLanguage() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getPackageCount() + "").contains(charString.toLowerCase())) {
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
                filterListItem = (ArrayList<DthPlanLanguages>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView language;
        public TextView packageCount;

        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            language = view.findViewById(R.id.language);
            packageCount = view.findViewById(R.id.packageCount);

        }
    }

}
