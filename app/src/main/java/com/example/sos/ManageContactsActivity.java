package com.example.sos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ManageContactsActivity extends AppCompatActivity implements ContactAdapter.OnDeleteClickListener {

    private EditText nameEditText, phoneEditText;
    private Button addButton;
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private ContactManager contactManager;
    private List<ContactModel> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_contacts);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Emergency Contacts");
        }

        nameEditText = findViewById(R.id.edit_contact_name);
        phoneEditText = findViewById(R.id.edit_contact_number);
        addButton = findViewById(R.id.btn_add_contact);
        recyclerView = findViewById(R.id.recycler_contacts);

        contactManager = new ContactManager(this);
        contactList = contactManager.getContacts();

        contactAdapter = new ContactAdapter(contactList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactAdapter);

        addButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please enter both name and phone.", Toast.LENGTH_SHORT).show();
                return;
            }

            ContactModel newContact = new ContactModel(name, phone);
            contactManager.addContact(newContact);
            contactList.add(newContact);
            contactAdapter.notifyItemInserted(contactList.size() - 1);
            nameEditText.setText("");
            phoneEditText.setText("");
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onDeleteClick(int position) {
        contactManager.deleteContact(position);
        contactList.remove(position);
        contactAdapter.notifyItemRemoved(position);
    }
}
