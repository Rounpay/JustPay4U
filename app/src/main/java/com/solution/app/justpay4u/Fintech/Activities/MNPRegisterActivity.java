package com.solution.app.justpay4u.Fintech.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Fintech.Adapter.MNPClaimReportAdapter;
import com.solution.app.justpay4u.Api.Fintech.Object.MNPClaimsList;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.Api.Fintech.Response.GetMNPStatusResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.RechargeProviderActivity;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;

public class MNPRegisterActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, View.OnClickListener {


    private LinearLayout registerView;
    private EditText et_number;
    private TextView MobileNoCount;
    private TextView MobileNoError;
    private RelativeLayout rl_oprator;
    private ImageView iv_oprator;
    private AppCompatTextView tv_operator;
    private TextView OperatorError;
    private LinearLayout userDetailView;
    private ImageView iv_icon;
    private AppCompatTextView opName;
    private TextView mnpUserNameTv;
    private TextView mnpMobileTv;
    private TextView mnpPasswordTv;
    private TextView installBtn;
    private EditText portMobileEt;
    private EditText portReffrenceEt;
    private EditText portAmtEt;
    private TextView claimnBtn;
    private RelativeLayout historyView;
    private AppCompatTextView viewMore;
    private RecyclerView recyclerView;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    private Button bt_register;
    public int operatorSelectedId;
    String operatorSelected = "";
    CustomLoader loader;
    String AccountName = "Mobile Number", AccountRemark = "", Icon = "";
    private LoginResponse mLoginDataResponse;
    private int INTENT_SELECT_OPERATOR = 7291;
    RequestOptions requestOptions;
    private TextToSpeech tts;
    private boolean isTTSInit;
    private String msgSpeak = "";
    private boolean isRegister;
    GetMNPStatusResponse mnpStatusResponse;
    private AppPreferences mAppPreferences;
    String deviceId, deviceSerialNum;
    private Dialog alertDialogMobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_mnp_register);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            isRegister = getIntent().getBooleanExtra("Register", false);
            mnpStatusResponse =  getIntent().getParcelableExtra("Response");
            getIds();

            setUiData();
            if (!isRegister) {
                HitApi();
            }
            loader.dismiss();
        });

    }


    private void getIds() {
        setTitle(isRegister ? "Register MNP" : "User Detail");


        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);

        registerView = findViewById(R.id.registerView);
        et_number = findViewById(R.id.et_number);
        MobileNoCount = findViewById(R.id.MobileNoCount);
        MobileNoError = findViewById(R.id.MobileNoError);
        rl_oprator = findViewById(R.id.rl_oprator);
        iv_oprator = findViewById(R.id.iv_oprator);
        tv_operator = findViewById(R.id.tv_operator);
        OperatorError = findViewById(R.id.OperatorError);
        userDetailView = findViewById(R.id.userDetailView);
        iv_icon = findViewById(R.id.iv_icon);
        opName = findViewById(R.id.opName);
        mnpUserNameTv = findViewById(R.id.mnpUserNameTv);
        mnpMobileTv = findViewById(R.id.mnpMobileTv);
        mnpPasswordTv = findViewById(R.id.mnpPasswordTv);
        installBtn = findViewById(R.id.installBtn);
        portMobileEt = findViewById(R.id.portMobileEt);
        portReffrenceEt = findViewById(R.id.portReffrenceEt);
        portAmtEt = findViewById(R.id.portAmtEt);
        claimnBtn = findViewById(R.id.claimnBtn);
        historyView = findViewById(R.id.historyView);
        viewMore = findViewById(R.id.viewMore);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bt_register = findViewById(R.id.bt_register);
        setListners();
    }

    private void setListners() {
        rl_oprator.setOnClickListener(this);

        bt_register.setOnClickListener(this);
        viewMore.setOnClickListener(this);


        installBtn.setOnClickListener(v -> {
            if (mnpStatusResponse != null) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(mnpStatusResponse.getAppLink() + "")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    Toast.makeText(this, "Required App Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        claimnBtn.setOnClickListener(v -> {
            if (portMobileEt.getText().toString().trim().isEmpty()) {
                msgSpeak = "Please enter mobile number";
                portMobileEt.setError(msgSpeak);
                portMobileEt.requestFocus();
                playVoice();
                return;
            } else if (portMobileEt.getText().length() != 10) {
                msgSpeak = "Mobile number should be length of " + 10 + " digits";
                portMobileEt.setError(msgSpeak);
                portMobileEt.requestFocus();
                playVoice();
                return;
            } else if (portReffrenceEt.getText().toString().trim().isEmpty()) {
                msgSpeak = "Please enter refference number";
                portReffrenceEt.setError(msgSpeak);
                portReffrenceEt.requestFocus();
                playVoice();
                return;
            } else if (portAmtEt.getText().toString().trim().isEmpty()) {
                msgSpeak = "Please enter amount.";
                portAmtEt.setError(msgSpeak);
                portAmtEt.requestFocus();
                playVoice();
                return;
            }

            HitMNPClaimApi();
        });
        et_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    MobileNoCount.setVisibility(View.VISIBLE);
                    MobileNoCount.setText("Number Digit- " + s.length());
                } else {
                    MobileNoCount.setVisibility(View.GONE);
                }
            }
        });

    }

    void setUiData() {


        new Handler(Looper.getMainLooper()).post(() -> {

            if (isRegister) {

                bt_register.setVisibility(View.VISIBLE);
                registerView.setVisibility(View.VISIBLE);
                userDetailView.setVisibility(View.GONE);
                historyView.setVisibility(View.GONE);
            } else {
                bt_register.setVisibility(View.GONE);
                registerView.setVisibility(View.GONE);
                userDetailView.setVisibility(View.VISIBLE);
                historyView.setVisibility(View.GONE);
            }

            requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_AppLogo_circleCrop();


            if (!mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.voiceEnablePref)) {
                tts = new TextToSpeech(this, this);
            }

            if (mnpStatusResponse != null) {
                opName.setText(mnpStatusResponse.getOpName() + "");
                mnpMobileTv.setText(mnpStatusResponse.getMnpMobile() + "");
                mnpUserNameTv.setText(mnpStatusResponse.getUserName() + "");
                mnpPasswordTv.setText(mnpStatusResponse.getPassword() + "");
                operatorSelectedId = mnpStatusResponse.getOid();
                operatorSelected = mnpStatusResponse.getOpName();
                Glide.with(this)
                        .load(ApplicationConstant.INSTANCE.baseIconUrl + mnpStatusResponse.getOid() + ".png")
                        .apply(requestOptions)
                        .into(iv_icon);
            }


        });
    }

    @Override
    public void onClick(View v) {
        if (v == viewMore) {
            Intent intent = new Intent(this, MNPClaimHistory.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (v == rl_oprator) {
            Intent i = new Intent(this, RechargeProviderActivity.class);
            i.putExtra("from", "MNP");
            i.putExtra("fromId", 1);
            i.putExtra("fromPhoneRecharge", true);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_SELECT_OPERATOR);
        } else if (v == bt_register) {
            MobileNoError.setVisibility(View.GONE);
            OperatorError.setVisibility(View.GONE);
            if (!validateMobile()) {
                return;
            } else if (tv_operator.getText().toString().isEmpty()) {
                OperatorError.setVisibility(View.VISIBLE);
                msgSpeak = "Please Select Operator.";
                playVoice();
                return;
            }

            if (ApiFintechUtilMethods.INSTANCE.isVpnConnected(this)) {
                ApiFintechUtilMethods.INSTANCE.Error(this, "Your device connected with VPN, please disable before using this service");
                return;
            }


            confirmDialog();


        }


    }


    void confirmDialog() {
        mnpRegisterConfiormDialog(this, false, ApplicationConstant.INSTANCE.baseIconUrl + Icon,
                et_number.getText().toString(), operatorSelected);

    }

    void register(String pinPass) {
        loader.show();
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);
        bt_register.setEnabled(true);
        ApiFintechUtilMethods.INSTANCE.UserMNPRegistration(MNPRegisterActivity.this, operatorSelectedId,
                et_number.getText().toString().trim(), pinPass, loader, mLoginDataResponse, deviceId, deviceSerialNum);
    }

    public void mnpRegisterConfiormDialog(Activity context, boolean isPinPass, String logo, String number,
                                          String operator) {
        if (alertDialogMobile != null && alertDialogMobile.isShowing()) {
            return;
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_mnp_register_confiorm, null);

        final View pinPassView = dialogView.findViewById(R.id.pinPassView);
        final EditText pinPassEt = dialogView.findViewById(R.id.pinPassEt);
        AppCompatTextView operatorTv = dialogView.findViewById(R.id.operator);
        AppCompatTextView numberTv = dialogView.findViewById(R.id.number);
        AppCompatTextView cancelTv = dialogView.findViewById(R.id.cancel);
        AppCompatTextView okTv = dialogView.findViewById(R.id.ok);
        AppCompatImageView logoIv = dialogView.findViewById(R.id.logo);

        alertDialogMobile = new Dialog(context, R.style.Theme_AppCompat_Dialog_Alert);

        alertDialogMobile.setCancelable(true);
        alertDialogMobile.setContentView(dialogView);
        alertDialogMobile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        if (isPinPass) {
            pinPassView.setVisibility(View.VISIBLE);
        } else {
            pinPassView.setVisibility(View.GONE);
        }

        operatorTv.setText(operator);

        numberTv.setText(number);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(logo)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.placeholderOf(R.mipmap.ic_launcher_round))
                .into(logoIv);

        okTv.setOnClickListener(v -> {
            if (isPinPass && pinPassEt.getText().toString().isEmpty()) {
                pinPassEt.setError("Field can't be empty");
                pinPassEt.requestFocus();
                return;
            }
            alertDialogMobile.dismiss();
            bt_register.setEnabled(false);
            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(MNPRegisterActivity.this)) {
                register(pinPassEt.getText().toString());


            } else {
                ApiFintechUtilMethods.INSTANCE.NetworkError(this);
            }
        });
        cancelTv.setOnClickListener(v -> {
            alertDialogMobile.dismiss();

        });

        alertDialogMobile.show();
        /*if (isFullScreen) {
            alertDialogMobile.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }*/
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == INTENT_SELECT_OPERATOR && resultCode == RESULT_OK) {
            OperatorList mOperatorList = data.getParcelableExtra("SelectedOperator");
            if(mOperatorList!=null) {
                operatorSelected = mOperatorList.getName();
                operatorSelectedId = mOperatorList.getOid();
                AccountName = mOperatorList.getAccountName();
                AccountRemark = mOperatorList.getAccountRemak();
                Icon = mOperatorList.getImage();

                tv_operator.setText(operatorSelected + "");
                OperatorError.setVisibility(View.GONE);
                Glide.with(this)
                        .load(ApplicationConstant.INSTANCE.baseIconUrl + Icon)
                        .apply(requestOptions)
                        .into(iv_oprator);

            }
        }
    }


    public void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            ApiFintechUtilMethods.INSTANCE.GetUserClaimsReport(this, "10", "", "",
                    true, loader, mLoginDataResponse, deviceId, deviceSerialNum,
                    new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            GetMNPStatusResponse mMnpStatusResponse = (GetMNPStatusResponse) object;
                            dataParse(mMnpStatusResponse);
                        }

                        @Override
                        public void onError(int error) {
                            historyView.setVisibility(View.GONE);

                        }
                    });

        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    public void HitMNPClaimApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            ApiFintechUtilMethods.INSTANCE.UserMNPClaim(this, operatorSelectedId + "", portMobileEt.getText().toString(),
                    portReffrenceEt.getText().toString(),
                    portAmtEt.getText().toString(), loader, mLoginDataResponse, deviceId, deviceSerialNum,
                    new ApiFintechUtilMethods.ApiCallBackTwoMethod() {
                        @Override
                        public void onSucess(Object object) {
                        }

                        @Override
                        public void onError(Object object) {

                        }
                    });

        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    public void dataParse(GetMNPStatusResponse response) {

        ArrayList<MNPClaimsList> transactionsObjects = response.getMnpClaimsList();
        if (transactionsObjects.size() > 0) {
            MNPClaimReportAdapter mAdapter = new MNPClaimReportAdapter(transactionsObjects, this);
            recyclerView.setAdapter(mAdapter);
            historyView.setVisibility(View.VISIBLE);
        } else {
            historyView.setVisibility(View.GONE);
        }
    }


    private boolean validateMobile() {
        if (et_number.getText().toString().trim().isEmpty()) {
            msgSpeak = "Number can't be empty.";
            MobileNoError.setText(msgSpeak);
            MobileNoError.setVisibility(View.VISIBLE);
            et_number.requestFocus();
            playVoice();
            return false;
        } else if (et_number.getText().length() != 10) {
            msgSpeak = AccountName + " should be length of " + 10;
            MobileNoError.setText(msgSpeak);
            MobileNoError.setVisibility(View.VISIBLE);
            et_number.requestFocus();
            playVoice();
            return false;
        } else {
            et_number.requestFocus();
            MobileNoError.setVisibility(View.GONE);
        }

        return true;
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            isTTSInit = true;
            if (msgSpeak != null && !msgSpeak.isEmpty()) {
                playVoice();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Init failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void playVoice() {
        if (tts != null && isTTSInit) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts.speak(msgSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                tts.speak(msgSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
            msgSpeak = "";
        }
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
