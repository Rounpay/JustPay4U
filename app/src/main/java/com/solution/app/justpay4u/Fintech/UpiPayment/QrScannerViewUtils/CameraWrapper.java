package com.solution.app.justpay4u.Fintech.UpiPayment.QrScannerViewUtils;

import android.hardware.Camera;

import androidx.annotation.NonNull;

public class CameraWrapper {
    public final Camera mCamera;
    public final int mCameraId;

    private CameraWrapper(@NonNull Camera camera, int cameraId) {
        if (camera == null) {
            throw new NullPointerException("Camera cannot be null");
        }
        this.mCamera = camera;
        this.mCameraId = cameraId;
    }

    public static CameraWrapper getWrapper(Camera camera, int cameraId) {
        if (camera == null) {
            return null;
        } else {
            return new CameraWrapper(camera, cameraId);
        }
    }
}
