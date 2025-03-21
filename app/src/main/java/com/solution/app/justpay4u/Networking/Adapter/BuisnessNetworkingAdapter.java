package com.solution.app.justpay4u.Networking.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Networking.Activity.DashboardTeamDetailsList;
import com.solution.app.justpay4u.Networking.Activity.NetworkingDashboardActivity;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

public class BuisnessNetworkingAdapter extends RecyclerView.Adapter<BuisnessNetworkingAdapter.MyViewHolder> {
    private ArrayList<DashboardTeamDetailsList> mBusinessList = new ArrayList<>();

    BalanceResponse balanceResponse;
    private Activity mContext;

    public BuisnessNetworkingAdapter(ArrayList<DashboardTeamDetailsList> mBusinessList, BalanceResponse balanceResponse, Activity mContext) {
        this.mBusinessList = mBusinessList;
        this.balanceResponse = balanceResponse;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public BuisnessNetworkingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_networking_business, parent, false);
        return new BuisnessNetworkingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final DashboardTeamDetailsList item = mBusinessList.get(position);
        if (item.getHeaderName1() != null && !item.getHeaderName1().isEmpty())
            holder.headerName1.setText(item.getHeaderName1());
        else holder.headerName1.setText("N/A");
        holder.headerValue1.setText(item.getHeaderValue1());
        if (balanceResponse!=null && balanceResponse.isPV() && balanceResponse.isBV()) {
            holder.pVBusinessView.setVisibility(View.VISIBLE);
            holder.headerName2.setVisibility(View.VISIBLE);
            holder.itemView.setVisibility(View.VISIBLE);
            holder.headerValue2.setVisibility(View.VISIBLE);
            holder.headerValue3.setVisibility(View.VISIBLE);
            holder.headerValue2.setText(item.getHeaderValue2()+"");
            holder.headerValue3.setText(item.getHeaderValue3()+"");
        }
        else if (balanceResponse!=null &&balanceResponse.isPV()) {
            holder.pVBusinessView.setVisibility(View.VISIBLE);
            holder.headerName2.setVisibility(View.VISIBLE);
            holder.itemView.setVisibility(View.GONE);
            holder.headerValue2.setVisibility(View.VISIBLE);
            holder.headerValue3.setVisibility(View.GONE);
            holder.headerValue2.setText(item.getHeaderValue2()+"");
            holder.headerValue3.setText(item.getHeaderValue3()+"");
        }
        else if (balanceResponse!=null &&balanceResponse.isBV()) {
            holder.pVBusinessView.setVisibility(View.VISIBLE);
            holder.headerName2.setVisibility(View.GONE);
            holder.itemView.setVisibility(View.VISIBLE);
            holder.headerValue2.setVisibility(View.GONE);
            holder.headerValue3.setVisibility(View.VISIBLE);
            holder.headerValue2.setText(item.getHeaderValue2()+"");
            holder.headerValue3.setText(item.getHeaderValue3()+"");
        }
        else {
            holder.pVBusinessView.setVisibility(View.GONE);
            holder.headerName2.setVisibility(View.GONE);
            //  holder.itemView.setVisibility(View.GONE);
            holder.headerValue2.setVisibility(View.GONE);
            holder.headerValue3.setVisibility(View.GONE);
            holder.headerValue2.setText(item.getHeaderValue2()+"");
            holder.headerValue3.setText(item.getHeaderValue3()+"");
        }
        holder.itemBusinessView.setOnClickListener(v -> {
            if (mContext instanceof NetworkingDashboardActivity) {
                ((NetworkingDashboardActivity) mContext).businessItemClick(item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mBusinessList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView headerName1, headerValue1, headerName2, headerValue2, headerValue3, headerName3;
        View itemBusinessView;
        View pVBusinessView;


        public MyViewHolder(View view) {
            super(view);
            headerName1 = view.findViewById(R.id.headerName1);
            headerValue1 = view.findViewById(R.id.headerValue1);
            itemBusinessView = view.findViewById(R.id.itemBusinessView);
            headerName2 = view.findViewById(R.id.headerName2);
            headerValue2 = view.findViewById(R.id.headerValue2);
            headerName3 = view.findViewById(R.id.headerName3);
            headerValue3 = view.findViewById(R.id.headerValue3);
            pVBusinessView = view.findViewById(R.id.pVBusinessView);

        }
    }
}

