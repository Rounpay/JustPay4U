package com.solution.app.justpay4u.Networking.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Object.MoveToWalletMappings;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.WalletTypeResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.Fintech.MoveToWallet.Adapter.WalletBalanceAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownModel;

import java.util.ArrayList;

public class CryptoWithdrawalActivity extends AppCompatActivity {

    View  moveFromChooserView;
    RecyclerView walletBalance;
    CustomLoader loader;
    EditText amount;
    TextView submit;
    View sourceView;
    String actiontype = "1";

    ArrayList<BalanceData> balanceTypes = new ArrayList<>();
    ArrayList<DropDownModel> walletTypeFromDropDown = new ArrayList<>();
    DropDownDialog mDropDownDialog;
    Gson gson;

    private WalletTypeResponse mWalletTypeResponse;
    private LoginResponse mLoginDataResponse;
    private TextView chooseMoveTo, chooseMoveFrom;
    private int selectedFromWalletPos = -1;
    private int selectedFromWalletId = -1;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_crypto_withdrawal);
            getIds();
            mDropDownDialog = new DropDownDialog(this);
            balanceTypes = getIntent().getParcelableArrayListExtra("items");
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            gson = new Gson();
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            adaptDataInRecyclerView(walletBalance);

            new Handler(Looper.getMainLooper()).post(() -> {

                mWalletTypeResponse = ApiFintechUtilMethods.INSTANCE.getWalletTypeResponse(mAppPreferences);
                setFromToData();
                setListeners();

            });
        });

    }



    private void getIds() {
        sourceView = findViewById(R.id.sourceView);
        moveFromChooserView = findViewById(R.id.moveFromChooserView);

        chooseMoveFrom = (TextView) findViewById(R.id.chooseMoveFrom);
        chooseMoveTo = (TextView) findViewById(R.id.chooseMoveTo);
        walletBalance = (RecyclerView) findViewById(R.id.walletBalance);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        amount = (EditText) findViewById(R.id.amount);
        submit = findViewById(R.id.submit);
    }


    private void setListeners() {


        moveFromChooserView.setOnClickListener(v ->
                mDropDownDialog.showDropDownPopup(v, selectedFromWalletPos, walletTypeFromDropDown, (clickPosition, value, object) -> {
                    if (selectedFromWalletPos != clickPosition) {
                        setFromData(clickPosition, (MoveToWalletMappings) object, value);
                    }

                }));




        submit.setOnClickListener(v -> {
            if (chooseMoveFrom.getText().toString().isEmpty()) {
                chooseMoveFrom.setError(getString(R.string.err_empty_field));
                chooseMoveFrom.requestFocus();
                return;
            } else if (chooseMoveTo.getText().toString().isEmpty()) {
                chooseMoveTo.setError(getString(R.string.err_empty_field));
                chooseMoveTo.requestFocus();
                return;
            } else if (amount.getText().toString().isEmpty()) {
                amount.setError(getString(R.string.err_empty_field));
                amount.requestFocus();
                return;
            }
            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(CryptoWithdrawalActivity.this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                /*MoveToWallet(CryptoWithdrawalActivity.this, actiontype, selectedOid, amount.getText().toString());*/
            } else {
                ApiFintechUtilMethods.INSTANCE.NetworkError(CryptoWithdrawalActivity.this);
            }

        });


    }


    private void adaptDataInRecyclerView(RecyclerView recyclerView) {

        if (balanceTypes != null && balanceTypes.size() > 0) {
            WalletBalanceAdapter mAdapter = new WalletBalanceAdapter(CryptoWithdrawalActivity.this, balanceTypes);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
    }


    void setFromToData() {
        if (mWalletTypeResponse != null && mWalletTypeResponse.getMoveToWalletMappings() != null &&
                mWalletTypeResponse.getMoveToWalletMappings().size() > 0) {

            ArrayList<Integer> addedFromWalletId = new ArrayList<>();
            ArrayList<DropDownModel> walletTypeToDropDownTemp = new ArrayList<>();
            for (MoveToWalletMappings mMoveToWalletMappings : mWalletTypeResponse.getMoveToWalletMappings()) {

                String fromName = /*"From " +*/ mMoveToWalletMappings.getFromWalletType().replace("Mini", "") + " Wallet";
                String toName = "";
                if (mMoveToWalletMappings.getToWalletID() == 3) {
                    toName = /*"To " + */mMoveToWalletMappings.getToWalletType() + " Account";
                } else {
                    toName = /*"To " +*/ mMoveToWalletMappings.getToWalletType() + " Wallet";
                }

                if (!addedFromWalletId.contains(mMoveToWalletMappings.getFromWalletID())) {
                    walletTypeFromDropDown.add(new DropDownModel(fromName, mMoveToWalletMappings));
                    walletTypeToDropDownTemp = new ArrayList<>();
                }

                walletTypeToDropDownTemp.add(new DropDownModel(toName, mMoveToWalletMappings));
                //toMap.put(mMoveToWalletMappings.getFromWalletID(), walletTypeToDropDownTemp);


                if (addedFromWalletId.size() == 0) {
                    setFromData(0, mMoveToWalletMappings, fromName);
                    /*setToData(0, mMoveToWalletMappings, toName);*/
                }

                addedFromWalletId.add(mMoveToWalletMappings.getFromWalletID());
            }

            if (walletTypeFromDropDown.size() == 1) {
                sourceView.setVisibility(View.GONE);
            } else {
                sourceView.setVisibility(View.VISIBLE);
            }
           /* if (walletTypeToDropDown.size() == 0 && toMap.containsKey(selectedFromWalletId)) {
                walletTypeToDropDown = toMap.get(selectedFromWalletId);
            }*/

        } else {
            ApiFintechUtilMethods.INSTANCE.WalletType(CryptoWithdrawalActivity.this, mLoginDataResponse, mAppPreferences, object -> {
                mWalletTypeResponse = (WalletTypeResponse) object;
                if (mWalletTypeResponse != null && mWalletTypeResponse.getMoveToWalletMappings() != null &&
                        mWalletTypeResponse.getMoveToWalletMappings().size() > 0) {
                    setFromToData();
                }
            });
        }
    }

    void setFromData(int clickPosition, MoveToWalletMappings moveToWalletMappings, String value) {
        if (moveToWalletMappings != null) {
            selectedFromWalletPos = clickPosition;
            selectedFromWalletId = moveToWalletMappings.getFromWalletID();
            actiontype = moveToWalletMappings.getFromWalletID() + "";
            chooseMoveFrom.setText(value + "");
            //walletTypeToDropDown = toMap.get(selectedFromWalletId);
            chooseMoveTo.setText("");
           // selectedToWalletPos = -1;
           // selectedToWalletId = -1;
           // selectedOid = 0;
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
