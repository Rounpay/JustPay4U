package com.solution.app.justpay4u.Networking.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.ApiHits.RankList;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

public class RankListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<RankList> mRankList;
    private Activity mContext;

    public RankListAdapter(Activity mContext, ArrayList<RankList> mRankList) {
        this.mContext = mContext;
        this.mRankList = mRankList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_networking_rank_list_new, parent, false);
        return new MyViewHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final RankList item = mRankList.get(position);
        if (item.getDisplayFields().toLowerCase().contains("rankid")) {
            ((MyViewHolder) viewHolder).rankidView.setVisibility(View.VISIBLE);
            ((MyViewHolder) viewHolder).rankid.setText(String.valueOf(item.getRankid()));
        } else {
            ((MyViewHolder) viewHolder).rankidView.setVisibility(View.GONE);
        }

        if (item.getDisplayFields().toLowerCase().contains("rankname")) {
            ((MyViewHolder) viewHolder).rankname.setVisibility(View.VISIBLE);
            ((MyViewHolder) viewHolder).rankname.setText(item.getRankname());
        } else {
            ((MyViewHolder) viewHolder).rankname.setVisibility(View.GONE);
        }

        if (item.getDisplayFields().toLowerCase().contains("sponserbusiness")) {
            ((MyViewHolder) viewHolder).sponserBusinessView.setVisibility(View.VISIBLE);
            ((MyViewHolder) viewHolder).sponserBusiness.setText(item.getSponserBusiness().toString());
        } else {
            ((MyViewHolder) viewHolder).sponserBusinessView.setVisibility(View.GONE);
        }
        if (item.getDisplayFields().toLowerCase().contains("sponserbusiness")) {
            ((MyViewHolder) viewHolder).sponserBusinessView.setVisibility(View.VISIBLE);
            ((MyViewHolder) viewHolder).sponserBusiness.setText(item.getSponserBusiness().toString());
        } else {
            ((MyViewHolder) viewHolder).sponserBusinessView.setVisibility(View.GONE);
        }
        if (item.getDisplayFields().toLowerCase().contains("directcount")) {
            ((MyViewHolder) viewHolder).directCountVIew.setVisibility(View.VISIBLE);
            if (item.getDirectCount() != 0 && item.getDirectCount() > 0) {
                ((MyViewHolder) viewHolder).directCount.setText(String.valueOf(item.getDirectCount()));
            } else {
                ((MyViewHolder) viewHolder).directCount.setText("0");
            }
        } else {
            ((MyViewHolder) viewHolder).directCountVIew.setVisibility(View.GONE);
        }
        if (item.getDisplayFields().toLowerCase().contains("achieveindays")) {
            ((MyViewHolder) viewHolder).achieveInDaysView.setVisibility(View.VISIBLE);
            ((MyViewHolder) viewHolder).achieveInDays.setText(String.valueOf(item.getAchieveInDays()));
        } else {
            ((MyViewHolder) viewHolder).achieveInDaysView.setVisibility(View.GONE);
        }
        if (item.getDisplayFields().toLowerCase().contains("remainingdays")) {
            ((MyViewHolder) viewHolder).remainingDaysView.setVisibility(View.VISIBLE);
            ((MyViewHolder) viewHolder).remainingDays.setText(String.valueOf(item.getRemainingDays()));
        } else {
            ((MyViewHolder) viewHolder).remainingDaysView.setVisibility(View.GONE);
        }
        if (item.getDisplayFields().toLowerCase().contains("holdcommamt")) {
            ((MyViewHolder) viewHolder).holdAmtView.setVisibility(View.VISIBLE);
            if (item.getHoldCommAmt() != 0 && item.getHoldCommAmt() > 0) {
                ((MyViewHolder) viewHolder).holdAmt.setText(String.valueOf(item.getHoldCommAmt()));
            } else {
                ((MyViewHolder) viewHolder).holdAmt.setText("0");
            }
        }else {
            ((MyViewHolder) viewHolder).holdAmtView.setVisibility(View.GONE);
        }
        if (item.getDisplayFields().toLowerCase().contains("status")) {
            ((MyViewHolder) viewHolder).status.setVisibility(View.VISIBLE);

            if (item.getUserRank() == item.getRankid()) {
                ((MyViewHolder) viewHolder).status.setText("Completed");
                ((MyViewHolder) viewHolder).status.setBackgroundResource(R.drawable.rounded_light_green);
            } else {
                ((MyViewHolder) viewHolder).status.setText("Pending");
                ((MyViewHolder) viewHolder).status.setBackgroundResource(R.drawable.rounded_light_yellow);
            }
        } else {
            ((MyViewHolder) viewHolder).status.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return mRankList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        AppCompatTextView rankname, rankid, sponserBusiness, achieveInDays, directCount,remainingDays,holdAmt;
        TextView status;

        View rankidView, sponserBusinessView, achieveInDaysView, directCountVIew,remainingDaysView,holdAmtView;

        MyViewHolder(View view) {
            super(view);
            itemView = view;
            rankname = view.findViewById(R.id.rankname);
            rankid = view.findViewById(R.id.rankid);
            sponserBusiness = view.findViewById(R.id.sponserBusiness);
            achieveInDays = view.findViewById(R.id.achieveInDays);
            status = view.findViewById(R.id.status);
            directCountVIew = view.findViewById(R.id.directCountVIew);
            directCount = view.findViewById(R.id.directCount);
            rankidView = view.findViewById(R.id.rankidView);
            sponserBusinessView = view.findViewById(R.id.sponserBusinessView);
            remainingDaysView = view.findViewById(R.id.remainingDaysView);
            remainingDays = view.findViewById(R.id.remainingDays);
            holdAmtView = view.findViewById(R.id.holdAmtView);
            holdAmt = view.findViewById(R.id.holdAmt);
            achieveInDaysView = view.findViewById(R.id.achieveInDaysView);
            directCountVIew = view.findViewById(R.id.directCountVIew);
        }
    }

}
