package com.solution.app.justpay4u.Fintech.Recharge.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.CircleList;
import com.solution.app.justpay4u.Api.Fintech.Response.CircleZoneListResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Adapter.CircleZoneAdapter;
import com.solution.app.justpay4u.Util.AppPreferences;

import java.util.ArrayList;

public class CircleZoneActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    View noDataView;
    CircleZoneAdapter mAdapter;
    ArrayList<CircleList> mCircleList = new ArrayList<>();
    private AppPreferences mAppPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_circle_zone);

            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mCircleList = getIntent().getParcelableArrayListExtra("CircleList");
            recycler_view = findViewById(R.id.recycler_view);
            recycler_view.setLayoutManager(new LinearLayoutManager(this));
            noDataView = findViewById(R.id.noDataView);
            TextView errorMsg = findViewById(R.id.errorMsg);
            EditText search_all = findViewById(R.id.search_all);
            errorMsg.setText("Circle Zone not found, please try after some time.");


            if (mCircleList != null && mCircleList.size() > 0) {
                noDataView.setVisibility(View.GONE);
                mAdapter = new CircleZoneAdapter(mCircleList, CircleZoneActivity.this);
                recycler_view.setAdapter(mAdapter);

            } else {
                CircleZoneListResponse mCircleZoneListResponse = ApiFintechUtilMethods.INSTANCE.getCircleZoneListResponse(mAppPreferences);
                mCircleList = mCircleZoneListResponse.getCirlces();
                if (mCircleList != null && mCircleList.size() > 0) {
                    noDataView.setVisibility(View.GONE);
                    mAdapter = new CircleZoneAdapter(mCircleList, CircleZoneActivity.this);
                    recycler_view.setAdapter(mAdapter);

                } else {
                    noDataView.setVisibility(View.VISIBLE);
                    ApiFintechUtilMethods.INSTANCE.CircleZoneList(this, ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences),
                            ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences), mAppPreferences, object -> {

                                CircleZoneListResponse mCircleResponse = (CircleZoneListResponse) object;
                                if (mCircleResponse != null) {
                                    mCircleList = mCircleResponse.getCirlces();
                                    if (mCircleList != null && mCircleList.size() > 0) {
                                        noDataView.setVisibility(View.GONE);
                                        mAdapter = new CircleZoneAdapter(mCircleList, CircleZoneActivity.this);
                                        recycler_view.setAdapter(mAdapter);
                                    }
                                }
                            });
                }


            }

            findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));

            search_all.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (mAdapter != null) {
                        mAdapter.getFilter().filter(s);
                    }
                }
            });
        });

    }

    public void ItemClick(String name, String id, String circle) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("selectedCircleName", circle);
        clickIntent.putExtra("selectedCircleId", id);
        setResult(RESULT_OK, clickIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

