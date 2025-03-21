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
import com.solution.app.justpay4u.Networking.Activity.TotalBuisnessNetworkingActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;

public class TotalBusinessNetworkingAdapter extends RecyclerView.Adapter<TotalBusinessNetworkingAdapter.MyViewHolder> implements Filterable {

    private ArrayList<MemberListData> mList = new ArrayList<>();
    private ArrayList<MemberListData> mFilterList = new ArrayList<>();
    private Context mContext;

    public TotalBusinessNetworkingAdapter(ArrayList<MemberListData> transactionsList, Context mContext) {
        this.mContext = mContext;
        this.mFilterList = transactionsList;
        this.mList = transactionsList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_networking_total_business, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MemberListData operator = mFilterList.get(position);
        holder.name.setText(operator.getName() + "");
        holder.userId.setText(operator.getUserID() + "");
        holder.sponserId.setText(operator.getParentId() + "");
        holder.sponserName.setText(operator.getParentName() + "");
        holder.levelNo.setText(operator.getPosition() + "");
        holder.businessType.setText(operator.getBusinessType() + "");
        //holder.status.setText(operator.getStatus() + "");
        //holder.regDate.setText(Utility.INSTANCE.formatedDateWithT2(operator.getRegDate()));
        holder.date.setText(Utility.INSTANCE.formatedDateWithT2(operator.getBusinessDate()));
        holder.packageCost.setText("Buisness\n" + Utility.INSTANCE.formatedAmountWithOutRupees(operator.getPackageCost() + ""));
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
                                (row.getPackageCost() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getBusinessType() + "").toLowerCase().contains(charString.toLowerCase())) {
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


                if (mContext instanceof TotalBuisnessNetworkingActivity) {
                    if (mFilterList.size() == 0) {
                        ((TotalBuisnessNetworkingActivity) mContext).noDataView.setVisibility(View.VISIBLE);
                    } else {
                        ((TotalBuisnessNetworkingActivity) mContext).noDataView.setVisibility(View.GONE);
                    }

                }

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView name, userId, levelNo, date, sponserId, sponserName, status, packageCost, regDate, activateDate, businessType;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            userId = view.findViewById(R.id.userId);
            sponserId = view.findViewById(R.id.sponserId);
            sponserName = view.findViewById(R.id.sponserName);
            levelNo = view.findViewById(R.id.levelNo);
            regDate = view.findViewById(R.id.regDate);
            activateDate = view.findViewById(R.id.activateDate);
            date = view.findViewById(R.id.date);
            status = view.findViewById(R.id.status);
            packageCost = view.findViewById(R.id.packageCost);
            businessType = view.findViewById(R.id.businessType);

        }
    }
}
