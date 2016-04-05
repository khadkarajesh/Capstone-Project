package com.example.rajesh.expensetracker.expense;


import com.example.rajesh.expensetracker.dashboard.Expense;

public interface ExpenseViewStorageModelContract {
    void saveExpense(Expense expense,OnExpenseSaveListener onExpenseSaveListener);
}
