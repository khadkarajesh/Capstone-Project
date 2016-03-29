package com.example.rajesh.expensetracker.account;


public interface AccountAddListener {
    void accountCreationSuccess();

    void accountCreationFailure(String message);
}
