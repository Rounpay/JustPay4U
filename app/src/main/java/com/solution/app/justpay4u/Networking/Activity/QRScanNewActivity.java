package com.solution.app.justpay4u.Networking.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;
import com.solution.app.justpay4u.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
public class QRScanNewActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        findViewById(R.id.closeIv).setOnClickListener(v -> finish());

        // Initialize the scanner view
        mScannerView = new ZXingScannerView(this);

        // Add the scanner view to the existing layout
        FrameLayout contentFrame = findViewById(R.id.content_frame);
        contentFrame.addView(mScannerView);

        // Handle flashlight button click (optional)
        ImageView flashImage = findViewById(R.id.flashImage);
        flashImage.setOnClickListener(v -> toggleFlash());
    }

    @Override
    public void onResume() {
        super.onResume();

        // Configure camera settings (example: enable autofocus)
        mScannerView.setAutoFocus(true);

        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

        // Optional: Set aspect ratio tolerance level for camera preview
        mScannerView.setAspectTolerance(0.2f);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        String codeValue = rawResult.getText();

        if (codeValue != null && !codeValue.isEmpty()) {
            // Create an Intent to start the new activity
            Intent intent = new Intent(this, WalletToWalletNewActivity.class);

            // Pass the scanned text as an extra
            intent.putExtra("UserID", codeValue);

            // Start the new activity
            startActivity(intent);

            // Optionally, finish the current activity if needed
            finish();
        } else {
            // Empty QR code
            showToast("Empty QR Code");
        }
    }


    // Method to vibrate the device
    private void vibrateDevice() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(500);
        }
    }

    // Method to show a toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Method to toggle the flashlight (optional)
    private void toggleFlash() {
        mScannerView.setFlash(!mScannerView.getFlash());
    }
}
