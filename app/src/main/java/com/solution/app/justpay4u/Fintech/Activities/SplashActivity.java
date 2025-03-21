package com.solution.app.justpay4u.Fintech.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.Authentication.LoginActivity;
import com.solution.app.justpay4u.Fintech.Dashboard.Activity.HomeDashActivity;
import com.solution.app.justpay4u.Fintech.Employee.Activity.EmpDashboardActivity;
import com.solution.app.justpay4u.Networking.Activity.NetworkingDashboardActivity;
import com.solution.app.justpay4u.Shopping.Activity.ShoppingDashboardActivity;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.InstallReferral;

public class SplashActivity extends AppCompatActivity {


    private int INTENT_PERMISSION = 123;
    private AppPreferences mAppPreferences;
    private LoginResponse mLoginDataResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* setContentView(R.layout.activity_splash);*/
        new Handler(Looper.getMainLooper()).post(() -> {
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            if (!mAppPreferences.getNonRemovalBoolean(ApplicationConstant.INSTANCE.isUserReferralSetPref)) {
                new InstallReferral(this).InstllReferralData(mAppPreferences);
            }
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            new Handler().postDelayed(() -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    ReadPhoneStatePermission();
                }
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ReadPhoneStatePermission();
                } else {
                    goToNextScreen();
                }
            }, 500);
        });
    }


    public void startDashboard() {
        if (mLoginDataResponse.getData().getLoginTypeID() == 1) {
            finish();
            if (mLoginDataResponse.getDashPreference() == 1){
                Intent intent = new Intent(SplashActivity.this, NetworkingDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else if (mLoginDataResponse.getDashPreference() == 2){
                Intent intent = new Intent(SplashActivity.this, HomeDashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else {
                Intent intent = new Intent(SplashActivity.this, ShoppingDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        } else{
            finish();
            Intent intent = new Intent(SplashActivity.this, EmpDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }

    public void startLogin(){
        if (!mAppPreferences.getNonRemovalBoolean(ApplicationConstant.INSTANCE.isTutorialVisiblePref)) {
            Intent intent = new Intent(SplashActivity.this, TutorialActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void ReadPhoneStatePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                /* ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED ||*/
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                /*ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED*/) {
            startActivityForResult(new Intent(this, PermissionActivity.class),
                    INTENT_PERMISSION);

        } else {
            goToNextScreen();
        }

    }


    void goToNextScreen() {
        if (mLoginDataResponse != null && mLoginDataResponse.getData() != null) {
//            startDashboard();
            Intent intent = new Intent(SplashActivity.this, NetworkingDashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            startLogin();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_PERMISSION && resultCode == RESULT_OK) {
            goToNextScreen();
        } else {
            finish();
        }
    }
}
