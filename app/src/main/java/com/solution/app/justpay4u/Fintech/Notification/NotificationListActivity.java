package com.solution.app.justpay4u.Fintech.Notification;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.Notification.Adapter.NotificationListAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;

public class NotificationListActivity extends AppCompatActivity {

    AppUserListResponse mNotificationResponse;
    RecyclerView recycler_view;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    NotificationListAdapter mNotificationListAdapter;
    AppPreferences mAppPreferences;
    private int readCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_notification_list);

            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            recycler_view = findViewById(R.id.recycler_view);
            recycler_view.setLayoutManager(new LinearLayoutManager(this));
            noDataView = findViewById(R.id.noDataView);
            noNetworkView = findViewById(R.id.noNetworkView);
            retryBtn = findViewById(R.id.retryBtn);
            errorMsg = findViewById(R.id.errorMsg);
            errorMsg.setText("Notification not available.");
            new Handler(Looper.getMainLooper()).post(() -> {
                mNotificationResponse = new Gson().fromJson(mAppPreferences.getNonRemovalString(ApplicationConstant.INSTANCE.notificationListPref), AppUserListResponse.class);

                if (mNotificationResponse != null && mNotificationResponse.getNotifications() != null && mNotificationResponse.getNotifications().size() > 0) {
                    mNotificationListAdapter = new NotificationListAdapter(this, mNotificationResponse.getNotifications());
                    recycler_view.setAdapter(mNotificationListAdapter);
                    noDataView.setVisibility(View.GONE);
                } else {
                    noDataView.setVisibility(View.VISIBLE);
                }
            });

        });


    }

    public void setReadNotification(int position) {
        mNotificationResponse.getNotifications().get(position).setSeen(true);
        mNotificationListAdapter.notifyDataSetChanged();
        mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.notificationListPref, new Gson().toJson(mNotificationResponse));

        readCount++;

        setResult(RESULT_OK, new Intent().putExtra("Notification_Count", readCount));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
