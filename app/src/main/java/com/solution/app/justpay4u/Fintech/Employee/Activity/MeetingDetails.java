package com.solution.app.justpay4u.Fintech.Employee.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.Fintech.Employee.Adapter.MeetingDetailsAdapter;
import com.solution.app.justpay4u.Fintech.Employee.Adapter.MettingDetailMapInfoWindowAdapter;
import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetMeetingDetails;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetMeetingDetailResponse;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class MeetingDetails extends AppCompatActivity {
    RecyclerView recycler_view;
    MeetingDetailsAdapter mAdapter;

    ArrayList<GetMeetingDetails> transactionsObjects = new ArrayList<>();
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    CustomLoader loader;
    EditText search_all;
    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterTillDate = "";
    private int filterChooseCriteriaId;
    private String filterChooseCriteria;
    private String filterEnterCriteria;
    private int filterTopValue = 50;
    private String filterRoleValue;
    private LoginResponse mLoginDataResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private Dialog mDialogMaps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_meeting_details);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            findViews();

            new Handler(Looper.getMainLooper()).post(() -> {
                mCustomFilterDialog = new CustomFilterDialog(this);
                filterFromDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(Calendar.getInstance().getTime());
                filterTillDate = filterFromDate;
                HitApi();
            });
        });
    }


    void findViews() {

        noDataView = findViewById(R.id.noDataView);
        search_all = findViewById(R.id.search_all);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        retryBtn.setOnClickListener(v -> HitApi());
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));
        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                if (mAdapter != null) {
                    mAdapter.filter(newText.toString());
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
            ApiFintechUtilMethods.INSTANCE.GetMeetingDetail(this, filterEnterCriteria, filterChooseCriteriaId, filterFromDate, filterTillDate,
                    filterTopValue,
                    mLoginDataResponse, deviceId, deviceSerialNum, loader, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            transactionsObjects.clear();
                            GetMeetingDetailResponse mGetMeetingDetailResponse = (GetMeetingDetailResponse) object;
                            if (mGetMeetingDetailResponse != null && mGetMeetingDetailResponse.getData() != null && mGetMeetingDetailResponse.getData().size() > 0) {
                                transactionsObjects.addAll(mGetMeetingDetailResponse.getData());
                                noDataView.setVisibility(View.GONE);
                            } else {
                                if (filterFromDate.equalsIgnoreCase(filterTillDate)) {
                                    errorMsg.setText("Record not found for " + filterFromDate);
                                    ApiFintechUtilMethods.INSTANCE.Error(MeetingDetails.this, "Record not found for\n" + filterFromDate);

                                } else {
                                    errorMsg.setText("Record not found between\n" + filterFromDate + " to " + filterTillDate);
                                    ApiFintechUtilMethods.INSTANCE.Error(MeetingDetails.this, "Record not found between\n" + filterFromDate + " to " + filterTillDate);

                                }
                                noNetworkView.setVisibility(View.GONE);
                                noDataView.setVisibility(View.VISIBLE);
                            }
                            mAdapter = new MeetingDetailsAdapter(transactionsObjects, MeetingDetails.this, false);
                            recycler_view.setAdapter(mAdapter);
                        }

                        @Override
                        public void onError(int error) {
                            setInfoHideShow(error);
                        }
                    });

        } else {
            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK);
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
            if (filterFromDate.equalsIgnoreCase(filterTillDate)) {
                errorMsg.setText("Record not found for " + filterFromDate);
                ApiFintechUtilMethods.INSTANCE.Error(MeetingDetails.this, "Record not found for\n" + filterFromDate);

            } else {
                errorMsg.setText("Record not found between\n" + filterFromDate + " to " + filterTillDate);
                ApiFintechUtilMethods.INSTANCE.Error(MeetingDetails.this, "Record not found between\n" + filterFromDate + " to " + filterTillDate);

            }
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);


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
            filterShow();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void filterShow() {
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

    }

    public void showMapDialog(GetMeetingDetails item) {
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

                LatLng mLat = new LatLng(Double.parseDouble(item.getLatitute()), Double.parseDouble(item.getLongitute()));
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(mLat));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLat, 11), new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        marker.showInfoWindow();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                googleMap.setInfoWindowAdapter(new MettingDetailMapInfoWindowAdapter(this, item));
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

}