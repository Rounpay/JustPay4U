package com.solution.app.justpay4u.Fintech.Paynear.Activity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pnsol.sdk.interfaces.PaymentTransactionConstants;
import com.pnsol.sdk.payment.PaymentInitialization;
import com.pnsol.sdk.vo.response.ICCTransactionResponse;
import com.pnsol.sdk.vo.response.TransactionStatusResponse;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pradeep.arige
 * updated by vasanthi.k  on 10/22/2018
 */

/*
 * This class is used to process the payment transaction. From the
 * SharedPreferences gets the Bluetooth MAC address. If the MAC address is
 * presents within SharedPreferences then it connects the BluetoothConnection
 * class and establish the BluetoothSocket between the PED and application to
 * process the payment transaction. Otherwise, if MAC address is not presents in
 * SharedPreferences then displays dialog box
 */

public class PaymentTransactionActivity extends AppCompatActivity implements
        PaymentTransactionConstants {

    public static final int REQUEST_ENABLE_BT = 1;
    private static String DEVICE_COMMUNICATION_MODE = "transactionmode";
    private static String DEVICE_NAME = "devicename";
    private static String MAC_ADDRESS = "macAddress";
    private static String DEVICE_TYPE = "devicetype";
    View loaderView;
    private BluetoothAdapter mBtAdapter;
    private boolean bluetoothOnFlag, checkFlag;
    private String amount = "11.00";
    private String cashBackAmount = null;
    private String merchantRefNo, deviceName, paymentoptioncode, deviceMACAddress, paymentType;
    private ListView emvList;
    private int deviceCommMode;
    private PaymentInitialization initialization;
    private com.pnsol.sdk.vo.request.EMI emivo;
    //private int deviceType;
    private String deviceSerial;
    private boolean isActivityOpen;
    //CustomAlertDialog mCustomAlertDialog;

    private ICCTransactionResponse iCCTransactionResponse;
    // The Handler that gets information back from the PaymentProcessThread
    @SuppressLint("HandlerLeak")
    private final Handler handlerTransactionDetail = new Handler() {

        public void handleMessage(android.os.Message msg) {
            loaderView.setVisibility(View.GONE);
            if (msg.what == SUCCESS) {
                //
                List<TransactionStatusResponse> transactionStatusResponse = (List<TransactionStatusResponse>) msg.obj;

                /*Intent i = new Intent(TransactionDetailsActivity.this, PaymentDetailsActivity.class);*/
                Intent i = new Intent(PaymentTransactionActivity.this, PaymentDetailsSlipActivity.class);
                i.putExtra("txnResponse", transactionStatusResponse.get(0));
                if (iCCTransactionResponse != null) {
                    i.putExtra("referenceNumber", iCCTransactionResponse.getReferenceNumber());
                    i.putExtra("rrn", iCCTransactionResponse.getRrn());
                }
                finish();
                startActivity(i);

            } else if (msg.what == FAIL) {
                alertMessageWithFinshError("Failed", (String) msg.obj);
                /*Toast.makeText(PaymentTransactionActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();*/
                //finish();
            } else if (msg.what == ERROR_MESSAGE) {
                alertMessageWithFinshError("Error", (String) msg.obj);
               /* Toast.makeText(PaymentTransactionActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();*/
            } else {
                if (msg.obj != null) {
                    alertMessageWithFinshError("Alert", (String) msg.obj);
                    /*Toast.makeText(PaymentTransactionActivity.this, (String) msg.obj,
                            Toast.LENGTH_LONG).show();*/
                }
            }

        }

        ;
    };
    // The Handler that gets information back from the PaymentProcessThread
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            checkFlag = true;
            if (msg.what == SOCKET_NOT_CONNECTED) {

                alertMessage((String) msg.obj);
            }
            if (msg.what == QPOS_ID) {
                alertMessageWithFinshError("Alert", (String) msg.obj);
                /*Toast.makeText(PaymentTransactionActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                finish();*/

            } else if (msg.what == CHIP_TRANSACTION_APPROVED
                    || msg.what == SWIP_TRANSACTION_APPROVED) {
                iCCTransactionResponse = (ICCTransactionResponse) msg.obj;
                if (iCCTransactionResponse.isSignatureRequired()) {
                    Intent i = new Intent(PaymentTransactionActivity.this, SignatureCaptureActivity.class);
                    i.putExtra("vo", iCCTransactionResponse);
                    //mpaysdk 2.0
                    i.putExtra("paymentType", paymentType);
                    finish();
                    PaymentTransactionActivity.this.startActivity(i);

                } else {
                    /*Intent i = new Intent(PaymentTransactionActivity.this, TransactionDetailsActivity.class);

                    i.putExtra("vo", iCCTransactionResponse);
                    //mpaysdk 2.0
                    i.putExtra("paymentType", paymentType);
                    finish();
                    PaymentTransactionActivity.this.startActivity(i);*/

                    try {
                        loaderView.setVisibility(View.VISIBLE);
                        PaymentInitialization initialization = new PaymentInitialization(PaymentTransactionActivity.this);
                        initialization.initiateTransactionDetails(handlerTransactionDetail, iCCTransactionResponse.getReferenceNumber(), null, paymentType, null, null);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }

            } else if (msg.what == CHIP_TRANSACTION_DECLINED
                    || msg.what == SWIP_TRANSACTION_DECLINED) {
                iCCTransactionResponse = (ICCTransactionResponse) msg.obj;
                if (isActivityOpen) {

                    new CustomAlertDialog(PaymentTransactionActivity.this).ErrorWithSingleBtnCallBack("Transaction Declined", "Transaction Status : " + iCCTransactionResponse.getResponseCode() + ":" + iCCTransactionResponse.getResponseMessage(), "Ok", false,
                            new CustomAlertDialog.DialogCallBack() {
                                @Override
                                public void onPositiveClick() {

                                   /* Intent i = new Intent(PaymentTransactionActivity.this,
                                            TransactionDetailsActivity.class);

                                    i.putExtra("vo", iCCTransactionResponse);
                                    i.putExtra("paymentType", paymentType);
                                    PaymentTransactionActivity.this.startActivity(i);

                                    finish();*/
                                    try {
                                        loaderView.setVisibility(View.VISIBLE);
                                        PaymentInitialization initialization = new PaymentInitialization(PaymentTransactionActivity.this);
                                        initialization.initiateTransactionDetails(handlerTransactionDetail, iCCTransactionResponse.getReferenceNumber(), null, paymentType, null, null);
                                    } catch (RuntimeException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onNegativeClick() {

                                }
                            });
                } else {

                   /* Intent i = new Intent(PaymentTransactionActivity.this,
                            TransactionDetailsActivity.class);

                    i.putExtra("vo", iCCTransactionResponse);
                    i.putExtra("paymentType", paymentType);
                    PaymentTransactionActivity.this.startActivity(i);
                    Toast.makeText(PaymentTransactionActivity.this, "Transaction Status : " + iCCTransactionResponse.getResponseCode() + ":" + iCCTransactionResponse.getResponseMessage(), Toast.LENGTH_LONG).show();
                    finish();*/

                    try {
                        Toast.makeText(PaymentTransactionActivity.this, "Transaction Status : " + iCCTransactionResponse.getResponseCode() + ":" + iCCTransactionResponse.getResponseMessage(), Toast.LENGTH_LONG).show();
                        loaderView.setVisibility(View.VISIBLE);
                        PaymentInitialization initialization = new PaymentInitialization(PaymentTransactionActivity.this);
                        initialization.initiateTransactionDetails(handlerTransactionDetail, iCCTransactionResponse.getReferenceNumber(), null, paymentType, null, null);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }


            } else if (msg.what == QPOS_DEVICE) {

                alertMessage((String) msg.obj);
            } else if (msg.what == TRANSACTION_FAILED) {

                if (msg.obj instanceof ICCTransactionResponse) {
                    iCCTransactionResponse = (ICCTransactionResponse) msg.obj;
                }

                if (paymentType.equalsIgnoreCase(EMI)) {

                    if (isActivityOpen) {
                        new CustomAlertDialog(PaymentTransactionActivity.this).ErrorWithSingleBtnCallBack("Transaction Failed", "Transaction Status : " + iCCTransactionResponse.getResponseCode() + ":" + iCCTransactionResponse.getResponseMessage(), "Ok", false,
                                new CustomAlertDialog.DialogCallBack() {
                                    @Override
                                    public void onPositiveClick() {
                                       /* Intent i = new Intent(PaymentTransactionActivity.this, TransactionDetailsActivity.class);
                                        i.putExtra("vo", vo1);
                                        i.putExtra("paymentType", paymentType);
                                        PaymentTransactionActivity.this.startActivity(i);
                                        finish();*/

                                        try {
                                            loaderView.setVisibility(View.VISIBLE);
                                            PaymentInitialization initialization = new PaymentInitialization(PaymentTransactionActivity.this);
                                            initialization.initiateTransactionDetails(handlerTransactionDetail, iCCTransactionResponse.getReferenceNumber(), null, paymentType, null, null);
                                        } catch (RuntimeException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onNegativeClick() {

                                    }
                                });
                    } else {
                       /* Intent i = new Intent(PaymentTransactionActivity.this, TransactionDetailsActivity.class);
                        i.putExtra("vo", vo1);
                        i.putExtra("paymentType", paymentType);
                        PaymentTransactionActivity.this.startActivity(i);
                        Toast.makeText(PaymentTransactionActivity.this, "Transaction Status : " + vo1.getResponseCode() + ":" + vo1.getResponseMessage(), Toast.LENGTH_LONG).show();
                        finish();*/
                        try {
                            Toast.makeText(PaymentTransactionActivity.this, "Transaction Status : " + iCCTransactionResponse.getResponseCode() + ":" + iCCTransactionResponse.getResponseMessage(), Toast.LENGTH_LONG).show();
                            loaderView.setVisibility(View.VISIBLE);
                            PaymentInitialization initialization = new PaymentInitialization(PaymentTransactionActivity.this);
                            initialization.initiateTransactionDetails(handlerTransactionDetail, iCCTransactionResponse.getReferenceNumber(), null, paymentType, null, null);
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    alertMessageWithFinshError("Transaction Failed", "Transaction Status : " + iCCTransactionResponse.getResponseCode() + ":" + iCCTransactionResponse.getResponseMessage());
                    /*Toast.makeText(PaymentTransactionActivity.this, "Transaction Status : " + vo.getResponseCode() + ":" + vo.getResponseMessage(), Toast.LENGTH_LONG).show();

                    finish();*/
                }
            } else if (msg.what == TRANSACTION_INITIATED) {
                Toast.makeText(PaymentTransactionActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();

            } else if (msg.what == ERROR_MESSAGE) {

                if (msg.obj instanceof ICCTransactionResponse) {
                    iCCTransactionResponse = (ICCTransactionResponse) msg.obj;
                    alertMessageWithFinshError("Error Alert", iCCTransactionResponse.getResponseCode() + ":" + iCCTransactionResponse.getResponseMessage());

                   /* Toast.makeText(PaymentTransactionActivity.this, vo.getResponseCode() + ":" + vo.getResponseMessage(), Toast.LENGTH_LONG).show();
                    finish();*/

                } else {
                    alertMessageError("Error Alert", (String) msg.obj);
                }
            } else if (msg.what == TRANSACTION_PENDING) {
                if (isActivityOpen) {
                    new CustomAlertDialog(PaymentTransactionActivity.this).WarningWithSingleBtnCallBack("Pending Status", (String) msg.obj, "Ok",
                            false,
                            new CustomAlertDialog.DialogCallBack() {
                                @Override
                                public void onPositiveClick() {
                                    finish();
                                }

                                @Override
                                public void onNegativeClick() {

                                }
                            });
                } else {
                    Toast.makeText(PaymentTransactionActivity.this,
                            (String) msg.obj + "Pending status", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else if (msg.what == DISPLAY_STATUS) {
                Toast.makeText(PaymentTransactionActivity.this,
                        (String) msg.obj, Toast.LENGTH_SHORT).show();
            } else if (msg.what == QPOS_EMV_MULITPLE_APPLICATION) {
                ArrayList<String> applicationList = (ArrayList<String>) msg.obj;
                emvList = (ListView) findViewById(R.id.application_list);
                emvList.setVisibility(View.VISIBLE);
                loaderView.setVisibility(View.GONE);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PaymentTransactionActivity.this, android.R.layout.simple_list_item_1, applicationList);
                emvList.setAdapter(adapter);
                emvList.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        if (initialization != null) {
                            initialization.getQposListener().executeSelectedEMVApplication(position);
                            emvList.setVisibility(View.GONE);
                        }
                    }
                });
            } else if (msg.what == SUCCESS) {
                if (isActivityOpen) {
                    new CustomAlertDialog(PaymentTransactionActivity.this).SuccessfulWithCallBack("Success Status", (String) msg.obj, "Ok",
                            false,
                            new CustomAlertDialog.DialogCallBack() {
                                @Override
                                public void onPositiveClick() {
                                    Intent i = new Intent(PaymentTransactionActivity.this, PaynearActivity.class);
                                    finish();
                                    startActivity(i);
                                }

                                @Override
                                public void onNegativeClick() {

                                }
                            });
                } else {
                    Toast.makeText(PaymentTransactionActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    // alertMessage((String) msg.obj);
                    Intent i = new Intent(PaymentTransactionActivity.this, PaynearActivity.class);
                    finish();
                    PaymentTransactionActivity.this.startActivity(i);
                }
            }

        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_paynear_transaction);

        // mCustomAlertDialog = new CustomAlertDialog(this);
        deviceCommMode = getIntent().getIntExtra(DEVICE_COMMUNICATION_MODE, 0);
        deviceMACAddress = getIntent().getStringExtra(MAC_ADDRESS);
        paymentType = getIntent().getStringExtra(PAYMENT_TYPE);
        deviceName = getIntent().getStringExtra(DEVICE_NAME);
        amount = getIntent().getStringExtra("amount");
        cashBackAmount = getIntent().getStringExtra("cashBackAmount");
        merchantRefNo = getIntent().getStringExtra("referanceno");
        loaderView = findViewById(R.id.loaderView);
        if (merchantRefNo == null || merchantRefNo.equalsIgnoreCase("null") || merchantRefNo.equalsIgnoreCase("")) {
            merchantRefNo = String.valueOf(System.currentTimeMillis());
        }

        if (paymentType.equalsIgnoreCase(EMI)) {
            emivo = (com.pnsol.sdk.vo.request.EMI) getIntent().getSerializableExtra("vo");
            paymentoptioncode = getIntent().getStringExtra("paymentcode");
        }
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bluetooth is enabled or not if not then asks for enable
        if (!checkFlag) {
            if (mBtAdapter != null) {
                mBtAdapter.cancelDiscovery();
                if (!mBtAdapter.isEnabled()) {
                    bluetoothOnFlag = false;
                    Intent enableIntent = new Intent(
                            BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                } else {
                    bluetoothOnFlag = true;
                }
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityOpen = true;
        if (!checkFlag) {
            if (bluetoothOnFlag) {
                initiateConnection();
            }
        }
    }

    private void initiateConnection() {

        isDeviceON(getString(R.string.pls_chk_cardreader_availble));
    }

    public void deviceConnection(String address) {
        checkFlag = true;
        // int device = getIntent().getIntExtra(DEVICE_TYPE, 0);
        if (paymentType.equalsIgnoreCase(EMI)) {
            if (paymentoptioncode.equalsIgnoreCase("emilist")) {
                initialization = new PaymentInitialization(
                        PaymentTransactionActivity.this);
                initialization.initiateTransaction(handler,
                        deviceName,
                        deviceMACAddress,
                        amount,
                        paymentType,
                        PaymentTransactionConstants.CREDIT,
                        null, null,
                        71.000001,
                        17.000001,
                        merchantRefNo,
                        null, emivo, deviceCommMode, merchantRefNo, null, null);
            } else {
                initialization = new PaymentInitialization(
                        PaymentTransactionActivity.this);
                initialization.initiateEMITransactionWithPaymentOptionCode(handler,
                        deviceName,
                        deviceMACAddress,
                        amount,
                        paymentType,
                        PaymentTransactionConstants.CREDIT,
                        null, null,
                        71.000001,
                        17.000001,
                        merchantRefNo,
                        null, paymentoptioncode, deviceCommMode);
            }

        } else if (paymentType.equalsIgnoreCase(DEVICE_STATUS)) {
            try {
                initialization = new PaymentInitialization(
                        PaymentTransactionActivity.this);
                initialization.isQPOSCardReaderAvailable(handler, deviceCommMode, address
                );
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } else if ((paymentType.equalsIgnoreCase(SALE_WITH_CASH_BACK))) {
            try {
                initialization = new PaymentInitialization(
                        PaymentTransactionActivity.this);
                initialization.initiateTransaction(handler, deviceName, address, amount, paymentType, null,
                        null, null, 0.0, 0.0, merchantRefNo, cashBackAmount, deviceCommMode, merchantRefNo, "", "");
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } else if ((paymentType.equalsIgnoreCase(CASH))) {
            try {
                initialization = new PaymentInitialization(
                        PaymentTransactionActivity.this);
                initialization.initiateCashTransaction(handler, amount, paymentType, "POS", null, null, 71.000001, 17.000001, merchantRefNo, "", "");
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } else if ((paymentType.equalsIgnoreCase(VAS_SALE_DEBIT))) {
            try {
                initialization = new PaymentInitialization(
                        PaymentTransactionActivity.this);
                initialization.initiateTransaction(handler, deviceName, address, amount, paymentType, "POS",
                        null, null, 71.000001, 17.000001, merchantRefNo, null, deviceCommMode, merchantRefNo, "", "");
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } else if (paymentType.equalsIgnoreCase(SERIAL_NUMBER)) {
            try {
                initialization = new PaymentInitialization(PaymentTransactionActivity.this);
                initialization.getDeviceeserialNumber(handler, deviceName, deviceCommMode, address);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } else if (paymentType.equalsIgnoreCase(MICRO_ATM)) {
            try {
                initialization = new PaymentInitialization(
                        PaymentTransactionActivity.this);
                initialization.initiateTransaction(handler, deviceName, address, amount, PaymentTransactionConstants.MICRO_ATM, "POS",
                        null, null, 71.000001, 17.000001, merchantRefNo, null, deviceCommMode, merchantRefNo, null, null);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } else {
            try {
                initialization = new PaymentInitialization(
                        PaymentTransactionActivity.this);
                initialization.initiateTransaction(handler, deviceName, address, amount, paymentType, "POS",
                        null, null, 71.000001, 17.000001, merchantRefNo, null, deviceCommMode, merchantRefNo, "", "");
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    protected void onPause() {
        isActivityOpen = false;
        super.onPause();
    }

    public void alertMessage(String message) {
        if (isActivityOpen) {
            new CustomAlertDialog(PaymentTransactionActivity.this).WarningWithSingleBtnCallBack("Alert", message, "Ok", false,
                    new CustomAlertDialog.DialogCallBack() {
                        @Override
                        public void onPositiveClick() {
                            finish();
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });
            /*AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Alert").setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            finish();
                        }
                    }).show();*/
        } else {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    public void alertMessageError(String title, String message) {
        if (isActivityOpen) {
            new CustomAlertDialog(PaymentTransactionActivity.this).ErrorWithSingleBtnCallBack(title, message, "Ok", false,
                    new CustomAlertDialog.DialogCallBack() {
                        @Override
                        public void onPositiveClick() {
                            finish();
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });
            /*AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Alert").setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            finish();
                        }
                    }).show();*/
        } else {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    public void alertMessageWithFinshError(String title, String message) {
        if (isActivityOpen) {
            new CustomAlertDialog(PaymentTransactionActivity.this).ErrorWithSingleBtnCallBack(title, message, "Ok", false,
                    new CustomAlertDialog.DialogCallBack() {
                        @Override
                        public void onPositiveClick() {
                            finish();
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });
            /*AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Alert").setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            finish();
                        }
                    }).show();*/
        } else {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void isDeviceON(String message) {

        if (isActivityOpen) {
            new CustomAlertDialog(PaymentTransactionActivity.this).WarningWithCallBack(message, "Alert", "Ok", false,
                    new CustomAlertDialog.DialogCallBack() {
                        @Override
                        public void onPositiveClick() {
                            deviceConnection(deviceMACAddress);
                        }

                        @Override
                        public void onNegativeClick() {
                            finish();
                        }
                    });
           /* AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);

            }
            builder.setTitle("Alert").setMessage(message)
                    .setCancelable(false)
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            deviceConnection(deviceMACAddress);
                        }
                    }).show();*/
        }
    }


    @Override
    public void onBackPressed() {

        new CustomAlertDialog(PaymentTransactionActivity.this).WarningWithCallBack("Are you sure, you want to go back from this screen?", "Close Screen", "Back", true, new CustomAlertDialog.DialogCallBack() {
            @Override
            public void onPositiveClick() {
                finish();
            }

            @Override
            public void onNegativeClick() {

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
