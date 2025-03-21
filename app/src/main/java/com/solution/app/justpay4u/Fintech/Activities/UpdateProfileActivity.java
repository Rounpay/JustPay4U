package com.solution.app.justpay4u.Fintech.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dreamseller.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.solution.app.justpay4u.Api.Fintech.Object.EditUser;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UpdateKycStatusRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UpdateUserRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.UploadDocRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetUserResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.UpdateKycResponse;
import com.solution.app.justpay4u.Api.Networking.Request.AadhaarOtpReq;
import com.solution.app.justpay4u.Api.Networking.Request.RequestAadhaarOTP;
import com.solution.app.justpay4u.Api.Networking.Request.ValidatePANAppRequest;
import com.solution.app.justpay4u.Api.Networking.Response.ResponseAadhaarOTP;
import com.solution.app.justpay4u.Api.Networking.Response.ValidateAadhaarOTPResponse;
import com.solution.app.justpay4u.Api.Networking.Response.ValidatePANAppResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.Adapter.UploadDocsAdapter;
import com.solution.app.justpay4u.Fintech.Authentication.ChangePinPassActivity;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.BankListScreenActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.TooltipPopup.BubbleDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {

    EditText branchNameEt;
    DropDownDialog mDropDownDialog;
    GetUserResponse mGetUserResponse;
    AppCompatButton kycUploadDocBtn;
    private EditText nameEt;
    private EditText outletNameEt;
    private View shopTypeChooserView;
    private TextView shopTypeTv;
    private EditText MobileNumberEt;
    private EditText AlternateMobileNumberEt;
    private EditText emailIdEt;
    private TextView tvDob;
    private AppCompatImageView calenderIcon;
    private View qualificationChooserView;
    private TextView qualificationTv;
    private EditText pincodeEt;
    private View locationTypeChooserView;
    private TextView locationTypeTv;
    private EditText LandmarkEt;
    private EditText addressEt;
    private View populationChooserView, dobView;
    private TextView populationTv;
    private EditText gstinEt;
    private EditText aadharEt;
    private EditText panEt;
    private View bankChooserView;
    private TextView bankTv;
    private AppCompatImageView bankArrow;
    private EditText IFSCEt;
    private EditText AccountNumberEt;
    private EditText AccountNameEt;
    private AppCompatTextView notice;
    private Button submitBtn;
    private Button kycBtn;
    private CustomLoader loader;
    private ImagePicker imagePicker;
    private int docTypeId, uid;
    private AlertDialog alertDialog;
    private RecyclerView recyclerViewDocs;
    private UpdateKycResponse mUpdateKycResponse;
    private ArrayList<String> arrayQualification;
    private ArrayList<String> arrayPoupulation;
    private ArrayList<String> arrayShopType;
    private ArrayList<String> arrayLocationType;
    private String selectedQualification = "SSC", selectedPopulation = "0 to 2000", selectedLocation = "Rural",
            selectedShopType = "Kirana Shop", selectedBank = "", ifsc = "";
    private int selectedQualificationPos = 0, selectedPopulationPos = 0, selectedLocationPos = 0, selectedShopTypePos = 0;
    private LoginResponse mLoginDataResponse;
    private int INTENT_SELECT_BANK = 8904;
    private AppPreferences mAppPreferences;
    private Gson gson;
    private String deviceId, deviceSerialNum;
    private int INTENT_PERMISSION_IMAGE = 587;
    private AlertDialog alertDialogImageType;
    private int INTENT_MULTI_IMAGE = 5100;
    private CustomAlertDialog mCustomPassDialog;
    private int INTENT_PASSWORD_EXPIRE = 6201;

    private TextView aadhaarV, panV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_update_profile);

            gson = new Gson();
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            mDropDownDialog = new DropDownDialog(this);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            findViews();
            onViewClick();

            new Handler(Looper.getMainLooper()).post(() -> {
                mCustomPassDialog = new CustomAlertDialog(this);
                mGetUserResponse = getIntent().getParcelableExtra("UserData");
                setUserData();

                //showKycButton();
            });
        });

    }

    private void findViews() {

        imagePicker = new ImagePicker(this, null, imageUri -> {
            Uri imgUri = imageUri;
            uploadDocApi(new File(imgUri.getPath()));
        }).setWithImageCrop();
        nameEt = findViewById(R.id.nameEt);
        branchNameEt = findViewById(R.id.branchNameEt);
        outletNameEt = findViewById(R.id.outletNameEt);
        shopTypeChooserView = findViewById(R.id.shopTypeChooserView);
        shopTypeTv = findViewById(R.id.shopTypeTv);
        MobileNumberEt = findViewById(R.id.MobileNumberEt);
        AlternateMobileNumberEt = findViewById(R.id.AlternateMobileNumberEt);
        emailIdEt = findViewById(R.id.emailIdEt);
        dobView = findViewById(R.id.dobView);
        tvDob = findViewById(R.id.tv_dob);
        calenderIcon = findViewById(R.id.calenderIcon);
        qualificationChooserView = findViewById(R.id.qualificationChooserView);
        qualificationTv = findViewById(R.id.qualificationTv);
        pincodeEt = findViewById(R.id.pincodeEt);
        locationTypeChooserView = findViewById(R.id.locationTypeChooserView);
        locationTypeTv = findViewById(R.id.locationTypeTv);
        LandmarkEt = findViewById(R.id.LandmarkEt);
        addressEt = findViewById(R.id.addressEt);
        populationChooserView = findViewById(R.id.populationChooserView);
        populationTv = findViewById(R.id.populationTv);
        gstinEt = findViewById(R.id.gstinEt);
        aadharEt = findViewById(R.id.aadharEt);
        panEt = findViewById(R.id.panEt);
        bankChooserView = findViewById(R.id.bankChooserView);
        bankTv = findViewById(R.id.bankTv);
        bankArrow = findViewById(R.id.bankArrow);
        IFSCEt = findViewById(R.id.IFSCEt);
        AccountNumberEt = findViewById(R.id.AccountNumberEt);
        AccountNameEt = findViewById(R.id.AccountNameEt);
        notice = findViewById(R.id.notice);
        submitBtn = findViewById(R.id.submitBtn);
        kycBtn = findViewById(R.id.kycBtn);
        aadhaarV = findViewById(R.id.verifyAadhaar);
        panV = findViewById(R.id.verifyPan);
    }

    void onViewClick() {
        /*arrayQualification = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Qualification)));
        arrayPoupulation = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Poupulation)));
        arrayShopType = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.ShopType)));
        arrayLocationType = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.LocationType)));


        qualificationChooserView.setOnClickListener(v ->
                mDropDownDialog.showDropDownPopup(v, selectedQualificationPos, arrayQualification, (clickPosition, value, object) -> {
                    selectedQualificationPos = clickPosition;
                    if (clickPosition == 0) {
                        selectedQualification = "";
                        qualificationTv.setText("");
                    } else {
                        selectedQualification = value;
                        qualificationTv.setText(value + "");
                    }

                }));


        populationChooserView.setOnClickListener(v ->
                mDropDownDialog.showDropDownPopup(v, selectedPopulationPos, arrayPoupulation, (clickPosition, value, object) -> {
                    selectedPopulationPos = clickPosition;
                    if (clickPosition == 0) {
                        selectedPopulation = "";
                        populationTv.setText("");
                    } else {
                        selectedPopulation = value;
                        populationTv.setText(value + "");
                    }
                }));


        shopTypeChooserView.setOnClickListener(v ->
                mDropDownDialog.showDropDownPopup(v, selectedShopTypePos, arrayShopType, (clickPosition, value, object) -> {
                    selectedShopTypePos = clickPosition;
                    if (clickPosition == 0) {
                        selectedShopType = "";
                        shopTypeTv.setText("");
                    } else {
                        selectedShopType = value;
                        shopTypeTv.setText(value + "");
                    }
                }));


        locationTypeChooserView.setOnClickListener(v ->
                mDropDownDialog.showDropDownPopup(v, selectedLocationPos, arrayLocationType, (clickPosition, value, object) -> {
                    selectedLocationPos = clickPosition;
                    if (clickPosition == 0) {
                        selectedLocation = "";
                        locationTypeTv.setText("");
                    } else {
                        selectedLocation = value;
                        locationTypeTv.setText(value + "");
                    }
                }));*/


        final Calendar myCalendar = Calendar.getInstance();

        dobView.setOnClickListener(v -> {
            /*R.style.Theme_AppCompat_Light_Dialog_Alert,*/
            DatePickerDialog mDatePicker = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd MMM yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tvDob.setText(sdf.format(myCalendar.getTime()));
            }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
            mDatePicker.show();
        });

        /*findViewById(R.id.iv_contact).setOnClickListener(v -> {
            openPhoneBook(INTENT_PICK_CONTACT, INTENT_PRERMISSION);
        });

        findViewById(R.id.iv_alternate_contact).setOnClickListener(v -> {

            openPhoneBook(INTENT_PICK_CONTACT_ALTERNATE, INTENT_PRERMISSION_ALTERNATE);
        });*/

        bankChooserView.setOnClickListener(v -> {
            Intent bankIntent = new Intent(this, BankListScreenActivity.class);
            bankIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(bankIntent, INTENT_SELECT_BANK);
        });
        submitBtn.setOnClickListener(v -> updateProfile());

        kycBtn.setOnClickListener(v -> updateKyc());

        aadhaarV.setOnClickListener(view -> {
            if (!aadharEt.getText().toString().isEmpty()) {
                VerifyAadhaarCard(this, aadharEt.getText().toString());
            } else {
                aadharEt.setError("Please enter valid Aadhaar Number");
                aadharEt.requestFocus();
            }
        });
        panV.setOnClickListener(v -> {
            if (!panEt.getText().toString().isEmpty()) {
                VerifyPanCard(this, panEt.getText().toString(), loader);
            } else {
                panEt.setError("Please enter valid Pan Number");
                panEt.requestFocus();
            }
        });
    }



    /*void openPhoneBook(int intentRequest, int inentPermisssion) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(this, PermissionActivity.class),
                        inentPermisssion);

            } else {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, intentRequest);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, intentRequest);
        }
    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("photopath", ImagePicker.currentCameraFileName);
        outState.putInt("docTypeId", docTypeId);
        outState.putInt("uID", uid);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("photopath") && ImagePicker.currentCameraFileName.length() < 5 && docTypeId == 0 && uid == 0) {
                ImagePicker.currentCameraFileName = savedInstanceState.getString("photopath");
                docTypeId = savedInstanceState.getInt("docTypeId");
                uid = savedInstanceState.getInt("uID");
            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }


    void setUserData() {
        if (mGetUserResponse != null && mGetUserResponse.getUserInfo() != null) {
            if (mGetUserResponse.getUserInfo().getKycStatus() == 2 || mGetUserResponse.getUserInfo().getKycStatus() == 3) {
                kycBtn.setText("View KYC");
                kycBtn.setVisibility(View.VISIBLE);
                submitBtn.setVisibility(View.GONE);
            } else {
                kycBtn.setText("Update KYC");
                kycBtn.setVisibility(View.GONE);
                submitBtn.setVisibility(View.VISIBLE);
            }
            if (mGetUserResponse.getUserInfo().getName() != null && !mGetUserResponse.getUserInfo().getName().isEmpty()) {
                if (mGetUserResponse.getUserInfo().getEkycid() > 0) {
                    nameEt.setFocusable(false);
                    nameEt.setLongClickable(false);
                    nameEt.setClickable(false);
                    ViewCompat.setBackgroundTintList(nameEt, ContextCompat.getColorStateList(this, R.color.blur_color));
                }
                nameEt.setText(mGetUserResponse.getUserInfo().getName().trim());

                //remove if shop name available
                outletNameEt.setText(mGetUserResponse.getUserInfo().getName().trim());
            }
           /* if (mGetUserResponse.getUserInfo().getOutletName() != null && !mGetUserResponse.getUserInfo().getOutletName().isEmpty()) {
                if (mGetUserResponse.getUserInfo().getEkycid() > 0) {
                    outletNameEt.setFocusable(false);
                    outletNameEt.setLongClickable(false);
                    outletNameEt.setClickable(false);
                    ViewCompat.setBackgroundTintList(outletNameEt,ContextCompat.getColorStateList(this,R.color.blur_color));
                }
                outletNameEt.setText(mGetUserResponse.getUserInfo().getOutletName().trim());
            }*/
            if (mGetUserResponse.getUserInfo().getEmailID() != null && !mGetUserResponse.getUserInfo().getEmailID().isEmpty()) {
                emailIdEt.setText(mGetUserResponse.getUserInfo().getEmailID().trim());
            }
            if (mGetUserResponse.getUserInfo().getAddress() != null && !mGetUserResponse.getUserInfo().getAddress().isEmpty()) {
                if (mGetUserResponse.getUserInfo().getEkycid() > 0) {
                    addressEt.setFocusable(false);
                    addressEt.setLongClickable(false);
                    addressEt.setClickable(false);
                    ViewCompat.setBackgroundTintList(addressEt, ContextCompat.getColorStateList(this, R.color.blur_color));
                }
                addressEt.setText(mGetUserResponse.getUserInfo().getAddress().trim());
            }
            if (mGetUserResponse.getUserInfo().getPincode() != null && !mGetUserResponse.getUserInfo().getPincode().isEmpty()) {
                if (mGetUserResponse.getUserInfo().getEkycid() > 0) {
                    pincodeEt.setFocusable(false);
                    pincodeEt.setLongClickable(false);
                    pincodeEt.setClickable(false);
                    ViewCompat.setBackgroundTintList(pincodeEt, ContextCompat.getColorStateList(this, R.color.blur_color));
                }
                pincodeEt.setText(mGetUserResponse.getUserInfo().getPincode().trim());
            }

            if (mGetUserResponse.getUserInfo().getGstin() != null && !mGetUserResponse.getUserInfo().getGstin().isEmpty()) {
                if (mGetUserResponse.getUserInfo().getEkycid() > 0) {
                    gstinEt.setFocusable(false);
                    gstinEt.setLongClickable(false);
                    gstinEt.setClickable(false);
                    ViewCompat.setBackgroundTintList(gstinEt, ContextCompat.getColorStateList(this, R.color.blur_color));
                }
                gstinEt.setText(mGetUserResponse.getUserInfo().getGstin().trim());
            }
            //aadhaar and pan
            if (mGetUserResponse.isVerifyAadharFromProfile() || mGetUserResponse.getUserInfo().isAadharVerified()) {
                aadhaarV.setVisibility(View.VISIBLE);
            } else {
                aadhaarV.setVisibility(View.GONE);
            }
            if (mGetUserResponse.isVerifyPANFromProfile() || mGetUserResponse.getUserInfo().isPANVerified()) {
                panV.setVisibility(View.VISIBLE);
            } else {
                panV.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getAadhar() != null && !mGetUserResponse.getUserInfo().getAadhar().isEmpty()) {
                if (mGetUserResponse.getUserInfo().getEkycid() > 0) {
                    aadharEt.setFocusable(false);
                    aadharEt.setLongClickable(false);
                    aadharEt.setClickable(false);
                    ViewCompat.setBackgroundTintList(aadharEt, ContextCompat.getColorStateList(this, R.color.blur_color));
                }
                aadharEt.setText(mGetUserResponse.getUserInfo().getAadhar().trim());
            }
            if (mGetUserResponse.getUserInfo().getPan() != null && !mGetUserResponse.getUserInfo().getPan().isEmpty()) {
                if (mGetUserResponse.getUserInfo().getEkycid() > 0) {
                    panEt.setFocusable(false);
                    panEt.setLongClickable(false);
                    panEt.setClickable(false);
                    ViewCompat.setBackgroundTintList(panEt, ContextCompat.getColorStateList(this, R.color.blur_color));
                }
                panEt.setText(mGetUserResponse.getUserInfo().getPan().trim());
            }
            if (mGetUserResponse.getUserInfo().getMobileNo() != null && !mGetUserResponse.getUserInfo().getMobileNo().isEmpty()) {
                MobileNumberEt.setFocusable(false);
                MobileNumberEt.setLongClickable(false);
                MobileNumberEt.setClickable(false);
                ViewCompat.setBackgroundTintList(MobileNumberEt, ContextCompat.getColorStateList(this, R.color.blur_color));
                MobileNumberEt.setText(mGetUserResponse.getUserInfo().getMobileNo().trim());
            }
            if (mGetUserResponse.getUserInfo().getAlternateMobile() != null && !mGetUserResponse.getUserInfo().getAlternateMobile().isEmpty()) {
                AlternateMobileNumberEt.setText(mGetUserResponse.getUserInfo().getAlternateMobile().trim());
            }

            if (mGetUserResponse.getUserInfo().getDob() != null && !mGetUserResponse.getUserInfo().getDob().isEmpty()) {
                if (mGetUserResponse.getUserInfo().getEkycid() > 0) {
                    calenderIcon.setVisibility(View.GONE);
                    dobView.setClickable(false);
                    ViewCompat.setBackgroundTintList(tvDob, ContextCompat.getColorStateList(this, R.color.blur_color));
                    /*dobView.setCardBackgroundColor(getResources().getColor(R.color.blur_color));*/
                }
                tvDob.setText(mGetUserResponse.getUserInfo().getDob().trim());
            }

            if (mGetUserResponse.getUserInfo().getIfsc() != null && !mGetUserResponse.getUserInfo().getIfsc().isEmpty()) {
                if (mGetUserResponse.getUserInfo().getEkycid() > 0) {
                    IFSCEt.setFocusable(false);
                    IFSCEt.setLongClickable(false);
                    IFSCEt.setClickable(false);
                    ViewCompat.setBackgroundTintList(IFSCEt, ContextCompat.getColorStateList(this, R.color.blur_color));
                }
                IFSCEt.setText(mGetUserResponse.getUserInfo().getIfsc().trim());
            }
            if (mGetUserResponse.getUserInfo().getBranchName() != null && !mGetUserResponse.getUserInfo().getBranchName().isEmpty()) {
                branchNameEt.setText(mGetUserResponse.getUserInfo().getBranchName().trim());
            }

            if (mGetUserResponse.getUserInfo().getLandmark() != null && !mGetUserResponse.getUserInfo().getLandmark().isEmpty()) {
                LandmarkEt.setText(mGetUserResponse.getUserInfo().getLandmark().trim());
            }

            if (mGetUserResponse.getUserInfo().getAccountNumber() != null && !mGetUserResponse.getUserInfo().getAccountNumber().isEmpty()) {
                if (mGetUserResponse.getUserInfo().getEkycid() > 0) {
                    AccountNumberEt.setFocusable(false);
                    AccountNumberEt.setLongClickable(false);
                    AccountNumberEt.setClickable(false);
                    ViewCompat.setBackgroundTintList(AccountNumberEt, ContextCompat.getColorStateList(this, R.color.blur_color));
                }
                AccountNumberEt.setText(mGetUserResponse.getUserInfo().getAccountNumber().trim());
            }
            if (mGetUserResponse.getUserInfo().getAccountName() != null && !mGetUserResponse.getUserInfo().getAccountName().isEmpty()) {
                if (mGetUserResponse.getUserInfo().getEkycid() > 0) {
                    AccountNameEt.setFocusable(false);
                    AccountNameEt.setLongClickable(false);
                    AccountNameEt.setClickable(false);
                    ViewCompat.setBackgroundTintList(AccountNameEt, ContextCompat.getColorStateList(this, R.color.blur_color));
                }
                AccountNameEt.setText(mGetUserResponse.getUserInfo().getAccountName().trim());
            }


           /* if (mGetUserResponse.getUserInfo().getQualification() != null && !mGetUserResponse.getUserInfo().getQualification().isEmpty()) {
                selectedQualification = mGetUserResponse.getUserInfo().getQualification() + "";
                qualificationTv.setText(selectedQualification);
                if (arrayQualification.contains(selectedQualification)) {
                    selectedQualificationPos = arrayQualification.indexOf(selectedQualification);
                }
            }*/


            /*---------setting Population Value---------*/

           /* if (mGetUserResponse.getUserInfo().getPoupulation() != null && !mGetUserResponse.getUserInfo().getPoupulation().isEmpty()) {
                selectedPopulation = mGetUserResponse.getUserInfo().getPoupulation() + "";
                populationTv.setText(selectedPopulation);
                if (arrayPoupulation.contains(selectedPopulation)) {
                    selectedPopulationPos = arrayPoupulation.indexOf(selectedPopulation);
                }
            }*/

            /*-------------setting Location Value---------*/

            /*if (mGetUserResponse.getUserInfo().getLocationType() != null && !mGetUserResponse.getUserInfo().getLocationType().isEmpty()) {
                selectedLocation = mGetUserResponse.getUserInfo().getLocationType() + "";
                locationTypeTv.setText(selectedLocation);
                if (arrayLocationType.contains(selectedLocation)) {
                    selectedLocationPos = arrayLocationType.indexOf(selectedLocation);
                }
            }*/

            /*------------setting ShopType Value---------*/

            /*if (mGetUserResponse.getUserInfo().getShoptype() != null && !mGetUserResponse.getUserInfo().getShoptype().isEmpty()) {
                selectedShopType = mGetUserResponse.getUserInfo().getShoptype() + "";
                shopTypeTv.setText(selectedShopType);
                if (arrayShopType.contains(selectedShopType)) {
                    selectedShopTypePos = arrayShopType.indexOf(selectedShopType);
                }
            }*/



            /*------------setting Bank Value---------*/

            if (mGetUserResponse.getUserInfo().getBankName() != null && !mGetUserResponse.getUserInfo().getBankName().isEmpty()) {
                if (mGetUserResponse.getUserInfo().getEkycid() > 0) {
                    bankArrow.setVisibility(View.GONE);
                    bankChooserView.setClickable(false);
                    ViewCompat.setBackgroundTintList(bankTv, ContextCompat.getColorStateList(this, R.color.blur_color));

                    //  bankChooserView.setCardBackgroundColor(getResources().getColor(R.color.blur_color));
                }
                selectedBank = mGetUserResponse.getUserInfo().getBankName();
                bankTv.setText(selectedBank);

            }
        } else {
            mGetUserResponse = ApiFintechUtilMethods.INSTANCE.getUserDetailResponse(mAppPreferences);
            if (mGetUserResponse != null && mGetUserResponse.getUserInfo() != null) {
                setUserData();
            } else {
                getUserDetail();
            }
        }
    }

    void showKycButton() {
        if (!nameEt.getText().toString().isEmpty() && !outletNameEt.getText().toString().isEmpty() &&
                !MobileNumberEt.getText().toString().trim().isEmpty() && MobileNumberEt.getText().toString().trim().length() == 10 &&
                !emailIdEt.getText().toString().isEmpty() && emailIdEt.getText().toString().contains("@") && emailIdEt.getText().toString().contains(".") &&
                !pincodeEt.getText().toString().isEmpty() && pincodeEt.getText().toString().length() >= 6 &&
                !addressEt.getText().toString().isEmpty() &&
                !aadharEt.getText().toString().isEmpty() && aadharEt.getText().toString().length() == 12 &&
                !panEt.getText().toString().isEmpty() && panEt.getText().toString().length() == 10 &&
                selectedBank != null && selectedBank.length() > 0 &&
                !selectedBank.equalsIgnoreCase("Select Bank") && !IFSCEt.getText().toString().isEmpty() &&
                !AccountNumberEt.getText().toString().isEmpty() && !AccountNameEt.getText().toString().isEmpty()) {

            //  kycBtn.setVisibility(View.VISIBLE);
            notice.setVisibility(View.GONE);
        } else {
            // kycBtn.setVisibility(View.GONE);
            notice.setVisibility(View.VISIBLE);
        }
    }

    void updateProfile() {
        if (nameEt.getText().toString().isEmpty()) {
            nameEt.setError(getString(R.string.err_empty_field));
            nameEt.requestFocus();
        } else if (outletNameEt.getText().toString().isEmpty()) {
            outletNameEt.setError(getString(R.string.err_empty_field));
            outletNameEt.requestFocus();
        } else if (shopTypeTv.getText().toString().isEmpty()) {
            shopTypeTv.setError("Please Select Shop Type");
            shopTypeTv.requestFocus();
        } else if (MobileNumberEt.getText().toString().isEmpty()) {
            MobileNumberEt.setError(getString(R.string.err_empty_field));
            MobileNumberEt.requestFocus();
        } else if (MobileNumberEt.getText().toString().length() != 10) {
            MobileNumberEt.setError(getString(R.string.err_msg_valid_mobile));
            MobileNumberEt.requestFocus();
        } else if (AlternateMobileNumberEt.getText().toString().isEmpty()) {
            AlternateMobileNumberEt.setError(getString(R.string.err_empty_field));
            AlternateMobileNumberEt.requestFocus();
        } else if (AlternateMobileNumberEt.getText().toString().length() != 10) {
            AlternateMobileNumberEt.setError(getString(R.string.err_msg_valid_mobile));
            AlternateMobileNumberEt.requestFocus();
        } else if (emailIdEt.getText().toString().isEmpty()) {
            emailIdEt.setError(getString(R.string.err_empty_field));
            emailIdEt.requestFocus();
        } else if (!emailIdEt.getText().toString().contains("@") || !emailIdEt.getText().toString().contains(".")) {
            emailIdEt.setError(getString(R.string.err_msg_email));
            emailIdEt.requestFocus();
        } else if (tvDob.getText().toString().isEmpty()) {
            tvDob.setError("Please choose Date of Birth");
            tvDob.requestFocus();
        } else if (qualificationTv.getText().toString().isEmpty()) {
            qualificationTv.setError("Please Select Qualification");
            qualificationTv.requestFocus();
        } else if (pincodeEt.getText().toString().isEmpty()) {
            pincodeEt.setError(getString(R.string.err_empty_field));
            pincodeEt.requestFocus();
        } else if (pincodeEt.getText().toString().length() < 6) {
            pincodeEt.setError(getString(R.string.pincode_error));
            pincodeEt.requestFocus();
        } else if (locationTypeTv.getText().toString().isEmpty()) {
            locationTypeTv.setError("Please Select Location Type");
            locationTypeTv.requestFocus();
        } else if (LandmarkEt.getText().toString().isEmpty()) {
            LandmarkEt.setError(getString(R.string.err_empty_field));
            LandmarkEt.requestFocus();
        } else if (addressEt.getText().toString().isEmpty()) {
            addressEt.setError(getString(R.string.err_empty_field));
            addressEt.requestFocus();
        } else if (populationTv.getText().toString().isEmpty()) {
            populationTv.setError("Please Select Poupulation");
            populationTv.requestFocus();
        } /*else if (gstinEt.getText().toString().isEmpty()) {
            gstinEt.setError(getString(R.string.err_empty_field));
            gstinEt.requestFocus();
        }*/ else if (aadharEt.getText().toString().isEmpty()) {
            aadharEt.setError(getString(R.string.err_empty_field));
            aadharEt.requestFocus();
        } else if (aadharEt.getText().toString().length() < 12) {
            aadharEt.setError(getString(R.string.invalid_aadhar));
            aadharEt.requestFocus();
        } else if (panEt.getText().toString().isEmpty()) {
            panEt.setError(getString(R.string.err_empty_field));
            panEt.requestFocus();
        } else if (panEt.getText().toString().length() < 10) {
            panEt.setError(getString(R.string.invalid_pan));
            panEt.requestFocus();
        } else if (bankTv.getText().toString().isEmpty()) {
            bankTv.setError("Please Select Bank");
            bankTv.requestFocus();
        } else if (branchNameEt.getText().toString().isEmpty()) {
            branchNameEt.setError(getString(R.string.err_empty_field));
            branchNameEt.requestFocus();
        } else if (IFSCEt.getText().toString().isEmpty()) {
            IFSCEt.setError(getString(R.string.err_empty_field));
            IFSCEt.requestFocus();
        } else if (AccountNumberEt.getText().toString().isEmpty()) {
            AccountNumberEt.setError(getString(R.string.err_empty_field));
            AccountNumberEt.requestFocus();
        } else if (AccountNameEt.getText().toString().isEmpty()) {
            AccountNameEt.setError(getString(R.string.err_empty_field));
            AccountNameEt.requestFocus();
        } else {
            updateProfileApi();
        }
    }


    void updateKyc() {
        if (nameEt.getText().toString().isEmpty()) {
            nameEt.setError(getString(R.string.err_empty_field));
            nameEt.requestFocus();
        } else if (outletNameEt.getText().toString().isEmpty()) {
            outletNameEt.setError(getString(R.string.err_empty_field));
            outletNameEt.requestFocus();
        } else if (shopTypeTv.getText().toString().isEmpty()) {
            shopTypeTv.setError("Please Select Shop Type");
            shopTypeTv.requestFocus();
        } else if (AlternateMobileNumberEt.getText().toString().isEmpty()) {
            AlternateMobileNumberEt.setError(getString(R.string.err_empty_field));
            AlternateMobileNumberEt.requestFocus();
        } else if (AlternateMobileNumberEt.getText().toString().length() != 10) {
            AlternateMobileNumberEt.setError(getString(R.string.err_msg_valid_mobile));
            AlternateMobileNumberEt.requestFocus();
        } else if (emailIdEt.getText().toString().isEmpty()) {
            emailIdEt.setError(getString(R.string.err_empty_field));
            emailIdEt.requestFocus();
        } else if (!emailIdEt.getText().toString().contains("@") || !emailIdEt.getText().toString().contains(".")) {
            emailIdEt.setError(getString(R.string.err_msg_email));
            emailIdEt.requestFocus();
        } else if (tvDob.getText().toString().isEmpty()) {
            tvDob.setError("Please choose Date of Birth");
            tvDob.requestFocus();
        } else if (qualificationTv.getText().toString().isEmpty()) {
            qualificationTv.setError("Please Select Qualification");
            qualificationTv.requestFocus();
        } else if (pincodeEt.getText().toString().isEmpty()) {
            pincodeEt.setError(getString(R.string.err_empty_field));
            pincodeEt.requestFocus();
        } else if (pincodeEt.getText().toString().length() < 6) {
            pincodeEt.setError(getString(R.string.pincode_error));
            pincodeEt.requestFocus();
        } else if (locationTypeTv.getText().toString().isEmpty()) {
            locationTypeTv.setError("Please Select Location Type");
            locationTypeTv.requestFocus();
        } else if (LandmarkEt.getText().toString().isEmpty()) {
            LandmarkEt.setError(getString(R.string.err_empty_field));
            LandmarkEt.requestFocus();
        } else if (addressEt.getText().toString().isEmpty()) {
            addressEt.setError(getString(R.string.err_empty_field));
            addressEt.requestFocus();
        } else if (populationTv.getText().toString().isEmpty()) {
            populationTv.setError("Please Select Poupulation");
            populationTv.requestFocus();
        } /*else if (gstinEt.getText().toString().isEmpty()) {
            gstinEt.setError(getString(R.string.err_empty_field));
            gstinEt.requestFocus();
        }*/ else if (aadharEt.getText().toString().isEmpty()) {
            aadharEt.setError(getString(R.string.err_empty_field));
            aadharEt.requestFocus();
        } else if (aadharEt.getText().toString().length() < 12) {
            aadharEt.setError(getString(R.string.invalid_aadhar));
            aadharEt.requestFocus();
        } else if (panEt.getText().toString().isEmpty()) {
            panEt.setError(getString(R.string.err_empty_field));
            panEt.requestFocus();
        } else if (panEt.getText().toString().length() < 10) {
            panEt.setError(getString(R.string.invalid_pan));
            panEt.requestFocus();
        } else if (bankTv.getText().toString().isEmpty()) {
            bankTv.setError("Please Select Bank");
            bankTv.requestFocus();
        } else if (branchNameEt.getText().toString().isEmpty()) {
            branchNameEt.setError(getString(R.string.err_empty_field));
            branchNameEt.requestFocus();
        } else if (IFSCEt.getText().toString().isEmpty()) {
            IFSCEt.setError(getString(R.string.err_empty_field));
            IFSCEt.requestFocus();
        } else if (AccountNumberEt.getText().toString().isEmpty()) {
            AccountNumberEt.setError(getString(R.string.err_empty_field));
            AccountNumberEt.requestFocus();
        } else if (AccountNameEt.getText().toString().isEmpty()) {
            AccountNameEt.setError(getString(R.string.err_empty_field));
            AccountNameEt.requestFocus();
        } else {
            updateKycApi();
            // imagePicker.choosePicture(true);
        }
    }

    public void getUserDetail() {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetUserResponse> call = git.GetProfile(new BasicRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<GetUserResponse>() {

                @Override
                public void onResponse(Call<GetUserResponse> call, retrofit2.Response<GetUserResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            mGetUserResponse = response.body();

                            if (mGetUserResponse != null) {
                                ApiFintechUtilMethods.INSTANCE.mGetUserResponse = mGetUserResponse;
                                if (mGetUserResponse.getPasswordExpired()) {
                                    mCustomPassDialog.WarningWithSingleBtnCallBack(getString(R.string.password_expired_msg), "Change", false, new CustomAlertDialog.DialogCallBack() {
                                        @Override
                                        public void onPositiveClick() {
                                            startActivityForResult(new Intent(UpdateProfileActivity.this, ChangePinPassActivity.class)
                                                    .putExtra("IsPin", false)
                                                    .putExtra("IsForcibly", true)
                                                    .putExtra("Intent_Result", INTENT_PASSWORD_EXPIRE)
                                                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_PASSWORD_EXPIRE);

                                        }

                                        @Override
                                        public void onNegativeClick() {

                                        }
                                    });

                                }

                                if (mGetUserResponse.getStatuscode() == 1) {
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.UserDetailPref, gson.toJson(mGetUserResponse));
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.IsRealApiPref, mGetUserResponse.getUserInfo().isRealAPI());

                                    mLoginDataResponse.getData().setDoubleFactor(mGetUserResponse.getUserInfo().isDoubleFactor());
                                    ApiFintechUtilMethods.INSTANCE.mTempLoginDataResponse = mLoginDataResponse;
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.LoginPref, gson.toJson(mLoginDataResponse));
                                    setUserData();


                                } else if (response.body().getStatuscode() == -1) {

                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, mGetUserResponse.getMsg() + "");
                                } else if (response.body().getStatuscode() == 0) {

                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, mGetUserResponse.getMsg() + "");
                                } else {

                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, mGetUserResponse.getMsg() + "");
                                }

                            } else {

                                ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(UpdateProfileActivity.this, response.code(), response.message());
                        }
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetUserResponse> call, Throwable t) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        ApiFintechUtilMethods.INSTANCE.apiFailureError(UpdateProfileActivity.this, t);


                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(this, getString(R.string.some_thing_error));
        }

    }


    public void updateProfileApi() {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            EditUser editUser = new EditUser(mGetUserResponse.getUserInfo().getCommRate(), mGetUserResponse.getUserInfo().getProfilePic(),
                    aadharEt.getText().toString(), panEt.getText().toString(), gstinEt.getText().toString(), addressEt.getText().toString(),
                    mGetUserResponse.getUserInfo().getUserID(), MobileNumberEt.getText().toString().trim()/*mGetUserResponse.getUserInfo().getMobileNo()*/,
                    nameEt.getText().toString(), outletNameEt.getText().toString(), emailIdEt.getText().toString(),
                    mGetUserResponse.getUserInfo().getGSTApplicable(), mGetUserResponse.getUserInfo().getTDSApplicable(),
                    mGetUserResponse.getUserInfo().getCCFGstApplicable(),
                    pincodeEt.getText().toString(), tvDob.getText().toString(), selectedShopType, selectedQualification, selectedPopulation,
                    selectedLocation, LandmarkEt.getText().toString(), AlternateMobileNumberEt.getText().toString(), selectedBank,
                    IFSCEt.getText().toString(), AccountNumberEt.getText().toString(), AccountNameEt.getText().toString(), branchNameEt.getText().toString());

            Call<BasicResponse> call = git.UpdateProfile(new UpdateUserRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(), editUser));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            BasicResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    ApiFintechUtilMethods.INSTANCE.SuccessfulWithTwoButton(UpdateProfileActivity.this, "Success",
                                            data.getMsg() + "", "Update KYC", true, new CustomAlertDialog.DialogCallBack() {
                                                @Override
                                                public void onPositiveClick() {
                                                    updateKyc();
                                                }

                                                @Override
                                                public void onNegativeClick() {

                                                }
                                            });
                                    ApiFintechUtilMethods.INSTANCE.isUserDetailUpdate = true;
                                    mLoginDataResponse.getData().setEmailID(emailIdEt.getText().toString());
                                    mLoginDataResponse.getData().setName(nameEt.getText().toString());
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.LoginPref, gson.toJson(mLoginDataResponse));
                                    ApiFintechUtilMethods.INSTANCE.mTempLoginDataResponse = mLoginDataResponse;

                                    mGetUserResponse.getUserInfo().setName(nameEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setOutletName(outletNameEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setEmailID(emailIdEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setPincode(pincodeEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setAddress(addressEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setGstin(gstinEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setAadhar(aadharEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setPan(panEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setMobileNo(MobileNumberEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setAlternateMobile(AlternateMobileNumberEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setDob(tvDob.getText().toString());
                                    mGetUserResponse.getUserInfo().setAccountName(AccountNameEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setAccountNumber(AccountNumberEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setLandmark(LandmarkEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setIfsc(IFSCEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setBranchName(branchNameEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setBankName(selectedBank);
                                    mGetUserResponse.getUserInfo().setQualification(selectedQualification);
                                    mGetUserResponse.getUserInfo().setLocationType(selectedLocation);
                                    mGetUserResponse.getUserInfo().setShoptype(selectedShopType);
                                    mGetUserResponse.getUserInfo().setPoupulation(selectedPopulation);
                                    kycBtn.setVisibility(View.VISIBLE);
                                    kycBtn.setText("Update KYC");
                                    submitBtn.setVisibility(View.GONE);
                                    // showKycButton();
                                    setResult(RESULT_OK);
                                    ApiFintechUtilMethods.INSTANCE.mGetUserResponse = mGetUserResponse;
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.UserDetailPref, gson.toJson(mGetUserResponse));

                                } else if (response.body().getStatuscode() == -1) {

                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                                } else if (response.body().getStatuscode() == 0) {

                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                                } else {

                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                                }

                            } else {

                                ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(UpdateProfileActivity.this, response.code(), response.message());
                        }
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {


                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(UpdateProfileActivity.this, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
        }

    }


    public void updateKycApi() {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<UpdateKycResponse> call = git.UpdateKycApi(new BasicRequest(mGetUserResponse.getUserInfo().getUserID(),
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<UpdateKycResponse>() {

                @Override
                public void onResponse(Call<UpdateKycResponse> call, retrofit2.Response<UpdateKycResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            mUpdateKycResponse = response.body();
                            if (mUpdateKycResponse != null) {
                                if (mUpdateKycResponse.getStatuscode() == 1) {
                                    //UtilMethods.INSTANCE.Successful(UpdateProfileActivity.this, data.getMsg() + "");
                                    if (alertDialog != null && alertDialog.isShowing() && recyclerViewDocs != null) {
                                        UploadDocsAdapter mUploadDocsAdapter = new UploadDocsAdapter(UpdateProfileActivity.this, mUpdateKycResponse.getUserDox());
                                        recyclerViewDocs.setAdapter(mUploadDocsAdapter);
                                        setKycBtnText();
                                        setResult(RESULT_OK);
                                    } else {
                                        showDocumentDialog();
                                    }
                                } else if (response.body().getStatuscode() == -1) {

                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, mUpdateKycResponse.getMsg() + "");
                                } else if (response.body().getStatuscode() == 0) {

                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, mUpdateKycResponse.getMsg() + "");
                                } else {

                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, mUpdateKycResponse.getMsg() + "");
                                }

                            } else {

                                ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(UpdateProfileActivity.this, response.code(), response.message());
                        }
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UpdateKycResponse> call, Throwable t) {


                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(UpdateProfileActivity.this, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }

        }

    }

    public void updateKycStatusApi(int outletId) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<UpdateKycResponse> call = git.UpdateKYCStatus(new UpdateKycStatusRequest(outletId,
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<UpdateKycResponse>() {

                @Override
                public void onResponse(Call<UpdateKycResponse> call, retrofit2.Response<UpdateKycResponse> response) {
                    try {
                        if (response.isSuccessful()) {
                            UpdateKycResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    ApiFintechUtilMethods.INSTANCE.Successful(UpdateProfileActivity.this, data.getMsg() + "");
                                    updateKycApi();
                                } else if (response.body().getStatuscode() == -1) {

                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                                } else if (response.body().getStatuscode() == 0) {

                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                                } else {

                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                                }

                            } else {

                                ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(UpdateProfileActivity.this, response.code(), response.message());
                        }
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UpdateKycResponse> call, Throwable t) {


                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(UpdateProfileActivity.this, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
        }

    }


    public void uploadDocApi(File file) {
   /*     try {*/
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            UploadDocRequest mUploadDocRequest = new UploadDocRequest(docTypeId, uid,
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession());

            String req = gson.toJson(mUploadDocRequest);
            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

            RequestBody requestStr = RequestBody.create(MediaType.parse("text/plain"), req);

            Call<BasicResponse> call = git.UploadDocs(fileToUpload, requestStr);

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {
//
                    try {
                        if (response.isSuccessful()) {
                            BasicResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    ApiFintechUtilMethods.INSTANCE.Successful(UpdateProfileActivity.this, data.getMsg() + "");
                                    updateKycApi();
                                } else if (response.body().getStatuscode() == -1) {

                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                                } else if (response.body().getStatuscode() == 0) {

                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                                } else {

                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                                }

                            } else {
                                ApiFintechUtilMethods.INSTANCE.apiErrorHandle(UpdateProfileActivity.this, response.code(), response.message());

//                                ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(UpdateProfileActivity.this, response.code(), response.message());
                        }
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {


                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(UpdateProfileActivity.this, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

    /*     }
       catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
        }*/

    }


    void showDocumentDialog() {


        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_upload_docs, null);
        alertDialog.setView(dialogView);
        AppCompatImageView closeBtn = dialogView.findViewById(R.id.closeBtn);
        recyclerViewDocs = dialogView.findViewById(R.id.recyclerView);
        recyclerViewDocs.setLayoutManager(new LinearLayoutManager(this));
        kycUploadDocBtn = dialogView.findViewById(R.id.kycBtn);
        setKycBtnText();
        UploadDocsAdapter mUploadDocsAdapter = new UploadDocsAdapter(this, mUpdateKycResponse.getUserDox());
        recyclerViewDocs.setAdapter(mUploadDocsAdapter);

        closeBtn.setOnClickListener(v -> alertDialog.dismiss());


        kycUploadDocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUpdateKycResponse.getUserDox().get(0).getKycStatus() == 1 || mUpdateKycResponse.getUserDox().get(0).getKycStatus() == 4 || mUpdateKycResponse.getUserDox().get(0).getKycStatus() == 5) {
                    updateKycStatusApi(mUpdateKycResponse.getUserDox().get(0).getOutletID());
                } else {

                }
            }
        });

        alertDialog.show();
        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
       /* if (isFullScreen) {
            alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }*/
    }


    void setKycBtnText() {
        if (mUpdateKycResponse != null && mUpdateKycResponse.getUserDox() != null && mUpdateKycResponse.getUserDox().size() > 0) {
            if (mUpdateKycResponse.getUserDox().get(0).getKycStatus() == 1) {
                kycUploadDocBtn.setText("Apply for KYC");
                kycBtn.setText("Update KYC");
                // submitBtn.setVisibility(View.VISIBLE);
                ViewCompat.setBackgroundTintList(kycUploadDocBtn, ColorStateList.valueOf
                        (getResources().getColor(R.color.grey)));
            } else if (mUpdateKycResponse.getUserDox().get(0).getKycStatus() == 2) {
                kycUploadDocBtn.setText("KYC Applied");
                kycBtn.setText("View KYC");
                // submitBtn.setVisibility(View.GONE);
                ViewCompat.setBackgroundTintList(kycUploadDocBtn, ColorStateList.valueOf
                        (getResources().getColor(R.color.style_color_accent)));
            } else if (mUpdateKycResponse.getUserDox().get(0).getKycStatus() == 3) {
                kycUploadDocBtn.setText("KYC Completed");
                kycBtn.setText("View KYC");
                // submitBtn.setVisibility(View.GONE);
                ViewCompat.setBackgroundTintList(kycUploadDocBtn, ColorStateList.valueOf
                        (getResources().getColor(R.color.green)));
            } else if (mUpdateKycResponse.getUserDox().get(0).getKycStatus() == 4) {
                kycUploadDocBtn.setText("Apply for REKYC");
                kycBtn.setText("Update KYC");
                // submitBtn.setVisibility(View.VISIBLE);
                ViewCompat.setBackgroundTintList(kycUploadDocBtn, ColorStateList.valueOf
                        (getResources().getColor(R.color.grey)));
            } else if (mUpdateKycResponse.getUserDox().get(0).getKycStatus() == 5) {
                kycUploadDocBtn.setText("KYC rejected REAPPLY");
                kycBtn.setText("Update KYC");
                // submitBtn.setVisibility(View.VISIBLE);
                ViewCompat.setBackgroundTintList(kycUploadDocBtn, ColorStateList.valueOf
                        (getResources().getColor(R.color.red)));
            }
        }
    }

    public void showInfo(String info, View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.document_info, null);
        TextView infoTv = view.findViewById(R.id.info);
        infoTv.setText(info);
        BubbleDialog dialog = new BubbleDialog(this);
        dialog.addContentView(view);
        dialog.setClickedView(v);
        dialog.calBar(false);
        dialog.show();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }


    public void uploadDocs(int doc_TypeID, int userId, String docName) {
        docTypeId = doc_TypeID;
        uid = userId;

        if (doc_TypeID == 2) {
            selectImageDialog(docName);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= 33) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(new Intent(this, PermissionActivity.class),
                            INTENT_PERMISSION_IMAGE);
                } else {
                    imagePicker.choosePictureWithoutPermission(true, (doc_TypeID == 1 || doc_TypeID == 2) ? false : true);
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(new Intent(this, PermissionActivity.class),
                            INTENT_PERMISSION_IMAGE);

                } else {

                    imagePicker.choosePictureWithoutPermission(true, (doc_TypeID == 1 || doc_TypeID == 2) ? true : true);
                }
            } else {
                imagePicker.choosePictureWithoutPermission(true, (doc_TypeID == 1 || doc_TypeID == 2) ? true : true);
            }
        }

    }

    public void selectImageDialog(String docName) {
        try {
            if (alertDialogImageType != null && alertDialogImageType.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this);
            alertDialogImageType = dialogBuilder.create();
            alertDialogImageType.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_select_image_type, null);
            alertDialogImageType.setView(dialogView);

            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);


            dialogView.findViewById(R.id.singleImage).setOnClickListener(v -> {
                alertDialogImageType.dismiss();
                if (android.os.Build.VERSION.SDK_INT >= 33) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        startActivityForResult(new Intent(this, PermissionActivity.class),
                                INTENT_PERMISSION_IMAGE);
                    } else {
                        imagePicker.choosePictureWithoutPermission(true, true);
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        startActivityForResult(new Intent(this, PermissionActivity.class),
                                INTENT_PERMISSION_IMAGE);
                    } else {
                        imagePicker.choosePictureWithoutPermission(true, true);
                    }
                } else {
                    imagePicker.choosePictureWithoutPermission(true, true);
                }
            });


            dialogView.findViewById(R.id.multiImage).setOnClickListener(v -> {
                alertDialogImageType.dismiss();
                startActivityForResult(new Intent(UpdateProfileActivity.this, MultiImageUploadActivity.class)
                        .putExtra("Title", docName + "")
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_MULTI_IMAGE);
            });

            closeIv.setOnClickListener(v -> alertDialogImageType.dismiss());


            alertDialogImageType.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_MULTI_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                uploadDocApi(new File(data.getStringExtra("DocPath")));
            } else {
                Toast.makeText(this, "Error in image file", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == INTENT_SELECT_BANK && resultCode == RESULT_OK) {
            selectedBank = data.getExtras().getString("bankName");
            ifsc = data.getExtras().getString("ifsc");

            //bankId = data.getExtras().getString("bankId");
            // accVerification = data.getExtras().getString("accVerification");
            // shortCode = data.getExtras().getString("shortCode");
            // isNeft = data.getExtras().getString("neft");
            // isImps = data.getExtras().getString("imps");
            // accLmt = data.getExtras().getString("accLmt");
            // ekO_BankID = data.getExtras().getString("ekO_BankID");

            bankTv.setText("" + selectedBank);
            bankTv.setError(null);
            if (ifsc != null && ifsc.length() > 0 && ifsc.length() > 4) {
                IFSCEt.setText(ifsc + "");
            } else {
                IFSCEt.setText("");
            }
        } /*else if (requestCode == INTENT_PICK_CONTACT && resultCode == Activity.RESULT_OK) {

            selectContact(data, MobileNumberEt);
        } else if (requestCode == INTENT_PICK_CONTACT_ALTERNATE && resultCode == Activity.RESULT_OK) {

            selectContact(data, AlternateMobileNumberEt);
        } else if (requestCode == INTENT_PRERMISSION && resultCode == Activity.RESULT_OK) {

            openPhoneBook(INTENT_PICK_CONTACT, INTENT_PRERMISSION);
        } else if (requestCode == INTENT_PRERMISSION_ALTERNATE && resultCode == Activity.RESULT_OK) {

            openPhoneBook(INTENT_PICK_CONTACT_ALTERNATE, INTENT_PRERMISSION_ALTERNATE);
        } */ else if (requestCode == INTENT_PASSWORD_EXPIRE && resultCode == INTENT_PASSWORD_EXPIRE) {
            getUserDetail();
        } else if (requestCode == INTENT_PERMISSION_IMAGE && resultCode == Activity.RESULT_OK) {
            imagePicker.choosePictureWithoutPermission(true, true);
        } else {
            imagePicker.handleActivityResult(resultCode, requestCode, data);
        }
    }

    /*void selectContact(Intent data, EditText mobileEt) {
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
                mobileEt.setText("");
                // <------------------------------------------------------------------>
                Number = phones.getString(phones.getColumnIndex("data1"));
//                            Log.e("number is:",Number);
            }
            String Name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            if (!Number.equals("")) {
                SetNumber(Number, mobileEt);
            } else {
                Toast.makeText(this, "Please select a valid number", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    public void SetNumber(final String Number, EditText mobileEt) {
        String Number1 = Number.replace("+91", "");
        String Number2 = Number1.replace("(", "");
        String Number3 = Number2.replace(")", "");
        String Number4 = Number3.replace(" ", "");
        String Number5 = Number4.replace("-", "");
        String Number6 = Number5.replace("_", "");
        mobileEt.setText(Number6);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void VerifyAadhaarCard(UpdateProfileActivity activity, String aadhaar) {
        loader.show();
        RequestAadhaarOTP aadhaarOTP = new RequestAadhaarOTP(ApplicationConstant.INSTANCE.APP_ID,
                deviceId,
                mLoginDataResponse.getData().getLoginTypeID() + "",
                "",
                deviceSerialNum,
                mLoginDataResponse.getData().getSession(),
                mLoginDataResponse.getData().getSessionID(),
                mLoginDataResponse.getData().getUserID() + "",
                BuildConfig.VERSION_NAME, aadhaar);
        FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
        Call<ResponseAadhaarOTP> aadhaarCallBack = git.GenerateAadhaarOTP(aadhaarOTP);
        aadhaarCallBack.enqueue(new Callback<ResponseAadhaarOTP>() {
            @Override
            public void onResponse(Call<ResponseAadhaarOTP> call, Response<ResponseAadhaarOTP> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseAadhaarOTP data = response.body();
                        if (data != null) {
                            assert response.body() != null;
                            if (data.getStatuscode() == 1) {
                                int referenceID = response.body().getReferenceID();
                                ApiFintechUtilMethods.INSTANCE.openOtpDepositDialog(activity, loader, aadhaar, referenceID, new ApiFintechUtilMethods.DialogOTPCallBackAadhaar() {
                                    @Override
                                    public void onPositiveClick(String otpValue, CustomLoader loader, String aadhaarNo, int referenceId, TextView timerTv, TextView resendCodeTv, Dialog mDialog) {
                                        VerifyAadhaarOTP(activity, otpValue, loader, aadhaar, referenceID + "", mDialog);
                                    }

                                    @Override
                                    public void onResetCallback(String otpValue, CustomLoader loader, String aadhaarNo, int referenceId, TextView timerTv, TextView resendCodeTv, Dialog mDialog) {

                                    }
                                });

                            } else {
                                if (response.body().getStatuscode() == -1) {
                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                                } else if (response.body().getStatuscode() == 0) {
                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                                }
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                        }
                    } else {
                        ApiFintechUtilMethods.INSTANCE.apiErrorHandle(UpdateProfileActivity.this, response.code(), response.message());
                    }
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                } catch (Exception e) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAadhaarOTP> call, Throwable t) {
                try {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    ApiFintechUtilMethods.INSTANCE.apiFailureError(UpdateProfileActivity.this, t);
                } catch (IllegalStateException ise) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    ApiFintechUtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                }
            }
        });
    }

    private void VerifyAadhaarOTP(UpdateProfileActivity context, String otpValue, CustomLoader loader, String aadhaar, String referenceID, Dialog mDialog) {
        try {
            loader.show();
            AadhaarOtpReq aadhaarOTPReq = new AadhaarOtpReq(
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    mLoginDataResponse.getData().getLoginTypeID() + "",
                    "",
                    deviceSerialNum,
                    mLoginDataResponse.getData().getSession(),
                    mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getUserID() + "",
                    BuildConfig.VERSION_NAME, otpValue, aadhaar, referenceID);
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<ValidateAadhaarOTPResponse> aadhaarCallBackVerify = git.ValidateAadhaarOTP(aadhaarOTPReq);
            aadhaarCallBackVerify.enqueue(new Callback<ValidateAadhaarOTPResponse>() {
                @Override
                public void onResponse(Call<ValidateAadhaarOTPResponse> call, retrofit2.Response<ValidateAadhaarOTPResponse> response) {
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
                                    if (response.body().getFullName() != null) {
                                        nameEt.setText(response.body().getFullName());
                                        nameEt.setEnabled(false);
                                    } else {
                                        nameEt.setEnabled(true);
                                    }
                                    if (response.body().getDob() != null) {
                                        tvDob.setText(response.body().getDob());
                                        tvDob.setEnabled(false);
                                    } else {
                                        tvDob.setEnabled(true);
                                    }
                                    if (response.body().getAddress() != null) {
                                        addressEt.setText(response.body().getAddress());
                                        addressEt.setEnabled(false);
                                    } else {
                                        addressEt.setEnabled(true);
                                    }
                                    if (response.body().getIsAadharValid() != null) {
                                        aadhaarV.setVisibility(View.GONE);
                                        aadharEt.setClickable(false);
                                        aadharEt.setEnabled(false);
                                        aadharEt.setKeyListener(null);
                                    } else {
                                        aadhaarV.setVisibility(View.VISIBLE);
                                        aadharEt.setClickable(true);
                                        aadharEt.setEnabled(true);
                                    }
                                    // getUserDetail();
                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                    }
                                }
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(UpdateProfileActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ValidateAadhaarOTPResponse> call, Throwable t) {

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

    private void VerifyPanCard(final Activity context, String panNumber, CustomLoader loader) {
        try {
            loader.show();
            ValidatePANAppRequest validatePanAppReq = new ValidatePANAppRequest(ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    mLoginDataResponse.getData().getLoginTypeID() + "",
                    "",
                    deviceSerialNum,
                    mLoginDataResponse.getData().getSession(),
                    mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getUserID() + "",
                    BuildConfig.VERSION_NAME, panNumber);
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<ValidatePANAppResponse> callValidatePan = git.ValidatePanApp(validatePanAppReq);
            callValidatePan.enqueue(new Callback<ValidatePANAppResponse>() {
                @Override
                public void onResponse(Call<ValidatePANAppResponse> call, retrofit2.Response<ValidatePANAppResponse> response) {
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    if (loader != null && loader.isShowing()) {
                                        loader.dismiss();
                                    }
                                    ApiFintechUtilMethods.INSTANCE.Successful(context, response.body().getMsg() + "");
                                    if (response.body().getFullName() != null) {
                                        nameEt.setText(response.body().getFullName());
                                        nameEt.setEnabled(false);
                                    } else {
                                        nameEt.setEnabled(true);
                                    }
                                    if (response.body().getIsPANValid() != null) {
                                        panV.setVisibility(View.GONE);
                                        panEt.setClickable(false);
                                        panEt.setEnabled(false);
                                        panEt.setKeyListener(null);
                                    } else {
                                        panV.setVisibility(View.VISIBLE);
                                        panEt.setClickable(true);
                                        panEt.setEnabled(true);
                                    }
                                    //getUserDetail();
                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                    }
                                }
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(UpdateProfileActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ValidatePANAppResponse> call, Throwable t) {

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
}