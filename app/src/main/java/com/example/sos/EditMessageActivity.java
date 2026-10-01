package com.example.sos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditMessageActivity extends AppCompatActivity {

    private EditText editTextMessage;
    private static final String PREFS_NAME = "SOSPrefs";
    private static final String KEY_MESSAGE = "sos_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_message);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Edit Emergency Message");
        }

        editTextMessage = findViewById(R.id.editTextMessage);
        Button btnSave = findViewById(R.id.btn_Save_Message);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedMessage = prefs.getString(KEY_MESSAGE, "I need help! This is my location.");
        editTextMessage.setText(savedMessage);

        btnSave.setOnClickListener(v -> {
            String newMessage = editTextMessage.getText().toString().trim();
            if (!newMessage.isEmpty()) {
                prefs.edit().putString(KEY_MESSAGE, newMessage).apply();
                Toast.makeText(this, "Message saved", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
