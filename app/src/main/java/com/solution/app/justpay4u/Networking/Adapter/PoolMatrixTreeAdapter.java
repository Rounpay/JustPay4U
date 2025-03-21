package com.solution.app.justpay4u.Networking.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Networking.Object.PoolTargetData;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;


public class PoolMatrixTreeAdapter extends RecyclerView.Adapter<PoolMatrixTreeAdapter.MyViewHolder> {

    private ArrayList<PoolTargetData> mList = new ArrayList<>();
    private ArrayList<PoolTargetData> mFilterList = new ArrayList<>();
    private Context mContext;

    public PoolMatrixTreeAdapter(ArrayList<PoolTargetData> transactionsList, Context mContext) {
        this.mContext = mContext;
        this.mFilterList = transactionsList;
        this.mList = transactionsList;

    }

    @Override
    public PoolMatrixTreeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_networking_pool_matrix_tree, parent, false);

        return new PoolMatrixTreeAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final PoolMatrixTreeAdapter.MyViewHolder holder, int position) {
        final PoolTargetData operator = mFilterList.get(position);
        if (operator.getDisplayFields() != null && !operator.getDisplayFields().isEmpty()) {

            if (operator.getDisplayFields().toLowerCase().contains("poolname")) {
                holder.poolName.setText(operator.getPoolName() + "");
                holder.poolName.setVisibility(View.VISIBLE);
            } else {
                holder.poolName.setVisibility(View.GONE);
            }
            if (operator.getDisplayFields().toLowerCase().contains("entrystatus")) {
                holder.targetStatus.setVisibility(View.VISIBLE);
                if (operator.getEntryStatus()) {
                    holder.targetStatus.setImageResource(R.drawable.true_user);
                } else {
                    holder.targetStatus.setImageResource(R.drawable.false_user);
                }
            } else {
                holder.targetStatus.setVisibility(View.GONE);
            }


            if (operator.getDisplayFields().toLowerCase().contains("requiredmember")
                    || operator.getDisplayFields().toLowerCase().contains("completedmember")
            ) {

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


                int completedMember = operator.getCompletedMember();

                // Change color of downline ImageViews based on Complete member count
                ImageView[] downlineImageViews = {
                        holder.downlineStatus1,
                        holder.downlineStatus2,
                        holder.downlineStatus3,
                        holder.downlineStatus4,
                        holder.downlineStatus5,
                        holder.downlineStatus6
                };
                for (int i = 0; i < downlineImageViews.length; i++) {
                    if (i < completedMember) {
                        downlineImageViews[i].setImageResource(R.drawable.true_user);
                    } else {  // Otherwise, it's not completed
                        downlineImageViews[i].setImageResource(R.drawable.false_user);
                    }
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


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView poolName,
                requiredMemberLabel, requiredMember ,
                completedMemberLabel,completedMember;

        public ImageView targetStatus;
        public ImageView downlineStatus1,downlineStatus2 , downlineStatus3 , downlineStatus4, downlineStatus5,downlineStatus6;
        public View entryDateView;
        public RelativeLayout memberView;

        public MyViewHolder(View view) {
            super(view);
            poolName = view.findViewById(R.id.poolName);
            targetStatus = view.findViewById(R.id.targetStatus);
            downlineStatus1 = view.findViewById(R.id.downlineStatus1);
            downlineStatus2 = view.findViewById(R.id.downlineStatus2);
            downlineStatus3 = view.findViewById(R.id.downlineStatus3);
            downlineStatus4 = view.findViewById(R.id.downlineStatus4);
            downlineStatus5 = view.findViewById(R.id.downlineStatus5);
            downlineStatus6 = view.findViewById(R.id.downlineStatus6);
            memberView = view.findViewById(R.id.memberView);
            requiredMemberLabel = view.findViewById(R.id.requiredMemberLabel);
            requiredMember = view.findViewById(R.id.requiredMember);
            completedMemberLabel = view.findViewById(R.id.achievedTitle);
            completedMember = view.findViewById(R.id.totalAchieved);


        }
    }
}
