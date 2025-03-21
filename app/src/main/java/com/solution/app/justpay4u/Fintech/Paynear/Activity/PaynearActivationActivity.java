package com.solution.app.justpay4u.Fintech.Paynear.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.pnsol.sdk.auth.AccountValidator;
import com.pnsol.sdk.interfaces.PaymentTransactionConstants;
import com.pnsol.sdk.vo.response.LoginResponse;
import com.solution.app.justpay4u.Fintech.Activities.PermissionActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;

/**
 * @Author : Bhavani.A
 * @Version : V_13
 * @Date : Fed 20, 2017
 * @Copyright : (C) Paynear Solutions Pvt. Ltd.
 * <p>
 * updated By vasanthi.k 10/22/2018
 */

public class PaynearActivationActivity extends AppCompatActivity implements
        PaymentTransactionConstants {


    private String merchantAPIKey = "8A23F8787722"/*"763432092B47"*/;
    private String partnerAPIKey = "41401A93062F"/*"763432092B47"*/;
    private int INTENT_PERMISSION_STORAGE = 123;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg != null) {
                if (msg.what == SUCCESS) {
                    LoginResponse vo = (LoginResponse) msg.obj;

                    //Toast.makeText(PaynearActivationActivity.this, "" + vo.getResponseMessage(), Toast.LENGTH_LONG).show();
                    Intent i = new Intent(PaynearActivationActivity.this, PaynearActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                    finish();
                } else if (msg.what == FAIL) {
                    alertMessageError("Failed", (String) msg.obj);
                   /* Toast.makeText(PaynearActivationActivity.this, (String) msg.obj,
                            Toast.LENGTH_SHORT).show();*/
                } else if (msg.what == ERROR_MESSAGE) {
                    alertMessageError("Error", (String) msg.obj);
                    /*Toast.makeText(PaynearActivationActivity.this, (String) msg.obj,
                            Toast.LENGTH_LONG).show();*/
                } else {
                    alertMessageError("Alert", (String) msg.obj);
                    /*Toast.makeText(PaynearActivationActivity.this, (String) msg.obj,
                            Toast.LENGTH_LONG).show();*/
                }
            } else {
                alertMessageError("Error", "null object error");
              /*  Toast.makeText(PaynearActivationActivity.this, "null object error",
                        Toast.LENGTH_LONG).show();*/
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_paynear_activation);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void alertMessageError(String title, String message) {

        new CustomAlertDialog(PaynearActivationActivity.this).ErrorWithSingleBtnCallBack(title, message, "Ok", false,
                new CustomAlertDialog.DialogCallBack() {
                    @Override
                    public void onPositiveClick() {
                        /* finish();*/
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });

    }


    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(PaynearActivationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(this, PermissionActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        } else {
            AccountValidator validator = new AccountValidator(getApplicationContext());
            if (validator.isAccountActivated()) {
                Intent i = new Intent(PaynearActivationActivity.this, PaynearActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish();
            } else {
                validator.accountActivation(handler, merchantAPIKey, partnerAPIKey);
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
