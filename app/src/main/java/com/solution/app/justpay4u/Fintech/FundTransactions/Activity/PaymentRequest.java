package com.solution.app.justpay4u.Fintech.FundTransactions.Activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dreamseller.imagepicker.ImagePicker;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Object.Bank;
import com.solution.app.justpay4u.Api.Fintech.Object.PaymentMode;
import com.solution.app.justpay4u.Api.Fintech.Request.FundRequestToUsers;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.FundreqToResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetBankAndPaymentModeResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.Activities.PermissionActivity;
import com.solution.app.justpay4u.Fintech.Activities.QrCodeActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownModel;
import com.solution.app.justpay4u.Util.FundRequestUtil;
import com.solution.app.justpay4u.Util.RoundedCornersTransformation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PaymentRequest extends AppCompatActivity implements View.OnClickListener {


    FundRequestUtil mFundRequestUtil;
    ArrayList<DropDownModel> arrayListRole = new ArrayList<>();
    ArrayList<DropDownModel> arrayListBank = new ArrayList<>();
    ArrayList<DropDownModel> arrayListMode = new ArrayList<>();
    ArrayList<DropDownModel> arrayListWallet = new ArrayList<>();
    CustomLoader loader;
    int BankID;
    int PaymentModeID;
    Bank selectedBank, selectedIntentBank;
    int walletTypeId = 1;
    int parentIdIntent;
    CustomAlertDialog mCustomAlertDialog;
    private View requestToChooserView;
    private TextView requestTo;
    private LinearLayout walletTypeView, requestToView;
    private View walletTypeChooserView;
    private TextView walletTypeTv;
    private View bankListChooserView;
    private TextView banklist;
    private TextView number;
    private LinearLayout llAcHolder;
    private TextView txtAccountName;
    private View paymentModeChooserView;
    private TextView paymentMode;
    private TextView scanQrCode;
    private EditText txttranferAmount;
    private TextView UpiBtn;
    private LinearLayout llAcHolderName;
    private EditText txtacHolderName;
    private LinearLayout llTranLable;
    private EditText txtTransactionID;
    private LinearLayout llCheque;
    private EditText ChecknumberID;
    private LinearLayout llCardNo;
    private EditText txtCardNo;
    private LinearLayout llBranchName;
    private EditText txtbranchName;
    private LinearLayout llUpiId;
    private EditText txtupiId;
    private LinearLayout llMoblie;
    private EditText txtMobileNo;
    private TextView txtSelectImage;
    private AppCompatImageView image;
    private Button btnPaymentSubmit;
    private String upiGenrateOrderId = "0";
    private String orderSecureKey = "";
    private boolean isUPIID;
    private ImagePicker imagePicker;
    private boolean isQrCodeModeSelect;
    private File selectedImageFile;
    private int INTENT_UPI = 638;
    private String selectedUpiId;
    private LoginResponse mLoginDataResponse;
    private DropDownDialog mDropDownDialog;
    private int selectedParentId;
    private int selectedRolePos;
    private int selectedBankPos;
    private int selectedBankModePos;
    private int selectedWalletPos;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private int INTENT_PERMISSION_IMAGE = 865;
    private int selectedModeBankId;
    private BalanceResponse mBalanceResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.setCancelable(false);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_payment_request);
            mFundRequestUtil = new FundRequestUtil(this);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            mDropDownDialog = new DropDownDialog(this);
            mCustomAlertDialog = new CustomAlertDialog(this);


            findViews();
            new Handler(Looper.getMainLooper()).post(() -> {
                selectedIntentBank = getIntent().getParcelableExtra("SelectedBank");
                parentIdIntent = getIntent().getIntExtra("ParentId", 0);
                HitApi();
                mBalanceResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
                gettingWalletType();

                LocalBroadcastManager
                        .getInstance(this)
                        .registerReceiver(mNewNotificationReciver, new IntentFilter("New_UPI_Order_Notification_Detect"));
            });
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("photopath", ImagePicker.currentCameraFileName);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("photopath") && ImagePicker.currentCameraFileName.length() < 5) {
                ImagePicker.currentCameraFileName = savedInstanceState.getString("photopath");
            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    private void findViews() {

        requestToChooserView = findViewById(R.id.requestToChooserView);
        requestTo = findViewById(R.id.requestTo);
        walletTypeView = findViewById(R.id.walletTypeView);
        requestToView = findViewById(R.id.requestToView);
        walletTypeChooserView = findViewById(R.id.walletTypeChooserView);
        walletTypeTv = findViewById(R.id.walletType);
        bankListChooserView = findViewById(R.id.bankListChooserView);
        banklist = findViewById(R.id.banklist);
        number = findViewById(R.id.number);
        llAcHolder = findViewById(R.id.ll_AcHolder);
        txtAccountName = findViewById(R.id.txtAccountName);
        paymentModeChooserView = findViewById(R.id.paymentModeChooserView);
        paymentMode = findViewById(R.id.paymentMode);
        scanQrCode = findViewById(R.id.scanQrCode);
        txttranferAmount = findViewById(R.id.txttranferAmount);
        UpiBtn = findViewById(R.id.UpiBtn);
        llAcHolderName = findViewById(R.id.ll_acHolderName);
        txtacHolderName = findViewById(R.id.txtacHolderName);
        llTranLable = findViewById(R.id.ll_tranLable);
        txtTransactionID = findViewById(R.id.txtTransactionID);
        llCheque = findViewById(R.id.ll_cheque);
        ChecknumberID = findViewById(R.id.ChecknumberID);
        llCardNo = findViewById(R.id.ll_cardNo);
        txtCardNo = findViewById(R.id.txtCardNo);
        llBranchName = findViewById(R.id.ll_branchName);
        txtbranchName = findViewById(R.id.txtbranchName);
        llUpiId = findViewById(R.id.ll_upiId);
        txtupiId = findViewById(R.id.txtupiId);
        llMoblie = findViewById(R.id.ll_Moblie);
        txtMobileNo = findViewById(R.id.txtMobileNo);
        txtSelectImage = findViewById(R.id.txtSelectImage);
        image = findViewById(R.id.image);
        btnPaymentSubmit = findViewById(R.id.btnPaymentSubmit);

        imagePicker = new ImagePicker(this, null, imageUri -> {
            Uri imgUri = imageUri;
            selectedImageFile = new File(imgUri.getPath());
            // image.setImageURI(imgUri);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.transform(new RoundedCornersTransformation(PaymentRequest.this, (int) getResources().getDimension(R.dimen._5sdp), 0, RoundedCornersTransformation.CornerType.RIGHT));

            Glide.with(PaymentRequest.this)
                    .load(selectedImageFile)
                    .apply(requestOptions)
                    .into(image);
            // uploadDocApi(new File(imgUri.getPath()));
        }).setWithImageCrop();

        SetListener();
    }

    private void SetListener() {

        btnPaymentSubmit.setOnClickListener(this);
        txtSelectImage.setOnClickListener(this);

        requestToChooserView.setOnClickListener(v -> {
            if (arrayListRole != null && arrayListRole.size() > 0) {
                mDropDownDialog.showDropDownPopup(v, selectedRolePos, arrayListRole, (clickPosition, value, object) -> {

                            if (selectedRolePos != clickPosition) {
                                requestTo.setText(value + "");
                                selectedRolePos = clickPosition;
                                selectedParentId = ((FundRequestToUsers) object).getParentID();
                                GetBankAndPaymentMode(selectedParentId);
                            }
                        }
                );
            } else {
                mCustomAlertDialog.WarningWithCallBack("Role not available to request, please try again", "Role Not Found", "Retry", true, new CustomAlertDialog.DialogCallBack() {
                    @Override
                    public void onPositiveClick() {
                        HitApi();
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });

        bankListChooserView.setOnClickListener(v -> {
            if (arrayListBank != null && arrayListBank.size() > 0) {
                mDropDownDialog.showDropDownPopup(v, selectedBankPos, arrayListBank, (clickPosition, value, object) -> {
                            if (selectedBankPos != clickPosition) {
                                banklist.setText(value + "");
                                selectedBankPos = clickPosition;
                                selectedBank = (Bank) object;
                                bankSelect();
                            }
                        }
                );
            } else {
                mCustomAlertDialog.WarningWithCallBack("Bank not available to request, please try again", "Bank Not Found", "Retry", true, new CustomAlertDialog.DialogCallBack() {
                    @Override
                    public void onPositiveClick() {
                        GetBankAndPaymentMode(selectedParentId);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });

        walletTypeChooserView.setOnClickListener(v -> {
            if (arrayListWallet != null && arrayListWallet.size() > 0) {
                mDropDownDialog.showDropDownPopup(v, selectedWalletPos, arrayListWallet, (clickPosition, value, object) -> {
                            if (selectedWalletPos != clickPosition) {
                                walletTypeTv.setText(value + "");
                                selectedWalletPos = clickPosition;
                                walletTypeId = ((BalanceData) object).getId();
                            }
                        }
                );
            } else {
                mCustomAlertDialog.WarningWithCallBack("Wallet not available to request, please try again", "Wallet Not Found", "Retry", true, new CustomAlertDialog.DialogCallBack() {
                    @Override
                    public void onPositiveClick() {
                        // GetBankAndPaymentMode(selectedParentId);
                        ApiFintechUtilMethods.INSTANCE.Balancecheck(PaymentRequest.this, loader, mLoginDataResponse, deviceId,
                                deviceSerialNum, mAppPreferences, null, object -> {
                                    mBalanceResponse = (BalanceResponse) object;
                                    if (mBalanceResponse != null && mBalanceResponse.getBalanceData() != null &&
                                            mBalanceResponse.getBalanceData().size() > 0) {
                                        gettingWalletType();
                                    }
                                });

                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });

        paymentModeChooserView.setOnClickListener(v -> {
            if (arrayListMode != null && arrayListMode.size() > 0) {
                mDropDownDialog.showDropDownPopup(v, selectedBankModePos, arrayListMode, (clickPosition, value, object) -> {
                            if (selectedBankModePos != clickPosition) {
                                selectedBankModePos = clickPosition;
                                selectPaymentMode((PaymentMode) object);
                            }
                        }
                );
            } else {
                Toast.makeText(PaymentRequest.this, "Mode not Available", Toast.LENGTH_SHORT).show();
            }
        });
        scanQrCode.setOnClickListener(v -> {

            if (selectedBank != null && selectedBank.getIsqrenable()) {
                startActivity(new Intent(PaymentRequest.this, QrCodeActivity.class)
                        .putExtra("SelectedBank", selectedBank != null ? selectedBank : null)
                        .putExtra("FROMINTENT", 1)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            } else {
                Toast.makeText(PaymentRequest.this, "Select QR Code Supported Bank.", Toast.LENGTH_LONG).show();
            }
        });

        UpiBtn.setOnClickListener(v -> {
            if (txttranferAmount.getText() != null && txttranferAmount.getText().toString().trim().isEmpty()) {
                txttranferAmount.setError("Please enter valid amount!!");
                txttranferAmount.requestFocus();
                return;
            } else if (selectedUpiId == null || selectedUpiId.isEmpty() || !selectedUpiId.contains("@")) {
                ApiFintechUtilMethods.INSTANCE.Error(PaymentRequest.this, "Invalid UPI Payee Address, Change Bank Or Contact to Admin.");
                return;
            }
            upiGenrateOrderId = "0";
            orderSecureKey = "";
            ApiFintechUtilMethods.INSTANCE.OrderForUPI(PaymentRequest.this, txttranferAmount.getText().toString().trim(), selectedUpiId,
                    loader, mLoginDataResponse, deviceId, deviceSerialNum, object -> {
                        int orderId = (int) object;
                        upiGenrateOrderId = String.valueOf(orderId);
                        openUpiIntent(getUPIString(selectedUpiId, txtAccountName.getText().toString().trim(),
                                getString(R.string.app_name).replaceAll(" ", "") + "UPITransaction", txttranferAmount.getText().toString().trim(),
                                ApplicationConstant.INSTANCE.baseUrl));
                    });
            /*openUpiIntent(getUPIString(selectedUpiId, txtAccountName.getText().toString().trim(),
                    getString(R.string.app_name).replaceAll(" ", "") + "UPITransaction", txttranferAmount.getText().toString().trim(),
                    ApplicationConstant.INSTANCE.baseUrl));*/

        });
    }

    @Override
    public void onClick(View v) {

        if (v == txtSelectImage) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
            /*imagePicker.choosePicture(true);*/
        } else if (v == btnPaymentSubmit) {
            if (validationForm() == 0) {
                if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    String checksumValue = orderSecureKey +
                            "|" +
                            upiGenrateOrderId +
                            "|" +
                            txttranferAmount.getText().toString().trim() +
                            "|" +
                            mLoginDataResponse.getData().getSessionID() +
                            "|" +
                            mLoginDataResponse.getData().getUserID() +
                            "|" +
                            selectedUpiId;
                    String upichecksum = mFundRequestUtil.AppEncrypt(checksumValue);
                    ApiFintechUtilMethods.INSTANCE.PaymentRequest(this, isUPIID ? selectedUpiId : "", upiGenrateOrderId, upichecksum, selectedImageFile, BankID,
                            txttranferAmount.getText().toString().trim() + "",
                            number.getText().toString().trim() + "",
                            txtTransactionID.getText().toString().trim() + "",
                            ChecknumberID.getText().toString().trim() + "",
                            txtCardNo.getText().toString().trim() + "",
                            txtMobileNo.getText().toString().trim() + "",
                            llAcHolderName.getVisibility() == View.VISIBLE ? txtacHolderName.getText().toString().trim() : txtAccountName.getText().toString().trim(),
                            PaymentModeID, walletTypeId + "", btnPaymentSubmit, loader, mLoginDataResponse, deviceId, deviceSerialNum, object -> {
                                GetBankAndPaymentModeResponse modeResponse = (GetBankAndPaymentModeResponse) object;
                                txtMobileNo.setText("");
                                txtTransactionID.setText("");
                                txttranferAmount.setText("");
                                upiGenrateOrderId = "0";
                                orderSecureKey = "";
                            });

                } else {
                    ApiFintechUtilMethods.INSTANCE.NetworkError(this);
                }
            }
        }
    }

    private void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {


            if (!loader.isShowing()) {
                loader.show();
            }
            ApiFintechUtilMethods.INSTANCE.FundRequestTo(this, loader, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, new ApiFintechUtilMethods.ApiResponseCallBack() {
                @Override
                public void onSucess(Object object) {
                    FundreqToResponse mFundreqToResponse = (FundreqToResponse) object;
                    selectRoleData(mFundreqToResponse);
                }

                @Override
                public void onError(int error) {

                }
            });
        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    void selectRoleData(FundreqToResponse fundreqToResponse) {

        if (fundreqToResponse != null) {
            final ArrayList<FundRequestToUsers> fundRequestToUsers = fundreqToResponse.getFundRequestToUsers();

            if (fundRequestToUsers != null && fundRequestToUsers.size() != 0) {

                selectedRolePos = 0;
                selectedParentId = fundRequestToUsers.get(0).getParentID();
                arrayListRole.clear();
                for (int i = 0; i < fundRequestToUsers.size(); i++) {
                    arrayListRole.add(new DropDownModel(fundRequestToUsers.get(i).getParentName(), fundRequestToUsers.get(i)));
                    if (parentIdIntent != 0 && fundRequestToUsers.get(i).getParentID() == parentIdIntent) {
                        selectedRolePos = i;
                        selectedParentId = parentIdIntent;
                    }
                }

                requestTo.setText(fundRequestToUsers.get(selectedRolePos).getParentName() + "");
                GetBankAndPaymentMode(selectedParentId);

                if(arrayListRole.size()>1){
                    requestToView.setVisibility(View.VISIBLE);
                }else {
                    requestToView.setVisibility(View.GONE);
                }
            }
        }

    }

    void GetBankAndPaymentMode(int selectedParentId) {
        loader.show();
        ApiFintechUtilMethods.INSTANCE.GetBankAndPaymentMode(PaymentRequest.this, selectedParentId + "",
                loader, deviceId, deviceSerialNum, mLoginDataResponse, new ApiFintechUtilMethods.ApiResponseCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        GetBankAndPaymentModeResponse getBankAndPaymentModeResponse = (GetBankAndPaymentModeResponse) object;
                        // selectBankData(getBankAndPaymentModeResponse);
                        getBankData(getBankAndPaymentModeResponse);
                    }

                    @Override
                    public void onError(int error) {

                    }
                });
    }

    void getBankData(GetBankAndPaymentModeResponse getBankAndPaymentModeResponse) {
        selectedBankPos = 0;
        if (getBankAndPaymentModeResponse != null) {
            final List<Bank> bankArrayList = getBankAndPaymentModeResponse.getBanks();
            if (bankArrayList != null && bankArrayList.size() > 0) {
                arrayListBank.clear();
                for (int i = 0; i < bankArrayList.size(); i++) {
                    arrayListBank.add(new DropDownModel(bankArrayList.get(i).getBankName(), bankArrayList.get(i)));
                    if (selectedIntentBank != null && selectedIntentBank.getIsqrenable()
                            && bankArrayList.get(i).getIsqrenable() && bankArrayList.get(i).getId() == selectedIntentBank.getId()) {
                        selectedBankPos = i;
                    }
                }
                selectedBank = bankArrayList.get(selectedBankPos);
                bankSelect();

            } else {
                selectedBank = null;
                bankSelect();
            }
        } else {
            selectedBank = null;
            bankSelect();
        }


    }

    void bankSelect() {

        if (selectedBank != null) {
            BankID = selectedBank.getId();
            banklist.setText(selectedBank.getBankName() + "");
            if (selectedBank.getAccountHolder() != null && !selectedBank.getAccountHolder().isEmpty()) {
                txtAccountName.setText(selectedBank.getAccountHolder() + "");
                llAcHolder.setVisibility(View.VISIBLE);
            } else {
                llAcHolder.setVisibility(View.GONE);
            }

            number.setText(selectedBank.getAccountNo() + "");

            selectedUpiId = selectedBank.getUpinumber();
            if (!selectedBank.getIsqrenable()) {
                scanQrCode.setVisibility(View.GONE);
            } else if (selectedBank.getIsqrenable() && isQrCodeModeSelect) {
                scanQrCode.setVisibility(View.VISIBLE);
            } else {
                scanQrCode.setVisibility(View.GONE);
            }

            List<PaymentMode> PaymentMode = selectedBank.getMode();
            getPaymentMode(PaymentMode);
        } else {
            BankID = 0;
            banklist.setText("");
            txtAccountName.setText("");
            number.setText("");
            selectedBank = null;
            scanQrCode.setVisibility(View.GONE);
            getPaymentMode(new ArrayList<>());
        }
    }

    void getPaymentMode(final List<PaymentMode> paymentModeList) {
        selectedBankModePos = 0;
        if (paymentModeList != null && paymentModeList.size() > 0) {
            arrayListMode.clear();
            for (int i = 0; i < paymentModeList.size(); i++) {
                arrayListMode.add(new DropDownModel(paymentModeList.get(i).getMode(), paymentModeList.get(i)));
                if (selectedIntentBank != null && selectedIntentBank.getIsqrenable() && paymentModeList.get(i).getModeID() == 6) {
                    selectedBankModePos = i;
                }
            }


            selectPaymentMode(paymentModeList.get(selectedBankModePos));
        } else {
            paymentMode.setText("");
            llTranLable.setVisibility(View.GONE);
            llAcHolderName.setVisibility(View.GONE);
            llCardNo.setVisibility(View.GONE);
            llBranchName.setVisibility(View.GONE);
            llUpiId.setVisibility(View.GONE);
            llCheque.setVisibility(View.GONE);
            llMoblie.setVisibility(View.GONE);
            PaymentModeID = 0;
            scanQrCode.setVisibility(View.GONE);
            UpiBtn.setVisibility(View.GONE);
            isQrCodeModeSelect = false;
        }


    }

    void selectPaymentMode(PaymentMode mPaymentMode) {
        paymentMode.setText(mPaymentMode.getMode());
        if (mPaymentMode.getIsTransactionIdAuto()) {
            llTranLable.setVisibility(View.GONE);
        } else {
            llTranLable.setVisibility(View.VISIBLE);
        }
        if (mPaymentMode.getIsAccountHolderRequired()) {
            llAcHolderName.setVisibility(View.VISIBLE);
        } else {
            llAcHolderName.setVisibility(View.GONE);
        }
        if (mPaymentMode.getIsCardNumberRequired()) {
            llCardNo.setVisibility(View.VISIBLE);
        } else {
            llCardNo.setVisibility(View.GONE);
        }

        if (mPaymentMode.getIsBranchRequired()) {
            llBranchName.setVisibility(View.VISIBLE);
        } else {
            llBranchName.setVisibility(View.GONE);
        }
        if (mPaymentMode.getIsUPIID()) {
            llUpiId.setVisibility(View.VISIBLE);
        } else {
            llUpiId.setVisibility(View.GONE);
        }

        if (mPaymentMode.getIsChequeNoRequired()) {
            llCheque.setVisibility(View.VISIBLE);
        } else {
            llCheque.setVisibility(View.GONE);
        }
        if (mPaymentMode.getIsMobileNoRequired()) {
            llMoblie.setVisibility(View.VISIBLE);
        } else {
            llMoblie.setVisibility(View.GONE);
        }

        PaymentModeID = mPaymentMode.getModeID();
        selectedModeBankId = mPaymentMode.getBankID();
        if (selectedBank != null && selectedBank.getIsqrenable() && PaymentModeID == 6) {
            isQrCodeModeSelect = true;
            scanQrCode.setVisibility(View.VISIBLE);
        } else {
            isQrCodeModeSelect = false;
            scanQrCode.setVisibility(View.GONE);
        }


        if (PaymentModeID == 7) {
            isUPIID = true;
            UpiBtn.setVisibility(View.VISIBLE);

        } else {
            isUPIID = false;
            UpiBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }

    private int validationForm() {
        int flag = 0;
        if (number.getText() != null && number.getText().toString().trim().isEmpty()) {
            number.setError("Please enter valid account number!!");
            number.requestFocus();
            flag++;
        } else if (txttranferAmount.getText() != null && txttranferAmount.getText().toString().trim().isEmpty()) {
            txttranferAmount.setError("Please enter valid amount!!");
            txttranferAmount.requestFocus();
            flag++;
        } else if (llAcHolderName.getVisibility() == View.VISIBLE && txtacHolderName.getText() != null && txtacHolderName.getText().toString().trim().isEmpty()) {

            txtacHolderName.setError("Please enter valid info!!");
            txtacHolderName.requestFocus();
            flag++;

        } else if (llTranLable.getVisibility() == View.VISIBLE && txtTransactionID.getText() != null && txtTransactionID.getText().toString().trim().isEmpty()) {

            txtTransactionID.setError("Please enter valid info!!");
            txtTransactionID.requestFocus();
            flag++;

        } else if (llCheque.getVisibility() == View.VISIBLE && ChecknumberID.getText() != null && ChecknumberID.getText().toString().trim().isEmpty()) {

            ChecknumberID.setError("Please enter valid Check Number!!");
            ChecknumberID.requestFocus();
            flag++;

        } else if (llCardNo.getVisibility() == View.VISIBLE && txtCardNo.getText() != null && txtCardNo.getText().toString().trim().isEmpty()) {

            txtCardNo.setError("Please enter valid Card Number!!");
            txtCardNo.requestFocus();
            flag++;

        } else if (llBranchName.getVisibility() == View.VISIBLE && txtbranchName.getText() != null && txtbranchName.getText().toString().trim().isEmpty()) {

            txtbranchName.setError("Please enter valid Branch Name!!");
            txtbranchName.requestFocus();
            flag++;

        } else if (llUpiId.getVisibility() == View.VISIBLE && txtupiId.getText() != null && txtupiId.getText().toString().trim().isEmpty() && !txtupiId.getText().toString().trim().contains("@")) {

            txtupiId.setError("Please enter valid Upi Id!!");
            txtupiId.requestFocus();
            flag++;

        } else if (llMoblie.getVisibility() == View.VISIBLE && !txtMobileNo.getText().toString().trim().isEmpty() && txtMobileNo.getText().length() != 10) {
            txtMobileNo.setError("Please enter valid mobile number!!");
            txtMobileNo.requestFocus();
            flag++;
        }

        return flag;
    }

    private void gettingWalletType() {
        if (mBalanceResponse != null && mBalanceResponse.getBalanceData() != null &&
                mBalanceResponse.getBalanceData().size() > 0) {

            boolean isFirstDataSet = false;
            arrayListWallet.clear();
            for (int i = 0; i < mBalanceResponse.getBalanceData().size(); i++) {
                BalanceData item = mBalanceResponse.getBalanceData().get(i);
                if (item.isInFundProcess()) {
                    if (!isFirstDataSet) {
                        walletTypeId = item.getId();
                        walletTypeTv.setText(item.getWalletType() + "");
                        selectedWalletPos = i;
                        isFirstDataSet = true;
                    }
                    arrayListWallet.add(new DropDownModel(item.getWalletType(), item));
                }
            }


            if (arrayListWallet.size() > 1) {
                walletTypeView.setVisibility(View.VISIBLE);
            } else {
                walletTypeView.setVisibility(View.GONE);
            }
        } else {
            walletTypeView.setVisibility(View.GONE);
            ApiFintechUtilMethods.INSTANCE.Balancecheck(this, loader, mLoginDataResponse, deviceId,
                    deviceSerialNum, mAppPreferences, null, object -> {
                        mBalanceResponse = (BalanceResponse) object;
                        if (mBalanceResponse != null && mBalanceResponse.getBalanceData() != null &&
                                mBalanceResponse.getBalanceData().size() > 0) {
                            gettingWalletType();
                        }
                    });
        }

    }

    private Uri getUPIString(String payeeAddress, String payeeName,
                             String trxnNote, String payeeAmount, String refUrl) {
        /* String UPI = "upi://pay?pa=" + payeeAddress + "&pn=" + payeeName + *//*"&mc=" + payeeMCC +*//* *//*"&tid=" + trxnID +*//* *//*"&tr=" + trxnRefId
                +*//* "&tn=" + trxnNote + "&am=" + payeeAmount + "&cu=" + currencyCode
                + "&refUrl=" + refUrl;*/
        // String UPI = "upi://pay?pa=" + payeeAddress + "&pn=" + payeeName + "&tn=" + trxnNote + "&am=" + payeeAmount + "&refUrl=" + refUrl;

        Uri uri =
                new Uri.Builder()
                        .scheme("upi")
                        .authority("pay")
                        .appendQueryParameter("pa", payeeAddress)
                        .appendQueryParameter("pn", payeeName)
                        /* .appendQueryParameter("mc", "your-merchant-code")
                         .appendQueryParameter("tr", "your-transaction-ref-id")*/
                        .appendQueryParameter("tn", trxnNote)
                        .appendQueryParameter("am", payeeAmount)
                        .appendQueryParameter("cu", "INR")
                        .appendQueryParameter("url", refUrl)
                        .build();
        return uri;
    }

    void openUpiIntent(Uri Upi) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Upi);
        Intent chooser = Intent.createChooser(intent, "Pay with...");
        chooser.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivityForResult(chooser, INTENT_UPI, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_UPI) {

            if (data != null && resultCode == RESULT_OK) {
                String paymentResponse, txnId, status, txnRef, ApprovalRefNo, TrtxnRef, responseCode, bleTxId;
                paymentResponse = data.getStringExtra("response");
                txnId = data.getStringExtra("txnId");
                status = data.getStringExtra("Status");
                txnRef = data.getStringExtra("txnRef");
                ApprovalRefNo = data.getStringExtra("ApprovalRefNo");
                TrtxnRef = data.getStringExtra("TrtxnRef");
                responseCode = data.getStringExtra("responseCode");
                bleTxId = data.getStringExtra("bleTxId");/*TrtxnRef*/


                if ((status == null || status.isEmpty() || status.toLowerCase().contains("null") || status.toLowerCase().contains("undefined"))
                        && paymentResponse != null && !paymentResponse.isEmpty() && !paymentResponse.toLowerCase().equalsIgnoreCase("undefined")
                        && paymentResponse.contains("&") && paymentResponse.contains("=")) {

                    String[] splitArray = TextUtils.split(paymentResponse, "&");

                    for (int i = 0; i < splitArray.length; i++) {
                        if (splitArray[i].contains("txnId=")) {
                            txnId = splitArray[i].replace("txnId=", "").trim();
                        }
                        if (splitArray[i].contains("Status=")) {
                            status = splitArray[i].replace("Status=", "").trim();
                        }
                        if (splitArray[i].contains("txnRef=")) {
                            txnRef = splitArray[i].replace("txnRef=", "").trim();
                        }
                        if (splitArray[i].contains("ApprovalRefNo=")) {
                            ApprovalRefNo = splitArray[i].replace("ApprovalRefNo=", "").trim();
                        }
                        if (splitArray[i].contains("TrtxnRef=")) {
                            TrtxnRef = splitArray[i].replace("TrtxnRef=", "").trim();
                        }
                        if (splitArray[i].contains("responseCode=")) {
                            responseCode = splitArray[i].replace("responseCode=", "").trim();
                        }
                        if (splitArray[i].contains("bleTxId=")) {
                            bleTxId = splitArray[i].replace("bleTxId=", "").trim();
                        }
                    }

                }

                if (status != null) {
                    if (status.toLowerCase().equalsIgnoreCase("success")) {
                        if (txnRef != null && !txnRef.isEmpty() && !txnRef.equalsIgnoreCase("null") && !txnRef.toLowerCase().contains("undefined")) {
                            txtTransactionID.setText(txnRef);
                            onClick(btnPaymentSubmit);
                        } else if (ApprovalRefNo != null && !ApprovalRefNo.isEmpty() && !ApprovalRefNo.equalsIgnoreCase("null") && !ApprovalRefNo.toLowerCase().contains("undefined")) {
                            txtTransactionID.setText(ApprovalRefNo);
                            onClick(btnPaymentSubmit);
                        } else if (TrtxnRef != null && !TrtxnRef.isEmpty() && !TrtxnRef.equalsIgnoreCase("null") && !TrtxnRef.toLowerCase().contains("undefined")) {
                            txtTransactionID.setText(TrtxnRef);
                            onClick(btnPaymentSubmit);
                        } else {
                            ApiFintechUtilMethods.INSTANCE.SuccessfulWithTitle(this, "Success", "Transaction Successful.");
                        }
                    } else if (status.toLowerCase().equalsIgnoreCase("submitted")) {
                        upiGenrateOrderId = "0";
                        orderSecureKey = "";
                        ApiFintechUtilMethods.INSTANCE.ProcessingWithTitle(this, "Submitted", "Transaction Submitted, Please Wait After Confirmation You Can Add Fund Request.");
                    } else {
                        upiGenrateOrderId = "0";
                        orderSecureKey = "";
                        ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(this, "Failed", "Transaction Failed, Please Try After Some Time.");
                    }
                } else {
                    upiGenrateOrderId = "0";
                    orderSecureKey = "";
                }

                /*Toast.makeText(PaymentRequest.this, data.getExtras() + "", Toast.LENGTH_LONG).show();
                 Log.e("UPI",paymentResponse+"");
                   Log.e("UPI",data.getExtras()+"");
                   Log.e("UPI",data.getData()+"");*/

                /*Bundle[{Status=Success, isExternalMerchant=true, txnRef=uDK7591578462474072lpif, TRANSACTION_STATUS=3, response=txnId=Airpay1139Rjc1578462474071&txnRef=uDK7591578462474072lpif&Status=Success&responseCode=00, bleTxId=P2001081118229818376376, txnId=Airpay1139Rjc1578462474071, responseCode=00}]*/
                /*Bundle[{Status=SUCCESS, txnRef=fjD8091578483039469owLn, ApprovalRefNo=, response=txnId=Airpay1297Fyo1578483039469&responseCode=0&Status=SUCCESS&txnRef=fjD8091578483039469owLn, txnId=Airpay1297Fyo1578483039469, responseCode=0, TrtxnRef=fjD8091578483039469owLn}]*/

                /*txnId=AXI17152979abdf4b2b9a9e141083936913&responseCode=&Status=SUBMITTED&txnRef=*/
                /*txnId=SBIfa2b4b8c907a4226bf8829e8769b55f7&responseCode=UP00&Status=SUCCESS&txnRef=000817212057*/
                /*txnId=139Zas1578403940921Ys4T&responseCode=ZD&Status=FAILURE&txnRef=1375qE1578403940921BFMf*/
            } else {
                upiGenrateOrderId = "0";
                orderSecureKey = "";
            }
        } else if (requestCode == INTENT_PERMISSION_IMAGE && resultCode == RESULT_OK) {
            imagePicker.choosePictureWithoutPermission(true, true);
        } else {
            imagePicker.handleActivityResult(resultCode, requestCode, data);
        }

    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNewNotificationReciver);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private BroadcastReceiver mNewNotificationReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            orderSecureKey = intent.getExtras().getString("ORDER_KEY", "");
        }
    };
}
