package com.solution.app.justpay4u.Fintech.DthSubscription.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.DthPackage;
import com.solution.app.justpay4u.Fintech.DthSubscription.Adapter.DthPackageAdapter;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

public class DthPackageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_dth_package);
            setTitle(getIntent().getStringExtra("Title"));
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ArrayList<DthPackage> mGetDthPackageResponse = getIntent().getParcelableArrayListExtra("PACKAGE_DATA_ARRAY");
            recyclerView.setAdapter(new DthPackageAdapter(mGetDthPackageResponse, this));
        });

    }

    public void ViewChannel(DthPackage operator) {

        startActivity(new Intent(this, DthChannelActivity.class)
                .putExtra("DTH_PACKAGE", operator)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

    public void SelectedPackage(DthPackage operator) {

        Intent intent = new Intent();
        intent.putExtra("Package", operator);
        setResult(RESULT_OK, intent);
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
