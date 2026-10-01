package com.example.sos;
import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LocationUtils {

    public interface LocationCallback {
        void onLocationReceived(String locationUrl);
    }

    @SuppressLint("MissingPermission")
    public static void getLocation(Context context, LocationCallback callback) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                String url = "http://maps.google.com/?q=" + location.getLatitude() + "," + location.getLongitude();
                callback.onLocationReceived(url);
            } else {
                Toast.makeText(context, "Unable to get location", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e ->
                Toast.makeText(context, "Location error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
        );

    }
}
