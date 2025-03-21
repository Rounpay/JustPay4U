package com.solution.app.justpay4u.Shopping.Services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Locale;

public class GetAddressShoppingIntentService extends IntentService {

    private static final String IDENTIFIER = "GetAddressIntentService";
    private ResultReceiver addressResultReceiver;
    // Map<String, String> locationAddress = new HashMap<>();

    public GetAddressShoppingIntentService() {
        super(IDENTIFIER);
    }

    //handle the address request
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String msg = "";
        //get result receiver from intent
        addressResultReceiver = intent.getParcelableExtra("add_receiver");

        if (addressResultReceiver == null) {
            Log.e("GetAddressIntentService",
                    "No receiver, not processing the request further");
            return;
        }

        Location location = intent.getParcelableExtra("add_location");

        //send no location error to results receiver
        if (location == null) {
            msg = "No location, can't go further without location";
            Bundle bundle = new Bundle();
            bundle.putString("Message", msg);
            sendResultsToReceiver(0, bundle);
            return;
        }
        //call GeoCoder getFromLocation to get address
        //returns list of addresses, take first one and send info to result receiver
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (Exception ioException) {
            Log.e("", "Error in getting address for the location");
        }

        if (addresses == null || addresses.size() == 0) {
            msg = "No address found for the location";
            Bundle bundle = new Bundle();
            bundle.putString("Message", msg);
            sendResultsToReceiver(1, bundle);
        } else {
            Address address = addresses.get(0);
            // StringBuffer addressDetails = new StringBuffer();
            Bundle bundle = new Bundle();
            bundle.putString("Address", address.getAddressLine(0).replace(", " + address.getSubAdminArea(), "")
                    .replace(", " + address.getAdminArea(), "").replace(" " + address.getPostalCode(), "")
                    .replace(", " + address.getCountryName(), "").replace(", " + address.getSubLocality(), ""));
            bundle.putString("FeatureName", address.getFeatureName());
            bundle.putString("Thoroughfare", address.getThoroughfare());
            bundle.putString("Locality", address.getLocality());
            bundle.putString("City", address.getSubAdminArea());
            bundle.putString("State", address.getAdminArea());
            bundle.putString("Country", address.getCountryName());
            bundle.putString("Pincode", address.getPostalCode());
            bundle.putString("SubLocality", address.getSubLocality());
            sendResultsToReceiver(2, bundle);
        }
    }

    //to send results to receiver in the source activity
    private void sendResultsToReceiver(int resultCode, Bundle bundle) {

        addressResultReceiver.send(resultCode, bundle);
    }
}