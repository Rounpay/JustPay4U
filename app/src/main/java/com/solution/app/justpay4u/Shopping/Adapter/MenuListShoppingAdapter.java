package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Shopping.Object.DashbaordInfoMenu;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.MainCategoryDetailShoppingActivity;
import com.solution.app.justpay4u.Shopping.Activity.ShoppingCategoryActivity;

import java.util.List;


public class MenuListShoppingAdapter extends RecyclerView.Adapter<MenuListShoppingAdapter.MyViewHolder> {

    private final int imageSize;
    private final RequestOptions optionsCircleImage;
    private final List<DashbaordInfoMenu> transactionsList;
    private final Context mContext;
    int subListVisiblePos = -1;

    public MenuListShoppingAdapter(Context mContext, List<DashbaordInfoMenu> transactionsList) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        imageSize = (int) mContext.getResources().getDimension(R.dimen._100sdp);
        optionsCircleImage = new RequestOptions()
                .circleCrop()
                .timeout(65000)
                .placeholder(R.drawable.placeholder_square)
                .error(R.drawable.defaultlogo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shopping_drawer_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DashbaordInfoMenu catmenu = transactionsList.get(position);

        holder.CategoryName.setText("" + catmenu.getName());
        Glide.with(mContext)
                .load(catmenu.getIcon())
                .thumbnail(0.4f)
                .apply(optionsCircleImage.override(imageSize, imageSize))
                .into(holder.icon);
        if (catmenu.getCategoryList() != null && catmenu.getCategoryList().size() > 0) {
            holder.ivFrwd.setVisibility(View.VISIBLE);
            if (subListVisiblePos != -1 && subListVisiblePos == position) {
                holder.recyclerView.setVisibility(View.VISIBLE);
                holder.rlCatView.setBackgroundResource(R.drawable.primary_gradient_dark);
                holder.CategoryName.setTextColor(Color.WHITE);
                holder.ivFrwd.setImageResource(R.drawable.ic_arrow_up_white_24px);
            } else {
                holder.recyclerView.setVisibility(View.GONE);
                holder.rlCatView.setBackgroundResource(0);
                holder.CategoryName.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                holder.ivFrwd.setImageResource(R.drawable.ic_arrow_down);
            }
            holder.mCategoryListShoppingAdapter = new MenuListCategoryShoppingAdapter(mContext, catmenu);
            holder.recyclerView.setAdapter(holder.mCategoryListShoppingAdapter);

            holder.itemView.setOnClickListener(v -> {
                if (mContext instanceof ShoppingCategoryActivity) {

                    if (subListVisiblePos == position) {
                        subListVisiblePos = -1;
                    } else {
                        subListVisiblePos = position;
                    }

                    notifyDataSetChanged();

                }

            });
        } else {
            holder.ivFrwd.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, MainCategoryDetailShoppingActivity.class);
                intent.putExtra("Id", catmenu.getMainCategoryID());
                intent.putExtra("CategoryId", 0);
                intent.putExtra("isFromNavigationDrawer", false);
                intent.putExtra("CategoryName", catmenu.getName());
                intent.putExtra("CategoryImage", catmenu.getMainCategoryImage());
                intent.putParcelableArrayListExtra("CategoryList", catmenu.getCategoryList());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);

            });

        }

    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView CategoryName;
        RelativeLayout rlCatView;
        View itemView;
        ImageView ivFrwd, icon;
        RecyclerView recyclerView;
        MenuListCategoryShoppingAdapter mCategoryListShoppingAdapter;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            CategoryName = view.findViewById(R.id.rowParentText);
            rlCatView = view.findViewById(R.id.rl_cat_view);
            ivFrwd = view.findViewById(R.id.iv_frwd);
            icon = view.findViewById(R.id.icon);
            ivFrwd.setVisibility(View.VISIBLE);
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }


}