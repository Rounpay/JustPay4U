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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.solution.app.justpay4u.Api.Fintech.Object.LedgerObject;
import com.solution.app.justpay4u.Api.Fintech.Response.FosAccStmtAndCollReportResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.Fintech.AppUser.Adapter.AccountStatementReportAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class AccountStatementServiceWiseActivity extends AppCompatActivity {


    RecyclerView recycler_view;
    AccountStatementReportAdapter fosCollectionReportAdapter;
    ArrayList<LedgerObject> transactionsObjects = new ArrayList<>();
    EditText search_all;
    CustomLoader loader;
    View noDataView, noNetworkView, retryBtn, tabView;
    TextView errorMsg, allTab, saleTab, collectionTab;
    int areaId;
    private String filterFromDate = "", filterToDate = "";
    private int filterTopValue = 50;
    private LoginResponse loginPrefResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    private String[] ledgerChooseTopArray = {"50", "100", "200", "500", "1000", "1500", "ALL"};
    private CustomFilterDialog mCustomFilterDialog;
    private String filterArea = "";
    private TextView refundStatusChooser;
    private String filterMobile = "";
    private int serviceId;
    private FosAccStmtAndCollReportResponse fosAccStmtAndCollReportResponse;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_account_statement_service_wise_report);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            loginPrefResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);


            findViews();
            clickView();

            new Handler(Looper.getMainLooper()).post(() -> {
                mCustomFilterDialog = new CustomFilterDialog(this);
                serviceId = getIntent().getIntExtra("ServiceId", 0);//0=all, 4= sale, 44= Collection
                filterMobile = getIntent().getStringExtra("Mobile");
                filterFromDate = getIntent().getStringExtra("FilterFromDate");
                filterToDate = getIntent().getStringExtra("FilterToDate");
                areaId = getIntent().getIntExtra("areaID", 0);

                if (filterToDate == null || filterFromDate == null) {
                    filterFromDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(Calendar.getInstance().getTime());
                    filterToDate = filterFromDate;
                }

                HitApi();
            });
        });


    }


    void findViews() {
        search_all = findViewById(R.id.search_all);
        tabView = findViewById(R.id.tabView);
        allTab = findViewById(R.id.allTab);
        saleTab = findViewById(R.id.saleTab);
        collectionTab = findViewById(R.id.collectionTab);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Record not found");
        recycler_view = findViewById(R.id.recycler_view);
        fosCollectionReportAdapter = new AccountStatementReportAdapter(transactionsObjects, this);

        recycler_view.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recycler_view.setAdapter(fosCollectionReportAdapter);


        if (serviceId != 0) {
            tabView.setVisibility(View.GONE);
        }
    }


    void clickView() {
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));
        allTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (serviceId != 0) {
                    allTab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    saleTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    collectionTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    serviceId = 0;
                    if (fosAccStmtAndCollReportResponse != null) {
                        dataParse(fosAccStmtAndCollReportResponse);
                    } else {
                        HitApi();
                    }
                }
            }
        });
        saleTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (serviceId != 4) {
                    saleTab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    allTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    collectionTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    serviceId = 4;
                    if (fosAccStmtAndCollReportResponse != null) {
                        dataParse(fosAccStmtAndCollReportResponse);
                    } else {
                        HitApi();
                    }
                }
            }
        });
        collectionTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (serviceId != 44) {
                    collectionTab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    saleTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    allTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    serviceId = 44;
                    if (fosAccStmtAndCollReportResponse != null) {
                        dataParse(fosAccStmtAndCollReportResponse);
                    } else {
                        HitApi();
                    }
                }
            }
        });

        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                if (fosCollectionReportAdapter != null) {
                    fosCollectionReportAdapter.getFilter().filter(newText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HitApi();
            }
        });
    }

    public void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            if (!loader.isShowing()) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }

            ApiFintechUtilMethods.INSTANCE.AccStmtReport(this, filterMobile, filterTopValue + "", filterFromDate, filterToDate,
                    "0", loader, loginPrefResponse, deviceId, deviceSerialNum
                    , areaId, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            fosAccStmtAndCollReportResponse = (FosAccStmtAndCollReportResponse) object;
                            dataParse(fosAccStmtAndCollReportResponse);
                        }

                        @Override
                        public void onError(int error) {
                            recycler_view.setVisibility(View.GONE);
                            transactionsObjects.clear();
                            fosCollectionReportAdapter.notifyDataSetChanged();

                            if (error == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
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


                    });
        } else {
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.VISIBLE);
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }


    public void dataParse(FosAccStmtAndCollReportResponse transactions) {

        if (transactions != null && transactions.getAccountStatementSummary() != null) {
            transactionsObjects.clear();
            if (serviceId != 0) {
                for (LedgerObject item : transactions.getAccountStatementSummary()) {
                    if (item.getServiceID() == serviceId) {
                        transactionsObjects.add(item);
                    }
                }
            } else {
                transactionsObjects.addAll(transactions.getAccountStatementSummary());
            }
        }
        if (transactionsObjects.size() > 0 && transactionsObjects != null) {
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            fosCollectionReportAdapter.notifyDataSetChanged();
        } else {
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
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

            openReportFosFilter();

        }


        return super.onOptionsItemSelected(item);
    }

    public void openReportFosFilter() {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);

        View sheetView = getLayoutInflater().inflate(R.layout.dialog_ledger_filter, null);
        LinearLayout childMobileView = sheetView.findViewById(R.id.childMobileView);
        childMobileView.setVisibility(View.GONE);

        LinearLayout mobileView = sheetView.findViewById(R.id.mobileView);
        mobileView.setVisibility(View.GONE);

        EditText mobileNoEt = sheetView.findViewById(R.id.mobileNoEt);
        mobileNoEt.setText(filterMobile + "");
        LinearLayout accountNoView = sheetView.findViewById(R.id.accountNoView);
        accountNoView.setVisibility(View.GONE);

        LinearLayout transactionIdView = sheetView.findViewById(R.id.transactionIdView);
        transactionIdView.setVisibility(View.GONE);

        LinearLayout walletTypeView = sheetView.findViewById(R.id.walletTypeView);
        walletTypeView.setVisibility(View.GONE);


        LinearLayout dateTypeChooserView = sheetView.findViewById(R.id.dateTypeChooserView);
        dateTypeChooserView.setVisibility(View.GONE);

        LinearLayout modeChooserView = sheetView.findViewById(R.id.modeChooserView);
        modeChooserView.setVisibility(View.GONE);

        LinearLayout criteriaView = sheetView.findViewById(R.id.criteriaView);
        criteriaView.setVisibility(View.GONE);

        LinearLayout criteriaChooserView = sheetView.findViewById(R.id.criteriaChooserView);
        criteriaChooserView.setVisibility(View.GONE);


        LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
        final AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);
        startDate.setText(filterFromDate);
        LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);
        endDate.setText(filterToDate);


        LinearLayout topChooserView = sheetView.findViewById(R.id.topChooserView);
        final AppCompatTextView topChooser = sheetView.findViewById(R.id.topChooser);
        topChooser.setText(filterTopValue + "");


        Button filter = sheetView.findViewById(R.id.filter);

        LinearLayout refundStatusChooserView = sheetView.findViewById(R.id.refundStatusChooserView);
        TextView statusTitleTv = sheetView.findViewById(R.id.statusTitleTv);
        refundStatusChooser = sheetView.findViewById(R.id.refundStatusChooser);


       /* statusTitleTv.setText("Select Area");
        refundStatusChooser.setHint("Select Area");*/
        refundStatusChooserView.setVisibility(View.GONE);
       /* refundStatusChooser.setText(filterArea + "");
        refundStatusChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountStatementReportActivity.this, com.solution.app.moneycenter.AppUser.Activity.FosAreaReportActivity.class);
                startActivityForResult(intent, INTENT_SELECT_AREA);
            }
        });*/
        topChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = 0;
                if (topChooser.getText().toString().length() == 0) {
                    selectedIndex = 0;
                } else {
                    selectedIndex = Arrays.asList(ledgerChooseTopArray).indexOf(topChooser.getText().toString());
                }
                mCustomFilterDialog.showSingleChoiceAlert(ledgerChooseTopArray, selectedIndex, "Select Top", "Choose Top", new CustomFilterDialog.SingleChoiceDialogCallBack() {
                    @Override
                    public void onPositiveClick(int index) {
                        topChooser.setText(ledgerChooseTopArray[index]);
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });
        startDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomFilterDialog.setDCFromDate(startDate, endDate);
            }
        });
        endDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomFilterDialog.setDCToDate(startDate, endDate);
            }
        });


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                filterFromDate = startDate.getText().toString();
                filterToDate = endDate.getText().toString();
                filterMobile = mobileNoEt.getText().toString();
                filterTopValue = topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString());
                HitApi();

            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4231 && resultCode == RESULT_OK) {
            areaId = data.getIntExtra("areaID", 0);
            filterArea = data.getStringExtra("area");

            if (refundStatusChooser != null) {
                refundStatusChooser.setText(filterArea + "");
            }
        }
    }
}
