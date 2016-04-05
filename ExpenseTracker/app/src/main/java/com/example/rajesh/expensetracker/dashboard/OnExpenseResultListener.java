package com.example.rajesh.expensetracker.dashboard;

import com.example.rajesh.expensetracker.category.ExpenseCategory;

import java.util.ArrayList;


public interface OnExpenseResultListener {
    void onExpenseSuccess(ArrayList<Expense> expenses, ArrayList<ExpenseCategory> expenseCategories);

    void onExpenseFailure();
}
