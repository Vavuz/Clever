package com.example.clever;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    // Properties instantiation
    ArrayList<ViewPagerItem> viewPagerItems;

    /**
     * Constructor
     * @param viewPagerItems
     */
    public ViewPagerAdapter(ArrayList<ViewPagerItem> viewPagerItems) {
        this.viewPagerItems = viewPagerItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.total_expenses_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewPagerItem viewPagerItem = viewPagerItems.get(position);
        holder.totalTypeText.setText(viewPagerItem.type);
        holder.totalPriceText.setText("£" + viewPagerItem.total);
    }

    /**
     * Returns viewPagerItems length
     * @return
     */
    @Override
    public int getItemCount() {
        return viewPagerItems.size();
    }

    /**
     * Creates a view for the total_expenses cells with time frame and total price
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView totalTypeText;
        TextView totalPriceText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            totalTypeText = itemView.findViewById(R.id.total_type);
            totalPriceText = itemView.findViewById(R.id.total_price);
        }
    }
}
