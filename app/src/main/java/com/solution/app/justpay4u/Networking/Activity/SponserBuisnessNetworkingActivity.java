package com.solution.app.justpay4u.Networking.Activity;

import android.app.Activity;
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
import com.solution.app.justpay4u.Api.Networking.Object.GetTopupDetailsByUserIdData;
import com.solution.app.justpay4u.Api.Networking.Object.MemberListData;
import com.solution.app.justpay4u.Api.Networking.Request.FindUserDetailsByIdRequest;
import com.solution.app.justpay4u.Api.Networking.Response.FindUserDetailsByIdResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApiNetworkingUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.MemberListRequest;
import com.solution.app.justpay4u.ApiHits.MemberListResponse;
import com.solution.app.justpay4u.ApiHits.NetworkingEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Networking.Adapter.SponserBusinessNetworkingAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class SponserBuisnessNetworkingActivity extends AppCompatActivity {


    private String[] statusArray = {"All", "Active", "Deactive"};
    private String[] levelNoArray;
    private String[] businessTypeArray;


    HashMap<String, GetTopupDetailsByUserIdData> businessTypeMap = new HashMap<>();
    RecyclerView recycler_view;
    SponserBusinessNetworkingAdapter mAdapter;
    ArrayList<MemberListData> transactionsObjects = new ArrayList<>();
    EditText search_all;
    CustomLoader loader;

    public View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterToDate = "", filterStaus = "All";
    private int filterLevelNo = 1;
    private LoginResponse loginPrefResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    ArrayList<MemberListData> levelNoList = new ArrayList<>();
    private String filterSponsorId = "";
    private String filterBuisnessType = "All";
    private int filterBuisnessTypeId;
    private CustomAlertDialog mCustomAlertDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_networking_sponser_buisness);


            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            loginPrefResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            mCustomAlertDialog = new CustomAlertDialog(this);
            findViews();
            clickView();


            new Handler(Looper.getMainLooper()).post(() -> {
                mCustomFilterDialog = new CustomFilterDialog(this);
                filterFromDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(new Date());
                filterToDate = filterFromDate;

                getLevel();
                getBusinessTypeApi();
                HitApi(this,true);
            });
        });

    }


    void findViews() {
        search_all = findViewById(R.id.search_all);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);


        recycler_view = findViewById(R.id.recycler_view);
        mAdapter = new SponserBusinessNetworkingAdapter(transactionsObjects, SponserBuisnessNetworkingActivity.this);
        recycler_view.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recycler_view.setAdapter(mAdapter);

    }


    void clickView() {
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));

        retryBtn.setOnClickListener(v -> {
            getLevel();
            getBusinessTypeApi();
            HitApi(this,true);
        });

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

    private void HitApi(Activity context,boolean IsRecent) {
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
                    Call<MemberListResponse> call = git.SponserBusiness(new MemberListRequest(new BasicRequest(loginPrefResponse.getData().getUserID(),
                            loginPrefResponse.getData().getLoginTypeID(),
                            ApplicationConstant.INSTANCE.APP_ID, deviceId,
                            "", BuildConfig.VERSION_NAME, deviceSerialNum, loginPrefResponse.getData().getSessionID(),
                            loginPrefResponse.getData().getSession()), new MemberListRequest(filterSponsorId.isEmpty() ? "0" : filterSponsorId, filterBuisnessTypeId + "",
                            filterLevelNo, filterFromDate, filterToDate, IsRecent)));

                    call.enqueue(new Callback<MemberListResponse>() {
                        @Override
                        public void onResponse(Call<MemberListResponse> call, final retrofit2.Response<MemberListResponse> response) {

                            try {

                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        if (response.body().getStatuscode() == 1) {

                                            dataParse(response.body());
                                            if (loader != null) {
                                                if (loader.isShowing()) {
                                                    loader.dismiss();
                                                }
                                            }
                                        } else {
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
                    setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                    e.printStackTrace();
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
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
    }

    private void getLevel() {
        if (ApiNetworkingUtilMethods.INSTANCE.isVpnConnected(this)) {
            mCustomAlertDialog.ErrorVPN(this);
        } else {
            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {


                ApiNetworkingUtilMethods.INSTANCE.getLevel(this, deviceId, deviceSerialNum, loginPrefResponse, new ApiNetworkingUtilMethods.ApiGetLevelCallBack() {
                    @Override
                    public void onSuccess(MemberListResponse mResponse) {
                        if (mResponse != null && mResponse.getData() != null && mResponse.getData().size() > 0) {
                            levelNoList = mResponse.getData();
                            levelNoArray = new String[levelNoList.size()];
                            for (int i = 0; i < levelNoList.size(); i++) {
                                levelNoArray[i] = levelNoList.get(i).getLevelNo() + "";
                            }
                        }
                    }
                });
            } else {

            }
        }
    }

    void getBusinessTypeApi() {
        try {

            if (ApiNetworkingUtilMethods.INSTANCE.isVpnConnected(this)) {
                mCustomAlertDialog.ErrorVPN(this);
            } else {
                NetworkingEndPointInterface git = ApiClient.getClient().create(NetworkingEndPointInterface.class);


                Call<FindUserDetailsByIdResponse> call = git.GetOPIDListByUserId(new FindUserDetailsByIdRequest(new BasicRequest(
                        loginPrefResponse.getData().getUserID(), loginPrefResponse.getData().getLoginTypeID(),
                        ApplicationConstant.INSTANCE.APP_ID,
                        deviceId,
                        "", BuildConfig.VERSION_NAME, deviceSerialNum,
                        loginPrefResponse.getData().getSessionID(), loginPrefResponse.getData().getSession()), "0"));

                call.enqueue(new Callback<FindUserDetailsByIdResponse>() {
                    @Override
                    public void onResponse(Call<FindUserDetailsByIdResponse> call, final retrofit2.Response<FindUserDetailsByIdResponse> response) {
                        if (response != null) {
                            if (response.isSuccessful()) {

                                try {
                                    if (response.body() != null) {

                                        FindUserDetailsByIdResponse data = response.body();
                                        if (data.getStatuscode() == 1) {


                                            if (data.getData() != null && data.getData().size() > 0) {

                                                ArrayList<GetTopupDetailsByUserIdData> mBuisnessTypeList = data.getData();
                                                if (mBuisnessTypeList.size() > 0) {
                                                    businessTypeArray = new String[mBuisnessTypeList.size() + 1];
                                                    businessTypeMap = new HashMap<>();
                                                    /*filterBuisnessTypeId = mBuisnessTypeList.get(0).getOid();
                                                    filterBuisnessType = mBuisnessTypeList.get(0).getName();*/
                                                    businessTypeArray[0] = "All";
                                                    businessTypeMap.put("All", null);

                                                    for (int i = 0; i < mBuisnessTypeList.size(); i++) {

                                                        businessTypeArray[i + 1] = mBuisnessTypeList.get(i).getName();
                                                        businessTypeMap.put(mBuisnessTypeList.get(i).getName(), mBuisnessTypeList.get(i));
                                                    }
                                                }
                                            }


                                        }

                                    }
                                } catch (Exception ex) {

                                }
                            } else {
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<FindUserDetailsByIdResponse> call, Throwable t) {

                       /* try {


                            ApiNetworkingUtilMethods.INSTANCE.apiFailureError(ActivateUserByEpinNetworkingActivity.this, t);
                        } catch (IllegalStateException ise) {

                            ApiNetworkingUtilMethods.INSTANCE.Error(ActivateUserByEpinNetworkingActivity.this, ise.getMessage() + "");


                        }*/
                    }
                });
            }
        } catch (Exception ex) {
           /*
            searchIv.setVisibility(View.VISIBLE);
            typeView.setVisibility(View.GONE);

            receiverDetailView.setVisibility(View.GONE);
            ApiNetworkingUtilMethods.INSTANCE.Error(ActivateUserByEpinNetworkingActivity.this, ex.getMessage() + "");
*/
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
        }
        return super.onOptionsItemSelected(item);
    }

    public void openFilter() {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);

        View sheetView = getLayoutInflater().inflate(R.layout.dialog_networking_member_filter, null);
        LinearLayout dateView = sheetView.findViewById(R.id.dateView);
        LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
        AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);
        startDate.setText(filterFromDate);
        LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
        AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);
        endDate.setText(filterToDate);
        LinearLayout sponsorIdView = sheetView.findViewById(R.id.sponsorIdView);
        EditText sponsorIdEt = sheetView.findViewById(R.id.sponsorIdEt);
        sponsorIdEt.setText(filterSponsorId + "");
        LinearLayout businessTypeView = sheetView.findViewById(R.id.businessTypeView);
        RelativeLayout businessTypeChooser = sheetView.findViewById(R.id.businessTypeChooser);
        TextView businessTypeTv = sheetView.findViewById(R.id.businessTypeTv);
        businessTypeTv.setText(filterBuisnessType + "");
        LinearLayout levelNoView = sheetView.findViewById(R.id.levelNoView);
        TextView levelNoTitle = sheetView.findViewById(R.id.levelNoTitle);
        RelativeLayout levelNoChooser = sheetView.findViewById(R.id.levelNoChooser);
        AppCompatTextView levelNoTv = sheetView.findViewById(R.id.levelNoTv);
        levelNoTv.setText(filterLevelNo + "");

        LinearLayout statusView = sheetView.findViewById(R.id.statusView);
        statusView.setVisibility(View.GONE);
        RelativeLayout statusChooserView = sheetView.findViewById(R.id.statusChooserView);
        final AppCompatTextView statusChooserTv = sheetView.findViewById(R.id.statusChooser);
        if (filterStaus.equalsIgnoreCase("0")) {
            statusChooserTv.setText("Deactive");
        } else if (filterStaus.equalsIgnoreCase("1")) {
            statusChooserTv.setText("Active");
        } else {
            statusChooserTv.setText("All");
        }


        Button filter = sheetView.findViewById(R.id.filter);


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

        businessTypeChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (businessTypeTv.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(businessTypeArray).indexOf(businessTypeTv.getText().toString());
            }
            mCustomFilterDialog.showSingleChoiceAlert(businessTypeArray, selectedIndex, "Choose Business Type", "Choose Any Business Type", new CustomFilterDialog.SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    businessTypeTv.setText(businessTypeArray[index]);


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
                selectedIndex = Arrays.asList(levelNoArray).indexOf(levelNoTv.getText().toString());
            }
            mCustomFilterDialog.showSingleChoiceAlert(levelNoArray, selectedIndex, "Choose Level No", "Choose Any Level No", new CustomFilterDialog.SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    levelNoTv.setText(levelNoArray[index]);

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
            try {
                filterLevelNo = Integer.parseInt(levelNoTv.getText().toString());
            } catch (NumberFormatException nfe) {

            }
            if (statusChooserTv.getText().toString().equalsIgnoreCase("All")) {
                filterStaus = "All";
            } else if (statusChooserTv.getText().toString().equalsIgnoreCase("Active")) {
                filterStaus = "1";
            } else {
                filterStaus = "0";
            }

            filterBuisnessType = businessTypeTv.getText().toString();
            if (businessTypeMap != null && businessTypeMap.containsKey(filterBuisnessType)) {
                if (businessTypeMap.get(filterBuisnessType) == null) {
                    filterBuisnessTypeId = 0;
                } else {
                    filterBuisnessTypeId = businessTypeMap.get(filterBuisnessType).getOid();
                }
            }
            filterSponsorId = sponsorIdEt.getText().toString();

            mBottomSheetDialog.dismiss();
            HitApi(this,false);
        });
        mBottomSheetDialog.setContentView(sheetView);

        BottomSheetBehavior
                .from(mBottomSheetDialog.findViewById(R.id.design_bottom_sheet))
                .setState(BottomSheetBehavior.STATE_EXPANDED);

        mBottomSheetDialog.show();

    }


}
