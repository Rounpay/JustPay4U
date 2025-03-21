package com.solution.app.justpay4u.Fintech.Employee.Activity;

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

import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.Fintech.Employee.Adapter.EmployeeAdapter;
import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetEmployees;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetEmployeesResponse;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;


public class EmployeesListReport extends AppCompatActivity {
    RecyclerView recycler_view;
    EmployeeAdapter mAdapter;

    ArrayList<GetEmployees> transactionsObjects = new ArrayList<>();
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    CustomLoader loader;
    EditText search_all;
    private CustomFilterDialog mCustomFilterDialog;
    private int filterChooseCriteriaId;
    private String filterChooseCriteria;
    private String filterEnterCriteria;
    private int filterTopValue = 50, filterRoleId;
    private String filterRoleValue;
    private LoginResponse mLoginDataResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_employees_list_report);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            mCustomFilterDialog = new CustomFilterDialog(this);


            findViews();

            retryBtn.setOnClickListener(v -> HitApi(true));
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

            HitApi(true);
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
        mAdapter = new EmployeeAdapter(transactionsObjects, this);
        recycler_view.setAdapter(mAdapter);
    }

    private void HitApi(boolean isLoaderShow) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {

            if (isLoaderShow && !loader.isShowing()) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }
            ApiFintechUtilMethods.INSTANCE.GetEmployees(this, filterEnterCriteria, filterChooseCriteriaId, filterRoleId, false, true, 0,
                    filterTopValue,
                    mLoginDataResponse, deviceId, deviceSerialNum, loader, new ApiFintechUtilMethods.ApiResponseCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            transactionsObjects.clear();
                            GetEmployeesResponse mGetEmployeesResponse = (GetEmployeesResponse) object;
                            if (mGetEmployeesResponse != null && mGetEmployeesResponse.getData() != null && mGetEmployeesResponse.getData().size() > 0) {
                                transactionsObjects.addAll(mGetEmployeesResponse.getData());
                                noDataView.setVisibility(View.GONE);
                                noNetworkView.setVisibility(View.GONE);
                            } else {
                                ApiFintechUtilMethods.INSTANCE.Error(EmployeesListReport.this, "No Record Found");
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                            }
                            mAdapter.notifyDataSetChanged();
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
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);

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
            filterShow();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void filterShow() {
        mCustomFilterDialog.openEmpUserListFilter(filterChooseCriteriaId, filterChooseCriteria, filterEnterCriteria, filterTopValue, filterRoleValue, new CustomFilterDialog.EmpFilterCallBack() {
            @Override
            public void onSubmitClick(int topValue, String enterCriteria, int criteriaId, String criteriaValue, String roleValue, int roleId) {
                filterChooseCriteriaId = criteriaId;
                filterChooseCriteria = criteriaValue;
                filterEnterCriteria = enterCriteria;
                filterTopValue = topValue;
                filterRoleValue = roleValue;
                filterRoleId = roleId;
                HitApi(true);
            }
        });
    }


}
