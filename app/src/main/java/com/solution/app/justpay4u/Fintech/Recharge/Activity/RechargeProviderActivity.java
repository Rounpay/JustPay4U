package com.solution.app.justpay4u.Fintech.Recharge.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OperatorListResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Adapter.RechargeProviderHeaderAdapter;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.RecyclerViewStickyHeader.HeaderItemDecoration;

import java.util.ArrayList;

public class RechargeProviderActivity extends AppCompatActivity {

    RecyclerView header_recyclerview;
    RechargeProviderHeaderAdapter mHeaderProviderAdapter;
    View searchViewLayout;
    String from = "dth";
    CustomLoader loader;
    View noDataView;
    TextView errorMsg;
    EditText search_all;
    private int fromId;
    private boolean fromPhoneRecharge;
    private LoginResponse mLoginDataResponse;
    private AppPreferences mAppPreferences;
    private OperatorListResponse operatorListResponse;
    private RequestOptions requestOptionsAdapter;
    private int stateId;
    private String state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_recharge_provider);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            stateId = mLoginDataResponse.getData().getStateID();
            state = mLoginDataResponse.getData().getState();
            requestOptionsAdapter = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_AppLogo();

            getId();
        });

    }

    private void getId() {
        header_recyclerview = findViewById(R.id.header_recyclerView);
        header_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        searchViewLayout = findViewById(R.id.searchViewLayout);
        search_all = findViewById(R.id.search_all);
        noDataView = findViewById(R.id.noDataView);
        errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Operators not available, try after some time");


        new Handler(Looper.getMainLooper()).post(() -> {
            fromPhoneRecharge = getIntent().getBooleanExtra("fromPhoneRecharge", false);
            from = getIntent().getExtras().getString("from");
            fromId = getIntent().getExtras().getInt("fromId", 0);
            getOperator(fromId, ApiFintechUtilMethods.INSTANCE.getOperatorListResponse(mAppPreferences));
        });

        // getOperatorList();


        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));

        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mHeaderProviderAdapter != null) {
                    mHeaderProviderAdapter.filter(s.toString());
                }

            }
        });
    }


    private void getOperator(int op_Type, OperatorListResponse mOperatorListResponse) {

        if (mOperatorListResponse != null && mOperatorListResponse.getOperators() != null &&
                mOperatorListResponse.getOperators().size() > 0) {

            ArrayList<OperatorList> localProviderArray = new ArrayList<>();
            ArrayList<OperatorList> otherProviderArray = new ArrayList<>();
            ArrayList<OperatorList> providerWithHeaderArray = new ArrayList<>();
            // ArrayList<OperatorList> providerNormalArray = new ArrayList<>();
            for (OperatorList op : mOperatorListResponse.getOperators()) {
                if (op.getOpType() == op_Type) {
                    if (stateId != 0) {
                        if (op.getStateID() == stateId) {
                            localProviderArray.add(op);
                        } else {
                            otherProviderArray.add(op);
                        }
                    } else {
                        otherProviderArray.add(op);
                    }

                }
            }

            boolean isLocalAvailable = false;
            if (localProviderArray.size() > 0 && otherProviderArray.size() > 0) {
                OperatorList localProviderHeader = new OperatorList();
                localProviderHeader.setHeader(state + " " + from + " Provider");
                localProviderArray.add(0, localProviderHeader);
                providerWithHeaderArray.addAll(localProviderArray);
                isLocalAvailable = true;

                OperatorList otherProviderHeader = new OperatorList();
                otherProviderHeader.setHeader("Other State " + from + " Provider");
                otherProviderArray.add(0, otherProviderHeader);
                providerWithHeaderArray.addAll(otherProviderArray);

            } else if (otherProviderArray.size() > 0) {
                OperatorList otherProviderHeader = new OperatorList();
                otherProviderHeader.setHeader(from + " Provider");
                otherProviderArray.add(0, otherProviderHeader);
                providerWithHeaderArray.addAll(otherProviderArray);
                isLocalAvailable = false;
            }


            if (providerWithHeaderArray != null && providerWithHeaderArray.size() > 0) {
                noDataView.setVisibility(View.GONE);
                searchViewLayout.setVisibility(View.VISIBLE);
                mHeaderProviderAdapter = new RechargeProviderHeaderAdapter(providerWithHeaderArray, this, requestOptionsAdapter,
                        stateId, from, isLocalAvailable);
                header_recyclerview.setAdapter(mHeaderProviderAdapter);
                header_recyclerview.setVisibility(View.VISIBLE);
                new HeaderItemDecoration(mHeaderProviderAdapter);
            } else {
                searchViewLayout.setVisibility(View.GONE);
                header_recyclerview.setVisibility(View.GONE);
                noDataView.setVisibility(View.VISIBLE);
            }

        } else {
            String deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            String deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            ApiFintechUtilMethods.INSTANCE.OperatorList(this, deviceId, deviceSerialNum, mAppPreferences, object -> {
                operatorListResponse = (OperatorListResponse) object;
                if (operatorListResponse != null && operatorListResponse.getOperators() != null && operatorListResponse.getOperators().size() > 0) {
                    getOperator(op_Type, operatorListResponse);
                }
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void ItemClick(OperatorList operator) {


        /*if (operator.getBBPS() && !operator.getIsPartial()) {

            if (ApiUtilMethods.INSTANCE.isNetworkAvialable(this)) {
                loader.show();
                ApiUtilMethods.INSTANCE.CallOnboarding(this, operator.getOid(), "", true, true,false,
                        null, null, null, loader, mLoginDataResponse, mAppPreferences, deviceId, deviceSerialNum, object -> {
                            if (object != null) {
                                OnboardingResponse mOnboardingResponse = (OnboardingResponse) object;
                                if (mOnboardingResponse != null) {
                                    openRechargeScreen(operator);
                                }
                            }
                        });
            } else {
                ApiUtilMethods.INSTANCE.NetworkError(this);
            }

        } else {*/

        openRechargeScreen(operator);

        /*}*/
    }


    void openRechargeScreen(OperatorList operator) {
        if (fromPhoneRecharge) {
            Intent clickIntent = new Intent();
            clickIntent.putExtra("SelectedOperator", operator);
            clickIntent.putExtra("from", from);
            clickIntent.putExtra("fromId", fromId);
            setResult(RESULT_OK, clickIntent);
            finish();
        } else {
            Intent clickIntent = new Intent(this, RechargeBillPaymentActivity.class);
            clickIntent.putExtra("SelectedOperator", operator);
            clickIntent.putExtra("from", from);
            clickIntent.putExtra("fromId", fromId);
            clickIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(clickIntent);
            /* finish();*/
        }
    }

    /*public void ItemClick(final String name, final int id, final String Max, final String Min, final String Length,
                          final Boolean IsPartial, final Boolean BBPS, final String AccountName, final String AccountRemark, final String Icon, final Boolean isBilling, final String StartWith, final String lengthMax, final boolean isAccountNumeric) {


        if (BBPS && !IsPartial) {
            *//*--------------------Calling Onboarding service----------------------------*//*

            if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                UtilMethods.INSTANCE.CallOnboarding(this, id, "", true, false, loader, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        if (object != null) {
                            OnboardingResponse mOnboardingResponse = (OnboardingResponse) object;
                            if (mOnboardingResponse != null) {
                                Intent clickIntent = new Intent(RechargeProviderActivity.this, SecondRechargeActivity.class);
                                clickIntent.putExtra("selected", name);
                                clickIntent.putExtra("selectedId", id);
                                clickIntent.putExtra("Max", Max);
                                clickIntent.putExtra("Min", Min);
                                clickIntent.putExtra("from", from);
                                clickIntent.putExtra("fromId", fromId);
                                clickIntent.putExtra("Length", Length);
                                clickIntent.putExtra("IsPartial", IsPartial);
                                clickIntent.putExtra("BBPS", BBPS);
                                clickIntent.putExtra("AccountName", AccountName);
                                clickIntent.putExtra("AccountRemark", AccountRemark);
                                clickIntent.putExtra("Icon", Icon);
                                clickIntent.putExtra("isBilling", isBilling);
                                clickIntent.putExtra("lengthMax", lengthMax);
                                clickIntent.putExtra("isAccountNumeric", isAccountNumeric);
                                clickIntent.putExtra("StartWith", StartWith);
                                startActivity(clickIntent);
                            }
                        }
                    }
                });
            } else {
                UtilMethods.INSTANCE.NetworkError(this);
            }

        } else {
            Intent clickIntent = new Intent(RechargeProviderActivity.this, SecondRechargeActivity.class);
            clickIntent.putExtra("selected", name);
            clickIntent.putExtra("selectedId", id);
            clickIntent.putExtra("Max", Max);
            clickIntent.putExtra("Min", Min);
            clickIntent.putExtra("from", from);
            clickIntent.putExtra("fromId", fromId);
            clickIntent.putExtra("Length", Length);
            clickIntent.putExtra("IsPartial", IsPartial);
            clickIntent.putExtra("BBPS", BBPS);
            clickIntent.putExtra("AccountName", AccountName);
            clickIntent.putExtra("AccountRemark", AccountRemark);
            clickIntent.putExtra("Icon", Icon);
            clickIntent.putExtra("isBilling", isBilling);
            clickIntent.putExtra("lengthMax", lengthMax);
            clickIntent.putExtra("isAccountNumeric", isAccountNumeric);
            clickIntent.putExtra("StartWith", StartWith);
            startActivity(clickIntent);
        }


    }
*/
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        if (searchMenuItem == null) {
            return true;
        }

        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint("Search");
        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                }
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //  mAdapter.filter(newText);
                newText = newText.toLowerCase();
                ArrayList<OperatorList> newlist = new ArrayList<>();
                for (OperatorList op : operatorArray) {
                    String getName = op.getName().toLowerCase();
                    if (getName.contains(newText)) {
                        newlist.add(op);

                    }
                }
                mAdapter.filter(newlist);
                return true;
            }
        });

        return true;
    }
*/


}
