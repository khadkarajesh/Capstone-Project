package com.example.rajesh.expensetracker.report;


import com.example.rajesh.expensetracker.category.ExpenseCategory;

import java.util.HashMap;

public interface ReportView {
    void provideTotalAccountByTimeStamp(long totalAmount);

    void provideExpenseByCategory(HashMap<ExpenseCategory, Integer> hashMap);
}
