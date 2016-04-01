package com.example.rajesh.expensetracker.category;


import java.util.ArrayList;

public interface OnCategoryLoadedListener {
    void onCategoryLoadedSuccess(ArrayList<ExpenseCategory> categories);

    void onCategoryLoadedFailure(String message);
}
