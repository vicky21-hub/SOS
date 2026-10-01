package com.example.sos;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.List;

public class SOSUtils {

    public static void sendSOS(Context context, PreferencesHelper preferencesHelper) {
        ContactManager contactManager = new ContactManager(context);
        List<ContactModel> contacts = contactManager.getContacts();

        if (contacts.isEmpty()) {
            Toast.makeText(context, "No emergency contacts registered.", Toast.LENGTH_LONG).show();
            return;
        }

        String customMessage = preferencesHelper.getSOSMessage();
        String locationInfo = "";
        Location location = getLastKnownLocation(context);
        if (location != null) {
            locationInfo = " Location: https://maps.google.com/?q=" + location.getLatitude() + "," + location.getLongitude();
        } else {
            locationInfo = " Location unavailable.";
        }

        String fullMessage = customMessage + locationInfo;
        SmsManager smsManager = SmsManager.getDefault();

        for (ContactModel contact : contacts) {
            String phoneNumber = contact.getPhoneNumber();
            try {
                smsManager.sendTextMessage(phoneNumber, null, fullMessage, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(context, "SOS messages sent.", Toast.LENGTH_SHORT).show();
    }

    public static void callEmergencyNumber(Context context, String unusedNumber) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Call permission not granted.", Toast.LENGTH_SHORT).show();
            return;
        }

        ContactManager contactManager = new ContactManager(context);
        List<ContactModel> contacts = contactManager.getContacts();

        if (!contacts.isEmpty()) {
            String phoneNumber = contacts.get(0).getPhoneNumber(); // ✅ Correct
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            context.startActivity(callIntent);
        } else {
            Toast.makeText(context, "No emergency contact to call.", Toast.LENGTH_SHORT).show();
        }
    }

    private static Location getLastKnownLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (locationGPS != null) return locationGPS;
        if (locationNetwork != null) return locationNetwork;

        return null;
    }
}