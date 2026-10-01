package com.example.sos;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.List;

public class SmsSender {

    // ✅ Method 1: Send SMS to saved contacts using stored message + location
    public static void sendEmergencySms(Context context, String customMessageParam, Location location) {
        ContactManager contactManager = new ContactManager(context);
        List<ContactModel> contactList = contactManager.getContacts();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "SMS permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Use message from SharedPreferences
        String customMessage = context.getSharedPreferences("sos_settings", Context.MODE_PRIVATE)
                .getString("custom_message", "Emergency! I need help.");

        String locationUrl = (location != null)
                ? "https://maps.google.com/?q=" + location.getLatitude() + "," + location.getLongitude()
                : "Location unavailable";

        String fullMessage = customMessage + "\nMy Location: " + locationUrl;

        SmsManager smsManager = SmsManager.getDefault();

        for (ContactModel contact : contactList) {
            String phoneNumber = contact.getPhoneNumber();
            try {
                smsManager.sendTextMessage(phoneNumber, null, fullMessage, null, null);
            } catch (Exception e) {
                Toast.makeText(context, "Failed to send SMS to: " + phoneNumber, Toast.LENGTH_SHORT).show();
            }
        }

        Toast.makeText(context, "SOS message sent", Toast.LENGTH_SHORT).show();
    }

    // ✅ Method 2: Send to manually passed contact list
    public static void sendSmsToContacts(Context context, List<String> numbers, String message, Location location) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "SMS permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        String locationUrl = (location != null)
                ? "https://maps.google.com/?q=" + location.getLatitude() + "," + location.getLongitude()
                : "Location unavailable";

        String finalMessage = message + "\nMy Location: " + locationUrl;

        SmsManager smsManager = SmsManager.getDefault();

        for (String number : numbers) {
            try {
                smsManager.sendTextMessage(number, null, finalMessage, null, null);
            } catch (Exception e) {
                Toast.makeText(context, "Failed to send SMS to: " + number, Toast.LENGTH_SHORT).show();
            }
        }

        Toast.makeText(context, "SMS sent", Toast.LENGTH_SHORT).show();
    }
}
