package com.example.rajesh.expensetracker.dashboard;

import java.util.ArrayList;


public interface OnExpenseResultListener {
    void onExpenseSuccess(ArrayList<Expense> expenses);

    void onExpenseFailure();
}
