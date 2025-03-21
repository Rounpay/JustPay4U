package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Shopping.Object.ProductInfoFilter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.RecyclerViewItemDecoration;

import java.util.ArrayList;

public class FeatureCategoryListShoppingAdapter extends RecyclerView.Adapter<FeatureCategoryListShoppingAdapter.ViewHolder> {


    // private List<Movie> moviesList;

    private final Context mContext;
    RecyclerView.RecycledViewPool viewPool;
    private ArrayList<ProductInfoFilter> listItems = new ArrayList<>();

    public FeatureCategoryListShoppingAdapter(Context context, ArrayList<ProductInfoFilter> listItems) {


        this.listItems = listItems;
        mContext = context;
        viewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shopping_feature_category_horizontal_view, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {

        final ProductInfoFilter object = listItems.get(listPosition);

        holder.title.setText(object.getName() + " :");
        //holder.value.setText(object.getValue());
        if (object.getName().toLowerCase().contains("color")) {
            holder.mFeatureCategoryImageTextAdapter = new FeatureCategoryImageTextShoppingAdapter(mContext, object.getOptionLists(), true, holder.value);
        } else {
            holder.mFeatureCategoryImageTextAdapter = new FeatureCategoryImageTextShoppingAdapter(mContext, object.getOptionLists(), false, holder.value);

        }
        holder.recyclerView.setAdapter(holder.mFeatureCategoryImageTextAdapter);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        View itemView;
        TextView title, value;
        RecyclerView recyclerView;
        FeatureCategoryImageTextShoppingAdapter mFeatureCategoryImageTextAdapter;

        public ViewHolder(View view) {
           super(view);
            itemView = view;
            title =  view.findViewById(R.id.title);
            value =  view.findViewById(R.id.value);
            recyclerView =  view.findViewById(R.id.recyclerView);
            recyclerView.addItemDecoration(new RecyclerViewItemDecoration(8, RecyclerViewItemDecoration.HORIZONTAL, false));
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setNestedScrollingEnabled(true);
            recyclerView.setRecycledViewPool(viewPool);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

        }
    }


}
