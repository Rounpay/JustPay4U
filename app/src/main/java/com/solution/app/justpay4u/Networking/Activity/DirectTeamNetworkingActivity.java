package com.solution.app.justpay4u.Networking.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Networking.Object.MemberListData;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.DataPoolCount;
import com.solution.app.justpay4u.ApiHits.MemberListRequest;
import com.solution.app.justpay4u.ApiHits.MemberListResponse;
import com.solution.app.justpay4u.ApiHits.NetworkingEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Networking.Adapter.DirectTeamNetworkingAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class DirectTeamNetworkingActivity extends AppCompatActivity {

    private String[] dateTypeArray = {"Registration Date", "Activation Date"};
    private String[] dateFilterArray = {"All" , "This Month" , "Last Month" , "Custom Date"};
    private String[] typeSortArray = {"Team Business" , "Self Business" , "Direct Business","Direct Team","Active Team" ,"Deactive Team","Register Date","Activate Date" };
    private String[] statusArray = {"All", "Active", "Deactive"};
    private String[] legArray = {"All"/*, "Left", "Right"*/};
    RecyclerView recycler_view;
    DirectTeamNetworkingAdapter mAdapter;
    ArrayList<MemberListData> transactionsObjects = new ArrayList<>();
    int balanceCheckResponse;
    EditText search_all;
    CustomLoader loader;

    public View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterToDate = "", filterStaus = "All";

    private LoginResponse loginPrefResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private String filterLeg = "All";
    private int filterDateType = 1;
    private int filterDateFilter = 0;
    private MemberListResponse memberListResponse;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_networking_direct_team);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            loginPrefResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            findViews();
            clickView();


            new Handler(Looper.getMainLooper()).post(() -> {
                mCustomFilterDialog = new CustomFilterDialog(this);
                filterFromDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(new Date());
                filterToDate = filterFromDate;


                HitApi(this,"","",true );
            });
        });

    }


    void findViews() {
        search_all = findViewById(R.id.search_all);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        Intent intent = getIntent();
        filterStaus=intent.getStringExtra("Deactive");
        balanceCheckResponse = getIntent().getIntExtra("BalanceCheckResponse",0);
        recycler_view = findViewById(R.id.recycler_view);
        mAdapter = new DirectTeamNetworkingAdapter(balanceCheckResponse,transactionsObjects, DirectTeamNetworkingActivity.this);
        recycler_view.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recycler_view.setAdapter(mAdapter);

    }


    void clickView() {
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));

        retryBtn.setOnClickListener(v -> HitApi(this, filterFromDate, filterToDate,true));

        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                mAdapter.getFilter().filter(newText.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void HitApi(Activity context, String filterFromDate, String filterToDate,boolean IsRecent) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {


            if (!loader.isShowing()) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }
            try {

                NetworkingEndPointInterface git = ApiClient.getClient().create(NetworkingEndPointInterface.class);
                Call<MemberListResponse> call = git.DirectMemberList(new MemberListRequest(new BasicRequest(loginPrefResponse.getData().getUserID(),
                        loginPrefResponse.getData().getLoginTypeID(),
                        ApplicationConstant.INSTANCE.APP_ID, deviceId,
                        "", BuildConfig.VERSION_NAME, deviceSerialNum, loginPrefResponse.getData().getSessionID(),
                        loginPrefResponse.getData().getSession()), new MemberListRequest(filterDateFilter+"",filterDateType+"",filterLeg, 0, filterFromDate, filterToDate, filterStaus,IsRecent)));

                call.enqueue(new Callback<MemberListResponse>() {
                    @Override
                    public void onResponse(Call<MemberListResponse> call, final retrofit2.Response<MemberListResponse> response) {

                        try {

                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    if (response.body().getStatuscode() == 1) {
                                        memberListResponse = response.body();
                                        dataParse(response.body());
                                        if (loader != null) {
                                            if (loader.isShowing()) {
                                                loader.dismiss();
                                            }
                                        }
                                    }else if (response.body().getStatuscode() == -1){
                                        if (loader != null) {
                                            if (loader.isShowing()) {
                                                loader.dismiss();
                                            }
                                        }
                                        openFilter();
                                    }else {
                                        if (loader != null) {
                                            if (loader.isShowing()) {
                                                loader.dismiss();
                                            }
                                        }
                                        setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                        if (!response.body().isVersionValid()) {
                                            ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                        }
                                    }
                                }
                            } else {
                                if (loader != null) {
                                    if (loader.isShowing()) {
                                        loader.dismiss();
                                    }
                                }
                                setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                            }
                        } catch (Exception e) {
                            if (loader != null) {
                                if (loader.isShowing()) {
                                    loader.dismiss();
                                }
                            }
                            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                        }

                    }

                    @Override
                    public void onFailure(Call<MemberListResponse> call, Throwable t) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        try {
                            if (t instanceof UnknownHostException || t instanceof IOException) {
                                ApiFintechUtilMethods.INSTANCE.NetworkError(context);
                                setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK);
                            } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
                                setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                            } else {
                                setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                    ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
                                }
                            }

                        } catch (IllegalStateException ise) {
                            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
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
                if (loader != null) {
                    if (loader.isShowing()) {
                        loader.dismiss();
                    }
                }
                setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                e.printStackTrace();
            }

        } else {
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
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
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
            if (filterFromDate.equalsIgnoreCase(filterToDate)) {
                errorMsg.setText("Record not found for " + filterFromDate);
            } else {
                errorMsg.setText("Record not found between\n" + filterFromDate + " to " + filterToDate);
            }
        }
    }


    public void dataParse(MemberListResponse transactions) {

        if (transactions != null && transactions.getData() != null && transactions.getData().size() > 0) {
            transactionsObjects.clear();
            transactionsObjects.addAll(transactions.getData());
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        } else {
            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
            ApiFintechUtilMethods.INSTANCE.Error(this, "Record not found between\n" + filterFromDate + " to " + filterToDate);
            recycler_view.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sort, menu);
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
            openFilter();
            return true;
        } else if (id == R.id.action_sort) {
            openSorting();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openFilter() {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);

        View sheetView = getLayoutInflater().inflate(R.layout.dialog_networking_directmember_filter, null);
        LinearLayout dateView = sheetView.findViewById(R.id.dateView);
        LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
        AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);
        startDate.setText(filterFromDate);
        LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
        AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);
        endDate.setText(filterToDate);

        LinearLayout sponsorIdView = sheetView.findViewById(R.id.sponsorIdView);
        EditText sponsorIdEt = sheetView.findViewById(R.id.sponsorIdEt);
        sponsorIdView.setVisibility(View.GONE);
        LinearLayout businessTypeView = sheetView.findViewById(R.id.businessTypeView);

        RelativeLayout businessTypeChooser = sheetView.findViewById(R.id.businessTypeChooser);
        TextView businessTypeLabel = sheetView.findViewById(R.id.businessTypeLabel);
        TextView businessTypeTv = sheetView.findViewById(R.id.businessTypeTv);
        if (balanceCheckResponse == 2) {
            businessTypeView.setVisibility(View.VISIBLE);
        } else {
            businessTypeView.setVisibility(View.GONE);
        }
        businessTypeLabel.setText("Leg");
        businessTypeTv.setHint("Select Leg");
        if (filterLeg.equalsIgnoreCase("L")) {
            businessTypeTv.setText("Left");
        } else if (filterLeg.equalsIgnoreCase("R")) {
            businessTypeTv.setText("Right");
        } else {
            businessTypeTv.setText("All");
        }

        LinearLayout levelNoView = sheetView.findViewById(R.id.levelNoView);
        RelativeLayout levelNoChooser = sheetView.findViewById(R.id.levelNoChooser);
        TextView levelNoTitle = sheetView.findViewById(R.id.levelNoTitle);
        AppCompatTextView levelNoTv = sheetView.findViewById(R.id.levelNoTv);
        levelNoView.setVisibility(View.VISIBLE);
        levelNoTitle.setText("Search Type");
        levelNoTv.setHint("Search Type");
        if (filterDateType == 1) {
            levelNoTv.setText(dateTypeArray[0]);
        } else {
            levelNoTv.setText(dateTypeArray[1]);
        }


        LinearLayout statusView = sheetView.findViewById(R.id.statusView);
        RelativeLayout statusChooserView = sheetView.findViewById(R.id.statusChooserView);
        final AppCompatTextView statusChooserTv = sheetView.findViewById(R.id.statusChooser);
        if (filterStaus.equalsIgnoreCase("0")) {
            statusChooserTv.setText("Deactive");
        } else if (filterStaus.equalsIgnoreCase("1")) {
            statusChooserTv.setText("Active");
        } else {
            statusChooserTv.setText("All");
        }
        LinearLayout dateFilterView = sheetView.findViewById(R.id.dateFilterView);
        RelativeLayout dateChooserView = sheetView.findViewById(R.id.dateChooserView);
        TextView dateTitleTv = sheetView.findViewById(R.id.dateTitleTv);
        AppCompatTextView dateChooser = sheetView.findViewById(R.id.dateChooser);
        dateChooserView.setVisibility(View.VISIBLE);
        if (filterDateFilter == 0) {
            dateChooser.setText(dateFilterArray[0]);
        } else if (filterDateFilter == 1){
            dateChooser.setText(dateFilterArray[1]);
        } else if (filterDateFilter == 2){
            dateChooser.setText(dateFilterArray[2]);
        } else {
            dateChooser.setText(dateFilterArray[3]);
        }
        Button filter = sheetView.findViewById(R.id.filter);
        dateChooserView.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (dateChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(dateFilterArray).indexOf(dateChooser.getText().toString());
            }
            mCustomFilterDialog.showSingleChoiceAlert(dateFilterArray, selectedIndex, "Choose Date Type", "Choose Any Date Type", new CustomFilterDialog.SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    dateChooser.setText(dateFilterArray[index]);

                    // Handle date filter selection here
                    String selectedFilter = dateFilterArray[index];
                    Calendar calendar = Calendar.getInstance();
                    if (selectedFilter.equals("All")) {
                        startDate.setText("");
                        endDate.setText("");
                    } else if (selectedFilter.equals("This Month")) {
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = 1; // Start from the first day of the month
                        calendar.set(year, month, day);
                        startDate.setText(ApiFintechUtilMethods.INSTANCE.formatDate(calendar.getTime()));
                        endDate.setText(ApiFintechUtilMethods.INSTANCE.formatDate(Calendar.getInstance().getTime())); // Set end date to current date

                    } else if (selectedFilter.equals("Last Month")) {
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH) - 1; // Previous month
                        int day = 1; // Start from the first day of the previous month
                        if (month < 0) {
                            month = 11; // Wrap around to December of the previous year
                            year--;
                        }
                        calendar.set(year, month, day);
                        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                        calendar.set(Calendar.DAY_OF_MONTH, lastDay);
                        endDate.setText(ApiFintechUtilMethods.INSTANCE.formatDate(calendar.getTime()));
                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                        startDate.setText(ApiFintechUtilMethods.INSTANCE.formatDate(calendar.getTime()));
                    } else if (selectedFilter.equals("Custom Date")) {
                        // Handle custom date range selection here, if needed
                        filterDateFilter = 3;
                       // showCustomDateRangePicker();
                    }
                }
                @Override
                public void onNegativeClick() {

                }
            });
        });
        levelNoChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (levelNoTv.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(dateTypeArray).indexOf(levelNoTv.getText().toString());
            }
            mCustomFilterDialog.showSingleChoiceAlert(dateTypeArray, selectedIndex, "Choose Date Type", "Choose Any Date Type", new CustomFilterDialog.SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    levelNoTv.setText(dateTypeArray[index]);

                }

                @Override
                public void onNegativeClick() {

                }
            });
        });

        businessTypeChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (businessTypeTv.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(legArray).indexOf(businessTypeTv.getText().toString());
            }
         /*   mCustomFilterDialog.showSingleChoiceAlert(legArray, selectedIndex, "Choose Leg", "Choose Any Leg", new CustomFilterDialog.SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    businessTypeTv.setText(legArray[index]);

                }

                @Override
                public void onNegativeClick() {

                }
            });*/

        });
        statusChooserView.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (statusChooserTv.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(statusArray).indexOf(statusChooserTv.getText().toString());
            }
            mCustomFilterDialog.showSingleChoiceAlert(statusArray, selectedIndex, "Choose Status", "Choose Any Status", new CustomFilterDialog.SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    statusChooserTv.setText(statusArray[index]);

                }

                @Override
                public void onNegativeClick() {

                }
            });
        });

        startDateView.setOnClickListener(v -> mCustomFilterDialog.setDCFromDate(startDate, endDate));
        endDateView.setOnClickListener(v -> mCustomFilterDialog.setDCToDate(startDate, endDate));


        filter.setOnClickListener(v -> {
            filterFromDate = startDate.getText().toString();
            filterToDate = endDate.getText().toString();
            if (statusChooserTv.getText().toString().equalsIgnoreCase("All")) {
                filterStaus = "All";
            } else if (statusChooserTv.getText().toString().equalsIgnoreCase("Active")) {
                filterStaus = "1";
            } else {
                filterStaus = "0";
            }
            if (businessTypeTv.getText().toString().equalsIgnoreCase("All")) {
                filterLeg = "All";
            } else if (businessTypeTv.getText().toString().equalsIgnoreCase("Left")) {
                filterLeg = "L";
            } else {
                filterLeg = "R";
            }
            if (dateChooser.getText().toString().equalsIgnoreCase("All")) {
                filterDateFilter = 0;
                filterFromDate = ""; // Empty fromDate for "All" option
                filterToDate = "";   // Empty toDate for "All" option
            } else if (dateChooser.getText().toString().equalsIgnoreCase("This Month")) {
                filterDateFilter = 1;
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = 1; // Start from the first day of the month
                calendar.set(year, month, day);
                filterFromDate = ApiFintechUtilMethods.INSTANCE.formatDate(calendar.getTime());
                filterToDate = ApiFintechUtilMethods.INSTANCE.formatDate(Calendar.getInstance().getTime());
            } else if (dateChooser.getText().toString().equalsIgnoreCase("Last Month")) {
                filterDateFilter = 2;
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) - 1; // Previous month
                int day = 1; // Start from the first day of the previous month
                if (month < 0) {
                    month = 11; // Wrap around to December of the previous year
                    year--;
                }
                calendar.set(year, month, day);
                int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar.set(Calendar.DAY_OF_MONTH, lastDay);
                filterToDate = ApiFintechUtilMethods.INSTANCE.formatDate(calendar.getTime());
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                filterFromDate = ApiFintechUtilMethods.INSTANCE.formatDate(calendar.getTime());
            }


            if (levelNoTv.getText().toString().equalsIgnoreCase(dateTypeArray[0])) {
                filterDateType = 1;
            } else {
                filterDateType = 2;
            }
            mBottomSheetDialog.dismiss();
            HitApi(this, filterFromDate, filterToDate,false);
        });
        mBottomSheetDialog.setContentView(sheetView);

        BottomSheetBehavior
                .from(mBottomSheetDialog.findViewById(R.id.design_bottom_sheet))
                .setState(BottomSheetBehavior.STATE_EXPANDED);

        mBottomSheetDialog.show();

    }

    public void openSorting(){
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_networking_directmember_short, null);
        LinearLayout typeView = sheetView.findViewById(R.id.typeView);
        RelativeLayout typeChooserView = sheetView.findViewById(R.id.typeChooserView);
        TextView typeTitleTv = sheetView.findViewById(R.id.typeTitleTv);
        AppCompatTextView typeChooser = sheetView.findViewById(R.id.typeChooser);
        typeChooserView.setVisibility(View.VISIBLE);
        if (filterDateFilter == 0) {
            typeChooser.setText(typeSortArray[0]);
        } else if (filterDateFilter == 1){
            typeChooser.setText(typeSortArray[1]);
        } else if (filterDateFilter == 2){
            typeChooser.setText(typeSortArray[2]);
        } else if (filterDateFilter == 3){
            typeChooser.setText(typeSortArray[3]);
        } else if (filterDateFilter == 4){
            typeChooser.setText(typeSortArray[4]);
        } else if (filterDateFilter == 5){
            typeChooser.setText(typeSortArray[5]);
        } else if (filterDateFilter == 6){
            typeChooser.setText(typeSortArray[6]);
        } else if (filterDateFilter == 7){
            typeChooser.setText(typeSortArray[7]);
        }
        Button sort = sheetView.findViewById(R.id.sort);
        typeChooserView.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (typeChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(typeSortArray).indexOf(typeChooser.getText().toString());
            }
            mCustomFilterDialog.showSingleChoiceAlert(typeSortArray, selectedIndex, "Choose Type", "Choose Any Type", new CustomFilterDialog.SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    typeChooser.setText(typeSortArray[index]);

                    // Handle date filter selection here
                    String selectedFilter = typeSortArray[index];
                    Calendar calendar = Calendar.getInstance();

                }
                @Override
                public void onNegativeClick() {

                }
            });
        });

        sort.setOnClickListener(v -> {
            if (typeChooser.getText().toString().equalsIgnoreCase("Team Business")) {
                transactionsObjects.clear();
                transactionsObjects.addAll(memberListResponse.getData());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // Sort Items by double value using Double.compare
                    Collections.sort(transactionsObjects, new Comparator<MemberListData>() {
                        @Override
                        public int compare(MemberListData o1, MemberListData o2) {
                            return Double.compare(o2.getTeamBusiness(), o1.getTeamBusiness());
                        }
                    });
                }

                recycler_view.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
// Empty toDate for "All" option
            } else if (typeChooser.getText().toString().equalsIgnoreCase("Self Business")) {
                transactionsObjects.clear();
                transactionsObjects.addAll(memberListResponse.getData());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // Sort Items by double value using Double.compare
                    Collections.sort(transactionsObjects, new Comparator<MemberListData>() {
                        @Override
                        public int compare(MemberListData o1, MemberListData o2) {
                            return Double.compare(o2.getSelfBusiness(), o1.getSelfBusiness());
                        }
                    });
                }
                recycler_view.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();// Empty toDate for "All" option
            }else if (typeChooser.getText().toString().equalsIgnoreCase("Direct Business")) {
                transactionsObjects.clear();
                transactionsObjects.addAll(memberListResponse.getData());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // Sort Items by double value using Double.compare
                    Collections.sort(transactionsObjects, new Comparator<MemberListData>() {
                        @Override
                        public int compare(MemberListData o1, MemberListData o2) {
                            return Double.compare(o2.getDirectBusiness(), o1.getDirectBusiness());
                        }
                    });
                }
                recycler_view.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();
            }else if (typeChooser.getText().toString().equalsIgnoreCase("Direct Team")) {
                transactionsObjects.clear();
                transactionsObjects.addAll(memberListResponse.getData());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // Sort Items by double value using Double.compare
                    Collections.sort(transactionsObjects, new Comparator<MemberListData>() {
                        @Override
                        public int compare(MemberListData o1, MemberListData o2) {
                            return Double.compare(o2.getTotalDirectCount(), o1.getTotalDirectCount());
                        }
                    });
                }
                recycler_view.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();// Empty toDate for "All" option
            }else if (typeChooser.getText().toString().equalsIgnoreCase("Active Team")) {
                transactionsObjects.clear();
                transactionsObjects.addAll(memberListResponse.getData());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // Sort Items by double value using Double.compare
                    Collections.sort(transactionsObjects, new Comparator<MemberListData>() {
                        @Override
                        public int compare(MemberListData o1, MemberListData o2) {
                            return Double.compare(o2.getActiveDirect(), o1.getActiveDirect());
                        }
                    });
                }
                recycler_view.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();// Empty toDate for "All" option
            }else if (typeChooser.getText().toString().equalsIgnoreCase("Deactive Team")) {
                transactionsObjects.clear();
                transactionsObjects.addAll(memberListResponse.getData());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // Sort Items by double value using Double.compare
                    Collections.sort(transactionsObjects, new Comparator<MemberListData>() {
                        @Override
                        public int compare(MemberListData o1, MemberListData o2) {
                            return Double.compare(o2.getDeActiveDirect(), o1.getDeActiveDirect());
                        }
                    });
                }
                recycler_view.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();// Empty toDate for "All" option
            }else if (typeChooser.getText().toString().equalsIgnoreCase("Register Date")) {
                transactionsObjects.clear();
                transactionsObjects.addAll(memberListResponse.getData());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // Define the date format based on the response
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm a", Locale.ENGLISH);

                    // Sort items by Activation Date
                    Collections.sort(transactionsObjects, new Comparator<MemberListData>() {
                        @Override
                        public int compare(MemberListData o1, MemberListData o2) {
                            try {
                                Date date1 = dateFormat.parse(o1.getRegDate());
                                Date date2 = dateFormat.parse(o2.getRegDate());

                                // Compare dates as long timestamps
                                return Long.compare(date2.getTime(), date1.getTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return 0; // Keep the order unchanged if parsing fails
                            }
                        }
                    });
                }

                recycler_view.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
// Empty toDate for "All" option
            }else if (typeChooser.getText().toString().equalsIgnoreCase("Activate Date")) {
                transactionsObjects.clear();
                transactionsObjects.addAll(memberListResponse.getData());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // Define the date format based on the response
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm a", Locale.ENGLISH);
                    // Sort items by Activation Date
                    Collections.sort(transactionsObjects, new Comparator<MemberListData>() {
                        @Override
                        public int compare(MemberListData o1, MemberListData o2) {
                            try {
                                Date date1 = dateFormat.parse(o1.getActivationDate());
                                Date date2 = dateFormat.parse(o2.getActivationDate());

                                // Compare dates as long timestamps
                                return Long.compare(date2.getTime(), date1.getTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return 0; // Keep the order unchanged if parsing fails
                            }
                        }
                    });
                }

                recycler_view.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();

// Empty toDate for "All" option
            }

            mBottomSheetDialog.dismiss();
