package com.example.sos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "sos_settings";
    private static final String KEY_SHAKE_ENABLED = "shake_enabled";
    private static final String KEY_POWER_ENABLED = "power_enabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Settings");
        }

        SwitchCompat switchShake = findViewById(R.id.switchShake);
        SwitchCompat switchPowerButton = findViewById(R.id.switchPowerButton);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Load saved toggle states
        switchShake.setChecked(prefs.getBoolean(KEY_SHAKE_ENABLED, false));
        switchPowerButton.setChecked(prefs.getBoolean(KEY_POWER_ENABLED, false));

        // Shake detection toggle
        switchShake.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean(KEY_SHAKE_ENABLED, isChecked).apply();
            Toast.makeText(this, "Shake Detection " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();

            Intent shakeIntent = new Intent(this, ShakeService.class);
            if (isChecked) startService(shakeIntent);
            else stopService(shakeIntent);
        });

        // Power button toggle logic
        switchPowerButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean(KEY_POWER_ENABLED, isChecked).apply();
            Toast.makeText(this, "Power Button SOS " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
