package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Shopping.Object.DashbaordInfoCategoryList;
import com.solution.app.justpay4u.Api.Shopping.Object.DashbaordInfoMenu;
import com.solution.app.justpay4u.Api.Shopping.Object.DashbaordInfoSubCategoryList;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.MainCategoryDetailShoppingActivity;

import java.util.ArrayList;

/**
 * Created by Vishnu on 18-04-2017.
 */

public class MenuListCategoryShoppingAdapter extends RecyclerView.Adapter<MenuListCategoryShoppingAdapter.MyViewHolder> {

    private final int imageSize;

    private final RequestOptions optionsCircleImage;
    private final ArrayList<DashbaordInfoCategoryList> transactionsList;
    private final Context mContext;
    int resourceId = 0;
    int subListVisiblePos = -1;
    DashbaordInfoMenu menuItem;

    public MenuListCategoryShoppingAdapter(Context mContext, DashbaordInfoMenu menuItem) {
        this.transactionsList = menuItem.getCategoryList();
        this.mContext = mContext;
        this.menuItem = menuItem;
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
                .inflate(R.layout.adapter_shopping_drawer_category_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DashbaordInfoCategoryList catmenu = transactionsList.get(position);
        holder.CategoryName.setText(catmenu.getName());
        //  holder.CategoryName.setText("\u25CB" + catmenu.getValue());
        Glide.with(mContext)
                .load(catmenu.getImage())
                .thumbnail(0.4f)
                .apply(optionsCircleImage.override(imageSize, imageSize))
                .into(holder.icon);
        if (catmenu.getSubcategory() != null && catmenu.getSubcategory().size() > 0) {
            holder.ivImage.setVisibility(View.VISIBLE);
            if (subListVisiblePos != -1 && subListVisiblePos == position) {
                holder.recyclerView.setVisibility(View.VISIBLE);
                holder.rlCatView.setBackgroundResource(R.drawable.primary_gradient_light);
                holder.CategoryName.setTextColor(Color.WHITE);
                holder.ivImage.setImageResource(R.drawable.ic_arrow_up_white_24px);
            } else {
                holder.recyclerView.setVisibility(View.GONE);
                holder.rlCatView.setBackgroundResource(0);
                holder.CategoryName.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryLight));
                holder.ivImage.setImageResource(R.drawable.ic_arrow_down);
            }
            dataparse(holder, catmenu.getSubcategory(), catmenu.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View view) {

                    if (subListVisiblePos == position) {
                        subListVisiblePos = -1;
                    } else {
                        subListVisiblePos = position;
                    }

                    notifyDataSetChanged();
                }
            });
        } else {
            holder.ivImage.setVisibility(View.INVISIBLE);
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, MainCategoryDetailShoppingActivity.class);
                intent.putExtra("Id", catmenu.getMainCategoryID());
                intent.putExtra("CategoryId", 0);
                intent.putExtra("isFromNavigationDrawer", false);
                intent.putExtra("CategoryName", catmenu.getName());
                intent.putExtra("CategoryImage", catmenu.getImage());
                intent.putParcelableArrayListExtra("CategoryList", catmenu.getSubcategory());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);

            });
        }

    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    private void dataparse(MyViewHolder holder, ArrayList<DashbaordInfoSubCategoryList> dashboardNews, String subCateoryName) {
        holder.subCategoryListAdapter = new MenuListSubCategoryShoppingAdapter(dashboardNews, mContext, menuItem.getMainCategoryID(), subCateoryName + " (" + menuItem.getName() + ")");
        holder.recyclerView.setAdapter(holder.subCategoryListAdapter);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView CategoryName;
        RelativeLayout rlCatView;
        ImageView ivImage, icon;
        RecyclerView recyclerView;

        View itemView;
        LinearLayoutManager linearLayoutManager;
        MenuListSubCategoryShoppingAdapter subCategoryListAdapter;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            CategoryName = view.findViewById(R.id.rowParentText);
            rlCatView = view.findViewById(R.id.rl_cat_view);
            recyclerView = view.findViewById(R.id.recyclerView);
            linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            ivImage = view.findViewById(R.id.iv_image);
            icon = view.findViewById(R.id.icon);
        }
    }

}