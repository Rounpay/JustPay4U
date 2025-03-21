package com.solution.app.justpay4u.Shopping.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.solution.app.justpay4u.Api.Shopping.Object.MainCategoryDetailTopHorizontalListItem;
import com.solution.app.justpay4u.Api.Shopping.Object.OtherOfferList;
import com.solution.app.justpay4u.Api.Shopping.Response.OfferListResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ShoppingEndPointInterface;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.FragmentViewPagerShoppingAdapter;
import com.solution.app.justpay4u.Shopping.Adapter.MainCategoryDetailTopHorizonalListShoppingAdapter;
import com.solution.app.justpay4u.Shopping.Fragment.OtherOfferFragmentShopping;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.RecyclerViewItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class OtherOfferShoppingActivity extends AppCompatActivity {

    public int selectedPosition = 0;
    View noDataView, noNetworkView;
    View retryBtn;
    CustomAlertDialog mCustomAlertDialog;
    CustomLoader mCustomLoader;
    ArrayList<MainCategoryDetailTopHorizontalListItem> topHorizontalListItem = new ArrayList<>();
    MainCategoryDetailTopHorizonalListShoppingAdapter mAdapter;
    private RecyclerView tabRecyclerView;
    private ViewPager viewPager;
    private FragmentViewPagerShoppingAdapter mFragmentViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCustomLoader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        mCustomLoader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_other_offer);
            initToolBar();
            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            retryBtn = findViewById(R.id.retryBtn);
            TextView errorMsg = findViewById(R.id.errorMsg);
            errorMsg.setText("Currently no offer.");
            tabRecyclerView = findViewById(R.id.tabRecyclerView);
            tabRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            tabRecyclerView.addItemDecoration(new RecyclerViewItemDecoration((int) getResources().getDimension(R.dimen._10sdp), RecyclerViewItemDecoration.HORIZONTAL, false));
            mAdapter = new MainCategoryDetailTopHorizonalListShoppingAdapter(topHorizontalListItem, this);
            tabRecyclerView.setAdapter(mAdapter);
            viewPager = findViewById(R.id.viewPager);
            mFragmentViewPagerAdapter = new FragmentViewPagerShoppingAdapter(getSupportFragmentManager());


            mCustomAlertDialog = new CustomAlertDialog(this);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {


                    topHorizontalListItem.get(selectedPosition).setSelected(false);
                    topHorizontalListItem.get(position).setSelected(true);
                    mAdapter.notifyDataSetChanged();
                    selectedPosition = position;
                    tabRecyclerView.getLayoutManager().scrollToPosition(position);

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            getOfferApi();
        });
    }

    private void initToolBar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        ImageView logoIv = findViewById(R.id.image);
        setSupportActionBar(mToolbar);
        /*if (ApiShoppingUtilMethods.INSTANCE.mLogo != 0) {
            setTitle("");
            logoIv.setVisibility(View.VISIBLE);
            logoIv.setImageResource(ApiShoppingUtilMethods.INSTANCE.mLogo);
        } else {
            logoIv.setVisibility(View.GONE);
            setTitle("Offers");
        }*/
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setDataInUI(List<OtherOfferList> data) {

        mFragmentViewPagerAdapter.removeFragment();
        topHorizontalListItem.clear();

        for (int i = 0; i < data.size(); i++) {
            OtherOfferFragmentShopping mOtherOfferFragment = new OtherOfferFragmentShopping();
            Bundle arg = new Bundle();
            arg.putSerializable("OtherOfferList", (Serializable) data.get(i).getOtherOffer());
            mOtherOfferFragment.setArguments(arg);
            mFragmentViewPagerAdapter.addFragment(mOtherOfferFragment, "" + data.get(i).getAffiliateType());

            if (i == 0) {
                topHorizontalListItem.add(new MainCategoryDetailTopHorizontalListItem(i, data.get(i).getAffiliateType(), true));

            } else {
                topHorizontalListItem.add(new MainCategoryDetailTopHorizontalListItem(i, data.get(i).getAffiliateType(), false));
            }


        }

        viewPager.setAdapter(mFragmentViewPagerAdapter);
        viewPager.setOffscreenPageLimit(topHorizontalListItem.size());

        setItemSelect(selectedPosition);
    }

    public void setItemSelect(int position) {
        topHorizontalListItem.get(selectedPosition).setSelected(false);
        topHorizontalListItem.get(position).setSelected(true);
        mAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(position);
        selectedPosition = position;
    }


    public void getOfferApi() {

        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            try {
                if (!mCustomLoader.isShowing()) {
                    mCustomLoader.show();
                }
                ShoppingEndPointInterface git = ApiClient.getClient().create(ShoppingEndPointInterface.class);
                Call<OfferListResponse> call = git.getOtherOfferDetails(ApiShoppingUtilMethods.INSTANCE.mUserID);
                call.enqueue(new Callback<OfferListResponse>() {
                    @Override
                    public void onResponse(Call<OfferListResponse> call, final retrofit2.Response<OfferListResponse> response) {

                        if (response != null) {
                            try {
                                mCustomLoader.dismiss();
                                OfferListResponse data = response.body();
                                if (data != null) {
                                    if (data.getStatus().equalsIgnoreCase("1")) {
                                        if (data.getOtherOfferList() != null && data.getOtherOfferList().size() > 0) {
                                            setDataInUI(data.getOtherOfferList());

                                        } else {
                                            hideShowErrorView(View.VISIBLE, View.GONE);
                                        }
                                    } else {
                                        mCustomAlertDialog.Error(data.getMessage());
                                        hideShowErrorView(View.VISIBLE, View.GONE);
                                    }

                                } else {
                                    hideShowErrorView(View.VISIBLE, View.GONE);
                                }
                            } catch (Exception ex) {
                                hideShowErrorView(View.VISIBLE, View.GONE);

                            }
                        } else {
                            mCustomLoader.dismiss();
                            hideShowErrorView(View.VISIBLE, View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<OfferListResponse> call, Throwable t) {


                        try {
                            mCustomLoader.dismiss();
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                                if (t.getMessage().contains("No address associated with hostname")) {
                                    ApiShoppingUtilMethods.INSTANCE.NetworkError(OtherOfferShoppingActivity.this);
                                    hideShowErrorView(View.GONE, View.VISIBLE);

                                } else {
                                    mCustomAlertDialog.Error(t.getMessage() + "");
                                    hideShowErrorView(View.VISIBLE, View.GONE);
                                }

                            } else {
                                mCustomAlertDialog.Error(getString(R.string.some_thing_error));
                                hideShowErrorView(View.VISIBLE, View.GONE);
                            }
                        } catch (IllegalStateException ise) {
                            mCustomAlertDialog.Error(ise.getMessage() + "");
                            hideShowErrorView(View.VISIBLE, View.GONE);
                        }
                    }
                });
            } catch (Exception ex) {
                mCustomLoader.dismiss();
                mCustomAlertDialog.Error(ex.getMessage() + "");
                hideShowErrorView(View.VISIBLE, View.GONE);
            }
        } else {
            ApiShoppingUtilMethods.INSTANCE.NetworkError(OtherOfferShoppingActivity.this);
            hideShowErrorView(View.GONE, View.VISIBLE);
        }
    }

    void hideShowErrorView(int noDataViewVisibility, int noNetworkVisibility) {

        noDataView.setVisibility(noDataViewVisibility);
        noNetworkView.setVisibility(noNetworkVisibility);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
   /* void setTabData(List<OtherOfferList> data){

        for(int i=0;i<data.size();i++){
            if(i==0) {
                topHorizontalListItem.add(new MainCategoryDetailTopHorizontalListItem(i, data.get(i).getAffiliateType(), true));
            }else{
                topHorizontalListItem.add(new MainCategoryDetailTopHorizontalListItem(i, data.get(i).getAffiliateType(), false));
            }
        }
        mAdapter.notifyDataSetChanged();

        setDataInUI(data);
    }*/
}
