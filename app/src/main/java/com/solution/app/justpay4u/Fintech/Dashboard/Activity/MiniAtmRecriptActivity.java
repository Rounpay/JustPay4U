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
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MiniAtmRecriptActivity extends AppCompatActivity {
    private LinearLayout shareView;
    private AppCompatTextView tvBankMsg;
    private AppCompatTextView companyNameTv;
    private AppCompatTextView address;
    private LinearLayout txnTypeView;
    private AppCompatTextView txnTypeTv;
    private LinearLayout txnIdView;
    private AppCompatTextView txnIdTv;
    private LinearLayout midView;
    private AppCompatTextView midTv;
    private LinearLayout clientRefView;
    private AppCompatTextView clientRefTv;
    private LinearLayout vendorIdView;
    private AppCompatTextView vendorIdv;
    private LinearLayout stanNoView;
    private AppCompatTextView stanNoTv;
    private LinearLayout invoiceNoView;
    private AppCompatTextView invoiceNoTv;
    private LinearLayout rrnView;
    private AppCompatTextView rrnTv;
    private LinearLayout cardNoView;
    private AppCompatTextView cardNoTv;
    private LinearLayout balanceView;
    private AppCompatTextView balanceTv;
    private LinearLayout amountView;
    private AppCompatTextView amountTv;
    private LinearLayout defineValue1View;
    private AppCompatTextView defineValue1Tv;
    private LinearLayout defineValue2View;
    private AppCompatTextView defineValue2Tv;
    private LinearLayout defineValue3View;
    private AppCompatTextView defineValue3Tv;
    private LinearLayout defineValue4View;
    private AppCompatTextView defineValue4Tv;
    private LinearLayout dateView;
    private AppCompatTextView dateTv;
    private AppCompatTextView outletDetail;
    private LinearLayout btShare;
    private LinearLayout btWhatsapp;
    private AppPreferences mAppPreferences;
    private CustomLoader mCustomLoader;
    private LoginResponse LoginDataResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_mini_atm_recript);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            LoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            mCustomLoader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            findViews();
            getIntentData();
        });

    }

    private void findViews() {
        shareView = (LinearLayout) findViewById(R.id.shareView);
        tvBankMsg = (AppCompatTextView) findViewById(R.id.tv_bank_msg);
        companyNameTv = (AppCompatTextView) findViewById(R.id.companyName);
        address = (AppCompatTextView) findViewById(R.id.address);
        txnTypeView = (LinearLayout) findViewById(R.id.txnTypeView);
        txnTypeTv = (AppCompatTextView) findViewById(R.id.txnTypeTv);
        txnIdView = (LinearLayout) findViewById(R.id.txnIdView);
        txnIdTv = (AppCompatTextView) findViewById(R.id.txnIdTv);
        midView = (LinearLayout) findViewById(R.id.midView);
        midTv = (AppCompatTextView) findViewById(R.id.midTv);
        clientRefView = (LinearLayout) findViewById(R.id.clientRefView);
        clientRefTv = (AppCompatTextView) findViewById(R.id.clientRefTv);
        vendorIdView = (LinearLayout) findViewById(R.id.vendorIdView);
        vendorIdv = (AppCompatTextView) findViewById(R.id.vendorIdv);
        stanNoView = (LinearLayout) findViewById(R.id.stanNoView);
        stanNoTv = (AppCompatTextView) findViewById(R.id.stanNoTv);
        invoiceNoView = (LinearLayout) findViewById(R.id.invoiceNoView);
        invoiceNoTv = (AppCompatTextView) findViewById(R.id.invoiceNoTv);
        rrnView = (LinearLayout) findViewById(R.id.rrnView);
        rrnTv = (AppCompatTextView) findViewById(R.id.rrnTv);
        cardNoView = (LinearLayout) findViewById(R.id.cardNoView);
        cardNoTv = (AppCompatTextView) findViewById(R.id.cardNoTv);
        balanceView = (LinearLayout) findViewById(R.id.balanceView);
        balanceTv = (AppCompatTextView) findViewById(R.id.balanceTv);
        amountView = (LinearLayout) findViewById(R.id.amountView);
        amountTv = (AppCompatTextView) findViewById(R.id.amountTv);
        defineValue1View = (LinearLayout) findViewById(R.id.defineValue1View);
        defineValue1Tv = (AppCompatTextView) findViewById(R.id.defineValue1Tv);
        defineValue2View = (LinearLayout) findViewById(R.id.defineValue2View);
        defineValue2Tv = (AppCompatTextView) findViewById(R.id.defineValue2Tv);
        defineValue3View = (LinearLayout) findViewById(R.id.defineValue3View);
        defineValue3Tv = (AppCompatTextView) findViewById(R.id.defineValue3Tv);
        defineValue4View = (LinearLayout) findViewById(R.id.defineValue4View);
        defineValue4Tv = (AppCompatTextView) findViewById(R.id.defineValue4Tv);
        dateView = (LinearLayout) findViewById(R.id.dateView);
        dateTv = (AppCompatTextView) findViewById(R.id.dateTv);
        outletDetail = (AppCompatTextView) findViewById(R.id.outletDetail);
        btShare = (LinearLayout) findViewById(R.id.bt_share);
        btWhatsapp = (LinearLayout) findViewById(R.id.bt_whatsapp);

        ImageView logoIv = findViewById(R.id.appLogo);
        RequestOptions requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
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

    private void getIntentData() {
        String requesttxn = getIntent().getStringExtra("requesttxn");//Type of transaction
        String bankremarks = getIntent().getStringExtra("msg");//Bank message
        String refstan = getIntent().getStringExtra("refstan");// Mahagram Stan Number
        String cardno = getIntent().getStringExtra("cardno");//Atm card number
        String date = getIntent().getStringExtra("date");//Date of transaction
        String balance = getIntent().getStringExtra("amount");//Account Balance
        String invoicenumber = getIntent().getStringExtra("invoicenumber");//Invoice Number
        String mid = getIntent().getStringExtra("mid");//Mid
        String tid = getIntent().getStringExtra("tid");//Tid
        String clientrefid = getIntent().getStringExtra("clientrefid");//Your reference Id
        String vendorid = getIntent().getStringExtra("vendorid");//Your define value
        String udf1 = getIntent().getStringExtra("udf1");//Your define value
        String udf2 = getIntent().getStringExtra("udf2");//Your define value
        String udf3 = getIntent().getStringExtra("udf3");//Your define value
        String udf4 = getIntent().getStringExtra("udf4");//Your define value
        String txnamount = getIntent().getStringExtra("txnamount");//Transaction amount (0 in case of balance inquiry and transaction amount in cash withdrawal)
        String rrn = getIntent().getStringExtra("rrn");//Bank RRN number
        String respcode = getIntent().getStringExtra("respcode");//Response code of bank( “00” for success transaction)


        if (!rrn.isEmpty()) {
            rrnView.setVisibility(View.VISIBLE);
            rrnTv.setText(rrn);
        } else {
            rrnView.setVisibility(View.GONE);
        }
        if (!requesttxn.isEmpty()) {
            txnTypeView.setVisibility(View.VISIBLE);
            txnTypeTv.setText(requesttxn);
        } else {
            txnTypeView.setVisibility(View.GONE);
        }
        if (!bankremarks.isEmpty()) {
            tvBankMsg.setVisibility(View.VISIBLE);
            tvBankMsg.setText(bankremarks);
        } else {
            tvBankMsg.setVisibility(View.GONE);
        }
        if (!refstan.isEmpty()) {
            stanNoView.setVisibility(View.VISIBLE);
            stanNoTv.setText(refstan);
        } else {
            stanNoView.setVisibility(View.GONE);
        }
        if (!cardno.isEmpty()) {
            cardNoView.setVisibility(View.VISIBLE);
            cardNoTv.setText(cardno);
        } else {
            cardNoView.setVisibility(View.GONE);
        }
        if (!date.isEmpty()) {
            dateView.setVisibility(View.VISIBLE);
            dateTv.setText(date);
        } else {
            dateView.setVisibility(View.GONE);
        }
        if (!txnamount.isEmpty()) {
            amountView.setVisibility(View.VISIBLE);
            amountTv.setText(Utility.INSTANCE.formatedAmountWithRupees(txnamount));
        } else {
            amountView.setVisibility(View.GONE);
        }
        if (!balance.isEmpty()) {
            balanceView.setVisibility(View.VISIBLE);
            balanceTv.setText(Utility.INSTANCE.formatedAmountWithRupees(balance));
        } else {
            balanceView.setVisibility(View.GONE);
        }
        if (!invoicenumber.isEmpty()) {
            invoiceNoView.setVisibility(View.VISIBLE);
            invoiceNoTv.setText(invoicenumber);
        } else {
            invoiceNoView.setVisibility(View.GONE);
        }

        if (!mid.isEmpty()) {
            midView.setVisibility(View.VISIBLE);
            midTv.setText(invoicenumber);
        } else {
            midView.setVisibility(View.GONE);
        }

        if (!tid.isEmpty()) {
            txnIdView.setVisibility(View.VISIBLE);
            txnIdTv.setText(tid);
        } else {
            txnIdView.setVisibility(View.GONE);
        }
        if (!clientrefid.isEmpty()) {
            clientRefView.setVisibility(View.VISIBLE);
            clientRefTv.setText(clientrefid);
        } else {
            clientRefView.setVisibility(View.GONE);
        }
        if (!vendorid.isEmpty()) {
            vendorIdView.setVisibility(View.VISIBLE);
            vendorIdv.setText(vendorid);
        } else {
            vendorIdView.setVisibility(View.GONE);
        }
        if (!udf1.isEmpty()) {
            defineValue1View.setVisibility(View.VISIBLE);
            defineValue1Tv.setText(udf1);
        } else {
            defineValue1View.setVisibility(View.GONE);
        }
        if (!udf2.isEmpty()) {
            defineValue2View.setVisibility(View.VISIBLE);
            defineValue2Tv.setText(udf2);
        } else {
            defineValue2View.setVisibility(View.GONE);
        }
        if (!udf3.isEmpty()) {
            defineValue3View.setVisibility(View.VISIBLE);
            defineValue3Tv.setText(udf3);
        } else {
            defineValue3View.setVisibility(View.GONE);
        }
        if (!udf4.isEmpty()) {
            defineValue4View.setVisibility(View.VISIBLE);
            defineValue4Tv.setText(udf4);
        } else {
            defineValue4View.setVisibility(View.GONE);
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
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "Mini ATM Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }


    void openWhatsapp(Uri myUri) {

        try {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Mini ATM Receipt");
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

