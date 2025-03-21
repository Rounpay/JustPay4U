package com.solution.app.justpay4u.Fintech.Paynear.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pnsol.sdk.interfaces.PaymentTransactionConstants;
import com.pnsol.sdk.payment.PaymentInitialization;
import com.pnsol.sdk.vo.request.EMI;
import com.pnsol.sdk.vo.response.AcquirerBanks;
import com.solution.app.justpay4u.Fintech.Paynear.Adapter.EmiListAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownModel;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Bhavani.A
 * @Version : V_13
 * @Date : Fed 20, 2017
 * @Copyright : (C) Paynear Solutions Pvt. Ltd.
 * <p>
 * updated by vasanthi.k
 */

public class EmiPaymentActivity extends AppCompatActivity
        implements PaymentTransactionConstants, OnItemClickListener {
    private static String MAC_ADDRESS = "macAddress";
    private static String DEVICE_COMMUNICATION_MODE = "transactionmode";
    private static String DEVICE_TYPE = "devicetype";
    private static String DEVICE_NAME = "devicename";
    View chooseBankView;
    DropDownDialog mDropDownDialog;
    View loaderView, noDataView, noNetworkView;
    private PaymentInitialization initialization;
    private List<DropDownModel> dropdownEmiDetails;
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {


        @SuppressWarnings("unchecked")
        public void handleMessage(android.os.Message msg) {
            if (msg.what == SUCCESS) {
                loaderView.setVisibility(View.GONE);
                noDataView.setVisibility(View.GONE);
                AcquirerBanks acquirerBanks = (AcquirerBanks) msg.obj;
                dropdownEmiDetails = new ArrayList<>();
                for (EMI item : acquirerBanks.getEmiDetails()) {
                    dropdownEmiDetails.add(new DropDownModel(item.getBankName(), item));
                }
            }
            if (msg.what == FAIL) {
                loaderView.setVisibility(View.GONE);
                noDataView.setVisibility(View.VISIBLE);
                new CustomAlertDialog(EmiPaymentActivity.this).ErrorWithSingleBtnCallBack("Failed", msg.obj.toString(), "Ok", false,
                        new CustomAlertDialog.DialogCallBack() {
                            @Override
                            public void onPositiveClick() {
                                finish();
                            }

                            @Override
                            public void onNegativeClick() {

                            }
                        });
                /*Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_LONG).show();*/

            }
        }

        ;
    };
    private ListView emilist;
    @SuppressLint("HandlerLeak")
    private final Handler seectedbankhandler = new Handler() {

        @SuppressWarnings("unchecked")
        public void handleMessage(android.os.Message msg) {
            if (msg.what == SUCCESS) {
                loaderView.setVisibility(View.GONE);
                noDataView.setVisibility(View.GONE);
                AcquirerBanks acquirerBanks = (AcquirerBanks) msg.obj;
                List<EMI> emiDetails = acquirerBanks.getEmiDetails();
                dropdownEmiDetails = new ArrayList<>();
                for (EMI item : emiDetails) {
                    dropdownEmiDetails.add(new DropDownModel(item.getBankName(), item));
                }
                if (emiDetails != null && emiDetails.size() > 0) {
                    EmiListAdapter emiadapter = new EmiListAdapter(EmiPaymentActivity.this, R.layout.adapter_paynear_emi_options, emiDetails);
                    emilist.setAdapter(emiadapter);
                    emilist.setOnItemClickListener(EmiPaymentActivity.this);
                }

            }
            if (msg.what == FAIL) {
                loaderView.setVisibility(View.GONE);
                noDataView.setVisibility(View.VISIBLE);
                new CustomAlertDialog(EmiPaymentActivity.this).ErrorWithSingleBtnCallBack("Failed", msg.obj.toString(), "Ok", false,
                        new CustomAlertDialog.DialogCallBack() {
                            @Override
                            public void onPositiveClick() {
                                finish();
                            }

                            @Override
                            public void onNegativeClick() {

                            }
                        });
                /*Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_LONG).show();*/

            }
        }

        ;
    };
    private String deviceMACAddress;
    private String amount;
    private TextView txtBankMinimum, chooseBankTv;
    private int deviceCommMode;
    private LinearLayout emititle;
    private String deviceName;
    private int selectedEMIIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paynear_emipayment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noDataView = findViewById(R.id.noDataView);
        loaderView = findViewById(R.id.loaderView);
        loaderView.setVisibility(View.VISIBLE);
        noNetworkView = findViewById(R.id.noNetworkView);
        TextView errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Banks not available, try after some time");
        mDropDownDialog = new DropDownDialog(this);
        deviceCommMode = getIntent().getIntExtra(DEVICE_COMMUNICATION_MODE, 0);
        deviceName = getIntent().getStringExtra(DEVICE_NAME);
        emititle = (LinearLayout) findViewById(R.id.emititle);
        deviceMACAddress = getIntent().getStringExtra(MAC_ADDRESS);
        emilist = (ListView) findViewById(R.id.emi_listplans);
        txtBankMinimum = (TextView) findViewById(R.id.txtBankMinimum);
        amount = getIntent().getStringExtra("amount");
        initialization = new PaymentInitialization(getApplicationContext());
        initialization.getEMIBankList(handler, amount);
        chooseBankView = findViewById(R.id.chooseBankView);
        chooseBankTv = findViewById(R.id.chooseBankTv);
        chooseBankView.setOnClickListener(view -> showModeListPoupWindow(view));

    }

    private void showModeListPoupWindow(View anchor) {
        if (dropdownEmiDetails != null && dropdownEmiDetails.size() > 0) {
            mDropDownDialog.showDropDownPopup(anchor, selectedEMIIndex, dropdownEmiDetails, (clickPosition, value, object) -> {
                selectedEMIIndex = clickPosition;
                EMI item = (EMI) object;
                if (item.getAcquirerId() != 0) {
                    if (Double.parseDouble(amount) < item
                            .getMinBankAmount()) {
                        txtBankMinimum.setVisibility(View.VISIBLE);
                        txtBankMinimum.setText("Minimum amount for "
                                + item.getBankName()
                                + " is "
                                + Utility.INSTANCE.formatedAmountWithRupees(item.getMinBankAmount() + ""));
                        emititle.setVisibility(View.GONE);
                        emilist.setVisibility(View.GONE);

                    } else {
                        txtBankMinimum.setVisibility(View.GONE);
                        emilist.setVisibility(View.VISIBLE);
                        emititle.setVisibility(View.VISIBLE);
                        loaderView.setVisibility(View.VISIBLE);
                        initialization.getSelectedBankEMITenureList(seectedbankhandler, amount, item);

                    }
                } else {
                    emilist.setVisibility(View.GONE);
                    emititle.setVisibility(View.GONE);
                    txtBankMinimum.setVisibility(View.GONE);
                }
            });

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
        EMI selectedEMI = (EMI) parent.getItemAtPosition(position);

        int device = getIntent().getIntExtra(DEVICE_TYPE, 0);
        Intent i = new Intent(EmiPaymentActivity.this, PaymentTransactionActivity.class);
        i.putExtra(MAC_ADDRESS, deviceMACAddress);
        i.putExtra(DEVICE_COMMUNICATION_MODE, deviceCommMode);
        i.putExtra("vo", selectedEMI);
        i.putExtra(PAYMENT_TYPE, EMI);
        i.putExtra("paymentcode", "emilist");
        i.putExtra("amount", amount);
        i.putExtra(DEVICE_TYPE, device);
        i.putExtra(DEVICE_NAME, deviceName);
        i.putExtra("referanceno", getIntent().getStringExtra("referanceno"));

        startActivity(i);
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
