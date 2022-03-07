package com.example.clever;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class AddExpense extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);
        Toast.makeText(getBaseContext(), "In Add Expense activity", Toast.LENGTH_LONG).show();
    }
}
