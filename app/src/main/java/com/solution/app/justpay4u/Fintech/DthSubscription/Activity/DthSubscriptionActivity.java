package com.solution.app.justpay4u.Fintech.DthSubscription.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.AreaWithPincode;
import com.solution.app.justpay4u.Api.Fintech.Object.CommissionDisplay;
import com.solution.app.justpay4u.Api.Fintech.Object.DthPackage;
import com.solution.app.justpay4u.Api.Fintech.Object.DthSubscriptionReport;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.Api.Fintech.Response.DthSubscriptionReportResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetDthPackageResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.AreaWithPincodeResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.AddMoneyActivity;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.RechargeBillPaymentActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.RechargeProviderActivity;
import com.solution.app.justpay4u.Fintech.Reports.Activity.DthSubscriptionReportActivity;
import com.solution.app.justpay4u.Fintech.Reports.Adapter.DthSubscriptionReportAdapter;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownModel;
import com.solution.app.justpay4u.Util.GetLocation;
import com.solution.app.justpay4u.Util.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class DthSubscriptionActivity extends AppCompatActivity {


    int maxAmountLength = 0;
    int minAmountLength = 0;
    boolean isAcountNumeric;
    int minNumberLength = 0, maxNumberLength = 0;
    String AccountName = "", Account_Remark = "";
    Boolean BBPS = false;
    Boolean isBilling = false;
    String StartWith = "";
    ArrayList<String> StartWithArray = new ArrayList<>();
    String operatorSelected = "";
    int operatorSelectedId = 0;
    CustomAlertDialog mCustomAlertDialog;
    AppPreferences mAppPreferences;
    GetLocation mGetLocation;
    private RelativeLayout selectedOperatorView;
    private ImageView ivOprator;
    private AppCompatTextView tvOperator;
    private ImageView billLogo;
    private RelativeLayout rlPackage;
    private TextView tvPackage;
    private TextView viewChannel;
    private EditText etName;
    private EditText etLastName;
    private RelativeLayout rlGender;
    private TextView tvGender;
    private TextView tvNumberName;
    private EditText etNumber;
    private EditText etAddress;
    private EditText etPincode;
    private RelativeLayout rlArea;
    private TextView tvArea;
    private LinearLayout historyView;
    private AppCompatTextView viewMore;
    private RecyclerView recyclerView;
    private Button btRecharge;
    private String fromDateStr, toDateStr;
    private String icon;
    private RequestOptions requestOptionsAdapter, requestOptions;
    private String from;
    private int fromId;
    private CustomLoader loader;
    private GetDthPackageResponse mGetDthPackageResponse;
    private int INTENT_SELECT_OPERATOR = 432;
    private int INTENT_PACKAGE = 529;
    private DthPackage mIntentDthPackage;
    private LoginResponse mLoginDataResponse;
    private boolean isBBPS;
    private String deviceId, deviceSerialNum;
    private int selectedAreaId;
    private String dataOfPincode = "";
    private DropDownDialog mDropDownDialog;
    private ArrayList<String> arrayListGender = new ArrayList<>();
    private int selectedGenderPos = -1;
    private int selectedAreaPos = -1;
    private ArrayList<DropDownModel> arrayListArea = new ArrayList<>();
    private String inputPinPass = "";
    private int INTENT_ADD_MONEY = 956;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.myLooper()).post(() -> {
            setContentView(R.layout.activity_dth_subscription);

            arrayListGender.add("Male");
            arrayListGender.add("Female");

            mDropDownDialog = new DropDownDialog(this);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            mCustomAlertDialog = new CustomAlertDialog(this);

            requestOptionsAdapter = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_AppLogo();
            findViews();


            final Calendar myCalendar = Calendar.getInstance();

            fromDateStr = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(myCalendar.getTime());
            toDateStr = fromDateStr;

            mGetLocation = new GetLocation(this, loader);
            if (ApiFintechUtilMethods.INSTANCE.getLattitude == 0 || ApiFintechUtilMethods.INSTANCE.getLongitude == 0) {
                mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
                    ApiFintechUtilMethods.INSTANCE.getLattitude = lattitude;
                    ApiFintechUtilMethods.INSTANCE.getLongitude = longitude;
                });
            }
            HitApi();
        });
    }


    private void findViews() {
        from = getIntent().getExtras().getString("from");
        fromId = getIntent().getExtras().getInt("fromId", 0);
        setTitle(from + " Subscriptions");
        requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_AppLogo_circleCrop();

        selectedOperatorView = findViewById(R.id.selected_operator_view);
        ivOprator = findViewById(R.id.iv_oprator);
        tvOperator = (AppCompatTextView) findViewById(R.id.tv_operator);
        billLogo = findViewById(R.id.bill_logo);
        rlPackage = findViewById(R.id.rl_package);
        tvPackage = findViewById(R.id.tv_package);
        viewChannel = findViewById(R.id.viewChannel);
        etName = findViewById(R.id.et_name);
        etLastName = findViewById(R.id.et_last_name);
        rlGender = findViewById(R.id.rl_gender);
        tvGender = findViewById(R.id.tv_gender);
        tvNumberName = findViewById(R.id.tv_number_name);
        etNumber = findViewById(R.id.et_number);
        etAddress = findViewById(R.id.et_address);
        etPincode = findViewById(R.id.et_pincode);
        rlArea = findViewById(R.id.rl_area);
        tvArea = findViewById(R.id.tv_area);
        historyView = findViewById(R.id.historyView);
        viewMore = (AppCompatTextView) findViewById(R.id.viewMore);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btRecharge = (Button) findViewById(R.id.bt_recharge);


        viewMore.setOnClickListener(v -> {
            Intent i = new Intent(DthSubscriptionActivity.this, DthSubscriptionReportActivity.class);
           /* i.putExtra("from", from + "");
            i.putExtra("fromId", fromId);*/
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });

        selectedOperatorView.setOnClickListener(v -> {
            Intent i = new Intent(DthSubscriptionActivity.this, RechargeProviderActivity.class);
            i.putExtra("from", from);
            i.putExtra("fromId", fromId);
            i.putExtra("fromPhoneRecharge", true);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_SELECT_OPERATOR);
        });
        /*ivPhoneBook.setOnClickListener(v -> {
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

        });*/
        rlPackage.setOnClickListener(v -> {
            if (TextUtils.isEmpty(tvOperator.getText())) {
                tvOperator.setError("Please Select Operator First");
                tvOperator.requestFocus();
                return;
            }
            if (mGetDthPackageResponse != null && mGetDthPackageResponse.getDthPackage() != null && mGetDthPackageResponse.getDthPackage().size() > 0) {
                startActivityForResult(new Intent(DthSubscriptionActivity.this, DthPackageActivity.class)
                        .putParcelableArrayListExtra("PACKAGE_DATA_ARRAY", mGetDthPackageResponse.getDthPackage())
                        .putExtra("Title", from + " (" + operatorSelected + ")")
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_PACKAGE);
            } else {
                mCustomAlertDialog.WarningWithDoubleBtnCallBack("Sorry, Package Not Found.", "Retry", true, new CustomAlertDialog.DialogCallBack() {
                    @Override
                    public void onPositiveClick() {
                        getDthPackage();
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });

        viewChannel.setOnClickListener(v ->
                startActivity(new Intent(DthSubscriptionActivity.this, DthChannelActivity.class)
                        .putExtra("DTH_PACKAGE", mIntentDthPackage)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));


        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tvNumberName.getVisibility() == View.VISIBLE && s.length() != 10) {
                    tvNumberName.setVisibility(View.GONE);
                }
            }
        });

        rlGender.setOnClickListener(v -> {
            mDropDownDialog.showDropDownPopup(v, selectedGenderPos, arrayListGender, (clickPosition, value, object) -> {

                        if (selectedGenderPos != clickPosition) {
                            tvGender.setText(value + "");
                            selectedGenderPos = clickPosition;
                        }
                    }
            );
        });

        rlArea.setOnClickListener(v -> {
            clickAreaView(v);
        });
        etPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (dataOfPincode != null && !dataOfPincode.equalsIgnoreCase(s.toString()) && s.toString().trim().length() == 6) {
                    tvArea.setText("");
                    arrayListArea.clear();
                    PincodeArea();
                }
            }
        });

        btRecharge.setOnClickListener(v -> submitDetail());
    }


    void clickAreaView(View v) {
        etPincode.setError(null);
        if (TextUtils.isEmpty(etPincode.getText())) {
            etPincode.setError("Please Enter Valid Area Pincode");
            etPincode.requestFocus();
            return;
        } else if (etPincode.getText().toString().replaceAll(" ", "").length() != 6) {
            etPincode.setError("Please Enter 6 Digit Area Pincode");
            etPincode.requestFocus();
            return;
        } else if (arrayListArea == null || arrayListArea.size() == 0) {

            etPincode.setError("Please Enter Valid Area Pincode");
            etPincode.requestFocus();
            return;
        }

        mDropDownDialog.showDropDownPopup(v, selectedAreaPos, arrayListArea, (clickPosition, value, object) -> {

                    if (selectedAreaPos != clickPosition) {
                        AreaWithPincode mPincodeArea = (AreaWithPincode) object;

                       /* if (mPincodeArea.getReachInHour() == 0) {
                            tvArea.setText(value + "");
                        } else {*/
                        tvArea.setText(value + " (Reach Time - " + Utility.INSTANCE.formatedAmountWithOutRupees(mPincodeArea.getReachInHour() + "") + " Hr)");
                        /* }*/
                        selectedAreaPos = clickPosition;
                        selectedAreaId = mPincodeArea.getId();
                    }
                }
        );
    }

    void submitDetail() {

        if (TextUtils.isEmpty(tvOperator.getText())) {
            tvOperator.setError("Please Select Operator First");
            tvOperator.requestFocus();
            return;
        } else if (TextUtils.isEmpty(tvPackage.getText())) {
            tvPackage.setError("Please Select Package First");
            tvPackage.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etName.getText())) {
            etName.setError("Please Enter Your First Name");
            etName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etLastName.getText())) {
            etLastName.setError("Please Enter Your Last Name");
            etLastName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(tvGender.getText())) {
            tvGender.setError("Please Select Your Gender");
            tvGender.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etNumber.getText())) {

            etNumber.setError("Please Enter Valid Mobile Number");
            etNumber.requestFocus();
            return;
        } else if (etNumber.getText().toString().replaceAll(" ", "").length() != 10) {
            etNumber.setError("Please Enter 10 Digit Mobile Number");
            etNumber.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etPincode.getText())) {
            etPincode.setError("Please Enter Valid Area Pincode");
            etPincode.requestFocus();
            return;
        } else if (etPincode.getText().toString().replaceAll(" ", "").length() != 6) {
            etPincode.setError("Please Enter 6 Digit Area Pincode");
            etPincode.requestFocus();
            return;
        } else if (TextUtils.isEmpty(tvArea.getText())) {
            tvArea.setError("Please Select Your Area");
            tvArea.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etAddress.getText())) {
            etAddress.setError("Please Enter Your Address");
            etAddress.requestFocus();
            return;
        }


        ConfirmDialog(null);
    }


    void ConfirmDialog(CommissionDisplay mCommissionDisplay) {

        if (ApiFintechUtilMethods.INSTANCE.getLattitude != 0 && ApiFintechUtilMethods.INSTANCE.getLongitude != 0) {
            DthSubscription(ApiFintechUtilMethods.INSTANCE.getLattitude, ApiFintechUtilMethods.INSTANCE.getLongitude, mCommissionDisplay);
        } else {
            if (mGetLocation != null) {
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    ApiFintechUtilMethods.INSTANCE.getLattitude = lattitude;
                    ApiFintechUtilMethods.INSTANCE.getLongitude = longitude;
                    DthSubscription(lattitude, longitude, mCommissionDisplay);
                });
            } else {
                mGetLocation = new GetLocation(DthSubscriptionActivity.this, loader);
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    ApiFintechUtilMethods.INSTANCE.getLattitude = lattitude;
                    ApiFintechUtilMethods.INSTANCE.getLongitude = longitude;
                    DthSubscription(lattitude, longitude, mCommissionDisplay);
                });
            }
        }


    }

    private void DthSubscription(double lattitude, double longitude, CommissionDisplay mCommissionDisplay) {
        String fullAddress = etAddress.getText().toString().contains(etPincode.getText().toString()) ? etAddress.getText().toString() : etAddress.getText().toString() + " - " + etPincode.getText().toString();
        ApiFintechUtilMethods.INSTANCE.dthSubscriptionConfiormDialog(this, mCommissionDisplay, false,
                mLoginDataResponse.getData().getIsDoubleFactor(), ApplicationConstant.INSTANCE.baseIconUrl + icon,
                etNumber.getText().toString(), operatorSelected, Utility.INSTANCE.formatedAmountWithRupees(mIntentDthPackage.getPackageMRP() + ""),
                tvPackage.getText().toString(), etName.getText().toString(),
                fullAddress, new ApiFintechUtilMethods.DialogCallBack(){
                    @Override
                    public void onPositiveClick(String pinPass) {
                        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(DthSubscriptionActivity.this)) {
                            loader.show();
                            loader.setCancelable(false);
                            loader.setCanceledOnTouchOutside(false);

                            ApiFintechUtilMethods.INSTANCE.DTHSubscription(DthSubscriptionActivity.this, BBPS, false, operatorSelectedId,
                                    mIntentDthPackage != null ? mIntentDthPackage.getId() : 0,
                                    etLastName.getText().toString(), tvGender.getText().toString(), selectedAreaId,
                                    operatorSelected, etName.getText().toString(), etAddress.getText().toString(),
                                    etPincode.getText().toString().trim(), etNumber.getText().toString().trim(),
                                    lattitude + "," + longitude, pinPass, mIntentDthPackage, mLoginDataResponse,
                                    loader, mAppPreferences, deviceId, deviceSerialNum, object -> {

                                    });
                        } else {

                            ApiFintechUtilMethods.INSTANCE.NetworkError(DthSubscriptionActivity.this);
                        }
                    }

                    @Override
                    public void onResetCallback(String pinPass, double requestedAmt) {
                        inputPinPass = pinPass;
                        Intent i = new Intent(DthSubscriptionActivity.this, AddMoneyActivity.class);
                        i.putExtra("IsFromRecharge", true);
                        /*i.putExtra("AMOUNT", (requestedAmt % 1) == 0?(int)(requestedAmt):(int)(requestedAmt+1));*/
                        i.putExtra("AMOUNT", requestedAmt);
                        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivityForResult(i, INTENT_ADD_MONEY);

                    }

                    @Override
                    public void onCancelClick() {

                    }
                });
    }

    public void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            ApiFintechUtilMethods.INSTANCE.DthSubscriptionReport(this, fromId + "", "20", 0, 0, fromDateStr, toDateStr,
                    "", "", "", "false", true, loader,
                    mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            DthSubscriptionReportResponse mRechargeReportResponse = (DthSubscriptionReportResponse) object;
                            dataParse(mRechargeReportResponse);
                        }

                        @Override
                        public void onError(int error) {

                        }
                    });


        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }


    public void dataParse(DthSubscriptionReportResponse response) {

        ArrayList<DthSubscriptionReport> transactionsObjects = response.getDTHSubscriptionReport();
        if (transactionsObjects != null && transactionsObjects.size() > 0) {
            DthSubscriptionReportAdapter mAdapter = new DthSubscriptionReportAdapter(transactionsObjects, this, requestOptionsAdapter,
                    mLoginDataResponse, mCustomAlertDialog, loader, deviceId, deviceSerialNum);
            recyclerView.setAdapter(mAdapter);
            historyView.setVisibility(View.VISIBLE);
        } else {
            historyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        /*if (reqCode == PICK_CONTACT && resultCode == RESULT_OK) {
            Uri contactData = data.getData();
            Cursor c = this.managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {
                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if (hasPhone.equalsIgnoreCase("1")) {
                    Cursor phones = this.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                            null, null);
                    phones.moveToFirst();
                    // For Clear Old Value Of Number...
                    etNumber.setText("");
                    // <------------------------------------------------------------------>
                    if (phones != null && phones.moveToFirst()) {
                        number = phones.getString(phones.getColumnIndex("data1"));

                        phones.close();
                    }
                }
                String Name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (!number.equals("")) {
                    if (!Name.equals("")) {
                        tvNumberName.setText(Name);
                        tvNumberName.setVisibility(View.VISIBLE);
                    } else {
                        tvNumberName.setVisibility(View.VISIBLE);
                    }
                    //Set the Number Without space and +91..
                    SetNumber(number);
                } else {
                    Toast.makeText(this, "Please select a valid number", Toast.LENGTH_SHORT).show();
                }

            }
        } else if (reqCode == INTENT_PERMISSION && resultCode == RESULT_OK) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, PICK_CONTACT);
        } else*/
        if (reqCode == INTENT_PACKAGE && resultCode == RESULT_OK) {
            mIntentDthPackage = (DthPackage) data.getParcelableExtra("Package");
            if (mIntentDthPackage != null) {
                tvPackage.setText(mIntentDthPackage.getPackageName() + "");
                viewChannel.setVisibility(View.VISIBLE);
            }

        } else if (reqCode == INTENT_SELECT_OPERATOR && resultCode == RESULT_OK) {
            OperatorList mOperatorList =  data.getParcelableExtra("SelectedOperator");
            if (mOperatorList != null) {
                setIntentData(mOperatorList);
            }
        }
    }

    void setIntentData(OperatorList mOperatorList) {
        operatorSelected = mOperatorList.getName();
        operatorSelectedId = mOperatorList.getOid();
        minAmountLength = mOperatorList.getMin();
        maxAmountLength = mOperatorList.getMax();
        minNumberLength = mOperatorList.getLength();
        maxNumberLength = mOperatorList.getLengthMax();
        isAcountNumeric = mOperatorList.getIsAccountNumeric();
        //isPartial = mOperatorList.getIsPartial();
        isBBPS = mOperatorList.getBBPS();
        isBilling = mOperatorList.getIsBilling();
        AccountName = mOperatorList.getAccountName();
        Account_Remark = mOperatorList.getAccountRemak();
        StartWith = mOperatorList.getStartWith();
        icon = mOperatorList.getImage();

        if (StartWith != null && StartWith.length() > 0 && StartWith.contains(",")) {
            StartWithArray = new ArrayList<>(Arrays.asList(StartWith.trim().split(",")));
        } else if (StartWith != null && !StartWith.isEmpty() && !StartWith.equalsIgnoreCase("0")) {
            StartWithArray.add(StartWith);
        }

        tvOperator.setText(operatorSelected + "");
        tvOperator.setError(null);
        if (icon != null && !icon.isEmpty()) {
            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + icon)
                    .apply(requestOptions)
                    .into(ivOprator);
        } else {
            ivOprator.setImageResource(R.drawable.ic_tower);
        }

        if (isAcountNumeric == true) {
            etNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            etNumber.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        if (maxNumberLength != 0 && maxNumberLength > 0) {
            InputFilter[] filterArray = new InputFilter[1];
            filterArray[0] = new InputFilter.LengthFilter(maxNumberLength);
            etNumber.setFilters(filterArray);
        }


        if (!AccountName.equals("")) {
            etNumber.setHint(AccountName + "");
        }


        if (isBBPS) {
            billLogo.setVisibility(View.VISIBLE);
        } else {
            billLogo.setVisibility(View.GONE);
        }
        getDthPackage();

    }

    public void SetNumber(final String Number) {
        String Number1 = Number.replace("+91", "");
        String Number2 = Number1.replace(" ", "");
        String Number3 = Number2.replace("(", "");
        String Number4 = Number3.replace(")", "");
        String Number5 = Number4.replace("_", "");
        String Number6 = Number5.replace("-", "");
        etNumber.setText(Number6);
    }


    void getDthPackage() {
        ApiFintechUtilMethods.INSTANCE.GetDthPackage(DthSubscriptionActivity.this, operatorSelectedId + "", loader, mLoginDataResponse,
                deviceId, deviceSerialNum, object -> {
                    mGetDthPackageResponse = (GetDthPackageResponse) object;
                });
    }

    void PincodeArea() {
        loader.show();
        ApiFintechUtilMethods.INSTANCE.PincodeArea(DthSubscriptionActivity.this, etPincode.getText().toString().trim(), loader,
                mLoginDataResponse, deviceId, deviceSerialNum, object -> {
                    AreaWithPincodeResponse mPincodeAreaResponse = (AreaWithPincodeResponse) object;
                    dataOfPincode = etPincode.getText().toString();

                    for (AreaWithPincode mPincodeArea : mPincodeAreaResponse.getAreas()) {
                        arrayListArea.add(new DropDownModel(mPincodeArea.getArea() + "", mPincodeArea));
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
