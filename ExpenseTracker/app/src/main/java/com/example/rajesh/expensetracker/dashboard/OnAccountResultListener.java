package com.example.rajesh.expensetracker.dashboard;


public interface OnAccountResultListener {
    void onAccountByMonthListSuccess(long totalAmount);

    void onAccountByMonthListFailure(String message);
}
