package com.example.rajesh.expensetracker.account.list;


import com.example.rajesh.expensetracker.account.Account;

import java.util.ArrayList;

public interface AccountListListener {
    void onAccountListSuccess(ArrayList<Account> accounts);

    void onAccountListFailure(String message);
}
