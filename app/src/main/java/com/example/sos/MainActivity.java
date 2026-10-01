package com.example.sos;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnSOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSOS = findViewById(R.id.btn_SOS);
        ImageButton btnSettings = findViewById(R.id.btn_Settings);
        ImageButton btnContacts = findViewById(R.id.btn_Contacts);

        if (btnSettings != null) {
            btnSettings.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));
        }
        if (btnContacts != null) {
            btnContacts.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ManageContactsActivity.class)));
        }

        requestAllPermissions();

        if (btnSOS != null) {
            btnSOS.setOnClickListener(v -> sendSOSStatic(MainActivity.this));
        }
    }

    public static void sendSOSStatic(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (lm != null && !lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(context, "⚠️ Please enable GPS", Toast.LENGTH_LONG).show();
        }

        LocationHelper locationHelper = new LocationHelper(
                context,
                location -> {
                    if (location != null) {
                        SharedPreferences prefs = context.getSharedPreferences("SOSPrefs", MODE_PRIVATE);
                        String customMsg = prefs.getString("sos_message", "Emergency! I need help.");

                        String locationMessage = customMsg + "\nLocation: https://maps.google.com/?q=" +
                                location.getLatitude() + "," + location.getLongitude();

                        // Send Emergency SMS
                        SmsSender.sendEmergencySms(context, locationMessage, location);

                        // Start Audio Recording
                        try {
                            context.startService(new Intent(context, AudioRecordService.class));
                        } catch (Exception e) {
                            Log.e("MainActivity", "Error starting AudioRecordService", e);
                        }

                        // Make Emergency Call (e.g. 112)
                        if (context instanceof Activity) {
                            try {
                                EmergencyCallHelper.makeEmergencyCall((Activity) context, "112");
                            } catch (Exception e) {
                                Log.e("MainActivity", "Error in EmergencyCallHelper", e);
                            }
                        }

                        Toast.makeText(context, "SOS sent!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed to fetch location", Toast.LENGTH_SHORT).show();
                    }
                },
                Looper.getMainLooper()
        );

        locationHelper.fetchLocation();
    }

    private void requestAllPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.SEND_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.RECORD_AUDIO,
        }, 101);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_manage_contacts) {
            startActivity(new Intent(this, ManageContactsActivity.class));
            return true;
        } else if (id == R.id.menu_edit_message) {
            startActivity(new Intent(this, EditMessageActivity.class));
            return true;
        } else if (id == R.id.menu_view_contacts) {
            startActivity(new Intent(this, ViewNumberActivity.class));
            return true;
        } else if (id == R.id.menu_register_multiple) {
            startActivity(new Intent(this, RegisterMultipleContactsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
