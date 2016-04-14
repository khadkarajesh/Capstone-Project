package com.example.rajesh.expensetracker.report;


import com.example.rajesh.expensetracker.category.ExpenseCategory;

import java.util.HashMap;

public interface OnExpenseListListener {
    void listExpenseByCategory(HashMap<ExpenseCategory, Integer> hashMap);

    void onFailureToListExpense(String message);
}
