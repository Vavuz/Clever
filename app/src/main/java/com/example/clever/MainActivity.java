package com.example.clever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ListView expenseListView;
    public static float totalExpensePerDay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // the title will be hidden
        getSupportActionBar().hide(); // the title bar will be hidden
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        initWidgets();
        setExpenseAdapter();
        loadFromDBToMemory();
        setOnClickListener();

        // Get total expenses per day
        Date tempDate = new Date(2001, 12, 21);
        Expense tempExpense = new Expense(0,"a","0","Monthly",tempDate);
        for (Expense expense1 : tempExpense.nonDeletedExpenses()){
            String type = expense1.getSubscriptionType();
            float fullPrice = Float.parseFloat(expense1.getPrice());
            totalExpensePerDay += fullPrice / tempExpense.subscriptionsDict.get(expense1.getSubscriptionType());
        }
        Toast.makeText(getBaseContext(), totalExpensePerDay + " EDDAJE COMEE ON!", Toast.LENGTH_LONG).show();

        FloatingActionButton  addExpenseBtn = findViewById(R.id.add_expense);
        addExpenseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityA = new Intent(MainActivity.this, AddExpense.class);
                startActivityForResult(activityA, 1);
            }
        });

        ViewPager2 totalPager = (ViewPager2) findViewById(R.id.total_expenses);
        ArrayList<ViewPagerItem> viewPagerItems = new ArrayList<>();
        viewPagerItems.add(new ViewPagerItem("Daily total:", totalExpensePerDay));
        viewPagerItems.add(new ViewPagerItem("Weekly total:", totalExpensePerDay * 7));
        viewPagerItems.add(new ViewPagerItem("Monthly total:", totalExpensePerDay * 30));
        viewPagerItems.add(new ViewPagerItem("Yearly total:", totalExpensePerDay * 365));

        ViewPagerAdapter adapter = new ViewPagerAdapter(viewPagerItems);
        totalPager.setAdapter(adapter);
        totalPager.setClipToPadding(false);
        totalPager.setClipChildren(false);
        totalPager.setOffscreenPageLimit(2);
        totalPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
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