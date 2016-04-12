package com.example.rajesh.expensetracker.dashboard;


public interface ExpenseModelContract {
    void getExpense(OnExpenseResultListener onExpenseResultListener, String expenseType);

    void getAccountsByMonth(OnAccountResultListener onAccountResultListener);
}
