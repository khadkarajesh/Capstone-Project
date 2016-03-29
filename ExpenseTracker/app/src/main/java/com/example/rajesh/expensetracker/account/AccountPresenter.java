package com.example.rajesh.expensetracker.account;


public class AccountPresenter implements AccountPresenterContract, AccountAddListener {

    AccountView accountView;
    AccountModel accountModel;

    public AccountPresenter(AccountView accountView) {
        this.accountView = accountView;
        accountModel = new AccountModel();
    }

    @Override
    public void addAccount(Account account) {
        accountModel.saveAccount(account, this);
    }


    @Override
    public void accountCreationSuccess() {
        accountView.navigateToActivity();
    }

    @Override
    public void accountCreationFailure(String message) {
        accountView.showFailureMessage(message);
    }
}
