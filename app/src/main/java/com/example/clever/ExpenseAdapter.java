package com.example.clever;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense> {
    public ExpenseAdapter(Context context, List<Expense> expenses)
    {
        super(context, 0, expenses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Expense expense = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.expense_cell, parent, false);

        TextView name = convertView.findViewById(R.id.cellName);
        TextView price = convertView.findViewById(R.id.cellPrice);
        name.setText(expense.getName());

        DecimalFormat df = new DecimalFormat("0.00");
        price.setText("£" + df.format(Float.parseFloat(expense.getPrice())));

        return convertView;
    }
}
