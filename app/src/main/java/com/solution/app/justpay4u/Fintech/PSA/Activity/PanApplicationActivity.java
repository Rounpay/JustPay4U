package com.solution.app.justpay4u.Fintech.PSA.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.solution.app.justpay4u.R;

public class PanApplicationActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout btn_panLogin, btn_purchaseToken, btn_viewCredential;
    WebView t_n_c;


    String PANID, psaRequestId, outletId, userId, emailId, mobileNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_pan_application);

            PANID = getIntent().getExtras().getString("PANID", "");
            psaRequestId = getIntent().getExtras().getString("psaRequestId", "");
            outletId = getIntent().getExtras().getString("outletId", "");
            userId = getIntent().getExtras().getString("userId", "");
            emailId = getIntent().getExtras().getString("emailId", "");
            mobileNo = getIntent().getExtras().getString("mobileNo", "");

            btn_panLogin = findViewById(R.id.btn_panLogin);
            btn_purchaseToken = findViewById(R.id.btn_purchaseToken);
            btn_viewCredential = findViewById(R.id.btn_viewCredential);
            t_n_c = findViewById(R.id.t_n_c);
            t_n_c.loadData(getString(R.string.pan_tc), "text/html", "UTF-8");

            btn_viewCredential.setOnClickListener(this);
            btn_purchaseToken.setOnClickListener(this);
            btn_panLogin.setOnClickListener(this);
        });


    }

    @Override
    public void onClick(View view) {

        if (view == btn_panLogin) {

            Intent intent = new Intent(PanApplicationActivity.this, UTILoginActivity.class);
            startActivity(intent);
            finish();
        } else if (view == btn_viewCredential) {
            viewCredential();
        } else if (view == btn_purchaseToken) {


            Intent intent = new Intent(PanApplicationActivity.this, PurchaseTokenActivity.class);

            // intent.putExtra("panList", panList);
            intent.putExtra("PANID", PANID);
           /* intent.putExtra("id",id);
            intent.putExtra("amount",amount);
            intent.putExtra("panType",panType);*/
            startActivity(intent);
        }

    }

    private void viewCredential() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_view_credential, null);
        TextView tv_psaId = viewMyLayout.findViewById(R.id.psaid);
        TextView tv_paspassword = viewMyLayout.findViewById(R.id.paspassword);
        View okButton = viewMyLayout.findViewById(R.id.okButton);
        TextView copyPsaId = viewMyLayout.findViewById(R.id.copyPsaId);
        TextView copyPass = viewMyLayout.findViewById(R.id.copyPass);

        tv_psaId.setText(PANID);
        tv_paspassword.setText(PANID);

        final Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        dialog.setCancelable(false);
        dialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        copyPsaId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClipboard(tv_psaId.getText().toString());
            }
        });
        copyPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClipboard(tv_paspassword.getText().toString());
            }
        });

        dialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);


    }

    private void setClipboard(String text) {

        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Share Link", text);
                clipboard.setPrimaryClip(clip);
            }

            Toast.makeText(PanApplicationActivity.this, "Text Copied to clipboard", Toast.LENGTH_LONG).show();
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_view) {

        }
        return super.onOptionsItemSelected(item);
    }*/


    private void getId() {

    }
}
