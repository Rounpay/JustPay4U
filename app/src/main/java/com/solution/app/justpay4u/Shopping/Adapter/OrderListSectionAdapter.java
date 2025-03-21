package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Shopping.Object.OrderDetail;
import com.solution.app.justpay4u.Api.Shopping.Object.OrderList;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.ItemDetailsShoppingActivity;
import com.solution.app.justpay4u.Shopping.Activity.OrderDetailShoppingActivity;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class OrderListSectionAdapter extends Section {

    private final OrderDetail mOrderDetail;
    private final List<OrderList> list;

    private final RequestOptions requestOptions;
    private final Context mContext;

    public OrderListSectionAdapter(@NonNull Context mContext, @NonNull final OrderDetail mOrderDetail) {

        super(SectionParameters.builder()
                .itemResourceId(R.layout.adapter_shopping_order_list)
                .headerResourceId(R.layout.adapter_shopping_order_list_header)
                .build());

        this.mOrderDetail = mOrderDetail;
        this.list = mOrderDetail.getOrderDetailList();
        this.mContext = mContext;

        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder_square);
        requestOptions.error(R.drawable.placeholder_square);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    public int getContentItemsTotal() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(final View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;

        final OrderList operator = list.get(position);
        if (position == list.size() - 1) {
            itemHolder.line.setVisibility(View.VISIBLE);
        } else {
            itemHolder.line.setVisibility(View.GONE);
        }

        itemHolder.name.setText(operator.getProductName() + "");
        itemHolder.amount.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(operator.getSellingPrice() + ""));
        itemHolder.mrp.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(operator.getMrp() + ""));
        if (operator.getMrp() > operator.getSellingPrice()) {
            itemHolder.mrp.setVisibility(View.VISIBLE);
        } else {
            itemHolder.mrp.setVisibility(View.GONE);
        }
        itemHolder.qty.setText(operator.getQuantity() + " Qty");
        if (operator.getShippingCharge() == 0) {
            itemHolder.shippingChargeTv.setText("Shipping Charge- Free");

        } else {
            itemHolder.shippingChargeTv.setText("Shipping Charge- " + ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(operator.getShippingCharge() + ""));

        }

        if (operator.isDiscountType()) {
            itemHolder.discount.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmount(operator.getDiscount() + "") + "% Off");
        } else {
            itemHolder.discount.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(operator.getDiscount() + "") + " Off");
        }
        if (operator.getStatus() == 1) {
            itemHolder.statusTv.setText("Order Placed");
            itemHolder.statusTv.setTextColor(mContext.getResources().getColor(R.color.color_delete));
            itemHolder.statusTv.setVisibility(View.VISIBLE);
            itemHolder.statusTv.setBackgroundResource(R.drawable.rounded_orange_border);
        } else if (operator.getStatus() == 2) {
            itemHolder.statusTv.setText("Order Approved");
            itemHolder.statusTv.setTextColor(mContext.getResources().getColor(R.color.green));
            itemHolder.statusTv.setVisibility(View.VISIBLE);
            itemHolder.statusTv.setBackgroundResource(R.drawable.rounded_green_border_fill);
        } else if (operator.getStatus() == 3) {
            itemHolder.statusTv.setText("Order Disapproved");
            itemHolder.statusTv.setTextColor(mContext.getResources().getColor(R.color.color_red));
            itemHolder.statusTv.setVisibility(View.VISIBLE);
            itemHolder.statusTv.setBackgroundResource(R.drawable.rounded_red_border);
        } else if (operator.getStatus() == 4) {
            itemHolder.statusTv.setText("Order Canceled");
            itemHolder.statusTv.setTextColor(mContext.getResources().getColor(R.color.color_red));
            itemHolder.statusTv.setVisibility(View.VISIBLE);
            itemHolder.statusTv.setBackgroundResource(R.drawable.rounded_red_border);
        } else if (operator.getStatus() == 5) {
            itemHolder.statusTv.setText("Order Dispatched");
            itemHolder.statusTv.setTextColor(mContext.getResources().getColor(R.color.color_delete));
            itemHolder.statusTv.setVisibility(View.VISIBLE);
            itemHolder.statusTv.setBackgroundResource(R.drawable.rounded_orange_border);
        } else if (operator.getStatus() == 6) {
            itemHolder.statusTv.setText("Order Delivered");
            itemHolder.statusTv.setTextColor(mContext.getResources().getColor(R.color.color_indigo));
            itemHolder.statusTv.setVisibility(View.VISIBLE);
            itemHolder.statusTv.setBackgroundResource(R.drawable.rounded_blue_border);
        } else if (operator.getStatus() == 7) {
            itemHolder.statusTv.setText("Order Closed");
            itemHolder.statusTv.setTextColor(mContext.getResources().getColor(R.color.black));
            itemHolder.statusTv.setVisibility(View.VISIBLE);
            itemHolder.statusTv.setBackgroundResource(R.drawable.rounded_accent_border);
        } else {
            itemHolder.statusTv.setVisibility(View.GONE);
        }
        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.baseUrl + "image/Products/" + operator.getProductId() + "/" + operator.getImgUrl())
                .apply(requestOptions)
                .into(itemHolder.image);

        itemHolder.itemView.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, ItemDetailsShoppingActivity.class)
                    .putExtra("ProductId", operator.getProductDetailId())
                    /*.putExtra("ProductDetailId", operator.getProductDetailId())*/
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(final View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(final RecyclerView.ViewHolder holder) {
        final HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

        headerHolder.name.setText("Order Id- " + mOrderDetail.getOrderId() + "");
        headerHolder.date.setText("Order Date- " + ApiShoppingUtilMethods.INSTANCE.formatedDateOfSlash(mOrderDetail.getOrderDate() + ""));
        headerHolder.qty.setText("Total Quantity- " + mOrderDetail.getQuantity());
        headerHolder.amount.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(mOrderDetail.getTotalCost() + ""));
        if (mOrderDetail.getStatus() == 1) {
            headerHolder.status.setText("Order Placed");
            headerHolder.status.setTextColor(mContext.getResources().getColor(R.color.color_delete));
            headerHolder.statusView.setVisibility(View.VISIBLE);
        } else if (mOrderDetail.getStatus() == 2) {
            headerHolder.status.setText("Order Approved");
            headerHolder.status.setTextColor(mContext.getResources().getColor(R.color.green));
            headerHolder.statusView.setVisibility(View.VISIBLE);
        } else if (mOrderDetail.getStatus() == 3) {
            headerHolder.status.setText("Order Disapproved");
            headerHolder.status.setTextColor(mContext.getResources().getColor(R.color.color_red));
            headerHolder.statusView.setVisibility(View.VISIBLE);
        } else if (mOrderDetail.getStatus() == 4) {
            headerHolder.status.setText("Order Canceled");
            headerHolder.status.setTextColor(mContext.getResources().getColor(R.color.color_red));
            headerHolder.statusView.setVisibility(View.VISIBLE);
        } else if (mOrderDetail.getStatus() == 5) {
            headerHolder.status.setText("Order Dispatched");
            headerHolder.status.setTextColor(mContext.getResources().getColor(R.color.color_delete));
            headerHolder.statusView.setVisibility(View.VISIBLE);
        } else if (mOrderDetail.getStatus() == 6) {
            headerHolder.status.setText("Order Delivered");
            headerHolder.status.setTextColor(mContext.getResources().getColor(R.color.color_indigo));
            headerHolder.statusView.setVisibility(View.VISIBLE);
        } else if (mOrderDetail.getStatus() == 7) {
            headerHolder.status.setText("Order Closed");
            headerHolder.status.setTextColor(mContext.getResources().getColor(R.color.black));
            headerHolder.statusView.setVisibility(View.VISIBLE);
        } else {
            headerHolder.statusView.setVisibility(View.GONE);
        }

        if (mOrderDetail.getTotalShipping() == 0) {
            headerHolder.shippingChargeTv.setText("Shipping Charge- Free");

        } else {
            headerHolder.shippingChargeTv.setText("Shipping Charge- " + ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(mOrderDetail.getTotalShipping() + ""));

        }
        headerHolder.mrp.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(mOrderDetail.getTotalMRP() + ""));
        if (mOrderDetail.getTotalMRP() > mOrderDetail.getTotalSellingPrice()) {
            headerHolder.mrp.setVisibility(View.VISIBLE);
        } else {
            headerHolder.mrp.setVisibility(View.GONE);
        }
        if (mOrderDetail.isDiscountType()) {
            headerHolder.discount.setText("You saved " + ApiShoppingUtilMethods.INSTANCE.formatedAmount(mOrderDetail.getTotalDiscount() + "") + "% on this order");
        } else {
            headerHolder.discount.setText("You saved " + ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees(mOrderDetail.getTotalDiscount() + "") + " on this order");
        }

       /* Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.baseProductImageUrl + operator.getProductId() + "/" + operator.getImgUrl())
                .apply(requestOptions)
                .into(headerHolder.image);*/

        headerHolder.itemView.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, OrderDetailShoppingActivity.class)
                    .putExtra("OrderId", mOrderDetail.getOrderId())
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });
    }

    final class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView name;
        private final TextView amount;
        private final TextView discount;
        private final TextView mrp;
        private final TextView qty;
        private final TextView shippingChargeTv;
        private final TextView statusTv;
        private final View itemView;
        private final View line;

        ItemViewHolder(@NonNull View view) {
           super(view);

            itemView = view;
            line = view.findViewById(R.id.line);
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

    final class HeaderViewHolder extends RecyclerView.ViewHolder {

        //  private ImageView image;
        private final TextView name;
        private final TextView amount;
        private final TextView discount;
        private final TextView shippingChargeTv;
        private final TextView mrp;
        private final TextView date;
        private final TextView qty;
        private final TextView status;
        private final View itemView;
        private final View statusView;

        HeaderViewHolder(@NonNull View view) {
           super(view);

            itemView = view;
            // image = view.findViewById(R.id.image);
            amount = view.findViewById(R.id.amount);
            mrp = view.findViewById(R.id.mrp);
            statusView = view.findViewById(R.id.statusView);
            status = view.findViewById(R.id.status);
            shippingChargeTv = view.findViewById(R.id.shippingChargeTv);
            mrp.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            discount = view.findViewById(R.id.discount);
            name = view.findViewById(R.id.name);
            date = view.findViewById(R.id.date);
            qty = view.findViewById(R.id.qty);
        }
    }


}
