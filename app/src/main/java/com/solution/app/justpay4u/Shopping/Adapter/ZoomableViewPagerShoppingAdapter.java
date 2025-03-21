package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.solution.app.justpay4u.Api.Shopping.Object.ProductDetailImageListItem;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.ZoomImageShoppingActivity;

import java.util.ArrayList;

public class ZoomableViewPagerShoppingAdapter extends PagerAdapter {

    private final RequestOptions optionsRectangleImage;
    private final ArrayList<ProductDetailImageListItem> imageList;
    private final LayoutInflater inflater;
    private final Context context;


    public ZoomableViewPagerShoppingAdapter(Context context, ArrayList<ProductDetailImageListItem> imageList) {
        this.context = context;
        this.imageList = imageList;
        inflater = LayoutInflater.from(context);
        optionsRectangleImage = new RequestOptions()
                .fitCenter()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .timeout(65000)
                .placeholder(R.drawable.placeholder_square)
                .error(R.drawable.defaultlogo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.adapter_shopping_zoom_image_view, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout.findViewById(R.id.image);

        Glide.with(context)
                .load(imageList.get(position).getImageBig())
                .apply(optionsRectangleImage)
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof ZoomImageShoppingActivity) {
                    ((ZoomImageShoppingActivity) context).hideShowView();
                }
            }
        });
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
