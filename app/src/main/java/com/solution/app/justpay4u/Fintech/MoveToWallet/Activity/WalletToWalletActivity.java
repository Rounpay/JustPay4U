package com.solution.app.justpay4u.Fintech.MoveToWallet.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.solution.app.justpay4u.Api.Fintech.Object.AllowedWallet;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Object.MoveToWalletMappings;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.Api.Fintech.Object.SlabRangeDetail;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.SlabRangeDetailRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetUserResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OperatorListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.SlabRangeDetailResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.WalletTypeResponse;
import com.solution.app.justpay4u.Api.Networking.Request.FindUserDetailsByIdRequest;
import com.solution.app.justpay4u.Api.Networking.Request.WalletToWalletRequest;
import com.solution.app.justpay4u.Api.Networking.Response.FindUserDetailsByIdResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApiNetworkingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.ApiHits.NetworkingEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity;
import com.solution.app.justpay4u.Fintech.MoveToWallet.Adapter.SettlmentChargeDetailAdapter;
import com.solution.app.justpay4u.Fintech.MoveToWallet.Adapter.WalletBalanceAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownModel;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletToWalletActivity extends AppCompatActivity {

    private int fromWalletId, toWalletId;

    private ArrayList<AllowedWallet> walletList = new ArrayList<>();
    private String beneficiaryUserId;
    public LinearLayout ll_TransactionMode;
    View receiverDetailView, receiverIdView, searchIdIv, moveToChooserView, moveFromChooserView, chargesView, modeTypeChooserView;
    RecyclerView walletBalance;
    CustomLoader loader;
    EditText amount, receiverIdEt, remark;
    TextView submit, receiverNameTv, receiverIdViewLabel;
    View sourceView, destView;
    String actiontype = "1";
    String TransMode = "NEFT";
    ArrayList<BalanceData> balanceTypes = new ArrayList<>();
    ArrayList<DropDownModel> walletTypeFromDropDown = new ArrayList<>();
    HashMap<Integer, ArrayList<DropDownModel>> toMap = new HashMap<>();
    public HashMap<Integer, BalanceData> balanceDataMap = new HashMap<>();
    ArrayList<DropDownModel> modeTypeDropDown = new ArrayList<>();
    ArrayList<DropDownModel> walletTypeToDropDown = new ArrayList<>();
    DropDownDialog mDropDownDialog;
    Gson gson;
    View updateBankView, minMaxView;
    private WalletTypeResponse mWalletTypeResponse;
    private LoginResponse mLoginDataResponse;
    private TextView chooseMoveTo, chooseMoveFrom, chooseMode, minTransfer, maxTransfer;
    private int selectedFromWalletPos = -1, selectedToWalletPos = -1, selectedModePos;
    private int selectedFromWalletId = -1, selectedToWalletId = -1;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private GetUserResponse mGetUserResponse;
    private TextView beneName, beneBank, beneAccountNumber, beneIFSC;
    private int INTENT_BANK_UPDATE = 1234;
    private int mtwID;
    private int selectedOid;
    private OperatorListResponse operatorListResponse;
    private AlertDialog alertDialog;
    private BalanceResponse balanceCheckResponse;
    private BalanceData intentBalanceData;
    private String receiverUserId;
    private boolean isUserDetailApiCalled;
    private String userId;
    private double maxTransferAmount, minTransferAmount;
    private CustomAlertDialog mCustomAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Toast.makeText(this, "WalletToWalletActivity", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_wallet_to_wallet);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            mDropDownDialog = new DropDownDialog(this);
            balanceTypes = getIntent().getParcelableArrayListExtra("items");
            intentBalanceData = getIntent().getParcelableExtra("Data");
            fromWalletId = getIntent().getIntExtra("FromWalletId", 0);
            walletList = getIntent().getParcelableArrayListExtra("WalletList");
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            gson = new Gson();
            mCustomAlertDialog = new CustomAlertDialog(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            userId = mLoginDataResponse.getData().getUserID();
            mGetUserResponse = ApiFintechUtilMethods.INSTANCE.getUserDetailResponse(mAppPreferences);

            getIds();


            new Handler(Looper.getMainLooper()).post(() -> {

                if (intentBalanceData != null) {
                    if (intentBalanceData.getWalletTransferType() == 1) {
                        receiverIdViewLabel.setVisibility(View.GONE);
                        receiverIdView.setVisibility(View.GONE);
                        // receiverDetailView.setVisibility(View.GONE);
                    } else {
                        receiverIdViewLabel.setVisibility(View.VISIBLE);
                        receiverIdView.setVisibility(View.VISIBLE);
                        //receiverDetailView.setVisibility(View.VISIBLE);
                    }
                }
                if (balanceDataMap != null && balanceDataMap.size() > 0 && balanceDataMap.get(selectedToWalletId).getWalletTransferType() == 2) {
                    receiverIdEt.setText("");
                } else {
//                    receiverIdEt.setText(userId + "");
                    receiverUserId = userId;
                    setReceiverDetails(
                            mLoginDataResponse.getData().getName() + "", mLoginDataResponse.getData().getMobileNo() + "",
                            mLoginDataResponse.getData().getEmailID() + "");
                    adaptDataInRecyclerView(walletBalance);

                }

                mWalletTypeResponse = ApiFintechUtilMethods.INSTANCE.getWalletTypeResponse(mAppPreferences);
                setFromToData();
                setListeners();
                if (mGetUserResponse != null && mGetUserResponse.getUserInfo() != null) {
                    setBankData(mGetUserResponse);
                }

            });
        });

    }

    /* void setReceiverDetails(String name, String mob, String email) {

         receiverNameTv.setText(Html.fromHtml("<font color='#c9c6c3'><b>Name :</b></font> " + name
                 + "<br/><font color='#c9c6c3'><b>Mobile No :</b></font> " + (mob.length() >= 3 ? "*******" + mob.substring(mob.length() - 3) : mob)
                 + "<br/><font color='#c9c6c3'><b>Email Id :</b></font> " + email.replaceAll("(?<=.{3}).(?=.*@)", "*")));
     }*/

    void setReceiverDetails(String name, String mob, String email) {

        receiverNameTv.setText(Html.fromHtml("<font color='#c9c6c3'><b>Name :</b></font> " + name
                + "<br/><font color='#c9c6c3'><b>Mobile No :</b></font> " + mob
                + "<br/><font color='#c9c6c3'><b>Email Id :</b></font> " + email));
    }

    String getX(int length) {
        String value = "";
        for (int i = 0; i < length; i++) {
            value = value + "X";
        }
        return value;
    }

    private void setBankData(GetUserResponse mGetUserResponse) {
        beneName.setText(mGetUserResponse.getUserInfo().getAccountName() + "");
        beneBank.setText(mGetUserResponse.getUserInfo().getBankName() + "");
        beneAccountNumber.setText(mGetUserResponse.getUserInfo().getAccountNumber() + "");
        beneIFSC.setText(mGetUserResponse.getUserInfo().getIfsc() + "");
    }

    private void getIds() {
        sourceView = findViewById(R.id.sourceView);
        destView = findViewById(R.id.destView);
        minMaxView = findViewById(R.id.minMaxView);
        minTransfer = findViewById(R.id.minTransfer);
        maxTransfer = findViewById(R.id.maxTransfer);
        moveFromChooserView = findViewById(R.id.moveFromChooserView);
        receiverIdViewLabel = findViewById(R.id.receiverIdViewLabel);
        receiverIdEt = findViewById(R.id.receiverId);
        searchIdIv = findViewById(R.id.searchId);
        receiverNameTv = findViewById(R.id.receiverName);
        receiverIdView = findViewById(R.id.receiverIdView);
        receiverDetailView = findViewById(R.id.receiverDetailView);
        moveToChooserView = findViewById(R.id.moveToChooserView);
        modeTypeChooserView = findViewById(R.id.modeTypeChooserView);
        beneName = findViewById(R.id.beneName);
        beneBank = findViewById(R.id.beneBank);
        beneAccountNumber = findViewById(R.id.beneAccountNumber);
        beneIFSC = findViewById(R.id.beneIFSC);
        updateBankView = findViewById(R.id.updateBankView);
        chooseMoveFrom = findViewById(R.id.chooseMoveFrom);
        chooseMoveTo = findViewById(R.id.chooseMoveTo);
        chooseMode = findViewById(R.id.chooseMode);
        ll_TransactionMode = findViewById(R.id.ll_TransactionMode);
        amount = findViewById(R.id.amount);
        remark = findViewById(R.id.remark);
        submit = findViewById(R.id.submit);
        chargesView = findViewById(R.id.chargesView);
        walletBalance = findViewById(R.id.walletBalance);
        walletBalance.setLayoutManager(new LinearLayoutManager(this));
    }


    private void setListeners() {

        receiverIdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (receiverDetailView.getVisibility() == View.VISIBLE) {
                    receiverDetailView.setVisibility(View.GONE);
                    searchIdIv.setVisibility(View.VISIBLE);
                    beneficiaryUserId = "0";
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchIdIv.setOnClickListener(view -> {
            if (receiverIdEt.getText().toString().isEmpty()) {
                receiverIdEt.setError("Please enter userid or mobile number");
                receiverIdEt.requestFocus();
                return;
            }
            getUserDetailById(this);
        });
        updateBankView.setOnClickListener(v ->
                startActivityForResult(new Intent(WalletToWalletActivity.this, SettlementBankListActivity.class /*UpdateBankActivity.class*/)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_BANK_UPDATE));

        moveFromChooserView.setOnClickListener(v ->
                mDropDownDialog.showDropDownPopup(v, selectedFromWalletPos, walletTypeFromDropDown, (clickPosition, value, object) -> {
                    if (selectedFromWalletPos != clickPosition) {
//                        if (intentBalanceData.getWalletTransferType()==1){
//                            chooseMoveFrom.setText(clickPosition);
                    } else {
                        setFromData(clickPosition, (MoveToWalletMappings) object, value);
                    }


                }));

        moveToChooserView.setOnClickListener(v ->
                mDropDownDialog.showDropDownPopup(v, selectedToWalletPos, walletTypeToDropDown, (clickPosition, value, object) -> {
                    if (selectedToWalletPos != clickPosition) {
                        setToData(clickPosition, (MoveToWalletMappings) object, value);

                    }

                }));

        moveToChooserView.setOnClickListener(view -> {
            if (walletList != null && walletList.size() > 0) {
                mDropDownDialog.showDropDownPopup(view, selectedToWalletPos, 1, walletList,
                        (clickPosition, item) -> {
                            chooseMoveTo.setText(item.getToWalletName() + "");
                            toWalletId = item.getToWalletId();
                            // setBuisnessTypeData(item);
                        });
            } else {

                Toast.makeText(this, "Wallet is not available", Toast.LENGTH_SHORT).show();
            }
        });
        if (walletList != null && walletList.size() > 0) {
            if (walletList.size() > 1) {
                destView.setVisibility(View.VISIBLE);
//                coinView.setVisibility(View.VISIBLE);
//                dropIcon.setVisibility(View.VISIBLE);
                moveToChooserView.setClickable(true);
            } else {
                chooseMoveTo.setText(walletList.get(0).getToWalletName() + "");
                toWalletId = walletList.get(0).getToWalletId();
//                coinView.setVisibility(View.VISIBLE);
//                dropIcon.setVisibility(View.GONE);
                moveToChooserView.setClickable(false);
            }

        } else {
            moveToChooserView.setVisibility(View.VISIBLE);
        }


//        moveToChooserView.setOnClickListener(view -> {
//            if (walletList != null && walletList.size() > 0) {
//                mDropDownDialog.showDropDownPopup(view, selectedModePos, 1, walletList,
//                        (clickPosition, item) -> {
//                            chooseMode.setText(item.getToWalletName() + "");
//                            toWalletId = item.getToWalletId();
//                            // setBuisnessTypeData(item);
//                        });
//            } else {
////
//                Toast.makeText(this, "Wallet is not available", Toast.LENGTH_SHORT).show();
//            }
//        });
//        if (walletList != null && walletList.size() > 0) {
//            if (walletList.size() > 1) {
//                modeTypeChooserView.setVisibility(View.VISIBLE);
//
//            } else {
//                chooseMode.setText(walletList.get(0).getToWalletName() + "");
//                toWalletId = walletList.get(0).getToWalletId();
//                modeTypeChooserView.setVisibility(View.VISIBLE);
//
//            }
//
//        } else {
//            modeTypeChooserView.setVisibility(View.VISIBLE);
//        }

//        modeTypeChooserView.setOnClickListener(v ->
//                mDropDownDialog.showDropDownPopup(v, selectedModePos, modeTypeDropDown, (clickPosition, value, object) -> {
//                    selectedModePos = clickPosition;
//                    chooseMode.setText(value + "");
//                    TransMode = value + "";
////                    OperatorList modeTxn = (OperatorList) object;
////                    if (TransMode != null && !TransMode.isEmpty()) {
////                        selectedOid = modeTxn.getOid();
//////                        chargesView.setVisibility(View.VISIBLE);
////                    } else {
////                        selectedOid = 0;
////                    }
//                }));


        submit.setOnClickListener(v -> {
            receiverIdEt.setError(null);
            chooseMoveFrom.setError(null);
            chooseMoveTo.setError(null);
            amount.setError(null);
            if (receiverIdView.getVisibility() == View.VISIBLE && receiverIdEt.getText().toString().isEmpty()) {
                receiverIdEt.setError(getString(R.string.err_empty_field));
                receiverIdEt.requestFocus();
                return;
            } else if (!receiverIdEt.getText().toString().isEmpty() && receiverDetailView.getVisibility() == View.GONE) {
                receiverIdEt.setError("Please enter registerd receiver id");
                receiverIdEt.requestFocus();
                return;
            } else if (sourceView.getVisibility() == View.VISIBLE && chooseMoveFrom.getText().toString().isEmpty()) {
                chooseMoveFrom.setError(getString(R.string.err_empty_field));
                chooseMoveFrom.requestFocus();
                return;
            } else if (destView.getVisibility() == View.VISIBLE && chooseMoveTo.getText().toString().isEmpty()) {
                chooseMoveTo.setError(getString(R.string.err_empty_field));
                chooseMoveTo.requestFocus();
                return;
            } else if (amount.getText().toString().isEmpty()) {
                amount.setError(getString(R.string.err_empty_field));
                amount.requestFocus();
                return;
            } else if (balanceDataMap.get(selectedToWalletId).getWalletTransferType() == 5) {
                userId.equalsIgnoreCase(receiverUserId + "");
            }
            if ((balanceDataMap != null && balanceDataMap.size() > 0 &&
                    balanceDataMap.containsKey(selectedToWalletId)
                    && balanceDataMap.get(selectedToWalletId).getWalletTransferType() == 2 &&
                    userId.equalsIgnoreCase(receiverUserId + ""))) {

                receiverIdEt.setError("Please enter other receiver id, self transaction is not enable for this wallet");
                receiverIdEt.requestFocus();
                return;
            } else if (minTransferAmount != 0 && maxTransferAmount != 0 &&
                    Double.parseDouble(amount.getText().toString().trim()) > maxTransferAmount &&
                    Double.parseDouble(amount.getText().toString().trim()) < minTransferAmount) {
                amount.setError("Amount can't be lower then " + Utility.INSTANCE.formatedAmountWithRupees(minTransferAmount + "") + " or greater then "
                        + Utility.INSTANCE.formatedAmountWithRupees(maxTransferAmount + ""));
                amount.requestFocus();
                return;
            }

            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(WalletToWalletActivity.this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                MoveToWallet(WalletToWalletActivity.this);
            } else {
                ApiFintechUtilMethods.INSTANCE.NetworkError(WalletToWalletActivity.this);
            }

        });

        chargesView.setOnClickListener(view -> HitChargeDetail(this));
    }


    public void HitChargeDetail(Activity context) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            //loader.setCancelable(false);
            //loader.setCanceledOnTouchOutside(false);


            try {
                FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
                Call<SlabRangeDetailResponse> call = git.RealTimeCommission(new SlabRangeDetailRequest(selectedOid,
                        userId + "", mLoginDataResponse.getData().getLoginTypeID(),
                        ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME,
                        deviceSerialNum, mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));
                call.enqueue(new Callback<SlabRangeDetailResponse>() {

                    @Override
                    public void onResponse(Call<SlabRangeDetailResponse> call, retrofit2.Response<SlabRangeDetailResponse> response) {

                        try {
                            if (loader != null) {
                                if (loader.isShowing()) {
                                    loader.dismiss();
                                }
                            }
                            if (response.isSuccessful()) {

                                if (response.body() != null) {
                                    if (response.body().getStatuscode() == 1) {
                                        if (response.body().getData() != null && response.body().getData().size() > 0) {

                                            chargesDialog(response.body().getData());
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(context, "Slab Range Data not found.");
                                        }

                                    } else {
                                        if (response.body().getVersionValid() == false) {
                                            ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                        } else {
                                            if (response.body().getMsg() != null && !response.body().getMsg().isEmpty()) {
                                                ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                            } else {
                                                ApiFintechUtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error) + "");
                                            }
                                        }
                                    }

                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error) + "");
                                }
                            } else {
                                ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (loader != null) {
                                if (loader.isShowing()) {
                                    loader.dismiss();
                                }
                            }
                            ApiFintechUtilMethods.INSTANCE.Error(context, e.getMessage() + "");
                        }

                    }

                    @Override
                    public void onFailure(Call<SlabRangeDetailResponse> call, Throwable t) {

                        try {
                            if (loader != null) {
                                if (loader.isShowing()) {
                                    loader.dismiss();
                                }
                            }
                            ApiFintechUtilMethods.INSTANCE.apiFailureError(context, t);
                            /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                        } catch (IllegalStateException ise) {
                            ApiFintechUtilMethods.INSTANCE.Error(context, ise.getMessage());
                            if (loader != null) {
                                if (loader.isShowing()) {
                                    loader.dismiss();
                                }
                            }
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                if (loader != null) {
                    if (loader.isShowing()) {
                        loader.dismiss();
                    }
                }
                ApiFintechUtilMethods.INSTANCE.Error(context, e.getMessage() + "");
            }


        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    public void chargesDialog(ArrayList<SlabRangeDetail> mSlabRangeDetail) {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                return;
            }
            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this);
            alertDialog = dialogBuilder.create();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_settlement_charge_details, null);
            alertDialog.setView(dialogView);


            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView operator = dialogView.findViewById(R.id.operator);
            TextView maxMin = dialogView.findViewById(R.id.maxMin);
            TextView maxComTitle = dialogView.findViewById(R.id.maxComTitle);
            TextView comSurTitle = dialogView.findViewById(R.id.comSurTitle);
            TextView fixedChargedTitle = dialogView.findViewById(R.id.fixedChargedTitle);
            ImageView iconIv = dialogView.findViewById(R.id.icon);
            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new SettlmentChargeDetailAdapter(mSlabRangeDetail, this));
            /*operator.setText(operatorValue);
            maxMin.setText("Range : " + maxMinValue);
*/
           /* if (mSlabRangeDetail.get(0).getDmrModelID() == 2 || mSlabRangeDetail.get(0).getDmrModelID() == 3 ||
                    mSlabRangeDetail.get(0).getDmrModelID() == 4) {
                fixedChargedTitle.setVisibility(View.VISIBLE);
                maxComTitle.setVisibility(View.GONE);
            } else if (mSlabRangeDetail.get(0).getDmrModelID() == 1) {
                fixedChargedTitle.setVisibility(View.GONE);
                maxComTitle.setVisibility(View.GONE);
            } else {
                fixedChargedTitle.setVisibility(View.GONE);
                maxComTitle.setVisibility(View.VISIBLE);
            }*/

            /*if (mSlabRangeDetail.get(0).isCommType()) {
                comSurTitle.setText("Surcharge");
            } else {
                comSurTitle.setText("Commission");
            }*/

            /*Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + oid + ".png")
                    .apply(requestOptions)
                    .into(iconIv);*/
            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });


            alertDialog.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }

    private void adaptDataInRecyclerView(RecyclerView recyclerView) {

        if (balanceTypes != null && balanceTypes.size() > 0) {
            WalletBalanceAdapter mAdapter = new WalletBalanceAdapter(WalletToWalletActivity.this, balanceTypes);
            recyclerView.setAdapter(mAdapter);
        } else {
            balanceCheckResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                    && balanceCheckResponse.getBalanceData().size() > 0) {

                balanceTypes = balanceCheckResponse.getBalanceData();
                if (balanceTypes != null && balanceTypes.size() > 0) {
                    adaptDataInRecyclerView(recyclerView);
                }
            } else {
                ApiFintechUtilMethods.INSTANCE.Balancecheck(this, null, mLoginDataResponse,
                        deviceId, deviceSerialNum,
                        mAppPreferences, null, object -> {
                            balanceCheckResponse = (BalanceResponse) object;
                            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                    && balanceCheckResponse.getBalanceData().size() > 0) {
                                balanceTypes = balanceCheckResponse.getBalanceData();
                                if (balanceTypes != null && balanceTypes.size() > 0) {
                                    balanceDataMap.clear();
                                    adaptDataInRecyclerView(recyclerView);
                                }
                            }
                        });
            }
        }
    }


    void setFromToData() {
        if (mWalletTypeResponse != null && mWalletTypeResponse.getMoveToWalletMappings() != null &&
                mWalletTypeResponse.getMoveToWalletMappings().size() > 0) {

            ArrayList<Integer> addedFromWalletId = new ArrayList<>();
            ArrayList<DropDownModel> walletTypeToDropDownTemp = new ArrayList<>();
            for (MoveToWalletMappings mMoveToWalletMappings : mWalletTypeResponse.getMoveToWalletMappings()) {

                String fromName = /*"From " +*/ mMoveToWalletMappings.getFromWalletType().replace("Mini", "") /*+ " Wallet"*/;
                String toName = "";
                if (mMoveToWalletMappings.getToWalletID() == 3) {
                    toName = /*"To " + */mMoveToWalletMappings.getToWalletType()/* + " Account"*/;
                } else {
                    toName = /*"To " +*/ mMoveToWalletMappings.getToWalletType() /*+ " Wallet"*/;
                }

                if (!addedFromWalletId.contains(mMoveToWalletMappings.getFromWalletID())) {
                    walletTypeFromDropDown.add(new DropDownModel(fromName, mMoveToWalletMappings));
                    walletTypeToDropDownTemp = new ArrayList<>();
                }

                walletTypeToDropDownTemp.add(new DropDownModel(toName, mMoveToWalletMappings));
                toMap.put(mMoveToWalletMappings.getFromWalletID(), walletTypeToDropDownTemp);


                if (addedFromWalletId.size() == 0) {
                    setFromData(0, mMoveToWalletMappings, fromName);
                    setToData(0, mMoveToWalletMappings, toName);
                }

                addedFromWalletId.add(mMoveToWalletMappings.getFromWalletID());
            }

            if (walletList.size() == 1) {
                sourceView.setVisibility(View.GONE);
            } else {
                sourceView.setVisibility(View.GONE);
            }
            if (walletTypeToDropDown.isEmpty() && toMap.containsKey(selectedFromWalletId)) {
                walletTypeToDropDown = toMap.get(selectedFromWalletId);
            }

            if (walletTypeToDropDown.size() == 1) {
                destView.setVisibility(View.GONE);
            } else {
                destView.setVisibility(View.VISIBLE);
            }

        } else {
            ApiFintechUtilMethods.INSTANCE.WalletType(WalletToWalletActivity.this, mLoginDataResponse, mAppPreferences, object -> {
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
            walletTypeToDropDown = toMap.get(selectedFromWalletId);
            chooseMoveTo.setText("");
            selectedToWalletPos = -1;
            selectedToWalletId = -1;
            mtwID = -1;
            ll_TransactionMode.setVisibility(View.GONE);
            chargesView.setVisibility(View.GONE);
            selectedOid = 0;
        }
    }

    void setToData(int clickPosition, MoveToWalletMappings moveToWalletMappings, String value) {
        if (moveToWalletMappings != null) {
            selectedToWalletPos = clickPosition;
            chooseMoveTo.setText(value + "");
            selectedToWalletId = moveToWalletMappings.getToWalletID();
            if (balanceDataMap != null && balanceDataMap.containsKey(selectedToWalletId)) {

                maxTransferAmount = balanceDataMap.get(selectedToWalletId).getMaxTransferAmount();
                minTransferAmount = balanceDataMap.get(selectedToWalletId).getMinTransferAmount();
                if (maxTransferAmount > 0 && minTransferAmount > 0) {
                    minTransfer.setText("Min Amount : " + Utility.INSTANCE.formatedAmountWithRupees(minTransferAmount + ""));
                    maxTransfer.setText("Max Amount : " + Utility.INSTANCE.formatedAmountWithRupees(maxTransferAmount + ""));
                    minMaxView.setVisibility(View.VISIBLE);
                } else {
                    maxTransferAmount = 0;
                    minTransferAmount = 0;
                    minMaxView.setVisibility(View.GONE);
                }
            } else {
                maxTransferAmount = 0;
                minTransferAmount = 0;
                minMaxView.setVisibility(View.GONE);
            }

            mtwID = moveToWalletMappings.getId();
            actiontype = moveToWalletMappings.getFromWalletID() + "";
            if (moveToWalletMappings.getToWalletID() == 3) {
                selectedOid = 0;
                operatorListResponse = ApiFintechUtilMethods.INSTANCE.getOperatorListResponse(mAppPreferences);
                getOperator(42);
                if (!isUserDetailApiCalled) {
                    getUserDetail();
                }
            } else {
                ll_TransactionMode.setVisibility(View.GONE);
                chargesView.setVisibility(View.GONE);
                selectedOid = 0;
            }
        }
    }

    private void getOperator(int op_Type) {

        if (operatorListResponse != null && operatorListResponse.getOperators() != null && operatorListResponse.getOperators().size() > 0) {
            int count = 0;
            modeTypeDropDown.clear();
            for (OperatorList op : operatorListResponse.getOperators()) {
                if (op.getOpType() == op_Type && op.isActive()) {
                    modeTypeDropDown.add(new DropDownModel(op.getName(), op));

                    if (count == 0) {
                        selectedModePos = 0;
                        chooseMode.setText(op.getName() + "");
                        TransMode = op.getName() + "";

                        if (TransMode != null && !TransMode.isEmpty()) {
                            selectedOid = op.getOid();
                            chargesView.setVisibility(View.GONE);

                        } else {
                            selectedOid = 0;
                        }

                        count++;
                    }
                }
            }
//            ll_TransactionMode.setVisibility(View.VISIBLE);

        } else {

            loader.show();
            ApiFintechUtilMethods.INSTANCE.OperatorList(this, loader, deviceId, deviceSerialNum, mAppPreferences, mLoginDataResponse, object -> {
                operatorListResponse = (OperatorListResponse) object;
                if (operatorListResponse != null && operatorListResponse.getOperators() != null && operatorListResponse.getOperators().size() > 0) {
                    getOperator(op_Type);
                }
            });

        }
    }


   /* public void MoveToWallet(final Activity context, String actiontype, int oid, String amount) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<TransactionModeResponse> call = git.MoveToWallet(new MoveToWalletRequest(ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    mLoginDataResponse.getData().getLoginTypeID(), "",
                    deviceSerialNum,
                    mLoginDataResponse.getData().getSession(),
                    mLoginDataResponse.getData().getSessionID(),
                    userId + "",
                    BuildConfig.VERSION_NAME, actiontype, amount, oid, mtwID));
            call.enqueue(new Callback<TransactionModeResponse>() {
                @Override
                public void onResponse(Call<TransactionModeResponse> call, Response<TransactionModeResponse> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                    ApiFintechUtilMethods.INSTANCE.isReadyToUpdateBalance = true;
                                    ApiFintechUtilMethods.INSTANCE.Successfulok(response.body().getMsg(), MoveToWalletActivity.this);
                                } else {
                                    if (response.body().getMsg() != null) {
                                        ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
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
                public void onFailure(Call<TransactionModeResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }

                    ApiFintechUtilMethods.INSTANCE.apiFailureError(MoveToWalletActivity.this, t);

                }
            });

        } catch (Exception ex) {

            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            ApiFintechUtilMethods.INSTANCE.Error(MoveToWalletActivity.this, ex.getMessage() + "");
        }
    }*/

    public void MoveToWallet(final Activity context) {
        try {
            NetworkingEndPointInterface git = ApiClient.getClient().create(NetworkingEndPointInterface.class);

            Call<FindUserDetailsByIdResponse> call = git.WalletToWalletTransfer(new WalletToWalletRequest(new BasicRequest(
                    userId, mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()),
                    new WalletToWalletRequest(userId, receiverUserId + "", fromWalletId,
                            toWalletId, amount.getText().toString(), remark.getText().toString())));
            call.enqueue(new Callback<FindUserDetailsByIdResponse>() {
                @Override
                public void onResponse(Call<FindUserDetailsByIdResponse> call, Response<FindUserDetailsByIdResponse> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    setResult(RESULT_OK);
                                    ApiFintechUtilMethods.INSTANCE.isReadyToUpdateBalance = true;
                                    ApiFintechUtilMethods.INSTANCE.Successfulok(response.body().getMsg(), WalletToWalletActivity.this);
                                } else {
                                    if (response.body().getMsg() != null) {
                                        ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg());
                                    }
                                }
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
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
                public void onFailure(Call<FindUserDetailsByIdResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }

                    ApiFintechUtilMethods.INSTANCE.apiFailureError(WalletToWalletActivity.this, t);

                }
            });

        } catch (Exception ex) {

            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            ApiFintechUtilMethods.INSTANCE.Error(WalletToWalletActivity.this, ex.getMessage() + "");
        }
    }


    public void getUserDetailById(Activity context) {
        try {

            loader.show();
            NetworkingEndPointInterface git = ApiClient.getClient().create(NetworkingEndPointInterface.class);

            Call<FindUserDetailsByIdResponse> call = git.FindUserDetailsById(new FindUserDetailsByIdRequest(new BasicRequest(
                    userId, mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()), receiverIdEt.getText().toString()));

            call.enqueue(new Callback<FindUserDetailsByIdResponse>() {

                @Override
                public void onResponse(Call<FindUserDetailsByIdResponse> call, Response<FindUserDetailsByIdResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            FindUserDetailsByIdResponse mFindUserDetailsByIdResponse = response.body();
                            if (mFindUserDetailsByIdResponse != null) {
                                if (loader != null) {
                                    if (loader.isShowing()) {
                                        loader.dismiss();
                                    }
                                }
                                if (mFindUserDetailsByIdResponse.getStatuscode() == 1) {
                                    if (mFindUserDetailsByIdResponse.getName() != null && !mFindUserDetailsByIdResponse.getName().isEmpty()) {
                                        receiverUserId = mFindUserDetailsByIdResponse.getUserId() + "";
                                        receiverDetailView.setVisibility(View.VISIBLE);
                                        searchIdIv.setVisibility(View.GONE);
                                        receiverIdEt.setError(null);
                                        setReceiverDetails(mFindUserDetailsByIdResponse.getName() + "",
                                                mFindUserDetailsByIdResponse.getMobile() + "",
                                                mFindUserDetailsByIdResponse.getEmailId() + "");
                                    } else {
                                        searchIdIv.setVisibility(View.VISIBLE);
                                        receiverDetailView.setVisibility(View.GONE);
                                        ApiNetworkingUtilMethods.INSTANCE.Error(WalletToWalletActivity.this, "User does not exists");
                                    }

                                } else {
                                    searchIdIv.setVisibility(View.VISIBLE);
                                    receiverDetailView.setVisibility(View.GONE);
                                    ApiNetworkingUtilMethods.INSTANCE.Error(WalletToWalletActivity.this, mFindUserDetailsByIdResponse.getMsg() + "");
                                }

                            } else {
                                if (loader != null) {
                                    if (loader.isShowing()) {
                                        loader.dismiss();
                                    }
                                }
                                searchIdIv.setVisibility(View.VISIBLE);
                                receiverDetailView.setVisibility(View.GONE);
                                ApiNetworkingUtilMethods.INSTANCE.Error(WalletToWalletActivity.this, getString(R.string.some_thing_error));
                            }
                        } else {
                            if (loader != null) {
                                if (loader.isShowing()) {
                                    loader.dismiss();
                                }
                            }
                            searchIdIv.setVisibility(View.VISIBLE);
                            receiverDetailView.setVisibility(View.GONE);
                            ApiNetworkingUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        searchIdIv.setVisibility(View.VISIBLE);
                        receiverDetailView.setVisibility(View.GONE);
                        ApiNetworkingUtilMethods.INSTANCE.Error(WalletToWalletActivity.this, e.getMessage() + "");
                    }
                }

                @Override
                public void onFailure(Call<FindUserDetailsByIdResponse> call, Throwable t) {
                    receiverDetailView.setVisibility(View.GONE);
                    searchIdIv.setVisibility(View.VISIBLE);
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        ApiNetworkingUtilMethods.INSTANCE.Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            searchIdIv.setVisibility(View.VISIBLE);
            receiverDetailView.setVisibility(View.GONE);
            ApiNetworkingUtilMethods.INSTANCE.Error(this, getString(R.string.some_thing_error));
        }

    }

    public void getUserDetail() {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetUserResponse> call = git.GetProfile(new BasicRequest(
                    userId, mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<GetUserResponse>() {

                @Override
                public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            mGetUserResponse = response.body();
                            if (mGetUserResponse != null) {
                                ApiFintechUtilMethods.INSTANCE.mGetUserResponse = mGetUserResponse;
                                if (mGetUserResponse.getStatuscode() == 1) {
                                    isUserDetailApiCalled = true;
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.UserDetailPref, gson.toJson(mGetUserResponse));
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.IsRealApiPref, mGetUserResponse.getUserInfo().isRealAPI());

                                    mLoginDataResponse.getData().setDoubleFactor(mGetUserResponse.getUserInfo().isDoubleFactor());
                                    ApiFintechUtilMethods.INSTANCE.mTempLoginDataResponse = mLoginDataResponse;
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.LoginPref, gson.toJson(mLoginDataResponse));

                                    setBankData(mGetUserResponse);


                                } /*else {

                            ApiUtilMethods.INSTANCE.Error(MoveToWalletActivity.this, mGetUserResponse.getMsg() + "");
                        }*/

                            } /*else {

                        ApiUtilMethods.INSTANCE.Error(MoveToWalletActivity.this, getString(R.string.some_thing_error));
                    }*/
                        } else {
                            /*ApiUtilMethods.INSTANCE.apiErrorHandle(context,response.code(),response.message());*/
                        }
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<GetUserResponse> call, Throwable t) {


                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            //  ApiUtilMethods.INSTANCE.Error(this, getString(R.string.some_thing_error));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_BANK_UPDATE && resultCode == RESULT_OK) {
            getUserDetail();

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