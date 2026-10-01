package com.example.sos;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    private static final String PREFS_NAME = "contacts_prefs";
    private static final String KEY_CONTACTS = "contacts_list";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public ContactManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();

    }

    public List<ContactModel> getContacts() {
        String json = sharedPreferences.getString(KEY_CONTACTS, null);
        Type type = new TypeToken<List<ContactModel>>() {}.getType();
        return json == null ? new ArrayList<>() : gson.fromJson(json, type);
    }

    public List<String> getContactNumbers() {
        List<String> numbers = new ArrayList<>();
        for (ContactModel model : getContacts()) {
            numbers.add(model.getPhoneNumber());
        }
        return numbers;
    }

    public void saveContacts(List<ContactModel> contacts) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(contacts);
        editor.putString(KEY_CONTACTS, json);
        editor.apply();
    }

    public void addContact(ContactModel contact) {
        List<ContactModel> contacts = getContacts();
        contacts.add(contact);
        saveContacts(contacts);
    }

    public void deleteContact(int index) {
        List<ContactModel> contacts = getContacts();
        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            saveContacts(contacts);
        }
    }
}
