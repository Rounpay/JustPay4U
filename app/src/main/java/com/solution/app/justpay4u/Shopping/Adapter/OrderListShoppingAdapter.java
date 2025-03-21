package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Shopping.Object.OrderList;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.ItemDetailsShoppingActivity;

import java.util.List;

/**
 * Created by Vishnu on 18-04-2017.
 */

public class OrderListShoppingAdapter extends RecyclerView.Adapter<OrderListShoppingAdapter.MyViewHolder> {
    private final RequestOptions requestOptions;
    private final List<OrderList> menuList;
    private final Context mContext;

    public OrderListShoppingAdapter(List<OrderList> menuList, Context mContext) {
        this.menuList = menuList;
        this.mContext = mContext;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder_square);
        requestOptions.error(R.drawable.placeholder_square);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shopping_order_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final OrderList operator = menuList.get(position);


        holder.name.setText(operator.getProductName() + "");
        holder.amount.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(operator.getSellingPrice() + ""));
        holder.mrp.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(operator.getMrp() + ""));
        if (operator.getMrp() > operator.getSellingPrice()) {
            holder.mrp.setVisibility(View.VISIBLE);
        } else {
            holder.mrp.setVisibility(View.GONE);
        }
        holder.qty.setText(operator.getQuantity() + " Qty");
        if (operator.getShippingCharge() == 0) {
            holder.shippingChargeTv.setText("Shipping Charge- Free");

        } else {
            holder.shippingChargeTv.setText("Shipping Charge- " + ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(operator.getShippingCharge() + ""));

        }

        if (operator.isDiscountType()) {
            holder.discount.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmount(operator.getDiscount() + "") + "% Off");
        } else {
            holder.discount.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(operator.getDiscount() + "") + " Off");
        }
        if (operator.getStatus() == 1) {
            holder.statusTv.setText("Order Placed");
            holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.color_delete));
            holder.statusTv.setVisibility(View.VISIBLE);
            holder.statusTv.setBackgroundResource(R.drawable.rounded_orange_border);
        } else if (operator.getStatus() == 2) {
            holder.statusTv.setText("Order Approved");
            holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.statusTv.setVisibility(View.VISIBLE);
            holder.statusTv.setBackgroundResource(R.drawable.rounded_green_border_fill);
        } else if (operator.getStatus() == 3) {
            holder.statusTv.setText("Order Disapproved");
            holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.color_red));
            holder.statusTv.setVisibility(View.VISIBLE);
            holder.statusTv.setBackgroundResource(R.drawable.rounded_red_border);
        } else if (operator.getStatus() == 4) {
            holder.statusTv.setText("Order Canceled");
            holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.color_red));
            holder.statusTv.setVisibility(View.VISIBLE);
            holder.statusTv.setBackgroundResource(R.drawable.rounded_red_border);
        } else if (operator.getStatus() == 5) {
            holder.statusTv.setText("Order Dispatched");
            holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.color_delete));
            holder.statusTv.setVisibility(View.VISIBLE);
            holder.statusTv.setBackgroundResource(R.drawable.rounded_orange_border);
        } else if (operator.getStatus() == 6) {
            holder.statusTv.setText("Order Delivered");
            holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.color_indigo));
            holder.statusTv.setVisibility(View.VISIBLE);
            holder.statusTv.setBackgroundResource(R.drawable.rounded_blue_border);
        } else if (operator.getStatus() == 7) {
            holder.statusTv.setText("Order Closed");
            holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.statusTv.setVisibility(View.VISIBLE);
            holder.statusTv.setBackgroundResource(R.drawable.rounded_accent_border);
        } else {
            holder.statusTv.setVisibility(View.GONE);
        }
        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.baseUrl + "image/Products/" + operator.getProductId() + "/" + operator.getImgUrl())
                .apply(requestOptions)
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, ItemDetailsShoppingActivity.class)
                    .putExtra("ProductId", operator.getProductDetailId())
                    /*.putExtra("ProductDetailId", operator.getProductDetailId())*/
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView name;
        private final TextView amount;
        private final TextView discount;
        private final TextView mrp;
        private final TextView qty;
        private final TextView shippingChargeTv;
        private final TextView statusTv;
        private final View itemView;

        public MyViewHolder(View view) {
           super(view);
            itemView = view;
            image = view.findViewById(R.id.image);
            amount = view.findViewById(R.id.amount);
            statusTv = view.findViewById(R.id.status);
            mrp = view.findViewById(R.id.mrp);
            mrp.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            discount = view.findViewById(R.id.discount);
            name = view.findViewById(R.id.name);
            qty = view.findViewById(R.id.qty);
            shippingChargeTv = view.findViewById(R.id.shippingChargeTv);

        }
    }


}