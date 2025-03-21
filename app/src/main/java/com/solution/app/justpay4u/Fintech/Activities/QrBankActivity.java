package com.solution.app.justpay4u.Fintech.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Fintech.Adapter.QrBankAdapter;
import com.solution.app.justpay4u.Api.Fintech.Object.Bank;
import com.solution.app.justpay4u.Api.Fintech.Request.FundRequestToUsers;
import com.solution.app.justpay4u.Api.Fintech.Response.FundreqToResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetBankAndPaymentModeResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownDialog;
import com.solution.app.justpay4u.Util.DropdownDialog.DropDownModel;

import java.util.ArrayList;
import java.util.List;

public class QrBankActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    TextView requestTo;
    View roleLayout;
    ArrayList<DropDownModel> arrayListRole = new ArrayList<>();
    DropDownDialog mDropDownDialog;
    int selectRolePos = -1;
    int selectedParentId = 1;
    RequestOptions requestOptions;
    boolean isRoleApiError;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    String deviceId, deviceSerialNum;
    private CustomLoader loader;
    private LoginResponse mLoginDataResponse;
    private CustomAlertDialog mCustomAlertDialog;
    private AppPreferences mAppPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_qr_bank);
            findViews();
            requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
            mCustomAlertDialog = new CustomAlertDialog(this);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            mDropDownDialog = new DropDownDialog(this);

            roleLayout.setOnClickListener(v -> {
                if (arrayListRole != null && arrayListRole.size() > 0) {
                    mDropDownDialog.showDropDownPopup(v, selectRolePos, arrayListRole, (clickPosition, value, object) -> {
                                requestTo.setText(value + "");
                                selectRolePos = clickPosition;
                                selectedParentId = ((FundRequestToUsers) object).getParentID();
                                GetBank();
                            }
                    );
                } else {
                    mCustomAlertDialog.WarningWithCallBack("Role not available to request, please try again", "Retry Fetch Role", "Retry", true, new CustomAlertDialog.DialogCallBack() {
                        @Override
                        public void onPositiveClick() {
                            HitApi();
                            ;
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });
                }
            });

            retryBtn.setOnClickListener(v -> {
                if (isRoleApiError) {
                    HitApi();
                } else {
                    GetBank();
                }
            });

            HitApi();
        });
    }

    void findViews() {


        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Bank List Not Found");
        requestTo = findViewById(R.id.requestTo);
        roleLayout = findViewById(R.id.roleLayout);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {


            ApiFintechUtilMethods.INSTANCE.FundRequestTo(this, loader, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, new ApiFintechUtilMethods.ApiResponseCallBack() {
                @Override
                public void onSucess(Object object) {
                    FundreqToResponse mFundreqToResponse = (FundreqToResponse) object;
                    selectRoleData(mFundreqToResponse);
                    isRoleApiError = false;
                }

                @Override
                public void onError(int error) {
                    isRoleApiError = true;
                    setInfoHideShow(error);
                }
            });

        } else {
            isRoleApiError = true;
            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK);
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }


    void selectRoleData(FundreqToResponse fundreqToResponse) {

        if (fundreqToResponse != null) {
            final ArrayList<FundRequestToUsers> fundRequestToUsers = fundreqToResponse.getFundRequestToUsers();

            if (fundRequestToUsers != null && fundRequestToUsers.size() != 0) {
                if (fundRequestToUsers.size() == 1) {
                    roleLayout.setVisibility(View.GONE);
                } else {
                    roleLayout.setVisibility(View.VISIBLE);
                }

                requestTo.setText(fundRequestToUsers.get(0).getParentName() + "");
                selectRolePos = 0;
                selectedParentId = fundRequestToUsers.get(0).getParentID();
                GetBank();
                for (FundRequestToUsers mFundRequestToUsers : fundRequestToUsers) {
                    arrayListRole.add(new DropDownModel(mFundRequestToUsers.getParentName(), mFundRequestToUsers));
                }
            } else {
                setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                roleLayout.setVisibility(View.GONE);
            }
        }

    }

    public void GetBank() {
        try {
            loader.show();
            ApiFintechUtilMethods.INSTANCE.GetBankAndPaymentMode(QrBankActivity.this, selectedParentId + "",
                    loader, deviceId, deviceSerialNum, mLoginDataResponse, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            GetBankAndPaymentModeResponse getBankAndPaymentModeResponse = (GetBankAndPaymentModeResponse) object;
                            List<Bank> qrBank = new ArrayList<>();
                            for (int i = 0; i < getBankAndPaymentModeResponse.getBanks().size(); i++) {
                                if (getBankAndPaymentModeResponse.getBanks().get(i).getIsqrenable()) {
                                    qrBank.add(getBankAndPaymentModeResponse.getBanks().get(i));
                                }
                            }
                            isRoleApiError = false;
                            if (qrBank.size() > 0) {
                                QrBankAdapter mBankDetailAdapter = new QrBankAdapter(qrBank, QrBankActivity.this, selectedParentId, requestOptions);
                                mRecyclerView.setAdapter(mBankDetailAdapter);
                                mRecyclerView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                noDataView.setVisibility(View.GONE);
                            } else {
                                setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                            }
                        }

                        @Override
                        public void onError(int error) {
                            setInfoHideShow(error);
                            isRoleApiError = false;
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
            isRoleApiError = false;
            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
        }
    }

    void setInfoHideShow(int errorType) {
        mRecyclerView.setVisibility(View.GONE);


        if (errorType == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        } else {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);

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