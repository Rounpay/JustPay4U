package com.solution.app.justpay4u.Networking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Networking.Object.MemberListData;
import com.solution.app.justpay4u.Networking.Activity.DirectBuisnessNetworkingActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;

public class DirectBuisnessNetworkingAdapter extends RecyclerView.Adapter<DirectBuisnessNetworkingAdapter.MyViewHolder> implements Filterable {

    private ArrayList<MemberListData> mList = new ArrayList<>();
    private ArrayList<MemberListData> mFilterList = new ArrayList<>();
    private Context mContext;

    public DirectBuisnessNetworkingAdapter(ArrayList<MemberListData> transactionsList, Context mContext) {
        this.mContext = mContext;
        this.mFilterList = transactionsList;
        this.mList = transactionsList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_networking_direct_buisness, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MemberListData operator = mFilterList.get(position);
        holder.name.setText(operator.getBusinessType() + "");
        holder.userId.setText(operator.getUserID() + "");
        holder.userName.setText(operator.getName() + "");
        holder.date.setText(Utility.INSTANCE.formatedDateWithT2(operator.getentryDate()));
        holder.packageCost.setText("Business\n" /*+operator.getBusinessCurrency()+" "*/+ Utility.INSTANCE.formatedAmountWithOutRupees(operator.getPackageCost() + ""));

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
                        if ((row.getBusinessType() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getPackageCost() + "").toLowerCase().contains(charString.toLowerCase())) {
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


                if (mContext instanceof DirectBuisnessNetworkingActivity) {
                    if (mFilterList.size() == 0) {
                        ((DirectBuisnessNetworkingActivity) mContext).noDataView.setVisibility(View.VISIBLE);
                    } else {
                        ((DirectBuisnessNetworkingActivity) mContext).noDataView.setVisibility(View.GONE);
                    }

                }

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView name,userId,userName, date, packageCost;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            userId = view.findViewById(R.id.userId);
            userName = view.findViewById(R.id.userName);
            date = view.findViewById(R.id.date);
            packageCost = view.findViewById(R.id.packageCost);

        }
    }
}
