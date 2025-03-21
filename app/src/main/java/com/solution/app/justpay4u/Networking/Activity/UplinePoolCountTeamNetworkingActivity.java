package com.solution.app.justpay4u.Networking.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.solution.app.justpay4u.Api.Fintech.Request.BasicRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Networking.Object.MemberListData;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.DataPoolCount;
import com.solution.app.justpay4u.ApiHits.NetworkingEndPointInterface;
import com.solution.app.justpay4u.ApiHits.UplinePoolCountRequest;
import com.solution.app.justpay4u.ApiHits.UplinePoolCountResponse;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeoutException;
import retrofit2.Call;
import retrofit2.Callback;

public class UplinePoolCountTeamNetworkingActivity extends AppCompatActivity {

    RecyclerView uplinePoolCount_recycler_view;
    UplinePoolCountAdapter uplinePoolCountAdapter;
    int businessType;
    ArrayList<DataPoolCount> poolCountResponseArrayList = new ArrayList<>();
    EditText search_all;
    CustomLoader loader;

    public View noDataView, noNetworkView, retryBtn ;
    TextView errorMsg;
    private String filterStaus = "All";
    private LoginResponse loginPrefResponse;
    private AppPreferences mAppPreferences;
    private String deviceId, deviceSerialNum;
    ArrayList<MemberListData> levelNoList = new ArrayList<>();
    private int LevelNo = 1;
    private String UserId ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_networking_upline_count_team);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            loginPrefResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            UserId = loginPrefResponse.getData().getUserID();
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            findViews();
            clickView();
            getUplinePoolCount(this);

        });

    }


    void findViews() {
        search_all = findViewById(R.id.search_all);
        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        Intent intent = getIntent();
        filterStaus = intent.getStringExtra("status");
        businessType = getIntent().getIntExtra("BusinessType",0);

        uplinePoolCount_recycler_view = findViewById(R.id.uplinePoolCount_recycler_view);

        uplinePoolCountAdapter = new UplinePoolCountAdapter(poolCountResponseArrayList,filterStaus, UplinePoolCountTeamNetworkingActivity.this);
        uplinePoolCount_recycler_view.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        uplinePoolCount_recycler_view.setAdapter(uplinePoolCountAdapter);


    }


    void clickView() {
        retryBtn.setOnClickListener(v -> {
            getUplinePoolCount(this);
        });


    }

    private void getUplinePoolCount(Activity context) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(this)) {
            if (!loader.isShowing()) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }
            try {
                NetworkingEndPointInterface git = ApiClient.getClient().create(NetworkingEndPointInterface.class);
                Call<UplinePoolCountResponse> call = git.GetLevelWiseCount(new UplinePoolCountRequest(new BasicRequest(
                        ApplicationConstant.INSTANCE.APP_ID,
                        deviceId,
                        loginPrefResponse.getData().getLoginTypeID(),
                        "",
                        deviceSerialNum,
                        loginPrefResponse.getData().getSession(),
                        loginPrefResponse.getData().getSessionID(),
                        loginPrefResponse.getData().getUserID(),
                        BuildConfig.VERSION_NAME),new UplinePoolCountRequest(UserId, LevelNo, filterStaus)));
                call.enqueue(new Callback<UplinePoolCountResponse>() {
                    @Override
                    public void onResponse(Call<UplinePoolCountResponse> call, final retrofit2.Response<UplinePoolCountResponse> response) {

                        try {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    if (response.body().getStatuscode() == 1) {
                                        dataParseCount(response.body());
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
                                        setInfoHideShowCount(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
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
                                setInfoHideShowCount(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                            }
                        } catch (Exception e) {
                            if (loader != null) {
                                if (loader.isShowing()) {
                                    loader.dismiss();
                                }
                            }
                            setInfoHideShowCount(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                        }

                    }

                    @Override
                    public void onFailure(Call<UplinePoolCountResponse> call, Throwable t) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        try {
                            if (t instanceof UnknownHostException || t instanceof IOException) {
                                ApiFintechUtilMethods.INSTANCE.NetworkError(context);
                                setInfoHideShowCount(ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK);
                            } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
                                setInfoHideShowCount(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                            } else {
                                setInfoHideShowCount(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
                                if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                    ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
                                }
                            }

                        } catch (IllegalStateException ise) {
                            setInfoHideShowCount(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
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
                setInfoHideShowCount(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
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
            setInfoHideShowCount(ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK);
            ApiFintechUtilMethods.INSTANCE.NetworkError(this);
        }
    }

    private void setInfoHideShowCount(int errorType) {
        uplinePoolCount_recycler_view.setVisibility(View.GONE);
        poolCountResponseArrayList.clear();
        uplinePoolCountAdapter.notifyDataSetChanged();
        if (errorType == ApiFintechUtilMethods.INSTANCE.ERROR_NETWORK) {
            noNetworkView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
        } else {
            noNetworkView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
            errorMsg.setText("Record not found");
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void dataParseCount(UplinePoolCountResponse poolCountResponse) {
        if (poolCountResponse != null && poolCountResponse.getData() != null && poolCountResponse.getData().size() > 0) {
            poolCountResponseArrayList.clear();
            poolCountResponseArrayList.addAll(poolCountResponse.getData());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //Collections.sort(poolCountResponseArrayList, Comparator.comparing(DataPoolCount::getLevel));
                //Here, I provide a Comparator to sort Items by number.
                Collections.sort(poolCountResponseArrayList, new Comparator<DataPoolCount>() {
                    @Override
                    public int compare(DataPoolCount o1, DataPoolCount o2) {
                        return o1.getLevel() - o2.getLevel();
                    }
                });
            }
            noDataView.setVisibility(View.GONE);
            noNetworkView.setVisibility(View.GONE);
            uplinePoolCount_recycler_view.setVisibility(View.VISIBLE);
            uplinePoolCountAdapter.notifyDataSetChanged();
        } else {
            setInfoHideShowCount(ApiFintechUtilMethods.INSTANCE.ERROR_OTHER);
            ApiFintechUtilMethods.INSTANCE.Error(this, "Record not found");
            uplinePoolCount_recycler_view.setVisibility(View.GONE);
        }
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
        }
        return super.onOptionsItemSelected(item);
    }

    public void ItemClick(DataPoolCount operator, String filterStatus) {
        Intent i = new Intent(this, SponserTeamNetworkingActivity.class);
        i.putExtra("Level", operator.getLevel());
        i.putExtra("DownlineID", UserId);
        i.putExtra("Deactive", filterStatus);
        i.putExtra("maxReportDisplayLevel", operator.getMaxReportDisplayLevel());
        i.putExtra("isMultiLayerTeamDisplay", operator.isMultiLayerTeamDisplay());
        i.putExtra("BusinessType",businessType);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }
}
