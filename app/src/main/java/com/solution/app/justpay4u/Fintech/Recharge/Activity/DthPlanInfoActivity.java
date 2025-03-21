package com.solution.app.justpay4u.Fintech.Recharge.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.solution.app.justpay4u.Api.Fintech.Object.DthPlanInfoAllData;
import com.solution.app.justpay4u.Api.Fintech.Object.DthPlanLanguages;
import com.solution.app.justpay4u.Api.Fintech.Object.PlanInfoPlan;
import com.solution.app.justpay4u.Api.Fintech.Object.PlanInfoRPData;
import com.solution.app.justpay4u.Api.Fintech.Object.PlanInfoRPWithPackage;
import com.solution.app.justpay4u.Api.Fintech.Object.PlanInfoRecords;
import com.solution.app.justpay4u.Api.Fintech.Request.DthPlanLanguageWiseRequest;
import com.solution.app.justpay4u.Api.Fintech.Request.ROfferRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.DthPlanInfoAllResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DthPlanInfoResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.PlanRPResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Adapter.ViewPagerAdapter;
import com.solution.app.justpay4u.Fintech.Recharge.Fragment.DthViewPlanFragment;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class DthPlanInfoActivity extends AppCompatActivity {



    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg, tv_note;
    String intentOpId;
    boolean intentFromLanguage;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private CustomLoader loader;
    private ViewPagerAdapter adapter;
    private String intentLanguage;
    private String intentNumber;
    private String intentDeviceId;
    private String intentDeviceSerialNum;
    private int INTENT_VIEW_PLAN = 634;
    private boolean isPlanServiceUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_dth_plan_info);

            intentOpId = getIntent().getStringExtra("OperatorSelectedId");
            intentNumber = getIntent().getStringExtra("Number");
            intentLanguage = getIntent().getStringExtra("Language");
            intentDeviceId = getIntent().getStringExtra("DeviceId");
            intentDeviceSerialNum = getIntent().getStringExtra("DeviceSerialNum");
            isPlanServiceUpdated = getIntent().getBooleanExtra("IsPlanServiceUpdated", false);
            intentFromLanguage = getIntent().getBooleanExtra("IsFromLanguage", false);
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Plans" + (intentFromLanguage ? " (" + intentLanguage + ")" : ""));
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            retryBtn = findViewById(R.id.retryBtn);
            errorMsg = findViewById(R.id.errorMsg);
            tv_note = findViewById(R.id.tv_note);
            errorMsg.setText("Plan not available.");
            tabLayout = findViewById(R.id.tabs);
            tabLayout.setVisibility(View.GONE);
            viewPager = findViewById(R.id.viewpager);

            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            retryBtn.setOnClickListener(v -> HitApi());

            HitApi();
        });



    }


    public void HitApi() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            try {
                if (!loader.isShowing()) {
                    loader.show();
                }
                FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);

                if (isPlanServiceUpdated) {
                    Call<DthPlanInfoAllResponse> call;
                    if (intentFromLanguage) {
                        call = git.GetDTHPlanByLang(new DthPlanLanguageWiseRequest(intentOpId,
                                intentLanguage, intentNumber,
                                ApplicationConstant.INSTANCE.APP_ID,
                                intentDeviceId,
                                "", BuildConfig.VERSION_NAME,
                                intentDeviceSerialNum));


                    } else {
                        call = git.GetDTHSimplePlanInfo(new ROfferRequest(intentOpId,
                                intentNumber,
                                ApplicationConstant.INSTANCE.APP_ID,
                                intentDeviceId,
                                "", BuildConfig.VERSION_NAME,
                                intentDeviceSerialNum));


                    }

                    call.enqueue(new Callback<DthPlanInfoAllResponse>() {
                        @Override
                        public void onResponse(Call<DthPlanInfoAllResponse> call, final retrofit2.Response<DthPlanInfoAllResponse> response) {

                            try {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        if (response.body().getStatuscode() == 1) {
                                            hideShowView(0);
                                            if (response.body().getData() != null && response.body().getData().size() > 0) {

                                                dataParseAll(response.body().getData());
                                            } else {
                                                hideShowView(1);
                                                ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, "Plan not found");
                                            }
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, response.body().getMsg() + "");
                                        }


                                    } else {
                                        hideShowView(1);
                                        ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, "Something went wrong, try after some time.");
                                    }
                                } else {
                                    hideShowView(1);
                                    ApiFintechUtilMethods.INSTANCE.apiErrorHandle(DthPlanInfoActivity.this, response.code(), response.message());
                                }
                            } catch (Exception e) {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }
                                hideShowView(1);
                            }

                        }

                        @Override
                        public void onFailure(Call<DthPlanInfoAllResponse> call, Throwable t) {

                            try {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }

                                if (t instanceof UnknownHostException || t instanceof IOException) {
                                    hideShowView(2);
                                    ApiFintechUtilMethods.INSTANCE.NetworkError(DthPlanInfoActivity.this);
                                } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                    hideShowView(1);
                                    ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(DthPlanInfoActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                                } else {
                                    hideShowView(1);
                                    if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                        ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(DthPlanInfoActivity.this, "FATAL ERROR", t.getMessage() + "");
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, getString(R.string.some_thing_error));
                                    }
                                }
                            } catch (IllegalStateException ise) {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }
                                ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, ise.getMessage());
                                hideShowView(1);
                            }

                        }
                    });
                } else {

                    Call<DthPlanInfoResponse> call;
                    if (intentFromLanguage) {

                        call = git.GetDTHPlanListByLanguage(new DthPlanLanguageWiseRequest(intentOpId,
                                intentLanguage, intentNumber,
                                ApplicationConstant.INSTANCE.APP_ID,
                                intentDeviceId,
                                "", BuildConfig.VERSION_NAME,
                                intentDeviceSerialNum));


                    } else {

                        call = git.DTHSimplePlanInfo(new ROfferRequest(intentOpId,
                                intentNumber,
                                ApplicationConstant.INSTANCE.APP_ID,
                                intentDeviceId,
                                "", BuildConfig.VERSION_NAME,
                                intentDeviceSerialNum));


                    }

                    call.enqueue(new Callback<DthPlanInfoResponse>() {
                        @Override
                        public void onResponse(Call<DthPlanInfoResponse> call, final retrofit2.Response<DthPlanInfoResponse> response) {

                            try {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        if (response.body().getStatuscode() == 1) {
                                            hideShowView(0);
                                            if (response.body().getData() != null && response.body().getData().getRecords() != null) {

                                                dataParse(response.body().getData().getRecords());
                                            } else if (response.body().getDataRP() != null && response.body().getDataRP().getResponse() != null && response.body().getDataRP().getResponse().size() > 0) {

                                                dataParseRP(response.body().getDataRP());
                                            } else if (response.body().getDataRPDTHWithPackage() != null && response.body().getDataRPDTHWithPackage() != null) {

                                                dataParseRPNew(response.body().getDataRPDTHWithPackage());
                                            } else if (response.body().getDataPA() != null && response.body().getDataPA().getRecords() != null) {

                                                dataParsePA(response.body().getDataPA().getRecords());
                                            } else {
                                                hideShowView(1);
                                                ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, "Plan not found");
                                            }
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, response.body().getMsg() + "");
                                        }


                                    } else {
                                        hideShowView(1);
                                        ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, "Something went wrong, try after some time.");
                                    }
                                } else {
                                    hideShowView(1);
                                    ApiFintechUtilMethods.INSTANCE.apiErrorHandle(DthPlanInfoActivity.this, response.code(), response.message());
                                }
                            } catch (Exception e) {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }
                                hideShowView(1);
                            }

                        }

                        @Override
                        public void onFailure(Call<DthPlanInfoResponse> call, Throwable t) {

                            try {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }

                                if (t instanceof UnknownHostException || t instanceof IOException) {
                                    hideShowView(2);
                                    ApiFintechUtilMethods.INSTANCE.NetworkError(DthPlanInfoActivity.this);
                                } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                    hideShowView(1);
                                    ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(DthPlanInfoActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                                } else {
                                    hideShowView(1);
                                    if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                        ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(DthPlanInfoActivity.this, "FATAL ERROR", t.getMessage() + "");
                                    } else {
                                        ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, getString(R.string.some_thing_error));
                                    }
                                }
                            } catch (IllegalStateException ise) {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }
                                ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, ise.getMessage());
                                hideShowView(1);
                            }

                        }
                    });
                }


            } catch (Exception e) {
                if (loader != null && loader.isShowing()) {
                    loader.dismiss();
                }
                e.printStackTrace();
                hideShowView(1);
            }
        } else {
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            hideShowView(2);
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }


    void hideShowView(int type) {
        if (type == 1) {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        } else {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.GONE);
        }

    }

    private void dataParse(PlanInfoRecords responsePlan) {
        hideShowView(0);
        if (responsePlan != null) {

            if (responsePlan.getPlan() != null && responsePlan.getPlan().size() > 0) {
                Bundle arg = new Bundle();
                arg.putParcelableArrayList("response", responsePlan.getPlan());
                arg.putString("OpID", intentOpId);
                arg.putBoolean("IsPlanServiceUpdated", isPlanServiceUpdated);
                DthViewPlanFragment mFragment = new DthViewPlanFragment();
                mFragment.setArguments(arg);
                adapter.addFragment(mFragment, "Plan");
            }
            if (responsePlan.getAddOnPack() != null && responsePlan.getAddOnPack().size() > 0) {
                Bundle arg = new Bundle();
                arg.putParcelableArrayList("response", responsePlan.getAddOnPack());
                arg.putString("OpID", intentOpId);
                arg.putBoolean("IsPlanServiceUpdated", isPlanServiceUpdated);
                DthViewPlanFragment mFragment = new DthViewPlanFragment();
                mFragment.setArguments(arg);
                adapter.addFragment(mFragment, "Add On Pack");
            }

            if (adapter.getCount() > 0) {
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(adapter.getCount());
                tv_note.setVisibility(View.VISIBLE);
                if (adapter.getCount() > 1) {
                    tabLayout.setVisibility(View.VISIBLE);
                }
                tabLayout.setupWithViewPager(viewPager);
            } else {
                hideShowView(1);
                ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, "Plan not found");
            }
        } else {
            hideShowView(1);
            ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, "Plan not found");
        }
    }

    private void dataParsePA(PlanInfoRecords responsePAPlan) {
        hideShowView(0);
        if (responsePAPlan != null) {

            if (responsePAPlan.getPlan() != null && responsePAPlan.getPlan().size() > 0) {
                Bundle arg = new Bundle();
                arg.putParcelableArrayList("responsePA", responsePAPlan.getPlan());
                arg.putString("OpID", intentOpId);
                arg.putBoolean("IsPlanServiceUpdated", isPlanServiceUpdated);
                DthViewPlanFragment mFragment = new DthViewPlanFragment();
                mFragment.setArguments(arg);
                adapter.addFragment(mFragment, "Plan");
            }
            if (responsePAPlan.getAddOnPack() != null && responsePAPlan.getAddOnPack().size() > 0) {
                Bundle arg = new Bundle();
                arg.putParcelableArrayList("responsePA", responsePAPlan.getAddOnPack());
                arg.putString("OpID", intentOpId);
                arg.putBoolean("IsPlanServiceUpdated", isPlanServiceUpdated);
                DthViewPlanFragment mFragment = new DthViewPlanFragment();
                mFragment.setArguments(arg);
                adapter.addFragment(mFragment, "Add On Pack");
            }

            if (adapter.getCount() > 0) {
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(adapter.getCount());
                tv_note.setVisibility(View.VISIBLE);
                if (adapter.getCount() > 1) {
                    tabLayout.setVisibility(View.VISIBLE);
                }
                tabLayout.setupWithViewPager(viewPager);
            } else {
                hideShowView(1);
                ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, "Plan not found");
            }
        } else {
            hideShowView(1);
            ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, "Plan not found");
        }
    }

    private void dataParseAll(ArrayList<DthPlanInfoAllData> dthPlanInfoAllData) {
        hideShowView(0);
        if (dthPlanInfoAllData != null) {

            for (DthPlanInfoAllData mDthPlanInfoAllData : dthPlanInfoAllData) {
                if (mDthPlanInfoAllData.getpType() != null && !mDthPlanInfoAllData.getpType().isEmpty()
                        && mDthPlanInfoAllData.getpDetials() != null && mDthPlanInfoAllData.getpDetials().size() > 0) {
                    Bundle arg = new Bundle();
                    arg.putParcelableArrayList("response", mDthPlanInfoAllData.getpDetials());
                    arg.putString("OpID", intentOpId);
                    arg.putBoolean("IsPlanServiceUpdated", isPlanServiceUpdated);
                    DthViewPlanFragment mFragment = new DthViewPlanFragment();
                    mFragment.setArguments(arg);
                    adapter.addFragment(mFragment, mDthPlanInfoAllData.getpType());
                }
            }


            if (adapter.getCount() > 0) {
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(adapter.getCount());
                tv_note.setVisibility(View.VISIBLE);
                if (adapter.getCount() > 1) {
                    tabLayout.setVisibility(View.VISIBLE);
                }
                tabLayout.setupWithViewPager(viewPager);
            } else {
                hideShowView(1);
                ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, "Plan not found");
            }
        } else {
            hideShowView(1);
            ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, "Plan not found");
        }
    }


    private void dataParseRP(PlanInfoRPData responseRPPlan) {
        hideShowView(0);
        if (responseRPPlan != null) {

            if (responseRPPlan.getResponse() != null && responseRPPlan.getResponse().size() > 0) {
                HashMap<String, ArrayList<PlanRPResponse>> mapList = new HashMap<>();
                String planType = "";
                for (PlanRPResponse item : responseRPPlan.getResponse()) {
                    planType = item.getRechargeType().trim();
                    if (mapList.containsKey(planType)) {
                        mapList.get(planType).add(item);
                    } else {
                        ArrayList<PlanRPResponse> mList = new ArrayList<>();
                        mList.add(item);
                        mapList.put(planType, mList);
                    }

                }

                for (Map.Entry<String, ArrayList<PlanRPResponse>> entry : mapList.entrySet()) {

                    Bundle arg = new Bundle();
                    arg.putParcelableArrayList("responseRP", entry.getValue());
                    arg.putString("OpID", intentOpId);
                    arg.putBoolean("IsPlanServiceUpdated", isPlanServiceUpdated);
                    DthViewPlanFragment mFragment = new DthViewPlanFragment();
                    mFragment.setArguments(arg);
                    adapter.addFragment(mFragment, entry.getKey());
                }

            }

            if (adapter.getCount() > 0) {
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(adapter.getCount());
                tv_note.setVisibility(View.VISIBLE);
                if (adapter.getCount() > 1) {
                    tabLayout.setVisibility(View.VISIBLE);
                }
                tabLayout.setupWithViewPager(viewPager);
            } else {
                hideShowView(1);
                ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, "Plan not found");
            }
        } else {
            hideShowView(1);
            ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, "Plan not found");
        }
    }

    private void dataParseRPNew(PlanInfoRPWithPackage responseRPNewPlan) {
        hideShowView(0);
        if (responseRPNewPlan != null) {
            if (responseRPNewPlan.getPackageList() != null && responseRPNewPlan.getPackageList().size() > 0) {
                Bundle arg = new Bundle();
                arg.putParcelableArrayList("responseRPNew", responseRPNewPlan.getPackageList());
                arg.putString("OpID", intentOpId);
                arg.putBoolean("IsPlanServiceUpdated", isPlanServiceUpdated);
                DthViewPlanFragment mFragment = new DthViewPlanFragment();
                mFragment.setArguments(arg);
                adapter.addFragment(mFragment, "Top Plan");
            }

            if (responseRPNewPlan.getLanguages() != null && responseRPNewPlan.getLanguages().size() > 0) {
                Bundle arg = new Bundle();
                arg.putParcelableArrayList("responseRPNewLanguages", responseRPNewPlan.getLanguages());
                arg.putString("OpID", intentOpId);
                arg.putBoolean("IsPlanServiceUpdated", isPlanServiceUpdated);
                DthViewPlanFragment mFragment = new DthViewPlanFragment();
                mFragment.setArguments(arg);
                adapter.addFragment(mFragment, "Languages");
            }

            if (responseRPNewPlan.getResponse() != null && responseRPNewPlan.getResponse().size() > 0) {
                HashMap<String, ArrayList<PlanRPResponse>> mapList = new HashMap<>();
                String planType = "";
                for (PlanRPResponse item : responseRPNewPlan.getResponse()) {
                    planType = item.getRechargeType().trim();
                    if (mapList.containsKey(planType)) {
                        mapList.get(planType).add(item);
                    } else {
                        ArrayList<PlanRPResponse> mList = new ArrayList<>();
                        mList.add(item);
                        mapList.put(planType, mList);
                    }

                }

                for (Map.Entry<String, ArrayList<PlanRPResponse>> entry : mapList.entrySet()) {

                    Bundle arg = new Bundle();
                    arg.putParcelableArrayList("responseRPNew", entry.getValue());
                    arg.putString("OpID", intentOpId);
                    arg.putBoolean("IsPlanServiceUpdated", isPlanServiceUpdated);
                    DthViewPlanFragment mFragment = new DthViewPlanFragment();
                    mFragment.setArguments(arg);
                    adapter.addFragment(mFragment, entry.getKey());
                }

            }


            if (adapter.getCount() > 0) {
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(adapter.getCount());
                tv_note.setVisibility(View.VISIBLE);
                if (adapter.getCount() > 1) {
                    tabLayout.setVisibility(View.VISIBLE);
                }
                tabLayout.setupWithViewPager(viewPager);
            } else {
                hideShowView(1);
                ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, "Plan not found");
            }
        } else {
            hideShowView(1);
            ApiFintechUtilMethods.INSTANCE.Error(DthPlanInfoActivity.this, "Plan not found");
        }
    }


   /* private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < rechargeType.size(); i++) {
            DthViewPlanFragment mFragment = new DthViewPlanFragment();

            Bundle arg = new Bundle();
            arg.putString("type", rechargeType.get(i));
            arg.putSerializable("response", responsePlan);
            arg.putSerializable("OpID", intentOpId);
            arg.putSerializable("responseRP", responseRPPlan);
            arg.putSerializable("responseRPNew", responseRPNewPlan);
            arg.putSerializable("responsePA", responsePAPlan);
            mFragment.setArguments(arg);
            adapter.addFragment(mFragment, "" + rechargeType.get(i));
        }
        viewPager.setAdapter(adapter);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.browseplan_menu, menu);
        final MenuItem settingsItem = menu.findItem(R.id.operatorIcon);
        this.invalidateOptionsMenu();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    public void ItemClick(String amount, String desc) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("Amount", Utility.INSTANCE.formatedAmountWithOutRupees(amount));
        clickIntent.putExtra("desc", desc);
        setResult(RESULT_OK, clickIntent);
        finish();
    }

    public void languageClick(DthPlanLanguages operator) {
        Intent planIntent = new Intent(this, DthPlanInfoActivity.class);
        planIntent.putExtra("OperatorSelectedId", intentOpId + "");
        planIntent.putExtra("Number", intentNumber);
        planIntent.putExtra("Language", operator.getLanguage() + "");
        planIntent.putExtra("IsFromLanguage", true);
        planIntent.putExtra("DeviceId", intentDeviceId);
        planIntent.putExtra("IsPlanServiceUpdated", isPlanServiceUpdated);
        planIntent.putExtra("DeviceSerialNum", intentDeviceSerialNum);
        startActivityForResult(planIntent, INTENT_VIEW_PLAN);
    }

    public void languageClick(PlanInfoPlan operator) {
        Intent planIntent = new Intent(this, DthPlanInfoActivity.class);
        planIntent.putExtra("OperatorSelectedId", intentOpId + "");
        planIntent.putExtra("Number", intentNumber);
        planIntent.putExtra("Language", operator.getpLangauge() + "");
        planIntent.putExtra("IsFromLanguage", true);
        planIntent.putExtra("DeviceId", intentDeviceId);
        planIntent.putExtra("IsPlanServiceUpdated", isPlanServiceUpdated);
        planIntent.putExtra("DeviceSerialNum", intentDeviceSerialNum);
        startActivityForResult(planIntent, INTENT_VIEW_PLAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_VIEW_PLAN && resultCode == RESULT_OK) {
            if (data != null) {
                ItemClick(data.getStringExtra("Amount"), data.getStringExtra("desc"));
            } else {
                Toast.makeText(this, getString(R.string.some_thing_error), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
