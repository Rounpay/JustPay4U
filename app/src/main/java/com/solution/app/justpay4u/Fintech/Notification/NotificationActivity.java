package com.solution.app.justpay4u.Fintech.Notification;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.R;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_notification);
            String title = getIntent().getStringExtra("Title");
            String msg = getIntent().getStringExtra("Message");
            String imageUrl = getIntent().getStringExtra("Image");
            final String url = getIntent().getStringExtra("Url");
            final String time = getIntent().getStringExtra("Time");
            TextView titleTv = findViewById(R.id.title);
            TextView msgTv = findViewById(R.id.message);
            final ImageView imageIv = findViewById(R.id.banner);
            TextView detailBtn = findViewById(R.id.detailBtn);
            TextView timeTv = findViewById(R.id.time);
            if (time != null && !time.isEmpty()) {
                timeTv.setText(time + "");
            } else {
                findViewById(R.id.timeView).setVisibility(View.GONE);
            }
            titleTv.setText(title);
            msgTv.setText(msg);
            if (imageUrl != null && !imageUrl.isEmpty() && URLUtil.isValidUrl(imageUrl)) {
                imageIv.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(imageUrl)
                        .apply(RequestOptions.placeholderOf(R.drawable.defaultlogo))
                        .into(imageIv);
            } else {
                imageIv.setVisibility(View.GONE);
            }
            if (url != null && !url.isEmpty() && URLUtil.isValidUrl(url)) {
                detailBtn.setVisibility(View.VISIBLE);
                detailBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openBrowser(url);
                    }
                });
            } else {
                detailBtn.setVisibility(View.GONE);
            }

        });
    }

    void openBrowser(String url) {
        url = url.replaceAll(" ", "");
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException anfe) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } catch (ActivityNotFoundException anfe2) {

            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
