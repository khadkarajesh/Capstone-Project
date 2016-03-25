package com.example.rajesh.expensetracker.dashboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rajesh.expensetracker.R;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder> {

    ArrayList<Expense> expense = new ArrayList<>();

    public ExpenseAdapter(ArrayList<Expense> expenses) {
        this.expense = expenses;
    }

    @Override
    public ExpenseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_expense_item_layout, parent, false);
        return new ExpenseHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseHolder holder, int position) {
        holder.expenseTitle.setText(expense.get(position).expenseTitle);
    }

    @Override
    public int getItemCount() {
        return expense.size();
    }

    public void addExpenses(ArrayList<Expense> expenses) {
        expense.addAll(expenses);
        notifyDataSetChanged();
    }

    public static class ExpenseHolder extends RecyclerView.ViewHolder {
        TextView expenseTitle;

        public ExpenseHolder(View itemView) {
            super(itemView);
            expenseTitle = (TextView) itemView.findViewById(R.id.tv_expense_title);
        }
    }
}
