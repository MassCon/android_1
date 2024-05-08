package com.example.helloworld;


import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.Contact;
import com.example.helloworld.ContactAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private RecyclerView contactsRecyclerView;
    private List<Contact> contactList;
    private ContactAdapter contactAdapter; // You'll need to create this adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addContactButton = findViewById(R.id.addContactButton);
        addContactButton.setOnClickListener(view -> showAddContactDialog());

        searchEditText = findViewById(R.id.searchEditText);
        contactsRecyclerView = findViewById(R.id.contactsRecyclerView);

        contactList = new ArrayList<>();
        // You'll eventually load contacts from the database here

        contactAdapter = new ContactAdapter(contactList);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsRecyclerView.setAdapter(contactAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterContacts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterContacts(String searchText) {
        List<Contact> filteredList = new ArrayList<>();
        for (Contact contact : contactList) {
            if (contact.getName().toLowerCase().contains(searchText.toLowerCase()) ||
                    contact.getPhoneNumber().contains(searchText)) {
                filteredList.add(contact);
            }
        }

        contactAdapter.updateContactList(filteredList);
    }

    // In MainActivity:
    private void showAddContactDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_contact);

        EditText nameEditText = dialog.findViewById(R.id.nameEditText);
        EditText phoneEditText = dialog.findViewById(R.id.phoneEditText);
        Button saveButton = dialog.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(view -> {
            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            Contact newContact = new Contact(name, phone);
            contactList.add(newContact);
            contactAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        dialog.show();
    }
}