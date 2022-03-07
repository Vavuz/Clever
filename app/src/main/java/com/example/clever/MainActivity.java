package com.example.clever;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // the title will be hidden
        getSupportActionBar().hide(); // the title bar will be hidden
        setContentView(R.layout.activity_main);

        // Button to add an expense to the list of expenses
        Button addExpenseButton = findViewById(R.id.add_expense);
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "A new expense has been added!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}