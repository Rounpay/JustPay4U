package com.solution.app.justpay4u.Fintech.Employee.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.Fintech.Employee.Adapter.MeetingDetailsAdapter;
import com.solution.app.justpay4u.Fintech.Employee.Adapter.MeetingReportAdapter;
import com.solution.app.justpay4u.Fintech.Employee.Adapter.MettingReportMapInfoWindowAdapter;
import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetMeetingDetails;
import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetMeetingReport;
import com.solution.app.justpay4u.Fintech.Employee.Api.Object.MapPoints;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetMeetingDetailResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetMeetingReportResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.MapPointResponse;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class MeetingtReport extends AppCompatActivity {

    ProgressDialog mProgressDialog = null;
    RecyclerView recycler_view;

    ArrayList<GetMeetingReport> transactionsObjects = new ArrayList<>();

    CustomLoader loader;
    EditText search_all;
    CustomFilterDialog mCustomFilterDialog;
    private int filterChooseCriteriaId;
    private String filterChooseCriteria;
    private String filterEnterCriteria;
    private int filterTopValue = 50;
    private String filterRoleValue;
    private LoginResponse mLoginDataResponse;
    private String filterFromDate = "", filterTillDate = "";
    TextView expense, travel, count;
    View bottomView;
    private AlertDialog alertDialogSubList;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    private String deviceId, deviceSerialNum;
    private Dialog mDialogMaps;
    private AppPreferences mAppPreferences;
    private MeetingReportAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_meeting_report);


            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            findViewId();
            new Handler(Looper.getMainLooper()).post(() -> {
                mCustomFilterDialog = new CustomFilterDialog(this);
                filterFromDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(Calendar.getInstance().getTime());
                filterTillDate = filterFromDate;
                HitApi();
            });
        });
    }

    void findViewId() {
        search_all = findViewById(R.id.search_all);
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));
        noDataView = findViewById(R.id.noDataView);
        search_all = findViewById(R.id.search_all);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MeetingReportAdapter(transactionsObjects, this);
        recycler_view.setAdapter(mAdapter);
        mProgressDialog = new ProgressDialog(MeetingtReport.this);
        expense = findViewById(R.id.expense);
        travel = findViewById(R.id.travel);
        count = findViewById(R.id.count);
        bottomView = findViewById(R.id.bottomView);
        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mAdapter != null) {
                    mAdapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            if (!loader.isShowing()) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }
            ApiFintechUtilMethods.INSTANCE.GetMeetingReport(this, filterEnterCriteria, filterChooseCriteriaId, filterFromDate, filterTillDate,
                    filterTopValue, mLoginDataResponse, deviceId, deviceSerialNum,
                    loader, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            transactionsObjects.clear();
                            GetMeetingReportResponse mGetMeetingReportResponse = (GetMeetingReportResponse) object;
                            if (mGetMeetingReportResponse != null && mGetMeetingReportResponse.getData() != null && mGetMeetingReportResponse.getData().size() > 0) {
                                double totalExpense = 0, totalTravel = 0;
                                int totalCount = 0;
                                for (GetMeetingReport meetingReport : mGetMeetingReportResponse.getData()) {
                                    totalExpense = totalExpense + meetingReport.getTotalExpense();
                                    totalTravel = totalTravel + meetingReport.getTotalTravel();
                                    totalCount = totalCount + meetingReport.getMeetingCount();
                                }
                                bottomView.setVisibility(View.VISIBLE);
                                expense.setText(Utility.INSTANCE.formatedAmountWithOutRupees(totalExpense + ""));
                                travel.setText(Utility.INSTANCE.formatedAmountWithOutRupees(totalTravel + ""));
                                count.setText(totalCount + "");

                                transactionsObjects.addAll(mGetMeetingReportResponse.getData());
                                noDataView.setVisibility(View.GONE);
                            } else {
                                if (filterFromDate.equalsIgnoreCase(filterTillDate)) {
                                    errorMsg.setText("Record not found for " + filterFromDate);
                                    ApiFintechUtilMethods.INSTANCE.Error(MeetingtReport.this, "Record not found for\n" + filterFromDate);

                                } else {
                                    errorMsg.setText("Record not found between\n" + filterFromDate + " to " + filterTillDate);
                                    ApiFintechUtilMethods.INSTANCE.Error(MeetingtReport.this, "Record not found between\n" + filterFromDate + " to " + filterTillDate);

                                }
                                bottomView.setVisibility(View.GONE);
                                noDataView.setVisibility(View.VISIBLE);
                            }
                            mAdapter = new MeetingReportAdapter(transactionsObjects, MeetingtReport.this);
                            recycler_view.setAdapter(mAdapter);
                        }

                        @Override
                        public void onError(int error) {
                            setInfoHideShow(error);
                        }
                    });

        } else {
            noDataView.setVisibility(View.VISIBLE);
            bottomView.setVisibility(View.GONE);
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    void setInfoHideShow(int errorType) {
        recycler_view.setVisibility(View.GONE);
        transactionsObjects.clear();
        mAdapter.notifyDataSetChanged();

        if (errorType == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        } else {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
            if (filterFromDate.equalsIgnoreCase(filterTillDate)) {
                errorMsg.setText("Record not found for " + filterFromDate);
                ApiFintechUtilMethods.INSTANCE.Error(MeetingtReport.this, "Record not found for\n" + filterFromDate);

            } else {
                errorMsg.setText("Record not found between\n" + filterFromDate + " to " + filterTillDate);
                ApiFintechUtilMethods.INSTANCE.Error(MeetingtReport.this, "Record not found between\n" + filterFromDate + " to " + filterTillDate);

            }
            errorMsg.setText("Record not found");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_filter) {
           /* if (visibleFlag) {
                visibleFlag = false;
                searchContainer.setVisibility(View.GONE);
            } else {
                visibleFlag = true;
                searchContainer.setVisibility(View.VISIBLE);
            }*/
            mCustomFilterDialog.openEmpMeetingFilter(filterFromDate, filterTillDate, filterChooseCriteriaId, filterChooseCriteria,
                    filterEnterCriteria, filterTopValue, filterRoleValue, new CustomFilterDialog.EmpMeetingFilterCallBack() {
                        @Override
                        public void onSubmitClick(String fromDate, String tillDate, int topValue, String enterCriteria, int criteriaId, String criteriaValue, String roleValue, int roleId) {
                            filterFromDate = fromDate;
                            filterTillDate = tillDate;
                            filterChooseCriteriaId = criteriaId;
                            filterChooseCriteria = criteriaValue;
                            filterEnterCriteria = enterCriteria;
                            filterTopValue = topValue;
                            filterRoleValue = roleValue;
                            HitApi();
                        }
                    });


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void HitSubReportApi(GetMeetingReport meetingReport) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            ApiFintechUtilMethods.INSTANCE.GetSubMeetingReport(this, meetingReport.getId(), filterEnterCriteria, filterChooseCriteriaId, filterFromDate, filterTillDate,
                    filterTopValue, mLoginDataResponse, deviceId, deviceSerialNum,
                    loader, new ApiFintechUtilMethods.ApiCallBackTwoMethod() {
                        @Override
                        public void onSucess(Object object) {
                            GetMeetingDetailResponse mGetMeetingDetailResponse = (GetMeetingDetailResponse) object;
                            if (mGetMeetingDetailResponse != null && mGetMeetingDetailResponse.getData() != null && mGetMeetingDetailResponse.getData().size() > 0) {
                                meetingReport.setMeetingDetailList(mGetMeetingDetailResponse.getData());
                                showSubListDialog(meetingReport, mGetMeetingDetailResponse.getData());
                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(MeetingtReport.this, "Data not found.");
                            }

                        }

                        @Override
                        public void onError(Object object) {

                        }
                    });

        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    public void HitMapPoinApi(GetMeetingReport meetingReport) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            ApiFintechUtilMethods.INSTANCE.GetMapPoints(this, meetingReport.getId(), mLoginDataResponse, deviceId, deviceSerialNum,
                    loader, new ApiFintechUtilMethods.ApiCallBackTwoMethod() {
                        @Override
                        public void onSucess(Object object) {
                            MapPointResponse mMapPointResponse = (MapPointResponse) object;
                            if (mMapPointResponse != null && mMapPointResponse.getData() != null && mMapPointResponse.getData().size() > 0) {
                                meetingReport.setMapPointList(mMapPointResponse.getData());
                                showMapDialog(meetingReport, mMapPointResponse.getData());
                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(MeetingtReport.this, "Data not found.");
                            }

                        }

                        @Override
                        public void onError(Object object) {

                        }
                    });

        } else {
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }


    public void showSubListDialog(GetMeetingReport meetingReport, ArrayList<GetMeetingDetails> arrayList) {
        try {
            if (alertDialogSubList != null && alertDialogSubList.isShowing()) {
                return;
            }


            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this, R.style.full_screen_dialog);
            alertDialogSubList = dialogBuilder.create();
            //alertDialogSubList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_meeting_sub_report, null);
            alertDialogSubList.setView(dialogView);

            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView titleTv = dialogView.findViewById(R.id.titleTv);

            TextView nameTv = dialogView.findViewById(R.id.nameTv);
            TextView travel = dialogView.findViewById(R.id.travel);
            TextView expense = dialogView.findViewById(R.id.expense);
            TextView date = dialogView.findViewById(R.id.date);
            TextView count = dialogView.findViewById(R.id.count);
            TextView close = dialogView.findViewById(R.id.close);

            nameTv.setText(meetingReport.getUserName() + "");
            expense.setText(meetingReport.getTotalExpense() + "");
            travel.setText(meetingReport.getTotalTravel() + "");
            count.setText(meetingReport.getMeetingCount() + "");
            date.setText(Utility.INSTANCE.formatedDateWithT(meetingReport.getEntryDate() + ""));
            if (meetingReport.isClosed()) {
                close.setText("Close");
                close.setTextColor(getResources().getColor(R.color.red));
                close.setBackgroundResource(R.drawable.rounded_light_red_border);
            } else {
                close.setText("Open");
                close.setTextColor(getResources().getColor(R.color.darkGreen));
                close.setBackgroundResource(R.drawable.rounded_green_border);
            }

            MeetingDetailsAdapter meetingDetailsAdapter = new MeetingDetailsAdapter(arrayList, this, true);

            recyclerView.setAdapter(meetingDetailsAdapter);

            closeIv.setOnClickListener(v -> alertDialogSubList.dismiss());
            alertDialogSubList.show();
            alertDialogSubList.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            alertDialogSubList.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }

    public void showMapDialog(GetMeetingReport meetingReport, ArrayList<MapPoints> arrayList) {
        try {
            if (mDialogMaps != null && mDialogMaps.isShowing()) {
                return;
            }
            mDialogMaps = new Dialog(this, R.style.full_screen_dialog);
            mDialogMaps.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialogMaps.setContentView(R.layout.dialog_map);
            mDialogMaps.setCancelable(true);

            MapView mMapView = mDialogMaps.findViewById(R.id.mapView);

            mMapView.onCreate(mDialogMaps.onSaveInstanceState());
            mMapView.onResume();
            mMapView.getMapAsync(googleMap -> {
                for (MapPoints mapPoints : arrayList) {
                    LatLng mLat = new LatLng(Double.parseDouble(mapPoints.getLatitude()), Double.parseDouble(mapPoints.getLongitude()));
                    googleMap.addMarker(new MarkerOptions()
                            .position(mLat)
                            .title(mapPoints.getDescription() + ""));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLat, 12));

                    googleMap.setInfoWindowAdapter(new MettingReportMapInfoWindowAdapter(this));
                }

            });
            mDialogMaps.findViewById(R.id.closeBtn).setOnClickListener(v -> mDialogMaps.dismiss());
            mDialogMaps.show();
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*/
}