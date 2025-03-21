package com.solution.app.justpay4u.Shopping.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Shopping.Object.DashboardProductListData;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.SimillarProductListShoppingAdapter;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;

public class SimilarProductShoppingActivity extends AppCompatActivity {

    TextView textCartItemCount;
    int mCartItemCount = 10;
    SimillarProductListShoppingAdapter mSimillarProductListAdapter;
    RecyclerView mRecyclerView;
    CustomLoader loader;
    String intentTitle;
    private ArrayList<DashboardProductListData> mProductDetailsList = new ArrayList<>();
    private View noDataView;
    private View noNetworkView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_similar_product);
            intentTitle = getIntent().getStringExtra("TITLE");
            setTitle(intentTitle);

            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            loader.setCancelable(false);
            mRecyclerView = findViewById(R.id.recyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            View retryBtn = findViewById(R.id.retryBtn);
            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            TextView errorMsg = findViewById(R.id.errorMsg);
            errorMsg.setText("Product not found");

            mProductDetailsList = (ArrayList<DashboardProductListData>) getIntent().getSerializableExtra("SIMILAR_PRODUCT_LIST");
            if (mProductDetailsList != null && mProductDetailsList.size() > 0) {
                noDataView.setVisibility(View.GONE);
            } else {
                noDataView.setVisibility(View.VISIBLE);
            }

            mSimillarProductListAdapter = new SimillarProductListShoppingAdapter(this, mProductDetailsList);
            mRecyclerView.setAdapter(mSimillarProductListAdapter);

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
        textCartItemCount = actionView.findViewById(R.id.cart_badge);

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

            startActivity(new Intent(SimilarProductShoppingActivity.this, SearchResultShoppingActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));


        } else if (id == R.id.action_cart) {

            Intent intent = new Intent(SimilarProductShoppingActivity.this, CartDetailShoppingActivity.class);
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

        noDataView.setVisibility(noDataViewVisibility);
        noNetworkView.setVisibility(noNetworkVisibility);
    }


}
