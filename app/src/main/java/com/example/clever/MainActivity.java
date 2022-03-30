package com.example.clever;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Date;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    private ListView expenseListView;
    public static float totalExpensePerDay = 0;
    private MediaPlayer buttonSound;

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

        // Add expense button
        FloatingActionButton  addExpenseBtn = findViewById(R.id.add_expense);
        addExpenseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSound.start();
                Intent addExpenseActivity = new Intent(MainActivity.this, AddExpense.class);
                startActivityForResult(addExpenseActivity, 1);
            }
        });

        // Settings button
        Button settingsBtn = findViewById(R.id.settings);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSound.start();
                Intent settingsActivity = new Intent(MainActivity.this, Settings.class);
                startActivityForResult(settingsActivity, 1);
            }
        });

        // ViewPager2 creation
        ViewPager2 totalPager = (ViewPager2) findViewById(R.id.total_expenses);
        ArrayList<ViewPagerItem> viewPagerItems = new ArrayList<>();
        viewPagerItems.add(new ViewPagerItem(getString(R.string.daily_total), totalExpensePerDay));
        viewPagerItems.add(new ViewPagerItem(getString(R.string.weekly_total), totalExpensePerDay * 7));
        viewPagerItems.add(new ViewPagerItem(getString(R.string.monthly_total), totalExpensePerDay * 30));
        viewPagerItems.add(new ViewPagerItem(getString(R.string.yearly_total), totalExpensePerDay * 365));

        // ViewPager2 adapter creation
        ViewPagerAdapter adapter = new ViewPagerAdapter(viewPagerItems);
        totalPager.setAdapter(adapter);
        totalPager.setClipToPadding(false);
        totalPager.setClipChildren(false);
        totalPager.setOffscreenPageLimit(2);
        totalPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        CircleIndicator3 indicator = (CircleIndicator3) findViewById(R.id.tab_view);
        indicator.setViewPager(totalPager);

    }

    private void initWidgets()
    {
        expenseListView = findViewById(R.id.expenses_list);
        buttonSound = MediaPlayer.create(this, R.raw.any_button_sound);
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
                buttonSound.start();
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