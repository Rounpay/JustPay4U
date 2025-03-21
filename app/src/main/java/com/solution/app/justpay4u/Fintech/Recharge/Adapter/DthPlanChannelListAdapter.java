package com.solution.app.justpay4u.Fintech.Recharge.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.DthPlanChannels;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.RecyclerViewStickyHeader.HeaderStickyListener;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vishnu on 26-04-2017.
 */

public class DthPlanChannelListAdapter extends ListAdapter<DthPlanChannels, RecyclerView.ViewHolder> implements HeaderStickyListener {
    public static final DiffUtil.ItemCallback<DthPlanChannels> ModelDiffUtilCallback =
            new DiffUtil.ItemCallback<DthPlanChannels>() {
                @Override
                public boolean areItemsTheSame(@NonNull DthPlanChannels model, @NonNull DthPlanChannels t1) {
                    return model.getHeader().equals(t1.getHeader());
                }

                @Override
                public boolean areContentsTheSame(@NonNull DthPlanChannels model, @NonNull DthPlanChannels t1) {
                    return model.equals(t1);
                }
            };
    public static int ListData = 1;
    public static int Header = 2;
    String charText = "";
    int resourceId = 0;
    String operatorType = "";
    /*  public CommissionAdapter() {

      }*/
    RequestOptions requestOptions;
    private ArrayList<DthPlanChannels> rechargeStatus;
    private ArrayList<DthPlanChannels> transactionsList;
    private Context mContext;

    public DthPlanChannelListAdapter(ArrayList<DthPlanChannels> transactionsList, Context mContext, RequestOptions requestOptions) {
        super(ModelDiffUtilCallback);
        this.transactionsList = transactionsList;
        this.rechargeStatus = new ArrayList<>();
        this.rechargeStatus.addAll(transactionsList);
        this.mContext = mContext;
        this.requestOptions = requestOptions;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Header) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticky_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dth_plan_channel, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final DthPlanChannels operator = transactionsList.get(position);
        if (getItemViewType(position) == ListData) {


            ((MyViewHolder) holder).name.setText(operator.getName() + "");
            Glide.with(mContext)
                    .load(operator.getLogo() + "")
                    .apply(requestOptions)
                    .into(((MyViewHolder) holder).logo);
        } else {
            ((HeaderViewHolder) holder).title.setText(operator.getHeader());
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (transactionsList.get(position).getHeader() != null && !transactionsList.get(position).getHeader().isEmpty()) {
            return Header;
        } else {
            return ListData;
        }

    }

    @Override
    public String getHeaderString(int headerPosition) {
        return transactionsList.get(headerPosition).getHeader();
    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        Integer headerPosition = 0;
        for (Integer i = itemPosition; i > 0; i--) {
            if (isHeader(i)) {
                headerPosition = i;
                return headerPosition;
            }
        }
        return headerPosition;
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        return R.layout.item_sticky_header;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {
        TextView tv = header.findViewById(R.id.opType);
        tv.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        tv.setTextColor(Color.WHITE);
        tv.setText(transactionsList.get(headerPosition).getHeader());
    }

    @Override
    public boolean isHeader(int itemPosition) {
        if (transactionsList.get(itemPosition).getHeader() != null && !transactionsList.get(itemPosition).getHeader().isEmpty()) {
            return true;
        }
        return false;
    }

    // Filter Class
    public void filter(String charText) {
        this.charText = charText.toLowerCase(Locale.getDefault());
        transactionsList.clear();
        if (charText.length() == 0) {
            transactionsList.addAll(rechargeStatus);
        } else {


            String CategoryName = "";

            for (DthPlanChannels wp : rechargeStatus) {
                if (wp.getGenre().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {

                    if (!CategoryName.equalsIgnoreCase(wp.getGenre())) {
                        CategoryName = wp.getGenre();
                        transactionsList.add(new DthPlanChannels(wp.getGenre(), "", "", ""));
                    }
                    transactionsList.add(new DthPlanChannels(null, wp.getName(), wp.getGenre(), wp.getLogo()));
                }
            }

        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView logo;
        public TextView name;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            logo = view.findViewById(R.id.logo);
            name = view.findViewById(R.id.name);

        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.opType);
            title.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
            title.setTextColor(Color.WHITE);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) title.getLayoutParams();
            params.bottomMargin = 10;
        }

    }
}