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
import com.solution.app.justpay4u.Api.Shopping.Object.CartDetail;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.CartDetailShoppingActivity;
import com.solution.app.justpay4u.Shopping.Activity.ItemDetailsShoppingActivity;

import java.util.List;

/**
 * Created by Vishnu on 18-04-2017.
 */

public class CartListShoppingAdapter extends RecyclerView.Adapter<CartListShoppingAdapter.MyViewHolder> {
    private final RequestOptions requestOptions;
    private final List<CartDetail> menuList;
    private final Context mContext;
    private int clickPos;

    public CartListShoppingAdapter(List<CartDetail> menuList, Context mContext) {
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
                .inflate(R.layout.adapter_shopping_cart_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CartDetail operator = menuList.get(position);


        holder.name.setText(operator.getProductName() + "");
        holder.amount.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees((operator.getSellingPrice() /** operator.getQuantity()*/) + ""));
        holder.mrp.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees((operator.getMrp() /** operator.getQuantity()*/) + ""));
        if (!ApiShoppingUtilMethods.INSTANCE.cartProductIdList.contains(operator.getProductDetailID())) {
            ApiShoppingUtilMethods.INSTANCE.cartProductIdList.add(operator.getProductDetailID());
        }
        /*if (operator.getShippingCharges() == 0) {
            holder.shippingCharge.setText("Shipping Charge : Free");
        } else {
            holder.shippingCharge.setText("Shipping Charge : "+UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getShippingCharges() + ""));
        }*/

        if (operator.getMrp() > operator.getSellingPrice()) {
            holder.mrp.setVisibility(View.VISIBLE);
        } else {
            holder.mrp.setVisibility(View.GONE);
        }
        if (operator.isDiscountType()) {
            holder.discount.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmount(operator.getDiscount() + "") + "% Off");
        } else {
            holder.discount.setText(ApiShoppingUtilMethods.INSTANCE.formatedAmountWithRupees((operator.getDiscount() /** operator.getQuantity()*/) + "") + " Off");
        }
        holder.quantity.setText(operator.getQuantity() + "");
        if (operator.getQuantity() <= 1) {
            holder.minus.setClickable(false);
            holder.minus.setTextColor(mContext.getResources().getColor(R.color.light_grey));
        } else {
            holder.minus.setOnClickListener(v ->
                    ((CartDetailShoppingActivity) mContext).changeQuantity((operator.getQuantity() - 1), operator.getId(), position, holder.minus, holder.quantity));

            holder.minus.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }

        holder.plus.setOnClickListener(v ->
                ((CartDetailShoppingActivity) mContext).changeQuantity((operator.getQuantity() + 1), operator.getId(), position, holder.plus, holder.quantity));

        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.baseUrl + "image/Products/" + operator.getProductID() + "/" + operator.getImgUrl())
                .apply(requestOptions)
                .into(holder.image);

        holder.removeBtn.setOnClickListener(v -> ((CartDetailShoppingActivity) mContext).remove(operator.getId(), operator.getProductDetailID(), position));

        holder.itemView.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, ItemDetailsShoppingActivity.class)
                    /* .putExtra("ProductId", operator.getPosId() + "")*/
                    .putExtra("ProductId", operator.getProductDetailID() + "")
                    /*.putExtra("ProductDetailId", operator.getProductDetailID())*/
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
        private final TextView minus;
        private final TextView plus;
        private final TextView quantity;
        private final TextView removeBtn;
        private final View itemView;

        public MyViewHolder(View view) {
           super(view);
            itemView = view;
            image = view.findViewById(R.id.image);
            amount = view.findViewById(R.id.amount);
            mrp = view.findViewById(R.id.mrp);
            mrp.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            discount = view.findViewById(R.id.discount);
            TextView shippingCharge = view.findViewById(R.id.shippingCharge);
            name = view.findViewById(R.id.name);
            minus = view.findViewById(R.id.minus);
            plus = view.findViewById(R.id.plus);
            quantity = view.findViewById(R.id.quantity);
            removeBtn = view.findViewById(R.id.removeBtn);
            View btnView = view.findViewById(R.id.btnView);

        }
    }


}