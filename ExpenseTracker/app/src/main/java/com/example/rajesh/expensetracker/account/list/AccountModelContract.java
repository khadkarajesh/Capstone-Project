package com.example.rajesh.expensetracker.account.list;


import com.example.rajesh.expensetracker.account.list.AccountListListener;

public interface AccountModelContract {
    void getAccounts(AccountListListener accountListListener);
}
