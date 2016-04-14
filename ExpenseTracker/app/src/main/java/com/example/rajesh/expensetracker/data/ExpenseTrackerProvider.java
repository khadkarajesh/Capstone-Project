package com.example.rajesh.expensetracker.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.rajesh.expensetracker.Constant;

import timber.log.Timber;


public class ExpenseTrackerProvider extends ContentProvider {

    ExpenseTrackerDbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    UriMatcher uriMatcher = buildUriMatcher();

    private static final int ACCOUNT = 100;
    private static final int SINGLE_ACCOUNT = 101;
    private static final int ACCOUNT_LIST = 102;
    private static final int ACCOUNT_BY_ID = 103;
    private static final int ACCOUNTS_BY_MONTH = 104;


    private static final int EXPENSE = 200;
    private static final int SINGLE_EXPENSE = 201;
    private static final int EXPENSE_LIST = 202;
    private static final int EXPENSE_WITH_CATEGORY = 203;
    private static final int EXPENSE_BY_ID = 204;
    private static final int RECURRING_EXPENSE_WITH_CATEGORY = 205;
    private static final int EXPENSE_BY_CATEGORY = 206;


    private static final int CATEGORY = 300;
    private static final int SINGLE_CATEGORY = 301;
    private static final int CATEGORY_LIST = 302;
    private static final int CATEGORY_BY_ID = 303;

    public static String EXPENSE_SORT_BY_DATE = ExpenseTrackerContract.ExpenseEntry.TABLE_NAME + "." + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_DATE + " DESC";
    private String EXPENSE_BY_CATEGORY_ID = ExpenseTrackerContract.ExpenseEntry._ID + " = ?" + " AND " + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_DATE + " >= ?" + " AND " + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_DATE + " <= ?";

    public String startDate, endDate;
    public static SQLiteQueryBuilder sqLiteQueryBuilder;
    public String CATEGORY_AND_EXPENSE_BY_MONTH = ExpenseTrackerContract.ExpenseEntry.TABLE_NAME
            + "."
            + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_DATE
            + " >= ? "
            + " AND "
            + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME
            + "."
            + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_DATE
            + " <= ?";

    public String CATEGORY_AND_RECURRING_EXPENSE_BY_MONTH = ExpenseTrackerContract.ExpenseEntry.TABLE_NAME
            + "."
            + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_DATE
            + " >= ? "
            + " AND "
            + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME
            + "."
            + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_DATE
            + " <= ?"
            + " AND "
            + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME
            + "."
            + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_TYPE + " = ?";

    public String CATEGORY_AND_RECURRING_EXPENSE_ONLY = ExpenseTrackerContract.ExpenseEntry.TABLE_NAME
            + "."
            + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_TYPE + " = ?";

    private String ACCOUNTS_BY_MONTH_SELECTION = ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_CREATED_DATE + " >= ?" + " AND " + ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_CREATED_DATE + " <= ?" + " OR " + ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_TYPE + "= ?";

