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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Shopping.Object.ProductInfoFilterOptionList;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.ItemDetailsShoppingActivity;

import java.util.ArrayList;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class FeatureCategoryImageTextShoppingAdapter extends RecyclerView.Adapter<FeatureCategoryImageTextShoppingAdapter.ViewHolder> {

    private final RequestOptions requestOptions;
    private final ArrayList<ProductInfoFilterOptionList> listItem;
    private final Context mContext;
    boolean isImageView;
    TextView selectedValue;

    public FeatureCategoryImageTextShoppingAdapter(Context mContext, ArrayList<ProductInfoFilterOptionList> listItem, boolean isImageView, TextView selectedValue) {
        this.listItem = listItem;
        this.mContext = mContext;
        this.isImageView = isImageView;
        this.selectedValue = selectedValue;

        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder_square);
        requestOptions.error(R.drawable.placeholder_square);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        //requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        //requestOptions.skipMemoryCache(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shopping_feature_category_image_text, null);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ProductInfoFilterOptionList item = listItem.get(position);
        if (item != null) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ItemDetailsShoppingActivity.class);
                    intent.putExtra("ProductId", item.getPosid() + "");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }
            });

            if (isImageView) {
                holder.textBottom.setVisibility(View.VISIBLE);
                holder.textBottom.setText(item.getOptionName());

                holder.image.setVisibility(View.VISIBLE);
                holder.text.setVisibility(View.GONE);
                Glide.with(mContext)
                        .load(item.getImage())
                        .thumbnail(0.5f)
                        .apply(requestOptions)
                        .into(holder.image);
            } else {
                holder.textBottom.setVisibility(View.GONE);
                holder.image.setVisibility(View.GONE);
                holder.textBottom.setText(item.getOptionName());
                holder.text.setVisibility(View.VISIBLE);
                holder.text.setText(item.getOptionName());
            }


            if (item.getSelected()) {
                holder.itemView.setClickable(false);
                selectedValue.setText(item.getOptionName());
                holder.bgView.setBackgroundResource(R.drawable.rounded_image_selected);
            } else {
                holder.itemView.setClickable(true);
                holder.bgView.setBackgroundResource(R.drawable.rounded_image_not_selected);
            }


        }

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text, textBottom;
        ImageView image;
        RelativeLayout bgView;
        View itemView;

        public ViewHolder(View view) {
           super(view);
            itemView = view;
            bgView = view.findViewById(R.id.bgView);
            text = view.findViewById(R.id.text);
            textBottom = view.findViewById(R.id.textBottom);
            image = view.findViewById(R.id.image);
        }
    }


}
