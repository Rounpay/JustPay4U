package com.solution.app.justpay4u.Fintech.PSA.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomLoader;

public class UTILoginActivity extends AppCompatActivity {
    WebView panWeb;
    CustomLoader customLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customLoader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        customLoader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_utilogin);
            panWeb = findViewById(R.id.panWeb);
            panWeb.setWebViewClient(new MyBrowser(customLoader));

            panWeb.getSettings().setLoadsImagesAutomatically(true);
            panWeb.getSettings().setJavaScriptEnabled(true);
            panWeb.setInitialScale(1);
            panWeb.getSettings().setLoadWithOverviewMode(true);
            panWeb.getSettings().setUseWideViewPort(true);
            panWeb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            panWeb.setScrollbarFadingEnabled(false);

            panWeb.loadUrl("https://www.psaonline.utiitsl.com/psaonline/");
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    private class MyBrowser extends WebViewClient {

        private CustomLoader customLoader;


        public MyBrowser(CustomLoader customLoader) {
            this.customLoader = customLoader;
            customLoader.show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (customLoader != null)
                if (customLoader.isShowing())
                    customLoader.dismiss();
        }
    }
}

