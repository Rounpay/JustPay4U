package com.solution.app.justpay4u.Shopping.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.solution.app.justpay4u.Api.Shopping.Object.DashbaordInfoCategoryList;
import com.solution.app.justpay4u.Api.Shopping.Object.DashbaordInfoSubCategoryList;
import com.solution.app.justpay4u.Api.Shopping.Object.Filter;
import com.solution.app.justpay4u.Api.Shopping.Object.MainCategoryDetailTopHorizontalListItem;
import com.solution.app.justpay4u.Api.Shopping.Object.ProductInfoFilter;
import com.solution.app.justpay4u.Api.Shopping.Object.ProductInfoFilterOptionList;
import com.solution.app.justpay4u.Api.Shopping.Object.RelatedProduct;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.FragmentViewPagerShoppingAdapter;
import com.solution.app.justpay4u.Shopping.Adapter.MainCategoryDetailTopHorizonalListShoppingAdapter;
import com.solution.app.justpay4u.Shopping.Fragment.FragmentMainCategoryDetailShopping;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;
import java.util.HashMap;


public class MainCategoryDetailShoppingActivity extends AppCompatActivity {


    private final int INTENT_FILTER = 630;
    private final int LOGIN_ACTIVITY_RESULT = 482;
    public View filterSortView;
    public int selectedPosition = 0;
    public HashMap<Integer, ArrayList<ProductInfoFilter>> filterMapList = new HashMap<>();
    public HashMap<Integer, ArrayList<ProductInfoFilterOptionList>> selectedFilterMapList = new HashMap<>();
    public HashMap<Integer, String> sortingMapList = new HashMap<>();
    CustomLoader loader;
    RecyclerView tabRecyclerView;
    ViewPager viewPager;
    int categoryId = 0, subCategoryId = 0, mainCategoryId = 0;
    String categoryName = "", categoryImage = "";
    boolean isFromNavigationDrawer = false;
    FragmentViewPagerShoppingAdapter mFragmentViewPagerAdapter;
    MainCategoryDetailTopHorizonalListShoppingAdapter mAdapter;
    ArrayList<DashbaordInfoSubCategoryList> mSubCategoryLists = new ArrayList<>();
    ArrayList<DashbaordInfoCategoryList> mCategoryLists = new ArrayList<>();
    ArrayList<RelatedProduct> mRelatedProducts = new ArrayList<>();
    ArrayList<MainCategoryDetailTopHorizontalListItem> topHorizontalListItem = new ArrayList<>();
    TextView textCartItemCount;
    int mCartItemCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_main_category_detail);
            categoryName = getIntent().getStringExtra("CategoryName");
            categoryImage = getIntent().getStringExtra("CategoryImage");
            setTitle(categoryName + "");
            filterSortView = findViewById(R.id.filterSortView);
            tabRecyclerView = findViewById(R.id.tabRecyclerView);
            viewPager = findViewById(R.id.viewPager);


            categoryId = getIntent().getIntExtra("CategoryId", 0);
            subCategoryId = getIntent().getIntExtra("SubCategoryId", 0);

            mainCategoryId = getIntent().getIntExtra("Id", 0);
            isFromNavigationDrawer = getIntent().getBooleanExtra("isFromNavigationDrawer", false);

            View noDataView = findViewById(R.id.noDataView);
            if (getIntent().getSerializableExtra("CategoryList") != null) {

                if (isFromNavigationDrawer) {
                    // filterSortView.setVisibility(View.VISIBLE);
                    mSubCategoryLists.addAll((ArrayList<DashbaordInfoSubCategoryList>) getIntent().getSerializableExtra("CategoryList"));

                } else {
                    // filterSortView.setVisibility(View.GONE);
                    mCategoryLists.add(new DashbaordInfoCategoryList("0", 0, mainCategoryId, "All", categoryImage, null));
                    mCategoryLists.addAll( getIntent().getParcelableArrayListExtra("CategoryList"));
                }
            }
            if (mCategoryLists.size() > 0 || mSubCategoryLists.size() > 0) {
                noDataView.setVisibility(View.GONE);
                filterSortView.setVisibility(View.VISIBLE);
            } else {
                noDataView.setVisibility(View.VISIBLE);
                filterSortView.setVisibility(View.GONE);
            }

            if (mCategoryLists.size() == 1 || mSubCategoryLists.size() == 1) {
                tabRecyclerView.setVisibility(View.GONE);
            }


            tabRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            // tabRecyclerView.setItemAnimator(new DefaultItemAnimator());
            // tabRecyclerView.addItemDecoration(new RecyclerViewItemDecoration((int) getResources().getDimension(R.dimen._10sdp), RecyclerViewItemDecoration.HORIZONTAL, false));
            mAdapter = new MainCategoryDetailTopHorizonalListShoppingAdapter(topHorizontalListItem, this);
            tabRecyclerView.setAdapter(mAdapter);


            mFragmentViewPagerAdapter = new FragmentViewPagerShoppingAdapter(getSupportFragmentManager());


            viewPager.setAdapter(mFragmentViewPagerAdapter);
            if (isFromNavigationDrawer) {
                viewPager.setOffscreenPageLimit(mSubCategoryLists.size());
            } else {
                viewPager.setOffscreenPageLimit(mCategoryLists.size());
            }


            findViewById(R.id.filterView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Filter> list = new ArrayList<>();
                    ArrayList<ProductInfoFilter> mFilters = filterMapList.get(selectedPosition);
                    ArrayList<ProductInfoFilterOptionList> listSelected = new ArrayList<>();
                    ArrayList<ProductInfoFilterOptionList> mSelectedFilters = selectedFilterMapList.get(selectedPosition);
                    if (mFilters != null && mFilters.size() > 0) {
                        startActivityForResult(new Intent(MainCategoryDetailShoppingActivity.this, FilterShoppingActivity.class)
                                .putExtra("SelectedTabIndex", selectedPosition)
                                .putExtra("SelectedFilterList", mSelectedFilters != null && mSelectedFilters.size() > 0 ? mSelectedFilters : listSelected)
                                .putExtra("FilterList", mFilters != null && mFilters.size() > 0 ? mFilters : list)
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_FILTER);
                    } else {
                        Toast.makeText(MainCategoryDetailShoppingActivity.this, "Filter is not available.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            findViewById(R.id.sortView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSortingDialog();
                }
            });
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    if (topHorizontalListItem != null && topHorizontalListItem.size() > 0) {
                        topHorizontalListItem.get(selectedPosition).setSelected(false);
                        topHorizontalListItem.get(position).setSelected(true);
                        mAdapter.notifyDataSetChanged();
                        selectedPosition = position;
                        tabRecyclerView.getLayoutManager().scrollToPosition(position);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            setDataInUI();
        });
    }


    public void setItemSelect(int position) {
        if (topHorizontalListItem != null && topHorizontalListItem.size() > 0) {
            topHorizontalListItem.get(selectedPosition).setSelected(false);
            topHorizontalListItem.get(position).setSelected(true);
            mAdapter.notifyDataSetChanged();
            viewPager.setCurrentItem(position);
            selectedPosition = position;
            if (isFromNavigationDrawer) {
                viewPager.setOffscreenPageLimit(mSubCategoryLists.size());
            } else {
                viewPager.setOffscreenPageLimit(mCategoryLists.size());
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCartItemCount = ApiShoppingUtilMethods.INSTANCE.getCartCount(this);
        setupBadge();
    }





   /* private void ShowContent(String response) {
        Gson gson = new Gson();
        if (isFromNavigationDrawer) {
            SubCategoryResponse subCategoryResponse = gson.fromJson(response, SubCategoryResponse.class);
            mSubCategoryLists.clear();
            mRelatedProducts.clear();
            mFilters.clear();
            mSubCategoryLists.addAll(subCategoryResponse.getLeftCategoryList().get(0).getSubCategoryList());
            mRelatedProducts.addAll(subCategoryResponse.getRelatedProduct());
            mFilters.addAll(subCategoryResponse.getFilter());
            viewPager.setOffscreenPageLimit(mSubCategoryLists.size());
        } else {
            MainCategoryDetailResponse mainCategoryDetailResponse = gson.fromJson(response, MainCategoryDetailResponse.class);
            mLeftCategoryLists.clear();
            mRelatedProducts.clear();
            mFilters.clear();
            mLeftCategoryLists.add(new LeftCategoryList(0, "All", null));
            mLeftCategoryLists.addAll(mainCategoryDetailResponse.getLeftCategoryList());
            mRelatedProducts.addAll(mainCategoryDetailResponse.getRelatedProduct());
            mFilters.addAll(mainCategoryDetailResponse.getFilter());
            viewPager.setOffscreenPageLimit(mLeftCategoryLists.size());
        }
        setDataInUI();
    }*/

    private void setDataInUI() {

        mFragmentViewPagerAdapter.removeFragment();
        topHorizontalListItem.clear();
        if (isFromNavigationDrawer) {
            for (int i = 0; i < mSubCategoryLists.size(); i++) {
                sortingMapList.put(i, "1");
                FragmentMainCategoryDetailShopping mFragmentMainCategoryDetail = new FragmentMainCategoryDetailShopping();
                Bundle arg = new Bundle();
                arg.putInt("CategoryId", mSubCategoryLists.get(i).getCategoryID());
                arg.putInt("SubCategoryId", mSubCategoryLists.get(i).getSubCategoryId());
                arg.putInt("MainCategoryId", mainCategoryId);
                arg.putInt("Index", i);
                arg.putBoolean("isFromNavigationDrawer", isFromNavigationDrawer);
                arg.putString("CategoryName", categoryName);
                if (mSubCategoryLists.get(i).getSubCategoryId() == subCategoryId) {
                    arg.putSerializable("RelatedProductList", mRelatedProducts);
                    selectedPosition = i;
                    topHorizontalListItem.add(new MainCategoryDetailTopHorizontalListItem(mSubCategoryLists.get(i).getCategoryID(),
                            mSubCategoryLists.get(i).getName(), mSubCategoryLists.get(i).getImage(), true));
                } else {
                    ArrayList<RelatedProduct> list = new ArrayList<>();
                    arg.putSerializable("RelatedProductList", list);
                    topHorizontalListItem.add(new MainCategoryDetailTopHorizontalListItem(mSubCategoryLists.get(i).getCategoryID(),
                            mSubCategoryLists.get(i).getName(), mSubCategoryLists.get(i).getImage(), false));

                }
                mFragmentMainCategoryDetail.setArguments(arg);

                mFragmentViewPagerAdapter.addFragment(mFragmentMainCategoryDetail, "" + mSubCategoryLists.get(i).getName());

            }

        } else {
            for (int i = 0; i < mCategoryLists.size(); i++) {
                sortingMapList.put(i, "1");
                FragmentMainCategoryDetailShopping mFragmentMainCategoryDetail = new FragmentMainCategoryDetailShopping();
                Bundle arg = new Bundle();
                arg.putInt("CategoryId", mCategoryLists.get(i).getCategoryID());
                arg.putInt("SubCategoryId", 0);

                arg.putInt("MainCategoryId", mainCategoryId);
                arg.putInt("Index", i);
                arg.putBoolean("isFromNavigationDrawer", isFromNavigationDrawer);
                arg.putString("CategoryName", categoryName);
                if (i == 0) {
                    arg.putSerializable("RelatedProductList", mRelatedProducts);
                    topHorizontalListItem.add(new MainCategoryDetailTopHorizontalListItem(mCategoryLists.get(i).getCategoryID(),
                            mCategoryLists.get(i).getName(), mCategoryLists.get(i).getImage(), true));

                } else {
                    ArrayList<RelatedProduct> list = new ArrayList<>();
                    arg.putSerializable("RelatedProductList", list);
                    topHorizontalListItem.add(new MainCategoryDetailTopHorizontalListItem(mCategoryLists.get(i).getCategoryID(),
                            mCategoryLists.get(i).getName(), mCategoryLists.get(i).getImage(), false));
                }
                mFragmentMainCategoryDetail.setArguments(arg);

                mFragmentViewPagerAdapter.addFragment(mFragmentMainCategoryDetail, "" + mCategoryLists.get(i).getName());


            }
        }
        mFragmentViewPagerAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
        setItemSelect(selectedPosition);

        loader.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping, menu);
        menu.getItem(0).setVisible(true);
        menu.getItem(1).setVisible(false);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = actionView.findViewById(R.id.cart_badge);
        mCartItemCount = ApiShoppingUtilMethods.INSTANCE.getCartCount(this);
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

    public void cartUpdate(int count) {
        mCartItemCount = count;
        setupBadge();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_search) {
            startActivity(new Intent(MainCategoryDetailShoppingActivity.this, SearchResultShoppingActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

        } else if (id == R.id.action_cart) {

            Intent intent = new Intent(MainCategoryDetailShoppingActivity.this, CartDetailShoppingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);


        }
        return super.onOptionsItemSelected(item);
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
        String selectedSorting = sortingMapList.get(selectedPosition);

        selectedSorting(selectedSorting, newestRadioBtn, lowToHighRadioBtn, highToLowRadioBtn, newestFirstView, lowToHighView, highToLowView);


        newestFirstView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!newestRadioBtn.isChecked()) {
                    selectedSorting("1", newestRadioBtn, lowToHighRadioBtn, highToLowRadioBtn, newestFirstView, lowToHighView, highToLowView);

                    sortingMapList.remove(selectedPosition);
                    sortingMapList.put(selectedPosition, "1");

                    Fragment activeFragment = mFragmentViewPagerAdapter.getItem(selectedPosition);
                    ((FragmentMainCategoryDetailShopping) activeFragment).applySorting("New", "a", selectedPosition);

                    /*FragmentActivityMessage fragmentActivityMessage =
                            new FragmentActivityMessage("Sorting" + selectedPosition, "1");
                    GlobalBus.getBus().post(fragmentActivityMessage);*/
                }
                mBottomSheetDialog.dismiss();
            }
        });
        lowToHighView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lowToHighRadioBtn.isChecked()) {
                    selectedSorting("2", newestRadioBtn, lowToHighRadioBtn, highToLowRadioBtn, newestFirstView, lowToHighView, highToLowView);

                    sortingMapList.remove(selectedPosition);
                    sortingMapList.put(selectedPosition, "2");
                    Fragment activeFragment = mFragmentViewPagerAdapter.getItem(selectedPosition);
                    ((FragmentMainCategoryDetailShopping) activeFragment).applySorting("Price", "a", selectedPosition);
                    /*FragmentActivityMessage fragmentActivityMessage =
                            new FragmentActivityMessage("Sorting" + selectedPosition, "2");
                    GlobalBus.getBus().post(fragmentActivityMessage);*/
                }
                mBottomSheetDialog.dismiss();
            }
        });
        highToLowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!highToLowRadioBtn.isChecked()) {
                    selectedSorting("3", newestRadioBtn, lowToHighRadioBtn, highToLowRadioBtn, newestFirstView, lowToHighView, highToLowView);

                    sortingMapList.remove(selectedPosition);
                    sortingMapList.put(selectedPosition, "3");

                    Fragment activeFragment = mFragmentViewPagerAdapter.getItem(selectedPosition);
                    ((FragmentMainCategoryDetailShopping) activeFragment).applySorting("Price", "d", selectedPosition);
                    /*FragmentActivityMessage fragmentActivityMessage =
                            new FragmentActivityMessage("Sorting" + selectedPosition, "3");
                    GlobalBus.getBus().post(fragmentActivityMessage);*/
                }
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
    }


    void selectedSorting(String selectedSorting, RadioButton newestRadioBtn, RadioButton lowToHighRadioBtn, RadioButton highToLowRadioBtn,
                         RelativeLayout newestFirstView, RelativeLayout lowToHighView, RelativeLayout highToLowView) {
        if (selectedSorting.equalsIgnoreCase("1")) {
            newestRadioBtn.setChecked(true);
            lowToHighRadioBtn.setChecked(false);
            highToLowRadioBtn.setChecked(false);
            newestFirstView.setBackgroundResource(R.drawable.rounded_focused);
            lowToHighView.setBackgroundResource(R.drawable.rounded_unfocused);
            highToLowView.setBackgroundResource(R.drawable.rounded_unfocused);
        } else if (selectedSorting.equalsIgnoreCase("2")) {
            newestRadioBtn.setChecked(false);
            lowToHighRadioBtn.setChecked(true);
            highToLowRadioBtn.setChecked(false);
            newestFirstView.setBackgroundResource(R.drawable.rounded_unfocused);
            lowToHighView.setBackgroundResource(R.drawable.rounded_focused);
            highToLowView.setBackgroundResource(R.drawable.rounded_unfocused);
        } else if (selectedSorting.equalsIgnoreCase("3")) {
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
            selectedFilterMapList.put(selectedPosition, (ArrayList<ProductInfoFilterOptionList>) data.getSerializableExtra("SelectedFilter"));
            Fragment activeFragment = mFragmentViewPagerAdapter.getItem(selectedPosition);
            ((FragmentMainCategoryDetailShopping) activeFragment).applyFilter((ArrayList<String>) data.getSerializableExtra("SelectedStringFilter"), selectedPosition);

        }

        if (requestCode == LOGIN_ACTIVITY_RESULT && resultCode == RESULT_OK) {
            for (int i = 0; i < topHorizontalListItem.size(); i++) {
                Fragment activeFragment = mFragmentViewPagerAdapter.getItem(i);
                ((FragmentMainCategoryDetailShopping) activeFragment).refreshData();
            }


        }

    }


    /* private void dataparseSubCatMenu(ArrayList<PList> leftMainCatProducts) {
        dashboardGridAdapter = new DashboardGridAdapter(leftMainCatProducts, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager. VERTICAL, false);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(dashboardGridAdapter);
    }*/
}
