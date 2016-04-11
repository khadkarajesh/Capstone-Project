package com.example.rajesh.expensetracker.account.edit;


import com.example.rajesh.expensetracker.account.Account;

public class AddAccountPresenter implements AddAccountPresenterContract, AccountAddListener {

    AccountAddView accountAddView;
    AddAccountModel addAccountModel;

    public AddAccountPresenter(AccountAddView accountAddView) {
        this.accountAddView = accountAddView;
        addAccountModel = new AddAccountModel();
    }

    @Override
    public void addAccount(Account account) {
        addAccountModel.saveAccount(account, this);
    }

    @Override
    public void updateAccount(Account account) {
        addAccountModel.updateAccount(account, this);
    }


    @Override
    public void accountCreationSuccess() {
        accountAddView.navigateToActivity();
    }

    @Override
    public void accountCreationFailure(String message) {
        accountAddView.showFailureMessage(message);
    }
}
