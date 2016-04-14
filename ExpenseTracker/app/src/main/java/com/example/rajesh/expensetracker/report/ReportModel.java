package com.example.rajesh.expensetracker.report;


import android.database.Cursor;
import android.net.Uri;

import com.example.rajesh.expensetracker.ExpenseTrackerApplication;
import com.example.rajesh.expensetracker.category.ExpenseCategory;
import com.example.rajesh.expensetracker.data.ExpenseTrackerContract;
import com.example.rajesh.expensetracker.utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportModel implements ReportModelContract {

    @Override
    public void getExpenseByCategory(ArrayList<ExpenseCategory> arrayList, ReportFragment.ReportType reportType, OnExpenseListListener onExpenseListListener) {
        int totalAmount = 0;

        HashMap<ExpenseCategory, Integer> hashMap = new HashMap<>();
        reportType = ReportFragment.ReportType.REPORT_BY_WEEK;
        DateTimeUtil.getTimeInMilliSecondsByReportType(reportType);
        for (ExpenseCategory expenseCategory : arrayList) {
            Uri uri = ExpenseTrackerContract.ExpenseEntry.buildExpenseByCategoryIdUri(String.valueOf(expenseCategory.id), String.valueOf(DateTimeUtil.getTimeInMilliSecondsByReportType(reportType)[0]), String.valueOf(DateTimeUtil.getTimeInMilliSecondsByReportType(reportType)[1]));
            Cursor cursor = ExpenseTrackerApplication.getExpenseTrackerApplication().getContentResolver().query(uri, null, null, null, null);

            if (cursor.moveToNext()) {
                totalAmount = cursor.getInt(0);
            } else {
                totalAmount = -1;
            }
            hashMap.put(expenseCategory, totalAmount);
        }

        if (hashMap.size() > 0) {
            onExpenseListListener.listExpenseByCategory(hashMap);
        } else {
            onExpenseListListener.onFailureToListExpense("Failed to load data");
        }
    }
}
