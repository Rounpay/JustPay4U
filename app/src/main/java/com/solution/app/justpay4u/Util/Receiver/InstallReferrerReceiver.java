package com.solution.app.justpay4u.Util.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Util.AppPreferences;

/**
 * Created by Vishnu Agarwal on 27,January,2020
 */
public class InstallReferrerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        if (intent.getAction() != null && intent.getAction().equals("com.android.vending.INSTALL_REFERRER")) {
            AppPreferences mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(context);
            String referrer = intent.getStringExtra("referrer");
            if (referrer != null && !referrer.isEmpty() & !referrer.contains("utm_source") && !referrer.contains("utm_medium") &&
                    !referrer.contains("chrome") && !referrer.contains("google")) {
                try {
                    int referrerCode = Integer.parseInt(referrer.trim().replaceAll(" ", ""));
                    mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.UserReferralPref, referrerCode + "");
                    mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.isUserReferralSetPref, true);
                } catch (NumberFormatException nfe) {

                }

            } else {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    referrer = extras.getString("referrer");
                    if (referrer != null && !referrer.isEmpty() && !referrer.contains("utm_source") && !referrer.contains("utm_medium") &&
                            !referrer.contains("chrome") && !referrer.contains("google")) {
                        try {
                            int referrerCode = Integer.parseInt(referrer.trim().replaceAll(" ", ""));
                            mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.UserReferralPref, referrerCode + "");
                            mAppPreferences.setNonRemoval(ApplicationConstant.INSTANCE.isUserReferralSetPref, true);
                        } catch (NumberFormatException nfe) {

                        }
                    }
                }
            }
            // Toast.makeText(context, referrer + "", Toast.LENGTH_SHORT).show();
            /*adb shell am broadcast -a com.android.vending.INSTALL_REFERRER -n com.solution.app.dreamseller/.Util.Receiver.InstallReferrerReceiver --es referrer test_referrer=test
             */
        }
    }
}
