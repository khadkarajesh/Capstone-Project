package com.example.rajesh.expensetracker.account.edit;


import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;

import com.example.rajesh.expensetracker.ExpenseTrackerApplication;
import com.example.rajesh.expensetracker.account.Account;
import com.example.rajesh.expensetracker.data.ExpenseTrackerContract;

public class AddAccountModel implements AddAccountModelContract {

    public AddAccountModel() {
    }

    @Override
    public void saveAccount(Account account, AccountAddListener accountAddListener) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_TITLE, account.accountName);
        contentValues.put(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_AMOUNT, account.amount);
        contentValues.put(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_CREATED_DATE, account.date);
        contentValues.put(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_TYPE, account.accountType);

        Uri uri = ExpenseTrackerApplication.getExpenseTrackerApplication().getContentResolver().insert(ExpenseTrackerContract.AccountEntry.CONTENT_URI, contentValues);

        if (ContentUris.parseId(uri) > 0) {
            accountAddListener.accountCreationSuccess();
        } else {
            accountAddListener.accountCreationFailure("failed to insert data");
        }
    }

    @Override
    public void updateAccount(Account account, AccountAddListener accountAddListener) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_TITLE, account.accountName);
        contentValues.put(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_AMOUNT, account.amount);
        contentValues.put(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_CREATED_DATE, account.date);
        contentValues.put(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_TYPE, account.accountType);
        int updatedId = ExpenseTrackerApplication.getExpenseTrackerApplication().getContentResolver().update(ExpenseTrackerContract.AccountEntry.buildAccountUri(account.accountId), contentValues, null, null);
        if (updatedId > 0) {
            accountAddListener.accountCreationSuccess();
        } else {
            accountAddListener.accountCreationFailure("Failed to Update Account");
        }
    }
}
