package com.solution.app.justpay4u.Networking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Networking.Object.MemberListData;
import com.solution.app.justpay4u.Networking.Activity.TotalTeamNetworkingActivity;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

public class TotalTeamNetworkingAdapter extends RecyclerView.Adapter<TotalTeamNetworkingAdapter.MyViewHolder> implements Filterable {

    private ArrayList<MemberListData> mList = new ArrayList<>();
    private ArrayList<MemberListData> mFilterList = new ArrayList<>();
    private Context mContext;
    private int balanceCheckResponse;

    public TotalTeamNetworkingAdapter(int balanceCheckResponse,ArrayList<MemberListData> transactionsList, Context mContext) {
        this.balanceCheckResponse = balanceCheckResponse;
        this.mContext = mContext;
        this.mFilterList = transactionsList;
        this.mList = transactionsList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_networking_total_team, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MemberListData operator = mFilterList.get(position);
        holder.name.setText(operator.getName() + "");
        holder.userId.setText(operator.getUserID() + "");
        holder.sponserId.setText(operator.getReferalID() + "");
        holder.sponserName.setText(operator.getSponserName() + "");
        holder.parentName.setText(operator.getParentName() + "");
        if (balanceCheckResponse ==2) {
            holder.levelView.setVisibility(View.VISIBLE);
            holder.levelNo.setText(operator.getPosition() + "");
        } else {
            holder.levelView.setVisibility(View.GONE);

        }
        holder.businessType.setText(operator.getIntroducedBy() + "");
        //holder.status.setText(operator.getStatus() + "");
        //holder.regDate.setText(Utility.INSTANCE.formatedDateWithT2(operator.getRegDate()));
        /*holder.date.setText(Utility.INSTANCE.formatedDateWithT2(operator.getBusinessDate()));*/
        //holder.packageCost.setText("Buisness\n" + Utility.INSTANCE.formatedAmountWithOutRupees(operator.getPackageCost() + ""));
        /*if (operator.getStatus().equalsIgnoreCase("Active")) {
            holder.status.setBackgroundResource(R.drawable.rounded_light_green);
        } else {
            holder.status.setBackgroundResource(R.drawable.rounded_light_red);
        }*/
    }

    @Override
    public int getItemCount() {
        return mFilterList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilterList = mList;
                } else {
                    ArrayList<MemberListData> filteredList = new ArrayList<>();
                    for (MemberListData row : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getName() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getUserID() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getSponserId() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getSponserName() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getReferalID() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getIntroducedBy() + "").toLowerCase().contains(charString.toLowerCase())||
                                (row.getParentName() + "").toLowerCase().contains(charString.toLowerCase())||
                                (row.getPosition() + "").toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mFilterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilterList = (ArrayList<MemberListData>) filterResults.values;
                notifyDataSetChanged();


                if (mContext instanceof TotalTeamNetworkingActivity) {
                    if (mFilterList.size() == 0) {
                        ((TotalTeamNetworkingActivity) mContext).noDataView.setVisibility(View.VISIBLE);
                    } else {
                        ((TotalTeamNetworkingActivity) mContext).noDataView.setVisibility(View.GONE);
                    }

                }

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout levelView;
        public AppCompatTextView name, userId, levelNo, date, sponserId, sponserName, parentName, status, packageCost, regDate, activateDate, businessType;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            userId = view.findViewById(R.id.userId);
            sponserId = view.findViewById(R.id.sponserId);
            sponserName = view.findViewById(R.id.sponserName);
            parentName = view.findViewById(R.id.parentName);
            levelNo = view.findViewById(R.id.levelNo);
            levelView = view.findViewById(R.id.levelView);
            regDate = view.findViewById(R.id.regDate);
            activateDate = view.findViewById(R.id.activateDate);
            date = view.findViewById(R.id.date);
            status = view.findViewById(R.id.status);
            packageCost = view.findViewById(R.id.packageCost);
            businessType = view.findViewById(R.id.businessType);

        }
    }
}
