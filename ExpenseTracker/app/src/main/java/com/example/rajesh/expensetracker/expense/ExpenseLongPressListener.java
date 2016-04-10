package com.example.rajesh.expensetracker.expense;


import com.example.rajesh.expensetracker.category.ExpenseCategory;
import com.example.rajesh.expensetracker.dashboard.Expense;

public interface ExpenseLongPressListener {
    void onExpenseLongPress(Expense expense, ExpenseCategory expenseCategory);
}
