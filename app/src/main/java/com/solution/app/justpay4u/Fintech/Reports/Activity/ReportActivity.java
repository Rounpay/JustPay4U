package com.solution.app.justpay4u.Fintech.Reports.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.solution.app.justpay4u.ApiHits.AssignedOpTypeIndustryWise;
import com.solution.app.justpay4u.ApiHits.OpTypeDataIndustryWise;
import com.solution.app.justpay4u.ApiHits.OpTypeIndustryWiseResponse;
import com.solution.app.justpay4u.Fintech.Activities.CreateUserActivity;
import com.solution.app.justpay4u.Fintech.Activities.MNPClaimHistory;
import com.solution.app.justpay4u.Fintech.Activities.QrBankActivity;
import com.solution.app.justpay4u.Fintech.Activities.UpgradePackageActivity;
import com.solution.app.justpay4u.Api.Fintech.Object.AssignedOpType;
import com.solution.app.justpay4u.Api.Fintech.Object.DataOpType;
import com.solution.app.justpay4u.Api.Fintech.Response.AppGetAMResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.AccountStatementReportActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.AppUserListActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.ChannelReportActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.FosAreaReportActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.FosReportActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.FosUserListActivity;
import com.solution.app.justpay4u.Fintech.CommissionSlab.Activity.CommissionScreenActivity;
import com.solution.app.justpay4u.Fintech.Dashboard.Adapter.HomeOptionListAdapter;
import com.solution.app.justpay4u.Fintech.Employee.Activity.MeetingDetails;
import com.solution.app.justpay4u.Fintech.Employee.Activity.MeetingtReport;
import com.solution.app.justpay4u.Fintech.Employee.Activity.PstReport;
import com.solution.app.justpay4u.Fintech.Employee.Activity.TargetReport;
import com.solution.app.justpay4u.Fintech.Employee.Activity.TeriatryReport;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.AddMoneyActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private AppPreferences mAppPreferences;
    private LoginResponse mLoginDataResponse;
    int userRollId;
    private CustomAlertDialog customAlertDialog;
    private int INTENT_UPGRADE_PACKAGE = 8765;
    private CustomLoader loader;
    RecyclerView recyclerView;
    private AppGetAMResponse mAppGetAMResponse;
    private String deviceId, deviceSerialNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_report);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            userRollId = mLoginDataResponse.getData().getRoleID();
            customAlertDialog = new CustomAlertDialog(this);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            getActiveService();
            setDashboardData();

//            setOptionListData(ApiFintechUtilMethods.INSTANCE.getActiveService(mAppPreferences), recyclerView, 4, R.layout.adapter_dashboard_option);
        });
    }
    private void setDashboardData() {

        DataOpType mDataOpType = new DataOpType();

        if (userRollId == 3 || userRollId == 2) {
            setOptionListData(mDataOpType.getRetailerReportOptions(
                    ApiFintechUtilMethods.INSTANCE.isDMTEnable,
                    ApiFintechUtilMethods.INSTANCE.isDMTActive, ApiFintechUtilMethods.INSTANCE.isDMTServiceActive,
                    mLoginDataResponse.isAccountStatement(),
                    ApiFintechUtilMethods.INSTANCE.isAdditionalService, ApiFintechUtilMethods.INSTANCE.isPaidService), recyclerView, 4, R.layout.report_layout_check);

        } else {
            setOptionListData(mDataOpType.getOtherReportSeriveOptions(ApiFintechUtilMethods.INSTANCE.isDMTEnable,
                    ApiFintechUtilMethods.INSTANCE.isDMTActive, ApiFintechUtilMethods.INSTANCE.isDMTServiceActive,
                    ApiFintechUtilMethods.INSTANCE.isAdditionalService, ApiFintechUtilMethods.INSTANCE.isPaidService), recyclerView, 4, R.layout.report_layout_check);

        }


    }

    void setOptionListData(ArrayList<AssignedOpType> mListData, RecyclerView mRecyclerView, int type, int layout) {
        HomeOptionListAdapter mDashboardOptionListAdapter = new HomeOptionListAdapter(mListData, ReportActivity.this, new HomeOptionListAdapter.ClickView() {
            @Override
            public void onClick(AssignedOpType operator) {
                /*int serviceId, int parentId, String name, ArrayList<AssignedOpType> subOpTypeList*/
                if (operator.getSubOpTypeList() != null && operator.getSubOpTypeList().size() > 0) {
                    if (customAlertDialog == null) {
                        customAlertDialog = new CustomAlertDialog(ReportActivity.this);
                    }
                    customAlertDialog.serviceListDialog(operator.getParentID(), operator.getService(),
                            operator.getSubOpTypeList(), type, new CustomAlertDialog.DialogServiceListCallBack() {
                                @Override
                                public void onIconClick(AssignedOpType opType) {
                                    openNewScreen(opType.getServiceID(), opType.getParentID(), opType.getName());
                                }

                                @Override
                                public void onUpgradePackage(boolean isFromAdditionalService, int serviceId) {

                                    startActivityForResult(new Intent(ReportActivity.this, UpgradePackageActivity.class)
                                            .putExtra("UID", mLoginDataResponse.getData().getUserID() + "")
                                            .putExtra("BENE_NAME", mLoginDataResponse.getData().getName() + " (" + mLoginDataResponse.getData().getRoleName() + ")")
                                            .putExtra("BENE_MOBILE", mLoginDataResponse.getData().getMobileNo() + "")
                                            .putExtra("FromAdditionalService", isFromAdditionalService)
                                            .putExtra("OPTypeId", serviceId)
                                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_UPGRADE_PACKAGE);
                                }
                            });
                } else {
                    openNewScreen(operator.getServiceID(), operator.getParentID(), operator.getName());
                }

            }

            @Override
            public void onPackageUpgradeClick(boolean isFromAdditionalService, int serviceId) {

                startActivityForResult(new Intent(ReportActivity.this, UpgradePackageActivity.class)
                        .putExtra("UID", mLoginDataResponse.getData().getUserID() + "")
                        .putExtra("BENE_NAME", mLoginDataResponse.getData().getName() + " (" + mLoginDataResponse.getData().getRoleName() + ")")
                        .putExtra("BENE_MOBILE", mLoginDataResponse.getData().getMobileNo() + "")
                        .putExtra("FromAdditionalService", isFromAdditionalService)
                        .putExtra("OPTypeId", serviceId)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_UPGRADE_PACKAGE);
            }
        }, layout, type);
        mRecyclerView.setAdapter(mDashboardOptionListAdapter);
    }

