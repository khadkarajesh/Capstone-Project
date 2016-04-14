package com.example.rajesh.expensetracker.report;


import com.example.rajesh.expensetracker.category.ExpenseCategory;

import java.util.ArrayList;

public interface ReportModelContract {
    void getExpenseByCategory(ArrayList<ExpenseCategory> arrayList, ReportFragment.ReportType reportType, OnExpenseListListener onExpenseListListener);
}
