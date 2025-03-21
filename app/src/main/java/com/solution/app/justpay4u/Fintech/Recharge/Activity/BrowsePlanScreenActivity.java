package com.solution.app.justpay4u.Fintech.Recharge.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.solution.app.justpay4u.Api.Fintech.Object.PlanData;
import com.solution.app.justpay4u.Api.Fintech.Object.PlanDataDetails;
import com.solution.app.justpay4u.Api.Fintech.Object.PlanTypes;
import com.solution.app.justpay4u.Api.Fintech.Request.PlanRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.PlanResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Adapter.ViewPagerAdapter;
import com.solution.app.justpay4u.Fintech.Recharge.Fragment.RechargeTypeFragment;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class BrowsePlanScreenActivity extends AppCompatActivity {

    PlanResponse responsePlan = new PlanResponse();
    Map<String, ArrayList<PlanDataDetails>> mapPlanData = new HashMap<>();
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg, tv_note;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private CustomLoader loader;

    public BrowsePlanScreenActivity() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_browse_plan);

            boolean isPlanServiceUpdated = getIntent().getBooleanExtra("IsPlanServiceUpdated", false);
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Plans");
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
            tabLayout = findViewById(R.id.tabs);
            tabLayout.setVisibility(View.GONE);
            viewPager = findViewById(R.id.viewpager);
            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            retryBtn = findViewById(R.id.retryBtn);
            errorMsg = findViewById(R.id.errorMsg);
            tv_note = findViewById(R.id.tv_note);

            errorMsg.setText("Plan not available.");

            retryBtn.setOnClickListener(v -> HitApi(isPlanServiceUpdated));
            HitApi(isPlanServiceUpdated);
        });


    }

    void HitApi(boolean isOtherPlan) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            try {
                if (!loader.isShowing()) {
                    loader.show();
                }
                FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
                Call<PlanResponse> call;
                if (isOtherPlan) {
                    call = git.RechagePlansUpdated(new PlanRequest(getIntent().getStringExtra("OperatorSelectedId"),
                            getIntent().getStringExtra("OperatorRefCircleID"),
                            ApplicationConstant.INSTANCE.APP_ID,
                            getIntent().getStringExtra("DeviceId"),
                            "", BuildConfig.VERSION_NAME,
                            getIntent().getStringExtra("DeviceSerialNum")));
                } else {
                    call = git.Rechageplans(new PlanRequest(getIntent().getStringExtra("OperatorSelectedId"),
                            getIntent().getStringExtra("OperatorRefCircleID"),
                            ApplicationConstant.INSTANCE.APP_ID,
                            getIntent().getStringExtra("DeviceId"),
                            "", BuildConfig.VERSION_NAME,
                            getIntent().getStringExtra("DeviceSerialNum")));
                }
                call.enqueue(new Callback<PlanResponse>() {
                    @Override
                    public void onResponse(Call<PlanResponse> call, final retrofit2.Response<PlanResponse> response) {
                        try {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            if (response.isSuccessful()) {
                                responsePlan = response.body();
                                if (responsePlan != null) {

                                    if (responsePlan.getStatuscode() == 1) {
                                        if (responsePlan.getData() != null && responsePlan.getData().getRecords() != null ||
                                                responsePlan.getData() != null && responsePlan.getData().getTypes() != null && responsePlan.getData().getTypes().size() > 0 ||
                                                responsePlan.getDataRP() != null && responsePlan.getDataRP().getRecords() != null ||
                                                responsePlan.getDataPA() != null && responsePlan.getDataPA().getError() == 0 && responsePlan.getDataPA().getRecords() != null) {

                                            hideShowView(0);

                                            PlanData records = responsePlan.getData();
                                            PlanData recordsUpdate = responsePlan.getData();
                                            PlanData recordsRP = responsePlan.getDataRP();
                                            PlanData recordsPA = responsePlan.getDataPA();

                                            if (records != null && records.getRecords() != null && records.getStatus() != 0) {
                                                dataParse(records);
                                            } else if (recordsUpdate != null && recordsUpdate.getTypes() != null && recordsUpdate.getTypes().size() > 0) {
                                                dataParseUpdated(recordsUpdate.getTypes());
                                            } else if (recordsRP != null && recordsRP.getRecords() != null && recordsRP.getStatus() != 0) {
                                                dataParse(recordsRP);
                                            } else if (recordsPA != null && recordsPA.getRecords() != null && recordsPA.getError() == 0) {
                                                dataParse(recordsPA);
                                            } else if (recordsPA != null && recordsPA.getError() != 0) {
                                                hideShowView(1);
                                                ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Recharge.Activity.BrowsePlanScreenActivity.this, recordsPA.getMessage() + "");
                                            } else {
                                                hideShowView(1);
                                                ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Recharge.Activity.BrowsePlanScreenActivity.this, "No Record Found ! ");
                                            }

                                        } else if (responsePlan.getDataPA() != null && responsePlan.getDataPA().getError() != 0) {
                                            hideShowView(1);
                                            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Recharge.Activity.BrowsePlanScreenActivity.this, responsePlan.getDataPA().getMessage() + "");
                                        } else if (responsePlan.getStatuscode() == 1) {
                                            hideShowView(1);
                                            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Recharge.Activity.BrowsePlanScreenActivity.this, "Plan not found");
                                        }
                                    } else {
                                        hideShowView(1);
                                        if (!responsePlan.getIsVersionValid()) {
                                            ApiFintechUtilMethods.INSTANCE.versionDialog(com.solution.app.justpay4u.Fintech.Recharge.Activity.BrowsePlanScreenActivity.this);
                                        } else {
                                            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Recharge.Activity.BrowsePlanScreenActivity.this, responsePlan.getMsg() + "");
                                        }
                                    }

                                } else {
                                    hideShowView(1);
                                    ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Recharge.Activity.BrowsePlanScreenActivity.this, "Plan not found");
                                }
                            } else {
                                hideShowView(1);
                                ApiFintechUtilMethods.INSTANCE.apiErrorHandle(com.solution.app.justpay4u.Fintech.Recharge.Activity.BrowsePlanScreenActivity.this, response.code(), response.message());
                            }
                        } catch (Exception e) {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            hideShowView(1);
                            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Recharge.Activity.BrowsePlanScreenActivity.this, e.getMessage() + "");
                        }

                    }

                    @Override
                    public void onFailure(Call<PlanResponse> call, Throwable t) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        try {
                            if (t instanceof UnknownHostException || t instanceof IOException) {
                                hideShowView(2);
                                ApiFintechUtilMethods.INSTANCE.NetworkError(com.solution.app.justpay4u.Fintech.Recharge.Activity.BrowsePlanScreenActivity.this);
                            } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                hideShowView(1);
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(com.solution.app.justpay4u.Fintech.Recharge.Activity.BrowsePlanScreenActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                            } else {
                                hideShowView(1);
                                if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                    ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(com.solution.app.justpay4u.Fintech.Recharge.Activity.BrowsePlanScreenActivity.this, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Recharge.Activity.BrowsePlanScreenActivity.this, getString(R.string.some_thing_error));
                                }
                            }


                        } catch (IllegalStateException ise) {
                            hideShowView(1);
                            ApiFintechUtilMethods.INSTANCE.Error(com.solution.app.justpay4u.Fintech.Recharge.Activity.BrowsePlanScreenActivity.this, ise.getMessage());

                        }

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                if (loader != null && loader.isShowing()) {
                    loader.dismiss();
                }
                hideShowView(1);
            }
        } else {
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

    private void dataParse(PlanData records) {
        if (records.getRecords().isJsonObject()) {
            JsonObject mJsonObject = (JsonObject) records.getRecords();
            Type type = new TypeToken<List<PlanDataDetails>>() {
            }.getType();
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            for (String key : mJsonObject.keySet()) {
                JsonArray mJsonArray = null;
                try {
                    mJsonArray = mJsonObject.getAsJsonArray(key);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (mJsonArray != null && mJsonArray.size() > 0) {
                    ArrayList<PlanDataDetails> arrayList = new Gson().fromJson(mJsonArray, type);

                    RechargeTypeFragment rechargeTypeFragment = new RechargeTypeFragment();
                    Bundle arg = new Bundle();
                    arg.putString("type", key);
                    arg.putParcelableArrayList("response", arrayList);
                    rechargeTypeFragment.setArguments(arg);
                    adapter.addFragment(rechargeTypeFragment, "" + key);


                }
            }

            if (adapter.getCount() > 0) {
                viewPager.setAdapter(adapter);

                tv_note.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.VISIBLE);
                noDataView.setVisibility(View.GONE);
                noNetworkView.setVisibility(View.GONE);
                tabLayout.setupWithViewPager(viewPager);
            } else {
                noDataView.setVisibility(View.VISIBLE);
                noNetworkView.setVisibility(View.GONE);
            }
        } else {
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
        }
    }

    private void dataParseUpdated(ArrayList<PlanTypes> mPlanTypes) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (PlanTypes item : mPlanTypes) {


            if (item.getpDetails() != null && item.getpDetails().size() > 0) {
                RechargeTypeFragment rechargeTypeFragment = new RechargeTypeFragment();
                Bundle arg = new Bundle();
                arg.putString("type", item.getpType() + "");
                arg.putParcelableArrayList("response", item.getpDetails());
                rechargeTypeFragment.setArguments(arg);
                adapter.addFragment(rechargeTypeFragment, "" + item.getpType());

            }

        }

        if (adapter.getCount() > 0) {
            viewPager.setAdapter(adapter);

            tv_note.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            tabLayout.setupWithViewPager(viewPager);
        } else {
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
        }


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        for (Map.Entry<String, ArrayList<PlanDataDetails>> entry : mapPlanData.entrySet()) {
            String key = entry.getKey();
            ArrayList<PlanDataDetails> arrayList = entry.getValue();
            RechargeTypeFragment rechargeTypeFragment = new RechargeTypeFragment();
            Bundle arg = new Bundle();
            arg.putString("type", key);
            arg.putParcelableArrayList("response", arrayList);
            rechargeTypeFragment.setArguments(arg);
            adapter.addFragment(rechargeTypeFragment, "" + key);
        }

        viewPager.setAdapter(adapter);
    }

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
        clickIntent.putExtra("AMOUNT", amount);
        clickIntent.putExtra("DESCRIPTION", desc);
        setResult(RESULT_OK, clickIntent);

        finish();
    }


}