/*
    void setOptionListData(DashBoardServiceReportOptions mActiveServiceData*/
/*ArrayList<AssignedOpType> mListData*//*
,
                           RecyclerView mRecyclerView, int type, int layout) {
        if (mLoginDataResponse.getData().getLoginTypeID() == 3) {
            setAdapter(new DataOpType().getEmpReportSerive(), mRecyclerView, type, layout);
        } else {
            if (mActiveServiceData != null) {
                List<AssignedOpType> mListData = new ArrayList<>();
                if (userRollId == 3 || userRollId == 2) {
                    mListData = mActiveServiceData.getRetailerReportOption();
                } else {
                    mListData = mActiveServiceData.getOtherReportOption();
                }
                setAdapter(mListData, mRecyclerView, type, layout);

            } else {
                getActiveService();
            }
        }


    }
*/

    void setAdapter(List<AssignedOpType> mListData, RecyclerView mRecyclerView, int type, int layout) {
        HomeOptionListAdapter mDashboardOptionListAdapter = new HomeOptionListAdapter(mListData, ReportActivity.this, new HomeOptionListAdapter.ClickView() {
            @Override
            public void onClick(AssignedOpType operator) {
                /*int serviceId, int parentId, String name, ArrayList<AssignedOpType> subOpTypeList*/
                if (operator.getSubOpTypeList() != null && operator.getSubOpTypeList().size() > 0) {
                    customAlertDialog.serviceListDialog(operator.getParentID(), operator.getService(),
                            operator.getSubOpTypeList(), type, new CustomAlertDialog.DialogServiceListCallBack() {
                                @Override
                                public void onIconClick(AssignedOpType opType) {
                                    openNewScreen(opType.getServiceID(), opType.getParentID(), opType.getName());
                                }

                                @Override
                                public void onUpgradePackage(boolean isFromAdditionalService, int serviceId) {

                                    startActivityForResult(new Intent(ReportActivity.this, UpgradePackageActivity.class)
                                            .putExtra("UID", mLoginDataResponse.getData().getUserID() + "")
                                            .putExtra("BENE_NAME", mLoginDataResponse.getData().getName() + " (" + mLoginDataResponse.getData().getRoleName() + ")")
                                            .putExtra("BENE_MOBILE", mLoginDataResponse.getData().getMobileNo() + "")
                                            .putExtra("FromAdditionalService", isFromAdditionalService)
                                            .putExtra("OPTypeId", serviceId)
                                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_UPGRADE_PACKAGE);
                                }
                            });
                } else {
                    openNewScreen(operator.getServiceID(), operator.getParentID(), operator.getName());
                }

            }

            @Override
            public void onPackageUpgradeClick(boolean isFromAdditionalService, int serviceId) {

                startActivityForResult(new Intent(ReportActivity.this, UpgradePackageActivity.class)
                        .putExtra("UID", mLoginDataResponse.getData().getUserID() + "")
                        .putExtra("BENE_NAME", mLoginDataResponse.getData().getName() + " (" + mLoginDataResponse.getData().getRoleName() + ")")
                        .putExtra("BENE_MOBILE", mLoginDataResponse.getData().getMobileNo() + "")
                        .putExtra("FromAdditionalService", isFromAdditionalService)
                        .putExtra("OPTypeId", serviceId)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), INTENT_UPGRADE_PACKAGE);
            }
        }, layout, type);
        mRecyclerView.setAdapter(mDashboardOptionListAdapter);
    }

    void openNewScreen(int id, int parentId, String name) {
        if (id == 1001) {

            Intent intent = new Intent(ReportActivity.this, RechargeReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == 1002 || id == 1028) {

            Intent intent = new Intent(ReportActivity.this, LedgerReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == 1003) {

            Intent i = new Intent(ReportActivity.this, FundOrderReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 1004) {

            Intent n = new Intent(ReportActivity.this, DisputeReportActivity.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        } else if (id == 1005) {

            Intent i = new Intent(ReportActivity.this, DMRReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        } else if (id == 1007) {

            Intent i = new Intent(ReportActivity.this, FundDcReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        } else if (id == 1008) {

            Intent i = new Intent(ReportActivity.this, UserDayBookActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        } else if (id == 1009) {

            Intent i = new Intent(ReportActivity.this, FundOrderPendingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 1011) {

            Intent i = new Intent(ReportActivity.this, CreateUserActivity.class);

            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 1012) {

            if (userRollId == 8) {
                startActivity(new Intent(this, FosUserListActivity.class)
                        .putExtra("Title", "FOS User List")
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            } else {
                Intent i = new Intent(ReportActivity.this, AppUserListActivity.class);
                i.putExtra("Title", "User List");
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        } else if (id == 1013) {
            Intent n = new Intent(ReportActivity.this, CommissionScreenActivity.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        } else if (id == 1014) {

            Intent n = new Intent(ReportActivity.this, W2RHistoryActivity.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        } else if (id == 1015) {
            Intent i = new Intent(ReportActivity.this, UserDayBookDMTActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        } else if (id == 1017) {

            Intent n = new Intent(ReportActivity.this, QrBankActivity.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        } else if (id == 1018) {

            Intent i = new Intent(ReportActivity.this, SpecificRechargeReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        } else if (id == 1019) {

            Intent i = new Intent(ReportActivity.this, AddMoneyActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        } else if (id == 1020) {
            Intent i = new Intent(ReportActivity.this, AEPSReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        } else if (id == 1021) {

            startActivity(new Intent(ReportActivity.this, UpgradePackageActivity.class)
                    .putExtra("UID", mLoginDataResponse.getData().getUserID() + "")
                    .putExtra("BENE_NAME", mLoginDataResponse.getData().getName() + " (" + mLoginDataResponse.getData().getRoleName() + ")")
                    .putExtra("BENE_MOBILE", mLoginDataResponse.getData().getMobileNo() + "")
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

        } else if (id == 1022) {
            Intent i = new Intent(ReportActivity.this, DthSubscriptionReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
       else if (id == 1024) {

            Intent i = new Intent(ReportActivity.this, CreateUserActivity.class);
            i.putExtra("IsFOS", true);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 1026 || id == 1031) {
            // UtilMethods.INSTANCE.GetArealist(getActivity(), loader, loginPrefResponse, null);
            Intent intent = new Intent(this, AccountStatementReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == 1027) {

            //customAlertDialog.channelFosListDialog();
            Intent intent = new Intent(this, FosReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == 1030) {

            if (mLoginDataResponse.isAreaMaster()) {
                mAppGetAMResponse = ApiFintechUtilMethods.INSTANCE.getAppGetAMResponse(mAppPreferences);
                if (mAppGetAMResponse == null || mAppGetAMResponse.getAreaMaster() == null) {
                    mAppGetAMResponse = new Gson().fromJson(mAppPreferences.getString(ApplicationConstant.INSTANCE.areaListPref), AppGetAMResponse.class);
                    if (mAppGetAMResponse == null || mAppGetAMResponse.getAreaMaster() == null) {
                        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
                            loader.show();
                            ApiFintechUtilMethods.INSTANCE.GetArealist(this, loader, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences,
                                    new ApiFintechUtilMethods.ApiResponseCallBack() {
                                        @Override
                                        public void onSucess(Object object) {
                                            mAppGetAMResponse = (AppGetAMResponse) object;
                                            if (mAppGetAMResponse != null && mAppGetAMResponse.getAreaMaster() != null && mAppGetAMResponse.getAreaMaster().size() > 0) {
                                                customAlertDialog.channelAreaListDialog(mAppGetAMResponse.getAreaMaster());
                                            }
                                        }

                                        @Override
                                        public void onError(int error) {

                                        }
                                    });


                        } else {
                            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
                        }
                    } else {
                        customAlertDialog.channelAreaListDialog(mAppGetAMResponse.getAreaMaster());
                    }
                } else {
                    customAlertDialog.channelAreaListDialog(mAppGetAMResponse.getAreaMaster());
                }

            } else {
                Intent intent = new Intent(this, ChannelReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }

        } else if (id == 1029) {

            // UtilMethods.INSTANCE.GetArealist(getActivity(), loader, loginPrefResponse, null);
            Intent intent = new Intent(this, FosAreaReportActivity.class);
            intent.putExtra("ISFromFOS", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            startActivity(intent);
        } else if (id == 1032) {

            customAlertDialog.channelFosListDialog(loader, mLoginDataResponse, deviceId, deviceSerialNum);
        } else if (id == 1035) {
            Intent i = new Intent(ReportActivity.this, MoveToBankReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        } else if (id == 1036) {
            Intent intent = new Intent(this, MNPClaimHistory.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        }
        /*else if (id == 1078) {
            Intent i = new Intent(ReportActivity.this, GetPoolUpLineReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }*/else if (id == 2001) {
            Intent i = new Intent(this, RechargeReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 2002) {
            Intent i = new Intent(this, LedgerReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 2005) {
            Intent i = new Intent(this, DMRReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 2008) {
            Intent i = new Intent(this, UserDayBookActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 2030) {
            Intent i = new Intent(this, PstReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 2031) {
            Intent i = new Intent(this, TeriatryReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 2032) {
            Intent i = new Intent(this, TargetReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 2033) {
            Intent i = new Intent(this, MeetingDetails.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (id == 2034) {
            Intent i = new Intent(this, MeetingtReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_UPGRADE_PACKAGE && resultCode == RESULT_OK) {
            getActiveService();
        }

    }

  /*  void getActiveService() {

        try {

            ApiFintechUtilMethods.INSTANCE.GetActiveService(ReportActivity.this, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, object -> {
                DashBoardServiceReportOptions mActiveServiceData = (DashBoardServiceReportOptions) object;
                if (mActiveServiceData != null) {

                    if (mActiveServiceData.getRetailerReportOption() != null && mActiveServiceData.getRetailerReportOption().size() > 0) {
                        setOptionListData(mActiveServiceData, recyclerView, 4, R.layout.adapter_dashboard_option);
                    }
                } else {
                    loader.dismiss();
                }
            });
        } catch (Exception e) {
            loader.dismiss();
            e.printStackTrace();
        }


    }*/
    void getActiveService() {

        try {
            ApiFintechUtilMethods.INSTANCE.GetActiveServiceIndustryWise(ReportActivity.this, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, object -> {
                OpTypeIndustryWiseResponse mActiveServiceData = (OpTypeIndustryWiseResponse) object;
                if (mActiveServiceData != null && mActiveServiceData.getData() != null &&
                        mActiveServiceData.getData().size() > 0) {
                    for (OpTypeDataIndustryWise item : mActiveServiceData.getData()) {
                        if (item.getOpTypes() != null && item.getOpTypes().size() > 0) {
                            for (AssignedOpTypeIndustryWise itemOpType : item.getOpTypes()) {
                                if (itemOpType.getId() == 14) {
                                    ApiFintechUtilMethods.INSTANCE.isDMTEnable = true;
                                    ApiFintechUtilMethods.INSTANCE.isDMTActive = itemOpType.isActive();
                                    ApiFintechUtilMethods.INSTANCE.isDMTServiceActive = itemOpType.isServiceActive();
                                    ApiFintechUtilMethods.INSTANCE.isAdditionalService = itemOpType.isAdditionalServiceType();
                                    ApiFintechUtilMethods.INSTANCE.isPaidService = itemOpType.isPaidAdditional();
                                    break;
                                }
                            }
                        }
                    }


                    setDashboardData();
                } else {

                }
            });
        } catch (Exception e) {

            e.printStackTrace();
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