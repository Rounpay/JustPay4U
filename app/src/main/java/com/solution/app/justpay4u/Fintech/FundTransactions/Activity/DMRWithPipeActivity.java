package com.solution.app.justpay4u.Fintech.FundTransactions.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Object.BenisObject;
import com.solution.app.justpay4u.Api.Fintech.Object.RecentDmrLogin;
import com.solution.app.justpay4u.Api.Fintech.Object.SenderObject;
import com.solution.app.justpay4u.Api.Fintech.Request.GetSenderRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.CreateSenderResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetEKYCDetailResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.RechargeReportResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.Dashboard.Adapter.WalletBalanceAdapter;
import com.solution.app.justpay4u.Fintech.FundTransactions.Adapter.BeneficiaryAdapter;
import com.solution.app.justpay4u.Fintech.FundTransactions.Adapter.RecentDmrLoginAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Reports.Activity.DMRReportActivity;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.EKYCStepsDialog;
import com.solution.app.justpay4u.Util.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class DMRWithPipeActivity extends AppCompatActivity implements View.OnClickListener {


    public View noDataView, loaderView;
    CustomLoader loader = null;
    EditText searchEt;
    ArrayList<RecentDmrLogin> mRecentDmrLogins = new ArrayList<>();
    String senderNumberStr;
    AppPreferences mAppPrefrences;
    CustomAlertDialog mCustomAlertDialogs;
    ArrayList<BalanceData> mBalanceTypes = new ArrayList<>();
    BalanceResponse balanceCheckResponse;
    private LoginResponse mLoginDataResponse;
    private LinearLayout dmrLoginLayout, recentLoginView;
    private TextView heading;
    private EditText senderNumberEt;
    private LinearLayout dmrCreate;
    private EditText nameEt;
    private EditText lastNameEt;
    private EditText pincodeEt;
    private EditText addressEt;
    private TextView dobTv;
    private Button submit;
    private LinearLayout senderLayout;
    private TextView senderName;
    private TextView senderNum, monthlyLimitTv;
    private ImageView dmrLogout;
    private TextView remaining;
    private View addBeneficiaryView;
    private ImageView phoneBookIv;
    private View dmtReportView, dobView;
    private View noNetworkView, beneListView;
    private TextView noticeMsg;
    private RecyclerView recyclerView, recentRecyclerView;
    private Dialog dialog;
    private int INTENT_ADD_BENE = 5553;
    private Gson gson;
    private String deviceId;
    private String deviceSerialNum;
    private WalletBalanceAdapter mWalletBalanceAdapter;
    private int INTENT_SEND_MONEY = 4324;
    private int opTypeIntent;
    private int oidIntent;
    private String sidValue;
    private CreateSenderResponse getSenderData;
    private EKYCStepsDialog mEKYCStepsDialog;
    private boolean isEKYCCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(DMRWithPipeActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.myLooper()).post(() -> {
            setContentView(R.layout.activity_dmr);

            mAppPrefrences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            gson = new Gson();
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPrefrences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPrefrences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPrefrences);
            opTypeIntent = getIntent().getIntExtra("OpType", 0);
            oidIntent = getIntent().getIntExtra("OID", 0);


            findViews();

            new Handler(Looper.getMainLooper()).post(() -> {
                isEKYCCompleted = mAppPrefrences.getBoolean(ApplicationConstant.INSTANCE.isEKYCComplete);
                mEKYCStepsDialog = new EKYCStepsDialog(this, mLoginDataResponse, deviceId, deviceSerialNum, mAppPrefrences);

                setDbData();
                showBalanceData();
                loader.dismiss();
            });
        });
    }

    private void findViews() {

        TextView errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Beneficiary not found");
        noticeMsg = findViewById(R.id.noticeMsg);
        dmrLoginLayout = findViewById(R.id.dmr_login_layout);
        recentLoginView = findViewById(R.id.recent_login_view);
        heading = findViewById(R.id.heading);
        senderNumberEt = findViewById(R.id.senderNumberEt);
        dmrCreate = findViewById(R.id.dmr_create);
        nameEt = findViewById(R.id.nameEt);
        lastNameEt = findViewById(R.id.lastNameEt);
        pincodeEt = findViewById(R.id.pincodeEt);
        addressEt = findViewById(R.id.addressEt);
        dobTv = findViewById(R.id.dobTv);
        monthlyLimitTv = findViewById(R.id.monthlyLimitTv);
        submit = findViewById(R.id.submit);
        phoneBookIv = findViewById(R.id.phoneBookIv);
        senderLayout = findViewById(R.id.sender_layout);
        senderName = findViewById(R.id.sender_name);
        senderNum = findViewById(R.id.sender_num);
        dmrLogout = findViewById(R.id.dmr_logout);
        remaining = findViewById(R.id.remaining);
        addBeneficiaryView = findViewById(R.id.addBeneficiaryView);
        dmtReportView = findViewById(R.id.dmtReportView);
        loaderView = findViewById(R.id.loaderView);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        beneListView = findViewById(R.id.beneListView);
        searchEt = findViewById(R.id.search_all);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recentRecyclerView = findViewById(R.id.recentRecyclerView);
        recentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dobView = findViewById(R.id.dobView);

        RecyclerView balanceRecyclerView = findViewById(R.id.balanceRecyclerView);
        balanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWalletBalanceAdapter = new WalletBalanceAdapter(this, mBalanceTypes, R.layout.adapter_wallet_balance);
        balanceRecyclerView.setAdapter(mWalletBalanceAdapter);
        findViewById(R.id.clearIcon).setOnClickListener(v -> searchEt.setText(""));
        senderNumberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 10) {
                    loader.show();
                    GetSender(DMRWithPipeActivity.this, senderNumberEt.getText().toString(), loader,
                            mLoginDataResponse);
                }
            }
        });
        SetListener();


    }

    void setDbData() {
        mRecentDmrLogins = gson.fromJson(mAppPrefrences.getNonRemovalString(ApplicationConstant.INSTANCE.RecentDMRLoginPref), new TypeToken<ArrayList<RecentDmrLogin>>() {
        }.getType());
        String senderNum = mAppPrefrences.getString(ApplicationConstant.INSTANCE.senderNumberPref);
        getSenderData = gson.fromJson(mAppPrefrences.getString(ApplicationConstant.INSTANCE.senderInfoPref), CreateSenderResponse.class);
        String senderName;
        String senderRemainigLimit;
        String senderTotalLimit;
        if (getSenderData != null) {
            sidValue = getSenderData.getSid() + "";
            senderName = getSenderData.getSenderName() + "";
            senderRemainigLimit = getSenderData.getRemainingLimit() + "";
            senderTotalLimit = getSenderData.getAvailbleLimit() + "";
        } else {
            senderName = mAppPrefrences.getString(ApplicationConstant.INSTANCE.senderNamePref);
            senderRemainigLimit = mAppPrefrences.getString(ApplicationConstant.INSTANCE.senderRemainigLimit);
            senderTotalLimit = mAppPrefrences.getString(ApplicationConstant.INSTANCE.senderTotalLimit);
        }

        if (senderNum != null && senderNum.length() > 0 &&
                senderRemainigLimit != null && senderRemainigLimit.length() > 0 &&
                senderTotalLimit != null && senderTotalLimit.length() > 0) {
            SetSender(senderNum, senderName, senderRemainigLimit, senderTotalLimit);

            senderNumberStr = senderNum;
            GetBeneficiary();
        } else {
            DMRStatus();
        }

    }

    public void SetSender(String mobileNo, String senderName, String remainigBalance, String totalBalance) {
        senderNumberEt.setText("");
        nameEt.setText("");
        lastNameEt.setText("");
        pincodeEt.setText("");
        addressEt.setText("");
        dobTv.setText("");
        setCurrentDetail(mobileNo, senderName, remainigBalance, totalBalance);
    }


    public void OtherResponse() {
        senderLayout.setVisibility(View.GONE);
        noticeMsg.setVisibility(View.GONE);
        heading.setText("Create Sender");
        dmrCreate.setVisibility(View.VISIBLE);
    }

    private void DMRStatus() {
        noticeMsg.setVisibility(View.VISIBLE);
        heading.setText("Login Sender");
        dmrLoginLayout.setVisibility(View.VISIBLE);
        dmrCreate.setVisibility(View.GONE);
        senderLayout.setVisibility(View.GONE);
        senderNumberStr = "";

        if (mRecentDmrLogins != null && mRecentDmrLogins.size() > 0) {
            recentLoginView.setVisibility(View.VISIBLE);
            recentRecyclerView.setAdapter(new RecentDmrLoginAdapter(mRecentDmrLogins, this));
        } else {
            recentLoginView.setVisibility(View.GONE);
        }

    }


    private void SetListener() {
        phoneBookIv.setOnClickListener(this);
        submit.setOnClickListener(this);
        dmrLogout.setOnClickListener(this);
        addBeneficiaryView.setOnClickListener(this);
        dmtReportView.setOnClickListener(this);
        dobView.setOnClickListener(this);
        findViewById(R.id.retryBtn).setOnClickListener(v -> {
            noNetworkView.setVisibility(View.GONE);
            GetBeneficiary();
        });
    }

    public void setCurrentDetail(String number, String name, String remainigBalance, String totalBalance) {
        if (name != null && !name.isEmpty() && !name.equalsIgnoreCase("null")) {
            senderName.setText(name);
            senderName.setVisibility(View.VISIBLE);
        } else {
            senderName.setVisibility(View.GONE);
        }
        senderNum.setText(number + "");
        monthlyLimitTv.setVisibility(View.VISIBLE);
        monthlyLimitTv.setText("Monthly Limit : " + Utility.INSTANCE.formatedAmountWithOutRupees(totalBalance + ""));
        remaining.setText(Utility.INSTANCE.formatedAmountWithOutRupees(remainigBalance + ""));
        dmrLoginLayout.setVisibility(View.GONE);
        senderLayout.setVisibility(View.VISIBLE);

        RecentDmrLogin mRecentDmrLogin = new RecentDmrLogin(name.trim(), number.trim(), remainigBalance.trim());
        if (mRecentDmrLogins != null) {
            boolean isAvailable = false;
            if (mRecentDmrLogins.size() > 0) {
                for (RecentDmrLogin item : mRecentDmrLogins) {
                    if (item.getNumber().equalsIgnoreCase(number.trim())) {
                        isAvailable = true;
                        break;
                    }
                }
            }
            if (!isAvailable) {
                mRecentDmrLogins.add(mRecentDmrLogin);
            }
        } else {
            mRecentDmrLogins = new ArrayList<>();
            mRecentDmrLogins.add(mRecentDmrLogin);
        }
        mAppPrefrences.setNonRemoval(ApplicationConstant.INSTANCE.RecentDMRLoginPref, gson.toJson(mRecentDmrLogins));
    }


    @Override
    public void onClick(View v) {

        if (v == dobView) {
            final Calendar myCalendar = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd MMM yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dobTv.setText(sdf.format(myCalendar.getTime()));
            };
            DatePickerDialog mDatePicker = new DatePickerDialog(DMRWithPipeActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
            mDatePicker.show();
        }
       /* if (v == phoneBookIv) {
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
        if (v == submit) {
            createAccount();
        }
        if (v == dmrLogout) {
            if (mCustomAlertDialogs == null) {
                mCustomAlertDialogs = new CustomAlertDialog(this);
            }
            mCustomAlertDialogs.WarningWithCallBack("Are you sure, you want to logout this sender?", "Logout", false, new CustomAlertDialog.DialogCallBack() {
                @Override
                public void onPositiveClick() {
                    ApiFintechUtilMethods.INSTANCE.setSenderNumber("", "", "", "", "", "", mAppPrefrences);
                    // mAppPrefrences.set(ApplicationConstant.INSTANCE.beneficiaryListPref, "");
                    DMRStatus();
                }

                @Override
                public void onNegativeClick() {

                }
            });

        }
        if (v == addBeneficiaryView) {

            Intent i = new Intent(DMRWithPipeActivity.this, AddBeneficiaryWithPipeActivity.class);
            i.putExtra("SenderNumber", senderNumberStr);
            i.putExtra("OpType", opTypeIntent);
            i.putExtra("OID", oidIntent);
            i.putExtra("SID", sidValue);
            startActivityForResult(i, INTENT_ADD_BENE);

        }
        /*if (v == bene_list) {
            SharedPreferences prefs = DMRLogin.this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
            if (UtilMethods.INSTANCE.isNetworkAvialable(DMRLogin.this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                UtilMethods.INSTANCE.GetBeneficiary(DMRLogin.this, prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, ""),
                        mLoginDataResponse, loader);


            } else {
                UtilMethods.INSTANCE.NetworkError(DMRLogin.this);
            }
        }*/
        if (v == dmtReportView) {

            Intent m = new Intent(DMRWithPipeActivity.this, DMRReportActivity.class);
            m.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(m);

        }

    }

    private void showBalanceData() {

        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                && balanceCheckResponse.getBalanceData().size() > 0) {
            mBalanceTypes.clear();
            mBalanceTypes = balanceCheckResponse.getBalanceData();
            mWalletBalanceAdapter.notifyDataSetChanged();
        } else {
            balanceCheckResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPrefrences);
            if (balanceCheckResponse != null && balanceCheckResponse.isEKYCForced() && !isEKYCCompleted) {
                mEKYCStepsDialog.GetKycDetails(new EKYCStepsDialog.ApiCallBackTwoMethod() {
                    @Override
                    public void onSucess(GetEKYCDetailResponse object) {

                        isEKYCCompleted = object.getData().isIsEKYCDone();
                    }

                    @Override
                    public void onError(Object object) {

                    }
                });

            }
            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                    && balanceCheckResponse.getBalanceData().size() > 0) {
                showBalanceData();
            } else {
                ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse, deviceId, deviceSerialNum,
                        mAppPrefrences, mEKYCStepsDialog, object -> {
                            balanceCheckResponse = (BalanceResponse) object;
                            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                    && balanceCheckResponse.getBalanceData().size() > 0) {
                                showBalanceData();
                            }
                        });
            }

        }

    }

    void createAccount() {

        if (nameEt.getText().length() == 0) {
            nameEt.setError(getString(R.string.err_empty_field));
            nameEt.requestFocus();
            return;
        } else if (lastNameEt.getText().length() == 0) {
            lastNameEt.setError(getString(R.string.err_empty_field));
            lastNameEt.requestFocus();
            return;
        } else if (pincodeEt.getText().length() == 0) {
            pincodeEt.setError(getString(R.string.err_empty_field));
            pincodeEt.requestFocus();
            return;
        } else if (pincodeEt.getText().length() != 6) {
            pincodeEt.setError(getString(R.string.pincode_error));
            pincodeEt.requestFocus();
            return;
        } else if (addressEt.getText().length() == 0) {
            addressEt.setError(getString(R.string.err_empty_field));
            addressEt.requestFocus();
            return;
        } else if (dobTv.getText().length() == 0) {
            dobTv.setError(getString(R.string.err_empty_field));
            dobTv.requestFocus();
            return;
        }

        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(DMRWithPipeActivity.this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            CreateSender(DMRWithPipeActivity.this,
                    senderNumberEt.getText().toString(),
                    nameEt.getText().toString(),
                    lastNameEt.getText().toString(),
                    pincodeEt.getText().toString(),
                    addressEt.getText().toString(),
                    "",
                    dobTv.getText().toString(), loader, mLoginDataResponse, null, null, null);

        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(DMRWithPipeActivity.this);
        }
    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == INTENT_ADD_BENE && resultCode == RESULT_OK) {
            loader.show();
            GetBeneficiary();
        }
        /*else if (reqCode == PICK_CONTACT && resultCode == RESULT_OK) {

            Uri contactData = data.getData();
            Cursor c = DMRWithPipeActivity.this.getContentResolver().query(contactData, null, null, null, null);
            if (c.moveToFirst()) {

                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String Number = "";

                if (hasPhone.equalsIgnoreCase("1")) {
                    Cursor phones = DMRWithPipeActivity.this.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                            null, null);
                    phones.moveToFirst();
                    // For Clear Old Value Of Number...
                    senderNumberEt.setText("");
                    // <------------------------------------------------------------------>
                    Number = phones.getString(phones.getColumnIndex("data1"));
//                            Log.e("number is:",Number);
                }
                String Name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (!Number.equals("")) {
                    SetNumber(Number);
                } else {
                    Toast.makeText(DMRWithPipeActivity.this, "Please select a valid number", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (reqCode == INTENT_PERMISSION && resultCode == RESULT_OK) {

            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, PICK_CONTACT);
        }*/
        else if (reqCode == INTENT_SEND_MONEY && resultCode == RESULT_OK) {
            ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse, deviceId, deviceSerialNum,
                    mAppPrefrences, mEKYCStepsDialog, object -> {
                        balanceCheckResponse = (BalanceResponse) object;
                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                && balanceCheckResponse.getBalanceData().size() > 0) {
                            showBalanceData();
                        }
                    });
        }

    }

    public void SetNumber(final String Number) {
        String Number1 = Number.replace("+91", "");
        String Number2 = Number1.replace("(", "");
        String Number3 = Number2.replace(")", "");
        String Number4 = Number3.replace(" ", "");
        String Number5 = Number4.replace("-", "");
        String Number6 = Number5.replace("_", "");
        senderNumberEt.setText(Number6);
    }


    public void CreateSender(final Activity context, final String MobileNumber, String name, String lastName,
                             String pincode, String address, final String otp, String dob, final CustomLoader loader,
                             LoginResponse LoginDataResponse, TextView timerTv, TextView resendCodeTv, Dialog mDialog) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            SenderObject mSenderobject = new SenderObject(MobileNumber, name, lastName, pincode, address, otp, dob);
            Call<RechargeReportResponse> call = git.CreateSenderWithPipe(new GetSenderRequest(oidIntent + "", mSenderobject, LoginDataResponse.getData().getUserID() + "",
                    LoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, deviceSerialNum,
                    "", BuildConfig.VERSION_NAME, deviceId, LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().isOTPRequired()) {
                                        if (mDialog != null && mDialog.isShowing()) {
                                            ApiFintechUtilMethods.INSTANCE.Successful(DMRWithPipeActivity.this, "OTP has been resend successfully");
                                            if (timerTv != null && resendCodeTv != null) {
                                                ApiFintechUtilMethods.INSTANCE.setTimer(timerTv, resendCodeTv);
                                            }
                                        } else {


                                            ApiFintechUtilMethods.INSTANCE.openOtpDialog(DMRWithPipeActivity.this, 6, MobileNumber, new ApiFintechUtilMethods.DialogOTPCallBack() {
                                                @Override
                                                public void onPositiveClick(String otpValue, TextView timerTv, TextView resendCodeTv, Dialog mDialog) {
                                                    loader.show();
                                                    VerifySender(DMRWithPipeActivity.this, MobileNumber, otpValue, response.body().getSid(), loader, mLoginDataResponse, mDialog);
                                                }

                                                @Override
                                                public void onResetCallback(String otpValue, TextView timerTv, TextView resendCodeTv, Dialog mDialog) {
                                                    CreateSender(context, MobileNumber, name, lastName,
                                                            pincode, address, otp, dob, loader,
                                                            LoginDataResponse, timerTv, resendCodeTv, mDialog);
                                                }
                                            });

                                        }
                                    } else {
                                        if (mDialog != null && mDialog.isShowing()) {
                                            mDialog.dismiss();
                                        }
                                        ApiFintechUtilMethods.INSTANCE.Successful(context, response.body().getMsg() + "");
                                        loader.show();
                                        GetSender(DMRWithPipeActivity.this, MobileNumber, loader, mLoginDataResponse);

                                    }

                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                    }
                                }
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(DMRWithPipeActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    try {

                        ApiFintechUtilMethods.INSTANCE.apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        ApiFintechUtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(context, e.getMessage());
        }

    }


    public void VerifySender(final Activity context, final String MobileNumber, String otp, String sid, final CustomLoader loader,
                             LoginResponse LoginDataResponse, Dialog mDialog) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<RechargeReportResponse> call = git.VerifySenderWithPipe(new GetSenderRequest(oidIntent + "", sid,
                    new SenderObject(MobileNumber, otp, sid),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (mDialog != null && mDialog.isShowing()) {
                                        mDialog.dismiss();
                                    }
                                    ApiFintechUtilMethods.INSTANCE.Successful(context, response.body().getMsg() + "");
                                    loader.show();
                                    GetSender(DMRWithPipeActivity.this, MobileNumber, loader, mLoginDataResponse);
                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                    }
                                }

                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(DMRWithPipeActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    try {

                        ApiFintechUtilMethods.INSTANCE.apiFailureError(context, t);


                    } catch (IllegalStateException ise) {
                        ApiFintechUtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(context, e.getMessage());
        }

    }


    public void GetSender(final Activity context, final String MobileNumber, final CustomLoader loader, LoginResponse LoginDataResponse) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<CreateSenderResponse> call = git.GetSenderWithPipe(new GetSenderRequest(oidIntent + "", new SenderObject(MobileNumber),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<CreateSenderResponse>() {

                @Override
                public void onResponse(Call<CreateSenderResponse> call, retrofit2.Response<CreateSenderResponse> response) {
                    try {
                        if (response.isSuccessful()) {
                            getSenderData = response.body();
                            if (getSenderData != null) {
                                if (getSenderData.getStatuscode() == 1) {
                                    if (!getSenderData.isSenderNotExists()) {
                                        ApiFintechUtilMethods.INSTANCE.setSenderNumber(MobileNumber, getSenderData.getSenderName(),
                                                getSenderData.getSenderBalance(), getSenderData.getRemainingLimit(),
                                                getSenderData.getAvailbleLimit(), gson.toJson(getSenderData), mAppPrefrences);
                                        senderNumberStr = MobileNumber + "";
                                        sidValue = getSenderData.getSid();
                                        SetSender(MobileNumber + "", getSenderData.getSenderName() + "",
                                                getSenderData.getRemainingLimit() + "", getSenderData.getAvailbleLimit() + "");

                                        GetBeneficiary();

                                    } else {
                                        //Error(context, getSenderData.getMsg() + "");
                                        OtherResponse();
                                        if (loader != null && loader.isShowing()) {
                                            loader.dismiss();
                                        }
                                    }

                                } else {
                                    if (loader != null && loader.isShowing()) {
                                        loader.dismiss();
                                    }
                                    if (!getSenderData.isVersionValid()) {
                                        ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(context, getSenderData.getMsg() + "");
                                    }
                                }

                            } else {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }
                            }
                        } else {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(DMRWithPipeActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CreateSenderResponse> call, Throwable t) {

                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    try {

                        ApiFintechUtilMethods.INSTANCE.apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        ApiFintechUtilMethods.INSTANCE.Error(context, ise.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            e.printStackTrace();
            ApiFintechUtilMethods.INSTANCE.Error(context, e.getMessage());
        }

    }

    private void GetBeneficiary() {

        if (beneListView.getVisibility() == View.GONE) {
            loaderView.setVisibility(View.VISIBLE);
        }
        ApiFintechUtilMethods.INSTANCE.GetBeneficiaryWithPipe(DMRWithPipeActivity.this, oidIntent + "", senderNumberStr, mLoginDataResponse, loader, mAppPrefrences,
                deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        loaderView.setVisibility(View.GONE);
                        getBeneficiaryList((ArrayList<BenisObject>) object);
                    }

                    @Override
                    public void onError(int error) {
                        loaderView.setVisibility(View.GONE);
                        if (error == ApiFintechUtilMethods.INSTANCE.ERROR_OTHER) {
                            noDataView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                        } else {
                            noDataView.setVisibility(View.GONE);
                            noNetworkView.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    public void getBeneficiaryList(ArrayList<BenisObject> mBeneArray) {

        if (mBeneArray != null && mBeneArray.size() > 0) {
            beneListView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            BeneficiaryAdapter mAdapter = new BeneficiaryAdapter(mBeneArray, this);
            recyclerView.setAdapter(mAdapter);
            searchEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mAdapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else {
            beneListView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
        }

    }

    public void confirmationDialog(BenisObject operator) {

        if (dialog != null && dialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_beneficiary_delete_confirm, null);

        ((TextView) view.findViewById(R.id.beneName)).setText(operator.getBeneName());
        ((TextView) view.findViewById(R.id.beneAccountNumber)).setText(operator.getAccountNo());
        ((TextView) view.findViewById(R.id.beneBank)).setText(operator.getBankName());
        ((TextView) view.findViewById(R.id.beneIFSC)).setText(operator.getIfsc());
        dialog = new Dialog(this, R.style.alert_dialog_light);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.findViewById(R.id.cancel).setOnClickListener(v -> dialog.dismiss());
        view.findViewById(R.id.ok).setOnClickListener(v -> {
            dialog.dismiss();
            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                ApiFintechUtilMethods.INSTANCE.DeletebeneficiaryWithPipe(this, null, null, oidIntent + "", sidValue, "", senderNumberStr, operator.getBeneID(), loader, mLoginDataResponse,
                        deviceId, deviceSerialNum, object -> GetBeneficiary());
            } else {
                ApiFintechUtilMethods.INSTANCE.NetworkError(this);
            }
        });
        dialog.show();
    }

    public void recentLogin(RecentDmrLogin operator) {
        senderNumberEt.setText(operator.getNumber());
    }

    public void sendMoneyClick(BenisObject operator) {
        Intent transferIntent = new Intent(this, MoneyTransferWithPipeActivity.class);
        transferIntent.putExtra("name", operator.getBeneName());
        transferIntent.putExtra("bankAccount", operator.getAccountNo());
        transferIntent.putExtra("bank", operator.getBankName());
        transferIntent.putExtra("OpType", opTypeIntent);
        transferIntent.putExtra("OID", oidIntent);
        transferIntent.putExtra("SID", sidValue);
        transferIntent.putExtra("beneficiaryCode", operator.getBeneID());
        transferIntent.putExtra("ifsc", operator.getIfsc());
        transferIntent.putExtra("SenderNumber", senderNumberStr);
        startActivityForResult(transferIntent, INTENT_SEND_MONEY);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
