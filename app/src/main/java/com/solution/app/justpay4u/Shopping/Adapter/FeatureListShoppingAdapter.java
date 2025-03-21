package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Shopping.Object.ProductInfoSpecificFeature;
import com.solution.app.justpay4u.R;

import java.util.List;

/**
 * Created by Asus on 20-12-2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class FeatureListShoppingAdapter extends RecyclerView.Adapter<FeatureListShoppingAdapter.MyViewHolder> {

    // private List<Movie> moviesList;
    List<ProductInfoSpecificFeature> listItem;
    Context mContext;

    public FeatureListShoppingAdapter(Context context, List<ProductInfoSpecificFeature> listItem) {
        mContext = context;
        this.listItem = listItem;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shopping_features_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.itemView.setTag(position);
        holder.title.setText(listItem.get(position).getKey() + " :");
        holder.feature.setText(listItem.get(position).getValue());


    }

    public void setClickListener(ItemClickListener itemClickListener) {
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public interface ItemClickListener {
        void viewClick(LinearLayout view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, feature;

        View itemView;

        public MyViewHolder(View view) {
           super(view);
            itemView = view;

            title = view.findViewById(R.id.titleText);
            feature = view.findViewById(R.id.featureText);
        }
    }

}
