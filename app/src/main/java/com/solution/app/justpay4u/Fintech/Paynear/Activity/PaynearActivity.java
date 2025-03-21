package com.solution.app.justpay4u.Fintech.Paynear.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.pnsol.sdk.auth.AccountValidator;
import com.pnsol.sdk.interfaces.DeviceCommunicationMode;
import com.pnsol.sdk.interfaces.DeviceType;
import com.pnsol.sdk.interfaces.PaymentTransactionConstants;
import com.pnsol.sdk.payment.PaymentInitialization;
import com.pnsol.sdk.payment.PaymentModeThread;
import com.pnsol.sdk.vo.response.PaymentTypes;
import com.solution.app.justpay4u.Fintech.Activities.PermissionActivity;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownModel;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class PaynearActivity extends AppCompatActivity implements PaymentTransactionConstants, DeviceCommunicationMode {
    private static final String DEVICE_SERIAL = "deviceSerialNumber";
    private static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 123;
    private static final String ACTION_USB_PERMISSION = "com.pnsol.sdkdemo.USB_PERMISSION";
    private static final int REQUEST_ENABLE_BT = 1;
    private static String DEVICE_COMMUNICATION_MODE = "transactionmode";
    private static String DEVICE_TYPE = "devicetype";
    private static String MAC_ADDRESS = "macAddress";
    private static String DEVICE_NAME = "devicename";
    //private Spinner  spn_payment_mode;
    View choosePairedProviderView, modeChooserView;
    TextView choosePairedProviderTv, chooseModeTv;
    View chooseDeviceView, chooseModeView;
    String ipPortStr;
    View viewUSB, viewBluetooth;
    ImageView iconBluetooth, iconUsb;
    TextView textBluetooth, textUsb;
    ArrayList<DropDownModel> dropdownListBluetoothDevice = new ArrayList<>();
    DropDownDialog mDropDownDialog;
    boolean isUsb;
    private int INTENT_NEXT_SCREEN = 528;
    private BluetoothAdapter mBluetoothAdapter;
    private int devicePosition, device_Type;
    private String deviceMACAddress, deviceName, selectedUSBDevice, ipandport, deviceType;
    private Button trxnstatus, cashBtn, btn_vasSale, getdeviceserialnumber;
    private Set<BluetoothDevice> pairedDevices;
    private EditText amount, referanceno, cashbacckamount;
    /* private RadioGroup radioCommGroup;
     private RadioButton rdoBlueTooth, rdoUSB;*/
    private int deviceCommMode;
    private UsbManager mUsbManager;
    private PendingIntent mPermissionIntent;
    //mpaysdk 2.0
    private PaymentTypes paymentModeResponse;
    private ArrayList<String> dropdownListMode;
    private RadioGroup radiodevice, radioComm;
    private LinearLayout llRadio;
    private boolean isBlutoothEnableOpen;
    private ArrayList<String> dropdownListUsb = new ArrayList<>();
    private int selectedBluetoothIndex = -1;
    private int selectedUSBDeviceIndex = -1;
    private int selectedModeIndex = -1;
    private CustomLoader loader;
    //mpaySDk 2.0
    private final Handler paymentHandler = new Handler() {

        public void handleMessage(Message msg) {

            loader.dismiss();
            if (msg.what == SUCCESS) {

                paymentModeResponse = (PaymentTypes) msg.obj;

                dropdownListMode = new ArrayList<String>();

                for (String s : paymentModeResponse.getPaymentTypes()) {
                    if (!s.toLowerCase().contains("void") && !s.toLowerCase().contains("emi")) {
                        dropdownListMode.add(s);
                    }
                }
                /*  al.add("Sale with cash back");*/
               /* dropdownListMode.add("BalanceEnquiry");
                dropdownListMode.add("Sale");*/


            } else if (msg.what == FAIL) {
                new CustomAlertDialog(PaynearActivity.this).ErrorWithSingleBtnCallBack("Failed", getString(R.string.no_payment_modes),
                        "Ok", false,
                        new CustomAlertDialog.DialogCallBack() {
                            @Override
                            public void onPositiveClick() {
                                /* finish();*/
                            }

                            @Override
                            public void onNegativeClick() {

                            }
                        });
                /*Toast.makeText(PaynearActivity.this, getString(R.string.no_payment_modes), Toast.LENGTH_LONG).show();*/
            } else if (msg.what == ERROR_MESSAGE) {
                new CustomAlertDialog(PaynearActivity.this).ErrorWithSingleBtnCallBack("Error", getString(R.string.smething_went_wrong),
                        "Ok", false,
                        new CustomAlertDialog.DialogCallBack() {
                            @Override
                            public void onPositiveClick() {
                                /* finish();*/
                            }

                            @Override
                            public void onNegativeClick() {

                            }
                        });
                /*Toast.makeText(PaynearActivity.this, getString(R.string.smething_went_wrong), Toast.LENGTH_LONG).show();*/
            }

        }
    };
    private AppPreferences mAppPreferences;
    BroadcastReceiver mUSBAttachDeattachReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (device != null) {
                   /* rdoUSB.setChecked(false);
                    rdoBlueTooth.setChecked(true);*/
                    isUsb = false;
                    mAppPreferences.set(ApplicationConstant.INSTANCE.Paynear_USB, false);
                    setCommDesign();
                    if (!mBluetoothAdapter.isEnabled() && !isBlutoothEnableOpen) {
                        isBlutoothEnableOpen = true;
                        Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(discoveryIntent, REQUEST_ENABLE_BT);
                    } else {
                        getBlueToothSpinnerList();
                    }
                }
            } else if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {

               /* rdoUSB.setChecked(true);
                rdoBlueTooth.setChecked(false);*/
                isUsb = true;
                mAppPreferences.set(ApplicationConstant.INSTANCE.Paynear_USB, true);
                setCommDesign();
                getUSBPermission();
            }
        }
    };
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            /* call method to set up device communication*/
                            deviceName = device.getSerialNumber();
                            getUSBSpinnerList();
                        }
                    } else {
                        /*rdoUSB.setChecked(false);
                        rdoBlueTooth.setChecked(true);*/

                        isUsb = false;
                        mAppPreferences.set(ApplicationConstant.INSTANCE.Paynear_USB, false);
                        setCommDesign();
                        getBlueToothSpinnerList();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {


            setContentView(R.layout.activity_paynear);

            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            isUsb = mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.Paynear_USB);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            mDropDownDialog = new DropDownDialog(this);
            cashBtn = (Button) findViewById(R.id.cashBtn);
            btn_vasSale = (Button) findViewById(R.id.btn_vasSale);
            amount = (EditText) findViewById(R.id.amount);
            referanceno = (EditText) findViewById(R.id.referanceno);
        /*radioCommGroup = (RadioGroup) findViewById(R.id.radioComm);
        rdoBlueTooth = (RadioButton) findViewById(R.id.radioBluetooth);
         rdoUSB = (RadioButton) findViewById(R.id.radioUSB);*/
            cashbacckamount = (EditText) findViewById(R.id.cashbacckamount);

            trxnstatus = (Button) findViewById(R.id.trxn_status);
            choosePairedProviderView = findViewById(R.id.choosePairedProviderView);
            modeChooserView = findViewById(R.id.modeChooserView);
            choosePairedProviderTv = findViewById(R.id.choosePairedProvider);
            mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
            getdeviceserialnumber = (Button) findViewById(R.id.getdeviceserialnumber);
            radiodevice = (RadioGroup) findViewById(R.id.radiodevice);
            llRadio = findViewById(R.id.llRadio);
            viewUSB = findViewById(R.id.viewUSB);
            viewBluetooth = findViewById(R.id.viewBluetooth);
            iconBluetooth = findViewById(R.id.iconBluetooth);
            iconUsb = findViewById(R.id.iconUsb);
            textBluetooth = findViewById(R.id.textBluetooth);
            textUsb = findViewById(R.id.textUsb);
            chooseDeviceView = findViewById(R.id.chooseDeviceView);
            chooseModeView = findViewById(R.id.chooseModeView);
            chooseModeTv = findViewById(R.id.chooseMode);
            setCommDesign();
            initUSBReceiver();
            modeChooserView.setOnClickListener(view -> showModeListPoupWindow(view));

            choosePairedProviderView.setOnClickListener(view -> {
                if (isUsb) {
                    showUsbListPoupWindow(view);
                } else {
                    showBlueToothListPoupWindow(view);
                }
            });
            /*check bluetooth or USB*/
            viewUSB.setOnClickListener(v -> {
                chooseModeTv.setText("");
                choosePairedProviderTv.setText("");
                selectedModeIndex = -1;
                selectedBluetoothIndex = -1;
                selectedUSBDeviceIndex = -1;
                isUsb = true;
                mAppPreferences.set(ApplicationConstant.INSTANCE.Paynear_USB, true);
                setCommDesign();
                getUSBPermission();
            });

            viewBluetooth.setOnClickListener(v -> {
                chooseModeTv.setText("");
                choosePairedProviderTv.setText("");
                selectedModeIndex = -1;
                selectedBluetoothIndex = -1;
                selectedUSBDeviceIndex = -1;
                isUsb = false;
                mAppPreferences.set(ApplicationConstant.INSTANCE.Paynear_USB, false);
                setCommDesign();
                onStart();
                getBlueToothSpinnerList();
            });
       /* radioCommGroup.setOnCheckedChangeListener((group, checkedId) -> {
            chooseModeTv.setText("");
            choosePairedProviderTv.setText("");
            selectedModeIndex = -1;
            selectedBluetoothIndex = -1;
            selectedUSBDeviceIndex = -1;
            if (checkedId == R.id.radioBluetooth) {
                if (rdoBlueTooth.isChecked()) {
                    onStart();
                    getBlueToothSpinnerList();
                }
            } else if (checkedId == R.id.radioUSB) {
                if (rdoUSB.isChecked()) {
                    getUSBPermission();
                }
            }

        });*/
            radiodevice.setOnCheckedChangeListener((group, checkedId) -> {
                chooseModeTv.setText("");
                choosePairedProviderTv.setText("");
                selectedModeIndex = -1;
                selectedBluetoothIndex = -1;
                selectedUSBDeviceIndex = -1;
                deviceName = "";
                deviceType = "";
                if (checkedId == R.id.n910) {
                    llRadio.setVisibility(View.GONE);
                    chooseDeviceView.setVisibility(View.GONE);
                    deviceType = DeviceType.N910;
                    deviceName = DeviceType.N910;
                    PaymentInitialization.setIPandPort(getApplicationContext(), ipPortStr);
                    initiatePaymentMode(paymentHandler);
                } else if (checkedId == R.id.n3) {
                    llRadio.setVisibility(View.GONE);
                    chooseDeviceView.setVisibility(View.GONE);
                    deviceType = DeviceType.N3;
                    deviceName = DeviceType.N3;
                    PaymentInitialization.setIPandPort(getApplicationContext(), ipPortStr);
                    initiatePaymentMode(paymentHandler);
                } else if (checkedId == R.id.n6) {
                    llRadio.setVisibility(View.GONE);
                    chooseDeviceView.setVisibility(View.GONE);
                /*deviceType = DeviceType.N6;
                deviceName = DeviceType.N6;*/
                    PaymentInitialization.setIPandPort(getApplicationContext(), ipPortStr);
                    initiatePaymentMode(paymentHandler);
                } else if (checkedId == R.id.qpos) {
                    llRadio.setVisibility(View.VISIBLE);
                    deviceType = DeviceType.QPOS;
                    deviceName = DeviceType.QPOS;
                    chooseDeviceView.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.me30s) {
                    llRadio.setVisibility(View.VISIBLE);
                    deviceType = DeviceType.ME30S;
                    deviceName = DeviceType.ME30S;
                    chooseDeviceView.setVisibility(View.VISIBLE);

                }
            });


            /*on click button-cash transaction*/
            cashBtn.setOnClickListener(v -> {
                if (amount.getText().toString().length() == 0) {
                    amount.setError(getString(R.string.pls_enter_amount));
                    amount.requestFocus();
                } else if (referanceno.getText().toString().length() == 0) {
                    referanceno.setError(getString(R.string.pls_enter_referenceno));
                    referanceno.requestFocus();
                } else {
                    Intent intent = new Intent(PaynearActivity.this, PaymentTransactionActivity.class);

                    intent.putExtra(PAYMENT_TYPE, CASH);
                    intent.putExtra("referanceno", (referanceno.getText().toString()));
                    intent.putExtra("amount", Utility.INSTANCE.getAmountFormat(amount.getText().toString()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(intent, INTENT_NEXT_SCREEN);
                }

            });

            findViewById(R.id.btn_cashPos).setOnClickListener(v -> {
                if (amount.getText().toString().length() == 0) {
                    amount.setError(getString(R.string.pls_enter_amount));
                    amount.requestFocus();
                } else if (referanceno.getText().toString().length() == 0) {
                    referanceno.setError(getString(R.string.pls_enter_referenceno));
                    referanceno.requestFocus();
                } else if (chooseDeviceView.getVisibility() == View.VISIBLE && choosePairedProviderTv.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.pls_select_device), Toast.LENGTH_SHORT).show();
                } else {
                    selectMode(CASH_AT_POS);

                }

            });
            findViewById(R.id.btn_sale).setOnClickListener(v -> {
                if (amount.getText().toString().length() == 0) {
                    amount.setError(getString(R.string.pls_enter_amount));
                    amount.requestFocus();
                } else if (referanceno.getText().toString().length() == 0) {
                    referanceno.setError(getString(R.string.pls_enter_referenceno));
                    referanceno.requestFocus();
                } else if (chooseDeviceView.getVisibility() == View.VISIBLE && choosePairedProviderTv.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.pls_select_device), Toast.LENGTH_SHORT).show();
                } else {
                    selectMode(SALE);

                }

            });

            getdeviceserialnumber.setOnClickListener(v -> {
                if (chooseDeviceView.getVisibility() == View.VISIBLE && choosePairedProviderTv.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.pls_select_device), Toast.LENGTH_SHORT).show();
                } else {
                    // if (Utils.checkAmount(amount.getText().toString())) {
                    Intent i = new Intent(PaynearActivity.this, PaymentTransactionActivity.class);
                    if (deviceCommMode == BLUETOOTHCOMMUNICATION) {
                        i.putExtra(MAC_ADDRESS, deviceMACAddress);
                    } else {
                        i.putExtra(MAC_ADDRESS, selectedUSBDevice);
                    }
                    i.putExtra(DEVICE_NAME, deviceName);
                    i.putExtra(PAYMENT_TYPE, SERIAL_NUMBER);
                    i.putExtra(DEVICE_COMMUNICATION_MODE, deviceCommMode);
                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(i, INTENT_NEXT_SCREEN);
                }
            });

            /*get transaction status */
            trxnstatus.setOnClickListener(v -> {
                Intent i = new Intent(PaynearActivity.this, TransactionStatusActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(i, INTENT_NEXT_SCREEN);
            });

            /*on click vas with Debit transaction*/
            btn_vasSale.setOnClickListener(v -> {


                if (amount.getText().toString().length() == 0) {
                    amount.setError(getString(R.string.pls_enter_amount));
                    amount.requestFocus();
                } else if (chooseDeviceView.getVisibility() == View.VISIBLE && choosePairedProviderTv.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.pls_select_device), Toast.LENGTH_SHORT).show();
                } /*else if (chooseModeView.getVisibility() == View.VISIBLE && chooseModeTv.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), getString(R.string.pls_select_mode), Toast.LENGTH_SHORT).show();
            } */ else {
                    Intent intent = new Intent(PaynearActivity.this, PaymentTransactionActivity.class);
                    if (deviceCommMode == BLUETOOTHCOMMUNICATION)
                        intent.putExtra(MAC_ADDRESS, deviceMACAddress);
                    else
                        intent.putExtra(MAC_ADDRESS, selectedUSBDevice);
                    intent.putExtra(DEVICE_NAME, deviceName);
                    intent.putExtra(DEVICE_COMMUNICATION_MODE, deviceCommMode);
                    intent.putExtra(PAYMENT_TYPE, VAS_SALE_DEBIT);
                    intent.putExtra("referanceno", (referanceno.getText().toString()));
                    intent.putExtra("amount", Utility.INSTANCE.getAmountFormat(amount.getText().toString()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(intent, INTENT_NEXT_SCREEN);

                }
            });
        });
    }

    void setCommDesign() {
        if (isUsb) {
            viewUSB.setBackgroundResource(R.drawable.rounded_primary_border);
            textUsb.setTextColor(getResources().getColor(R.color.colorPrimary));
            ImageViewCompat.setImageTintList(iconUsb, AppCompatResources.getColorStateList(this, R.color.colorPrimary));

            viewBluetooth.setBackgroundResource(R.drawable.rounded_grey_border_fill);
            textBluetooth.setTextColor(getResources().getColor(R.color.colorAccent));
            ImageViewCompat.setImageTintList(iconBluetooth, AppCompatResources.getColorStateList(this, R.color.colorAccent));

        } else {
            viewBluetooth.setBackgroundResource(R.drawable.rounded_primary_border);
            textBluetooth.setTextColor(getResources().getColor(R.color.colorPrimary));
            ImageViewCompat.setImageTintList(iconBluetooth, AppCompatResources.getColorStateList(this, R.color.colorPrimary));

            viewUSB.setBackgroundResource(R.drawable.rounded_grey_border_fill);
            textUsb.setTextColor(getResources().getColor(R.color.colorAccent));
            ImageViewCompat.setImageTintList(iconUsb, AppCompatResources.getColorStateList(this, R.color.colorAccent));
        }
    }

    void selectMode(String modeName) {
        if (deviceType == DeviceType.N910 || deviceType == DeviceType.N3 /*|| deviceType == DeviceType.N6*/) {
            if (amount.getText().toString().length() == 0) {
                amount.setError(getString(R.string.pls_enter_amount));
                amount.requestFocus();
            } /*else if (pairedSpinnerSelectProvider.getSelectedItemPosition() == 0) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.pls_select_device), Toast.LENGTH_SHORT).show();
                            } */ else {
                if (modeName.equalsIgnoreCase("emi")) {


                    Intent intent = new Intent(PaynearActivity.this, EmiPaymentActivity.class);
                    intent.putExtra(DEVICE_NAME, deviceName);
                    intent.putExtra(DEVICE_TYPE, device_Type);
                    /*   intent.putExtra(DEVICE_COMMUNICATION_MODE, deviceCommMode);*/
                    intent.putExtra("amount", Utility.INSTANCE.getAmountFormawitdot(amount.getText().toString()));
                    intent.putExtra("referanceno", (referanceno.getText().toString()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(intent, INTENT_NEXT_SCREEN);

                } else {

                    Intent intent = new Intent(PaynearActivity.this, PaymentTransactionActivity.class);
                    if (deviceCommMode == BLUETOOTHCOMMUNICATION)
                        intent.putExtra(MAC_ADDRESS, deviceMACAddress);
                    else
                        intent.putExtra(MAC_ADDRESS, selectedUSBDevice);
                    intent.putExtra(DEVICE_NAME, deviceName);
                    intent.putExtra(DEVICE_COMMUNICATION_MODE, deviceCommMode);
                    intent.putExtra(PAYMENT_TYPE, modeName);
                    intent.putExtra("referanceno", (referanceno.getText().toString()));
                    intent.putExtra("amount", Utility.INSTANCE.getAmountFormawitdot(amount.getText().toString()));
                    intent.putExtra("cashBackAmoumt", Utility.INSTANCE.getAmountFormat(cashbacckamount.getText().toString()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(intent, INTENT_NEXT_SCREEN);
                }
            }

        } else {

            if (amount.getText().toString().length() == 0) {
                amount.setError(getString(R.string.pls_enter_amount));
                amount.requestFocus();
            } else if (chooseDeviceView.getVisibility() == View.VISIBLE && choosePairedProviderTv.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), getString(R.string.pls_select_device), Toast.LENGTH_SHORT).show();
            } else {
                if (modeName.equalsIgnoreCase("emi")) {
                    Intent intent = new Intent(PaynearActivity.this, EmiPaymentActivity.class);
                    if (deviceCommMode == BLUETOOTHCOMMUNICATION) {
                        intent.putExtra(MAC_ADDRESS, deviceMACAddress);
                        intent.putExtra(DEVICE_NAME, deviceName);
                    } else {
                        intent.putExtra(MAC_ADDRESS, selectedUSBDevice);
                    }
                    intent.putExtra(DEVICE_TYPE, device_Type);
                    intent.putExtra(DEVICE_COMMUNICATION_MODE, deviceCommMode);
                    intent.putExtra("amount", Utility.INSTANCE.getAmountFormat(amount.getText().toString()));
                    intent.putExtra("referanceno", (referanceno.getText().toString()));
                    intent.putExtra("cashBackAmoumt", Utility.INSTANCE.getAmountFormat(cashbacckamount.getText().toString()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(intent, INTENT_NEXT_SCREEN);

                } else {

                    Intent intent = new Intent(PaynearActivity.this, PaymentTransactionActivity.class);
                    if (deviceCommMode == BLUETOOTHCOMMUNICATION)
                        intent.putExtra(MAC_ADDRESS, deviceMACAddress);
                    else
                        intent.putExtra(MAC_ADDRESS, selectedUSBDevice);
                    intent.putExtra(DEVICE_NAME, deviceName);
                    intent.putExtra(DEVICE_COMMUNICATION_MODE, deviceCommMode);
                    intent.putExtra(PAYMENT_TYPE, modeName);
                    intent.putExtra("referanceno", (referanceno.getText().toString()));
                    intent.putExtra("amount", Utility.INSTANCE.getAmountFormawitdot(amount.getText().toString()));
                    intent.putExtra("cashBackAmount", Utility.INSTANCE.getAmountFormat(cashbacckamount.getText().toString()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(intent, INTENT_NEXT_SCREEN);
                }
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // take an instance of BluetoothAdapter`
        if (ContextCompat.checkSelfPermission(PaynearActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(this, PermissionActivity.class));
        }
        if (!isBlutoothEnableOpen) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                new CustomAlertDialog(this).ErrorWithSingleBtnCallBack("Error", getString(R.string.bluetooth_not_supported_device), "Ok", false,
                        new CustomAlertDialog.DialogCallBack() {
                            @Override
                            public void onPositiveClick() {
                                finish();
                            }

                            @Override
                            public void onNegativeClick() {

                            }
                        });
                /* Toast.makeText(this, getString(R.string.bluetooth_not_supported_device), Toast.LENGTH_LONG).show();*/
            } else {
                if (!mBluetoothAdapter.isEnabled()) {
                    isBlutoothEnableOpen = true;
                    Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(discoveryIntent, REQUEST_ENABLE_BT);
                }
            }
        }
    }

    @Override
    protected void onResume() {

        ipandport = PaymentInitialization.getIPandPort(getApplicationContext());
        if (ipandport != null && !ipandport.isEmpty())
            ipPortStr = "" + ipandport;

        AccountValidator validator = new AccountValidator(getApplicationContext());
        if (!validator.isAccountActivated()) {
            /*   boolean checkPermission=checkPermission();*/
            Intent i = new Intent(PaynearActivity.this, PaynearActivationActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_NEXT_SCREEN);
        } else {
            if (!isUsb/*radioCommGroup.getCheckedRadioButtonId() == R.id.radioBluetooth*/) {
                getBlueToothSpinnerList();
            }

        }

        super.onResume();
    }

    private void initUSBReceiver() {
        IntentFilter filter = new IntentFilter(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        registerReceiver(mUSBAttachDeattachReceiver, filter);
        IntentFilter filter1 = new IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(mUSBAttachDeattachReceiver, filter1);
        mPermissionIntent = PendingIntent.getBroadcast(PaynearActivity.this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter2 = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter2);

    }

    private void getUSBSpinnerList() {
        PaymentInitialization getusblist = new PaymentInitialization(this);
        ArrayList<String> deviceList = getusblist.getUSBDeviceList();

        if (deviceList != null && deviceList.size() > 0) {
            dropdownListUsb = new ArrayList<>();
            dropdownListUsb.addAll(deviceList);
        } else {
            Toast.makeText(PaynearActivity.this, getString(R.string.no_device_found), Toast.LENGTH_SHORT).show();
        }
    }

    private void getBlueToothSpinnerList() {
        dropdownListBluetoothDevice = new ArrayList<>();
        pairedDevices = mBluetoothAdapter.getBondedDevices();

        for (BluetoothDevice device : pairedDevices) {
            deviceName = device.getName();
            if (deviceName.startsWith(getString(R.string.paynear)) || deviceName.startsWith(getString(R.string.mpos)) || deviceName.startsWith(getString(R.string.c_me))) {
                dropdownListBluetoothDevice.add(new DropDownModel(device.getName(), device));
            }

        }

    }

    private void showBlueToothListPoupWindow(View anchor) {


        if (dropdownListBluetoothDevice != null && dropdownListBluetoothDevice.size() > 0) {
            mDropDownDialog.showDropDownPopup(anchor, selectedBluetoothIndex, dropdownListBluetoothDevice, (clickPosition, value, object) -> {
                selectedBluetoothIndex = clickPosition;
                BluetoothDevice mBluetoothDevice = (BluetoothDevice) object;

                if (!isUsb/*radioCommGroup.getCheckedRadioButtonId() == R.id.radioBluetooth*/) {
                    choosePairedProviderTv.setText(mBluetoothDevice.getName() + "");
                    deviceMACAddress = mBluetoothDevice.getAddress() + "";
                    deviceName = mBluetoothDevice.getName() + "";
                    deviceCommMode = BLUETOOTHCOMMUNICATION;
                } else {
                    choosePairedProviderTv.setText(mBluetoothDevice.getName() + "");
                    selectedUSBDevice = mBluetoothDevice.getName() + "";
                    deviceName = mBluetoothDevice.getName() + "";
                    deviceCommMode = USBCOMMUNICATION;
                    Toast.makeText(PaynearActivity.this, "" + selectedUSBDevice, Toast.LENGTH_LONG).show();

                }

                //mpaysdk 2.0

                PaymentInitialization.setIPandPort(getApplicationContext(), ipPortStr);
                initiatePaymentMode(paymentHandler);
            });
        } else {
            getBlueToothSpinnerList();
        }
    }

    private void showUsbListPoupWindow(View anchor) {
        if (dropdownListUsb != null && dropdownListUsb.size() > 0) {
            mDropDownDialog.showDropDownPopup(anchor, selectedUSBDeviceIndex, dropdownListUsb, (clickPosition, item, object) -> {
                selectedUSBDeviceIndex = clickPosition;
                if (!isUsb/*radioCommGroup.getCheckedRadioButtonId() == R.id.radioBluetooth*/) {
                    choosePairedProviderTv.setText(item + "");
                    deviceMACAddress = "";
                    deviceName = item + "";
                    deviceCommMode = BLUETOOTHCOMMUNICATION;
                } else {
                    choosePairedProviderTv.setText(item + "");
                    selectedUSBDevice = item;
                    deviceName = item + "";
                    deviceCommMode = USBCOMMUNICATION;
                    Toast.makeText(PaynearActivity.this, "" + selectedUSBDevice, Toast.LENGTH_LONG).show();

                }

                //mpaysdk 2.0

                PaymentInitialization.setIPandPort(getApplicationContext(), ipPortStr);
                initiatePaymentMode(paymentHandler);
            });
        } else {
            getUSBSpinnerList();
        }
    }

    private void showModeListPoupWindow(View anchor) {
        if (chooseDeviceView.getVisibility() == View.VISIBLE && choosePairedProviderTv.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.pls_select_device), Toast.LENGTH_SHORT).show();
        } else {
            if (dropdownListMode != null && dropdownListMode.size() > 0) {
                mDropDownDialog.showDropDownPopup(anchor, selectedModeIndex, dropdownListMode, (clickPosition, item, object) -> {
                    selectedModeIndex = clickPosition;
                    chooseModeTv.setText(item + "");
                    selectMode(item);
                });
            } else {
                if (paymentModeResponse != null && paymentModeResponse.getPaymentTypes() != null) {
                    dropdownListMode = new ArrayList<String>();
                    for (String s : paymentModeResponse.getPaymentTypes()) {
                        dropdownListMode.add(s);
                    }
                }
            }
        }
    }

    private void getUSBPermission() {
        HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        if (deviceList.isEmpty()) {
            Toast.makeText(this, getString(R.string.usb_otg_not_detected), Toast.LENGTH_LONG).show();
            getUSBSpinnerList();
        } else {
            while (deviceIterator.hasNext()) {
                UsbDevice device = deviceIterator.next();
                mUsbManager.requestPermission(device, mPermissionIntent);

            }
        }
    }

    protected void onPause() {
        super.onPause();
        PaymentInitialization.setIPandPort(getApplicationContext(), ipPortStr);
    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mUSBAttachDeattachReceiver);
        unregisterReceiver(mUsbReceiver);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            /*
             * Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
             * Log.d("orientation", "landscape");
             */
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            /*
             * Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
             * Log.d("orientation", "portrait");
             */
        }
    }

    //mpaySDk 2.0
    /* get  payment modes*/
    public void initiatePaymentMode(Handler paymentHandler) {
        loader.show();
        PaymentModeThread paymentThread = new PaymentModeThread(PaynearActivity.this, paymentHandler);
        Thread thread = new Thread(paymentThread);
        thread.start();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            isBlutoothEnableOpen = false;
        }

        if (requestCode == INTENT_NEXT_SCREEN) {
            amount.setText("");
            cashbacckamount.setText("");
            referanceno.setText("");
            chooseModeTv.setText("");
            selectedModeIndex = -1;
            selectedBluetoothIndex = -1;
            selectedUSBDeviceIndex = -1;
            choosePairedProviderTv.setText("");
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
