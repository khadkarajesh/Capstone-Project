package com.example.rajesh.expensetracker.account.edit;


import com.example.rajesh.expensetracker.account.Account;

public interface AddAccountModelContract {
    void saveAccount(Account account, AccountAddListener accountAddListener);

    void updateAccount(Account account, AccountAddListener accountAddListener);
}
