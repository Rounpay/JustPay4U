package com.solution.app.justpay4u.Shopping.Adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Shopping.Object.DashboardProductCategoryWiseList;
import com.solution.app.justpay4u.Api.Shopping.Object.DashboardProductListData;
import com.solution.app.justpay4u.Api.Shopping.Object.OtherOffer;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.MainCategoryDetailShoppingActivity;
import com.solution.app.justpay4u.Shopping.Activity.ShoppingDashboardActivity;
import com.solution.app.justpay4u.Shopping.Activity.SimilarProductShoppingActivity;
import com.solution.app.justpay4u.Util.RecyclerViewItemDecoration;

import java.util.List;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class DashboardCategoryShoppingAdapter extends RecyclerView.Adapter<DashboardCategoryShoppingAdapter.MyViewHolder> {

    private final RequestOptions requestOptions;
    private final List<DashboardProductCategoryWiseList> transactionsList;
    private final Activity mContext;
    // Allows to remember the last item shown on screen
    private final int lastPosition = -1;
    int resourceId = 0;
    RecyclerView.RecycledViewPool viewPool;
    boolean isRefreshData;
    int isLogin;

    public DashboardCategoryShoppingAdapter(Activity mContext, List<DashboardProductCategoryWiseList> transactionsList) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        viewPool = new RecyclerView.RecycledViewPool();
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder_square);
        requestOptions.error(R.drawable.placeholder_square);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        //requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        //requestOptions.skipMemoryCache(true);
    }


    public void setLoginData() {
        isRefreshData = true;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shopping_dashbaord_product, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final DashboardProductCategoryWiseList operator = transactionsList.get(position);
        if (operator.getOtherOffer() != null && operator.getOtherOffer().size() > 0) {
            if (operator.getOtherOffer().size() == 1) {
                final OtherOffer object = operator.getOtherOffer().get(0);
                holder.otherOfferRecyclerView.setVisibility(View.GONE);
                holder.otherOfferView.setVisibility(View.VISIBLE);

                holder.tvOtherOfferDescription.setText(object.getDescription());
                holder.tvOtherOfferTitle.setText(object.getName());
                Glide.with(mContext)
                        .load(object.getImage())
                        .apply(requestOptions)
                        .into(holder.otherOfferImage);
                if (object.getCashback()
                        != null && !object.getCashback().isEmpty()
                        && object.getCashbackType() != null
                        && !object.getCashbackType().isEmpty()) {
                    holder.tvOtherOfferCashBack.setVisibility(View.VISIBLE);
                    if (object.getCashbackType().equalsIgnoreCase("2")) {
                        holder.tvOtherOfferCashBack.setText("Get " + object.getCashback().replace(".00", "") + "% Cashback in Moneyfy wallet");
                    } else {
                        holder.tvOtherOfferCashBack.setText("Get \u20B9 " + object.getCashback().replace(".00", "") + " Cashback in Moneyfy wallet");
                    }

                } else {
                    holder.tvOtherOfferCashBack.setVisibility(View.GONE);
                }
                holder.otherOfferImage.setOnClickListener(v -> {
                    if (object.getOpenType().equalsIgnoreCase("0")) {
                        String appRedirectUrl = object.getURL();

                        if (appRedirectUrl.contains("{USERID}")) {
                            appRedirectUrl = appRedirectUrl.replace("{USERID}", ApiShoppingUtilMethods.INSTANCE.mUserID + "");
                        }
                        if (appRedirectUrl.contains("{CLICKID}")) {
                            appRedirectUrl = appRedirectUrl.replace("{CLICKID}", ApiShoppingUtilMethods.INSTANCE.mMobileNumber + "");
                        }
                        if (appRedirectUrl.contains("{DEVICEID}")) {
                            appRedirectUrl = appRedirectUrl.replace("{DEVICEID}", ApiShoppingUtilMethods.INSTANCE.mDeviceId);
                        }
                        if (appRedirectUrl.contains("{ANDROIDID}")) {
                            appRedirectUrl = appRedirectUrl.replace("{ANDROIDID}", ApiShoppingUtilMethods.INSTANCE.mDeviceId);
                        }
                        try {
                            mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(appRedirectUrl)));
                        } catch (ActivityNotFoundException anfe) {
                            try {
                                mContext.startActivity(new Intent(Intent.ACTION_VIEW)
                                        .setData(Uri.parse(appRedirectUrl)));
                            } catch (ActivityNotFoundException anf) {

                            }
                        } catch (Exception e) {

                        }
                    } else {

                    }
                });


            } else {
                holder.otherOfferRecyclerView.setVisibility(View.VISIBLE);
                holder.otherOfferView.setVisibility(View.GONE);
                dataparseOtherOffer(holder, operator.getOtherOffer());
            }

        } else {
            holder.otherOfferRecyclerView.setVisibility(View.GONE);
            holder.otherOfferView.setVisibility(View.GONE);
        }
        if (operator.getOtherOffer() != null) {

        } else {
            holder.otherOfferView.setVisibility(View.GONE);
        }
        holder.tvCategory.setText((operator.getTitle() + "").trim());
        if (((ShoppingDashboardActivity) mContext).manuCategoryMap != null && ((ShoppingDashboardActivity) mContext).manuCategoryMap.containsKey(operator.getMainCategoryId())) {
            setSubCategoryString(holder.sub_category, ((ShoppingDashboardActivity) mContext).manuCategoryMap.get(operator.getMainCategoryId()).getCategoryString());
        } else {
            holder.sub_category.setVisibility(View.GONE);
        }
        if (operator.getMainCategoryId() == 0) {
            holder.viewAllBtn.setVisibility(View.GONE);
        } else {
            holder.viewAllBtn.setVisibility(View.VISIBLE);
        }
        holder.viewAllBtn.setOnClickListener(v -> {

            if (operator.getMainCategoryId() == -2) {
                mContext.startActivity(new Intent(mContext, SimilarProductShoppingActivity.class)
                        .putExtra("TITLE", "Recent View Products")
                        .putExtra("SIMILAR_PRODUCT_LIST", ((ShoppingDashboardActivity) mContext).recentViewProductListData)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            } else {
                Intent intent = new Intent(mContext, MainCategoryDetailShoppingActivity.class);
                intent.putExtra("Id", operator.getMainCategoryId());
                intent.putExtra("CategoryId", 0);
                intent.putExtra("isFromNavigationDrawer", false);
                intent.putExtra("CategoryName", operator.getTitle());
                intent.putExtra("CategoryImage", ((ShoppingDashboardActivity) mContext).manuCategoryMap.get(operator.getMainCategoryId()).getMainCategoryImage());
                intent.putParcelableArrayListExtra("CategoryList", ((ShoppingDashboardActivity) mContext).manuCategoryMap.get(operator.getMainCategoryId()).getCategoryList());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);
            }
        });

        /*if (mContext instanceof ShoppingDashboardActivity && !operator.isAdsNotShow()) {
            ((ShoppingDashboardActivity) mContext).setBanner(holder.adsContainer, holder.adsView);
        }*/

        dataparse(operator.getViewType(), holder, operator.getmDashboardProductListItems(), position);
    }

    private void dataparse(String type, MyViewHolder holder,
                           List<DashboardProductListData> dashboardProductList, int position) {
        //for grid & horizontal list

        if (type.equalsIgnoreCase("Grid_Vertical")) {
            /*List<PList> dashboardNewsSort = new ArrayList<>();
            for (int i = 0; i < dashboardNews.size(); i++) {
                if (i < 4) {
                    dashboardNewsSort.add(dashboardNews.get(i));
                }
            }*/
            holder.dashboardGridAdapter = new DashboardGridShoppingAdapter(mContext,
                    dashboardProductList, R.layout.adapter_shopping_grid_item_layout_vertical);
            holder.recyclerViewGridVertical.setAdapter(holder.dashboardGridAdapter);
            holder.recyclerViewGridVertical.setVisibility(View.VISIBLE);
            holder.recyclerViewGridHorizontal.setVisibility(View.GONE);
            holder.recyclerViewHorizontal.setVisibility(View.GONE);

        } else if (type.equalsIgnoreCase("Grid_Horizontal")) {
          /*  List<PList> dashboardNewsSort = new ArrayList<>();
            for (int i = 0; i < dashboardNews.size(); i++) {
                if (i < 4) {
                    dashboardNewsSort.add(dashboardNews.get(i));
                }
            }*/
            holder.dashboardGridAdapter = new DashboardGridShoppingAdapter(mContext, dashboardProductList, R.layout.adapter_shopping_grid_item_layout_horizontal);
            holder.recyclerViewGridHorizontal.setAdapter(holder.dashboardGridAdapter);
            holder.recyclerViewGridVertical.setVisibility(View.GONE);
            holder.recyclerViewGridHorizontal.setVisibility(View.VISIBLE);
            holder.recyclerViewHorizontal.setVisibility(View.GONE);

        } else {
            holder.dashboardGridAdapter = new DashboardGridShoppingAdapter(mContext, dashboardProductList, R.layout.adapter_shopping_grid_item_layout);
            holder.recyclerViewHorizontal.setAdapter(holder.dashboardGridAdapter);
            holder.recyclerViewGridVertical.setVisibility(View.GONE);
            holder.recyclerViewGridHorizontal.setVisibility(View.GONE);
            holder.recyclerViewHorizontal.setVisibility(View.VISIBLE);
        }


        if (isRefreshData) {
            holder.dashboardGridAdapter.notifyDataSetChanged();
        }
        //for horizontal list
        /*holder.dashboardGridAdapter = new DashboardGridAdapter(mContext,dashboardNews,position,R.layout.grid_item_layout );
        holder.recyclerViewHorizontal.setAdapter(holder.dashboardGridAdapter);*/


    }

    private void dataparseOtherOffer(MyViewHolder holder, List<OtherOffer> otherOfferList) {
        OtherOfferShoppingAdapter adapter = new OtherOfferShoppingAdapter(otherOfferList, mContext, R.layout.adapter_shopping_other_offer_list_sort);
        //holder.dashboardGridAdapter.setIsLogin();
        holder.otherOfferRecyclerView.setAdapter(adapter);


    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    private void setSubCategoryString(TextView textView, String subCategoryString) {


        if (subCategoryString != null && !subCategoryString.isEmpty() && subCategoryString.length() > 0) {
            if (subCategoryString.length() > 45) {
                String str = subCategoryString.substring(0, 46);
                str = str.substring(0, str.lastIndexOf(","));
                textView.setText(str + " & More");
            } else {
                textView.setText(subCategoryString);
            }

        } else {
            textView.setVisibility(View.GONE);
        }


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCategory, sub_category, tvOtherOfferTitle, tvOtherOfferDescription, tvOtherOfferCashBack;
        public RecyclerView recyclerViewHorizontal, recyclerViewGridHorizontal, recyclerViewGridVertical, otherOfferRecyclerView;
        Button viewAllBtn;
        DashboardGridShoppingAdapter dashboardGridAdapter;
        View otherOfferView;
        CardView adsView;
        RelativeLayout adsContainer;
        ImageView otherOfferImage;

        public MyViewHolder(View view) {
           super(view);
            adsView = view.findViewById(R.id.adsView);
            adsContainer = view.findViewById(R.id.adsContainer);
            tvCategory = view.findViewById(R.id.tv_category);
            sub_category = view.findViewById(R.id.sub_category);
            recyclerViewHorizontal = view.findViewById(R.id.recyclerViewHorizontal);
            recyclerViewGridHorizontal = view.findViewById(R.id.recyclerViewGridHorizontal);
            recyclerViewGridVertical = view.findViewById(R.id.recyclerViewGridVertical);
            otherOfferRecyclerView = view.findViewById(R.id.otherOfferRecyclerView);
            otherOfferRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            viewAllBtn = view.findViewById(R.id.viewAllBtn);
            otherOfferView = view.findViewById(R.id.otherOfferView);
            tvOtherOfferDescription = view.findViewById(R.id.tv_other_offer_description);
            tvOtherOfferCashBack = view.findViewById(R.id.tv_other_offer_cashback);
            tvOtherOfferTitle = view.findViewById(R.id.tv_other_offer_title);
            otherOfferImage = view.findViewById(R.id.offerImage);

            recyclerViewGridVertical.addItemDecoration(new RecyclerViewItemDecoration(8, RecyclerViewItemDecoration.GRID, false));
            recyclerViewGridVertical.setLayoutManager(new GridLayoutManager(mContext, 2));

            recyclerViewGridHorizontal.addItemDecoration(new RecyclerViewItemDecoration(8, RecyclerViewItemDecoration.HORIZONTAL, true));
            recyclerViewGridHorizontal.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

            recyclerViewHorizontal.addItemDecoration(new RecyclerViewItemDecoration(8, RecyclerViewItemDecoration.HORIZONTAL, true));
            recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

            //for horizontal list
          /*  recyclerViewHorizontal.addItemDecoration(new RecyclerViewItemDecoration(8, RecyclerViewItemDecoration.HORIZONTAL, true));
           recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));*/

        }
    }

}
