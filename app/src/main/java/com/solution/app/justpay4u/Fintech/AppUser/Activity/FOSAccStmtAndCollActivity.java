package com.solution.app.justpay4u.Fintech.AppUser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.AreaMaster;
import com.solution.app.justpay4u.Api.Fintech.Object.AscReport;
import com.solution.app.justpay4u.Api.Fintech.Response.FosAccStmtAndCollReportResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.Fintech.AppUser.Adapter.FOSAccSmtReportAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class FOSAccStmtAndCollActivity extends AppCompatActivity {
    RecyclerView recycler_view;
    FOSAccSmtReportAdapter fosAccSmtReportAdapter;
    ArrayList<AscReport> transactionsObjects = new ArrayList<>();
    EditText search_all;
    CustomLoader loader;
    AreaMaster areaMaster;
    int areaId;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    private CustomFilterDialog mCustomFilterDialog;
    private String filterFromDate = "", filterToDate = "";
    private int filterTopValue = 50;
    private LoginResponse loginPrefResponse;
    private int INTENT_COLLECTION = 2341;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_fos_acc_smt_and_coll);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            loginPrefResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            findViews();
            clickView();
            new Handler(Looper.getMainLooper()).post(() -> {
                mCustomFilterDialog = new CustomFilterDialog(this);
                filterFromDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(Calendar.getInstance().getTime());
                filterToDate = filterFromDate;


                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    areaId = extras.getInt("areaID");
                }

                HitApi();
            });
        });


    }


    void findViews() {
        search_all = findViewById(R.id.search_all);

        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Record not found");
        recycler_view = findViewById(R.id.recycler_view);
        fosAccSmtReportAdapter = new FOSAccSmtReportAdapter(transactionsObjects,
                FOSAccStmtAndCollActivity.this, loader,
                loginPrefResponse, deviceId, deviceSerialNum, mAppPreferences, () -> HitApi());

        recycler_view.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recycler_view.setAdapter(fosAccSmtReportAdapter);

    }


    void clickView() {
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));


        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                if (fosAccSmtReportAdapter != null) {
                    fosAccSmtReportAdapter.getFilter().filter(newText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        retryBtn.setOnClickListener(view -> HitApi());
    }

    private void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            if (!loader.isShowing()) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }

            areaMaster = new AreaMaster();


            ApiFintechUtilMethods.INSTANCE.AccStmtAndCollFilterFosClick(this, filterTopValue + "", filterFromDate,
                    filterToDate, "1", loader, loginPrefResponse, deviceId, deviceSerialNum
                    , areaId, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            FosAccStmtAndCollReportResponse fosAccStmtAndCollReportResponse = (FosAccStmtAndCollReportResponse) object;
                            dataParse(fosAccStmtAndCollReportResponse);
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


    public void dataParse(FosAccStmtAndCollReportResponse transactions) {

        if (transactions != null && transactions.getAscReport() != null) {
            transactionsObjects.clear();
            transactionsObjects.addAll(transactions.getAscReport());
        }
        if (transactionsObjects.size() > 0 && transactionsObjects != null) {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            fosAccSmtReportAdapter.notifyDataSetChanged();
        } else {
            setInfoHideShow(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
            ApiFintechUtilMethods.INSTANCE.Error(this, "Record not found between\n" + filterFromDate + " to " + filterToDate);
            recycler_view.setVisibility(View.GONE);
        }
    }

    void setInfoHideShow(int errorType) {
        recycler_view.setVisibility(View.GONE);
        transactionsObjects.clear();
        fosAccSmtReportAdapter.notifyDataSetChanged();

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


            mCustomFilterDialog.openReportFosFilter(filterFromDate, filterToDate, filterTopValue,
                    (fromDate, toDate, topValue) -> {
                        filterFromDate = fromDate;
                        filterToDate = toDate;

                        filterTopValue = topValue;
                        HitApi();
                    });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_COLLECTION && resultCode == RESULT_OK) {
            HitApi();
        }
    }

    public void FosCollectionActivity(AscReport operator) {
        Intent i = new Intent(this, FosCollectionActivity.class);
        i.putExtra("userID", operator.getUserID() + "");
        i.putExtra("outletName", operator.getOutletName() + "");
        i.putExtra("mobile", operator.getMobile() + "");
        startActivityForResult(i, INTENT_COLLECTION);
    }
}
