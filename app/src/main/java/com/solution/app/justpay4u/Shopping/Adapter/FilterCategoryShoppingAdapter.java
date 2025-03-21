package com.solution.app.justpay4u.Shopping.Adapter;

import static android.graphics.Color.TRANSPARENT;
import static android.graphics.Color.WHITE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Shopping.Object.ProductInfoFilter;
import com.solution.app.justpay4u.R;

import java.util.List;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class FilterCategoryShoppingAdapter extends RecyclerView.Adapter<FilterCategoryShoppingAdapter.MyViewHolder> {

    private final List<ProductInfoFilter> listItem;

    private ItemClickListener clickListener;
    Context mContext;

    public FilterCategoryShoppingAdapter(List<ProductInfoFilter> listItem, Context mContext) {
        this.listItem = listItem;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shopping_filter_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ProductInfoFilter item = listItem.get(position);

        holder.text.setText(item.getName());
        // setSubCategoryString(holder.sub_category,operator.getCategoryString());
        if (item.getSelected()) {
            holder.text.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.text.setBackgroundColor(WHITE);
        } else {
            holder.text.setTextColor(mContext.getResources().getColor(R.color.greycon));
            holder.text.setBackgroundColor(TRANSPARENT);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) clickListener.viewClick(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void viewClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        View itemView;

        public MyViewHolder(View view) {
           super(view);
            itemView = view;
            text =  view.findViewById(R.id.text);


        }
    }


}
