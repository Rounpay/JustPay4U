package com.solution.app.justpay4u.Networking.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Networking.Object.NetworkingDashboardData;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

public class TeamDetailsListAdapter extends RecyclerView.Adapter<TeamDetailsListAdapter.MyViewHolder> {

    private ArrayList<DashboardTeamDetailsList> mteamDetailsList = new ArrayList<>();

    private NetworkingDashboardData mNetworkingDashboardData;
    private Activity mContext;

    public TeamDetailsListAdapter(ArrayList<DashboardTeamDetailsList> mteamDetailsList, NetworkingDashboardData mNetworkingDashboardData, Activity mContext) {
        this.mteamDetailsList = mteamDetailsList;
        this.mNetworkingDashboardData = mNetworkingDashboardData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TeamDetailsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_networking_team, parent, false);

        return new TeamDetailsListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamDetailsListAdapter.MyViewHolder holder, int position) {
        final DashboardTeamDetailsList item = mteamDetailsList.get(position);

        if (position % 2 == 0) {
            holder.itemTeamView.setBackground(mContext.getDrawable(R.drawable.rounded_team_dark_style));
        } else {
            holder.itemTeamView.setBackground(mContext.getDrawable(R.drawable.rounded_team_light_style));
        }
        if (item.getDisplayName() != null && !item.getDisplayName().isEmpty())
            holder.displayName.setText(item.getDisplayName());
        else
            holder.displayName.setText("N/A");

        holder.headerName1.setText(item.getHeaderName1() + "");

        holder.headerValue1.setText((item.getHeaderValue1() +""));

        holder.headerName2.setText(item.getHeaderName2() + "");
        holder.headerValue2.setText(item.getHeaderValue2());
        if ("Remaining".equals(item.getHeaderName3())) {
            holder.headerName3.setTextColor(Color.parseColor("#F8DE7E"));
            holder.headerName3.setText(item.getHeaderName3() + "");
        } else {
            holder.headerName3.setText(item.getHeaderName3() + "");
        }
        holder.headerValue3.setText(item.getHeaderValue3() + "");

        if (item.getId() == 8) {
            holder.userShareView.setVisibility(View.VISIBLE);
            holder.userShareTv.setText(mNetworkingDashboardData.getSingleData().getLeftReferralLink());
            holder.userCopy.setOnClickListener(v -> {
                if (mContext instanceof NetworkingDashboardActivity) {
                    ((NetworkingDashboardActivity) mContext).setClipboard(mNetworkingDashboardData.getSingleData().getLeftReferralLink());
                }
            });
            holder.userShare.setOnClickListener(v -> {
                if (mContext instanceof NetworkingDashboardActivity) {
                    ((NetworkingDashboardActivity) mContext).shareContent(null, mNetworkingDashboardData.getSingleData().getLeftReferralLink());
                }
            });
        }
        if (item.getId() == 9) {
            holder.userShareView.setVisibility(View.VISIBLE);
            holder.userShareTv.setText(mNetworkingDashboardData.getSingleData().getRightReferralLink());
            holder.userCopy.setOnClickListener(v -> {
                if (mContext instanceof NetworkingDashboardActivity) {
                    ((NetworkingDashboardActivity) mContext).setClipboard(mNetworkingDashboardData.getSingleData().getRightReferralLink());
                }
            });
            holder.userShare.setOnClickListener(v -> {
                if (mContext instanceof NetworkingDashboardActivity) {
                    ((NetworkingDashboardActivity) mContext).shareContent(null, mNetworkingDashboardData.getSingleData().getRightReferralLink());
                }
            });
        }

        holder.itemTeamView.setOnClickListener(view -> {
            if (mContext instanceof NetworkingDashboardActivity) {
                ((NetworkingDashboardActivity) mContext).teamItemClick(item,"All");
            }
        });
        holder.activeView.setOnClickListener(view -> {
            if (mContext instanceof NetworkingDashboardActivity) {
                ((NetworkingDashboardActivity) mContext).teamItemClick(item,"1");
            }
        });
        holder.deActiveView.setOnClickListener(view -> {
            if (mContext instanceof NetworkingDashboardActivity) {
                ((NetworkingDashboardActivity) mContext).teamItemClick(item,"0");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mteamDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView displayName,headerName1,headerValue1,headerName2,headerName3,headerValue2,headerValue3,userShareTv,userCopy,userShare ;
        View itemTeamView,activeView,deActiveView;
        RelativeLayout userShareView;

        public MyViewHolder(View view) {
            super(view);

            displayName = view.findViewById(R.id.displayName);
            headerName1 = view.findViewById(R.id.headerName1);
            headerValue1 = view.findViewById(R.id.headerValue1);
            headerName2 = view.findViewById(R.id.headerName2);
            headerValue2 = view.findViewById(R.id.headerValue2);
            headerName3 = view.findViewById(R.id.headerName3);
            headerValue3 = view.findViewById(R.id.headerValue3);
            itemTeamView = view.findViewById(R.id.itemTeamView);
            activeView = view.findViewById(R.id.activeView);
            deActiveView = view.findViewById(R.id.deActiveView);
            userShareView = view.findViewById(R.id.userShareView);
            userShareTv = view.findViewById(R.id.userShareTv);
            userCopy = view.findViewById(R.id.userCopy);
            userShare = view.findViewById(R.id.userShare);
        }
    }
}
