package com.example.clever;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    private ListView expenseListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // the title will be hidden
        getSupportActionBar().hide(); // the title bar will be hidden
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        initWidgets();
        loadFromDBToMemory();
        setExpenseAdapter();
        setOnClickListener();

        Button addExpenseBtn = (Button) findViewById(R.id.add_expense);
        addExpenseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityA = new Intent(MainActivity.this, AddExpense.class);
                startActivityForResult(activityA, 1);
            }
        });
    }


    private void initWidgets()
    {
        expenseListView = findViewById(R.id.expenses_list);
    }

    private void loadFromDBToMemory()
    {
        ExpensesDatabaseManager expensesDatabaseManager = ExpensesDatabaseManager.instanceOfDatabase(this);
        expensesDatabaseManager.populateExpenseListArray();
    }

    private void setExpenseAdapter()
    {
        ExpenseAdapter expenseAdapter = new ExpenseAdapter(getApplicationContext(), Expense.nonDeletedExpenses());
        expenseListView.setAdapter(expenseAdapter);
    }


    private void setOnClickListener()
    {
        expenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Expense selectedExpense = (Expense) expenseListView.getItemAtPosition(position);
                Intent editExpenseIntent = new Intent(getApplicationContext(), AddExpense.class);
                editExpenseIntent.putExtra(Expense.EXPENSE_EDIT_EXTRA, selectedExpense.getId());
                startActivity(editExpenseIntent);
            }
        });
    }


    public void newExpense(View view)
    {
        Intent newExpenseIntent = new Intent(this, AddExpense.class);
        startActivity(newExpenseIntent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setExpenseAdapter();
    }
}