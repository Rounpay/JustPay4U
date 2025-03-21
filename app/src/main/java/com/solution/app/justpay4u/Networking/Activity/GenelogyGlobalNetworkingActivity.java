package com.solution.app.justpay4u.Networking.Activity;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.solution.app.justpay4u.R;

public class GenelogyGlobalNetworkingActivity extends AppCompatActivity {

    View loaderView;
    WebView mWebView;
    String urlValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_networking_genelogy);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            onWindowFocusChanged(true);
            setTitle("Geneology Global Report");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            loaderView = findViewById(R.id.loaderView);
            mWebView = findViewById(R.id.webView);
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled( true );
           /* webSettings.setDatabaseEnabled(true);
            webSettings.setDatabasePath("/data/data/"+getPackageName()+"/databases");*/
            webSettings.setDomStorageEnabled(true);
            // Optional: Enable local file access
            webSettings.setAllowFileAccess(true);
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);


            /*if ( !isNetworkAvailable() ) { // loading offline
                webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
            }*/

       /* webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        mWebView.setInitialScale(1);*/
            webSettings.setBuiltInZoomControls(true);
            webSettings.setSupportZoom(true);
            webSettings.setDisplayZoomControls(false);
            mWebView.setInitialScale(100);
            mWebView.setWebViewClient(new MyWebViewClient());
            mWebView.setWebChromeClient(new MyWebChromeClient());
            loaderView.setVisibility(View.VISIBLE);
//            urlValue=ApplicationConstant.INSTANCE.genelogyGlobalUrl + getIntent().getStringExtra("UserId");
            urlValue="https://justpay4u.com"+getIntent().getStringExtra("Url")/*+getIntent().getStringExtra("UserId")*/;
//            mWebView.loadUrl(ApplicationConstant.INSTANCE.genelogyGlobalUrl + getIntent().getStringExtra("UserId"));
            mWebView.loadUrl(urlValue);
//            Log.d("Weburl",urlValue+"");
//            Toast.makeText(this, urlValue+"", Toast.LENGTH_SHORT).show();
           // Log.d("MATRIXURL",urlValue);
            //   mWebView.loadUrl(ApplicationConstant.INSTANCE.genelogyGlobalUrl.replaceAll(getString(R.string.userId),getIntent().getStringExtra("UserId")));

        });


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            loaderView.setVisibility(View.VISIBLE);
            mWebView.loadUrl(url);
            return false;
        }


    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

            if(newProgress>95){
                loaderView.setVisibility(View.GONE);
            }else {
                loaderView.setVisibility(View.VISIBLE);
            }
        }

    }
}
