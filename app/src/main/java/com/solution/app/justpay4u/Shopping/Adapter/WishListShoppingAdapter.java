package com.solution.app.justpay4u.Shopping.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.like.LikeButton;
import com.solution.app.justpay4u.Api.Shopping.Object.DashboardProductListData;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.ItemDetailsShoppingActivity;
import com.solution.app.justpay4u.Shopping.Activity.WishlistShoppingActivity;

import java.util.ArrayList;

/**
 * Created by Vishnu on 18-04-2017.
 */

public class WishListShoppingAdapter extends RecyclerView.Adapter<WishListShoppingAdapter.MyViewHolder> {


    private final RequestOptions optionsRectangleImage;
    private final ArrayList<DashboardProductListData> mWishLists;
    private final Activity mContext;
    RecyclerView visibleRecyclerView;
    ImageView arrowImage;
    ArrayList<String> quantityArray;
    ChooseQuantityListShoppingAdapter mChooseQuantityListAdapter;
    private PopupWindow popup;
    private Dialog dialog;

    public WishListShoppingAdapter(Activity mContext, ArrayList<DashboardProductListData> mWishLists) {
        this.mWishLists = mWishLists;
        this.mContext = mContext;
        optionsRectangleImage = new RequestOptions()
                .fitCenter()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .timeout(65000)
                .placeholder(R.drawable.placeholder_square)
                .error(R.drawable.defaultlogo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shopping_similar_product, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DashboardProductListData item = mWishLists.get(position);
        if (item.getDiscount() == 0 || item.getDiscount() == 0.00) {
            holder.discountView.setVisibility(View.GONE);
            holder.mrpText.setVisibility(View.GONE);
        } else {
            holder.discountView.setVisibility(View.VISIBLE);
            holder.mrpText.setVisibility(View.VISIBLE);

            holder.mrpText.setText("MRP: \u20B9 " + ApiShoppingUtilMethods.INSTANCE.formatedAmount(item.getMrp() + ""));
            holder.mrpText.setPaintFlags(holder.mrpText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if (item.isDiscountType()) {
                holder.discountText.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmount(item.getDiscount() + "") + "% Off");
            } else {
                holder.discountText.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees((item.getDiscount() /** operator.getQuantity()*/) + "") + " Off");
            }

            /*holder.discountText.setText("\u20B9 " + ApiShoppingUtilMethods.INSTANCE.formatedAmount(item.getDiscount() + "") + " Off");*/
        }
        holder.titleText.setText(item.getProductName() != null && !item.getProductName().isEmpty() ? item.getProductName() : item.getTitle());
        //TODO set Unit price and filter
        holder.priceText.setText("\u20B9 " + ApiShoppingUtilMethods.INSTANCE.formatedAmount(item.getSellingPrice() + ""));
        if (item.getSetName() != null && !item.getSetName().isEmpty()) {
            holder.subDescription.setVisibility(View.VISIBLE);
            holder.subDescription.setText(item.getSetName().trim());
        } else {
            holder.subDescription.setVisibility(View.GONE);
        }
        Glide.with(mContext)
                .load(item.getFrontImage())
                .thumbnail(0.6f)
                .apply(optionsRectangleImage)
                .into(holder.ivProductImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, ItemDetailsShoppingActivity.class);
                intent.putExtra("ProductId", item.getPosId() + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);

            }
        });

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WishlistShoppingActivity) mContext).RemoveWishList(position, item.getPosId() + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWishLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivProductImage;
        private final TextView titleText;
        private final TextView subDescription;
        private final AppCompatImageView deleteIcon;
        private final TextView priceText;
        private final TextView mrpText;
        private final LinearLayout discountView;
        private final TextView discountText;
        View itemView;


        public MyViewHolder(View view) {
           super(view);
            itemView = view;
            RelativeLayout llMainCat = view.findViewById(R.id.ll_main_cat);
            ivProductImage =  view.findViewById(R.id.iv_product_image);
            titleText =  view.findViewById(R.id.titleText);
            subDescription =  view.findViewById(R.id.subDescription);
            deleteIcon = (AppCompatImageView) view.findViewById(R.id.deleteIcon);
            priceText =  view.findViewById(R.id.priceText);
            mrpText =  view.findViewById(R.id.mrpText);
            discountView =  view.findViewById(R.id.discountView);
            discountText =  view.findViewById(R.id.discountText);
            LinearLayout userActionView =  view.findViewById(R.id.userActionView);
            LikeButton vectorCartIcon = (LikeButton) view.findViewById(R.id.vector_cart_icon);
            LikeButton vectorLike = (LikeButton) view.findViewById(R.id.vector_like);
            deleteIcon.setVisibility(View.VISIBLE);
            userActionView.setVisibility(View.GONE);

        }
    }


}