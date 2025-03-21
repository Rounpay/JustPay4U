package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Shopping.Object.ProductDetailImageListItem;
import com.solution.app.justpay4u.R;

import java.util.List;

/**
 * Created by Asus on 20-12-2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class ProductDetailImageListShoppingAdapter extends RecyclerView.Adapter<ProductDetailImageListShoppingAdapter.MyViewHolder> {

    private final RequestOptions requestOptions;
    // private List<Movie> moviesList;
    List<ProductDetailImageListItem> imageListItem;
    Context mContext;
    boolean zoomableList;
    private ItemClickListener clickListener;

    public ProductDetailImageListShoppingAdapter(Context context, List<ProductDetailImageListItem> imageListItem, boolean zoomableList) {
        mContext = context;
        this.imageListItem = imageListItem;
        this.zoomableList = zoomableList;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder_square);
        requestOptions.error(R.drawable.placeholder_square);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        //requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        //requestOptions.skipMemoryCache(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shopping_product_detail_image, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null)
                    clickListener.viewClick(holder.blurView, holder.bgView, position);
            }
        });
        holder.itemView.setTag(position);

        Glide.with(mContext)
                .load(imageListItem.get(position).getImageSmall())
                .thumbnail(0.7f)
                .apply(requestOptions)
                .into(holder.image);
        if (imageListItem.get(position).isSelected()) {
            holder.itemView.setClickable(false);
            holder.bgView.setBackgroundResource(R.drawable.rounded_image_selected);
        } else {
            holder.itemView.setClickable(true);
            holder.bgView.setBackgroundResource(R.drawable.rounded_image_not_selected);
        }

        if (!zoomableList) {
            if (imageListItem.size() > 4 && position == 3) {
                holder.blurView.setVisibility(View.VISIBLE);
                holder.counText.setText(imageListItem.size() - 4 + "+");
            } else {
                holder.blurView.setVisibility(View.GONE);
            }
        }

    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {

        if (zoomableList) {
            return imageListItem.size();
        } else {

            if (imageListItem.size() > 4) {
                return 4;
            } else {
                return imageListItem.size();
            }
        }

    }

    public interface ItemClickListener {
        void viewClick(LinearLayout blurView, RelativeLayout view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView counText;
        LinearLayout blurView;
        RelativeLayout bgView;
        View itemView;

        public MyViewHolder(View view) {
           super(view);
            itemView = view;
            bgView = view.findViewById(R.id.bgView);
            image = view.findViewById(R.id.image);
            counText = view.findViewById(R.id.countText);
            blurView = view.findViewById(R.id.blurView);

            if (zoomableList) {
                int height = (int) mContext.getResources().getDimension(R.dimen._85sdp);
                int width = (int) mContext.getResources().getDimension(R.dimen._70sdp);
                bgView.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
            }

        }
    }

}
