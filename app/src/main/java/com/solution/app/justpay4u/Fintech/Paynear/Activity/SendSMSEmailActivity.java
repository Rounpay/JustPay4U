package com.solution.app.justpay4u.Fintech.Paynear.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pnsol.sdk.interfaces.PaymentTransactionConstants;
import com.pnsol.sdk.payment.PaymentInitialization;
import com.pnsol.sdk.vo.response.TransactionStatusResponse;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pradeep.arige
 * updated by vasanthi.k  on 08/12/2018
 */

public class SendSMSEmailActivity extends AppCompatActivity implements OnClickListener, PaymentTransactionConstants {

    private Button send;
    private EditText email, sms, cName;
    private TransactionStatusResponse response;
    private String templateName;
    private RadioGroup radiodevice, radioComm;
    private String paymentMode;
    private CustomLoader loader;
    // The Handler that gets information back from the PaymentProcessThread
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            loader.dismiss();
            if (msg.what == SUCCESS) {
                Toast.makeText(SendSMSEmailActivity.this, getString(R.string.success),
                        Toast.LENGTH_LONG).show();
                finish();
            } else if (msg.what == FAIL) {
                Toast.makeText(SendSMSEmailActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
                finish();
            } else if (msg.what == ERROR_MESSAGE) {
                Toast.makeText(SendSMSEmailActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(SendSMSEmailActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
                finish();
            }
        }

        ;
    };

    public static boolean isValidMobileNo(String str) {
        Pattern mobileNo = Pattern.compile("\\d{10}");
        Matcher matcher = mobileNo.matcher(str);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    // Email validation
    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paynear_sms_email);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.setCancelable(false);
        email = (EditText) findViewById(R.id.email);
        sms = (EditText) findViewById(R.id.sms);
        send = (Button) findViewById(R.id.send);
        cName = (EditText) findViewById(R.id.customerName);
        send.setOnClickListener(this);
        response = (TransactionStatusResponse) getIntent().getSerializableExtra("response");
        radiodevice = (RadioGroup) findViewById(R.id.radiodevice);

        radiodevice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.sale) {
                    paymentMode = SALE;
                } else if (checkedId == R.id.cashpos) {
                    paymentMode = CASH_AT_POS;
                } else if (checkedId == R.id.balanceEnquiry) {

                    paymentMode = BALANCE_ENQUIRY;
                } else if (checkedId == R.id.matm) {

                    paymentMode = MICRO_ATM;
                }
            }
        });

        // templateName = getIntent().getStringExtra("templateName");
    }

    @Override
    public void onClick(View v) {
        boolean validFlag = false;
        String eMailIdStr = email.getText().toString();
        String mobileNoStr = sms.getText().toString();
        String customerName = cName.getText().toString();
        if (mobileNoStr != null && !mobileNoStr.isEmpty() || eMailIdStr != null && !eMailIdStr.isEmpty()) {
            if (mobileNoStr != null && !mobileNoStr.isEmpty()) {
                if (isValidMobileNo(mobileNoStr)) {
                    validFlag = true;
                } else {
                    validFlag = false;
                    sms.setError(getString(R.string.mobilenumber_error));
                    sms.requestFocus();
                    Toast.makeText(SendSMSEmailActivity.this,
                            getString(R.string.mobilenumber_error),
                            Toast.LENGTH_SHORT).show();
                }
            }
            if (eMailIdStr != null && !eMailIdStr.isEmpty()) {
                if (isValidEmail(eMailIdStr)) {
                    validFlag = true;
                } else {
                    validFlag = false;
                    email.setError(getString(R.string.err_msg_email));
                    email.requestFocus();
                    Toast.makeText(SendSMSEmailActivity.this,
                                    getString(R.string.err_msg_email), Toast.LENGTH_SHORT)
                            .show();
                }
            }
            if (validFlag) {
                try {
                    loader.show();
                    PaymentInitialization initialization = new PaymentInitialization(
                            getApplicationContext());
                    initialization.sendSMSEmail(handler, response.getReferenceNumber(), mobileNoStr, eMailIdStr, customerName, paymentMode);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        } else {
            sms.setError(getString(R.string.mobilenumber_error));
            sms.requestFocus();
            email.setError(getString(R.string.err_msg_email));
            email.requestFocus();
            Toast.makeText(SendSMSEmailActivity.this,
                            getString(R.string.enter_mobile_email), Toast.LENGTH_SHORT)
                    .show();
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
