package com.example.rajesh.expensetracker.account.list;


public interface AccountModelContract {
    void getAccounts(AccountListListener accountListListener, String accountType);

    void getDistinctAccounts(AccountListListener accountListListener, String accountType);
}
