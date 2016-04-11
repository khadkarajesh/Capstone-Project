package com.example.rajesh.expensetracker.account.list;

import android.database.Cursor;

import com.example.rajesh.expensetracker.ExpenseTrackerApplication;
import com.example.rajesh.expensetracker.account.Account;
import com.example.rajesh.expensetracker.data.ExpenseTrackerContract;

import java.util.ArrayList;

public class AccountModel implements AccountModelContract {
    @Override
    public void getAccounts(AccountListListener accountListListener) {

        ArrayList<Account> accounts = new ArrayList<>();

        Cursor cursor = ExpenseTrackerApplication.getExpenseTrackerApplication().getContentResolver().query(ExpenseTrackerContract.AccountEntry.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Account account = new Account();
                account.accountId = cursor.getInt(cursor.getColumnIndex(ExpenseTrackerContract.AccountEntry._ID));
                account.accountName = cursor.getString(cursor.getColumnIndex(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_TITLE));
                account.amount = cursor.getInt(cursor.getColumnIndex(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_AMOUNT));
                account.date = cursor.getInt(cursor.getColumnIndex(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_CREATED_DATE));
                account.accountType = cursor.getString(cursor.getColumnIndex(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_TYPE));
                accounts.add(account);
            }
            accountListListener.onAccountListSuccess(accounts);
        } else {
            accountListListener.onAccountListFailure("Failed to list data");
        }
    }
}
