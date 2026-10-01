package com.example.sos;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterNumberActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "sos_prefs";
    private static final String KEY_PHONE_NUMBER = "phone_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_number);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Register Primary Contact");
        }

        EditText editTextNumber = findViewById(R.id.editTextNumber);
        Button btnSave = findViewById(R.id.btn_Save_Number);

        btnSave.setOnClickListener(v -> {
            String number = editTextNumber.getText().toString().trim();
            if (!number.isEmpty()) {
                savePhoneNumber(number);
                Toast.makeText(RegisterNumberActivity.this, "Number saved", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(RegisterNumberActivity.this, "Please enter a number", Toast.LENGTH_SHORT).show();
            }
        });

        String savedNumber = getSavedPhoneNumber();
        if (!savedNumber.isEmpty()) {
            editTextNumber.setText(savedNumber);
        }
    }

    private void savePhoneNumber(String number) {
        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_PHONE_NUMBER, number)
                .apply();
    }

    private String getSavedPhoneNumber() {
        return getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(KEY_PHONE_NUMBER, "");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
