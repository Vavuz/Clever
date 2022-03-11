package com.example.clever;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class AddExpense extends AppCompatActivity {

    private EditText nameEditText, priceEditText, subscriptionTypeEditText;
    private Button deleteButton;
    private Expense selectedExpense;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);
        initWidgets();
        checkForEditExpense();

        Button saveExpenseBtn = (Button) findViewById(R.id.add);
        saveExpenseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveExpense(v);
            }
        });

        Button button = (Button) findViewById(R.id.deleteExpenseButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteExpense(v);
            }
        });
    }

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
