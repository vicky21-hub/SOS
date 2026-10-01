package com.example.sos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteNumberActivity extends AppCompatActivity {

    private TextView txtSavedNumber;
    private Button btnDelete;
    private PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_number);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Delete Saved Number");
        }

        preferencesHelper = new PreferencesHelper(this);
        txtSavedNumber = findViewById(R.id.txt_Saved_Number);
        btnDelete = findViewById(R.id.button_Delete_Number);

        String savedNumber = preferencesHelper.getPhoneNumber();
        if (savedNumber != null) {
            txtSavedNumber.setText(getString(R.string.saved_number, savedNumber));
        } else {
            txtSavedNumber.setText(R.string.no_number_saved);
            btnDelete.setEnabled(false);
        }

        btnDelete.setOnClickListener(v -> {
            preferencesHelper.clearPhoneNumber();
            Toast.makeText(this, getString(R.string.number_deleted), Toast.LENGTH_SHORT).show();
            txtSavedNumber.setText(R.string.no_number_saved);
            btnDelete.setEnabled(false);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
