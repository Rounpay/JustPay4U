package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Shopping.Object.MainCategoryDetailTopHorizontalListItem;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.MainCategoryDetailShoppingActivity;
import com.solution.app.justpay4u.Shopping.Activity.OtherOfferShoppingActivity;

import java.util.List;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class MainCategoryDetailTopHorizonalListShoppingAdapter extends RecyclerView.Adapter<MainCategoryDetailTopHorizonalListShoppingAdapter.MyViewHolder> {

    private final List<MainCategoryDetailTopHorizontalListItem> transactionsList;
    private final Context mContext;
    private final RequestOptions requestOptions;
    RecyclerView.RecycledViewPool viewPool;

    public MainCategoryDetailTopHorizonalListShoppingAdapter(List<MainCategoryDetailTopHorizontalListItem> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        viewPool = new RecyclerView.RecycledViewPool();
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder_square);
        requestOptions.error(R.drawable.placeholder_square);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shopping_category_tab_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MainCategoryDetailTopHorizontalListItem item = transactionsList.get(position);

        holder.text.setText(item.getName());

        if (item.isSelected()) {
            holder.text.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.itemView.setBackgroundResource(R.drawable.border_left_right);
        } else {
            holder.text.setTextColor(Color.BLACK);
            holder.itemView.setBackgroundResource(0);
        }
        Glide.with(mContext)
                .load(item.getImage() + "")
                .apply(requestOptions)
                .into(holder.icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof MainCategoryDetailShoppingActivity) {
                    ((MainCategoryDetailShoppingActivity) mContext).setItemSelect(position);
                } else if (mContext instanceof OtherOfferShoppingActivity) {
                    ((OtherOfferShoppingActivity) mContext).setItemSelect(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        View itemView;
        ImageView icon;

        public MyViewHolder(View view) {
           super(view);
            itemView = view;
            text = view.findViewById(R.id.text);
            icon = view.findViewById(R.id.icon);

        }
    }

}
