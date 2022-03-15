package com.example.clever;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Date;

public class AddExpense extends AppCompatActivity {

    // Interface's features initialisation
    private EditText nameEditText, priceEditText, subscriptionTypeEditText;
    private Button deleteButton;
    private Expense selectedExpense;

    /**
     * Activity's creation method
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // the title will be hidden
        getSupportActionBar().hide(); // the title bar will be hidden
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.add_expense);
        initWidgets();
        checkForEditExpense();

        /*
        _binding = FragmentFirstBinding;
        String[] types = getResources().getStringArray(R.array.types);
        String[] arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown, types);
        binding.autoCompleteTextView.setAdapter(arrayAdapter);

         */

        // Button to add an expense to the database
        Button saveExpenseBtn = (Button) findViewById(R.id.add);
        saveExpenseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), nameEditText.getText() + " has been added!", Toast.LENGTH_LONG).show();
                saveExpense(v);
            }
        });

        // Button to delete an expense from the database
        Button button = (Button) findViewById(R.id.deleteExpenseButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), nameEditText.getText() + " has been deleted!", Toast.LENGTH_LONG).show();
                deleteExpense(v);
            }
        });
    }

    /**
     * Interface's features instantiation
     */
    private void initWidgets()
    {
        nameEditText = findViewById(R.id.expense_name);
        priceEditText = findViewById(R.id.expense_price);
        subscriptionTypeEditText = findViewById(R.id.expense_type);
        deleteButton = findViewById(R.id.deleteExpenseButton);
    }

    private void checkForEditExpense()
    {
        Intent previousIntent = getIntent();

        int passedExpenseID = previousIntent.getIntExtra(Expense.EXPENSE_EDIT_EXTRA, -1);
        selectedExpense = Expense.getExpenseForID(passedExpenseID);

        if (selectedExpense != null)
        {
            nameEditText.setText(selectedExpense.getName());
            priceEditText.setText(selectedExpense.getPrice());
            subscriptionTypeEditText.setText(selectedExpense.getSubscriptionType());
        }
        else
        {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    public void saveExpense(View view)
    {
        ExpensesDatabaseManager sqLiteManager = ExpensesDatabaseManager.instanceOfDatabase(this);
        String name = String.valueOf(nameEditText.getText());
        String price = String.valueOf(priceEditText.getText());
        String subscriptionType = String.valueOf(subscriptionTypeEditText.getText());

        if(selectedExpense == null)
        {
            int id = Expense.expenseArrayList.size();
            Expense newExpense = new Expense(id, name, price, subscriptionType);
            Expense.expenseArrayList.add(newExpense);
            sqLiteManager.addExpenseToDatabase(newExpense);
        }
        else
        {
            selectedExpense.setName(name);
            selectedExpense.setPrice(price);
            selectedExpense.setSubscriptionType(subscriptionType);
            sqLiteManager.updateExpenseInDB(selectedExpense);
        }

        finish();
    }

    public void deleteExpense(View view)
    {
        selectedExpense.setDeleted(new Date());
        ExpensesDatabaseManager sqLiteManager = ExpensesDatabaseManager.instanceOfDatabase(this);
        sqLiteManager.updateExpenseInDB(selectedExpense);
        finish();
    }
}
