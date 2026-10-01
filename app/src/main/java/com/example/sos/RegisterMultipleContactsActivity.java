package com.example.sos;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RegisterMultipleContactsActivity extends AppCompatActivity {

    private EditText editName, editPhone;
    private Button btnAdd;
    private RecyclerView recyclerView;

    private ContactManager contactManager;
    private ContactAdapter adapter;
    private List<ContactModel> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_multiple_contacts);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Register Contacts");
        }

        contactManager = new ContactManager(this);
        contactList = contactManager.getContacts();

        editName = findViewById(R.id.edit_contact_name);
        editPhone = findViewById(R.id.edit_contact_phone);
        btnAdd = findViewById(R.id.btn_add_contact);
        recyclerView = findViewById(R.id.recycler_contacts);

        adapter = new ContactAdapter(contactList, position -> {
            contactManager.deleteContact(position);
            contactList.remove(position);
            adapter.notifyItemRemoved(position);
            Toast.makeText(this, "Contact Deleted", Toast.LENGTH_SHORT).show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
                Toast.makeText(this, "Please enter name and phone", Toast.LENGTH_SHORT).show();
                return;
            }

            ContactModel newContact = new ContactModel(name, phone);
            contactManager.addContact(newContact);
            contactList.add(newContact);
            adapter.notifyItemInserted(contactList.size() - 1);

            editName.setText("");
            editPhone.setText("");
            Toast.makeText(this, "Contact Added", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
