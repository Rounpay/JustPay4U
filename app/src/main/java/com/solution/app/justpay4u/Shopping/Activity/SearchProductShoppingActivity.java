package com.solution.app.justpay4u.Shopping.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.solution.app.justpay4u.Api.Shopping.Object.DashboardProductListData;
import com.solution.app.justpay4u.Api.Shopping.Object.ProductInfoFilter;
import com.solution.app.justpay4u.Api.Shopping.Object.ProductInfoFilterOptionList;
import com.solution.app.justpay4u.Api.Shopping.Response.AllProductListResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.GetAllFilterResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.SearchKeywordResponse;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.SearchProductListShoppingAdapter;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;

public class SearchProductShoppingActivity extends AppCompatActivity {

    private final ArrayList<DashboardProductListData> mProductDetailsList = new ArrayList<>();
    private final int INTENT_FILTER = 562;
    private final int LOGIN_ACTIVITY_RESULT = 892;
    SearchKeywordResponse searchKeywordResponse;
    TextView textCartItemCount;
    int mCartItemCount = 10;
    SearchProductListShoppingAdapter mSearchProductListAdapter;
    RecyclerView mRecyclerView;
    CustomLoader loader;
    int pageIndex;
    int totalListSize;
    ArrayList<ProductInfoFilter> filterList = new ArrayList<>();
    ArrayList<ProductInfoFilterOptionList> selectedFilterList = new ArrayList<>();
    ArrayList<String> filterOptionTypeIdList = new ArrayList<>();
    int selectedSortingValue;
    private View noDataView;
    private View noNetworkView;
    private boolean userScrolled;
    private int visibleItemCount, totalItemCount, pastVisiblesItems;
    private boolean isLoading;
    private int intentSubCategoryId, intentKeywordId;
    private String sortOrderBy, sortOrderByType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_search_product);

            searchKeywordResponse = (SearchKeywordResponse) getIntent().getSerializableExtra("SCREEN_DATA");


            if (searchKeywordResponse != null) {
                setTitle(searchKeywordResponse.getKeyword() + "");
            } else {
                setTitle("Search Result");
            }
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            loader.setCancelable(false);
            View filterSortView = findViewById(R.id.filterSortView);
            mRecyclerView = findViewById(R.id.recyclerView);
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            View retryBtn = findViewById(R.id.retryBtn);
            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            TextView errorMsg = findViewById(R.id.errorMsg);
            errorMsg.setText("You have not search any product");

            mSearchProductListAdapter = new SearchProductListShoppingAdapter(this, mProductDetailsList);
            mRecyclerView.setAdapter(mSearchProductListAdapter);

            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        userScrolled = true;
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx,
                                       int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (userScrolled && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                        userScrolled = false;

                        if (mProductDetailsList.size() < totalListSize && !isLoading) {
                            if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(SearchProductShoppingActivity.this)) {
                                isLoading = true;
                                pageIndex = mProductDetailsList.size();
                                mProductDetailsList.add(null);
                                mSearchProductListAdapter.notifyItemInserted(mProductDetailsList.size() - 1);
                                HitApi();
                            } else {
                                //show dialog for network error
                                // TODO Show network error dialog
                            }
                        }

                    }
                }
            });
            retryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HitApi();
                }
            });
            if (searchKeywordResponse != null) {
                intentSubCategoryId = searchKeywordResponse.getSubcategoryId();
                intentKeywordId = searchKeywordResponse.getKeywordId();
                sortOrderBy = "New";
                sortOrderByType = "a";
                pageIndex = 0;
                HitApi();
            } else {
                noDataView.setVisibility(View.VISIBLE);
            }
            findViewById(R.id.filterView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (filterList != null && filterList.size() > 0) {
                        startActivityForResult(new Intent(SearchProductShoppingActivity.this, FilterShoppingActivity.class)
                                .putExtra("SelectedTabIndex", 0)
                                .putExtra("SelectedFilterList", selectedFilterList != null && selectedFilterList.size() > 0 ? selectedFilterList : new ArrayList<>())
                                .putExtra("FilterList", filterList != null && filterList.size() > 0 ? filterList : new ArrayList<>())
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_FILTER);
                    }
                }
            });

            findViewById(R.id.sortView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSortingDialog();
                }
            });
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
      /*  menu.getItem(0).setVisible(true);
        menu.getItem(1).setVisible(false);*/

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

        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (id == R.id.action_cart) {

            Intent intent = new Intent(SearchProductShoppingActivity.this, CartDetailShoppingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);


        }

        return super.onOptionsItemSelected(item);
    }


    public void cartUpdate(int count) {
        mCartItemCount = count;
        setupBadge();
    }


    void hideShowErrorView(int noDataViewVisibility, int noNetworkVisibility) {
        if (mProductDetailsList.size() == 0) {
            noDataView.setVisibility(noDataViewVisibility);
            noNetworkView.setVisibility(noNetworkVisibility);
        }
    }


    void showSortingDialog() {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);

        View sheetView = getLayoutInflater().inflate(R.layout.dialog_shopping_sorting, null);

        final RelativeLayout newestFirstView = sheetView.findViewById(R.id.newestFirst);
        final RelativeLayout lowToHighView = sheetView.findViewById(R.id.lowToHeigh);
        final RelativeLayout highToLowView = sheetView.findViewById(R.id.highToLow);
        final RadioButton newestRadioBtn = sheetView.findViewById(R.id.newestRadioBtn);
        final RadioButton lowToHighRadioBtn = sheetView.findViewById(R.id.lowToHeighRadioBtn);
        final RadioButton highToLowRadioBtn = sheetView.findViewById(R.id.heighToLowRadioBtn);


        selectedSorting(newestRadioBtn, lowToHighRadioBtn, highToLowRadioBtn, newestFirstView, lowToHighView, highToLowView);


        newestFirstView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!newestRadioBtn.isChecked()) {
                    selectedSortingValue = 1;
                    selectedSorting(newestRadioBtn, lowToHighRadioBtn, highToLowRadioBtn, newestFirstView, lowToHighView, highToLowView);
                    sortOrderBy = "New";
                    sortOrderByType = "a";
                    pageIndex = 0;
                    HitApi();
                }
                mBottomSheetDialog.dismiss();
            }
        });
        lowToHighView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lowToHighRadioBtn.isChecked()) {
                    selectedSortingValue = 2;
                    selectedSorting(newestRadioBtn, lowToHighRadioBtn, highToLowRadioBtn, newestFirstView, lowToHighView, highToLowView);
                    sortOrderBy = "Price";
                    sortOrderByType = "a";
                    pageIndex = 0;
                    HitApi();
                }
                mBottomSheetDialog.dismiss();
            }
        });
        highToLowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!highToLowRadioBtn.isChecked()) {
                    selectedSortingValue = 3;
                    selectedSorting(newestRadioBtn, lowToHighRadioBtn, highToLowRadioBtn, newestFirstView, lowToHighView, highToLowView);
                    sortOrderBy = "Price";
                    sortOrderByType = "d";
                    pageIndex = 0;
                    HitApi();
                }
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
    }


    void selectedSorting(RadioButton newestRadioBtn, RadioButton lowToHighRadioBtn, RadioButton highToLowRadioBtn,
                         RelativeLayout newestFirstView, RelativeLayout lowToHighView, RelativeLayout highToLowView) {
        if (selectedSortingValue == 1) {
            newestRadioBtn.setChecked(true);
            lowToHighRadioBtn.setChecked(false);
            highToLowRadioBtn.setChecked(false);
            newestFirstView.setBackgroundResource(R.drawable.rounded_focused);
            lowToHighView.setBackgroundResource(R.drawable.rounded_unfocused);
            highToLowView.setBackgroundResource(R.drawable.rounded_unfocused);
        } else if (selectedSortingValue == 2) {
            newestRadioBtn.setChecked(false);
            lowToHighRadioBtn.setChecked(true);
            highToLowRadioBtn.setChecked(false);
            newestFirstView.setBackgroundResource(R.drawable.rounded_unfocused);
            lowToHighView.setBackgroundResource(R.drawable.rounded_focused);
            highToLowView.setBackgroundResource(R.drawable.rounded_unfocused);
        } else if (selectedSortingValue == 3) {
            newestRadioBtn.setChecked(false);
            lowToHighRadioBtn.setChecked(false);
            highToLowRadioBtn.setChecked(true);
            newestFirstView.setBackgroundResource(R.drawable.rounded_unfocused);
            lowToHighView.setBackgroundResource(R.drawable.rounded_unfocused);
            highToLowView.setBackgroundResource(R.drawable.rounded_focused);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_FILTER && resultCode == RESULT_OK) {
            selectedFilterList = (ArrayList<ProductInfoFilterOptionList>) data.getSerializableExtra("SelectedFilter");
            pageIndex = 0;
            filterOptionTypeIdList = (ArrayList<String>) data.getSerializableExtra("SelectedStringFilter");
            HitApi();

        }
        if (requestCode == LOGIN_ACTIVITY_RESULT && resultCode == RESULT_OK) {
            pageIndex = 0;
            totalListSize = 0;
            //  mSearchProductListAdapter.setLoginPref();
            HitApi();
        }

    }


    private void HitApi() {
        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(SearchProductShoppingActivity.this)) {

            if (pageIndex == 0) {
                loader.show();
            }
            ApiShoppingUtilMethods.INSTANCE.getAllPlroductList(SearchProductShoppingActivity.this, filterOptionTypeIdList, "S", intentSubCategoryId, intentKeywordId + "", sortOrderBy, sortOrderByType, pageIndex, new ApiShoppingUtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(final Object response) {
                    isLoading = false;
                    if (loader != null) {
                        loader.dismiss();
                    }
                    final AllProductListResponse mAllProductListResponse = (AllProductListResponse) response;
                    totalListSize = mAllProductListResponse.getData().getTotalRecords();
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {

                            ShowContent(mAllProductListResponse);
                        }
                    });
                }

                @Override
                public void onError(int ErrorType, String msg) {
                    isLoading = false;
                    if (loader != null) {
                        loader.dismiss();
                    }
                    if (pageIndex == 0) {
                        mProductDetailsList.clear();
                        mSearchProductListAdapter.notifyDataSetChanged();
                        if (ErrorType == ApiShoppingUtilMethods.INSTANCE.ERRORR_NETWORK) {
                            noNetworkView.setVisibility(View.VISIBLE);
                            noDataView.setVisibility(View.GONE);
                        } else {
                            noNetworkView.setVisibility(View.GONE);
                            noDataView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            HitFilterApi("S", intentSubCategoryId);
        } else {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        }
    }

    private void ShowContent(AllProductListResponse response) {
        hideShowErrorView(View.GONE, View.GONE);
        if (pageIndex == 0) {
            mProductDetailsList.clear();
        }

        if (pageIndex != 0) {
            mProductDetailsList.remove(mProductDetailsList.size() - 1);
            mSearchProductListAdapter.notifyItemRemoved(mProductDetailsList.size());
        }

        mProductDetailsList.addAll(response.getData().getProductSetList());
        mSearchProductListAdapter.notifyDataSetChanged();

        if (mProductDetailsList.size() > 0) {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.GONE);

        } else {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
        }
        loader.dismiss();


    }

    private void HitFilterApi(String filterType, int filterTypeId) {
        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(SearchProductShoppingActivity.this)) {

            ApiShoppingUtilMethods.INSTANCE.getAllFilterList(SearchProductShoppingActivity.this, filterType, filterTypeId, new ApiShoppingUtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(final Object response) {

                    final GetAllFilterResponse mGetAllFilterResponse = (GetAllFilterResponse) response;
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {


                            filterList = mGetAllFilterResponse.getData().getFilterLists();

                        }
                    });
                }

                @Override
                public void onError(int ErrorType, String msg) {

                }
            });
           /* UtilMethods.INSTANCE.MainCategoryDetail(getActivity(), mId, CatId, index, new UtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(final Object response) {
                    if (loader != null) {

                        loader.setVisibility(View.GONE);
                    }


                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {

                            ShowContent((MainCategoryDetailResponse) response);
                        }
                    });
                }

                @Override
                public void onError(int ErrorType, String msg) {
                    if (loader != null) {

                        loader.setVisibility(View.GONE);
                    }
                    if (ErrorType == UtilMethods.INSTANCE.ERRORR_NETWORK) {
                        noNetworkView.setVisibility(View.VISIBLE);
                        noDataView.setVisibility(View.GONE);
                    } else {
                        noNetworkView.setVisibility(View.GONE);
                        noDataView.setVisibility(View.VISIBLE);
                    }
                }
            });*/

        } else {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        }
    }

}
