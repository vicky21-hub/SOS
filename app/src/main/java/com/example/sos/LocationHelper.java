package com.example.sos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;


import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
public class LocationHelper {

    public interface MyLocationCallback {
        void onLocationResult(Location location);

    }

    private final Context context;
    private final MyLocationCallback callback;
    private final Looper looper;
    private final FusedLocationProviderClient fusedLocationClient;

    public LocationHelper(Context context, MyLocationCallback callback, Looper looper) {
        this.context = context;
        this.callback = callback;
        this.looper = looper;
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @SuppressLint("MissingPermission")
    public void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            callback.onLocationResult(null);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000)
                .setNumUpdates(1);

        fusedLocationClient.requestLocationUpdates(locationRequest, new com.google.android.gms.location.LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null && !locationResult.getLocations().isEmpty()) {
                    callback.onLocationResult(locationResult.getLastLocation());
                } else {
                    callback.onLocationResult(null);
                }
            }
        }, looper);
    }
}