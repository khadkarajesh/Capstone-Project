package com.example.rajesh.expensetracker.category;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rajesh.expensetracker.R;
import com.example.rajesh.expensetracker.widget.CircularView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ExpenseHolder> {

    ArrayList<ExpenseCategory> expense = new ArrayList<>();

    public CategoryAdapter(ArrayList<ExpenseCategory> expenses) {
        this.expense = expenses;
    }

    @Override
    public ExpenseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_category_list_layout, parent, false);
        return new ExpenseHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseHolder holder, int position) {
        holder.expenseTitle.setText(expense.get(position).categoryTitle);
        //holder.circularView.setBackgroundColor(Color.parseColor(expense.get(position).categoryColor));
        holder.circularView.setBackgroundColor(Color.parseColor(expense.get(position).categoryColor));

    }

    @Override
    public int getItemCount() {
        return expense.size();
    }

    public void addCategories(ArrayList<ExpenseCategory> expenses) {
        expense.addAll(expenses);
        notifyDataSetChanged();
    }

    public static class ExpenseHolder extends RecyclerView.ViewHolder {
        TextView expenseTitle;
        CircularView circularView;

        public ExpenseHolder(View itemView) {
            super(itemView);
            expenseTitle = (TextView) itemView.findViewById(R.id.tv_categories_title);
            circularView = (CircularView) itemView.findViewById(R.id.iv_circular_image);
        }
    }
}
