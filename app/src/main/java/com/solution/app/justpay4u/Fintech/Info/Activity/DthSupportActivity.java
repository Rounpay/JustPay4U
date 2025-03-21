package com.solution.app.justpay4u.Fintech.Info.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.Api.Fintech.Object.SupportListItem;
import com.solution.app.justpay4u.Api.Fintech.Response.OperatorListResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.Fintech.Info.Adapter.SupportAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;

import java.util.ArrayList;

public class DthSupportActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    SupportAdapter mSupportAdapter;
    String from;
    private AppPreferences mAppPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_dth_support);
            from = getIntent().getStringExtra("From");
            setTitle(from.equalsIgnoreCase("DTH") ? "DTH Support" : "Prepaid Support");
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mRecyclerView = findViewById(R.id.recyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            new Handler(Looper.getMainLooper()).post(() -> {
                mSupportAdapter = new SupportAdapter(this, from.equalsIgnoreCase("DTH") ? getDthListItem(3) : getMobileListItem(1));
                mRecyclerView.setAdapter(mSupportAdapter);
            });
        });

    }


    ArrayList<SupportListItem> getDthListItem(int op_Type) {
        ArrayList<SupportListItem> mSupportListItems = new ArrayList<>();
        OperatorListResponse mOperatorListResponse = ApiFintechUtilMethods.INSTANCE.getOperatorListResponse(mAppPreferences);
        for (OperatorList op : mOperatorListResponse.getOperators()) {
            if (op.getOpType() == op_Type && !op.getTollFree().isEmpty()) {
                mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), op.getTollFree().replaceAll(",", "\n"), ""));
            }

        }

        return mSupportListItems;
    }

    ArrayList<SupportListItem> getMobileListItem(int op_Type) {
        ArrayList<SupportListItem> mSupportListItems = new ArrayList<>();


        OperatorListResponse mOperatorListResponse = ApiFintechUtilMethods.INSTANCE.getOperatorListResponse(mAppPreferences);
        for (OperatorList op : mOperatorListResponse.getOperators()) {
            if (op.getOpType() == op_Type && !op.getTollFree().isEmpty()) {
                mSupportListItems.add(new SupportListItem(op.getName(), op.getImage(), op.getTollFree().replaceAll(",", "\n"), ""));
            }

        }

        return mSupportListItems;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

}
