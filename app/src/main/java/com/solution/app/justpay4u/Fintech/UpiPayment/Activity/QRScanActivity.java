package com.solution.app.justpay4u.Fintech.UpiPayment.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dreamseller.imagepicker.ImagePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.Fintech.UpiPayment.QrScannerViewUtils.ScannerView;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomLoader;


import java.io.IOException;


public class QRScanActivity extends AppCompatActivity implements ScannerView.ResultHandler {

    private static final String FLASH_STATE = "FLASH_STATE";
    private ScannerView mScannerView;
    private boolean mFlash;
    private ImageView flashImage, galleryImage;
    private ImagePicker imagePicker;
    private CustomLoader loader;
    private int INTENT_PAY = 9023;
    private int REQUEST_PERMISSIONS = 2121;
    private Snackbar mSnackBar;
    private String senderNum;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        findViewById(R.id.closeIv).setOnClickListener(v -> finish());
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        flashImage = findViewById(R.id.flashImage);
        galleryImage = findViewById(R.id.galleryImage);
        senderNum = getIntent().getStringExtra("SenderNum");
        findViewById(R.id.closeIv).setOnClickListener(v -> finish());
        FrameLayout contentFrame = findViewById(R.id.content_frame);
        mScannerView = new ScannerView(this);
        mScannerView.setSquareViewFinder(true);
        contentFrame.addView(mScannerView);
        flashImage.setOnClickListener(v -> toggleFlash());
        imagePicker = new ImagePicker(this, null, imageUri -> {

            try {
                loader.show();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                if (bitmap != null) {
                    String code = getGalleryImageQrCodeData(bitmap);
                    loader.dismiss();
                    if (code != null && !code.isEmpty()) {
                        handleValue(code);
                    } else {
                        ApiFintechUtilMethods.INSTANCE.Error(this, "Invalid Qr Code");
                    }

                } else {
                    loader.dismiss();
                    ApiFintechUtilMethods.INSTANCE.Error(this, "Something went wrong, Please try other image");
                }

            } catch (IOException e) {
                loader.dismiss();
                Toast.makeText(this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        });
        galleryImage.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
                } else {
                    imagePicker.choosePicture(false, true);
                }
            } else {
                imagePicker.choosePicture(false, true);
            }

        });


    }

    @Override
    protected void onStart() {
        checkPermissionCamera();
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        // You can optionally set aspect ratio tolerance level
        // that is used in calculating the optimal Camera preview size
        mScannerView.setAspectTolerance(0.2f);
        mScannerView.startCamera();
        mScannerView.setFlash(mFlash);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onDestroy() {
       /* if(new Contents().progressDialog!=null &&new Contents().progressDialog.isShowing() ){
            new Contents().progressDialog.dismiss();
        }*/
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
    }

    @Override
    public void handleResult(Result rawResult) {

        if (rawResult.getText() != null && !rawResult.getText().isEmpty()) {
            handleValue(rawResult.getText());

        } else {
            ApiFintechUtilMethods.INSTANCE.Error(this, "Invalid Qr Code");
        }

        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(QRScanActivity.this);
            }
        }, 2000);
    }


    void handleValue(String codeValue) {
        if (codeValue.contains("upi://pay?")) {
            if (getIntent().getBooleanExtra("FROM_SCANPAY", false)) {
                try {
                    Uri mUri = Uri.parse(codeValue);
                    String pa = mUri.getQueryParameter("pa");
                    String pn = mUri.getQueryParameter("pn");
                    String am = mUri.getQueryParameter("am");

                    if (pa != null && !pa.isEmpty() && pa.contains("@")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            vibrator.vibrate(500);
                        }
                        startActivityForResult(new Intent(this, QRPayActivity.class)
                                .putExtra("SenderNum", senderNum)
                                .putExtra("UPI", pa)
                                .putExtra("Amount", am)
                                .putExtra("Name", pn.trim().replaceAll(" +", " ")), INTENT_PAY);
                    } else {
                        ApiFintechUtilMethods.INSTANCE.Error(this, "Invalid Qr Code");
                    }
                } catch (Exception e) {
                    ApiFintechUtilMethods.INSTANCE.Error(this, "Invalid Qr Code");
                }

            } else {
                Intent intent = new Intent();
                intent.putExtra("codeValue", codeValue);
                setResult(RESULT_OK, intent);
                finish();
            }
        } else {
            ApiFintechUtilMethods.INSTANCE.Error(this, "Invalid Qr Code");
        }
    }

    public void toggleFlash() {
        mFlash = !mFlash;
        if (mFlash) {
            flashImage.setImageResource(R.drawable.ic_flash_off_white_24dp);
        } else {
            flashImage.setImageResource(R.drawable.ic_flash_on_white_24dp);
        }

        mScannerView.setFlash(mFlash);
    }


    void checkPermissionCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSIONS);
            } else {

            }
        } else {

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            int permissionCheck = PackageManager.PERMISSION_GRANTED;
            for (int permission : grantResults) {
                permissionCheck = permissionCheck + permission;
            }
            if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {

                onResume();
            } else {
                showWarningSnack(R.string.str_ShowOnPermisstionDenied, "Enable", true);
            }
        } else {
            imagePicker.handlePermission(requestCode, grantResults);
        }
    }

    void showWarningSnack(int stringId, String btn, final boolean isForSetting) {
        if (mSnackBar != null && mSnackBar.isShown()) {
            return;
        }

        mSnackBar = Snackbar.make(findViewById(android.R.id.content), stringId,
                Snackbar.LENGTH_INDEFINITE).setAction(btn,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isForSetting) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            startActivity(intent);
                        } else {
                            ActivityCompat.requestPermissions(QRScanActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
                        }

                    }
                });
        mSnackBar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        TextView mainTextView = (TextView) (mSnackBar.getView()).
                findViewById(R.id.snackbar_text);
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen._12sdp));
        mainTextView.setMaxLines(4);
        mSnackBar.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_PAY && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        } else {
            imagePicker.handleActivityResult(resultCode, requestCode, data);
        }
    }


    private String getGalleryImageQrCodeData(Bitmap bitmap) {

        String contents = "";

        int[] intArray = new int[bitmap.getWidth() * bitmap.getHeight()];

        bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        LuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), intArray);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

        Reader reader = new MultiFormatReader();
        Result result = null;
        try {
            result = reader.decode(binaryBitmap);
            contents = result.getText();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contents;
    }


}