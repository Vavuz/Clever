package com.example.clever;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.view.Window;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // the title will be hidden
        getSupportActionBar().hide(); // the title bar will be hidden
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        // List view setting up
        ListView attractionDetails = (ListView) findViewById (R.id.expenses_list);

        //Navigation back is via phones back button
        final String[] attractionDetailsArray = new String[] { "Netflix",
                "Spotify",
                "Gym",
                "Vodafone",
                "Sky",
                "Google Drive",
                "Amazon Prime",
                "Prime Video",
                "Deliveroo Plus"};

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                attractionDetailsArray);

        attractionDetails.setAdapter(adapter);


        // Button to add an expense to the list of expenses
        Button addExpenseButton = findViewById(R.id.add_expense);
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addExpense = new Intent(MainActivity.this, AddExpense.class);
                startActivityForResult(addExpense, 1);
            }
        });
    }
}