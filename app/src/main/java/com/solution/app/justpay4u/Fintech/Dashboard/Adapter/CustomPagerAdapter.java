package com.solution.app.justpay4u.Fintech.Dashboard.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.solution.app.justpay4u.Api.Fintech.Object.Banners;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;


/**
 * it display the list of Images at start of app
 */


public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    int type;
    private ArrayList<Banners> ImageList;

    public CustomPagerAdapter(ArrayList<Banners> ImageList, Context context, int type) {
        this.ImageList = ImageList;
        this.mContext = context;
        this.type = type;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ImageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((CardView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);


        Glide.with(mContext)
                .load(type == 2 ? ImageList.get(position).getSiteResourceUrl() : ImageList.get(position).getResourceUrl())
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}