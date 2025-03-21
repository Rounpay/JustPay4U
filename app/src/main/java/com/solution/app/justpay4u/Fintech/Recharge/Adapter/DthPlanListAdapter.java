package com.solution.app.justpay4u.Fintech.Recharge.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.PlanInfoPlan;
import com.solution.app.justpay4u.Api.Fintech.Object.PlanValidity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.DthPlanChannelListActivity;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.DthPlanInfoActivity;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.List;


public class DthPlanListAdapter extends RecyclerView.Adapter<DthPlanListAdapter.MyViewHolder> implements Filterable {

    String opId;
    boolean isPlanServiceUpdated;
    private List<PlanInfoPlan> filterListItem, listItem;
    private Context mContext;

    public DthPlanListAdapter(boolean isPlanServiceUpdated, String opId, List<PlanInfoPlan> listItem, Context mContext) {
        this.filterListItem = listItem;
        this.listItem = listItem;
        this.mContext = mContext;
        this.opId = opId;
        this.isPlanServiceUpdated = isPlanServiceUpdated;
    }

    @Override
    public DthPlanListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dth_plan_all, parent, false);

        return new DthPlanListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DthPlanListAdapter.MyViewHolder holder, final int position) {
        final PlanInfoPlan operator = filterListItem.get(position);
        List<PlanValidity> mPlanValidities = new ArrayList<>();

        if (operator.getRs() != null) {
            if (operator.getRs().get1MONTHS() != null && !operator.getRs().get1MONTHS().isEmpty()) {
                mPlanValidities.add(new PlanValidity(operator.getRs().get1MONTHS(), "1 Month",
                        operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + ""));
            }
            if (operator.getRs().get3MONTHS() != null && !operator.getRs().get3MONTHS().isEmpty()) {
                mPlanValidities.add(new PlanValidity(operator.getRs().get3MONTHS(), "3 Months",
                        operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + ""));
            }
            if (operator.getRs().get6MONTHS() != null && !operator.getRs().get6MONTHS().isEmpty()) {
                mPlanValidities.add(new PlanValidity(operator.getRs().get6MONTHS(), "6 Months",
                        operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + ""));
            }
            if (operator.getRs().get9MONTHS() != null && !operator.getRs().get9MONTHS().isEmpty()) {
                mPlanValidities.add(new PlanValidity(operator.getRs().get9MONTHS(), "9 Months",
                        operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + ""));
            }
            if (operator.getRs().get1YEAR() != null && !operator.getRs().get1YEAR().isEmpty()) {
                mPlanValidities.add(new PlanValidity(operator.getRs().get1YEAR(), "1 Year",
                        operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + ""));
            }
            if (operator.getRs().get5YEAR() != null && !operator.getRs().get5YEAR().isEmpty()) {
                mPlanValidities.add(new PlanValidity(operator.getRs().get5YEAR(), "5 Years",
                        operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + ""));
            }
        }

        if (mPlanValidities != null && mPlanValidities.size() > 0) {
            if (mPlanValidities.size() > 1) {
                holder.amountValidityView.setVisibility(View.GONE);
                holder.rsGrid.setVisibility(View.VISIBLE);
                if (mPlanValidities.size() <= 5) {
                    holder.rsGrid.setLayoutManager(new GridLayoutManager(mContext, mPlanValidities.size()));
                }
                DthPlanValidityGridAdapter mDthPlanValidityGridAdapter = new DthPlanValidityGridAdapter(R.layout.adapter_plan_validity, mContext, mPlanValidities);
                holder.rsGrid.setAdapter(mDthPlanValidityGridAdapter);
            } else {
                holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(mPlanValidities.get(0).getAmount() + ""));
                holder.validity.setText(mPlanValidities.get(0).getValidity() + "");
                holder.amountValidityView.setVisibility(View.VISIBLE);
                holder.rsGrid.setVisibility(View.GONE);
            }
        } else {
            holder.amountValidityView.setVisibility(View.GONE);
            holder.rsGrid.setVisibility(View.GONE);
        }
        holder.packageCountView.setOnClickListener(view -> {
            if (mContext instanceof DthPlanInfoActivity) {
                ((DthPlanInfoActivity) mContext).languageClick(operator);
            }
        });
        holder.channelCountView.setOnClickListener(view -> {
            mContext.startActivity(new Intent(mContext, DthPlanChannelListActivity.class)
                    .putExtra("DataAll", operator)
                    .putExtra("OperatorId", opId)
                    .putExtra("IsPlanServiceUpdated", isPlanServiceUpdated)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });

        holder.itemView.setOnClickListener(v -> {
            if (operator.getRs() != null && operator.getRs().get1MONTHS() != null && !operator.getRs().get1MONTHS().isEmpty()) {
                ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getRs().get1MONTHS(),
                        operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + "");
            } else if (operator.getRs() != null && operator.getRs().get3MONTHS() != null && !operator.getRs().get3MONTHS().isEmpty()) {
                ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getRs().get3MONTHS(),
                        operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + "");
            } else if (operator.getRs() != null && operator.getRs().get6MONTHS() != null && !operator.getRs().get6MONTHS().isEmpty()) {
                ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getRs().get6MONTHS(),
                        operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + "");
            } else if (operator.getRs() != null && operator.getRs().get9MONTHS() != null && !operator.getRs().get9MONTHS().isEmpty()) {
                ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getRs().get9MONTHS(),
                        operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + "");
            } else if (operator.getRs() != null && operator.getRs().get1YEAR() != null && !operator.getRs().get1YEAR().isEmpty()) {
                ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getRs().get1YEAR(),
                        operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + "");
            } else if (operator.getRs() != null && operator.getRs().get5YEAR() != null && !operator.getRs().get5YEAR().isEmpty()) {
                ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getRs().get5YEAR(),
                        operator.getDesc() != null && !operator.getDesc().isEmpty() ? operator.getDesc() : operator.getPlanName() + "");
            }

        });
        if (operator.getpChannelCount() != 0) {
            holder.channelCountView.setVisibility(View.VISIBLE);
            holder.channelCount.setText(operator.getpChannelCount() + "");
        } else {
            holder.channelCountView.setVisibility(View.GONE);
            holder.channelCount.setText("0");
        }
        if (operator.getpCount() != 0) {
            holder.packageCountView.setVisibility(View.VISIBLE);
            holder.packageCount.setText(operator.getpCount() + "");
        } else {
            holder.packageCountView.setVisibility(View.GONE);
            holder.packageCount.setText("0");
        }
        if (operator.getPlanName() != null && !operator.getPlanName().isEmpty()) {
            holder.planName.setVisibility(View.VISIBLE);
            holder.planName.setText(operator.getPlanName());
        } else {
            holder.planName.setVisibility(View.GONE);
            holder.planName.setText("N/A");
        }
        if (operator.getDesc() != null && !operator.getDesc().isEmpty()) {
            holder.descriptionLayout.setVisibility(View.VISIBLE);
            holder.description.setText(operator.getDesc());
        } else {
            holder.descriptionLayout.setVisibility(View.GONE);
            holder.description.setText("N/A");
        }
        if (operator.getpLangauge() != null && !operator.getpLangauge().isEmpty()) {
            holder.languageLayout.setVisibility(View.VISIBLE);
            holder.language.setText(operator.getpLangauge());
        } else {
            holder.languageLayout.setVisibility(View.GONE);
            holder.language.setText("N/A");
        }

    }

    @Override
    public int getItemCount() {
        return filterListItem != null ? filterListItem.size() : 0;
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
                    ArrayList<PlanInfoPlan> filteredList = new ArrayList<>();
                    for (PlanInfoPlan row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        String month1 = "", month3 = "", month6 = "", month9 = "", year1 = "", year5 = "";
                        if (row.getRs() != null) {
                            month1 = row.getRs().get1MONTHS() + "";
                            month3 = row.getRs().get3MONTHS() + "";
                            month6 = row.getRs().get6MONTHS() + "";
                            month9 = row.getRs().get9MONTHS() + "";
                            year1 = row.getRs().get1YEAR() + "";
                            year5 = row.getRs().get5YEAR() + "";
                        }
                        if ((row.getDesc() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getPlanName() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                month1.toLowerCase().contains(charString.toLowerCase()) ||
                                month3.toLowerCase().contains(charString.toLowerCase()) ||
                                month6.toLowerCase().contains(charString.toLowerCase()) ||
                                month9.toLowerCase().contains(charString.toLowerCase()) ||
                                year1.toLowerCase().contains(charString.toLowerCase()) ||
                                year5.contains(charString.toLowerCase())) {
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
                filterListItem = (ArrayList<PlanInfoPlan>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView validity, planName;
        public TextView amount, channelCount, packageCount;
        public TextView description, language;
        RecyclerView rsGrid;
        View itemView, amountValidityView, packageCountView, channelCountView, languageLayout, descriptionLayout;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            validity = view.findViewById(R.id.validity);
            planName = view.findViewById(R.id.planName);
            amount = view.findViewById(R.id.amount);
            channelCount = view.findViewById(R.id.channelCount);
            packageCount = view.findViewById(R.id.packageCount);
            channelCountView = view.findViewById(R.id.channelCountView);
            packageCountView = view.findViewById(R.id.packageCountView);
            language = view.findViewById(R.id.language);
            languageLayout = view.findViewById(R.id.languageLayout);
            descriptionLayout = view.findViewById(R.id.descriptionLayout);
            description = view.findViewById(R.id.description);
            amountValidityView = view.findViewById(R.id.amountValidityView);
            rsGrid = view.findViewById(R.id.rsGrid);
            rsGrid.setLayoutManager(new GridLayoutManager(mContext, 5));

        }
    }

}
