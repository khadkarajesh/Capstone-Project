package com.example.rajesh.expensetracker.account.list;


public interface AccountPresenterContract {
    void getAccounts(String accountType);

    void getDistinctRecurringAccounts(String accountType);
}
