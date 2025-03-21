package com.solution.app.justpay4u.Fintech.UpiPayment.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.VPAList;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.UpiPayment.Activity.UPIListActivity;
import com.solution.app.justpay4u.Fintech.UpiPayment.UpiHandleApp;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 29-12-2017.
 */

public class VPAListAdapter extends RecyclerView.Adapter<VPAListAdapter.MyViewHolder> implements Filterable {


    Dialog dialog = null;
    Activity activity;
    private ArrayList<VPAList> listItem, filterListItem;
    RequestOptions requestOptions;

    public VPAListAdapter(ArrayList<VPAList> listItem, Activity activity) {
        this.listItem = listItem;
        this.filterListItem = listItem;
        this.activity = activity;
        requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.error(R.drawable.ic_upi_icon);
        requestOptions.placeholder(R.drawable.ic_upi_icon);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_vpa, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final VPAList operator = filterListItem.get(position);
        holder.beneName.setText(operator.getAccountHolder() + "");
        holder.vpaName.setText(operator.getVpa() + "");


        if (operator.getVpa() != null && operator.getVpa().contains("@")) {
            String vpaHandle = operator.getVpa().substring(operator.getVpa().indexOf("@") + 1);
            holder.appName.setText(UpiHandleApp.INSTANCE.getAppName(vpaHandle));
            Glide.with(activity)
                    .load(ApplicationConstant.INSTANCE.upiHandleLogoUrl + vpaHandle.trim() + ".png")
                    .apply(requestOptions).into(holder.vpaIv);
        }


        holder.sendView.setOnClickListener(v -> {
            if (activity instanceof UPIListActivity) {
                ((UPIListActivity) activity).sendMoneyClick(operator);
            }


        });

      /*  holder.deleteView.setOnClickListener(v -> {

        });*/
    }

    @Override
    public int getItemCount() {
        return filterListItem.size();
    }


    public void deleteDone() {
        if (dialog != null) {
            dialog.dismiss();
        }
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
                    ArrayList<VPAList> filteredList = new ArrayList<>();
                    for (VPAList row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getVpa().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getAccountHolder().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getSenderNo().toLowerCase().contains(charString.toLowerCase())) {
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
                filterListItem = (ArrayList<VPAList>) filterResults.values;
                notifyDataSetChanged();


                if (activity instanceof UPIListActivity) {
                    if (filterListItem.size() == 0) {
                        ((UPIListActivity) activity).noDataView.setVisibility(View.VISIBLE);
                    } else {
                        ((UPIListActivity) activity).noDataView.setVisibility(View.GONE);
                    }

                }

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView beneName;
        public TextView vpaName;
        public TextView appName;
        public ImageView vpaIv;

        public View sendView;
        public View deleteView;


        public MyViewHolder(View view) {
            super(view);
            beneName = view.findViewById(R.id.beneName);
            vpaName = view.findViewById(R.id.vpaName);
            appName = view.findViewById(R.id.appName);
            vpaIv = view.findViewById(R.id.vpaIv);

            sendView = view.findViewById(R.id.sendView);
            deleteView = view.findViewById(R.id.deleteView);

        }
    }


}