//            HitApi(this, filterFromDate, filterToDate,false);
        });
        mBottomSheetDialog.setContentView(sheetView);

        BottomSheetBehavior
                .from(mBottomSheetDialog.findViewById(R.id.design_bottom_sheet))
                .setState(BottomSheetBehavior.STATE_EXPANDED);

        mBottomSheetDialog.show();

    }
/*    private void showCustomDateRangePicker() {
        DatePickerDialog.OnDateSetListener fromDateListener = (view, year, monthOfYear, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            filterFromDate = ApiFintechUtilMethods.INSTANCE.formatDate(calendar.getTime());

            DatePickerDialog.OnDateSetListener toDateListener = (view1, year1, monthOfYear1, dayOfMonth1) -> {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(year1, monthOfYear1, dayOfMonth1);
                filterToDate = ApiFintechUtilMethods.INSTANCE.formatDate(calendar1.getTime());

                // Call the API with the custom date range
              //  HitApi(this);
            };

            // Show the "To" date picker dialog
            showDatePickerDialog(this, toDateListener, calendar);
        };

        // Show the "From" date picker dialog
        showDatePickerDialog(this, fromDateListener, Calendar.getInstance());
    }

    private void showDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener listener, Calendar calendar) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Show the date picker dialog
        datePickerDialog.show();
    }*/



}