    static {
        sqLiteQueryBuilder = new SQLiteQueryBuilder();
        String JOIN_EXPENSE_AND_CATEGORY_TABLE = ExpenseTrackerContract.ExpenseCategoriesEntry.TABLE_NAME
                + " INNER JOIN "
                + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME
                + " ON "
                + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME + "." + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_CATEGORIES_ID
                + " = "
                + ExpenseTrackerContract.ExpenseCategoriesEntry.TABLE_NAME + "." + ExpenseTrackerContract.ExpenseCategoriesEntry._ID;

        sqLiteQueryBuilder.setTables(JOIN_EXPENSE_AND_CATEGORY_TABLE);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new ExpenseTrackerDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor = null;
        sqLiteDatabase = dbHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case CATEGORY:
                retCursor = sqLiteDatabase.query(ExpenseTrackerContract.ExpenseCategoriesEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case EXPENSE:
                retCursor = sqLiteDatabase.query(ExpenseTrackerContract.ExpenseEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case EXPENSE_WITH_CATEGORY:
                startDate = ExpenseTrackerContract.ExpenseEntry.getStartDateOfMonth(uri);
                endDate = ExpenseTrackerContract.ExpenseEntry.getLastDateOfMonth(uri);
                retCursor = sqLiteQueryBuilder.query(dbHelper.getWritableDatabase(), null, CATEGORY_AND_EXPENSE_BY_MONTH, new String[]{startDate, endDate}, null, null, sortOrder);
                break;
            case ACCOUNT:
                retCursor = sqLiteDatabase.query(ExpenseTrackerContract.AccountEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ACCOUNTS_BY_MONTH:
                startDate = ExpenseTrackerContract.AccountEntry.getStartDateOfMonth(uri);
                endDate = ExpenseTrackerContract.AccountEntry.getLastDateOfMonth(uri);
                retCursor = sqLiteDatabase.rawQuery("SELECT SUM (" + ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_AMOUNT + ") FROM " + ExpenseTrackerContract.AccountEntry.TABLE_NAME + " WHERE " + ACCOUNTS_BY_MONTH_SELECTION, new String[]{startDate, endDate, Constant.RECURRING_TYPE});
                break;
            case EXPENSE_BY_CATEGORY:
                String categoryId = ExpenseTrackerContract.ExpenseEntry.getCategoryId(uri);
                startDate = ExpenseTrackerContract.ExpenseEntry.getStartTimeStamp(uri);
                endDate = ExpenseTrackerContract.ExpenseEntry.getEndTimeStamp(uri);
                retCursor = sqLiteDatabase.rawQuery("SELECT SUM (" + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_AMOUNT + ") FROM " + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME + " WHERE " + EXPENSE_BY_CATEGORY_ID, new String[]{categoryId, startDate, endDate});
                Timber.d("cursor is here");
                break;
            case RECURRING_EXPENSE_WITH_CATEGORY:
                //startDate = ExpenseTrackerContract.ExpenseEntry.getStartDateOfMonth(uri);
                //endDate = ExpenseTrackerContract.ExpenseEntry.getLastDateOfMonth(uri);
                String expenseType = ExpenseTrackerContract.ExpenseEntry.getExpenseType(uri);
                retCursor = sqLiteQueryBuilder.query(dbHelper.getWritableDatabase(), null, CATEGORY_AND_RECURRING_EXPENSE_ONLY, new String[]{expenseType}, null, null, sortOrder);
                Timber.d("cursor size :::: %d", retCursor.getCount());
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
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
            case ACCOUNT_BY_ID:
                return ExpenseTrackerContract.AccountEntry.CONTENT_ITEM_TYPE;
            case EXPENSE:
                return ExpenseTrackerContract.ExpenseEntry.CONTENT_ITEM_TYPE;
            case EXPENSE_LIST:
                return ExpenseTrackerContract.ExpenseEntry.CONTENT_TYPE;
            case RECURRING_EXPENSE_WITH_CATEGORY:
                return ExpenseTrackerContract.ExpenseEntry.CONTENT_TYPE;
            case CATEGORY:
                return ExpenseTrackerContract.ExpenseCategoriesEntry.CONTENT_ITEM_TYPE;
            case CATEGORY_LIST:
                return ExpenseTrackerContract.ExpenseCategoriesEntry.CONTENT_TYPE;
            case EXPENSE_WITH_CATEGORY:
                return ExpenseTrackerContract.ExpenseEntry.CONTENT_TYPE;
            case CATEGORY_BY_ID:
                return ExpenseTrackerContract.ExpenseCategoriesEntry.CONTENT_ITEM_TYPE;
            case EXPENSE_BY_ID:
                return ExpenseTrackerContract.ExpenseEntry.CONTENT_ITEM_TYPE;
            case EXPENSE_BY_CATEGORY:
                return ExpenseTrackerContract.ExpenseEntry.CONTENT_TYPE;
        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri resultUri = null;
        long _id;
        switch (uriMatcher.match(uri)) {
            case ACCOUNT:
                _id = dbHelper.getWritableDatabase().insert(ExpenseTrackerContract.AccountEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    resultUri = ExpenseTrackerContract.AccountEntry.buildAccountUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case CATEGORY:
                _id = dbHelper.getWritableDatabase().insert(ExpenseTrackerContract.ExpenseCategoriesEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    resultUri = ExpenseTrackerContract.ExpenseCategoriesEntry.buildAccountUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case EXPENSE:
                _id = dbHelper.getWritableDatabase().insert(ExpenseTrackerContract.ExpenseEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    resultUri = ExpenseTrackerContract.ExpenseEntry.buildExpenseUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowDeleted = 0;
        switch (uriMatcher.match(uri)) {
            case CATEGORY_BY_ID:
                int categoryId = ExpenseTrackerContract.ExpenseCategoriesEntry.getCategoryId(uri);
                rowDeleted = dbHelper.getWritableDatabase().delete(ExpenseTrackerContract.ExpenseCategoriesEntry.TABLE_NAME, ExpenseTrackerContract.ExpenseCategoriesEntry._ID + " = ?", new String[]{"" + categoryId});
                break;
            case EXPENSE_BY_ID:
                int expenseId = ExpenseTrackerContract.ExpenseEntry.getExpenseId(uri);
                rowDeleted = dbHelper.getWritableDatabase().delete(ExpenseTrackerContract.ExpenseEntry.TABLE_NAME, ExpenseTrackerContract.ExpenseEntry._ID + " =?", new String[]{"" + expenseId});
                break;
            case ACCOUNT_BY_ID:
                int accountId = ExpenseTrackerContract.AccountEntry.getAccountId(uri);
                rowDeleted = dbHelper.getWritableDatabase().delete(ExpenseTrackerContract.AccountEntry.TABLE_NAME, ExpenseTrackerContract.AccountEntry._ID + " =?", new String[]{"" + accountId});
                break;
            default:
                Timber.d("failed to delete data");
        }
        if (rowDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updateId = 0;
        switch (uriMatcher.match(uri)) {
            case CATEGORY_BY_ID:
                int categoryId = ExpenseTrackerContract.ExpenseCategoriesEntry.getCategoryId(uri);
                updateId = dbHelper.getWritableDatabase().update(ExpenseTrackerContract.ExpenseCategoriesEntry.TABLE_NAME, values, ExpenseTrackerContract.ExpenseCategoriesEntry._ID + " = ?", new String[]{"" + categoryId});
                break;
            case EXPENSE_BY_ID:
                int expenseId = ExpenseTrackerContract.ExpenseEntry.getExpenseId(uri);
                Timber.d("expense id %d", expenseId);
                updateId = dbHelper.getWritableDatabase().update(ExpenseTrackerContract.ExpenseEntry.TABLE_NAME, values, ExpenseTrackerContract.ExpenseEntry._ID + " =?", new String[]{"" + expenseId});
                break;
            case ACCOUNT_BY_ID:
                int accountId = ExpenseTrackerContract.AccountEntry.getAccountId(uri);
                updateId = dbHelper.getWritableDatabase().update(ExpenseTrackerContract.AccountEntry.TABLE_NAME, values, ExpenseTrackerContract.ExpenseEntry._ID + " =?", new String[]{"" + accountId});
                break;
        }
        if (updateId != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateId;
    }

    static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        String authority = ExpenseTrackerContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, ExpenseTrackerContract.ACCOUNT_PATH, ACCOUNT);
        uriMatcher.addURI(authority, ExpenseTrackerContract.ACCOUNT_PATH + "/*", ACCOUNT_LIST);
        uriMatcher.addURI(authority, ExpenseTrackerContract.ACCOUNT_PATH + "/#", SINGLE_ACCOUNT);
        uriMatcher.addURI(authority, ExpenseTrackerContract.ACCOUNT_PATH + "/*", ACCOUNT_BY_ID);
        uriMatcher.addURI(authority, ExpenseTrackerContract.ACCOUNT_PATH + "/*/*", ACCOUNTS_BY_MONTH);

        uriMatcher.addURI(authority, ExpenseTrackerContract.EXPENSE_CATEGORIES_PATH, CATEGORY);
        uriMatcher.addURI(authority, ExpenseTrackerContract.EXPENSE_CATEGORIES_PATH + "/*", CATEGORY_LIST);
        uriMatcher.addURI(authority, ExpenseTrackerContract.EXPENSE_CATEGORIES_PATH + "/#", SINGLE_CATEGORY);
        uriMatcher.addURI(authority, ExpenseTrackerContract.EXPENSE_CATEGORIES_PATH + "/*", CATEGORY_BY_ID);


        uriMatcher.addURI(authority, ExpenseTrackerContract.EXPENSE_PATH, EXPENSE);
        uriMatcher.addURI(authority, ExpenseTrackerContract.EXPENSE_PATH + "/*", EXPENSE_LIST);
        uriMatcher.addURI(authority, ExpenseTrackerContract.EXPENSE_PATH + "/#", SINGLE_EXPENSE);
        uriMatcher.addURI(authority, ExpenseTrackerContract.EXPENSE_PATH + "/*/*", EXPENSE_WITH_CATEGORY);
        uriMatcher.addURI(authority, ExpenseTrackerContract.EXPENSE_PATH + "/*", EXPENSE_BY_ID);
        uriMatcher.addURI(authority, ExpenseTrackerContract.EXPENSE_PATH +"/*/*/*/*", RECURRING_EXPENSE_WITH_CATEGORY);
        uriMatcher.addURI(authority, ExpenseTrackerContract.EXPENSE_PATH + "/*/*/*", EXPENSE_BY_CATEGORY);

        return uriMatcher;
    }


}
