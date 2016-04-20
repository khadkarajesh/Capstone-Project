package com.example.rajesh.expensetracker.account.list;

import android.database.Cursor;
import android.net.Uri;

import com.example.rajesh.expensetracker.ExpenseTrackerApplication;
import com.example.rajesh.expensetracker.account.Account;
import com.example.rajesh.expensetracker.data.ExpenseTrackerContract;

import java.util.ArrayList;

import timber.log.Timber;

public class AccountModel implements AccountModelContract {
    @Override
    public void getAccounts(AccountListListener accountListListener, String accountType) {

        ArrayList<Account> accounts = new ArrayList<>();
        String selection = accountType != null ? ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_TYPE + " = '" + accountType + "'" : accountType;
        Cursor cursor = ExpenseTrackerApplication.getExpenseTrackerApplication().getContentResolver().query(ExpenseTrackerContract.AccountEntry.CONTENT_URI, null, selection, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Account account = new Account();
                account.accountId = cursor.getInt(cursor.getColumnIndex(ExpenseTrackerContract.AccountEntry._ID));
                account.accountName = cursor.getString(cursor.getColumnIndex(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_TITLE));
                account.amount = cursor.getInt(cursor.getColumnIndex(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_AMOUNT));
                account.date = cursor.getLong(cursor.getColumnIndex(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_CREATED_DATE));
                account.accountType = cursor.getString(cursor.getColumnIndex(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_TYPE));
                accounts.add(account);
            }
            accountListListener.onAccountListSuccess(accounts);
        } else {
            accountListListener.onAccountListFailure("Failed to list data");
        }
    }

    @Override
    public void getDistinctAccounts(AccountListListener accountListListener, String accountType) {
        ArrayList<Account> accounts = new ArrayList<>();
        Uri uri=ExpenseTrackerContract.AccountEntry.CONTENT_URI.buildUpon().appendPath("distinct").appendPath("recurring").appendPath("type").appendPath("hello").build();
        Cursor cursor=null;
        Timber.d("called here it is");
        cursor = ExpenseTrackerApplication.getExpenseTrackerApplication().getContentResolver().query(uri, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Account account = new Account();
                account.accountId = cursor.getInt(cursor.getColumnIndex(ExpenseTrackerContract.AccountEntry._ID));
                account.accountName = cursor.getString(cursor.getColumnIndex(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_TITLE));
                account.amount = cursor.getInt(cursor.getColumnIndex(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_AMOUNT));
                account.date = cursor.getLong(cursor.getColumnIndex(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_CREATED_DATE));
                account.accountType = cursor.getString(cursor.getColumnIndex(ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_TYPE));
                accounts.add(account);
            }
            accountListListener.onAccountListSuccess(accounts);
        } else {
            accountListListener.onAccountListFailure("Failed to list data");
        }
    }
}
