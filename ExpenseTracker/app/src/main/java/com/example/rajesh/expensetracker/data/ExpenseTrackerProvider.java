package com.example.rajesh.expensetracker.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;


public class ExpenseTrackerProvider extends ContentProvider {

    ExpenseTrackerDbHelper dbHelper;
    UriMatcher uriMatcher = buildUriMatcher();

    private static final int ACCOUNT = 100;
    private static final int SINGLE_ACCOUNT = 101;
    private static final int ACCOUNT_LIST = 102;


    private static final int EXPENSE = 200;
    private static final int SINGLE_EXPENSE = 201;
    private static final int EXPENSE_LIST = 202;


    private static final int CATEGORY = 300;
    private static final int SINGLE_CATEGORY = 301;
    private static final int CATEGORY_LIST = 302;

    @Override
    public boolean onCreate() {
        dbHelper = new ExpenseTrackerDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        UriMatcher uriMatcher = buildUriMatcher();
        switch (uriMatcher.match(uri)) {
            case ACCOUNT:
                return ExpenseTrackerContract.AccountEntry.CONTENT_ITEM_TYPE;
            case ACCOUNT_LIST:
                return ExpenseTrackerContract.AccountEntry.CONTENT_TYPE;
            case SINGLE_EXPENSE:
                return ExpenseTrackerContract.ExpenseEntry.CONTENT_ITEM_TYPE;
            case EXPENSE_LIST:
                return ExpenseTrackerContract.ExpenseEntry.CONTENT_TYPE;
            case SINGLE_CATEGORY:
                return ExpenseTrackerContract.ExpenseCategoriesEntry.CONTENT_ITEM_TYPE;
            case CATEGORY_LIST:
                return ExpenseTrackerContract.ExpenseCategoriesEntry.CONTENT_TYPE;
        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri resultUri = null;
        switch (uriMatcher.match(uri)) {
            case ACCOUNT:
                long _id = dbHelper.getWritableDatabase().insert(ExpenseTrackerContract.AccountEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    resultUri = ExpenseTrackerContract.AccountEntry.buildAccountUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case SINGLE_CATEGORY:
                dbHelper.getWritableDatabase().insert(ExpenseTrackerContract.ExpenseCategoriesEntry.TABLE_NAME, null, values);
                break;
            case SINGLE_EXPENSE:
                dbHelper.getWritableDatabase().insert(ExpenseTrackerContract.ExpenseEntry.TABLE_NAME, null, values);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        String authority = ExpenseTrackerContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, ExpenseTrackerContract.ACCOUNT_PATH, ACCOUNT);
        uriMatcher.addURI(authority, ExpenseTrackerContract.ACCOUNT_PATH + "/*", ACCOUNT_LIST);
        uriMatcher.addURI(authority, ExpenseTrackerContract.ACCOUNT_PATH + "/#", SINGLE_ACCOUNT);

        uriMatcher.addURI(authority, ExpenseTrackerContract.EXPENSE_CATEGORIES_PATH + "/*", CATEGORY_LIST);
        uriMatcher.addURI(authority, ExpenseTrackerContract.EXPENSE_CATEGORIES_PATH + "/#", SINGLE_CATEGORY);

        return uriMatcher;
    }


}
