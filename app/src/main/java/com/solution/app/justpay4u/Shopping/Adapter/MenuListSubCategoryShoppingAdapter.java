package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Shopping.Object.DashbaordInfoSubCategoryList;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.MainCategoryDetailShoppingActivity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vishnu on 18-04-2017.
 */

public class MenuListSubCategoryShoppingAdapter extends RecyclerView.Adapter<MenuListSubCategoryShoppingAdapter.MyViewHolder> {

    private final RequestOptions optionsCircleImage;
    private final ArrayList<DashbaordInfoSubCategoryList> transactionsList;
    private final Context mContext;
    int imageSize = 0;
    int mainCatId;
    String categoryName;

    public MenuListSubCategoryShoppingAdapter(ArrayList<DashbaordInfoSubCategoryList> transactionsList, Context mContext, int mainCatId, String categoryName) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.mainCatId = mainCatId;
        this.categoryName = categoryName;
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
                .inflate(R.layout.adapter_shopping_drawer_sub_category_sub_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DashbaordInfoSubCategoryList catmenu = transactionsList.get(position);
        holder.CategoryName.setText(catmenu.getName());
        Glide.with(mContext)
                .load(catmenu.getImage())
                .thumbnail(0.4f)
                .apply(optionsCircleImage.override(imageSize, imageSize))
                .into(holder.icon);


        holder.rlCatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MainCategoryDetailShoppingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("MainCategoryId", mainCatId);
                intent.putExtra("Id", mainCatId);
                intent.putExtra("CategoryId", catmenu.getCategoryID());
                intent.putExtra("SubCategoryId", catmenu.getSubCategoryId());
                intent.putParcelableArrayListExtra("CategoryList", transactionsList);
                intent.putExtra("CategoryImage", catmenu.getImage());
                intent.putExtra("isFromNavigationDrawer", true);
                intent.putExtra("CategoryName", categoryName);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    private String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView CategoryName;
        RelativeLayout rlCatView;
        ImageView ivDown, icon;
        ImageView ivFrwd;

        public MyViewHolder(View view) {
            super(view);
            CategoryName = view.findViewById(R.id.rowParentText);
            rlCatView = view.findViewById(R.id.rl_cat_view);
            icon = view.findViewById(R.id.icon);
        }
    }
}