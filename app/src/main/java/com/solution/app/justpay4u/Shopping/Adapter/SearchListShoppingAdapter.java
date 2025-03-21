package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Shopping.Response.SearchKeywordResponse;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

/**
 * Created by Asus on 20-12-2017.
 */

public class SearchListShoppingAdapter extends RecyclerView.Adapter<SearchListShoppingAdapter.MyViewHolder> {

    private final RequestOptions optionsSquareSearchImage;
    // private List<Movie> moviesList;
    ArrayList<SearchKeywordResponse> listItem;
    Context mContext;

    private ItemClickListener clickListener;

    public SearchListShoppingAdapter(Context context, ArrayList<SearchKeywordResponse> listItem) {
        mContext = context;
        this.listItem = listItem;
        optionsSquareSearchImage = new RequestOptions()
                .fitCenter()
                .timeout(65000)
                .placeholder(R.drawable.ic_history)
                .error(R.drawable.ic_history)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shopping_search_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.itemView.setTag(position);
        holder.searchText.setText(listItem.get(position).getKeyword().trim());
        holder.catText.setText(listItem.get(position).getSubcategoryName().trim());
        Glide.with(mContext)
                .load(listItem.get(position).getImage())
                .thumbnail(0.6f)
                .apply(optionsSquareSearchImage)
                .into(holder.icon);

    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public interface ItemClickListener {
        void viewClick(int position, View view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView searchText, catText;
        ImageView icon;
        View itemView;

        public MyViewHolder(View view) {
           super(view);
            itemView = view;
            catText = view.findViewById(R.id.catText);
            icon = view.findViewById(R.id.icon);
            searchText = view.findViewById(R.id.searchText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.viewClick(getAdapterPosition(), v);
        }
    }

}
