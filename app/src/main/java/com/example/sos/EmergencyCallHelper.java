package com.example.sos;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.app.ActivityCompat;

public class EmergencyCallHelper {

    public static final int REQUEST_CALL_PERMISSION = 101;

    public static void makeEmergencyCall(Activity activity, String phoneNumber) {
        // Check for permission
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Request permission if not granted
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PERMISSION
            );
            return;
        }

        // Make the call
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        activity.startActivity(callIntent);
    }


}