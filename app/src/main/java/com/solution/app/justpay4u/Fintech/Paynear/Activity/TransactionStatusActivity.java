package com.solution.app.justpay4u.Fintech.Paynear.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pnsol.sdk.interfaces.PaymentTransactionConstants;
import com.pnsol.sdk.payment.PaymentInitialization;
import com.pnsol.sdk.util.SharedPreferenceDataUtil;
import com.pnsol.sdk.vo.response.TransactionStatusResponse;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.util.List;


/**
 * @author pradeep.arige
 * updated by vasanthi.k  on 08/12/2018
 */
public class TransactionStatusActivity extends AppCompatActivity implements PaymentTransactionConstants {

    CustomLoader loader;
    private Button status;
    private EditText trxnId, refNo;
    private String paymentMode = SALE;
    private TextView statusTv, statusContentTv;
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {

        @SuppressWarnings("unchecked")
        public void handleMessage(android.os.Message msg) {
            if (msg.what == SUCCESS) {
                loader.dismiss();
                List<TransactionStatusResponse> transactionStatusResponse = (List<TransactionStatusResponse>) msg.obj;


                if (transactionStatusResponse.size() > 1 && transactionStatusResponse != null) {
                    setResultData(transactionStatusResponse);

                } else if (transactionStatusResponse.size() == 1 && transactionStatusResponse != null) {


                    /*Intent i = new Intent(TransactionStatusActivity.this, PaymentDetailsActivity.class);*/
                    Intent i = new Intent(TransactionStatusActivity.this, PaymentDetailsSlipActivity.class);
                    i.putExtra("txnResponse", transactionStatusResponse.get(0));
                    i.putExtra("referenceNumber", refNo.getText().toString());
                    startActivity(i);

                }

            } else if (msg.what == FAIL) {
                loader.dismiss();
                statusTv.setVisibility(View.VISIBLE);
                statusContentTv.setVisibility(View.GONE);
                statusTv.setText(" " + (String) msg.obj);
            } else {
                loader.dismiss();
                statusTv.setVisibility(View.VISIBLE);
                statusContentTv.setVisibility(View.GONE);
                statusTv.setText(" " + (String) msg.obj);
            }

        }
    };
    private RadioGroup radiodevice;
    private SharedPreferenceDataUtil prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_paynear_transaction_status);

            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            loader.setCancelable(false);
            radiodevice = (RadioGroup) findViewById(R.id.radiodevice);
            status = (Button) findViewById(R.id.status);
            trxnId = (EditText) findViewById(R.id.trxnid);
            refNo = (EditText) findViewById(R.id.refno);
            statusContentTv = findViewById(R.id.statusContentTv);
            statusTv = findViewById(R.id.statusTv);


            status.setOnClickListener(new OnClickListener() {

                @SuppressWarnings("null")
                @Override
                public void onClick(View v) {
                    String trxnID = trxnId.getText().toString();
                    String merchantRefNo = refNo.getText().toString();
                    if (trxnID == null && trxnID.isEmpty() || merchantRefNo == null && merchantRefNo.isEmpty()) {
                        trxnId.setError(getString(R.string.enter_txn_id));
                        trxnId.requestFocus();
                        Toast.makeText(TransactionStatusActivity.this, getString(R.string.enter_txn_id), Toast.LENGTH_SHORT).show();
                    } else {

                        try {
                            loader.show();
                            PaymentInitialization initialization = new PaymentInitialization(getApplicationContext());
                            String id = null;
                            if (!trxnID.isEmpty()) {
                                id = trxnID;
                            }
                            prefs = new SharedPreferenceDataUtil(getApplicationContext());
//                    initialization.initiateTransactionDetails(handler, id, merchantRefNo,"","");
                            initialization.initiateTransactionDetails(handler, id, merchantRefNo, paymentMode, "", "");
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            loader.dismiss();
                            Toast.makeText(TransactionStatusActivity.this, getString(R.string.enter_numeric_txn_id), Toast.LENGTH_SHORT).show();
                        } catch (RuntimeException e) {
                            loader.dismiss();
                            e.printStackTrace();
                        }
                    }
                }
            });

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
        });

    }

    void setResultData(List<TransactionStatusResponse> data) {
        if (data != null) {
            statusTv.setVisibility(View.GONE);
            statusContentTv.setVisibility(View.VISIBLE);
            String contentStr = "";
            int count = 1;
            for (TransactionStatusResponse item : data) {
                contentStr = contentStr + "<b>" + count + " :- " + "Status : " + item.getTransactionStatus() + "" + "</b>" + "<br/>";
                contentStr = contentStr + "<b>Current Balance : </b>" + Utility.INSTANCE.formatedAmountWithRupees(item.getCurrentBalance() + "") + "<br/>";
                contentStr = contentStr + "<b>Ledger Balance : </b>" + Utility.INSTANCE.formatedAmountWithRupees(item.getLedgerBalance() + "") + "<br/>";
                contentStr = contentStr + "<b>Merchant Id : </b>" + item.getMerchantId() + "" + "<br/>";
                contentStr = contentStr + "<b>Payment Mode : </b>" + item.getPaymentMethod() + "" + "<br/>";
                if (item.getCardHolderName() != null && !item.getCardHolderName().isEmpty()) {
                    contentStr = contentStr + "<b>Card Holder Name : </b>" + item.getCardHolderName() + "" + "<br/>";
                }
                if (item.getCardBrand() != null && !item.getCardBrand().isEmpty()) {
                    contentStr = contentStr + "<b>Card Brand : </b>" + item.getCardBrand() + "" + "<br/>";
                }
                if (item.getCardNumber() != null && !item.getCardNumber().isEmpty()) {
                    contentStr = contentStr + "<b>Card Number : </b>" + item.getCardNumber() + "" + "<br/>";
                }
                if (item.getCardType() != null && !item.getCardType().isEmpty()) {
                    contentStr = contentStr + "<b>Card Type : </b>" + item.getCardType() + "" + "<br/>";
                }
                if (item.getMerchantRefNo() != null && !item.getMerchantRefNo().isEmpty()) {
                    contentStr = contentStr + "<b>Merchant Ref. No. : </b>" + item.getMerchantRefNo() + "" + "<br/>";
                }
                if (item.getTerminalSerialNo() != null && !item.getTerminalSerialNo().isEmpty()) {
                    contentStr = contentStr + "<b>Terminal Serial No. : </b>" + item.getTerminalSerialNo() + "" + "<br/>";
                }
                contentStr = contentStr + "<b>Payment Dtae : </b>" + item.getTransactionDateNTime() + "";
                count++;
            }


            statusContentTv.setText(Html.fromHtml(contentStr));
        } else {
            statusTv.setText("Data not Found");
            statusTv.setVisibility(View.VISIBLE);
            statusContentTv.setVisibility(View.GONE);
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
