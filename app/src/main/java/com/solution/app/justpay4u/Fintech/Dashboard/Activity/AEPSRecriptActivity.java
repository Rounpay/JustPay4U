package com.solution.app.justpay4u.Fintech.Dashboard.Activity;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AEPSRecriptActivity extends AppCompatActivity {
    private LinearLayout shareView;
    private AppCompatTextView tvTitle;
    private AppCompatTextView tvBankMsg;
    private AppCompatTextView companyNameTv;
    private AppCompatTextView address;
    private LinearLayout custNoView;
    private AppCompatTextView custNoTv;
    private LinearLayout rrnView, stanNoView;
    private AppCompatTextView rrnTv;
    private LinearLayout balanceView;
    private AppCompatTextView balanceTitle;
    private AppCompatTextView balanceTv;
    private AppCompatTextView stanNoTv;
    private LinearLayout bcNameView;
    private AppCompatTextView bcNameTv;
    private LinearLayout bcCodeView;
    private AppCompatTextView bcCodeTv;
    private LinearLayout bcLocationView;
    private AppCompatTextView bcLocationTv;
    private LinearLayout ulbalCodeView;
    private AppCompatTextView ulbalCodeTv;
    private AppCompatTextView outletDetail;
    private LinearLayout btShare;
    private LinearLayout btWhatsapp;

    private AppPreferences mAppPreferences;
    private CustomLoader mCustomLoader;
    private LoginResponse mLoginDataResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_aeps_recript);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mCustomLoader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);

            findViews();
            getIntentData();
        });

    }

    private void findViews() {
        shareView = (LinearLayout) findViewById(R.id.shareView);
        tvTitle = (AppCompatTextView) findViewById(R.id.tv_title);
        tvBankMsg = (AppCompatTextView) findViewById(R.id.tv_bank_msg);
        companyNameTv = (AppCompatTextView) findViewById(R.id.companyName);
        address = (AppCompatTextView) findViewById(R.id.address);
        custNoView = (LinearLayout) findViewById(R.id.custNoView);
        custNoTv = (AppCompatTextView) findViewById(R.id.custNoTv);
        rrnView = (LinearLayout) findViewById(R.id.rrnView);
        stanNoView = (LinearLayout) findViewById(R.id.stanNoView);
        rrnTv = (AppCompatTextView) findViewById(R.id.rrnTv);
        balanceView = (LinearLayout) findViewById(R.id.balanceView);
        balanceTitle = (AppCompatTextView) findViewById(R.id.balanceTitle);
        balanceTv = (AppCompatTextView) findViewById(R.id.balanceTv);
        stanNoTv = (AppCompatTextView) findViewById(R.id.stanNoTv);
        bcNameView = (LinearLayout) findViewById(R.id.bcNameView);
        bcNameTv = (AppCompatTextView) findViewById(R.id.bcNameTv);
        bcCodeView = (LinearLayout) findViewById(R.id.bcCodeView);
        bcCodeTv = (AppCompatTextView) findViewById(R.id.bcCodeTv);
        bcLocationView = (LinearLayout) findViewById(R.id.bcLocationView);
        bcLocationTv = (AppCompatTextView) findViewById(R.id.bcLocationTv);
        ulbalCodeView = (LinearLayout) findViewById(R.id.ulbalCodeView);
        ulbalCodeTv = (AppCompatTextView) findViewById(R.id.ulbalCodeTv);
        outletDetail = (AppCompatTextView) findViewById(R.id.outletDetail);
        btShare = (LinearLayout) findViewById(R.id.bt_share);
        btWhatsapp = (LinearLayout) findViewById(R.id.bt_whatsapp);
    }

    private void getIntentData() {
        String response = getIntent().getExtras().getString("ResponseData", "");
        String transactionType = getIntent().getExtras().getString("TransactionType", "");

        try {
            JSONObject jsonobject = new JSONObject(response);

            setTransactionUiData(jsonobject, transactionType);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        setOutletDetail();


        setCompanyDetail(ApiFintechUtilMethods.INSTANCE.getCompanyProfile(mAppPreferences));

        btShare.setOnClickListener(view -> shareit(false));


        if (Utility.INSTANCE.isPackageInstalled("com.whatsapp", getPackageManager())) {
            btWhatsapp.setVisibility(View.VISIBLE);
            btWhatsapp.setOnClickListener(view -> shareit(true));
        } else {
            btWhatsapp.setVisibility(View.GONE);
        }
    }

    void setCompanyDetail(AppUserListResponse companyData) {

        if (companyData != null && companyData.getCompanyProfile() != null) {
            companyNameTv.setText(companyData.getCompanyProfile().getName() + "");
            String company = "" + Html.fromHtml(companyData.getCompanyProfile().getAddress());
            if (companyData.getCompanyProfile().getPhoneNo() != null && !companyData.getCompanyProfile().getPhoneNo().isEmpty()) {
                company = company + "\nLandline No : " + companyData.getCompanyProfile().getPhoneNo();
            }
            if (companyData.getCompanyProfile().getMobileNo() != null && !companyData.getCompanyProfile().getMobileNo().isEmpty()) {
                company = company + "\nMobile No : " + companyData.getCompanyProfile().getMobileNo();
            }
            if (companyData.getCompanyProfile().getEmailId() != null && !companyData.getCompanyProfile().getEmailId().isEmpty()) {
                company = company + "\nEmail : " + companyData.getCompanyProfile().getEmailId();
            }
            address.setText(company);
        } else {
            String deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            String deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            ApiFintechUtilMethods.INSTANCE.GetCompanyProfile(this, null, mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, object -> {
                AppUserListResponse companyData1 = (AppUserListResponse) object;
                if (companyData1 != null && companyData1.getCompanyProfile() != null) {
                    setCompanyDetail(companyData1);
                }
            });
        }
    }


    void setTransactionUiData(JSONObject jsonobject, String transactionType) {
        String Message = jsonobject.optString("Message", "");
        String StatusCode = jsonobject.optString("StatusCode", "");
        String RRN = jsonobject.optString("RRN", "");
        String CustNo = jsonobject.optString("CustNo", "");
        String bankmessage = jsonobject.optString("bankmessage", "");
        String Stan_no = jsonobject.optString("Stan_no", "");
        String bcloc = jsonobject.optString("bcloc", "");
        String uldalcode = jsonobject.optString("UIDAI_Code", "");
        String bccode = jsonobject.optString("bccode", "");
        String bcname = jsonobject.optString("bcname", "");

        if (!RRN.isEmpty()) {
            rrnView.setVisibility(View.VISIBLE);
            rrnTv.setText(RRN);
        } else {
            rrnView.setVisibility(View.GONE);
        }
        if (!CustNo.isEmpty()) {
            custNoView.setVisibility(View.VISIBLE);
            custNoTv.setText(CustNo);
        } else {
            custNoView.setVisibility(View.GONE);
        }
        if (!bankmessage.isEmpty()) {
            tvBankMsg.setVisibility(View.VISIBLE);
            tvBankMsg.setText(bankmessage);
        } else {
            tvBankMsg.setVisibility(View.GONE);
        }
        if (!Stan_no.isEmpty()) {
            stanNoView.setVisibility(View.VISIBLE);
            stanNoTv.setText(Stan_no);
        } else {
            stanNoView.setVisibility(View.GONE);
        }
        if (!bcloc.isEmpty()) {
            bcLocationView.setVisibility(View.VISIBLE);
            bcLocationTv.setText(bcloc);
        } else {
            bcLocationView.setVisibility(View.GONE);
        }
        if (!uldalcode.isEmpty()) {
            ulbalCodeView.setVisibility(View.VISIBLE);
            ulbalCodeTv.setText(uldalcode);
        } else {
            ulbalCodeView.setVisibility(View.GONE);
        }
        if (!bccode.isEmpty()) {
            bcCodeView.setVisibility(View.VISIBLE);
            bcCodeTv.setText(bccode);
        } else {
            bcCodeView.setVisibility(View.GONE);
        }
        if (!bcname.isEmpty()) {
            bcNameView.setVisibility(View.VISIBLE);
            bcNameTv.setText(bcname);
        } else {
            bcNameView.setVisibility(View.GONE);
        }
        tvTitle.setText("AEPS Receipt");
        // if (StatusCode.equalsIgnoreCase("001")) {
        if (transactionType.equalsIgnoreCase("BalanceInquiryActivity")) {
            if (jsonobject.has("Balance_Details")) {
                tvTitle.setText("AEPS Balance Receipt");
                String BalanceDetails = jsonobject.optString("Balance_Details", "");
                if (!BalanceDetails.isEmpty()) {
                    balanceView.setVisibility(View.VISIBLE);
                    balanceTitle.setText("Balance");
                    balanceTv.setText(BalanceDetails);
                } else {
                    balanceView.setVisibility(View.GONE);
                }
            }
        } else {
            if (jsonobject.has("Amount")) {
                tvTitle.setText("AEPS Amount Receipt");
                String Amount = jsonobject.optString("Amount", "");
                if (!Amount.isEmpty()) {
                    balanceView.setVisibility(View.VISIBLE);
                    balanceTitle.setText("Amount");
                    balanceTv.setText(Amount);
                } else {
                    balanceView.setVisibility(View.GONE);
                }
            }
        }
        // }

    }


    void setOutletDetail() {


        String outletDetailStr = "";
        if (mLoginDataResponse.getData().getName() != null && !mLoginDataResponse.getData().getName().isEmpty()) {
            outletDetailStr = outletDetailStr + "Name : " + mLoginDataResponse.getData().getName();
        }
        if (mLoginDataResponse.getData().getOutletName() != null && !mLoginDataResponse.getData().getOutletName().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Shop Name : " + mLoginDataResponse.getData().getOutletName();
        }
        if (mLoginDataResponse.getData().getMobileNo() != null && !mLoginDataResponse.getData().getMobileNo().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Contact No : " + mLoginDataResponse.getData().getMobileNo();
        }
        if (mLoginDataResponse.getData().getEmailID() != null && !mLoginDataResponse.getData().getEmailID().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Email : " + mLoginDataResponse.getData().getEmailID();
        }
        if (mLoginDataResponse.getData().getAddress() != null && !mLoginDataResponse.getData().getAddress().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Address : " + mLoginDataResponse.getData().getAddress();
        }
        outletDetail.setText(outletDetailStr);

    }

    public void shareit(boolean isWhatsapp) {
        mCustomLoader.show();
        Bitmap myBitmap = Bitmap.createBitmap(shareView.getWidth(), shareView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(myBitmap);
        shareView.layout(0, 0, shareView.getWidth(), shareView.getHeight());
        shareView.draw(c);
        saveImage(myBitmap, isWhatsapp);

    }

    private void saveImage(Bitmap bitmap, boolean isWhatsapp) {
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            ContentValues values = contentValues();
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
            values.put(MediaStore.Images.Media.IS_PENDING, true);

            Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try {
                    saveImageToStream(bitmap, this.getContentResolver().openOutputStream(uri));
                    values.put(MediaStore.Images.Media.IS_PENDING, false);
                    this.getContentResolver().update(uri, values, null, null);
                    if (isWhatsapp) {
                        openWhatsapp(uri);
                    } else {
                        sendMail(uri);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } else {
            File directory = new File(Environment.getExternalStorageDirectory().toString() + "/Pictures/" + getString(R.string.app_name));

            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".png";
            File file = new File(directory, fileName);
            try {
                saveImageToStream(bitmap, new FileOutputStream(file));
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (isWhatsapp) {
                    openWhatsapp(Uri.parse("file://" + file));
                } else {
                    sendMail(Uri.parse("file://" + file));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        }
        return values;
    }

    private void saveImageToStream(Bitmap bitmap, OutputStream outputStream) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
            } catch (FileNotFoundException e) {
                Log.e("GREC", e.getMessage(), e);
            } catch (IOException e) {
                Log.e("GREC", e.getMessage(), e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*public void saveBitmap(Bitmap bitmap, boolean isWhatsapp) {
        // Create a media file name
        Log.v("first", "first");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        String filePath = Environment.getExternalStorageDirectory().toString()
                + "/" + timeStamp + ".jpg";
        File imagePath = new File(filePath);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Log.v("first", "second");
            if (isWhatsapp) {
                openWhatsapp(filePath);
            } else {
                sendMail(filePath);
            }
            mCustomLoader.dismiss();
        } catch (FileNotFoundException e) {
            mCustomLoader.dismiss();
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            mCustomLoader.dismiss();
            Log.e("GREC", e.getMessage(), e);
        }
    }*/

    public void sendMail(Uri myUri) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "AEPS Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");

        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }


    void openWhatsapp(Uri myUri) {

        try {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "AEPS Receipt");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Receipt");
            sendIntent.setType("image/png");
            sendIntent.putExtra(Intent.EXTRA_STREAM, myUri);
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } catch (ActivityNotFoundException e) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp"));
            startActivity(intent);


        }

    }

}

