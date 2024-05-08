package com.example.helloworld;


import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.Contact;
import com.example.helloworld.ContactAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Random random = new Random();

    private List<Account> accounts;
    private List<AccountHolder> accountHolders;
    private List<Operation> operations;

    private Spinner senderSpinner;
    private Spinner receiverSpinner;
    private ListView listViewAccounts;
    private EditText editTextAmount;
    private Button sendButton;
    private Button cancelButton;

    private Operation lastOperation;

    private Boolean isProcessing = false;
    private AsyncTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        senderSpinner = findViewById(R.id.senderSpinner);
        receiverSpinner = findViewById(R.id.receiverSpinner);
        listViewAccounts = findViewById(R.id.listViewAccounts);
        editTextAmount = findViewById(R.id.editTextAmount);
        sendButton = findViewById(R.id.buttonSend);
        cancelButton = findViewById(R.id.buttonCancel);

        // Initialize data
        initializeData();

        // Set up spinners
        setUpData();

        setButtonClickListeners();

    }

    private void initializeData() {
        // Generate 5 accounts and account holders
        accountHolders = generateAccountHolders();
        accounts = generateAccounts();

        // Initialize operations list
        operations = new ArrayList<>();
    }

    private List<AccountHolder> generateAccountHolders() {
        ArrayList<AccountHolder> generatedAccountHolders = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            AccountHolder accountHolder = new AccountHolder();
            accountHolder.setName("Mykola " + (i + 1));
            accountHolder.setSurname("Galichanyn" + (i + 1));
            accountHolder.setCreditRate(0.7);
            accountHolder.setRegistrationDT(new Date());
            generatedAccountHolders.add(accountHolder);
        }

        return generatedAccountHolders;
    }

    private List<Account> generateAccounts() {
        List<Account> generatedAccounts = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Account account = new Account();
            account.setHolder(accountHolders.get(i));
            account.setMoney( 100 + (10000 - 100) * random.nextDouble() );
            account.setOpenDT(new Date());
            generatedAccounts.add(account);
        }

        return generatedAccounts;
    }

    private void setUpData() {
        // Set up senderSpinner and receiverSpinner using ArrayAdapter
        ArrayAdapter<Account> accountAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, accounts);
        accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        senderSpinner.setAdapter(accountAdapter);
        receiverSpinner.setAdapter(accountAdapter);
        listViewAccounts.setAdapter(accountAdapter);
    }

    private void setButtonClickListeners() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Send button click
                isProcessing = true;
                sendMoney();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the Cancel button click
                if(isProcessing == true) {
                    cancelOperation();
                }
            }
        });
    }

    private void sendMoney() {
        // Get selected sender and receiver accounts
        Account senderAccount = (Account) senderSpinner.getSelectedItem();
        Account receiverAccount = (Account) receiverSpinner.getSelectedItem();
        Double amount = Double.parseDouble(editTextAmount.getText().toString());

        // Create a new operation
        Operation operation = new Operation();
        operation.setIncome(false);
        operation.setOperationDT(new Date());
        operation.setStatus(Operation.Status.Processing);
        operation.setSender(senderAccount);
        operation.setReciver(receiverAccount);
        operation.setAmount(amount);

        senderAccount.setMoney(senderAccount.getMoney() - amount);
        receiverAccount.setMoney(receiverAccount.getMoney() + amount);

        // Add the operation to the operations list
        operations.add(operation);

        // Start the background task for the transaction
        task = new TransactionTask().execute(senderAccount, receiverAccount, operation, amount);
    }

    private void cancelOperation() {
        if(isProcessing) {
            task.cancel(true);
            lastOperation.Cancel();
        }
        Toast.makeText(this, "Operation Canceled", Toast.LENGTH_SHORT).show();
    }

    private class TransactionTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected Void doInBackground(Object... params) {
            Operation operation = (Operation) params[2];

            lastOperation = operation;

            operation.setStatus(Operation.Status.Processing);

            // Simulate a 10-second transaction process
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update the operation status to Ready
            operation.setStatus(Operation.Status.Ready);
            isProcessing = false;

            // Update the UI on the main thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Notify the adapter that the data set has changed
                    ((ArrayAdapter) listViewAccounts.getAdapter()).notifyDataSetChanged();
                }
            });

            return null;
        }
    }
}