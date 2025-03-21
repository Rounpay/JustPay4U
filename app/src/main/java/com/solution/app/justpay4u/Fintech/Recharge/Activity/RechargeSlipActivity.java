package com.solution.app.justpay4u.Fintech.Recharge.Activity;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.OperatorListResponse;
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

public class RechargeSlipActivity extends AppCompatActivity {

    public AppCompatImageView operatorImage;
    TextView tvAmount;
    TextView tvRechargeMobileNo;
    TextView tvliveId, tvTxnStatus, refrenceIdLabel;
    TextView tvoperatorname;
    TextView tvpdate, packageTv, numberLabel;
    TextView tvtxid, txnId, outletDetail;
    TextView companyNameTv, address, customerNameTv, customerAddressTv;
    View btShare, btWhatsapp, btRepeat;
    LinearLayout shareView, addressDetailsView;
    View rlCancel, packageView, customerNameView, customerAddressView;
    String amount = "";
    String RechargeMobileNo = "";
    String liveId = "";
    String pdate = "";
    String ptime = "";
    String operatorname = "";
    String txid = "";
    String txStatus = "";
    boolean isBBPS;
    int codeStatus;
    String typerecharge = "";
    String imageurl = "";
    int oid;
    LinearLayout manin_lin;
    ImageView statusIcon;
    private ImageView bbpsLogo;
    private AppPreferences mAppPreferences;
    private String packageName;
    private String customerAddress, customerName;
    private boolean isFromRecharge;
    private LoginResponse LoginDataResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_recharge_slip);
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);

            LoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);

            getIds();

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
        });

    }

    private void getIds() {

        outletDetail = findViewById(R.id.outletDetail);
        statusIcon = findViewById(R.id.statusIcon);
        shareView = findViewById(R.id.shareView);
        manin_lin = findViewById(R.id.manin_lin);
        numberLabel = findViewById(R.id.numberLabel);
        operatorImage = findViewById(R.id.operatorImage);
        rlCancel = findViewById(R.id.rl_cancel);
        addressDetailsView = findViewById(R.id.addressDetailsView);
        bbpsLogo = findViewById(R.id.bbpsLogo);
        packageTv = findViewById(R.id.packageTv);
        packageView = findViewById(R.id.packageView);
        customerNameView = findViewById(R.id.customerNameView);
        customerAddressView = findViewById(R.id.custAddressView);
        customerNameTv = findViewById(R.id.customerNameTv);
        customerAddressTv = findViewById(R.id.custAddressTv);
        tvTxnStatus = findViewById(R.id.tv_txstatus);
        companyNameTv = findViewById(R.id.companyName);
        address = findViewById(R.id.address);
        tvAmount = findViewById(R.id.tv_amount);
        tvRechargeMobileNo = findViewById(R.id.tv_RechargeMobileNo);
        tvliveId = findViewById(R.id.tv_liveId);
        refrenceIdLabel = findViewById(R.id.refrenceIdLabel);
        tvoperatorname = findViewById(R.id.tv_operatorname);
        tvpdate = findViewById(R.id.tv_pdate);
        btShare = findViewById(R.id.bt_share);
        btWhatsapp = findViewById(R.id.bt_whatsapp);
        btRepeat = findViewById(R.id.bt_repeat);
        tvtxid = findViewById(R.id.tv_txid);
        txnId = findViewById(R.id.txnId);


        new Handler(Looper.getMainLooper()).post(() -> {


            amount = getIntent().getExtras().getString("amount");
            RechargeMobileNo = getIntent().getExtras().getString("RechargeMobileNo");
            liveId = getIntent().getExtras().getString("liveId");
            operatorname = getIntent().getExtras().getString("operatorname");
            pdate = getIntent().getExtras().getString("pdate");
            ptime = getIntent().getExtras().getString("ptime");
            txid = getIntent().getExtras().getString("txid");
            txStatus = getIntent().getExtras().getString("txStatus");
            codeStatus = getIntent().getExtras().getInt("StatusCode", 0);
            oid = getIntent().getExtras().getInt("OID", 0);
            typerecharge = getIntent().getExtras().getString("typerecharge");
            packageName = getIntent().getExtras().getString("PackageName");
            customerAddress = getIntent().getExtras().getString("CustomerAddress");
            customerName = getIntent().getExtras().getString("CustomerName");
            imageurl = getIntent().getExtras().getString("imageurl");
            isBBPS = getIntent().getExtras().getBoolean("IsBBPS", false);
            isFromRecharge = getIntent().getExtras().getBoolean("IsFromRecharge", false);

            if (codeStatus == 2) {
                tvTxnStatus.setText(txStatus);
                statusIcon.setImageResource(R.drawable.ic_check_mark);
                tvTxnStatus.setTextColor(getResources().getColor(R.color.green));
                addressDetailsView.setVisibility(View.VISIBLE);
                btRepeat.setVisibility(View.GONE);
            } else if (codeStatus == 1) {
                tvTxnStatus.setText(txStatus);
                statusIcon.setImageResource(R.drawable.ic_pending_outline);
                tvTxnStatus.setTextColor(getResources().getColor(R.color.color_orange));
                addressDetailsView.setVisibility(View.VISIBLE);
                btRepeat.setVisibility(View.GONE);
            } else {
                tvTxnStatus.setText(txStatus + "\n(" + liveId + ")");
                refrenceIdLabel.setText("Reason");
                statusIcon.setImageResource(R.drawable.ic_cross_mark);
                tvTxnStatus.setTextColor(getResources().getColor(R.color.color_red));
                addressDetailsView.setVisibility(View.GONE);
                if (isFromRecharge) {
                    btRepeat.setVisibility(View.VISIBLE);
                } else {
                    btRepeat.setVisibility(View.GONE);
                }
            }


       /* if (typerecharge.equalsIgnoreCase("SUCCESS")) {
            manin_lin.setVisibility(View.VISIBLE);

        } else {
            manin_lin.setVisibility(View.GONE);

        }*/
            if (packageName != null && !packageName.isEmpty()) {
                packageView.setVisibility(View.VISIBLE);
                packageTv.setText(packageName + "");
            } else {
                packageView.setVisibility(View.GONE);
            }


            if (customerAddress != null && !customerAddress.isEmpty()) {
                customerAddressView.setVisibility(View.VISIBLE);
                customerAddressTv.setText(customerAddress + "");
            } else {
                customerAddressView.setVisibility(View.GONE);
            }

            if (customerName != null && !customerName.isEmpty()) {
                customerNameView.setVisibility(View.VISIBLE);
                customerNameTv.setText(customerName + "");
            } else {
                customerNameView.setVisibility(View.GONE);
            }

            if (imageurl != null && !imageurl.isEmpty()) {
                Glide.with(this).load(imageurl)
                        .thumbnail(0.5f)
                        .transition(new DrawableTransitionOptions().crossFade())
                        .apply(new RequestOptions().placeholder(R.drawable.ic_operator_default_icon).error(R.drawable.ic_operator_default_icon).diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(operatorImage);
            } else {
                operatorImage.setImageResource(R.drawable.ic_tower);
            }

            if (isBBPS) {
                bbpsLogo.setVisibility(View.VISIBLE);
                refrenceIdLabel.setText("BBPS Reference Id");
            } else {
                bbpsLogo.setVisibility(View.GONE);
                refrenceIdLabel.setText("Reference Id");
            }

            tvAmount.setText(getString(R.string.rupiya) + " " + amount);
            tvRechargeMobileNo.setText(RechargeMobileNo);
            tvliveId.setText(liveId);
            tvpdate.setText(Utility.INSTANCE.formatedDate(pdate));
            tvoperatorname.setText(operatorname);
            tvtxid.setText(txid);
            if (txid != null && txid.length() > 4) {
                txnId.setText("Ref:- " + txid);
                txnId.setVisibility(View.VISIBLE);
            } else {
                txnId.setVisibility(View.GONE);
            }


            setOutletDetail();
            setCompanyDetail(ApiFintechUtilMethods.INSTANCE.getCompanyProfile(mAppPreferences));

            if (oid != 0) {
                OperatorListResponse operatorListResponse = ApiFintechUtilMethods.INSTANCE.getOperatorListResponse(mAppPreferences);

                getOperator(operatorListResponse);
            }


        });
        btShare.setOnClickListener(view -> shareit(false));

        btRepeat.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
        rlCancel.setOnClickListener(view -> {
            setResult(RESULT_OK);
            finish();
        });
        if (Utility.INSTANCE.isPackageInstalled("com.whatsapp", getPackageManager())) {
            btWhatsapp.setVisibility(View.VISIBLE);
            btWhatsapp.setOnClickListener(view -> shareit(true));
        } else {
            btWhatsapp.setVisibility(View.GONE);
        }
    }


    void getOperator(OperatorListResponse response) {


        if (response != null && response.getOperators() != null && response.getOperators().size() > 0) {
            for (OperatorList op : response.getOperators()) {
                if (op.getOid() == oid) {
                    if (op.getAccountName() != null && !op.getAccountName().isEmpty()) {
                        numberLabel.setText(op.getAccountName());
                        break;
                    }

                }
            }
        } else {
            String deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            String deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

            ApiFintechUtilMethods.INSTANCE.OperatorList(this, deviceId, deviceSerialNum, mAppPreferences, object -> {
                OperatorListResponse operatorListResponse = (OperatorListResponse) object;
                if (operatorListResponse != null && operatorListResponse.getOperators() != null && operatorListResponse.getOperators().size() > 0) {
                    getOperator(operatorListResponse);
                }
            });
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
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }*/

    public void sendMail(Uri myUri) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "Recharge Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }


    void openWhatsapp(Uri myUri) {

        try {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Recharge Receipt");
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
