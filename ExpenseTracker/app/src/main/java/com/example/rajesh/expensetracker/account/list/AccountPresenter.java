package com.example.rajesh.expensetracker.account.list;


import com.example.rajesh.expensetracker.account.Account;

import java.util.ArrayList;

public class AccountPresenter implements AccountPresenterContract, AccountListListener {
    AccountView mAccountView;
    AccountModelContract accountModelContract;

    public AccountPresenter(AccountView accountView) {
        this.mAccountView = accountView;
        this.accountModelContract = new AccountModel();
    }

    @Override
    public void getAccounts(String accountType) {
        accountModelContract.getAccounts(this, accountType);
    }

    @Override
    public void getDistinctRecurringAccounts(String accountType) {
        accountModelContract.getDistinctAccounts(this, accountType);
    }

    @Override
    public void onAccountListSuccess(ArrayList<Account> accounts) {
        mAccountView.showAccounts(accounts);
    }

    @Override
    public void onAccountListFailure(String message) {
        mAccountView.showError(message);
    }
}
