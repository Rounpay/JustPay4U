package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Shopping.Object.DashbaordInfoTopCategory;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.MainCategoryDetailShoppingActivity;

import java.util.List;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class DashboardTopCategoryShoppingAdapter extends RecyclerView.Adapter<DashboardTopCategoryShoppingAdapter.MyViewHolder> {

    private final RequestOptions requestOptions;
    private final List<DashbaordInfoTopCategory> transactionsList;
    private final Context mContext;
    RecyclerView.RecycledViewPool viewPool;

    public DashboardTopCategoryShoppingAdapter(Context mContext, List<DashbaordInfoTopCategory> transactionsList) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder_square);
        requestOptions.error(R.drawable.placeholder_square);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        //requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        //requestOptions.skipMemoryCache(true);
        viewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shopping_category_tab_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final DashbaordInfoTopCategory operator = transactionsList.get(position);

        holder.name.setText((operator.getName() + "").trim());
        Glide.with(mContext)
                .load(operator.getImage())
                .thumbnail(0.4f)
                .apply(requestOptions)
                .into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainCategoryDetailShoppingActivity.class);
                intent.putExtra("Id", operator.getMainCategoryID());
                intent.putExtra("CategoryId", operator.getCategoryID());
                intent.putExtra("isFromNavigationDrawer", true);
                intent.putExtra("CategoryName", operator.getName());
                intent.putExtra("CategoryImage", operator.getImage());
                intent.putParcelableArrayListExtra("CategoryList", operator.getSubcategory());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;
        View itemView;

        public MyViewHolder(View view) {
           super(view);
            itemView = view;
            name =  view.findViewById(R.id.name);
            image =  view.findViewById(R.id.image);

        }
    }

}
