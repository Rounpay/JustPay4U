package com.solution.app.justpay4u.Fintech.MoveToWallet.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Object.MoveToWalletMappings;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.Api.Fintech.Object.SlabRangeDetail;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.SlabRangeDetailRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.GetUserResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OperatorListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.SlabRangeDetailResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.WalletTypeResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;

import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.Activities.SettlementBankListActivity;
import com.solution.app.justpay4u.Fintech.MoveToWallet.Adapter.SettlmentChargeDetailAdapter;
import com.solution.app.justpay4u.Fintech.MoveToWallet.Adapter.WalletBalanceAdapter;
import com.solution.app.justpay4u.Fintech.MoveToWallet.Model.MoveToWalletRequest;
import com.solution.app.justpay4u.Fintech.MoveToWallet.Model.TransactionModeResponse;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoveToWalletActivity extends AppCompatActivity {
    public LinearLayout ll_TransactionMode;
    View moveToChooserView, moveFromChooserView, chargesView, modeTypeChooserView;
    RecyclerView walletBalance;
    CustomLoader loader;
    EditText amount;
    TextView submit;
    View sourceView;
    String actiontype = "1";
    String TransMode = "NEFT";
    ArrayList<BalanceData> balanceTypes = new ArrayList<>();
    ArrayList<DropDownModel> walletTypeFromDropDown = new ArrayList<>();
    HashMap<Integer, ArrayList<DropDownModel>> toMap = new HashMap<>();

    ArrayList<DropDownModel> modeTypeDropDown = new ArrayList<>();
    ArrayList<DropDownModel> walletTypeToDropDown = new ArrayList<>();
    DropDownDialog mDropDownDialog;
    Gson gson;
    View updateBankView;
    private WalletTypeResponse mWalletTypeResponse;
    private LoginResponse mLoginDataResponse;
    private TextView chooseMoveTo, chooseMoveFrom, chooseMode;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_move_to_wallet);
            getIds();
            mDropDownDialog = new DropDownDialog(this);
            balanceTypes = getIntent().getParcelableArrayListExtra("items");
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            gson = new Gson();
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            mGetUserResponse = ApiFintechUtilMethods.INSTANCE.getUserDetailResponse(mAppPreferences);
            adaptDataInRecyclerView(walletBalance);

            new Handler(Looper.getMainLooper()).post(() -> {
                if (mGetUserResponse != null && mGetUserResponse.getUserInfo() != null) {
                    setBankData(mGetUserResponse);
                }
                mWalletTypeResponse = ApiFintechUtilMethods.INSTANCE.getWalletTypeResponse(mAppPreferences);
                setFromToData();
                setListeners();
                getUserDetail();
            });
        });

    }

    private void setBankData(GetUserResponse mGetUserResponse) {
        beneName.setText(mGetUserResponse.getUserInfo().getAccountName() + "");
        beneBank.setText(mGetUserResponse.getUserInfo().getBankName() + "");
        beneAccountNumber.setText(mGetUserResponse.getUserInfo().getAccountNumber() + "");
        beneIFSC.setText(mGetUserResponse.getUserInfo().getIfsc() + "");
    }

    private void getIds() {
        sourceView = findViewById(R.id.sourceView);
        moveFromChooserView = findViewById(R.id.moveFromChooserView);
        moveToChooserView = findViewById(R.id.moveToChooserView);
        modeTypeChooserView = findViewById(R.id.modeTypeChooserView);
        beneName = findViewById(R.id.beneName);
        beneBank = findViewById(R.id.beneBank);
        beneAccountNumber = findViewById(R.id.beneAccountNumber);
        beneIFSC = findViewById(R.id.beneIFSC);
        updateBankView = findViewById(R.id.updateBankView);
        chooseMoveFrom = (TextView) findViewById(R.id.chooseMoveFrom);
        chooseMoveTo = (TextView) findViewById(R.id.chooseMoveTo);
        chooseMode = (TextView) findViewById(R.id.chooseMode);
        walletBalance = (RecyclerView) findViewById(R.id.walletBalance);
        ll_TransactionMode = (LinearLayout) findViewById(R.id.ll_TransactionMode);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        amount = (EditText) findViewById(R.id.amount);
        submit = findViewById(R.id.submit);
        chargesView = findViewById(R.id.chargesView);
    }


    private void setListeners() {

        updateBankView.setOnClickListener(v ->
                startActivityForResult(new Intent(MoveToWalletActivity.this, SettlementBankListActivity.class /*UpdateBankActivity.class*/)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_BANK_UPDATE));

        moveFromChooserView.setOnClickListener(v ->
                mDropDownDialog.showDropDownPopup(v, selectedFromWalletPos, walletTypeFromDropDown, (clickPosition, value, object) -> {
                    if (selectedFromWalletPos != clickPosition) {
                        setFromData(clickPosition, (MoveToWalletMappings) object, value);
                    }

                }));

        moveToChooserView.setOnClickListener(v ->
                mDropDownDialog.showDropDownPopup(v, selectedToWalletPos, walletTypeToDropDown, (clickPosition, value, object) -> {
                    if (selectedToWalletPos != clickPosition) {
                        setToData(clickPosition, (MoveToWalletMappings) object, value);

                    }

                }));

        modeTypeChooserView.setOnClickListener(v ->
                mDropDownDialog.showDropDownPopup(v, selectedModePos, modeTypeDropDown, (clickPosition, value, object) -> {
                    selectedModePos = clickPosition;
                    chooseMode.setText(value + "");
                    TransMode = value + "";
                    OperatorList modeTxn = (OperatorList) object;
                    if (TransMode != null && !TransMode.isEmpty()) {
                        selectedOid = modeTxn.getOid();
                        chargesView.setVisibility(View.VISIBLE);

                    } else {
                        selectedOid = 0;
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
            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(MoveToWalletActivity.this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                MoveToWallet(MoveToWalletActivity.this, actiontype, selectedOid, amount.getText().toString());
            } else {
                ApiFintechUtilMethods.INSTANCE.NetworkError(MoveToWalletActivity.this);
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
                        mLoginDataResponse.getData().getUserID() + "", mLoginDataResponse.getData().getLoginTypeID(),
                        ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME,
                        deviceSerialNum, mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));
                call.enqueue(new Callback<SlabRangeDetailResponse>() {

                    @Override
                    public void onResponse(Call<SlabRangeDetailResponse> call, Response<SlabRangeDetailResponse> response) {

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
            WalletBalanceAdapter mAdapter = new WalletBalanceAdapter(MoveToWalletActivity.this, balanceTypes);
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

                String fromName = /*"From " +*/ mMoveToWalletMappings.getFromWalletType().replace("Mini", "") /*+ " Wallet"*/;
                String toName = "";
                if (mMoveToWalletMappings.getToWalletID() == 3) {
                    toName = /*"To " + */mMoveToWalletMappings.getToWalletType() /*+ " Account"*/;
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

            if (walletTypeFromDropDown.size() == 1) {
                sourceView.setVisibility(View.GONE);
            } else {
                sourceView.setVisibility(View.VISIBLE);
            }
            if (walletTypeToDropDown.size() == 0 && toMap.containsKey(selectedFromWalletId)) {
                walletTypeToDropDown = toMap.get(selectedFromWalletId);
            }

        } else {
            ApiFintechUtilMethods.INSTANCE.WalletType(MoveToWalletActivity.this, mLoginDataResponse, mAppPreferences, object -> {
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
            mtwID = moveToWalletMappings.getId();
            actiontype = moveToWalletMappings.getFromWalletID() + "";
            if (moveToWalletMappings.getToWalletID() == 3) {
                selectedOid = 0;
                operatorListResponse = ApiFintechUtilMethods.INSTANCE.getOperatorListResponse(mAppPreferences);
                getOperator(42);
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
                            chargesView.setVisibility(View.VISIBLE);

                        } else {
                            selectedOid = 0;
                        }

                        count++;
                    }
                }
            }
            ll_TransactionMode.setVisibility(View.VISIBLE);

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


    public void MoveToWallet(final Activity context, String actiontype, int oid, String amount) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<TransactionModeResponse> call = git.MoveToWallet(new MoveToWalletRequest(ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    mLoginDataResponse.getData().getLoginTypeID(), "",
                    deviceSerialNum,
                    mLoginDataResponse.getData().getSession(),
                    mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getUserID() + "",
                    BuildConfig.VERSION_NAME, actiontype, amount, oid, mtwID));
            call.enqueue(new Callback<TransactionModeResponse>() {
                @Override
                public void onResponse(Call<TransactionModeResponse> call, Response<TransactionModeResponse> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode().equalsIgnoreCase("1")) {
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
    }


    public void getUserDetail() {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetUserResponse> call = git.GetProfile(new BasicRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
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
