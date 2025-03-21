package com.solution.app.justpay4u.Util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.solution.app.justpay4u.Api.Fintech.Object.BankListObject;
import com.solution.app.justpay4u.Api.Fintech.Object.EKycDirectorOrCompany;
import com.solution.app.justpay4u.Api.Fintech.Object.EkycType;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.EKycStepsValidationRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.GetEKYCDetailResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetUserResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.BankListScreenActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownEKYCStepsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Vishnu Agarwal on 18/01/2022.
 */
public class EKYCStepsDialog {
    Activity mActivity;
    AppPreferences mAppPreferences;
    LoginResponse LoginDataResponse;
    String deviceId;
    String deviceSerialNum;
    ArrayList<Integer> stepsList = new ArrayList<>();
    private TextView previous, line, next, currentSteps, totalSteps;
    private ImageView aadharEditIv;
    private LinearLayout gstinRadioView;
    private RadioGroup gstRadioGroup;
    private RadioButton gstinNoRadio;
    private LinearLayout gstinInputView;
    private RelativeLayout compnyTypeChooserView;
    private TextView companyType;
    private EditText etGstin;
    private LinearLayout gstinDetailView;
    private TextView gstinNumber;
    private LinearLayout directordetailView;
    private TextView directordetail;
    private TextView gstInEdit;
    private LinearLayout aadharInputView;
    private LinearLayout directerView;
    private RelativeLayout directorChooserView;
    private TextView director, chooseDirectorError, aadharError, otpError;
    private EditText etAadhar;
    private LinearLayout otpView;
    private EditText etOTP;
    private LinearLayout aadharDetailView;
    private TextView aadharNumber;
    private TextView aadharEdit;
    private LinearLayout panView;
    private LinearLayout companyPanView;
    private TextView companyPan;
    private TextView panLabel;
    private EditText etPan;
    private LinearLayout panDetailView;
    private LinearLayout compPanNumberView;
    private TextView compPanNumber;
    private LinearLayout directorPanDetailView;
    private TextView panNumLabel;
    private TextView panNum;
    private TextView panEdit;
    private LinearLayout bankView;
    private RelativeLayout bankChooserView;
    private TextView bankTv;
    private EditText etAccountNum;
    private EditText etIfsc;
    private LinearLayout bankDetailView;
    private LinearLayout bankNameView;
    private TextView bankName;
    private LinearLayout accountNumView;
    private TextView accountNum;
    private LinearLayout ifscView;
    private TextView ifscCode;
    private LinearLayout accountNameView;
    private TextView accountName;
    private TextView bankEdit, chooseCompError, gstinError, panError, chooseBankError, accountNumError, ifscError;
    private LinearLayout btnView;
    private TextView consentError;
    private TextView verifyBtn;
    private CheckBox checkbox;
    private LinearLayout loaderView;
    private LinearLayout preventView;
    private TextView apiErrorTv;
    private int currentStepId;
    private int selectedDirectorPos, selectedComPos;
    private PopupWindow popup;
    private GetEKYCDetailResponse mGetEKYCDetailResponse;
    private String selectedDirectorValue, selectedComValue;
    private String refrenceId = "";
    private BankListObject selectedBank;
    private int selectedBankId;
    private InputMethodManager imm;
    private ArrayList<Integer> visibleStepsList = new ArrayList<>();
    private AlertDialog alertDialog;


    public EKYCStepsDialog(Activity mActivity, LoginResponse LoginDataResponse,
                           String deviceId, String deviceSerialNum, AppPreferences mAppPreferences) {
        this.mActivity = mActivity;
        this.LoginDataResponse = LoginDataResponse;
        this.mAppPreferences = mAppPreferences;
        this.deviceId = deviceId;
        this.deviceSerialNum = deviceSerialNum;

    }

