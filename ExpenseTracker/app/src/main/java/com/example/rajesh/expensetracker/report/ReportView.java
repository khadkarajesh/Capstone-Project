package com.example.rajesh.expensetracker.report;


import com.example.rajesh.expensetracker.category.ExpenseCategory;

import java.util.HashMap;

public interface ReportView {
    void showReport(HashMap<ExpenseCategory,Integer> hashMap);
}
