package com.example.rajesh.expensetracker.account.list;


import com.example.rajesh.expensetracker.account.Account;

import java.util.ArrayList;

public interface AccountView {
    void showAccounts(ArrayList<Account> accounts);

    void showError(String message);
}
