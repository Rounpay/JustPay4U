package com.solution.app.justpay4u.Fintech.Paynear.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pnsol.sdk.interfaces.DeviceType;
import com.pnsol.sdk.interfaces.PaymentTransactionConstants;
import com.pnsol.sdk.payment.PaymentInitialization;
import com.pnsol.sdk.vo.response.POSReceipt;
import com.pnsol.sdk.vo.response.TransactionResponse;
import com.pnsol.sdk.vo.response.TransactionStatusResponse;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.util.HashMap;
import java.util.List;

//import com.pnsol.sdk.newland.interfaces.NewlandPaymentConst;

/**
 * Created by Vasanthi.k 09/08/2018
 * updated by vasanthi.k  on 10/22/2018
 */

public class PaymentDetailsActivity extends AppCompatActivity implements
        PaymentTransactionConstants {

    ImageView statusIcon;
    CustomLoader loader;
    private EditText void_ref_no;
    private Button send, voidBt, saleCompletion, refundBt, btnPrint;
    private TextView statusTv, statusContentTv;
    // The Handler that gets information back from the PaymentProcessThread
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if (msg.what == SUCCESS) {
                List<TransactionStatusResponse> transactionStatusResponse = (List<TransactionStatusResponse>) msg.obj;

                setResultData(transactionStatusResponse);


                voidBt.setVisibility(View.VISIBLE);
                refundBt.setVisibility(View.VISIBLE);
                saleCompletion.setVisibility(View.VISIBLE);
                send.setVisibility(View.VISIBLE);
                void_ref_no.setVisibility(View.VISIBLE);
            }
            if (msg.what == FAIL) {
                Toast.makeText(PaymentDetailsActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
                //finish();
            } else if (msg.what == ERROR_MESSAGE) {
                Toast.makeText(PaymentDetailsActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
            }

        }

        ;
    };
    @SuppressLint("HandlerLeak")
    private final Handler voidHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if (msg.what == SUCCESS) {

                TransactionResponse hs = (TransactionResponse) msg.obj;
                setResultData(hs);

                Toast.makeText(PaymentDetailsActivity.this, getString(R.string.void_success), Toast.LENGTH_LONG).show();
            }
            if (msg.what == FAIL) {
                Toast.makeText(PaymentDetailsActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
            } else if (msg.what == ERROR_MESSAGE) {
                Toast.makeText(PaymentDetailsActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
            }

        }

        ;
    };
    @SuppressLint("HandlerLeak")
    private final Handler saleCmpltnHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            loader.dismiss();

            if (msg.what == SUCCESS) {

                TransactionResponse hs = (TransactionResponse) msg.obj;
                setResultData(hs);
                Toast.makeText(PaymentDetailsActivity.this, getString(R.string.sale_completion_success),
                        Toast.LENGTH_LONG).show();
            } else if (msg.what == FAIL) {
                Toast.makeText(PaymentDetailsActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();

            } else if (msg.what == ERROR_MESSAGE) {
                Toast.makeText(PaymentDetailsActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(PaymentDetailsActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
            }
        }

        ;
    };
    @SuppressLint("HandlerLeak")
    private final Handler refundHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if (msg.what == SUCCESS) {


                TransactionResponse hs = (TransactionResponse) msg.obj;
                setResultData(hs);
                Toast.makeText(PaymentDetailsActivity.this, getString(R.string.refund_success),
                        Toast.LENGTH_LONG).show();
            }
            if (msg.what == FAIL) {
                Toast.makeText(PaymentDetailsActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
                // finish();
            } else if (msg.what == ERROR_MESSAGE) {
                Toast.makeText(PaymentDetailsActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
            }
        }
    };
    private TransactionStatusResponse txnResponse;
    private boolean customerCopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.setCancelable(false);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_paynear_payment_details);
            loadUiElements();
        });


    }

    private void loadUiElements() {


        void_ref_no = (EditText) findViewById(R.id.void_ref_no);
        statusIcon = findViewById(R.id.statusIcon);
        send = (Button) findViewById(R.id.send);
        send.setVisibility(View.GONE);
        voidBt = (Button) findViewById(R.id.void_bt);
        saleCompletion = (Button) findViewById(R.id.sale_completion);
        refundBt = (Button) findViewById(R.id.refund_bt);
        btnPrint = (Button) findViewById(R.id.print_bt);
        statusContentTv = findViewById(R.id.statusContentTv);
        statusTv = findViewById(R.id.statusTv);

        txnResponse = (TransactionStatusResponse) getIntent().getSerializableExtra("txnResponse");
        setResultData(txnResponse);


        voidBt.setVisibility(View.VISIBLE);
        refundBt.setVisibility(View.VISIBLE);
        saleCompletion.setVisibility(View.VISIBLE);
        send.setVisibility(View.VISIBLE);
        void_ref_no.setVisibility(View.VISIBLE);
        btnPrint.setVisibility(View.VISIBLE);


        send.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(PaymentDetailsActivity.this,
                        SendSMSEmailActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.putExtra("response", txnResponse);
                startActivity(i);
            }
        });
        voidBt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {

                    PaymentInitialization initialization = new PaymentInitialization(
                            PaymentDetailsActivity.this);
                    initialization.initiateVoidTransaction(voidHandler, txnResponse.getReferenceNumber(), "", "");
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        });
        saleCompletion.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String saleComplAmt = void_ref_no.getText().toString();
                    if (saleComplAmt != null && saleComplAmt.length() > 0) {
                        loader.show();
                        PaymentInitialization initialization = new PaymentInitialization(
                                PaymentDetailsActivity.this);
                        initialization.initiateSaleCompletionTransaction(saleCmpltnHandler, Double.parseDouble(saleComplAmt),
                                txnResponse.getReferenceNumber(), "", "");
                    } else {
                        Toast.makeText(PaymentDetailsActivity.this, getString(R.string.enter_sale_completion_amt), Toast.LENGTH_SHORT).show();

                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        });
        refundBt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String refundAmt = void_ref_no.getText().toString();
                    if (refundAmt != null && refundAmt.length() > 0) {
                        PaymentInitialization initialization = new PaymentInitialization(
                                PaymentDetailsActivity.this);
                        initialization.initiateRefundTransaction(refundHandler, txnResponse.getReferenceNumber(), Double.parseDouble(refundAmt), "", "");
                    } else {
                        Toast.makeText(PaymentDetailsActivity.this, getString(R.string.enter_refund_amt), Toast.LENGTH_SHORT).show();

                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        });

        btnPrint.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentInitialization initialization = new PaymentInitialization(
                        PaymentDetailsActivity.this);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.payswiff);
                initialization.initiatePrintReceipt(printHandler, DeviceType.N910, txnResponse.getReferenceNumber(), null, customerCopy);
            }
        });
    }

    void setResultData(TransactionStatusResponse data) {
        if (data != null) {
            statusTv.setText(data.getTransactionStatus() + "");
            if (data.getTransactionStatus() != null && data.getTransactionStatus().toLowerCase().contains("approved")) {
                statusIcon.setImageResource(R.drawable.ic_check_mark);
            } else if (data.getTransactionStatus() != null && data.getTransactionStatus().toLowerCase().contains("pending")) {
                statusIcon.setImageResource(R.drawable.ic_pending_outline);
            } else {
                statusIcon.setImageResource(R.drawable.ic_cross_mark);
            }
            String contentStr = "";

            // contentStr = contentStr + "<b>Current Balance : </b>" + "\u20B9 " + UtilMethods.INSTANCE.formatedAmount(data.getCurrentBalance() + "") + "<br/>";
            // contentStr = contentStr + "<b>Ledger Balance : </b>" + "\u20B9 " + UtilMethods.INSTANCE.formatedAmount(data.getLedgerBalance() + "") + "<br/>";
            contentStr = contentStr + "<b>Merchant Id : </b>" + data.getMerchantId() + "" + "<br/>";
            contentStr = contentStr + "<b>Payment Mode : </b>" + data.getPaymentMethod() + "" + "<br/>";

            if (data.getCardBrand() != null && !data.getCardBrand().isEmpty()) {
                contentStr = contentStr + "<b>Card Brand : </b>" + data.getCardBrand() + "" + "<br/>";
            }
            if (data.getCardType() != null && !data.getCardType().isEmpty()) {
                contentStr = contentStr + "<b>Card Type : </b>" + data.getCardType() + "" + "<br/>";
            }
            if (data.getCardHolderName() != null && !data.getCardHolderName().isEmpty()) {
                contentStr = contentStr + "<b>Card Holder Name : </b>" + data.getCardHolderName() + "" + "<br/>";
            }
            if (data.getCardNumber() != null && !data.getCardNumber().isEmpty()) {
                contentStr = contentStr + "<b>Card Number : </b>" + data.getCardNumber() + "" + "<br/>";
            }

            if (data.getMerchantRefNo() != null && !data.getMerchantRefNo().isEmpty()) {
                contentStr = contentStr + "<b>Merchant Ref. No. : </b>" + data.getMerchantRefNo() + "" + "<br/>";
            }
            if (data.getTerminalSerialNo() != null && !data.getTerminalSerialNo().isEmpty()) {
                contentStr = contentStr + "<b>Terminal Serial No. : </b>" + data.getTerminalSerialNo() + "" + "<br/>";
            }
            contentStr = contentStr + "<b>Payment Dtae : </b>" + data.getTransactionDateNTime() + "";
            statusContentTv.setText(Html.fromHtml(contentStr));
        } else {
            statusTv.setText("Data not Found");
            statusContentTv.setVisibility(View.GONE);
            statusIcon.setImageResource(R.drawable.ic_cross_mark);

        }
    }

    void setResultData(List<TransactionStatusResponse> data) {
        if (data != null && data.size() > 0) {
            statusTv.setVisibility(View.GONE);
            statusIcon.setVisibility(View.GONE);
            String contentStr = "";
            int count = 1;
            for (TransactionStatusResponse item : data) {
                contentStr = contentStr + "<b>" + count + " :- " + "Status : " + item.getTransactionStatus() + "" + "</b>" + "<br/>";
                //contentStr = contentStr + "<b>Current Balance : </b>" + "\u20B9 " + UtilMethods.INSTANCE.formatedAmount(item.getCurrentBalance() + "") + "<br/>";
                //contentStr = contentStr + "<b>Ledger Balance : </b>" + "\u20B9 " + UtilMethods.INSTANCE.formatedAmount(item.getLedgerBalance() + "") + "<br/>";
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
            statusIcon.setImageResource(R.drawable.ic_cross_mark);


        }
    }

    void setResultData(TransactionResponse data) {
        if (data != null) {
            statusTv.setText(data.getTransactionStatus() + "");
            if (data.getTransactionStatus() != null && data.getTransactionStatus().toLowerCase().contains("approved")) {
                statusIcon.setImageResource(R.drawable.ic_check_mark);
            } else if (data.getTransactionStatus() != null && data.getTransactionStatus().toLowerCase().contains("pending")) {
                statusIcon.setImageResource(R.drawable.ic_pending_outline);
            } else {
                statusIcon.setImageResource(R.drawable.ic_cross_mark);
            }

            String contentStr = "<b>Amount : </b>" + Utility.INSTANCE.formatedAmountWithRupees(data.getAmount() + "") + "<br/>";
            contentStr = contentStr + "<b>RRN Number : </b>" + data.getRrn() + "" + "<br/>";
            statusContentTv.setText(Html.fromHtml(contentStr));
        } else {
            statusTv.setText("Data not Found");
            statusTv.setVisibility(View.VISIBLE);
            statusContentTv.setVisibility(View.GONE);
            statusIcon.setImageResource(R.drawable.ic_cross_mark);
        }
    }

    private void showDialogBox() {

        final POSReceipt vo = (POSReceipt) getIntent()
                .getSerializableExtra("vo");
        final PaymentInitialization initialization = new PaymentInitialization(
                PaymentDetailsActivity.this);
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.payswiff);
        AlertDialog.Builder dialog = new AlertDialog.Builder(PaymentDetailsActivity.this);
        // dialog.setCancelable(false);
        dialog.setTitle("Print Customer Copy");
        dialog.setMessage("Do you want to Print Customer Copy?");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                HashMap<String, String> meMap = new HashMap<String, String>();
                meMap.put("Color1", "Red");
                meMap.put("Color2", "Blue");
                meMap.put("Color3", "Green");
                meMap.put("Color4", "White");
                customerCopy = true;
                initialization.initiatePrintReceipt(printHandler, DeviceType.N910, txnResponse.getReferenceNumber(), bitmap, customerCopy);

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Action for "Cancel".
                return;
            }
        });

        final AlertDialog alert = dialog.create();
        alert.show();


    }    @SuppressLint("HandlerLeak")
    private final Handler printHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if (msg.what == SUCCESS) {
                /*POSReceipt posReceipts = (POSReceipt) msg.obj;*/
                Toast.makeText(PaymentDetailsActivity.this, "Success",
                        Toast.LENGTH_LONG).show();
                if (!customerCopy) {
                    showDialogBox();
                } else {
                    return;
                }
            } else if (msg.what == FAIL) {
                Toast.makeText(PaymentDetailsActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
                //finish();
            } else if (msg.what == ERROR_MESSAGE) {
                Toast.makeText(PaymentDetailsActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(PaymentDetailsActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
            }
        }

        ;

    };

    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(PaymentDetailsActivity.this, PaynearActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }


}
