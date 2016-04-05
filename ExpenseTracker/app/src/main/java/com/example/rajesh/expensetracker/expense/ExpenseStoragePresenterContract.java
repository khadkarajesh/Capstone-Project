package com.example.rajesh.expensetracker.expense;

import com.example.rajesh.expensetracker.dashboard.Expense;


public interface ExpenseStoragePresenterContract {
    void saveExpense(Expense expense);
}
