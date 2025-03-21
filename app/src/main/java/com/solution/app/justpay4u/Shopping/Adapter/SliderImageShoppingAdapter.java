package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Shopping.Object.DashbaordInfoBannerProducts;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.LoopingPager.LoopingPagerAdapter;

import java.util.ArrayList;

public abstract class SliderImageShoppingAdapter extends LoopingPagerAdapter<DashbaordInfoBannerProducts> {

    private final RequestOptions requestOptions;
    private final ArrayList<DashbaordInfoBannerProducts> ImageList;
    public View.OnClickListener clickListener;
    Context mContext;

    public SliderImageShoppingAdapter(Context context, ArrayList<DashbaordInfoBannerProducts> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
        mContext = context;
        ImageList = itemList;
        requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
        //requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        //requestOptions.skipMemoryCache(true);
        clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onImageClick(v, String.valueOf(v.getTag()));
            }
        };
    }

    protected abstract void onImageClick(View v, String id);

    @Override
    protected int getItemViewType(int listPosition) {
        return 0;
    }

    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {

        return LayoutInflater.from(context).inflate(R.layout.shopping_pager_item, container, false);
    }

    @Override
    protected void bindView(View convertView, int listPosition, int viewType) {
        DashbaordInfoBannerProducts listObject = ImageList.get(listPosition);
        ImageView imageView =  convertView.findViewById(R.id.imageView);

        Glide.with(mContext)
                .load(listObject.getBannerImage())
                .apply(requestOptions)
                .into(imageView);

        imageView.setTag(listObject.getRedirectUrl());
        imageView.setOnClickListener(clickListener);
    }


}
