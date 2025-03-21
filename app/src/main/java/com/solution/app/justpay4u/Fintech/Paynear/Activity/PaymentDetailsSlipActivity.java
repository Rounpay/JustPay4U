package com.solution.app.justpay4u.Fintech.Paynear.Activity;

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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pnsol.sdk.vo.response.TransactionStatusResponse;
import com.solution.app.justpay4u.Api.Fintech.Object.ReceiptObject;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.Fintech.MicroAtm.Adapter.ReceiptDetailListAdapter;
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

public class PaymentDetailsSlipActivity extends AppCompatActivity {
    TextView titleTv;
    private LinearLayout shareView;
    private AppCompatImageView statusIcon;
    private AppCompatTextView statusTv;
    private AppCompatTextView statusMsg;
    private LinearLayout addressDetailsView;
    private AppCompatTextView companyName;
    private AppCompatTextView address;
    private RecyclerView recyclerView;
    private AppCompatTextView outletDetail;
    private LinearLayout btRepeat;
    private LinearLayout btShare;
    private LinearLayout btWhatsapp;
    private AppCompatImageView closeIv;


    private AppPreferences mAppPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_paynear_payment_details_slip);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);


            findViews();
            setUiData();
            closeIv.setOnClickListener(v -> finish());


            btShare.setOnClickListener(v -> shareit(false));

            btRepeat.setOnClickListener(v -> finish());

            if (Utility.INSTANCE.isPackageInstalled("com.whatsapp", getPackageManager())) {
                btWhatsapp.setVisibility(View.VISIBLE);
                btWhatsapp.setOnClickListener(view -> shareit(true));
            } else {
                btWhatsapp.setVisibility(View.GONE);
            }
        });
    }


    private void findViews() {
        shareView = (LinearLayout) findViewById(R.id.shareView);
        titleTv = findViewById(R.id.titleTv);
        statusIcon = (AppCompatImageView) findViewById(R.id.statusIcon);
        statusTv = (AppCompatTextView) findViewById(R.id.statusTv);
        statusMsg = (AppCompatTextView) findViewById(R.id.statusMsg);
        addressDetailsView = (LinearLayout) findViewById(R.id.addressDetailsView);
        companyName = (AppCompatTextView) findViewById(R.id.companyName);
        address = (AppCompatTextView) findViewById(R.id.address);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        outletDetail = (AppCompatTextView) findViewById(R.id.outletDetail);
        btRepeat = (LinearLayout) findViewById(R.id.bt_repeat);
        btShare = (LinearLayout) findViewById(R.id.bt_share);
        btWhatsapp = (LinearLayout) findViewById(R.id.bt_whatsapp);
        closeIv = (AppCompatImageView) findViewById(R.id.closeIv);

    }


    void setUiData() {
        TransactionStatusResponse txnResponse = (TransactionStatusResponse) getIntent().getSerializableExtra("txnResponse");
        String referenceNumber = getIntent().getStringExtra("referenceNumber");
        String rrn = getIntent().getStringExtra("rrn");

        if (txnResponse != null) {
            statusTv.setText(txnResponse.getTransactionStatus() + "");
            if (txnResponse.getTransactionStatus() != null &&
                    txnResponse.getTransactionStatus().toLowerCase().contains("approved")) {
                statusIcon.setImageResource(R.drawable.ic_check_mark);
                ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.green));
                statusTv.setTextColor(getResources().getColor(R.color.green));

                if (txnResponse.getMerchantRefNo() != null && !txnResponse.getMerchantRefNo().isEmpty()) {
                    statusMsg.setText("Transaction with Merchant Ref No : " + txnResponse.getMerchantRefNo() + " completed successfully");
                } else if (txnResponse.getMerchantId() != 0) {
                    statusMsg.setText("Transaction with Merchant Id : " + txnResponse.getMerchantId() + " completed successfully");
                } else if (txnResponse.getRrn() != null && !txnResponse.getRrn().isEmpty()) {
                    statusMsg.setText("Transaction with RRN Number : " + txnResponse.getRrn() + " completed successfully");
                } else if (rrn != null && !rrn.isEmpty()) {
                    statusMsg.setText("Transaction with RRN Number : " + rrn + " completed successfully");
                } else {
                    statusMsg.setText("Transaction completed successfully");
                }
            } else if (txnResponse.getTransactionStatus() != null &&
                    txnResponse.getTransactionStatus().toLowerCase().contains("pending")) {
                statusIcon.setImageResource(R.drawable.ic_pending_outline);
                ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.color_orange));
                statusTv.setTextColor(getResources().getColor(R.color.color_orange));

                if (txnResponse.getMerchantRefNo() != null && !txnResponse.getMerchantRefNo().isEmpty()) {
                    statusMsg.setText("Transaction with Merchant Ref No : " + txnResponse.getMerchantRefNo() + " under process, please wait 24 to 48 Hour for confirmation");
                } else if (txnResponse.getMerchantId() != 0) {
                    statusMsg.setText("Transaction with Merchant Id : " + txnResponse.getMerchantId() + " under process, please wait 24 to 48 Hour for confirmation");
                } else if (txnResponse.getRrn() != null && !txnResponse.getRrn().isEmpty()) {
                    statusMsg.setText("Transaction with RRN Number : " + txnResponse.getRrn() + " under process, please wait 24 to 48 Hour for confirmation");
                } else if (rrn != null && !rrn.isEmpty()) {
                    statusMsg.setText("Transaction with RRN Number : " + rrn + " under process, please wait 24 to 48 Hour for confirmation");
                } else {
                    statusMsg.setText("Transaction under process, please wait 24 to 48 Hour for confirmation");
                }
            } else {
                statusIcon.setImageResource(R.drawable.ic_cross_mark);
                ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.color_red));
                statusTv.setTextColor(getResources().getColor(R.color.color_red));
                if (txnResponse != null) {
                    if (txnResponse.getMerchantRefNo() != null && !txnResponse.getMerchantRefNo().isEmpty()) {
                        statusMsg.setText("Sorry, transaction failed with Merchant Ref No : " + txnResponse.getMerchantRefNo() + ", please try after some time");
                    } else if (txnResponse.getMerchantId() != 0) {
                        statusMsg.setText("Sorry, transaction failed with Merchant Id : " + txnResponse.getMerchantId() + ", please try after some time");
                    } else if (txnResponse.getRrn() != null && !txnResponse.getRrn().isEmpty()) {
                        statusMsg.setText("Sorry, transaction failed with RRN Number : " + txnResponse.getRrn() + ", please try after some time");
                    } else if (rrn != null && !rrn.isEmpty()) {
                        statusMsg.setText("Sorry, transaction failed with RRN Number : " + rrn + ", please try after some time");
                    } else {
                        statusMsg.setText("Sorry, transaction failed, please try after some time");
                    }
                } else {
                    statusMsg.setText("Sorry, transaction failed, please try after some time");
                }

            }

            ArrayList<ReceiptObject> mReceiptObjects = new ArrayList<>();
            if (txnResponse.getAmount() != 0) {
                mReceiptObjects.add(new ReceiptObject("Transaction Amount", Utility.INSTANCE.formatedAmountWithRupees(txnResponse.getAmount() + "")));
            }
            if (txnResponse.getCurrentBalance() != 0) {
                mReceiptObjects.add(new ReceiptObject("Current Balance", Utility.INSTANCE.formatedAmountWithRupees(txnResponse.getCurrentBalance() + "")));
            }
            if (txnResponse.getLedgerBalance() != 0) {
                mReceiptObjects.add(new ReceiptObject("Ledger Balance", Utility.INSTANCE.formatedAmountWithRupees(txnResponse.getLedgerBalance() + "")));
            }
            if (txnResponse.getMerchantId() != 0) {
                mReceiptObjects.add(new ReceiptObject("Merchant Id", txnResponse.getMerchantId() + ""));
            }
            if (txnResponse.getMerchantRefNo() != null && !txnResponse.getMerchantRefNo().isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Merchant Ref No", txnResponse.getMerchantRefNo()));
            }
            if (txnResponse.getPaymentMethod() != null && !txnResponse.getPaymentMethod().isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Payment Mode", txnResponse.getPaymentMethod()));
            }
            if (txnResponse.getRrn() != null && !txnResponse.getRrn().isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("RRN Number", txnResponse.getRrn()));
            } else if (rrn != null && !rrn.isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("RRN Number", rrn));
            }
           /* if (txnResponse.getReferenceNumber() != null && !txnResponse.getReferenceNumber().isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Reference Number", txnResponse.getReferenceNumber()));
            } else if (referenceNumber != null && !referenceNumber.isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Reference Number", referenceNumber));
            }*/
            if (txnResponse.getCardBrand() != null && !txnResponse.getCardBrand().isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Card Brand", txnResponse.getCardBrand()));
            }
            if (txnResponse.getCardType() != null && !txnResponse.getCardType().isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Card Type", txnResponse.getCardType()));
            }
            if (txnResponse.getCardHolderName() != null && !txnResponse.getCardHolderName().isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Card Holder Name", txnResponse.getCardHolderName()));
            }
            if (txnResponse.getCardNumber() != null && !txnResponse.getCardNumber().isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Card Number", txnResponse.getCardNumber()));
            }

            /*if (txnResponse.getMerchantRefNo() != null && !txnResponse.getMerchantRefNo().isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Merchant Ref. No.", txnResponse.getMerchantRefNo()));
            }*/
            if (txnResponse.getTerminalSerialNo() != null && !txnResponse.getTerminalSerialNo().isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Terminal Serial No.", txnResponse.getTerminalSerialNo()));
            }
            if (txnResponse.getTransactionDateNTime() != null && !txnResponse.getTransactionDateNTime().isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Payment Date", Utility.INSTANCE.formatedDateTimeOfSlash(txnResponse.getTransactionDateNTime())));
            } else {


                SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy hh:mm aa");

                try {
                    String dateStr = sdfDate.format(new Date());
                    mReceiptObjects.add(new ReceiptObject("Payment Date", dateStr + ""));
                } catch (Exception e) {

                }
            }

            recyclerView.setAdapter(new ReceiptDetailListAdapter(mReceiptObjects, this));
        } else {
            statusIcon.setImageResource(R.drawable.ic_cross_mark);
            ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.color_red));
            statusTv.setTextColor(getResources().getColor(R.color.color_red));
            statusTv.setText("Failed");
            titleTv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            statusMsg.setText("Sorry, Transaction Data not found");

        }
        setCompanyDetail(ApiFintechUtilMethods.INSTANCE.getCompanyProfile(mAppPreferences));
        setOutletDetail();
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
            LoginResponse mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
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

    void setOutletDetail() {

        LoginResponse LoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);

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
                "Payment Detail Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }


    void openWhatsapp(Uri myUri) {

        try {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Payment Detail Receipt");
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
