package com.solution.app.justpay4u.Fintech.MicroAtm.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fingpay.microatmsdk.utils.Constants;
import com.roundpay.emoneylib.Utils.KeyConstant;
import com.solution.app.justpay4u.Api.Fintech.Object.ReceiptObject;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MicroATMStatusRPActivity extends AppCompatActivity {
    TextView titleTv;
    private LinearLayout shareView;
    private ImageView statusIcon;
    private TextView statusTv;
    private TextView statusMsg;
    private TextView companyName;
    private TextView address;
    private RecyclerView recyclerView;
    private TextView outletDetail;
    private LinearLayout btRepeat;
    private LinearLayout btShare;
    private LinearLayout btWhatsapp;
    private ImageView closeIv;
    private LoginResponse LoginDataResponse;
    private AppPreferences mAppPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_micro_atm_status_rp);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            LoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);

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
        shareView =  findViewById(R.id.shareView);
        titleTv = findViewById(R.id.titleTv);
        statusIcon =  findViewById(R.id.statusIcon);
        statusTv =  findViewById(R.id.statusTv);
        statusMsg =  findViewById(R.id.statusMsg);
        companyName =  findViewById(R.id.companyName);
        address =  findViewById(R.id.address);
        recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        outletDetail =  findViewById(R.id.outletDetail);
        btRepeat =  findViewById(R.id.bt_repeat);
        btShare =  findViewById(R.id.bt_share);
        btWhatsapp =  findViewById(R.id.bt_whatsapp);
        closeIv =  findViewById(R.id.closeIv);
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


    void setUiData() {
        boolean status = getIntent().getBooleanExtra(KeyConstant.TRANS_STATUS, false);
        String response = getIntent().getStringExtra(KeyConstant.MESSAGE);
        double transAmount = getIntent().getDoubleExtra(KeyConstant.TRANS_AMOUNT, 0);
        double balAmount = getIntent().getDoubleExtra(KeyConstant.BALANCE_AMOUNT, 0);
        String bankRrn = getIntent().getStringExtra(KeyConstant.RRN);
        String transType = getIntent().getStringExtra(KeyConstant.TRANS_TYPE);
        int type = getIntent().getIntExtra(KeyConstant.TYPE, 0);
        String cardNum = getIntent().getStringExtra(KeyConstant.CARD_NUM);
        String bankName = getIntent().getStringExtra(KeyConstant.BANK_NAME);
        String cardType = getIntent().getStringExtra(KeyConstant.CARD_TYPE);
        String terminalId = getIntent().getStringExtra(KeyConstant.TERMINAL_ID);
        String fpId = getIntent().getStringExtra(KeyConstant.FP_TRANS_ID);
        String transId = getIntent().getStringExtra(KeyConstant.TRANS_ID);
        String appTid = getIntent().getStringExtra("APP_TID");

        /*-----------------------------------------------------------------*/
        String status_code = getIntent().getStringExtra(KeyConstant.STATUS_CODE);
        String bank_msg = getIntent().getStringExtra(KeyConstant.BANK_MESSAGE);
        String roundpayTransactionId = getIntent().getStringExtra(KeyConstant.RNP_TRANS_ID);
        String roundpayLiveId = getIntent().getStringExtra(KeyConstant.RNP_LIVE_ID);
        String time = getIntent().getStringExtra(KeyConstant.TRANS_TIME);
        String invoiceNum = getIntent().getStringExtra(KeyConstant.INVOICE_NUM);
        String mid = getIntent().getStringExtra(KeyConstant.MID);
        String clientrefid = getIntent().getStringExtra(KeyConstant.CLIENT_REFID);
        String vendorId = getIntent().getStringExtra(KeyConstant.VENDOR_ID);
        String stanNo = getIntent().getStringExtra(KeyConstant.STAN_NO);
        String udf1 = getIntent().getStringExtra(KeyConstant.UDF1);
        String udf2 = getIntent().getStringExtra(KeyConstant.UDF2);
        String udf3 = getIntent().getStringExtra(KeyConstant.UDF3);
        String udf4 = getIntent().getStringExtra(KeyConstant.UDF4);
        /*-----------------------------------------------------------------*/


        if (status) {
            statusIcon.setImageResource(R.drawable.ic_check_mark);
            //   ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.green));
            statusTv.setTextColor(getResources().getColor(R.color.green));
            statusTv.setText("Success");
            if (appTid != null && !appTid.isEmpty()) {
                statusMsg.setText("Transaction with transaction id " + appTid + " completed successfully");
            } else if (roundpayTransactionId != null && !roundpayTransactionId.isEmpty()) {
                statusMsg.setText("Transaction with transaction id " + roundpayTransactionId + " completed successfully");
            } else if (transId != null && !transId.isEmpty()) {
                statusMsg.setText("Transaction with transaction id " + transId + " completed successfully");
            } else if (bankRrn != null && !bankRrn.isEmpty()) {
                statusMsg.setText("Transaction with bank rrn " + bankRrn + " completed successfully");
            } else if (roundpayLiveId != null && !roundpayLiveId.isEmpty()) {
                statusMsg.setText("Transaction with bank rrn " + roundpayLiveId + " completed successfully");
            } else {
                statusMsg.setText("Transaction completed successfully");
            }
        } else {
            if (status_code != null && status_code.equalsIgnoreCase("2")) {
                statusIcon.setImageResource(R.drawable.ic_pending_outline);
                //  ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.color_orange));
                statusTv.setTextColor(getResources().getColor(R.color.color_orange));
                statusTv.setText("Processing");
                if (roundpayTransactionId != null && !roundpayTransactionId.isEmpty()) {
                    statusMsg.setText("Transaction with reference id " + roundpayTransactionId + " under process");
                } else if (transId != null && !transId.isEmpty()) {
                    statusMsg.setText("Transaction with reference id " + transId + " under process");
                } else if (bankRrn != null && !bankRrn.isEmpty()) {
                    statusMsg.setText("Transaction with bank rrn " + bankRrn + " under process");
                } else if (roundpayLiveId != null && !roundpayLiveId.isEmpty()) {
                    statusMsg.setText("Transaction with bank rrn " + roundpayLiveId + " under process");
                } else {
                    statusMsg.setText("Transaction under process");
                }

            } else {
                statusIcon.setImageResource(R.drawable.ic_cross_mark);
                //  ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.color_red));
                statusTv.setTextColor(getResources().getColor(R.color.color_red));
                statusTv.setText("Failed");
                if (response != null && !response.isEmpty()) {
                    statusMsg.setText(response + "");
                } else if (bank_msg != null && !bank_msg.isEmpty()) {
                    statusMsg.setText(bank_msg + "");
                } else {
                    statusMsg.setText("Sorry, Transaction Failed, Please try after some time");
                }
            }
        }

        ArrayList<ReceiptObject> mReceiptObjects = new ArrayList<>();
        if (bankName != null && !bankName.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Bank Name", bankName));
        }

        if (transId != null && !transId.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Transaction Id", transId));
        }
        if (roundpayTransactionId != null && !roundpayTransactionId.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("RP Transaction Id", roundpayTransactionId));
        }

        if (transAmount != 0) {
            mReceiptObjects.add(new ReceiptObject("Transaction Amount", Utility.INSTANCE.formatedAmountWithRupees(transAmount + "")));
        }
        /*if (balAmount != 0 ) {*/
        mReceiptObjects.add(new ReceiptObject("Balance Amount", Utility.INSTANCE.formatedAmountWithRupees(balAmount + "")));
        /* }*/


        if (bankRrn != null && !bankRrn.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Bank RRN", bankRrn));
        }
        if (roundpayLiveId != null && !roundpayLiveId.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("RP Live Id", roundpayLiveId));
        }

        if (fpId != null && !fpId.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("FP Transaction Id", fpId));
        }

        if (terminalId != null && !terminalId.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Terminal Id", terminalId));
        }

        if (invoiceNum != null && !invoiceNum.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Invoice Number", invoiceNum));
        }
        if (mid != null && !mid.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Merchant Id", mid));
        }
        if (clientrefid != null && !clientrefid.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Client Ref Id", clientrefid));
        }
        if (vendorId != null && !vendorId.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Vendor Id", vendorId));
        }
        if (stanNo != null && !stanNo.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Stan Number", stanNo));
        }


        if (type == Constants.CASH_WITHDRAWAL) {
            titleTv.setText("Cash Withdrawal Details");
            mReceiptObjects.add(new ReceiptObject("Transaction Type", "Cash Withdrawal"));
        } else if (type == Constants.CASH_DEPOSIT) {
            titleTv.setText("Cash Deposit Details");
            mReceiptObjects.add(new ReceiptObject("Transaction Type", "Cash Deposit"));
        } else if (type == Constants.BALANCE_ENQUIRY) {
            titleTv.setText("Balance Enquiry Details");
            mReceiptObjects.add(new ReceiptObject("Transaction Type", "Balance Enquiry"));
        } else if (type == Constants.MINI_STATEMENT) {
            titleTv.setText("Mini Statement Details");
            mReceiptObjects.add(new ReceiptObject("Transaction Type", "Mini Statement"));
        } else if (type == Constants.CARD_ACTIVATION) {
            titleTv.setText("Card Activation Details");
            mReceiptObjects.add(new ReceiptObject("Transaction Type", "Card Activation"));
        } else if (type == Constants.PIN_RESET) {
            titleTv.setText("Reset Pin Details");
            mReceiptObjects.add(new ReceiptObject("Transaction Type", "Reset Pin"));
        } else if (type == Constants.CHANGE_PIN) {
            titleTv.setText("Change Pin Details");
            mReceiptObjects.add(new ReceiptObject("Transaction Type", "Change Pin"));
        } else {
            if (transType != null && !transType.isEmpty()) {
                titleTv.setText("Transaction Slip");
                mReceiptObjects.add(new ReceiptObject("Transaction Type", transType));
            }
        }

        if (cardNum != null && !cardNum.isEmpty()) {

            if (cardType != null && !cardType.isEmpty()) {
                mReceiptObjects.add(new ReceiptObject("Card Number", cardNum + " [" + cardType + "]"));

            } else {
                mReceiptObjects.add(new ReceiptObject("Card Number", cardNum + ""));
            }
        }

        if (udf1 != null && !udf1.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Define value 1", udf1));
        }
        if (udf2 != null && !udf2.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Define value 2", udf2));
        }
        if (udf3 != null && !udf3.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Define value 3", udf3));
        }
        if (udf4 != null && !udf4.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Define value 4", udf4));
        }

        if (time != null && !time.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Transaction Date", time + ""));
        } else {
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy hh:mm aa");

            try {
                String dateStr = sdfDate.format(new Date());
                mReceiptObjects.add(new ReceiptObject("Transaction Date", dateStr + ""));
            } catch (Exception e) {

            }
        }

        recyclerView.setAdapter(new ReceiptDetailListAdapter(mReceiptObjects, this));

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
        saveBitmap(myBitmap, isWhatsapp);

    }

    public void saveBitmap(Bitmap bitmap, boolean isWhatsapp) {
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
            // Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            // Log.e("GREC", e.getMessage(), e);
        }
    }

    public void sendMail(String path) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "AEPS Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }


    void openWhatsapp(String path) {

        try {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "AEPS Receipt");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Receipt");
            sendIntent.setType("image/png");
            Uri myUri = Uri.parse("file://" + path);
            sendIntent.putExtra(Intent.EXTRA_STREAM, myUri);
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } catch (ActivityNotFoundException e) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp"));
            startActivity(intent);


        }

    }


}
