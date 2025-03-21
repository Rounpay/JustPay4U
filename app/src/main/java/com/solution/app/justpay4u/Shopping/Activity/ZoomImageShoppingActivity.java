package com.solution.app.justpay4u.Shopping.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.solution.app.justpay4u.Api.Shopping.Object.ProductDetailImageListItem;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.ProductDetailImageListShoppingAdapter;
import com.solution.app.justpay4u.Shopping.Adapter.ZoomableViewPagerShoppingAdapter;
import com.solution.app.justpay4u.Util.RecyclerViewItemDecoration;

import java.util.ArrayList;

public class ZoomImageShoppingActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    ArrayList<ProductDetailImageListItem> imageListItems = new ArrayList<>();
    ViewPager viewPager;
    ProductDetailImageListShoppingAdapter imageListAdapter;
    ZoomableViewPagerShoppingAdapter mZoomzbleViewPagerAdapter;

    private String productName, shareLink, affiliateShareLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_zoom_image);
            initToolBar();
            recyclerView = findViewById(R.id.recyclerView);
            viewPager = findViewById(R.id.viewPager);

            int selectedImagePosition = getIntent().getIntExtra("SelectIndex", 0);
            imageListItems = (ArrayList<ProductDetailImageListItem>) getIntent().getSerializableExtra("ImageList");
            shareLink = getIntent().getStringExtra("ShareLink");
            affiliateShareLink = getIntent().getStringExtra("AffiliateShareLink");
            productName = getIntent().getStringExtra("ProductName");
            mZoomzbleViewPagerAdapter = new ZoomableViewPagerShoppingAdapter(ZoomImageShoppingActivity.this, imageListItems);
            viewPager.setAdapter(mZoomzbleViewPagerAdapter);
            viewPager.setCurrentItem(selectedImagePosition);
            recyclerView.addItemDecoration(new RecyclerViewItemDecoration(14, RecyclerViewItemDecoration.HORIZONTAL, false));
            recyclerView.setLayoutManager(new LinearLayoutManager(ZoomImageShoppingActivity.this, LinearLayoutManager.HORIZONTAL, false));
            imageListAdapter = new ProductDetailImageListShoppingAdapter(this, imageListItems, true);
            recyclerView.setAdapter(imageListAdapter);
            imageListAdapter.setClickListener(new ProductDetailImageListShoppingAdapter.ItemClickListener() {
                @Override
                public void viewClick(LinearLayout blurView, RelativeLayout view, int position) {
                    // setImage(imageListItems.get(position).getImage());
                    viewPager.setCurrentItem(position);
                    selectRecyclerViewItem(position);


                }
            });

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    selectRecyclerViewItem(position);
                    recyclerView.getLayoutManager().scrollToPosition(position);

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    slideDown(recyclerView);
                }
            }, 15000);
        });
    }

    private void initToolBar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        ImageView logoIv=findViewById(R.id.image);
        setSupportActionBar(mToolbar);
       /* if (ApiShoppingUtilMethods.INSTANCE.mLogo != 0) {
            setTitle("");
            logoIv.setImageResource(ApiShoppingUtilMethods.INSTANCE.mLogo);
        } else {
            setTitle("Details");
        }*/
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    public void hideShowView() {
        if (recyclerView.getVisibility() == View.VISIBLE) {
            slideDown(recyclerView);
        } else {
            slideUp(recyclerView);
        }
    }


    // slide the view from below itself to the current position
    private void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(150);
        // animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    private void slideDown(View view) {
        view.setVisibility(View.GONE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(150);
        //animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    private void selectRecyclerViewItem(int position) {
        imageListItems.get(position).setSelected(true);
        for (int i = 0; i < imageListItems.size(); i++) {
            if (i != position) {
                imageListItems.get(i).setSelected(false);
            }
        }

        imageListAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping, menu);
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(false);
        menu.getItem(2).setVisible(false);
        menu.getItem(3).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {

            shareLink(affiliateShareLink);


        }

        return super.onOptionsItemSelected(item);
    }


    void shareLink(String link) {
        try {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = productName + "\n" + link;
            String shareSub = ApplicationConstant.INSTANCE.baseUrl;
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via " + getResources().getString(R.string.app_name) + "..."));
        } catch (Exception e) {

        }
    }


}
