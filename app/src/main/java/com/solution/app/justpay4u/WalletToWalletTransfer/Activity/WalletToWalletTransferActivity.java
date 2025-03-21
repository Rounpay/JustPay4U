package com.solution.app.justpay4u.WalletToWalletTransfer.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.solution.app.justpay4u.Api.Fintech.Object.WalletType;

import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.WalletTypeResponse;

import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.WalletToWalletTransfer.dto.UDetailByMobRequest;
import com.solution.app.justpay4u.WalletToWalletTransfer.dto.UDetailByMobResponse;
import com.solution.app.justpay4u.WalletToWalletTransfer.dto.UDetailsWTW;
import com.solution.app.justpay4u.WalletToWalletTransfer.dto.WalletToWalletFTRequest;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class WalletToWalletTransferActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
    private View customerNoView,customerDetailsView,amountView,remarkView,pinPassView;
    private TextInputLayout txt_customerNo,txt_amount,txt_walletType,txt_pinNo;
    private TextInputEditText remarkEdit,customerNoEdit,amountEdit,pinNoEdit;
    private AutoCompleteTextView walletTypeSpin;
    private AppCompatTextView customerDetailsTv;
    private MaterialButton transferBtn;
    private MaterialToolbar materialToolbar;
    private CustomLoader loader;
    private int walletID,uid,roleId;
    private AppPreferences mAppPreferences;
    private LoginResponse mLoginDataResponse;
    private ArrayList<String> walletTypesArray;
    private HashMap<String, Integer> walletTypesMap = new HashMap<>();
    private WalletTypeResponse mWalletTypeResponse;
    private String deviceId,deviceSerialNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_to_wallet_transfer);
        mAppPreferences =ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
        mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
        deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
        deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
        getIDS();



        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_closeIcon:
                        onBackPressed();
                        return true;
                    default:
                        return false;
                }

            }
        });

        customerNoEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!customerNoEdit.getText().toString().isEmpty()){
                    if(customerNoEdit.getText().toString().length()==10 || count==10){
                        txt_customerNo.setErrorEnabled(false);

                        getUDetailByMobAPI();

                    }else {
                        txt_customerNo.setErrorEnabled(true);
                        txt_customerNo.setError("Mobile No should be length of 10 digits");
                    }
                }
            }

            private void getUDetailByMobAPI() {


                UDetailByMobRequest uDetailByMobRequest=new UDetailByMobRequest(
                        mLoginDataResponse.getData().getUserID(),
                        mLoginDataResponse.getData().getLoginTypeID(),
                        ApplicationConstant.INSTANCE.APP_ID,
                        deviceId,
                        "",
                        BuildConfig.VERSION_NAME,
                        deviceSerialNum,
                        mLoginDataResponse.getData().getSessionID(),
                        mLoginDataResponse.getData().getSession(),
                        customerNoEdit.getText().toString().trim());


                if(uDetailByMobRequest!=null){
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    try {

                        FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
                        Call<UDetailByMobResponse> call = git.getUDetailByMob(uDetailByMobRequest);

                        call.enqueue(new Callback<UDetailByMobResponse>() {

                            @Override
                            public void onResponse(Call<UDetailByMobResponse> call, retrofit2.Response<UDetailByMobResponse> response) {
                                if (loader != null && loader.isShowing())
                                    loader.dismiss();

                                if (response.isSuccessful() && response.body() != null) {
                                    if (response.body().getStatuscode()==1) {
                                        UDetailsWTW uDetailsWTW=response.body().getuDetailsWTW();
                                        if(uDetailsWTW!=null && uDetailsWTW.getStatusCode()==1)
                                            setUiForTransfer(uDetailsWTW);
                                        else if(uDetailsWTW!=null && uDetailsWTW.getStatusCode()==-1){
                                            ApiFintechUtilMethods.INSTANCE.Error(WalletToWalletTransferActivity.this, uDetailsWTW.getMsg() + "");
                                        }

                                    } else if (response.body().getStatuscode()==-1) {
                                        if (!response.body().isVersionValid()) {
                                            ApiFintechUtilMethods.INSTANCE.versionDialog(WalletToWalletTransferActivity.this);
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(WalletToWalletTransferActivity.this, response.body().getMsg() + "");
                                        }
                                    }

                                }else{
                                    if (loader != null && loader.isShowing())
                                        loader.dismiss();

                                    ApiFintechUtilMethods.INSTANCE.apiErrorHandle(WalletToWalletTransferActivity.this,response.code() ,response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<UDetailByMobResponse> call, Throwable t) {
                                if (loader != null && loader.isShowing())
                                    loader.dismiss();

                                ApiFintechUtilMethods.INSTANCE.apiFailureError(WalletToWalletTransferActivity.this, t);
                            }
                        });

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null && loader.isShowing())
                            loader.dismiss();
                    }


                }

            }

            private void setUiForTransfer(UDetailsWTW uDetailsWTW) {

                customerNoView.setVisibility(View.GONE);
                customerDetailsView.setVisibility(View.VISIBLE);
                amountView.setVisibility(View.VISIBLE);
                remarkView.setVisibility(View.VISIBLE);
                transferBtn.setVisibility(View.VISIBLE);

                String customerDetailsStr="";

                if(uDetailsWTW.getOutletName()!=null && !uDetailsWTW.getOutletName().isEmpty())
                    customerDetailsStr+=uDetailsWTW.getOutletName();

                if(uDetailsWTW.getMobileNo()!=null && !uDetailsWTW.getMobileNo().isEmpty())
                    customerDetailsStr=customerDetailsStr + "[ " +uDetailsWTW.getMobileNo() +" ]";


                if(customerDetailsStr!=null && !customerDetailsStr.isEmpty())
                {
                    customerDetailsTv.setVisibility(View.VISIBLE);
                    customerDetailsTv.setText("Credit To "+customerDetailsStr);
                }else{
                    customerDetailsTv.setVisibility(View.GONE);
                }

                gettingWalletType(uDetailsWTW);


                if(uDetailsWTW.isDoubleFactor() || mLoginDataResponse.getData().getIsDoubleFactor())
                    pinPassView.setVisibility(View.VISIBLE);
                else
                    pinPassView.setVisibility(View.GONE);

                uid=uDetailsWTW.getUserID();
                roleId=uDetailsWTW.getRoleID();


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        walletTypeSpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(walletTypesMap!=null){
                    if(!walletTypeSpin.getText().toString().isEmpty()){
                        walletID=walletTypesMap.get(walletTypeSpin.getText().toString());
                    }
                }
            }
        });


    }

    private void getIDS() {
        activity=this;
        loader=new CustomLoader(activity,android.R.style.Theme_Translucent_NoTitleBar);
        materialToolbar=findViewById(R.id.materialToolbar);
        customerNoView=findViewById(R.id.customerNoView);
        pinPassView=findViewById(R.id.pinPassView);
        customerDetailsView=findViewById(R.id.customerDetailsView);
        amountView=findViewById(R.id.amountView);
        remarkView=findViewById(R.id.remarkView);
        txt_customerNo=findViewById(R.id.txt_customerNo);
        txt_pinNo=findViewById(R.id.txt_pinNo);
        txt_amount=findViewById(R.id.txt_amount);
        remarkEdit=findViewById(R.id.edit_remark);
        amountEdit=findViewById(R.id.edit_amount);
        customerNoEdit=findViewById(R.id.edit_customerNo);
        pinNoEdit=findViewById(R.id.edit_pinNo);
        customerDetailsTv=findViewById(R.id.customerDetailsTv);
        transferBtn=findViewById(R.id.btn_transfer);
        txt_walletType=findViewById(R.id.txt_walletType);
        walletTypeSpin=findViewById(R.id.spin_walletType);
        transferBtn.setOnClickListener(this);

        customerDetailsView.setVisibility(View.GONE);
        amountView.setVisibility(View.GONE);
        remarkView.setVisibility(View.GONE);
        transferBtn.setVisibility(View.GONE);


    }

    private void gettingWalletType(UDetailsWTW mUDetailsWTW) {

        mWalletTypeResponse = ApiFintechUtilMethods.INSTANCE.getWalletTypeResponse(mAppPreferences);
        if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
            walletTypesArray=new ArrayList<>();
            for (WalletType walletType: mWalletTypeResponse.getWalletTypes()) {

                if(mUDetailsWTW.isPrepaidB())
                {
                    if(walletType.getActive() && walletType.getInFundProcess() /*&& walletType.getName().toLowerCase().contains("service")*/){
                        walletTypesMap.put(walletType.getName(), walletType.getId());
                        walletTypesArray.add(walletType.getName());
//                        walletTypeSpin.setText(walletType.getName());
//                        walletTypeSpin.setClickable(false);
                    }
                }
                if(mUDetailsWTW.isUtilityB())
                {
                    if(walletType.getActive() && walletType.getInFundProcess() && walletType.getName().toLowerCase().contains("dmt")){
                        walletTypesMap.put(walletType.getName(), walletType.getId());
                        walletTypesArray.add(walletType.getName());
                    }
                }

                if(mUDetailsWTW.isBankB())
                {
                    if(walletType.getActive() && walletType.getInFundProcess() && walletType.getName().toLowerCase().contains("income")){
                        walletTypesMap.put(walletType.getName(), walletType.getId());
                        walletTypesArray.add(walletType.getName());
                    }
                }



            }

            walletTypeSpin.setText(walletTypesArray.get(0), true);
            ArrayAdapter mrrayAdapter = new ArrayAdapter<String>(this, R.layout.text_input_spinner_item, walletTypesArray);
            walletTypeSpin.setAdapter(mrrayAdapter);
        } else {
            ApiFintechUtilMethods.INSTANCE.WalletType(this, mLoginDataResponse, mAppPreferences, object -> {
                mWalletTypeResponse = (WalletTypeResponse) object;
                if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
                    gettingWalletType(mUDetailsWTW);
                }
            });

        }
    }

    @Override
    public void onClick(View clickView) {
        if(clickView==transferBtn){
            if(!validateForm()){
                return;
            }

            callWalletToWalletFTAPI();
        }
    }

    private void callWalletToWalletFTAPI() {

        WalletToWalletFTRequest walletFTRequest=new WalletToWalletFTRequest(
                mLoginDataResponse.getData().getUserID(),
                mLoginDataResponse.getData().getLoginTypeID(),
                ApplicationConstant.INSTANCE.APP_ID,
                deviceId,
                "",
                BuildConfig.VERSION_NAME,
                deviceSerialNum,
                mLoginDataResponse.getData().getSessionID(),
                mLoginDataResponse.getData().getSession(),
                uid,
                amountEdit.getText().toString().trim(),
                remarkEdit.getText().toString(),
                walletID,
                pinNoEdit.getText().toString()
        );

        if(walletFTRequest!=null){
            loader.show();
            loader.setCancelable(false);

            try {

                FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
                Call<UDetailByMobResponse> call = git.walletToWalletFT(walletFTRequest);

                call.enqueue(new Callback<UDetailByMobResponse>() {

                    @Override
                    public void onResponse(Call<UDetailByMobResponse> call, retrofit2.Response<UDetailByMobResponse> response) {
                        if (loader != null && loader.isShowing())
                            loader.dismiss();

                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatuscode()==1) {
                                ApiFintechUtilMethods.INSTANCE.Successful(activity,response.body().getMsg()+"");
                                amountEdit.setText("");
                                remarkEdit.setText("");
                                pinNoEdit.setText("");
                            } else if (response.body() != null && response.body().getStatuscode()==-1) {
                                if (!response.body().isVersionValid()) {
                                    ApiFintechUtilMethods.INSTANCE.versionDialog(activity);
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(activity, response.body().getMsg() + "");
                                }
                            }

                        }else{
                            if (loader != null && loader.isShowing())
                                loader.dismiss();

                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(WalletToWalletTransferActivity.this,response.code() ,response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<UDetailByMobResponse> call, Throwable t) {
                        if (loader != null && loader.isShowing())
                            loader.dismiss();
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(WalletToWalletTransferActivity.this,t);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                if (loader != null && loader.isShowing())
                    loader.dismiss();
            }

        }


    }

    private boolean validateForm() {
        txt_walletType.setErrorEnabled(false);
        txt_pinNo.setErrorEnabled(false);
        txt_amount.setErrorEnabled(false);
        if(roleId!=3){
            ApiFintechUtilMethods.INSTANCE.Error(activity,"User is not a valid for this service");
            return false;
        }else if(walletID==0){
            txt_walletType.setError("Choose Wallet Type");
            walletTypeSpin.requestFocus();
            //UtilMethods.INSTANCE.Error(activity,"Choose Wallet Type ");
            return false;
        }else if(amountEdit.getText().toString().isEmpty()){
            txt_amount.setError(getString(R.string.err_empty_field));
            amountEdit.requestFocus();
            return false;
        }else if(pinPassView.getVisibility()==View.VISIBLE && pinNoEdit.getText().toString().isEmpty()){
            txt_pinNo.setError(getString(R.string.err_empty_field));
            pinNoEdit.requestFocus();
            return false;
        }
        txt_walletType.setErrorEnabled(false);
        txt_pinNo.setErrorEnabled(false);
        txt_amount.setErrorEnabled(false);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}