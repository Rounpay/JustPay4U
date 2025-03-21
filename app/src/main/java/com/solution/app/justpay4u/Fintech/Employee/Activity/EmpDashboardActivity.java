package com.solution.app.justpay4u.Fintech.Employee.Activity;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.solution.app.justpay4u.Api.Fintech.Object.AssignedOpType;
import com.solution.app.justpay4u.Api.Fintech.Object.DataOpType;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.Fintech.Dashboard.Adapter.HomeOptionListAdapter;
import com.solution.app.justpay4u.Fintech.Employee.Adapter.EmpDownlineUserAdapter;
import com.solution.app.justpay4u.Fintech.Employee.Adapter.EmpLMTDVsMTDAdapter;
import com.solution.app.justpay4u.Fintech.Employee.Adapter.EmpTargetSegmentAdapter;
import com.solution.app.justpay4u.Fintech.Employee.Adapter.EmpTodayLivePSTAdapter;
import com.solution.app.justpay4u.Fintech.Employee.Adapter.SetUserCommitmentAdapter;
import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetLMTDVsMTD;
import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetLastSevenDayPSTData;
import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetLastdayVsTodayChart;
import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetUserCommitment;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetEmpDownlineUserResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetEmpTodayLivePSTResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetLMTDVsMTDResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetLastSevenDayPSTDataResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetLastdayVsTodayChartResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetTargetSegmentResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetUserCommitmentResponse;
import com.solution.app.justpay4u.Fintech.Employee.Utils.MyMarkerView;
import com.solution.app.justpay4u.Fintech.Reports.Activity.ReportActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.List;

public class EmpDashboardActivity extends AppCompatActivity {

    private AppCompatImageView refresh;
    private AppCompatTextView userNameTv;
    private AppCompatTextView roleTv;
    private AppCompatTextView mobileNumTv;
    private AppCompatTextView emailTv;
    private LinearLayout reportView;
    private LinearLayout logoutView;
    private TextView reportLabel;
    private RecyclerView recyclerView;
    private LinearLayout empTragetView;
    private RecyclerView recyclerViewTraget;
    private LinearLayout empLMTDVsMTDView;
    private RecyclerView recyclerViewLMTDVsMTD;
    private LinearLayout todayRecordView;
    private RecyclerView recyclerViewTodayRecord;
    private LinearLayout empAttendanceView;
    private RecyclerView recyclerViewAttandance;
    private LinearLayout empLastdayVsTodayChart;
    private BarChart empLastdayVsTodayChartView;
    private LinearLayout empLast7DaysPSTChart;
    private LineChart empLast7DaysPSTChartView;
    private LinearLayout empLMTDVsMTDChart;
    private BarChart empLMTDVsMTDChartView;
    private LinearLayout empCommitmentVsAcheivementChart;
    private PieChart empCommitmentVsAcheivementChartView;
    private ImageView editCA;

