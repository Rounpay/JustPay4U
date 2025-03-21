package com.solution.app.justpay4u.Networking.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.ApiHits.DataPoolCount;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

public class UplinePoolCountAdapter extends RecyclerView.Adapter<UplinePoolCountAdapter.MyViewHolder> {
    private ArrayList<DataPoolCount> mList = new ArrayList<>();
    private Context mContext;
    int selectedIndex = -1;
    String filterStaus;

    public UplinePoolCountAdapter(ArrayList<DataPoolCount> mList,String filterStaus ,Context mContext) {
        this.mList = mList;
        this.filterStaus = filterStaus;
        this.mContext = mContext;
    }


    @Override
    public UplinePoolCountAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_networking_generation_team, parent, false);

        return new UplinePoolCountAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DataPoolCount operator = mList.get(position);
//        if (filterStaus.equalsIgnoreCase("Deactive")){
//            ViewCompat.setBackgroundTintList(holder.itemView, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.colorPrimaryMoreMoreLight)));
//        } else {
//            ViewCompat.setBackgroundTintList(holder.itemView, ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.colorPrimary)));
//        }
        if (filterStaus.contains("All")) {
            holder.activeCount.setVisibility(View.VISIBLE);
            holder.deActiveCount.setVisibility(View.VISIBLE);
            holder.activeCount.setText("Active Team\n \n " + operator.getActiveCount() + "");
            holder.deActiveCount.setText("Deactive Team\n \n " + operator.getDeActiveCount() + "");
        } else if (filterStaus.contains("Active")) {
            holder.activeCount.setVisibility(View.VISIBLE);
            holder.activeCount.setText("Active Team\n \n " + operator.getActiveCount() + "");
            holder.deActiveCount.setVisibility(View.GONE);
        } else if (filterStaus.contains("Deactive")) {
            holder.deActiveCount.setVisibility(View.VISIBLE);
            holder.deActiveCount.setText("Deactive Team\n \n " + operator.getDeActiveCount() + "");
            holder.activeCount.setVisibility(View.GONE);
        }
        holder.levelNo.setText(operator.getLevel() + "");
        if (filterStaus.contains("Active") && operator.getActiveCount() > 0 ||
                filterStaus.contains("Deactive") && operator.getDeActiveCount() > 0 ||
                filterStaus.contains("All") && (operator.getActiveCount() > 0 || operator.getDeActiveCount() > 0)
        ) {
            holder.next.setVisibility(View.VISIBLE);
        } else {
            holder.next.setVisibility(View.GONE);
        }
        holder.activeCount.setOnClickListener(v -> {
            if (filterStaus.contains("Active") && operator.getActiveCount() > 0 ||
                    filterStaus.contains("Deactive") && operator.getDeActiveCount() > 0 ||
                    filterStaus.contains("All") && (operator.getActiveCount() > 0 || operator.getDeActiveCount() > 0)
            ) {
                if (mContext instanceof UplinePoolCountTeamNetworkingActivity) {
                    ((UplinePoolCountTeamNetworkingActivity) mContext).ItemClick(operator,"Active");
                    selectedIndex = position;
                    notifyDataSetChanged();
                }
            }

        });
        holder.deActiveCount.setOnClickListener(v -> {
            if (filterStaus.contains("Active") && operator.getActiveCount() > 0 ||
                    filterStaus.contains("Deactive") && operator.getDeActiveCount() > 0 ||
                    filterStaus.contains("All") && (operator.getActiveCount() > 0 || operator.getDeActiveCount() > 0)
            ) {
                if (mContext instanceof UplinePoolCountTeamNetworkingActivity) {
                    ((UplinePoolCountTeamNetworkingActivity) mContext).ItemClick(operator,"Deactive");
                    selectedIndex = position;
                    notifyDataSetChanged();
                }
            }

        });
        holder.itemView.setOnClickListener(v -> {
            if (filterStaus.contains("Active") && operator.getActiveCount() > 0 ||
                    filterStaus.contains("Deactive") && operator.getDeActiveCount() > 0 ||
                    filterStaus.contains("All") && (operator.getActiveCount() > 0 || operator.getDeActiveCount() > 0)
            ) {
                if (mContext instanceof UplinePoolCountTeamNetworkingActivity) {
                    ((UplinePoolCountTeamNetworkingActivity) mContext).ItemClick(operator,"All");
                    selectedIndex = position;
                    notifyDataSetChanged();
                }
            }

        });



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView userId, levelNo, activeCount, deActiveCount, next;


        public MyViewHolder(View view) {
            super(view);
            levelNo = view.findViewById(R.id.levelNo);
            activeCount = view.findViewById(R.id.activeCount);
            deActiveCount = view.findViewById(R.id.deActiveCount);
            next = view.findViewById(R.id.next);

        }
    }
}
