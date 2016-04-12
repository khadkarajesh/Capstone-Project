package com.example.rajesh.expensetracker.expense;


public interface OnExpenseSaveListener {
    void onExpenseSaveSuccess();

    void onExpenseSaveFailure(String message);
}
