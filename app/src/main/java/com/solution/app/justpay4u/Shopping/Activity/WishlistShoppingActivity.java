package com.solution.app.justpay4u.Shopping.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Shopping.Object.DashboardProductListData;
import com.solution.app.justpay4u.Api.Shopping.Response.BasicApiResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.MainCategoryWiseProductResponse;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.WishListShoppingAdapter;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;

public class WishlistShoppingActivity extends AppCompatActivity {
    private final ArrayList<DashboardProductListData> mWishLists = new ArrayList<>();
    public CustomAlertDialog mCustomAlertDialog;
    public CustomLoader loader;
    TextView textCartItemCount;
    int mCartItemCount = 10;
    WishListShoppingAdapter mWishListAdapter;
    RecyclerView mRecyclerView;
    View noDataView, noNetworkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_wishlist);


            mCustomAlertDialog = new CustomAlertDialog(this);
            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            TextView errorMsg = findViewById(R.id.errorMsg);
            errorMsg.setText("Product not added in Wishlist");

            mRecyclerView = findViewById(R.id.recyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mWishListAdapter = new WishListShoppingAdapter(this, mWishLists);
            mRecyclerView.setAdapter(mWishListAdapter);

            //removable


            getWishListApi();
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCartItemCount = ApiShoppingUtilMethods.INSTANCE.getCartCount(this);
        setupBadge();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping, menu);
        menu.getItem(0).setVisible(true);
        menu.getItem(1).setVisible(false);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount =  actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_search) {
            startActivity(new Intent(WishlistShoppingActivity.this, SearchResultShoppingActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

        } else if (id == R.id.action_cart) {

            Intent intent = new Intent(WishlistShoppingActivity.this, CartDetailShoppingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);


        }

        return super.onOptionsItemSelected(item);
    }


    public void getWishListApi() {

        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            if (!loader.isShowing()) {
                loader.show();
            }
            ApiShoppingUtilMethods.INSTANCE.GetWishListProducts(this, new ApiShoppingUtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(Object response) {
                    loader.dismiss();
                    MainCategoryWiseProductResponse data = (MainCategoryWiseProductResponse) response;
                    if (data.getMainCategoryWiseProductData().size() > 0) {
                        mWishLists.clear();
                        mWishLists.addAll(data.getMainCategoryWiseProductData());
                        mWishListAdapter.notifyDataSetChanged();
                    } else {
                        hideShowErrorView(View.VISIBLE, View.GONE);
                    }
                }

                @Override
                public void onError(int ErrorType, String msg) {
                    mCustomAlertDialog.Error(msg + "");
                    loader.dismiss();
                    if (ErrorType == ApiShoppingUtilMethods.INSTANCE.ERRORR_NETWORK) {
                        hideShowErrorView(View.GONE, View.VISIBLE);
                    } else {
                        hideShowErrorView(View.VISIBLE, View.GONE);
                    }
                }
            });

        } else {
            ApiShoppingUtilMethods.INSTANCE.NetworkError(WishlistShoppingActivity.this);
            hideShowErrorView(View.GONE, View.VISIBLE);
        }
    }

    void hideShowErrorView(int noDataViewVisibility, int noNetworkVisibility) {

        noDataView.setVisibility(noDataViewVisibility);
        noNetworkView.setVisibility(noNetworkVisibility);
    }

    public void RemoveWishList(final int position, String posId) {
        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(WishlistShoppingActivity.this)) {

            ApiShoppingUtilMethods.INSTANCE.WishListAddRemove(WishlistShoppingActivity.this, true, posId,
                    new ApiShoppingUtilMethods.ApiHitCallBack() {
                        @Override
                        public void onSucess(Object response) {
                            mWishLists.remove(position);
                            mWishListAdapter.notifyDataSetChanged();
                            if (mWishLists.size() == 0) {
                                hideShowErrorView(View.VISIBLE, View.GONE);
                            }
                            Toast.makeText(WishlistShoppingActivity.this, ((BasicApiResponse) response).getMessage() + "", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(int ErrorType, String msg) {
                            mCustomAlertDialog.Error(msg);
                            if (mWishLists.size() == 0) {
                                if (ErrorType == ApiShoppingUtilMethods.INSTANCE.ERRORR_NETWORK) {
                                    hideShowErrorView(View.GONE, View.VISIBLE);
                                } else {
                                    hideShowErrorView(View.VISIBLE, View.GONE);
                                }
                            }
                        }
                    });

            /*try {
                loader.show();
                EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
                Call<BasicResponse> call = git.RemoveFromWishlist(userId, posId);
                call.enqueue(new Callback<BasicResponse>() {
                    @Override
                    public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                        if (response != null) {
                            try {
                                loader.dismiss();
                                BasicResponse data = response.body();
                                if (data != null) {
                                    if (data.getStatus().equalsIgnoreCase("1")) {
                                        mWishLists.remove(position);
                                        mWishListAdapter.notifyDataSetChanged();
                                        if(mWishLists.size()==0){
                                            hideShowErrorView(View.VISIBLE, View.GONE);
                                        }
                                    } else {
                                        if(mWishLists.size()==0){
                                            hideShowErrorView(View.VISIBLE, View.GONE);
                                        }
                                        mCustomAlertDialog.Error(true, data.getMessage());

                                    }

                                } else {
                                    mCustomAlertDialog.Error(true, getString(R.string.something_error));
                                }
                            } catch (Exception ex) {
                                mCustomAlertDialog.Error(true, getString(R.string.something_error));

                            }
                        } else {
                            loader.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<BasicResponse> call, Throwable t) {


                        try {
                            loader.dismiss();
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                                if (t.getMessage().contains("No address associated with hostname")) {
                                    mCustomAlertDialog.Error(true, getString(R.string.err_msg_network));


                                } else {
                                    mCustomAlertDialog.Error(true, t.getMessage());

                                }

                            } else {
                                mCustomAlertDialog.Error(true, getString(R.string.something_error));

                            }
                        } catch (IllegalStateException ise) {
                            mCustomAlertDialog.Error(true, getString(R.string.something_error));

                        }
                    }
                });
            } catch (Exception ex) {
                loader.dismiss();
                mCustomAlertDialog.Error(true, getString(R.string.something_error));

            }*/
        } else {
            ApiShoppingUtilMethods.INSTANCE.NetworkError(this);

        }
    }


}
