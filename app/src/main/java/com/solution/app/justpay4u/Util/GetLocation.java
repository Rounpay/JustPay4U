package com.solution.app.justpay4u.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.solution.app.justpay4u.Fintech.Activities.PermissionActivity;
import com.solution.app.justpay4u.R;


public class GetLocation {

    private final FusedLocationProviderClient fusedLocationClient;
    private final LocationCallback locationCallback;
    public int REQUEST_CHECK_SETTINGS = 1111;
    public int REQUEST_CHECK_ENABLE_SETTINGS = 6543;
    /*public int LOCATION_PERMISSION_REQUEST_CODE = 2222;*/
    public int INTENT_LOCATION_PERMISSION = 2222;
    Activity mContext;
    CustomLoader mLoader;
    LocationLatLong mLocationLatLong;
    private ResolvableApiException resolvable;
    private Location currentLocation;
    private CheckSettingEnable mCheckSettingEnable;

    public GetLocation(Activity context, CustomLoader mLoader) {

        this.mContext = context;
        this.mLoader = mLoader;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                if (currentLocation != null && currentLocation.getLatitude() != 0 && currentLocation.getLongitude() != 0 && mLocationLatLong != null) {
                    if (mLoader.isShowing()) {
                        mLoader.dismiss();
                    }
                    mLocationLatLong.LatLong(currentLocation.getLatitude(), currentLocation.getLongitude());
                } else {
                    Toast.makeText(context, mContext.getResources().getString(R.string.some_thing_error), Toast.LENGTH_SHORT).show();
                }
                if (fusedLocationClient != null && locationCallback != null) {
                    fusedLocationClient.removeLocationUpdates(locationCallback);
                }
            }
        };


    }

    public void startLocationUpdatesIfSettingEnable(LocationLatLong mLocationLatLong) {
        this.mLocationLatLong = mLocationLatLong;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {


            final LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

            builder.setNeedBle(true);
            SettingsClient client = LocationServices.getSettingsClient(mContext);
            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

            task.addOnSuccessListener(mContext, locationSettingsResponse -> {
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

            });

        }
    }

    public void startLocationUpdates(LocationLatLong mLocationLatLong) {
        this.mLocationLatLong = mLocationLatLong;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
           /* ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);*/
            mContext.startActivityForResult(new Intent(mContext, PermissionActivity.class),
                    INTENT_LOCATION_PERMISSION);
        } else {

            final LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

            builder.setNeedBle(true);
            SettingsClient client = LocationServices.getSettingsClient(mContext);
            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

            task.addOnSuccessListener(mContext, locationSettingsResponse -> {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                mLoader.show();
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

            });

            task.addOnFailureListener(mContext, e -> {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().

                        resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(mContext,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            });

        }
    }

    public void checkSettingEnable(CheckSettingEnable mCheckSettingEnable) {
        this.mCheckSettingEnable = mCheckSettingEnable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            mContext.startActivityForResult(new Intent(mContext, PermissionActivity.class),
                    INTENT_LOCATION_PERMISSION);
        } else {

            final LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

            builder.setNeedBle(true);
            SettingsClient client = LocationServices.getSettingsClient(mContext);
            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

            task.addOnSuccessListener(mContext, locationSettingsResponse -> {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                if (mCheckSettingEnable != null) {
                    mCheckSettingEnable.Enable();
                }

            });

            task.addOnFailureListener(mContext, e -> {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().

                        resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(mContext,
                                REQUEST_CHECK_ENABLE_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            });

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> startLocationUpdates(mLocationLatLong), 500);
            } else {
                showSnackbarOnCancel();
            }
        } else if (requestCode == REQUEST_CHECK_ENABLE_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> checkSettingEnable(mCheckSettingEnable), 500);

            } else {
                showSnackbarOnCancel();
            }
        } else if (requestCode == INTENT_LOCATION_PERMISSION && resultCode == Activity.RESULT_OK) {
            startLocationUpdates(mLocationLatLong);
        }
    }


   /* public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates(mLocationLatLong);
            } else {
                showSnackbarOnDeny();
            }
        }


    }*/


    private void showSnackbarOnCancel() {

        final Snackbar mSnackBar = Snackbar.make(mContext.findViewById(android.R.id.content), mContext.getResources().getString(R.string.enable_gps), Snackbar.LENGTH_LONG);
        mSnackBar.setAction(mContext.getResources().getString(R.string.enable),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            mSnackBar.dismiss();
                            if (resolvable != null) {
                                resolvable.startResolutionForResult(mContext, REQUEST_CHECK_SETTINGS);
                            }
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                    }
                });
        mSnackBar.setActionTextColor(mContext.getResources().getColor(R.color.button_color));
        TextView mainTextView = (TextView) (mSnackBar.getView()).
                findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                mContext.getResources().getDimension(R.dimen._14sdp));
        mSnackBar.show();


    }

    private void showSnackbarOnDeny() {

        final Snackbar mSnackBar = Snackbar.make(mContext.findViewById(android.R.id.content), mContext.getResources().getString(R.string.allow_permission),
                Snackbar.LENGTH_LONG);
        mSnackBar.setAction(mContext.getResources().getString(R.string.Setting), v -> {
            mSnackBar.dismiss();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
            intent.setData(uri);
            mContext.startActivity(intent);

        });
        mSnackBar.setActionTextColor(mContext.getResources().getColor(R.color.button_color));
        TextView mainTextView = (TextView) (mSnackBar.getView()).
                findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                mContext.getResources().getDimension(R.dimen._12sdp));
        mainTextView.setMaxLines(4);
        mSnackBar.show();
    }


    public void onPause() {
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }


    public interface LocationLatLong {
        void LatLong(double lattitude, double longitude);
    }

    public interface CheckSettingEnable {
        void Enable();
    }
}