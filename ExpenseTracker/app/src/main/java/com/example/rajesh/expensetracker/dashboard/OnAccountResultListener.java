package com.example.rajesh.expensetracker.dashboard;


public interface OnAccountResultListener {
    void onAccountByTimeStampListSuccess(long totalAmount);

    void onAccountByTimeStampListFailure(String message);
}