    public void showStepsDialog() {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                return;
            }


            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mActivity);
            alertDialog = dialogBuilder.create();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = mActivity.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_ekyc_step_verify, null);
            alertDialog.setView(dialogView);
            alertDialog.setCancelable(false);

            previous = dialogView.findViewById(R.id.previous);
            line = dialogView.findViewById(R.id.line);
            next = dialogView.findViewById(R.id.next);
            currentSteps = dialogView.findViewById(R.id.currentSteps);
            totalSteps = dialogView.findViewById(R.id.totalSteps);

            gstinRadioView = dialogView.findViewById(R.id.gstinRadioView);
            gstRadioGroup =  dialogView.findViewById(R.id.gstRadioGroup);
            gstinNoRadio = dialogView.findViewById(R.id.gstinNoRadio);
            gstinInputView = dialogView.findViewById(R.id.gstinInputView);
            compnyTypeChooserView = dialogView.findViewById(R.id.compnyTypeChooserView);
            companyType = dialogView.findViewById(R.id.companyType);
            etGstin = dialogView.findViewById(R.id.etGstin);
            gstinDetailView = dialogView.findViewById(R.id.gstinDetailView);
            gstinNumber = dialogView.findViewById(R.id.gstinNumber);
            directordetailView = dialogView.findViewById(R.id.directordetailView);
            directordetail = dialogView.findViewById(R.id.directordetail);
            gstInEdit = dialogView.findViewById(R.id.gstInEdit);
            aadharInputView = dialogView.findViewById(R.id.aadharInputView);
            directerView = dialogView.findViewById(R.id.directerView);
            directorChooserView = dialogView.findViewById(R.id.directorChooserView);
            director = dialogView.findViewById(R.id.director);
            chooseDirectorError = dialogView.findViewById(R.id.chooseDirectorError);
            aadharError = dialogView.findViewById(R.id.aadharError);
            otpError = dialogView.findViewById(R.id.otpError);
            etAadhar = dialogView.findViewById(R.id.etAadhar);
            aadharEditIv = dialogView.findViewById(R.id.aadharEditIv);
            otpView = dialogView.findViewById(R.id.otpView);
            etOTP = dialogView.findViewById(R.id.etOTP);
            aadharDetailView = dialogView.findViewById(R.id.aadharDetailView);
            aadharNumber = dialogView.findViewById(R.id.aadharNumber);
            aadharEdit = dialogView.findViewById(R.id.aadharEdit);
            panView = dialogView.findViewById(R.id.panView);
            companyPanView = dialogView.findViewById(R.id.companyPanView);
            companyPan = dialogView.findViewById(R.id.companyPan);
            panLabel = dialogView.findViewById(R.id.panLabel);
            etPan = dialogView.findViewById(R.id.etPan);
            panDetailView = dialogView.findViewById(R.id.panDetailView);
            compPanNumberView = dialogView.findViewById(R.id.compPanNumberView);
            compPanNumber = dialogView.findViewById(R.id.compPanNumber);
            directorPanDetailView = dialogView.findViewById(R.id.directorPanDetailView);
            panNumLabel = dialogView.findViewById(R.id.panNumLabel);
            panNum = dialogView.findViewById(R.id.panNum);
            panEdit = dialogView.findViewById(R.id.panEdit);
            bankView = dialogView.findViewById(R.id.bankView);
            bankChooserView = dialogView.findViewById(R.id.bankChooserView);
            bankTv = dialogView.findViewById(R.id.bankTv);
            etAccountNum = dialogView.findViewById(R.id.etAccountNum);
            etIfsc = dialogView.findViewById(R.id.etIfsc);
            bankDetailView = dialogView.findViewById(R.id.bankDetailView);
            bankNameView = dialogView.findViewById(R.id.bankNameView);
            bankName = dialogView.findViewById(R.id.bankName);
            accountNumView = dialogView.findViewById(R.id.accountNumView);
            accountNum = dialogView.findViewById(R.id.accountNum);
            ifscView = dialogView.findViewById(R.id.ifscView);
            ifscCode = dialogView.findViewById(R.id.ifscCode);
            accountNameView = dialogView.findViewById(R.id.accountNameView);
            accountName = dialogView.findViewById(R.id.accountName);
            bankEdit = dialogView.findViewById(R.id.bankEdit);
            btnView = dialogView.findViewById(R.id.btnView);
            consentError = dialogView.findViewById(R.id.consentError);
            chooseCompError = dialogView.findViewById(R.id.chooseCompError);
            gstinError = dialogView.findViewById(R.id.gstinError);
            panError = dialogView.findViewById(R.id.panError);
            chooseBankError = dialogView.findViewById(R.id.chooseBankError);
            accountNumError = dialogView.findViewById(R.id.accountNumError);
            ifscError = dialogView.findViewById(R.id.ifscError);
            verifyBtn = dialogView.findViewById(R.id.verifyBtn);
            checkbox = dialogView.findViewById(R.id.checkbox);
            loaderView = dialogView.findViewById(R.id.loaderView);
            preventView = dialogView.findViewById(R.id.preventView);
            apiErrorTv = dialogView.findViewById(R.id.apiErrorTv);


            selectedDirectorPos = -1;
            selectedComPos = -1;
            compnyTypeChooserView.setOnClickListener(view -> {
                if (mGetEKYCDetailResponse.getData().getCompanyTypeSelect() != null && mGetEKYCDetailResponse.getData().getCompanyTypeSelect().size() > 0) {
                    showDropDownPopup(view, selectedComPos, mGetEKYCDetailResponse.getData().getCompanyTypeSelect(), (clickPosition, value, object) -> {

                                if (selectedComPos != clickPosition) {
                                    companyType.setText(value + "");
                                    selectedComPos = clickPosition;
                                    selectedComValue = object.getValue() + "";
                                    chooseCompError.setVisibility(View.GONE);
                                }
                            }
                    );
                } else {
                    new CustomAlertDialog(mActivity).Warning("Company list is not available");
                }
            });
            bankChooserView.setOnClickListener(view -> {
                Utility.INSTANCE.mBankClickCallBack = operator -> {
                    selectedBank = operator;
                    selectedBankId = operator.getId();
                    bankTv.setText(selectedBank.getBankName() + "");
                    chooseBankError.setVisibility(View.GONE);
                    Utility.INSTANCE.mBankClickCallBack = null;
                };

                mActivity.startActivity(new Intent(mActivity, BankListScreenActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

            });
            directorChooserView.setOnClickListener(view -> {
                if (mGetEKYCDetailResponse.getData().getDirectors() != null && mGetEKYCDetailResponse.getData().getDirectors().size() > 0) {
                    showDropDownPopup(view, selectedDirectorPos, mGetEKYCDetailResponse.getData().getDirectors(), (clickPosition, value, object) -> {

                                if (selectedDirectorPos != clickPosition) {
                                    director.setText(value + "");
                                    selectedDirectorPos = clickPosition;
                                    selectedDirectorValue = object.getValue() + "";
                                    chooseDirectorError.setVisibility(View.GONE);
                                }
                            }
                    );
                } else {
                    new CustomAlertDialog(mActivity).Warning("Director list is not available");
                }
            });
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        consentError.setVisibility(View.GONE);
                    }
                }
            });
            previous.setOnClickListener(view -> {

                if (currentStepId == 4) {
                    if (visibleStepsList.contains(3) && visibleStepsList.contains(2) && visibleStepsList.contains(1)) {
                        previous.setVisibility(View.VISIBLE);
                        line.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        showPAN();
                    } else if (!visibleStepsList.contains(3) && visibleStepsList.contains(2) && visibleStepsList.contains(1)) {
                        previous.setVisibility(View.VISIBLE);
                        line.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        showAadhar();
                    } else if (visibleStepsList.contains(3) && !visibleStepsList.contains(2) && visibleStepsList.contains(1)) {
                        previous.setVisibility(View.VISIBLE);
                        line.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        showPAN();
                    } else if (visibleStepsList.contains(3) && visibleStepsList.contains(2) && !visibleStepsList.contains(1)) {
                        previous.setVisibility(View.VISIBLE);
                        line.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        showPAN();
                    } else if (!visibleStepsList.contains(3) && !visibleStepsList.contains(2) && visibleStepsList.contains(1)) {
                        previous.setVisibility(View.GONE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.VISIBLE);
                        showGstView();
                    } else if (visibleStepsList.contains(3) && !visibleStepsList.contains(2) && !visibleStepsList.contains(1)) {
                        previous.setVisibility(View.GONE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.VISIBLE);
                        showPAN();
                    } else if (!visibleStepsList.contains(3) && visibleStepsList.contains(2) && !visibleStepsList.contains(1)) {
                        previous.setVisibility(View.GONE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.VISIBLE);
                        showAadhar();
                    } else {
                        previous.setVisibility(View.GONE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        showBank();
                    }


                } else if (currentStepId == 3) {
                    if (visibleStepsList.contains(2) && visibleStepsList.contains(1)) {
                        previous.setVisibility(View.VISIBLE);
                        line.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        showAadhar();
                    } else if (!visibleStepsList.contains(2) && visibleStepsList.contains(1)) {
                        previous.setVisibility(View.GONE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.VISIBLE);
                        showGstView();
                    } else if (visibleStepsList.contains(2) && !visibleStepsList.contains(1)) {
                        previous.setVisibility(View.GONE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.VISIBLE);
                        showAadhar();
                    } else {
                        previous.setVisibility(View.GONE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        showPAN();
                    }

                } else if (currentStepId == 2) {
                    if (visibleStepsList.contains(1)) {
                        previous.setVisibility(View.GONE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.VISIBLE);
                        showGstView();
                    } else {
                        previous.setVisibility(View.GONE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        showAadhar();
                    }

                } else {

                    previous.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);
                    next.setVisibility(View.GONE);
                    showGstView();
                }


            });

            next.setOnClickListener(view -> {

                if (currentStepId == 1) {
                    if (visibleStepsList.contains(2) && visibleStepsList.contains(3) && visibleStepsList.contains(4)) {
                        previous.setVisibility(View.VISIBLE);
                        line.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        showAadhar();
                    } else if (visibleStepsList.contains(2) && visibleStepsList.contains(3) && !visibleStepsList.contains(4)) {
                        previous.setVisibility(View.VISIBLE);
                        line.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        showAadhar();
                    } else if (visibleStepsList.contains(2) && !visibleStepsList.contains(3) && visibleStepsList.contains(4)) {
                        previous.setVisibility(View.VISIBLE);
                        line.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        showAadhar();
                    } else if (!visibleStepsList.contains(2) && visibleStepsList.contains(3) && visibleStepsList.contains(4)) {
                        previous.setVisibility(View.VISIBLE);
                        line.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        showPAN();
                    } else if (!visibleStepsList.contains(2) && !visibleStepsList.contains(3) && visibleStepsList.contains(4)) {
                        previous.setVisibility(View.VISIBLE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        showBank();
                    } else if (!visibleStepsList.contains(2) && visibleStepsList.contains(3) && !visibleStepsList.contains(4)) {
                        previous.setVisibility(View.VISIBLE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        showPAN();
                    } else if (visibleStepsList.contains(2) && !visibleStepsList.contains(3) && !visibleStepsList.contains(4)) {
                        previous.setVisibility(View.VISIBLE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        showAadhar();
                    } else {
                        previous.setVisibility(View.GONE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        showGstView();
                    }

                } else if (currentStepId == 2) {
                    if (visibleStepsList.contains(4) && visibleStepsList.contains(3)) {
                        previous.setVisibility(View.VISIBLE);
                        line.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        showPAN();
                    } else if (visibleStepsList.contains(4) && !visibleStepsList.contains(3)) {
                        previous.setVisibility(View.VISIBLE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        showBank();
                    } else if (!visibleStepsList.contains(4) && visibleStepsList.contains(3)) {
                        previous.setVisibility(View.VISIBLE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        showPAN();
                    } else {
                        previous.setVisibility(View.GONE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        showAadhar();
                    }

                } else if (currentStepId == 3) {
                    if (visibleStepsList.contains(4)) {
                        previous.setVisibility(View.VISIBLE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        showBank();
                    } else {
                        previous.setVisibility(View.GONE);
                        line.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        showPAN();
                    }

                } else {

                    previous.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);
                    next.setVisibility(View.GONE);
                    showBank();

                }

            });

            gstInEdit.setOnClickListener(view -> {
                preventView.setVisibility(View.VISIBLE);
                loaderView.setVisibility(View.VISIBLE);
                EditE_KYCStep(1, new ApiCallBackTwoMethod() {
                    @Override
                    public void onSucess(GetEKYCDetailResponse object) {
                        GetKycDetails(new ApiCallBackTwoMethod() {
                            @Override
                            public void onSucess(GetEKYCDetailResponse object) {

                            }

                            @Override
                            public void onError(Object object) {
                                loaderView.setVisibility(View.GONE);
                                preventView.setVisibility(View.GONE);
                                if (object != null) {
                                    apiErrorTv.setVisibility(View.VISIBLE);
                                    if (object instanceof GetEKYCDetailResponse) {
                                        apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                                    } else {
                                        apiErrorTv.setText(object + "");
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Object object) {
                        loaderView.setVisibility(View.GONE);
                        preventView.setVisibility(View.GONE);
                        if (object != null) {
                            apiErrorTv.setVisibility(View.VISIBLE);
                            if (object instanceof GetEKYCDetailResponse) {
                                apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                            } else {
                                apiErrorTv.setText(object + "");
                            }
                        }
                    }
                });
            });
            aadharEdit.setOnClickListener(view -> {
                preventView.setVisibility(View.VISIBLE);
                loaderView.setVisibility(View.VISIBLE);
                EditE_KYCStep(2, new ApiCallBackTwoMethod() {
                    @Override
                    public void onSucess(GetEKYCDetailResponse object) {
                        GetKycDetails(new ApiCallBackTwoMethod() {
                            @Override
                            public void onSucess(GetEKYCDetailResponse object) {

                            }

                            @Override
                            public void onError(Object object) {
                                loaderView.setVisibility(View.GONE);
                                preventView.setVisibility(View.GONE);
                                if (object != null) {
                                    apiErrorTv.setVisibility(View.VISIBLE);
                                    if (object instanceof GetEKYCDetailResponse) {
                                        apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                                    } else {
                                        apiErrorTv.setText(object + "");
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Object object) {
                        loaderView.setVisibility(View.GONE);
                        preventView.setVisibility(View.GONE);
                        if (object != null) {
                            apiErrorTv.setVisibility(View.VISIBLE);
                            if (object instanceof GetEKYCDetailResponse) {
                                apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                            } else {
                                apiErrorTv.setText(object + "");
                            }
                        }
                    }
                });
            });
            panEdit.setOnClickListener(view -> {
                preventView.setVisibility(View.VISIBLE);
                loaderView.setVisibility(View.VISIBLE);
                EditE_KYCStep(3, new ApiCallBackTwoMethod() {
                    @Override
                    public void onSucess(GetEKYCDetailResponse object) {
                        GetKycDetails(new ApiCallBackTwoMethod() {
                            @Override
                            public void onSucess(GetEKYCDetailResponse object) {

                            }

                            @Override
                            public void onError(Object object) {
                                loaderView.setVisibility(View.GONE);
                                preventView.setVisibility(View.GONE);
                                if (object != null) {
                                    apiErrorTv.setVisibility(View.VISIBLE);
                                    if (object instanceof GetEKYCDetailResponse) {
                                        apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                                    } else {
                                        apiErrorTv.setText(object + "");
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Object object) {
                        loaderView.setVisibility(View.GONE);
                        preventView.setVisibility(View.GONE);
                        if (object != null) {
                            apiErrorTv.setVisibility(View.VISIBLE);
                            if (object instanceof GetEKYCDetailResponse) {
                                apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                            } else {
                                apiErrorTv.setText(object + "");
                            }
                        }
                    }
                });
            });
            bankEdit.setOnClickListener(view -> {
                preventView.setVisibility(View.VISIBLE);
                loaderView.setVisibility(View.VISIBLE);
                EditE_KYCStep(4, new ApiCallBackTwoMethod() {
                    @Override
                    public void onSucess(GetEKYCDetailResponse object) {
                        GetKycDetails(new ApiCallBackTwoMethod() {
                            @Override
                            public void onSucess(GetEKYCDetailResponse object) {

                            }

                            @Override
                            public void onError(Object object) {
                                loaderView.setVisibility(View.GONE);
                                preventView.setVisibility(View.GONE);
                                if (object != null) {
                                    apiErrorTv.setVisibility(View.VISIBLE);
                                    if (object instanceof GetEKYCDetailResponse) {
                                        apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                                    } else {
                                        apiErrorTv.setText(object + "");
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Object object) {
                        loaderView.setVisibility(View.GONE);
                        preventView.setVisibility(View.GONE);
                        if (object != null) {
                            apiErrorTv.setVisibility(View.VISIBLE);
                            if (object instanceof GetEKYCDetailResponse) {
                                apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                            } else {
                                apiErrorTv.setText(object + "");
                            }
                        }
                    }
                });
            });
            aadharEditIv.setOnClickListener(view -> {
                etAadhar.setFocusableInTouchMode(true);
                etAadhar.setFocusable(true);
                otpView.setVisibility(View.GONE);
                apiErrorTv.setVisibility(View.GONE);
                aadharEditIv.setVisibility(View.GONE);
                verifyBtn.setText("Verify AADHAR");
            });
            verifyBtn.setOnClickListener(view -> {
                apiErrorTv.setVisibility(View.GONE);

                if (currentStepId == 1) {
                    chooseCompError.setVisibility(View.GONE);
                    gstinError.setVisibility(View.GONE);
                    consentError.setVisibility(View.GONE);
                    if (selectedComValue == null || selectedComValue.isEmpty()) {
                        chooseCompError.setVisibility(View.VISIBLE);
                    } else if (etGstin.getText().toString().isEmpty()) {
                        gstinError.setVisibility(View.VISIBLE);
                    } else if (!checkbox.isChecked()) {
                        consentError.setVisibility(View.VISIBLE);
                    } else {
                        hideKeyboard();
                        btnView.setVisibility(View.GONE);
                        preventView.setVisibility(View.VISIBLE);
                        loaderView.setVisibility(View.VISIBLE);
                        ValidateGSTINE_KYC(etGstin.getText().toString(), selectedComValue, false, checkbox.isChecked(), new ApiCallBackTwoMethod() {
                            @Override
                            public void onSucess(GetEKYCDetailResponse object) {
                                GetKycDetails(new ApiCallBackTwoMethod() {
                                    @Override
                                    public void onSucess(GetEKYCDetailResponse object) {

                                    }

                                    @Override
                                    public void onError(Object object) {
                                        loaderView.setVisibility(View.GONE);
                                        preventView.setVisibility(View.GONE);
                                        btnView.setVisibility(View.VISIBLE);
                                        if (object != null) {
                                            apiErrorTv.setVisibility(View.VISIBLE);
                                            if (object instanceof GetEKYCDetailResponse) {
                                                apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                                            } else {
                                                apiErrorTv.setText(object + "");
                                            }
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onError(Object object) {
                                loaderView.setVisibility(View.GONE);
                                preventView.setVisibility(View.GONE);
                                btnView.setVisibility(View.VISIBLE);
                                if (object != null) {
                                    apiErrorTv.setVisibility(View.VISIBLE);
                                    if (object instanceof GetEKYCDetailResponse) {
                                        apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                                    } else {
                                        apiErrorTv.setText(object + "");
                                    }
                                }
                            }
                        });
                    }
                } else if (currentStepId == 2) {
                    chooseDirectorError.setVisibility(View.GONE);
                    aadharError.setVisibility(View.GONE);
                    otpError.setVisibility(View.GONE);
                    consentError.setVisibility(View.GONE);
                    if (directerView.getVisibility() == View.VISIBLE && (selectedDirectorValue == null || selectedDirectorValue.isEmpty())) {
                        chooseDirectorError.setVisibility(View.VISIBLE);
                    } else if (etAadhar.getText().toString().isEmpty() || etAadhar.getText().toString().length() != 12) {
                        aadharError.setVisibility(View.VISIBLE);
                    } else if (otpView.getVisibility() == View.VISIBLE &&
                            (etOTP.getText().toString().isEmpty() || etOTP.getText().toString().length() != 6)) {
                        otpError.setVisibility(View.VISIBLE);
                    } else if (!checkbox.isChecked()) {
                        consentError.setVisibility(View.VISIBLE);
                    } else {
                        hideKeyboard();
                        btnView.setVisibility(View.GONE);
                        preventView.setVisibility(View.VISIBLE);
                        loaderView.setVisibility(View.VISIBLE);
                        if (otpView.getVisibility() == View.VISIBLE) {
                            ValidateAadharOTPE_KYC(etOTP.getText().toString(), refrenceId, new ApiCallBackTwoMethod() {
                                @Override
                                public void onSucess(GetEKYCDetailResponse object) {


                                    GetKycDetails(new ApiCallBackTwoMethod() {
                                        @Override
                                        public void onSucess(GetEKYCDetailResponse object) {

                                        }

                                        @Override
                                        public void onError(Object object) {
                                            loaderView.setVisibility(View.GONE);
                                            preventView.setVisibility(View.GONE);
                                            btnView.setVisibility(View.VISIBLE);
                                            if (object != null) {
                                                apiErrorTv.setVisibility(View.VISIBLE);
                                                if (object instanceof GetEKYCDetailResponse) {
                                                    apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                                                } else {
                                                    apiErrorTv.setText(object + "");
                                                }
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onError(Object object) {
                                    loaderView.setVisibility(View.GONE);
                                    preventView.setVisibility(View.GONE);
                                    btnView.setVisibility(View.VISIBLE);
                                    if (object != null) {
                                        apiErrorTv.setVisibility(View.VISIBLE);
                                        if (object instanceof GetEKYCDetailResponse) {
                                            apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                                        } else {
                                            apiErrorTv.setText(object + "");
                                        }
                                    }
                                }
                            });
                        } else {
                            ValidateAadharE_KYC(etAadhar.getText().toString(), selectedDirectorValue, false, checkbox.isChecked(), new ApiCallBackTwoMethod() {
                                @Override
                                public void onSucess(GetEKYCDetailResponse object) {
                                    loaderView.setVisibility(View.GONE);
                                    preventView.setVisibility(View.GONE);
                                    btnView.setVisibility(View.VISIBLE);
                                    otpView.setVisibility(View.VISIBLE);
                                    aadharEditIv.setVisibility(View.VISIBLE);
                                    etAadhar.setFocusable(false);
                                    verifyBtn.setText("Verify OTP");
                                }

                                @Override
                                public void onError(Object object) {
                                    loaderView.setVisibility(View.GONE);
                                    preventView.setVisibility(View.GONE);
                                    btnView.setVisibility(View.VISIBLE);
                                    if (object != null) {
                                        apiErrorTv.setVisibility(View.VISIBLE);
                                        if (object instanceof GetEKYCDetailResponse) {
                                            apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                                        } else {
                                            apiErrorTv.setText(object + "");
                                        }
                                    }
                                }
                            });

                        }
                    }
                } else if (currentStepId == 3) {
                    panError.setVisibility(View.GONE);
                    consentError.setVisibility(View.GONE);
                    if (etPan.getText().toString().isEmpty()) {
                        panError.setVisibility(View.VISIBLE);
                    } else if (etPan.getText().toString().length() != 10) {
                        panError.setVisibility(View.VISIBLE);
                    } else if (!checkbox.isChecked()) {
                        consentError.setVisibility(View.VISIBLE);
                    } else {
                        hideKeyboard();
                        btnView.setVisibility(View.GONE);
                        preventView.setVisibility(View.VISIBLE);
                        loaderView.setVisibility(View.VISIBLE);
                        ValidatePAN(etPan.getText().toString(), mGetEKYCDetailResponse.getData().getSelectedDirector() + "", false, checkbox.isChecked(), new ApiCallBackTwoMethod() {
                            @Override
                            public void onSucess(GetEKYCDetailResponse object) {
                                GetKycDetails(new ApiCallBackTwoMethod() {
                                    @Override
                                    public void onSucess(GetEKYCDetailResponse object) {

                                    }

                                    @Override
                                    public void onError(Object object) {
                                        loaderView.setVisibility(View.GONE);
                                        preventView.setVisibility(View.GONE);
                                        btnView.setVisibility(View.VISIBLE);
                                        if (object != null) {
                                            apiErrorTv.setVisibility(View.VISIBLE);
                                            if (object instanceof GetEKYCDetailResponse) {
                                                apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                                            } else {
                                                apiErrorTv.setText(object + "");
                                            }
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onError(Object object) {
                                loaderView.setVisibility(View.GONE);
                                preventView.setVisibility(View.GONE);
                                btnView.setVisibility(View.VISIBLE);
                                if (object != null) {
                                    apiErrorTv.setVisibility(View.VISIBLE);
                                    if (object instanceof GetEKYCDetailResponse) {
                                        apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                                    } else {
                                        apiErrorTv.setText(object + "");
                                    }
                                }
                            }
                        });
                    }
                } else {
                    chooseBankError.setVisibility(View.GONE);
                    accountNumError.setVisibility(View.GONE);
                    ifscError.setVisibility(View.GONE);
                    consentError.setVisibility(View.GONE);
                    if (bankTv.getText().toString().isEmpty()) {
                        chooseBankError.setVisibility(View.VISIBLE);
                    } else if (etAccountNum.getText().toString().isEmpty()) {
                        accountNumError.setVisibility(View.VISIBLE);
                    } else if (etIfsc.getText().toString().isEmpty()) {
                        ifscError.setVisibility(View.VISIBLE);
                    } else if (etIfsc.getText().toString().length() != 11) {
                        ifscError.setVisibility(View.VISIBLE);
                    } else if (!checkbox.isChecked()) {
                        consentError.setVisibility(View.VISIBLE);
                    } else {
                        hideKeyboard();
                        btnView.setVisibility(View.GONE);
                        preventView.setVisibility(View.VISIBLE);
                        loaderView.setVisibility(View.VISIBLE);
                        ValidateBankAccount(etAccountNum.getText().toString(), etIfsc.getText().toString(),
                                selectedBankId, false, checkbox.isChecked(), new ApiCallBackTwoMethod() {
                                    @Override
                                    public void onSucess(GetEKYCDetailResponse object) {
                                        GetKycDetails(new ApiCallBackTwoMethod() {
                                            @Override
                                            public void onSucess(GetEKYCDetailResponse object) {

                                            }

                                            @Override
                                            public void onError(Object object) {
                                                loaderView.setVisibility(View.GONE);
                                                preventView.setVisibility(View.GONE);
                                                btnView.setVisibility(View.VISIBLE);
                                                if (object != null) {
                                                    apiErrorTv.setVisibility(View.VISIBLE);
                                                    if (object instanceof GetEKYCDetailResponse) {
                                                        apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                                                    } else {
                                                        apiErrorTv.setText(object + "");
                                                    }
                                                }
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(Object object) {
                                        loaderView.setVisibility(View.GONE);
                                        preventView.setVisibility(View.GONE);
                                        btnView.setVisibility(View.VISIBLE);
                                        if (object != null) {
                                            apiErrorTv.setVisibility(View.VISIBLE);
                                            if (object instanceof GetEKYCDetailResponse) {
                                                apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                                            } else {
                                                apiErrorTv.setText(object + "");
                                            }
                                        }
                                    }
                                });
                    }

                }

            });

            setUi();
            gstRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
                if (i == R.id.gstinYesRadio) {
                    gstinInputView.setVisibility(View.VISIBLE);
                    btnView.setVisibility(View.VISIBLE);
                    verifyBtn.setText("Verify GST");
                    currentStepId = 1;
                } else {
                    gstinInputView.setVisibility(View.GONE);
                    btnView.setVisibility(View.GONE);
                    preventView.setVisibility(View.VISIBLE);
                    loaderView.setVisibility(View.VISIBLE);
                    ValidateGSTINE_KYC("", "0", true, checkbox.isChecked(), new ApiCallBackTwoMethod() {
                        @Override
                        public void onSucess(GetEKYCDetailResponse object) {
                            GetKycDetails(new ApiCallBackTwoMethod() {
                                @Override
                                public void onSucess(GetEKYCDetailResponse object) {

                                }

                                @Override
                                public void onError(Object object) {
                                    loaderView.setVisibility(View.GONE);
                                    preventView.setVisibility(View.GONE);
                                    if (object != null) {
                                        apiErrorTv.setVisibility(View.VISIBLE);
                                        if (object instanceof GetEKYCDetailResponse) {
                                            apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                                        } else {
                                            apiErrorTv.setText(object + "");
                                        }
                                    }
                                }
                            });
                        }

                        @Override
                        public void onError(Object object) {
                            loaderView.setVisibility(View.GONE);
                            preventView.setVisibility(View.GONE);
                            if (object != null) {
                                apiErrorTv.setVisibility(View.VISIBLE);
                                if (object instanceof GetEKYCDetailResponse) {
                                    apiErrorTv.setText(((GetEKYCDetailResponse) object).getMsg() + "");
                                } else {
                                    apiErrorTv.setText(object + "");
                                }
                            }
                        }
                    });
                }
            });


            alertDialog.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (WindowManager.BadTokenException bde) {
            bde.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }
    }

    private void showBank() {
        currentStepId = 4;
        hideKeyboard();
        if (!mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {
            bankView.setVisibility(View.VISIBLE);
            btnView.setVisibility(View.VISIBLE);
            verifyBtn.setText("Verify Bank Account");
            bankDetailView.setVisibility(View.GONE);
        } else {
            bankView.setVisibility(View.GONE);
            btnView.setVisibility(View.GONE);
            bankDetailView.setVisibility(View.VISIBLE);
        }
        aadharInputView.setVisibility(View.GONE);
        aadharDetailView.setVisibility(View.GONE);
        gstinRadioView.setVisibility(View.GONE);
        gstinInputView.setVisibility(View.GONE);
        gstinDetailView.setVisibility(View.GONE);
        panView.setVisibility(View.GONE);
        panDetailView.setVisibility(View.GONE);
        loaderView.setVisibility(View.GONE);
        preventView.setVisibility(View.GONE);
        apiErrorTv.setVisibility(View.GONE);

    }

    private void showPAN() {
        currentStepId = 3;
        hideKeyboard();
        if (!mGetEKYCDetailResponse.getData().isIsPANEKYCDone()) {
            panView.setVisibility(View.VISIBLE);
            btnView.setVisibility(View.VISIBLE);
            verifyBtn.setText("Verify PAN");
            panDetailView.setVisibility(View.GONE);
        } else {
            panView.setVisibility(View.GONE);
            btnView.setVisibility(View.GONE);
            panDetailView.setVisibility(View.VISIBLE);
        }
        aadharInputView.setVisibility(View.GONE);
        aadharDetailView.setVisibility(View.GONE);
        gstinRadioView.setVisibility(View.GONE);
        gstinInputView.setVisibility(View.GONE);
        gstinDetailView.setVisibility(View.GONE);
        bankView.setVisibility(View.GONE);
        bankDetailView.setVisibility(View.GONE);
        loaderView.setVisibility(View.GONE);
        preventView.setVisibility(View.GONE);
        apiErrorTv.setVisibility(View.GONE);
    }

    private void showAadhar() {
        currentStepId = 2;
        hideKeyboard();
        if (!mGetEKYCDetailResponse.getData().isIsAadharEKYCDone()) {
            aadharInputView.setVisibility(View.VISIBLE);
            btnView.setVisibility(View.VISIBLE);
            aadharDetailView.setVisibility(View.GONE);
            if (otpView.getVisibility() == View.VISIBLE) {
                verifyBtn.setText("Verify OTP");
            } else {
                verifyBtn.setText("Verify AADHAR");

            }

        } else {
            aadharInputView.setVisibility(View.GONE);
            btnView.setVisibility(View.GONE);
            aadharDetailView.setVisibility(View.VISIBLE);
        }
        gstinRadioView.setVisibility(View.GONE);
        gstinInputView.setVisibility(View.GONE);
        gstinDetailView.setVisibility(View.GONE);
        panView.setVisibility(View.GONE);
        panDetailView.setVisibility(View.GONE);
        bankView.setVisibility(View.GONE);
        bankDetailView.setVisibility(View.GONE);
        loaderView.setVisibility(View.GONE);
        preventView.setVisibility(View.GONE);
        apiErrorTv.setVisibility(View.GONE);
    }

    private void showGstView() {
        currentStepId = 1;
        previous.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
        next.setVisibility(View.VISIBLE);
        hideKeyboard();
        if (!mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() && mGetEKYCDetailResponse.getData().isIsGSTSkipped()) {
            gstinRadioView.setVisibility(View.VISIBLE);
            gstinInputView.setVisibility(View.GONE);
            gstinDetailView.setVisibility(View.GONE);
            btnView.setVisibility(View.GONE);
        } else if (!mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() && !mGetEKYCDetailResponse.getData().isIsGSTSkipped()) {
            gstinRadioView.setVisibility(View.VISIBLE);
            gstinInputView.setVisibility(View.VISIBLE);
            gstinDetailView.setVisibility(View.GONE);
            btnView.setVisibility(View.VISIBLE);
            verifyBtn.setText("Verify GST");
        } else {
            gstinRadioView.setVisibility(View.GONE);
            gstinInputView.setVisibility(View.GONE);
            gstinDetailView.setVisibility(View.VISIBLE);
            btnView.setVisibility(View.GONE);
        }
        aadharInputView.setVisibility(View.GONE);
        aadharDetailView.setVisibility(View.GONE);
        panView.setVisibility(View.GONE);
        panDetailView.setVisibility(View.GONE);
        bankView.setVisibility(View.GONE);
        bankDetailView.setVisibility(View.GONE);
        loaderView.setVisibility(View.GONE);
        preventView.setVisibility(View.GONE);
        apiErrorTv.setVisibility(View.GONE);
    }

    void setUi() {
        try {

            //1=GST
            //2=AADHAR
            //3=PAN
            //4=Bank
            stepsList = new ArrayList<>();
            previous.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
            next.setVisibility(View.GONE);

            chooseCompError.setVisibility(View.GONE);
            chooseDirectorError.setVisibility(View.GONE);
            gstinError.setVisibility(View.GONE);
            panError.setVisibility(View.GONE);
            chooseBankError.setVisibility(View.GONE);
            accountNumError.setVisibility(View.GONE);
            ifscError.setVisibility(View.GONE);
            aadharError.setVisibility(View.GONE);
            aadharEditIv.setVisibility(View.GONE);
            otpView.setVisibility(View.GONE);
            otpError.setVisibility(View.GONE);
            loaderView.setVisibility(View.GONE);
            preventView.setVisibility(View.GONE);
            apiErrorTv.setVisibility(View.GONE);
            consentError.setVisibility(View.GONE);

            currentStepId = 0;
            selectedDirectorValue = "";
            selectedBankId = 0;
            selectedBank = null;
            selectedComValue = "";
            checkbox.setChecked(false);

            for (EkycType item : mGetEKYCDetailResponse.getData().getEkycType()) {
                stepsList.add(item.getId());
            }


            if (mGetEKYCDetailResponse.getData().getEkycType().size() == 4) {


                if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped())
                        && !mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                        !mGetEKYCDetailResponse.getData().isIsPANEKYCDone() &&
                        !mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {
                    visibleStepsList.clear();
                    visibleStepsList.add(1);
                    visibleStepsList.add(2);


                } else if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped())
                        && mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                        !mGetEKYCDetailResponse.getData().isIsPANEKYCDone() &&
                        !mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {
                    visibleStepsList.clear();
                    visibleStepsList.add(1);
                    visibleStepsList.add(2);
                    visibleStepsList.add(3);
                } else if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                        mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                        mGetEKYCDetailResponse.getData().isIsPANEKYCDone() &&
                        !mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {
                    visibleStepsList.clear();
                    visibleStepsList.add(1);
                    visibleStepsList.add(2);
                    visibleStepsList.add(3);
                    visibleStepsList.add(4);

                } else if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                        mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                        mGetEKYCDetailResponse.getData().isIsPANEKYCDone() &&
                        mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {
                    visibleStepsList.clear();
                    visibleStepsList.add(1);
                    visibleStepsList.add(2);
                    visibleStepsList.add(3);
                    visibleStepsList.add(4);
                } else {
                    visibleStepsList.clear();
                    visibleStepsList.add(1);

                }
            } else if (mGetEKYCDetailResponse.getData().getEkycType().size() == 3) {
                if (!stepsList.contains(1)) {

                    if (mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                            !mGetEKYCDetailResponse.getData().isIsPANEKYCDone() &&
                            !mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {

                        visibleStepsList.clear();
                        visibleStepsList.add(2);
                        visibleStepsList.add(3);
                    } else if (mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                            mGetEKYCDetailResponse.getData().isIsPANEKYCDone() &&
                            !mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {
                        visibleStepsList.clear();
                        visibleStepsList.add(2);
                        visibleStepsList.add(3);
                        visibleStepsList.add(4);
                    } else if (mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                            mGetEKYCDetailResponse.getData().isIsPANEKYCDone() &&
                            mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {
                        visibleStepsList.clear();
                        visibleStepsList.add(2);
                        visibleStepsList.add(3);
                        visibleStepsList.add(4);
                    } else {

                        visibleStepsList.clear();
                        visibleStepsList.add(2);
                    }
                } else if (!stepsList.contains(2)) {

                    if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                            !mGetEKYCDetailResponse.getData().isIsPANEKYCDone() &&
                            !mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {

                        visibleStepsList.clear();
                        visibleStepsList.add(1);
                        visibleStepsList.add(3);
                    } else if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                            mGetEKYCDetailResponse.getData().isIsPANEKYCDone() &&
                            !mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {
                        visibleStepsList.clear();
                        visibleStepsList.add(1);
                        visibleStepsList.add(3);
                        visibleStepsList.add(4);
                    } else if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                            mGetEKYCDetailResponse.getData().isIsPANEKYCDone() &&
                            mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {
                        visibleStepsList.clear();
                        visibleStepsList.add(1);
                        visibleStepsList.add(3);
                        visibleStepsList.add(4);
                    } else {
                        visibleStepsList.clear();
                        visibleStepsList.add(1);

                    }
                } else if (!stepsList.contains(3)) {

                    if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                            !mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                            !mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {

                        visibleStepsList.clear();
                        visibleStepsList.add(1);
                        visibleStepsList.add(2);
                    } else if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                            mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                            !mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {
                        visibleStepsList.clear();
                        visibleStepsList.add(1);
                        visibleStepsList.add(2);
                        visibleStepsList.add(3);
                    } else if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                            mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                            mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {
                        visibleStepsList.clear();
                        visibleStepsList.add(1);
                        visibleStepsList.add(2);
                        visibleStepsList.add(3);
                    } else {
                        visibleStepsList.clear();
                        visibleStepsList.add(1);
                    }
                } else {


                    if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                            !mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                            !mGetEKYCDetailResponse.getData().isIsPANEKYCDone()) {

                        visibleStepsList.clear();
                        visibleStepsList.add(1);
                        visibleStepsList.add(2);

                    } else if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                            mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                            !mGetEKYCDetailResponse.getData().isIsPANEKYCDone()) {
                        visibleStepsList.clear();
                        visibleStepsList.add(1);
                        visibleStepsList.add(2);
                        visibleStepsList.add(3);
                    } else if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                            mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                            mGetEKYCDetailResponse.getData().isIsPANEKYCDone()) {
                        visibleStepsList.clear();
                        visibleStepsList.add(1);
                        visibleStepsList.add(2);
                        visibleStepsList.add(3);

                    } else {
                        visibleStepsList.clear();
                        visibleStepsList.add(1);

                    }
                }


            } else if (mGetEKYCDetailResponse.getData().getEkycType().size() == 2) {

                if (!stepsList.contains(1) && !stepsList.contains(2)) {

                    if (!mGetEKYCDetailResponse.getData().isIsPANEKYCDone() &&
                            !mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {

                        visibleStepsList.clear();
                        visibleStepsList.add(3);
                    } else if (mGetEKYCDetailResponse.getData().isIsPANEKYCDone() &&
                            !mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {

                        visibleStepsList.clear();
                        visibleStepsList.add(3);
                        visibleStepsList.add(4);
                    } else {

                        visibleStepsList.clear();
                        visibleStepsList.add(3);
                        visibleStepsList.add(4);
                    }

                } else if (!stepsList.contains(1) && !stepsList.contains(3)) {

                    if (!mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                            !mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {

                        visibleStepsList.clear();

                        visibleStepsList.add(2);
                    } else if (mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                            !mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {

                        visibleStepsList.clear();
                        visibleStepsList.add(2);
                        visibleStepsList.add(4);
                    } else {

                        visibleStepsList.clear();
                        visibleStepsList.add(2);
                        visibleStepsList.add(4);
                    }


                } else if (!stepsList.contains(1) && !stepsList.contains(4)) {

                    if (!mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                            !mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {

                        visibleStepsList.clear();
                        visibleStepsList.add(2);
                    } else if (mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() &&
                            !mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {

                        visibleStepsList.clear();
                        visibleStepsList.add(2);
                        visibleStepsList.add(3);
                    } else {

                        visibleStepsList.clear();
                        visibleStepsList.add(2);
                        visibleStepsList.add(3);
                    }

                } else if (!stepsList.contains(2) && !stepsList.contains(3)) {


                    if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                            !mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {

                        visibleStepsList.clear();
                        visibleStepsList.add(1);
                        visibleStepsList.add(4);
                    } else if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                            mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone()) {

                        visibleStepsList.clear();
                        visibleStepsList.add(1);
                        visibleStepsList.add(4);
                    } else {

                        visibleStepsList.clear();
                        visibleStepsList.add(1);

                    }


                } else if (!stepsList.contains(2) && !stepsList.contains(4)) {

                    if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                            !mGetEKYCDetailResponse.getData().isIsPANEKYCDone()) {

                        visibleStepsList.clear();
                        visibleStepsList.add(1);
                        visibleStepsList.add(3);
                    } else if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                            mGetEKYCDetailResponse.getData().isIsPANEKYCDone()) {

                        visibleStepsList.clear();
                        visibleStepsList.add(1);
                        visibleStepsList.add(3);
                    } else {

                        visibleStepsList.clear();
                        visibleStepsList.add(1);

                    }


                } else if (!stepsList.contains(3) && !stepsList.contains(4)) {


                    if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                            !mGetEKYCDetailResponse.getData().isIsAadharEKYCDone()) {

                        visibleStepsList.clear();
                        visibleStepsList.add(1);
                        visibleStepsList.add(2);
                    } else if ((mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() || mGetEKYCDetailResponse.getData().isIsGSTSkipped()) &&
                            mGetEKYCDetailResponse.getData().isIsAadharEKYCDone()) {

                        visibleStepsList.clear();
                        visibleStepsList.add(1);
                        visibleStepsList.add(2);
                    } else {

                        visibleStepsList.clear();
                        visibleStepsList.add(1);

                    }
                } else {
                    visibleStepsList.clear();

                }


            } else {
                visibleStepsList.clear();

            }


            totalSteps.setText("/" + mGetEKYCDetailResponse.getData().getEkycType().size() + "");
            int completeStep = mGetEKYCDetailResponse.getData().getStepCompleted();
            if (completeStep < mGetEKYCDetailResponse.getData().getEkycType().size()) {
                int currentStep = completeStep + 1;
                currentSteps.setText(currentStep + "");
            } else {
                currentSteps.setText(completeStep + "");
            }
            if (completeStep == 0) {
                previous.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
            } else {
                previous.setVisibility(View.VISIBLE);
                line.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
            }


            gstinNoRadio.setChecked(mGetEKYCDetailResponse.getData().isIsGSTSkipped());
            if (mGetEKYCDetailResponse.getData().getGstin() != null && !mGetEKYCDetailResponse.getData().getGstin().isEmpty()) {
                gstinNumber.setText(mGetEKYCDetailResponse.getData().getGstin());
            }
            if (mGetEKYCDetailResponse.getData().getSelectedDirector() != null && !mGetEKYCDetailResponse.getData().getSelectedDirector().isEmpty()) {
                directordetail.setText(mGetEKYCDetailResponse.getData().getSelectedDirector());
                panNumLabel.setText("PAN of " + mGetEKYCDetailResponse.getData().getSelectedDirector());
                panLabel.setText("Enter PAN of " + mGetEKYCDetailResponse.getData().getSelectedDirector());
                directordetailView.setVisibility(View.VISIBLE);
            } else {
                directordetailView.setVisibility(View.GONE);
            }
            if (mGetEKYCDetailResponse.getData().getAadharNo() != null && !mGetEKYCDetailResponse.getData().getAadharNo().isEmpty()) {
                aadharNumber.setText(mGetEKYCDetailResponse.getData().getAadharNo());
            }
            if (mGetEKYCDetailResponse.getData().getPan() != null && mGetEKYCDetailResponse.getData().getPanOfDirector() != null &&
                    mGetEKYCDetailResponse.getData().getPanOfDirector().equalsIgnoreCase(mGetEKYCDetailResponse.getData().getPan())) {
                panNum.setText(mGetEKYCDetailResponse.getData().getPanOfDirector());
                companyPan.setText(mGetEKYCDetailResponse.getData().getPan());
                compPanNumber.setText(mGetEKYCDetailResponse.getData().getPan());
                directorPanDetailView.setVisibility(View.VISIBLE);
                companyPanView.setVisibility(View.VISIBLE);
                compPanNumberView.setVisibility(View.GONE);
            } else {
                if (mGetEKYCDetailResponse.getData().getPan() != null && !mGetEKYCDetailResponse.getData().getPan().isEmpty()) {
                    companyPan.setText(mGetEKYCDetailResponse.getData().getPan());
                    compPanNumber.setText(mGetEKYCDetailResponse.getData().getPan());
                    companyPanView.setVisibility(View.VISIBLE);
                    compPanNumberView.setVisibility(View.VISIBLE);
                } else {
                    companyPanView.setVisibility(View.GONE);
                    compPanNumberView.setVisibility(View.GONE);
                }

                if (mGetEKYCDetailResponse.getData().getPanOfDirector() != null && !mGetEKYCDetailResponse.getData().getPanOfDirector().isEmpty()) {
                    panNum.setText(mGetEKYCDetailResponse.getData().getPanOfDirector());
                    directorPanDetailView.setVisibility(View.VISIBLE);
                } else {
                    directorPanDetailView.setVisibility(View.GONE);
                }
            }


            if (mGetEKYCDetailResponse.getData().getSelectedBank() != null && !mGetEKYCDetailResponse.getData().getSelectedBank().isEmpty()) {
                bankName.setText(mGetEKYCDetailResponse.getData().getSelectedBank());
                bankNameView.setVisibility(View.VISIBLE);
            } else {
                bankNameView.setVisibility(View.GONE);
            }
            if (mGetEKYCDetailResponse.getData().getAccountNumber() != null && !mGetEKYCDetailResponse.getData().getAccountNumber().isEmpty()) {
                accountNum.setText(mGetEKYCDetailResponse.getData().getAccountNumber());
                accountNumView.setVisibility(View.VISIBLE);
            } else {
                accountNumView.setVisibility(View.GONE);
            }
            if (mGetEKYCDetailResponse.getData().getIfsc() != null && !mGetEKYCDetailResponse.getData().getIfsc().isEmpty()) {
                ifscCode.setText(mGetEKYCDetailResponse.getData().getIfsc());
                ifscView.setVisibility(View.VISIBLE);
            } else {
                ifscView.setVisibility(View.GONE);
            }
            if (mGetEKYCDetailResponse.getData().getAccountHolder() != null && !mGetEKYCDetailResponse.getData().getAccountHolder().isEmpty()) {
                accountName.setText(mGetEKYCDetailResponse.getData().getAccountHolder());
                accountNameView.setVisibility(View.VISIBLE);
            } else {
                accountNameView.setVisibility(View.GONE);
            }


            if (!mGetEKYCDetailResponse.getData().isIsGSTINEKYCDone() && stepsList.contains(1)) {
                if (mGetEKYCDetailResponse.getData().isIsGSTSkipped() && mGetEKYCDetailResponse.getData().isIsGSTIN()) {

                    if (!mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() && stepsList.contains(2)) {
                        currentStepId = 2;
                        aadharInputView.setVisibility(View.VISIBLE);
                        if (mGetEKYCDetailResponse.getData().getDirectors() != null && mGetEKYCDetailResponse.getData().getDirectors().size() > 0) {
                            directerView.setVisibility(View.VISIBLE);
                        } else {
                            directerView.setVisibility(View.GONE);
                        }
                        btnView.setVisibility(View.VISIBLE);
                        gstinRadioView.setVisibility(View.GONE);
                        gstinInputView.setVisibility(View.GONE);
                        gstinDetailView.setVisibility(View.GONE);
                        otpView.setVisibility(View.GONE);
                        aadharDetailView.setVisibility(View.GONE);
                        companyPanView.setVisibility(View.GONE);
                        panView.setVisibility(View.GONE);
                        panDetailView.setVisibility(View.GONE);
                        bankView.setVisibility(View.GONE);
                        bankDetailView.setVisibility(View.GONE);
                        verifyBtn.setText("Verify AADHAR");
                    } else if (!mGetEKYCDetailResponse.getData().isIsPANEKYCDone() && stepsList.contains(3)) {
                        currentStepId = 3;
                        aadharInputView.setVisibility(View.GONE);
                        btnView.setVisibility(View.VISIBLE);
                        gstinRadioView.setVisibility(View.GONE);
                        gstinInputView.setVisibility(View.GONE);
                        gstinDetailView.setVisibility(View.GONE);
                        directerView.setVisibility(View.GONE);
                        otpView.setVisibility(View.GONE);
                        aadharDetailView.setVisibility(View.GONE);
                        companyPanView.setVisibility(View.GONE);
                        panView.setVisibility(View.VISIBLE);
                        panDetailView.setVisibility(View.GONE);
                        bankView.setVisibility(View.GONE);
                        bankDetailView.setVisibility(View.GONE);
                        verifyBtn.setText("Verify PAN");
                    } else if (!mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone() && stepsList.contains(4)) {
                        currentStepId = 4;
                        aadharInputView.setVisibility(View.GONE);
                        btnView.setVisibility(View.VISIBLE);
                        gstinRadioView.setVisibility(View.GONE);
                        gstinInputView.setVisibility(View.GONE);
                        gstinDetailView.setVisibility(View.GONE);
                        directerView.setVisibility(View.GONE);
                        otpView.setVisibility(View.GONE);
                        aadharDetailView.setVisibility(View.GONE);
                        companyPanView.setVisibility(View.GONE);
                        panView.setVisibility(View.GONE);
                        panDetailView.setVisibility(View.GONE);
                        bankView.setVisibility(View.VISIBLE);
                        bankDetailView.setVisibility(View.GONE);
                        verifyBtn.setText("Verify Bank Account");
                    } else {

                        gstinRadioView.setVisibility(View.GONE);
                        gstinInputView.setVisibility(View.GONE);
                        gstinDetailView.setVisibility(View.VISIBLE);
                        aadharInputView.setVisibility(View.GONE);
                        aadharDetailView.setVisibility(View.VISIBLE);
                        panView.setVisibility(View.GONE);
                        panDetailView.setVisibility(View.VISIBLE);
                        bankView.setVisibility(View.GONE);
                        bankDetailView.setVisibility(View.VISIBLE);
                        btnView.setVisibility(View.GONE);
                       /* currentStepId = 1;
                        gstinRadioView.setVisibility(View.VISIBLE);
                        gstinInputView.setVisibility(View.GONE);
                        gstinDetailView.setVisibility(View.GONE);
                        directerView.setVisibility(View.GONE);
                        aadharInputView.setVisibility(View.GONE);
                        otpView.setVisibility(View.GONE);
                        aadharDetailView.setVisibility(View.GONE);
                        companyPanView.setVisibility(View.GONE);
                        panView.setVisibility(View.GONE);
                        panDetailView.setVisibility(View.GONE);
                        bankView.setVisibility(View.GONE);
                        bankDetailView.setVisibility(View.GONE);
                        btnView.setVisibility(View.GONE);
                        verifyBtn.setText("Verify GST");*/
                    }


                } else {
                    currentStepId = 1;
                    gstinRadioView.setVisibility(View.VISIBLE);
                    gstinInputView.setVisibility(View.GONE);
                    gstinDetailView.setVisibility(View.GONE);
                    directerView.setVisibility(View.GONE);
                    aadharInputView.setVisibility(View.GONE);
                    otpView.setVisibility(View.GONE);
                    aadharDetailView.setVisibility(View.GONE);
                    companyPanView.setVisibility(View.GONE);
                    panView.setVisibility(View.GONE);
                    panDetailView.setVisibility(View.GONE);
                    bankView.setVisibility(View.GONE);
                    bankDetailView.setVisibility(View.GONE);
                    btnView.setVisibility(View.GONE);
                    verifyBtn.setText("Verify GST");
                }
            } else if (!mGetEKYCDetailResponse.getData().isIsAadharEKYCDone() && stepsList.contains(2)) {
                currentStepId = 2;
                aadharInputView.setVisibility(View.VISIBLE);
                btnView.setVisibility(View.VISIBLE);
                if (mGetEKYCDetailResponse.getData().getDirectors() != null && mGetEKYCDetailResponse.getData().getDirectors().size() > 0) {
                    directerView.setVisibility(View.VISIBLE);
                } else {
                    directerView.setVisibility(View.GONE);
                }

                gstinRadioView.setVisibility(View.GONE);
                gstinInputView.setVisibility(View.GONE);
                gstinDetailView.setVisibility(View.GONE);
                otpView.setVisibility(View.GONE);
                aadharDetailView.setVisibility(View.GONE);
                companyPanView.setVisibility(View.GONE);
                panView.setVisibility(View.GONE);
                panDetailView.setVisibility(View.GONE);
                bankView.setVisibility(View.GONE);
                bankDetailView.setVisibility(View.GONE);
                verifyBtn.setText("Verify AADHAR");
            } else if (!mGetEKYCDetailResponse.getData().isIsPANEKYCDone() && stepsList.contains(3)) {
                currentStepId = 3;

                panView.setVisibility(View.VISIBLE);
                btnView.setVisibility(View.VISIBLE);
                directerView.setVisibility(View.GONE);
                gstinRadioView.setVisibility(View.GONE);
                gstinInputView.setVisibility(View.GONE);
                gstinDetailView.setVisibility(View.GONE);
                otpView.setVisibility(View.GONE);
                aadharDetailView.setVisibility(View.GONE);
                aadharInputView.setVisibility(View.GONE);
                panDetailView.setVisibility(View.GONE);
                bankView.setVisibility(View.GONE);
                bankDetailView.setVisibility(View.GONE);
                verifyBtn.setText("Verify PAN");
            } else if (!mGetEKYCDetailResponse.getData().isIsBanckAccountEKYCDone() && stepsList.contains(4)) {
                currentStepId = 4;
                bankView.setVisibility(View.VISIBLE);
                btnView.setVisibility(View.VISIBLE);
                directerView.setVisibility(View.GONE);
                gstinRadioView.setVisibility(View.GONE);
                gstinInputView.setVisibility(View.GONE);
                gstinDetailView.setVisibility(View.GONE);
                otpView.setVisibility(View.GONE);
                aadharDetailView.setVisibility(View.GONE);
                aadharInputView.setVisibility(View.GONE);
                panDetailView.setVisibility(View.GONE);
                panView.setVisibility(View.GONE);
                bankDetailView.setVisibility(View.GONE);
                verifyBtn.setText("Verify Bank Account");
            } else {
                gstinRadioView.setVisibility(View.GONE);
                gstinInputView.setVisibility(View.GONE);
                gstinDetailView.setVisibility(View.VISIBLE);
                aadharInputView.setVisibility(View.GONE);
                aadharDetailView.setVisibility(View.VISIBLE);
                panView.setVisibility(View.GONE);
                panDetailView.setVisibility(View.VISIBLE);
                bankView.setVisibility(View.GONE);
                bankDetailView.setVisibility(View.VISIBLE);
                btnView.setVisibility(View.GONE);
            }
        } catch (NullPointerException npe) {

        } catch (Exception e) {

        }
    }

    public void hideKeyboard() {
        if (imm == null) {
            imm = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        }

        if (etAadhar.hasFocus()) {
            imm.hideSoftInputFromWindow(etAadhar.getWindowToken(), 0);
        } else if (etOTP.hasFocus()) {
            imm.hideSoftInputFromWindow(etOTP.getWindowToken(), 0);
        } else if (etGstin.hasFocus()) {
            imm.hideSoftInputFromWindow(etGstin.getWindowToken(), 0);
        } else if (etPan.hasFocus()) {
            imm.hideSoftInputFromWindow(etPan.getWindowToken(), 0);
        } else if (etAccountNum.hasFocus()) {
            imm.hideSoftInputFromWindow(etAccountNum.getWindowToken(), 0);
        } else if (etIfsc.hasFocus()) {
            imm.hideSoftInputFromWindow(etIfsc.getWindowToken(), 0);
        }


    }

    public void showDropDownPopup(View v, final int selectPos, List<EKycDirectorOrCompany> mDropDownList, ClickDropDownItem mClickDropDownItem) {
        if (popup != null && popup.isShowing()) {
            return;
        }
        popup = new PopupWindow(mActivity);
        View layout = mActivity.getLayoutInflater().inflate(R.layout.dialog_drop_down, null);
        RecyclerView recyclerView = layout.findViewById(R.id.recyclerView);
        LinearLayout view = layout.findViewById(R.id.viewContainer);
        view.getLayoutParams().width = v.getMeasuredWidth();
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));


        DropDownEKYCStepsAdapter mDropDownListAdapter = new DropDownEKYCStepsAdapter(mActivity, false, mDropDownList, selectPos, (clickPosition, value1, object) -> {
            if (popup != null) {
                popup.dismiss();
            }


            if (mClickDropDownItem != null) {
                mClickDropDownItem.onClick(clickPosition, value1, object);
            }

        });
        recyclerView.setAdapter(mDropDownListAdapter);

        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int margine = (int) mActivity.getResources().getDimension(R.dimen._5sdp);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popup.setOverlapAnchor(true);
            popup.showAsDropDown(v, -margine, -margine);
        } else {
            // Show anchored to button
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            popup.showAtLocation(v, Gravity.NO_GRAVITY, (int) (location[0] - margine), (int) (location[1] - margine));

        }
        // popup.showAsDropDown(v);

    }

    public void GetKycDetails(ApiCallBackTwoMethod mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetEKYCDetailResponse> call = git.GetE_KYCDetail(new BasicRequest(LoginDataResponse.getData().getUserID() + "",
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetEKYCDetailResponse>() {
                @Override
                public void onResponse(Call<GetEKYCDetailResponse> call, final retrofit2.Response<GetEKYCDetailResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            mGetEKYCDetailResponse = response.body();
                            if (mGetEKYCDetailResponse != null) {


                                if (mGetEKYCDetailResponse.getStatuscode() == 1) {

                                    if (mGetEKYCDetailResponse.getData() != null) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(mGetEKYCDetailResponse);
                                        }
                                        mAppPreferences.set(ApplicationConstant.INSTANCE.isEKYCComplete, mGetEKYCDetailResponse.getData().isIsEKYCDone());
                                        if (!mGetEKYCDetailResponse.getData().isIsEKYCDone() &&
                                                mGetEKYCDetailResponse.getData().getEkycType() != null &&
                                                mGetEKYCDetailResponse.getData().getEkycType().size() > 0) {
                                            if (alertDialog != null && alertDialog.isShowing()) {
                                                setUi();
                                            } else {
                                                showStepsDialog();
                                            }

                                        } else {
                                            getUserDetail();
                                        }
                                    } else {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(mGetEKYCDetailResponse);
                                        }
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(mGetEKYCDetailResponse);
                                    }
                                   /* if (mGetEKYCDetailResponse.isIsVersionValid() == false) {
                                        ApiUtilMethods.INSTANCE.versionDialog(mActivity);
                                    } else {
                                        ApiUtilMethods.INSTANCE.Error(mActivity, mGetEKYCDetailResponse.getMsg() + "");
                                    }*/
                                }
                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(mActivity.getResources().getString(R.string.some_thing_error));
                                }
                            }

                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(response.message() + "");
                            }
                            // ApiUtilMethods.INSTANCE.apiErrorHandle(mActivity, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (mApiCallBack != null) {
                            mApiCallBack.onError(e.getMessage() + "");
                        }
                        // ApiUtilMethods.INSTANCE.Error(mActivity, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<GetEKYCDetailResponse> call, Throwable t) {

                    if (mApiCallBack != null) {
                        mApiCallBack.onError(t.getMessage() + "");
                    }
                    //    ApiUtilMethods.INSTANCE.apiFailureError(mActivity, t);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            if (mApiCallBack != null) {
                mApiCallBack.onError(e.getMessage() + "");
            }
            // ApiUtilMethods.INSTANCE.Error(mActivity, e.getMessage() + "");
        }
    }

    public void getUserDetail() {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetUserResponse> call = git.GetProfile(new BasicRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<GetUserResponse>() {

                @Override
                public void onResponse(Call<GetUserResponse> call, retrofit2.Response<GetUserResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            GetUserResponse mGetUserResponse = response.body();
                            if (mGetUserResponse != null) {
                                ApiFintechUtilMethods.INSTANCE.mGetUserResponse = mGetUserResponse;

                                if (mGetUserResponse.getStatuscode() == 1) {
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.UserDetailPref, new Gson().toJson(mGetUserResponse));

                                    mAppPreferences.set(ApplicationConstant.INSTANCE.IsRealApiPref, mGetUserResponse.getUserInfo().isRealAPI());

                                    LoginDataResponse.getData().setDoubleFactor(mGetUserResponse.getUserInfo().isDoubleFactor());
                                    ApiFintechUtilMethods.INSTANCE.mTempLoginDataResponse = LoginDataResponse;
                                    mAppPreferences.set(ApplicationConstant.INSTANCE.LoginPref, new Gson().toJson(LoginDataResponse));

                                    if (alertDialog != null && alertDialog.isShowing()) {
                                        alertDialog.dismiss();
                                        if (mGetUserResponse.getUserInfo().getKycStatus() == 2 || mGetUserResponse.getUserInfo().getKycStatus() == 3) {
                                            ApiFintechUtilMethods.INSTANCE.Successful(mActivity, "E-KYC Successfully Verified");
                                        } else {
                                            CustomAlertDialog customPassDialog = new CustomAlertDialog(mActivity);
                                            showCallOnBoardingMsgs(mActivity, mGetUserResponse, customPassDialog);
                                        }

                                    } else {
                                        if (mGetUserResponse.getUserInfo().getKycStatus() != 2 && mGetUserResponse.getUserInfo().getKycStatus() != 3) {
                                            CustomAlertDialog customPassDialog = new CustomAlertDialog(mActivity);
                                            showCallOnBoardingMsgs(mActivity, mGetUserResponse, customPassDialog);
                                        }
                                    }


                                } else {

                                    // ApiUtilMethods.INSTANCE.Error(mActivity, mGetUserResponse.getMsg() + "");
                                }

                            } else {

                                // ApiUtilMethods.INSTANCE.Error(mActivity, mActivity.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            // ApiUtilMethods.INSTANCE.apiErrorHandle(mActivity, response.code(), response.message());
                        }

                    } catch (Exception e) {
                        // ApiUtilMethods.INSTANCE.Error(mActivity,  e.getMessage()+"");
                    }
                }


                @Override
                public void onFailure(Call<GetUserResponse> call, Throwable t) {

                    try {

                        //ApiUtilMethods.INSTANCE.apiFailureError(mActivity, t);


                    } catch (IllegalStateException ise) {

                        //ApiUtilMethods.INSTANCE.Error(mActivity, mActivity.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            // ApiUtilMethods.INSTANCE.Error(mActivity, mActivity.getResources().getString(R.string.some_thing_error));
        }

    }

    public boolean showCallOnBoardingMsgs(Activity context, GetUserResponse mGetUserResponse, CustomAlertDialog customPassDialog) {
        if (mGetUserResponse.getUserInfo().getKycStatus() == 5) {
            customPassDialog.showKycMsg(context.getResources().getString(R.string.REJECT), "KYC is rejected, Please re-apply KYC", R.drawable.reject4, false, mGetUserResponse);
            return false;
        } else if (mGetUserResponse.getUserInfo().getKycStatus() == 4) {
            customPassDialog.showKycMsg(context.getResources().getString(R.string.INCOMPLETE), "KYC is not complete, Please re-apply KYC", R.drawable.incomplete5, false, mGetUserResponse);
            return false;
        } else if (mGetUserResponse.getUserInfo().getKycStatus() == 1) {
            customPassDialog.showKycMsg(context.getResources().getString(R.string.REJECT), "KYC is not applied, Please apply KYC", R.drawable.incomplete5, false, mGetUserResponse);
            return false;
        } else {
            customPassDialog.showKycMsg(context.getResources().getString(R.string.UNDERSCREENING), "KYC verification is under process , Please wait some time", R.drawable.underscreaning7, true, mGetUserResponse);
            return false;
        }

    }

    public void ValidateGSTINE_KYC(String verificationAccount, String companyTypeID, boolean isSkip, boolean isConcent
            , ApiCallBackTwoMethod mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetEKYCDetailResponse> call = git.ValidateGSTINE_KYC(new EKycStepsValidationRequest(verificationAccount, companyTypeID, isSkip, isConcent, LoginDataResponse.getData().getUserID() + "",
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetEKYCDetailResponse>() {
                @Override
                public void onResponse(Call<GetEKYCDetailResponse> call, final retrofit2.Response<GetEKYCDetailResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            GetEKYCDetailResponse mGetEKYCDetailResponse = response.body();
                            if (mGetEKYCDetailResponse != null) {


                                if (mGetEKYCDetailResponse.getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mGetEKYCDetailResponse);
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(mGetEKYCDetailResponse);
                                    }
                                   /* if (mGetEKYCDetailResponse.isIsVersionValid() == false) {
                                        ApiUtilMethods.INSTANCE.versionDialog(mActivity);
                                    } else {
                                        ApiUtilMethods.INSTANCE.Error(mActivity, mGetEKYCDetailResponse.getMsg() + "");
                                    }*/
                                }
                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(mActivity.getResources().getString(R.string.some_thing_error));
                                }
                            }

                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(response.message() + "");
                            }
                            // ApiUtilMethods.INSTANCE.apiErrorHandle(mActivity, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (mApiCallBack != null) {
                            mApiCallBack.onError(e.getMessage() + "");
                        }
                        // ApiUtilMethods.INSTANCE.Error(mActivity, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<GetEKYCDetailResponse> call, Throwable t) {

                    if (mApiCallBack != null) {
                        mApiCallBack.onError(t.getMessage() + "");
                    }
                    //    ApiUtilMethods.INSTANCE.apiFailureError(mActivity, t);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            if (mApiCallBack != null) {
                mApiCallBack.onError(e.getMessage() + "");
            }
            // ApiUtilMethods.INSTANCE.Error(mActivity, e.getMessage() + "");
        }
    }

    public void ValidatePAN(String verificationAccount, String director, boolean isSkip, boolean isConcent
            , ApiCallBackTwoMethod mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetEKYCDetailResponse> call = git.ValidatePAN(new EKycStepsValidationRequest(isSkip, verificationAccount, director, isConcent, LoginDataResponse.getData().getUserID() + "",
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetEKYCDetailResponse>() {
                @Override
                public void onResponse(Call<GetEKYCDetailResponse> call, final retrofit2.Response<GetEKYCDetailResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            GetEKYCDetailResponse mGetEKYCDetailResponse = response.body();
                            if (mGetEKYCDetailResponse != null) {


                                if (mGetEKYCDetailResponse.getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mGetEKYCDetailResponse);
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(mGetEKYCDetailResponse);
                                    }
                                   /* if (mGetEKYCDetailResponse.isIsVersionValid() == false) {
                                        ApiUtilMethods.INSTANCE.versionDialog(mActivity);
                                    } else {
                                        ApiUtilMethods.INSTANCE.Error(mActivity, mGetEKYCDetailResponse.getMsg() + "");
                                    }*/
                                }
                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(mActivity.getResources().getString(R.string.some_thing_error));
                                }
                            }

                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(response.message() + "");
                            }
                            //  ApiUtilMethods.INSTANCE.apiErrorHandle(mActivity, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (mApiCallBack != null) {
                            mApiCallBack.onError(e.getMessage() + "");
                        }
                        // ApiUtilMethods.INSTANCE.Error(mActivity, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<GetEKYCDetailResponse> call, Throwable t) {

                    if (mApiCallBack != null) {
                        mApiCallBack.onError(t.getMessage() + "");
                    }
                    //    ApiUtilMethods.INSTANCE.apiFailureError(mActivity, t);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            if (mApiCallBack != null) {
                mApiCallBack.onError(e.getMessage() + "");
            }
            // ApiUtilMethods.INSTANCE.Error(mActivity, e.getMessage() + "");
        }
    }

    public void ValidateBankAccount(String verificationAccount, String ifsc, int bankId, boolean isSkip, boolean isConcent
            , ApiCallBackTwoMethod mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetEKYCDetailResponse> call = git.ValidateBankAccount(new EKycStepsValidationRequest(verificationAccount, ifsc, bankId, isSkip, isConcent, LoginDataResponse.getData().getUserID() + "",
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetEKYCDetailResponse>() {
                @Override
                public void onResponse(Call<GetEKYCDetailResponse> call, final retrofit2.Response<GetEKYCDetailResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            GetEKYCDetailResponse mGetEKYCDetailResponse = response.body();
                            if (mGetEKYCDetailResponse != null) {


                                if (mGetEKYCDetailResponse.getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mGetEKYCDetailResponse);
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(mGetEKYCDetailResponse);
                                    }
                                   /* if (mGetEKYCDetailResponse.isIsVersionValid() == false) {
                                        ApiUtilMethods.INSTANCE.versionDialog(mActivity);
                                    } else {
                                        ApiUtilMethods.INSTANCE.Error(mActivity, mGetEKYCDetailResponse.getMsg() + "");
                                    }*/
                                }
                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(mActivity.getResources().getString(R.string.some_thing_error));
                                }
                            }

                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(response.message() + "");
                            }
                            // ApiUtilMethods.INSTANCE.apiErrorHandle(mActivity, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (mApiCallBack != null) {
                            mApiCallBack.onError(e.getMessage() + "");
                        }
                        // ApiUtilMethods.INSTANCE.Error(mActivity, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<GetEKYCDetailResponse> call, Throwable t) {

                    if (mApiCallBack != null) {
                        mApiCallBack.onError(t.getMessage() + "");
                    }
                    //    ApiUtilMethods.INSTANCE.apiFailureError(mActivity, t);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            if (mApiCallBack != null) {
                mApiCallBack.onError(e.getMessage() + "");
            }
            // ApiUtilMethods.INSTANCE.Error(mActivity, e.getMessage() + "");
        }
    }

    public void ValidateAadharE_KYC(String verificationAccount, String director, boolean isSkip, boolean isConcent,
                                    ApiCallBackTwoMethod mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetEKYCDetailResponse> call = git.ValidateAadharE_KYC(new EKycStepsValidationRequest(isSkip, verificationAccount, director, isConcent, LoginDataResponse.getData().getUserID() + "",
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetEKYCDetailResponse>() {
                @Override
                public void onResponse(Call<GetEKYCDetailResponse> call, final retrofit2.Response<GetEKYCDetailResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            GetEKYCDetailResponse mGetEKYCDetailResponse = response.body();
                            if (mGetEKYCDetailResponse != null) {


                                if (mGetEKYCDetailResponse.getStatuscode() == 1) {
                                    if (mGetEKYCDetailResponse.getRid() != null && !mGetEKYCDetailResponse.getRid().isEmpty()) {
                                        refrenceId = mGetEKYCDetailResponse.getRid();
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(mGetEKYCDetailResponse);
                                        }
                                    } else {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(mActivity.getResources().getString(R.string.some_thing_error));
                                        }
                                    }


                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(mGetEKYCDetailResponse);
                                    }
                                   /* if (mGetEKYCDetailResponse.isIsVersionValid() == false) {
                                        ApiUtilMethods.INSTANCE.versionDialog(mActivity);
                                    } else {
                                        ApiUtilMethods.INSTANCE.Error(mActivity, mGetEKYCDetailResponse.getMsg() + "");
                                    }*/
                                }
                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(mActivity.getResources().getString(R.string.some_thing_error));
                                }
                            }

                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(response.message() + "");
                            }
                            // ApiUtilMethods.INSTANCE.apiErrorHandle(mActivity, response.code(), response.message() + "");
                        }
                    } catch (Exception e) {

                        if (mApiCallBack != null) {
                            mApiCallBack.onError(e.getMessage() + "");
                        }
                        // ApiUtilMethods.INSTANCE.Error(mActivity, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<GetEKYCDetailResponse> call, Throwable t) {

                    if (mApiCallBack != null) {
                        mApiCallBack.onError(t.getMessage() + "");
                    }
                    //    ApiUtilMethods.INSTANCE.apiFailureError(mActivity, t);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            if (mApiCallBack != null) {
                mApiCallBack.onError(e.getMessage());
            }
            // ApiUtilMethods.INSTANCE.Error(mActivity, e.getMessage() + "");
        }
    }

    public void ValidateAadharOTPE_KYC(String otp, String refid, ApiCallBackTwoMethod mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetEKYCDetailResponse> call = git.ValidateAadharOTPE_KYC(new EKycStepsValidationRequest(otp, refid, LoginDataResponse.getData().getUserID() + "",
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetEKYCDetailResponse>() {
                @Override
                public void onResponse(Call<GetEKYCDetailResponse> call, final retrofit2.Response<GetEKYCDetailResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            GetEKYCDetailResponse mGetEKYCDetailResponse = response.body();
                            if (mGetEKYCDetailResponse != null) {


                                if (mGetEKYCDetailResponse.getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mGetEKYCDetailResponse);
                                    }


                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(mGetEKYCDetailResponse);
                                    }
                                   /* if (mGetEKYCDetailResponse.isIsVersionValid() == false) {
                                        ApiUtilMethods.INSTANCE.versionDialog(mActivity);
                                    } else {
                                        ApiUtilMethods.INSTANCE.Error(mActivity, mGetEKYCDetailResponse.getMsg() + "");
                                    }*/
                                }
                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(mActivity.getResources().getString(R.string.some_thing_error));
                                }
                            }

                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(response.message() + "");
                            }
                            //  ApiUtilMethods.INSTANCE.apiErrorHandle(mActivity, response.code(), response.message() + "");
                        }
                    } catch (Exception e) {

                        if (mApiCallBack != null) {
                            mApiCallBack.onError(e.getMessage() + "");
                        }
                        // ApiUtilMethods.INSTANCE.Error(mActivity, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<GetEKYCDetailResponse> call, Throwable t) {

                    if (mApiCallBack != null) {
                        mApiCallBack.onError(t.getMessage() + "");
                    }
                    //    ApiUtilMethods.INSTANCE.apiFailureError(mActivity, t);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            if (mApiCallBack != null) {
                mApiCallBack.onError(e.getMessage());
            }
            // ApiUtilMethods.INSTANCE.Error(mActivity, e.getMessage() + "");
        }
    }

    public void EditE_KYCStep(int stepid, ApiCallBackTwoMethod mApiCallBack) {
        try {
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

            Call<GetEKYCDetailResponse> call = git.EditE_KYCSteps(new EKycStepsValidationRequest(stepid,
                    LoginDataResponse.getData().getUserID() + "",
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetEKYCDetailResponse>() {
                @Override
                public void onResponse(Call<GetEKYCDetailResponse> call, final retrofit2.Response<GetEKYCDetailResponse> response) {

                    try {

                        if (response.isSuccessful()) {
                            GetEKYCDetailResponse mGetEKYCDetailResponse = response.body();
                            if (mGetEKYCDetailResponse != null) {


                                if (mGetEKYCDetailResponse.getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mGetEKYCDetailResponse);
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError(mGetEKYCDetailResponse);
                                    }
                                   /* if (mGetEKYCDetailResponse.isIsVersionValid() == false) {
                                        ApiUtilMethods.INSTANCE.versionDialog(mActivity);
                                    } else {
                                        ApiUtilMethods.INSTANCE.Error(mActivity, mGetEKYCDetailResponse.getMsg() + "");
                                    }*/
                                }
                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(mActivity.getResources().getString(R.string.some_thing_error));
                                }
                            }

                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(null);
                            }
                            // ApiUtilMethods.INSTANCE.apiErrorHandle(mActivity, response.code(), response.message());
                        }
                    } catch (Exception e) {

                        if (mApiCallBack != null) {
                            mApiCallBack.onError(e.getMessage() + "");
                        }
                        // ApiUtilMethods.INSTANCE.Error(mActivity, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<GetEKYCDetailResponse> call, Throwable t) {

                    if (mApiCallBack != null) {
                        mApiCallBack.onError(t.getMessage() + "");
                    }
                    //    ApiUtilMethods.INSTANCE.apiFailureError(mActivity, t);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            if (mApiCallBack != null) {
                mApiCallBack.onError(e.getMessage());
            }
            // ApiUtilMethods.INSTANCE.Error(mActivity, e.getMessage() + "");
        }
    }

    public interface ClickDropDownItem {
        void onClick(int clickPosition, String value, EKycDirectorOrCompany object);
    }

    public interface ApiCallBackTwoMethod {
        void onSucess(GetEKYCDetailResponse object);

        void onError(Object object);
    }

    public interface BankClickCallBack {
        void Bank(BankListObject operator);
    }
}
