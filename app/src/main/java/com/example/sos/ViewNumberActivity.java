package com.example.sos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewNumberActivity extends AppCompatActivity {

    private TextView txtViewNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_number);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("View Primary Number");
        }

        txtViewNumber = findViewById(R.id.txtViewNumber);
        SharedPreferences prefs = getSharedPreferences("sos_prefs", MODE_PRIVATE);
        String phoneNumber = prefs.getString("sos_number", "No number saved.");
        txtViewNumber.setText(phoneNumber);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
