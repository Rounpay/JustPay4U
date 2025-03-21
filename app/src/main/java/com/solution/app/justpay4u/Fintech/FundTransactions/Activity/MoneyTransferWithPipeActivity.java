package com.solution.app.justpay4u.Fintech.FundTransactions.Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.solution.app.justpay4u.Api.Fintech.Response.DMTChargedAmountResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownModel;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;

public class MoneyTransferWithPipeActivity extends AppCompatActivity implements View.OnClickListener {


    String name, bank, bankAccount, beneficiaryCode, channel = "true", amount, senderNumber, ifsc;
    CustomLoader loader;
    ArrayList<DropDownModel> dropDownModels = new ArrayList<>();
    int selectedModePos = 0;
    private AppCompatTextView bankTv;
    private AppCompatTextView accountNumberTv;
    private TextView acHolderNameTv, ifscTv;
    private View paymentModeChooserView;
    private TextView paymentModeTv;
    private EditText tranferAmountEt;
    private View btTransfer;
    private LoginResponse mLoginDataResponse;
    private DropDownDialog mDropDownDialog;
    private Dialog dialog;
    private AppPreferences mAppPreferences;
    private int opTypeValue, oidValue;
    private String sidValue;
    private String deviceId, deviceSerialNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_money_transfer);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            mDropDownDialog = new DropDownDialog(this);
            name = getIntent().getStringExtra("name");
            bank = getIntent().getStringExtra("bank");
            bankAccount = getIntent().getStringExtra("bankAccount");
            beneficiaryCode = getIntent().getStringExtra("beneficiaryCode");
            ifsc = getIntent().getStringExtra("ifsc");
            senderNumber = getIntent().getStringExtra("SenderNumber");
            dropDownModels.add(new DropDownModel("IMPS", "true"));
            dropDownModels.add(new DropDownModel("NEFT", "false"));
            opTypeValue = getIntent().getIntExtra("OpType", 0);
            oidValue = getIntent().getIntExtra("OID", 0);
            sidValue = getIntent().getStringExtra("SID");


            selectedModePos = 0;

            channel = "true";
            findViews();
        });
    }

    private void findViews() {

        bankTv = findViewById(R.id.bankTv);
        bankTv.setText(bank + "");
        accountNumberTv = findViewById(R.id.accountNumberTv);
        accountNumberTv.setText(bankAccount + "");
        ifscTv = findViewById(R.id.ifscTv);
        ifscTv.setText(ifsc + "");
        acHolderNameTv = findViewById(R.id.acHolderNameTv);
        acHolderNameTv.setText(name + "");
        paymentModeChooserView = findViewById(R.id.paymentModeChooserView);
        paymentModeTv = findViewById(R.id.paymentModeTv);
        paymentModeTv.setText("IMPS");
        tranferAmountEt = findViewById(R.id.tranferAmountEt);
        btTransfer = findViewById(R.id.bt_transfer);
        paymentModeChooserView.setOnClickListener(this);
        btTransfer.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == paymentModeChooserView) {

            mDropDownDialog.showDropDownPopup(v, selectedModePos, dropDownModels, (clickPosition, value, object) -> {
                selectedModePos = clickPosition;
                paymentModeTv.setText(value + "");
                channel = (String) object;
            });

        }


        if (v == btTransfer) {
            submitData();
        }
    }

    public void submitData() {


        if (tranferAmountEt.getText().toString().trim().length() == 0) {
            tranferAmountEt.setError(getString(R.string.err_empty_field));
            tranferAmountEt.requestFocus();
            return;

        }
        amount = tranferAmountEt.getText().toString().trim();
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);


            ApiFintechUtilMethods.INSTANCE.GetChargedAmountWithPipe(this, oidValue + "", amount, loader,
                    mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, object -> {
                        DMTChargedAmountResponse mDmtChargedAmountResponse = (DMTChargedAmountResponse) object;
                        double amt = Double.parseDouble(amount);
                        double charged = mDmtChargedAmountResponse.getChargedAmount();
                        double totalAmount = amt + charged;
                        confirmationDialog(amt, charged, totalAmount);
                    });
           /* } else if (flag == 2) {
                UtilMethods.INSTANCE.SendMoney(this, pinPassEt.getText().toString(), beneficiaryCode, senderNumber,
                        ifsc, bankAccount, amount, channel, bank, name, loader, MoneyTransfer.this, submitButton, mLoginDataResponse);
            }*/
        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }


    public void confirmationDialog(double amt, double charged, double totalAmount) {

        if (dialog != null && dialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_money_transfer_confiormation, null);

        View pinPassView = view.findViewById(R.id.pinPassView);
        EditText pinPassEt = view.findViewById(R.id.pinPassEt);
        ((TextView) view.findViewById(R.id.bankName)).setText(bank + " (" + ifsc + ")");
        ((TextView) view.findViewById(R.id.accountNo)).setText(bankAccount);
        ((TextView) view.findViewById(R.id.name)).setText(name);
        ((TextView) view.findViewById(R.id.mode)).setText(paymentModeTv.getText().toString());

        ((TextView) view.findViewById(R.id.amountTv)).setText(Utility.INSTANCE.formatedAmountWithRupees(amt + ""));
        ((TextView) view.findViewById(R.id.dmtChargeTv)).setText(Utility.INSTANCE.formatedAmountWithRupees(charged + ""));
        ((TextView) view.findViewById(R.id.totalAmtTv)).setText(Utility.INSTANCE.formatedAmountWithRupees(totalAmount + ""));

        dialog = new Dialog(this, R.style.alert_dialog_light);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (mLoginDataResponse.getData().getIsPinRequired() || mLoginDataResponse.getData().getIsDoubleFactor()) {
            pinPassView.setVisibility(View.VISIBLE);
        } else {
            pinPassView.setVisibility(View.GONE);
        }
        view.findViewById(R.id.cancel).setOnClickListener(v -> dialog.dismiss());
        view.findViewById(R.id.ok).setOnClickListener(v -> {

            if (pinPassView.getVisibility() == View.VISIBLE && pinPassEt.getText().length() == 0) {
                pinPassEt.setError(getString(R.string.err_empty_field));
                pinPassEt.requestFocus();
                return;
            }
            dialog.dismiss();
            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
                setResult(RESULT_OK);
                loader.show();
                ApiFintechUtilMethods.INSTANCE.SendMoneyWithPipe(this, oidValue + "", pinPassEt.getText().toString(), beneficiaryCode, senderNumber,
                        ifsc, bankAccount, amount, channel, bank, name, loader, mAppPreferences, mLoginDataResponse, deviceId, deviceSerialNum);
            } else {
                ApiFintechUtilMethods.INSTANCE.NetworkError(this);
            }
        });
        dialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
