package com.example.rajesh.expensetracker.dashboard;

import android.database.Cursor;
import android.net.Uri;

import com.example.rajesh.expensetracker.Constant;
import com.example.rajesh.expensetracker.ExpenseTrackerApplication;
import com.example.rajesh.expensetracker.category.ExpenseCategory;
import com.example.rajesh.expensetracker.data.ExpenseTrackerContract;
import com.example.rajesh.expensetracker.data.ExpenseTrackerProvider;
import com.example.rajesh.expensetracker.report.ReportFragment;
import com.example.rajesh.expensetracker.utils.DateTimeUtil;

import java.util.ArrayList;

import timber.log.Timber;


public class ExpenseModel implements ExpenseModelContract {

    @Override
    public void getExpense(OnExpenseResultListener onExpenseResultListener, String expenseType) {
        ArrayList<Expense> expenses = new ArrayList<>();
        ArrayList<ExpenseCategory> expenseCategories = new ArrayList<>();

        //long endOfMonthTimeStamp = DateTimeUtil.getTimeInMilliSeconds(DateTimeUtil.TimeStamp.MONTH_LAST_DAY);
        //long startOfMonthTimeStamp = DateTimeUtil.getTimeInMilliSeconds(DateTimeUtil.TimeStamp.MONTH_FIRST_DAY);

        String milliStart = String.valueOf(DateTimeUtil.getTimeInMilliSecondsByReportType(ReportFragment.ReportType.REPORT_BY_MONTH)[0]);
        String milliEnd = String.valueOf(DateTimeUtil.getTimeInMilliSecondsByReportType(ReportFragment.ReportType.REPORT_BY_MONTH)[1]);

        Timber.d(" milli start %s :: end %s ", milliStart, milliEnd);

        Uri uri;
        Cursor cursor;
        if (expenseType == null) {
            uri = ExpenseTrackerContract.ExpenseEntry.buildExpenseCategoryUri(String.valueOf(DateTimeUtil.getTimeInMilliSecondsByReportType(ReportFragment.ReportType.REPORT_BY_MONTH)[0]), String.valueOf(DateTimeUtil.getTimeInMilliSecondsByReportType(ReportFragment.ReportType.REPORT_BY_MONTH)[1]));
            cursor =
                    ExpenseTrackerApplication.getExpenseTrackerApplication().getContentResolver().query(uri
                            , null
                            , null
                            , null
                            , ExpenseTrackerProvider.EXPENSE_SORT_BY_DATE);
        } else {
            uri = ExpenseTrackerContract.ExpenseEntry.buildExpenseCategoryUriExpenseType(milliStart, milliEnd, Constant.RECURRING_TYPE);
            cursor = ExpenseTrackerApplication.getExpenseTrackerApplication().getContentResolver().query(uri, null, null, null, ExpenseTrackerProvider.EXPENSE_SORT_BY_DATE);
        }

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                Expense expense = new Expense();
                ExpenseCategory expenseCategory = new ExpenseCategory();

                expense.expenseId = cursor.getInt(cursor.getColumnIndexOrThrow(ExpenseTrackerContract.ExpenseEntry._ID));
                expense.expenseTitle = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_TITLE));
                expense.expenseAmount = cursor.getInt(cursor.getColumnIndexOrThrow(ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_AMOUNT));
                expense.categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_CATEGORIES_ID));
                expense.expenseDescription = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_DESCRIPTION));
                expense.expenseType = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_TYPE));
                expense.expenseDate = cursor.getLong(cursor.getColumnIndexOrThrow(ExpenseTrackerContract.ExpenseEntry.COLUMNS_EXPENSE_DATE));

                expenseCategory.categoryTitle = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTrackerContract.ExpenseCategoriesEntry.COLUMNS_CATEGORIES_NAME));
                expenseCategory.categoryColor = cursor.getString(cursor.getColumnIndex(ExpenseTrackerContract.ExpenseCategoriesEntry.COLUMNS_CATEGORIES_COLOR));

                expenses.add(expense);
                expenseCategories.add(expenseCategory);
            }
        }
        onExpenseResultListener.onExpenseSuccess(expenses, expenseCategories);
    }

    @Override
    public void getAccountsByTimeStamp(OnAccountResultListener onAccountResultListener, ReportFragment.ReportType type) {
        long totalAmount = 0;

        long endOfMonthTimeStamp = DateTimeUtil.getTimeInMilliSecondsByReportType(type)[1];
        long startOfMonthTimeStamp = DateTimeUtil.getTimeInMilliSecondsByReportType(type)[0];
        Cursor cursor = ExpenseTrackerApplication.getExpenseTrackerApplication().getContentResolver().query(ExpenseTrackerContract.AccountEntry.buildAccountUriByMonth(String.valueOf(startOfMonthTimeStamp), String.valueOf(endOfMonthTimeStamp)), null, null, null, null);

        if (cursor.moveToFirst()) {
            totalAmount = cursor.getInt(0);
        } else {
            totalAmount = -1;
        }

        if (totalAmount > 0) {
            onAccountResultListener.onAccountByMonthListSuccess(totalAmount);
        } else {
            onAccountResultListener.onAccountByMonthListFailure("There is no amount");
        }
    }


}
