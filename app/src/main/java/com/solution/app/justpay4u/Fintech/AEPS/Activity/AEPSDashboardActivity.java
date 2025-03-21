package com.solution.app.justpay4u.Fintech.AEPS.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Object.BankListObject;
import com.solution.app.justpay4u.Api.Fintech.Object.PidData;
import com.solution.app.justpay4u.Api.Fintech.Object.PidDataAdditionalInfo;
import com.solution.app.justpay4u.Api.Fintech.Object.PidDataData;
import com.solution.app.justpay4u.Api.Fintech.Object.PidDataDeviceInfo;
import com.solution.app.justpay4u.Api.Fintech.Object.PidDataParam;
import com.solution.app.justpay4u.Api.Fintech.Object.PidDataResp;
import com.solution.app.justpay4u.Api.Fintech.Object.PidDataSkey;
import com.solution.app.justpay4u.Api.Fintech.Object.SDKDetail;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BankListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GenerateDepositOTPResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetAEPSResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetEKYCDetailResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.AEPS.Adapter.AEPSRecentBankAdapter;
import com.solution.app.justpay4u.Fintech.Reports.Activity.AEPSReportActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.EKYCStepsDialog;
import com.solution.app.justpay4u.Util.GetLocation;
import com.solution.app.justpay4u.Util.Utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class AEPSDashboardActivity extends AppCompatActivity {


    EditText et_aadhar, et_account_num, et_amount;
    TextView tv_device, btnTxt, btn_balance, balanceTv, tv_500, tv_100, tv_1000, tv_1500;
    View amtView, bankbalanceView, deviceBlurView, aadharNumView, accountNumView, bankSelectionView, depositDefaultBankView;
    ImageView deviceIcon;
    View btn_withdrawl, miniStatement, withdrawal, deposit, adharPay, btn_kyc;
    ArrayList<String> arrayListDevice = new ArrayList<>();
    RecyclerView bankRecyclerView;
    AEPSRecentBankAdapter mAepsRecentBankAdapter;
    ArrayList<BankListObject> mRecentBankListObjects = new ArrayList<>();
    private CustomLoader loader;
    private BankListResponse mBankListResponse;
    private int INTENT_BANK = 543;
    private int bankIIN;
    private int typeTxn = 1;
    private int INTENT_READ_DEVICE = 876;
    private int sdkType;
    private boolean isBalance;
    private LoginResponse mLoginDataResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private DropDownDialog mDropDownDialog;
    private int selectedDevicePos = -1;
    private String bankId;
    private String bankName;
    private String btnSetString = "";
    private BalanceResponse mBalanceResponse;
    private Dialog dialogVerify;
    private int INTENT_DEPOSIT_STATUS = 54321;
    private SDKDetail sdkDetail;
    private GetLocation mGetLocation;
    private EKYCStepsDialog mEKYCStepsDialog;
    private boolean isEKYCCompleted;
    View mainView;

    public static boolean NUL(String paramString, PackageManager paramPackageManager) {
        try {
            paramPackageManager.getPackageInfo(paramString, 0);
            return true;
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_aeps_dashboard);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            isEKYCCompleted = mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isEKYCComplete);
            mEKYCStepsDialog = new EKYCStepsDialog(this, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);
            mDropDownDialog = new DropDownDialog(this);

            sdkType = getIntent().getIntExtra("SDKType", 0);
            sdkDetail = getIntent().getParcelableExtra("SDKDetail");
            findViewId();

            clickView();

            new Handler(Looper.getMainLooper()).post(() -> {
                mGetLocation = new GetLocation(this, loader);
                if (ApiFintechUtilMethods.INSTANCE.getLattitude == 0 || ApiFintechUtilMethods.INSTANCE.getLongitude == 0) {
                    mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
                        ApiFintechUtilMethods.INSTANCE.getLattitude = lattitude;
                        ApiFintechUtilMethods.INSTANCE.getLongitude = longitude;
                    });
                }

                setBankData();


                arrayListDevice.add("Mantra");
                arrayListDevice.add("Morpho");
                arrayListDevice.add("Tatvik");
                arrayListDevice.add("Startek");
                arrayListDevice.add("Precision");
                arrayListDevice.add("Secugen");
                arrayListDevice.add("Evolute");

                setSelectedDevice();


                ApiFintechUtilMethods.INSTANCE.GetAEPSBanklist(this, loader, mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum,
                        new ApiFintechUtilMethods.ApiResponseCallBack() {
                            @Override
                            public void onSucess(Object object) {
                                mBankListResponse = (BankListResponse) object;
                                if (mRecentBankListObjects != null) {
                                    if (mRecentBankListObjects.size() == 0) {
                                        mRecentBankListObjects.addAll(mBankListResponse.getBankMasters().subList(0, 5));
                                        mAepsRecentBankAdapter.notifyDataSetChanged();
                                    } else if (mRecentBankListObjects.size() > 0 && mRecentBankListObjects.size() < 5) {
                                        mRecentBankListObjects.addAll(mBankListResponse.getBankMasters().subList(0, (5 - mRecentBankListObjects.size())));
                                        mAepsRecentBankAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    mRecentBankListObjects = new ArrayList<>();
                                    mRecentBankListObjects.addAll(mBankListResponse.getBankMasters().subList(0, 5));
                                    mAepsRecentBankAdapter.notifyDataSetChanged();
                                }

                                mAppPreferences.set(ApplicationConstant.INSTANCE.recentAEPSbankListPref, new Gson().toJson(mRecentBankListObjects));
                            }

                            @Override
                            public void onError(int error) {

                            }
                        });

                mBalanceResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
                showBalanceData();
            });
        });
    }


    void findViewId() {
        mainView = findViewById(R.id.mainView);
        et_aadhar = findViewById(R.id.et_aadhar);
        et_account_num = findViewById(R.id.et_account_num);
        et_amount = findViewById(R.id.et_amount);
        deviceIcon = findViewById(R.id.deviceIcon);
        tv_500 = findViewById(R.id.tv500);
        tv_100 = findViewById(R.id.tv100);
        tv_1000 = findViewById(R.id.tv1000);
        tv_1500 = findViewById(R.id.tv1500);
        tv_device = findViewById(R.id.tv_device);
        btnTxt = findViewById(R.id.btnTxt);
        btn_withdrawl = findViewById(R.id.btn_withdrawl);
        btn_kyc = findViewById(R.id.btn_kyc);
        miniStatement = findViewById(R.id.miniStatement);
        withdrawal = findViewById(R.id.withdrawal);
        deposit = findViewById(R.id.deposit);
        adharPay = findViewById(R.id.adharPay);
        btn_balance = findViewById(R.id.btn_balance);
        balanceTv = findViewById(R.id.balanceTv);
        amtView = findViewById(R.id.amtView);
        aadharNumView = findViewById(R.id.aadharNumView);
        accountNumView = findViewById(R.id.accountNumView);
        bankbalanceView = findViewById(R.id.bankbalanceView);
        deviceBlurView = findViewById(R.id.deviceBlurView);
        bankRecyclerView = findViewById(R.id.bankRecyclerView);
        bankSelectionView = findViewById(R.id.bankSelectionView);
        depositDefaultBankView = findViewById(R.id.depositDefaultBankView);
        bankRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
    }

    void clickView() {
        et_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    btnTxt.setText(btnSetString + " of " + Utility.INSTANCE.formatedAmountWithRupees(s.toString()));
                } else {
                    btnTxt.setText(btnSetString);
                }
            }
        });
        btn_kyc.setOnClickListener(v -> {
            startActivity(new Intent(this, EKYCProcessActivity.class)
                    .putExtra("SDKDetail", sdkDetail)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });
        btn_withdrawl.setOnClickListener(v -> {
            isBalance = false;
            fetchDetaill();
        });
        withdrawal.setOnClickListener(v -> {
            deviceBlurView.setVisibility(View.GONE);
            aadharNumView.setVisibility(View.VISIBLE);
            accountNumView.setVisibility(View.GONE);
            bankSelectionView.setVisibility(View.VISIBLE);
            depositDefaultBankView.setVisibility(View.GONE);
            typeTxn = 1;
            isBalance = false;
            amtView.setVisibility(View.VISIBLE);
            String amt = "";
            if (et_amount.getText().length() > 0) {
                amt = " of " + Utility.INSTANCE.formatedAmountWithRupees(et_amount.getText().toString());
            }
            btnSetString = "Withdrawal from " + tv_device.getText().toString().toLowerCase();
            btnTxt.setText(btnSetString + amt);
            withdrawal.setBackgroundResource(R.drawable.rounded_primary_border);
            deposit.setBackgroundResource(R.drawable.rounded_grey_border);
            adharPay.setBackgroundResource(R.drawable.rounded_grey_border);
            miniStatement.setBackgroundResource(R.drawable.rounded_grey_border);
        });
        deposit.setOnClickListener(v -> {
            deviceBlurView.setVisibility(View.VISIBLE);
            aadharNumView.setVisibility(View.GONE);
            accountNumView.setVisibility(View.VISIBLE);
            bankSelectionView.setVisibility(View.GONE);
            depositDefaultBankView.setVisibility(View.VISIBLE);
            typeTxn = 2;
            isBalance = false;
            amtView.setVisibility(View.VISIBLE);
            String amt = "";
            if (et_amount.getText().length() > 0) {
                amt = " of " + Utility.INSTANCE.formatedAmountWithRupees(et_amount.getText().toString());
            }
            btnSetString = "Deposit";
            btnTxt.setText(btnSetString + amt);
            withdrawal.setBackgroundResource(R.drawable.rounded_grey_border);
            deposit.setBackgroundResource(R.drawable.rounded_primary_border);
            adharPay.setBackgroundResource(R.drawable.rounded_grey_border);
            miniStatement.setBackgroundResource(R.drawable.rounded_grey_border);
        });
        adharPay.setOnClickListener(v -> {
            deviceBlurView.setVisibility(View.GONE);
            aadharNumView.setVisibility(View.VISIBLE);
            accountNumView.setVisibility(View.GONE);
            bankSelectionView.setVisibility(View.VISIBLE);
            depositDefaultBankView.setVisibility(View.GONE);
            typeTxn = 3;
            isBalance = false;
            amtView.setVisibility(View.VISIBLE);
            String amt = "";
            if (et_amount.getText().length() > 0) {
                amt = " of " + Utility.INSTANCE.formatedAmountWithRupees(et_amount.getText().toString());
            }
            btnSetString = "Payment from " + tv_device.getText().toString().toLowerCase();
            btnTxt.setText(btnSetString + amt);
            withdrawal.setBackgroundResource(R.drawable.rounded_grey_border);
            deposit.setBackgroundResource(R.drawable.rounded_grey_border);
            adharPay.setBackgroundResource(R.drawable.rounded_primary_border);
            miniStatement.setBackgroundResource(R.drawable.rounded_grey_border);
        });
        miniStatement.setOnClickListener(v -> {
            deviceBlurView.setVisibility(View.GONE);
            aadharNumView.setVisibility(View.VISIBLE);
            accountNumView.setVisibility(View.GONE);
            bankSelectionView.setVisibility(View.VISIBLE);
            depositDefaultBankView.setVisibility(View.GONE);
            typeTxn = 4;
            isBalance = false;
            amtView.setVisibility(View.GONE);

            btnSetString = "Mini Statement from " + tv_device.getText().toString().toLowerCase();
            btnTxt.setText(btnSetString);
            withdrawal.setBackgroundResource(R.drawable.rounded_grey_border);
            deposit.setBackgroundResource(R.drawable.rounded_grey_border);
            adharPay.setBackgroundResource(R.drawable.rounded_grey_border);
            miniStatement.setBackgroundResource(R.drawable.rounded_primary_border);
            /* fetchDetaill();*/

            //For Testing
           /* ArrayList<MiniStatements> miniStatements = new ArrayList<>();

            miniStatements.add(new MiniStatements("12 Aug 2020 12:20 PM", "cr", "1200", "Good"));
            miniStatements.add(new MiniStatements("12 Aug 2020 12:20 PM", "dr", "12.34", "Good"));
            miniStatements.add(new MiniStatements("12 Aug 2020 12:20 PM", "dr", "2500.76", "Good"));
            miniStatements.add(new MiniStatements("12 Aug 2020 12:20 PM", "cr", "11.00", "Good"));
            miniStatements.add(new MiniStatements("12 Aug 2020 12:20 PM", "dr", "343.2", "Good"));
            Intent intent = new Intent(AEPSDashboardActivity.this, AEPSMiniStatementActivity.class);
            intent.putExtra("OP_NAME", bankName + "");
            intent.putExtra("OP_IMAGE", bankId + "");
            intent.putExtra("MINI_STATEMENT", miniStatements);
            intent.putExtra("NUMBER", et_aadhar.getText().toString());
            intent.putExtra("Device_NAME", tv_device.getText().toString());
            startActivity(intent);*/
        });

        btn_balance.setOnClickListener(v -> {
            isBalance = true;
            fetchDetaill();

        });
       /* refreshIv.setOnClickListener(v -> {
            type = 2;
            fetchDetaill();
        });*/
        findViewById(R.id.deviceView).setOnClickListener(v ->
                mDropDownDialog.showDropDownPopup(v, selectedDevicePos, arrayListDevice, (clickPosition, value, object) -> {

                            if (selectedDevicePos != clickPosition) {
                                tv_device.setText(value + "");
                                selectedDevicePos = clickPosition;
                                mAppPreferences.set(ApplicationConstant.INSTANCE.selectedAEPSDevicePref, clickPosition);
                                String amt = "";

                                if (typeTxn == 1) {
                                    if (et_amount.getText().length() > 0) {
                                        amt = " of " + Utility.INSTANCE.formatedAmountWithRupees(et_amount.getText().toString());
                                    }
                                    btnSetString = "Withdrawal from " + value.toLowerCase();
                                } else if (typeTxn == 4) {
                                    btnSetString = "Mini Statement from " + value.toLowerCase();
                                } else if (typeTxn == 2) {
                                    if (et_amount.getText().length() > 0) {
                                        amt = " of " + Utility.INSTANCE.formatedAmountWithRupees(et_amount.getText().toString());
                                    }
                                    btnSetString = "Deposit";
                                } else if (typeTxn == 3) {
                                    if (et_amount.getText().length() > 0) {
                                        amt = " of " + Utility.INSTANCE.formatedAmountWithRupees(et_amount.getText().toString());
                                    }
                                    btnSetString = "Payment from " + value.toLowerCase();

                                } else {
                                    if (et_amount.getText().length() > 0) {
                                        amt = " of " + Utility.INSTANCE.formatedAmountWithRupees(et_amount.getText().toString());
                                    }
                                    btnSetString = "Transaction";
                                }
                                btnTxt.setText(btnSetString + amt);
                                if (clickPosition == 0) {
                                    deviceIcon.setImageResource(R.drawable.mantra);
                                } else if (clickPosition == 1) {
                                    deviceIcon.setImageResource(R.drawable.morpho);
                                } else if (clickPosition == 2) {
                                    deviceIcon.setImageResource(R.drawable.tatvik);
                                } else if (clickPosition == 3) {
                                    deviceIcon.setImageResource(R.drawable.startek);
                                } else if (clickPosition == 4) {
                                    deviceIcon.setImageResource(R.drawable.precision);
                                } else if (clickPosition == 5) {
                                    deviceIcon.setImageResource(R.drawable.secugen);
                                } else if (clickPosition == 6) {
                                    deviceIcon.setImageResource(R.drawable.evolute);
                                } else {
                                    deviceIcon.setImageResource(R.drawable.placeholder_square);
                                }
                            }
                        }
                ));
        findViewById(R.id.btn_history).setOnClickListener(v -> {
            Intent i = new Intent(AEPSDashboardActivity.this, AEPSReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });

        findViewById(R.id.bankView).setOnClickListener(v ->
                startActivityForResult(new Intent(this, AEPSBankListActivity.class)
                        .putExtra("BankList", mBankListResponse)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_BANK));


        tv_100.setOnClickListener(v -> {
            if (et_amount.getText().length() == 0) {
                et_amount.setText("100");
            } else {
                try {
                    int amt = Integer.parseInt(et_amount.getText().toString());
                    amt = amt + 100;
                    et_amount.setText(amt + "");
                } catch (NumberFormatException nfe) {

                }
            }

            et_amount.setSelection(et_amount.getText().length());
        });

        tv_500.setOnClickListener(v -> {
            if (et_amount.getText().length() == 0) {
                et_amount.setText("500");
            } else {
                try {
                    int amt = Integer.parseInt(et_amount.getText().toString());
                    amt = amt + 500;
                    et_amount.setText(amt + "");
                } catch (NumberFormatException nfe) {

                }
            }

            et_amount.setSelection(et_amount.getText().length());
        });

        tv_1000.setOnClickListener(v -> {
            if (et_amount.getText().length() == 0) {
                et_amount.setText("1000");
            } else {
                try {
                    int amt = Integer.parseInt(et_amount.getText().toString());
                    amt = amt + 1000;
                    et_amount.setText(amt + "");
                } catch (NumberFormatException nfe) {

                }
            }

            et_amount.setSelection(et_amount.getText().length());
        });

        tv_1500.setOnClickListener(v -> {
            if (et_amount.getText().length() == 0) {
                et_amount.setText("1500");
            } else {
                try {
                    int amt = Integer.parseInt(et_amount.getText().toString());
                    amt = amt + 1500;
                    et_amount.setText(amt + "");
                } catch (NumberFormatException nfe) {

                }
            }

            et_amount.setSelection(et_amount.getText().length());
        });
    }

    private void showBalanceData() {
        if (mBalanceResponse != null && mBalanceResponse.isEKYCForced() && !isEKYCCompleted) {
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
        if (mBalanceResponse != null && mBalanceResponse.getBalanceData() != null &&
                mBalanceResponse.getBalanceData().size() > 0) {
            bankbalanceView.setVisibility(View.GONE);
            for (BalanceData item : mBalanceResponse.getBalanceData()) {
                if (item.getId() == 3) {
                    bankbalanceView.setVisibility(View.VISIBLE);
                    balanceTv.setText(Utility.INSTANCE.formatedAmountWithRupees(item.getBalance() + ""));
                    break;
                }
            }


        } else {

            ApiFintechUtilMethods.INSTANCE.Balancecheck(this, loader, mLoginDataResponse, deviceId, deviceSerialNum,
                    mAppPreferences, mEKYCStepsDialog, object -> {
                        mBalanceResponse = (BalanceResponse) object;
                        if (mBalanceResponse != null && mBalanceResponse.getBalanceData() != null &&
                                mBalanceResponse.getBalanceData().size() > 0) {
                            showBalanceData();
                        }
                    });


        }

    }

    void setSelectedDevice() {
        int index = mAppPreferences.getInt(ApplicationConstant.INSTANCE.selectedAEPSDevicePref);
        tv_device.setText(arrayListDevice.get(index) + "");
        selectedDevicePos = index;
        String amt = "";

        if (typeTxn == 1) {
            if (et_amount.getText().length() > 0) {
                amt = " of " + Utility.INSTANCE.formatedAmountWithRupees(et_amount.getText().toString());
            }
            btnSetString = "Withdrawal from " + arrayListDevice.get(index).toLowerCase();
        } else if (typeTxn == 4) {
            btnSetString = "Mini Statement from " + arrayListDevice.get(index).toLowerCase();
        } else if (typeTxn == 2) {
            if (et_amount.getText().length() > 0) {
                amt = " of " + Utility.INSTANCE.formatedAmountWithRupees(et_amount.getText().toString());
            }
            btnSetString = "Deposit";
        } else if (typeTxn == 3) {
            if (et_amount.getText().length() > 0) {
                amt = " of " + Utility.INSTANCE.formatedAmountWithRupees(et_amount.getText().toString());
            }
            btnSetString = "Payment from " + arrayListDevice.get(index).toLowerCase();

        } else {
            if (et_amount.getText().length() > 0) {
                amt = " of " + Utility.INSTANCE.formatedAmountWithRupees(et_amount.getText().toString());
            }
            btnSetString = "Transaction";
        }
        btnTxt.setText(btnSetString + amt);

        if (index == 0) {
            deviceIcon.setImageResource(R.drawable.mantra);
        } else if (index == 1) {
            deviceIcon.setImageResource(R.drawable.morpho);
        } else if (index == 2) {
            deviceIcon.setImageResource(R.drawable.tatvik);
        } else if (index == 3) {
            deviceIcon.setImageResource(R.drawable.startek);
        } else if (index == 4) {
            deviceIcon.setImageResource(R.drawable.precision);
        } else if (index == 5) {
            deviceIcon.setImageResource(R.drawable.secugen);
        } else if (index == 6) {
            deviceIcon.setImageResource(R.drawable.evolute);
        } else {
            deviceIcon.setImageResource(R.drawable.placeholder_square);
        }
    }

    void setBankData() {
        mRecentBankListObjects = new Gson().fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.recentAEPSbankListPref),
                new TypeToken<List<BankListObject>>() {
                }.getType());
        if (mRecentBankListObjects != null && mRecentBankListObjects.size() > 0) {
            mAepsRecentBankAdapter = new AEPSRecentBankAdapter(mRecentBankListObjects, this);
            bankRecyclerView.setAdapter(mAepsRecentBankAdapter);
        } else {
            mRecentBankListObjects = new ArrayList<>();
            mAepsRecentBankAdapter = new AEPSRecentBankAdapter(mRecentBankListObjects, this);
            bankRecyclerView.setAdapter(mAepsRecentBankAdapter);
        }
    }

    void fetchDetaill() {
        if (bankSelectionView.getVisibility() == View.VISIBLE && (bankName == null || bankName.isEmpty())) {
            NUL(mainView, "Please select bank", Color.RED);
            return;
        } else if (aadharNumView.getVisibility() == View.VISIBLE && et_aadhar.getText().toString().isEmpty()) {
            et_aadhar.setError("Please enter valid aadhar number");
            et_aadhar.requestFocus();
            return;
        } else if (aadharNumView.getVisibility() == View.VISIBLE && et_aadhar.getText().toString().length() != 12) {
            et_aadhar.setError("Please enter valid aadhar number");
            et_aadhar.requestFocus();
            return;
        } else if (accountNumView.getVisibility() == View.VISIBLE && et_account_num.getText().toString().isEmpty()) {
            et_account_num.setError("Please enter valid account number");
            et_account_num.requestFocus();
            return;
        } else if (accountNumView.getVisibility() == View.VISIBLE && et_account_num.getText().toString().length() != 12) {
            et_account_num.setError("Please enter valid 12 digit account number");
            et_account_num.requestFocus();
            return;
        }
        if ((typeTxn == 1 || typeTxn == 2 || typeTxn == 3) && !isBalance && et_amount.getText().toString().isEmpty()) {
            et_amount.setError("Please enter amount");
            et_amount.requestFocus();
            return;
        } else if (selectedDevicePos == -1 || tv_device.getText().toString().isEmpty()) {
            NUL(mainView, "Please select device", Color.RED);
            return;
        }

        loader.show();

        if (typeTxn == 2) {

            GenerateDepositOTP(null, null);
        } else {
            if (selectedDevicePos == 0) {
                // mantra();
                readDevice("com.mantra.rdservice", "com.mantra.rdservice.RDServiceActivity",
                        "Mantra", null);
            } else if (selectedDevicePos == 1) {
                // marpho();
                String pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" posh=\"UNKNOWN\" env=\"P\" /> <CustOpts><Param name=\"marphokey\" value=\"\" /></CustOpts> </PidOptions>";
                readDevice("com.scl.rdservice", "com.scl.rdservice.FingerCaptureActivity",
                        "Marpho", pidData);
            } else if (selectedDevicePos == 2) {
                // tatvik();
                readDevice("com.tatvik.bio.tmf20", "com.tatvik.bio.tmf20.RDMainActivity",
                        "Tatvik", null);
            } else if (selectedDevicePos == 3) {
                // startek();
                readDevice("com.acpl.registersdk", "com.acpl.registersdk.MainActivity",
                        "Startek", null);
            } else if (selectedDevicePos == 4) {
                //  precision();
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
                loader.dismiss();
           /* String pidData = "<PidOptions ver=\"1.0\"> <Opts env=\"P\" fCount=\"1\" fType=\"0\" format=\"0\" pidVer=\"2.0\" posh=\"UNKNOWN\" timeout=\"10000\" /> </PidOptions>";
            readDevice("com.precision.pb510.rdservice", "com.precision.rdservice.CaptureActivity",
                    "Precision", pidData);*/
            } else if (selectedDevicePos == 5) {
                // secugen();
                readDevice("com.secugen.rdservice", "com.secugen.rdservice.Capture", "Secugen", null);
            } else if (selectedDevicePos == 6) {
                // evolute();
                readDevice("com.evolute.rdservice", "com.evolute.rdservice.RDserviceActivity", "Evolute", null);
            }
        }

    }

    private void readDevice(String packageName, String serviceName, String name, String piddata) {

        if (NUL(packageName, getPackageManager())) {
            String pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\" posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"" + name + "key\" value=\"\" /></CustOpts> </PidOptions>";
            if (piddata != null && !piddata.isEmpty()) {
                pidData = piddata;
            }
            Intent localIntent = new Intent();
            localIntent.setComponent(new ComponentName(packageName, serviceName));
            localIntent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            localIntent.putExtra("PID_OPTIONS", pidData);
            startActivityForResult(localIntent, INTENT_READ_DEVICE);

        } else {
            loader.dismiss();
            openServiceOnPlay(name, packageName);
        }
    }

    /*private void marpho() {

        if (NUL("com.scl.rdservice", getPackageManager())) {
            String pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" posh=\"UNKNOWN\" env=\"P\" /> <CustOpts><Param name=\"mantrakey\" value=\"\" /></CustOpts> </PidOptions>";

            //String pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\"   pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\" posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"mantrakey\" value=\"\" /></CustOpts> </PidOptions>";

            Intent localIntent = new Intent();
            localIntent.setComponent(new ComponentName("com.scl.rdservice", "com.scl.rdservice.FingerCaptureActivity"));
            localIntent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            localIntent.putExtra("PID_OPTIONS", pidData);
            startActivityForResult(localIntent, INTENT_READ_DEVICE);

        } else {
            loader.dismiss();
            new AlertDialog.Builder(this)
                    .setTitle("Get Service")
                    .setMessage("Morpho RD Services Not Found.Click OK to Download Now.")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        try {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.scl.rdservice")));
                        } catch (Exception localException) {
                            NUL(mainView, "Something went wrong. Please try again later.", Color.RED);
                            localException.printStackTrace();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)

                    .show();


        }
    }

    private void mantra() {

        String pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\" posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"mantrakey\" value=\"\" /></CustOpts> </PidOptions>";
        if (NUL("com.mantra.rdservice", getPackageManager())) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.mantra.rdservice", "com.mantra.rdservice.RDServiceActivity"));
            intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent.putExtra("PID_OPTIONS", pidData);
            startActivityForResult(intent, INTENT_READ_DEVICE);
        } else {
            loader.dismiss();
            new AlertDialog.Builder(this)
                    .setTitle("Get Service")
                    .setMessage("Mantra RD Services Not Found.Click OK to Download Now.")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        try {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.mantra.rdservice")));
                        } catch (Exception localException) {
                            NUL(mainView, "Something went wrong. Please try again later.", Color.RED);
                            localException.printStackTrace();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)

                    .show();


        }
    }

    private void tatvik() {

        String pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\" posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"mantrakey\" value=\"\" /></CustOpts> </PidOptions>";
        if (NUL("com.tatvik.bio.tmf20", getPackageManager())) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.tatvik.bio.tmf20", "com.tatvik.bio.tmf20.RDMainActivity"));
            intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent.putExtra("PID_OPTIONS", pidData);
            startActivityForResult(intent, INTENT_READ_DEVICE);
        } else {
            loader.dismiss();
            new AlertDialog.Builder(this)
                    .setTitle("Get Service")
                    .setMessage("Tatvik RD Services Not Found.Click OK to Download Now.")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        try {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.tatvik.bio.tmf20")));
                        } catch (Exception localException) {
                            NUL(mainView, "Something went wrong. Please try again later.", Color.RED);
                            localException.printStackTrace();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)

                    .show();


        }
    }


    private void startek() {

        String pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\" posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"startekkey\" value=\"\" /></CustOpts> </PidOptions>";
        if (NUL("com.acpl.registersdk", getPackageManager())) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.acpl.registersdk", "com.acpl.registersdk.MainActivity"));
            intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent.putExtra("PID_OPTIONS", pidData);
            startActivityForResult(intent, INTENT_READ_DEVICE);
        } else {
            loader.dismiss();
            new AlertDialog.Builder(this)
                    .setTitle("Get Service")
                    .setMessage("Startek RD Services Not Found.Click OK to Download Now.")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        try {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.acpl.registersdk")));
                        } catch (Exception localException) {
                            NUL(mainView, "Something went wrong. Please try again later.", Color.RED);
                            localException.printStackTrace();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)

                    .show();


        }
    }*/

    void openServiceOnPlay(String name, String packageName) {
        new AlertDialog.Builder(this)
                .setTitle("Get Service")
                .setMessage(name + " RD Services Not Found.Click OK to Download Now.")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    try {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                    } catch (Exception localException) {
                        NUL(mainView, "Something went wrong. Please try again later.", Color.RED);
                        localException.printStackTrace();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                /*.setIcon(android.R.drawable.ic_dialog_alert)*/
                .show();
    }

    public void ItemClick(BankListObject mBankListObject) {

        bankName = mBankListObject.getBankName() + "";
        bankIIN = mBankListObject.getIin();
        bankId = mBankListObject.getId() + "";

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_BANK && resultCode == RESULT_OK) {
            if (data != null) {
                BankListObject mBankListObject = (BankListObject) data.getParcelableExtra("Bank");
                bankName = mBankListObject.getBankName() + "";
                bankIIN = mBankListObject.getIin();
                bankId = mBankListObject.getId() + "";
                int alreadyHaveIndex = -1;
                for (int i = 0; i < mRecentBankListObjects.size(); i++) {
                    if (mRecentBankListObjects.get(i).getId() == mBankListObject.getId()) {
                        alreadyHaveIndex = i;
                    }
                }

                if (alreadyHaveIndex != -1) {
                    mAepsRecentBankAdapter.setSelection(alreadyHaveIndex);
                } else {
                    mRecentBankListObjects.add(0, mBankListObject);
                    mAepsRecentBankAdapter.setSelection(0);
                }

                if (mRecentBankListObjects.size() > 5) {
                    mRecentBankListObjects.remove(mRecentBankListObjects.size() - 1);
                }

                mAepsRecentBankAdapter.notifyDataSetChanged();
                mAppPreferences.set(ApplicationConstant.INSTANCE.recentAEPSbankListPref, new Gson().toJson(mRecentBankListObjects));


            } else {
                NUL(mainView, "Bank not found", Color.RED);
            }
        } else if (requestCode == INTENT_READ_DEVICE) {
            loader.dismiss();
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String pidData = data.getStringExtra("PID_DATA");
                    pidData(mainView, pidData);
                } else {
                    NUL(mainView, "Didn't receive any data", Color.RED);
                }
            } else {
                NUL(mainView, "Canceled", Color.RED);
            }
        } else if (requestCode == INTENT_DEPOSIT_STATUS && resultCode == RESULT_OK) {
            et_account_num.setText("");
            et_amount.setText("");
        }
    }

    public void pidData(View paramRelativeLayout, String paramString) {
        PidData mPidData = new PidData();
        PidDataAdditionalInfo mAdditionalInfo = new PidDataAdditionalInfo();
        PidDataDeviceInfo mDeviceInfo = new PidDataDeviceInfo();
        PidDataResp mResp = new PidDataResp();
        PidDataSkey mSkey = new PidDataSkey();
        PidDataData mData = new PidDataData();
        /*locale.b("Please connect your Mantra device!");
        locale.a("111");*/
        try {
            if (paramString != null && !paramString.isEmpty() && paramString.contains("<PidData>")) {
                DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();
                Document localDocument = localDocumentBuilder.parse(new ByteArrayInputStream(paramString.getBytes("UTF-8")));
                Node localNode = localDocument.getElementsByTagName("PidData").item(0);
                Element localElement1 = (Element) localNode;
                NodeList localNodeList1 = localDocument.getElementsByTagName("Resp");
                Element localElement2 = (Element) localNodeList1.item(0);

                mResp.setErrCode(localElement2.getAttribute("errCode"));
                mResp.setErrInfo(localElement2.getAttribute("errInfo"));
                mResp.setfCount(localElement2.getAttribute("fCount"));
                mResp.setNmPoints(localElement2.getAttribute("nmPoints"));
                mResp.setfType(localElement2.getAttribute("fType"));
                mResp.setqScore(localElement2.getAttribute("qScore"));
                mResp.setiCount(localElement2.getAttribute("iCount"));
                mResp.setiType(localElement2.getAttribute("iType"));
                mResp.setpCount(localElement2.getAttribute("pCount"));
                mResp.setpType(localElement2.getAttribute("pType"));


                if (mResp.getErrCode().equalsIgnoreCase("0") && paramString.contains("<Hmac>")) {
                    NodeList localNodeList2 = localDocument.getElementsByTagName("Skey");
                    Element localElement3 = (Element) localNodeList2.item(0);
                    mSkey.setCi("" + localElement3.getAttribute("ci"));
                    mSkey.setText("" + NUL("Skey", localElement1));

                    NodeList localNodeList3 = localDocument.getElementsByTagName("DeviceInfo");
                    Element localElement4 = (Element) localNodeList3.item(0);
                    mDeviceInfo.setDc("" + localElement4.getAttribute("dc"));
                    mDeviceInfo.setDpId("" + localElement4.getAttribute("dpId"));
                    mDeviceInfo.setMc("" + localElement4.getAttribute("mc"));
                    mDeviceInfo.setMi("" + localElement4.getAttribute("mi"));
                    mDeviceInfo.setRdsId("" + localElement4.getAttribute("rdsId"));
                    mDeviceInfo.setRdsVer("" + localElement4.getAttribute("rdsVer"));

                    NodeList localNodeList4 = ((Element) localNodeList3.item(0)).getElementsByTagName("additional_info");

                    List<PidDataParam> mParams = new ArrayList<>();

                    NodeList mNodeList = localNodeList4.item(0).getChildNodes();
                    if (mNodeList != null && mNodeList.getLength() > 0) {
                        for (int i = 0; i < mNodeList.getLength(); i++) {
                            Node node = mNodeList.item(i);
                            if (node instanceof Element) {
                                mParams.add(new PidDataParam(((Element) node).getAttribute("name") + "", ((Element) node).getAttribute("value") + ""));
                            }
                        }
                    }

                    mAdditionalInfo.setParam(mParams);
                    mDeviceInfo.setAdditionalInfo(mAdditionalInfo);

                    NodeList localNodeList5 = localDocument.getElementsByTagName("Data");
                    Element localElement6 = (Element) localNodeList5.item(0);
                    mData.setType("" + localElement6.getAttribute("type"));
                    mData.setText("" + NUL("Data", localElement1));


                    mPidData.setHmac("" + NUL("Hmac", localElement1));
                    mPidData.setData(mData);
                    mPidData.setDeviceInfo(mDeviceInfo);
                    mPidData.setResp(mResp);
                    mPidData.setSkey(mSkey);

                    NUL(paramRelativeLayout, "Finger Captured Successfully!", getResources().getColor(R.color.green));
                    if (typeTxn == 1 && !isBalance) {
                        ApiFintechUtilMethods.INSTANCE.GetWithdrawlAEPS(this, paramString, ApiFintechUtilMethods.INSTANCE.getLattitude + "",
                                ApiFintechUtilMethods.INSTANCE.getLongitude + "", mPidData, et_aadhar.getText().toString(), et_amount.getText().toString(),
                                bankIIN, sdkType, loader, mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiCallBack() {
                                    @Override
                                    public void onSucess(Object object) {
                                        GetAEPSResponse mGetAEPSResponse = (GetAEPSResponse) object;

                                        Intent intent = new Intent(AEPSDashboardActivity.this, AEPSStatusActivity.class);
                                        intent.putExtra("STATUS", mGetAEPSResponse.getStatus());
                                        intent.putExtra("MESSAGE", mGetAEPSResponse.getMsg() + "");
                                        intent.putExtra("LIVE_ID", mGetAEPSResponse.getLiveID() + "");
                                        intent.putExtra("BALANCE", Utility.INSTANCE.formatedAmountWithRupees(mGetAEPSResponse.getBalance() + ""));
                                        intent.putExtra("TRANSACTION_ID", mGetAEPSResponse.getTransactionID() + "");
                                        intent.putExtra("OP_NAME", bankName + "");
                                        intent.putExtra("OP_IMAGE", bankId + "");
                                        intent.putExtra("AMOUNT", et_amount.getText().toString());
                                        intent.putExtra("NUMBER", et_aadhar.getText().toString().replace(et_aadhar.getText().toString().substring(2, 8), "XXXXXX"));
                                        intent.putExtra("Device_NAME", tv_device.getText().toString());
                                        startActivity(intent);

                                    }
                                });
                    } else if (typeTxn == 3 && !isBalance) {
                        ApiFintechUtilMethods.INSTANCE.GetAadharPay(this, paramString, ApiFintechUtilMethods.INSTANCE.getLattitude + "",
                                ApiFintechUtilMethods.INSTANCE.getLongitude + "", mPidData, et_aadhar.getText().toString(), et_amount.getText().toString(),
                                bankIIN, sdkType, loader, mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiCallBack() {
                                    @Override
                                    public void onSucess(Object object) {
                                        GetAEPSResponse mGetAEPSResponse = (GetAEPSResponse) object;

                                        Intent intent = new Intent(AEPSDashboardActivity.this, AEPSStatusActivity.class);
                                        intent.putExtra("STATUS", mGetAEPSResponse.getStatus());
                                        intent.putExtra("MESSAGE", mGetAEPSResponse.getMsg() + "");
                                        intent.putExtra("LIVE_ID", mGetAEPSResponse.getLiveID() + "");
                                        intent.putExtra("BALANCE", Utility.INSTANCE.formatedAmountWithRupees(mGetAEPSResponse.getBalance() + ""));
                                        intent.putExtra("TRANSACTION_ID", mGetAEPSResponse.getTransactionID() + "");
                                        intent.putExtra("OP_NAME", bankName + "");
                                        intent.putExtra("OP_IMAGE", bankId + "");
                                        intent.putExtra("AMOUNT", et_amount.getText().toString());
                                        intent.putExtra("NUMBER", et_aadhar.getText().toString().replace(et_aadhar.getText().toString().substring(2, 8), "XXXXXX"));
                                        intent.putExtra("Device_NAME", tv_device.getText().toString());
                                        startActivity(intent);

                                    }
                                });
                    } else if (typeTxn == 4 && !isBalance) {
                        ApiFintechUtilMethods.INSTANCE.GetMINIStatementAEPS(this, paramString, ApiFintechUtilMethods.INSTANCE.getLattitude + "",
                                ApiFintechUtilMethods.INSTANCE.getLongitude + "", mPidData, et_aadhar.getText().toString(),
                                bankIIN, bankName, sdkType, loader, mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiCallBack() {
                                    @Override
                                    public void onSucess(Object object) {
                                        GetAEPSResponse mGetAEPSResponse = (GetAEPSResponse) object;

                                        Intent intent = new Intent(AEPSDashboardActivity.this, AEPSMiniStatementActivity.class);
                                        intent.putExtra("OP_NAME", bankName + "");
                                        intent.putExtra("OP_IMAGE", bankId + "");
                                        intent.putExtra("BALANCE", Utility.INSTANCE.formatedAmountWithRupees(mGetAEPSResponse.getBalance() + ""));
                                        intent.putParcelableArrayListExtra("MINI_STATEMENT", mGetAEPSResponse.getStatements());
                                        intent.putExtra("NUMBER", et_aadhar.getText().toString().replace(et_aadhar.getText().toString().substring(2, 8), "XXXXXX"));
                                        intent.putExtra("Device_NAME", tv_device.getText().toString());
                                        startActivity(intent);
                                    }
                                });

                    } else {

                        ApiFintechUtilMethods.INSTANCE.GetBalanceAEPS(this, paramString, ApiFintechUtilMethods.INSTANCE.getLattitude + "",
                                ApiFintechUtilMethods.INSTANCE.getLongitude + "", mPidData, et_aadhar.getText().toString(),
                                bankIIN, sdkType, loader, mLoginDataResponse, deviceId, deviceSerialNum, object -> {
                                    GetAEPSResponse mGetAEPSResponse = (GetAEPSResponse) object;
                                    /* btn_balance.setText(Utility.INSTANCE.formatedAmountWithRupees(mGetAEPSResponse.getBalance() + ""));*/


                                    Intent intent = new Intent(AEPSDashboardActivity.this, AEPSBalanceStatusActivity.class);
                                    intent.putExtra("STATUS", 2);
                                    intent.putExtra("OP_NAME", bankName + "");
                                    intent.putExtra("OP_IMAGE", bankId + "");
                                    intent.putExtra("AMOUNT", Utility.INSTANCE.formatedAmountWithRupees(mGetAEPSResponse.getBalance() + ""));
                                    intent.putExtra("NUMBER", et_aadhar.getText().toString().replace(et_aadhar.getText().toString().substring(2, 8), "XXXXXX"));
                                    intent.putExtra("Device_NAME", tv_device.getText().toString());
                                    startActivity(intent);
                                });
                    }
                } else {
                    NUL(paramRelativeLayout, "Error Code : " + mResp.getErrCode() + "\n" +
                            "Error Message : " + mResp.getErrInfo() + "", Color.RED);
                }
            } else {
                NUL(paramRelativeLayout, "Didn't receive any data", Color.RED);
            }
        } catch (Exception e) {
            NUL(paramRelativeLayout, e.getMessage() + "", Color.RED);
            e.printStackTrace();
        }

    }

    void GenerateDepositOTP(TextView timerTv, TextView resendCodeTv) {

        ApiFintechUtilMethods.INSTANCE.GenerateDepositOTP(this, ApiFintechUtilMethods.INSTANCE.getLattitude + "",
                ApiFintechUtilMethods.INSTANCE.getLongitude + "", et_account_num.getText().toString(), et_amount.getText().toString(),
                508534, sdkType, loader, mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        GenerateDepositOTPResponse mGenerateDepositOTPResponse = (GenerateDepositOTPResponse) object;
                        if (mGenerateDepositOTPResponse != null && mGenerateDepositOTPResponse.getStatuscode() == 1) {
                            if (timerTv != null && resendCodeTv != null) {
                                ApiFintechUtilMethods.INSTANCE.setTimer(timerTv, resendCodeTv);
                            } else {
                                ApiFintechUtilMethods.INSTANCE.openOtpDepositDialog(AEPSDashboardActivity.this,
                                        mLoginDataResponse.getData().getMobileNo(), new ApiFintechUtilMethods.DialogOTPCallBack() {
                                            @Override
                                            public void onPositiveClick(String otpValue, TextView timerTv, TextView resendCodeTv, Dialog mDialog) {
                                                ApiFintechUtilMethods.INSTANCE.VerifyDepositOTP(AEPSDashboardActivity.this,
                                                        ApiFintechUtilMethods.INSTANCE.getLattitude + "",
                                                        ApiFintechUtilMethods.INSTANCE.getLongitude + "", mGenerateDepositOTPResponse.getReff1(), mGenerateDepositOTPResponse.getReff2(),
                                                        mGenerateDepositOTPResponse.getReff3(), otpValue,
                                                        et_account_num.getText().toString(), et_amount.getText().toString(),
                                                        508534, sdkType, loader, mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiCallBack() {
                                                            @Override
                                                            public void onSucess(Object object) {
                                                                mDialog.dismiss();
                                                                GenerateDepositOTPResponse mGenerateDepositVerifyResponse = (GenerateDepositOTPResponse) object;
                                                                openOtpDepositVerifyDialog(AEPSDashboardActivity.this, otpValue, et_amount.getText().toString(),
                                                                        et_account_num.getText().toString().trim(), mGenerateDepositOTPResponse, mGenerateDepositVerifyResponse);
                                                            }
                                                        });
                                            }

                                            @Override
                                            public void onResetCallback(String otpValue, TextView timerTv, TextView resendCodeTv, Dialog mDialog) {
                                                loader.show();
                                                GenerateDepositOTP(timerTv, resendCodeTv);
                                            }
                                        });
                            }


                        } else {

                        }
                    }
                });
    }

    void NUL(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Mini ATM Response")
                .setMessage(s)
                /* .setPositiveButton(android.R.string.yes, (dialog, which) -> finish())*/
                .setNegativeButton(android.R.string.yes, null)
                /*.setIcon(android.R.drawable.ic_dialog_alert)*/
                .show();
    }

    public void NUL(View paramView, String paramString, int paramInt) {
        Snackbar localSnackbar = Snackbar.make(paramView, "" + paramString, Snackbar.LENGTH_LONG);
        View snackBarView = localSnackbar.getView();
        snackBarView.setBackgroundColor(paramInt);
        TextView mainTextView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setMaxLines(5);
        TextViewCompat.setTextAppearance(mainTextView, R.style.TextAppearance_AppCompat_Body2);
        localSnackbar.show();

    }

    private String NUL(String paramString, Element paramElement) {
        NodeList localNodeList = paramElement.getElementsByTagName(paramString).item(0).getChildNodes();
        Node localNode = localNodeList.item(0);
        return localNode.getNodeValue();
    }

    public void openOtpDepositVerifyDialog(final Activity context, String otpValue, String txnAmt, String accountNum,
                                           GenerateDepositOTPResponse genrateResp, GenerateDepositOTPResponse verifyResp) {

        if (dialogVerify != null && dialogVerify.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_deposit_otp_verify, null);

        TextView statusTv = view.findViewById(R.id.statusTv);
        TextView amtTv = view.findViewById(R.id.amtTv);
        TextView nameTv = view.findViewById(R.id.nameTv);
        TextView accountNumTv = view.findViewById(R.id.accountNumTv);
        final ImageView closeIv = view.findViewById(R.id.closeIv);
        final View btnSubmit = view.findViewById(R.id.btnSubmit);
        final View btnCancel = view.findViewById(R.id.btnCancel);


        amtTv.setText(Utility.INSTANCE.formatedAmountWithRupees(txnAmt + ""));
        if (accountNum != null && !accountNum.isEmpty()) {
            accountNumTv.setText(accountNum + "");
        }
        if (verifyResp != null) {
            statusTv.setText(verifyResp.getMsg() + "");
            nameTv.setText(verifyResp.getBeneficiaryName() + "");
        }

        dialogVerify = new Dialog(context, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        dialogVerify.setCancelable(false);
        dialogVerify.setContentView(view);
        dialogVerify.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        closeIv.setOnClickListener(v -> dialogVerify.dismiss());
        btnCancel.setOnClickListener(v -> dialogVerify.dismiss());


        btnSubmit.setOnClickListener(v -> {
            dialogVerify.dismiss();

            ApiFintechUtilMethods.INSTANCE.DepositNow(AEPSDashboardActivity.this, ApiFintechUtilMethods.INSTANCE.getLattitude + "",
                    ApiFintechUtilMethods.INSTANCE.getLongitude + "",
                    genrateResp.getReff1(), genrateResp.getReff2(),
                    genrateResp.getReff3(), otpValue,
                    et_account_num.getText().toString(), et_amount.getText().toString(),
                    508534, sdkType, loader, mLoginDataResponse, deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            GenerateDepositOTPResponse mGenerateDepositResponse = (GenerateDepositOTPResponse) object;

                            if (mGenerateDepositResponse.getBeneficiaryName() == null
                                    && verifyResp.getBeneficiaryName() != null && !verifyResp.getBeneficiaryName().isEmpty()) {
                                mGenerateDepositResponse.setBeneficiaryName(verifyResp.getBeneficiaryName());
                            } else if (mGenerateDepositResponse.getBeneficiaryName().isEmpty()
                                    && verifyResp.getBeneficiaryName() != null && !verifyResp.getBeneficiaryName().isEmpty()) {
                                mGenerateDepositResponse.setBeneficiaryName(verifyResp.getBeneficiaryName());
                            }
                            startActivityForResult(new Intent(AEPSDashboardActivity.this, AEPSDepositStatusActivity.class)
                                    .putExtra("data", mGenerateDepositResponse)
                                    .putExtra("txnAmt", et_amount.getText().toString().trim())
                                    .putExtra("accountNum", et_account_num.getText().toString().trim()), INTENT_DEPOSIT_STATUS);


                        }
                    });

        });


        dialogVerify.show();
    }

    @Override
    protected void onPause() {
        if (mGetLocation != null) {
            mGetLocation.onPause();
        }
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
