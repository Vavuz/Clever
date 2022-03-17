package com.example.clever;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class AddExpense extends AppCompatActivity {

    // Interface's features initialisation
    private EditText nameEditText, priceEditText;
    private Spinner typeSpinner;
    private FloatingActionButton deleteButton;
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

        // Button to add an expense to the database
        FloatingActionButton saveExpenseBtn = (FloatingActionButton) findViewById(R.id.add);
        saveExpenseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (nameEditText.getText().toString().isEmpty() || priceEditText.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(), "You are missing a field!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getBaseContext(), nameEditText.getText() + " has been saved!", Toast.LENGTH_LONG).show();
                    saveExpense(v);
                }
            }
        });

        // Button to delete an expense from the database
        deleteButton.setOnClickListener(new View.OnClickListener() {
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
        deleteButton = findViewById(R.id.deleteExpenseButton);

        // Spinner for expense type
        typeSpinner = findViewById(R.id.expense_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
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
            typeSpinner.setSelection(getIndex(typeSpinner, selectedExpense.getSubscriptionType(), typeSpinner.getCount()));
        }
        else
        {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Finds the index of a specific element of the dropdown menu
     * @param provaUnoDueSpinner
     * @param subscriptionType
     * @param count
     * @return
     */
    private int getIndex(Spinner provaUnoDueSpinner, String subscriptionType, int count) {
        for (int i = 0; i < count; i++) {
            if (provaUnoDueSpinner.getItemAtPosition(i).toString().equals(subscriptionType)) {
                return i;
            }
        }
        return 0;
    }

    public void saveExpense(View view)
    {
        ExpensesDatabaseManager sqLiteManager = ExpensesDatabaseManager.instanceOfDatabase(this);
        String name = String.valueOf(nameEditText.getText());
        String price = String.valueOf(priceEditText.getText());
        String subscriptionType = typeSpinner.getSelectedItem().toString();

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
