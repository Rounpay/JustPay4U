package com.solution.app.justpay4u.Fintech.AEPS.Activity;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Fintech.AEPS.Adapter.AEPSMiniStatementAdapter;
import com.solution.app.justpay4u.Api.Fintech.Object.MiniStatements;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AEPSMiniStatementActivity extends AppCompatActivity {
    ImageView operatorImage;
    View deviceView;
    LinearLayout shareView;
    ArrayList<MiniStatements> miniStatements;
    private AppCompatImageView closeIv;
    private TextView  outletDetail, address, companyNameTv;
    private TextView opTv;
    private TextView numTv, balTv;
    private TextView deviceTv, dateTv;
    private View shareBtn, repetBtn, btWhatsapp;
    private String intentOpName, intentOpImage, intentNum, intentBalance, intentDeviceName;
    private int intentStatus;
    private AppPreferences mAppPreferences;
    private RequestOptions requestOptions;
    private LoginResponse LoginDataResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_aeps_mini_statement);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            LoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);

            intentOpName = getIntent().getStringExtra("OP_NAME");
            intentOpImage = getIntent().getStringExtra("OP_IMAGE");
            miniStatements = getIntent().getParcelableArrayListExtra("MINI_STATEMENT");
            intentNum = getIntent().getStringExtra("NUMBER");
            intentBalance = getIntent().getStringExtra("BALANCE");
            intentDeviceName = getIntent().getStringExtra("Device_NAME");
            findViews();
            setUiData();
            closeIv.setOnClickListener(v -> finish());


            shareBtn.setOnClickListener(v -> shareit(false));

            repetBtn.setOnClickListener(v -> finish());

            if (Utility.INSTANCE.isPackageInstalled("com.whatsapp", getPackageManager())) {
                btWhatsapp.setVisibility(View.VISIBLE);
                btWhatsapp.setOnClickListener(view -> shareit(true));
            } else {
                btWhatsapp.setVisibility(View.GONE);
            }

        });
    }


    private void findViews() {

        shareView = findViewById(R.id.shareView);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AEPSMiniStatementAdapter(miniStatements, this));
        closeIv = findViewById(R.id.closeIv);
        outletDetail = findViewById(R.id.outletDetail);
        companyNameTv = findViewById(R.id.companyName);
        address = findViewById(R.id.address);
        opTv = findViewById(R.id.opTv);
        balTv = findViewById(R.id.balTv);
        operatorImage = findViewById(R.id.operatorImage);
        numTv = findViewById(R.id.numTv);

        deviceTv = findViewById(R.id.deviceTv);
        dateTv = findViewById(R.id.dateTv);
        deviceView = findViewById(R.id.deviceView);

        shareBtn = findViewById(R.id.bt_share);
        repetBtn = findViewById(R.id.bt_repeat);
        btWhatsapp = findViewById(R.id.bt_whatsapp);

        ImageView logoIv = findViewById(R.id.appLogo);
        requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
        String appLogoUrl = ApiFintechUtilMethods.INSTANCE.getAppLogoUrl(mAppPreferences);
        if (appLogoUrl != null && !appLogoUrl.isEmpty()) {
            Glide.with(this)
                    .load(appLogoUrl)
                    .apply(requestOptions)
                    .into(logoIv);
        } else {
            int wid = LoginDataResponse.getData().getWid();
            if (wid > 0) {

                Glide.with(this)
                        .load(ApplicationConstant.INSTANCE.baseAppIconUrl + wid + "/logo.png")
                        .apply(requestOptions)
                        .into(logoIv);
            }
        }
    }


    void setUiData() {

        // statusMsg.setText(intentMsg);

        Glide.with(this)
                .load(ApplicationConstant.INSTANCE.basebankLogoUrl + intentOpImage + ".png")
                .apply(requestOptions)
                .into(operatorImage);
        opTv.setText(intentOpName + "");
        numTv.setText(intentNum + "");

        if (intentDeviceName != null && !intentDeviceName.isEmpty()) {
            deviceView.setVisibility(View.VISIBLE);
            deviceTv.setText(intentDeviceName + "");
        } else {
            deviceView.setVisibility(View.GONE);
        }
        balTv.setText(intentBalance + "");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy hh:mm aa");

        try {
            String dateStr = sdfDate.format(new Date());
            dateTv.setText(dateStr);
        } catch (Exception e) {

        }
        setCompanyDetail(ApiFintechUtilMethods.INSTANCE.getCompanyProfile(mAppPreferences));
        setOutletDetail();
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

            ApiFintechUtilMethods.INSTANCE.GetCompanyProfile(this, null, LoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, object -> {
                AppUserListResponse companyData1 = (AppUserListResponse) object;
                if (companyData1 != null && companyData1.getCompanyProfile() != null) {
                    setCompanyDetail(companyData1);
                }
            });
        }
    }

    void setOutletDetail() {


        String outletDetailStr = "";
        if (LoginDataResponse.getData().getName() != null && !LoginDataResponse.getData().getName().isEmpty()) {
            outletDetailStr = outletDetailStr + "Name : " + LoginDataResponse.getData().getName();
        }
        if (LoginDataResponse.getData().getOutletName() != null && !LoginDataResponse.getData().getOutletName().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Shop Name : " + LoginDataResponse.getData().getOutletName();
        }
        if (LoginDataResponse.getData().getMobileNo() != null && !LoginDataResponse.getData().getMobileNo().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Contact No : " + LoginDataResponse.getData().getMobileNo();
        }
        if (LoginDataResponse.getData().getEmailID() != null && !LoginDataResponse.getData().getEmailID().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Email : " + LoginDataResponse.getData().getEmailID();
        }
        if (LoginDataResponse.getData().getAddress() != null && !LoginDataResponse.getData().getAddress().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Address : " + LoginDataResponse.getData().getAddress();
        }
        outletDetail.setText(outletDetailStr);

    }

    public void shareit(boolean isWhatsapp) {
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
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
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
