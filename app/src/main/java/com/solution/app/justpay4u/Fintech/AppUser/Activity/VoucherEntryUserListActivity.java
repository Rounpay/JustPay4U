package com.solution.app.justpay4u.Fintech.AppUser.Activity;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.AscReport;
import com.solution.app.justpay4u.Fintech.AppUser.Adapter.VoucherEntryUserListAdapter;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

public class VoucherEntryUserListActivity extends AppCompatActivity {
    RecyclerView recycler_view;
    VoucherEntryUserListAdapter mAdapter;
    EditText search_all;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    private ArrayList<AscReport> ascReportList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_voucher_entry_user_list);

            ascReportList = getIntent().getParcelableArrayListExtra("Data");
            search_all = findViewById(R.id.search_all);
            findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));
            recycler_view = findViewById(R.id.recycler_view);
            recycler_view.setLayoutManager(new LinearLayoutManager(this));


            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            retryBtn = findViewById(R.id.retryBtn);
            errorMsg = findViewById(R.id.errorMsg);
            errorMsg.setText("Bank not found");

            getOperatorList();


            search_all.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (mAdapter != null) {
                        mAdapter.getFilter().filter(s);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
     /*   retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HitApi();
            }
        });*/
        });

    }

    public void getOperatorList() {
        if (ascReportList != null && ascReportList.size() > 0) {
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            mAdapter = new VoucherEntryUserListAdapter(ascReportList, VoucherEntryUserListActivity.this);
            recycler_view.setAdapter(mAdapter);
        } else {
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);

        }

    }


    public void selectUser(AscReport operator) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("Data", operator);
        setResult(RESULT_OK, clickIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
