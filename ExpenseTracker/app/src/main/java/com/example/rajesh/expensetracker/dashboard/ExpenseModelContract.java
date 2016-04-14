package com.example.rajesh.expensetracker.dashboard;


import com.example.rajesh.expensetracker.report.ReportFragment;

public interface ExpenseModelContract {
    void getExpense(OnExpenseResultListener onExpenseResultListener, String expenseType);

    void getAccountsByTimeStamp(OnAccountResultListener onAccountResultListener, ReportFragment.ReportType type);
}
