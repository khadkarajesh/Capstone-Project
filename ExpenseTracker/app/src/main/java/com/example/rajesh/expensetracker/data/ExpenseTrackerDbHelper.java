package com.example.rajesh.expensetracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ExpenseTrackerDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Expense.db";
    private static final int DATABASE_VERSION = 1;

    public ExpenseTrackerDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + ExpenseTrackerContract.AccountEntry.TABLE_NAME
                + "("
                + ExpenseTrackerContract.AccountEntry._ID + " INTEGER PRIMARY KEY,"
                + ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_TITLE + " TEXT NOT NULL,"
                + ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_CREATED_DATE + " INTEGER NOT NULL,"
                + ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_AMOUNT + " INTEGER NOT NULL,"
                + ExpenseTrackerContract.AccountEntry.COLUMNS_ACCOUNT_TYPE + " TEXT NOT NULL"
                + ");";

        final String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + ExpenseTrackerContract.ExpenseCategoriesEntry.TABLE_NAME
                + "("
                + ExpenseTrackerContract.ExpenseCategoriesEntry._ID + " INTEGER PRIMARY KEY,"
                + ExpenseTrackerContract.ExpenseCategoriesEntry.COLUMNS_CATEGORIES_NAME + " TEXT NOT NULL,"
                + ExpenseTrackerContract.ExpenseCategoriesEntry.COLUMNS_CATEGORIES_COLOR + " TEXT NOT NULL"
                + ");";

        final String CREATE_EXPENSE_TABLE = "CREATE TABLE " + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME
                + "("
                + ExpenseTrackerContract.ExpenseEntry._ID + " INTEGER PRIMARY KEY,"
                + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_DATE + " INTEGER NOT NULL,"
                + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_TITLE + " TEXT NOT NULL,"
                + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_DESCRIPTION + " TEXT NOT NULL,"
                + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_RECURRING_TYPE + " TEXT NOT NULL,"
                + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_CATEGORIES_ID + " INTEGER NOT NULL"
                + " FOREIGN KEY(" + ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_CATEGORIES_ID + ")"
                + "REFERENCES " + ExpenseTrackerContract.ExpenseCategoriesEntry.TABLE_NAME
                + "(" + ExpenseTrackerContract.ExpenseCategoriesEntry._ID + ")"
                + ");";

        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_EXPENSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ExpenseTrackerContract.AccountEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ExpenseTrackerContract.ExpenseCategoriesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ExpenseTrackerContract.ExpenseEntry.TABLE_NAME);
        onCreate(db);
    }
}
