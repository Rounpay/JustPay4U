package com.solution.app.justpay4u.Fintech.AEPS.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.solution.app.justpay4u.Api.Fintech.Object.SDKDetail;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;

public class EKYCProcessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_ekyc_process);
            SDKDetail sdkDetail = getIntent().getParcelableExtra("SDKDetail");
            TextView contentTv = findViewById(R.id.contentTv);
            TextView installApp = findViewById(R.id.installApp);
            String username = sdkDetail.getApiOutletID();
            String superMerchantId = sdkDetail.getApiPartnerID();
            contentTv.setText(Html.fromHtml(getString(R.string.ekyc_steps, username + "", superMerchantId + "")));

            installApp.setOnClickListener(v -> {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ApplicationConstant.INSTANCE.baseUrl + "apk/Icici.verification.apk")));
                } catch (ActivityNotFoundException anfe) {

                }
            });
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
