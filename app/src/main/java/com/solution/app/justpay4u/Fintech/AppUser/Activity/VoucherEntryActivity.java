package com.solution.app.justpay4u.Fintech.AppUser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.solution.app.justpay4u.Api.Fintech.Object.AscReport;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;

public class VoucherEntryActivity extends AppCompatActivity {
    String bankId;
    String  userListName;

    String mobileNumber = " ";
    String bankName;
    int userListid;
    boolean isBank;
    private AppCompatTextView cashCollectionTv;
    private AppCompatTextView bankCollectionTv;
    private LinearLayout bankView;
    private RelativeLayout bankSelect_ll, userSelect_ll;
    private TextView bankTv, userNameTv;
    private AppCompatEditText amountEt;
    private AppCompatEditText remarksEt;
    private AppCompatTextView bankUtr_tv;
    private AppCompatEditText bankUtrEt;
    private AppCompatTextView cancelBtn;
    private AppCompatTextView fundCollectBtn;
    private AppCompatImageView closeIv;
    private int INTENT_SELECT_BANK = 4343;
    private int INTENT_SELECT_USER = 5656;
    private CustomLoader loader;
    private LoginResponse mLoginDataResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private ArrayList<AscReport> ascReportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler(Looper.getMainLooper()).post(() -> {


            setContentView(R.layout.activity_voucher_entry);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);


            ascReportList =  getIntent().getParcelableArrayListExtra("Data");
      /*  Bundle bundle1 = getIntent().getExtras();
        userListName = bundle1.getString("outletName");
        userListid = bundle1.getInt("id");
        mobileNumber = bundle1.getString("mobile");*/


            cashCollectionTv = (AppCompatTextView) findViewById(R.id.cashCollectionTv);
            bankCollectionTv = (AppCompatTextView) findViewById(R.id.bankCollectionTv);
            bankView = (LinearLayout) findViewById(R.id.bankView);
            userSelect_ll = (RelativeLayout) findViewById(R.id.userSelect_ll);
            bankSelect_ll = (RelativeLayout) findViewById(R.id.bankSelect_ll);
            userNameTv = (TextView) findViewById(R.id.userNameTv);
            bankTv = (TextView) findViewById(R.id.bankTv);
            amountEt = (AppCompatEditText) findViewById(R.id.amountEt);
            remarksEt = (AppCompatEditText) findViewById(R.id.remarksEt);
            bankUtr_tv = (AppCompatTextView) findViewById(R.id.bankUtr_tv);
            bankUtrEt = (AppCompatEditText) findViewById(R.id.bankUtrEt);
            cancelBtn = (AppCompatTextView) findViewById(R.id.cancelBtn);
            fundCollectBtn = (AppCompatTextView) findViewById(R.id.fundCollectBtn);
            closeIv = (AppCompatImageView) findViewById(R.id.closeIv);

            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            cashCollectionTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bankUtr_tv.setVisibility(View.GONE);
                    bankUtrEt.setVisibility(View.GONE);
                    bankView.setVisibility(View.GONE);
                    cashCollectionTv.setBackgroundResource(R.drawable.rounded_light_green);
                    bankCollectionTv.setBackgroundResource(0);
                    bankCollectionTv.setTextColor(getResources().getColor(R.color.lightDarkGreen));
                    cashCollectionTv.setTextColor(getResources().getColor(R.color.white));


                }
            });

            bankCollectionTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bankUtr_tv.setVisibility(View.VISIBLE);
                    bankUtrEt.setVisibility(View.VISIBLE);
                    bankView.setVisibility(View.VISIBLE);
                    bankCollectionTv.setBackgroundResource(R.drawable.rounded_light_green);
                    cashCollectionTv.setBackgroundResource(0);
                    cashCollectionTv.setTextColor(getResources().getColor(R.color.lightDarkGreen));
                    bankCollectionTv.setTextColor(getResources().getColor(R.color.white));
                }
            });


            bankSelect_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent bankIntent = new Intent(VoucherEntryActivity.this, FosCollectionBankListActivity.class);
                    bankIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(bankIntent, INTENT_SELECT_BANK);
                }
            });

            userSelect_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent bankIntent = new Intent(VoucherEntryActivity.this, VoucherEntryUserListActivity.class);
                    bankIntent.putParcelableArrayListExtra("Data", ascReportList);
                    bankIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(bankIntent, INTENT_SELECT_USER);
                }
            });


            fundCollectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (bankView.getVisibility() == View.VISIBLE && bankTv.getText().toString().isEmpty()) {
                        bankTv.setError("This Field Is Empty");
                        bankTv.requestFocus();
                    } else if (bankView.getVisibility() == View.VISIBLE && bankUtrEt.getText().toString().isEmpty()) {
                        bankUtrEt.setError("This Field Is Empty");
                        bankUtrEt.requestFocus();
                    } else if (amountEt.getText().toString().isEmpty()) {
                        amountEt.setError("This Field Is Empty");
                        amountEt.requestFocus();
                    } else if (remarksEt.getText().toString().isEmpty()) {
                        remarksEt.setError("Fill valid Remark");
                        remarksEt.requestFocus();
                    } else if (!remarksEt.getText().toString().matches("[a-zA-Z.? ]*")) {
                        remarksEt.setError("Fill valid Remark");
                        remarksEt.requestFocus();
                    } else {

                        ApiFintechUtilMethods.INSTANCE.ASPayCollect(VoucherEntryActivity.this, userListid,
                                remarksEt.getText().toString(), amountEt.getText().toString(),
                                userListName, loader, isBank ? "Bank" : "Cash", bankUtrEt.getText().toString(), bankTv.getText().toString(),
                                mLoginDataResponse, deviceId, deviceSerialNum,
                                new ApiFintechUtilMethods.ApiCallBack() {
                                    @Override
                                    public void onSucess(Object object) {

                                        setResult(RESULT_OK);
                                    }
                                });


                    }

                }


            });


            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == INTENT_SELECT_BANK && resultCode == RESULT_OK) {
            if (data != null) {
                bankId = data.getExtras().getString("bankId");
                bankName = data.getExtras().getString("bankName");
                bankTv.setText("" + bankName);
            }

        } else if (requestCode == INTENT_SELECT_USER && resultCode == RESULT_OK) {
            if (data != null) {
                AscReport ascReport =  data.getParcelableExtra("Data");
                userListid = ascReport.getUserID();
                userListName = ascReport.getOutletName();
                mobileNumber = ascReport.getMobile();
                userNameTv.setText(userListName + " [" + mobileNumber + "]");
            }


        }
    }


}
