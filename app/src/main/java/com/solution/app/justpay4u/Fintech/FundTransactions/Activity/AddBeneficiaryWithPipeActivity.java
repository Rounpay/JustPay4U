package com.solution.app.justpay4u.Fintech.FundTransactions.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;

public class AddBeneficiaryWithPipeActivity extends AppCompatActivity implements View.OnClickListener {

    EditText beneficiaryName, beneficiaryNumber, accountNumber, ifscEt;
    TextView bankTv;
    View bankView;
    TextView accVerify;
    View btAdd;
    CustomLoader loader;
    String bankId, isNeft, isImps, accLmt, ekO_BankID;
    String bankName;
    String accVerification;
    String shortCode = "";
    String verified = "1";
    String currentSenderNumber = "";
    String fullIfscCode = "";
    ImageView ivContact;
    private LoginResponse mLoginDataResponse;
    private int INTENT_SELECT_BANK = 4343;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private int opTypeValue, oidValue;
    private String sidValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.myLooper()).post(() -> {
            setContentView(R.layout.activity_add_beneficiary);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            opTypeValue = getIntent().getIntExtra("OpType", 0);
            oidValue = getIntent().getIntExtra("OID", 0);
            sidValue = getIntent().getStringExtra("SID");
            GetId();
            ApiFintechUtilMethods.INSTANCE.GetBanklist(this, loader, mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum, null);
        });
    }

    private void GetId() {

        currentSenderNumber = getIntent().getStringExtra("SenderNumber");
        if (currentSenderNumber == null || currentSenderNumber.isEmpty()) {

            currentSenderNumber = mAppPreferences.getString(ApplicationConstant.INSTANCE.senderNumberPref);
        }

        beneficiaryName = findViewById(R.id.beneficiaryName);
        beneficiaryNumber = findViewById(R.id.beneficiaryNumber);

        beneficiaryNumber.setText(currentSenderNumber);
        bankTv = findViewById(R.id.bank);
        bankView = findViewById(R.id.bankView);
        accountNumber = findViewById(R.id.accountNumber);
        ifscEt = findViewById(R.id.ifsc);
        accVerify = findViewById(R.id.accVerify);
        btAdd = findViewById(R.id.bt_add);
        ivContact = findViewById(R.id.iv_contact);


        ifscEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (shortCode != null && !shortCode.isEmpty() && !s.toString().startsWith(shortCode)) {
                    ifscEt.setText(shortCode);
                    Selection.setSelection(ifscEt.getText(), ifscEt.getText().length());

                }

            }
        });
        SetListener();
    }

    private void SetListener() {
        accVerify.setOnClickListener(this);
        bankView.setOnClickListener(this);
        btAdd.setOnClickListener(this);
        ivContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == bankView) {
            Intent bankIntent = new Intent(this, BankListScreenActivity.class);
            bankIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(bankIntent, INTENT_SELECT_BANK);
        }

        if (v == accVerify) {
            if (validationVerifyAccount()) {

                if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
                    loader.show();

                    ApiFintechUtilMethods.INSTANCE.VerifyAccountWithPipe(this, oidValue + "", beneficiaryName.getText().toString(),
                            bankId, currentSenderNumber, ifscEt.getText().toString().trim(), accountNumber.getText().toString().trim(), bankName, loader,
                            mLoginDataResponse, deviceId, deviceSerialNum, object -> {
                                accVerify.setVisibility(View.GONE);
                                beneficiaryName.setText((String) object);
                                verified = "1";
                            });

                } else {
                    ApiFintechUtilMethods.INSTANCE.NetworkError(this);
                }
            }
        }

        if (v == btAdd) {
            if (validationAddBeneficiary()) {

                if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
                    loader.show();
                    ApiFintechUtilMethods.INSTANCE.AddBeneficiaryWithPipe(this, oidValue + "", sidValue, "", currentSenderNumber, beneficiaryNumber.getText().toString().trim(), beneficiaryName.getText().toString().trim(),
                            ifscEt.getText().toString().trim(), accountNumber.getText().toString().trim(), bankName, bankId, loader,
                            AddBeneficiaryWithPipeActivity.this, mLoginDataResponse, deviceId, deviceSerialNum, object -> {
                                beneficiaryName.setText("");
                                beneficiaryNumber.setText(currentSenderNumber);
                                accountNumber.setText("");
                                shortCode = "";
                                ifscEt.setText("");

                                bankTv.setText("");

                                setResult(RESULT_OK);
                            });
                } else {
                    ApiFintechUtilMethods.INSTANCE.NetworkError(this);
                }
            }
        }
       /* if (v == ivContact) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(new Intent(this, PermissionActivity.class),
                            INTENT_PERMISSION);

                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(intent, PICK_CONTACT);
                }
            } else {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, PICK_CONTACT);
            }
        }*/
    }

    public boolean validationAddBeneficiary() {

        if (beneficiaryName.getText().length() == 0) {
            beneficiaryName.setError(getString(R.string.err_empty_field));
            beneficiaryName.requestFocus();
            return false;
        } else if (beneficiaryNumber.getText().length() == 0) {
            beneficiaryNumber.setError(getString(R.string.err_empty_field));
            beneficiaryNumber.requestFocus();
            return false;
        } else if (beneficiaryNumber.getText().length() != 10) {
            beneficiaryNumber.setError(getString(R.string.mobilenumber_error));
            beneficiaryNumber.requestFocus();
            return false;
        } else if (bankTv.getText().length() == 0) {
            bankTv.setError(getString(R.string.err_empty_field));
            bankTv.requestFocus();
            return false;
        } else if ( ifscEt.getText().length() == 0) {
            ifscEt.setError(getString(R.string.err_empty_field));
            ifscEt.requestFocus();
            return false;
        } else if ( ifscEt.getText().length() != 11) {
            ifscEt.setError(getString(R.string.bene_ifsc_error));
            ifscEt.requestFocus();
            return false;
        } else if (accountNumber.getText().length() == 0) {
            accountNumber.setError(getString(R.string.err_empty_field));
            accountNumber.requestFocus();
            return false;
        } else if (accountNumber.getText().toString().trim().length() < 10) {
            accountNumber.setError(getResources().getString(R.string.bene_acc_error));
            accountNumber.requestFocus();
            return false;
        }
        return true;

    }

    public boolean validationVerifyAccount() {

        if (beneficiaryNumber.getText().length() == 0) {
            beneficiaryNumber.setError(getString(R.string.err_empty_field));
            beneficiaryNumber.requestFocus();
            return false;
        } else if (beneficiaryNumber.getText().length() != 10) {
            beneficiaryNumber.setError(getString(R.string.mobilenumber_error));
            beneficiaryNumber.requestFocus();
            return false;
        } else if (bankTv.getText().length() == 0) {
            bankTv.setError(getString(R.string.err_empty_field));
            bankTv.requestFocus();
            return false;
        } else if ( ifscEt.getText().length() == 0) {
            ifscEt.setError(getString(R.string.err_empty_field));
            ifscEt.requestFocus();
            return false;
        } else if (ifscEt.getText().length() != 11) {
            ifscEt.setError(getString(R.string.bene_ifsc_error));
            ifscEt.requestFocus();
            return false;
        } else if (accountNumber.getText().length() == 0) {
            accountNumber.setError(getString(R.string.err_empty_field));
            accountNumber.requestFocus();
            return false;
        } else if (accountNumber.getText().toString().trim().length() < 10) {
            accountNumber.setError(getResources().getString(R.string.bene_acc_error));
            accountNumber.requestFocus();
            return false;
        }
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == INTENT_SELECT_BANK && resultCode == RESULT_OK) {
            bankId = data.getExtras().getString("bankId");
            bankName = data.getExtras().getString("bankName");
            accVerification = data.getExtras().getString("accVerification");
            shortCode = data.getExtras().getString("shortCode");
            fullIfscCode = data.getExtras().getString("ifsc");
            isNeft = data.getExtras().getString("neft");
            isImps = data.getExtras().getString("imps");
            accLmt = data.getExtras().getString("accLmt");
            ekO_BankID = data.getExtras().getString("ekO_BankID");

            bankTv.setText("" + bankName);
            bankTv.setError(null);
            if (shortCode != null && shortCode.length() > 0 && fullIfscCode != null && fullIfscCode.length() > 0
                    && fullIfscCode.startsWith(shortCode)) {

                ifscEt.setText(fullIfscCode);
                Selection.setSelection(ifscEt.getText(), ifscEt.getText().length());
            } else if (fullIfscCode != null && fullIfscCode.length() > 0 && fullIfscCode.length() > 4) {
                shortCode = fullIfscCode.substring(0, 4);
                ifscEt.setText(fullIfscCode);
                Selection.setSelection(ifscEt.getText(), ifscEt.getText().length());
            } else {
                shortCode = "";
                ifscEt.setText("");
            }

            if (accVerification.equalsIgnoreCase("true")) {
                accVerify.setVisibility(View.VISIBLE);
            } else {
                accVerify.setVisibility(View.GONE);
            }

        }
        /*else if (requestCode == PICK_CONTACT && resultCode == Activity.RESULT_OK) {

            Uri contactData = data.getData();
            Cursor c = this.getContentResolver().query(contactData, null, null, null, null);
            if (c.moveToFirst()) {

                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String Number = "";

                if (hasPhone.equalsIgnoreCase("1")) {
                    Cursor phones = this.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                            null, null);
                    phones.moveToFirst();
                    // For Clear Old Value Of Number...
                    beneficiaryNumber.setText("");
                    // <------------------------------------------------------------------>
                    Number = phones.getString(phones.getColumnIndex("data1"));
//                            Log.e("number is:",Number);
                }
                String Name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (!Number.equals("")) {
                    SetNumber(Number);
                } else {
                    Toast.makeText(this, "Please select a valid number", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (requestCode == INTENT_PERMISSION && resultCode == Activity.RESULT_OK) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, PICK_CONTACT);
        }*/
    }

    public void SetNumber(final String Number) {
        String Number1 = Number.replace("+91", "");
        String Number2 = Number1.replace("(", "");
        String Number3 = Number2.replace(")", "");
        String Number4 = Number3.replace(" ", "");
        String Number5 = Number4.replace("-", "");
        String Number6 = Number5.replace("_", "");
        beneficiaryNumber.setText(Number6);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
