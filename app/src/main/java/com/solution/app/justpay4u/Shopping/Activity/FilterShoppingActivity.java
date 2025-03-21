package com.solution.app.justpay4u.Shopping.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Shopping.Object.ProductInfoFilter;
import com.solution.app.justpay4u.Api.Shopping.Object.ProductInfoFilterOptionList;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.FilterCategoryShoppingAdapter;
import com.solution.app.justpay4u.Shopping.Adapter.FilterCategorySubListShoppingAdapter;

import java.util.ArrayList;

public class FilterShoppingActivity extends AppCompatActivity {
    ArrayList<ProductInfoFilter> mFilters = new ArrayList<>();
    ArrayList<ProductInfoFilterOptionList> mFilterLists = new ArrayList<>();
    ArrayList<ProductInfoFilterOptionList> mSelectedFilterLists = new ArrayList<>();
    ArrayList<String> mSelectedStringLists = new ArrayList<>();
    RecyclerView categoryRecyclerView, categorySubListRecyclerView;
    int currentClickPosition = 0;
    int selectedTabIndex = 0;
    EditText searchEditText;
    TextView applyBtn, cancelBtn;
    FilterCategoryShoppingAdapter mFilterCategoryAdapter;
    FilterCategorySubListShoppingAdapter mFilterCategorySubListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_filter);
            initToolBar();
            selectedTabIndex = getIntent().getIntExtra("SelectedTabIndex", 0);
            mFilters.addAll((ArrayList<ProductInfoFilter>) getIntent().getSerializableExtra("FilterList"));
            mSelectedFilterLists.addAll((ArrayList<ProductInfoFilterOptionList>) getIntent().getSerializableExtra("SelectedFilterList"));
            applyBtn = findViewById(R.id.text_action_apply);
            cancelBtn = findViewById(R.id.text_action_cancel);
            searchEditText = findViewById(R.id.searchEditText);
            categoryRecyclerView = findViewById(R.id.filterCategoryRecyclerView);
            categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            //categoryRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
            categorySubListRecyclerView = findViewById(R.id.filterCategorySubListRecyclerView);
            categorySubListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
           //categorySubListRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            mFilterCategoryAdapter = new FilterCategoryShoppingAdapter(mFilters, this);
            categoryRecyclerView.setAdapter(mFilterCategoryAdapter);
            ArrayList<ProductInfoFilterOptionList> list = new ArrayList<>();
            if (mFilters != null && mFilters.size() > 0) {
                mFilters.get(0).setSelected(true);
                searchEditText.setHint("Search for " + mFilters.get(0).getName());
                setSelectedFilter();

            }
            mFilterLists.addAll(mFilters != null && mFilters.size() > 0 ? mFilters.get(0).getOptionLists() : list);
            mFilterCategorySubListAdapter = new FilterCategorySubListShoppingAdapter(mFilterLists, this);


            categorySubListRecyclerView.setAdapter(mFilterCategorySubListAdapter);
            mFilterCategoryAdapter.setClickListener(new FilterCategoryShoppingAdapter.ItemClickListener() {
                @Override
                public void viewClick(View view, int position) {
                    searchEditText.setHint("Search for " + mFilters.get(position).getName());
                    mFilters.get(currentClickPosition).setSelected(false);
                    mFilters.get(currentClickPosition).setSearchText(searchEditText.getText().toString());

                    currentClickPosition = position;
                    mFilters.get(position).setSelected(true);
                    mFilterCategoryAdapter.notifyDataSetChanged();
                    setSubList(position);

                }
            });
            mFilterCategorySubListAdapter.setClickListener(new FilterCategorySubListShoppingAdapter.ItemClickListener() {
                @Override
                public void viewClick(View view, int position) {
                    if (mFilters.get(currentClickPosition).getOptionLists().get(position).getSelected()) {
                        mFilters.get(currentClickPosition).getOptionLists().get(position).setSelected(false);
                        mFilterLists.get(position).setSelected(false);

                        for (int i = 0; i < mSelectedFilterLists.size(); i++) {
                            if (mSelectedFilterLists.get(i).getId() == mFilterLists.get(position).getId()) {
                                mSelectedFilterLists.remove(i);
                                mSelectedStringLists.remove(mFilterLists.get(position).getId() + "");
                            }
                        }
                    } else {
                        mFilters.get(currentClickPosition).getOptionLists().get(position).setSelected(true);
                        mFilterLists.get(position).setSelected(true);

                        mSelectedFilterLists.add(mFilterLists.get(position));
                        if (!mSelectedStringLists.contains(mFilterLists.get(position).getId() + "")) {
                            mSelectedStringLists.add(mFilterLists.get(position).getId() + "");
                        }
                    }
                    mFilterCategorySubListAdapter.notifyDataSetChanged();

                    applyButtonGesture();
                }
            });

            applyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("SelectedFilter", mSelectedFilterLists);
                    intent.putExtra("SelectedStringFilter", mSelectedStringLists);
                    setResult(RESULT_OK, intent);
                    finish();
               /* FragmentActivityMessage fragmentActivityMessage =
                        new FragmentActivityMessage("FilterApply" + selectedTabIndex, "" + new Gson().toJson(mSelectedFilterLists).toString());
                GlobalBus.getBus().post(fragmentActivityMessage);*/
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mFilterCategorySubListAdapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            applyButtonGesture();
        });
    }

    private void initToolBar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        ImageView logoIv = findViewById(R.id.image);
        setSupportActionBar(mToolbar);
       /* if (ApiShoppingUtilMethods.INSTANCE.mLogo != 0) {

            if (logoIv != null) {
                setTitle("");
                logoIv.setVisibility(View.VISIBLE);
                logoIv.setImageResource(ApiShoppingUtilMethods.INSTANCE.mLogo);
            } else {
                logoIv.setVisibility(View.GONE);
                setTitle("Filter");
            }

        } else {
            logoIv.setVisibility(View.GONE);
            setTitle("Filter");
        }*/
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
               /* Intent ii = new Intent(getApplicationContext(), DashboardActivity.class);
                ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ii);
                overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);*/
            }
        });
    }

    private void setSelectedFilter() {
        if (mSelectedFilterLists != null && mSelectedFilterLists.size() > 0) {
            for (int i = 0; i < mFilters.size(); i++) {
                for (int j = 0; j < mSelectedFilterLists.size(); j++) {
                    for (int k = 0; k < mFilters.get(i).getOptionLists().size(); k++) {
                        if (mSelectedFilterLists.get(j).getId() == mFilters.get(i).getOptionLists().get(k).getId()) {
                            mFilters.get(i).getOptionLists().get(k).setSelected(true);

                            if (!mSelectedStringLists.contains(mSelectedFilterLists.get(j).getId() + "")) {
                                mSelectedStringLists.add(mSelectedFilterLists.get(j).getId() + "");
                            }
                        }
                    }
                }
            }
        }
    }

    private void applyButtonGesture() {
        if (mSelectedFilterLists != null && mSelectedFilterLists.size() > 0) {
            applyBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            applyBtn.setClickable(true);
        } else {
            applyBtn.setBackgroundColor(getResources().getColor(R.color.light_grey));
            applyBtn.setClickable(false);
        }
    }


    public void setSubList(int position) {
        mFilterLists.clear();
        mFilterLists.addAll(mFilters.get(position).getOptionLists());
        mFilterCategorySubListAdapter.notifyDataSetChanged();
        searchEditText.setText(mFilters.get(position).getSearchText());
        searchEditText.setSelection(searchEditText.getText().length());
    }

    /*@Override
    public void onDestroy() {
        // Unregister the registered event.
        GlobalBus.getBus().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }*/

    /*@Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("FilterList" + selectedPosition)) {
            String Response = activityFragmentMessage.getFrom();
            ShowContent(Response);
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
    /*    if (id == R.id.action_clear) {

            if (mSelectedFilterLists.size() > 0) {
                mSelectedFilterLists.clear();
                mSelectedStringLists.clear();
                Intent intent = new Intent();
                intent.putExtra("SelectedFilter", mSelectedFilterLists);
                intent.putExtra("SelectedStringFilter", mSelectedStringLists);
                setResult(RESULT_OK, intent);
            }
            finish();
            *//*FragmentActivityMessage fragmentActivityMessage =
                    new FragmentActivityMessage("FilterClear" + selectedTabIndex, "");
            GlobalBus.getBus().post(fragmentActivityMessage);*//*
        }*/

        return super.onOptionsItemSelected(item);
    }


}
