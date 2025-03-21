package com.solution.app.justpay4u.Networking.Adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Networking.Object.PoolTargetData;
import com.solution.app.justpay4u.Networking.Activity.PoolTeamNetworkingActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;

public class PoolTeamNetworkingAdapter extends RecyclerView.Adapter<PoolTeamNetworkingAdapter.MyViewHolder> implements Filterable {

    private ArrayList<PoolTargetData> mList = new ArrayList<>();
    private ArrayList<PoolTargetData> mFilterList = new ArrayList<>();
    private Context mContext;

    public PoolTeamNetworkingAdapter(ArrayList<PoolTargetData> transactionsList, Context mContext) {
        this.mContext = mContext;
        this.mFilterList = transactionsList;
        this.mList = transactionsList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_networking_pool_team, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final PoolTargetData operator = mFilterList.get(position);
        if (operator.getDisplayFields() != null && !operator.getDisplayFields().isEmpty()) {
            if (operator.getDisplayFields().toLowerCase().contains("poolid")) {
                holder.poolIdView.setVisibility(View.VISIBLE);
                holder.poolId.setText(operator.getPoolId() + "");
            } else {
                holder.poolIdView.setVisibility(View.GONE);
            }
            holder.infoStatus.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mContext instanceof PoolTeamNetworkingActivity) {
                        ((PoolTeamNetworkingActivity) mContext).infoStatusClick((Activity) mContext,operator);
                    }
                }
            });

            if (operator.getDisplayFields().toLowerCase().contains("poolname")) {
                holder.poolName.setText(operator.getPoolName() + "");
                holder.poolName.setVisibility(View.VISIBLE);
            } else {
                holder.poolName.setVisibility(View.GONE);
            }

            if (operator.getDisplayFields().toLowerCase().contains("poolmatrixview")) {
                holder.poolMatrix.setText(operator.getPoolName() + "");
                holder.poolMatrixView.setVisibility(View.VISIBLE);
            } else {
                holder.poolMatrixView.setVisibility(View.GONE);
            }
            if (operator.getDisplayFields().toLowerCase().contains("entrystatus")) {
                holder.targetStatus.setVisibility(View.VISIBLE);
                if (operator.getEntryStatus()) {
                    holder.targetStatus.setImageResource(R.drawable.right_icon);
                } else {
                    holder.targetStatus.setImageResource(R.drawable.ic_entry_status_cross);
                }
            } else {
                holder.targetStatus.setVisibility(View.GONE);
            }

            if (operator.getDisplayFields().toLowerCase().contains("entrydate")) {
                holder.entryDate.setText(Utility.INSTANCE.formatedDateWithT2(operator.getEntryDate()));
                holder.entryDateView.setVisibility(View.VISIBLE);
            } else {
                holder.entryDateView.setVisibility(View.GONE);
            }

            if (operator.getDisplayFields().toLowerCase().contains("requiredmember")
                    || operator.getDisplayFields().toLowerCase().contains("completedmember")
                    || operator.getDisplayFields().toLowerCase().contains("remainingmember")) {

                if (operator.getDisplayFields().toLowerCase().contains("requiredmember")) {
                    holder.memberView.setVisibility(View.VISIBLE);
                    holder.requiredMemberLabel.setVisibility(View.VISIBLE);
                    holder.requiredMember.setVisibility(View.VISIBLE);
                    holder.requiredMember.setText(operator.getRequiredMember() + "");
                } else {
                    holder.requiredMemberLabel.setVisibility(View.GONE);
                    holder.requiredMember.setVisibility(View.GONE);
                }
                if (operator.getDisplayFields().toLowerCase().contains("completedmember")) {
                    holder.memberView.setVisibility(View.VISIBLE);
                    holder.completedMemberLabel.setVisibility(View.VISIBLE);
                    holder.completedMember.setVisibility(View.VISIBLE);
                    holder.completedMember.setText(operator.getCompletedMember() + "");
                } else {
                    holder.completedMemberLabel.setVisibility(View.GONE);
                    holder.completedMember.setVisibility(View.GONE);
                }
                if (operator.getDisplayFields().toLowerCase().contains("remainingmember")) {
                    holder.memberView.setVisibility(View.VISIBLE);
                    holder.remainingMemberLabel.setVisibility(View.VISIBLE);
                    holder.remainingMember.setVisibility(View.VISIBLE);
                    holder.remainingMember.setText(operator.getRemainingMember() + "");
                } else {
                    holder.remainingMemberLabel.setVisibility(View.GONE);
                    holder.remainingMember.setVisibility(View.GONE);
                }
            } else {
                holder.memberView.setVisibility(View.GONE);
            }




        }

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
                    ArrayList<PoolTargetData> filteredList = new ArrayList<>();
                    for (PoolTargetData row : mList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getPoolName() + "").toLowerCase().contains(charString.toLowerCase()) || (row.getPoolId() + "").toLowerCase().contains(charString.toLowerCase())  || (row.getPoolMatrix() + "").toLowerCase().contains(charString.toLowerCase())) {
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
                mFilterList = (ArrayList<PoolTargetData>) filterResults.values;
                notifyDataSetChanged();


                if (mContext instanceof PoolTeamNetworkingActivity) {
                    if (mFilterList.size() == 0) {
                        ((PoolTeamNetworkingActivity) mContext).noDataView.setVisibility(View.VISIBLE);
                    } else {
                        ((PoolTeamNetworkingActivity) mContext).noDataView.setVisibility(View.GONE);
                    }

                }

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView poolName, poolId, levelNo, poolMatrix, status, entryDate,
                requiredMemberLabel, requiredMember ,
                completedMemberLabel,completedMember,
                remainingMemberLabel,remainingMember;

        public ImageView targetStatus,infoStatus;
        public ConstraintLayout memberView;
        public View poolIdView, poolMatrixView, entryDateView;

        public MyViewHolder(View view) {
            super(view);
            poolName = view.findViewById(R.id.poolName);
            poolId = view.findViewById(R.id.poolId);
            poolIdView = view.findViewById(R.id.poolIdView);
            poolMatrix = view.findViewById(R.id.poolMatrix);
            poolMatrixView = view.findViewById(R.id.poolMatrixView);
            entryDate = view.findViewById(R.id.entryDate);
            entryDateView = view.findViewById(R.id.entryDateView);
            targetStatus = view.findViewById(R.id.targetStatus);
            infoStatus = view.findViewById(R.id.infoStatus);
            memberView = view.findViewById(R.id.memberView);
            requiredMemberLabel = view.findViewById(R.id.requiredMemberLabel);
            requiredMember = view.findViewById(R.id.requiredMember);
            completedMemberLabel = view.findViewById(R.id.completedMemberLabel);
            completedMember = view.findViewById(R.id.completedMember);
            remainingMemberLabel = view.findViewById(R.id.remainingMemberLabel);
            remainingMember = view.findViewById(R.id.remainingMember);

        }
    }
}


