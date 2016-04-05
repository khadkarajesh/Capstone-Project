package com.example.rajesh.expensetracker.expense;


import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;

import com.example.rajesh.expensetracker.ExpenseTrackerApplication;
import com.example.rajesh.expensetracker.dashboard.Expense;
import com.example.rajesh.expensetracker.data.ExpenseTrackerContract;

public class ExpenseStorageModel implements ExpenseViewStorageModelContract {
    public ExpenseStorageModel() {

    }

    @Override
    public void saveExpense(Expense expense, OnExpenseSaveListener onExpenseSaveListener) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_TITLE, expense.expenseTitle);
        contentValues.put(ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_DATE, expense.expenseDate);
        contentValues.put(ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_AMOUNT, expense.expenseAmount);
        contentValues.put(ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_DESCRIPTION, expense.expenseDescription);
        contentValues.put(ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_CATEGORIES_ID, expense.categoryId);
        contentValues.put(ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_TYPE, expense.expenseType);
        Uri uri = ExpenseTrackerApplication.getExpenseTrackerApplication().getContentResolver().insert(ExpenseTrackerContract.ExpenseEntry.CONTENT_URI, contentValues);
        if (ContentUris.parseId(uri) > 0) {
            onExpenseSaveListener.onExpenseSaveSuccess();
        } else {
            onExpenseSaveListener.onExpenseSaveFailure("failed to insert data");
        }
    }
}
