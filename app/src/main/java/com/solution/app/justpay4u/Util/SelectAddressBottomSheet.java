package com.solution.app.justpay4u.Util;

import static android.view.animation.Animation.INFINITE;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Services.GetAddressShoppingIntentService;

/**
 * Created by Vishnu Agarwal on 10,January,2020
 */
public class SelectAddressBottomSheet extends BottomSheetDialogFragment {

    public static final int REQUEST_CHECK_SETTINGS = 1001;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    String titleTxt;

    CustomLoader mCustomLoader;
    CustomAlertDialog mCustomAlertDialog;
    ButtonCallBack mButtonCallBack;
    View sheetView;
    private ImageView locationIcon;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationAddressResultReceiver addressResultReceiver;
    private Location currentLocation;
    private ResolvableApiException resolvable;
    private LocationCallback locationCallback;

    public void setCallBack(ButtonCallBack mButtonCallBack) {
        this.mButtonCallBack = mButtonCallBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sheetView = inflater.inflate(R.layout.dialog_shopping_select_address, container, false);
        mCustomLoader = new CustomLoader(getActivity());
        mCustomAlertDialog = new CustomAlertDialog(getActivity());

        String productId = getTag();
        /*if (UtilMethods.INSTANCE.mLoginResponse == null) {
            UtilMethods.INSTANCE.mLoginResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(getActivity()), LoginResponse.class);

        }
        String userId = UtilMethods.INSTANCE.mLoginResponse.getData().getUserID();*/
        TextView titleTv = sheetView.findViewById(R.id.titleTv);
        if (titleTxt != null && !titleTxt.isEmpty()) {
            titleTv.setText(titleTxt);
        }
        locationIcon = sheetView.findViewById(R.id.locationIcon);
        ImageView closeBtn = sheetView.findViewById(R.id.closeBtn);
        final EditText pincodeEt = sheetView.findViewById(R.id.pincodeEt);
        TextView checkBtn = sheetView.findViewById(R.id.checkBtn);
        View currentLocationBtn = sheetView.findViewById(R.id.currentLocation);
        TextView selectAddressBtn = sheetView.findViewById(R.id.selectAddressBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pincodeEt.getText().toString().isEmpty()) {
                    pincodeEt.setError("Please Enter Pincode");
                    pincodeEt.requestFocus();
                    return;
                } else if (pincodeEt.getText().length() != 6) {
                    pincodeEt.setError("Invalid Pincode");
                    pincodeEt.requestFocus();
                    return;
                }
                if (mButtonCallBack != null) {

                    dismiss();
                    mButtonCallBack.pincodeCheckBtnClick(pincodeEt.getText().toString());
                }

            }
        });


        selectAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mButtonCallBack != null) {

                    dismiss();
                    mButtonCallBack.selectAddressBTnClick();
                }
            }
        });


        addressResultReceiver = new LocationAddressResultReceiver(new Handler());


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                getAddress();
            }
        };


        currentLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startLocationUpdates();
            }
        });
        return sheetView;
    }


    public void setTitle(String title) {
        titleTxt = title;
    }


    private void hideKeyBoard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {


            final LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

            builder.setNeedBle(true);
            SettingsClient client = LocationServices.getSettingsClient(getActivity());
            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

            task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
                @Override
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                    // All location settings are satisfied. The client can initialize
                    // location requests here.
                    // ...
                    RotateAnimation anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    anim.setDuration((long) 1000);
                    anim.setRepeatCount(INFINITE);
                    locationIcon.startAnimation(anim);
                    fusedLocationClient.requestLocationUpdates(locationRequest,
                            locationCallback,
                            null);

                }
            });

            task.addOnFailureListener(getActivity(), new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof ResolvableApiException) {
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().

                            resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(getActivity(),
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                    }
                }
            });

        }
    }

    @SuppressWarnings("MissingPermission")
    private void getAddress() {

        if (!Geocoder.isPresent()) {
            Toast.makeText(getActivity(),
                    "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(getActivity(), GetAddressShoppingIntentService.class);
        intent.putExtra("add_receiver", addressResultReceiver);
        intent.putExtra("add_location", currentLocation);
        getActivity().startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {
                    showSnackbarOnDeny();
                   /* Toast.makeText(this, "Location permission not granted, " +
                                    "restart the app if you want the feature",
                            Toast.LENGTH_SHORT).show();*/
                }
                return;
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made

                        startLocationUpdates();

                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        showSnackbarOnCancel();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    private void showSnackbarOnCancel() {

        final Snackbar mSnackBar = Snackbar.make(sheetView.findViewById(android.R.id.content), getString(R.string.enable_gps), Snackbar.LENGTH_LONG);
        mSnackBar.setAction(getString(R.string.enable),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            mSnackBar.dismiss();
                            if (resolvable != null) {
                                resolvable.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                            }
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                    }
                });
        mSnackBar.setActionTextColor(getResources().getColor(R.color.darkGreen));
        TextView mainTextView =  (mSnackBar.getView()).
                findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen._14sdp));
        mSnackBar.show();


    }

    private void showResults(Bundle result) {
       /* if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }*/

       /* addressEt.setText(result.getString("Address"));
        cityEt.setText(result.getString("City"));
        stateEt.setText(result.getString("State"));
        pincodeEt.setText(result.getString("Pincode"));
        areaEt.setText(result.getString("SubLocality"));*/
        if (mButtonCallBack != null) {
            dismiss();
            mButtonCallBack.gpsBtnClick(result.getString("Pincode") + "");
        }
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    private void showSnackbarOnDeny() {

        final Snackbar mSnackBar = Snackbar.make(sheetView.findViewById(android.R.id.content), getString(R.string.allow_permission),
                Snackbar.LENGTH_LONG);
        mSnackBar.setAction(getString(R.string.Setting), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackBar.dismiss();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);

            }
        });
        mSnackBar.setActionTextColor(getResources().getColor(R.color.darkGreen));
        TextView mainTextView =  (mSnackBar.getView()).
                findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen._12sdp));
        mainTextView.setMaxLines(4);
        mSnackBar.show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }


    public interface ButtonCallBack {
        void pincodeCheckBtnClick(String pincode);

        void gpsBtnClick(String pincode);

        void selectAddressBTnClick();

    }

    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultCode == 0) {
                //Last Location can be null for various reasons
                //for example the api is called first time
                //so retry till location is set
                //since intent service runs on background thread, it doesn't block main thread
                Log.d("Address", "Location null retrying");
                getAddress();
            }

            if (resultCode == 1) {
                Toast.makeText(getActivity(),
                        "Address not found, ",
                        Toast.LENGTH_SHORT).show();
            }

            if (resultCode == 2) {
                showResults(resultData);
            }
            locationIcon.clearAnimation();

        }

    }

}
