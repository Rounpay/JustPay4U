package com.solution.app.justpay4u.Networking.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.Api.Fintech.Object.SettlementAccountData;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OperatorListResponse;
import com.solution.app.justpay4u.Api.Networking.Request.BankTransferRequest;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApiNetworkingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.NetworkingEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownModel;
import com.solution.app.justpay4u.Util.Utility;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class MoneyTransferNetworkingActivity extends AppCompatActivity implements View.OnClickListener {


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
    private String deviceId, deviceSerialNum;
    private SettlementAccountData mAccountData;
    private int mWalletId;
    private int mOpTypeId;
    private int modeOpId;
    private CustomAlertDialog mCustomAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_networking_money_transfer);
       /* new Handler(Looper.getMainLooper()).post(() -> {

        });*/
        mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
        mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
        deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
        deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        mDropDownDialog = new DropDownDialog(this);
        mCustomAlertDialog = new CustomAlertDialog(this);
        mAccountData = getIntent().getParcelableExtra("AccountData");
        mWalletId = getIntent().getIntExtra("WalletId", 0);
        mOpTypeId = getIntent().getIntExtra("OpTypeId", 0);
        //operatorList();
        findViews();
        getOperator(mOpTypeId, ApiFintechUtilMethods.INSTANCE.getOperatorListResponse(mAppPreferences));
    }

    private void operatorList() {
        ApiFintechUtilMethods.INSTANCE.OperatorList(this, deviceId, deviceSerialNum, mAppPreferences, object -> {
            OperatorListResponse operatorListResponse = (OperatorListResponse) object;
            if (operatorListResponse != null && operatorListResponse.getOperators() != null && operatorListResponse.getOperators().size() > 0) {
                getOperator(mOpTypeId, operatorListResponse);
            }
        });
    }

    private void findViews() {

        bankTv = findViewById(R.id.bankTv);

        accountNumberTv = findViewById(R.id.accountNumberTv);

        ifscTv = findViewById(R.id.ifscTv);

        acHolderNameTv = findViewById(R.id.acHolderNameTv);

        paymentModeChooserView = findViewById(R.id.paymentModeChooserView);
        paymentModeTv = findViewById(R.id.paymentModeTv);
        tranferAmountEt = findViewById(R.id.tranferAmountEt);
        btTransfer = findViewById(R.id.bt_transfer);
        paymentModeChooserView.setOnClickListener(this);
        btTransfer.setOnClickListener(this);

        if (mAccountData != null) {
            bankTv.setText(mAccountData.getBankName() + "");
            accountNumberTv.setText(mAccountData.getAccountNumber() + "");
            ifscTv.setText(mAccountData.getIfsc() + "");
            acHolderNameTv.setText(mAccountData.getAccountHolder() + "");
        }
    }

    private void getOperator(int mOpTypeId, OperatorListResponse mOperatorListResponse) {
        if (mOperatorListResponse != null && mOperatorListResponse.getOperators() != null && mOperatorListResponse.getOperators().size() > 0) {
            int count = 0;
            for (OperatorList op : mOperatorListResponse.getOperators()) {
                if (op.getOpType() == mOpTypeId) {
                    if(op.isActive() && op.getOid()==771 && op.getName().toLowerCase().contains("imps")){
                        if (count == 0) {
                            count = count + 1;
                            selectedModePos = 0;
                            paymentModeTv.setText(op.getName() + "");
                            modeOpId = op.getOid();
                        }
                        dropDownModels.add(new DropDownModel(op.getName(), op));
                    }else{
                        //Toast.makeText(this, "Operator is not active", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        }
        else {
            String deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            String deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            ApiFintechUtilMethods.INSTANCE.OperatorList(this, deviceId, deviceSerialNum, mAppPreferences, object -> {
                OperatorListResponse operatorListResponse = (OperatorListResponse) object;
                if (operatorListResponse != null && operatorListResponse.getOperators() != null && operatorListResponse.getOperators().size() > 0) {
                    getOperator(mOpTypeId, operatorListResponse);
                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        if (v == paymentModeChooserView) {
            if(dropDownModels.size()==0){
                ///
            }else{
                mDropDownDialog.showDropDownPopup(v, selectedModePos, dropDownModels, (clickPosition, value, object) -> {
                    selectedModePos = clickPosition;
                    paymentModeTv.setText(value + "");
                    modeOpId = ((OperatorList) object).getOid();
                });
            }



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
        try {
            double amount = Double.parseDouble(tranferAmountEt.getText().toString().trim());
            if (amount < 100 ){
                tranferAmountEt.setError("The amount must be 100 or higher");
                tranferAmountEt.requestFocus();
                return;
            }
            confirmationDialog(amount, 0, 0);
        } catch (NumberFormatException nfe) {
            ApiFintechUtilMethods.INSTANCE.Error(this, nfe.getMessage() + "");
        }
       /* if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
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

        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }*/
    }

    public void confirmationDialog(double amt, double charged, double totalAmount) {

        if (dialog != null && dialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_networkimg_money_transfer_confiormation, null);

        View pinPassView = view.findViewById(R.id.pinPassView);
        View dmtChargeView = view.findViewById(R.id.dmtChargeView);
        dmtChargeView.setVisibility(View.GONE);
        View totalAmountView = view.findViewById(R.id.totalAmountView);
        totalAmountView.setVisibility(View.GONE);
        EditText pinPassEt = view.findViewById(R.id.pinPassEt);
        if (mAccountData != null) {

            ((TextView) view.findViewById(R.id.bankName)).setText(mAccountData.getBankName() + " (" + mAccountData.getIfsc() + ")");
            ((TextView) view.findViewById(R.id.accountNo)).setText(mAccountData.getAccountNumber());
            ((TextView) view.findViewById(R.id.name)).setText(mAccountData.getAccountHolder());
        }

        ((TextView) view.findViewById(R.id.mode)).setText(paymentModeTv.getText().toString());

        ((TextView) view.findViewById(R.id.amountTv)).setText(Utility.INSTANCE.formatedAmountWithRupees(amt + ""));
        ((TextView) view.findViewById(R.id.dmtChargeTv)).setText(Utility.INSTANCE.formatedAmountWithRupees(charged + ""));
        ((TextView) view.findViewById(R.id.totalAmtTv)).setText(Utility.INSTANCE.formatedAmountWithRupees(totalAmount + ""));

        dialog = new Dialog(this, R.style.alert_dialog_light);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pinPassView.setVisibility(View.GONE);
       /* if (mLoginDataResponse.getData().getIsPinRequired() || mLoginDataResponse.getData().getIsDoubleFactor()) {
            pinPassView.setVisibility(View.VISIBLE);
        } else {
            pinPassView.setVisibility(View.GONE);
        }*/
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
                TransferMoney(this);

            } else {
                ApiFintechUtilMethods.INSTANCE.NetworkError(this);
            }
        });
        dialog.show();

    }

    private void TransferMoney(Activity context) {
        if (ApiNetworkingUtilMethods.INSTANCE.isVpnConnected(this)) {
            mCustomAlertDialog.ErrorVPN(this);
        } else {
            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

                if (!loader.isShowing()) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                }
                try {

                    NetworkingEndPointInterface git = ApiClient.getClient().create(NetworkingEndPointInterface.class);
                    Call<BasicResponse> call = git.BankTranferServiceMLM(new BankTransferRequest(new BasicRequest(mLoginDataResponse.getData().getUserID(),
                            mLoginDataResponse.getData().getLoginTypeID(),
                            ApplicationConstant.INSTANCE.APP_ID, deviceId,
                            "", BuildConfig.VERSION_NAME, deviceSerialNum, mLoginDataResponse.getData().getSessionID(),
                            mLoginDataResponse.getData().getSession()), new BankTransferRequest(tranferAmountEt.getText().toString().trim()
                            , mAccountData.getId(),
                            mWalletId, modeOpId)));

                    call.enqueue(new Callback<BasicResponse>() {
                        @Override
                        public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                            try {
                                if (loader != null) {
                                    if (loader.isShowing()) {
                                        loader.dismiss();
                                    }
                                }
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        if (response.body().getStatuscode() == 1) {
                                            setResult(RESULT_OK);
                                            ApiFintechUtilMethods.INSTANCE.Successful(context, response.body().getMsg() + "");
                                        } else if (response.body().getStatuscode()==2) {
                                            setResult(RESULT_OK);
                                            ApiFintechUtilMethods.INSTANCE.Processing(context, response.body().getMsg() + "");
                                        } else {
                                            if (!response.body().isVersionValid()) {
                                                ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                            } else {
                                                ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                            }
                                        }
                                    }
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                                }
                            } catch (Exception e) {
                                if (loader != null) {
                                    if (loader.isShowing()) {
                                        loader.dismiss();
                                    }
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<BasicResponse> call, Throwable t) {
                            if (loader != null) {
                                if (loader.isShowing()) {
                                    loader.dismiss();
                                }
                            }
                            try {
                                if (t instanceof UnknownHostException || t instanceof IOException) {
                                    ApiFintechUtilMethods.INSTANCE.NetworkError(context);

                                } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                    ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");

                                } else {

                                    if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                        ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
                                    }
                                }

                            } catch (IllegalStateException ise) {

                                ApiFintechUtilMethods.INSTANCE.Error(context, ise.getMessage());

                            }

                        }
                    });

                } catch (Exception e) {

                    e.printStackTrace();
                }

            } else {

                ApiFintechUtilMethods.INSTANCE.NetworkError(this);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
