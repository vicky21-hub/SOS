package com.example.sos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Collections;

public class SmsSenderActivity extends AppCompatActivity {

    private static final int REQUEST_SMS_PERMISSION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_sender);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Send Emergency SMS");
        }

        EditText editPhone = findViewById(R.id.editPhoneNumber);
        EditText editMessage = findViewById(R.id.editMessage);
        Button btnSend = findViewById(R.id.btn_SendSms);

        btnSend.setOnClickListener(v -> {
            String phone = editPhone.getText().toString();
            String message = editMessage.getText().toString();

            if (phone.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_SMS_PERMISSION);
            } else {
                new LocationHelper(this, location -> {
                    if (location != null) {
                        SmsSender.sendSmsToContacts(this, Collections.singletonList(phone), message, location);
                    } else {
                        Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
                    }
                }, Looper.getMainLooper()).fetchLocation();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
