package com.solution.app.justpay4u.Util;

import android.app.Activity;

import com.solution.app.justpay4u.BuildConfig;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FundRequestUtil {



    Activity mActivity;

    public FundRequestUtil(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);

            while (md5.length() < 32) {
                md5 = "0" + md5;
            }

            return md5;
        } catch (NoSuchAlgorithmException e) {
            //Log.e("MD5", e.getLocalizedMessage());
            return null;
        }

    }

    public String AppEncrypt(String input) {
        return md5(md5(input) + BuildConfig.KEY);
    }


}
