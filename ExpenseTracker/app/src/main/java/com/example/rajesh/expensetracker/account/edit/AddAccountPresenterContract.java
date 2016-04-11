package com.example.rajesh.expensetracker.account.edit;


import com.example.rajesh.expensetracker.account.Account;

public interface AddAccountPresenterContract {
    void addAccount(Account account);

    void updateAccount(Account account);
}
