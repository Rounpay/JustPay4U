package com.solution.app.justpay4u.Shopping.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.solution.app.justpay4u.Api.Shopping.Object.DashboardProductListData;
import com.solution.app.justpay4u.Api.Shopping.Object.FilterList;
import com.solution.app.justpay4u.Api.Shopping.Object.ProductInfoFilterOptionList;
import com.solution.app.justpay4u.Api.Shopping.Response.AllProductListResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.GetAllFilterResponse;
import com.solution.app.justpay4u.Api.Shopping.Response.MainCategoryDetailResponse;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.MainCategoryDetailShoppingActivity;
import com.solution.app.justpay4u.Shopping.Adapter.RelatedProductShoppingAdapter;
import com.solution.app.justpay4u.Util.SpacesItemDecoration;

import java.util.ArrayList;

public class FragmentMainCategoryDetailShopping extends Fragment {

    ArrayList<DashboardProductListData> mRelatedProducts = new ArrayList<>();
    RecyclerView recyclerView;
    RelatedProductShoppingAdapter relatedProductAdapter;
    int categoryId = 0, subCategoryId;
    ProgressBar loader;
    int index = 0;
    String categoryName = "";
    int mainCategoryId = 0;
    boolean isFromNavigationDrawer;
    View noDataView, noNetworkView, retryBtn;
    ArrayList<String> filterOptionTypeIdList = new ArrayList<>();
    int pastVisiblesItems, visibleItemCount;
    View loaderView;
    private String sortOrderBy = "New", sortOrderByType = "a";
    private int startPagingIndex = 0;
    private boolean userScrolled, isLoading;
    private int totalItemCount;
    private int totalListSize;
    private boolean isFilterApply;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_main_category_detail, container, false);
        loader = view.findViewById(R.id.loader);

        noDataView = view.findViewById(R.id.noDataView);
        noNetworkView = view.findViewById(R.id.noNetworkView);
        retryBtn = view.findViewById(R.id.retryBtn);
        loaderView = view.findViewById(R.id.loaderView);
        recyclerView =  view.findViewById(R.id.recyclerView);

        relatedProductAdapter = new RelatedProductShoppingAdapter(getActivity(), mRelatedProducts, R.layout.adapter_shopping_category_detail_grid);
        mRelatedProducts.addAll((ArrayList<DashboardProductListData>) getArguments().getSerializable("RelatedProductList"));
        isFromNavigationDrawer = getArguments().getBoolean("isFromNavigationDrawer", false);
        categoryId = getArguments().getInt("CategoryId", 0);
        subCategoryId = getArguments().getInt("SubCategoryId", 0);
        mainCategoryId = getArguments().getInt("MainCategoryId", 0);
        index = getArguments().getInt("Index", 0);
        categoryName = getArguments().getString("CategoryName", "");

        int spacingInPixels = getActivity().getResources().getDimensionPixelSize(R.dimen._4sdp);
        recyclerView.addItemDecoration(new SpacesItemDecoration(2, spacingInPixels, true));
        final GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(relatedProductAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

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

                visibleItemCount = mGridLayoutManager.getChildCount();
                totalItemCount = mGridLayoutManager.getItemCount();
                pastVisiblesItems = mGridLayoutManager.findFirstVisibleItemPosition();

                if (userScrolled && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                    userScrolled = false;

                    if (mRelatedProducts.size() < totalListSize && !isLoading) {
                        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                            isLoading = true;
                            loaderView.setVisibility(View.VISIBLE);
                            startPagingIndex = mRelatedProducts.size();
                            isFilterApply = false;
                            hitApi();
                        } else {
                            //show dialog for network error
                            // TODO Show network error dialog
                        }
                    }

                } else {
                    loaderView.setVisibility(View.GONE);
                }
            }
        });
        if (mRelatedProducts == null || mRelatedProducts.size() == 0) {
            isFilterApply = false;
            hitApi();
        }
        return view;
    }

    void hitApi() {
        if (isFromNavigationDrawer) {
            HitApi("S", subCategoryId, sortOrderBy, sortOrderByType, startPagingIndex);
        } else {
            HitApi(index == 0 ? "M" : "C", index == 0 ? mainCategoryId : categoryId, sortOrderBy, sortOrderByType, startPagingIndex);
        }
    }

    private void HitApi(String filterType, int filterTypeId, String orderBy, String orderByType, final int startIndex) {
        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

            if (startIndex == 0) {
                loader.setVisibility(View.VISIBLE);
            }
            ApiShoppingUtilMethods.INSTANCE.getAllPlroductList(getActivity(), filterOptionTypeIdList, filterType, filterTypeId, null, orderBy, orderByType, startIndex, new ApiShoppingUtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(final Object response) {
                    isLoading = false;
                    if (loader != null) {
                        loader.setVisibility(View.GONE);
                    }
                    loaderView.setVisibility(View.GONE);
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
                    loaderView.setVisibility(View.GONE);
                    if (loader != null) {

                        loader.setVisibility(View.GONE);
                    }
                    if (startIndex == 0) {
                        mRelatedProducts.clear();
                        relatedProductAdapter.notifyDataSetChanged();
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

            HitFilterApi(filterType, filterTypeId);
        } else {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        }
    }

    private void HitFilterApi(String filterType, int filterTypeId) {
        if (ApiShoppingUtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

            ApiShoppingUtilMethods.INSTANCE.getAllFilterList(getActivity(), filterType, filterTypeId, new ApiShoppingUtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(final Object response) {

                    final GetAllFilterResponse mGetAllFilterResponse = (GetAllFilterResponse) response;
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {

                            if (getActivity() instanceof MainCategoryDetailShoppingActivity) {
                                ((MainCategoryDetailShoppingActivity) getActivity()).filterMapList.put(index, mGetAllFilterResponse.getData().getFilterLists());
                            }
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

    /*private void HitApiDrawer(String filterType) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

            loader.setVisibility(View.VISIBLE);


            UtilMethods.INSTANCE.SubCategoryDetail(getActivity(), CatId, index, new UtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(final Object response) {
                    if (loader != null) {

                        loader.setVisibility(View.GONE);
                    }
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {

                            ShowContent(response);
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
            });

        } else {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        }
    }*/


    /*private void HitFilterApi(Integer CatId, Integer mId) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

            loader.setVisibility(View.VISIBLE);
            UtilMethods.INSTANCE.MainCategoryDetail(getActivity(), mId, CatId, index, new UtilMethods.ApiHitCallBack() {
                @Override
                public void onSucess(final Object response) {
                    if (loader != null) {

                        loader.setVisibility(View.GONE);
                    }
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {

                            ShowContent(response);
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
            });

        } else {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        }
    }

    private void HitApiFilterDrawer(JsonObject option) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

            loader.setVisibility(View.VISIBLE);
            UtilMethods.INSTANCE.SubCategoryProductDetail(getActivity(), categoryId, option, loader, index);

        } else {
            UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.err_msg_network));
        }
    }*/

  /*  @Subscribe
    public void onActivityActivityMessage(FragmentActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("FilterApply" + index)) {

            String response = activityFragmentMessage.getFrom();
            showFilterAplyContent(response);

        } else if (activityFragmentMessage.getMessage().equalsIgnoreCase("FilterClear" + index)) {
            filterClear();

        } else if (activityFragmentMessage.getMessage().equalsIgnoreCase("filterDetailResponse" + index)) {

            String Response = activityFragmentMessage.getFrom();
            ShowFilterContent(Response);
        } else if (activityFragmentMessage.getMessage().equalsIgnoreCase("Sorting" + index)) {

            // sorting(activityFragmentMessage.getFrom());
        }
    }*/


    /*private void sorting(String type) {
        if (type.equalsIgnoreCase("1")) {
            Collections.sort(mRelatedProducts, new Comparator<DashboardProductListData>() {
                public int compare(DashboardProductListData obj1, DashboardProductListData obj2) {
                    return Long.valueOf(obj2.getPosId()).compareTo(Long.valueOf(obj1.getPosId())); // To compare string values
                }
            });
        } else if (type.equalsIgnoreCase("2")) {

            Collections.sort(mRelatedProducts, new Comparator<DashboardProductListData>() {
                public int compare(DashboardProductListData obj1, DashboardProductListData obj2) {
                    // ## Ascending order
                    return Double.valueOf(obj1.getSellingPrice()).compareTo(Double.valueOf(obj2.getSellingPrice())); // To compare string values
                    // return Integer.valueOf(obj1.getId()).compareTo(obj2.getId()); // To compare integer values

                    // ## Descending order
                    // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                    // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                }
            });
        } else if (type.equalsIgnoreCase("3")) {
            Collections.sort(mRelatedProducts, new Comparator<DashboardProductListData>() {
                public int compare(DashboardProductListData obj1, DashboardProductListData obj2) {
                    return Double.valueOf(obj2.getSellingPrice()).compareTo(Double.valueOf(obj1.getSellingPrice())); // To compare string values
                }
            });
        }

        relatedProductAdapter.notifyDataSetChanged();
    }*/

    private void showFilterAplyContent(String response) {
        ArrayList<ProductInfoFilterOptionList> mSelectedFilters = new ArrayList<>();
        Gson gson = new Gson();

        mSelectedFilters = gson.fromJson(response, new TypeToken<ArrayList<FilterList>>() {
        }.getType());
        if (getActivity() instanceof MainCategoryDetailShoppingActivity) {

            ((MainCategoryDetailShoppingActivity) getActivity()).selectedFilterMapList.put(index, mSelectedFilters);
        }

        JsonObject jsonObj = new JsonObject();
        JsonArray param = new JsonArray();

        for (int i = 0; i < mSelectedFilters.size(); i++) {
            JsonObject obj = new JsonObject();
            obj.addProperty("ID", mSelectedFilters.get(i).getId());
            obj.addProperty("Value", mSelectedFilters.get(i).getOptionName());
            param.add(obj);
        }

        jsonObj.addProperty("SID", categoryId);
        jsonObj.addProperty("userid", ApiShoppingUtilMethods.INSTANCE.mUserID + "");
        jsonObj.add("Option", param);


        /*if (isFromNavigationDrawer) {
            HitApiFilterDrawer(jsonObj);
        } else {
            HitFilterApi(categoryId, mainCategoryId);
        }*/
    }

    private void filterClear() {
        if (getActivity() instanceof MainCategoryDetailShoppingActivity) {
            ArrayList<ProductInfoFilterOptionList> mSelectedFilters = new ArrayList<>();
            ((MainCategoryDetailShoppingActivity) getActivity()).selectedFilterMapList.put(index, mSelectedFilters);
        }
        if (isFromNavigationDrawer) {
            HitApi("S", subCategoryId, sortOrderBy, sortOrderByType, startPagingIndex);
        } else {
            HitApi("C", categoryId, sortOrderBy, sortOrderByType, startPagingIndex);
        }
    }

    private void ShowFilterContent(String response) {
        Gson gson = new Gson();
        MainCategoryDetailResponse mainCategoryDetailResponse = gson.fromJson(response, MainCategoryDetailResponse.class);
        mRelatedProducts.clear();

        // mRelatedProducts.addAll(mainCategoryDetailResponse.getRelatedProduct());
        //  sorting(((MainCategoryDetailActivity) getActivity()).sortingMapList.get(index));
        relatedProductAdapter.notifyDataSetChanged();
        loader.setVisibility(View.GONE);


    }

    private void ShowContent(AllProductListResponse response) {

        if (isFilterApply || startPagingIndex == 0) {
            mRelatedProducts.clear();
        }
        mRelatedProducts.addAll(response.getData().getProductSetList());


        if (mRelatedProducts.size() > 0) {
            //  sorting(((MainCategoryDetailActivity) getActivity()).sortingMapList.get(index));
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.GONE);


            relatedProductAdapter.notifyDataSetChanged();

        } else {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);

        }
        loader.setVisibility(View.GONE);


    }



   /* private void ShowContent(String response) {
        Gson gson = new Gson();
        MainCategoryDetailResponse mainCategoryDetailResponse = gson.fromJson(response, MainCategoryDetailResponse.class);
        mRelatedProducts.clear();

        mRelatedProducts.addAll(mainCategoryDetailResponse.getRelatedProduct());
        if (getActivity() instanceof MainCategoryDetailActivity) {
            ((MainCategoryDetailActivity) getActivity()).filterMapList.put(index, mainCategoryDetailResponse.getFilter());
        }

        relatedProductAdapter.notifyDataSetChanged();
        loader.setVisibility(View.GONE);


    }*/


    public void applyFilter(ArrayList<String> filterList, int tabIndex) {
        if (index == tabIndex) {
            filterOptionTypeIdList = filterList;
            isFilterApply = true;
            startPagingIndex = 0;
            hitApi();
        }
    }


    public void applySorting(String sortBy, String sortType, int tabIndex) {
        if (index == tabIndex) {
            sortOrderBy = sortBy;
            sortOrderByType = sortType;
            isFilterApply = true;
            startPagingIndex = 0;
            hitApi();
        }
    }


    public void refreshData() {
        for (DashboardProductListData item : mRelatedProducts) {
            item.setAffiliateShareLink(item.getShareLink() + "?SM=" + ApiShoppingUtilMethods.INSTANCE.mMobileNumber);
        }
        relatedProductAdapter.notifyDataSetChanged();
    }
}
