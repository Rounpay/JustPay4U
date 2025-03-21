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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.ReceiptObject;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GenerateDepositOTPResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.MicroAtm.Adapter.ReceiptDetailListAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class AEPSDepositStatusActivity extends AppCompatActivity {

    private LinearLayout shareView;
    private AppCompatImageView statusIcon;
    private AppCompatTextView tv_txstatus;
    private AppCompatTextView txnId;
    private AppCompatTextView companyName;
    private AppCompatTextView address;
    private AppCompatTextView amountTv;
    private RecyclerView recyclerView;
    private AppCompatTextView outletDetail;
    private LinearLayout bt_repeat;
    private LinearLayout bt_share;
    private LinearLayout bt_whatsapp;
    private AppCompatImageView rl_cancel;
    private AppPreferences mAppPreferences;
    private LoginResponse LoginDataResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_aeps_deposit_status);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            LoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            getIds();

            setUiData();

        });
    }

    private void getIds() {
        shareView = (LinearLayout) findViewById(R.id.shareView);
        statusIcon = (AppCompatImageView) findViewById(R.id.statusIcon);
        tv_txstatus = (AppCompatTextView) findViewById(R.id.tv_txstatus);
        txnId = (AppCompatTextView) findViewById(R.id.txnId);
        companyName = (AppCompatTextView) findViewById(R.id.companyName);
        address = (AppCompatTextView) findViewById(R.id.address);
        amountTv = (AppCompatTextView) findViewById(R.id.amountTv);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        outletDetail = (AppCompatTextView) findViewById(R.id.outletDetail);
        bt_repeat = (LinearLayout) findViewById(R.id.bt_repeat);
        bt_share = (LinearLayout) findViewById(R.id.bt_share);
        bt_whatsapp = (LinearLayout) findViewById(R.id.bt_whatsapp);
        rl_cancel = (AppCompatImageView) findViewById(R.id.rl_cancel);
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

        rl_cancel.setOnClickListener(view -> {
            setResult(RESULT_OK);
            finish();
        });


        bt_share.setOnClickListener(view -> shareit(false));

        bt_repeat.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

    }

    void setUiData() {
        setOutletDetail();
        setCompanyDetail(ApiFintechUtilMethods.INSTANCE.getCompanyProfile(mAppPreferences));


        ArrayList<ReceiptObject> mReceiptObjects = new ArrayList<>();

        GenerateDepositOTPResponse mGenerateDepositOTPResponse = getIntent().getParcelableExtra("data");
        String txnAmt = getIntent().getStringExtra("txnAmt");
        String accountNum = getIntent().getStringExtra("accountNum");

        if (mGenerateDepositOTPResponse != null) {
            if (mGenerateDepositOTPResponse.getStatus() == 2) {
                tv_txstatus.setText("" + mGenerateDepositOTPResponse.getMsg());
                if (mGenerateDepositOTPResponse.getTransactionID() != null && !mGenerateDepositOTPResponse.getTransactionID().isEmpty()) {
                    txnId.setText("Amount has been successfully deposit with transaction id " + mGenerateDepositOTPResponse.getTransactionID());
                } else if (mGenerateDepositOTPResponse.getLiveID() != null && !mGenerateDepositOTPResponse.getLiveID().isEmpty()) {
                    txnId.setText("Amount has been successfully deposit with live id " + mGenerateDepositOTPResponse.getLiveID());
                } else {
                    txnId.setText("Amount has been successfully deposit in account number " + accountNum);
                }
                statusIcon.setImageResource(R.drawable.ic_check_mark);
                tv_txstatus.setTextColor(getResources().getColor(R.color.green));
                bt_repeat.setVisibility(View.GONE);

            } else if (mGenerateDepositOTPResponse.getStatus() == 1) {
                tv_txstatus.setText("" + mGenerateDepositOTPResponse.getMsg());
                if (mGenerateDepositOTPResponse.getTransactionID() != null && !mGenerateDepositOTPResponse.getTransactionID().isEmpty()) {
                    txnId.setText("Your request has been proccessed with transaction id " + mGenerateDepositOTPResponse.getTransactionID() + ", Please wait 48 hour to confirmation");
                } else if (mGenerateDepositOTPResponse.getLiveID() != null && !mGenerateDepositOTPResponse.getLiveID().isEmpty()) {
                    txnId.setText("Your request has been proccessed with live id " + mGenerateDepositOTPResponse.getLiveID() + ", Please wait 48 hour to confirmation");
                } else {
                    txnId.setText("Your request has been proccessed, Please wait 48 hour to confirmation");
                }
                statusIcon.setImageResource(R.drawable.ic_pending_outline);
                tv_txstatus.setTextColor(getResources().getColor(R.color.color_orange));
                bt_repeat.setVisibility(View.GONE);

            } else {
                tv_txstatus.setText("" + mGenerateDepositOTPResponse.getMsg());
                if (mGenerateDepositOTPResponse.getTransactionID() != null && !mGenerateDepositOTPResponse.getTransactionID().isEmpty()) {
                    txnId.setText("Your deposit request has been failed with transaction id " + mGenerateDepositOTPResponse.getTransactionID() + ", Please check your bank details or try again");
                } else if (mGenerateDepositOTPResponse.getLiveID() != null && !mGenerateDepositOTPResponse.getLiveID().isEmpty()) {
                    txnId.setText("Your deposit request has been failed with live id " + mGenerateDepositOTPResponse.getLiveID() + ", Please check your bank details or try again");
                } else {
                    txnId.setText("Your deposit request has been failed,  Please check your bank details or try again");
                }
                statusIcon.setImageResource(R.drawable.ic_cross_mark);
                tv_txstatus.setTextColor(getResources().getColor(R.color.color_red));
                bt_repeat.setVisibility(View.GONE);
            }


            if (accountNum != null && !accountNum.isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Account Number", accountNum + ""));
            }


            if (mGenerateDepositOTPResponse.getBeneficiaryName() != null && !mGenerateDepositOTPResponse.getBeneficiaryName().isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Beneficiary Name", mGenerateDepositOTPResponse.getBeneficiaryName() + ""));
            }
            if (txnAmt != null && !txnAmt.isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Deposit Amount", Utility.INSTANCE.formatedAmountWithRupees(txnAmt + "")));
                amountTv.setText(Utility.INSTANCE.formatedAmountWithRupees(txnAmt + ""));
            }
            if (mGenerateDepositOTPResponse.getBalance() != 0) {
                mReceiptObjects.add(new ReceiptObject("Balance", Utility.INSTANCE.formatedAmountWithRupees(mGenerateDepositOTPResponse.getBalance() + "")));
            }
            if (mGenerateDepositOTPResponse.getLiveID() != null && !mGenerateDepositOTPResponse.getLiveID().isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Live Id", mGenerateDepositOTPResponse.getLiveID() + ""));
            }
            if (mGenerateDepositOTPResponse.getTransactionID() != null && !mGenerateDepositOTPResponse.getTransactionID().isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Transaction Id", mGenerateDepositOTPResponse.getTransactionID() + ""));
            }
            if (mGenerateDepositOTPResponse.getServerDate() != null && !mGenerateDepositOTPResponse.getServerDate().isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Transaction Date", Utility.INSTANCE.formatedDate2(mGenerateDepositOTPResponse.getServerDate() + "")));
            }

        }
        recyclerView.setAdapter(new ReceiptDetailListAdapter(mReceiptObjects, this));
        if (Utility.INSTANCE.isPackageInstalled("com.whatsapp", getPackageManager())) {
            bt_whatsapp.setVisibility(View.VISIBLE);
            bt_whatsapp.setOnClickListener(view -> shareit(true));
        } else {
            bt_whatsapp.setVisibility(View.GONE);
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
                "Deposit Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }


    void openWhatsapp(Uri myUri) {

        try {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Deposit Receipt");
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

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
