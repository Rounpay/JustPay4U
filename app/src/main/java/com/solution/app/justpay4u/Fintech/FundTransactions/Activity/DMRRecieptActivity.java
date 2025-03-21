package com.solution.app.justpay4u.Fintech.FundTransactions.Activity;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.ListOblect;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.DMTReceiptResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.FundTransactions.Adapter.DmrRecieptAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DMRRecieptActivity extends AppCompatActivity {
    String flag;
    TextView Name, companyDetail, statusTv, refrenceIdLabel, companyName, BankName, senderNo, AccountNo, ifsc, Date, outletDetail, mode;
    ImageView cancel, statusIcon;
    RecyclerView list;
    LinearLayout shareView;
    DMTReceiptResponse dmtReceiptResponse;
    List<ListOblect> listOblects;
    List<ListOblect> listOblectsnew;
    DmrRecieptAdapter mAdapter;
    View btShare, btWhatsapp;
    CustomLoader mCustomLoader;
    private AppPreferences mAppPreferences;
    private LoginResponse mLoginDataResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler(Looper.myLooper()).post(() -> {
            setContentView(R.layout.activity_dmr_reciept);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mCustomLoader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);

            dmtReceiptResponse =  getIntent().getParcelableExtra("response");
            flag = getIntent().getStringExtra("flag");
            getID();
            try {
                if (flag.equals("All")) {
                    dataparse();

                } else {

                    dataparse2();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setOutletDetail();
            setCompanyDetail(ApiFintechUtilMethods.INSTANCE.getCompanyProfile(mAppPreferences));
        });

    }

    private void getID() {
        outletDetail = findViewById(R.id.outletDetail);
        Name = findViewById(R.id.Name);
        companyName = findViewById(R.id.companyName);
        companyDetail = findViewById(R.id.companyDetail);
        statusTv = findViewById(R.id.statusTv);
        refrenceIdLabel = findViewById(R.id.liveId);
        BankName = findViewById(R.id.BankName);
        statusIcon = findViewById(R.id.statusIcon);
        senderNo = findViewById(R.id.senderNo);
        shareView = findViewById(R.id.shareView);
        AccountNo = findViewById(R.id.AccountNo);
        mode = findViewById(R.id.mode);
        Date = findViewById(R.id.Date);
        ifsc = findViewById(R.id.ifsc);
        list = findViewById(R.id.list);
        btShare = findViewById(R.id.bt_share);
        btWhatsapp = findViewById(R.id.bt_whatsapp);
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> finish());
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        btShare.setOnClickListener(v -> shareit(false));
        if (Utility.INSTANCE.isPackageInstalled("com.whatsapp", getPackageManager())) {
            btWhatsapp.setVisibility(View.VISIBLE);
            btWhatsapp.setOnClickListener(view -> shareit(true));
        } else {
            btWhatsapp.setVisibility(View.GONE);
        }


        ImageView logoIv = findViewById(R.id.appLogo);
        RequestOptions requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
        String appLogoUrl = ApiFintechUtilMethods.INSTANCE.getAppLogoUrl(mAppPreferences);
        if (appLogoUrl != null && !appLogoUrl.isEmpty()) {
            Glide.with(this)
                    .load(appLogoUrl)
                    .apply(requestOptions)
                    .into(logoIv);
        } else {
            int wid = mLoginDataResponse.getData().getWid();
            if (wid > 0) {

                Glide.with(this)
                        .load(ApplicationConstant.INSTANCE.baseAppIconUrl + wid + "/logo.png")
                        .apply(requestOptions)
                        .into(logoIv);
            }
        }
    }


    void setCompanyDetail(AppUserListResponse companyData) {

        if (companyData != null && companyData.getCompanyProfile() != null) {
            companyName.setText(companyData.getCompanyProfile().getName() + "");
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
            companyDetail.setText(company);
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


    private void dataparse() throws ParseException {


        if (dmtReceiptResponse != null && dmtReceiptResponse.getTransactionDetail() != null) {
            listOblects = dmtReceiptResponse.getTransactionDetail().getLists();
            if (listOblects != null && listOblects.size() > 0) {
                if (flag.equals("All")) {
                    mAdapter = new DmrRecieptAdapter(listOblects, DMRRecieptActivity.this);
                } else {
                    listOblectsnew = new ArrayList<>();
                    for (int i = 0; i < listOblects.size(); i++) {
                        String status = listOblects.get(i).getStatuscode() + "";
                        if (status.equals("2")) {
                            listOblectsnew.add(listOblects.get(i));
                        }
                    }
                    mAdapter = new DmrRecieptAdapter(listOblectsnew, DMRRecieptActivity.this);
                }
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                list.setLayoutManager(mLayoutManager);
                list.setItemAnimator(new DefaultItemAnimator());
                list.setAdapter(mAdapter);
            }
            Name.setText(dmtReceiptResponse.getTransactionDetail().getBeneName());
            BankName.setText(dmtReceiptResponse.getTransactionDetail().getBankName());
            mode.setText(dmtReceiptResponse.getTransactionDetail().getChannel());
            senderNo.setText(dmtReceiptResponse.getTransactionDetail().getSenderNo());
            AccountNo.setText(dmtReceiptResponse.getTransactionDetail().getAccount());
            Date.setText(Utility.INSTANCE.formatedDateYMD(dmtReceiptResponse.getTransactionDetail().getEntryDate() + ""));
            ifsc.setText(dmtReceiptResponse.getTransactionDetail().getIfsc());
            statusTv.setText(dmtReceiptResponse.getTransactionDetail().getStatus() + "");
            if (dmtReceiptResponse.getTransactionDetail().getStatuscode() == 2) {
                statusIcon.setImageResource(R.drawable.ic_check_mark);
                statusTv.setTextColor(getResources().getColor(R.color.green));
            } else if (dmtReceiptResponse.getTransactionDetail().getStatuscode() == 3) {
                refrenceIdLabel.setText("Reason");
                statusIcon.setImageResource(R.drawable.ic_cross_mark);
                statusTv.setTextColor(getResources().getColor(R.color.color_red));
            } else if (dmtReceiptResponse.getTransactionDetail().getStatuscode() == 1) {
                statusIcon.setImageResource(R.drawable.ic_pending_outline);
                statusTv.setTextColor(getResources().getColor(R.color.color_orange));
            } else {
                statusIcon.setImageResource(R.drawable.ic_refund_outline);
                statusTv.setTextColor(getResources().getColor(R.color.dark_blue));
            }
            // Email.setText(dmtReceiptResponse.getTransactionDetail().getEmail());
        }
    }

    private void dataparse2() {

        listOblectsnew = new ArrayList<>();
        if (dmtReceiptResponse != null && dmtReceiptResponse.getTransactionDetail() != null) {
            listOblects = dmtReceiptResponse.getTransactionDetail().getLists();
            if (listOblects != null) {
                for (int i = 0; i < listOblects.size(); i++) {
                    String status = listOblects.get(i).getStatuscode() + "";
                    if (status.equals("2")) {
                        listOblectsnew.add(listOblects.get(i));
                    }
                }
            }
            if (listOblectsnew.size() > 0) {
                mAdapter = new DmrRecieptAdapter(listOblectsnew, DMRRecieptActivity.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                list.setLayoutManager(mLayoutManager);
                list.setItemAnimator(new DefaultItemAnimator());
                list.setAdapter(mAdapter);
                Name.setText(dmtReceiptResponse.getTransactionDetail().getBeneName());
                mode.setText(dmtReceiptResponse.getTransactionDetail().getChannel());
                BankName.setText(dmtReceiptResponse.getTransactionDetail().getBankName());
                senderNo.setText(dmtReceiptResponse.getTransactionDetail().getSenderNo());
                AccountNo.setText(dmtReceiptResponse.getTransactionDetail().getAccount());
                Date.setText(Utility.INSTANCE.formatedDateYMD(dmtReceiptResponse.getTransactionDetail().getEntryDate() + ""));
                ifsc.setText(dmtReceiptResponse.getTransactionDetail().getIfsc());
                statusTv.setText(dmtReceiptResponse.getTransactionDetail().getStatus() + "");
                if (dmtReceiptResponse.getTransactionDetail().getStatuscode() == 2) {
                    statusIcon.setImageResource(R.drawable.ic_check_mark);
                } else if (dmtReceiptResponse.getTransactionDetail().getStatuscode() == 3) {
                    statusIcon.setImageResource(R.drawable.ic_cross_mark);
                } else if (dmtReceiptResponse.getTransactionDetail().getStatuscode() == 1) {
                    statusIcon.setImageResource(R.drawable.ic_pending_outline);
                } else {
                    statusIcon.setImageResource(R.drawable.ic_refund_outline);
                }
                // Email.setText(dmtReceiptResponse.getTransactionDetail().getEmail());

            } else {
                ApiFintechUtilMethods.INSTANCE.Error(this, "No Transaction to print");
            }
        }
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
       /* File picFile = null;
        Bitmap myBitmap = null;

        // View v1 = getWindow().getDecorView().getRootView();
        shareView.setDrawingCacheEnabled(true);
        myBitmap = Bitmap.createBitmap(shareView.getDrawingCache());
        shareView.setDrawingCacheEnabled(false);*/
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

   /* public void saveBitmap(Bitmap bitmap, boolean isWhatsapp) {
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
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "DMR Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Receipt");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }

    void openWhatsapp(Uri myUri) {

        try {

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "DMR Receipt");
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