    private LoginResponse mLoginDataResponse;
    private CustomAlertDialog customAlertDialog;
    private AppUpdateManager appUpdateManager;
    private AppPreferences mAppPreferences;
    private String deviceId;
    private String deviceSerialNum;
    private boolean isUpdateDialogShowing;
    private int MY_REQUEST_UPDATE_APP_CODE = 967;
    private CustomLoader loader;
    private AlertDialog alertDialogServiceList;
    private AlertDialog alertDialogDailyClosing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_emp_dashboard);


            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            findViews();
            clickView();
            new Handler(Looper.getMainLooper()).post(() -> {
                appUpdateManager = AppUpdateManagerFactory.create(this);
                customAlertDialog = new CustomAlertDialog(this);
                setUserData();
                setOptionListData(new DataOpType().getEmpDashboardOption(), recyclerView, 1, R.layout.adapter_dashboard_service_option_grid);

                GetTargetSegment(false);
                GetTodayRecord(false);
                GetLMTDVsMTD(false);
                GetLastdayVsTodayChart(false);
                GetLastSevenDayPSTChart(true);
                GetUserCommitmentChart(true);
                GetEmpDownlineUser(true);
            });

        });

    }

    private void findViews() {

        refresh = findViewById(R.id.refresh);
        userNameTv = findViewById(R.id.userNameTv);
        roleTv = findViewById(R.id.roleTv);
        mobileNumTv = findViewById(R.id.mobileNumTv);
        emailTv = findViewById(R.id.emailTv);
        reportView = findViewById(R.id.reportView);
        reportLabel = findViewById(R.id.reportLabel);
        logoutView = findViewById(R.id.logoutView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        empTragetView = findViewById(R.id.empTragetView);
        recyclerViewTraget = findViewById(R.id.recyclerViewTraget);
        recyclerViewTraget.setLayoutManager(new LinearLayoutManager(this));
        empLMTDVsMTDView = findViewById(R.id.empLMTDVsMTDView);
        recyclerViewLMTDVsMTD = findViewById(R.id.recyclerViewLMTDVsMTD);
        recyclerViewLMTDVsMTD.setLayoutManager(new LinearLayoutManager(this));
        todayRecordView = findViewById(R.id.todayRecordView);
        recyclerViewTodayRecord = findViewById(R.id.recyclerViewTodayRecord);
        recyclerViewTodayRecord.setLayoutManager(new LinearLayoutManager(this));
        empAttendanceView = findViewById(R.id.empAttendanceView);
        recyclerViewAttandance = findViewById(R.id.recyclerViewAttandance);
        recyclerViewAttandance.setLayoutManager(new LinearLayoutManager(this));
        empLastdayVsTodayChart = findViewById(R.id.empLastdayVsTodayChart);
        empLastdayVsTodayChartView = findViewById(R.id.empLastdayVsTodayChartView);
        empLast7DaysPSTChart = findViewById(R.id.empLast7DaysPSTChart);
        empLast7DaysPSTChartView = findViewById(R.id.empLast7DaysPSTChartView);
        empLMTDVsMTDChart = findViewById(R.id.empLMTDVsMTDChart);
        empLMTDVsMTDChartView = findViewById(R.id.empLMTDVsMTDChartView);
        empCommitmentVsAcheivementChart = findViewById(R.id.empCommitmentVsAcheivementChart);
        empCommitmentVsAcheivementChartView = findViewById(R.id.empCommitmentVsAcheivementChartView);
        editCA = findViewById(R.id.editCA);

    }

    void clickView() {
        logoutView.setOnClickListener(v -> {
            customAlertDialog.Successfullogout(loader, "Do you really want to Logout?", this,
                    mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);

        });

        editCA.setOnClickListener(v13 -> {

            GetUserCommitment(true);

        });
        reportView.setOnClickListener(v13 -> {

            startActivity(new Intent(this, ReportActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(500);
                rotate.setRepeatCount(Animation.INFINITE);
                rotate.setInterpolator(new LinearInterpolator());
                refresh.startAnimation(rotate);
                GetTargetSegment(false);
                GetTodayRecord(false);
                GetLMTDVsMTD(false);
                GetLastdayVsTodayChart(false);
                GetLastSevenDayPSTChart(false);
                GetUserCommitmentChart(false);
                GetEmpDownlineUser(false);
            }
        });

    }

    void setUserData() {
        userNameTv.setText(mLoginDataResponse.getData().getName() + " - " + mLoginDataResponse.getData().getRoleName());
        roleTv.setText(mLoginDataResponse.getData().getRoleName() + "");
        mobileNumTv.setText(mLoginDataResponse.getData().getMobileNo() + "");
        emailTv.setText(mLoginDataResponse.getData().getEmailID() + "");
    }


    void setOptionListData(ArrayList<AssignedOpType> mListData, RecyclerView recyclerView, int type, int layout) {
        HomeOptionListAdapter mDashboardOptionListAdapter = new HomeOptionListAdapter(mListData, this, new HomeOptionListAdapter.ClickView() {
            @Override
            public void onClick(AssignedOpType opType) {
                if (opType.getSubOpTypeList() != null && opType.getSubOpTypeList().size() > 0) {
                    customAlertDialog.serviceListDialog(opType.getParentID(), opType.getService(), opType.getSubOpTypeList(), type, new CustomAlertDialog.DialogServiceListCallBack() {
                        @Override
                        public void onIconClick(AssignedOpType opType) {
                            openNewScreen(opType.getServiceID());
                        }

                        @Override
                        public void onUpgradePackage(boolean isFromAdditionalService, int serviceId) {

                        }
                    });
                } else {
                    openNewScreen(opType.getServiceID());
                }

            }

            @Override
            public void onPackageUpgradeClick(boolean isFromAdditionalService, int serviceId) {

            }
        }, layout, type);
        recyclerView.setAdapter(mDashboardOptionListAdapter);
    }

    private void openNewScreen(int serviceId) {
        if (serviceId == 2026) {
            Intent i = new Intent(this, EmployeesListReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (serviceId == 2027) {
            Intent i = new Intent(this, EmpUserListReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (serviceId == 2028) {
            Intent i = new Intent(this, NewMeetingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (serviceId == 2029) {
            dailyCloasingdialog();
        }
    }

    void dailyCloasingdialog() {
        try {
            if (alertDialogDailyClosing != null && alertDialogDailyClosing.isShowing()) {
                return;
            }

           /* type = 1 //Recharge Report
            type = 2 // Bank List
            type = 3 // Call Back Request*/
            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this);
            alertDialogDailyClosing = dialogBuilder.create();
            alertDialogDailyClosing.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_daily_closing, null);
            alertDialogDailyClosing.setView(dialogView);

            EditText totalTravelEt = dialogView.findViewById(R.id.totalTravelEt);
            AppCompatTextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);
            AppCompatTextView sendBtn = dialogView.findViewById(R.id.sendBtn);
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            EditText totalExpenseEt = dialogView.findViewById(R.id.totalExpenseEt);


            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogDailyClosing.dismiss();
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogDailyClosing.dismiss();
                }
            });

            sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (totalTravelEt.getText().toString().isEmpty()) {
                        totalTravelEt.setError("Field can't be empty");
                        totalTravelEt.requestFocus();
                        return;
                    } else if (totalExpenseEt.getText().toString().isEmpty()) {
                        totalExpenseEt.setError("Field can't be empty");
                        totalExpenseEt.requestFocus();
                        return;
                    }
                    loader.show();
                    ApiFintechUtilMethods.INSTANCE.PostDailyClosing(EmpDashboardActivity.this, totalTravelEt.getText().toString(), totalExpenseEt.getText().toString(),
                            loader, mLoginDataResponse,
                            deviceId, deviceSerialNum, new ApiFintechUtilMethods.ApiCallBack() {
                                @Override
                                public void onSucess(Object object) {
                                    alertDialogDailyClosing.dismiss();
                                }
                            });
                }
            });


            alertDialogDailyClosing.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }

    private void GetTargetSegment(boolean isLoaderShow) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(EmpDashboardActivity.this)) {
            try {
                ApiFintechUtilMethods.INSTANCE.GetTargetSegment(EmpDashboardActivity.this, mLoginDataResponse, deviceId,
                        deviceSerialNum, isLoaderShow ? loader : null, object -> {
                            GetTargetSegmentResponse mGetTargetSegmentResponse = (GetTargetSegmentResponse) object;
                            if (mGetTargetSegmentResponse != null && mGetTargetSegmentResponse.getData() != null && mGetTargetSegmentResponse.getData().size() > 0) {
                                setEmpTargetSegmentData(mGetTargetSegmentResponse);
                            } else {
                                empTragetView.setVisibility(View.GONE);
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            empTragetView.setVisibility(View.GONE);
            ApiFintechUtilMethods.INSTANCE.NetworkError(EmpDashboardActivity.this);
        }

    }

    private void GetTodayRecord(boolean isLoaderShow) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(EmpDashboardActivity.this)) {
            try {
                ApiFintechUtilMethods.INSTANCE.GetTodayRecord(EmpDashboardActivity.this, mLoginDataResponse, deviceId,
                        deviceSerialNum, isLoaderShow ? loader : null, object -> {
                            GetEmpTodayLivePSTResponse mGetEmpTodayLivePSTResponse = (GetEmpTodayLivePSTResponse) object;
                            if (mGetEmpTodayLivePSTResponse != null && mGetEmpTodayLivePSTResponse.getData() != null && mGetEmpTodayLivePSTResponse.getData().size() > 0) {
                                setTodayRecordData(mGetEmpTodayLivePSTResponse);
                            } else {
                                todayRecordView.setVisibility(View.GONE);
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            todayRecordView.setVisibility(View.GONE);
            ApiFintechUtilMethods.INSTANCE.NetworkError(EmpDashboardActivity.this);
        }

    }

    private void GetLMTDVsMTD(boolean isLoaderShow) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(EmpDashboardActivity.this)) {
            try {

                ApiFintechUtilMethods.INSTANCE.GetLMTDVsMTD(EmpDashboardActivity.this, mLoginDataResponse, deviceId,
                        deviceSerialNum, isLoaderShow ? loader : null, new ApiFintechUtilMethods.ApiCallBack() {
                            @Override
                            public void onSucess(Object object) {
                                GetLMTDVsMTDResponse mGetLMTDVsMTDResponse = (GetLMTDVsMTDResponse) object;
                                if (mGetLMTDVsMTDResponse != null && mGetLMTDVsMTDResponse.getData() != null && mGetLMTDVsMTDResponse.getData().size() > 0) {
                                    setEmpLMTDVSMTDData(mGetLMTDVsMTDResponse);
                                } else {
                                    empLMTDVsMTDView.setVisibility(View.GONE);
                                }
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            empLMTDVsMTDView.setVisibility(View.GONE);
            ApiFintechUtilMethods.INSTANCE.NetworkError(EmpDashboardActivity.this);
        }

    }

    private void GetLastdayVsTodayChart(boolean isLoaderShow) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(EmpDashboardActivity.this)) {
            try {

                ApiFintechUtilMethods.INSTANCE.GetLastdayVsTodayChart(EmpDashboardActivity.this, mLoginDataResponse, deviceId,
                        deviceSerialNum, isLoaderShow ? loader : null, new ApiFintechUtilMethods.ApiCallBack() {
                            @Override
                            public void onSucess(Object object) {
                                GetLastdayVsTodayChartResponse mResponse = (GetLastdayVsTodayChartResponse) object;
                                if (mResponse != null && mResponse.getData() != null && mResponse.getData().size() > 0) {
                                    chartLastdayVsTodayData(mResponse);
                                } else {
                                    empLMTDVsMTDView.setVisibility(View.GONE);
                                }
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            empLMTDVsMTDView.setVisibility(View.GONE);
            ApiFintechUtilMethods.INSTANCE.NetworkError(EmpDashboardActivity.this);
        }

    }

    private void GetLastSevenDayPSTChart(boolean isLoaderShow) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(EmpDashboardActivity.this)) {
            try {

                ApiFintechUtilMethods.INSTANCE.GetLastSevenDayPSTChart(EmpDashboardActivity.this, mLoginDataResponse, deviceId,
                        deviceSerialNum, isLoaderShow ? loader : null, new ApiFintechUtilMethods.ApiCallBack() {
                            @Override
                            public void onSucess(Object object) {
                                GetLastSevenDayPSTDataResponse mResponse = (GetLastSevenDayPSTDataResponse) object;
                                refresh.clearAnimation();
                                if (mResponse != null && mResponse.getData() != null && mResponse.getData().size() > 0) {

                            /*Collections.sort(mResponse.getData(), new Comparator<GetLastSevenDayPSTData>() {
                                DateFormat f = new SimpleDateFormat("dd MMM");
                                @Override
                                public int compare(GetLastSevenDayPSTData lhs, GetLastSevenDayPSTData rhs) {
                                    try {
                                        return f.parse(lhs.getTransactionDate()).compareTo(f.parse(rhs.getTransactionDate()));
                                    } catch (ParseException e) {
                                        throw new IllegalArgumentException(e);
                                    }
                                }
                            });*/


                                    chartLastSevenDayPSTData(mResponse);
                                } else {
                                    empLast7DaysPSTChart.setVisibility(View.GONE);
                                }
                            }
                        });

            } catch (Exception e) {
                refresh.clearAnimation();
                e.printStackTrace();
            }

        } else {
            refresh.clearAnimation();
            empLast7DaysPSTChart.setVisibility(View.GONE);
            ApiFintechUtilMethods.INSTANCE.NetworkError(EmpDashboardActivity.this);
        }

    }

    private void GetUserCommitmentChart(boolean isLoaderShow) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(EmpDashboardActivity.this)) {
            try {

                ApiFintechUtilMethods.INSTANCE.GetUserCommitmentChart(EmpDashboardActivity.this, mLoginDataResponse, deviceId,
                        deviceSerialNum, isLoaderShow ? loader : null, object -> {
                            BasicResponse mResponse = (BasicResponse) object;
                            refresh.clearAnimation();
                            if (mResponse != null) {

                                chartUserCommitmentData(mResponse);
                            } else {
                                empCommitmentVsAcheivementChart.setVisibility(View.GONE);
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
                refresh.clearAnimation();
                empCommitmentVsAcheivementChart.setVisibility(View.GONE);
            }

        } else {
            refresh.clearAnimation();
            empCommitmentVsAcheivementChart.setVisibility(View.GONE);
            ApiFintechUtilMethods.INSTANCE.NetworkError(EmpDashboardActivity.this);
        }

    }

    private void GetUserCommitment(boolean isLoaderShow) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(EmpDashboardActivity.this)) {
            try {

                ApiFintechUtilMethods.INSTANCE.GetUserCommitment(EmpDashboardActivity.this, mLoginDataResponse, deviceId,
                        deviceSerialNum, isLoaderShow ? loader : null, new ApiFintechUtilMethods.ApiCallBack() {
                            @Override
                            public void onSucess(Object object) {
                                GetUserCommitmentResponse mResponse = (GetUserCommitmentResponse) object;

                                if (mResponse != null && mResponse.getData() != null && mResponse.getData().size() > 0) {
                                    setCommitmentDialog("Set Commitment", mResponse.getData(), mLoginDataResponse, deviceId, deviceSerialNum, loader);
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(EmpDashboardActivity.this, "Data not found");
                                }
                            }

                        });

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            ApiFintechUtilMethods.INSTANCE.NetworkError(EmpDashboardActivity.this);

        }

    }

    private void GetEmpDownlineUser(boolean isLoaderShow) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(EmpDashboardActivity.this)) {
            try {

                ApiFintechUtilMethods.INSTANCE.GetEmpDownlineUser(EmpDashboardActivity.this, mLoginDataResponse, deviceId,
                        deviceSerialNum, isLoaderShow ? loader : null, new ApiFintechUtilMethods.ApiCallBack() {
                            @Override
                            public void onSucess(Object object) {
                                refresh.clearAnimation();
                                GetEmpDownlineUserResponse mGetEmpDownlineUserResponse = (GetEmpDownlineUserResponse) object;
                                if (mGetEmpDownlineUserResponse != null && mGetEmpDownlineUserResponse.getData() != null && mGetEmpDownlineUserResponse.getData().size() > 0) {

                                    setEmpDownlineUserData(mGetEmpDownlineUserResponse);
                                } else {
                                    empAttendanceView.setVisibility(View.GONE);
                                }
                            }
                        });

            } catch (Exception e) {
                refresh.clearAnimation();
                e.printStackTrace();
            }

        } else {
            refresh.clearAnimation();
            empAttendanceView.setVisibility(View.GONE);
            ApiFintechUtilMethods.INSTANCE.NetworkError(EmpDashboardActivity.this);
        }

    }

    public void setCommitmentDialog(String title, final List<GetUserCommitment> list, LoginResponse loginPrefResponse, String deviceId, String deviceSerialNum, CustomLoader loader) {
        try {
            if (alertDialogServiceList != null && alertDialogServiceList.isShowing()) {
                return;
            }


            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(EmpDashboardActivity.this, R.style.full_screen_dialog);
            alertDialogServiceList = dialogBuilder.create();
            //alertDialogServiceList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            LayoutInflater inflater = EmpDashboardActivity.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_emp_commitment_list, null);
            alertDialogServiceList.setView(dialogView);

            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(EmpDashboardActivity.this));
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView titleTv = dialogView.findViewById(R.id.titleTv);

            titleTv.setText(title);

            SetUserCommitmentAdapter mSetUserCommitmentAdapter = new SetUserCommitmentAdapter(list, EmpDashboardActivity.this, loginPrefResponse, deviceId, deviceSerialNum, loader, new SetUserCommitmentAdapter.onSucess() {
                @Override
                public void onSucess() {
                    GetUserCommitmentChart(false);
                }
            });
            recyclerView.setAdapter(mSetUserCommitmentAdapter);

            closeIv.setOnClickListener(v -> alertDialogServiceList.dismiss());
            alertDialogServiceList.show();
            alertDialogServiceList.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            alertDialogServiceList.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }


    void setEmpTargetSegmentData(GetTargetSegmentResponse mGetTargetSegmentResponse) {
        empTragetView.setVisibility(View.VISIBLE);
        recyclerViewTraget.setAdapter(new EmpTargetSegmentAdapter(mGetTargetSegmentResponse.getData(), this));
    }

    void setTodayRecordData(GetEmpTodayLivePSTResponse mGetEmpTodayLivePSTResponse) {
        todayRecordView.setVisibility(View.VISIBLE);
        recyclerViewTodayRecord.setAdapter(new EmpTodayLivePSTAdapter(mGetEmpTodayLivePSTResponse.getData(), mLoginDataResponse, loader, deviceId, deviceSerialNum, this));
    }


    void setEmpLMTDVSMTDData(GetLMTDVsMTDResponse mGetLMTDVsMTDResponse) {
        empLMTDVsMTDView.setVisibility(View.VISIBLE);
        recyclerViewLMTDVsMTD.setAdapter(new EmpLMTDVsMTDAdapter(mGetLMTDVsMTDResponse.getData(), this));

        chartLMTDVsMTDData(mGetLMTDVsMTDResponse);
    }

    void setEmpDownlineUserData(GetEmpDownlineUserResponse mGetEmpDownlineUserResponse) {
        empAttendanceView.setVisibility(View.VISIBLE);
        recyclerViewAttandance.setAdapter(new EmpDownlineUserAdapter(mGetEmpDownlineUserResponse.getData(), this));
    }

    void chartLMTDVsMTDData(GetLMTDVsMTDResponse mGetLMTDVsMTDResponse) {

        if (mGetLMTDVsMTDResponse != null && mGetLMTDVsMTDResponse.getData() != null && mGetLMTDVsMTDResponse.getData().size() > 0) {
            empLMTDVsMTDChart.setVisibility(View.VISIBLE);
            empLMTDVsMTDChartView.getDescription().setEnabled(false);
            empLMTDVsMTDChartView.setPinchZoom(false);
            empLMTDVsMTDChartView.setDoubleTapToZoomEnabled(false);
            empLMTDVsMTDChartView.setHighlightFullBarEnabled(true);
            MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
            mv.setChartView(empLMTDVsMTDChartView); // For bounds control
            empLMTDVsMTDChartView.setMarker(mv);

            List<String> typeArray = new ArrayList<>();
            ArrayList<BarEntry> barEntries = new ArrayList<>();
            ArrayList<BarEntry> barEntries1 = new ArrayList<>();
            ArrayList<BarEntry> barEntries2 = new ArrayList<>();
            for (int i = 0; i < mGetLMTDVsMTDResponse.getData().size(); i++) {
                GetLMTDVsMTD mGetLMTDVsMTD = mGetLMTDVsMTDResponse.getData().get(i);
                typeArray.add(mGetLMTDVsMTD.getType() + "");

                barEntries.add(new BarEntry(i + 1, Float.parseFloat(Utility.INSTANCE.formatedAmountWithOutRupees(mGetLMTDVsMTD.getLm() + ""))));
                barEntries1.add(new BarEntry(i + 1, Float.parseFloat(Utility.INSTANCE.formatedAmountWithOutRupees(mGetLMTDVsMTD.getLmtd() + ""))));
                barEntries2.add(new BarEntry(i + 1, Float.parseFloat(Utility.INSTANCE.formatedAmountWithOutRupees(mGetLMTDVsMTD.getMtd() + ""))));

            }


            BarDataSet barDataSet = new BarDataSet(barEntries, "LM");
            barDataSet.setColor(Color.parseColor("#96C5F3"));
            BarDataSet barDataSet1 = new BarDataSet(barEntries1, "LMTD");
            barDataSet1.setColors(Color.parseColor("#707080"));
            BarDataSet barDataSet2 = new BarDataSet(barEntries2, "MTD");
            barDataSet2.setColors(Color.parseColor("#A5F494"));

            BarData data = new BarData(barDataSet, barDataSet1, barDataSet2);
            empLMTDVsMTDChartView.setData(data);
            XAxis xAxis = empLMTDVsMTDChartView.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(typeArray));
            xAxis.setTextSize(10f);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1);
            xAxis.setTextColor(Color.WHITE);
            // xAxis.setYOffset(5f);

            xAxis.setCenterAxisLabels(true);


            YAxis leftAxis = empLMTDVsMTDChartView.getAxisLeft();
            leftAxis.setValueFormatter(new LargeValueFormatter());
            leftAxis.setAxisMinimum(0f);
            leftAxis.setTextColor(Color.WHITE);

            Legend l = empLMTDVsMTDChartView.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setTextColor(Color.WHITE);
            l.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
            l.setDrawInside(false);
            l.setTextSize(11f);
            l.setFormSize(10f);

           /*
            l.setXEntrySpace(15);
        l.setYOffset(10f);
       // l.setXOffset(10f);
            l.setYEntrySpace(35f);
            l.setTextSize(15f);*/

            empLMTDVsMTDChartView.getAxisRight().setEnabled(false);

            float barSpace = 0.15f;
            float groupSpace = 0.10f;
            int groupCount = 3;

            //IMPORTANT *****
            data.setBarWidth(0.15f);
            data.setDrawValues(false);
            empLMTDVsMTDChartView.getXAxis().setAxisMinimum(0);
            empLMTDVsMTDChartView.getXAxis().setAxisMaximum(0 + empLMTDVsMTDChartView.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
            empLMTDVsMTDChartView.groupBars(0, groupSpace, barSpace); // perform the "explicit" grouping
            //***** IMPORTANT

        } else {
            empLMTDVsMTDChart.setVisibility(View.GONE);
        }
    }

    void chartLastdayVsTodayData(GetLastdayVsTodayChartResponse mResponse) {

        if (mResponse != null && mResponse.getData() != null && mResponse.getData().size() > 0) {
            empLastdayVsTodayChart.setVisibility(View.VISIBLE);
            empLastdayVsTodayChartView.getDescription().setEnabled(false);
            empLastdayVsTodayChartView.setPinchZoom(false);
            empLastdayVsTodayChartView.setDoubleTapToZoomEnabled(false);
            empLastdayVsTodayChartView.setHighlightFullBarEnabled(true);
            MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
            mv.setChartView(empLastdayVsTodayChartView); // For bounds control
            empLastdayVsTodayChartView.setMarker(mv);
           /* MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
            mv.setChartView(chart); // For bounds control
            empLastdayVsTodayChartView.setMarker(mv);*/

            List<String> typeArray = new ArrayList<>();
            ArrayList<BarEntry> barEntries = new ArrayList<>();
            ArrayList<BarEntry> barEntries1 = new ArrayList<>();
            for (int i = 0; i < mResponse.getData().size(); i++) {
                GetLastdayVsTodayChart mGetLMTDVsMTD = mResponse.getData().get(i);
                typeArray.add(mGetLMTDVsMTD.getType() + "");

                barEntries.add(new BarEntry(i + 1, Float.parseFloat(Utility.INSTANCE.formatedAmountWithOutRupees(mGetLMTDVsMTD.getLastDay() + ""))));
                barEntries1.add(new BarEntry(i + 1, Float.parseFloat(Utility.INSTANCE.formatedAmountWithOutRupees(mGetLMTDVsMTD.getToday() + ""))));


            }


            BarDataSet barDataSet = new BarDataSet(barEntries, "Last Day");
            barDataSet.setColor(Color.parseColor("#2b912d"));
            BarDataSet barDataSet1 = new BarDataSet(barEntries1, "Today");
            barDataSet1.setColors(Color.parseColor("#D64208"));

            BarData data = new BarData(barDataSet, barDataSet1);
            empLastdayVsTodayChartView.setData(data);
            XAxis xAxis = empLastdayVsTodayChartView.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(typeArray));
            xAxis.setTextSize(9f);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1);
            /*xAxis.setYOffset(5);*/
            xAxis.setTextColor(Color.WHITE);
            xAxis.setCenterAxisLabels(true);


            YAxis leftAxis = empLastdayVsTodayChartView.getAxisLeft();
            leftAxis.setValueFormatter(new LargeValueFormatter());
            leftAxis.setAxisMinimum(0f);
            leftAxis.setTextColor(Color.WHITE);

            Legend l = empLastdayVsTodayChartView.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setTextColor(Color.WHITE);
            l.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
            l.setDrawInside(false);
            l.setTextSize(11f);
            l.setFormSize(10f);
            //  l.setXEntrySpace(12f);
           /*
            l.setXEntrySpace(15);
        l.setYOffset(10f);
       // l.setXOffset(10f);
            l.setYEntrySpace(35f);
            l.setTextSize(15f);*/

            empLastdayVsTodayChartView.getAxisRight().setEnabled(false);

            float barSpace = 0.20f;
            float groupSpace = 0.20f;
            int groupCount = 5;

            //IMPORTANT *****
            data.setBarWidth(0.20f);
            data.setDrawValues(false);
            empLastdayVsTodayChartView.getXAxis().setAxisMinimum(0);
            empLastdayVsTodayChartView.getXAxis().setAxisMaximum(0 + empLastdayVsTodayChartView.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
            empLastdayVsTodayChartView.groupBars(0, groupSpace, barSpace); // perform the "explicit" grouping
            //***** IMPORTANT

        } else {
            empLastdayVsTodayChart.setVisibility(View.GONE);
        }
    }

    void chartLastSevenDayPSTData(GetLastSevenDayPSTDataResponse mResponse) {
        if (mResponse != null && mResponse.getData() != null && mResponse.getData().size() > 0) {
            empLast7DaysPSTChart.setVisibility(View.VISIBLE);
            empLast7DaysPSTChartView.getDescription().setEnabled(false);
            empLast7DaysPSTChartView.setPinchZoom(false);
            empLast7DaysPSTChartView.setDoubleTapToZoomEnabled(false);

            // empLast7DaysPSTChartView.setHighlightPerDragEnabled(true);

            // empLast7DaysPSTChartView.setTouchEnabled(true);

            //empLast7DaysPSTChartView.setDragDecelerationFrictionCoef(0.9f);

            // enable scaling and dragging
            // empLast7DaysPSTChartView.setDragEnabled(true);
            empLast7DaysPSTChartView.setScaleEnabled(false);
            empLast7DaysPSTChartView.setDrawGridBackground(false);
            empLast7DaysPSTChartView.animateX(1500);

            MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
            mv.setChartView(empLast7DaysPSTChartView); // For bounds control
            empLast7DaysPSTChartView.setMarker(mv);

            ArrayList<Entry> values1 = new ArrayList<>();
            ArrayList<Entry> values2 = new ArrayList<>();
            ArrayList<Entry> values3 = new ArrayList<>();
            /*ArrayList<Double> valuesList = new ArrayList<>();*/
            List<String> dateArray = new ArrayList<>();
            for (int i = 0; i < mResponse.getData().size(); i++) {
                GetLastSevenDayPSTData mGetLastSevenDayPSTData = mResponse.getData().get(i);
                values1.add(new Entry(i, (float) mGetLastSevenDayPSTData.getPrimary()));
                values2.add(new Entry(i, (float) mGetLastSevenDayPSTData.getSecoundary()));
                values3.add(new Entry(i, (float) mGetLastSevenDayPSTData.getTertiary()));
                dateArray.add(Utility.INSTANCE.formatedOnlyDate(mGetLastSevenDayPSTData.getTransactionDate()));
                /*valuesList.add(mGetLastSevenDayPSTData.getPrimary());
                valuesList.add(mGetLastSevenDayPSTData.getSecoundary());
                valuesList.add(mGetLastSevenDayPSTData.getTertiary());*/
            }

            //  double largeValue=  Collections.max(valuesList);

            Legend l = empLast7DaysPSTChartView.getLegend();

            // modify the legend ...
            l.setForm(Legend.LegendForm.LINE);
            // l.setTypeface(Typeface.DEFAULT_BOLD);

            l.setTextSize(11f);
            l.setFormSize(10f);
            // l.setXEntrySpace(12f);
            l.setTextColor(Color.WHITE);
            l.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);

//        l.setYOffset(11f);

            XAxis xAxis = empLast7DaysPSTChartView.getXAxis();
            // xAxis.setTypeface(tfLight);
            xAxis.setTextSize(9f);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(dateArray));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor(Color.WHITE);
            // xAxis.setDrawGridLines(false);
            // xAxis.setDrawAxisLine(false);
            xAxis.setGranularity(1);
            // xAxis.setCenterAxisLabels(true);

            YAxis leftAxis = empLast7DaysPSTChartView.getAxisLeft();
            //leftAxis.setTypeface(tfLight);
            leftAxis.setValueFormatter(new LargeValueFormatter());
            leftAxis.setTextColor(Color.WHITE);
            //  leftAxis.setAxisMaximum((float) largeValue);
            //   leftAxis.setAxisMinimum(0f);
            leftAxis.setDrawGridLines(true);
            leftAxis.setGranularityEnabled(true);
            YAxis rightAxis = empLast7DaysPSTChartView.getAxisRight();

            // rightAxis.setTypeface(tfLight);
            //rightAxis.setTextColor(Color.RED);
            rightAxis.setAxisMaximum(0);
            rightAxis.setAxisMinimum(0);
            //rightAxis.setDrawGridLines(false);
            // rightAxis.setDrawZeroLine(false);
            // rightAxis.setGranularityEnabled(false);

            LineDataSet set1, set2, set3;

            set1 = new LineDataSet(values1, "Primary");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setColor(Color.parseColor("#2b912d"));

            set1.setCircleColor(Color.parseColor("#2b912d"));
            set1.setLineWidth(3f);
            set1.setCircleRadius(5f);
           /* set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);*/

            set2 = new LineDataSet(values2, "Secoundary");
            set2.setAxisDependency(YAxis.AxisDependency.LEFT);

            set2.setColor(Color.parseColor("#707080"));
            set2.setCircleColor(Color.parseColor("#707080"));
            set2.setLineWidth(3f);
            set2.setCircleRadius(5f);
           /* set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));*/
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = new LineDataSet(values3, "Tertiary");
            set3.setAxisDependency(YAxis.AxisDependency.LEFT);
            set3.setColor(Color.parseColor("#D64208"));
            set3.setCircleColor(Color.parseColor("#D64208"));
            set3.setLineWidth(3f);
            set3.setCircleRadius(5f);
           /* set3.setFillAlpha(65);
            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set3.setDrawCircleHole(false);
            set3.setHighLightColor(Color.rgb(244, 117, 117));*/

            // create a data object with the data sets
            LineData data = new LineData(set1, set2, set3);
            // data.setValueTextColor(Color.WHITE);
            // data.setValueTextSize(7f);
            //  data.setValueFormatter(new LargeValueFormatter());
            data.setDrawValues(false);

            // set data
            empLast7DaysPSTChartView.setData(data);

        } else {
            empLast7DaysPSTChart.setVisibility(View.GONE);
        }
    }

    void chartUserCommitmentData(BasicResponse mResponse) {

        if (mResponse != null) {
            empCommitmentVsAcheivementChart.setVisibility(View.VISIBLE);
            //empCommitmentVsAcheivementChartView.setUsePercentValues(false);
            empCommitmentVsAcheivementChartView.getDescription().setEnabled(false);
            // empCommitmentVsAcheivementChartView.setExtraOffsets(5, 10, 5, 5);

            // empCommitmentVsAcheivementChartView.setDragDecelerationFrictionCoef(0.95f);


            // empCommitmentVsAcheivementChartView.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

            empCommitmentVsAcheivementChartView.setDrawHoleEnabled(false);
            // empCommitmentVsAcheivementChartView.setHoleColor(Color.WHITE);

            // empCommitmentVsAcheivementChartView.setTransparentCircleColor(Color.BLUE);
            //  empCommitmentVsAcheivementChartView.setTransparentCircleAlpha(110);

            //  empCommitmentVsAcheivementChartView.setHoleRadius(58f);
            //  empCommitmentVsAcheivementChartView.setTransparentCircleRadius(61f);

            //empCommitmentVsAcheivementChartView.setDrawCenterText(true);

            empCommitmentVsAcheivementChartView.setRotationAngle(0);
            // enable rotation of the chart by touch
            empCommitmentVsAcheivementChartView.setRotationEnabled(true);
            empCommitmentVsAcheivementChartView.setHighlightPerTapEnabled(true);
            empCommitmentVsAcheivementChartView.animateY(1400, Easing.EaseInOutQuad);
            // chart.spin(2000, 0, 360);

            Legend l = empCommitmentVsAcheivementChartView.getLegend();
            l.setXOffset(10);
            l.setYOffset(7);
            l.setTextSize(11f);
            l.setFormSize(10f);
            l.setTextColor(Color.WHITE);
            l.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
            l.setDrawInside(false);
            l.setEnabled(true);
            ArrayList<PieEntry> entries = new ArrayList<>();


            entries.add(new PieEntry((float) mResponse.getTotalCommitment(), "Total Commitment"));
            entries.add(new PieEntry((float) mResponse.getTotalAchieved(), "Total Acheived"));

            //entries.add(new PieEntry(1000f, "Total Commitment"));
            //entries.add(new PieEntry(200f, "Total Acheived"));


            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setSliceSpace(4f);
            dataSet.setSelectionShift(6f);
            // add a lot of colors

            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.RED);
            colors.add(Color.GREEN);
            /*for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);
*/
      /*  for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);*/

            /*  colors.add(ColorTemplate.getHoloBlue());*/

            dataSet.setColors(colors);
            //dataSet.setSelectionShift(0f);


            dataSet.setValueLinePart1OffsetPercentage(70.f);
            dataSet.setValueLinePart1Length(0.2f);
            dataSet.setValueLinePart2Length(1f);

            //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(15f);
            data.setValueTypeface(Typeface.DEFAULT_BOLD);
            data.setValueTextColor(Color.WHITE);
            empCommitmentVsAcheivementChartView.setDrawEntryLabels(false);
            empCommitmentVsAcheivementChartView.setData(data);

            // undo all highlights
            // empCommitmentVsAcheivementChartView.highlightValues(null);

            empCommitmentVsAcheivementChartView.invalidate();

        } else {
            empCommitmentVsAcheivementChart.setVisibility(View.GONE);
        }
    }

    public void UpdateApp() {

        if (!isUpdateDialogShowing) {

            appUpdateManager
                    .getAppUpdateInfo()
                    .addOnSuccessListener(appUpdateInfo -> {
                        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE ||
                                appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                            try {
                                isUpdateDialogShowing = true;
                                if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
                                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, IMMEDIATE, this, MY_REQUEST_UPDATE_APP_CODE);
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.versionOldDialog(this);
                                }
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                                isUpdateDialogShowing = false;
                                ApiFintechUtilMethods.INSTANCE.versionOldDialog(this);
                            }
                        }
                    });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_UPDATE_APP_CODE) {
            if (resultCode == RESULT_CANCELED) {
                isUpdateDialogShowing = false;
                UpdateApp();
            } else if (resultCode == RESULT_OK) {
                isUpdateDialogShowing = false;
                UpdateApp();
            } else {
                isUpdateDialogShowing = false;
                ApiFintechUtilMethods.INSTANCE.versionOldDialog(this);
            }

        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }
}
