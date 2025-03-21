package com.solution.app.justpay4u.Fintech.Paynear.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.pnsol.sdk.interfaces.PaymentTransactionConstants;
import com.solution.app.justpay4u.R;

/**
 * @Author : Bhavani.A
 * @Version : V_13
 * @Date : Fed 20, 2017
 * @Copyright : (C) Paynear Solutions Pvt. Ltd.
 */

public class EmipaymentwithCodeActivity extends AppCompatActivity implements PaymentTransactionConstants {
    private static String MAC_ADDRESS = "macAddress";
    private static String DEVICE_COMMUNICATION_MODE = "transactionmode";
    private Button pay;
    private EditText paymentcode;
    private int deviceCommMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paynear_emi_paymet_with_code);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pay = (Button) findViewById(R.id.pay);
        paymentcode = (EditText) findViewById(R.id.paymentcode);
        deviceCommMode = getIntent().getIntExtra(DEVICE_COMMUNICATION_MODE, 0);
        pay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (paymentcode.getText().toString().length() > 0) {
                    if (paymentcode.getText().toString().length() == 5) {
                        Intent i = new Intent(EmipaymentwithCodeActivity.this, PaymentTransactionActivity.class);
                        i.putExtra(MAC_ADDRESS, getIntent().getStringExtra(MAC_ADDRESS));
                        i.putExtra(PAYMENT_TYPE, EMI);
                        i.putExtra(DEVICE_COMMUNICATION_MODE, deviceCommMode);
                        i.putExtra("amount", getIntent().getStringExtra("amount"));
                        i.putExtra("paymentcode", paymentcode.getText().toString());
                        startActivity(i);
                        finish();
                    } else {
                        paymentcode.setError(getString(R.string.invalid_code));
                        paymentcode.requestFocus();
                    }
                } else {
                    paymentcode.setError(getString(R.string.pls_enter_code));
                    paymentcode.requestFocus();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
