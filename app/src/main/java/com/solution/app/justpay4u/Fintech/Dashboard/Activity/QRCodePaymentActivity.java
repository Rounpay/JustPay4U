package com.solution.app.justpay4u.Fintech.Dashboard.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.solution.app.justpay4u.Fintech.Activities.PermissionActivity;
import com.solution.app.justpay4u.Api.Fintech.Request.MapQRToUserRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.UpiPayment.Activity.QRScanActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import retrofit2.Call;
import retrofit2.Callback;

public class QRCodePaymentActivity extends AppCompatActivity {

    TextView custCare, upiId, OutletName;
    ImageView qrcode, cameraView;
    String deviceId, deviceSerialNum;
    private CustomLoader loader;
    private LoginResponse mLoginDataResponse;
    private View shareView, btnView;
    private boolean isDownload;
    private int INTENT_PERMISSION_IMAGE = 4563;
    private int INTENT_SCAN = 9465;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_qr_code_payment);
            custCare = findViewById(R.id.custCare);
            upiId = findViewById(R.id.upiId);
            qrcode = findViewById(R.id.qrcode);
            cameraView = findViewById(R.id.cameraView);
            shareView = findViewById(R.id.shareView);
            btnView = findViewById(R.id.btnView);
            OutletName = findViewById(R.id.OutletName);
            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            AppPreferences mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(this);
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            OutletName.setText(mLoginDataResponse.getData().getName() + "");

            new Handler(Looper.getMainLooper()).post(() -> {
                boolean isQRMappedToUser = getIntent().getBooleanExtra("isQRMappedToUser", false);
                boolean isBulkQRGeneration = getIntent().getBooleanExtra("isBulkQRGeneration", false);
                if (isBulkQRGeneration && !isQRMappedToUser) {
                    cameraView.setVisibility(View.VISIBLE);
                } else {
                    cameraView.setVisibility(View.GONE);
                }
                setQRCode();


                AppUserListResponse companyProfileData = ApiFintechUtilMethods.INSTANCE.getCompanyProfile(mAppPreferences);
                if (companyProfileData != null && companyProfileData.getCompanyProfile() != null) {
                    String value = "";
                    if (companyProfileData.getCompanyProfile().getCustomerCareMobileNos() != null && !companyProfileData.getCompanyProfile().getCustomerCareMobileNos().isEmpty()) {
                        value = value + getHtml("Mobile No", companyProfileData.getCompanyProfile().getCustomerCareMobileNos());
                    }
                    if (companyProfileData.getCompanyProfile().getCustomerPhoneNos() != null && !companyProfileData.getCompanyProfile().getCustomerPhoneNos().isEmpty()) {
                        if (value.length() > 0) {
                            value = value + "<br/>";
                        }
                        value = value + getHtml("Landline No", companyProfileData.getCompanyProfile().getCustomerPhoneNos());
                    }
                    if (companyProfileData.getCompanyProfile().getCustomerWhatsAppNosLink() != null && !companyProfileData.getCompanyProfile().getCustomerWhatsAppNosLink().isEmpty()) {
                        if (value.length() > 0) {
                            value = value + "<br/>";
                        }
                        value = value + "Whatsapp - " + companyProfileData.getCompanyProfile().getCustomerWhatsAppNosLink();
                    }

                    Utility.INSTANCE.setTextViewHTML(this, custCare, "Customer Care<br/>" + value);

                } else {
                    custCare.setVisibility(View.GONE);
                }
            });
            cameraView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(QRCodePaymentActivity.this, QRScanActivity.class), INTENT_SCAN);
                }
            });
            findViewById(R.id.closeIv).setOnClickListener(v -> finish());
            findViewById(R.id.download).setOnClickListener(v -> {
                isDownload = true;
                shareit();
            });

            findViewById(R.id.share).setOnClickListener(v -> {
                isDownload = false;
                shareit();
            });
        });

    }

    private void setQRCode() {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder_square);
        requestOptions.error(R.drawable.placeholder_square);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.skipMemoryCache(true);

        Glide.with(this)
                .load(ApplicationConstant.INSTANCE.baseQrImageUrl + mLoginDataResponse.getData().getUserID() +
                        "&appid=" + ApplicationConstant.INSTANCE.APP_ID + "&imei=" + deviceId + "&regKey=&version=" + BuildConfig.VERSION_NAME +
                        "&serialNo=" + deviceSerialNum + "&sessionID=" + mLoginDataResponse.getData().getSessionID() +
                        "&session=" + mLoginDataResponse.getData().getSession() + "&loginTypeID=" + mLoginDataResponse.getData().getLoginTypeID())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        btnView.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(qrcode);
    }

    String getHtml(String prefix, String number) {
        if (number.contains(",")) {
            String[] array = number.split(",");
            String num = "";

            for (int i = 0; i < array.length; i++) {

                if (i > 0) {
                    num = num + ", <a href=tel:" + array[i] + ">" + array[i] + "</a>";
                } else {
                    num = num + "<a href=tel:" + array[i] + ">" + array[i] + "</a>";
                }

            }
            return prefix + " - " + num;
        } else {
            return prefix + " - <a href=tel:" + number + ">" + number + "</a>";
        }
    }

    public void shareit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(this, PermissionActivity.class),
                        INTENT_PERMISSION_IMAGE);

            } else {
                getBitmap();
            }
        } else {
            getBitmap();
        }


    }

    void getBitmap() {
        shareView.setDrawingCacheEnabled(true);
        Bitmap myBitmap = shareView.getDrawingCache();
        saveImage(isDownload, myBitmap);
        shareView.setDrawingCacheEnabled(false);
    }

    private void saveImage(boolean isDownload, Bitmap bitmap) {
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            ContentValues values = contentValues();
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
            values.put(MediaStore.Images.Media.IS_PENDING, true);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "QR_CODE");

            Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try {
                    saveImageToStream(bitmap, this.getContentResolver().openOutputStream(uri));
                    values.put(MediaStore.Images.Media.IS_PENDING, false);
                    this.getContentResolver().update(uri, values, null, null);
                    if (isDownload) {
                        Toast.makeText(this, "Successfully Download", Toast.LENGTH_SHORT).show();
                        MediaScannerConnection.scanFile(this, new String[]{uri.getPath()}, new String[]{"image/png"}, null);
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
            String fileName = "QR_CODE.png";
            File file = new File(directory, fileName);
            try {
                saveImageToStream(bitmap, new FileOutputStream(file));
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (isDownload) {
                    Toast.makeText(this, "Successfully Download", Toast.LENGTH_SHORT).show();
                    MediaScannerConnection.scanFile(this, new String[]{file.getPath()}, new String[]{"image/png"}, null);
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

    /*public void saveBitmap(boolean isDownload, Bitmap bitmap) {

        File filePath = new File(Environment.getExternalStorageDirectory().toString() + "/" + getString(R.string.app_name));

        if (!filePath.exists()) {
            filePath.mkdir();
        }
        File imagePath = new File(filePath + "/QR_CODE.jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            if (isDownload) {
                Toast.makeText(this, "Successfully Download", Toast.LENGTH_SHORT).show();
                MediaScannerConnection.scanFile(this, new String[]{imagePath.getPath()}, new String[]{"image/jpeg"}, null);
            } else {
                sendMail(imagePath.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void sendMail(Uri myUri) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "QR CODE");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "QR CODE");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_PERMISSION_IMAGE && resultCode == RESULT_OK) {
            getBitmap();
        } else if (requestCode == INTENT_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String codeValue = data.getStringExtra("codeValue");
                Uri mUri = Uri.parse(codeValue);
                String pa = mUri.getQueryParameter("pa");
                String pn = mUri.getQueryParameter("pn");
                String tr = mUri.getQueryParameter("tr");
                String mc = mUri.getQueryParameter("mc");
                if (pa != null && mc != null && tr != null && pn != null) {


                    new CustomAlertDialog(this).WarningWithDoubleBtnCallBack(pn + " : " + pa, "QR Detais", "Link", true, new CustomAlertDialog.DialogCallBack() {
                        @Override
                        public void onPositiveClick() {
                            MapQRToUser(QRCodePaymentActivity.this, codeValue, loader);
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });
                }
            }


        }
    }


    public void MapQRToUser(final Activity context, String qrData, final CustomLoader loader) {
        try {
            loader.show();
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<BasicResponse> call = git.MapQRToUser(new MapQRToUserRequest(qrData,
                    mLoginDataResponse.getData().getUserID() + "",
                    mLoginDataResponse.getData().getLoginTypeID() ,
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                ApiFintechUtilMethods.INSTANCE.Successful(context, response.body().getMsg() + "");
                                cameraView.setVisibility(View.GONE);
                                setQRCode();
                            } else {
                                if (response.body().getVersionValid() == false) {
                                    ApiFintechUtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        ApiFintechUtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();

                    try {
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        ApiFintechUtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            ApiFintechUtilMethods.INSTANCE.Error(context, e.getMessage());
        }

    }
}
