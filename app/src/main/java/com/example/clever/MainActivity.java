package com.example.clever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.view.Window;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import me.relex.circleindicator.CircleIndicator3;


public class MainActivity extends AppCompatActivity {

    // Properties instantiation
    private ListView expenseListView;
    public static float totalExpensePerDay;
    private MediaPlayer buttonSound;
    public static boolean soundOn = true;
    public static boolean populateFreedom = true;
    public static boolean secondPopulateFreedom = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Activity initialisation
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // the title will be hidden
        getSupportActionBar().hide(); // the title bar will be hidden
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        // Layout and database initialisation
        initWidgets();
        setExpenseAdapter();
        loadFromDBToMemory();
        setOnClickListener();

        // Getting the total money to spend
        totalExpensePerDay = 0;
        for (Expense expense : Expense.nonDeletedExpenses()){
            String type = expense.getSubscriptionType();
            float fullPrice = Float.parseFloat(expense.getPrice());
            totalExpensePerDay += fullPrice / expense.subscriptionsDict.get(type);
        }

        // Add expense button
        FloatingActionButton  addExpenseBtn = findViewById(R.id.add_expense);
        addExpenseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MainActivity.soundOn) { buttonSound.start(); }
                Intent addExpenseActivity = new Intent(MainActivity.this, AddExpense.class);
                startActivityForResult(addExpenseActivity, 1);
            }
        });

        // Settings button
        Button settingsBtn = findViewById(R.id.settings);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                populateFreedom = false;
                secondPopulateFreedom = false;
                if (MainActivity.soundOn) { buttonSound.start(); }
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

        // ViewPager2 Circle Indicator creation
        CircleIndicator3 indicator = (CircleIndicator3) findViewById(R.id.tab_view);
        indicator.setViewPager(totalPager);
    }

    /**
     * Properties initialisation
     */
    private void initWidgets()
    {
        expenseListView = findViewById(R.id.expenses_list);
        totalExpensePerDay = 0;
        buttonSound = MediaPlayer.create(this, R.raw.any_button_sound);
    }

    /**
     * Loads expenses form the local database
     */
    private void loadFromDBToMemory()
    {
        if (populateFreedom) {
            ExpensesDatabaseManager expensesDatabaseManager = ExpensesDatabaseManager.instanceOfDatabase(this);
            expensesDatabaseManager.populateExpenseListArray();
        }
    }

    /**
     * Sets the adapter of the ListView
     */
    private void setExpenseAdapter()
    {
        ExpenseAdapter expenseAdapter = new ExpenseAdapter(getApplicationContext(), Expense.nonDeletedExpenses());
        expenseListView.setAdapter(expenseAdapter);
    }

    /**
     * OnClickListener for the expenses in the ListView
     */
    private void setOnClickListener()
    {
        expenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                if (MainActivity.soundOn) { buttonSound.start(); }
                Expense selectedExpense = (Expense) expenseListView.getItemAtPosition(position);
                Intent editExpenseIntent = new Intent(getApplicationContext(), AddExpense.class);
                editExpenseIntent.putExtra(Expense.EXPENSE_EDIT_EXTRA, selectedExpense.getId());
                startActivity(editExpenseIntent);
            }
        });
    }

    /**
     * When the MainActivity is resumed
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        setExpenseAdapter();
        // Avoid duplication of data
        try {
            ComponentName componentName = this.getCallingActivity();
            if (componentName.toString().endsWith("AddExpense}") && secondPopulateFreedom) {

                populateFreedom = true;
            }
        } catch (Exception e) {
            // skip
        }
    }
}